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

import vn.com.vhc.vmsc2.statistics.dao.Bsc3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyBscDropDetails3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrBscDropDetails3GDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc3G;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBscDropDetails3G;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrBscDropDetails3G;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;

@Controller
@RequestMapping("/report/radio3g/rnc/drop/*")
public class BscDropDetails3gController extends BaseController{
	@Autowired
	private VRpDyBscDropDetails3GDAO vRpDyBscDropDetails3GDao;
	@Autowired
	private VRpHrBscDropDetails3GDAO vRpHrBscDropDetails3GDao;
	@Autowired
	private Bsc3GDAO bsc3GDao;
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
		
		List<VRpDyBscDropDetails3G> dyBscDropDetails3G = vRpDyBscDropDetails3GDao.filter(df.format(startDate), df.format(endDate), bscid, region);
		
		model.addAttribute("bscid", bscid);
		List<Bsc3G> bscList = bsc3GDao.getAll();
		model.addAttribute("bscList", bscList);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("dyBscDropDetails3G", dyBscDropDetails3G);
		model.addAttribute("region", region);
		
		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> speechDrprList = new ArrayList<Float>();
		for (VRpDyBscDropDetails3G vRpDyBscDropDetails3G : dyBscDropDetails3G) {
			categories.add(df.format(vRpDyBscDropDetails3G.getDay()));
			speechDrprList.add(vRpDyBscDropDetails3G.getSpeechDrpr());
		}

		Map<String, List<Float>> speechDrprSeries = new LinkedHashMap<String, List<Float>>();
		speechDrprSeries.put("Speech DCR --80699b", speechDrprList);
		model.addAttribute("speechDrprChart", Chart.multiColumn("speechDrprChart", "Speech DCR", categories, speechDrprSeries));
		
		return new ModelAndView("dyBscDropDetails3G");
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
		
		List<VRpHrBscDropDetails3G> hrBscDropDetails3G = new ArrayList<VRpHrBscDropDetails3G>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			hrBscDropDetails3G.addAll(vRpHrBscDropDetails3GDao.filter(dates.get(day).get("startHour"), dates.get(day).get("endHour"), day, bscid, region));
		}
		model.addAttribute("hrBscDropDetails3G", hrBscDropDetails3G);
		model.addAttribute("bscid", bscid);
		List<Bsc3G> bscList = bsc3GDao.getAll();
		model.addAttribute("bscList", bscList);
		model.addAttribute("region", region);
		
		List<String> categories = new ArrayList<String>();
		List<Float> speechDrprList = new ArrayList<Float>();
		for (VRpHrBscDropDetails3G vRpHrBscDropDetails3G : hrBscDropDetails3G) {
			categories.add(Integer.toString(vRpHrBscDropDetails3G.getHour())+":00"+df.format(vRpHrBscDropDetails3G.getDay()));
			speechDrprList.add(vRpHrBscDropDetails3G.getSpeechDrpr());
		}

		Map<String, List<Float>> speechDrprSeries = new LinkedHashMap<String, List<Float>>();
		speechDrprSeries.put("Speech DCR --80699b", speechDrprList);
		model.addAttribute("speechDrprChart", Chart.multiColumn("speechDrprChart", "Speech DCR", categories, speechDrprSeries));
		
		return new ModelAndView("hrBscDropDetails3G");
	}
}
