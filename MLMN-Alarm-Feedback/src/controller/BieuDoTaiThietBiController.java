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

import dao.DyTaiThietBiDAO;
import dao.MnTaiThietBiDAO;
import dao.SysParametersDAO;
import dao.WkTaiThietBiDAO;
import vo.DyTaiThietBi;
import vo.MnTaiThietBi;
import vo.SysParameters;
import vo.VDyIpbbLink;
import vo.WkTaiThietBi;
import vo.alarm.utils.Chart;

@Controller
@RequestMapping("/chart/rp/tai-thiet-bi/*")
public class BieuDoTaiThietBiController {
	@Autowired
	private SysParametersDAO sysParametersDAO;
	@Autowired
	private DyTaiThietBiDAO dyTaiThietBiDao;
	@Autowired
	private WkTaiThietBiDAO wkTaiThietBiDao;
	@Autowired
	private MnTaiThietBiDAO mnTaiThietBiDao;

	@RequestMapping("dy")
	public String dyList(@RequestParam(required = false) String region, @RequestParam(required = false) String min,
			@RequestParam(required = false) String max, @RequestParam(required = false) String date, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) {
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		List<SysParameters> regionList = sysParametersDAO.filter("REGION");
		List<DyTaiThietBi> dataListByCpu = new ArrayList<DyTaiThietBi>();
		List<DyTaiThietBi> dataListByMemory = new ArrayList<DyTaiThietBi>();
		String cpuChart = "";
		String memoryChart = "";
		Map<String, Number> cpuSeries = new HashMap<String, Number>();
		Map<String, Number> memorySeries = new HashMap<String, Number>();
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
			dataListByCpu = dyTaiThietBiDao.filterByDay("CPU_MAX", date, region, min, max);
			
			if (dataListByCpu != null && dataListByCpu.size() > 0) {
				for(DyTaiThietBi v : dataListByCpu) {
					cpuSeries.put(v.getNe(),  v.getCpuMax());
				}
				cpuChart = Chart.drawChart("spline", "cpuChart", "BIỂU ĐỒ CPU THEO NGÀY", "CPU_MAX", cpuSeries, dataListByCpu.size());
			}
			dataListByMemory = dyTaiThietBiDao.filterByDay("MEMORY", date, region, min, max);
			
			if (dataListByMemory != null && dataListByMemory.size() > 0) {
				for(DyTaiThietBi v : dataListByMemory) {
					memorySeries.put(v.getNe(), v.getMemory());
				}
				memoryChart = Chart.drawChart("spline","memoryChart", "BIỂU ĐỒ MEMORY  THEO NGÀY ", "MEMORY", memorySeries, dataListByMemory.size());
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
		model.addAttribute("cpuChart", cpuChart);
		model.addAttribute("memoryChart", memoryChart);
		return "jsp/dyTaiThietBi";
	}
	
	@RequestMapping("wk")
	public String wkList(@RequestParam(required = false) String region, @RequestParam(required = false) String min,
			@RequestParam(required = false) String max, @RequestParam(required = false) Integer week, 
			@RequestParam(required = false) String year, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) {
		
		List<SysParameters> regionList = sysParametersDAO.filter("REGION");
		List<WkTaiThietBi> dataListByCpu = new ArrayList<WkTaiThietBi>();
		List<WkTaiThietBi> dataListByMemory = new ArrayList<WkTaiThietBi>();
		String cpuChart = "";
		String memoryChart = "";
		Map<String, Number> cpuSeries = new HashMap<String, Number>();
		Map<String, Number> memorySeries = new HashMap<String, Number>();
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
					dataListByCpu = wkTaiThietBiDao.filterByWeek("CPU_MAX", week, year, region, min, max);
					dataListByMemory = wkTaiThietBiDao.filterByWeek("MEMORY", week, year, region, min, max);
				}		
			}	
			
			if (dataListByCpu != null && dataListByCpu.size() > 0) {
				for(WkTaiThietBi v : dataListByCpu) {
					cpuSeries.put(v.getNe(), (Number) v.getCpuMax());
				}
				cpuChart = Chart.drawChart("spline", "cpuChart", "BIỂU ĐỒ CPU_MAX  THEO TUẦN", "CPU_MAX", cpuSeries, dataListByCpu.size());
			}
			
			if (dataListByMemory != null && dataListByMemory.size() > 0) {
				for(WkTaiThietBi v : dataListByMemory) {
					memorySeries.put(v.getNe(), (Number) v.getMemory());
				}
				memoryChart = Chart.drawChart("spline","memoryChart", "BIỂU ĐỒ MEMORY  THEO TUẦN", "MEMORY", memorySeries, dataListByMemory.size());
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
		model.addAttribute("cpuChart", cpuChart);
		model.addAttribute("memoryChart", memoryChart);
		return "jsp/wkTaiThietBi";
	}
	
