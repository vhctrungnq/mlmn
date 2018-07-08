package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.ArrayList;
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
import vn.com.vhc.vmsc2.statistics.dao.VRpWkBscHoBhIbcDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkBscHoIbcDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkBscHoBhIbc;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkBscHoIbc;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
public class WkBscHoIbcController extends BaseController{
	@Autowired
	private VRpWkBscHoIbcDAO vRpWkBscHoIbcDao;
	@Autowired
	private VRpWkBscHoBhIbcDAO vRpWkBscHoBhIbcDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private BscDAO bscDao;
	
	@RequestMapping("/report/radio/ibc/bsc-ho/wk/list")
	public ModelAndView dyBscHoList(@RequestParam(required = false) String region,@RequestParam(required = false) String bscid, @RequestParam(required = false) Integer week, @RequestParam(required = false) Integer year, ModelMap model,
			HttpServletRequest request) {
		
		DateTime dt = new DateTime();
		dt = dt.minusWeeks(1);

		if (week == null) {
			week = dt.getWeekOfWeekyear();
		}
		
		if(year == null)
			year = dt.getYear();

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		if (region == null)
			region = "";
		
		model.addAttribute("region", region);
		
		List<VRpWkBscHoIbc> vRpWkBscHoIbc = vRpWkBscHoIbcDao.filter(bscid, week, year, region);
		
		model.addAttribute("week", week);
		model.addAttribute("year", year);
		model.addAttribute("vRpWkBscHoIbc", vRpWkBscHoIbc);
		return new ModelAndView("wkBscHoIbcList");
	}
	
	@RequestMapping("/report/radio/ibc/bsc-ho/wk/details")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required=true) String bscid,
			@RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		
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
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		if (region == null)
			region = "";
		
		model.addAttribute("region", region);
		
		List<VRpWkBscHoIbc> vRpWkBscHoIbcDetails = vRpWkBscHoIbcDao.filterDetails(startWeek.toString(), startYear.toString(), endWeek.toString(), endYear.toString(), bscid, region);
		
		model.addAttribute("vRpWkBscHoIbcDetails", vRpWkBscHoIbcDetails);
		model.addAttribute("bscid", bscid);
		
		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> inHoSucrList = new ArrayList<Float>();
		List<Float> ogHoSucrList = new ArrayList<Float>();
		
		for (VRpWkBscHoIbc vRpWkBscHoIbc : vRpWkBscHoIbcDetails) {
			categories.add(vRpWkBscHoIbc.getWeek()+"/"+vRpWkBscHoIbc.getYear());
			inHoSucrList.add(vRpWkBscHoIbc.getInHoSucr());
			ogHoSucrList.add(vRpWkBscHoIbc.getOgHoSucr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("In_Ho_Sucr", inHoSucrList);
		series.put("Og_Ho_Sucr", ogHoSucrList);

		model.addAttribute("chart", Chart.basicLine("BSC IBC HO " + bscid + " Weekly Report", categories, series));

		// checkBox
		String[] checkColumns = { "ogHoAtt", "ogHoSuc", "ogHoSucr", "inHoAtt", "inHoSuc", "inHoSucr"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

		String[] checkSeries = {"ogHoSucr", "inHoSucr"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		
	return "wkBscHoIbcDetails";
	}
	
	@RequestMapping("/report/radio/ibc/bsc-ho/wk/bhDetails")
	public String showReportBhDetails(@RequestParam(required = false) String region,@RequestParam(required=true) String bscid,
			@RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		
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
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		if (region == null)
			region = "";
		
		model.addAttribute("region", region);
		
		List<VRpWkBscHoBhIbc> vRpWkBscHoBhIbcDetails = vRpWkBscHoBhIbcDao.filterDetails(startWeek.toString(), startYear.toString(), endWeek.toString(), endYear.toString(), bscid, region);
		
		model.addAttribute("vRpWkBscHoBhIbcDetails", vRpWkBscHoBhIbcDetails);
		model.addAttribute("bscid", bscid);
		
		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> inHoSucrList = new ArrayList<Float>();
		List<Float> ogHoSucrList = new ArrayList<Float>();
		
		for (VRpWkBscHoBhIbc vRpWkBscHoBhIbc : vRpWkBscHoBhIbcDetails) {
			categories.add(vRpWkBscHoBhIbc.getWeek()+"/"+vRpWkBscHoBhIbc.getYear());
			inHoSucrList.add(vRpWkBscHoBhIbc.getBhInHoSucr());
			ogHoSucrList.add(vRpWkBscHoBhIbc.getBhOgHoSucr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("Bh_In_Ho_Sucr", inHoSucrList);
		series.put("Bh_Og_Ho_Sucr", ogHoSucrList);

		model.addAttribute("chart", Chart.basicLine("BSC IBC HO BH " + bscid + " Weekly Report", categories, series));

		// checkBox
		String[] checkColumns = { "bhOgHoAtt","bhOgHoSuc","bhOgHoSucr","bhInHoAtt","bhInHoSuc","bhInHoSucr"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

		String[] checkSeries = {"bhOgHoSucr", "bhInHoSucr"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		
	return "wkBscHoBhIbcDetails";
	}
}
