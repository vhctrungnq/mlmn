package vn.com.vhc.sts.util.monitor;

import java.util.EventListener;

public interface FreeMemoryChangeListener extends EventListener {
    public void freeMemoryChanged(vn.com.vhc.sts.util.monitor.FreeMemoryChangeEvent event);
}
