package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import vn.com.vhc.vmsc2.statistics.dao.VRpDyBranch3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyRegion3GDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBranch3G;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyRegion3G;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
@RequestMapping("/report/radio3g/branch/dy/*")
public class DyBranchQos3gController extends BaseController {
	@Autowired
	private VRpDyBranch3GDAO vRpDyBranchDao;
	@Autowired
	private VRpDyRegion3GDAO vRpDyRegionDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("list")
	public ModelAndView dyList(@RequestParam(required = false) String region,@RequestParam(required = false) String branch, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model) {
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";
		
		List<VRpDyBranch3G> vRpDyBranch = vRpDyBranchDao.filter(df.format(startDate), df.format(endDate), branch, region);

		List<HProvincesCode> branchList = hProvincesCodeDao.getAllBranch();
		model.addAttribute("branchList", branchList);
		model.addAttribute("branch", branch);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("vRpDyBranch", vRpDyBranch);
		model.addAttribute("region", region);
		
		List<VRpDyRegion3G> vRpDyRegionList = vRpDyRegionDao.filter(df.format(startDate), df.format(endDate), region);
		model.addAttribute("vRpDyRegionList", vRpDyRegionList);
		
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyBranch"));

		return new ModelAndView("dyBranch3GList");
	}

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required = true) String branch, @RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();

		try {
			Date startDay;
			Date endDay;
			if (endDate == null) {
				endDay = new Date(currentTime - 24 * 60 * 60 * 1000);
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

			List<HProvincesCode> branchList = hProvincesCodeDao.getAllBranch();
			model.addAttribute("branchList", branchList);

			List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
			model.addAttribute("regionList", regionList);
			
			if (region == null)
				region = "";

			List<VRpDyBranch3G> vRpDyBranchList = vRpDyBranchDao.filter(df.format(startDay), df.format(endDay), branch, region);

			model.addAttribute("vRpDyBranchList", vRpDyBranchList);
			model.addAttribute("branch", branch);
			model.addAttribute("region", region);
			
			List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
			model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyBranch"));

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
			for (VRpDyBranch3G vRpDyBranch : vRpDyBranchList) {
				categories.add(df.format(vRpDyBranch.getDay()));
				speechCssrList.add(vRpDyBranch.getSpeechCssr());
				cs64CssrList.add(vRpDyBranch.getCs64Cssr());
				psr99CssrList.add(vRpDyBranch.getPsr99Cssr());
				hsupaCssrList.add(vRpDyBranch.getHsupaCssr());
				hsdpaCssrList.add(vRpDyBranch.getHsdpaCssr());
				speechDrprList.add(vRpDyBranch.getSpeechDrpr());
				cs64DrprList.add(vRpDyBranch.getCs64Drpr());
				psr99DrprList.add(vRpDyBranch.getPsr99Drpr());
				hsupaDrprList.add(vRpDyBranch.getHsupaDrpr());
				hsdpaDrprList.add(vRpDyBranch.getHsdpaDrpr());
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
			String[] checkColumns = {"SPEECH_TRAFF","CS64_TRAFF","PSR99_UL_TRAFF","PSR99_DL_TRAFF","HSUPA_UL_TRAFF","HSDPA_DL_TRAFF","SPEECH_CSSR","CS64_CSSR","PSR99_CSSR","HSUPA_CSSR","HSDPA_CSSR","SPEECH_DRPR","CS64_DRPR","PSR99_DRPR","HSUPA_DRPR","HSDPA_DRPR","PSR99_UL_THROUGHTPUT","PSR99_DL_THROUGHTPUT","HSUPA_UL_THROUGHTPUT","HSDPA_DL_THROUGHTPUT","SPEECH_SHO_SR_OUT","SHO_SR_OUT","SHO_SR_IN","CS_IRAT_HO_SR","PS_IRAT_HO_SR","CELL_DOWNTIME","HS_DOWNTIME","EUL_DOWNTIME","DATALOAD"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpDyBranch", 8));

			return "dyBranchQos3G";
		} catch (ParseException e) {
			saveError(request, e.toString());
		}

		return "dyBranchQos3G";
	}
}
