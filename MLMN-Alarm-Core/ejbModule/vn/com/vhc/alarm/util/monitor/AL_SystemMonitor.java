package vn.com.vhc.alarm.util.monitor;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import javax.sql.DataSource;

import org.apache.log4j.Logger;


@SuppressWarnings("rawtypes")
public class AL_SystemMonitor implements Callable {
    public static final int CHECK_CPU = 1;
    public static final int CHECK_MEMORY = 2;
    public static final int CHECK_HDD = 3;
    public static final int CHECK_DATABASE = 4;
    public static final int CHECK_TABLESPACE = 5;

    private static Logger logger =
        Logger.getLogger(AL_SystemMonitor.class.getName());
    private static AL_SystemMonitor singleton;

    private transient ArrayList hddChangeListeners = new ArrayList(2);
    private transient ArrayList cpuChangeListeners = new ArrayList(2);
    private transient ArrayList memoryChangeListeners = new ArrayList(2);
    private transient ArrayList dbStateChangeListeners = new ArrayList(2);
    private transient ArrayList dBTablespaceListeners = new ArrayList(2);
    private AL_SystemChecker checker;
    private boolean stop = true;
    private boolean exitLoop = false;
    private Thread runningThread;

    private int delay = 60;
    private DataSource dataSource;
    private String checkStatement;
    private String[] mountPoints;

    private boolean checkCPU = true;
    private boolean checkMemory = true;
    private boolean checkDatabase = false;
    private boolean checkTablespace = false;
    private boolean checkHdd = false;


    public static void main(String[] args) {
        //SystemMonitorService systemMonitorService = new SystemMonitorService();
    }

    @SuppressWarnings("unchecked")
	public static synchronized void startMonitor(ExecutorService threadPool,
                                                 int delay, DataSource ds,
                                                 String checkStatement,
                                                 String... mountPoint) {
        if (singleton == null) {
            singleton =
                    new AL_SystemMonitor(delay, ds, checkStatement, mountPoint);
        } else {
            singleton.delay = delay;
            singleton.dataSource = ds;
            singleton.checkStatement = checkStatement;
            singleton.mountPoints = mountPoint;
        }

        if (singleton.isStop()) {
            singleton.exitLoop = false;
            threadPool.submit(singleton);
        }
    }

    public static synchronized void stopMonitor() {
        if (singleton != null) {
            singleton.stop();
        }
    }

    public static synchronized boolean isCanRegisterListener() {
        if (singleton != null) {
            return true;
        }

        return false;
    }

    public synchronized void setCheckType(int type, boolean check) {
        if (singleton != null) {
            switch (type) {
            case CHECK_CPU:
                singleton.checkCPU = check;
                break;
            case CHECK_MEMORY:
                singleton.checkMemory = check;
                break;
            case CHECK_DATABASE:
                singleton.checkDatabase = check;
                break;
            case CHECK_TABLESPACE:
                singleton.checkTablespace = check;
                break;
            case CHECK_HDD:
                singleton.checkHdd = check;
            }
        }
    }

    public static synchronized void addCPULoadChangeListener(AL_CPULoadChangeListener listener) {
        if (singleton != null) {
            singleton.addCPULoadListener(listener);
        }
    }

    public static synchronized void removeCPULoadChangeListener(AL_CPULoadChangeListener listener) {
        if (singleton != null) {
            singleton.removeCPULoadListener(listener);
        }
    }

    public static synchronized void addMemoryLoadChangeListener(AL_FreeMemoryChangeListener listener) {
        if (singleton != null) {
            singleton.addMemoryLoadListener(listener);
        }
    }

    public static synchronized void removeMemoryLoadChangeListener(AL_FreeMemoryChangeListener listener) {
        if (singleton != null) {
            singleton.removeMemoryLoadListener(listener);
        }
    }

    public static synchronized void addDatabaseStateChangeListener(AL_DatabaseStateChangeListener listener) {
        if (singleton != null) {
            singleton.addDatabaseStateListener(listener);
        }
    }

    public static synchronized void removeDatabaseStateChangeListener(AL_DatabaseStateChangeListener listener) {
        if (singleton != null) {
            singleton.removeDatabaseStateListener(listener);
        }
    }

    public static synchronized void addHDDFreespaceChangeListener(AL_HDDFreespaceChangeListener listener) {
        if (singleton != null) {
            singleton.addHDDFreespaceListener(listener);
        }
    }

