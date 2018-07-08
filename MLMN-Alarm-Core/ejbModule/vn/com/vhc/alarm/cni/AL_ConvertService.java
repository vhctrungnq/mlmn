package vn.com.vhc.alarm.cni;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Hashtable;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import vn.com.vhc.alarm.cni.info.AL_FileInfo;
import vn.com.vhc.alarm.core.AL_Global;
import vn.com.vhc.alarm.util.AL_Setting;
import vn.com.vhc.alarm.util.AL_Util;
import vn.com.vhc.alarm.util.exceptions.AL_ConvertException;
import vn.com.vhc.alarm.util.task.AL_IllegalServiceStateException;
import vn.com.vhc.alarm.util.task.AL_TaskInfo;
import vn.com.vhc.alarm.util.task.AL_TaskService;
import vn.com.vhc.alarm.utils.log.AL_DbAppender;



public class AL_ConvertService extends AL_TaskService {

    private static Logger logger =
        Logger.getLogger(AL_ConvertService.class.getName());

    private int maxExecutor = -1;
    private int retryNumber = -1;

    private DataSource ds = null;

    public AL_ConvertService(DataSource ds) {
        this("Convert Service", ds);
    }

    public AL_ConvertService(String name, DataSource ds) {
        super(name);
        this.ds = ds;
    }

