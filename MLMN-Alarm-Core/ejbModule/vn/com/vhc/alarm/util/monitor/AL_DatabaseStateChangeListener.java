package vn.com.vhc.alarm.util.monitor;

import java.util.EventListener;

public interface AL_DatabaseStateChangeListener extends EventListener {
    public void databaseStateChanged(vn.com.vhc.alarm.util.monitor.AL_DatabaseStateChangeEvent event);
}
