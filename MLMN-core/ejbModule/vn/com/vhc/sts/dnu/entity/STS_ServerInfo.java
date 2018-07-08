package vn.com.vhc.sts.dnu.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.com.vhc.sts.util.ftp.STS_FtpServerInfo;



public class STS_ServerInfo implements STS_FtpServerInfo {

    private int port = -1;

    private String serverId = "";
    private String serverName = "";
    private String ipAddress = "";
    private String username = "";
    private String password = "";
    private Date loginTime = null;

    private List<String> listDir = new ArrayList<String>();

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setUsername(String ftpUser) {
        this.username = ftpUser;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String ftpPassword) {
        this.password = ftpPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setDirectory(String dir) {
        if (dir != null && dir.length() > 0) {
            for (String d : this.listDir) {
                if (d.equalsIgnoreCase(dir)) {
                    return;
                }
            }
            this.listDir.add(dir);
        }
    }

    public String getDirectory(int count) {
        if (count < this.listDir.size()) {
            return this.listDir.get(count);
        }
        return "";
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public String getHostName() {
        return ipAddress;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public List<String> getListDir() {
        return listDir;
    }

    public void setListDir(List<String> listDir) {
        this.listDir = listDir;
    }
}