    public static synchronized void removeHDDFreespaceChangeListener(AL_HDDFreespaceChangeListener listener) {
        if (singleton != null) {
            singleton.removeHDDFreespaceListener(listener);
        }
    }

    public static synchronized void addDBTablespaceChangeListener(AL_DBTablespaceChangeListener listener) {
        if (singleton != null) {
            singleton.addDBTablespaceListener(listener);
        }
    }

    public static synchronized void removeDBTablespaceChangeListener(AL_DBTablespaceChangeListener listener) {
        if (singleton != null) {
            singleton.removeDBTablespaceListener(listener);
        }
    }

    private AL_SystemMonitor(int delay, DataSource ds, String checkStatement,
                          String... mountPoint) {
        this.delay = delay;

        if (ds != null) {
            this.dataSource = ds;
            checkTablespace = true;
        }

        if (ds != null && checkStatement != null &&
            checkStatement.trim().length() > 0) {
            this.checkStatement = checkStatement;
            checkDatabase = true;
        }

        if (mountPoint != null && mountPoint.length > 0) {
            this.mountPoints = mountPoint;
            checkHdd = true;
        }

        checker = new AL_SystemChecker();
    }

    public Object call() {
        stop = false;
        runningThread = Thread.currentThread();

        logger.info("System monitor service started");

        while (!exitLoop) {
            doSystemCheck();

            try {
                Thread.sleep(delay * 1000);
            } catch (InterruptedException e) {
                logger.warn("System monitor service interupted");
            }
        }
        logger.info("System monitor service stopped");
        runningThread = null;
        stop = true;
        return null;
    }

    private synchronized void stop() {
        exitLoop = true;
        if (runningThread != null) {
            runningThread.interrupt();
        }
    }

    @SuppressWarnings("unchecked")
	private synchronized void addCPULoadListener(AL_CPULoadChangeListener listener) {
        if (cpuChangeListeners == null) {
            cpuChangeListeners = new ArrayList(2);
        }
        cpuChangeListeners.add(listener);
    }

    private synchronized void removeCPULoadListener(AL_CPULoadChangeListener listener) {
        if (cpuChangeListeners != null) {
            cpuChangeListeners.remove(listener);
        }
    }

    @SuppressWarnings("unchecked")
	private synchronized void addMemoryLoadListener(AL_FreeMemoryChangeListener listener) {
        if (memoryChangeListeners == null) {
            memoryChangeListeners = new ArrayList(2);
        }
        memoryChangeListeners.add(listener);
    }

    private synchronized void removeMemoryLoadListener(AL_FreeMemoryChangeListener listener) {
        if (memoryChangeListeners != null) {
            memoryChangeListeners.remove(listener);
        }
    }

    @SuppressWarnings("unchecked")
	private synchronized void addDatabaseStateListener(AL_DatabaseStateChangeListener listener) {
        if (dbStateChangeListeners == null) {
            dbStateChangeListeners = new ArrayList(2);
        }
        dbStateChangeListeners.add(listener);
    }

    private synchronized void removeDatabaseStateListener(AL_DatabaseStateChangeListener listener) {
        if (dbStateChangeListeners != null) {
            dbStateChangeListeners.remove(listener);
        }
    }

    @SuppressWarnings("unchecked")
	private synchronized void addHDDFreespaceListener(AL_HDDFreespaceChangeListener listener) {
        if (hddChangeListeners == null) {
            hddChangeListeners = new ArrayList(2);
        }
        hddChangeListeners.add(listener);
    }

    private synchronized void removeHDDFreespaceListener(AL_HDDFreespaceChangeListener listener) {
        if (hddChangeListeners != null) {
            hddChangeListeners.remove(listener);
        }
    }

    @SuppressWarnings("unchecked")
	private synchronized void addDBTablespaceListener(AL_DBTablespaceChangeListener listener) {
        if (dBTablespaceListeners == null) {
            dBTablespaceListeners = new ArrayList(2);
        }
        dBTablespaceListeners.add(listener);
    }

    private synchronized void removeDBTablespaceListener(AL_DBTablespaceChangeListener listener) {
        if (dBTablespaceListeners != null) {
            dBTablespaceListeners.remove(listener);
        }
    }

