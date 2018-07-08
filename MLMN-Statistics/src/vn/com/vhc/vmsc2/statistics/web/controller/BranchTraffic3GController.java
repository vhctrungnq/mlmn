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

import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnBranchTraff3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkBranchTraff3GDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnBranchTraff3G;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkBranchTraff3G;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;


@Controller
@RequestMapping("/report/radio3g/branch/traffic/*")
public class BranchTraffic3GController extends BaseController {
	@Autowired
	private VRpMnBranchTraff3GDAO vRpMnBranchTraff3GDao;
	@Autowired
	private VRpWkBranchTraff3GDAO vRpWkBranchTraff3GDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;

	// private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("mn")
	public String showMnReport(@RequestParam(required = false) String region, @RequestParam(required = false) String branch,
			@RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {

		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		if (endMonth == null) {
			cal.add(Calendar.MONTH, -1);
			endMonth = cal.get(Calendar.MONTH) + 1;
			endYear = cal.get(Calendar.YEAR);
		}

		if (startMonth == null) {
			if (endMonth > 3) {
				startMonth = endMonth - 3;
				startYear = endYear;
			} else {
				cal1.add(Calendar.MONTH, -3);
				startMonth = cal1.get(Calendar.MONTH) + 1;
				startYear = cal1.get(Calendar.YEAR);
			}
		}
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		model.addAttribute("monthList", monthList);

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";

		List<VRpMnBranchTraff3G> mnBranchList = vRpMnBranchTraff3GDao.filter(startMonth, startYear, endMonth, endYear, branch, region);

		List<HProvincesCode> branchList = hProvincesCodeDao.getAllBranch();
		model.addAttribute("branchList", branchList);
		model.addAttribute("mnBranchList", mnBranchList);
		model.addAttribute("branch", branch);
		model.addAttribute("region", region);
		/* Chart Start */
		Map<String, List<Float>> speechTraffMap = new LinkedHashMap<String, List<Float>>();
		if (branch.isEmpty()) {
			for (HProvincesCode prv : branchList) {
				List<Float> speechTraffList = new ArrayList<Float>();
				for (VRpMnBranchTraff3G mnBranch : mnBranchList) {
					if (prv.getBranch().equalsIgnoreCase(mnBranch.getBranch())) {
						speechTraffList.add(mnBranch.getSpeechTraff());
					}
				}
				speechTraffMap.put(prv.getBranch(), speechTraffList);
			}
		} else {
			List<Float> speechTraffList = new ArrayList<Float>();
			for (VRpMnBranchTraff3G mnBranch : mnBranchList) {
				speechTraffList.add(mnBranch.getSpeechTraff());
			}
			speechTraffMap.put(branch, speechTraffList);
		}
		
		List<String> categories = new ArrayList<String>();
		for (VRpMnBranchTraff3G mnBranch : mnBranchList) {
			categories.add(mnBranch.getMonth() + "/" + mnBranch.getYear());
		}

		Map<String, List<Float>> speechTraffSeries = new LinkedHashMap<String, List<Float>>();
		for (String key : speechTraffMap.keySet()) {
			speechTraffSeries.put(key + "-spline-", speechTraffMap.get(key));
		}
		model.addAttribute("speechTraffChart", Chart.multiColumn("speechTraffChart", "Speech Traffic", categories, speechTraffSeries));

		return "mnBranchTraff3G";
	}

	@RequestMapping("wk")
	public String showWkReport(@RequestParam(required = false) String region, @RequestParam(required = false) String branch,
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

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";

		List<VRpWkBranchTraff3G> wkBranchList = vRpWkBranchTraff3GDao.filter(startWeek, startYear, endWeek, endYear, branch, region);

		List<HProvincesCode> branchList = hProvincesCodeDao.getAllBranch();
		model.addAttribute("branchList", branchList);
		model.addAttribute("wkBranchList", wkBranchList);
		model.addAttribute("branch", branch);
		model.addAttribute("region", region);
		
		/* Chart Start */
		Map<String, List<Float>> speechTraffMap = new LinkedHashMap<String, List<Float>>();
		if (branch.isEmpty()) {
			for (HProvincesCode prv : branchList) {
				List<Float> speechTraffList = new ArrayList<Float>();
				for (VRpWkBranchTraff3G wkBranch : wkBranchList) {
					if (prv.getBranch().equalsIgnoreCase(wkBranch.getBranch())) {
						speechTraffList.add(wkBranch.getSpeechTraff());
					}
				}
				speechTraffMap.put(prv.getBranch(), speechTraffList);
			}
		} else {
			List<Float> speechTraffList = new ArrayList<Float>();
			for (VRpWkBranchTraff3G wkBranch : wkBranchList) {
				speechTraffList.add(wkBranch.getSpeechTraff());
			}
			speechTraffMap.put(branch, speechTraffList);
		}

		List<String> categories = new ArrayList<String>();
		for (VRpWkBranchTraff3G wkBranch : wkBranchList) {
			categories.add(wkBranch.getWeek() + "/" + wkBranch.getYear());
		}

		Map<String, List<Float>> speechTraffSeries = new LinkedHashMap<String, List<Float>>();
		for (String key : speechTraffMap.keySet()) {
			speechTraffSeries.put(key + "-spline-", speechTraffMap.get(key));
		}

		model.addAttribute("speechTraffChart", Chart.multiColumn("speechTraffChart", "Speech Traffic", categories, speechTraffSeries));

		return "wkBranchTraff3G";
	}
}
