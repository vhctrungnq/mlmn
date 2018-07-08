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

import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrBranchDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrBranch;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
@RequestMapping("/report/radio/branch/hr/*")
public class HrBranchQosController extends BaseController {
	@Autowired
	private VRpHrBranchDAO vRpHrBranchDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat ddf = new SimpleDateFormat("dd/MM");

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required = true) String branch, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
			ModelMap model) {

		if (endDate == null) {
			endDate = new Date();
		}
		if (startDate == null) {
			startDate = endDate;
		}
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

		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("startHour", startHour + ":00");
		model.addAttribute("endHour", endHour + ":00");

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		String[] hourList = {"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
		model.addAttribute("hourList", hourList);
		
		if (region == null)
			region = "";

		List<HProvincesCode> branchList = hProvincesCodeDao.getAllBranch();
		model.addAttribute("branchList", branchList);

		List<VRpHrBranch> vRpHrBranchList = new ArrayList<VRpHrBranch>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			vRpHrBranchList.addAll(vRpHrBranchDao.filter(dates.get(day).get("startHour"), dates.get(day).get("endHour"), df.format(day), branch, region));
		}

		model.addAttribute("vRpHrBranchList", vRpHrBranchList);
		model.addAttribute("branch", branch);
		model.addAttribute("region", region);

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpHrBranch"));
		
		// chart
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

		for (VRpHrBranch vRpHrBranch : vRpHrBranchList) {
			categories.add(Integer.toString(vRpHrBranch.getHour()) + ":00 " + ddf.format(vRpHrBranch.getDay()));
			cssrList.add(vRpHrBranch.getCssr());
			tTrafList.add(vRpHrBranch.gettTraf());
			tDrprList.add(vRpHrBranch.gettDrpr());
			tBlkrList.add(Helper.bigdecimal2Float(vRpHrBranch.gettBlkr()));
			sDrprList.add(Helper.bigdecimal2Float(vRpHrBranch.getsDrpr()));
			sBlkrList.add(Helper.bigdecimal2Float(vRpHrBranch.getsBlkr()));
			haftratePercentList.add(vRpHrBranch.getHaftratePercent());
			ogHoSucrList.add(vRpHrBranch.getOgHoSucr());
			inHoSucrList.add(vRpHrBranch.getInHoSucr());
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
		String[] checkColumns = { "T_TRAF", "T_NBLKR", "T_HOBLKR", "T_DRPR", "CSSR", "SSR", "S_BLKR", "S_DRPR", "HAFT_RATE", "IN_HO", "OG_HO" };
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpHrBranch", 5));

		return "hrBranchQos";
	}
}
