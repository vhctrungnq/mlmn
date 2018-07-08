package vn.com.vhc.sts.util.monitor;

import java.util.EventObject;

public class CPULoadChangeEvent extends EventObject {
    private double load;

    public CPULoadChangeEvent(Object source, double load) {
        super(source);
        this.load = load;
    }

    public CPULoadChangeEvent(Object source) {
        this(source, 0);
    }

    public void setLoad(double load) {
        this.load = load;
    }

    public double getLoad() {
        return load;
    }
}
