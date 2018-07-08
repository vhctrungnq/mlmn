package inventory.dnu;

import inventory.core.IN_Global;
import inventory.entity.IN_FileInfo;
import inventory.entity.IN_PatternInfo;
import inventory.util.IN_Setting;
import inventory.util.IN_Util;
import inventory.util.task.IN_IllegalServiceStateException;
import inventory.util.task.IN_TaskInfo;
import inventory.util.task.IN_TaskService;
import inventory.util.telnet.impl.IN_MaxQueueException;
import inventory.utils.log.IN_DbAppender;

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




public class IN_SelectionService extends IN_TaskService {

	private static Logger logger = Logger.getLogger(IN_SelectionService.class
			.getName());

	private int retryNumber = -1;
	private int maxExecutor = -1;
	private int maxQueueSize = 0;
	private int taskCount = 0;  
	
	//Duong dan lay,di chuyen file trong thu muc download
	private String baseDirectory = "";
	//Duong dan tao,lay,xoa file trong thu muc selection
	private String selectionDirectory = "";
	//Duong dan tao,lay,xoa file trong thu muc illegal
	private String illegalDirectory = "";

	private DataSource ds = null;
	private List<IN_PatternInfo> patterns = null;
	private List<IN_FileInfo> illegalFiles = new ArrayList<IN_FileInfo>();

	public IN_SelectionService(String name, DataSource ds) {
		super(name);
		this.ds = ds;
	}

	public void doInit() {
		String value = "";
		try {
			value = IN_Global.SYSTEM_CONFIG
					.getProperty(IN_Setting.MAX_SELECTION_THREAD_KEY);
			maxExecutor = Integer.parseInt(value);
		} catch (Exception e) {
			logger.warn("Invalid '" + IN_Setting.MAX_SELECTION_THREAD_KEY
					+ "' config in C_SYSTEM_CONFIGS_MLMN");
		}

		try {
			value = IN_Global.SYSTEM_CONFIG
					.getProperty(IN_Setting.MAX_SELECTION_RETRY_KEY);
			retryNumber = Integer.parseInt(value);
		} catch (Exception e) {
			logger.warn("Invalid '" + IN_Setting.MAX_SELECTION_RETRY_KEY
					+ "' config in C_SYSTEM_CONFIGS_MLMN");
		}

		try {
			value = IN_Global.SYSTEM_CONFIG
					.getProperty(IN_Setting.MAX_SELECTION_QUEUE_SIZE_KEY);
			maxQueueSize = Integer.parseInt(value);
		} catch (Exception e) {
			logger.warn("Invalid '" + IN_Setting.MAX_SELECTION_QUEUE_SIZE_KEY
					+ "' config in C_SYSTEM_CONFIGS_MLMN");
		}

		try {
			baseDirectory = IN_Global.SYSTEM_CONFIG
					.getProperty(IN_Setting.BASE_LOCAL_DIR);
		} catch (Exception e) {
			logger.warn("Invalid '" + IN_Setting.BASE_LOCAL_DIR
					+ "' config in C_SYSTEM_CONFIGS_MLMN");
		}

		try {
			selectionDirectory = IN_Global.SYSTEM_CONFIG
					.getProperty(IN_Setting.SELECTION_DIR);
		} catch (Exception e) {
			logger.warn("Invalid '" + IN_Setting.SELECTION_DIR
					+ "' config in C_SYSTEM_CONFIGS_MLMN");
		}

		try {
			illegalDirectory = IN_Global.SYSTEM_CONFIG
					.getProperty(IN_Setting.ILLEGAL_DIR);
		} catch (Exception e) {
			logger.warn("Invalid '" + IN_Setting.ILLEGAL_DIR
					+ "' config in C_SYSTEM_CONFIGS_MLMN");
		}

		this.maxExecutor = (this.maxExecutor <= 0) ? 1 : this.maxExecutor;
		this.retryNumber = (this.retryNumber < 0) ? 0 : this.retryNumber;

		initTaskQueues();
	}

