package vn.com.vhc.alarm.cni.download;

import java.text.SimpleDateFormat;

import java.util.ArrayList; 
import java.util.Collections; 
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import vn.com.vhc.alarm.cni.download.AL_DownloadServicesTelnet;  
import vn.com.vhc.alarm.util.AL_Attribute;
import vn.com.vhc.alarm.util.task.AL_TaskService;

@SuppressWarnings("rawtypes")
public class AL_DownloadServicesThread implements Callable {

    private static Logger logger =
        Logger.getLogger(AL_DownloadServicesThread.class.getName());
    private AL_DownloadServicesTelnet downloadServiceTelnet = null;   
    private static AL_DownloadServicesThread singleton = null;
    private DataSource dataSource = null;
    private AL_TaskService runningService = null;
    //private Thread runningThread = null; 
    private List<Thread> runningThreads =
            Collections.synchronizedList(new ArrayList<Thread>());
    private boolean exitLoop = false;
    private boolean stopped = true;
    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH-mm-ss";
    private String status = "";
    private long delay = 0;


    private AL_DownloadServicesThread(DataSource ds, long delay) {
        this.dataSource = ds;
        this.delay = delay;
        
        downloadServiceTelnet = new AL_DownloadServicesTelnet("AL_DownloadService",dataSource); 
    }

    @SuppressWarnings("unchecked")
	public static synchronized void startService(ExecutorService threadPool,
                                                 DataSource ds, long delay) {
        if (singleton == null) {
            singleton = new AL_DownloadServicesThread(ds, delay);
        } else {
            singleton.delay = delay;
            singleton.dataSource = ds;
        }

        if (singleton.isStopped()) {
            singleton.exitLoop = false;
            threadPool.submit(singleton);
        }
    }

    public static synchronized void stopService() {
        if (singleton != null) {
            singleton.stop();
        }
    }

    public static synchronized void switchToNextService() {
        if (singleton != null && !singleton.isStopped()) {
            singleton.switchNext();
        }
    }

    public static synchronized List<AL_Attribute> getRunningServiceInfo() {
        List<AL_Attribute> serviceInfo = null;
        if (singleton != null) {
            serviceInfo = singleton.getServiceInfo();
        }
        return serviceInfo;
    }

    public static synchronized boolean isStarted() {
        if (singleton != null && !singleton.isStopped()) {
            return true;
        }

        return false;
    }

    @SuppressWarnings("unused")
	public Object call() {
        stopped = false;
        logger.info("Download service started"); 

        int downloadCnt=0;

        while (!exitLoop) {
            try {

	       	    logger.info("Process downloading ...");
            	switchService("Download", downloadServiceTelnet);
            	downloadServiceTelnet.processTasks();            	
                logger.info("DownloadService finished" + downloadServiceTelnet.getStartTime() );
                downloadCnt = downloadServiceTelnet.getDoneTasks();
                //Must be check to control stop service properly
                if (exitLoop) {
                    break;
                } 
            } catch (Exception ex) {
                logger.warn("Download service fatal error: " + ex.getMessage(), ex);
            }

            switchService("IDLE", null);
            try {
                logger.info("Download service sleep for " + delay + " seconds");
                Thread.sleep(delay * 1000);
            } catch (InterruptedException e) {
                logger.warn("Download service interrupted");
            }
        }

        switchService("STOPPED", null);
        logger.info("Download service stopped!!!");

        if (!runningThreads.isEmpty()) {
            for (Thread th : runningThreads) {
                th.interrupt();
            }
            runningThreads.clear(); 
        }
        stopped = true;
        return null;
    }

    protected synchronized void stop() {
        logger.info("Try to stop Download service");
        status = "STOPPING";
        this.exitLoop = true;
        singleton = null;
        if (runningService != null) {
            logger.info("Try to stop running service");
            runningService.stop();
        }

        if (!runningThreads.isEmpty()) {
            for (Thread th : runningThreads) {
                th.interrupt();
            }
            runningThreads.clear();
        }
    }

    protected synchronized void switchNext() {
        if (runningService != null) {
            runningService.stop();
        }
    }

    private synchronized void switchService(String status,
                                            AL_TaskService service) {
        this.status = status;
        runningService = service;
        logger.info("Service status switched, new status = " + status +
                    ", running service = " + runningService);
    }

    @SuppressWarnings("unchecked")
	protected synchronized List<AL_Attribute> getServiceInfo() {
        List<AL_Attribute> list = new ArrayList<AL_Attribute>();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        list.add(new AL_Attribute("Status: ", status, 0));
        if (runningService != null) {
            list.add(new AL_Attribute("Start time: ",
                                   format.format(runningService.getStartTime()),
                                   1));
            list.add(new AL_Attribute("Total files: ",
                                   String.valueOf(runningService.getTotalTasks()),
                                   2));
            list.add(new AL_Attribute("Remain files: ",
                                   String.valueOf(runningService.getRemainTasks()),
                                   3));
            list.add(new AL_Attribute("Current file: ",
                                   runningService.getWorkingTaskInfos(), 4));
            list.add(new AL_Attribute("", "---------------^^^--------------", 5));
        }
        Collections.sort(list);

        return list;
    }

    protected synchronized boolean isStopped() {
        return stopped;
    }
}
