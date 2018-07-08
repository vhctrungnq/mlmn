package vn.com.vhc.sts.dnu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.core.STS_Global;
import vn.com.vhc.sts.dnu.entity.STS_ServerInfo;
import vn.com.vhc.sts.util.STS_Attribute;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.STS_Util;



public class STS_DownloadServiceThread {

    private static Logger logger =
        Logger.getLogger(STS_DownloadServiceThread.class.getName());

    private static STS_DownloadServiceThread singleton = null;
    private DataSource dataSource = null;
    private ExecutorService threadPool = null;

    private List<TaskExecutor> executors = new ArrayList<TaskExecutor>();
    private List<Thread> runningThreads =
        Collections.synchronizedList(new ArrayList<Thread>());
    private Hashtable<String, STS_ServerInfo> servers = null;

    private long delay = 600; //default to delay in 10 minutes
    private boolean exitLoop = false;

    private STS_DownloadServiceThread(DataSource ds) {
        this.dataSource = ds;
        this.startup();
    }

    @SuppressWarnings("unchecked")
	private void startup() {
        this.reset();

        try {
            String value =
                STS_Global.SYSTEM_CONFIG.getProperty(STS_Setting.DOWNLOAD_DELAY_KEY);
            this.delay = Integer.parseInt(value);
        } catch (Exception e) {
            logger.warn("Invalid '" + STS_Setting.DOWNLOAD_DELAY_KEY +
                           "' config in C_SYSTEM_CONFIGS_MLMN");
        }

        servers = STS_Setting.getListServerInfo();
        if (servers != null && servers.size() > 0) {
            threadPool = Executors.newFixedThreadPool(servers.size());
            logger.info("Download service start on: " + servers.size() +
                        " server");

            TaskExecutor task = null;
            STS_ServerInfo info = null;
            Enumeration<STS_ServerInfo> elements = servers.elements();
            while (elements.hasMoreElements()) {
                info = elements.nextElement();
                task = new TaskExecutor(info.getServerName(), info);
                executors.add(task);
                threadPool.submit(task);
            }
        }
    }

    public static synchronized void startService(DataSource ds) {
        if (singleton == null) {
            singleton = new STS_DownloadServiceThread(ds);
        } else {
            singleton.dataSource = ds;
            if (singleton.isStopped()) {
                singleton.startup();
            }
        }
    }

    public static synchronized void stopService() {
        if (singleton != null) {
            singleton.stop();
        }
    }

    protected synchronized void stop() {
        logger.info("Try to stop all DownloadService service");
        this.exitLoop = true;
        threadPool.shutdown();

        if (!executors.isEmpty()) {
            for (TaskExecutor executor : executors) {
                logger.info("Try to stop running dowload service: " +
                            executor.getName());
                executor.setStatus(STS_Setting.STOPING_STATUS);
                executor.stop();
                executor.setStatus(STS_Setting.STOPED_STATUS);
            }
            executors.clear();
        }

        if (!runningThreads.isEmpty()) {
            for (Thread th : runningThreads) {
                th.interrupt();
            }
            runningThreads.clear();
        }
    }

    public static synchronized void switchToNextService() {
    }

    protected synchronized void switchNext() {
    }

    public static synchronized List<STS_Attribute> getRunningServiceInfo() {
        List<STS_Attribute> serviceInfos = null;
        if (singleton != null) {
            serviceInfos = singleton.getServiceInfo();
        }
        return serviceInfos;
    }

    @SuppressWarnings("unchecked")
	protected synchronized List<STS_Attribute> getServiceInfo() {
        List<STS_Attribute> list = new ArrayList<STS_Attribute>();

        if (!this.executors.isEmpty()) {
            for (TaskExecutor executor : this.executors) {
                list = new ArrayList<STS_Attribute>();
                list.add(new STS_Attribute("Server name", executor.getName(), 0));
                list.add(new STS_Attribute("Status", executor.getStatus(), 1));
                list.add(new STS_Attribute("Start time",
                                       STS_Util.getTime(executor.getStartTime()),
                                       2));
                list.add(new STS_Attribute("Total files",
                                       String.valueOf(executor.getTotalTasks()),
                                       3));
                list.add(new STS_Attribute("Remain files",
                                       String.valueOf(executor.getRemainTasks()),
                                       4));
                list.add(new STS_Attribute("", "---------------^^^--------------",
                                       5));
                Collections.sort(list);
            }
        } else {
            if (this.exitLoop == true) {
                list.add(new STS_Attribute("Status", STS_Setting.STOPED_STATUS));
            }
        }
        return list;
    }

    private void reset() {
        this.delay = 0;
        this.exitLoop = false;
        this.executors.clear();
        this.runningThreads.clear();
    }

    public static synchronized boolean isStarted() {
        if (singleton != null && !singleton.isStopped()) {
            return true;
        }
        return false;
    }

    protected synchronized boolean isStopped() {
        return this.executors.isEmpty();
    }

    //-------------------------------------------------------------------------
    //
    //-------------------------------------------------------------------------

    @SuppressWarnings("rawtypes")
	private class TaskExecutor extends STS_DownloadService implements Callable {
        private String name = "";

        public TaskExecutor(String name, STS_ServerInfo serverInfo) {
            super("DownloadService", dataSource, serverInfo);
            this.name = "<" + this.getServiceName() + ":" + name + ">";
        }

        public Object call() {
            logger.info("Download on " + this.getName() + " started!");
            runningThreads.add(Thread.currentThread());

            while (!exitLoop) {
                try {
                    this.processTasks();
                } catch (Exception e) {
                    logger.warn(this.getName() + " run with error", e);
                }
                try {
                    logger.info(this.getName() + " service sleep for " +
                                delay + " seconds");
                    this.setStatus(STS_Setting.SLEEPING_STATUS);
                    Thread.sleep(delay * 1000);
                } catch (InterruptedException e) {
                    logger.warn("Download service interrupted");
                }
            }

            logger.info("Download on " + this.getName() + " stopped!");
            this.setStatus(STS_Setting.DONE_STATUS);

            return "Done";
        }

        public String getName() {
            return this.name;
        }
    }
}
