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

import vn.com.vhc.vmsc2.statistics.dao.MnBadCellBsc3gDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyBadCellBsc3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkBadCellBsc3gDAO;
import vn.com.vhc.vmsc2.statistics.domain.MnBadCellBsc3g;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBadCellBsc3G;
import vn.com.vhc.vmsc2.statistics.domain.WkBadCellBsc3g;


@Controller
public class BadCell3gRncController extends BaseController {
	@Autowired
	private VRpDyBadCellBsc3GDAO vRpDyBadCellBsc3GDao;
	@Autowired
	private WkBadCellBsc3gDAO wkBadCellBsc3GDao;
	@Autowired
	private MnBadCellBsc3gDAO mnBadCellBsc3GDao;
	/*@Autowired
	private ProvinceDAO provinceDao;*/

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("/report/radio3g/bad-cell/by-rnc/list")
	public ModelAndView listByBsc(@RequestParam(required = false) String region, @RequestParam(required = false) String bscid,
			@RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, ModelMap model) {
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}

		/*List<Province> regionList = provinceDao.getAllRegion();
		model.addAttribute("regionList", regionList);
			
		if (region == null)
			region = "";
		model.addAttribute("region", region);*/

		/*List<VRpDyBadCellBsc3G> dyBadCell3GList = vRpDyBadCellBsc3GDao.filter(bscid, df.format(startDate), df.format(endDate), region);*/
		
		List<VRpDyBadCellBsc3G> dyBadCell3GList = vRpDyBadCellBsc3GDao.filter(bscid, df.format(startDate), df.format(endDate));
		
		model.addAttribute("bscid", bscid);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("dyBadCell3GList", dyBadCell3GList);

		return new ModelAndView("badCell3gBscDy");
	}

	@RequestMapping("/report/radio3g/bad-cell/by-rnc/wk/list")
	public ModelAndView wkListBadByBsc(@RequestParam(required = false) String bscid, @RequestParam(required = false) Integer startWeek,
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

		model.addAttribute("startWeek", startWeek);
		model.addAttribute("endWeek", endWeek);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		model.addAttribute("bscid", bscid);

		List<WkBadCellBsc3g> wkBadCellList = wkBadCellBsc3GDao.filter(bscid, startWeek, startYear, endWeek, endYear);

		model.addAttribute("wkBadCellList", wkBadCellList);

		return new ModelAndView("badCell3gBscWk");
	}

	@RequestMapping("/report/radio3g/bad-cell/by-rnc/mn/list")
	public ModelAndView mnListBadByBsc(@RequestParam(required = false) String bscid, @RequestParam(required = false) Integer startMonth,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endMonth,
			@RequestParam(required = false) Integer endYear, ModelMap model) {
		Calendar cal = Calendar.getInstance();
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
		{
			endYear = calender.get(Calendar.YEAR);
		}
		if (endMonth == null) {
			cal.add(Calendar.MONTH, -1);
			endMonth = cal.get(Calendar.MONTH) + 1;
			endYear = cal.get(Calendar.YEAR);
		}

		if (startMonth == null) {
			startMonth = endMonth;
			startYear = endYear;
		}
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		model.addAttribute("bscid", bscid);
		model.addAttribute("monthList", monthList);

		List<MnBadCellBsc3g> mnBadCellList = mnBadCellBsc3GDao.filter(bscid, startMonth, startYear, endMonth, endYear);

		model.addAttribute("mnBadCellList", mnBadCellList);

		return new ModelAndView("badCell3gBscMn");
	}
}
