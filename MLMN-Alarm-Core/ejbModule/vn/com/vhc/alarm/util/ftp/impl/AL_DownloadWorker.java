package vn.com.vhc.alarm.util.ftp.impl;


import com.jscape.inet.ftp.Ftp;
import com.jscape.inet.ftp.FtpException;
import com.jscape.inet.ftp.FtpFile;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import vn.com.vhc.alarm.core.AL_Global;
import vn.com.vhc.alarm.util.ftp.AL_FtpClientPool;
import vn.com.vhc.alarm.util.ftp.AL_FtpServerInfo;
import vn.com.vhc.alarm.util.ftp.AL_InvalidPoolStateException;
import vn.com.vhc.alarm.util.task.AL_TaskInfo;
import vn.com.vhc.alarm.util.task.AL_TaskService;



public class AL_DownloadWorker extends AL_TaskService {
    private static Logger logger =
        Logger.getLogger(AL_DownloadWorker.class.getName());
    protected static int MAX_RETRY = 1;
    protected static int MAX_QUEUE = 1024;
    private List<String> fileList;
    private AL_FtpClientPool ftpPool;
    //private DataSource ds;

    public AL_DownloadWorker(String name, DataSource ds) {
        super(name);
        //this.ds = ds;
    }

    public static void main(String[] args) throws AL_InvalidPoolStateException {
        new AL_DownloadWorker("DownlodWorker", null).doTest();
    }

    void doTest() throws AL_InvalidPoolStateException {
        doInit();

        String baseDir = "/obs";
        Ftp ftp = ftpPool.getFtpConnection(new STSFtpServerInfo("STS-8"));
        try {
            dirListingRecursive(ftp, baseDir);
        } catch (AL_MaxQueueException e) {
            logger.info("Listing compelete, queue size: " + fileList.size());
            for (String s : fileList) {
                System.out.println(s);
            }
        }
    }


    public void doInit() {
        try {
            MAX_QUEUE =
                    Integer.parseInt(AL_Global.SYSTEM_CONFIG.getProperty("max.queue.download"));
        } catch (Exception e) {
            logger.warn("Invalid 'max.queue.download' config in C_SYSTEM_CONFIGS_MLMN");
        }
        fileList = new ArrayList<String>(MAX_QUEUE);
        ftpPool = new AL_FtpClientPool(getMaxExecutorThread());

        try {
            MAX_RETRY =
                    Integer.parseInt(AL_Global.SYSTEM_CONFIG.getProperty("max.queue.retry"));
        } catch (Exception e) {
            logger.warn("Invalid 'max.queue.retry' config in C_SYSTEM_CONFIGS_MLMN");
        }
    }

    public void doTask(AL_TaskInfo input) {
    }

    public void doFinallize() {
    }

    public int getMaxExecutorThread() {
        try {
            return Integer.parseInt(AL_Global.SYSTEM_CONFIG.getProperty("max.download.thread"));
        } catch (Exception e) {
            logger.warn("Invalid 'max.download.thread' config in C_SYSTEM_CONFIGS_MLMN");
            return 1;
        }
    }

    /**
     * List and take all file from baseDir recursively in FTP server
     * @param ftp Ftp object use to connect to FTP server
     * @param baseDir Base subDir to perform dir listing
     */
    protected void dirListingRecursive(Ftp ftp,
                                       String baseDir) throws AL_MaxQueueException {
        try {
            int cnt = 0;
            logger.info("Dir listing: " + baseDir + " ... ");
            ftp.setDir(baseDir);
            Enumeration<?> dirList = ftp.getDirListing();
            FtpFile file = null;
            String filePath = null;
            while (dirList.hasMoreElements()) {
                file = (FtpFile)dirList.nextElement();
                filePath = baseDir + AL_Global.SEPARATOR + file.getFilename();
                if (file.isDirectory()) {
                    dirListingRecursive(ftp, filePath);
                } else {
                    cnt++;
                    addToQueue(filePath);
                }
            }
            logger.info("... found " + cnt + " files in " + baseDir);
        } catch (FtpException e) {
            logger.warn("Set dir to " + baseDir + " failure: " +
                           e.getMessage());
        }
    }

    protected void addToQueue(String file) throws AL_MaxQueueException {
        fileList.add(file);
        if (fileList.size() >= MAX_QUEUE) {
            throw new AL_MaxQueueException("Queue reach " + MAX_QUEUE + " limit");
        }
    }

    static class STSFtpServerInfo implements AL_FtpServerInfo {
        private String ip = "192.168.20.100";
        private String id = null;

        public STSFtpServerInfo(String id) {
            this.id = id;
        }

        public String getServerId() {
            return id;
        }

        public String getHostName() {
            return ip;
        }

        public String getUsername() {
            return "ftpuser";
        }

        public String getPassword() {
            return "ftppassword";
        }

        public int getPort() {
            return 21;
        }
    }
}
