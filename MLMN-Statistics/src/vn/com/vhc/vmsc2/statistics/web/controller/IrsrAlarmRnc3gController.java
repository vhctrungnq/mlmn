package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyIrsrAlarmBsc3gDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnIrsrAlarmBsc3gDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpQrIrsrAlarmBsc3gDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpYrIrsrAlarmBsc3gDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyIrsrAlarmBsc3g;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnIrsrAlarmBsc3g;
import vn.com.vhc.vmsc2.statistics.domain.VRpQrIrsrAlarmBsc3g;
import vn.com.vhc.vmsc2.statistics.domain.VRpYrIrsrAlarmBsc3g;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
public class IrsrAlarmRnc3gController extends BaseController {
	@Autowired
	private VRpDyIrsrAlarmBsc3gDAO vRpDyIrsrAlarmBsc3gDao;
	@Autowired
	private VRpMnIrsrAlarmBsc3gDAO vRpMnIrsrAlarmBsc3gDao;
	@Autowired
	private VRpQrIrsrAlarmBsc3gDAO vRpQrIrsrAlarmBsc3gDao;
	@Autowired
	private VRpYrIrsrAlarmBsc3gDAO vRpYrIrsrAlarmBsc3gDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("/report/radio3g/irsr/by-rnc/dy/list")
	public ModelAndView listByBsc(@RequestParam(required = false) String region, @RequestParam(required = false) String bscid,
			@RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, ModelMap model) {
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";

		List<VRpDyIrsrAlarmBsc3g> dyIrsrAlarmBsc3gList = vRpDyIrsrAlarmBsc3gDao.filter(bscid, df.format(startDate), df.format(endDate), region);

		model.addAttribute("region", region);
		model.addAttribute("bscid", bscid);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("dyIrsrAlarmBsc3gList", dyIrsrAlarmBsc3gList);

		return new ModelAndView("irsrAlarmBsc3gDy");
	}

	@RequestMapping("/report/radio3g/irsr/by-rnc/mn/list")
	public ModelAndView mnListBadByBsc(@RequestParam(required = false) String bscid, @RequestParam(required = false) Integer startMonth,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endMonth,
			@RequestParam(required = false) Integer endYear, ModelMap model) {
		Calendar cal = Calendar.getInstance();
		if (endMonth == null) {
			cal.add(Calendar.MONTH, -1);
			endMonth = cal.get(Calendar.MONTH) + 1;
			endYear = cal.get(Calendar.YEAR);
		}

		if (startMonth == null) {
			startMonth = endMonth;
			startYear = endYear;
		}
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		model.addAttribute("bscid", bscid);
		model.addAttribute("monthList", monthList);

		List<VRpMnIrsrAlarmBsc3g> mnIrsrAlarmBsc3gList = vRpMnIrsrAlarmBsc3gDao.filter(bscid, startMonth, startYear, endMonth, endYear);

		model.addAttribute("mnIrsrAlarmBsc3gList", mnIrsrAlarmBsc3gList);

		return new ModelAndView("irsrAlarmBsc3gMn");
	}
	
	@RequestMapping("/report/radio3g/irsr/by-rnc/qr/list")
	public ModelAndView qrListBadByBsc(@RequestParam(required = false) String bscid, @RequestParam(required = false) Integer startQuarter,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endQuarter,
			@RequestParam(required = false) Integer endYear, ModelMap model) {
		Calendar calender = Calendar.getInstance();
		if (endYear ==null)
		{
			endYear = calender.get(Calendar.YEAR);
		}
		if (endQuarter == null)
			endQuarter = Helper.getQuarter();
		
		if (startYear ==null)
		{
			startYear = endYear;
		}
		if (startQuarter == null)
			startQuarter = endQuarter;
		if (startYear > endYear) 
			startYear = endYear;
		if (startQuarter> endQuarter&& startYear>=endYear){
			startQuarter = endQuarter;
			startYear = endYear;
		}
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		model.addAttribute("startQuarter", startQuarter);
		model.addAttribute("endQuarter", endQuarter);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		model.addAttribute("bscid", bscid);
		model.addAttribute("monthList", monthList);

		List<VRpQrIrsrAlarmBsc3g> qrIrsrAlarmBsc3gList = vRpQrIrsrAlarmBsc3gDao.filter(bscid, startQuarter, startYear, endQuarter, endYear);

		model.addAttribute("qrIrsrAlarmBsc3gList", qrIrsrAlarmBsc3gList);

		return new ModelAndView("irsrAlarmBsc3gQr");
	}
	
	@RequestMapping("/report/radio3g/irsr/by-rnc/yr/list")
	public ModelAndView yrListBadByBsc(@RequestParam(required = false) String bscid, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endYear, ModelMap model) {
		Calendar calender = Calendar.getInstance();
		if (endYear ==null)
		{
			endYear = calender.get(Calendar.YEAR);
		}
		
		if (startYear ==null)
		{
			startYear = endYear;
		}
		
		if (startYear > endYear) 
			startYear = endYear;

		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		model.addAttribute("bscid", bscid);

		List<VRpYrIrsrAlarmBsc3g> yrIrsrAlarmBsc3gList = vRpYrIrsrAlarmBsc3gDao.filter(bscid, startYear, endYear);

		model.addAttribute("yrIrsrAlarmBsc3gList", yrIrsrAlarmBsc3gList);

		return new ModelAndView("irsrAlarmBsc3gYr");
	}
}
