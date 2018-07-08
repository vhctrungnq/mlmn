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
import vn.com.vhc.vmsc2.statistics.dao.VRpDyNdsDAO;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyNds;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;
import vn.com.vhc.vmsc2.statistics.web.utils.ModelAddtributes;

/**
 * 
 * @author Mr. ThangPX
 * @return Nds Report Level Daily, Hourly, Weekly, Monthly
 */
@Controller
@RequestMapping("/report/core/csi-nds/*")
public class NdsController extends BaseController {
	@Autowired
	private VRpDyNdsDAO dyCsiDao;
	@Autowired
	private HighlightConfigDAO highlightDao;

	@RequestMapping(value = "hr")
	public String showReport(@RequestParam(required = false) String nodeid, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, ModelMap model, HttpServletRequest request) {
		if(nodeid == null)
			nodeid = "";
		model.addAttribute("nodeid", nodeid);
		model.addAttribute("linkReport", "csi-nds");
		model.addAttribute("nodeType", "Nodeid");
		String[] s = ModelAddtributes.checkHour(model, startDate, startHour, endHour).split(";");
		List<VRpDyNds> dyCsiList = dyCsiDao.filterHourly("V_RP_HR_NDS", nodeid, s[0], s[1], s[2]);
		model.addAttribute("dyCsiList", dyCsiList);
		return "dyNds";
	}

	// Daily report
	@RequestMapping(value = "dy")
	public String showReport(@RequestParam(required = false) String nodeid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model, HttpServletRequest request) {
		if(nodeid == null)
			nodeid = "";
		model.addAttribute("nodeid", nodeid);
		model.addAttribute("linkReport", "csi-nds");
		model.addAttribute("nodeType", "Nodeid");
		String[] s = ModelAddtributes.checkDate2(model, startDate, endDate).split(";");
		List<VRpDyNds> dyCsiList = dyCsiDao.filterDaily("V_RP_DY_NDS", nodeid, s[0], s[1]);
		model.addAttribute("dyCsiList", dyCsiList);
		List<HighlightConfig> highlightConfigList = highlightDao.getByKey("AAA");
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "dyCsiList"));
		return "dyNds";
	}

	// Weekly Report
	@RequestMapping(value = "wk")
	public String showReportwk(@RequestParam(required = false) String nodeid, @RequestParam(required = false) Integer startWeek,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek,
			@RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		if(nodeid == null)
			nodeid = "";
		model.addAttribute("nodeid", nodeid);
		model.addAttribute("linkReport", "csi-nds");
		model.addAttribute("nodeType", "Nodeid");
		String[] s = ModelAddtributes.checkWeek(model, startWeek, endWeek, startYear, endYear).split(";");
		List<VRpDyNds> dyCsiList = dyCsiDao.filterWeekly("V_RP_WK_NDS", nodeid, s[0], s[1], s[2], s[3]);
		model.addAttribute("dyCsiList", dyCsiList);
		List<HighlightConfig> highlightConfigList = highlightDao.getByKey("AAA");
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "dyCsiList"));
		return "dyNds";
	}

	// Month Report
	@RequestMapping(value = "mn")
	public String showReportmn(@RequestParam(required = false) String nodeid, @RequestParam(required = false) Integer startMonth,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endMonth,
			@RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		if(nodeid == null)
			nodeid = "";
		model.addAttribute("nodeid", nodeid);
		model.addAttribute("linkReport", "csi-nds");
		model.addAttribute("nodeType", "Nodeid");
		String[] s = ModelAddtributes.checkMonth(model, startMonth, endMonth, startYear, endYear).split(";");
		List<VRpDyNds> dyCsiList = dyCsiDao.filterMonthly("V_RP_MN_NDS", nodeid, s[0], s[1], s[2], s[3]);
		model.addAttribute("dyCsiList", dyCsiList);
		List<HighlightConfig> highlightConfigList = highlightDao.getByKey("AAA");
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "dyCsiList"));
		return "dyNds";
	}
}
