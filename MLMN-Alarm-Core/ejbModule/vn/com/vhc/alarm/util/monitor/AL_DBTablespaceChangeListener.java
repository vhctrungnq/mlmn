package vn.com.vhc.alarm.util.monitor;

import java.util.EventListener;

public interface AL_DBTablespaceChangeListener extends EventListener {
    public void freeTablespaceChanged(vn.com.vhc.alarm.util.monitor.AL_DBTablespaceChangeEvent event);
}
