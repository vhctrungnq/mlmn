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

import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkProvinceGprsQosBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkProvinceGprsQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkProvinceGprsQos;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkProvinceGprsQosBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
public class WkProvinceGprsQosController extends BaseController {
	@Autowired
	private VRpWkProvinceGprsQosDAO vRpWkProvinceGprsQosDao;
	@Autowired
	private VRpWkProvinceGprsQosBhDAO vRpWkProvinceGprsQosBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;

	@RequestMapping("/report/radio/province-gprs-qos/wk/list")
	public ModelAndView dyProvinceGprsQosList(@RequestParam(required = false) String region, @RequestParam(required = false) String province,
			@RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model) {
		
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
		{
			endYear = calender.get(Calendar.YEAR);
		}
		DateTime dt = new DateTime();
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
		
		if (region == null)
			region = "";
		
		model.addAttribute("region", region);
		
		List<VRpWkProvinceGprsQos> vRpWkProvinceGprsQos = vRpWkProvinceGprsQosDao.filterDetails(startWeek.toString(), startYear.toString(), endWeek.toString(), endYear.toString(), province, region);

		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("vRpWkProvinceGprsQos", vRpWkProvinceGprsQos);
		return new ModelAndView("wkProvinceGprsQosList");
	}

	@RequestMapping("/report/radio/province-gprs-qos/wk/details")
	public String showReport(@RequestParam(required = false) String region, @RequestParam(required = true) String province,
			@RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
		{
			endYear = calender.get(Calendar.YEAR);
		}
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
		
		if (region == null)
			region = "";
		
		model.addAttribute("region", region);
		
		List<VRpWkProvinceGprsQos> vRpWkProvinceGprsQosDetails = vRpWkProvinceGprsQosDao.filterDetails(startWeek.toString(), startYear.toString(),
				endWeek.toString(), endYear.toString(), province, region);

		model.addAttribute("vRpWkProvinceGprsQosDetails", vRpWkProvinceGprsQosDetails);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);

		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> bhDlTbfSucrList = new ArrayList<Float>();
		List<Float> bhUlTbfSucrList = new ArrayList<Float>();
		List<Float> dataLoadList = new ArrayList<Float>();

		for (VRpWkProvinceGprsQos vRpWkProvinceGprsQos : vRpWkProvinceGprsQosDetails) {
			categories.add(vRpWkProvinceGprsQos.getWeek() + "/" + vRpWkProvinceGprsQos.getYear());
			bhDlTbfSucrList.add(vRpWkProvinceGprsQos.getDlTbfSucr());
			bhUlTbfSucrList.add(vRpWkProvinceGprsQos.getUlTbfSucr());
			dataLoadList.add(vRpWkProvinceGprsQos.getDataload());
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

		return "wkProvinceGprsQosDetails";
	}

	@RequestMapping("/report/radio/province-gprs-qos/wk/bhDetails")
	public String showReportBhDetails(@RequestParam(required = false) String region, @RequestParam(required = true) String province,
			@RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
		{
			endYear = calender.get(Calendar.YEAR);
		}
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
		
		if (region == null)
			region = "";
		
		model.addAttribute("region", region);
		
		List<VRpWkProvinceGprsQosBh> vRpWkProvinceGprsQosBhDetails = vRpWkProvinceGprsQosBhDao.filterDetails(startWeek.toString(), startYear.toString(),
				endWeek.toString(), endYear.toString(), province, region);

		model.addAttribute("vRpWkProvinceGprsQosBhDetails", vRpWkProvinceGprsQosBhDetails);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);

		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> bhDlTbfSucrList = new ArrayList<Float>();
		List<Float> bhUlTbfSucrList = new ArrayList<Float>();

		for (VRpWkProvinceGprsQosBh vRpWkProvinceGprsQosBh : vRpWkProvinceGprsQosBhDetails) {
			categories.add(vRpWkProvinceGprsQosBh.getWeek() + "/" + vRpWkProvinceGprsQosBh.getYear());
			bhDlTbfSucrList.add(vRpWkProvinceGprsQosBh.getBhDlTbfSucr());
			bhUlTbfSucrList.add(vRpWkProvinceGprsQosBh.getBhUlTbfSucr());
		}

		Map<String, List<Float>> dlTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
		dlTbfSucrSeries.put("DL TBF SUCR (%)--80699b", bhDlTbfSucrList);
		model.addAttribute("dlTbfSucrChart", Chart.multiColumn("dlTbfSucrChart", "DL TBF SUCR (%)", categories, dlTbfSucrSeries));

		Map<String, List<Float>> ulTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
		ulTbfSucrSeries.put("UL TBF SUCR (%)--db843d", bhUlTbfSucrList);
		model.addAttribute("ulTbfSucrChart", Chart.multiColumn("ulTbfSucrChart", "UL TBF SUCR (%)", categories, ulTbfSucrSeries));

		// checkBox
		String[] checkColumns = { "bhDlTbfReq", "bhDlTbfSucr", "bhUlTbfReq", "bhUlTbfSucr", "bhGdlTraf", "bhGulTraf", "bhEdlTraf", "bhEulTraf" };
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 6));

		String[] checkSeries = { "bhDlTbfSucr", "bhUlTbfSucr" };
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));

		return "wkProvinceGprsQosBhDetails";
	}
}
