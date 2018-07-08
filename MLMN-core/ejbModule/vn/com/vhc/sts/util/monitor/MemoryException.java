package vn.com.vhc.sts.util.monitor;

public class MemoryException extends Exception {
    private int currentFree; //Megabyte
    private int threshold;

    public MemoryException(Throwable cause) {
        super(cause);
    }

    public MemoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemoryException(String message) {
        super(message);
    }

    public MemoryException() {
        super();
    }

    public MemoryException(Throwable cause, int currentFree, int threshold) {
        super(cause);
        this.currentFree = currentFree;
        this.threshold = threshold;
    }

    public MemoryException(String message, Throwable cause, int currentFree,
                           int threshold) {
        super(message, cause);
        this.currentFree = currentFree;
        this.threshold = threshold;
    }

    public MemoryException(String message, int currentFree, int threshold) {
        super(message);
        this.currentFree = currentFree;
        this.threshold = threshold;
    }

    public MemoryException(int currentFree, int threshold) {
        super();
        this.currentFree = currentFree;
        this.threshold = threshold;
    }

    public void setCurrentFree(int currentFree) {
        this.currentFree = currentFree;
    }

    public int getCurrentFree() {
        return currentFree;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public int getThreshold() {
        return threshold;
    }
}
