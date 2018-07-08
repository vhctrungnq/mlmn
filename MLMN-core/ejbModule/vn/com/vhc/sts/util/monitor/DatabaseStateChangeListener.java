package vn.com.vhc.sts.util.monitor;

import java.util.EventListener;

public interface DatabaseStateChangeListener extends EventListener {
    public void databaseStateChanged(vn.com.vhc.sts.util.monitor.DatabaseStateChangeEvent event);
}
