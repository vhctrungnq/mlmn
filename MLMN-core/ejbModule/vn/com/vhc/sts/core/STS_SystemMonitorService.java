package vn.com.vhc.sts.core;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.util.monitor.CPULoadChangeEvent;
import vn.com.vhc.sts.util.monitor.CPULoadChangeListener;
import vn.com.vhc.sts.util.monitor.DBTablespaceChangeEvent;
import vn.com.vhc.sts.util.monitor.DBTablespaceChangeListener;
import vn.com.vhc.sts.util.monitor.DatabaseStateChangeEvent;
import vn.com.vhc.sts.util.monitor.DatabaseStateChangeListener;
import vn.com.vhc.sts.util.monitor.FreeMemoryChangeEvent;
import vn.com.vhc.sts.util.monitor.FreeMemoryChangeListener;
import vn.com.vhc.sts.util.monitor.HDDFreespaceChangeEvent;
import vn.com.vhc.sts.util.monitor.HDDFreespaceChangeListener;
import vn.com.vhc.sts.util.monitor.SystemMonitor;



public class STS_SystemMonitorService implements CPULoadChangeListener,
                                             DBTablespaceChangeListener,
                                             DatabaseStateChangeListener,
                                             FreeMemoryChangeListener,
                                             HDDFreespaceChangeListener {
    private static Logger logger =
        Logger.getLogger(STS_SystemMonitorService.class.getName());
    private static boolean registerMonEvt = false;
    private static STS_SystemMonitorService singleton;

    public static synchronized void registerMonitorEvent() {
        if (!registerMonEvt && SystemMonitor.isCanRegisterListener()) {
            singleton = new STS_SystemMonitorService();

            SystemMonitor.addCPULoadChangeListener(singleton);
            SystemMonitor.addDBTablespaceChangeListener(singleton);
            SystemMonitor.addDatabaseStateChangeListener(singleton);
            SystemMonitor.addHDDFreespaceChangeListener(singleton);
            SystemMonitor.addMemoryLoadChangeListener(singleton);

            registerMonEvt = true;
            logger.info("System monitor service registered!");
        }
    }

    private STS_SystemMonitorService() {
        super();
    }

    public void cpuLoadChanged(CPULoadChangeEvent event) {
        logger.info("Current CPU Load: " + event.getLoad() + "%");
    }

    public void freeTablespaceChanged(DBTablespaceChangeEvent event) {
        logger.info("Current tablespace " + event.getTablespace() + " free: " +
                    event.getFree() + " MB");
    }

    public void databaseStateChanged(DatabaseStateChangeEvent event) {
        logger.info("Current database state: " +
                    (event.getErrorMessage() != null ?
                     event.getErrorMessage() : "OK"));
    }

    public void freeMemoryChanged(FreeMemoryChangeEvent event) {
        logger.info("Current memory infomation: max heap = " +
                    event.getTotal() + " MB, free heap = " + event.getFree() +
                    " MB");
    }

    public void freeHddChanged(HDDFreespaceChangeEvent event) {
        logger.info("Current mountpoint " + event.getDriver() + " free " +
                    event.getFree() + " MB");
    }
}
