package vn.com.vhc.alarm.util.monitor;

import java.util.EventObject;

@SuppressWarnings("serial")
public class AL_CPULoadChangeEvent extends EventObject {
    private double load;

    public AL_CPULoadChangeEvent(Object source, double load) {
        super(source);
        this.load = load;
    }

    public AL_CPULoadChangeEvent(Object source) {
        this(source, 0);
    }

    public void setLoad(double load) {
        this.load = load;
    }

    public double getLoad() {
        return load;
    }
}
