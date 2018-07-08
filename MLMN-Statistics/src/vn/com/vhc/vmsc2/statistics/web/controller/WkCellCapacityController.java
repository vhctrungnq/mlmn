package vn.com.vhc.vmsc2.statistics.web.controller;


import java.util.Calendar;
import java.util.List;

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
import vn.com.vhc.vmsc2.statistics.dao.VRpWkCellCapacityDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkCellCapacity;


@Controller
public class WkCellCapacityController extends BaseController{
	@Autowired
	private VRpWkCellCapacityDAO vRpWkCellCapacityDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private BscDAO bscDao; 

	@RequestMapping("/report/radio/cell-capacity/wk/list")
	public ModelAndView wkList(@RequestParam(required = false) String region, @RequestParam(required = false) String bscid,
			@RequestParam(required = false) String province, @RequestParam(required = false) String cellid, @RequestParam(required = false) Integer startWeek,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek,
			@RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
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
			startWeek = endWeek;
			startYear = endYear;
		}
		model.addAttribute("startWeek", startWeek);
		model.addAttribute("endWeek", endWeek);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";
		
		model.addAttribute("region", region);

//		int startRecord = 0;
//		int order = 1;
//		String column = "YEAR, WEEK, REGION, PROVINCE, BSCID, CELLID";
//		try {
//			startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("vRpWkCellGprsCs").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
//			order = Integer.parseInt(request.getParameter((new ParamEncoder("vRpWkCellGprsCs").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
//			column = request.getParameter((new ParamEncoder("vRpWkCellGprsCs").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
//		} catch (NumberFormatException e) {
//		}
//		int endRecord = startRecord + 100;

		List<VRpWkCellCapacity> vRpWkCellCapacity = vRpWkCellCapacityDao.filter(startWeek, startYear, endWeek, endYear, cellid, province, bscid, region);

//		Integer totalSize = vRpWkCellGprsCsDao.countRow(startWeek, startYear, endWeek, endYear, cellid, bscid, province, region);
//		model.addAttribute("totalSize", totalSize);

		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);

		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		model.addAttribute("province", province);

		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);

		model.addAttribute("vRpWkCellCapacity", vRpWkCellCapacity);

		return new ModelAndView("wkCellCapacityList");
	}
}
