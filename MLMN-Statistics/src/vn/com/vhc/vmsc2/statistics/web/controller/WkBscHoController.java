package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
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
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkBscHoBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkBscHoDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkBscHo;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkBscHoBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
public class WkBscHoController extends BaseController{
	@Autowired
	private VRpWkBscHoDAO vRpWkBscHoDao;
	@Autowired
	private VRpWkBscHoBhDAO vRpWkBscHoBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private BscDAO bscDao;
	
	@RequestMapping("/report/radio/bsc-ho/wk/list")
	public ModelAndView showReportList(@RequestParam(required = false) String region,@RequestParam(required=false) String bscid,
			@RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model, 
			HttpServletRequest request) {
		
		DateTime dt = new DateTime();
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
		{
			endYear = calender.get(Calendar.YEAR);
		}
		if (endWeek == null) {
			dt = dt.minusWeeks(1);
			endWeek = dt.getWeekOfWeekyear();
			endYear = dt.getYear();
		}
		if (startWeek == null) {
			startWeek = endWeek;
			startYear = endYear;
		}
		model.addAttribute("startWeek", startWeek);
		model.addAttribute("endWeek", endWeek);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		if (region == null)
			region = "";
		
		model.addAttribute("region", region);
		
		List<VRpWkBscHo> vRpWkBscHo = vRpWkBscHoDao.filterDetails(startWeek.toString(), startYear.toString(), endWeek.toString(), endYear.toString(), bscid, region);

		model.addAttribute("vRpWkBscHo", vRpWkBscHo);
		return new ModelAndView("wkBscHoList");
	}
	
	@RequestMapping("/report/radio/bsc-ho/wk/details")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required=true) String bscid,
			@RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		
		DateTime dt = new DateTime();
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
		{
			endYear = calender.get(Calendar.YEAR);
		}
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
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		if (region == null)
			region = "";
		
		model.addAttribute("region", region);
		
		List<VRpWkBscHo> vRpWkBscHoDetails = vRpWkBscHoDao.filterDetails(startWeek.toString(), startYear.toString(), endWeek.toString(), endYear.toString(), bscid, region);
		
		model.addAttribute("vRpWkBscHoDetails", vRpWkBscHoDetails);
		model.addAttribute("bscid", bscid);
		
		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> inHoSucrList = new ArrayList<Float>();
		List<Float> ogHoSucrList = new ArrayList<Float>();
		
		for (VRpWkBscHo vRpWkBscHo : vRpWkBscHoDetails) {
			categories.add(vRpWkBscHo.getWeek()+"/"+vRpWkBscHo.getYear());
			inHoSucrList.add(vRpWkBscHo.getInHoSucr());
			ogHoSucrList.add(vRpWkBscHo.getOgHoSucr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("In_Ho_Sucr", inHoSucrList);
		series.put("Og_Ho_Sucr", ogHoSucrList);

		model.addAttribute("chart", Chart.basicLine("BSC HO " + bscid + " Weekly Report", categories, series));

		// checkBox
		String[] checkColumns = { "ogHoAtt", "ogHoSuc", "ogHoSucr", "inHoAtt", "inHoSuc", "inHoSucr"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

		String[] checkSeries = {"ogHoSucr", "inHoSucr"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		
	return "wkBscHoDetails";
	}
	
	@RequestMapping("/report/radio/bsc-ho/wk/bhDetails")
	public String showReportBhDetails(@RequestParam(required = false) String region,@RequestParam(required=true) String bscid,
			@RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		
		DateTime dt = new DateTime();
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
		{
			endYear = calender.get(Calendar.YEAR);
		}
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
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		if (region == null)
			region = "";
		
		model.addAttribute("region", region);
		
		List<VRpWkBscHoBh> vRpWkBscHoBhDetails = vRpWkBscHoBhDao.filterDetails(startWeek.toString(), startYear.toString(), endWeek.toString(), endYear.toString(), bscid, region);
		
		model.addAttribute("vRpWkBscHoBhDetails", vRpWkBscHoBhDetails);
		model.addAttribute("bscid", bscid);
		
		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> inHoSucrList = new ArrayList<Float>();
		List<Float> ogHoSucrList = new ArrayList<Float>();
		
		for (VRpWkBscHoBh vRpWkBscHoBh : vRpWkBscHoBhDetails) {
			categories.add(vRpWkBscHoBh.getWeek()+"/"+vRpWkBscHoBh.getYear());
			inHoSucrList.add(vRpWkBscHoBh.getBhInHoSucr());
			ogHoSucrList.add(vRpWkBscHoBh.getBhOgHoSucr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("Bh_In_Ho_Sucr", inHoSucrList);
		series.put("Bh_Og_Ho_Sucr", ogHoSucrList);

		model.addAttribute("chart", Chart.basicLine("BSC HO BH " + bscid + " Weekly Report", categories, series));

		// checkBox
		String[] checkColumns = { "bhOgHoAtt","bhOgHoSuc","bhOgHoSucr","bhInHoAtt","bhInHoSuc","bhInHoSucr"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

		String[] checkSeries = {"bhOgHoSucr", "bhInHoSucr"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		
	return "wkBscHoBhDetails";
	}
}
