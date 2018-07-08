package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrProvince3GDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrProvince3G;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
@RequestMapping("/report/radio3g/province/hr/*")
public class HrProvinceQos3gController extends BaseController {
	@Autowired
	private VRpHrProvince3GDAO vRpHrProvinceDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat ddf = new SimpleDateFormat("dd/MM");

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required = true) String province, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
			ModelMap model) {

		if (endDate == null) {
			endDate = new Date();
		}
		if (startDate == null) {
			startDate = endDate;
		}
		/*if (endHour == null) {
			endHour = "23";
		} else {
			endHour = endHour.substring(0, endHour.indexOf(":"));
		}
		if (startHour == null) {
			startHour = "0";
		} else {
			startHour = startHour.substring(0, startHour.indexOf(":"));
		}

		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("startHour", startHour + ":00");
		model.addAttribute("endHour", endHour + ":00");

		if (region == null)
			region = getCenter("TT2");*/
		if (endHour == null) {
			endHour = "23";
		} else {
			if(endHour.indexOf(":") >0)
				endHour = endHour.substring(0, endHour.indexOf(":"));
			else
				endHour = "23";
			}
		if (startHour == null) {
			startHour = "0";
		} else {	
			if(startHour.indexOf(":") >0)
				startHour = startHour.substring(0, startHour.indexOf(":"));
			else
				startHour = "0";
		}
		if (region==null)
		{
			region="";
		}
		if (province==null)
		{
			province="";
		}
		String[] hourList = {"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
		model.addAttribute("hourList", hourList);
		
		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";
		
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("startHour", startHour+":00");
		model.addAttribute("endHour", endHour+":00");

		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);

		List<VRpHrProvince3G> vRpHrProvinceList = new ArrayList<VRpHrProvince3G>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			vRpHrProvinceList.addAll(vRpHrProvinceDao.filter(dates.get(day).get("startHour"), dates.get(day).get("endHour"), df.format(day), province, region));
		}

		model.addAttribute("vRpHrProvinceList", vRpHrProvinceList);
		model.addAttribute("province", province);
		model.addAttribute("region", region);
		
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpHrProvince"));
		
		/* Chart Start */
		List<String> categories = new ArrayList<String>();
		List<Float> speechCssrList = new ArrayList<Float>();
		List<Float> cs64CssrList = new ArrayList<Float>();
		List<Float> psr99CssrList = new ArrayList<Float>();
		List<Float> hsupaCssrList = new ArrayList<Float>();
		List<Float> hsdpaCssrList = new ArrayList<Float>();
		List<Float> speechDrprList = new ArrayList<Float>();
		List<Float> cs64DrprList = new ArrayList<Float>();
		List<Float> psr99DrprList = new ArrayList<Float>();
		List<Float> hsupaDrprList = new ArrayList<Float>();
		List<Float> hsdpaDrprList = new ArrayList<Float>();
		for (VRpHrProvince3G vRpHrProvince : vRpHrProvinceList) {
			categories.add(vRpHrProvince.getHour()+":00 "+ddf.format(vRpHrProvince.getDay()));
			speechCssrList.add(vRpHrProvince.getSpeechCssr());
			cs64CssrList.add(vRpHrProvince.getCs64Cssr());
			psr99CssrList.add(vRpHrProvince.getPsr99Cssr());
			hsupaCssrList.add(vRpHrProvince.getHsupaCssr());
			hsdpaCssrList.add(vRpHrProvince.getHsdpaCssr());
			speechDrprList.add(vRpHrProvince.getSpeechDrpr());
			cs64DrprList.add(vRpHrProvince.getCs64Drpr());
			psr99DrprList.add(vRpHrProvince.getPsr99Drpr());
			hsupaDrprList.add(vRpHrProvince.getHsupaDrpr());
			hsdpaDrprList.add(vRpHrProvince.getHsdpaDrpr());
		}

		Map<String, List<Float>> speechCssrSeries = new LinkedHashMap<String, List<Float>>();
		speechCssrSeries.put("Speech CSSR (%)--4572a7", speechCssrList);
		model.addAttribute("speechCssrChart", Chart.multiColumn("speechCssrChart", "Speech CSSR (%)", categories, speechCssrSeries));
		
		Map<String, List<Float>> cs64CssrSeries = new LinkedHashMap<String, List<Float>>();
		cs64CssrSeries.put("CS64 CSSR (%)--aa4643", cs64CssrList);
		model.addAttribute("cs64CssrChart", Chart.multiColumn("cs64CssrChart", "CS64 CSSR (%)", categories, cs64CssrSeries));

		Map<String, List<Float>> psr99CssrSeries = new LinkedHashMap<String, List<Float>>();
		psr99CssrSeries.put("PS R99 CSSR (%)--89a54e", psr99CssrList);
		model.addAttribute("psr99CssrChart", Chart.multiColumn("psr99CssrChart", "PS R99 CSSR (%)", categories, psr99CssrSeries));

		Map<String, List<Float>> hsupaCssrSeries = new LinkedHashMap<String, List<Float>>();
		hsupaCssrSeries.put("PS HSUPA CSSR (%)--80699b", hsupaCssrList);
		model.addAttribute("hsupaCssrChart", Chart.multiColumn("hsupaCssrChart", "PS HSUPA CSSR (%)", categories, hsupaCssrSeries));

		Map<String, List<Float>> hsdpaCssrSeries = new LinkedHashMap<String, List<Float>>();
		hsdpaCssrSeries.put("PS HSDPA CSSR (%)--db843d", hsdpaCssrList);
		model.addAttribute("hsdpaCssrChart", Chart.multiColumn("hsdpaCssrChart", "PS HSDPA CSSR (%)", categories, hsdpaCssrSeries));
		
		Map<String, List<Float>> speechDrprSeries = new LinkedHashMap<String, List<Float>>();
		speechDrprSeries.put("Speech DCR (%)--4572a7", speechDrprList);
		model.addAttribute("speechDrprChart", Chart.multiColumn("speechDrprChart", "Speech DCR (%)", categories, speechDrprSeries));
		
		Map<String, List<Float>> cs64DrprSeries = new LinkedHashMap<String, List<Float>>();
		cs64DrprSeries.put("CS64 DCR (%)--aa4643", cs64DrprList);
		model.addAttribute("cs64DrprChart", Chart.multiColumn("cs64DrprChart", "CS64 DCR (%)", categories, cs64DrprSeries));

		Map<String, List<Float>> psr99DrprSeries = new LinkedHashMap<String, List<Float>>();
		psr99DrprSeries.put("PS R99 DCR (%)--89a54e", psr99DrprList);
		model.addAttribute("psr99DrprChart", Chart.multiColumn("psr99DrprChart", "PS R99 DCR (%)", categories, psr99DrprSeries));

		Map<String, List<Float>> hsupaDrprSeries = new LinkedHashMap<String, List<Float>>();
		hsupaDrprSeries.put("PS HSUPA DCR (%)--80699b", hsupaDrprList);
		model.addAttribute("hsupaDrprChart", Chart.multiColumn("hsupaDrprChart", "PS HSUPA DCR (%)", categories, hsupaDrprSeries));

		Map<String, List<Float>> hsdpaDrprSeries = new LinkedHashMap<String, List<Float>>();
		hsdpaDrprSeries.put("PS HSDPA DCR (%)--db843d", hsdpaDrprList);
		model.addAttribute("hsdpaDrprChart", Chart.multiColumn("hsdpaDrprChart", "PS HSDPA DCR (%)", categories, hsdpaDrprSeries));

		// checkBox
		String[] checkColumns = {"SPEECH_TRAFF","CS64_TRAFF","PSR99_UL_TRAFF","PSR99_DL_TRAFF","HSUPA_UL_TRAFF","HSDPA_DL_TRAFF","","SPEECH_CSSR","CS64_CSSR","PSR99_CSSR","HSUPA_CSSR","HSDPA_CSSR","","SPEECH_DRPR","CS64_DRPR","PSR99_DRPR","HSUPA_DRPR","HSDPA_DRPR","PSR99_UL_THROUGHTPUT","PSR99_DL_THROUGHTPUT","HSUPA_UL_THROUGHTPUT","HSDPA_DL_THROUGHTPUT","CS_IRAT_HO_SR","PS_IRAT_HO_SR","SHO_SR_OUT","SHO_SR_IN","CELL_DOWNTIME","HS_DOWNTIME","EUL_DOWNTIME"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpHrProvince", 6));

		return "hrProvinceQos3G";
	}
}
