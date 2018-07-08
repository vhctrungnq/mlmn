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
import vn.com.vhc.vmsc2.statistics.dao.SgsnDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDySgsnTrafficDAO;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.Sgsn;
import vn.com.vhc.vmsc2.statistics.domain.VRpDySgsnTraffic;
import vn.com.vhc.vmsc2.statistics.web.filter.SGSNFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;
import vn.com.vhc.vmsc2.statistics.web.utils.ModelAddtributes;

/**
 * 
 * @author Mr. ThangPX
 * @return SGSN Traffic Report Level Daily, Hourly, Weekly, Monthly
 */
@Controller
@RequestMapping("/report/core/sgsn-traffic/*")
public class SgsnTrafficController extends BaseController {
	@Autowired
	private VRpDySgsnTrafficDAO dySgsnDAO; 
	@Autowired
	private HighlightConfigDAO highlightDao;
	@Autowired
	private SgsnDAO sgsnDao;

	@RequestMapping(value = "hr")
	public String showReport(@RequestParam(required = false) String sgsnid, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, ModelMap model, HttpServletRequest request) {
		SGSNFilter filter = new SGSNFilter();
		List<Sgsn> sgsnList = sgsnDao.filter(filter);
		sgsnid = ModelAddtributes.checkSgsn(model, sgsnList, sgsnid, "sgsn-traffic");
		String[] s = ModelAddtributes.checkHour(model, startDate, startHour, endHour).split(";");
		List<VRpDySgsnTraffic> dySgsnList = dySgsnDAO.filterHourly("V_RP_HR_SGSN_TRAFFIC", sgsnid, s[0], s[1], s[2]);
		model.addAttribute("dySgsnList", dySgsnList);
		return "dySgsnTraffic";
	}

	// Daily report
	@RequestMapping(value = "dy")
	public String showReport(@RequestParam(required = false) String sgsnid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model, HttpServletRequest request) {
		SGSNFilter filter = new SGSNFilter();
		List<Sgsn> sgsnList = sgsnDao.filter(filter);
		sgsnid = ModelAddtributes.checkSgsn(model, sgsnList, sgsnid, "sgsn-traffic");
		String[] s = ModelAddtributes.checkDate2(model, startDate, endDate).split(";");
		List<VRpDySgsnTraffic> dySgsnList = dySgsnDAO.filterDaily("V_RP_DY_SGSN_TRAFFIC", sgsnid, s[0], s[1]);
		model.addAttribute("dySgsnList", dySgsnList);
		List<HighlightConfig> highlightConfigList = highlightDao.getByKey("TT");
		model.addAttribute("highlight", Helper.highLightLine(highlightConfigList, "dySgsnList"));
		return "dySgsnTraffic";
	}

	// Weekly Report
	@RequestMapping(value = "wk")
	public String showReportwk(@RequestParam(required = false) String sgsnid, @RequestParam(required = false) Integer startWeek,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek,
			@RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		SGSNFilter filter = new SGSNFilter();
		List<Sgsn> sgsnList = sgsnDao.filter(filter);
		sgsnid = ModelAddtributes.checkSgsn(model, sgsnList, sgsnid, "sgsn-traffic");
		String[] s = ModelAddtributes.checkWeek(model, startWeek, endWeek, startYear, endYear).split(";");
		List<VRpDySgsnTraffic> dySgsnList = dySgsnDAO.filterWeekly("V_RP_WK_SGSN_TRAFFIC", sgsnid, s[0], s[1], s[2], s[3]);
		model.addAttribute("dySgsnList", dySgsnList);
		List<HighlightConfig> highlightConfigList = highlightDao.getByKey("TT");
		model.addAttribute("highlight", Helper.highLightLine(highlightConfigList, "dySgsnList"));
		return "dySgsnTraffic";
	}

	// Month Report
	@RequestMapping(value = "mn")
	public String showReportmn(@RequestParam(required = false) String sgsnid, @RequestParam(required = false) Integer startMonth,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endMonth,
			@RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		SGSNFilter filter = new SGSNFilter();
		List<Sgsn> sgsnList = sgsnDao.filter(filter);
		sgsnid = ModelAddtributes.checkSgsn(model, sgsnList, sgsnid, "sgsn-traffic");
		String[] s = ModelAddtributes.checkMonth(model, startMonth, endMonth, startYear, endYear).split(";");
		List<VRpDySgsnTraffic> dySgsnList = dySgsnDAO.filterMonthly("V_RP_MN_SGSN_TRAFFIC", sgsnid, s[0], s[1], s[2], s[3]);
		model.addAttribute("dySgsnList", dySgsnList);
		List<HighlightConfig> highlightConfigList = highlightDao.getByKey("TT");
		model.addAttribute("highlight", Helper.highLightLine(highlightConfigList, "dySgsnList"));
		return "dySgsnTraffic";
	}
}
