package vn.com.vhc.sts.util.monitor;

import java.util.EventObject;

public class FreeMemoryChangeEvent extends EventObject {
    private int total; //Megabyte
    private int free; //Megabyte

    public FreeMemoryChangeEvent(Object source) {
        super(source);
    }

    public FreeMemoryChangeEvent(Object source, int total, int free) {
        super(source);
        this.total = total;
        this.free = free;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public void setFree(int free) {
        this.free = free;
    }

    public int getFree() {
        return free;
    }
}
