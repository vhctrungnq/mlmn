package vn.com.vhc.alarm.util.ftp;


import com.jscape.inet.ftp.Ftp;


public class AL_FtpConnection extends Ftp {
    private static int ID_GEN = 0;
    private AL_FtpClientPool clientPool;
    private String serverId;
    private int id;

    /**
     * Construct FtpConnection object in the pool
     * @param clientPool Pool to hold this FtpConnection
     */
    AL_FtpConnection(AL_FtpClientPool clientPool, String serverId) {
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
