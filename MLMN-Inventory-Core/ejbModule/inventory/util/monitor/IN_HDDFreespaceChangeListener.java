package inventory.util.monitor;

import java.util.EventListener;

public interface IN_HDDFreespaceChangeListener extends EventListener {
    public void freeHddChanged(inventory.util.monitor.IN_HDDFreespaceChangeEvent event);
}
