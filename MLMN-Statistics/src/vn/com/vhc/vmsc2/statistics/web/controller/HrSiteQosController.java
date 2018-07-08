package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.CellDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrSiteQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrSiteQos;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
@RequestMapping("/report/radio/site-qos/hr/*")
public class HrSiteQosController extends BaseController {
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private CellDAO cellDao;
	@Autowired
	private VRpHrSiteQosDAO vRpHrSiteQosDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat ddf = new SimpleDateFormat("dd/MM");

	@RequestMapping("details")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required = true) String bscid, @RequestParam(required = true) String siteid,
			@RequestParam(required = false) String startHour, @RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model) {
		long currentTime = System.currentTimeMillis();

		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		/*if (endHour == null) {
			endHour = "23";
		} else {
			endHour = endHour.substring(0, endHour.indexOf(":"));
		}
		if (startHour == null) {
			startHour = "0";
		} else {
			startHour = startHour.substring(0, startHour.indexOf(":"));
		}
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("startHour", startHour + ":00");
		model.addAttribute("endHour", endHour + ":00");

		if (region == null)
			region = getCenter("TT2");*/
		if (endHour == null) {
			endHour = "23";
		} else {
			if(endHour.indexOf(":") >0)
				endHour = endHour.substring(0, endHour.indexOf(":"));
			else
				endHour = "23";
			}
		if (startHour == null) {
			startHour = "0";
		} else {	
			if(startHour.indexOf(":") >0)
				startHour = startHour.substring(0, startHour.indexOf(":"));
			else
				startHour = "0";
		}
		String[] hourList = {"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
		model.addAttribute("hourList", hourList);
		
		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";

		List<VRpHrSiteQos> vRpHrSiteQosDetails = new ArrayList<VRpHrSiteQos>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			vRpHrSiteQosDetails.addAll(vRpHrSiteQosDao.filter(dates.get(day).get("startHour"), dates.get(day).get("endHour"), df.format(day), bscid, siteid, region));
		}

		model.addAttribute("vRpHrSiteQosDetails", vRpHrSiteQosDetails);
		model.addAttribute("siteid", siteid);
		model.addAttribute("bscid", bscid);

		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<String> siteList = cellDao.getSiteOfBsc(bscid);
		model.addAttribute("siteList", siteList);
		model.addAttribute("region", region);
		
		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> cssrList = new ArrayList<Float>();
		List<Float> tTrafList = new ArrayList<Float>();
		List<Float> haftrateList = new ArrayList<Float>();
		List<Float> tAttList = new ArrayList<Float>();
		List<Float> tDrpsList = new ArrayList<Float>();
		List<Float> tDrprList = new ArrayList<Float>();
		List<Float> tBlksList = new ArrayList<Float>();
		List<Float> tBlkrList = new ArrayList<Float>();
		List<Float> sAttList = new ArrayList<Float>();
		List<Float> sDrpsList = new ArrayList<Float>();
		List<Float> sDrprList = new ArrayList<Float>();
		List<Float> sBlksList = new ArrayList<Float>();
		List<Float> sBlkrList = new ArrayList<Float>();
		for (VRpHrSiteQos VRpHrSiteQos : vRpHrSiteQosDetails) {
			categories.add(Integer.toString(VRpHrSiteQos.getHour()) + ":00 " + ddf.format(VRpHrSiteQos.getDay()));
			cssrList.add(VRpHrSiteQos.getCssr());
			tTrafList.add(VRpHrSiteQos.gettTraf());
			haftrateList.add(VRpHrSiteQos.getHaftratePercent());
			tAttList.add(Helper.int2Float(VRpHrSiteQos.gettAtts()));
			tDrpsList.add(Helper.int2Float(VRpHrSiteQos.gettDrps()));
			tDrprList.add(VRpHrSiteQos.gettDrpr());
			tBlksList.add(Helper.int2Float(VRpHrSiteQos.gettBlks()));
			tBlkrList.add(VRpHrSiteQos.gettBlkr());
			sAttList.add(Helper.int2Float(VRpHrSiteQos.getsAtts()));
			sDrpsList.add(Helper.int2Float(VRpHrSiteQos.getsDrps()));
			sDrprList.add(VRpHrSiteQos.getsDrpr());
			sBlksList.add(Helper.int2Float(VRpHrSiteQos.getsBlks()));
			sBlkrList.add(VRpHrSiteQos.getsBlkr());

		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("T_TRAF", tTrafList);
		series.put("HAFT_RATE (%)", haftrateList);
		series.put("T_ATT", tAttList);
		series.put("T_DRP", tDrpsList);
		series.put("T_DRPR (%)", tDrprList);
		series.put("T_NBLKS", tBlksList);
		series.put("T_NBLKR (%)", tBlkrList);
		series.put("CSSR (%)", cssrList);
		series.put("S_ATT", sAttList);
		series.put("S_DRP", sDrpsList);
		series.put("S_DRPR (%)", sDrprList);
		series.put("S_BLK", sBlksList);
		series.put("S_BLKR (%)", sBlkrList);

		model.addAttribute("chart", Chart.comboDualAxes("SITE " + siteid + " Hourly Report", categories, series));

		// checkBox
		String[] checkColumns = { "T_DEF", "T_AVAIL", "T_TRAF", "HAFT_RATE", "T_ATT", "T_SEIZ", "T_DRP", "T_DRPR", "T_BLKS", "T_BLKR", "T_HOBLKS", "T_HOBLKR", "CSSR", "S_DEF",
				"S_AVAIL", "S_ATT", "S_SEIZ", "S_DRP", "S_DRPR", "S_BLK", "S_BLKR" };
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpHrSiteQosDetail", 7));

		return "hrSiteQosDetails";
	}
}
