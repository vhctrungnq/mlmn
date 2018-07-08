package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
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
import vn.com.vhc.vmsc2.statistics.dao.VRpWkBscData2gDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkBscData2g;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
@RequestMapping("/report/radio/bsc-data/wk/*")
public class WkBscData2gController extends BaseController {
	@Autowired
	private VRpWkBscData2gDAO vRpWkBscData2gDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private BscDAO bscDao;

	@RequestMapping("list")
	public ModelAndView dyList(@RequestParam(required = false) String region,@RequestParam(required = false) String bscid, @RequestParam(required = false) Integer startWeek,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek,@RequestParam(required = false) Integer endYear,  ModelMap model, HttpServletRequest request) {
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
		{
			endYear = calender.get(Calendar.YEAR);
		}
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
		
		List<VRpWkBscData2g> vRpWkBsc = vRpWkBscData2gDao.filter(startWeek,startYear, endWeek, endYear, bscid, region);
		model.addAttribute("vRpWkBsc", vRpWkBsc);
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpWkBsc"));

		return new ModelAndView("wkBscData2gList");
	}

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required = true) String bscid,
			@RequestParam(required = false) Integer startWeek,@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear,
			ModelMap model, HttpServletRequest request) {
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
		{
			endYear = calender.get(Calendar.YEAR);
		}
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
		
		List<VRpWkBscData2g> vRpWkBscList = vRpWkBscData2gDao.filter(startWeek,startYear, endWeek, endYear, bscid, region);

		model.addAttribute("vRpWkBscList", vRpWkBscList);
		model.addAttribute("bscid", bscid);
		
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpWkBsc"));
		
		List<String> categories = new ArrayList<String>();
		List<Float> packetChAllocSucrList = new ArrayList<Float>();
		List<Float> ulTbfSucrList = new ArrayList<Float>();
		List<Float> dlTbfSucrList = new ArrayList<Float>();
		for (VRpWkBscData2g vRpDyBsc : vRpWkBscList) {
			categories.add(vRpDyBsc.getWeek()+"/"+vRpDyBsc.getYear());
			packetChAllocSucrList.add(vRpDyBsc.getPacketChAllocSucr());
			ulTbfSucrList.add(vRpDyBsc.getUlTbfSucr());
			dlTbfSucrList.add(vRpDyBsc.getDlTbfSucr());

		}

		Map<String, List<Float>> tDrprSeries = new LinkedHashMap<String, List<Float>>();
		tDrprSeries.put("Packet CH Alloc (%)--4572a7", packetChAllocSucrList);
		model.addAttribute("tDrprChart", Chart.multiColumn("tDrprChart", "Packet CH Alloc (%)", categories, tDrprSeries));
		
		Map<String, List<Float>> sDrprSeries = new LinkedHashMap<String, List<Float>>();
		sDrprSeries.put("UL TBF Rate (%)--aa4643", ulTbfSucrList);
		model.addAttribute("sDrprChart", Chart.multiColumn("sDrprChart", "UL TBF Rate (%)", categories, sDrprSeries));

		Map<String, List<Float>> cssrSeries = new LinkedHashMap<String, List<Float>>();
		cssrSeries.put("DL TBL Rate (%)--89a54e", dlTbfSucrList);
		model.addAttribute("cssrChart", Chart.multiColumn("cssrChart", "DL TBL Rate (%)", categories, cssrSeries));
		
		// checkBox
		String[] checkColumns = {"GPRS_UL_DATA","GPRS_DL_DATA","EDGE_UL_DATA","EDGE_DL_DATA","GPRS_UL_DATA_THROUGHPUT","GPRS_DL_DATA_THROUGHPUT","EDGE_UL_DATA_THROUGHPUT","EDGE_DL_DATA_THROUGHPUT","UL_TBF_SUCR","DL_TBF_SUCR","PACKET_CH_ALLOC_SUCR"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpWkBsc", 8));

		return "wkBscData2g";
	}
}
