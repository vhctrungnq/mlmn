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
import vn.com.vhc.vmsc2.statistics.dao.VRpDyIrsrAlarmRegion2gDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnIrsrAlarmRegion2gDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpQrIrsrAlarmRegion2gDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpYrIrsrAlarmRegion2gDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyIrsrAlarmRegion2g;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnIrsrAlarmRegion2g;
import vn.com.vhc.vmsc2.statistics.domain.VRpQrIrsrAlarmRegion2g;
import vn.com.vhc.vmsc2.statistics.domain.VRpYrIrsrAlarmRegion2g;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
public class IrsrAlarmRegion2gController extends BaseController {
	@Autowired
	private VRpDyIrsrAlarmRegion2gDAO vRpDyIrsrAlarmRegion2gDao;
	@Autowired
	private VRpMnIrsrAlarmRegion2gDAO vRpMnIrsrAlarmRegion2gDao;
	@Autowired
	private VRpQrIrsrAlarmRegion2gDAO vRpQrIrsrAlarmRegion2gDao;
	@Autowired
	private VRpYrIrsrAlarmRegion2gDAO vRpYrIrsrAlarmRegion2gDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("/report/radio/irsr/by-region/dy/list")
	public ModelAndView listByRegion(@RequestParam(required = false) String region,@RequestParam(required = false) Date startDate,
			 @RequestParam(required = false) Date endDate, ModelMap model) {
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

		List<VRpDyIrsrAlarmRegion2g> dyIrsrAlarmRegion2gList = vRpDyIrsrAlarmRegion2gDao.filter(region, df.format(startDate), df.format(endDate));

		model.addAttribute("region", region);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("dyIrsrAlarmRegion2gList", dyIrsrAlarmRegion2gList);

		return new ModelAndView("irsrAlarmRegion2gDy");
	}

	@RequestMapping("/report/radio/irsr/by-region/mn/list")
	public ModelAndView mnListBadByRegion(@RequestParam(required = false) String region, @RequestParam(required = false) Integer startMonth,
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

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";
		
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		model.addAttribute("region", region);

		List<VRpMnIrsrAlarmRegion2g> mnIrsrAlarmRegion2gList = vRpMnIrsrAlarmRegion2gDao.filter(region, startMonth, startYear, endMonth, endYear);

		model.addAttribute("mnIrsrAlarmRegion2gList", mnIrsrAlarmRegion2gList);

		return new ModelAndView("irsrAlarmRegion2gMn");
	}
	
	@RequestMapping("/report/radio/irsr/by-region/qr/list")
	public ModelAndView qrListBadByRegion(@RequestParam(required = false) String region, @RequestParam(required = false) Integer startQuarter,
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
		
		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";
		
		model.addAttribute("startQuarter", startQuarter);
		model.addAttribute("endQuarter", endQuarter);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		model.addAttribute("region", region);

		List<VRpQrIrsrAlarmRegion2g> qrIrsrAlarmRegion2gList = vRpQrIrsrAlarmRegion2gDao.filter(region, startQuarter, startYear, endQuarter, endYear);

		model.addAttribute("qrIrsrAlarmRegion2gList", qrIrsrAlarmRegion2gList);

		return new ModelAndView("irsrAlarmRegion2gQr");
	}
	
	@RequestMapping("/report/radio/irsr/by-region/yr/list")
	public ModelAndView yrListBadByRegion(@RequestParam(required = false) String region, @RequestParam(required = false) Integer startYear,
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

		if (region == null)
			region = getCenter("TT2");
		
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		model.addAttribute("region", region);

		List<VRpYrIrsrAlarmRegion2g> yrIrsrAlarmRegion2gList = vRpYrIrsrAlarmRegion2gDao.filter(region, startYear, endYear);

		model.addAttribute("yrIrsrAlarmRegion2gList", yrIrsrAlarmRegion2gList);

		return new ModelAndView("irsrAlarmRegion2gYr");
	}
}
