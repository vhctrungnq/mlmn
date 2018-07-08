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
import vn.com.vhc.vmsc2.statistics.dao.VRpWkLocationDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkLocation;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
@RequestMapping("/report/radio/location/wk/*")
public class WkLocationQosController extends BaseController {
	@Autowired
	private VRpWkLocationDAO vRpWkLocationDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;

	// private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("list")
	public ModelAndView dyList(@RequestParam(required = false) String region,@RequestParam(required = false) String location, @RequestParam(required = false) Integer startWeek,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
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

		if (region == null)
			region = getCenter("TT2");

		List<VRpWkLocation> vRpWkLocation = vRpWkLocationDao.filter(startWeek,startYear, endWeek, endYear, location, region);
		List<HProvincesCode> locationList = hProvincesCodeDao.getAllLocation();
		model.addAttribute("locationList", locationList);
		model.addAttribute("location", location);
		model.addAttribute("vRpWkLocation", vRpWkLocation);
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpWkLocation"));

		return new ModelAndView("wkLocationList");
	}

	@RequestMapping("detail")
	public String showReport( @RequestParam(required = false) String region,@RequestParam(required = true) String location,
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

		if (region == null)
			region = getCenter("TT2");

		List<VRpWkLocation> vRpWkLocationList = vRpWkLocationDao.filter(startWeek,startYear, endWeek, endYear, location, region);

		model.addAttribute("vRpWkLocationList", vRpWkLocationList);
		List<HProvincesCode> locationList = hProvincesCodeDao.getAllLocation();
		model.addAttribute("locationList", locationList);
		model.addAttribute("location", location);
		
		/* Chart Start */
		List<String> categories = new ArrayList<String>();
		List<Float> cssrList = new ArrayList<Float>();
		List<Float> tDrprList = new ArrayList<Float>();
		List<Float> tBlkrList = new ArrayList<Float>();
		List<Float> sDrprList = new ArrayList<Float>();
		List<Float> sBlkrList = new ArrayList<Float>();

		for (VRpWkLocation vRpWkLocation : vRpWkLocationList) {
			categories.add(vRpWkLocation.getWeek().toString()+ "/" + vRpWkLocation.getYear());
			cssrList.add(vRpWkLocation.getCssr());
			tDrprList.add(vRpWkLocation.gettDrpr());
			tBlkrList.add(vRpWkLocation.gettBlkr());
			sDrprList.add(vRpWkLocation.getsDrpr());
			sBlkrList.add(vRpWkLocation.getsBlkr());
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
		/* Chart End*/

		// checkBox
		String[] checkColumns = { "T_TRAF", "HAFT_RATE", "CSSR", "TSR", "T_DRPR", "T_NBLKR", "SSR", "S_DRPR", "S_BLKR", "Og_Ho_Sucr", "In_Ho_Sucr", "DATALOAD" };
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 7));
		
		return "wkLocationQos";
	}
}
