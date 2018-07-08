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
import vn.com.vhc.vmsc2.statistics.dao.VRpDyIrsrAlarmDistrict3gDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnIrsrAlarmDistrict3gDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpQrIrsrAlarmDistrict3gDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpYrIrsrAlarmDistrict3gDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyIrsrAlarmDistrict3g;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnIrsrAlarmDistrict3g;
import vn.com.vhc.vmsc2.statistics.domain.VRpQrIrsrAlarmDistrict3g;
import vn.com.vhc.vmsc2.statistics.domain.VRpYrIrsrAlarmDistrict3g;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
public class IrsrAlarmDistrict3gController extends BaseController {
	@Autowired
	private VRpDyIrsrAlarmDistrict3gDAO vRpDyIrsrAlarmDistrict3gDao;
	@Autowired
	private VRpMnIrsrAlarmDistrict3gDAO vRpMnIrsrAlarmDistrict3gDao;
	@Autowired
	private VRpQrIrsrAlarmDistrict3gDAO vRpQrIrsrAlarmDistrict3gDao;
	@Autowired
	private VRpYrIrsrAlarmDistrict3gDAO vRpYrIrsrAlarmDistrict3gDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("/report/radio3g/irsr/by-district/dy/list")
	public ModelAndView listByRegion(@RequestParam(required = false) String region,@RequestParam(required = false) String province, @RequestParam(required = false) String district, @RequestParam(required = false) Date startDate,
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

		List<VRpDyIrsrAlarmDistrict3g> dyIrsrAlarmDistrict3gList = vRpDyIrsrAlarmDistrict3gDao.filter(region, province, district, df.format(startDate), df.format(endDate));

		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("district", district);
		model.addAttribute("region", region);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("dyIrsrAlarmDistrict3gList", dyIrsrAlarmDistrict3gList);

		return new ModelAndView("irsrAlarmDistrict3gDy");
	}

	@RequestMapping("/report/radio3g/irsr/by-district/mn/list")
	public ModelAndView mnListBadByRegion(@RequestParam(required = false) String region,@RequestParam(required = false) String province, @RequestParam(required = false) String district, @RequestParam(required = false) Integer startMonth,
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
		
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("district", district);
		model.addAttribute("region", region);
		model.addAttribute("monthList", monthList);

		List<VRpMnIrsrAlarmDistrict3g> mnIrsrAlarmDistrict3gList = vRpMnIrsrAlarmDistrict3gDao.filter(region, province, district, startMonth, startYear, endMonth, endYear);

		model.addAttribute("mnIrsrAlarmDistrict3gList", mnIrsrAlarmDistrict3gList);

		return new ModelAndView("irsrAlarmDistrict3gMn");
	}
	
	@RequestMapping("/report/radio3g/irsr/by-district/qr/list")
	public ModelAndView qrListBadByRegion(@RequestParam(required = false) String region,@RequestParam(required = false) String province, @RequestParam(required = false) String district, @RequestParam(required = false) Integer startQuarter,
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
		
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		model.addAttribute("startQuarter", startQuarter);
		model.addAttribute("endQuarter", endQuarter);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("district", district);
		model.addAttribute("region", region);
		model.addAttribute("monthList", monthList);

		List<VRpQrIrsrAlarmDistrict3g> qrIrsrAlarmDistrict3gList = vRpQrIrsrAlarmDistrict3gDao.filter(region, province, district, startQuarter, startYear, endQuarter, endYear);

		model.addAttribute("qrIrsrAlarmDistrict3gList", qrIrsrAlarmDistrict3gList);

		return new ModelAndView("irsrAlarmDistrict3gQr");
	}
	
	@RequestMapping("/report/radio3g/irsr/by-district/yr/list")
	public ModelAndView yrListBadByRegion(@RequestParam(required = false) String region,@RequestParam(required = false) String province, @RequestParam(required = false) String district, @RequestParam(required = false) Integer startYear,
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
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("district", district);
		model.addAttribute("region", region);

		List<VRpYrIrsrAlarmDistrict3g> yrIrsrAlarmDistrict3gList = vRpYrIrsrAlarmDistrict3gDao.filter(region, province, district, startYear, endYear);

		model.addAttribute("yrIrsrAlarmDistrict3gList", yrIrsrAlarmDistrict3gList);

		return new ModelAndView("irsrAlarmDistrict3gYr");
	}
}
