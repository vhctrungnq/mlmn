package vn.com.vhc.alarm.util.monitor;

import java.util.EventListener;

public interface AL_CPULoadChangeListener extends EventListener {
    public void cpuLoadChanged(vn.com.vhc.alarm.util.monitor.AL_CPULoadChangeEvent event);
}
