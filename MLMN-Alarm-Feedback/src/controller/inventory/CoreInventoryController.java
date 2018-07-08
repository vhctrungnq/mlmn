package controller.inventory; 

import inventory.core.IN_CoreServiceLocal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import controller.BaseController;


@Controller
@RequestMapping("/coreInventory/*")
public class CoreInventoryController extends BaseController {

	@Autowired
	private IN_CoreServiceLocal in_CoreService;
	//private static Logger logger = Logger.getLogger(CoreInventoryController.class.getName());

	/*private void lookupCoreService() {
		try {
			Context context = new InitialContext();
			in_CoreService = (IN_CoreServiceLocal) context.lookup("VMSC6/IN_CoreService/local");
		} catch (Exception ex) {
			logger.error("Cannot lookup Inventory CoreService", ex);
		}
	}*/

	@RequestMapping("view")
	public String view(ModelMap model) {
		/*lookupCoreService();*/

		if (in_CoreService.serviceStarted()) {
			model.put("serviceStarted", true);
			model.put("attList", in_CoreService.checkServiceInfo());
		} else {
			model.put("serviceStarted", false);
			model.put("attList", null);
		}
		return "jspiso/coreInventory";
	}

	@RequestMapping("start")
	public String start(HttpServletRequest request) {
		/*lookupCoreService();*/
		in_CoreService.start();
		saveMessage(request, "Inventory CoreService has been started successfully.");
		return "redirect:view.htm";

	}

	@RequestMapping("stop")
	public String stop(HttpServletRequest request) {
		/*lookupCoreService();*/
		in_CoreService.stop();
		saveMessage(request, "Inventory CoreService has been stoped successfully.");
		return "redirect:view.htm";
	}
}
