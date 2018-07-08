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

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnBscHoBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnBscHoDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnBscHo;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnBscHoBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
public class MnBscHoController extends BaseController{
	@Autowired
	private VRpMnBscHoDAO vRpMnBscHoDao;
	@Autowired
	private VRpMnBscHoBhDAO vRpMnBscHoBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private BscDAO bscDao;
	
	@RequestMapping("/report/radio/bsc-ho/mn/list")
	public ModelAndView showReportList(@RequestParam(required = false) String region,@RequestParam(required=false) String bscid,
			@RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model, 
			HttpServletRequest request) {
		
		Calendar cal = Calendar.getInstance();
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
		{
			endYear = calender.get(Calendar.YEAR);
		}
		if(endMonth == null){
			cal.add(Calendar.MONTH,-1);
			endMonth = cal.get(Calendar.MONTH)+1;
			endYear = cal.get(Calendar.YEAR);
		}
		if (startMonth == null) {
			startMonth = endMonth;
			startYear = endYear;
		}

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
	    model.addAttribute("monthList", monthList);
		
		if (region == null)
			region = "";
		model.addAttribute("region", region);
		
		List<VRpMnBscHo> vRpMnBscHo = vRpMnBscHoDao.filterDetails(startMonth.toString(), startYear.toString(), endMonth.toString(), endYear.toString(), bscid, region);
		
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		model.addAttribute("vRpMnBscHo", vRpMnBscHo);
		return new ModelAndView("mnBscHoList");
	}
	
	@RequestMapping("/report/radio/bsc-ho/mn/details")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required=true) String bscid,
			@RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
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
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		model.addAttribute("region", region);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<VRpMnBscHo> vRpMnBscHoDetails = vRpMnBscHoDao.filterDetails(startMonth.toString(), startYear.toString(), endMonth.toString(), endYear.toString(), bscid, region);
		
		model.addAttribute("vRpMnBscHoDetails", vRpMnBscHoDetails);
		model.addAttribute("bscid", bscid);
		
		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> inHoSucrList = new ArrayList<Float>();
		List<Float> ogHoSucrList = new ArrayList<Float>();
		
		for (VRpMnBscHo vRpMnBscHo : vRpMnBscHoDetails) {
			categories.add(vRpMnBscHo.getMonth()+"/"+vRpMnBscHo.getYear());
			inHoSucrList.add(vRpMnBscHo.getInHoSucr());
			ogHoSucrList.add(vRpMnBscHo.getOgHoSucr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("In_Ho_Sucr", inHoSucrList);
		series.put("Og_Ho_Sucr", ogHoSucrList);

		model.addAttribute("chart", Chart.basicLine("BSC HO " + bscid + " Monthly Report", categories, series));

		// checkBox
		String[] checkColumns = { "ogHoAtt", "ogHoSuc", "ogHoSucr", "inHoAtt", "inHoSuc", "inHoSucr"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

		String[] checkSeries = {"ogHoSucr", "inHoSucr"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		
	return "mnBscHoDetails";
	}
	
	@RequestMapping("/report/radio/bsc-ho/mn/bhDetails")
	public String showReportBhDetails(@RequestParam(required = false) String region,@RequestParam(required=true) String bscid,
			@RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
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
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", startYear);
		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		model.addAttribute("region", region);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<VRpMnBscHoBh> vRpMnBscHoBhDetails = vRpMnBscHoBhDao.filterDetails(startMonth.toString(), startYear.toString(), endMonth.toString(), endYear.toString(), bscid, region);
		
		model.addAttribute("vRpMnBscHoBhDetails", vRpMnBscHoBhDetails);
		model.addAttribute("bscid", bscid);
		
		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> inHoSucrList = new ArrayList<Float>();
		List<Float> ogHoSucrList = new ArrayList<Float>();
		
		for (VRpMnBscHoBh vRpMnBscHoBh : vRpMnBscHoBhDetails) {
			categories.add(vRpMnBscHoBh.getMonth()+"/"+vRpMnBscHoBh.getYear());
			inHoSucrList.add(vRpMnBscHoBh.getBhInHoSucr());
			ogHoSucrList.add(vRpMnBscHoBh.getBhOgHoSucr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("Bh_In_Ho_Sucr", inHoSucrList);
		series.put("Bh_Og_Ho_Sucr", ogHoSucrList);

		model.addAttribute("chart", Chart.basicLine("BSC HO BH " + bscid + " Monthly Report", categories, series));

		// checkBox
		String[] checkColumns = { "bhOgHoAtt","bhOgHoSuc","bhOgHoSucr","bhInHoAtt","bhInHoSuc","bhInHoSucr"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

		String[] checkSeries = {"bhOgHoSucr", "bhInHoSucr"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		
	return "mnBscHoBhDetails";
	}
}
