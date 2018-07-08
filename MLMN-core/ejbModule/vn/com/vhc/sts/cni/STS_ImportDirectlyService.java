package vn.com.vhc.sts.cni;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.cni.importer.STS_FileImporter;
import vn.com.vhc.sts.cni.info.STS_ImportedDirectlyInfo;
import vn.com.vhc.sts.cni.info.STS_MappingInfo;
import vn.com.vhc.sts.core.STS_Global;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.STS_Util;
import vn.com.vhc.sts.util.exceptions.STS_ImportException;
import vn.com.vhc.sts.util.task.STS_IllegalServiceStateException;
import vn.com.vhc.sts.util.task.STS_TaskInfo;
import vn.com.vhc.sts.util.task.STS_TaskService;
import vn.com.vhc.sts.utils.log.STS_DbAppender;



public class STS_ImportDirectlyService extends STS_TaskService {

    private static Logger logger =
        Logger.getLogger(STS_ImportDirectlyService.class.getName());

    private final String SEPARATOR = "#";

    private int maxExecutor = -1;
    private int retryNumber = -1;

    private Hashtable<String, Vector<STS_MappingInfo>> hashMappingData = null;
    private DataSource ds = null;

    public STS_ImportDirectlyService(DataSource ds) {
        this("Import Service", ds);
    }

    public STS_ImportDirectlyService(String name, DataSource ds) {
        super(name);
        this.ds = ds;
    }

    public void initTaskQueues() {
        Connection conn = null;
        String node = STS_Setting.getNode(STS_Setting.SERVER_KEY);
        Statement st = null;
        try {
            conn = this.ds.getConnection();
            st = conn.createStatement();

            String queryStr =
                "select F.PATTERN_ID, F.FILE_ID, F.FILE_NAME, F.RAW_TABLE, \n" +
                "F.SUB_DIR, P.SEPARATOR, P.COMMENT_CHAR, P.IMPORT_RULE \n" +
                "from C_RAW_FILES_MLMN F \n" +
                "inner join C_FILE_PATTERNS_MLMN P \n" +
                "on F.PATTERN_ID = P.PATTERN_ID \n" +
                "and NVL(F.SERVER_NODE,'ALL') = '"+node+"' " +
                "where MOD(F.CONVERT_FLAG,2) = 1 " +
                "and MOD(F.IMPORT_FLAG,2) = 0  and F.RAW_TABLE <> 'MULTI' order by DAY";

            ResultSet rs = st.executeQuery(queryStr);
            STS_ImportedDirectlyInfo importedInfo = null;
            while (rs.next()) {
                importedInfo = new STS_ImportedDirectlyInfo();

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
    				logger.warn("Cannot close the create statement to database",
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
                STS_Global.SYSTEM_CONFIG.getProperty(STS_Setting.MAX_IMPORT_THREAD_KEY);
            this.maxExecutor = Integer.parseInt(maxThread);
        } catch (NumberFormatException nfe) {
            logger.warn("Invalid '" + STS_Setting.MAX_IMPORT_THREAD_KEY);
        }

        try {
            String maxRetry =
                STS_Global.SYSTEM_CONFIG.getProperty(STS_Setting.MAX_IMPORT_RETRY_KEY);
            this.retryNumber = Integer.parseInt(maxRetry);
        } catch (NumberFormatException nfe) {
            logger.warn("Invalid '" + STS_Setting.MAX_IMPORT_RETRY_KEY);
        }

        this.maxExecutor = (this.maxExecutor <= 0) ? 1 : this.maxExecutor;
        this.retryNumber = (this.retryNumber < 0) ? 0 : this.retryNumber;

        initTaskQueues();
    }

    public void doTask(STS_TaskInfo input, Connection conn) {
        STS_ImportedDirectlyInfo beanInfo = (STS_ImportedDirectlyInfo)input;

        try {
            Date currentDate = new Date();
            beanInfo.setImportTime(STS_Util.getTime(currentDate));
            long minis = currentDate.getTime();

            String key = this.getMapKey(beanInfo);
            STS_FileImporter importer = new STS_FileImporter(this.ds);
            importer.doImport(beanInfo, this.hashMappingData.get(key));

            beanInfo.setImportDuration(STS_Util.getTimeDuration(minis));
            beanInfo.importDone();
            this.updateImportStatus(beanInfo.getFileId(), false);
            increaseDoneTasks();
            
            STS_DbAppender.log(beanInfo);
        } catch (STS_ImportException iex) {
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
                STS_DbAppender.log(beanInfo);
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

    private void doRetry(STS_ImportedDirectlyInfo beanInfo) {
        int retryCount = beanInfo.getRetryCount();
        retryCount += 1;

        if ((this.retryNumber <= 0) || (this.retryNumber < retryCount)) {
            this.updateImportStatus(beanInfo.getFileId(), true);
            return;
        }

        beanInfo.setRetryCount(retryCount);
        try {
            retryTask(beanInfo);
        } catch (STS_IllegalServiceStateException e) {
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

    private boolean validateInfo(STS_ImportedDirectlyInfo info) {
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
    public void run(STS_ImportedDirectlyInfo beanInfo) {
        this.loadMappingData();

        STS_FileImporter importer = new STS_FileImporter(this.ds);
        String key =
            this.getMapKey(beanInfo.getRawTable(), String.valueOf(beanInfo.getPatternId()));
        try {
            importer.doImport(beanInfo, this.hashMappingData.get(key));
        } catch (STS_ImportException e) {
            e.printStackTrace();
        }
    }

    private String getMapKey(String rawTable, String patternId) {
        return rawTable + SEPARATOR + patternId;
    }

    private String getMapKey(STS_ImportedDirectlyInfo info) {
        return this.getMapKey(info.getRawTable(),
                              String.valueOf(info.getPatternId()));
    }

    private void loadMappingData() {
        this.hashMappingData = new Hashtable<String, Vector<STS_MappingInfo>>();

        Connection conn = null;
        try {
            conn = this.ds.getConnection();
            Statement st = conn.createStatement();

            String queryStr = "select RAW_TABLE, TABLE_COLUMN, \n" +
                "FILE_COLUMN, DATA_TYPE, DATA_FORMAT, PATTERN_ID, \n" +
                "FILE_COLUMN_HEADER from I_IMPORT_MAPPING_MLMN where STATUS = 'Y'";
            ResultSet rs = st.executeQuery(queryStr);

            STS_MappingInfo mappingInfo = null;
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

                mappingInfo = new STS_MappingInfo();
                mappingInfo.setTableColumn(rs.getString("TABLE_COLUMN"));
                mappingInfo.setFileColumn(rs.getString("FILE_COLUMN"));
                mappingInfo.setDataType(rs.getString("DATA_TYPE"));
                mappingInfo.setDataFormat(rs.getString("DATA_FORMAT"));
                mappingInfo.setFileColumnHeader(rs.getString("FILE_COLUMN_HEADER"));

                if (hashMappingData.containsKey(key)) {
                    Vector<STS_MappingInfo> listMap = hashMappingData.get(key);
                    listMap.add(mappingInfo);
                } else {
                    Vector<STS_MappingInfo> listMap = new Vector<STS_MappingInfo>();
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
