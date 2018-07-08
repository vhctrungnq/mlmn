package vn.com.vhc.alarm.util.monitor;

@SuppressWarnings("serial")
public class AL_MemoryException extends Exception {
    private int currentFree; //Megabyte
    private int threshold;

    public AL_MemoryException(Throwable cause) {
        super(cause);
    }

    public AL_MemoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public AL_MemoryException(String message) {
        super(message);
    }

    public AL_MemoryException() {
        super();
    }

    public AL_MemoryException(Throwable cause, int currentFree, int threshold) {
        super(cause);
        this.currentFree = currentFree;
        this.threshold = threshold;
    }

    public AL_MemoryException(String message, Throwable cause, int currentFree,
                           int threshold) {
        super(message, cause);
        this.currentFree = currentFree;
        this.threshold = threshold;
    }

    public AL_MemoryException(String message, int currentFree, int threshold) {
        super(message);
        this.currentFree = currentFree;
        this.threshold = threshold;
    }

    public AL_MemoryException(int currentFree, int threshold) {
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
