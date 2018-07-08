package inventory.util.monitor;

import java.util.EventListener;

public interface IN_DBTablespaceChangeListener extends EventListener {
    public void freeTablespaceChanged(inventory.util.monitor.IN_DBTablespaceChangeEvent event);
}
