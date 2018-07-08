package vn.com.vhc.alarm.util.monitor;

import java.util.EventListener;

public interface AL_HDDFreespaceChangeListener extends EventListener {
    public void freeHddChanged(vn.com.vhc.alarm.util.monitor.AL_HDDFreespaceChangeEvent event);
}
