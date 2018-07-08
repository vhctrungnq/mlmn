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
import vn.com.vhc.vmsc2.statistics.dao.VRpDyRegionConDAO;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyRegionCon;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;
import vn.com.vhc.vmsc2.statistics.web.utils.ModelAddtributes;

/**
 * 
 * @author Mr. ThangPX
 * @return Summary core NGW Report Level Daily, Weekly, Monthly
 */
@Controller
@RequestMapping("/report/core/msc-sum-ngw/*")
public class MscNgwSummaryController extends BaseController {
	@Autowired
	private VRpDyRegionConDAO dyMscDAO; 
	@Autowired
	private HighlightConfigDAO highlightDao; 
	
	// Daily report
	@RequestMapping(value = "dy")
	public String showReport( @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model, HttpServletRequest request) {
		
		
		String[] s = ModelAddtributes.checkDate2(model, startDate, endDate).split(";");
		List<VRpDyRegionCon> dyMscList = dyMscDAO.filterDaily("V_RP_DY_REGION_CON", "", s[0], s[1]);
		model.addAttribute("dyMscList", dyMscList);
		model.addAttribute("linkReport", "msc-sum-ngw");
		List<HighlightConfig> highlightConfigList = highlightDao.getByKey("TT");
		model.addAttribute("highlight", Helper.highLightLine(highlightConfigList, "dyMscList"));
		return "dyMscSumCorengw";
	}

	// Weekly Report
	@RequestMapping(value = "wk")
	public String showReportwk( @RequestParam(required = false) Integer startWeek,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek,
			@RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		
		String[] s = ModelAddtributes.checkWeek(model, startWeek, endWeek, startYear, endYear).split(";");
		List<VRpDyRegionCon> dyMscList = dyMscDAO.filterWeekly("V_RP_WK_REGION_CON", "", s[0], s[1], s[2], s[3]);
		model.addAttribute("dyMscList", dyMscList);
		model.addAttribute("linkReport", "msc-sum-ngw");
		List<HighlightConfig> highlightConfigList = highlightDao.getByKey("TT");
		model.addAttribute("highlight", Helper.highLightLine(highlightConfigList, "dyMscList"));
		return "dyMscSumCorengw";
	}

	// Month Report
	@RequestMapping(value = "mn")
	public String showReportmn( @RequestParam(required = false) Integer startMonth,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endMonth,
			@RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
	

		String[] s = ModelAddtributes.checkMonth(model, startMonth, endMonth, startYear, endYear).split(";");
		List<VRpDyRegionCon> dyMscList = dyMscDAO.filterMonthly("V_RP_MN_REGION_CON", "", s[0], s[1], s[2], s[3]);
		model.addAttribute("dyMscList", dyMscList);
		model.addAttribute("linkReport", "msc-sum-ngw");
		List<HighlightConfig> highlightConfigList = highlightDao.getByKey("TT");
		model.addAttribute("highlight", Helper.highLightLine(highlightConfigList, "dyMscList"));
		return "dyMscSumCorengw";
	}
}
