package vn.com.vhc.alarm.util.monitor;

import java.util.EventObject;

@SuppressWarnings("serial")
public class AL_HDDFreespaceChangeEvent extends EventObject {
    private String driver;
    private int free; //Megabyte

    public AL_HDDFreespaceChangeEvent(Object source) {
        this(source, "", 0);
    }

    public AL_HDDFreespaceChangeEvent(Object source, String driver, int free) {
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
