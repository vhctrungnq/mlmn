package inventory.util.monitor;

import java.util.EventListener;

public interface IN_FreeMemoryChangeListener extends EventListener {
    public void freeMemoryChanged(inventory.util.monitor.IN_FreeMemoryChangeEvent event);
}
