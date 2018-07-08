package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyBscCploadQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrBscCploadQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnBscCploadQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkBscCploadQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.DyBscCploadQos;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HrBscCploadQos;
import vn.com.vhc.vmsc2.statistics.domain.MnBscCploadQos;
import vn.com.vhc.vmsc2.statistics.domain.WkBscCploadQos;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;

@Controller
@RequestMapping("/report/core/bsc/cpload/*")
public class BscCploadQosController extends BaseController{
	@Autowired
	private DyBscCploadQosDAO dyBscCploadQosDao;
	@Autowired
	private HrBscCploadQosDAO hrBscCploadQosDao;
	@Autowired
	private MnBscCploadQosDAO mnBscCploadQosDao;
	@Autowired
	private WkBscCploadQosDAO wkBscCploadQosDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private BscDAO bscDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("dy")
	public ModelAndView dyList(@RequestParam(required = false) String region,@RequestParam(required = false) String bscid, @RequestParam(required = false) Date startDate,
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
		
		List<DyBscCploadQos> dyBscCploadQos = dyBscCploadQosDao.filter(df.format(startDate), df.format(endDate), bscid, region);
		
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("dyBscCploadQos", dyBscCploadQos);
		model.addAttribute("region", region);
		
		return new ModelAndView("dyBscCploadQos");
	}
	
	@RequestMapping("hr")
	public ModelAndView hrList(@RequestParam(required = false) String region,@RequestParam(required = true) String bscid, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
			ModelMap model) {
		long currentTime = System.currentTimeMillis();

		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		if (endHour == null) {
			endHour = "23";
		} else {
			endHour = endHour.substring(0, endHour.indexOf(":"));
		}
		if (startHour == null) {
			startHour = "0";
		} else {
			startHour = startHour.substring(0, startHour.indexOf(":"));
		}
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("startHour", startHour + ":00");
		model.addAttribute("endHour", endHour + ":00");

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";
		
		String[] hourList = {"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
		model.addAttribute("hourList", hourList); 
		
		List<HrBscCploadQos> hrBscCploadQosList = new ArrayList<HrBscCploadQos>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			hrBscCploadQosList.addAll(hrBscCploadQosDao.filter(dates.get(day).get("startHour"), dates.get(day).get("endHour"), day, bscid, region));
		}
		model.addAttribute("hrBscCploadQosList", hrBscCploadQosList);
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		model.addAttribute("region", region);
		
		return new ModelAndView("hrBscCploadQos");
	}
	
	@RequestMapping("mn")
	public ModelAndView mnList( @RequestParam(required = false) String region,@RequestParam(required = false) String bscid,
			@RequestParam(required = false) Integer startMonth,@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear,
			ModelMap model, HttpServletRequest request) {

		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		if(endMonth==null){
			cal.add(Calendar.MONTH,-1);
			endMonth = cal.get(Calendar.MONTH)+1;
			endYear = cal.get(Calendar.YEAR);
		}
		
		if (startMonth == null) {
			if (endMonth > 3) {
				startMonth = endMonth -3;
				startYear = endYear;
			}
			else {
				cal1.add(Calendar.MONTH,-3);
				startMonth = cal1.get(Calendar.MONTH)+1;
				startYear = cal1.get(Calendar.YEAR);
			}
		}
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		model.addAttribute("monthList", monthList);
		
		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";

		List<MnBscCploadQos> mnBscCploadQosList = mnBscCploadQosDao.filter(startMonth,startYear, endMonth, endYear, bscid, region);

		model.addAttribute("mnBscCploadQosList", mnBscCploadQosList);
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		model.addAttribute("region", region);
		
		return new ModelAndView("mnBscCploadQos");
	}
	
	@RequestMapping("wk")
	public ModelAndView wkList(@RequestParam(required = false) String region,@RequestParam(required = false) String bscid,
			@RequestParam(required = false) Integer startWeek,@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear,
			ModelMap model, HttpServletRequest request) {

		DateTime dt = new DateTime();
		if (endWeek == null) {
			dt = dt.minusWeeks(1);
			endWeek = dt.getWeekOfWeekyear();
			endYear = dt.getYear();
		}
		if (startWeek == null) {
			dt = dt.minusWeeks(5);
			startWeek = dt.getWeekOfWeekyear();
			startYear = dt.getYear();
		}
		model.addAttribute("startWeek", startWeek);
		model.addAttribute("endWeek", endWeek);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";

		List<WkBscCploadQos> wkBscCploadQosList = wkBscCploadQosDao.filter(startWeek,startYear, endWeek, endYear, bscid, region);

		model.addAttribute("wkBscCploadQosList", wkBscCploadQosList);
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		model.addAttribute("region", region);
		
		return new ModelAndView("wkBscCploadQos");
	}
}
