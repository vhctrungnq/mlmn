package vn.com.vhc.vmsc2.statistics.web.controller; 

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpBscLoad2gDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpBscLoad2g;
import vn.com.vhc.vmsc2.statistics.web.filter.BscFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;
import vn.com.vhc.vmsc2.statistics.web.utils.ModelAddtributes;

/**
 * 
 * @author Mr. AnhNT
 * @return Load Report Level Daily, Hourly, Weekly, Monthly
 */
@Controller
@RequestMapping("/report/core/bsc-load-2g/*")
public class BscLoad2GController extends BaseController {
	@Autowired
	private VRpBscLoad2gDAO bscLoadDAO; 
	@Autowired
	private HighlightConfigDAO highlightDao;
	@Autowired
	private BscDAO bscDao;

	@RequestMapping(value = "hr")
	public String showReport(@RequestParam(required = false) String bscid,@RequestParam(required = false) String objName, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, ModelMap model, HttpServletRequest request) {
		BscFilter bscFilter = new BscFilter();
		List<Bsc> listBsc = bscDao.filter(bscFilter);
		bscid = ModelAddtributes.checkBscid(model, listBsc, bscid,"bsc-load-2g");
		String[] s = ModelAddtributes.checkHour(model, startDate, startHour, endHour).split(";");
		List<VRpBscLoad2g> dyBscList = bscLoadDAO.filterHourly("V_RP_HR_BSC_LOAD_2G", bscid, objName, s[0], s[1], s[2]);
		model.addAttribute("dyBscList", dyBscList);
		model.addAttribute("objName", objName);
		return "bscLoad2G";
	}

	// Daily report
	@RequestMapping(value = "dy")
	public String showReport(@RequestParam(required = false) String bscid,@RequestParam(required = false) String objName, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model, HttpServletRequest request) {
		BscFilter bscFilter = new BscFilter();
		List<Bsc> listBsc = bscDao.filter(bscFilter);
		bscid = ModelAddtributes.checkBscid(model, listBsc, bscid, "bsc-load-2g");
		String[] s = ModelAddtributes.checkDate(model, startDate, endDate).split(";");
		List<VRpBscLoad2g> dyBscList = bscLoadDAO.filterDaily("V_RP_DY_BSC_LOAD_2G", bscid, objName, s[0], s[1]);
		model.addAttribute("dyBscList", dyBscList);
		model.addAttribute("objName", objName);
		List<HighlightConfig> highlightConfigList = highlightDao.getByKey("TT");
		model.addAttribute("highlight", Helper.highLightLine(highlightConfigList, "dyBscList"));
		return "bscLoad2G";
	}

	// Weekly Report
	@RequestMapping(value = "wk")
	public String showReportwk(@RequestParam(required = false) String bscid,@RequestParam(required = false) String objName, @RequestParam(required = false) Integer startWeek,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek,
			@RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		BscFilter bscFilter = new BscFilter();
		List<Bsc> listBsc = bscDao.filter(bscFilter);
		bscid = ModelAddtributes.checkBscid(model, listBsc, bscid,"bsc-load-2g");
		String[] s = ModelAddtributes.checkWeek(model, startWeek, endWeek, startYear, endYear).split(";");
		List<VRpBscLoad2g> dyBscList = bscLoadDAO.filterWeekly("V_RP_WK_BSC_LOAD_2G", bscid, objName, s[0], s[1], s[2], s[3]);
		model.addAttribute("dyBscList", dyBscList);
		model.addAttribute("objName", objName);
		List<HighlightConfig> highlightConfigList = highlightDao.getByKey("TT");
		model.addAttribute("highlight", Helper.highLightLine(highlightConfigList, "dyBscList"));
		return "bscLoad2G";
	}

	// Month Report
	@RequestMapping(value = "mn")
	public String showReportmn(@RequestParam(required = false) String bscid,@RequestParam(required = false) String objName, @RequestParam(required = false) Integer startMonth,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endMonth,
			@RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		BscFilter bscFilter = new BscFilter();
		List<Bsc> listBsc = bscDao.filter(bscFilter);
		bscid = ModelAddtributes.checkBscid(model, listBsc, bscid, "bsc-load-2g");
		String[] s = ModelAddtributes.checkMonth(model, startMonth, endMonth, startYear, endYear).split(";");
		List<VRpBscLoad2g> dyBscList = bscLoadDAO.filterMonthly("V_RP_MN_BSC_LOAD_2G", bscid, objName, s[0], s[1], s[2], s[3]);
		model.addAttribute("dyBscList", dyBscList);
		model.addAttribute("objName", objName);
		List<HighlightConfig> highlightConfigList = highlightDao.getByKey("TT");
		model.addAttribute("highlight", Helper.highLightLine(highlightConfigList, "dyBscList"));
		return "bscLoad2G";
	}
} 