	/**
	 * @param input
	 */
	public void doTask(IN_TaskInfo input) {
		IN_FileInfo info = (IN_FileInfo) input;

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

			moveTo(file, direcPath, fileName);
			
			IN_DbAppender.log(info);

			increaseDoneTasks();
		} catch (Exception e) {
			logger.warn("Move file failure: FROM: " + info.getLocalDir()
					+ IN_Global.SEPARATOR + info.getOriginalName() + " --> TO: "
					+ direcPath + IN_Global.SEPARATOR + info.getFileName()
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
			this.patterns = IN_Setting.getListPatternInfo();
			dirListingRecursive(baseDirectory);
		} catch (IN_MaxQueueException e) {
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
			String preCommand = "insert /*+ APPEND NOLOGGING */ into C_ILLEGAL_FILES(DIRECTORY, FILE_NAME) values(?,?)";

			conn = this.ds.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pst = conn.prepareStatement(preCommand);

			for (IN_FileInfo info : illegalFiles) {
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

	private void dirListingRecursive(String path) throws IN_MaxQueueException {
		String filePath = null;
		String fileName = null;
		IN_FileInfo info = null;
		 
		File rootFile = new File(path);
		File[] files = rootFile.listFiles();
		if (files != null && files.length > 0) {
			for (File f : files) {
				fileName = f.getName();
				filePath = path + IN_Global.SEPARATOR + fileName;
				if (f.isDirectory()) {
					dirListingRecursive(filePath);
				} else {
					info = new IN_FileInfo(IN_FileInfo.LOCAL_TYPE);
					info.setFileName(fileName);
					info.setOriginalName(fileName);
					info.setLocalDir(path);
					info.setBaseDir(baseDirectory);

					parseFile( info);
				}
			}
		}
	}

	private void parseFile(IN_FileInfo info) throws IN_MaxQueueException {
		Pattern pattern = null;
		Matcher matcher = null;
		int indexGroup = -1;
		String time = "";

		for (IN_PatternInfo patternInfo : this.patterns) {
			pattern = Pattern.compile(patternInfo.getFilePattern().toUpperCase());
			matcher = pattern.matcher(info.getFileName().toUpperCase());

			if (matcher.matches()) {
				if (taskCount >= maxQueueSize) {
					throw new IN_MaxQueueException("Queue reach " + maxQueueSize
							+ " limit");
				}

				info.setPatternId(patternInfo.getPatternId());
				info.setRawTable(patternInfo.getRawTable());
				info.setSubDir(patternInfo.getSubDir());
				info.setModule(patternInfo.getModule());
				// Add by ThangPX
				info.setServerNode(patternInfo.getServerNode());
				if(patternInfo.getDescription() != null && !patternInfo.getDescription().equals("")){
					if(patternInfo.getDescription().toUpperCase().equals("FOLDER")){
						String[] temp = info.getLocalDir().split("/");
						int length = temp.length ;
						// set file name = node name + date + original name
						info.setFileName(temp[length-1] + "_" +info.getOriginalName());
					}
				} 
				
				/*--------- add by AnhNT -------------*/
				if(patternInfo.getFilePattern().toUpperCase().indexOf("ALCATELABISCHAINRING") != -1 || patternInfo.getFilePattern().toUpperCase().indexOf("ALCATELMANAGEDELEMENT") != -1
					|| patternInfo.getPatternId() == 26 || patternInfo.getPatternId() == 27 || patternInfo.getPatternId() == 28
					|| patternInfo.getPatternId() == 34 || patternInfo.getPatternId() == 35 || patternInfo.getPatternId() == 36
					|| patternInfo.getPatternId() == 37 || patternInfo.getPatternId() == 38 || patternInfo.getTimeFormat() == null
					|| patternInfo.getTimePatternGroup() == 0){
					// set date
					SimpleDateFormat format = new SimpleDateFormat(IN_Setting.DATE_FORMAT);
					Date tempDate = new Date();
					String date = format.format(tempDate);
					info.setDate(date);
					info.setHour(0);
				}else{
					// make node name
					indexGroup = patternInfo.getNodePatternGroup();
					if (indexGroup > -1) {
						info.setNodeName(matcher.group(indexGroup));
					}
					// make date and hour
					indexGroup = patternInfo.getTimePatternGroup();
					if (indexGroup > -1) {
						time = matcher.group(indexGroup);
					}
					if (!time.isEmpty()
							&& !patternInfo.getTimeFormat().equalsIgnoreCase("")) {
						String date = IN_Util.getTime(time,
								patternInfo.getTimeFormat(), IN_Setting.DATE_FORMAT);
						info.setDate(date);
						// set hour
						String hour = IN_Util.getTime(time,
								patternInfo.getTimeFormat(), "HH");
						try {
							info.setHour(Integer.parseInt(hour));
						} catch (NumberFormatException nfe) {
							info.setHour(0);
						}
					}
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

			int index = tempPath.indexOf(IN_Global.SEPARATOR);
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
	private void doRetry(IN_FileInfo entity) {
		int count = entity.getRetryCount();
		if (count < retryNumber) {
			count += 1;
			entity.setRetryCount(count);
			try {
				retryTask(entity);
			} catch (IN_IllegalServiceStateException i) {
				logger.warn("Retry selection failure: ", i);
			}
		} else {
			increaseFailureTasks();
			logger.warn("Selection " + entity.getLocalDir()
					+ IN_Global.SEPARATOR + entity.getFileName()
					+ " failure after " + retryNumber + " retries");
		}
	}
}
