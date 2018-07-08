package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnSiteHoBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnSiteHoDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnSiteHo;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnSiteHoBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
public class MnSiteHoController extends BaseController{
	@Autowired
	private VRpMnSiteHoDAO vRpMnSiteHoDao;
	@Autowired
	private VRpMnSiteHoBhDAO vRpMnSiteHoBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	
	@RequestMapping("/report/radio/site-ho/mn/list")
	public ModelAndView dySiteHoList(@RequestParam(required = false) String region,@RequestParam(required = false) String province,@RequestParam(required = false) String bscid, @RequestParam(required = false) String siteid, @RequestParam(required = false) Integer month, @RequestParam(required = false) Integer year, ModelMap model,
			HttpServletRequest request) {
		
		Calendar cal = Calendar.getInstance();
		if (month == null)
		{
			cal.add(Calendar.MONTH,-1);
			month = cal.get(Calendar.MONTH)+1; 
			if (year == null)
				year = cal.get(Calendar.YEAR);
		}

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
	    model.addAttribute("monthList", monthList);
		
		if (region == null)
			region = "";
		
		List<VRpMnSiteHo> vRpMnSiteHo = vRpMnSiteHoDao.filter(province,bscid, siteid, month, year, region);
		
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		model.addAttribute("vRpMnSiteHo", vRpMnSiteHo);
		return new ModelAndView("mnSiteHoList");
	}
	
	@RequestMapping("/report/radio/site-ho/mn/details")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required=true) String bscid, @RequestParam(required = true) String siteid,
			@RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		
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

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
	    model.addAttribute("monthList", monthList);
		
		if (region == null)
			region = "";
		
		List<VRpMnSiteHo> vRpMnSiteHoDetails = vRpMnSiteHoDao.filterDetails(startMonth.toString(), startYear.toString(), endMonth.toString(), endYear.toString(), bscid, siteid, region);
		
		model.addAttribute("vRpMnSiteHoDetails", vRpMnSiteHoDetails);
		model.addAttribute("siteid", siteid);
		model.addAttribute("bscid", bscid);
		
		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> inHoSucrList = new ArrayList<Float>();
		List<Float> ogHoSucrList = new ArrayList<Float>();
		
		for (VRpMnSiteHo vRpMnSiteHo : vRpMnSiteHoDetails) {
			categories.add(vRpMnSiteHo.getMonth()+"/"+vRpMnSiteHo.getYear());
			inHoSucrList.add(vRpMnSiteHo.getInHoSucr());
			ogHoSucrList.add(vRpMnSiteHo.getOgHoSucr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("In_Ho_Sucr", inHoSucrList);
		series.put("Og_Ho_Sucr", ogHoSucrList);

		model.addAttribute("chart", Chart.basicLine("SITE HO " + siteid + " Monthly Report", categories, series));

		// checkBox
		String[] checkColumns = { "ogHoAtt", "ogHoSuc", "ogHoSucr", "inHoAtt", "inHoSuc", "inHoSucr"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 6));

		String[] checkSeries = {"ogHoSucr", "inHoSucr"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		
	return "mnSiteHoDetails";
	}
	
	@RequestMapping("/report/radio/site-ho/mn/bhDetails")
	public String showReportBhDetails(@RequestParam(required = false) String region,@RequestParam(required=true) String bscid, @RequestParam(required = true) String siteid,
			@RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		
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

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
	    model.addAttribute("monthList", monthList);
		
		if (region == null)
			region = "";
		
		List<VRpMnSiteHoBh> vRpMnSiteHoBhDetails = vRpMnSiteHoBhDao.filterDetails(startMonth.toString(), startYear.toString(), endMonth.toString(), endYear.toString(), bscid, siteid, region);
		
		model.addAttribute("vRpMnSiteHoBhDetails", vRpMnSiteHoBhDetails);
		model.addAttribute("siteid", siteid);
		model.addAttribute("bscid", bscid);
		
		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> inHoSucrList = new ArrayList<Float>();
		List<Float> ogHoSucrList = new ArrayList<Float>();
		
		for (VRpMnSiteHoBh vRpMnSiteHoBh : vRpMnSiteHoBhDetails) {
			categories.add(vRpMnSiteHoBh.getMonth()+"/"+vRpMnSiteHoBh.getYear());
			inHoSucrList.add(vRpMnSiteHoBh.getBhInHoSucr());
			ogHoSucrList.add(vRpMnSiteHoBh.getBhOgHoSucr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("Bh_In_Ho_Sucr", inHoSucrList);
		series.put("Bh_Og_Ho_Sucr", ogHoSucrList);

		model.addAttribute("chart", Chart.basicLine("SITE HO BH " + siteid + " Monthly Report", categories, series));

		// checkBox
		String[] checkColumns = { "bhOgHoAtt","bhOgHoSuc","bhOgHoSucr","bhInHoAtt","bhInHoSuc","bhInHoSucr"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 7));

		String[] checkSeries = {"bhOgHoSucr", "bhInHoSucr"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		
	return "mnSiteHoBhDetails";
	}
}
