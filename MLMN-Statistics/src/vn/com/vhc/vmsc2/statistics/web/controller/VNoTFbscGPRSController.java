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

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.Vdybscdata2gDAO;
import vn.com.vhc.vmsc2.statistics.dao.Vhrbscdata2gDAO;
import vn.com.vhc.vmsc2.statistics.dao.Vmnbscdata2gDAO;
import vn.com.vhc.vmsc2.statistics.dao.Vwkbscdata2gDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.Vdybscdata2g;
import vn.com.vhc.vmsc2.statistics.domain.Vhrbscdata2g;
import vn.com.vhc.vmsc2.statistics.domain.Vmnbscdata2g;
import vn.com.vhc.vmsc2.statistics.domain.Vwkbscdata2g;
import vn.com.vhc.vmsc2.statistics.web.filter.CellFilter;

@Controller
@RequestMapping("/report/radio/bsc-gprs-no-traff/*")
public class VNoTFbscGPRSController extends BaseController{
	@Autowired
	private Vhrbscdata2gDAO hrbsc2GDAO;
	@Autowired
	private Vdybscdata2gDAO VdyBSC2GDAO;
	@Autowired
	private Vwkbscdata2gDAO VwkBSCGPRSDAO;
	@Autowired
	private Vmnbscdata2gDAO VmnBSCGPRSDAO;
	@Autowired
	private BscDAO bscDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	@RequestMapping("hr/list")
	public String showReport(@RequestParam(required = false) String bscid, @RequestParam(required = false) String startHour, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) String endHour,@RequestParam(required = false) Date endDate,
			@ModelAttribute("filterTF") CellFilter filter, ModelMap model, HttpServletRequest request)
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
				
				int startRecord = 0;
			    try {
			     startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("cell").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
			    } catch (NumberFormatException e) {
			    }
			    int endRecord = startRecord + 100;
			    filter.setStartRecord(startRecord);
			    filter.setEndRecord(endRecord);
			    String[] hourList = {"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
			    List<Vhrbscdata2g> hrBSCGPRS = hrbsc2GDAO.filterTF(startHour,startDate,endHour,endDate,bscid);
				
			    if(endDate.compareTo(startDate) < 0)
				{
					startDate = new Date();
					hrBSCGPRS = hrbsc2GDAO.filterTF2(startHour,startDate,endHour,bscid);
				}
				else if(endDate.compareTo(startDate)== 0)
				{
					
					hrBSCGPRS = hrbsc2GDAO.filterTF2(startHour,startDate,endHour,bscid);
				}
			    
			    List<Bsc> bscList = bscDAO.getAll();
				model.addAttribute("bscid", bscid);
				model.addAttribute("bscList", bscList);
				model.addAttribute("hrBSCGPRS", hrBSCGPRS);
				model.addAttribute("hourList", hourList);
				return "hrNoTFBSCGPRSlist";
			}
	@RequestMapping("dy/list")
	public String showReport(@RequestParam(required = true) String bscid,
			@RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
			@ModelAttribute("filter") CellFilter filter, ModelMap model, HttpServletRequest request)
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
			
			model.addAttribute("startDate", df.format(startDate));
			model.addAttribute("endDate", df.format(endDate));
			
		    
			List<Vdybscdata2g> dyBSCGPRS = VdyBSC2GDAO.filter2(df.format(startDate), df.format(endDate), bscid);
			List<Bsc> bscList = bscDAO.getAll();
			model.addAttribute("bscid", bscid);
			model.addAttribute("bscList", bscList);
			model.addAttribute("dyBSCGPRS", dyBSCGPRS);
			return "dyNoTFBSCGPRSlist";
			
		}
	@RequestMapping("wk/list")
	public ModelAndView wkList(@RequestParam(required = false) String bscid,
			@RequestParam(required = false) Integer startWeek,
			@RequestParam(required = false) Integer startYear, 
			@RequestParam(required = false) Integer endWeek,
			@RequestParam(required = false) Integer endYear, 
			ModelMap model, HttpServletRequest request) {
		DateTime dt = new DateTime();
		if (endWeek == null) {
			dt = dt.minusWeeks(1);
			endWeek = dt.getWeekOfWeekyear();
			endYear = dt.getYear();
		}
		if(bscid == null){
			bscid = "";
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
		
		List<Vwkbscdata2g> VwkBSCGPRS = VwkBSCGPRSDAO.filter2(startWeek,startYear, endWeek, endYear, bscid);
		model.addAttribute("bscid", bscid);
		model.addAttribute("VwkBSCGPRS", VwkBSCGPRS);
		List<Bsc> bscList = bscDAO.getAll();
		model.addAttribute("bscid", bscid);
		model.addAttribute("bscList", bscList);

		return new ModelAndView("WkNoTFbsc2gList");
	}
	@RequestMapping("mn/list")
	public ModelAndView mnList(@RequestParam(required = false) String bscid,
			@RequestParam(required = false) Integer startMonth,
			@RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endMonth,
			@RequestParam(required = false) Integer endYear, 
			ModelMap model, HttpServletRequest request) {
		Calendar cal = Calendar.getInstance();
		/*Calendar cal1 = Calendar.getInstance();*/
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
				cal.add(Calendar.MONTH,-3);
				startMonth = cal.get(Calendar.MONTH)+1;
				startYear = cal.get(Calendar.YEAR);
			}
		}
		
		if(bscid == null){
			bscid = "";
		}
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		
		List<Vmnbscdata2g> Vmnbsc2g = VmnBSCGPRSDAO.filter2(startMonth,startYear, endMonth, endYear, bscid);
		
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

		model.addAttribute("Vmnbsc2g", Vmnbsc2g);
		List<Bsc> bscList = bscDAO.getAll();
		model.addAttribute("bscid", bscid);
		model.addAttribute("bscList", bscList);
		model.addAttribute("monthList", monthList);

		return new ModelAndView("mnNoTFbsc2gList");
	}

}