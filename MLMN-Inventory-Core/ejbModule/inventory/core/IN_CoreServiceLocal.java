package inventory.core;

import inventory.util.IN_Attribute;

import java.util.List;

import javax.ejb.Local;




@Local
public interface IN_CoreServiceLocal {
    void start();

    void stop();

    List<IN_Attribute> checkServiceInfo();

    boolean serviceStarted();

    void switchToNextService();

    void startMonitor();

    void stopMonitor();

    void registerMonitorEvent();
}
