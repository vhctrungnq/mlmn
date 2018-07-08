package vn.com.vhc.sts.util.monitor;

public class HDDCheckException extends Exception {
    private long currentFree; //Megabyte
    private long threshold;
    private String driverName;

    public HDDCheckException(Throwable throwable) {
        super(throwable);
    }

    public HDDCheckException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public HDDCheckException(String message) {
        super(message);
    }

    public HDDCheckException() {
        super();
    }

    public HDDCheckException(Throwable throwable, String driverName,
                             long currentFree, long threshold) {
        super(throwable);
        this.driverName = driverName;
        this.currentFree = currentFree;
        this.threshold = threshold;
    }

    public HDDCheckException(String message, Throwable throwable,
                             String driverName, long currentFree,
                             long threshold) {
        super(message, throwable);
        this.driverName = driverName;
        this.currentFree = currentFree;
        this.threshold = threshold;
    }

    public HDDCheckException(String message, String driverName,
                             long currentFree, long threshold) {
        super(message);
        this.driverName = driverName;
        this.currentFree = currentFree;
        this.threshold = threshold;
    }

    public HDDCheckException(String driverName, long currentFree,
                             long threshold) {
        super();
        this.driverName = driverName;
        this.currentFree = currentFree;
        this.threshold = threshold;
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

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverName() {
        return driverName;
    }
}
