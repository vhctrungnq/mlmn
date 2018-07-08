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
import vn.com.vhc.vmsc2.statistics.dao.VRpMnProvinceGprsQosBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnProvinceGprsQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnProvinceGprsQos;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnProvinceGprsQosBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
public class MnProvinceGprsQosController extends BaseController {
	@Autowired
	private VRpMnProvinceGprsQosDAO vRpMnProvinceGprsQosDao;
	@Autowired
	private VRpMnProvinceGprsQosBhDAO vRpMnProvinceGprsQosBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;

	@RequestMapping("/report/radio/province-gprs-qos/mn/list")
	public ModelAndView dyProvinceGprsQosList(@RequestParam(required = false) String region, @RequestParam(required = false) String province,
			@RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model) {
		
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
		{
			endYear = calender.get(Calendar.YEAR);
		}
		Calendar cal = Calendar.getInstance();
		if (endMonth == null) {
			cal.add(Calendar.MONTH, -1);
			endMonth = cal.get(Calendar.MONTH) + 1;
			endYear = cal.get(Calendar.YEAR);
		}

		if (startMonth == null) {
			startMonth = endMonth;
			startYear = endYear;
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

		List<VRpMnProvinceGprsQos> vRpMnProvinceGprsQos = vRpMnProvinceGprsQosDao.filterDetails(startMonth.toString(), startYear.toString(),
				endMonth.toString(), endYear.toString(), province, region);

		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("region", region);
		model.addAttribute("vRpMnProvinceGprsQos", vRpMnProvinceGprsQos);
		return new ModelAndView("mnProvinceGprsQosList");
	}

	@RequestMapping("/report/radio/province-gprs-qos/mn/details")
	public String showReport(@RequestParam(required = false) String region, @RequestParam(required = true) String province,
			@RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {

		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
		{
			endYear = calender.get(Calendar.YEAR);
		}
		if (endMonth == null) {
			cal.add(Calendar.MONTH, -1);
			endMonth = cal.get(Calendar.MONTH) + 1;
			endYear = cal.get(Calendar.YEAR);
		}

		if (startMonth == null) {
			if (endMonth > 3) {
				startMonth = endMonth - 3;
				startYear = endYear;
			} else {
				cal1.add(Calendar.MONTH, -3);
				startMonth = cal1.get(Calendar.MONTH) + 1;
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

		List<VRpMnProvinceGprsQos> vRpMnProvinceGprsQosDetails = vRpMnProvinceGprsQosDao.filterDetails(startMonth.toString(), startYear.toString(),
				endMonth.toString(), endYear.toString(), province, region);

		model.addAttribute("vRpMnProvinceGprsQosDetails", vRpMnProvinceGprsQosDetails);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("region", region);
		
		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> bhDlTbfSucrList = new ArrayList<Float>();
		List<Float> bhUlTbfSucrList = new ArrayList<Float>();
		List<Float> dataLoadList = new ArrayList<Float>();

		for (VRpMnProvinceGprsQos vRpMnProvinceGprsQos : vRpMnProvinceGprsQosDetails) {
			categories.add(vRpMnProvinceGprsQos.getMonth() + "/" + vRpMnProvinceGprsQos.getYear());
			bhDlTbfSucrList.add(vRpMnProvinceGprsQos.getDlTbfSucr());
			bhUlTbfSucrList.add(vRpMnProvinceGprsQos.getUlTbfSucr());
			dataLoadList.add(vRpMnProvinceGprsQos.getDataload());
		}
		Map<String, List<Float>> dlTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
		dlTbfSucrSeries.put("DL TBF SUCR (%)--80699b", bhDlTbfSucrList);
		model.addAttribute("dlTbfSucrChart", Chart.multiColumn("dlTbfSucrChart", "DL TBF SUCR (%)", categories, dlTbfSucrSeries));

		Map<String, List<Float>> ulTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
		ulTbfSucrSeries.put("UL TBF SUCR (%)--db843d", bhUlTbfSucrList);
		model.addAttribute("ulTbfSucrChart", Chart.multiColumn("ulTbfSucrChart", "UL TBF SUCR (%)", categories, ulTbfSucrSeries));

		// checkBox
		String[] checkColumns = { "dlTbfReq", "dlTbfSucr", "ulTbfReq", "ulTbfSucr", "gdlTraf", "gulTraf", "edlTraf", "eulTraf", "dataload" };
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 6));

		String[] checkSeries = { "dlTbfSucr", "ulTbfSucr", "dataload" };
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));

		return "mnProvinceGprsQosDetails";
	}

	@RequestMapping("/report/radio/province-gprs-qos/mn/bhDetails")
	public String showReportBhDetails( 
			@RequestParam(required = false) Integer startMonth, 
			@RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endMonth, 
			@RequestParam(required = false) Integer endYear, 
			@RequestParam(required = false) String province,
			@RequestParam(required = false) String region,
			ModelMap model, HttpServletRequest request) {
			

		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
		{
			endYear = calender.get(Calendar.YEAR);
		}
		if (endMonth == null) {
			cal.add(Calendar.MONTH, -1);
			endMonth = cal.get(Calendar.MONTH) + 1;
			endYear = cal.get(Calendar.YEAR);
		}

		if (startMonth == null) {
			if (endMonth > 3) {
				startMonth = endMonth - 3;
				startYear = endYear;
			} else {
				cal1.add(Calendar.MONTH, -3);
				startMonth = cal1.get(Calendar.MONTH) + 1;
				startYear = cal1.get(Calendar.YEAR);
			}
		}
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", startYear);


		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
	    model.addAttribute("monthList", monthList);
		
		if (region == null)
			region = "";

		List<VRpMnProvinceGprsQosBh> vRpMnProvinceGprsQosBhDetails = vRpMnProvinceGprsQosBhDao.filterDetails(startMonth.toString(), startYear.toString(),
				endMonth.toString(), endYear.toString(), province, region);

		model.addAttribute("vRpMnProvinceGprsQosBhDetails", vRpMnProvinceGprsQosBhDetails);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("region", region);
		
		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> dlTbfSucrList = new ArrayList<Float>();
		List<Float> ulTbfSucrList = new ArrayList<Float>();

		for (VRpMnProvinceGprsQosBh vRpMnProvinceGprsQosBh : vRpMnProvinceGprsQosBhDetails) {
			categories.add(vRpMnProvinceGprsQosBh.getMonth() + "/" + vRpMnProvinceGprsQosBh.getYear());
			dlTbfSucrList.add(vRpMnProvinceGprsQosBh.getBhDlTbfSucr());
			ulTbfSucrList.add(vRpMnProvinceGprsQosBh.getBhUlTbfSucr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("Dl_Tbf_Sucr", dlTbfSucrList);
		series.put("Ul_Tbf_Sucr", ulTbfSucrList);

		model.addAttribute("chart", Chart.basicLine("PROVINCE GPRS QOS BH " + province + " Monthly Report", categories, series));

		// checkBox
		String[] checkColumns = { "bhDlTbfReq", "bhDlTbfSucr", "bhUlTbfReq", "bhUlTbfSucr", "bhGdlTraf", "bhGulTraf", "bhEdlTraf", "bhEulTraf" };
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 6));

		String[] checkSeries = { "bhDlTbfSucr", "bhUlTbfSucr" };
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));

		return "mnProvinceGprsQosBhDetails";
	}
}
