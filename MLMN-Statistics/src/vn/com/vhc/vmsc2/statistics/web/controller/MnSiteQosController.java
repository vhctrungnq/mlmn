package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
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
import vn.com.vhc.vmsc2.statistics.dao.CellDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnSiteQosBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnSiteQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnSiteQos;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnSiteQosBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
public class MnSiteQosController extends BaseController {
	@Autowired
	private VRpMnSiteQosDAO vRpMnSiteQosDao;
	@Autowired
	private VRpMnSiteQosBhDAO vRpMnSiteQosBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private CellDAO cellDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;

	@RequestMapping("/report/radio/site-qos/mn/list")
	public ModelAndView dySiteQosList(@RequestParam(required = false) String region,@RequestParam(required = false) String province, @RequestParam(required = false) String bscid,
			@RequestParam(required = false) String siteid, @RequestParam(required = false) Integer month, @RequestParam(required = false) Integer year,
			ModelMap model, HttpServletRequest request) {

		Calendar cal = Calendar.getInstance();
		if (month == null) {
			cal.add(Calendar.MONTH,-1);
			month = cal.get(Calendar.MONTH)+1;
			if (year == null)
				year = cal.get(Calendar.YEAR);
		}

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
	    model.addAttribute("monthList", monthList);
		
		if (region == null)
			region = "";
		
		List<VRpMnSiteQos> vRpMnSiteQos = vRpMnSiteQosDao.filter(province, bscid, siteid, month, year, region);

		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		model.addAttribute("vRpMnSiteQos", vRpMnSiteQos);
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpMnSiteQos"));
		return new ModelAndView("mnSiteQosList");
	}

