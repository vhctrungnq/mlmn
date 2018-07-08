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
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkBranch3GDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkBranch3G;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
@RequestMapping("/report/radio3g/branch/wk/*")
public class WkBranchQos3gController extends BaseController {
	@Autowired
	private VRpWkBranch3GDAO vRpWkBranchDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;

	@RequestMapping("list")
	public ModelAndView dyList(@RequestParam(required = false) String region,@RequestParam(required = false) String branch, @RequestParam(required = false) Integer startWeek,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek,
			@RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {

		if (endWeek == null) {
			DateTime dt = new DateTime();
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
		
		List<VRpWkBranch3G> vRpWkBranch = vRpWkBranchDao.filter(startWeek, startYear, endWeek, endYear, branch, region);
		List<HProvincesCode> branchList = hProvincesCodeDao.getAllBranch();
		model.addAttribute("branchList", branchList);
		model.addAttribute("branch", branch);
		model.addAttribute("vRpWkBranch", vRpWkBranch);
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpWkBranch"));

		return new ModelAndView("wkBranch3GList");
	}

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required = true) String branch, @RequestParam(required = false) Integer startWeek,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek,
			@RequestParam(required = false) Integer endYear, ModelMap model) {

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
		
		List<HProvincesCode> branchList = hProvincesCodeDao.getAllBranch();
		model.addAttribute("branchList", branchList);

		List<VRpWkBranch3G> vRpWkBranchList = vRpWkBranchDao.filter(startWeek, startYear, endWeek, endYear, branch, region);

		model.addAttribute("vRpWkBranchList", vRpWkBranchList);
		model.addAttribute("branch", branch);
		
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpWkBranch"));
		
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
		for (VRpWkBranch3G vRpWkBranch : vRpWkBranchList) {
			categories.add(vRpWkBranch.getWeek()+"/"+vRpWkBranch.getYear());
			speechCssrList.add(vRpWkBranch.getSpeechCssr());
			cs64CssrList.add(vRpWkBranch.getCs64Cssr());
			psr99CssrList.add(vRpWkBranch.getPsr99Cssr());
			hsupaCssrList.add(vRpWkBranch.getHsupaCssr());
			hsdpaCssrList.add(vRpWkBranch.getHsdpaCssr());
			speechDrprList.add(vRpWkBranch.getSpeechDrpr());
			cs64DrprList.add(vRpWkBranch.getCs64Drpr());
			psr99DrprList.add(vRpWkBranch.getPsr99Drpr());
			hsupaDrprList.add(vRpWkBranch.getHsupaDrpr());
			hsdpaDrprList.add(vRpWkBranch.getHsdpaDrpr());
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
		String[] checkColumns = {"SPEECH_TRAFF","CS64_TRAFF","PSR99_UL_TRAFF","PSR99_DL_TRAFF","HSUPA_UL_TRAFF","HSDPA_DL_TRAFF","SPEECH_CSSR","CS64_CSSR","PSR99_CSSR","HSUPA_CSSR","HSDPA_CSSR","SPEECH_DRPR","CS64_DRPR","PSR99_DRPR","HSUPA_DRPR","HSDPA_DRPR","PSR99_UL_THROUGHTPUT","PSR99_DL_THROUGHTPUT","HSUPA_UL_THROUGHTPUT","HSDPA_DL_THROUGHTPUT","CS_IRAT_HO_SR","PS_IRAT_HO_SR","SHO_SR_OUT","SHO_SR_IN","CELL_DOWNTIME","HS_DOWNTIME","EUL_DOWNTIME","DATALOAD"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpWkBranch", 9));

		return "wkBranchQos3G";
	}
}
