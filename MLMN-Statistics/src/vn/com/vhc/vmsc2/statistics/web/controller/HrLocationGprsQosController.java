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
import vn.com.vhc.vmsc2.statistics.dao.VRpHrLocationGprsQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrLocationGprsQos;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
@RequestMapping("/report/radio/location-gprs-qos/hr/*")
public class HrLocationGprsQosController extends BaseController {
	@Autowired
	private VRpHrLocationGprsQosDAO vRpHrLocationGprsQosDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("details")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required = true) String location, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
			ModelMap model) {
		long currentTime = System.currentTimeMillis();

		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		/*if (endHour == null) {
			endHour = "23";
		}
		if (startHour == null) {
			startHour = "0";
		}
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("startHour", startHour);
		model.addAttribute("endHour", endHour);

		if (region == null)
			region = getCenter("TT2");*/
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
		String[] hourList = {"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
		model.addAttribute("hourList", hourList);
		
		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";
		
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("startHour", startHour);
		model.addAttribute("endHour", endHour);

		List<VRpHrLocationGprsQos> vRpHrLocationGprsQosDetails = new ArrayList<VRpHrLocationGprsQos>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			vRpHrLocationGprsQosDetails.addAll(vRpHrLocationGprsQosDao.filter(dates.get(day).get("startHour"), dates.get(day).get("endHour"), df.format(day), location, region));
		}

		model.addAttribute("vRpHrLocationGprsQosDetails", vRpHrLocationGprsQosDetails);
		List<HProvincesCode> locationList = hProvincesCodeDao.getAllLocation();
		model.addAttribute("locationList", locationList);
		model.addAttribute("location", location);

		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> dlTbfSucrList = new ArrayList<Float>();
		List<Float> ulTbfSucrList = new ArrayList<Float>();

		for (VRpHrLocationGprsQos vRpHrLocationGprsQos : vRpHrLocationGprsQosDetails) {
			categories.add(Integer.toString(vRpHrLocationGprsQos.getHour()));
			dlTbfSucrList.add(vRpHrLocationGprsQos.getDlTbfSucr());
			ulTbfSucrList.add(vRpHrLocationGprsQos.getUlTbfSucr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("Dl_Tbf_Sucr", dlTbfSucrList);
		series.put("Ul_Tbf_Sucr", ulTbfSucrList);

		model.addAttribute("chart", Chart.basicLine("LOCATION GPRS QOS " + location + " Hourly Report", categories, series));

		// checkBox
		String[] checkColumns = { "dlTbfReq", "dlTbfSucr", "ulTbfReq", "ulTbfSucr", "gdlTraf", "gulTraf", "edlTraf", "eulTraf", "dataload" };
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

		String[] checkSeries = { "dlTbfSucr", "ulTbfSucr" };
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));

		return "hrLocationGprsQosDetails";
	}
}
