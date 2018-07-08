package vn.com.vhc.sts.dnu;


import com.jscape.inet.ftp.Ftp;
import com.jscape.inet.ftp.FtpException;
import com.jscape.inet.ftp.FtpFile;

import java.io.File;
import java.io.IOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

import vn.com.vhc.sts.core.STS_Global;
import vn.com.vhc.sts.dnu.entity.STS_FileInfo;
import vn.com.vhc.sts.dnu.entity.STS_ServerInfo;
import vn.com.vhc.sts.util.STS_Util;
import vn.com.vhc.sts.util.ftp.STS_FtpClientPool;
import vn.com.vhc.sts.util.ftp.STS_InvalidPoolStateException;
import vn.com.vhc.sts.util.ftp.impl.STS_MaxQueueException;



public class STS_TestDownload {

    public STS_TestDownload() {
        // runApps();
        //testTime();

        testMoveFile();
    }

    private void testMoveFile() {
        File file = new File("D:/RESEARCH", "abc.txt");

        String direcPath = "D:/RESEARCH/convert_file";
        String fileName = "counter.txt";

        try {
            moveTo(file, direcPath, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void moveTo(File file, String direcPath,
                        String fileName) throws IOException {
        // Destination directory
        File dirFile = new File(direcPath);
        if (!dirFile.exists()) {
            if (!dirFile.mkdirs()) {
                throw new IOException("Make directory failure");
            }
        }
        // Move file to new directory
        boolean result = file.renameTo(new File(direcPath, fileName));
        if (!result) {
            throw new IOException("Does't move file to direction path");
        }
    }


    private void runApps() {
        try {
            context = getInitialContext();
            ds = (DataSource)context.lookup("jdbc/pms2DS");
            if (ds != null) {
                STS_DownloadServiceThread.startService(ds);
            } else {
                System.out.println("Khong tao duoc DataSource");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testTime() {
        try {
            context = getInitialContext();
            ds = (DataSource)context.lookup("jdbc/pms2DS");
            if (ds == null) {
                return;
            }

            Connection conn = ds.getConnection();
            String query = "select * from C_RAW_FILES_MLMN";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            Date date = null;
            while (rs.next()) {
                date = rs.getDate("DAY");
                System.out.println("DAY: " +
                                   STS_Util.getTime(date, "yyyy-MM-dd HH:mm:ss"));
            }
            rs.close();
            st.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private STS_FtpClientPool ftpPool = null;
    private STS_ServerInfo serverInfo = null;

    public void test() {
        serverInfo = new STS_ServerInfo();
        serverInfo.setServerId("100");
        serverInfo.setPort(21);
        serverInfo.setUsername("ftpuser");
        serverInfo.setPassword("ftppassword");
        serverInfo.setIpAddress("192.168.20.100");

        Ftp ftp = null;
        String dir = "/obs";
        String baseDir = "/home/oracle/pmsdata";

        try {
            STS_FtpClientPool ftpPool = new STS_FtpClientPool(1);
            ftp = ftpPool.getFtpConnection(serverInfo);
            dirListingRecursive(ftp, baseDir, dir, serverInfo);
        } catch (STS_InvalidPoolStateException ie) {
            ie.printStackTrace();
        } catch (STS_MaxQueueException e) {
            e.printStackTrace();
        }
    }

    protected void dirListingRecursive(Ftp ftp, String baseDir, String path,
                                       STS_ServerInfo serverInfo) throws STS_MaxQueueException {
        try {
            int cnt = 0;
            //System.out.println("Dir listing: " + path + " ... ");
            ftp.setDir(path);
            String filePath = null;
            String fileName = null;
            FtpFile file = null;
            STS_FileInfo downloadInfo = null;

            Enumeration dirList = ftp.getDirListing();
            while (dirList.hasMoreElements()) {
                file = (FtpFile)dirList.nextElement();
                //dirList.

                fileName = file.getFilename();
                filePath = path + STS_Global.SEPARATOR + fileName;

                if (file.isDirectory()) {
                    dirListingRecursive(ftp, baseDir, filePath, serverInfo);
                } else {
                    cnt++;
                    downloadInfo = new STS_FileInfo();
                    downloadInfo.setFileName(fileName);
                    downloadInfo.setFileSize((int)file.getFilesize());
                    downloadInfo.setRemoteDir(path);
                    downloadInfo.setBaseDir(baseDir);

                    System.out.println(file.getDate() + " --- " +
                                       file.getTime() + " --- FILE_NAME: " +
                                       file.getFilename());
                }
            }
            System.out.println("Dir listing: " + path + " ... ");
        } catch (FtpException e) {
        }
    }


    private Context context = null;
    private static DataSource ds = null;

    public static DataSource getDataSource() {
        return ds;
    }

    public static void main(String[] args) {
        STS_TestDownload tconvert = new STS_TestDownload();
    }

    private static Context getInitialContext() throws NamingException {
        Hashtable env = new Hashtable();
        // WebLogic Server 10.x connection details
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "weblogic.jndi.WLInitialContextFactory");
        env.put(Context.PROVIDER_URL, "t3://192.168.20.8:8080");
        return new InitialContext(env);
    }
}
