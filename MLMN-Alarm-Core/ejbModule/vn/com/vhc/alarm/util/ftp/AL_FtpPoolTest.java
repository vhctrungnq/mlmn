package vn.com.vhc.alarm.util.ftp;


import com.jscape.inet.ftp.Ftp;
import com.jscape.inet.ftp.FtpException;
import com.jscape.inet.ftp.FtpFile;

import java.io.File;

import java.util.Enumeration;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;


public class AL_FtpPoolTest {
    private static Logger logger =
        Logger.getLogger(AL_FtpPoolTest.class.getName());

    public AL_FtpPoolTest() {
        super();
    }

    @SuppressWarnings("unused")
	public static void main(String[] args) throws AL_InvalidPoolStateException,
                                                  FtpException,
                                                  InterruptedException {
        AL_FtpServerInfo download = new STSFtpServerInfo("STS-Download");
        AL_FtpServerInfo upload = new STSFtpServerInfo("STS-Upload");

        //ExecutorService executor = Executors.newFixedThreadPool(10);

        AL_FtpClientPool pool = new AL_FtpClientPool(5);
        Ftp con = pool.getFtpConnection(upload);

        File local =
            new File("D:/temp/TT3_Backbone_RoDNG1N AnDon_RoHNI2N GiapBat_Friday_ September 04_ 2009.xls");
        con.upload(local, "/pmsdata/upload/TT3.xls");
    }

    static class STSFtpServerInfo implements AL_FtpServerInfo {
        private String ip = "192.168.20.8";
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
            return "oracle";
        }

        public String getPassword() {
            return "oracle";
        }

        public int getPort() {
            return 21;
        }
    }

    static class HomeFtpServerInfo implements AL_FtpServerInfo {
        private String id;

        public HomeFtpServerInfo(String id) {
            this.id = id;
        }

        public String getServerId() {
            return id;
        }

        public String getHostName() {
            return "192.168.2.141";
        }

        public String getUsername() {
            return "ftpuser";
        }

        public String getPassword() {
            return "ftp123";
        }

        public int getPort() {
            return 21;
        }
    }

    @SuppressWarnings("rawtypes")
	static class FtpWorker implements Callable {
        private AL_FtpClientPool pool = null;
        boolean stop = false;

        FtpWorker(AL_FtpClientPool pool) {
            this.pool = pool;
        }

        @SuppressWarnings({ "unused", "null" })
		public Object call() {
            AL_FtpServerInfo downloadInfo = new STSFtpServerInfo("STS-Download");
            //new HomeFtpServerInfo("Home-Download");
            AL_FtpConnection con = null;
            Enumeration dirList = null;
            FtpFile file = null;
            int count = 0;
            while (!stop) {
                try {
                    con = null;
                    long begin = System.currentTimeMillis();
                    con = pool.getFtpConnection(downloadInfo);
                    long dur = System.currentTimeMillis() - begin;
                    logger.info(getThreadName() + " taked " + con + " in " +
                                dur + " milisec");
                    con.getDir();
                    logger.info("FileParser: " + con.getFtpFileParser());
                    count = 0;
                    while (dirList.hasMoreElements()) {
                        file = (FtpFile)dirList.nextElement();
                        count++;
                    }

                    logger.info(getThreadName() + " (Use " + con + ") found " +
                                count + " elements in directory: " +
                                con.getDir());

                } catch (Exception e) {
                    logger.warn(getThreadName() + " (Use " + con + ") error:",
                               e);
                    break;
                } finally {
                    if (con != null) {
                        con.disconnect();
                    }
                    try {
                        Thread.sleep(75000);
                    } catch (Exception e) {
                    }
                }
            }

            logger.info(getThreadName() + " stopped!!!");
            return null;
        }

        public void setStop() {
            stop = true;
        }

        public String getThreadName() {
            return "Thread <FtpWorker:" + Thread.currentThread().getId() + ">";
        }
    }
}
