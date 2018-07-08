package vn.com.vhc.sts.util.ftp.impl;

import java.util.List;

import vn.com.vhc.sts.util.ftp.STS_FtpServerInfo;



class STS_SourceServerInfo implements STS_FtpServerInfo {
    static final String QUERY =
        "select distinct cs.server_id, cs.ip_address, cs.port, cs.ftp_user, cs.ftp_password, cs.base_dir  from c_file_patterns_mlmn cfp join c_servers cs on (cfp.source_server = cs.server_id)";

    private int serverId;
    private String hostName;
    private String username;
    private String password;
    private int port;
    private String baseDir;
    private List<STS_FilePatternInfo> filePatterns;

    public STS_SourceServerInfo() {
        super();
    }

    /**
     * Get all FilePatternInfo relative to sourceServer
     * @param sourceServer Source server in c_file_patterns_mlmn
     * @return List of FilePatternInfo in c_file_patterns_mlmn where Source_Server = sourceServer
     */
    public List<STS_FilePatternInfo> getFilePatternInfo(int sourceServer) {
        //Need to implements
        return null;
    }

    public String getServerId() {
        return String.valueOf(serverId);
    }

    public String getHostName() {
        return hostName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    public String getBaseDir() {
        return baseDir;
    }

    public void setFilePatterns(List<STS_FilePatternInfo> filePatterns) {
        this.filePatterns = filePatterns;
    }

    public List<STS_FilePatternInfo> getFilePatterns() {
        return filePatterns;
    }
}
