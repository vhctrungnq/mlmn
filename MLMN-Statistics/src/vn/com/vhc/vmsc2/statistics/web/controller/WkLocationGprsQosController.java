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
import vn.com.vhc.vmsc2.statistics.dao.VRpWkLocationGprsQosBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkLocationGprsQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkLocationGprsQos;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkLocationGprsQosBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
public class WkLocationGprsQosController extends BaseController{
	@Autowired
	private VRpWkLocationGprsQosDAO vRpWkLocationGprsQosDao;
	@Autowired
	private VRpWkLocationGprsQosBhDAO vRpWkLocationGprsQosBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	
	@RequestMapping("/report/radio/location-gprs-qos/wk/list")
	public ModelAndView dyLocationGprsQosList(@RequestParam(required = false) String region,@RequestParam(required = false) String location, @RequestParam(required = false) Integer week, @RequestParam(required = false) Integer year, ModelMap model,
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
		
		List<VRpWkLocationGprsQos> vRpWkLocationGprsQos = vRpWkLocationGprsQosDao.filter(location, week, year, region);

		List<HProvincesCode> locationList = hProvincesCodeDao.getAllLocation();
		model.addAttribute("locationList", locationList);
		model.addAttribute("location", location);
		model.addAttribute("week", week);
		model.addAttribute("year", year);
		model.addAttribute("vRpWkLocationGprsQos", vRpWkLocationGprsQos);
		return new ModelAndView("wkLocationGprsQosList");
	}
	
	@RequestMapping("/report/radio/location-gprs-qos/wk/details")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required=true) String location,
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
		
		List<VRpWkLocationGprsQos> vRpWkLocationGprsQosDetails = vRpWkLocationGprsQosDao.filterDetails(startWeek.toString(), startYear.toString(), endWeek.toString(), endYear.toString(), location, region);
		
		model.addAttribute("vRpWkLocationGprsQosDetails", vRpWkLocationGprsQosDetails);
		List<HProvincesCode> locationList = hProvincesCodeDao.getAllLocation();
		model.addAttribute("locationList", locationList);
		model.addAttribute("location", location);
		
		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> dlTbfSucrList = new ArrayList<Float>();
		List<Float> ulTbfSucrList = new ArrayList<Float>();
		List<Float> dataLoadList = new ArrayList<Float>();
		
		for (VRpWkLocationGprsQos vRpWkLocationGprsQos : vRpWkLocationGprsQosDetails) {
			categories.add(vRpWkLocationGprsQos.getWeek()+"/"+vRpWkLocationGprsQos.getYear());
			dlTbfSucrList.add(vRpWkLocationGprsQos.getDlTbfSucr());
			ulTbfSucrList.add(vRpWkLocationGprsQos.getUlTbfSucr());
			dataLoadList.add(vRpWkLocationGprsQos.getDataload());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("Dl_Tbf_Sucr", dlTbfSucrList);
		series.put("Ul_Tbf_Sucr", ulTbfSucrList);
		series.put("Dataload", dataLoadList);

		model.addAttribute("chart", Chart.basicLine("LOCATION GPRS QOS " + location + " Weekly Report", categories, series));

		// checkBox
		String[] checkColumns = {"dlTbfReq","dlTbfSucr","ulTbfReq","ulTbfSucr","gdlTraf","gulTraf","edlTraf","eulTraf","dataload"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

		String[] checkSeries = {"dlTbfSucr", "ulTbfSucr","dataload"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		
	return "wkLocationGprsQosDetails";
	}
	
	@RequestMapping("/report/radio/location-gprs-qos/wk/bhDetails")
	public String showReportBhDetails(@RequestParam(required = false) String region,@RequestParam(required=true) String location,
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
		
		List<VRpWkLocationGprsQosBh> vRpWkLocationGprsQosBhDetails = vRpWkLocationGprsQosBhDao.filterDetails(startWeek.toString(), startYear.toString(), endWeek.toString(), endYear.toString(), location, region);
		
		model.addAttribute("vRpWkLocationGprsQosBhDetails", vRpWkLocationGprsQosBhDetails);
		List<HProvincesCode> locationList = hProvincesCodeDao.getAllLocation();
		model.addAttribute("locationList", locationList);
		model.addAttribute("location", location);
		
		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> dlTbfSucrList = new ArrayList<Float>();
		List<Float> ulTbfSucrList = new ArrayList<Float>();
		
		for (VRpWkLocationGprsQosBh vRpWkLocationGprsQosBh : vRpWkLocationGprsQosBhDetails) {
			categories.add(vRpWkLocationGprsQosBh.getWeek()+"/"+vRpWkLocationGprsQosBh.getYear());
			dlTbfSucrList.add(vRpWkLocationGprsQosBh.getBhDlTbfSucr());
			ulTbfSucrList.add(vRpWkLocationGprsQosBh.getBhUlTbfSucr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("Dl_Tbf_Sucr", dlTbfSucrList);
		series.put("Ul_Tbf_Sucr", ulTbfSucrList);

		model.addAttribute("chart", Chart.basicLine("LOCATION GPRS QOS BH " + location + " Weekly Report", categories, series));

		// checkBox
		String[] checkColumns = {"bhDlTbfReq","bhDlTbfSucr","bhDlTbfReq","bhUlTbfSucr","bhGdlTraf","bhGulTraf","bhEdlTraf","bhEulTraf"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

		String[] checkSeries = {"bhDlTbfSucr", "bhUlTbfSucr"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		
	return "wkLocationGprsQosBhDetails";
	}
}
