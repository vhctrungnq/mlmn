package inventory.core;

import inventory.util.monitor.IN_CPULoadChangeEvent;
import inventory.util.monitor.IN_CPULoadChangeListener;
import inventory.util.monitor.IN_DBTablespaceChangeEvent;
import inventory.util.monitor.IN_DBTablespaceChangeListener;
import inventory.util.monitor.IN_DatabaseStateChangeEvent;
import inventory.util.monitor.IN_DatabaseStateChangeListener;
import inventory.util.monitor.IN_FreeMemoryChangeEvent;
import inventory.util.monitor.IN_FreeMemoryChangeListener;
import inventory.util.monitor.IN_HDDFreespaceChangeEvent;
import inventory.util.monitor.IN_HDDFreespaceChangeListener;
import inventory.util.monitor.IN_SystemMonitor;

import org.apache.log4j.Logger;




public class IN_SystemMonitorService implements IN_CPULoadChangeListener,
                                             IN_DBTablespaceChangeListener,
                                             IN_DatabaseStateChangeListener,
                                             IN_FreeMemoryChangeListener,
                                             IN_HDDFreespaceChangeListener {
    private static Logger logger =
        Logger.getLogger(IN_SystemMonitorService.class.getName());
    private static boolean registerMonEvt = false;
    private static IN_SystemMonitorService singleton;

    public static synchronized void registerMonitorEvent() {
        if (!registerMonEvt && IN_SystemMonitor.isCanRegisterListener()) {
            singleton = new IN_SystemMonitorService();

            IN_SystemMonitor.addCPULoadChangeListener(singleton);
            IN_SystemMonitor.addDBTablespaceChangeListener(singleton);
            IN_SystemMonitor.addDatabaseStateChangeListener(singleton);
            IN_SystemMonitor.addHDDFreespaceChangeListener(singleton);
            IN_SystemMonitor.addMemoryLoadChangeListener(singleton);

            registerMonEvt = true;
            logger.info("System monitor service registered!");
        }
    }

    private IN_SystemMonitorService() {
        super();
    }

    public void cpuLoadChanged(IN_CPULoadChangeEvent event) {
        logger.info("Current CPU Load: " + event.getLoad() + "%");
    }

    public void freeTablespaceChanged(IN_DBTablespaceChangeEvent event) {
        logger.info("Current tablespace " + event.getTablespace() + " free: " +
                    event.getFree() + " MB");
    }

    public void databaseStateChanged(IN_DatabaseStateChangeEvent event) {
        logger.info("Current database state: " +
                    (event.getErrorMessage() != null ?
                     event.getErrorMessage() : "OK"));
    }

    public void freeMemoryChanged(IN_FreeMemoryChangeEvent event) {
        logger.info("Current memory infomation: max heap = " +
                    event.getTotal() + " MB, free heap = " + event.getFree() +
                    " MB");
    }

    public void freeHddChanged(IN_HDDFreespaceChangeEvent event) {
        logger.info("Current mountpoint " + event.getDriver() + " free " +
                    event.getFree() + " MB");
    }
}