	@RequestMapping("mn")
	public String mnList(@RequestParam(required = false) String region, @RequestParam(required = false) String min,
			@RequestParam(required = false) String max, @RequestParam(required = false) Integer month, 
			@RequestParam(required = false) String year, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) {
		
		List<SysParameters> regionList = sysParametersDAO.filter("REGION");
		List<MnTaiThietBi> dataListByCpu = new ArrayList<MnTaiThietBi>();
		List<MnTaiThietBi> dataListByMemory = new ArrayList<MnTaiThietBi>();
		String cpuChart = "";
		String memoryChart = "";
		Map<String, Number> cpuSeries = new HashMap<String, Number>();
		Map<String, Number> memorySeries = new HashMap<String, Number>();
		int[] monthList = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
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
					dataListByCpu = mnTaiThietBiDao.filterByMonth("CPU_MAX", month, year, region, min, max);
					dataListByMemory = mnTaiThietBiDao.filterByMonth("MEMORY", month, year, region, min, max);						
				}
					
			}	
			
			if (dataListByCpu != null && dataListByCpu.size() > 0) {
				for(MnTaiThietBi v : dataListByCpu) {
					cpuSeries.put(v.getNe(), (Number) v.getCpuMax());
				}
				cpuChart = Chart.drawChart("spline", "cpuChart", "BIỂU ĐỒ CPU_MAX  THEO THÁNG", "CPU_MAX", cpuSeries, dataListByCpu.size());
			}
			
			if (dataListByMemory != null && dataListByMemory.size() > 0) {
				for(MnTaiThietBi v : dataListByMemory) {
					memorySeries.put(v.getNe(),(Number) v.getMemory());
				}
				memoryChart = Chart.drawChart("spline","memoryChart", "BIỂU ĐỒ MEMORY THEO THÁNG ", "MEMORY", memorySeries, dataListByMemory.size());
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
		model.addAttribute("cpuChart", cpuChart);
		model.addAttribute("memoryChart", memoryChart);
		return "jsp/mnTaiThietBi";
	}
	
	@RequestMapping("luy-ke")
	public String optionList(@RequestParam(required = false) String region, @RequestParam(required = false) String min,
			@RequestParam(required = false) String max, 
			@RequestParam(required = false) String year, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) {
		
		List<SysParameters> regionList = sysParametersDAO.filter("REGION");
		Map<String, List<Number>> cpuSeries = new LinkedHashMap<String, List<Number>>();
		Map<String, List<Number>> memorySeries = new LinkedHashMap<String, List<Number>>();
		String cpuChart = "";
		String memoryChart = "";
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		df.setLenient(false);
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		String endDate = df.format(date);
		cal.add(Calendar.DATE, -30);
		String startDate = df.format(cal.getTime());
		List<DyTaiThietBi> dayList = dyTaiThietBiDao.getDay(startDate, endDate);
		List<String> categories = new ArrayList<String>();
		for (DyTaiThietBi v : dayList) {
			String strDay = df.format(v.getDay());
			strDay = strDay.substring(0, strDay.lastIndexOf('/'));
			categories.add(strDay);
		}
		
		try {
			if (min != "" && min != "") {
				Integer.parseInt(min);
			}
			if (max != "" && max != "") {
				Integer.parseInt(max);
			}
			List<DyTaiThietBi> neList = dyTaiThietBiDao.getNe("cpu_max", region, min, max);
			for (DyTaiThietBi ne : neList) {
				List<Number> cpuValueList = new ArrayList<Number>();
				for (DyTaiThietBi day : dayList) {
					List<DyTaiThietBi>  record = dyTaiThietBiDao.get30NgayGanNhat("cpu_max", min, max, ne.getNe(), df.format(day.getDay()));
					if (record != null && record.size() > 0) {
						cpuValueList.add(record.get(0).getCpuMax());
					} else {
						cpuValueList.add(null);
					}
				}
				cpuSeries.put(ne.getNe(), cpuValueList);
			}	
			
			List<DyTaiThietBi> neList2 = dyTaiThietBiDao.getNe("memory", region, min, max);
			for (DyTaiThietBi ne : neList2) {
				List<Number> memoryValueList = new ArrayList<Number>();
				for (DyTaiThietBi day: dayList) {
					List<DyTaiThietBi>  record = dyTaiThietBiDao.get30NgayGanNhat("memory", min, max, ne.getNe(), df.format(day.getDay()));
					if (record != null && record.size() > 0) {
						memoryValueList.add(record.get(0).getMemory());
					} else {
						memoryValueList.add(null);
					}
				}
				memorySeries.put(ne.getNe(), memoryValueList);
			}		
			if (neList != null && neList.size() > 0) {
				cpuChart = Chart.newBasicLine("cpuChart", "BIỂU ĐỒ CPU_MAX 30 NGÀY GẦN NHẤT", "CPU_MAX", categories, cpuSeries);
			}
			if (neList2 != null && neList2.size() > 0) {
				memoryChart = Chart.newBasicLine("memoryChart", "BIỂU ĐỒ MEMORY 30 NGÀY GẦN NHẤT", "MEMORY", categories, memorySeries);
			}
			
		} catch(NumberFormatException e) {
			
		}
		
		
	//	
	/*	for (DyTaiThietBi ne : neList) {
			List
		}*/
		model.addAttribute("region", region);
		model.addAttribute("max", max);
		model.addAttribute("min", min);
		model.addAttribute("regionList", regionList);
		model.addAttribute("cpuChart", cpuChart);
		model.addAttribute("memoryChart", memoryChart);
		return "jsp/optionTaiThietBi";
	}
}
