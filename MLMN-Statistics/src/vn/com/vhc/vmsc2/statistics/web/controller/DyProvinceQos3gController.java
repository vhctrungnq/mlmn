package vn.com.vhc.vmsc2.statistics.web.controller;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyProvince3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyRegion3GDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyProvince3G;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyRegion3G;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;
import vn.com.vhc.vmsc2.statistics.web.utils.ModelAddtributes;

@Controller
@RequestMapping("/report/radio3g/province/dy/*")
public class DyProvinceQos3gController extends BaseController {
	@Autowired
	private VRpDyProvince3GDAO vRpDyProvinceDao;
	@Autowired
	private VRpDyRegion3GDAO vRpDyRegionDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("list")
	public ModelAndView dyList(@RequestParam(required = false) String region, @RequestParam(required = false) String province,
			@RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, ModelMap model) {
		String[] s = ModelAddtributes.checkDate(model, startDate, endDate).split(";");

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);

		List<VRpDyProvince3G> vRpDyProvince = vRpDyProvinceDao.filter(s[0], s[1], province, region);

		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("region", region);
		model.addAttribute("vRpDyProvince", vRpDyProvince);

		List<VRpDyRegion3G> vRpDyRegionList = vRpDyRegionDao.filter(s[0], s[1], region);
		model.addAttribute("vRpDyRegionList", vRpDyRegionList);

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyProvince"));

