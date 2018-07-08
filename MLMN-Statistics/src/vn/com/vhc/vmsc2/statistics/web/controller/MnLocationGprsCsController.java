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
import vn.com.vhc.vmsc2.statistics.dao.VRpMnLocationGprsCsBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnLocationGprsCsDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnLocationGprsCs;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnLocationGprsCsBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
public class MnLocationGprsCsController extends BaseController{
	@Autowired
	private VRpMnLocationGprsCsDAO vRpMnLocationGprsCsDao;
	@Autowired
	private VRpMnLocationGprsCsBhDAO vRpMnLocationGprsCsBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	
	@RequestMapping("/report/radio/location-gprs-cs/mn/list")
	public ModelAndView dyLocationGprsCsList(@RequestParam(required = false) String region,@RequestParam(required = false) String location, @RequestParam(required = false) Integer month, @RequestParam(required = false) Integer year, ModelMap model,
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
		
		List<VRpMnLocationGprsCs> vRpMnLocationGprsCs = vRpMnLocationGprsCsDao.filter(location, month.floatValue(), year.floatValue(), region);

		List<HProvincesCode> locationList = hProvincesCodeDao.getAllLocation();
		model.addAttribute("locationList", locationList);
		model.addAttribute("location", location);
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		model.addAttribute("vRpMnLocationGprsCs", vRpMnLocationGprsCs);
		return new ModelAndView("mnLocationGprsCsList");
	}
	
	@RequestMapping("/report/radio/location-gprs-cs/mn/details")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required=true) String location,
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
		
		List<VRpMnLocationGprsCs> vRpMnLocationGprsCsDetails = vRpMnLocationGprsCsDao.filterDetails(startMonth.toString(), startYear.toString(), endMonth.toString(), endYear.toString(), location, region);
		
		model.addAttribute("vRpMnLocationGprsCsDetails", vRpMnLocationGprsCsDetails);
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
		
		for (VRpMnLocationGprsCs vRpMnLocationGprsCs : vRpMnLocationGprsCsDetails) {
			categories.add(vRpMnLocationGprsCs.getMonth()+"/"+vRpMnLocationGprsCs.getYear());
			csxLevel1List.add(vRpMnLocationGprsCs.getCsxLevel1());
			csxLevel2List.add(vRpMnLocationGprsCs.getCsxLevel2());
			csxLevel3List.add(vRpMnLocationGprsCs.getCsxLevel3());
			csxLevel4List.add(vRpMnLocationGprsCs.getCsxLevel4());
			mcsxLevel1List.add(vRpMnLocationGprsCs.getMcsxLevel1());
			mcsxLevel2List.add(vRpMnLocationGprsCs.getMcsxLevel2());
			mcsxLevel3List.add(vRpMnLocationGprsCs.getMcsxLevel3());
			mcsxLevel4List.add(vRpMnLocationGprsCs.getMcsxLevel4());
			mcsxLevel5List.add(vRpMnLocationGprsCs.getMcsxLevel5());
			mcsxLevel6List.add(vRpMnLocationGprsCs.getMcsxLevel6());
			mcsxLevel7List.add(vRpMnLocationGprsCs.getMcsxLevel7());
			mcsxLevel8List.add(vRpMnLocationGprsCs.getMcsxLevel8());
			mcsxLevel9List.add(vRpMnLocationGprsCs.getMcsxLevel9());
			dataLoadList.add(vRpMnLocationGprsCs.getDataload());
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

		model.addAttribute("chart", Chart.basicLine("LOCATION GPRS CS " + location + " Monthly Report", categories, series));

		// checkBox
		String[] checkColumns = {"csxLevel1","csxLevel2","csxLevel3","csxLevel4","mcsxLevel1","mcsxLevel2","mcsxLevel3","mcsxLevel4","mcsxLevel5","mcsxLevel6","mcsxLevel7","mcsxLevel8","mcsxLevel9","dataload"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

		String[] checkSeries = {"dataload"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		
	return "mnLocationGprsCsDetails";
	}
	
	@RequestMapping("/report/radio/location-gprs-cs/mn/bhDetails")
	public String showReportBhDetails(@RequestParam(required = false) String region,@RequestParam(required=true) String location,
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
		
		List<VRpMnLocationGprsCsBh> vRpMnLocationGprsCsBhDetails = vRpMnLocationGprsCsBhDao.filterDetails(startMonth.toString(), startYear.toString(), endMonth.toString(), endYear.toString(), location, region);
		
		model.addAttribute("vRpMnLocationGprsCsBhDetails", vRpMnLocationGprsCsBhDetails);
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
		
		for (VRpMnLocationGprsCsBh VRpMnLocationGprsCsBh : vRpMnLocationGprsCsBhDetails) {
			categories.add(VRpMnLocationGprsCsBh.getMonth()+"/"+VRpMnLocationGprsCsBh.getYear());
			bhCsxLevel1List.add(VRpMnLocationGprsCsBh.getBhCsxLevel1());
			bhCsxLevel2List.add(VRpMnLocationGprsCsBh.getBhCsxLevel2());
			bhCsxLevel3List.add(VRpMnLocationGprsCsBh.getBhCsxLevel3());
			bhCsxLevel4List.add(VRpMnLocationGprsCsBh.getBhCsxLevel4());
			bhMcsxLevel1List.add(VRpMnLocationGprsCsBh.getBhMcsxLevel1());
			bhMcsxLevel2List.add(VRpMnLocationGprsCsBh.getBhMcsxLevel2());
			bhMcsxLevel3List.add(VRpMnLocationGprsCsBh.getBhMcsxLevel3());
			bhMcsxLevel4List.add(VRpMnLocationGprsCsBh.getBhMcsxLevel4());
			bhMcsxLevel5List.add(VRpMnLocationGprsCsBh.getBhMcsxLevel5());
			bhMcsxLevel6List.add(VRpMnLocationGprsCsBh.getBhMcsxLevel6());
			bhMcsxLevel7List.add(VRpMnLocationGprsCsBh.getBhMcsxLevel7());
			bhMcsxLevel8List.add(VRpMnLocationGprsCsBh.getBhMcsxLevel8());
			bhMcsxLevel9List.add(VRpMnLocationGprsCsBh.getBhMcsxLevel9());
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

		model.addAttribute("chart", Chart.basicLine("LOCATION GPRS CS BH " + location + " Monthly Report", categories, series));

		// checkBox
		String[] checkColumns = {"bhCsxLevel1","bhCsxLevel2","bhCsxLevel3","bhCsxLevel4","bhMcsxLevel1","bhMcsxLevel2","bhMcsxLevel3","bhMcsxLevel4","bhMcsxLevel5","bhMcsxLevel6","bhMcsxLevel7","bhMcsxLevel8","bhMcsxLevel9"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

		String[] checkSeries = {"bhCsxLevel1","bhCsxLevel2","bhCsxLevel3","bhCsxLevel4","bhMcsxLevel1","bhMcsxLevel2","bhMcsxLevel3","bhMcsxLevel4","bhMcsxLevel5","bhMcsxLevel6","bhMcsxLevel7","bhMcsxLevel8","bhMcsxLevel9"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		
	return "mnLocationGprsCsBhDetails";
	}
}
