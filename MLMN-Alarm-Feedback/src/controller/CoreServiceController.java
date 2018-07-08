package controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.com.vhc.alarm.core.AL_CoreServiceLocal;






@Controller
@RequestMapping("/coreService/*")
public class CoreServiceController extends BaseController {

	@Autowired
	private AL_CoreServiceLocal aL_CoreService;
	/*private static Logger logger = Logger.getLogger(CoreServiceController.class.getName());

	private void lookupCoreService() {
		try {
			Context context = new InitialContext();
			aL_CoreService = (AL_CoreServiceLocal) context.lookup("VMSC6/AL_CoreService/local");
		} catch (Exception ex) {
			logger.error("Cannot lookup Alarm CoreService", ex);
		}
	}*/

	@RequestMapping("view")
	public String view(ModelMap model) {
		//lookupCoreService();

		if (aL_CoreService.serviceStarted()) {
			model.put("serviceStarted", true);
			model.put("attList", aL_CoreService.checkServiceInfo());
		} else {
			model.put("serviceStarted", false);
			model.put("attList", null);
		}
		return "jsp/coreService";
	}

	@RequestMapping("start")
	public String start(HttpServletRequest request) {
		//lookupCoreService();
		aL_CoreService.start();
		saveMessage(request, "Alarm CoreService has been started successfully.");
		return "redirect:view.htm";

	}

	@RequestMapping("stop")
	public String stop(HttpServletRequest request) {
		//lookupCoreService();
		aL_CoreService.stop();
		saveMessage(request, "Alarm CoreService has been stoped successfully.");
		return "redirect:view.htm";
	}
}
