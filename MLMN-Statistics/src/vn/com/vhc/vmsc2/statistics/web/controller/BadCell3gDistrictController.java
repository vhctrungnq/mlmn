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

import vn.com.vhc.vmsc2.statistics.dao.MnBadCellDistrict3gDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyBadCellDistrict3gDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkBadCellDistrict3gDAO;
import vn.com.vhc.vmsc2.statistics.domain.MnBadCellDistrict3g;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBadCellDistrict3g;
import vn.com.vhc.vmsc2.statistics.domain.WkBadCellDistrict3g;


@Controller
public class BadCell3gDistrictController extends BaseController{
	@Autowired
	private VRpDyBadCellDistrict3gDAO vRpDyBadCellDistrict3GDao;
	@Autowired
	private WkBadCellDistrict3gDAO wkBadCellDistrict3GDao;
	@Autowired
	private MnBadCellDistrict3gDAO mnBadCellDistrict3GDao;

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("/report/radio3g/bad-cell/by-district/list")
	public ModelAndView listByDistrict(@RequestParam(required = false) String province, @RequestParam(required = false) String district,
			@RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, ModelMap model) {
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		List<VRpDyBadCellDistrict3g> dyBadCell3GList = vRpDyBadCellDistrict3GDao.filter(province, district, df.format(startDate), df.format(endDate));

		model.addAttribute("province", province);
		model.addAttribute("district", district);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("dyBadCell3GList", dyBadCell3GList);

		return new ModelAndView("badCell3gDistrictDy");
	}

	@RequestMapping("/report/radio3g/bad-cell/by-district/wk/list")
	public ModelAndView wkListBadByDistrict(@RequestParam(required = false) String province, @RequestParam(required = false) String district,
			@RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model) {
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
		model.addAttribute("province", province);
		model.addAttribute("district", district);

		List<WkBadCellDistrict3g> wkBadCellList = wkBadCellDistrict3GDao.filter(province, district, startWeek, startYear, endWeek, endYear);

		model.addAttribute("wkBadCellList", wkBadCellList);

		return new ModelAndView("badCell3gDistrictWk");
	}

	@RequestMapping("/report/radio3g/bad-cell/by-district/mn/list")
	public ModelAndView mnListBadByDistrict(@RequestParam(required = false) String province, @RequestParam(required = false) String district,
			@RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model) {
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
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		model.addAttribute("province", province);
		model.addAttribute("district", district);
		model.addAttribute("monthList", monthList);

		List<MnBadCellDistrict3g> mnBadCellList = mnBadCellDistrict3GDao.filter(province, district, startMonth, startYear, endMonth, endYear);

		model.addAttribute("mnBadCellList", mnBadCellList);

		return new ModelAndView("badCell3gDistrictMn");
	}
}
