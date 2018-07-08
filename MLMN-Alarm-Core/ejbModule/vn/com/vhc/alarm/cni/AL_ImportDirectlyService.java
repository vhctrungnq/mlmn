package vn.com.vhc.alarm.cni;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import vn.com.vhc.alarm.cni.importer.AL_FileImporter;
import vn.com.vhc.alarm.cni.info.AL_ImportedDirectlyInfo;
import vn.com.vhc.alarm.cni.info.AL_MappingInfo;
import vn.com.vhc.alarm.core.AL_Global;
import vn.com.vhc.alarm.util.AL_Setting;
import vn.com.vhc.alarm.util.AL_Util;
import vn.com.vhc.alarm.util.exceptions.AL_ImportException;
import vn.com.vhc.alarm.util.task.AL_IllegalServiceStateException;
import vn.com.vhc.alarm.util.task.AL_TaskInfo;
import vn.com.vhc.alarm.util.task.AL_TaskService;
import vn.com.vhc.alarm.utils.log.AL_DbAppender;



public class AL_ImportDirectlyService extends AL_TaskService {

    private static Logger logger =
        Logger.getLogger(AL_ImportDirectlyService.class.getName());

    private final String SEPARATOR = "#";

    private int maxExecutor = -1;
    private int retryNumber = -1;

    private Hashtable<String, Vector<AL_MappingInfo>> hashMappingData = null;
    private DataSource ds = null;

    public AL_ImportDirectlyService(DataSource ds) {
        this("Import Service", ds);
    }

