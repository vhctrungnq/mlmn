package vn.com.vhc.alarm.util.monitor;

import java.util.EventListener;

public interface AL_FreeMemoryChangeListener extends EventListener {
    public void freeMemoryChanged(vn.com.vhc.alarm.util.monitor.AL_FreeMemoryChangeEvent event);
}
