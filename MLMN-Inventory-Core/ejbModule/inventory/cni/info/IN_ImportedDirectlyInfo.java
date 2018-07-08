package inventory.cni.info;


import inventory.core.IN_Global;
import inventory.util.IN_Setting;
import inventory.util.IN_Util;
import inventory.util.task.IN_TaskInfo;
import inventory.utils.log.IN_DBLogParam;

public class IN_ImportedDirectlyInfo extends IN_TaskInfo implements IN_DBLogParam {

    private int retryCount = 0;

    private int patternId = -1;
    private int fileId = -1;
    private String fileName = "";
    private String subDir = "";
    private String rawTable = "";
    private String separatorChar = "";
    private String commentChar = "";
    private String importRule = "";
    private String module = "";

    private String importTime = "";
    private int importDuration = 0;
    private int rowCountImported = 0;
    private int rowCountInFile = 0;

    private String messageCode = "";
    private String sqlMessage = "";
    private boolean isImportDone = false;

    private final String IBY_MAPPING_RULE = "M";
    private final String IBY_FILE_HEADER_RULE = "F";

    public IN_ImportedDirectlyInfo() {
    }

    public void importDone() {
        this.isImportDone = true;
    }

    private boolean isDone() {
        return (this.isImportDone == true);
    }

    public void importFailure() {
        this.isImportDone = false;
    }

    public int getPatternId() {
        return this.patternId;
    }

    public void setPatternId(int patternId) {
        this.patternId = patternId;
    }

    public int getFileId() {
        return this.fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        String filePath =
            IN_Global.SYSTEM_CONFIG.getProperty(IN_Setting.CONVERT_DIR);
        filePath += this.getSubDir();
        return filePath;
    }

    public String getRawTable() {
        return this.rawTable;
    }

    public void setRawTable(String rawTable) {
        this.rawTable = rawTable;
    }

    public String getSeparatorChar() {
        return this.separatorChar;
    }

    public void setSeparatorChar(String sepChar) {
        this.separatorChar = sepChar;
    }

    public String getCommentChar() {
        return this.commentChar;
    }

    public void setCommentChar(String commentChar) {
        this.commentChar = commentChar;
    }

    public String getImportTime() {
        return this.importTime;
    }

    public void setImportDuration(int duration) {
        this.importDuration = duration;
    }

    public int getImportDuration() {
        return this.importDuration;
    }

    public void setRowCountImported(int rowCount) {
        this.rowCountImported = rowCount;
    }

    public int getRowCountImported() {
        return this.rowCountImported;
    }

    public void setRowCountInFile(int rowCount) {
        this.rowCountInFile = rowCount;
    }

    public int getRowCountInFile() {
        return this.rowCountInFile;
    }

    public void setImportTime(String importTime) {
        this.importTime = importTime;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessageCode() {
        return this.messageCode;
    }

    public void setSQLMessage(String msg) {
        this.sqlMessage = msg;
    }

    public String getSQLMessage() {
        return this.sqlMessage;
    }

    public void setImportRule(String importRule) {
        this.importRule = importRule;
    }

    public String getImportRule() {
        return this.importRule;
    }

    public int getRetryCount() {
        return this.retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public void setSubDir(String subDir) {
        this.subDir = subDir;
    }

    public String getSubDir() {
        return subDir;
    }

    public boolean isIByMapping() {
        return this.importRule.equalsIgnoreCase(IBY_MAPPING_RULE);
    }

    public boolean isIByFileHeader() {
        return this.importRule.equalsIgnoreCase(IBY_FILE_HEADER_RULE);
    }

    public String[] getDMLs() {
    	/*String[] sqls = new String[1];
        if (this.isDone()) {
            sqls[0] = this.getLogSQL();
        } else {
            sqls[0] = this.getErrorSQL();
        }*/ 
        return new String[0];
    }

    public String getTaskInfo() {
        return fileName;
    }

    private String getLogSQL() {
        if (this.getImportTime().equalsIgnoreCase("")) {
            this.setImportTime(IN_Util.getCurrentTime());
        } 
        String result = rowCountImported + "/" + rowCountInFile;
        String sqlCommand = "insert into I_FILE_IMPORT_LOGS \n" +
            "(FILE_ID, IMPORT_TIME, IMPORT_DURATION, STATUS_CODE, " +
            "IMPORTED_RESULT) \n" +
            "values(" + this.getFileId() + ", to_date('" +
            this.getImportTime() + "', '" + IN_Setting.DB_TIME_FORMAT + "'), " +
            this.getImportDuration() + ", '" + this.getMessageCode() + "', '" +
            result + "')";

        return sqlCommand;
    }

    private String getErrorSQL() {
        String des =
            "FILE_ID: " + this.getFileId() + " - RAW_TABLE: " + this.getRawTable();

        String queryStr = "insert into C_PLSQL_ERROR_LOGS(PROC, ERR_CODE, \n" +
            "ERR_MSG, DESCRIPTION) values('IMPORT USING JAVA', '" +
            this.getMessageCode() + "', '" + this.getSQLMessage() + "', '" +
            des + "')";

        return queryStr;
    }

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
}