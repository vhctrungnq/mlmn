package vn.com.vhc.alarm.util.task;

import org.apache.log4j.Logger;

public class AL_ExampleService extends AL_TaskService {
    private static Logger logger =
        Logger.getLogger(AL_ExampleService.class.getName());

    public AL_ExampleService(String name) {
        super(name);
    }

    public static void main(String[] args) throws InterruptedException {
        AL_ExampleService exampleService = new AL_ExampleService("Example service");
        exampleService.initQueue();

        Thread t = new Thread(new TestService(exampleService));
        t.start();

        Thread.sleep(10000);
        exampleService.stop();
    }

    public void initQueue() {
        ExampleTaskInfo info = null;
        for (int i = 0; i < 100; i++) {
            info = new ExampleTaskInfo(i);
            addTask(info);
        }
    }

    public void doInit() {
        logger.info("Init all config for this Task service");
    }

    public void doTask(AL_TaskInfo input) {
        ExampleTaskInfo info = (ExampleTaskInfo)input;

        logger.info(info.getDoingThreadName() + " doing task id = " +
                    info.getInfoId() + " ...");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            logger.warn(info.getDoingThreadName() + " " + e.getMessage());
        }
    }

    public void doFinallize() {
        logger.info("Clear all system resource of this Task service");
    }

    public int getMaxExecutorThread() {
        logger.info("Use max 5 thread for this Task service");
        return 5;
    }

    public class ExampleTaskInfo extends AL_TaskInfo {
        private int infoId;

        ExampleTaskInfo(int id) {
            this.infoId = id;
        }

        public int getInfoId() {
            return infoId;
        }

        public String toString() {
            return "ExampleTaskInfo, id=" + infoId;
        }

        public String getTaskInfo() {
            return toString();
        }
    }

    public static class TestService implements Runnable {
        AL_TaskService service;

        public TestService(AL_TaskService service) {
            this.service = service;
        }

        public void run() {
            service.processTasks();
        }
    }
}
