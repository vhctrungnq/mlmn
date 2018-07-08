package inventory.util.monitor;

import java.util.EventObject;

public class IN_FreeMemoryChangeEvent extends EventObject {
    private int total; //Megabyte
    private int free; //Megabyte

    public IN_FreeMemoryChangeEvent(Object source) {
        super(source);
    }

    public IN_FreeMemoryChangeEvent(Object source, int total, int free) {
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
