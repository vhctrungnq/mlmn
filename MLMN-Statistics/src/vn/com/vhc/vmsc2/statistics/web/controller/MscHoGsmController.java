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
import vn.com.vhc.vmsc2.statistics.dao.MscDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyMscHoGsmDAO;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.Msc;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyMscHoGsm;
import vn.com.vhc.vmsc2.statistics.web.filter.MscFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;
import vn.com.vhc.vmsc2.statistics.web.utils.ModelAddtributes;

/**
 * 
 * @author Mr. ThangPX
 * @return Authencation Report Level Daily, Hourly, Weekly, Monthly
 */
@Controller
@RequestMapping("/report/core/msc-ho-gsm/*")
public class MscHoGsmController extends BaseController {
	@Autowired
	private VRpDyMscHoGsmDAO dyMscDAO;
	@Autowired
	private HighlightConfigDAO highlightDao;
	@Autowired
	private MscDAO mscDao;

	@RequestMapping(value = "hr")
	public String showReport(@RequestParam(required = false) String mscid, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, ModelMap model, HttpServletRequest request) {
		MscFilter mscFilter = new MscFilter();
		List<Msc> listMsc = mscDao.filter(mscFilter);
		mscid = ModelAddtributes.checkMsc(model, listMsc, mscid, "msc-ho-gsm");
		String[] s = ModelAddtributes.checkHour(model, startDate, startHour, endHour).split(";");
		List<VRpDyMscHoGsm> dyMscList = dyMscDAO.filterHourly("V_RP_HR_MSC_HO_GSM", mscid, s[0], s[1], s[2]);
		model.addAttribute("dyMscList", dyMscList);
		return "dyMscHoGsm";
	}

	// Daily report
	@RequestMapping(value = "dy")
	public String showReport(@RequestParam(required = false) String mscid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model, HttpServletRequest request) {
		MscFilter mscFilter = new MscFilter();
		List<Msc> listMsc = mscDao.filter(mscFilter);
		mscid = ModelAddtributes.checkMsc(model, listMsc, mscid, "msc-ho-gsm");
		String[] s = ModelAddtributes.checkDate2(model, startDate, endDate).split(";");
		List<VRpDyMscHoGsm> dyMscList = dyMscDAO.filterDaily("V_RP_DY_MSC_HO_GSM", mscid, s[0], s[1]);
		model.addAttribute("dyMscList", dyMscList);
		List<HighlightConfig> highlightConfigList = highlightDao.getByKey("TT");
		model.addAttribute("highlight", Helper.highLightLine(highlightConfigList, "dyMscList"));
		return "dyMscHoGsm";
	}

	// Weekly Report
	@RequestMapping(value = "wk")
	public String showReportwk(@RequestParam(required = false) String mscid, @RequestParam(required = false) Integer startWeek,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek,
			@RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		MscFilter mscFilter = new MscFilter();
		List<Msc> listMsc = mscDao.filter(mscFilter);
		mscid = ModelAddtributes.checkMsc(model, listMsc, mscid, "msc-ho-gsm");
		String[] s = ModelAddtributes.checkWeek(model, startWeek, endWeek, startYear, endYear).split(";");
		List<VRpDyMscHoGsm> dyMscList = dyMscDAO.filterWeekly("V_RP_WK_MSC_HO_GSM", mscid, s[0], s[1], s[2], s[3]);
		model.addAttribute("dyMscList", dyMscList);
		List<HighlightConfig> highlightConfigList = highlightDao.getByKey("TT");
		model.addAttribute("highlight", Helper.highLightLine(highlightConfigList, "dyMscList"));
		return "dyMscHoGsm";
	}

	// Month Report
	@RequestMapping(value = "mn")
	public String showReportmn(@RequestParam(required = false) String mscid, @RequestParam(required = false) Integer startMonth,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endMonth,
			@RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		MscFilter mscFilter = new MscFilter();
		List<Msc> listMsc = mscDao.filter(mscFilter);
		mscid = ModelAddtributes.checkMsc(model, listMsc, mscid, "msc-ho-gsm");
		String[] s = ModelAddtributes.checkMonth(model, startMonth, endMonth, startYear, endYear).split(";");
		List<VRpDyMscHoGsm> dyMscList = dyMscDAO.filterMonthly("V_RP_MN_MSC_HO_GSM", mscid, s[0], s[1], s[2], s[3]);
		model.addAttribute("dyMscList", dyMscList);
		List<HighlightConfig> highlightConfigList = highlightDao.getByKey("TT");
		model.addAttribute("highlight", Helper.highLightLine(highlightConfigList, "dyMscList"));
		return "dyMscHoGsm";
	}
}