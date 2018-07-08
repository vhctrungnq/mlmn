package inventory.util.monitor;

public class IN_DBTablespaceException extends Exception {
    private long currentFree; //Megabyte
    private long threshold;
    private String tablespace;

    public IN_DBTablespaceException(Throwable throwable) {
        super(throwable);
    }

    public IN_DBTablespaceException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public IN_DBTablespaceException(String message) {
        super(message);
    }

    public IN_DBTablespaceException() {
        super();
    }

    public IN_DBTablespaceException(Throwable throwable, String tablespace,
                                 long currentFree, long threshold) {
        super(throwable);
        this.currentFree = currentFree;
        this.threshold = threshold;
        this.tablespace = tablespace;
    }

    public IN_DBTablespaceException(String message, Throwable throwable,
                                 String tablespace, long currentFree,
                                 long threshold) {
        super(message, throwable);
        this.currentFree = currentFree;
        this.threshold = threshold;
        this.tablespace = tablespace;
    }

    public IN_DBTablespaceException(String message, String tablespace,
                                 long currentFree, long threshold) {
        super(message);
        this.currentFree = currentFree;
        this.threshold = threshold;
        this.tablespace = tablespace;
    }

    public IN_DBTablespaceException(String tablespace, long currentFree,
                                 long threshold) {
        super();
        this.currentFree = currentFree;
        this.threshold = threshold;
        this.tablespace = tablespace;
    }

    public void setCurrentFree(long currentFree) {
        this.currentFree = currentFree;
    }

    public long getCurrentFree() {
        return currentFree;
    }

    public void setThreshold(long threshold) {
        this.threshold = threshold;
    }

    public long getThreshold() {
        return threshold;
    }

    public void setTablespace(String tablespace) {
        this.tablespace = tablespace;
    }

    public String getTablespace() {
        return tablespace;
    }
}
