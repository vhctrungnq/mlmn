package vn.com.vhc.sts.util.ftp;


import com.jscape.inet.ftp.FtpException;

import java.io.PrintStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;


public class STS_FtpClientPool {
    private static Logger logger =
        Logger.getLogger(STS_FtpClientPool.class.getName());
    private int poolSize = 1;
    private int retryConnect = 3;
    private ConcurrentMap<String, BlockingQueue<STS_FtpConnection>> poolMap;
    private List<STS_FtpConnection> poolList = new ArrayList<STS_FtpConnection>();
    private boolean closed = false;

    public STS_FtpClientPool(int poolSize) {
        if (poolSize > 0) {
            this.poolSize = poolSize;
        }
        poolMap =
                new ConcurrentHashMap<String, BlockingQueue<STS_FtpConnection>>();
    }

    public synchronized STS_FtpConnection getFtpConnection(STS_FtpServerInfo serverInfo) throws STS_InvalidPoolStateException {
        if (isClosed()) {
            throw new STS_InvalidPoolStateException("FtpConnection pool closed");
        }

        BlockingQueue<STS_FtpConnection> pool =
            poolMap.get(serverInfo.getServerId());
        if (pool == null) {
            initOneServerPool(serverInfo);
            pool = poolMap.get(serverInfo.getServerId());
        }

        try {
            STS_FtpConnection con = pool.take();

            //Check status of this connection to garantee it usable
            if (!con.isConnected()) {
                logger.warn(con + " disconnected. Try to reconnect ...");
                for (int i = 0; i < retryConnect; i++) {
                    try {
                        con.close();
                        con.connect();
                        logger.info(con + " reconnect successfully");
                        //logger.info(con + " taked from pool");
                        return con;
                    } catch (Exception e) {
                        logger.warn(con + " reconnect failure: " +
                                       e.getMessage());

                        if (i == retryConnect - 1) {
                            //Reconnect failure return pool
                            addToPool(con);
                            throw new STS_InvalidPoolStateException(e);
                        }
                    }
                }
                throw new STS_InvalidPoolStateException("Unknown fatal error expected!!!");
            } else {
                //logger.info(con + " taked from pool");
                return con;
            }
        } catch (InterruptedException e) {
            throw new STS_InvalidPoolStateException("Get FtpConnection from pool failure: " +
                                                e.getMessage());
        }
    }

    public void printPoolState(PrintStream out) {
        out.println(getPoolState());
    }

    public synchronized String getPoolState() {
        StringBuffer buf = new StringBuffer();
        Iterator<String> keys = poolMap.keySet().iterator();
        String key = null;
        BlockingQueue<STS_FtpConnection> pool = null;
        while (keys.hasNext()) {
            key = keys.next();
            pool = poolMap.get(key);
            buf.append("{");
            buf.append(key);
            buf.append(":");
            buf.append(pool);
            buf.append("}");
        }
        return buf.toString();
    }

    public synchronized String getAllConnectionState() {
        if (poolList != null) {
            return poolList.toString();
        } else {
            return "[]";
        }
    }


    protected void initOneServerPool(STS_FtpServerInfo serverInfo) throws STS_InvalidPoolStateException {
        String serverId = serverInfo.getServerId();

        BlockingQueue<STS_FtpConnection> pool =
            new ArrayBlockingQueue<STS_FtpConnection>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            try {
                pool.add(initFtpConnection(serverInfo));
            } catch (FtpException e) {
                logger.warn("Init connection to FTP server failure:", e);
            }
        }

        if (pool.size() > 0) {
            poolMap.put(serverInfo.getServerId(), pool);
            logger.info("Init FtpConnection pool successfully <" + serverId +
                        ":" + pool.size() + ">");
        } else {
            throw new STS_InvalidPoolStateException("Cannot init FtpConnection pool <" +
                                                serverId +
                                                ">. ServerInfo {Host: " +
                                                serverInfo.getHostName() +
                                                ", Port: " +
                                                serverInfo.getPort());
        }
    }

    /**
     * Add a FtpConnection to pool
     * @param con FtpConnection need add to pool
     */
    void addToPool(STS_FtpConnection con) {
        BlockingQueue<STS_FtpConnection> pool = poolMap.get(con.getServerId());
        if (!pool.contains(con)) {
            pool.add(con);
            //logger.info(con + " put back to pool");
        }
    }

    public synchronized void closePool() {
        logger.info("Closing FtpConnection pool");
        setClosed(true);
        for (STS_FtpConnection con : poolList) {
            con.close();
        }
        poolList.clear();
        logger.info("FtpConnection pool closed successfully");
    }

    private STS_FtpConnection initFtpConnection(STS_FtpServerInfo info) throws FtpException {
        STS_FtpConnection con = new STS_FtpConnection(this, info.getServerId());

        con.setHostname(info.getHostName());
        con.setPort(info.getPort());
        con.setUsername(info.getUsername());
        con.setPassword(info.getPassword());
        con.connect();
        logger.info("Connect to FTP server <" + info.getHostName() + ":" +
                    info.getPort() + "> successfully");
        //Contain here to use in closePool()
        poolList.add(con);
        return con;
    }

    void setClosed(boolean closed) {
        this.closed = closed;
    }

    boolean isClosed() {
        return closed;
    }
}
