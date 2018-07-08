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
import vn.com.vhc.vmsc2.statistics.dao.VRpMnBranchTrafDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkBranchTrafDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnBranchTraf;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkBranchTraf;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;

@Controller
@RequestMapping("/report/radio/branch/traffic/*")
public class BranchTrafficController extends BaseController {
	@Autowired
	private VRpMnBranchTrafDAO vRpMnBranchTrafDao;
	@Autowired
	private VRpWkBranchTrafDAO vRpWkBranchTrafDao;
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

		if (region == null)
			region = getCenter("TT2");

		List<VRpMnBranchTraf> mnBranchList = vRpMnBranchTrafDao.filter(startMonth, startYear, endMonth, endYear, branch, region);

		List<HProvincesCode> branchList = hProvincesCodeDao.getAllBranch();
		model.addAttribute("branchList", branchList);
		model.addAttribute("mnBranchList", mnBranchList);
		model.addAttribute("branch", branch);
		model.addAttribute("region", region);
		/* Chart Start */
		Map<String, List<Float>> tTrafMap = new LinkedHashMap<String, List<Float>>();
		if (branch.isEmpty()) {
			for (HProvincesCode prv : branchList) {
				List<Float> tTrafList = new ArrayList<Float>();
				for (VRpMnBranchTraf mnBranch : mnBranchList) {
					if (prv.getBranch().equalsIgnoreCase(mnBranch.getBranch())) {
						tTrafList.add(mnBranch.gettTraf());
					}
				}
				tTrafMap.put(prv.getBranch(), tTrafList);
			}
		} else {
			List<Float> tTrafList = new ArrayList<Float>();
			for (VRpMnBranchTraf mnBranch : mnBranchList) {
				tTrafList.add(mnBranch.gettTraf());
			}
			tTrafMap.put(branch, tTrafList);
		}
		
		List<String> categories = new ArrayList<String>();
		for (VRpMnBranchTraf mnBranch : mnBranchList) {
			categories.add(mnBranch.getMonth() + "/" + mnBranch.getYear());
		}

		Map<String, List<Float>> tTrafSeries = new LinkedHashMap<String, List<Float>>();
		for (String key : tTrafMap.keySet()) {
			tTrafSeries.put(key + "-spline-", tTrafMap.get(key));
		}
		model.addAttribute("tTrafChart", Chart.multiColumn("tTrafChart", "Traffic", categories, tTrafSeries));

		return "mnBranch";
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

		if (region == null)
			region = getCenter("TT2");

		List<VRpWkBranchTraf> wkBranchList = vRpWkBranchTrafDao.filter(startWeek, startYear, endWeek, endYear, branch, region);

		List<HProvincesCode> branchList = hProvincesCodeDao.getAllBranch();
		model.addAttribute("branchList", branchList);
		model.addAttribute("wkBranchList", wkBranchList);
		model.addAttribute("branch", branch);
		model.addAttribute("region", region);
		
		/* Chart Start */
		Map<String, List<Float>> tTrafMap = new LinkedHashMap<String, List<Float>>();
		if (branch == null) {
			for (HProvincesCode prv : branchList) {
				List<Float> tTrafList = new ArrayList<Float>();
				for (VRpWkBranchTraf wkBranch : wkBranchList) {
					if (prv.getBranch().equalsIgnoreCase(wkBranch.getBranch())) {
						tTrafList.add(wkBranch.gettTraf());
					}
				}
				tTrafMap.put(prv.getBranch(), tTrafList);
			}
		} else {
			List<Float> tTrafList = new ArrayList<Float>();
			for (VRpWkBranchTraf wkBranch : wkBranchList) {
				tTrafList.add(wkBranch.gettTraf());
			}
			tTrafMap.put(branch, tTrafList);
		}

		List<String> categories = new ArrayList<String>();
		for (VRpWkBranchTraf wkBranch : wkBranchList) {
			categories.add(wkBranch.getWeek() + "/" + wkBranch.getYear());
		}

		Map<String, List<Float>> tTrafSeries = new LinkedHashMap<String, List<Float>>();
		for (String key : tTrafMap.keySet()) {
			tTrafSeries.put(key + "-spline-", tTrafMap.get(key));
		}

		model.addAttribute("tTrafChart", Chart.multiColumn("tTrafChart", "Traffic", categories, tTrafSeries));

		return "wkBranch";
	}
}
