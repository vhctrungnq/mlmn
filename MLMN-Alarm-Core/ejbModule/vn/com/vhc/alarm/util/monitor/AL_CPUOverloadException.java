package vn.com.vhc.alarm.util.monitor;

@SuppressWarnings("serial")
public class AL_CPUOverloadException extends Exception {
    private int currentLoad;
    private int threshold;

    public AL_CPUOverloadException(Throwable throwable) {
        super(throwable);
    }

    public AL_CPUOverloadException(Throwable throwable, int currentLoad,
                                int threshold) {
        super(throwable);
        this.currentLoad = currentLoad;
        this.threshold = threshold;
    }

    public AL_CPUOverloadException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public AL_CPUOverloadException(String message, Throwable throwable,
                                int currentLoad, int threshold) {
        super(message, throwable);
        this.currentLoad = currentLoad;
        this.threshold = threshold;
    }

    public AL_CPUOverloadException(String message) {
        super(message);
    }

    public AL_CPUOverloadException(String message, int currentLoad,
                                int threshold) {
        super(message);
        this.currentLoad = currentLoad;
        this.threshold = threshold;
    }

    public AL_CPUOverloadException() {
        super();
    }

    public AL_CPUOverloadException(int currentLoad, int threshold) {
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
