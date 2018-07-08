package vn.com.vhc.sts.core;

import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.cni.STS_ConvertService;
import vn.com.vhc.sts.cni.STS_ImportDirectlyService;
import vn.com.vhc.sts.dnu.STS_SelectionService;
import vn.com.vhc.sts.util.STS_Attribute;
import vn.com.vhc.sts.util.task.STS_TaskService;
import vn.com.vhc.sts.utils.log.STS_DbAppender;



@SuppressWarnings("rawtypes")
public class STS_CoreServiceThread implements Callable {

    private static Logger logger =
        Logger.getLogger(STS_CoreServiceThread.class.getName());

    private STS_SelectionService selectionService = null;
    private STS_ConvertService convertService = null;
    private STS_ImportDirectlyService importService = null;

    private static STS_CoreServiceThread singleton = null;
    private DataSource dataSource = null;
    private STS_TaskService runningService = null;
    private Thread runningThread = null;

    private boolean exitLoop = false;
    private boolean stopped = true;

    private String status = "UNKNOWN";
    private long delay = 0;


    private STS_CoreServiceThread(DataSource ds, long delay) {
        this.dataSource = ds;
        this.delay = delay;

        selectionService =
                new STS_SelectionService("SelectionService", dataSource);
        convertService = new STS_ConvertService("ConvertService", dataSource);
        importService =
                new STS_ImportDirectlyService("JDBCImportService", dataSource);
    }

    @SuppressWarnings("unchecked")
	public static synchronized void startService(ExecutorService threadPool,
                                                 DataSource ds, long delay) {
        if (singleton == null) {
            singleton = new STS_CoreServiceThread(ds, delay);
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

    public static synchronized List<STS_Attribute> getRunningServiceInfo() {
        List<STS_Attribute> serviceInfo = null;
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

    public Object call() {
        stopped = false;
        logger.info("Core service started");

        runningThread = Thread.currentThread();
        Date selectionDate, convertDate, importDate, finishDate;
        selectionDate = new Date(System.currentTimeMillis());
        convertDate = selectionDate;
        importDate = selectionDate;
        finishDate = selectionDate;

        int selectionCnt = 0;
        int convertCnt = 0;
        int importCnt = 0;

        STS_CoreDBLogParam logParam = null;
        STS_FileSweeper cleaner = null;
        String rawBaseDir = STS_Global.SYSTEM_CONFIG.getProperty("raw.base.dir");

        long rawBackupDay = 3; //Default value
        try {
            rawBackupDay =
                    Long.parseLong(STS_Global.SYSTEM_CONFIG.getProperty("raw.backup.day"));
        } catch (Exception e) {
            logger.warn("Not found 'raw.backup.day' in C_SYSTEM_CONFIGS_MLMN");
        }

        if (rawBaseDir != null) {
            try {
                cleaner = new STS_FileSweeper(rawBaseDir, rawBackupDay);
            } catch (IOException e) {
                logger.warn("Init raw file cleaner error: " +
                               e.getMessage());
            }
        }

        while (!exitLoop) {
            try {
                logger.info("Process selecting ...");
                switchService("SELECTING", selectionService);
                selectionService.processTasks();
                selectionDate = selectionService.getStartTime();
                selectionCnt = selectionService.getDoneTasks();
                logger.info("Selection finished");
                //Must be check to control stop service properly
                if (exitLoop) {
                    break;
                }

                logger.info("Process converting ...");
                switchService("CONVERTING", convertService);
                convertService.processTasks();
                convertDate = convertService.getStartTime();
                convertCnt = convertService.getDoneTasks();
                logger.info("Convert finished");
                //Must be check to control stop service properly
                if (exitLoop) {
                    break;
                }

                logger.info("Process importing ...");
                switchService("IMPORTING", importService);
                importService.processTasks();
                importDate = importService.getStartTime();
                importCnt = importService.getDoneTasks();
                logger.info("Import finished");
                //Must be check to control stop service properly
                if (exitLoop) {
                    break;
                }
/*
                if (cleaner != null) {
                    switchService("FILE CLEANING", null);
                    logger.info("Clear raw files with modify time < current - " +
                                rawBackupDay + " day");
                    cleaner.doSweep();
                }*/
            } catch (Exception ex) {
                logger.warn("Core service fatal error: " + ex.getMessage(), ex);
            } finally {
                finishDate = new Date(System.currentTimeMillis());
                logParam =
                        new STS_CoreDBLogParam(selectionDate, selectionCnt, convertDate,
                                           convertCnt, importDate, importCnt,
                                           finishDate);
                logger.info("Finish one core cycle, write log to database");
                if(selectionCnt > 0)
                	STS_DbAppender.log(logParam);

                //Reset variables
                selectionCnt = 0;
                convertCnt = 0;
                importCnt = 0;
                selectionDate = finishDate;
                convertDate = finishDate;
                importDate = finishDate;
            }

            switchService("IDLE", null);
            try {
                logger.info("Core service sleep for " + delay + " seconds");
                Thread.sleep(delay * 1000);
            } catch (InterruptedException e) {
                logger.warn("Core service interrupted");
            }
        }

        switchService("STOPPED", null);
        logger.info("Core service stopped!!!");

        runningThread = null;
        stopped = true;
        return null;
    }

    protected synchronized void stop() {
        logger.info("Try to stop Core service");
        status = "STOPPING";
        this.exitLoop = true;

        if (runningService != null) {
            logger.info("Try to stop running service");
            runningService.stop();
        }

        if (runningThread != null) {
            logger.info("Try to interrupt Core service's running thread");
            runningThread.interrupt();
        }
    }

    protected synchronized void switchNext() {
        if (runningService != null) {
            runningService.stop();
        }
    }

    private synchronized void switchService(String status,
                                            STS_TaskService service) {
        this.status = status;
        runningService = service;
        logger.info("Service status switched, new status = " + status +
                    ", running service = " + runningService);
    }

    @SuppressWarnings("unchecked")
	protected synchronized List<STS_Attribute> getServiceInfo() {
        List<STS_Attribute> list = new ArrayList<STS_Attribute>();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        list.add(new STS_Attribute("Status: ", status, 0));
        if (runningService != null) {
            list.add(new STS_Attribute("Start time: ",
                                   format.format(runningService.getStartTime()),
                                   1));
            list.add(new STS_Attribute("Total files: ",
                                   String.valueOf(runningService.getTotalTasks()),
                                   2));
            list.add(new STS_Attribute("Remain files: ",
                                   String.valueOf(runningService.getRemainTasks()),
                                   3));
            list.add(new STS_Attribute("Current file: ",
                                   runningService.getWorkingTaskInfos(), 4));
            list.add(new STS_Attribute("", "---------------^^^--------------", 5));
        }
        Collections.sort(list);

        return list;
    }

    protected synchronized boolean isStopped() {
        return stopped;
    }
}
