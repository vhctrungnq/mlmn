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

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkBscIbcDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkBscIbc;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
@RequestMapping("/report/radio/ibc/bsc/wk/*")
public class WkBscQosIbcController extends BaseController {
	@Autowired
	private VRpWkBscIbcDAO vRpWkBscIbcDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private BscDAO bscDao;

	@RequestMapping("list")
	public ModelAndView dyList(@RequestParam(required = false) String region,@RequestParam(required = false) String bscid, @RequestParam(required = false) Integer startWeek,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek,@RequestParam(required = false) Integer endYear,  ModelMap model, HttpServletRequest request) {
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
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		if (region == null)
			region = "";
		
		model.addAttribute("region", region);
		
		List<VRpWkBscIbc> vRpWkBscIbc = vRpWkBscIbcDao.filter(startWeek,startYear, endWeek, endYear, bscid, region);
		model.addAttribute("vRpWkBscIbc", vRpWkBscIbc);
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpWkBscIbc"));

		return new ModelAndView("wkBscIbcList");
	}

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required = true) String bscid,
			@RequestParam(required = false) Integer startWeek,@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear,
			ModelMap model, HttpServletRequest request) {

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
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		if (region == null)
			region = "";
		
		model.addAttribute("region", region);
		
		List<VRpWkBscIbc> vRpWkBscIbcList = vRpWkBscIbcDao.filter(startWeek,startYear, endWeek, endYear, bscid, region);

		model.addAttribute("vRpWkBscIbcList", vRpWkBscIbcList);
		model.addAttribute("bscid", bscid);
		
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpWkBscIbc"));
		
		/* Chart Start */
		List<String> categories = new ArrayList<String>();
		List<Float> cssrList = new ArrayList<Float>();
		List<Float> tTrafList = new ArrayList<Float>();
		List<Float> tTrafhList = new ArrayList<Float>();
		List<Float> haftrateList = new ArrayList<Float>();
		List<Float> tDrprList = new ArrayList<Float>();
		List<Float> tBlkrList = new ArrayList<Float>();
		List<Float> sDrprList = new ArrayList<Float>();
		List<Float> sBlkrList = new ArrayList<Float>();
		for (VRpWkBscIbc vRpWkBscIbc : vRpWkBscIbcList) {
			categories.add(vRpWkBscIbc.getWeek()+"/"+vRpWkBscIbc.getYear());
			cssrList.add(vRpWkBscIbc.getCssr());
			tTrafList.add(vRpWkBscIbc.gettTraf());
			tTrafhList.add(vRpWkBscIbc.gettTrafh());
			haftrateList.add(vRpWkBscIbc.getHalfrate());
			tDrprList.add(vRpWkBscIbc.gettDrpr());
			tBlkrList.add(vRpWkBscIbc.gettBlkr());
			sDrprList.add(vRpWkBscIbc.getsDrpr());
			sBlkrList.add(vRpWkBscIbc.getsBlkr());
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

		Map<String, List<Float>> tTrafSeries = new LinkedHashMap<String, List<Float>>();
		tTrafSeries.put("Traffic--aa4643", tTrafList);
		tTrafSeries.put("Haft Traffic--89a54e", tTrafhList);
		model.addAttribute("tTrafChart", Chart.multiColumn("tTrafChart", "Traffic", categories, tTrafSeries));
		
		// checkBox
		String[] checkColumns = {"T_TRAF", "T_DRPR", "T_NBLKR", "T_HOBLKR", "CSSR", "S_SSR", "S_BLKR", "S_DRPR", "TSR", "HAFT_RATE", "DATALOAD"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpWkBscIbc", 9));

		return "wkBscIbcQos";
	}
}
