package inventory.util.monitor;

import java.util.EventObject;

public class IN_HDDFreespaceChangeEvent extends EventObject {
    private String driver;
    private int free; //Megabyte

    public IN_HDDFreespaceChangeEvent(Object source) {
        this(source, "", 0);
    }

    public IN_HDDFreespaceChangeEvent(Object source, String driver, int free) {
        super(source);
        this.driver = driver;
        this.free = free;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDriver() {
        return driver;
    }

    public void setFree(int free) {
        this.free = free;
    }

    public int getFree() {
        return free;
    }
}