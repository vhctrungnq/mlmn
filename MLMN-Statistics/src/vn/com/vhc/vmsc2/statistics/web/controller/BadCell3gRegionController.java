package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnBadCellRegion3gDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyBadCellRegion3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkBadCellRegion3gDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.MnBadCellRegion3g;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBadCellRegion3G;
import vn.com.vhc.vmsc2.statistics.domain.WkBadCellRegion3g;


@Controller
public class BadCell3gRegionController extends BaseController {
	@Autowired
	private VRpDyBadCellRegion3GDAO vRpDyBadCellRegion3GDao;
	@Autowired
	private WkBadCellRegion3gDAO wkBadCellRegion3GDao;
	@Autowired
	private MnBadCellRegion3gDAO mnBadCellRegion3GDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("/report/radio3g/bad-cell/by-region/list")
	public ModelAndView listByRegion(@RequestParam(required = false) String region, @RequestParam(required = false) Date startDate,
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

		List<VRpDyBadCellRegion3G> dyBadCell3GList = vRpDyBadCellRegion3GDao.filter(region, df.format(startDate), df.format(endDate));

		model.addAttribute("region", region);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("dyBadCell3GList", dyBadCell3GList);

		return new ModelAndView("badCell3gRegionDy");
	}

	@RequestMapping("/report/radio3g/bad-cell/by-region/mn/list")
	public ModelAndView mnListBadByRegion(@RequestParam(required = false) String region, @RequestParam(required = false) Integer startMonth,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endMonth,
			@RequestParam(required = false) Integer endYear, ModelMap model) {
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
		{
			endYear = calender.get(Calendar.YEAR);
		}
		Calendar cal = Calendar.getInstance();
		if (endMonth == null) {
			cal.add(Calendar.MONTH, -1);
			endMonth = cal.get(Calendar.MONTH) + 1;
			endYear = cal.get(Calendar.YEAR);
		}

		if (startMonth == null) {
			startMonth = endMonth;
			startYear = endYear;
		}

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";
		
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		model.addAttribute("monthList", monthList);
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		model.addAttribute("region", region);
		
		List<MnBadCellRegion3g> mnBadCellList = mnBadCellRegion3GDao.filter(region, startMonth, startYear, endMonth, endYear);

		model.addAttribute("mnBadCellList", mnBadCellList);

		return new ModelAndView("badCell3gRegionMn");
	}

	@RequestMapping("/report/radio3g/bad-cell/by-region/wk/list")
	public ModelAndView wkListBadByRegion(@RequestParam(required = false) String region, @RequestParam(required = false) Integer startWeek,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek,
			@RequestParam(required = false) Integer endYear, ModelMap model) {
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
		{
			endYear = calender.get(Calendar.YEAR);
		}
		if (endWeek == null) {
			DateTime dt = new DateTime();
			dt = dt.minusWeeks(1);
			endWeek = dt.getWeekOfWeekyear();
			endYear = dt.getYear();
		}

		if (startWeek == null) {
			startWeek = endWeek;
			startYear = endYear;
		}

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";

		model.addAttribute("startWeek", startWeek);
		model.addAttribute("endWeek", endWeek);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		model.addAttribute("region", region);

		List<WkBadCellRegion3g> wkBadCellList = wkBadCellRegion3GDao.filter(region, startWeek, startYear, endWeek, endYear);

		model.addAttribute("wkBadCellList", wkBadCellList);

		return new ModelAndView("badCell3gRegionWk");
	}
}
