package inventory.core;

import inventory.util.IN_Attribute;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;


public class IN_CoreServiceClient {

	public static void main(String[] args) {
		try {
			Context context = new InitialContext();

			IN_CoreServiceLocal coreService = (IN_CoreServiceLocal) context.lookup("VMSC6/IN_CoreService/local");

			coreService.start();
			// coreService.stop();
			// coreService.switchToNextService();

			// coreService.startMonitor();
			// coreService.stopMonitor();+
			// coreService.registerMonitorEvent();

			List<IN_Attribute> list = null;
			while (true) {
				list = coreService.checkServiceInfo();
				if (list != null) {
					for (IN_Attribute att : list) {
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
