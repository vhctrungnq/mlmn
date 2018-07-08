package vn.com.vhc.alarm.core;

import java.io.IOException;
import java.lang.management.*;
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

import vn.com.vhc.alarm.cni.AL_ConvertService;
import vn.com.vhc.alarm.cni.AL_ImportDirectlyService;
import vn.com.vhc.alarm.dnu.AL_SelectionService;
import vn.com.vhc.alarm.util.AL_Attribute;
import vn.com.vhc.alarm.util.task.AL_TaskService;
import vn.com.vhc.alarm.utils.log.AL_DbAppender;

/**
 * 
 * @author Mr
 * @return Coreservice thread
 *
 */
@SuppressWarnings("rawtypes")
public class AL_CoreServiceThread implements Callable {

    private static Logger logger = Logger.getLogger(AL_CoreServiceThread.class.getName());
  //  private AL_DownloadServicesTelnet downloadServiceTelnet = null;
    private AL_SelectionService selectionService = null;
    private AL_ConvertService convertService = null;
    private AL_ImportDirectlyService importService = null;
    AL_CoreService al = null;
    private static AL_CoreServiceThread singleton = null;
    private DataSource dataSource = null;
    private AL_TaskService runningService = null;
    private Thread runningThread = null;
    private boolean exitLoop = false;
    private boolean stopped = true;
    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH-mm-ss";
    private String status = "";
    private long delay = 0;
    
	private static CallableStatement cs = null; 


    private AL_CoreServiceThread(DataSource ds, long delay) {
        this.dataSource = ds;
        this.delay = delay;
        
        //downloadServiceTelnet = new AL_DownloadServicesTelnet("AL_DownloadService",dataSource);
        selectionService  =  new AL_SelectionService("AL_SelectionService", dataSource);
        convertService = new AL_ConvertService("AL_ConvertService", dataSource);
        importService = new AL_ImportDirectlyService("JDBCImportService", dataSource);
        
    }

    @SuppressWarnings("unchecked")
	public static synchronized void startService(ExecutorService threadPool,
                                                 DataSource ds, long delay) {
        if (singleton == null) {
            singleton = new AL_CoreServiceThread(ds, delay);
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
// Call thread
    @SuppressWarnings("unused")
	public Object call() {
        stopped = false;
        logger.info("Core service started");
        runningThread = Thread.currentThread();
        Date selectionDate, convertDate, importDate, finishDate, downLoadStartTime;
        selectionDate = new Date(System.currentTimeMillis());
        convertDate = selectionDate;
        importDate = selectionDate;
        finishDate = selectionDate;

        int selectionCnt = 0;
        int convertCnt = 0;
        int importCnt = 0;

        int downloadCnt=0;

        AL_CoreDBLogParam logParam = null;
        AL_FileSweeper cleaner = null;
        String rawBaseDir = AL_Global.SYSTEM_CONFIG.getProperty("raw.base.dir");

        long rawBackupDay = 3; 
        try {
            rawBackupDay =
                    Long.parseLong(AL_Global.SYSTEM_CONFIG.getProperty("raw.backup.day"));
        } catch (Exception e) {
            logger.warn("Not found 'raw.backup.day' in C_SYSTEM_CONFIGS_MLMN");
        }

        if (rawBaseDir != null) {
            try {
                cleaner = new AL_FileSweeper(rawBaseDir, rawBackupDay);
            } catch (IOException e) {
                logger.warn("Init raw file cleaner error: " +
                               e.getMessage());
            }
        }

        while (!exitLoop) {
            try {
            	//Start download 
	       	   	/*logger.info("Process downloading ...");
            	switchService("Download", downloadServiceTelnet);
            	downloadServiceTelnet.processTasks();            	
                logger.info("DownloadService finished" + downloadServiceTelnet.getStartTime() );
                downloadCnt = downloadServiceTelnet.getDoneTasks();
                if (exitLoop) {
                    break;
                }*/
              //Start select 
                logger.info("Process selecting ...");
                switchService("SELECTING", selectionService);
                selectionService.processTasks();
                selectionDate = selectionService.getStartTime();
                selectionCnt = selectionService.getDoneTasks();
                logger.info("Selection finished");
                if (exitLoop) {
                    break;
                }
              //Start convert 
                logger.info("Process converting ...");
                switchService("CONVERTING", convertService);
                convertService.processTasks();
                convertDate = convertService.getStartTime();
                convertCnt = convertService.getDoneTasks();
                logger.info("Convert finished");
                if (exitLoop) {
                    break;
                }
              //Start import
                logger.info("Process importing ...");
                switchService("IMPORTING", importService);
                importService.processTasks();
                importDate = importService.getStartTime();
                importCnt = importService.getDoneTasks();
                logger.info("Import finished");
                
             //Call package sms
               summary_data();
                if (exitLoop) {
                    break;
                }
            } catch (Exception ex) {
                logger.warn("Core service fatal error: " + ex.getMessage(), ex);
            } finally {
                finishDate = new Date(System.currentTimeMillis());
                logParam =
                        new AL_CoreDBLogParam(selectionDate, selectionCnt, convertDate,
                                           convertCnt, importDate, importCnt,
                                           finishDate);
                logger.info("Finish one core cycle, write log to database");
                if(selectionCnt > 0)
                	AL_DbAppender.log(logParam);

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
                                            AL_TaskService service) {
        this.status = status;
        runningService = service;
        logger.info("Service status switched, new status = " + status +
                    ", running service = " + runningService);
    }
    //Get service info
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
	//Check thread isstopped
    protected synchronized boolean isStopped() {
        return stopped;
    }
    //Call package Sms
    public void summary_data(){ 
    	Connection conn = null;
         try {
             conn = this.dataSource.getConnection();
             cs = conn.prepareCall(
                     "{call PK_AL_CORE_MLMN.summary_data_alarm}"
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
