package vn.com.vhc.sts.util.monitor;

import java.util.EventListener;

public interface CPULoadChangeListener extends EventListener {
    public void cpuLoadChanged(vn.com.vhc.sts.util.monitor.CPULoadChangeEvent event);
}
