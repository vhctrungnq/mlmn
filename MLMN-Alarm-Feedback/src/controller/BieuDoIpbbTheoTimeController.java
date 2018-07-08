package controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dao.SysParametersDAO;
import dao.VDyIpbbLinkDAO;
import vo.SysParameters;
import vo.VDyIpbbLink;
import vo.alarm.utils.Chart;

@Controller
@RequestMapping("/chart/rp/ipbb/*")
public class BieuDoIpbbTheoTimeController {
	@Autowired
	private SysParametersDAO sysParametersDAO;
	@Autowired
	private VDyIpbbLinkDAO vDyIpbbLinkDao;

	@RequestMapping("dy")
	public String dyList(@RequestParam(required = false) String region, @RequestParam(required = false) String min,
			@RequestParam(required = false) String max, @RequestParam(required = false) String date, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) {

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		List<SysParameters> regionList = sysParametersDAO.filter("REGION");
		List<VDyIpbbLink> dataListIn = new ArrayList<VDyIpbbLink>();
		List<VDyIpbbLink> dataListOut = new ArrayList<VDyIpbbLink>();
		String chartIn = "";
		String chartOut = "";
		Map<String, Number> seriIn = new HashMap<String, Number>();
		Map<String, Number> seriOut = new HashMap<String, Number>();
		if (region != null && region.equals("MĐ")) {
			region = "MD";
		}
		try {
			if (min != null && min != "") {
				Integer.parseInt(min);
			}
			if (max != null && max != "") {
				Integer.parseInt(max);
			}
			if (date != null && date != "") {
				df.parse(date);
			}
			dataListIn = vDyIpbbLinkDao.filterByDay("in_utilization", date, region, min, max);

			if (dataListIn != null && dataListIn.size() > 0) {
				for (VDyIpbbLink v : dataListIn) {
					seriIn.put(v.getDirection(), (Number) v.getInUtilization());
				}
				chartIn = Chart.drawChart("spline", "chartIn", "BIỂU ĐỒ IN_UTILIZATION IPBB THEO NGÀY",
						"IN_UTILIZATION", seriIn, dataListIn.size());
			}
			dataListOut = vDyIpbbLinkDao.filterByDay("out_utilization", date, region, min, max);

			if (dataListOut != null && dataListOut.size() > 0) {
				for (VDyIpbbLink v : dataListOut) {
					seriOut.put(v.getDirection(), v.getOutUtilization());
				}
				chartOut = Chart.drawChart("spline", "chartOut", "BIỂU ĐỒ OUT_UTILIZATION IPBB THEO NGÀY ",
						"OUT_UTILIZATION", seriOut, dataListOut.size());
			}
		} catch (NumberFormatException e) {

		} catch (ParseException e) {

		}
		if (region != null && region.equals("MD")) {
			region = "MĐ";
		}
		model.addAttribute("date", date);
		model.addAttribute("region", region);
		model.addAttribute("max", max);
		model.addAttribute("min", min);
		model.addAttribute("regionList", regionList);
		model.addAttribute("chartIn", chartIn);
		model.addAttribute("chartOut", chartOut);
		return "jsp/dyIpbb";
	}

