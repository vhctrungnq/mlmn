package vn.com.vhc.sts.util.ftp;


import com.jscape.inet.ftp.Ftp;


public class STS_FtpConnection extends Ftp {
    private static int ID_GEN = 0;
    private STS_FtpClientPool clientPool;
    private String serverId;
    private int id;

    /**
     * Construct FtpConnection object in the pool
     * @param clientPool Pool to hold this FtpConnection
     */
    STS_FtpConnection(STS_FtpClientPool clientPool, String serverId) {
        super();
        this.clientPool = clientPool;
        this.serverId = serverId;
        id = ID_GEN++;
    }

    /**
     * This Ftp connection return to FtpClientPool instead of disconect from FTP server
     */
    @Override
    public synchronized void disconnect() {
        clientPool.addToPool(this);
    }

    public synchronized void notifyConnectionError() {
        super.disconnect();
    }

    public String getServerId() {
        return serverId;
    }

    public String toString() {
        return "FtpConnection <" + serverId + ":" + id + ">";
    }

    synchronized void close() {
        super.disconnect();
    }
}
