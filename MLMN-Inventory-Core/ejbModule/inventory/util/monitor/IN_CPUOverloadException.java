package inventory.util.monitor;

public class IN_CPUOverloadException extends Exception {
    private int currentLoad;
    private int threshold;

    public IN_CPUOverloadException(Throwable throwable) {
        super(throwable);
    }

    public IN_CPUOverloadException(Throwable throwable, int currentLoad,
                                int threshold) {
        super(throwable);
        this.currentLoad = currentLoad;
        this.threshold = threshold;
    }

    public IN_CPUOverloadException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public IN_CPUOverloadException(String message, Throwable throwable,
                                int currentLoad, int threshold) {
        super(message, throwable);
        this.currentLoad = currentLoad;
        this.threshold = threshold;
    }

    public IN_CPUOverloadException(String message) {
        super(message);
    }

    public IN_CPUOverloadException(String message, int currentLoad,
                                int threshold) {
        super(message);
        this.currentLoad = currentLoad;
        this.threshold = threshold;
    }

    public IN_CPUOverloadException() {
        super();
    }

    public IN_CPUOverloadException(int currentLoad, int threshold) {
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
