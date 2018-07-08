package vn.com.vhc.alarm.cni.info;

import vn.com.vhc.alarm.util.task.AL_TaskInfo;

public class AL_ImportedInfo extends AL_TaskInfo {

    private int patternId = -1;
    private int fileId = -1;
    private String fileName = "";
    private String dir = "";
    private String tblName = "";
    private String separatorChar = "";
    private String commentChar = "";
    private String importRule = "";
    private int retryCount = 0;

    private final String IBY_MAPPING_RULE = "M";
    private final String IBY_FILE_HEADER_RULE = "F";

    public AL_ImportedInfo() {
    }

    public AL_ImportedInfo(int patternId, int fileId, String fileName, String dir,
                        String tblName, String separatorChar,
                        String commentChar, String importRule) {
        this.patternId = patternId;
        this.fileId = fileId;
        this.fileName = fileName;
        this.dir = dir;
        this.tblName = tblName;
        this.separatorChar = separatorChar;
        this.commentChar = commentChar;
        this.importRule = importRule;
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

    public String getDirectoryName() {
        return this.dir;
    }

    public void setDirectoryName(String dir) {
        this.dir = dir;
    }

    public String getTableName() {
        return this.tblName;
    }

    public void setTableName(String tblName) {
        this.tblName = tblName;
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

    public boolean isIByMapping() {
        return this.importRule.equalsIgnoreCase(IBY_MAPPING_RULE);
    }

    public boolean isIByFileHeader() {
        return this.importRule.equalsIgnoreCase(IBY_FILE_HEADER_RULE);
    }

    public String getTaskInfo() {
        return fileName;
    }
}
