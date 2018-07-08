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
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyBscDcrsQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrBscDcrsQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBscDcrsQos;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrBscDcrsQos;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;

@Controller
@RequestMapping("/report/radio/bsc/sdrop/*")
public class BscSdcchDropController extends BaseController{
	@Autowired
	private VRpDyBscDcrsQosDAO dyBscDcrsQosDao;
	@Autowired
	private VRpHrBscDcrsQosDAO hrBscDcrsQosDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("dy")
	public ModelAndView dyList(@RequestParam(required = false) String region,@RequestParam(required = false) String bscid, @RequestParam(required = false) Date startDate,
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
		
		List<VRpDyBscDcrsQos> dyBscDcrsQos = dyBscDcrsQosDao.filter(df.format(startDate), df.format(endDate), bscid, region);
		
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("dyBscDcrsQos", dyBscDcrsQos);
		model.addAttribute("region", region);
		
		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> f4List = new ArrayList<Float>();
		List<Float> f5List = new ArrayList<Float>();
		List<Float> f6List = new ArrayList<Float>();
		List<Float> f7List = new ArrayList<Float>();
		for (VRpDyBscDcrsQos vRpDyBscDcrsQos : dyBscDcrsQos) {
			categories.add(df.format(vRpDyBscDcrsQos.getDay()));
			f4List.add(vRpDyBscDcrsQos.getF4());
			f5List.add(vRpDyBscDcrsQos.getF5());
			f6List.add(vRpDyBscDcrsQos.getF6());
			f7List.add(vRpDyBscDcrsQos.getF7());
		}

		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("Low SS", f4List);
		series.put("Bad Quality", f5List);
		series.put("Excessive TA", f6List);
		series.put("Other", f7List);
		
		model.addAttribute("chart", Chart.stackedColumn("chart", "Drop Reason (%)", categories, series));
		
		return new ModelAndView("dyBscSdcchDrp");
	}
	
	@RequestMapping("hr")
	public ModelAndView hrList(@RequestParam(required = false) String region,@RequestParam(required = true) String bscid, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
			ModelMap model) {
		long currentTime = System.currentTimeMillis();

		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		if (endHour == null) {
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

		String[] hourList = {"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
		model.addAttribute("hourList", hourList); 
		
		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";
		
		List<VRpHrBscDcrsQos> hrBscDcrsQosList = new ArrayList<VRpHrBscDcrsQos>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			hrBscDcrsQosList.addAll(hrBscDcrsQosDao.filter(dates.get(day).get("startHour"), dates.get(day).get("endHour"), day, bscid, region));
		}
		model.addAttribute("hrBscDcrsQosList", hrBscDcrsQosList);
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		model.addAttribute("region", region);
		
		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> f4List = new ArrayList<Float>();
		List<Float> f5List = new ArrayList<Float>();
		List<Float> f6List = new ArrayList<Float>();
		List<Float> f7List = new ArrayList<Float>();
		for (VRpHrBscDcrsQos vRpHrBscDcrsQos : hrBscDcrsQosList) {
			categories.add(Integer.toString(vRpHrBscDcrsQos.getHour())+":00");
			f4List.add(vRpHrBscDcrsQos.getF4());
			f5List.add(vRpHrBscDcrsQos.getF5());
			f6List.add(vRpHrBscDcrsQos.getF6());
			f7List.add(vRpHrBscDcrsQos.getF7());
		}

		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("Low SS", f4List);
		series.put("Bad Quality", f5List);
		series.put("Excessive TA", f6List);
		series.put("Other", f7List);
		
		model.addAttribute("chart", Chart.stackedColumn("chart", "Drop Reason (%)", categories, series));
		
		return new ModelAndView("hrBscSdcchDrp");
	}
}
