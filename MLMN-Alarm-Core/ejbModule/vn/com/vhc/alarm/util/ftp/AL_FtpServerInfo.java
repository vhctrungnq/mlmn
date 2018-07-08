package vn.com.vhc.alarm.util.ftp;

/**
 * Class use FTPClient pool must be pass an object implements this interface to identify FTP server to connect to.
 */
public interface AL_FtpServerInfo {

    /**
     * ID to identify FTP server in the pool
     * @return
     */
    public String getServerId();

    /**
     * IP Address of FTP server need to connect
     * @return
     */
    public String getHostName();

    /**
     * Username use to connect FTP server
     * @return
     */
    public String getUsername();

    /**
     * Password use to connect FTP server
     * @return
     */
    public String getPassword();

    /**
     * Port listening on FTP server
     * @return
     */
    public int getPort();
}
