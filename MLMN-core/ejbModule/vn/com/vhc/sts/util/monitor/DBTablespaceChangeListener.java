package vn.com.vhc.sts.util.monitor;

import java.util.EventListener;

public interface DBTablespaceChangeListener extends EventListener {
    public void freeTablespaceChanged(vn.com.vhc.sts.util.monitor.DBTablespaceChangeEvent event);
}
