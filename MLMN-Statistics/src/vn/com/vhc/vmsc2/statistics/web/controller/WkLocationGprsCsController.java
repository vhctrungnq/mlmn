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
import vn.com.vhc.vmsc2.statistics.dao.VRpWkLocationGprsCsBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkLocationGprsCsDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkLocationGprsCs;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkLocationGprsCsBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
public class WkLocationGprsCsController extends BaseController{
	@Autowired
	private VRpWkLocationGprsCsDAO vRpWkLocationGprsCsDao;
	@Autowired
	private VRpWkLocationGprsCsBhDAO vRpWkLocationGprsCsBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	
	@RequestMapping("/report/radio/location-gprs-cs/wk/list")
	public ModelAndView dyLocationGprsCsList(@RequestParam(required = false) String region,@RequestParam(required = false) String location, @RequestParam(required = false) Integer week, @RequestParam(required = false) Integer year, ModelMap model,
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
		
		List<VRpWkLocationGprsCs> vRpWkLocationGprsCs = vRpWkLocationGprsCsDao.filter(location, week.floatValue(), year.floatValue(), region);
		
		List<HProvincesCode> locationList = hProvincesCodeDao.getAllLocation();
		model.addAttribute("locationList", locationList);
		model.addAttribute("location", location);
		model.addAttribute("week", week);
		model.addAttribute("year", year);
		model.addAttribute("vRpWkLocationGprsCs", vRpWkLocationGprsCs);
		return new ModelAndView("wkLocationGprsCsList");
	}
	
	@RequestMapping("/report/radio/location-gprs-cs/wk/details")
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
		model.addAttribute("region", region);
		
		List<VRpWkLocationGprsCs> vRpWkLocationGprsCsDetails = vRpWkLocationGprsCsDao.filterDetails(startWeek.toString(), startYear.toString(), endWeek.toString(), endYear.toString(), location, region);
		
		model.addAttribute("vRpWkLocationGprsCsDetails", vRpWkLocationGprsCsDetails);
		List<HProvincesCode> locationList = hProvincesCodeDao.getAllLocation();
		model.addAttribute("locationList", locationList);
		model.addAttribute("location", location);
		
		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> csxLevel1List = new ArrayList<Float>();
		List<Float> csxLevel2List = new ArrayList<Float>();
		List<Float> csxLevel3List = new ArrayList<Float>();
		List<Float> csxLevel4List = new ArrayList<Float>();
		List<Float> mcsxLevel1List = new ArrayList<Float>();
		List<Float> mcsxLevel2List = new ArrayList<Float>();
		List<Float> mcsxLevel3List = new ArrayList<Float>();
		List<Float> mcsxLevel4List = new ArrayList<Float>();
		List<Float> mcsxLevel5List = new ArrayList<Float>();
		List<Float> mcsxLevel6List = new ArrayList<Float>();
		List<Float> mcsxLevel7List = new ArrayList<Float>();
		List<Float> mcsxLevel8List = new ArrayList<Float>();
		List<Float> mcsxLevel9List = new ArrayList<Float>();
		List<Float> dataLoadList = new ArrayList<Float>();
		
