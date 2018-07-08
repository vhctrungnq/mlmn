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
import vn.com.vhc.vmsc2.statistics.dao.VRpDyIrsrAlarmDistrict2gDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnIrsrAlarmDistrict2gDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpQrIrsrAlarmDistrict2gDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpYrIrsrAlarmDistrict2gDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyIrsrAlarmDistrict2g;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnIrsrAlarmDistrict2g;
import vn.com.vhc.vmsc2.statistics.domain.VRpQrIrsrAlarmDistrict2g;
import vn.com.vhc.vmsc2.statistics.domain.VRpYrIrsrAlarmDistrict2g;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
public class IrsrAlarmDistrict2gController extends BaseController {
	@Autowired
	private VRpDyIrsrAlarmDistrict2gDAO vRpDyIrsrAlarmDistrict2gDao;
	@Autowired
	private VRpMnIrsrAlarmDistrict2gDAO vRpMnIrsrAlarmDistrict2gDao;
	@Autowired
	private VRpQrIrsrAlarmDistrict2gDAO vRpQrIrsrAlarmDistrict2gDao;
	@Autowired
	private VRpYrIrsrAlarmDistrict2gDAO vRpYrIrsrAlarmDistrict2gDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("/report/radio/irsr/by-district/dy/list")
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

		List<VRpDyIrsrAlarmDistrict2g> dyIrsrAlarmDistrict2gList = vRpDyIrsrAlarmDistrict2gDao.filter(region, province, district, df.format(startDate), df.format(endDate));

		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("district", district);
		model.addAttribute("region", region);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("dyIrsrAlarmDistrict2gList", dyIrsrAlarmDistrict2gList);

		return new ModelAndView("irsrAlarmDistrict2gDy");
	}

	@RequestMapping("/report/radio/irsr/by-district/mn/list")
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
		
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("district", district);
		model.addAttribute("region", region);

		List<VRpMnIrsrAlarmDistrict2g> mnIrsrAlarmDistrict2gList = vRpMnIrsrAlarmDistrict2gDao.filter(region, province, district, startMonth, startYear, endMonth, endYear);

		model.addAttribute("mnIrsrAlarmDistrict2gList", mnIrsrAlarmDistrict2gList);

		return new ModelAndView("irsrAlarmDistrict2gMn");
	}
	
	@RequestMapping("/report/radio/irsr/by-district/qr/list")
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
		
		model.addAttribute("startQuarter", startQuarter);
		model.addAttribute("endQuarter", endQuarter);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("district", district);
		model.addAttribute("region", region);

		List<VRpQrIrsrAlarmDistrict2g> qrIrsrAlarmDistrict2gList = vRpQrIrsrAlarmDistrict2gDao.filter(region, province, district, startQuarter, startYear, endQuarter, endYear);

		model.addAttribute("qrIrsrAlarmDistrict2gList", qrIrsrAlarmDistrict2gList);

		return new ModelAndView("irsrAlarmDistrict2gQr");
	}
	
	@RequestMapping("/report/radio/irsr/by-district/yr/list")
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

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";
		
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("district", district);
		model.addAttribute("region", region);

		List<VRpYrIrsrAlarmDistrict2g> yrIrsrAlarmDistrict2gList = vRpYrIrsrAlarmDistrict2gDao.filter(region, province, district, startYear, endYear);

		model.addAttribute("yrIrsrAlarmDistrict2gList", yrIrsrAlarmDistrict2gList);

		return new ModelAndView("irsrAlarmDistrict2gYr");
	}
}
