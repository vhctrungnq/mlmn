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
import vn.com.vhc.vmsc2.statistics.dao.VRpDyIrsrAlarmBsc2gDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnIrsrAlarmBsc2gDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpQrIrsrAlarmBsc2gDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpYrIrsrAlarmBsc2gDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyIrsrAlarmBsc2g;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnIrsrAlarmBsc2g;
import vn.com.vhc.vmsc2.statistics.domain.VRpQrIrsrAlarmBsc2g;
import vn.com.vhc.vmsc2.statistics.domain.VRpYrIrsrAlarmBsc2g;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
public class IrsrAlarmBsc2gController extends BaseController {
	@Autowired
	private VRpDyIrsrAlarmBsc2gDAO vRpDyIrsrAlarmBsc2gDao;
	@Autowired
	private VRpMnIrsrAlarmBsc2gDAO vRpMnIrsrAlarmBsc2gDao;
	@Autowired
	private VRpQrIrsrAlarmBsc2gDAO vRpQrIrsrAlarmBsc2gDao;
	@Autowired
	private VRpYrIrsrAlarmBsc2gDAO vRpYrIrsrAlarmBsc2gDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("/report/radio/irsr/by-bsc/dy/list")
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

		List<VRpDyIrsrAlarmBsc2g> dyIrsrAlarmBsc2gList = vRpDyIrsrAlarmBsc2gDao.filter(bscid, df.format(startDate), df.format(endDate), region);

		model.addAttribute("region", region);
		model.addAttribute("bscid", bscid);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("dyIrsrAlarmBsc2gList", dyIrsrAlarmBsc2gList);

		return new ModelAndView("irsrAlarmBsc2gDy");
	}

	@RequestMapping("/report/radio/irsr/by-bsc/mn/list")
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

		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		model.addAttribute("bscid", bscid);

		List<VRpMnIrsrAlarmBsc2g> mnIrsrAlarmBsc2gList = vRpMnIrsrAlarmBsc2gDao.filter(bscid, startMonth, startYear, endMonth, endYear);

		model.addAttribute("mnIrsrAlarmBsc2gList", mnIrsrAlarmBsc2gList);

		return new ModelAndView("irsrAlarmBsc2gMn");
	}
	
	@RequestMapping("/report/radio/irsr/by-bsc/qr/list")
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

		model.addAttribute("startQuarter", startQuarter);
		model.addAttribute("endQuarter", endQuarter);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		model.addAttribute("bscid", bscid);

		List<VRpQrIrsrAlarmBsc2g> qrIrsrAlarmBsc2gList = vRpQrIrsrAlarmBsc2gDao.filter(bscid, startQuarter, startYear, endQuarter, endYear);

		model.addAttribute("qrIrsrAlarmBsc2gList", qrIrsrAlarmBsc2gList);

		return new ModelAndView("irsrAlarmBsc2gQr");
	}
	
	@RequestMapping("/report/radio/irsr/by-bsc/yr/list")
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

		List<VRpYrIrsrAlarmBsc2g> yrIrsrAlarmBsc2gList = vRpYrIrsrAlarmBsc2gDao.filter(bscid, startYear, endYear);

		model.addAttribute("yrIrsrAlarmBsc2gList", yrIrsrAlarmBsc2gList);

		return new ModelAndView("irsrAlarmBsc2gYr");
	}
}
