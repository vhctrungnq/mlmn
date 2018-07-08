package inventory.cni.info;

import inventory.core.IN_Global;
import inventory.util.IN_Setting;
import inventory.util.IN_Util;
import inventory.util.task.IN_TaskInfo;
import inventory.utils.log.IN_DBLogParam;

import java.util.Hashtable;




public class IN_FileInfo extends IN_TaskInfo implements IN_DBLogParam {

    private int patternId = -1;
    private int fileId = -1;
    private String fileName = "";
    private String subDir = "";

    private String convertTime = "";
    private int durationTime = 0;
    private long originalSize = -1;
    private long convertedSize = -1;
    private String messageCode = "";
    private String convertClass = "";

    private int retryCount = 0;

    private Hashtable<Byte, String> params = null;

    public IN_FileInfo() {
        this.messageCode = "VMSC2-0300"; //convert success
    }

    public IN_FileInfo(int patternId, int fileId, String fileName,
                    String convertClass) {
        this.patternId = patternId;
        this.fileId = fileId;
        this.fileName = fileName;
        this.convertClass = convertClass;

        this.messageCode = "VMSC2-0300"; //convert success
    }

    public void setPatternId(int patternId) {
        this.patternId = patternId;
    }

    public int getPatternId() {
        return this.patternId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getFileId() {
        return this.fileId;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getOriginPath() {
        String originPath =
            IN_Global.SYSTEM_CONFIG.getProperty(IN_Setting.SELECTION_DIR);
        originPath += this.subDir;

        return originPath;
    }

    public String getDirectionPath() {
        String direcPath =
            IN_Global.SYSTEM_CONFIG.getProperty(IN_Setting.CONVERT_DIR);
        direcPath += this.subDir;

        return direcPath;
    }

    public String getBackupPath() {
        String backupPath =
            IN_Global.SYSTEM_CONFIG.getProperty(IN_Setting.CONVERT_ERROR_DIR);
        backupPath += this.subDir;

        return backupPath;
    }

    public void setConvertTime(String startTime) {
        this.convertTime = startTime;
    }

    public String getConvertTime() {
        return this.convertTime;
    }

    public void setDurationTime(int durationTime) {
        this.durationTime = durationTime;
    }

    public int getDurationTime() {
        return this.durationTime;
    }

    public void setOriginalSize(long fileSize) {
        this.originalSize = fileSize;
    }

    public long getOriginalSize() {
        return this.originalSize;
    }

    public void setConvertedSize(long fileSize) {
        this.convertedSize = fileSize;
    }

    public long getConvertedSize() {
        return this.convertedSize;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessageCode() {
        return this.messageCode;
    }

    public void setConvertClass(String convertClass) {
        this.convertClass = convertClass;
    }

    public String getConvertClass() {
        return this.convertClass;
    }

    public void setParams(Hashtable<Byte, String> params) {
        this.params = params;
    }

    public Hashtable<Byte, String> getParams() {
        return this.params;
    }

    public void reset() {
        this.convertTime = "";
        this.durationTime = 0;
        this.convertedSize = -1;
        this.originalSize = -1;
        this.messageCode = "";
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

    public String[] getDMLs() {
        if (this.convertTime.equalsIgnoreCase("")) {
            this.convertTime = IN_Util.getCurrentTime();
        }
        return new String[0];
        /*String[] sqls = new String[1];
        sqls[0] = "insert into I_FILE_CONVERT_LOGS \n" +
                "(FILE_ID, CONVERT_TIME, CONVERT_DURATION, ORIGINAL_SIZE, " +
                "CONVERTED_SIZE, STATUS_CODE) \n" +
                "values(" + this.getFileId() + ", to_date('" +
                this.getConvertTime() + "', '" + IN_Setting.DB_TIME_FORMAT +
                "'), " + this.getDurationTime() + ", " +
                this.getOriginalSize() + ", " + this.getConvertedSize() +
                ", '" + this.getMessageCode() + "')";
 
        return sqls;*/
    }

    public String getTaskInfo() {
        return fileName;
    }
}


