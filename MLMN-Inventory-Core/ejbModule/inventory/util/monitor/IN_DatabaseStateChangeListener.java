package inventory.util.monitor;

import java.util.EventListener;

public interface IN_DatabaseStateChangeListener extends EventListener {
    public void databaseStateChanged(inventory.util.monitor.IN_DatabaseStateChangeEvent event);
}
