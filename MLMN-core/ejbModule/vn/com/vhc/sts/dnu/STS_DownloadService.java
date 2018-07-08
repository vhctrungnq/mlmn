package vn.com.vhc.sts.dnu;


import com.jscape.inet.ftp.Ftp;
import com.jscape.inet.ftp.FtpException;
import com.jscape.inet.ftp.FtpFile;

import java.io.File;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.core.STS_Global;
import vn.com.vhc.sts.dnu.entity.STS_FileInfo;
import vn.com.vhc.sts.dnu.entity.STS_ServerInfo;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.STS_Util;
import vn.com.vhc.sts.util.ftp.STS_FtpClientPool;
import vn.com.vhc.sts.util.ftp.STS_FtpConnection;
import vn.com.vhc.sts.util.ftp.STS_InvalidPoolStateException;
import vn.com.vhc.sts.util.ftp.impl.STS_MaxQueueException;
import vn.com.vhc.sts.util.task.STS_IllegalServiceStateException;
import vn.com.vhc.sts.util.task.STS_TaskInfo;
import vn.com.vhc.sts.util.task.STS_TaskService;
import vn.com.vhc.sts.utils.log.STS_DbAppender;



public class STS_DownloadService extends STS_TaskService {

    private static Logger logger =
        Logger.getLogger(STS_DownloadService.class.getName());

    private int retryNumber = -1;
    private int maxExecutor = -1;
    private int totalTask = 0;
    private long startTimeLogin = 0;

    private String baseDirectory = "";
    private String status = "";

    private DataSource ds = null;
    private STS_FtpClientPool ftpPool = null;
    private STS_ServerInfo serverInfo = null;


    public STS_DownloadService(String name, DataSource ds, STS_ServerInfo serverInfo) {
        super(name);
        this.ds = ds;
        this.serverInfo = serverInfo;
        this.status = STS_Setting.UNKNOWN_STATUS ;
    }

    public String getServerId() {
        return this.serverInfo.getServerId();
    }

    public void doInit() {
        String value = "";
        try {
            value =
                    STS_Global.SYSTEM_CONFIG.getProperty(STS_Setting.MAX_DOWNLOAD_THREAD_KEY);
            maxExecutor = Integer.parseInt(value);
        } catch (Exception e) {
            logger.warn("Invalid '" + STS_Setting.MAX_DOWNLOAD_THREAD_KEY +
                           "' config in C_SYSTEM_CONFIGS_MLMN");
        }

        try {
            value =
                    STS_Global.SYSTEM_CONFIG.getProperty(STS_Setting.MAX_DOWNLOAD_RETRY_KEY);
            retryNumber = Integer.parseInt(value);
        } catch (Exception e) {
            logger.warn("Invalid '" + STS_Setting.MAX_DOWNLOAD_RETRY_KEY +
                           "' config in C_SYSTEM_CONFIGS_MLMN");
        }

        try {
            baseDirectory =
                    STS_Global.SYSTEM_CONFIG.getProperty(STS_Setting.BASE_LOCAL_DIR);
        } catch (Exception e) {
            logger.warn("Invalid '" + STS_Setting.BASE_LOCAL_DIR +
                           "' config in C_SYSTEM_CONFIGS_MLMN");
        }

        this.maxExecutor = (this.maxExecutor <= 0) ? 1 : this.maxExecutor;
        this.retryNumber = (this.retryNumber < 0) ? 0 : this.retryNumber;

        initTaskQueues();
    }

