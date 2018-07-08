package vn.com.vhc.sts.dnu.entity;


public class STS_PatternInfo {

    private int patternId = 0;
    private int nodePatternGroup = -1;
    private int timePatternGroup = -1;

    private String filePattern = "";
    private String nodeType = "";
    private String rawTable = "";
    private String convertClass = "";
    private String status = "";
    private String subDir = "";
    private String timeFormat = "";
// Update by ThangPX. 20140218
    private String serverNode="";
    public void setPatternId(int patternId) {
        this.patternId = patternId;
    }

    public int getPatternId() {
        return patternId;
    }

    public void setFilePattern(String filePattern) {
        this.filePattern = filePattern;
    }

    public String getFilePattern() {
        return filePattern;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setRawTable(String rawTable) {
        this.rawTable = rawTable;
    }

    public String getRawTable() {
        return rawTable;
    }

    public void setConvertClass(String convertClass) {
        this.convertClass = convertClass;
    }

    public String getConvertClass() {
        return convertClass;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setSubDir(String subDir) {
        this.subDir = subDir;
    }

    public String getSubDir() {
        return subDir;
    }

    public void setNodePatternGroup(int nodePatternGroup) {
        this.nodePatternGroup = nodePatternGroup;
    }

    public int getNodePatternGroup() {
        return nodePatternGroup;
    }

    public void setTimePatternGroup(int timePatternGroup) {
        this.timePatternGroup = timePatternGroup;
    }

    public int getTimePatternGroup() {
        return timePatternGroup;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

	public String getServerNode() {
		return serverNode;
	}

	public void setServerNode(String serverNode) {
		this.serverNode = serverNode;
	}

	 
    
}
