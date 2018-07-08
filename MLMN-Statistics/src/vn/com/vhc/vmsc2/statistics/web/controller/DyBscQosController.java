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

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyBscDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyRegionDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBsc;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyRegion;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;
import vn.com.vhc.vmsc2.statistics.web.utils.ModelAddtributes;



@Controller
@RequestMapping("/report/radio/bsc/dy/*")
public class DyBscQosController extends BaseController {
	@Autowired
	private VRpDyBscDAO vRpDyBscDao; 
	@Autowired
	private VRpDyRegionDAO vRpDyRegionDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("list")
	public ModelAndView dyList(@RequestParam(required = false) String region,@RequestParam(required = false) String bscid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model) {
		
		// Load region
		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		region = ModelAddtributes.checkRegion(model, regionList, region,"list.htm");
		String[] checkDate = ModelAddtributes.checkDate(model, startDate, endDate).split(";");
		List<Bsc> bscList = bscDao.getAll();
	    bscid = ModelAddtributes.checkBscid(model, bscList, bscid, "");
		List<VRpDyBsc> vRpDyBsc = vRpDyBscDao.filter(checkDate[0], checkDate[1], bscid, region);
		List<VRpDyRegion> vRpDyRegionList = vRpDyRegionDao.filter(checkDate[0], checkDate[1], region);
		
		
		model.addAttribute("vRpDyBsc", vRpDyBsc);
		model.addAttribute("vRpDyRegionList", vRpDyRegionList);
		
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyBsc"));
		return new ModelAndView("dyBscList");
	}

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required = true) String bscid, @RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request) {
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

			List<VRpDyBsc> vRpDyBscList = vRpDyBscDao.filter(df.format(startDay), df.format(endDay), bscid, region);

			model.addAttribute("vRpDyBscList", vRpDyBscList);
			model.addAttribute("bscid", bscid);
			model.addAttribute("region", region);
			
			List<Bsc> bscList = bscDao.getAll();
			model.addAttribute("bscList", bscList);
			
			List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
			model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyBsc"));

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
			for (VRpDyBsc vRpDyBsc : vRpDyBscList) {
				categories.add(df.format(vRpDyBsc.getDay()));
				cssrList.add(vRpDyBsc.getCssr());
				tTrafList.add(vRpDyBsc.gettTraf());
				tTrafhList.add(vRpDyBsc.gettTrafh());
				haftrateList.add(vRpDyBsc.getHalfrate());
				tDrprList.add(vRpDyBsc.gettDrpr());
				tBlkrList.add(vRpDyBsc.gettBlkr());
				sDrprList.add(vRpDyBsc.getsDrpr());
				sBlkrList.add(vRpDyBsc.getsBlkr());

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
			String[] checkColumns = {"", "T_TRAF", "", "T_DRPR", "T_NBLKR", "T_HOBLKR", "CSSR", "S_SSR", "S_BLKR", "", "S_DRPR", "TSR", "HAFT_RATE", "DATALOAD"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpDyBsc", 10));
			
		} catch (ParseException e) {
			saveError(request, e.toString());
		}

		return "dyBscQos";
	}
}