    /**
     * @param input
     */
    public void doTask(STS_TaskInfo input, Connection conn) {
        STS_FileInfo info = (STS_FileInfo)input;

        String remoteDir = info.getRemoteDir();
        String fileName = info.getFileName();
        STS_FtpConnection objFtp = null;
        try {
            objFtp = this.ftpPool.getFtpConnection(serverInfo);

            // make directory
            String path = "";
            String subDir = info.getSubDirectory();
            String serverName = serverInfo.getServerName();
            if (serverName.trim().isEmpty()) {
                path = baseDirectory + subDir;
            } else {
                path = baseDirectory + System.getProperty("file.separator");
                path += serverName.toUpperCase() + subDir;
            }
            File localDir = new File(path);
            if (!localDir.exists()) {
                if (!localDir.mkdirs()) {
                    logger.warn("Make directory failure: " + path);
                    increaseFailureTasks();
                    return;
                }
            }

            info.setServerId(serverInfo.getServerId());
            info.setDownloadTime(new Date());

            // download file on the server
            objFtp.setDir(remoteDir);
            objFtp.setLocalDir(localDir);
            objFtp.download(fileName);
            STS_DbAppender.log(info);

            increaseDoneTasks();
        } catch (STS_InvalidPoolStateException e) {
            logger.warn("Get ftp connection from pool failure: " +
                           e.getMessage());
            doRetry(info);
        } catch (Exception e) {
            logger.warn("Download on <SERVER:" +
                           this.serverInfo.getServerName() + "> " + remoteDir +
                           "/" + fileName + " failure: " + e.getMessage() +
                           ". Try to redownload");
            if (objFtp != null) {
                objFtp.notifyConnectionError();
            }

            doRetry(info);
        } finally {
            if (objFtp != null) {
                objFtp.disconnect();
            }
        }
    }

    public void doFinallize() {
        this.ftpPool.closePool();
        this.logCyclelife();
        this.updateLoginTime();
        
        this.retryNumber = -1;
        this.maxExecutor = -1;
        this.totalTask = 0;
    }

    public int getMaxExecutorThread() {
        return maxExecutor;
    }

    private void initTaskQueues() {
        if (serverInfo == null) {
            logger.warn("Server Info is null");
            return;
        }
        try {
            this.ftpPool = new STS_FtpClientPool(maxExecutor);
            this.startTimeLogin = System.currentTimeMillis();
            this.refreshLoginTime();
            this.searchFiles();
            if (this.totalTask > 0) {
                this.setStatus(STS_Setting.DOWNLOADING_STATUS);
            }
        } catch (Exception e) {
            logger.warn("Search file to download error:", e);
        }
    }

    private void searchFiles() {
        Ftp ftp = null;
        try {
            logger.info("Search files on server: " +
                        serverInfo.getIpAddress() + " with username: " +
                        serverInfo.getUsername());
            this.setStatus(STS_Setting.SEARCHING_STATUS);

            ftp = ftpPool.getFtpConnection(serverInfo);
            List<String> listDir = serverInfo.getListDir();
            String baseDir = "";
            for (String dir : listDir) {
                baseDir = dir;
                dirListingRecursive(ftp, baseDir, dir, serverInfo);
            }
        } catch (STS_InvalidPoolStateException e) {
            logger.warn("Get ftp connection from pool error:", e);
        } catch (Exception e) {
            logger.info(e.getMessage());
        } finally {
            ftp.disconnect();
        }
    }

    /**
     * Directory Listing Recursive
     * @param ftp
     * @param baseDir
     * @param path
     * @param serverInfo
     * @throws STS_MaxQueueException
     */
    private void dirListingRecursive(Ftp ftp, String baseDir, String path,
                                     STS_ServerInfo serverInfo) {
        try {
            int cnt = 0;
            logger.info("Dir listing: " + path + " ... ");
            ftp.setDir(path);
            String filePath = null;
            String fileName = null;
            FtpFile file = null;
            STS_FileInfo downloadInfo = null;
            String modifiedTime = "";

            Enumeration<?> dirList = ftp.getDirListing();
            while (dirList.hasMoreElements()) {
                file = (FtpFile)dirList.nextElement();
                fileName = file.getFilename();
                filePath = path + STS_Global.SEPARATOR + fileName;

                if (file.isDirectory()) {
                    dirListingRecursive(ftp, baseDir, filePath, serverInfo);
                } else {
                    cnt++;
                    modifiedTime = file.getDate() + ":" + file.getTime();

                    if (validateModifiedTime(modifiedTime)) {
                        downloadInfo = new STS_FileInfo();
                        downloadInfo.setFileName(fileName);
                        downloadInfo.setFileSize((int)file.getFilesize());
                        downloadInfo.setRemoteDir(path);
                        downloadInfo.setBaseDir(baseDir);

                        addTask(downloadInfo);
                        totalTask++;
                    }
                }
            }
            logger.info("... found " + cnt + " files in " + path);
        } catch (FtpException e) {
            logger.warn("SERVER: " + this.serverInfo.getServerId() +
                           " --- Dir listing " + path + " error: " +
                           e.getMessage());
        }
    }

