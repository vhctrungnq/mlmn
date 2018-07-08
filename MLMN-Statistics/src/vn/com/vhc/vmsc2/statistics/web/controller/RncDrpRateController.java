package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.MscDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyBsc3gQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrBsc3gQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnBsc3gQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkBsc3gQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.Msc;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBsc3gQos;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrBsc3gQos;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnBsc3gQos;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkBsc3gQos;
import vn.com.vhc.vmsc2.statistics.web.filter.CellFilter;
@Controller
@RequestMapping("/report/radio3g/rnc-drop-rate/*")
public class RncDrpRateController extends BaseController{
	@Autowired
	private VRpHrBsc3gQosDAO vRpHrBsc3gQosDAO;
	@Autowired
	private VRpDyBsc3gQosDAO vRpDyBsc3gQosDAO;
	@Autowired
	private VRpWkBsc3gQosDAO vRpWkBsc3gQosDAO;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private VRpMnBsc3gQosDAO vRpMnBsc3gQosDAO;
	@Autowired
	private MscDAO mscDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	@RequestMapping("hr")
	public String hrList(@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String mscid,
			@RequestParam(required = false) String region,
			@RequestParam(required = false) String startHour,
			@RequestParam(required = false) Date startDate,
			@RequestParam(required = false) String endHour,
			@RequestParam(required = false) Date endDate,
			@ModelAttribute("filter") CellFilter filter,
			ModelMap model, HttpServletRequest request)
			{
		
				if (endDate == null) {
					endDate = new Date();
				}
				if (startDate == null) {
					startDate = endDate;
				}
				if (endHour == null) {
					endHour = "23";
				} else {
					if(endHour.indexOf(":") > 0){
						endHour = endHour.substring(0, endHour.indexOf(":"));
					}
					else{
						endHour = "23";
					}
				}
				if (startHour == null) {
					startHour = "0";
				} else {
					if(startHour.indexOf(":") > 0 ){
						startHour = startHour.substring(0, startHour.indexOf(":"));
					}else{
						startHour = "0";
					}	
				}
				if(mscid == null){
					mscid = "";
				}
				if(bscid == null){
					bscid = "";
				}
				
				List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
				model.addAttribute("regionList", regionList);
				
				if (region == null)
					region = "";
				
				model.addAttribute("startDate", df.format(startDate));
				model.addAttribute("endDate", df.format(endDate));
				model.addAttribute("startHour", startHour + ":00");
				model.addAttribute("endHour", endHour + ":00");
				model.addAttribute("mscid", mscid);
				model.addAttribute("bscid", bscid);
				model.addAttribute("region", region);
				String[] hourList = {"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
				model.addAttribute("hourList", hourList);
				List<VRpHrBsc3gQos> vRpHrBsc3gQosList = vRpHrBsc3gQosDAO.filterAccessibility1(startHour,startDate, endHour, endDate, bscid, mscid, region);
				if(endDate.compareTo(startDate) < 0)
				{
					startDate = new Date();
					vRpHrBsc3gQosList = vRpHrBsc3gQosDAO.filterAccessibility2(startHour, endHour, endDate, bscid, mscid, region);
				}
				else if(endDate.compareTo(startDate)== 0)
				{
					
					vRpHrBsc3gQosList = vRpHrBsc3gQosDAO.filterAccessibility2(startHour, endHour, endDate, bscid, mscid, region);
				}
				model.addAttribute("vRpHrBsc3gQos", vRpHrBsc3gQosList);
				List<Msc> mscList = mscDAO.getAll();
				model.addAttribute("mscList", mscList);
				return "hrRncDroppedRate";
			}
	@RequestMapping("dy")
	public String dyList(@RequestParam(required = false) String bscid,
		@RequestParam(required = false) String mscid,
		@RequestParam(required = false) String region,
		@ModelAttribute("filter") CellFilter filter,
		 @RequestParam(required = false) Date startDate,
		 @RequestParam(required = false) Date endDate,
		 ModelMap model)
			{
				if (endDate == null) {
					endDate = new Date();
				}
				if (startDate == null) {
					startDate = endDate;
				}
				if(bscid == null){
					bscid = "";
				}
				if(mscid == null){
					mscid = "";
				}
				List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
				model.addAttribute("regionList", regionList);
				
				if (region == null)
					region = "";
				model.addAttribute("startDate", df.format(startDate));
				model.addAttribute("endDate", df.format(endDate));
				model.addAttribute("bscid", bscid);
				model.addAttribute("mscid", mscid);
				model.addAttribute("region", region);
				List<VRpDyBsc3gQos> vRpDyBsc3gQos = vRpDyBsc3gQosDAO.filter1(df.format(startDate), df.format(endDate), bscid, mscid, region);
				model.addAttribute("vRpDyBsc3gQos", vRpDyBsc3gQos);
				
				return "dyRncDroppedRate";
			}	
	@RequestMapping("wk")
	public String wkList(@RequestParam(required = false) String bscid,
		@RequestParam(required = false) String mscid,
		@RequestParam(required = false) String region,
		@ModelAttribute("filter") CellFilter filter,
		@RequestParam(required = false) Integer startWeek,
		@RequestParam(required = false) Integer startYear, 
		@RequestParam(required = false) Integer endWeek,
		@RequestParam(required = false) Integer endYear, 
		 ModelMap model)
			{
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear <1900)
		{
			endYear = calender.get(Calendar.YEAR);
		}
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
				if(bscid == null){
					bscid = "";
				}
				if(mscid == null){
					mscid = "";
				}
				List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
				model.addAttribute("regionList", regionList);
				
				if (region == null)
					region = "";
				model.addAttribute("startWeek", startWeek);
				model.addAttribute("endWeek", endWeek);
				model.addAttribute("startYear", startYear);
				model.addAttribute("endYear", endYear);
				model.addAttribute("bscid", bscid);
				model.addAttribute("mscid", mscid);
				model.addAttribute("region", region);
				List<VRpWkBsc3gQos> vRpWkBsc3gQos = vRpWkBsc3gQosDAO.filter1(startWeek, startYear, endWeek, endYear, bscid, mscid, region);
				model.addAttribute("vRpWkBsc3gQos", vRpWkBsc3gQos);
				
				return "wkRncDroppedRate";
			}
	@RequestMapping("mn")
	public ModelAndView mnList(@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String mscid,
			@RequestParam(required = false) String region,
			@ModelAttribute("filter") CellFilter filter,
			@RequestParam(required = false) Integer startMonth,
			@RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endMonth,
			@RequestParam(required = false) Integer endYear, 
			ModelMap model, HttpServletRequest request) {
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear <1900)
		{
			endYear = calender.get(Calendar.YEAR);
		}
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
		if(bscid == null){
			bscid = "";
		}
		if(mscid == null){
			mscid = "";
		}
		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		
	    String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
	    model.addAttribute("monthList", monthList);
		List<VRpMnBsc3gQos> vRpMnBsc3gQos = vRpMnBsc3gQosDAO.filter1(startMonth, startYear, endMonth, endYear, bscid, mscid, region);
		model.addAttribute("bscid", bscid);
		model.addAttribute("mscid", mscid);
		model.addAttribute("region", region);
		model.addAttribute("vRpMnBsc3gQos", vRpMnBsc3gQos);

		return new ModelAndView("mnRncDroppedRate");
	}
}