    public AL_ImportDirectlyService(String name, DataSource ds) {
        super(name);
        this.ds = ds;
    }
    public void initTaskQueues() {
        Connection conn = null;
        String node = AL_Setting.getNode(AL_Setting.SERVER_KEY);
        Statement st = null;
        try {
            conn = this.ds.getConnection();
            st = conn.createStatement();

            String queryStr =
                "select F.PATTERN_ID, F.FILE_ID, F.FILE_NAME, F.RAW_TABLE, \n" +
                "P.SUB_DIR, P.SEPARATOR, P.COMMENT_CHAR, P.IMPORT_RULE \n" +
                "from C_RAW_FILES_MLMN F \n" +
                "inner join C_FILE_PATTERNS P \n" +
                "on F.PATTERN_ID = P.PATTERN_ID \n" +
                "and NVL(F.SERVER_NODE,'ALL') = '"+node+"' " +
                "where MOD(F.CONVERT_FLAG,2) = 1 " +
                "and MOD(F.IMPORT_FLAG,2) = 0 and F.MODULE = 'ALARM' order by HOUR";

            ResultSet rs = st.executeQuery(queryStr);
            AL_ImportedDirectlyInfo importedInfo = null;
            while (rs.next()) {
                importedInfo = new AL_ImportedDirectlyInfo();

                importedInfo.setPatternId(rs.getInt("PATTERN_ID"));
                importedInfo.setFileId(rs.getInt("FILE_ID"));
                importedInfo.setFileName(rs.getString("FILE_NAME"));
                importedInfo.setRawTable(rs.getString("RAW_TABLE"));
                importedInfo.setSeparatorChar(rs.getString("SEPARATOR"));
                importedInfo.setCommentChar(rs.getString("COMMENT_CHAR"));
                importedInfo.setImportRule(rs.getString("IMPORT_RULE"));
                importedInfo.setSubDir(rs.getString("SUB_DIR"));

                if (this.validateInfo(importedInfo)) {
                    addTask(importedInfo);
                }
            }

            rs.close();
            
        } catch (SQLException e) {
            logger.warn("File list to import is empty", e);
        } finally {
        	try {
				st.close();
			} catch (SQLException e) {
				logger.warn("Cannot close the connection to database",
						e);
			}
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException sqlEx) {
                    logger.warn("Cannot close the connection to database",
                               sqlEx);
                }
            }
        }
    }

    public void doInit() {
        this.loadMappingData();

        try {
            String maxThread =
                AL_Global.SYSTEM_CONFIG.getProperty(AL_Setting.MAX_IMPORT_THREAD_KEY);
            this.maxExecutor = Integer.parseInt(maxThread);
        } catch (NumberFormatException nfe) {
            logger.warn("Invalid '" + AL_Setting.MAX_IMPORT_THREAD_KEY +
                           "' config in C_SYSTEM_CONFIGS_MLMN");
        }

        try {
            String maxRetry =
                AL_Global.SYSTEM_CONFIG.getProperty(AL_Setting.MAX_IMPORT_RETRY_KEY);
            this.retryNumber = Integer.parseInt(maxRetry);
        } catch (NumberFormatException nfe) {
            logger.warn("Invalid '" + AL_Setting.MAX_IMPORT_RETRY_KEY +
                           "' config in C_SYSTEM_CONFIGS_MLMN");
        }

        this.maxExecutor = (this.maxExecutor <= 0) ? 1 : this.maxExecutor;
        this.retryNumber = (this.retryNumber < 0) ? 0 : this.retryNumber;

        initTaskQueues();
    }

    public void doTask(AL_TaskInfo input) {
        AL_ImportedDirectlyInfo beanInfo = (AL_ImportedDirectlyInfo)input;

        try {
            Date currentDate = new Date();
            beanInfo.setImportTime(AL_Util.getTime(currentDate));
            long minis = currentDate.getTime();

            String key = this.getMapKey(beanInfo);
            AL_FileImporter importer = new AL_FileImporter(this.ds);
            importer.doImport(beanInfo, this.hashMappingData.get(key));

            beanInfo.setImportDuration(AL_Util.getTimeDuration(minis));
            beanInfo.importDone();
            this.updateImportStatus(beanInfo.getFileId(), false);
            increaseDoneTasks();
            
            AL_DbAppender.log(beanInfo);
        } catch (AL_ImportException iex) {
            logger.warn("Import file '" + beanInfo.getFileName() +
                           " error:" + iex.getMessage());
            if (iex.getErrorCode() != null &&
                iex.getErrorCode().trim().length() > 0) {
                beanInfo.setMessageCode(iex.getErrorCode());
            } else {
                beanInfo.setMessageCode("VMSC2-0360");
            }
            this.doRetry(beanInfo);

            if (this.retryNumber <= beanInfo.getRetryCount()) {
                beanInfo.importDone();
                increaseFailureTasks();
                AL_DbAppender.log(beanInfo);
            }
        }
    }

    public void doFinallize() {
        this.hashMappingData = null;
        this.maxExecutor = -1;
        this.retryNumber = -1;
    }

    public int getMaxExecutorThread() {
        return this.maxExecutor;
    }

    private void doRetry(AL_ImportedDirectlyInfo beanInfo) {
        int retryCount = beanInfo.getRetryCount();
        retryCount += 1;

        if ((this.retryNumber <= 0) || (this.retryNumber < retryCount)) {
            this.updateImportStatus(beanInfo.getFileId(), true);
            return;
        }

        beanInfo.setRetryCount(retryCount);
        try {
            retryTask(beanInfo);
        } catch (AL_IllegalServiceStateException e) {
            logger.warn("Retry failure: " + e.getMessage());
        }
    }

    private void updateImportStatus(int fileId, boolean error) {
        Connection conn = null;
        String exeCommand = "";
        try {
            conn = this.ds.getConnection();
            Statement st = conn.createStatement();
            if (error) {
                exeCommand = "update /*+ NOLOGGING */ C_RAW_FILES_MLMN set IMPORT_FLAG = -1 \n" +
                        "where FILE_ID = " + fileId;
            } else {
                exeCommand = "update /*+ NOLOGGING */ C_RAW_FILES_MLMN set IMPORT_FLAG = \n" +
                        "IMPORT_FLAG + 1 where FILE_ID = " + fileId;
            }
            st.execute(exeCommand);

            st.close();
        } catch (SQLException e) {
            logger.warn("Update import status failure", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException sqlEx) {
                    logger.warn("Cannot close the connection to database",
                               sqlEx);
                }
            }
        }
    }

    private boolean validateInfo(AL_ImportedDirectlyInfo info) {
        if (info == null) {
            return false;
        }

        int patternId = info.getPatternId();
        if (patternId == 0) {
            logger.warn("PATTERN_ID invalid: " + info.getPatternId() +
                           " - FILE_ID: " + info.getFileId());
            return false;
        }

        String fileName = info.getFileName();
        if (fileName == null || fileName.trim().equalsIgnoreCase("")) {
            logger.warn("File name is empty - FILE_ID: " +
                           info.getFileId());
            return false;
        }

        String filePath = info.getFilePath();
        if (filePath == null || filePath.trim().equalsIgnoreCase("")) {
            logger.warn("File path is empty - FILE_ID: " +
                           info.getFileId());
            return false;
        }

        String rawTable = info.getRawTable();
        if (rawTable == null || rawTable.trim().equalsIgnoreCase("")) {
            logger.warn("Raw table is empty - FILE_ID: " +
                           info.getFileId());
            return false;
        }

        String separator = info.getSeparatorChar();
        if (separator == null || separator.trim().equalsIgnoreCase("")) {
            info.setSeparatorChar("\t");
        }

        String commentChar = info.getCommentChar();
        if (commentChar == null) {
            info.setCommentChar("");
        }

        return true;
    }

    /**
     *  test method
     * */
    public void run(AL_ImportedDirectlyInfo beanInfo) {
        this.loadMappingData();

        AL_FileImporter importer = new AL_FileImporter(this.ds);
        String key =
            this.getMapKey(beanInfo.getRawTable(), String.valueOf(beanInfo.getPatternId()));
        try {
            importer.doImport(beanInfo, this.hashMappingData.get(key));
        } catch (AL_ImportException e) {
            e.printStackTrace();
        }
    }

    private String getMapKey(String rawTable, String patternId) {
        return rawTable + SEPARATOR + patternId;
    }

    private String getMapKey(AL_ImportedDirectlyInfo info) {
        return this.getMapKey(info.getRawTable(),
                              String.valueOf(info.getPatternId()));
    }

    private void loadMappingData() {
        this.hashMappingData = new Hashtable<String, Vector<AL_MappingInfo>>();

        Connection conn = null;
        try {
            conn = this.ds.getConnection();
            Statement st = conn.createStatement();

            String queryStr = "select RAW_TABLE, TABLE_COLUMN, \n" +
                "FILE_COLUMN, DATA_TYPE, DATA_FORMAT, PATTERN_ID, \n" +
                "FILE_COLUMN_HEADER from I_IMPORT_MAPPING where STATUS = 'Y' and upper(MODULE) = 'ALARM' " +
                "order by ORDERING";
            ResultSet rs = st.executeQuery(queryStr);

            AL_MappingInfo mappingInfo = null;
            String key = "";
            while (rs.next()) {
                String patternId = rs.getString("PATTERN_ID");
                if (patternId == null || patternId.trim().length() == 0) {
                    continue;
                }
                String rawTable = rs.getString("RAW_TABLE");
                if (rawTable == null || rawTable.trim().length() == 0) {
                    continue;
                }
                key = this.getMapKey(rawTable, patternId);

                mappingInfo = new AL_MappingInfo();
                mappingInfo.setTableColumn(rs.getString("TABLE_COLUMN"));
                mappingInfo.setFileColumn(rs.getString("FILE_COLUMN"));
                mappingInfo.setDataType(rs.getString("DATA_TYPE"));
                mappingInfo.setDataFormat(rs.getString("DATA_FORMAT"));
                mappingInfo.setFileColumnHeader(rs.getString("FILE_COLUMN_HEADER"));

                if (hashMappingData.containsKey(key)) {
                    Vector<AL_MappingInfo> listMap = hashMappingData.get(key);
                    listMap.add(mappingInfo);
                } else {
                    Vector<AL_MappingInfo> listMap = new Vector<AL_MappingInfo>();
                    listMap.add(mappingInfo);
                    hashMappingData.put(key, listMap);
                }
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            logger.warn("Cannot load the data mapping", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException sqlEx) {
                    logger.warn("Cannot close the connection to database",
                               sqlEx);
                }
            }
        }
    }
}