	@RequestMapping("/report/radio/site-qos/mn/details")
	public String sQoswReport(@RequestParam(required = false) String region,@RequestParam(required = true) String bscid, @RequestParam(required = true) String siteid,
			@RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {

		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		if (endMonth == null) {
			cal.add(Calendar.MONTH,-1);
			endMonth = cal.get(Calendar.MONTH)+1;
			endYear = cal.get(Calendar.YEAR);
		}

		if (startMonth == null) {
			if (endMonth > 3) {
				startMonth = endMonth - 3;
				startYear = endYear;
			} else {
				cal1.add(Calendar.MONTH, -3);
				startMonth = cal1.get(Calendar.MONTH)+1;
				startYear = cal1.get(Calendar.YEAR);
			}
		}
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
	    model.addAttribute("monthList", monthList);
		
		if (region == null)
			region = "";
		
		List<VRpMnSiteQos> vRpMnSiteQosDetails = vRpMnSiteQosDao.filterDetails(startMonth.toString(), startYear.toString(), endMonth.toString(),
				endYear.toString(), bscid, siteid, region);

		model.addAttribute("vRpMnSiteQosDetails", vRpMnSiteQosDetails);
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
		List<Float> tDrpsList = new ArrayList<Float>();
		List<Float> tDrprList = new ArrayList<Float>();
		List<Float> tBlkrList = new ArrayList<Float>();
		List<Float> sDrpsList = new ArrayList<Float>();
		List<Float> sDrprList = new ArrayList<Float>();
		List<Float> sBlksList = new ArrayList<Float>();
		List<Float> sBlkrList = new ArrayList<Float>();

		for (VRpMnSiteQos vRpMnSiteQos : vRpMnSiteQosDetails) {
			categories.add(vRpMnSiteQos.getMonth().toString() + "/" + vRpMnSiteQos.getYear());
			cssrList.add(vRpMnSiteQos.getCssr());
			tTrafList.add(vRpMnSiteQos.gettTraf());
			tDrpsList.add(Helper.int2Float(vRpMnSiteQos.gettDrps()));
			tDrprList.add(vRpMnSiteQos.gettDrpr());
			tBlkrList.add(vRpMnSiteQos.gettBlkr());
			sDrpsList.add(Helper.int2Float(vRpMnSiteQos.getsDrps()));
			sDrprList.add(vRpMnSiteQos.getsDrpr());
			sBlksList.add(Helper.int2Float(vRpMnSiteQos.getsBlks()));
			sBlkrList.add(vRpMnSiteQos.getsBlkr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("T_TRAF", tTrafList);
		series.put("T_DRP", tDrpsList);
		series.put("T_DRPR (%)", tDrprList);
		series.put("T_NBLKR (%)", tBlkrList);
		series.put("CSSR (%)", cssrList);
		series.put("S_DRP", sDrpsList);
		series.put("S_DRPR (%)", sDrprList);
		series.put("S_BLK", sBlksList);
		series.put("S_BLKR (%)", sBlkrList);

		model.addAttribute("chart", Chart.comboDualAxes("SITE " + siteid + " Monthly Report", categories, series));

		// checkBox
		String[] checkColumns = { "T_TRAF", "T_DRP", "T_DRPR", "T_NBLKS", "T_NBLKR", "T_HOBLKS", "T_HOBLKR", "CSSR", "S_DRP", "S_DRPR", "S_BLK", "S_BLKR", "DATALOAD" };
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpMnSiteQosDetail", 6));
		
		return "mnSiteQosDetails";
	}

	@RequestMapping("/report/radio/site-qos/mn/bhDetails")
	public String sQoswReportBhDetails(@RequestParam(required = false) String region,@RequestParam(required = true) String bscid, @RequestParam(required = true) String siteid,
			@RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {

		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		if (endMonth == null) {
			endMonth = cal.get(Calendar.MONTH)+1;
			endYear = cal.get(Calendar.YEAR);
		}

		if (startMonth == null) {
			if (endMonth > 3) {
				startMonth = endMonth - 3;
				startYear = endYear;
			} else {
				cal1.add(Calendar.MONTH, -3);
				startMonth = cal1.get(Calendar.MONTH)+1;
				startYear = cal1.get(Calendar.YEAR);
			}
		}
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", startYear);

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
	    model.addAttribute("monthList", monthList);
		
		if (region == null)
			region = "";
		
		List<VRpMnSiteQosBh> vRpMnSiteQosBhDetails = vRpMnSiteQosBhDao.filterDetails(startMonth.toString(), startYear.toString(), endMonth.toString(),
				endYear.toString(), bscid, siteid, region);

		model.addAttribute("vRpMnSiteQosBhDetails", vRpMnSiteQosBhDetails);
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
		for (VRpMnSiteQosBh VRpMnSiteQosBh : vRpMnSiteQosBhDetails) {
			categories.add(VRpMnSiteQosBh.getMonth() + "/" + VRpMnSiteQosBh.getYear());
			bhCssrList.add(VRpMnSiteQosBh.getBhCssr());
			bhTNblksList.add(VRpMnSiteQosBh.getBhTNblks());
			bhTNblkrList.add(VRpMnSiteQosBh.getBhTNblkr());
			bhTDrpsList.add(VRpMnSiteQosBh.getBhTDrps());
			bhTDrprList.add(VRpMnSiteQosBh.getBhTDrpr());
			bhTTrafList.add(VRpMnSiteQosBh.getBhTTraf());
			bhTTrafhList.add(VRpMnSiteQosBh.getBhTTrafh());
			bhTGosList.add(VRpMnSiteQosBh.getBhTGos());
			bhSBlksList.add(VRpMnSiteQosBh.getBhSBlks());
			bhSBlkrList.add(VRpMnSiteQosBh.getBhSBlkr());
			bhSDrpsList.add(VRpMnSiteQosBh.getBhSDrps());
			bhSDrprList.add(VRpMnSiteQosBh.getBhSDrpr());
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

		model.addAttribute("chart", Chart.comboDualAxes("SITE BH " + siteid + " Monthly Report", categories, series));
		// checkBox
		String[] checkColumns = { "T_DEF", "T_AVAIL", "T_ATT", "TRAF", "H_TRAF", "T_DRPS", "T_DRPR", "T_NBLKS", "T_NBLKR", "T_HOBLKR", "CSSR", "GoS", "S_DEF", "S_AVAIL",
				"S_ATT", "S_DRPS", "S_DRPR", "S_BLKS", "S_BLKR" };
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpMnSiteQosBhDetail", 6));
		/* End Chart */
		return "mnSiteQosBhDetails";
	}
}
