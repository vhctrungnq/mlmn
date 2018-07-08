package inventory.cni;

import inventory.cni.importer.IN_FileImporter;
import inventory.cni.info.IN_ImportedDirectlyInfo;
import inventory.cni.info.IN_MappingInfo;
import inventory.core.IN_Global;
import inventory.util.IN_Setting;
import inventory.util.IN_Util;
import inventory.util.exceptions.IN_ImportException;
import inventory.util.task.IN_IllegalServiceStateException;
import inventory.util.task.IN_TaskInfo;
import inventory.util.task.IN_TaskService;
import inventory.utils.log.IN_DbAppender;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import javax.sql.DataSource;

import org.apache.log4j.Logger;






public class IN_ImportDirectlyService extends IN_TaskService {

    private static Logger logger =
        Logger.getLogger(IN_ImportDirectlyService.class.getName());

    private final String SEPARATOR = "#";

    private int maxExecutor = -1;
    private int retryNumber = -1;

    private Hashtable<String, Vector<IN_MappingInfo>> hashMappingData = null;
    private DataSource ds = null;

    public IN_ImportDirectlyService(DataSource ds) {
        this("Import Service", ds);
    }

    public IN_ImportDirectlyService(String name, DataSource ds) {
        super(name);
        this.ds = ds;
    }

    public void initTaskQueues() {
        Connection conn = null;
        Statement st = null;
        String node = IN_Setting.getNode(IN_Setting.SERVER_KEY);
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
                "and MOD(F.IMPORT_FLAG,2) = 0 AND F.MODULE = 'INVENTORY' order by HOUR";
            
            ResultSet rs = st.executeQuery(queryStr);
            IN_ImportedDirectlyInfo importedInfo = null;
            while (rs.next()) {
                importedInfo = new IN_ImportedDirectlyInfo();

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
				e.printStackTrace();
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
                IN_Global.SYSTEM_CONFIG.getProperty(IN_Setting.MAX_IMPORT_THREAD_KEY);
            this.maxExecutor = Integer.parseInt(maxThread);
        } catch (NumberFormatException nfe) {
            logger.warn("Invalid '" + IN_Setting.MAX_IMPORT_THREAD_KEY +
                           "' config in C_SYSTEM_CONFIGS_MLMN");
        }

        try {
            String maxRetry =
                IN_Global.SYSTEM_CONFIG.getProperty(IN_Setting.MAX_IMPORT_RETRY_KEY);
            this.retryNumber = Integer.parseInt(maxRetry);
        } catch (NumberFormatException nfe) {
            logger.warn("Invalid '" + IN_Setting.MAX_IMPORT_RETRY_KEY +
                           "' config in C_SYSTEM_CONFIGS_MLMN");
        }

        this.maxExecutor = (this.maxExecutor <= 0) ? 1 : this.maxExecutor;
        this.retryNumber = (this.retryNumber < 0) ? 0 : this.retryNumber;

        initTaskQueues();
    }

    public void doTask(IN_TaskInfo input) {
        IN_ImportedDirectlyInfo beanInfo = (IN_ImportedDirectlyInfo)input;

        try {
            Date currentDate = new Date();
            beanInfo.setImportTime(IN_Util.getTime(currentDate));
            long minis = currentDate.getTime();

            String key = this.getMapKey(beanInfo);
            IN_FileImporter importer = new IN_FileImporter(this.ds);
            importer.doImport(beanInfo, this.hashMappingData.get(key));

            beanInfo.setImportDuration(IN_Util.getTimeDuration(minis));
            beanInfo.importDone();
            this.updateImportStatus(beanInfo.getFileId(), false);
            increaseDoneTasks();
            
            IN_DbAppender.log(beanInfo);
        } catch (IN_ImportException iex) {
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
                IN_DbAppender.log(beanInfo);
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

    private void doRetry(IN_ImportedDirectlyInfo beanInfo) {
        int retryCount = beanInfo.getRetryCount();
        retryCount += 1;

        if ((this.retryNumber <= 0) || (this.retryNumber < retryCount)) {
            this.updateImportStatus(beanInfo.getFileId(), true);
            return;
        }

        beanInfo.setRetryCount(retryCount);
        try {
            retryTask(beanInfo);
        } catch (IN_IllegalServiceStateException e) {
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

    private boolean validateInfo(IN_ImportedDirectlyInfo info) {
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
    public void run(IN_ImportedDirectlyInfo beanInfo) {
        this.loadMappingData();

        IN_FileImporter importer = new IN_FileImporter(this.ds);
        String key =
            this.getMapKey(beanInfo.getRawTable(), String.valueOf(beanInfo.getPatternId()));
        try {
            importer.doImport(beanInfo, this.hashMappingData.get(key));
        } catch (IN_ImportException e) {
            e.printStackTrace();
        }
    }

    private String getMapKey(String rawTable, String patternId) {
        return rawTable + SEPARATOR + patternId;
    }

    private String getMapKey(IN_ImportedDirectlyInfo info) {
        return this.getMapKey(info.getRawTable(),
                              String.valueOf(info.getPatternId()));
    }

    private void loadMappingData() {
        this.hashMappingData = new Hashtable<String, Vector<IN_MappingInfo>>();

        Connection conn = null;
        try {
            conn = this.ds.getConnection();
            Statement st = conn.createStatement();

            String queryStr = "select RAW_TABLE, TABLE_COLUMN, \n" +
                "FILE_COLUMN, DATA_TYPE, DATA_FORMAT, PATTERN_ID, \n" +
                "FILE_COLUMN_HEADER from I_IMPORT_MAPPING where UPPER(STATUS) = 'Y'" +
                "and  UPPER(MODULE) = 'INVENTORY' order by ORDERING";
            ResultSet rs = st.executeQuery(queryStr);

            IN_MappingInfo mappingInfo = null;
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

                mappingInfo = new IN_MappingInfo();
                mappingInfo.setTableColumn(rs.getString("TABLE_COLUMN"));
                mappingInfo.setFileColumn(rs.getString("FILE_COLUMN"));
                mappingInfo.setDataType(rs.getString("DATA_TYPE"));
                mappingInfo.setDataFormat(rs.getString("DATA_FORMAT"));
                mappingInfo.setFileColumnHeader(rs.getString("FILE_COLUMN_HEADER"));

                if (hashMappingData.containsKey(key)) {
                    Vector<IN_MappingInfo> listMap = hashMappingData.get(key);
                    listMap.add(mappingInfo);
                } else {
                    Vector<IN_MappingInfo> listMap = new Vector<IN_MappingInfo>();
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
