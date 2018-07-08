package inventory.util.monitor;

import java.util.EventObject;

public class IN_CPULoadChangeEvent extends EventObject {
    private double load;

    public IN_CPULoadChangeEvent(Object source, double load) {
        super(source);
        this.load = load;
    }

    public IN_CPULoadChangeEvent(Object source) {
        this(source, 0);
    }

    public void setLoad(double load) {
        this.load = load;
    }

    public double getLoad() {
        return load;
    }
}
