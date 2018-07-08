package vn.com.vhc.vmsc2.statistics.web.utils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.core.STS_CoreServiceLocal;

public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(MyServlet.class);

	private STS_CoreServiceLocal coreService;

	public void init() {
		Context context = null;

		logger.info("Starting core service..");

		try {
			context = new InitialContext();
			coreService = (STS_CoreServiceLocal) context.lookup("MLMN/STS_CoreService/local");
			coreService.start();

			//coreService.startMonitor();
		} catch (Exception ex) {
			logger.error("Cannot lookup CoreService", ex);
		}

		//logger.info("CoreService has been started successfully.");
/*
		SmppQueueManager smppQueueManager = new SmppQueueManager();
		smppQueueManager.start();*/
	}
}
