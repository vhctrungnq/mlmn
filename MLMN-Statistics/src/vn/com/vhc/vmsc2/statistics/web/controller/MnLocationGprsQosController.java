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
import vn.com.vhc.vmsc2.statistics.dao.VRpMnLocationGprsQosBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnLocationGprsQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnLocationGprsQos;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnLocationGprsQosBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
public class MnLocationGprsQosController extends BaseController{
	@Autowired
	private VRpMnLocationGprsQosDAO vRpMnLocationGprsQosDao;
	@Autowired
	private VRpMnLocationGprsQosBhDAO vRpMnLocationGprsQosBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	
	@RequestMapping("/report/radio/location-gprs-qos/mn/list")
	public ModelAndView dyLocationGprsQosList(@RequestParam(required = false) String region,@RequestParam(required = false) String location, @RequestParam(required = false) Integer month, @RequestParam(required = false) Integer year, ModelMap model,
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
		
		List<VRpMnLocationGprsQos> vRpMnLocationGprsQos = vRpMnLocationGprsQosDao.filter(location, month, year, region);

		List<HProvincesCode> locationList = hProvincesCodeDao.getAllLocation();
		model.addAttribute("locationList", locationList);
		model.addAttribute("location", location);
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		model.addAttribute("vRpMnLocationGprsQos", vRpMnLocationGprsQos);
		return new ModelAndView("mnLocationGprsQosList");
	}
	
	@RequestMapping("/report/radio/location-gprs-qos/mn/details")
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
		
		List<VRpMnLocationGprsQos> vRpMnLocationGprsQosDetails = vRpMnLocationGprsQosDao.filterDetails(startMonth.toString(), startYear.toString(), endMonth.toString(), endYear.toString(), location, region);
		
		model.addAttribute("vRpMnLocationGprsQosDetails", vRpMnLocationGprsQosDetails);
		List<HProvincesCode> locationList = hProvincesCodeDao.getAllLocation();
		model.addAttribute("locationList", locationList);
		model.addAttribute("location", location);
		
		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> dlTbfSucrList = new ArrayList<Float>();
		List<Float> ulTbfSucrList = new ArrayList<Float>();
		List<Float> dataLoadList = new ArrayList<Float>();
		
		for (VRpMnLocationGprsQos vRpMnLocationGprsQos : vRpMnLocationGprsQosDetails) {
			categories.add(vRpMnLocationGprsQos.getMonth()+"/"+vRpMnLocationGprsQos.getYear());
			dlTbfSucrList.add(vRpMnLocationGprsQos.getDlTbfSucr());
			ulTbfSucrList.add(vRpMnLocationGprsQos.getUlTbfSucr());
			dataLoadList.add(vRpMnLocationGprsQos.getDataload());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("Dl_Tbf_Sucr", dlTbfSucrList);
		series.put("Ul_Tbf_Sucr", ulTbfSucrList);
		series.put("Dataload", dataLoadList);

		model.addAttribute("chart", Chart.basicLine("LOCATION GPRS QOS " + location + " Monthly Report", categories, series));

		// checkBox
		String[] checkColumns = {"dlTbfReq","dlTbfSucr","ulTbfReq","ulTbfSucr","gdlTraf","gulTraf","edlTraf","eulTraf","dataload"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

		String[] checkSeries = {"dlTbfSucr", "ulTbfSucr","dataload"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		
	return "mnLocationGprsQosDetails";
	}
	
	@RequestMapping("/report/radio/location-gprs-qos/mn/bhDetails")
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
		
		List<VRpMnLocationGprsQosBh> vRpMnLocationGprsQosBhDetails = vRpMnLocationGprsQosBhDao.filterDetails(startMonth.toString(), startYear.toString(), endMonth.toString(), endYear.toString(), location, region);
		
		model.addAttribute("vRpMnLocationGprsQosBhDetails", vRpMnLocationGprsQosBhDetails);
		model.addAttribute("location", location);
		
		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> dlTbfSucrList = new ArrayList<Float>();
		List<Float> ulTbfSucrList = new ArrayList<Float>();
		
		for (VRpMnLocationGprsQosBh vRpMnLocationGprsQosBh : vRpMnLocationGprsQosBhDetails) {
			categories.add(vRpMnLocationGprsQosBh.getMonth()+"/"+vRpMnLocationGprsQosBh.getYear());
			dlTbfSucrList.add(vRpMnLocationGprsQosBh.getBhDlTbfSucr());
			ulTbfSucrList.add(vRpMnLocationGprsQosBh.getBhUlTbfSucr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("Bh_Dl_Tbf_Sucr", dlTbfSucrList);
		series.put("Bh_Ul_Tbf_Sucr", ulTbfSucrList);

		model.addAttribute("chart", Chart.basicLine("LOCATION GPRS QOS BH " + location + " Monthly Report", categories, series));

		// checkBox
		String[] checkColumns = {"bhDlTbfReq","bhDlTbfSucr","bhDlTbfReq","bhUlTbfSucr","bhGdlTraf","bhGulTraf","bhEdlTraf","bhEulTraf"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

		String[] checkSeries = {"bhDlTbfSucr", "bhDlTbfReq"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		
	return "mnLocationGprsQosBhDetails";
	}
}