	@RequestMapping("wk")
	public String wkList(@RequestParam(required = false) String region, @RequestParam(required = false) String min,
			@RequestParam(required = false) String max, @RequestParam(required = false) Integer week,
			@RequestParam(required = false) String year, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {

		List<SysParameters> regionList = sysParametersDAO.filter("REGION");
		List<VDyIpbbLink> dataListIn = new ArrayList<VDyIpbbLink>();
		List<VDyIpbbLink> dataListOut = new ArrayList<VDyIpbbLink>();
		String chartIn = "";
		String chartOut = "";
		Map<String, Number> seriIn = new HashMap<String, Number>();
		Map<String, Number> seriOut = new HashMap<String, Number>();
		if (region != null && region.equals("MĐ")) {
			region = "MD";
		}
		try {
			if (min != null && min != "") {
				Integer.parseInt(min);
			}
			if (max != null && max != "") {
				Integer.parseInt(max);
			}
			if (year != null && year != "") {
				Integer.parseInt(year);
				if (week != null) {
					if (week == 0) {
						week = 53;
					}
					dataListIn = vDyIpbbLinkDao.filterByWeek("in_utilization", week, year, region, min, max);
					dataListOut = vDyIpbbLinkDao.filterByWeek("out_utilization", week, year, region, min, max);
				}
			}

			if (dataListIn != null && dataListIn.size() > 0) {
				for (VDyIpbbLink v : dataListIn) {
					seriIn.put(v.getDirection(), v.getInUtilization());
				}
				chartIn = Chart.drawChart("spline", "chartIn", "BIỂU ĐỒ IN_UTILIZATION IPBB THEO TUẦN",
						"IN_UTILIZATION", seriIn, dataListIn.size());
			}

			if (dataListOut != null && dataListOut.size() > 0) {
				for (VDyIpbbLink v : dataListOut) {
					seriOut.put(v.getDirection(), v.getOutUtilization());
				}
				chartOut = Chart.drawChart("spline", "chartOut", "BIỂU ĐỒ OUT_UTILIZATION IPBB THEO TUẦN ",
						"OUT_UTILIZATION", seriOut, dataListOut.size());
			}
		} catch (NumberFormatException e) {

		}
		if (region != null && region.equals("MD")) {
			region = "MĐ";
		}
		model.addAttribute("week", week);
		model.addAttribute("year", year);
		model.addAttribute("region", region);
		model.addAttribute("max", max);
		model.addAttribute("min", min);
		model.addAttribute("regionList", regionList);
		model.addAttribute("chartIn", chartIn);
		model.addAttribute("chartOut", chartOut);
		return "jsp/wkIpbb";
	}

	@RequestMapping("mn")
	public String mnList(@RequestParam(required = false) String region, @RequestParam(required = false) String min,
			@RequestParam(required = false) String max, @RequestParam(required = false) Integer month,
			@RequestParam(required = false) String year, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {

		List<SysParameters> regionList = sysParametersDAO.filter("REGION");
		List<VDyIpbbLink> dataListIn = new ArrayList<VDyIpbbLink>();
		List<VDyIpbbLink> dataListOut = new ArrayList<VDyIpbbLink>();
		String chartIn = "";
		String chartOut = "";
		Map<String, Number> seriIn = new HashMap<String, Number>();
		Map<String, Number> seriOut = new HashMap<String, Number>();
		int[] monthList = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
		if (region != null && region.equals("MĐ")) {
			region = "MD";
		}
		try {
			if (min != null && min != "") {
				Integer.parseInt(min);
			}
			if (max != null && max != "") {
				Integer.parseInt(max);
			}
			if (year != null && year != "") {
				Integer.parseInt(year);
				if (month != null) {
					dataListIn = vDyIpbbLinkDao.filterByMonth("in_utilization", month, year, region, min, max);
					dataListOut = vDyIpbbLinkDao.filterByMonth("out_utilization", month, year, region, min, max);
				}

			}

			if (dataListIn != null && dataListIn.size() > 0) {
				for (VDyIpbbLink v : dataListIn) {
					seriIn.put(v.getDirection(), (Number) v.getInUtilization());
				}
				chartIn = Chart.drawChart("spline", "chartIn", "BIỂU ĐỒ IN_UTILIZATION IPBB THEO THÁNG",
						"IN_UTILIZATION", seriIn, dataListIn.size());
			}

			if (dataListOut != null && dataListOut.size() > 0) {
				for (VDyIpbbLink v : dataListOut) {
					seriOut.put(v.getDirection(), v.getOutUtilization());
				}
				chartOut = Chart.drawChart("spline", "chartOut", "BIỂU ĐỒ OUT_UTILIZATION IPBB THEO THÁNG ",
						"OUT_UTILIZATION", seriOut, dataListOut.size());
			}
		} catch (NumberFormatException e) {

		}
		if (region != null && region.equals("MD")) {
			region = "MĐ";
		}
		model.addAttribute("month", month);
		model.addAttribute("monthList", monthList);
		model.addAttribute("year", year);
		model.addAttribute("region", region);
		model.addAttribute("max", max);
		model.addAttribute("min", min);
		model.addAttribute("regionList", regionList);
		model.addAttribute("chartIn", chartIn);
		model.addAttribute("chartOut", chartOut);
		return "jsp/mnIpbb";
	}

	@RequestMapping("luy-ke")
	public String OptionList(@RequestParam(required = false) String region,
			@RequestParam(required = false) String column, @RequestParam(required = false) String site,
			@RequestParam(required = false) String min, @RequestParam(required = false) String max, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) {

//		region = "HCM";
		String chartIn = "";
		String chartOut = "";
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		df.setLenient(false);
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		String endDate = df.format(date);
		cal.add(Calendar.DATE, -30);
		String startDate = df.format(cal.getTime());
		List<VDyIpbbLink> directionListIn = new ArrayList<VDyIpbbLink>();
		List<VDyIpbbLink> directionListOut = new ArrayList<VDyIpbbLink>();
		List<VDyIpbbLink> dayList = vDyIpbbLinkDao.getDay(startDate, endDate, region, site);
		List<String> categories = new ArrayList<String>();
		for (VDyIpbbLink v : dayList) {
			String strDay = df.format(v.getDay());
			strDay = strDay.substring(0, strDay.lastIndexOf('/'));
			categories.add(strDay);
		}

		List<VDyIpbbLink> record = new ArrayList<VDyIpbbLink>();

		Map<String, List<Number>> inSeries = new LinkedHashMap<String, List<Number>>();
		Map<String, List<Number>> outSeries = new LinkedHashMap<String, List<Number>>();

		List<SysParameters> regionList = sysParametersDAO.filter("REGION");
		if (region != null && region.equals("MĐ")) {
			region = "MD";
		}
		try {
			if (min != "" && min != null) {
				Integer.parseInt(min);
			}
			if (max != "" && min != null) {
				Integer.parseInt(max);
			}
			directionListIn = vDyIpbbLinkDao.getDirection("in_utilization", startDate, endDate, region, site, min, max);
			directionListOut = vDyIpbbLinkDao.getDirection("out_utilization", startDate, endDate, region, site, min, max);
			for (VDyIpbbLink direction : directionListIn) {
				List<Number> inUtilizationValue = new ArrayList<Number>();
				for (VDyIpbbLink day : dayList) {
					record = vDyIpbbLinkDao.get30NgayGanNhat(df.format(day.getDay()), direction.getDirection(),"in_utilization", min, max);
					if (record == null || record.size() == 0) {
						inUtilizationValue.add(null);
					} else {
						inUtilizationValue.add(record.get(0).getInUtilization());
					}
					inSeries.put(direction.getDirection(), inUtilizationValue);			}	
			}
			for (VDyIpbbLink direction : directionListOut) {
				List<Number> outUtilizationValue = new ArrayList<Number>();
				for (VDyIpbbLink day : dayList) {
					record = vDyIpbbLinkDao.get30NgayGanNhat(df.format(day.getDay()), direction.getDirection(), "out_utilization", min, max);							
					if (record == null || record.size() == 0) {
						outUtilizationValue.add(null);
					} else {
						outUtilizationValue.add(record.get(0).getOutUtilization());
					}
				}
				outSeries.put(direction.getDirection(), outUtilizationValue);
			}
			if (directionListIn != null && directionListIn.size() > 0) {
				chartIn = Chart.newBasicLine("chartIn", "BIỂU ĐỒ IN_UTILIZATION  IPBB 30 NGÀY GÂN NHẤT", "IN_UTILIZATION", categories, inSeries);
						
			}
			if (directionListOut != null && directionListOut.size() > 0) {
				chartOut = Chart.newBasicLine("chartOut", "BIỂU ĐỒ OUT_UTILIZATION IPBB 30 NGÀY GÂN NHẤT", "OUT_UTILIZATION", categories, outSeries);
						
			}

		} catch (NumberFormatException e) {

		}
		
		List<VDyIpbbLink> directionList = new ArrayList<VDyIpbbLink>();
		if (region != null && region != "") {
			directionList = vDyIpbbLinkDao.getDirectionByRegion(region);
		}
		
		// Collections.sort(directionList);
		List<String> siteList = new ArrayList<String>();
		for (VDyIpbbLink direct : directionList) {
			int start = 0;
			int end = 0;
			String str = direct.getDirection();
			while (str.charAt(start) != '-' && str.charAt(start) != '_') {
				start++;
			}
			end = start + 1;
			while (str.charAt(end) != '-' && str.charAt(end) != '_') {
				end++;
			}
			if (!siteList.contains(str.substring(start + 1, end))) {
				siteList.add(str.substring(start + 1, end));
			}
		}
		if (region != null && region.equals("MD")) {
			region = "MĐ";
		}
		// String[] siteList = {"C30"};
		model.addAttribute("region", region);
		model.addAttribute("site", site);
		model.addAttribute("siteList", siteList);
		model.addAttribute("max", max);
		model.addAttribute("min", min);
		model.addAttribute("regionList", regionList);
		model.addAttribute("chartIn", chartIn);
		model.addAttribute("chartOut", chartOut);
		return "jsp/optionIpbb";
	}
}
