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
import vn.com.vhc.vmsc2.statistics.dao.VRpWkDistrictGprsQosBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkDistrictGprsQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkDistrictGprsQos;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkDistrictGprsQosBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
public class WkDistrictGprsQosController extends BaseController{
	@Autowired
	private VRpWkDistrictGprsQosDAO vRpWkDistrictGprsQosDao;
	@Autowired
	private VRpWkDistrictGprsQosBhDAO vRpWkDistrictGprsQosBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	
	@RequestMapping("/report/radio/district-gprs-qos/wk/list")
	public ModelAndView dyDistrictGprsQosList(@RequestParam(required = false) String region,@RequestParam(required=false) String province, @RequestParam(required=false) String district,
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
		model.addAttribute("region", region);
		
		List<VRpWkDistrictGprsQos> vRpWkDistrictGprsQos = vRpWkDistrictGprsQosDao.filterDetails(startWeek.toString(), startYear.toString(), endWeek.toString(), endYear.toString(), province, district, region);

		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("district", district);
		model.addAttribute("vRpWkDistrictGprsQos", vRpWkDistrictGprsQos);
		return new ModelAndView("wkDistrictGprsQosList");
	}
	
	@RequestMapping("/report/radio/district-gprs-qos/wk/details")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required=true) String province, @RequestParam(required=true) String district,
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

	
		model.addAttribute("region", region);
		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		List<VRpWkDistrictGprsQos> vRpWkDistrictGprsQosDetails = vRpWkDistrictGprsQosDao.filterDetails(startWeek.toString(), startYear.toString(), endWeek.toString(), endYear.toString(), province, district, region);
		
		model.addAttribute("vRpWkDistrictGprsQosDetails", vRpWkDistrictGprsQosDetails);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("district", district);
		
		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> bhDlTbfSucrList = new ArrayList<Float>();
		List<Float> bhUlTbfSucrList = new ArrayList<Float>();
		List<Float> dataLoadList = new ArrayList<Float>();
		
		for (VRpWkDistrictGprsQos vRpWkDistrictGprsQos : vRpWkDistrictGprsQosDetails) {
			categories.add(vRpWkDistrictGprsQos.getWeek()+"/"+vRpWkDistrictGprsQos.getYear());
			bhDlTbfSucrList.add(vRpWkDistrictGprsQos.getDlTbfSucr());
			bhUlTbfSucrList.add(vRpWkDistrictGprsQos.getUlTbfSucr());
			dataLoadList.add(vRpWkDistrictGprsQos.getDataload());
		}

		Map<String, List<Float>> dlTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
		dlTbfSucrSeries.put("DL TBF SUCR (%)--80699b", bhDlTbfSucrList);
		model.addAttribute("dlTbfSucrChart", Chart.multiColumn("dlTbfSucrChart", "DL TBF SUCR (%)", categories, dlTbfSucrSeries));

		Map<String, List<Float>> ulTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
		ulTbfSucrSeries.put("UL TBF SUCR (%)--db843d", bhUlTbfSucrList);
		model.addAttribute("ulTbfSucrChart", Chart.multiColumn("ulTbfSucrChart", "UL TBF SUCR (%)", categories, ulTbfSucrSeries));

		// checkBox
		String[] checkColumns = {"dlTbfReq","dlTbfSucr","ulTbfReq","ulTbfSucr","gdlTraf","gulTraf","edlTraf","eulTraf","dataload"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 8));

		String[] checkSeries = {"dlTbfSucr", "ulTbfSucr","dataload"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		
	return "wkDistrictGprsQosDetails";
	}
	
	@RequestMapping("/report/radio/district-gprs-qos/wk/bhDetails")
	public String showReportBhDetails(@RequestParam(required = false) String region,@RequestParam(required=true) String province,@RequestParam(required=true) String district,
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
		model.addAttribute("region", region);
		
		List<VRpWkDistrictGprsQosBh> vRpWkDistrictGprsQosBhDetails = vRpWkDistrictGprsQosBhDao.filterDetails(startWeek.toString(), startYear.toString(), endWeek.toString(), endYear.toString(), province, district, region);
		
		model.addAttribute("vRpWkDistrictGprsQosBhDetails", vRpWkDistrictGprsQosBhDetails);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("district", district);
		
		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> bhDlTbfSucrList = new ArrayList<Float>();
		List<Float> bhUlTbfSucrList = new ArrayList<Float>();
		
		for (VRpWkDistrictGprsQosBh vRpWkDistrictGprsQosBh : vRpWkDistrictGprsQosBhDetails) {
			categories.add(vRpWkDistrictGprsQosBh.getWeek()+"/"+vRpWkDistrictGprsQosBh.getYear());
			bhDlTbfSucrList.add(vRpWkDistrictGprsQosBh.getBhDlTbfSucr());
			bhUlTbfSucrList.add(vRpWkDistrictGprsQosBh.getBhUlTbfSucr());
		}
		Map<String, List<Float>> dlTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
		dlTbfSucrSeries.put("DL TBF SUCR (%)--80699b", bhDlTbfSucrList);
		model.addAttribute("dlTbfSucrChart", Chart.multiColumn("dlTbfSucrChart", "DL TBF SUCR (%)", categories, dlTbfSucrSeries));

		Map<String, List<Float>> ulTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
		ulTbfSucrSeries.put("UL TBF SUCR (%)--db843d", bhUlTbfSucrList);
		model.addAttribute("ulTbfSucrChart", Chart.multiColumn("ulTbfSucrChart", "UL TBF SUCR (%)", categories, ulTbfSucrSeries));

		// checkBox
		String[] checkColumns = {"bhDlTbfReq","bhDlTbfSucr","bhDlTbfReq","bhUlTbfSucr","bhGdlTraf","bhGulTraf","bhEdlTraf","bhEulTraf"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 8));

		String[] checkSeries = {"bhDlTbfSucr", "bhUlTbfSucr"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		
	return "wkDistrictGprsQosBhDetails";
	}
}
