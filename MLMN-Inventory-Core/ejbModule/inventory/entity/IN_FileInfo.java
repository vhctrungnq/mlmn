package inventory.entity;

import inventory.core.IN_Global;
import inventory.util.IN_Setting;
import inventory.util.IN_Util;
import inventory.util.task.IN_TaskInfo;
import inventory.utils.log.IN_DBLogParam;

import java.util.Date;




public class IN_FileInfo extends IN_TaskInfo implements IN_DBLogParam {

    public static final int REMOTE_TYPE = 1;
    public static final int LOCAL_TYPE = 2;

    private int retryCount = 0;

    //The attributes to insert D_DOWNLOAD_LOGS
    private String serverId = "";
    private String fileName = "";
    private String remoteDir = "";
    private String baseDir = "";
    private Date downloadTime = null;
    private int fileSize = 0;

    //The attributes to insert C_RAW_FILES_MLMN table
    private int patternId = 0;
    private int hour = 0;
    private String date = "";
    private String localDir = "";
    private String nodeName = "";
    private String rawTable = "";
    private String subDir = "";
    private String originalName = "";
    //Update 07-10-2013 by AnhNT
    private String module = ""; 
  //  private String subDirDownload = "";
    // Add by ThangPX
    private String serverNode="";

    private int type = 0;

    public IN_FileInfo() {
        this.type = REMOTE_TYPE;
    }

    public IN_FileInfo(int type) {
        this.type = type;
    }

    public String getTaskInfo() {
        return fileName;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setRemoteDir(String remoteDir) {
        this.remoteDir = remoteDir;
    }

    public String getRemoteDir() {
        return remoteDir;
    }

    public String getSubDirectory() {
        String subDir = "";
        if (this.isRemoteType()) {
            if (!remoteDir.isEmpty()) {
                subDir = remoteDir.replace(baseDir, "");
            }
        } else if (this.isLocalType()) {
            if (!localDir.isEmpty()) {
                subDir = localDir.replace(baseDir, "");
            }
        }
        if (!subDir.isEmpty() &&
            !subDir.substring(0, 1).equals(IN_Global.SEPARATOR)) {
            subDir = IN_Global.SEPARATOR + subDir;
        }
        return subDir;
    }

    public void setDownloadTime(Date downloadTime) {
        this.downloadTime = downloadTime;
    }

    public Date getDownloadTime() {
        return downloadTime;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getServerId() {
        return serverId;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    public String getBaseDir() {
        return baseDir;
    }

    public void setPatternId(int patternId) {
        this.patternId = patternId;
    }

    public int getPatternId() {
        return patternId;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getHour() {
        return hour;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setLocalDir(String localDir) {
        this.localDir = localDir;
    }

    public String getLocalDir() {
        return localDir;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setRawTable(String rawTable) {
        this.rawTable = rawTable;
    }

    public String getRawTable() {
        return rawTable;
    }

    public String getSubDir() {
        return this.subDir;
    }

    public void setSubDir(String subDir) {
        this.subDir = subDir;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public boolean isRemoteType() {
        return this.type == REMOTE_TYPE;
    }

    public boolean isLocalType() {
        return this.type == LOCAL_TYPE;
    }
    
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
	
	
    public String getServerNode() {
		return serverNode;
	}

	public void setServerNode(String serverNode) {
		this.serverNode = serverNode;
	}

	public String[] getDMLs() {
        String[] sqls = new String[1];
        sqls[0] = getInsertSQL();
        return sqls;
    } 

    protected String getInsertSQL() {
        String execCommand = "";
        if (this.isRemoteType()) {
            String time = IN_Util.getTime(this.downloadTime, IN_Setting.TIME_FORMAT);
            execCommand =
                    "insert into D_DOWNLOAD_LOGS(FILE_NAME,SERVER_ID,DIRECTORY," +
                    "DOWNLOAD_TIME,FILE_SIZE) values('" + getFileName() +
                    "', " + getServerId() + ", '" + getSubDirectory() +
                    "', to_date('" + time + "', '" + IN_Setting.DB_TIME_FORMAT +
                    "'), " + getFileSize() + ")";
        } else if (this.isLocalType()) {
            execCommand =
                    "insert into C_RAW_FILES_MLMN(FILE_ID,PATTERN_ID,FILE_NAME,DAY," +
                    "HOUR,CONVERT_FLAG,IMPORT_FLAG,NODE_NAME,RAW_TABLE,ORIGINAL_NAME,MODULE, SERVER_NODE) " +
                    "values(SEQ_CRF.NEXTVAL, " + this.getPatternId() + ", '" +
                    this.getFileName() + "',to_date('" + this.getDate() +
                    "','" + IN_Setting.DATE_FORMAT + "'), " + this.getHour() +
                    ", 0, 0, '" + this.getNodeName() + "', '" +
                    this.getRawTable() + "', '" + this.getOriginalName() +
                    "','" + this.getModule() + "', '" + this.getServerNode() + "')";
        }

        return execCommand;
    }
}
