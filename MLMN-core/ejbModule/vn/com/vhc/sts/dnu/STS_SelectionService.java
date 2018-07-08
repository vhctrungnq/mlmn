package vn.com.vhc.sts.dnu;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.core.STS_Global;
import vn.com.vhc.sts.dnu.entity.STS_FileInfo;
import vn.com.vhc.sts.dnu.entity.STS_PatternInfo;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.STS_Util;
import vn.com.vhc.sts.util.ftp.impl.STS_MaxQueueException;
import vn.com.vhc.sts.util.task.STS_IllegalServiceStateException;
import vn.com.vhc.sts.util.task.STS_TaskInfo;
import vn.com.vhc.sts.util.task.STS_TaskService;
import vn.com.vhc.sts.utils.log.STS_DbAppender;

public class STS_SelectionService extends STS_TaskService {

	private static Logger logger = Logger.getLogger(STS_SelectionService.class
			.getName());

	private int retryNumber = -1;
	private int maxExecutor = -1;
	private int maxQueueSize = 0;
	private int taskCount = 0;

	private String baseDirectory = "";
	private String selectionDirectory = "";
	private String illegalDirectory = "";

	private DataSource ds = null;
	private List<STS_PatternInfo> patterns = null;
	private List<STS_FileInfo> illegalFiles = new ArrayList<STS_FileInfo>();

	public STS_SelectionService(String name, DataSource ds) {
		super(name);
		this.ds = ds;
	}

	public void doInit() {
		String value = "";
		try {
			value = STS_Global.SYSTEM_CONFIG
					.getProperty(STS_Setting.MAX_SELECTION_THREAD_KEY);
			maxExecutor = Integer.parseInt(value);
		} catch (Exception e) {
			logger.warn("Invalid '" + STS_Setting.MAX_SELECTION_THREAD_KEY
					+ "' config in C_SYSTEM_CONFIGS_MLMN");
		}

		try {
			value = STS_Global.SYSTEM_CONFIG
					.getProperty(STS_Setting.MAX_SELECTION_RETRY_KEY);
			retryNumber = Integer.parseInt(value);
		} catch (Exception e) {
			logger.warn("Invalid '" + STS_Setting.MAX_SELECTION_RETRY_KEY
					+ "' config in C_SYSTEM_CONFIGS_MLMN");
		}

		try {
			value = STS_Global.SYSTEM_CONFIG
					.getProperty(STS_Setting.MAX_SELECTION_QUEUE_SIZE_KEY);
			maxQueueSize = Integer.parseInt(value);
		} catch (Exception e) {
			logger.warn("Invalid '" + STS_Setting.MAX_SELECTION_QUEUE_SIZE_KEY
					+ "' config in C_SYSTEM_CONFIGS_MLMN");
		}

		try {
			baseDirectory = STS_Global.SYSTEM_CONFIG
					.getProperty(STS_Setting.BASE_LOCAL_DIR);
		} catch (Exception e) {
			logger.warn("Invalid '" + STS_Setting.BASE_LOCAL_DIR
					+ "' config in C_SYSTEM_CONFIGS_MLMN");
		}

		try {
			selectionDirectory = STS_Global.SYSTEM_CONFIG
					.getProperty(STS_Setting.SELECTION_DIR);
		} catch (Exception e) {
			logger.warn("Invalid '" + STS_Setting.SELECTION_DIR
					+ "' config in C_SYSTEM_CONFIGS_MLMN");
		}

		try {
			illegalDirectory = STS_Global.SYSTEM_CONFIG
					.getProperty(STS_Setting.ILLEGAL_DIR);
		} catch (Exception e) {
			logger.warn("Invalid '" + STS_Setting.ILLEGAL_DIR
					+ "' config in C_SYSTEM_CONFIGS_MLMN");
		}

		this.maxExecutor = (this.maxExecutor <= 0) ? 1 : this.maxExecutor;
		this.retryNumber = (this.retryNumber < 0) ? 0 : this.retryNumber;

		initTaskQueues();
	}

