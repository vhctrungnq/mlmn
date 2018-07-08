package vn.com.vhc.sts.util.monitor;

public class DBTablespaceException extends Exception {
    private long currentFree; //Megabyte
    private long threshold;
    private String tablespace;

    public DBTablespaceException(Throwable throwable) {
        super(throwable);
    }

    public DBTablespaceException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public DBTablespaceException(String message) {
        super(message);
    }

    public DBTablespaceException() {
        super();
    }

    public DBTablespaceException(Throwable throwable, String tablespace,
                                 long currentFree, long threshold) {
        super(throwable);
        this.currentFree = currentFree;
        this.threshold = threshold;
        this.tablespace = tablespace;
    }

    public DBTablespaceException(String message, Throwable throwable,
                                 String tablespace, long currentFree,
                                 long threshold) {
        super(message, throwable);
        this.currentFree = currentFree;
        this.threshold = threshold;
        this.tablespace = tablespace;
    }

    public DBTablespaceException(String message, String tablespace,
                                 long currentFree, long threshold) {
        super(message);
        this.currentFree = currentFree;
        this.threshold = threshold;
        this.tablespace = tablespace;
    }

    public DBTablespaceException(String tablespace, long currentFree,
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
