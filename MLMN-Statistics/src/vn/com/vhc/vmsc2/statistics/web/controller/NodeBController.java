package vn.com.vhc.vmsc2.statistics.web.controller; 

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyNodebDAO;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyNodeb;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;
import vn.com.vhc.vmsc2.statistics.web.utils.ModelAddtributes;

/**
 * 
 * @author Mr. ThangPX
 * @return Load Report Level Daily, Hourly, Weekly, Monthly
 */
@Controller
@RequestMapping("/report/core/giam-sat-nodeb/*")
public class NodeBController extends BaseController {
	@Autowired
	private VRpDyNodebDAO nodeBDao; 
	@Autowired
	private HighlightConfigDAO highlightDao; 

	@RequestMapping(value = "hr")
	public String showReport(@RequestParam(required = false) String nodeid, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, ModelMap model, HttpServletRequest request) {
		 
		if(nodeid == null)
			nodeid = "";
		 
		String[] s = ModelAddtributes.checkHour(model, startDate, startHour, endHour).split(";");
		List<VRpDyNodeb> nodeBList = nodeBDao.filterHourly("V_RP_HR_NODEB", nodeid, s[0], s[1], s[2]);
		model.addAttribute("nodeBList", nodeBList);
		model.addAttribute("linkReport", "giam-sat-nodeb");
		model.addAttribute("nodeType", "NodeB");
		model.addAttribute("nodeid", nodeid);
		return "dyNodeB";
	}

	// Daily report
	@RequestMapping(value = "dy")
	public String showReport(@RequestParam(required = false) String nodeid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model, HttpServletRequest request) {
	 
		if(nodeid == null)
			nodeid = "";
		
		
		String[] s = ModelAddtributes.checkDate(model, startDate, endDate).split(";");
		List<VRpDyNodeb> nodeBList = nodeBDao.filterDaily("V_RP_DY_NODEB", nodeid, s[0], s[1]);
		model.addAttribute("nodeBList", nodeBList);
		model.addAttribute("linkReport", "giam-sat-nodeb");
		model.addAttribute("nodeType", "NodeB");
		model.addAttribute("nodeid", nodeid);
		List<HighlightConfig> highlightConfigList = highlightDao.getByKey("NODEB");
		model.addAttribute("highlight", Helper.highLightLine(highlightConfigList, "nodeBList"));
		return "dyNodeB";
	}
 
} 
