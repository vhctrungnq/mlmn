package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import vn.com.vhc.vmsc2.statistics.dao.Vdycell3gQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.Vhrcell3gQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.Vmncell3gQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.Vwkcell3gQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.Vdycell3gQos;
import vn.com.vhc.vmsc2.statistics.domain.Vhrcell3gQos;
import vn.com.vhc.vmsc2.statistics.domain.Vmncell3gQos;
import vn.com.vhc.vmsc2.statistics.domain.Vwkcell3gQos;
import vn.com.vhc.vmsc2.statistics.web.filter.CellFilter;

@Controller
@RequestMapping("/report/radio3g/cell-available/*")
public class UnCell3GController extends BaseController{
	@Autowired
	private Vhrcell3gQosDAO VhrCell3gDAO;
	@Autowired
	private Vdycell3gQosDAO VdyCell3gDAO;
	@Autowired
	private Vwkcell3gQosDAO VwkCell3gDAO;
	@Autowired
	private Vmncell3gQosDAO VmnCell3gDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	@RequestMapping("hr")
	public String showReport(@RequestParam(required = false) String bscid, @RequestParam(required = false) String cellid, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
			@ModelAttribute("filter") CellFilter filter, ModelMap model, HttpServletRequest request)
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
				
				int startRecord = 0;
			    try {
			     startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("cell").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
			    } catch (NumberFormatException e) {
			    }
			    int endRecord = startRecord + 100;
			    filter.setStartRecord(startRecord);
			    filter.setEndRecord(endRecord);
			    String[] hourList = {"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
			    List<Vhrcell3gQos> hrcell3g = VhrCell3gDAO.filter(startHour,startDate,endHour,endDate,bscid,cellid);
				
			    if(endDate.compareTo(startDate) < 0)
				{
					startDate = new Date();
					hrcell3g = VhrCell3gDAO.filter2(startHour,startDate,endHour,bscid,cellid);
				}
				else if(endDate.compareTo(startDate)== 0)
				{
					
					hrcell3g = VhrCell3gDAO.filter2(startHour,startDate,endHour,bscid,cellid);
				}
			    
			  
				model.addAttribute("hrcell3g", hrcell3g);
				model.addAttribute("hourList", hourList);
				return "hrUnCell3glist";
			}
	
	@RequestMapping("dy")
	public String dyList(@RequestParam(required = false) String bscid,
		@RequestParam(required = false) String cellid,
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
				if(cellid == null){
					cellid = "";
				}
				model.addAttribute("startDate", df.format(startDate));
				model.addAttribute("endDate", df.format(endDate));
				List<Vdycell3gQos> Vdycell3g = VdyCell3gDAO.filter(df.format(startDate), df.format(endDate), bscid, cellid);
				model.addAttribute("Vdycell3g", Vdycell3g);
				
				return "DyUncell3glist";
			}
	
	@RequestMapping("wk")
	public String wkList(@RequestParam(required = false) String bscid,
		@RequestParam(required = false) String cellid,
		@ModelAttribute("filter") CellFilter filter,
		@RequestParam(required = false) Integer startWeek,
		@RequestParam(required = false) Integer startYear, 
		@RequestParam(required = false) Integer endWeek,
		@RequestParam(required = false) Integer endYear, 
		 ModelMap model)
			{
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
				List<Vwkcell3gQos> Vwkcell3g = VwkCell3gDAO.filter(startWeek, startYear, endWeek, endYear, bscid, cellid);
				model.addAttribute("Vwkcell3g", Vwkcell3g);
				
				return "WkUncell3glist";
			}
	@RequestMapping("mn")
	public ModelAndView mnList(@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String cellid,
			@ModelAttribute("filter") CellFilter filter,
			@RequestParam(required = false) Integer startMonth,
			@RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endMonth,
			@RequestParam(required = false) Integer endYear, 
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
		List<Vmncell3gQos> Vmncell3g = VmnCell3gDAO.filter(startMonth,startYear, endMonth, endYear, bscid, cellid);
		model.addAttribute("Vmncell3g", Vmncell3g);

		return new ModelAndView("MnUncell3glist");
	}
}
