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
import vn.com.vhc.vmsc2.statistics.dao.VRpWkBranchDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkBranch;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
@RequestMapping("/report/radio/branch/wk/*")
public class WkBranchQosController extends BaseController {
	@Autowired
	private VRpWkBranchDAO vRpWkBranchDao;
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

		List<VRpWkBranch> vRpWkBranch = vRpWkBranchDao.filter(startWeek, startYear, endWeek, endYear, branch, region);
		List<HProvincesCode> branchList = hProvincesCodeDao.getAllBranch();
		model.addAttribute("branchList", branchList);
		model.addAttribute("branch", branch);
		model.addAttribute("region", region);
		model.addAttribute("vRpWkBranch", vRpWkBranch);
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpWkBranch"));

		return new ModelAndView("wkBranchList");
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

		List<HProvincesCode> branchList = hProvincesCodeDao.getAllBranch();
		model.addAttribute("branchList", branchList);

		List<VRpWkBranch> vRpWkBranchList = vRpWkBranchDao.filter(startWeek, startYear, endWeek, endYear, branch, region);

		model.addAttribute("vRpWkBranchList", vRpWkBranchList);
		model.addAttribute("branch", branch);
		model.addAttribute("region", region);
		
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpWkBranch"));
		
		// start chart
		List<String> categories = new ArrayList<String>();
		List<Float> cssrList = new ArrayList<Float>();
		List<Float> tTrafList = new ArrayList<Float>();
		List<Float> haftratePercentList = new ArrayList<Float>();
		List<Float> tDrprList = new ArrayList<Float>();
		List<Float> tBlkrList = new ArrayList<Float>();
		List<Float> sDrprList = new ArrayList<Float>();
		List<Float> sBlkrList = new ArrayList<Float>();
		List<Float> ogHoSucrList = new ArrayList<Float>();
		List<Float> inHoSucrList = new ArrayList<Float>();

		for (VRpWkBranch vRpWkBranch : vRpWkBranchList) {
			categories.add(Integer.toString(vRpWkBranch.getWeek()) + "/" + vRpWkBranch.getYear());
			cssrList.add(vRpWkBranch.getCssr());
			tTrafList.add(vRpWkBranch.gettTraf());
			tDrprList.add(vRpWkBranch.gettDrpr());
			tBlkrList.add(vRpWkBranch.gettBlkr());
			sDrprList.add(Helper.bigdecimal2Float(vRpWkBranch.getsDrpr()));
			sBlkrList.add(vRpWkBranch.getsBlkr());
			haftratePercentList.add(Helper.bigdecimal2Float(vRpWkBranch.getHaftratePercent()));
			ogHoSucrList.add(vRpWkBranch.getOgHoSucr());
			inHoSucrList.add(vRpWkBranch.getInHoSucr());
		}
		
		Map<String, List<Float>> tDrprSeries = new LinkedHashMap<String, List<Float>>();
		tDrprSeries.put("Drop Rate (%)--4572a7", tDrprList);
		model.addAttribute("tDrprChart", Chart.multiColumn("tDrprChart", "TCH Drop Rate (%)", categories, tDrprSeries));

		Map<String, List<Float>> sDrprSeries = new LinkedHashMap<String, List<Float>>();
		sDrprSeries.put("Drop Rate (%)--aa4643", sDrprList);
		model.addAttribute("sDrprChart", Chart.multiColumn("sDrprChart", "SDCCH Drop Rate (%)", categories, sDrprSeries));

		Map<String, List<Float>> cssrSeries = new LinkedHashMap<String, List<Float>>();
		cssrSeries.put("CSSR (%)--89a54e", cssrList);
		model.addAttribute("cssrChart", Chart.multiColumn("cssrChart", "CSSR (%)", categories, cssrSeries));

		Map<String, List<Float>> tBlkrSeries = new LinkedHashMap<String, List<Float>>();
		tBlkrSeries.put("Block Rate (%)--80699b", tBlkrList);
		model.addAttribute("tBlkrChart", Chart.multiColumn("tBlkrChart", "TCH Block Rate (%)", categories, tBlkrSeries));

		Map<String, List<Float>> sBlkrSeries = new LinkedHashMap<String, List<Float>>();
		sBlkrSeries.put("Block Rate (%)--db843d", sBlkrList);
		model.addAttribute("sBlkrChart", Chart.multiColumn("sBlkrChart", "SDCCH Block Rate (%)", categories, sBlkrSeries));

		// checkBox
		String[] checkColumns = { "T_TRAF", "T_NBLKR", "T_HOBLKR", "T_DRPR", "CSSR", "SSR", "S_BLKR", "S_DRPR", "HAFT_RATE", "IN_HO", "OG_HO", "DATALOAD" };
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpWkBranch", 8));

		return "wkBranchQos";
	}
}
