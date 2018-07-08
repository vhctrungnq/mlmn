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

import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkSiteHoBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkSiteHoDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkSiteHo;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkSiteHoBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
public class WkSiteHoController extends BaseController{
	@Autowired
	private VRpWkSiteHoDAO vRpWkSiteHoDao;
	@Autowired
	private VRpWkSiteHoBhDAO vRpWkSiteHoBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	
	@RequestMapping("/report/radio/site-ho/wk/list")
	public ModelAndView dySiteHoList(@RequestParam(required = false) String region,@RequestParam(required = false) String province,@RequestParam(required = false) String bscid, @RequestParam(required = false) String siteid, @RequestParam(required = false) Integer week, @RequestParam(required = false) Integer year, ModelMap model,
			HttpServletRequest request) {
		
		DateTime dt = new DateTime();
		dt = dt.minusWeeks(1);

		if (week == null) {
			week = dt.getWeekOfWeekyear();
		}
		
		if(year == null)
			year = dt.getYear();

		if (region == null)
			region = getCenter("TT2");
		model.addAttribute("region", region);
		
		List<VRpWkSiteHo> vRpWkSiteHo = vRpWkSiteHoDao.filter(province,bscid, siteid, week, year, region);
		
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("week", week);
		model.addAttribute("year", year);
		model.addAttribute("vRpWkSiteHo", vRpWkSiteHo);
		return new ModelAndView("wkSiteHoList");
	}
	
	@RequestMapping("/report/radio/site-ho/wk/details")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required=true) String bscid, @RequestParam(required = true) String siteid,
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

		if (region == null)
			region = getCenter("TT2");
		model.addAttribute("region", region);
		
		List<VRpWkSiteHo> vRpWkSiteHoDetails = vRpWkSiteHoDao.filterDetails(startWeek.toString(), startYear.toString(), endWeek.toString(), endYear.toString(), bscid, siteid, region);
		
		model.addAttribute("vRpWkSiteHoDetails", vRpWkSiteHoDetails);
		model.addAttribute("siteid", siteid);
		model.addAttribute("bscid", bscid);
		
		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> inHoSucrList = new ArrayList<Float>();
		List<Float> ogHoSucrList = new ArrayList<Float>();
		
		for (VRpWkSiteHo vRpWkSiteHo : vRpWkSiteHoDetails) {
			categories.add(vRpWkSiteHo.getWeek()+"/"+vRpWkSiteHo.getYear());
			inHoSucrList.add(vRpWkSiteHo.getInHoSucr());
			ogHoSucrList.add(vRpWkSiteHo.getOgHoSucr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("In_Ho_Sucr", inHoSucrList);
		series.put("Og_Ho_Sucr", ogHoSucrList);

		model.addAttribute("chart", Chart.basicLine("SITE HO " + siteid + " Weekly Report", categories, series));

		// checkBox
		String[] checkColumns = { "ogHoAtt", "ogHoSuc", "ogHoSucr", "inHoAtt", "inHoSuc", "inHoSucr"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 6));

		String[] checkSeries = {"ogHoSucr", "inHoSucr"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		
	return "wkSiteHoDetails";
	}
	
	@RequestMapping("/report/radio/site-ho/wk/bhDetails")
	public String showReportBhDetails(@RequestParam(required = false) String region,@RequestParam(required=true) String bscid, @RequestParam(required = true) String siteid,
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

		if (region == null)
			region = getCenter("TT2");
		model.addAttribute("region", region);
		
		List<VRpWkSiteHoBh> vRpWkSiteHoBhDetails = vRpWkSiteHoBhDao.filterDetails(startWeek.toString(), startYear.toString(), endWeek.toString(), endYear.toString(), bscid, siteid, region);
		
		model.addAttribute("vRpWkSiteHoBhDetails", vRpWkSiteHoBhDetails);
		model.addAttribute("siteid", siteid);
		model.addAttribute("bscid", bscid);
		
		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> inHoSucrList = new ArrayList<Float>();
		List<Float> ogHoSucrList = new ArrayList<Float>();
		
		for (VRpWkSiteHoBh vRpWkSiteHoBh : vRpWkSiteHoBhDetails) {
			categories.add(vRpWkSiteHoBh.getWeek()+"/"+vRpWkSiteHoBh.getYear());
			inHoSucrList.add(vRpWkSiteHoBh.getBhInHoSucr());
			ogHoSucrList.add(vRpWkSiteHoBh.getBhOgHoSucr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("Bh_In_Ho_Sucr", inHoSucrList);
		series.put("Bh_Og_Ho_Sucr", ogHoSucrList);

		model.addAttribute("chart", Chart.basicLine("SITE HO BH " + siteid + " Weekly Report", categories, series));

		// checkBox
		String[] checkColumns = { "bhOgHoAtt","bhOgHoSuc","bhOgHoSucr","bhInHoAtt","bhInHoSuc","bhInHoSucr"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 6));

		String[] checkSeries = {"bhOgHoSucr", "bhInHoSucr"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		
	return "wkSiteHoBhDetails";
	}
}
