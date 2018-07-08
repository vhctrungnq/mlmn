package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.DyRegionQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrRegionQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnRegionQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.QrRegionQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.YrRegionQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.DyRegionQos;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HrRegionQos;
import vn.com.vhc.vmsc2.statistics.domain.MnRegionQos;
import vn.com.vhc.vmsc2.statistics.domain.QrRegionQos;
import vn.com.vhc.vmsc2.statistics.domain.YrRegionQos;
import vn.com.vhc.vmsc2.statistics.web.filter.CellFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;

@Controller
@RequestMapping("/report/radio/center/*")
public class RegionQosController extends BaseController{
	@Autowired
	private HrRegionQosDAO HrRegionDao;
	@Autowired
	private DyRegionQosDAO DyRegionDao;
	@Autowired
	private MnRegionQosDAO MnRegionDao;
	@Autowired
	private QrRegionQosDAO QrRegionDao;
	@Autowired
	private YrRegionQosDAO YrRegionDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping("hr")
	public String showReport( 
			@RequestParam(required = true) String region,
			@RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour,
			@RequestParam(required = true) Date startDate,
			@RequestParam(required = true) Date endDate,
			@ModelAttribute("filter") CellFilter filter, ModelMap model, HttpServletRequest request)
			{
				
		if (endDate == null) {
			endDate = new Date();
		}
		if (startDate == null) {
			startDate = endDate;
		}
		if(region == null){
			region = "";
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
		List<HrRegionQos> Hrregion = HrRegionDao.filter(startHour,startDate,endHour,endDate,region);
		
		if(endDate.compareTo(startDate) < 0)
		{
			startDate = new Date();
			Hrregion = HrRegionDao.filter2(startHour,startDate,endHour,region);
		}
		else if(endDate.compareTo(startDate)== 0)
		{
			
			Hrregion = HrRegionDao.filter2(startHour,startDate,endHour,region);
		}
		
		List<HProvincesCode> regionlist = hProvincesCodeDao.getAllRegion();
		model.addAttribute("region", region);
		model.addAttribute("regionlist", regionlist);
		model.addAttribute("Hrregion", Hrregion);
		model.addAttribute("hourList", hourList);
				return "HrRegionQoslist";
			}
	
	@RequestMapping("dy")
	public String showReport(@RequestParam(required = false) String region,
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
				model.addAttribute("startDate", df.format(startDate));
				model.addAttribute("endDate", df.format(endDate));
				model.addAttribute("region", region);
				List<DyRegionQos> dyregion = DyRegionDao.filter(region,df.format(startDate), df.format(endDate));
				model.addAttribute("dyregion", dyregion);
				
				List<HProvincesCode> regionlist = hProvincesCodeDao.getAllRegion();
				model.addAttribute("regionlist", regionlist);
				return "DyRegionList";
			}	
	
	@RequestMapping("mn")
	public ModelAndView mnList(@RequestParam(required = false) String region,
			@RequestParam(required = false) Integer startMonth,
			@RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endMonth,
			@RequestParam(required = false) Integer endYear, 
			ModelMap model, HttpServletRequest request) {
		Calendar calender = Calendar.getInstance();
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		if (endYear ==null || endYear <1900)
		{
			endYear = calender.get(Calendar.YEAR);
		}
		if(endMonth==null){
			cal.add(Calendar.MONTH,-1);
			endMonth = cal.get(Calendar.MONTH)+1;
			endYear = cal.get(Calendar.YEAR);
		}
		if(region == null){
			region = "";
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
		model.addAttribute("region", region);
		
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		
		List<MnRegionQos> Mnregion = MnRegionDao.filter(startMonth,startYear, endMonth, endYear, region);

		List<HProvincesCode> regionlist = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionlist", regionlist);
		
		model.addAttribute("regionlist", regionlist);
		model.addAttribute("Mnregion", Mnregion);
		model.addAttribute("monthList", monthList);

		return new ModelAndView("MnRegionList");
	}
	
	@RequestMapping("qr")
	public ModelAndView qrListBadByRegion(@RequestParam(required = false) String region, @RequestParam(required = false) Integer startQuarter,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endQuarter,
			@RequestParam(required = false) Integer endYear, ModelMap model) {
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
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
		
		if (region == null)
			region = "";
		
		model.addAttribute("startQuarter", startQuarter);
		model.addAttribute("endQuarter", endQuarter);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		model.addAttribute("region", region);

		List<QrRegionQos> QrregionList = QrRegionDao.filter(region, startQuarter, startYear, endQuarter, endYear);
		
		List<HProvincesCode> regionlist = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionlist", regionlist);
		
		model.addAttribute("regionlist", regionlist);
		model.addAttribute("QrregionList", QrregionList);

		return new ModelAndView("QrRegionList");
	}
	
	@RequestMapping("yr")
	public ModelAndView yrListBadByRegion(@RequestParam(required = false) String region, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endYear, ModelMap model) {
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
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
			region = "";
		
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		model.addAttribute("region", region);

		List<YrRegionQos> YrregionList = YrRegionDao.filter(region, startYear, endYear);
		
		List<HProvincesCode> regionlist = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionlist", regionlist);
		
		model.addAttribute("regionlist", regionlist);
		model.addAttribute("YrregionList", YrregionList);

		return new ModelAndView("YrRegionList");
	}
}