	/**
	 * @param input
	 */
	public void doTask(STS_TaskInfo input, Connection conn) {
		STS_FileInfo info = (STS_FileInfo) input;

		String direcPath = selectionDirectory + info.getSubDir();
		try {
			File file = new File(info.getLocalDir(), info.getOriginalName());
			if (!file.exists()) {
				increaseFailureTasks();
				logger.warn("File does not found: " + info.getOriginalName());
				return;
			}
			// replace file name by rule: SERVER_NAME + original file name
			String fileName = replaceFileName(info.getLocalDir(),
					info.getFileName());
			info.setFileName(fileName);
			// TrungNQ kiem tra xem file da co trong thu muc selection chua neu co roi
			// thi xoa file khong move sang selection de tranh trung khoa o bang c_raw_files_mlmn
			File newFile = new File(direcPath, fileName);
			if (newFile.exists()) {
				file.delete();
				System.out.println("da xoa file");
			} else {
				moveTo(file, direcPath, fileName);				
				STS_DbAppender.log(info);
				increaseDoneTasks();
			}
			
		} catch (Exception e) {
			logger.warn("Move file failure: FROM: " + info.getLocalDir()
					+ STS_Global.SEPARATOR + info.getOriginalName() + " --> TO: "
					+ direcPath + STS_Global.SEPARATOR + info.getFileName()
					+ " -- MESSAGE: " + e.getMessage());
			doRetry(info);
		}
	}

	public void doFinallize() {
		this.retryNumber = -1;
		this.maxExecutor = -1;
		this.maxQueueSize = 0;
		this.taskCount = 0;
		this.patterns = null;
		this.illegalFiles.clear();
	}

	public int getMaxExecutorThread() {
		return maxExecutor;
	}

	private void initTaskQueues() {
		try {
			searchFiles();
			handleIllegalFiles();
		} catch (Exception e) {
			logger.warn("Init task with error: " + e.getMessage());
		}
	}

	private void searchFiles() {
		try {
			this.patterns = STS_Setting.getListPatternInfo();
			dirListingRecursive(baseDirectory);
		} catch (STS_MaxQueueException e) {
			logger.info(e.getMessage());
		}
	}

