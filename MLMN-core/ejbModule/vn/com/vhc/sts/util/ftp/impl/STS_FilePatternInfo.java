package vn.com.vhc.sts.util.ftp.impl;

class STS_FilePatternInfo {
    static final String QUERY =
        "select cfp.source_server, cfp.pattern_id, cfp.file_pattern, cfp.raw_table, cs.base_dir || cfp.sub_dir as local_dir, cfp.node_pattern_group, cfp.time_pattern_group, cfp.time_pattern from c_file_patterns_mlmn cfp join c_servers cs on (cfp.local_server = cs.server_id) where  status = 'Y' and cfp.source_server = ";

    private int sourceServer;
    private int patternId;
    private String filePattern;
    private String rawTable;
    private String localDir;
    private int nodeGroup;
    private int timeGroup;
    private String timePattern;

    public STS_FilePatternInfo() {
        super();
    }

    /**
     * Use this pattern info to parse filePath get from FTP server and get RawFileInfo
     * @param filePath full file path get from FTP Server
     * @return RawFileInfo use to log to Database
     */
    public STS_RawFileInfo getRawFileInfo(String filePath) {
        //Need to implements
        return null;
    }

    public void setSourceServer(int sourceServer) {
        this.sourceServer = sourceServer;
    }

    public int getSourceServer() {
        return sourceServer;
    }

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

    public void setRawTable(String rawTable) {
        this.rawTable = rawTable;
    }

    public String getRawTable() {
        return rawTable;
    }

    public void setLocalDir(String localDir) {
        this.localDir = localDir;
    }

    public String getLocalDir() {
        return localDir;
    }

    public void setNodeGroup(int nodeGroup) {
        this.nodeGroup = nodeGroup;
    }

    public int getNodeGroup() {
        return nodeGroup;
    }

    public void setTimeGroup(int timeGroup) {
        this.timeGroup = timeGroup;
    }

    public int getTimeGroup() {
        return timeGroup;
    }

    public void setTimePattern(String timePattern) {
        this.timePattern = timePattern;
    }

    public String getTimePattern() {
        return timePattern;
    }
}
