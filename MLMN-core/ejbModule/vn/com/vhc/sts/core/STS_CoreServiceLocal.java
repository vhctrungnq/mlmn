package vn.com.vhc.sts.core;

import java.util.List;

import javax.ejb.Local;

import vn.com.vhc.sts.util.STS_Attribute;



@Local
public interface STS_CoreServiceLocal {
    void start();

    void stop();

    List<STS_Attribute> checkServiceInfo();

    boolean serviceStarted();

    void switchToNextService();

    void startMonitor();

    void stopMonitor();

    void registerMonitorEvent();
}
