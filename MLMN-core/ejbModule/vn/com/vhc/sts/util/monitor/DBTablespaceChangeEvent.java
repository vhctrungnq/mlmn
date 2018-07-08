package vn.com.vhc.sts.util.monitor;

import java.util.EventObject;

public class DBTablespaceChangeEvent extends EventObject {
    private String tablespace;
    private int free; //Megabyte

    public DBTablespaceChangeEvent(Object source) {
        this(source, "", 0);
    }

    public DBTablespaceChangeEvent(Object source, String tablespace,
                                   int free) {
        super(source);
        this.tablespace = tablespace;
        this.free = free;
    }

    public String getTablespace() {
        return tablespace;
    }

    public void setFree(int free) {
        this.free = free;
    }

    public int getFree() {
        return free;
    }
}
