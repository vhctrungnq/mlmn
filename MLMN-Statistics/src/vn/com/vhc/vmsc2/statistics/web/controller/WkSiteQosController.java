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
import vn.com.vhc.vmsc2.statistics.dao.CellDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkSiteQosBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkSiteQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkSiteQos;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkSiteQosBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
public class WkSiteQosController extends BaseController {
	@Autowired
	private VRpWkSiteQosDAO vRpWkSiteQosDao;
	@Autowired
	private VRpWkSiteQosBhDAO vRpWkSiteQosBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private CellDAO cellDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;

	@RequestMapping("/report/radio/site-qos/wk/list")
	public ModelAndView dySiteQosList(@RequestParam(required = false) String region,@RequestParam(required = false) String province, @RequestParam(required = false) String bscid,
			@RequestParam(required = false) String siteid, @RequestParam(required = false) Integer week, @RequestParam(required = false) Integer year,
			ModelMap model, HttpServletRequest request) {

		DateTime dt = new DateTime();
		dt = dt.minusWeeks(1);

		if (week == null) {
			week = dt.getWeekOfWeekyear();
		}
		
		if(year == null)
			year = dt.getYear();

		if (region == null)
			region = getCenter("TT2");
		model.addAttribute("region", region);
		
		List<VRpWkSiteQos> vRpWkSiteQos = vRpWkSiteQosDao.filter(province, bscid, siteid, week, year, region);

		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("week", week);
		model.addAttribute("year", year);
		model.addAttribute("vRpWkSiteQos", vRpWkSiteQos);
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpWkSiteQos"));
		return new ModelAndView("wkSiteQosList");
	}

