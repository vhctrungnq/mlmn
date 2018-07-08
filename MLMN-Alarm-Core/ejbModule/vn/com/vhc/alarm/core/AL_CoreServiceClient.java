package vn.com.vhc.alarm.core;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import vn.com.vhc.alarm.util.AL_Attribute;

public class AL_CoreServiceClient {

	public static void main(String[] args) {
		try {
			Context context = new InitialContext();

			AL_CoreServiceLocal coreService = (AL_CoreServiceLocal) context.lookup("VMSC6/CoreService/local");

			coreService.start();
			// coreService.stop();
			// coreService.switchToNextService();

			// coreService.startMonitor();
			// coreService.stopMonitor();+
			// coreService.registerMonitorEvent();

			List<AL_Attribute> list = null;
			while (true) {
				list = coreService.checkServiceInfo();
				if (list != null) {
					for (AL_Attribute att : list) {
						System.out.println(att);
					}
				}
				Thread.sleep(10000);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
