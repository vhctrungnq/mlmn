package vn.com.vhc.sts.util.monitor;

public class CPUOverloadException extends Exception {
    private int currentLoad;
    private int threshold;

    public CPUOverloadException(Throwable throwable) {
        super(throwable);
    }

    public CPUOverloadException(Throwable throwable, int currentLoad,
                                int threshold) {
        super(throwable);
        this.currentLoad = currentLoad;
        this.threshold = threshold;
    }

    public CPUOverloadException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public CPUOverloadException(String message, Throwable throwable,
                                int currentLoad, int threshold) {
        super(message, throwable);
        this.currentLoad = currentLoad;
        this.threshold = threshold;
    }

    public CPUOverloadException(String message) {
        super(message);
    }

    public CPUOverloadException(String message, int currentLoad,
                                int threshold) {
        super(message);
        this.currentLoad = currentLoad;
        this.threshold = threshold;
    }

    public CPUOverloadException() {
        super();
    }

    public CPUOverloadException(int currentLoad, int threshold) {
        super();
        this.currentLoad = currentLoad;
        this.threshold = threshold;
    }


    public void setCurrentLoad(int currentLoad) {
        this.currentLoad = currentLoad;
    }

    public int getCurrentLoad() {
        return currentLoad;
    }

    public void setThreshold(int thresHold) {
        this.threshold = thresHold;
    }

    public int getThreshold() {
        return threshold;
    }
}
