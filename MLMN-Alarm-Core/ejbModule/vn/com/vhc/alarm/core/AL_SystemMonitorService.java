package vn.com.vhc.alarm.core;

import org.apache.log4j.Logger;

import vn.com.vhc.alarm.util.monitor.AL_CPULoadChangeEvent;
import vn.com.vhc.alarm.util.monitor.AL_CPULoadChangeListener;
import vn.com.vhc.alarm.util.monitor.AL_DBTablespaceChangeEvent;
import vn.com.vhc.alarm.util.monitor.AL_DBTablespaceChangeListener;
import vn.com.vhc.alarm.util.monitor.AL_DatabaseStateChangeEvent;
import vn.com.vhc.alarm.util.monitor.AL_DatabaseStateChangeListener;
import vn.com.vhc.alarm.util.monitor.AL_FreeMemoryChangeEvent;
import vn.com.vhc.alarm.util.monitor.AL_FreeMemoryChangeListener;
import vn.com.vhc.alarm.util.monitor.AL_HDDFreespaceChangeEvent;
import vn.com.vhc.alarm.util.monitor.AL_HDDFreespaceChangeListener;
import vn.com.vhc.alarm.util.monitor.AL_SystemMonitor;



public class AL_SystemMonitorService implements AL_CPULoadChangeListener,
                                             AL_DBTablespaceChangeListener,
                                             AL_DatabaseStateChangeListener,
                                             AL_FreeMemoryChangeListener,
                                             AL_HDDFreespaceChangeListener {
    private static Logger logger =
        Logger.getLogger(AL_SystemMonitorService.class.getName());
    private static boolean registerMonEvt = false;
    private static AL_SystemMonitorService singleton;

    public static synchronized void registerMonitorEvent() {
        if (!registerMonEvt && AL_SystemMonitor.isCanRegisterListener()) {
            singleton = new AL_SystemMonitorService();

            AL_SystemMonitor.addCPULoadChangeListener(singleton);
            AL_SystemMonitor.addDBTablespaceChangeListener(singleton);
            AL_SystemMonitor.addDatabaseStateChangeListener(singleton);
            AL_SystemMonitor.addHDDFreespaceChangeListener(singleton);
            AL_SystemMonitor.addMemoryLoadChangeListener(singleton);

            registerMonEvt = true;
            logger.info("System monitor service registered!");
        }
    }

    private AL_SystemMonitorService() {
        super();
    }

    public void cpuLoadChanged(AL_CPULoadChangeEvent event) {
        logger.info("Current CPU Load: " + event.getLoad() + "%");
    }

    public void freeTablespaceChanged(AL_DBTablespaceChangeEvent event) {
        logger.info("Current tablespace " + event.getTablespace() + " free: " +
                    event.getFree() + " MB");
    }

    public void databaseStateChanged(AL_DatabaseStateChangeEvent event) {
        logger.info("Current database state: " +
                    (event.getErrorMessage() != null ?
                     event.getErrorMessage() : "OK"));
    }

    public void freeMemoryChanged(AL_FreeMemoryChangeEvent event) {
        logger.info("Current memory infomation: max heap = " +
                    event.getTotal() + " MB, free heap = " + event.getFree() +
                    " MB");
    }

    public void freeHddChanged(AL_HDDFreespaceChangeEvent event) {
        logger.info("Current mountpoint " + event.getDriver() + " free " +
                    event.getFree() + " MB");
    }
}