		return new ModelAndView("dyProvince3GList");
	}

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = false) String region, @RequestParam(required = true) String province,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();

		try {
			Date startDay;
			Date endDay;
			if (endDate == null) {
				endDay = new Date(currentTime);
			} else {
				endDay = df.parse(endDate);
			}
			if (startDate == null) {
				DateTime dt = new DateTime(endDay);
				startDay = dt.minusDays(7).toDate();
			} else {
				startDay = df.parse(startDate);
			}
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("endDate", df.format(endDay));

			List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
			model.addAttribute("provinceList", provinceList);

			List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
			model.addAttribute("regionList", regionList);

			if (region == null)
				region = "";

			List<VRpDyProvince3G> vRpDyProvinceList = vRpDyProvinceDao.filter(df.format(startDay), df.format(endDay), province, region);

			model.addAttribute("vRpDyProvinceList", vRpDyProvinceList);
			model.addAttribute("province", province);
			model.addAttribute("region", region);

			List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
			model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyProvince"));

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
			for (VRpDyProvince3G vRpDyProvince : vRpDyProvinceList) {
				categories.add(df.format(vRpDyProvince.getDay()));
				speechCssrList.add(vRpDyProvince.getSpeechCssr());
				cs64CssrList.add(vRpDyProvince.getCs64Cssr());
				psr99CssrList.add(vRpDyProvince.getPsr99Cssr());
				hsupaCssrList.add(vRpDyProvince.getHsupaCssr());
				hsdpaCssrList.add(vRpDyProvince.getHsdpaCssr());
				speechDrprList.add(vRpDyProvince.getSpeechDrpr());
				cs64DrprList.add(vRpDyProvince.getCs64Drpr());
				psr99DrprList.add(vRpDyProvince.getPsr99Drpr());
				hsupaDrprList.add(vRpDyProvince.getHsupaDrpr());
				hsdpaDrprList.add(vRpDyProvince.getHsdpaDrpr());
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
			String[] checkColumns = { "SPEECH_TRAFF", "CS64_TRAFF", "PSR99_UL_TRAFF", "PSR99_DL_TRAFF", "HSUPA_UL_TRAFF", "HSDPA_DL_TRAFF", "", "SPEECH_CSSR",
					"CS64_CSSR", "PSR99_CSSR", "HSUPA_CSSR", "HSDPA_CSSR", "", "SPEECH_DRPR", "CS64_DRPR", "PSR99_DRPR", "HSUPA_DRPR", "HSDPA_DRPR",
					"PSR99_UL_THROUGHTPUT", "PSR99_DL_THROUGHTPUT", "HSUPA_UL_THROUGHTPUT", "HSDPA_DL_THROUGHTPUT", "CS_IRAT_HO_SR", "PS_IRAT_HO_SR",
					"SHO_SR_OUT", "SHO_SR_IN", "CELL_DOWNTIME", "HS_DOWNTIME", "EUL_DOWNTIME", "DATALOAD" };
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpDyProvince", 8));

			return "dyProvinceQos3G";
		} catch (ParseException e) {
			saveError(request, e.toString());
		}

		return "dyProvinceQos3G";
	}

	@RequestMapping("chart")
	public String showChart(@RequestParam(required = true) String province, @RequestParam(required = true) String kpi, ModelMap model) {
		if (province == null || province.length() == 0) {
			return "redirect:list.htm";
		}
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		String endDate = df.format(calendar.getTime());
		calendar.add(Calendar.DAY_OF_MONTH, -30);
		String startDate = df.format(calendar.getTime());
		List<VRpDyProvince3G> dataList = vRpDyProvinceDao.filter(startDate, endDate, province, null);
		List<String> categories = new ArrayList<String>();
		List<Float> kpiValue = new ArrayList<Float>();
		String methodName = "get" + kpi.substring(0, 1).toUpperCase() + kpi.substring(1);
		Class<VRpDyProvince3G> vRpDyProvinceClass = VRpDyProvince3G.class;
		String kpiTitle;
		switch (kpi) {
		case "speechTraff":
			kpiTitle = "Speech Traffic (ERL)";
			break;
		case "cs64Traff":
			kpiTitle = "CS64 Traffic (ERL)";
			break;
		case "psr99UlTraff":
			kpiTitle = "PSR99 UL Data (GB)";
			break;
		case "psr99DlTraff":
			kpiTitle = "PSR99 DL Data (GB)";
			break;
		case "hsupaUlTraff":
			kpiTitle = "HSUPA UL Data (GB)";
			break;
		case "hsdpaDlTraff":
			kpiTitle = "HSDPA DL Data (GB)";
			break;
		case "speechCssr":
			kpiTitle = "CSSR3G (%)";
			break;
		case "cs64Cssr":
			kpiTitle = "CS64 CSSR (%)";
			break;
		case "hsupaCssr":
			kpiTitle = "HSUPA CSSR (%)";
			break;	
		case "hsdpaCssr":
			kpiTitle = "HSDPA CSSR (%)";
			break;
		case "psr99Cssr":
			kpiTitle = "PSR99 CSSR (%)";
			break;	
		case "cs64Drpr":
			kpiTitle = "CS64 Drop(%)";
			break;	
		case "psr99Drpr":
			kpiTitle = "PSR99 Drop(%)";
			break;
		case "hsupaDrpr":
			kpiTitle = "HSUPA Drop (%)";
			break;
		case "hsdpaDrpr":
			kpiTitle = "HSDPA Drop (%)";
			break;
		case "psr99UlThroughtput":
			kpiTitle = "PSR99 UL Thrp (Kbps)";
			break;
		case "psr99DlThroughtput":
			kpiTitle = "PSR99 DL Thrp (Kbps)";
			break;
		case "hsupaUlThroughtput":
			kpiTitle = "HSUPA UL Thrp (Kbps)";
			break;
		case "hsdpaDlThroughtput":
			kpiTitle = "HSUPA DL Thrp (Kbps)";
			break;	
		case "cellDowntime":
			kpiTitle = "Cell Downtime (Hour)";
			break;
		case "hsDowntime":
			kpiTitle = "HS Downtime (Hour)";
			break;
		case "eulDowntime":
			kpiTitle = "EUL Downtime";
			break;
		case "dataload":
			kpiTitle = "DataLoad(%)";
			break;	
		default:
			kpiTitle = "";
			break;
		}
		
		try {
			Method getKpiValueMethod = vRpDyProvinceClass.getMethod(methodName);
			for (VRpDyProvince3G item : dataList) {
				categories.add(df.format(item.getDay()));
				kpiValue.add((Float) getKpiValueMethod.invoke(item));
			}
			Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
			series.put(kpiTitle + "--4572a7", kpiValue);
			model.addAttribute("chart", Chart.multiColumn("chart", kpiTitle, categories, series));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Method getKpiValueMethod = Class.forName("VRpDyCell").g
		model.addAttribute("kpiTitle", kpiTitle);
		model.addAttribute("province", province);
		return "dyProvince3gChart";
	}
}
