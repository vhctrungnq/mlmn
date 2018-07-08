package vn.com.vhc.sts.util.task;

/**
 * Class use to store all infomation for one task in Task queue
 * @author thangqm
 */
@SuppressWarnings("rawtypes")
public abstract class STS_TaskInfo implements Comparable {
    private String threadName;

    public STS_TaskInfo() {
    }

    public String toString() {
        return "TaskInfo";
    }

    /**
     * Return some information about this task
     * @return
     */
    public abstract String getTaskInfo();

    public final String getDoingThreadName() {
        return threadName;
    }

    public final void setDoingThreadName(String threadName) {
        this.threadName = threadName;
    }

    public int compareTo(Object o) {
        return 0;
    }
}
