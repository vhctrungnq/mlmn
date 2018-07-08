package vn.com.vhc.sts.util.task;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.core.STS_Global;


/**
 * All service use thread pool should extends this class
 * @author thangq
 * Edit by TRUNGNQ add connection use for convert nokia3g
 */
public abstract class STS_TaskService {
    private static Logger logger =
        Logger.getLogger(STS_TaskService.class.getName());

    private ExecutorService threadPool;
    private BlockingQueue<STS_TaskInfo> inputQueue;
    private String serviceName;
    private Date startTime;
    private int totalTasks = 0;
    private int doneTasks = 0;
    private int failureTasks = 0;
    @SuppressWarnings("unused")
	private int retryTasks = 0;
    private boolean stop = false;
    private List<Thread> runningThreads = new ArrayList<Thread>();
    private List<STS_TaskInfo> initQueue = new ArrayList<STS_TaskInfo>();
    private List<String> currentTaskInfos = Collections.synchronizedList(new ArrayList<String>());     
    List<Connection> connList = new ArrayList<Connection>();
    
    public STS_TaskService(String name) {
        this.serviceName = name;
    }

    /**
     * Main method of this class, will be initialize config, prepare tasks queue, etc. Execute all task in task queue and return
     */
    @SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public final void processTasks() {
        try {
            resetTaskInfo();

            doInit();
            //inputQueue = initTaskQueue();

            totalTasks = initQueue.size();
            logger.info(serviceName + " init completed. Total task: " +
                        totalTasks);
            if (totalTasks < 1) {
                //Nothing to do
                return;
            }

            inputQueue = new ArrayBlockingQueue<STS_TaskInfo>(totalTasks);
            inputQueue.addAll(initQueue);
            initQueue.clear();

            int maxThread = getMaxExecutorThread();
            threadPool = Executors.newFixedThreadPool(maxThread);

            TaskExecutor[] executors = new TaskExecutor[maxThread];
            Future[] results = new Future[maxThread];


            logger.info(serviceName + " started. Total tasks = " + totalTasks);
         
            // remove connections used
            connList.clear();
            for (int i = 0; i < maxThread; i++) {
            	
            	connList.add(STS_Global.DATA_SOURCE.getConnection());
                executors[i] = new TaskExecutor(i, connList.get(i));
            	//executors[i] = new TaskExecutor(i);
                results[i] = threadPool.submit(executors[i]);
            }

            for (int i = 0; i < maxThread; i++) {
                Object obj;
                try {
                    obj = results[i].get();
                } catch (InterruptedException e) {
                    logger.warn(executors[i].getThreadName() +
                                   " interrupted!");
                } catch (ExecutionException e) {
                    logger.warn(executors[i].getThreadName() + " error: " +
                                   e.getMessage());
                }
            }

        } catch (Exception e) {
            logger.warn(serviceName + " fatal error: " + e.getMessage(), e);
        } finally {
        	// close connection
        	for (Connection conn : connList) {
        		try {
					conn.close();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}

            if (threadPool != null) {
                threadPool.shutdown();
            }
            doFinallize();
            logger.info(serviceName + " finished. Remain tasks = " +
                        getRemainTasks());
        }
    }

    /**
     * Subclass must override this method to do all service config (Load config from database, etc ...)
     * Should using addTask in this method to init queue
     */
    public abstract void doInit();

    /**
     * Subclass must override this method to do one task in Task queue
     * @param input One task take from Task queue
     */
    public abstract void doTask(STS_TaskInfo input, Connection conn);

    /**
     * Subclass must override this method to clean system resource of this service affter executed
     */
    public abstract void doFinallize();

    /**
     * Sublcass must override this method to return max thread to execute in thread pool
     * @return
     */
    public abstract int getMaxExecutorThread();


    public final void addTask(STS_TaskInfo info) {
        initQueue.add(info);
    }

    public final void retryTask(STS_TaskInfo info) throws STS_IllegalServiceStateException {
        if (inputQueue != null) {
            inputQueue.add(info);
            increaseRetryTasks();
        } else {
            throw new STS_IllegalServiceStateException("Service not initialized");
        }
    }

    public Date getStartTime() {
        return startTime;
    }

    public final int getTotalTasks() {
        return totalTasks;
    }

    public final int getRemainTasks() {
        if (inputQueue != null) {
            return inputQueue.size();
        } else {
            return 0;
        }
    }

    public synchronized final int getDoneTasks() {
        return doneTasks;
    }

    public synchronized final void increaseDoneTasks() {
        ++doneTasks;
    }

    public synchronized final void increaseFailureTasks() {
        ++failureTasks;
    }

    synchronized void increaseRetryTasks() {
        ++retryTasks;
    }

    public final String getExecuteResult() {
        return doneTasks + "/" + totalTasks;
    }

    public synchronized final int getFailureTasks() {
        return failureTasks;
    }

    public final String getWorkingTaskInfos() {
        return currentTaskInfos.toString();
    }

    public String getServiceName() {
        return serviceName;
    }

    @SuppressWarnings("rawtypes")
	private class TaskExecutor implements Callable {
        private int id;
        private Connection conn;		
        public TaskExecutor(int id, Connection conn) {
            this.id = id;
            this.conn = conn;
        }

        /*public TaskExecutor(int id) {
            this.id = id;
        }*/
        
        public Object call() {
            logger.info(getThreadName() + " started");
            runningThreads.add(Thread.currentThread());

            STS_TaskInfo input = null;
            while (!inputQueue.isEmpty() && !stop) {
                try {
                    input = inputQueue.poll(1, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    logger.warn(getThreadName() +
                                   " get TaskInfo from queue interrupted");
                }

                if (input != null) {
                    currentTaskInfos.add(input.getTaskInfo());
                    input.setDoingThreadName(getThreadName());
                    try {
                        doTask(input, conn);
                    } catch (Exception e) {
                        increaseFailureTasks();
                        logger.warn(getThreadName() + " do task '" +
                                   input.getTaskInfo() + "' error: ", e);
                    }
                    currentTaskInfos.remove(input.getTaskInfo());
                    input.setDoingThreadName("None");
                } else {
                    logger.warn(getThreadName() +
                                   " get null task from queue, continue next cyle");
                    continue;
                }
            }

            if (!runningThreads.remove(Thread.currentThread())) {
                logger.warn("Remove running thread have some problem!!!");
            }

            logger.info(getThreadName() + " stopped!");
            return "Done";
        }

        public String getThreadName() {
            return "Thread <" + serviceName + ":" + id + ">";
        }
    }

    public final void stop() {
        logger.info("Try to stop " + serviceName);
        stop = true;

        if (runningThreads != null && runningThreads.size() > 0) {
            logger.info("Try to interrupt " + runningThreads.size() +
                        " running threads ...");
            for (Thread t : runningThreads) {
                t.interrupt();
            }
            runningThreads.clear();
        } else {
            logger.info("No running thread. " + serviceName + " stopped");
        }
    }

    private void resetTaskInfo() {
        stop = false;
        doneTasks = 0;
        failureTasks = 0;
        totalTasks = 0;
        retryTasks = 0;

        startTime = new Date(System.currentTimeMillis());
    }

    public String toString() {
        return serviceName;
    }
    
}
