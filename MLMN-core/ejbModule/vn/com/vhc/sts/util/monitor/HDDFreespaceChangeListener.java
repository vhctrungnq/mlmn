package vn.com.vhc.sts.util.monitor;

import java.util.EventListener;

public interface HDDFreespaceChangeListener extends EventListener {
    public void freeHddChanged(vn.com.vhc.sts.util.monitor.HDDFreespaceChangeEvent event);
}
