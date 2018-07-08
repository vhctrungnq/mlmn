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

import vn.com.vhc.vmsc2.statistics.dao.VRpDyBscHoDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrBscHoDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnBscHoDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkBscHoDAO;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBscHo;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrBscHo;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnBscHo;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkBscHo;
import vn.com.vhc.vmsc2.statistics.web.filter.CellFilter;

@Controller
@RequestMapping("/report/2g/bsc-low-succ/*")
public class BscLossSuccessHoController extends BaseController{
	@Autowired
	private VRpHrBscHoDAO vRpHrBscHoDao;
	@Autowired
	private VRpDyBscHoDAO vRpDyBscLSHoDao;
	@Autowired
	private VRpWkBscHoDAO vRpWkBscHoDao;
	@Autowired
	private VRpMnBscHoDAO vRpMnBscHoDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping("hr")
	public String showReport(@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour,
			@RequestParam(required = false) Date startDate,
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
				if(bscid == null){
					bscid = "";
				}
				if (endHour == null) {
					endHour = "23";
				} else {
					if(endHour.indexOf(":") >0)
						endHour = endHour.substring(0, endHour.indexOf(":"));
					else
						endHour = "0";
					}
				if (startHour == null) {
					startHour = "0";
				} else {	
					if(startHour.indexOf(":") >0)
						startHour = startHour.substring(0, startHour.indexOf(":"));
					else
						startHour = "0";
				}
				model.addAttribute("startDate", df.format(startDate));
				model.addAttribute("endDate", df.format(endDate));
				model.addAttribute("startHour", startHour + ":00");
				model.addAttribute("endHour", endHour + ":00");
				
			    String[] hourList = {"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
			    List<VRpHrBscHo> VRpHRBSC = vRpHrBscHoDao.filter1(startHour,startDate,endHour,endDate,bscid);
				
			    if(endDate.compareTo(startDate) < 0)
				{
					startDate = new Date();
					VRpHRBSC = vRpHrBscHoDao.filter2(startHour,startDate,endHour,bscid);
				}
				else if(endDate.compareTo(startDate)== 0)
				{					
					VRpHRBSC = vRpHrBscHoDao.filter2(startHour,startDate,endHour,bscid);
				}
			    
				model.addAttribute("bscid", bscid);
				model.addAttribute("VRpHRBSC", VRpHRBSC);
				model.addAttribute("hourList", hourList);
				
				return "hrBscLossSuccessHo";
	}
	@RequestMapping("dy")
	public String showReport(@RequestParam(required=false) String bscid,
			@RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate,
			@ModelAttribute("filter") CellFilter filtercell,
			ModelMap model, HttpServletRequest request) {
		
			if (endDate == null) {
				endDate = new Date();
			}
			if (startDate == null) {
				startDate = endDate;
			}
			if(bscid == null){
				bscid = "";
			}
			model.addAttribute("startDate", df.format(startDate));
			model.addAttribute("endDate", df.format(endDate));
			model.addAttribute("bscid",bscid);
			List<VRpDyBscHo> vRpDyBscLSHo = vRpDyBscLSHoDao.filterLS(df.format(startDate), df.format(endDate), bscid);			
			model.addAttribute("vRpDyBscLSHo", vRpDyBscLSHo);
			model.addAttribute("bscid", bscid);	
			
			return "dyBscLSHo";
	}
	@RequestMapping("wk")
	public ModelAndView showReportList(@RequestParam(required=false) String bscid,
			@RequestParam(required = false) Integer startWeek, 
			@RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endWeek, 
			@RequestParam(required = false) Integer endYear, 
			@ModelAttribute("filter") CellFilter filter,
			ModelMap model, 
			HttpServletRequest request) {		
		DateTime dt = new DateTime();
		if (endWeek == null) {
			dt = dt.minusWeeks(1);
			endWeek = dt.getWeekOfWeekyear();
			endYear = dt.getYear();
		}
		if (startWeek == null) {
			startWeek = endWeek;
			startYear = endYear;
		}
		if(bscid == null){
			bscid = "";
		}
		model.addAttribute("startWeek", startWeek);
		model.addAttribute("endWeek", endWeek);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		model.addAttribute("bscid",bscid);
		
		List<VRpWkBscHo> vRpWkBscHo = vRpWkBscHoDao.filterLS(startWeek.toString(), startYear.toString(), endWeek.toString(), endYear.toString(), bscid);
		model.addAttribute("vRpWkBscHo", vRpWkBscHo);
		
		return new ModelAndView("wkBscLossSuccess");
	}
	@RequestMapping("mn")
	public ModelAndView mnList(@RequestParam(required = false) String bscid,
			@RequestParam(required = false) Integer startMonth,
			@RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endMonth,
			@RequestParam(required = false) Integer endYear,
			@ModelAttribute("filter") CellFilter filtercell,
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
		if(bscid == null){
			bscid = "";
		}
		String[] monthList={"1","2","3","4","5","6","7","8","9","10","11","12"};
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		model.addAttribute("monthList", monthList);
		
		List<VRpMnBscHo> vRpMnBscHolist = vRpMnBscHoDAO.filterLS(startMonth.toString(), endMonth.toString(), startYear.toString(),endYear.toString(), bscid);
		model.addAttribute("bscid", bscid);
		model.addAttribute("vRpMnBscHo", vRpMnBscHolist);

		return new ModelAndView("mnBscLossSuccessHo");
	}
}
