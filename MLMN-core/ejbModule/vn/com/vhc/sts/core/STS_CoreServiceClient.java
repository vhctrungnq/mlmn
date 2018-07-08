package vn.com.vhc.sts.core;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import vn.com.vhc.sts.util.STS_Attribute;

public class STS_CoreServiceClient {

	public static void main(String[] args) {
		try {
			Context context = new InitialContext();

			STS_CoreServiceLocal coreService = (STS_CoreServiceLocal) context.lookup("VMSC2/AL_CoreService/local");

			coreService.start();
			// coreService.stop();
			// coreService.switchToNextService();

			// coreService.startMonitor();
			// coreService.stopMonitor();+
			// coreService.registerMonitorEvent();

			List<STS_Attribute> list = null;
			while (true) {
				list = coreService.checkServiceInfo();
				if (list != null) {
					for (STS_Attribute att : list) {
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
