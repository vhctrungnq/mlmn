package vn.com.vhc.alarm.core;

import java.util.List;

import javax.ejb.Local;

import vn.com.vhc.alarm.util.AL_Attribute;



@Local
public interface AL_CoreServiceLocal {
    void start();

    void stop();

    List<AL_Attribute> checkServiceInfo();

    boolean serviceStarted();

    void switchToNextService();

    void startMonitor();

    void stopMonitor();

    void registerMonitorEvent();
}