    private static final String MODIFIED_TIME_FORMAT = "MM-dd-yy:HH:mma";

    private boolean validateModifiedTime(String modifiedTime) {
        Date loginTime = this.serverInfo.getLoginTime();
        if (loginTime == null) {
            return true;
        }
        long modifiedMinisecond =
            STS_Util.getTimeMinisecond(modifiedTime, MODIFIED_TIME_FORMAT);
        if ((modifiedMinisecond >= loginTime.getTime()) &&
            (modifiedMinisecond <= this.startTimeLogin)) {
            return true;
        }
        return false;
    }

    /**
     * Add Queue Download file. Method call if download ftp file is false.
     * @param entity
     */
    private void doRetry(STS_FileInfo entity) {
        int count = entity.getRetryCount();
        if (count < retryNumber) {
            count += 1;
            entity.setRetryCount(count);
            try {
                retryTask(entity);
            } catch (STS_IllegalServiceStateException i) {
                logger.warn("Retry download failure: ", i);
            }
        } else {
            increaseFailureTasks();
            logger.warn("Download " + entity.getRemoteDir() +
                           STS_Global.SEPARATOR + entity.getFileName() +
                           " failure after " + retryNumber + " retries");
        }
    }

    private void refreshLoginTime() {
        Connection conn = null;
        try {
            conn = this.ds.getConnection();
            Statement st = conn.createStatement();

            String querySQL =
                "select LOGIN_TIME from C_SERVERS where SERVER_ID = " +
                this.serverInfo.getServerId();
            ResultSet rs = st.executeQuery(querySQL);
            while (rs.next()) {
                this.serverInfo.setLoginTime(rs.getDate("LOGIN_TIME"));
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            logger.warn("Can't get login time: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    logger.warn("Cannot close connection to database");
                }
            }
        }
    }

    private void logCyclelife() {
        String startTime =
            STS_Util.getTime(this.startTimeLogin, STS_Setting.TIME_FORMAT);
        String endTime = STS_Util.getCurrentTime();
        String serverName = this.serverInfo.getServerName();
        if (serverName == null || serverName.trim().length() == 0) {
            serverName = "NONE";
        }

        Connection conn = null;
        try {
            conn = this.ds.getConnection();
            Statement st = conn.createStatement();

            String sqlCommand =
                "insert into C_DOWNLOAD_LOGS(SERVER_NAME, BEGIN_TIME," +
                "TOTAL_TASK, END_TIME, DONE_TASK) values('" + serverName +
                "', to_date('" + startTime + "','" + STS_Setting.DB_TIME_FORMAT +
                "'), " + this.getTotalTasks() + ", to_date('" + endTime +
                "','" + STS_Setting.DB_TIME_FORMAT + "'), " + this.getDoneTasks() +
                ")";
            st.execute(sqlCommand);
            st.close();
        } catch (SQLException e) {
            logger.warn("Can't insert info into C_DOWNLOAD_LOGS table: " +
                           e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    logger.warn("Cannot close connection to database");
                }
            }
        }
    }

    private void updateLoginTime() {
        if (this.getRemainTasks() != 0) {
            return;
        }
        
        String time =
            STS_Util.getTime(new Date(this.startTimeLogin), STS_Setting.TIME_FORMAT);
        Connection conn = null;
        try {
            conn = this.ds.getConnection();
            Statement st = conn.createStatement();
            // update state
            String queryStr =
                "update C_SERVERS set LOGIN_TIME = to_date('" + time + "', '" +
                STS_Setting.DB_TIME_FORMAT + "') where SERVER_ID = " +
                this.getServerId();

            st.execute(queryStr);
            st.close();
        } catch (SQLException e) {
            logger.warn("Can't update login time with a error: " +
                           e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    logger.warn("Cannot close connection to database");
                }
            }
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