	@RequestMapping("/report/radio/site-qos/wk/details")
	public String sQoswReport(@RequestParam(required = false) String region,@RequestParam(required = true) String bscid, @RequestParam(required = true) String siteid,
			@RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {

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
		model.addAttribute("region", region);
		
		List<VRpWkSiteQos> vRpWkSiteQosDetails = vRpWkSiteQosDao.filterDetails(startWeek.toString(), startYear.toString(), endWeek.toString(),
				endYear.toString(), bscid, siteid, region);

		model.addAttribute("vRpWkSiteQosDetails", vRpWkSiteQosDetails);
		model.addAttribute("siteid", siteid);
		model.addAttribute("bscid", bscid);

		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<String> siteList = cellDao.getSiteOfBsc(bscid);
		model.addAttribute("siteList", siteList);

		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> cssrList = new ArrayList<Float>();
		List<Float> tTrafList = new ArrayList<Float>();
		List<Float> tDrprList = new ArrayList<Float>();
		List<Float> tBlkrList = new ArrayList<Float>();
		List<Float> sDrpsList = new ArrayList<Float>();
		List<Float> sDrprList = new ArrayList<Float>();
		List<Float> sBlksList = new ArrayList<Float>();
		List<Float> sBlkrList = new ArrayList<Float>();

		for (VRpWkSiteQos vRpWkSite : vRpWkSiteQosDetails) {
			categories.add("w" + vRpWkSite.getWeek().toString() + "/" + vRpWkSite.getYear().toString());
			cssrList.add(vRpWkSite.getCssr());
			tTrafList.add(vRpWkSite.gettTraf());
			tDrprList.add(vRpWkSite.gettDrpr());
			tBlkrList.add(vRpWkSite.gettBlkr());
			sDrpsList.add(Helper.int2Float(vRpWkSite.getsDrps()));
			sDrprList.add(vRpWkSite.getsDrpr());
			sBlksList.add(Helper.int2Float(vRpWkSite.getsBlks()));
			sBlkrList.add(vRpWkSite.getsBlkr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("T_TRAF", tTrafList);
		series.put("T_DRPR (%)", tDrprList);
		series.put("T_NBLKR (%)", tBlkrList);
		series.put("CSSR (%)", cssrList);
		series.put("S_DRP", sDrpsList);
		series.put("S_DRPR (%)", sDrprList);
		series.put("S_BLK", sBlksList);
		series.put("S_BLKR (%)", sBlkrList);

		model.addAttribute("chart", Chart.comboDualAxes("SITE " + siteid + " Weekly Report", categories, series));

		// checkBox
		String[] checkColumns = { "T_TRAF", "T_DRP", "T_DRPR", "T_NBLKS", "T_NBLKR", "T_HOBLKS", "T_HOBLKR", "CSSR", "S_DRP", "S_DRPR", "S_BLK", "S_BLKR", "DATALOAD" };
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpWkSiteQosDetail", 6));

		return "wkSiteQosDetails";
	}

	@RequestMapping("/report/radio/site-qos/wk/bhDetails")
	public String sQoswReportBhDetails(@RequestParam(required = false) String region,@RequestParam(required = true) String bscid, @RequestParam(required = true) String siteid,
			@RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {

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
		model.addAttribute("region", region);
		
		List<VRpWkSiteQosBh> vRpWkSiteQosBhDetails = vRpWkSiteQosBhDao.filterDetails(startWeek.toString(), startYear.toString(), endWeek.toString(),
				endYear.toString(), bscid, siteid, region);

		model.addAttribute("vRpWkSiteQosBhDetails", vRpWkSiteQosBhDetails);
		model.addAttribute("siteid", siteid);
		model.addAttribute("bscid", bscid);

		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<String> siteList = cellDao.getSiteOfBsc(bscid);
		model.addAttribute("siteList", siteList);

		/* Start Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> bhCssrList = new ArrayList<Float>();
		List<Float> bhTNblksList = new ArrayList<Float>();
		List<Float> bhTNblkrList = new ArrayList<Float>();
		List<Float> bhTDrpsList = new ArrayList<Float>();
		List<Float> bhTDrprList = new ArrayList<Float>();
		List<Float> bhTTrafList = new ArrayList<Float>();
		List<Float> bhTTrafhList = new ArrayList<Float>();
		List<Float> bhTGosList = new ArrayList<Float>();
		List<Float> bhSBlksList = new ArrayList<Float>();
		List<Float> bhSBlkrList = new ArrayList<Float>();
		List<Float> bhSDrpsList = new ArrayList<Float>();
		List<Float> bhSDrprList = new ArrayList<Float>();
		for (VRpWkSiteQosBh VRpWkSiteQosBh : vRpWkSiteQosBhDetails) {
			categories.add(VRpWkSiteQosBh.getWeek() + "/" + VRpWkSiteQosBh.getYear());
			bhCssrList.add(VRpWkSiteQosBh.getBhCssr());
			bhTNblksList.add(VRpWkSiteQosBh.getBhTNblks());
			bhTNblkrList.add(VRpWkSiteQosBh.getBhTNblkr());
			bhTDrpsList.add(VRpWkSiteQosBh.getBhTDrps());
			bhTDrprList.add(VRpWkSiteQosBh.getBhTDrpr());
			bhTTrafList.add(VRpWkSiteQosBh.getBhTTraf());
			bhTTrafhList.add(VRpWkSiteQosBh.getBhTTrafh());
			bhTGosList.add(VRpWkSiteQosBh.getBhTGos());
			bhSBlksList.add(VRpWkSiteQosBh.getBhSBlks());
			bhSBlkrList.add(VRpWkSiteQosBh.getBhSBlkr());
			bhSDrpsList.add(VRpWkSiteQosBh.getBhSDrps());
			bhSDrprList.add(VRpWkSiteQosBh.getBhSDrpr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("TRAF", bhTTrafList);
		series.put("H_TRAF", bhTTrafhList);
		series.put("T_DRPS", bhTDrpsList);
		series.put("T_DRPR (%)", bhTDrprList);
		series.put("T_NBLKS", bhTNblksList);
		series.put("T_NBLKR (%)", bhTNblkrList);
		series.put("CSSR (%)", bhCssrList);
		series.put("GoS (%)", bhTGosList);
		series.put("S_DRPS", bhSDrpsList);
		series.put("S_DRPR (%)", bhSDrprList);
		series.put("S_BLKS", bhSBlksList);
		series.put("S_BLKR (%)", bhSBlkrList);

		model.addAttribute("chart", Chart.comboDualAxes("SITE BH " + siteid + " Weekly Report", categories, series));
		// checkBox
		String[] checkColumns = { "T_DEF", "T_AVAIL", "T_ATT", "TRAF", "H_TRAF", "T_DRPS", "T_DRPR", "T_NBLKS", "T_NBLKR", "T_HOBLKS", "T_HOBLKR", "CSSR", "GoS", "S_DEF", "S_AVAIL",
				"S_ATT", "S_DRPS", "S_DRPR", "S_BLKS", "S_BLKR" };
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpWkSiteQosBhDetails", 6));
		/* End Chart */

		return "wkSiteQosBhDetails";
	}
}
