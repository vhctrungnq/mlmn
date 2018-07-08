package vn.com.vhc.alarm.util.ftp;


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


public class AL_FtpClientPool {
    private static Logger logger =
        Logger.getLogger(AL_FtpClientPool.class.getName());
    private int poolSize = 1;
    private int retryConnect = 3;
    private ConcurrentMap<String, BlockingQueue<AL_FtpConnection>> poolMap;
    private List<AL_FtpConnection> poolList = new ArrayList<AL_FtpConnection>();
    private boolean closed = false;

    public AL_FtpClientPool(int poolSize) {
        if (poolSize > 0) {
            this.poolSize = poolSize;
        }
        poolMap =
                new ConcurrentHashMap<String, BlockingQueue<AL_FtpConnection>>();
    }

    public synchronized AL_FtpConnection getFtpConnection(AL_FtpServerInfo serverInfo) throws AL_InvalidPoolStateException {
        if (isClosed()) {
            throw new AL_InvalidPoolStateException("FtpConnection pool closed");
        }

        BlockingQueue<AL_FtpConnection> pool =
            poolMap.get(serverInfo.getServerId());
        if (pool == null) {
            initOneServerPool(serverInfo);
            pool = poolMap.get(serverInfo.getServerId());
        }

        try {
            AL_FtpConnection con = pool.take();

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
                            throw new AL_InvalidPoolStateException(e);
                        }
                    }
                }
                throw new AL_InvalidPoolStateException("Unknown fatal error expected!!!");
            } else {
                //logger.info(con + " taked from pool");
                return con;
            }
        } catch (InterruptedException e) {
            throw new AL_InvalidPoolStateException("Get FtpConnection from pool failure: " +
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
        BlockingQueue<AL_FtpConnection> pool = null;
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


    protected void initOneServerPool(AL_FtpServerInfo serverInfo) throws AL_InvalidPoolStateException {
        String serverId = serverInfo.getServerId();

        BlockingQueue<AL_FtpConnection> pool =
            new ArrayBlockingQueue<AL_FtpConnection>(poolSize);
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
            throw new AL_InvalidPoolStateException("Cannot init FtpConnection pool <" +
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
    void addToPool(AL_FtpConnection con) {
        BlockingQueue<AL_FtpConnection> pool = poolMap.get(con.getServerId());
        if (!pool.contains(con)) {
            pool.add(con);
            //logger.info(con + " put back to pool");
        }
    }

    public synchronized void closePool() {
        logger.info("Closing FtpConnection pool");
        setClosed(true);
        for (AL_FtpConnection con : poolList) {
            con.close();
        }
        poolList.clear();
        logger.info("FtpConnection pool closed successfully");
    }

    private AL_FtpConnection initFtpConnection(AL_FtpServerInfo info) throws FtpException {
        AL_FtpConnection con = new AL_FtpConnection(this, info.getServerId());

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