    public void initTaskQueues() {
        Connection conn = null;
        String node = AL_Setting.getNode(AL_Setting.SERVER_KEY);
        try {
            conn = this.ds.getConnection();
            Statement st = conn.createStatement();

            String queryStr =
                "select F.FILE_ID, F.FILE_NAME, F.NODE_NAME, F.RAW_TABLE, P.PATTERN_ID, " +
                "P.FILE_PATTERN, P.SUB_DIR, P.CONVERT_CLASS, " +
                "P.SEPARATOR, P.COMMENT_CHAR, P.LAST_HOUR from C_RAW_FILES_MLMN F " +
                "inner join C_FILE_PATTERNS P " +
                "on F.PATTERN_ID = P.PATTERN_ID " +
                "and NVL(F.SERVER_NODE,'ALL') = '"+node+"' " +
                "where MOD(F.CONVERT_FLAG,2) = 0 and upper(F.MODULE) = 'ALARM' order by F.HOUR";

            ResultSet rs = st.executeQuery(queryStr);
            while (rs.next()) {
                AL_FileInfo fileInfo = new AL_FileInfo();

                fileInfo.setFileId(rs.getInt("FILE_ID"));
                fileInfo.setFileName(rs.getString("FILE_NAME"));
                fileInfo.setPatternId(rs.getInt("PATTERN_ID"));
                fileInfo.setConvertClass(rs.getString("CONVERT_CLASS"));
                fileInfo.setSubDir(rs.getString("SUB_DIR"));
                
                //create params
                Hashtable<Byte, String> hashParam =
                    new Hashtable<Byte, String>();

                String sep = rs.getString("SEPARATOR");
                if (sep != null) {
                    hashParam.put(AL_Setting.SEPARATOR_KEY, sep);
                }
                String commentChar = rs.getString("COMMENT_CHAR");
                if (commentChar != null) {
                    hashParam.put(AL_Setting.COMMENT_CHAR_KEY, commentChar);
                }
                String nodeName = rs.getString("NODE_NAME");
                if (nodeName != null) {
                    hashParam.put(AL_Setting.NODE_NAME_KEY, nodeName);
                }
                String filePattern = rs.getString("FILE_PATTERN");
                if (filePattern != null) {
                    hashParam.put(AL_Setting.FILE_PATTERN_KEY, filePattern);
                }
                String rawTable = rs.getString("RAW_TABLE");
                hashParam.put(AL_Setting.RAW_TABLE_KEY, rawTable);

                hashParam.put(AL_Setting.FILE_ID_KEY,
                              String.valueOf(fileInfo.getFileId()));

                String lastHour = rs.getString("LAST_HOUR");
                if (lastHour != null) {
                    hashParam.put(AL_Setting.LAST_HOUR, lastHour);
                }
                
                fileInfo.setParams(hashParam);

                if (this.validateInfo(fileInfo)) {
                    addTask(fileInfo);
                }
            }
            //close connection
            rs.close();
            st.close();
        } catch (SQLException e) {
            logger.warn("File list to convert is empty" + e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    logger.warn("Cannot close connection to database");
                }
            }
        }
    }

    public void doInit() {
        try {
            String maxThread =
                AL_Global.SYSTEM_CONFIG.getProperty(AL_Setting.MAX_CONVERT_THREAD_KEY);
            this.maxExecutor = Integer.parseInt(maxThread);
        } catch (NumberFormatException nfe) {
            logger.warn("Invalid '" + AL_Setting.MAX_CONVERT_THREAD_KEY +
                           "' config in C_SYSTEM_CONFIGS_MLMN");
        }

        try {
            String maxRetry =
                AL_Global.SYSTEM_CONFIG.getProperty(AL_Setting.MAX_CONVERT_RETRY_KEY);
            this.retryNumber = Integer.parseInt(maxRetry);
        } catch (NumberFormatException nfe) {
            logger.warn("Invalid '" + AL_Setting.MAX_CONVERT_RETRY_KEY +
                           "' config in C_SYSTEM_CONFIGS_MLMN");
        }

        this.maxExecutor = (this.maxExecutor <= 0) ? 1 : this.maxExecutor;
        this.retryNumber = (this.retryNumber < 0) ? 0 : this.retryNumber;

        initTaskQueues();
    }

    public void doTask(AL_TaskInfo input) {
        AL_FileInfo beanInfo = (AL_FileInfo)input;

        // validate file path
        String path =
            beanInfo.getOriginPath() + System.getProperty("file.separator") +
            beanInfo.getFileName();
        File file = new File(path);
        if (!file.exists()) {
            increaseFailureTasks();
            beanInfo.setMessageCode("VMSC2-0302");
            logger.warn("File not found: " + path);
            AL_DbAppender.log(beanInfo);
            return;
        }

        //init time execute convert
        Date currentDate = new Date();
        beanInfo.setConvertTime(AL_Util.getTime(currentDate));
        long minis = currentDate.getTime();

        //get handle convert file object
        AL_IConverter fconvert =
            (AL_IConverter)this.getInstanceOf(beanInfo.getConvertClass());
        if (fconvert == null) {
            increaseFailureTasks();
            beanInfo.setMessageCode("VMSC2-0301");
            logger.warn("Convert class doesn't exist: " + beanInfo.getPatternId());
            AL_DbAppender.log(beanInfo);
            return;
        }

        try {
            beanInfo.setOriginalSize(file.length());
            fconvert.convertFile(file, beanInfo.getDirectionPath(),
                                 beanInfo.getParams());

            beanInfo.setDurationTime(AL_Util.getTimeDuration(minis));
            File convertedFile =
                new File(beanInfo.getDirectionPath(), beanInfo.getFileName());
            beanInfo.setConvertedSize(convertedFile.length());
            beanInfo.setMessageCode("VMSC2-0300");

            this.updateConvertStatus(beanInfo.getFileId(), false);
            increaseDoneTasks();

            AL_DbAppender.log(beanInfo);
        } catch (AL_ConvertException ce) {
            //Xu ly cac truong hop convert loi o day
            if (ce.getErrorCode().trim().equalsIgnoreCase("")) {
                beanInfo.setMessageCode("VMSC2-0303");
            } else {
                beanInfo.setMessageCode(ce.getErrorCode());
            }
            this.doRetry(beanInfo);
            logger.warn("Convert file failure FILE_ID: " +
                           beanInfo.getFileId() + " - PATH: " + path);
        }
    }

    public void doFinallize() {
        this.maxExecutor = -1;
        this.retryNumber = -1;
    }

    public int getMaxExecutorThread() {
        return this.maxExecutor;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	private Object getInstanceOf(String className) {
        Object obj = null;
        try {
            Class cl = Class.forName(className);
            java.lang.reflect.Constructor contructor = cl.getConstructor();
            obj = contructor.newInstance();
        } catch (Exception e) {
            logger.warn("Create convert object for class name failure: " +
                       className + e);
        }
        return obj;
    }

    private void doRetry(AL_FileInfo beanInfo) {
        int retryCount = beanInfo.getRetryCount();
        retryCount += 1;

        if ((this.retryNumber <= 0) || (this.retryNumber < retryCount)) {
            this.backupFile(beanInfo.getBackupPath(), beanInfo.getOriginPath(),
                            beanInfo.getFileName());
            increaseFailureTasks();
            this.updateConvertStatus(beanInfo.getFileId(), true);
            AL_DbAppender.log(beanInfo);
            return;
        }

        beanInfo.setRetryCount(retryCount);
        try {
            retryTask(beanInfo);
        } catch (AL_IllegalServiceStateException e) {
            logger.warn("Retry failure: " + e.getMessage());
        }
    }

    private void backupFile(String backupPath, String originalPath,
                            String fileName) {
        if (backupPath == null || backupPath.length() == 0) {
            return;
        }
        try {
            // Destination directory
            File dirFile = new File(backupPath);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            // Move file to new directory
            String fPath =
                originalPath + System.getProperty("file.separator") + fileName;
            File file = new File(fPath);
            file.renameTo(new File(dirFile, fileName));
        } catch (Exception e) {
            logger.warn("Backup failure: " + fileName + e.getMessage());
        }
    }

    private void updateConvertStatus(int fileId, boolean error) {
        Connection conn = null;
        String exeCommand = "";
        try {
            conn = this.ds.getConnection();
            Statement st = conn.createStatement();
            if (error) {
                exeCommand = "update /*+ NOLOGGING */ C_RAW_FILES_MLMN set CONVERT_FLAG = -1 \n" +
                        "where FILE_ID = " + fileId;
            } else {
                exeCommand = "update /*+ NOLOGGING */ C_RAW_FILES_MLMN set CONVERT_FLAG = \n" +
                        "CONVERT_FLAG + 1 where FILE_ID = " + fileId;
            }
            st.execute(exeCommand);

            st.close();
        } catch (SQLException e) {
            logger.warn("Update convert status failure: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    logger.warn("Cannot close connection to database");
                }
            }
        }
    }

    private boolean validateInfo(AL_FileInfo fileInfo) {
        if (fileInfo == null) {
            return false;
        }

        String fileName = fileInfo.getFileName();
        if (fileName == null || fileName.trim().equalsIgnoreCase("")) {
            logger.warn("File name is empty - FILE_ID: " +
                           fileInfo.getFileId());
            return false;
        }

        String originalPath = fileInfo.getOriginPath();
        if (originalPath == null || originalPath.trim().equalsIgnoreCase("")) {
            logger.warn("Original path contain the file is empty - FILE_ID: " +
                           fileInfo.getFileId());
            return false;
        }

        String directionPath = fileInfo.getDirectionPath();
        if (directionPath == null ||
            directionPath.trim().equalsIgnoreCase("")) {
            logger.warn("The directory contain convert file is empty - FILE_ID: " +
                           fileInfo.getFileId());
            return false;
        }

        String convertClass = fileInfo.getConvertClass();
        if (convertClass == null || convertClass.trim().equalsIgnoreCase("")) {
            logger.warn("Convert class is empty - FILE_ID: " +
                           fileInfo.getFileId());
            return false;
        }

        return true;
    }
}
