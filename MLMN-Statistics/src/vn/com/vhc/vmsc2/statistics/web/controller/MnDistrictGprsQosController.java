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
import vn.com.vhc.vmsc2.statistics.dao.VRpMnDistrictGprsQosBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnDistrictGprsQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnDistrictGprsQos;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnDistrictGprsQosBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
public class MnDistrictGprsQosController extends BaseController{
	@Autowired
	private VRpMnDistrictGprsQosDAO vRpMnDistrictGprsQosDao;
	@Autowired
	private VRpMnDistrictGprsQosBhDAO vRpMnDistrictGprsQosBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	
	@RequestMapping("/report/radio/district-gprs-qos/mn/list")
	public ModelAndView dyProvinceGprsQosList(@RequestParam(required = false) String region,@RequestParam(required = false) String province,@RequestParam(required = false) String district, @RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		
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
		
		List<VRpMnDistrictGprsQos> vRpMnDistrictGprsQos = vRpMnDistrictGprsQosDao.filterDetails(province, district, startMonth.toString(), startYear.toString(), endMonth.toString(), endYear.toString(), region);

		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("district", district);
		model.addAttribute("region", region);
		model.addAttribute("vRpMnDistrictGprsQos", vRpMnDistrictGprsQos);
		return new ModelAndView("mnDistrictGprsQosList");
	}
	
	@RequestMapping("/report/radio/district-gprs-qos/mn/details")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required=true) String province,@RequestParam(required=true) String district,
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
		
		List<VRpMnDistrictGprsQos> vRpMnDistrictGprsQosDetails = vRpMnDistrictGprsQosDao.filterDetails(province, district, startMonth.toString(), startYear.toString(), endMonth.toString(), endYear.toString(), region);
		
		model.addAttribute("vRpMnDistrictGprsQosDetails", vRpMnDistrictGprsQosDetails);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("district", district);
		model.addAttribute("region", region);
		
		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> bhDlTbfSucrList = new ArrayList<Float>();
		List<Float> bhUlTbfSucrList = new ArrayList<Float>();
		List<Float> dataLoadList = new ArrayList<Float>();
		
		for (VRpMnDistrictGprsQos vRpMnDistrictGprsQos : vRpMnDistrictGprsQosDetails) {
			categories.add(vRpMnDistrictGprsQos.getMonth()+"/"+vRpMnDistrictGprsQos.getYear());
			bhDlTbfSucrList.add(vRpMnDistrictGprsQos.getDlTbfSucr());
			bhUlTbfSucrList.add(vRpMnDistrictGprsQos.getUlTbfSucr());
			dataLoadList.add(vRpMnDistrictGprsQos.getDataload());
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
		
	return "mnDistrictGprsQosDetails";
	}
	
	@RequestMapping("/report/radio/district-gprs-qos/mn/bhDetails")
	public String showReportBhDetails(@RequestParam(required = false) String region,@RequestParam(required=true) String province,@RequestParam(required=true) String district,
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
		
		List<VRpMnDistrictGprsQosBh> vRpMnDistrcitGprsQosBhDetails = vRpMnDistrictGprsQosBhDao.filterDetails(province, district, startMonth.toString(), startYear.toString(), endMonth.toString(), endYear.toString(), region);
		
		model.addAttribute("vRpMnDistrcitGprsQosBhDetails", vRpMnDistrcitGprsQosBhDetails);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("district", district);
		model.addAttribute("region", region);
		
		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> dlTbfSucrList = new ArrayList<Float>();
		List<Float> ulTbfSucrList = new ArrayList<Float>();
		
		for (VRpMnDistrictGprsQosBh vRpMnDistrictGprsQosBh : vRpMnDistrcitGprsQosBhDetails) {
			categories.add(vRpMnDistrictGprsQosBh.getMonth()+"/"+vRpMnDistrictGprsQosBh.getYear());
			dlTbfSucrList.add(vRpMnDistrictGprsQosBh.getBhDlTbfSucr());
			ulTbfSucrList.add(vRpMnDistrictGprsQosBh.getBhUlTbfSucr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("Dl_Tbf_Sucr", dlTbfSucrList);
		series.put("Ul_Tbf_Sucr", ulTbfSucrList);

		model.addAttribute("chart", Chart.basicLine("DISTRICT GPRS QOS BH " + district +"/"+ province + " Monthly Report", categories, series));

		// checkBox
		String[] checkColumns = {"bhDlTbfReq","bhDlTbfSucr","bhDlTbfReq","bhUlTbfSucr","bhGdlTraf","bhGulTraf","bhEdlTraf","bhEulTraf"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 8));

		String[] checkSeries = {"bhDlTbfSucr", "bhUlTbfSucr"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		
	return "mnDistrictGprsQosBhDetails";
	}
}