	private void handleIllegalFiles() {
		if (this.illegalFiles.isEmpty()) {
			return;
		}

		Connection conn = null;
		try {
			String direcPath = "";
			String preCommand = "insert into C_ILLEGAL_FILES(DIRECTORY, FILE_NAME) values(?,?)";

			conn = this.ds.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pst = conn.prepareStatement(preCommand);

			for (STS_FileInfo info : illegalFiles) {
				direcPath = illegalDirectory + info.getSubDirectory();
				try {
					File f = new File(info.getLocalDir(), info.getOriginalName());
					if (!f.exists()) {
						logger.warn("File not found: " + f.getName());
						continue;
					}
					moveTo(f, direcPath);

					pst.setString(1, info.getSubDirectory());
					pst.setString(2, info.getFileName());
					pst.execute();
				} catch (Exception e) {
					logger.warn("Move file failure: FILE_NAME: "
							+ info.getFileName() + " -- DIRECTION PATH: "
							+ direcPath + " -- MESSAGE: " + e.getMessage());
				}
			}
			conn.commit();
			pst.close();
		} catch (Exception e) {
			logger.warn("Handle illegal file failure: " + e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
					conn.close();
				} catch (Exception e) {
					logger.warn("Cannot close connection to database");
				}
			}
		}
	}

	private void dirListingRecursive(String path) throws STS_MaxQueueException {
		String filePath = null;
		String fileName = null;
		STS_FileInfo info = null;

		File rootFile = new File(path);
		File[] files = rootFile.listFiles();
		if (files != null && files.length > 0) {
			for (File f : files) {
				fileName = f.getName();
				filePath = path + STS_Global.SEPARATOR + fileName;
				if (f.isDirectory()) {
					dirListingRecursive(filePath);
				} else {
					info = new STS_FileInfo(STS_FileInfo.LOCAL_TYPE);
					info.setFileName(fileName);
					info.setOriginalName(fileName);
					info.setLocalDir(path);
					info.setBaseDir(baseDirectory);

					parseFile(info);
				}
			}
		}
	}

	private void parseFile(STS_FileInfo info) throws STS_MaxQueueException {
		Pattern pattern = null;
		Matcher matcher = null;
		int indexGroup = -1;

		for (STS_PatternInfo patternInfo : this.patterns) {
			pattern = Pattern.compile(patternInfo.getFilePattern());
			matcher = pattern.matcher(info.getFileName());

			if (matcher.matches()) {
				if (taskCount >= maxQueueSize) {
					throw new STS_MaxQueueException("Queue reach " + maxQueueSize
							+ " limit");
				}

				info.setPatternId(patternInfo.getPatternId());
				info.setRawTable(patternInfo.getRawTable());
				info.setSubDir(patternInfo.getSubDir());
				// Add by ThangPX
				info.setServerNode(patternInfo.getServerNode());
				/*--------- add by THANHLV -------------*/
				if(patternInfo.getTimePatternGroup() == -1){
					// set date
					
					SimpleDateFormat format = new SimpleDateFormat(STS_Setting.DATE_FORMAT);
					Date tempDate = new Date();
					String date = format.format(tempDate);
					info.setDate(date);
					info.setHour(0);
					if(patternInfo.getFilePattern().indexOf("sar")!= -1)
					{
						String[] temp = info.getLocalDir().split("/");
						int length = temp.length;
						info.setFileName( temp[length-1] + "_" +info.getOriginalName());
					}
					
				}
				
				/*--------- add by QuanNH -------------*/
				/*--------- update by ThangPX -------------*/
				if(patternInfo.getFilePattern().indexOf("RMFS") != -1 || patternInfo.getFilePattern().indexOf("R110") != -1 || patternInfo.getFilePattern().indexOf("R180") != -1|| patternInfo.getFilePattern().indexOf("R018") != -1|| patternInfo.getFilePattern().indexOf("cellname") != -1){
					String[] temp = info.getLocalDir().split("/");
					int length = temp.length;
					
					// set node name
					info.setNodeName(temp[length-2]);
					
					// set date
					String date = "";
					if(patternInfo.getFilePattern().indexOf("cellname") != -1){
						date = STS_Util.getTime(temp[length-1], "yyMMdd_HHmm", STS_Setting.DATE_FORMAT);
					}else{
						date = STS_Util.getTime(temp[length-1], "yyyyMMdd", STS_Setting.DATE_FORMAT);
					}
					info.setDate(date);
					
					// set hour
					indexGroup = patternInfo.getTimePatternGroup();
					String hour = "";
					if (indexGroup > -1) {
						hour = matcher.group(indexGroup);
					}
					if (!hour.isEmpty()) {
						try {
							info.setHour(Integer.parseInt(hour) - 1);
						} catch (NumberFormatException nfe) {
							info.setHour(0);
						}
					}
					
					// set file name = node name + date + original name
					info.setFileName(info.getNodeName() + "_" + temp[length-1] + "_" +info.getOriginalName());
				/*------------------------*/	
				} 
				addTask(info);
				taskCount++;
				return;
			}
		}

		// when the file is illegal, put to list object.
		this.illegalFiles.add(info);
	}

	private void moveTo(File file, String direcPath) throws IOException {
		this.moveTo(file, direcPath, file.getName());
	}

	private void moveTo(File file, String direcPath, String fileName)
			throws IOException {
		// Destination directory
		File dirFile = new File(direcPath);
		if (!dirFile.exists()) {
			if (!dirFile.mkdirs()) {
				throw new IOException("Make directory failure");
			}
		}
		// Move file to new directory
		boolean result = file.renameTo(new File(direcPath, fileName));
		if (!result) {
			throw new IOException("Does't move file to direction path");
		}

		logger.info("Move file: " + file.getName() + " success");
	}

	private String replaceFileName(String path, String fileName) {
		String name = "";
		String serverName = this.getServerName(path);
		if (!serverName.isEmpty()) {
			name = serverName + "_" + fileName;
		} else {
			name = fileName;
		}
		
		name = name.replace("+", "x");
		return name;
	}

	private String getServerName(String path) {
		String serverName = "";
		if (path != null && path.length() > 0) {
			String tempPath = path.replace(baseDirectory, "");
			tempPath = tempPath.substring(1, tempPath.length());

			int index = tempPath.indexOf(STS_Global.SEPARATOR);
			if (index >= 0) {
				serverName = tempPath.substring(0, index);
			} else {
				serverName = tempPath;
			}
		}
		return serverName;
	}

	/**
	 * 
	 * @param entity
	 */
	private void doRetry(STS_FileInfo entity) {
		int count = entity.getRetryCount();
		if (count < retryNumber) {
			count += 1;
			entity.setRetryCount(count);
			try {
				retryTask(entity);
			} catch (STS_IllegalServiceStateException i) {
				logger.warn("Retry selection failure: ", i);
			}
		} else {
			increaseFailureTasks();
			logger.warn("Selection " + entity.getLocalDir()
					+ STS_Global.SEPARATOR + entity.getFileName()
					+ " failure after " + retryNumber + " retries");
		}
	}
}