		for (VRpWkLocationGprsCs vRpWkLocationGprsCs : vRpWkLocationGprsCsDetails) {
			categories.add(vRpWkLocationGprsCs.getWeek()+"/"+vRpWkLocationGprsCs.getYear());
			csxLevel1List.add(vRpWkLocationGprsCs.getCsxLevel1());
			csxLevel2List.add(vRpWkLocationGprsCs.getCsxLevel2());
			csxLevel3List.add(vRpWkLocationGprsCs.getCsxLevel3());
			csxLevel4List.add(vRpWkLocationGprsCs.getCsxLevel4());
			mcsxLevel1List.add(vRpWkLocationGprsCs.getMcsxLevel1());
			mcsxLevel2List.add(vRpWkLocationGprsCs.getMcsxLevel2());
			mcsxLevel3List.add(vRpWkLocationGprsCs.getMcsxLevel3());
			mcsxLevel4List.add(vRpWkLocationGprsCs.getMcsxLevel4());
			mcsxLevel5List.add(vRpWkLocationGprsCs.getMcsxLevel5());
			mcsxLevel6List.add(vRpWkLocationGprsCs.getMcsxLevel6());
			mcsxLevel7List.add(vRpWkLocationGprsCs.getMcsxLevel7());
			mcsxLevel8List.add(vRpWkLocationGprsCs.getMcsxLevel8());
			mcsxLevel9List.add(vRpWkLocationGprsCs.getMcsxLevel9());
			dataLoadList.add(vRpWkLocationGprsCs.getDataload());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("CsxLevel1", csxLevel1List);
		series.put("CsxLevel2", csxLevel2List);
		series.put("CsxLevel3", csxLevel3List);
		series.put("CsxLevel4", csxLevel4List);
		series.put("McsxLevel1", mcsxLevel1List);
		series.put("McsxLevel2", mcsxLevel2List);
		series.put("McsxLevel3", mcsxLevel3List);
		series.put("McsxLevel4", mcsxLevel4List);
		series.put("McsxLevel5", mcsxLevel5List);
		series.put("McsxLevel6", mcsxLevel6List);
		series.put("McsxLevel7", mcsxLevel7List);
		series.put("McsxLevel8", mcsxLevel8List);
		series.put("McsxLevel9", mcsxLevel9List);
		series.put("Dataload", dataLoadList);

		model.addAttribute("chart", Chart.basicLine("LOCATION GPRS CS " + location + " Daily Report", categories, series));

		// checkBox
		String[] checkColumns = {"csxLevel1","csxLevel2","csxLevel3","csxLevel4","mcsxLevel1","mcsxLevel2","mcsxLevel3","mcsxLevel4","mcsxLevel5","mcsxLevel6","mcsxLevel7","mcsxLevel8","mcsxLevel9","dataload"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

		String[] checkSeries = {"dataload"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		
	return "wkLocationGprsCsDetails";
	}
	
	@RequestMapping("/report/radio/location-gprs-cs/wk/bhDetails")
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
		model.addAttribute("region", region);
		
		List<VRpWkLocationGprsCsBh> vRpWkLocationGprsCsBhDetails = vRpWkLocationGprsCsBhDao.filterDetails(startWeek.toString(), startYear.toString(), endWeek.toString(), endYear.toString(), location, region);
		
		model.addAttribute("vRpWkLocationGprsCsBhDetails", vRpWkLocationGprsCsBhDetails);
		List<HProvincesCode> locationList = hProvincesCodeDao.getAllLocation();
		model.addAttribute("locationList", locationList);
		model.addAttribute("location", location);
		
		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> bhCsxLevel1List = new ArrayList<Float>();
		List<Float> bhCsxLevel2List = new ArrayList<Float>();
		List<Float> bhCsxLevel3List = new ArrayList<Float>();
		List<Float> bhCsxLevel4List = new ArrayList<Float>();
		List<Float> bhMcsxLevel1List = new ArrayList<Float>();
		List<Float> bhMcsxLevel2List = new ArrayList<Float>();
		List<Float> bhMcsxLevel3List = new ArrayList<Float>();
		List<Float> bhMcsxLevel4List = new ArrayList<Float>();
		List<Float> bhMcsxLevel5List = new ArrayList<Float>();
		List<Float> bhMcsxLevel6List = new ArrayList<Float>();
		List<Float> bhMcsxLevel7List = new ArrayList<Float>();
		List<Float> bhMcsxLevel8List = new ArrayList<Float>();
		List<Float> bhMcsxLevel9List = new ArrayList<Float>();
		
		for (VRpWkLocationGprsCsBh vRpWkLocationGprsCsBh : vRpWkLocationGprsCsBhDetails) {
			categories.add(vRpWkLocationGprsCsBh.getWeek()+"/"+vRpWkLocationGprsCsBh.getYear());
			bhCsxLevel1List.add(vRpWkLocationGprsCsBh.getBhCsxLevel1());
			bhCsxLevel2List.add(vRpWkLocationGprsCsBh.getBhCsxLevel2());
			bhCsxLevel3List.add(vRpWkLocationGprsCsBh.getBhCsxLevel3());
			bhCsxLevel4List.add(vRpWkLocationGprsCsBh.getBhCsxLevel4());
			bhMcsxLevel1List.add(vRpWkLocationGprsCsBh.getBhMcsxLevel1());
			bhMcsxLevel2List.add(vRpWkLocationGprsCsBh.getBhMcsxLevel2());
			bhMcsxLevel3List.add(vRpWkLocationGprsCsBh.getBhMcsxLevel3());
			bhMcsxLevel4List.add(vRpWkLocationGprsCsBh.getBhMcsxLevel4());
			bhMcsxLevel5List.add(vRpWkLocationGprsCsBh.getBhMcsxLevel5());
			bhMcsxLevel6List.add(vRpWkLocationGprsCsBh.getBhMcsxLevel6());
			bhMcsxLevel7List.add(vRpWkLocationGprsCsBh.getBhMcsxLevel7());
			bhMcsxLevel8List.add(vRpWkLocationGprsCsBh.getBhMcsxLevel8());
			bhMcsxLevel9List.add(vRpWkLocationGprsCsBh.getBhMcsxLevel9());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("Bh_Csx_Level1", bhCsxLevel1List);
		series.put("Bh_Csx_Level2", bhCsxLevel2List);
		series.put("Bh_Csx_Level3", bhCsxLevel3List);
		series.put("Bh_Csx_Level4", bhCsxLevel4List);
		series.put("Bh_Mcsx_Level1", bhMcsxLevel1List);
		series.put("Bh_Mcsx_Level2", bhMcsxLevel2List);
		series.put("Bh_Mcsx_Level3", bhMcsxLevel3List);
		series.put("Bh_Mcsx_Level4", bhMcsxLevel4List);
		series.put("Bh_Mcsx_Level5", bhMcsxLevel5List);
		series.put("Bh_Mcsx_Level6", bhMcsxLevel6List);
		series.put("Bh_Mcsx_Level7", bhMcsxLevel7List);
		series.put("Bh_Mcsx_Level8", bhMcsxLevel8List);
		series.put("Bh_Mcsx_Level9", bhMcsxLevel9List);

		model.addAttribute("chart", Chart.basicLine("LOCATION GPRS CS BH " + location + " Weekly Report", categories, series));

		// checkBox
		String[] checkColumns = {"bhCsxLevel1","bhCsxLevel2","bhCsxLevel3","bhCsxLevel4","bhMcsxLevel1","bhMcsxLevel2","bhMcsxLevel3","bhMcsxLevel4","bhMcsxLevel5","bhMcsxLevel6","bhMcsxLevel7","bhMcsxLevel8","bhMcsxLevel9"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

		String[] checkSeries = {"bhCsxLevel1","bhCsxLevel2","bhCsxLevel3","bhCsxLevel4","bhMcsxLevel1","bhMcsxLevel2","bhMcsxLevel3","bhMcsxLevel4","bhMcsxLevel5","bhMcsxLevel6","bhMcsxLevel7","bhMcsxLevel8","bhMcsxLevel9"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		
	return "wkLocationGprsCsBhDetails";
	}
}