    private void doSystemCheck() {
        String result = null;
        Properties props = null;
        Enumeration enums = null;
        String key = null;

        //Check database state
        if (checkDatabase) {
            try {
                result =
                        checker.checkDatabaseState(dataSource, checkStatement);
                AL_DatabaseStateChangeEvent event =
                    new AL_DatabaseStateChangeEvent(this);
                if (result.equalsIgnoreCase("OK")) {
                    event.setState(AL_DatabaseStateChangeEvent.NORMAL_STATE);
                } else {
                    event.setState(AL_DatabaseStateChangeEvent.ERROR_STATE);
                }

                fireDatabaseStateChanged(event);
            } catch (Exception e) {
                logger.warn("Check database state failure: ", e);
            }
        }

        //Check free tablespace
        if (checkTablespace) {
            AL_DBTablespaceChangeEvent dbEvent = null;
            try {
                props = checker.checkDatabaseTableSpace(dataSource);

                enums = props.propertyNames();
                while (enums.hasMoreElements()) {
                    key = (String)enums.nextElement();
                    dbEvent =
                            new AL_DBTablespaceChangeEvent(this, key, (Integer)props.get(key));
                    fireDBTablespaceChanged(dbEvent);
                }

            } catch (Exception e) {
                logger.warn("Check free tablespace failure: ",
                           e);
            }
        }

        //Check HDD free space
        if (checkHdd) {
            AL_HDDFreespaceChangeEvent hddEvent = null;
            try {
                props = checker.checkHDDFreeSpace(mountPoints);

                enums = props.propertyNames();
                while (enums.hasMoreElements()) {
                    key = (String)enums.nextElement();
                    hddEvent =
                            new AL_HDDFreespaceChangeEvent(this, key, (Integer)props.get(key));
                    fireHDDFreespaceChanged(hddEvent);
                }

            } catch (Exception e) {
                logger.warn("Check hdd freespace failure: ", e);
            }
        }
        //Check memory
        if (checkMemory) {
            try {
                int[] memCheck = checker.checkFreeMemory();
                fireFreeMemoryChanged(new AL_FreeMemoryChangeEvent(this,
                                                                memCheck[0],
                                                                memCheck[1]));
            } catch (Exception e) {
                logger.warn("Check free memory failure: ", e);
            }
        }

        //Check CPU load
        if (checkCPU) {
            try {
                double cpuLoad = checker.checkCPULoad();
                fireCPULoadChanged(new AL_CPULoadChangeEvent(this, cpuLoad));
            } catch (Exception e) {
                logger.warn("Check CPU load failure: ", e);
            }
        }
    }

    private void fireDatabaseStateChanged(AL_DatabaseStateChangeEvent event) {
        java.util.List list;
        synchronized (this) {
            if (dbStateChangeListeners == null) {
                return;
            }
            list = (java.util.List)dbStateChangeListeners.clone();
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ((AL_DatabaseStateChangeListener)list.get(i)).databaseStateChanged(event);
        }
    }

    private void fireFreeMemoryChanged(AL_FreeMemoryChangeEvent event) {
        java.util.List list;
        synchronized (this) {
            if (memoryChangeListeners == null) {
                return;
            }
            list = (java.util.List)memoryChangeListeners.clone();
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ((AL_FreeMemoryChangeListener)list.get(i)).freeMemoryChanged(event);
        }
    }

    private void fireCPULoadChanged(AL_CPULoadChangeEvent event) {
        java.util.List list;
        synchronized (this) {
            if (cpuChangeListeners == null) {
                return;
            }
            list = (java.util.List)cpuChangeListeners.clone();
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ((AL_CPULoadChangeListener)list.get(i)).cpuLoadChanged(event);
        }
    }

    private void fireHDDFreespaceChanged(AL_HDDFreespaceChangeEvent event) {
        java.util.List list;
        synchronized (this) {
            if (hddChangeListeners == null) {
                return;
            }
            list = (java.util.List)hddChangeListeners.clone();
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ((AL_HDDFreespaceChangeListener)list.get(i)).freeHddChanged(event);
        }
    }

    private void fireDBTablespaceChanged(AL_DBTablespaceChangeEvent event) {
        java.util.List list;
        synchronized (this) {
            if (dBTablespaceListeners == null) {
                return;
            }
            list = (java.util.List)dBTablespaceListeners.clone();
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ((AL_DBTablespaceChangeListener)list.get(i)).freeTablespaceChanged(event);
        }
    }

    public boolean isStop() {
        return stop;
    }
}
