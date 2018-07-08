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

import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrCellQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnCellQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkCellQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.VRpDYCellQos;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrCellQos;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnCellQos;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkCellQos;
import vn.com.vhc.vmsc2.statistics.web.filter.CellFilter;
@Controller
@RequestMapping("/report/radio/bad-cell/hightraffic/*")
public class Highttraffic2gController extends BaseController{
		@Autowired
		private VRpHrCellQosDAO VrphrcellDAO;
		@Autowired
		private VRpDyCellQosDAO VrpdycellDAO;
		@Autowired
		private VRpWkCellQosDAO VRpWkCellQosDAO;
		@Autowired
		private VRpMnCellQosDAO VRpMnCellQosDAO;
		private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		@RequestMapping("hr")
		public String showReport(@RequestParam(required = false) String bscid, @RequestParam(required = false) String cellid, @RequestParam(required = false) String startHour,
				@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
				@ModelAttribute("filterhr") CellFilter filter, ModelMap model, HttpServletRequest request)
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
					model.addAttribute("bscid", bscid);
					model.addAttribute("cellid", cellid);
					String[] hourList = {"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
					model.addAttribute("hourList", hourList);
				    List<VRpHrCellQos> VrphrCellQos = VrphrcellDAO.filterhr(startHour,startDate,endHour,endDate,bscid,cellid);
				    if(endDate.compareTo(startDate) < 0)
					{
						startDate = new Date();
						VrphrCellQos = VrphrcellDAO.filterhr2(startHour,startDate,endHour,bscid,cellid);
					}
					else if(endDate.compareTo(startDate)== 0)
					{
						
						VrphrCellQos = VrphrcellDAO.filterhr2(startHour,startDate,endHour,bscid,cellid);
					}
					model.addAttribute("VrphrCellQos", VrphrCellQos);
					return "HrhighTFCelllist";
				}
		@RequestMapping("dy")
		public String dyList(@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String cellid,
			@ModelAttribute("filterhigh") CellFilter filter,
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
					if(cellid == null){
						cellid = "";
					}
					model.addAttribute("startDate", df.format(startDate));
					model.addAttribute("endDate", df.format(endDate));
					List<VRpDYCellQos> Vrpdycell = VrpdycellDAO.filterhigh(df.format(startDate), df.format(endDate), bscid, cellid);
					model.addAttribute("Vrpdycell", Vrpdycell);
					
					return "DyhighTFCelllist";
				}
		
		@RequestMapping("wk")
		public String wkList(@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String cellid,
			@ModelAttribute("filterhigh") CellFilter filter,
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
					if(cellid == null){
						cellid = "";
					}
					model.addAttribute("startWeek", startWeek);
					model.addAttribute("endWeek", endWeek);
					model.addAttribute("startYear", startYear);
					model.addAttribute("endYear", endYear);
					List<VRpWkCellQos> Vrpwkcell = VRpWkCellQosDAO.filterhigh(startWeek, startYear, endWeek, endYear, bscid, cellid);
					model.addAttribute("Vrpwkcell", Vrpwkcell);
					
					return "wkHighTFCelllist";
				}
		@RequestMapping("mn")
		public ModelAndView mnList(@RequestParam(required = false) String bscid,
				@RequestParam(required = false) String cellid,
				@ModelAttribute("filterhigh") CellFilter filter,
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
			if(cellid == null){
				cellid = "";
			}
			model.addAttribute("startMonth", startMonth);
			model.addAttribute("endMonth", endMonth);
			model.addAttribute("startYear", startYear);
			model.addAttribute("endYear", endYear);
			model.addAttribute("bscid", bscid);
			model.addAttribute("cellid", cellid);
			
		    String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		    model.addAttribute("monthList", monthList);
			List<VRpMnCellQos> VrpMncell = VRpMnCellQosDAO.filterhigh(startMonth,startYear, endMonth, endYear, bscid, cellid);
			model.addAttribute("VrpMncell", VrpMncell);

			return new ModelAndView("mnHighTFCelllist");
		}
}
