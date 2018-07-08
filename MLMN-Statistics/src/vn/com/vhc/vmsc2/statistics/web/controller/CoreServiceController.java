package vn.com.vhc.vmsc2.statistics.web.controller;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.com.vhc.sts.core.STS_CoreServiceLocal;


@Controller
@RequestMapping("/coreService/*")
public class CoreServiceController extends BaseController {
	private STS_CoreServiceLocal coreService;
	private static Logger logger = Logger.getLogger(CoreServiceController.class.getName());

	private void lookupCoreService() {
		try {
			Context context = new InitialContext();
			coreService = (STS_CoreServiceLocal) context.lookup("MLMN/STS_CoreService/local");
		} catch (Exception ex) {
			logger.error("Cannot lookup CoreService", ex);
		}
	}

	@RequestMapping("view")
	public String view(ModelMap model) {
		lookupCoreService();

		if (coreService.serviceStarted()) {
			model.put("serviceStarted", true);
			model.put("attList", coreService.checkServiceInfo());
		} else {
			model.put("serviceStarted", false);
			model.put("attList", null);
		}
		return "coreService";
	}

	@RequestMapping("start")
	public String start(HttpServletRequest request) {
		lookupCoreService();
		coreService.start();
		saveMessage(request, "CoreService has been started successfully.");
		return "redirect:view.htm";

	}

	@RequestMapping("stop")
	public String stop(HttpServletRequest request) {
		lookupCoreService();
		coreService.stop();
		saveMessage(request, "CoreService has been stoped successfully.");
		return "redirect:view.htm";
	}
}
