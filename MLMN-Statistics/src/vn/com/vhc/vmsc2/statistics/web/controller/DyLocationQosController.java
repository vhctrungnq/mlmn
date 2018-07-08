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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyLocationDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyRegionDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyLocation;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyRegion;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
@RequestMapping("/report/radio/location/dy/*")
public class DyLocationQosController extends BaseController {
	@Autowired
	private VRpDyLocationDAO vRpDyLocationDao;
	@Autowired
	private VRpDyRegionDAO vRpDyRegionDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("list")
	public ModelAndView dyList(@RequestParam(required = false) String region,@RequestParam(required = false) String location,  @RequestParam(required = false) Date startDate,
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
			
			List<VRpDyLocation> vRpDyLocation = vRpDyLocationDao.filter(df.format(startDate), df.format(endDate), location, region);

			List<HProvincesCode> locationList = hProvincesCodeDao.getAllLocation();
			model.addAttribute("locationList", locationList);
			model.addAttribute("location", location);
			model.addAttribute("startDate", df.format(startDate));
			model.addAttribute("endDate", df.format(endDate));
			model.addAttribute("vRpDyLocation", vRpDyLocation);

			List<VRpDyRegion> vRpDyRegionList = vRpDyRegionDao.filter(df.format(startDate), df.format(endDate));
			model.addAttribute("vRpDyRegionList", vRpDyRegionList);
			List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
			model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyLocation"));
			
		return new ModelAndView("dyLocationList");
	}

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required = true) String location,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request) {
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
				startDay = new Date(endDay.getTime() + 25 * 24 * 60 * 60 * 1000);
			} else {
				startDay = df.parse(startDate);
			}
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("endDate", df.format(endDay));

			List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
			model.addAttribute("regionList", regionList);
			
			if (region == null)
				region = "";

			List<VRpDyLocation> vRpDyLocationList = vRpDyLocationDao.filter(df.format(startDay), df.format(endDay), location, region);

			model.addAttribute("vRpDyLocationList", vRpDyLocationList);
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

			for (VRpDyLocation vRpDyLocation : vRpDyLocationList) {
				categories.add(df.format(vRpDyLocation.getDay()));
				cssrList.add(vRpDyLocation.getCssr());
				tDrprList.add(vRpDyLocation.gettDrpr());
				tBlkrList.add(vRpDyLocation.gettBlkr());
				sDrprList.add(vRpDyLocation.getsDrpr());
				sBlkrList.add(vRpDyLocation.getsBlkr());
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
		} catch (ParseException e) {
			saveError(request, e.toString());
		}

		return "dyLocationQos";
	}
}
