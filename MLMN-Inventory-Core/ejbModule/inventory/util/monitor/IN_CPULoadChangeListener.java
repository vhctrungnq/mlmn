package inventory.util.monitor;

import java.util.EventListener;

public interface IN_CPULoadChangeListener extends EventListener {
    public void cpuLoadChanged(inventory.util.monitor.IN_CPULoadChangeEvent event);
}
