package inventory.util.task;

/**
 * Class use to store all infomation for one task in Task queue
 * @author thangqm
 */
@SuppressWarnings("rawtypes")
public abstract class IN_TaskInfo implements Comparable {
    private String threadName;

    public IN_TaskInfo() {
    }

    public String toString() {
        return "IN_TaskInfo";
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
