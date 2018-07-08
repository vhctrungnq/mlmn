package inventory.core;

import inventory.cni.IN_ConvertService;
import inventory.cni.IN_ImportDirectlyService;
import inventory.dnu.IN_SelectionService;
import inventory.download.IN_DownloadServicesTelnet;
import inventory.util.IN_Attribute;
import inventory.util.task.IN_TaskService;
import inventory.utils.log.IN_DbAppender;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

@SuppressWarnings("rawtypes")
public class IN_CoreServiceThread implements Callable {

    private static Logger logger =
        Logger.getLogger(IN_CoreServiceThread.class.getName());
    
    private IN_DownloadServicesTelnet downloadService = null;
    private IN_SelectionService selectionService = null;
    private IN_ConvertService convertService = null;
    private IN_ImportDirectlyService importService = null;

    private static IN_CoreServiceThread singleton = null;
    private DataSource dataSource = null;
    private IN_TaskService runningService = null;
    private Thread runningThread = null;

    private boolean exitLoop = false;
    private boolean stopped = true;

    private String status = "UNKNOWN";
    private long delay = 0;
    
    private static CallableStatement cs = null;

    private IN_CoreServiceThread(DataSource ds, long delay) {
        this.dataSource = ds;
        this.delay = delay;
        
        downloadService = new IN_DownloadServicesTelnet("IN_DownloadService",dataSource);
        selectionService = new IN_SelectionService("IN_SelectionService", dataSource);
        convertService = new IN_ConvertService("IN_ConvertService", dataSource);
        importService = new IN_ImportDirectlyService("JDBCImportService", dataSource);
    }

    @SuppressWarnings("unchecked")
	public static synchronized void startService(ExecutorService threadPool,
                                                 DataSource ds, long delay) {
        if (singleton == null) {
            singleton = new IN_CoreServiceThread(ds, delay);
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

    public static synchronized List<IN_Attribute> getRunningServiceInfo() {
        List<IN_Attribute> serviceInfo = null;
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
        logger.info("Core service started");

        runningThread = Thread.currentThread();
        Date downloadDate,selectionDate, convertDate, importDate, finishDate;
        downloadDate =  new Date(System.currentTimeMillis()); 
        selectionDate = downloadDate;
        convertDate = downloadDate;
        importDate = downloadDate;
        finishDate = downloadDate;
        
        int downloadCnt = 0;
        int selectionCnt = 0;
        int convertCnt = 0;
        int importCnt = 0;

        IN_CoreDBLogParam logParam = null;
        IN_FileSweeper cleaner = null;
        String rawBaseDir = IN_Global.SYSTEM_CONFIG.getProperty("inventory.raw.base.dir");

        long rawBackupDay = 3; //Default value
        try {
            rawBackupDay =
                    Long.parseLong(IN_Global.SYSTEM_CONFIG.getProperty("inventory.raw.backup.day"));
        } catch (Exception e) {
            logger.warn("Not found 'inventory.raw.backup.day' in C_SYSTEM_CONFIGS_MLMN");
        }

        if (rawBaseDir != null) {
            try {
                cleaner = new IN_FileSweeper(rawBaseDir, rawBackupDay);
            } catch (IOException e) {
                logger.warn("Init raw file cleaner error: " +
                               e.getMessage());
            }
        }

        while (!exitLoop) {
            try {
            	logger.info("Process downloading ...");
                switchService("DOWNLOAD", downloadService);
                downloadService.processTasks();
                downloadDate = downloadService.getStartTime();
                downloadCnt = downloadService.getDoneTasks();
                logger.info("Download finished");
                //Must be check to control stop service properly
                if (exitLoop) {
                    break;
                }
                
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
                
              //Call package
                this.summary_data();
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
                        new IN_CoreDBLogParam(selectionDate, selectionCnt, convertDate,
                                           convertCnt, importDate, importCnt,
                                           finishDate);
                logger.info("Finish one core cycle, write log to database");
                if(selectionCnt > 0)
                	IN_DbAppender.log(logParam);

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
                                            IN_TaskService service) {
        this.status = status;
        runningService = service;
        logger.info("Service status switched, new status = " + status +
                    ", running service = " + runningService);
    }

    @SuppressWarnings("unchecked")
	protected synchronized List<IN_Attribute> getServiceInfo() {
        List<IN_Attribute> list = new ArrayList<IN_Attribute>();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        list.add(new IN_Attribute("Status: ", status, 0));
        if (runningService != null) {
            list.add(new IN_Attribute("Start time: ",
                                   format.format(runningService.getStartTime()),
                                   1));
            list.add(new IN_Attribute("Total files: ",
                                   String.valueOf(runningService.getTotalTasks()),
                                   2));
            list.add(new IN_Attribute("Remain files: ",
                                   String.valueOf(runningService.getRemainTasks()),
                                   3));
            list.add(new IN_Attribute("Current file: ",
                                   runningService.getWorkingTaskInfos(), 4));
            list.add(new IN_Attribute("", "---------------^^^--------------", 5));
        }
        Collections.sort(list);

        return list;
    }

    protected synchronized boolean isStopped() {
        return stopped;
    }
    
  //Call package tong hop du lieu
    private void summary_data(){ 
    	Connection conn = null;
         try {
             conn = this.dataSource.getConnection();
             cs = conn.prepareCall(
                     "{call pk_alarm_syn.pr_syn_nsn}"
                 );
             	cs.executeQuery();
                cs.close(); 
             
         } catch (SQLException e) {
             logger.warn("File list to import is empty", e);
         } finally {
             if (conn != null) {
                 try {
                     conn.close();
                 } catch (SQLException sqlEx) {
                     logger.warn("Cannot close the connection to database",
                                sqlEx);
                 }
             }
         }   
    }
}
