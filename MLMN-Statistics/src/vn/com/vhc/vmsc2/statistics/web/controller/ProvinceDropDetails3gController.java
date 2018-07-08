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

import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyProvinceDropDeta3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrProvinceDropDeta3GDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyProvinceDropDeta3G;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrProvinceDropDeta3G;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;

@Controller
@RequestMapping("/report/radio3g/province/drop/*")
public class ProvinceDropDetails3gController extends BaseController{
	@Autowired
	private VRpDyProvinceDropDeta3GDAO vRpDyProvinceDropDeta3GDao;
	@Autowired
	private VRpHrProvinceDropDeta3GDAO vRpHrProvinceDropDeta3GDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("dy")
	public ModelAndView dyList(@RequestParam(required = false) String region,@RequestParam(required = false) String province, @RequestParam(required = false) Date startDate,
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
		
		model.addAttribute("region", region);
		
		List<VRpDyProvinceDropDeta3G> dyProvinceDropDeta3G = vRpDyProvinceDropDeta3GDao.filter(df.format(startDate), df.format(endDate), province, region);
		
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("dyProvinceDropDeta3G", dyProvinceDropDeta3G);
		
		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> speechDrprList = new ArrayList<Float>();
		for (VRpDyProvinceDropDeta3G vRpDyProvinceDropDeta3G : dyProvinceDropDeta3G) {
			categories.add(df.format(vRpDyProvinceDropDeta3G.getDay()));
			speechDrprList.add(vRpDyProvinceDropDeta3G.getSpeechDrpr());
		}

		Map<String, List<Float>> csSpeechCssrSeries = new LinkedHashMap<String, List<Float>>();
		csSpeechCssrSeries.put("CS Speech CSSR --80699b", speechDrprList);
		model.addAttribute("csSpeechCssrChart", Chart.multiColumn("csSpeechCssrChart", "CS Speech CSSR", categories, csSpeechCssrSeries));
		
		return new ModelAndView("dyProvinceDropDetails3G");
	}
	
	@RequestMapping("hr")
	public ModelAndView hrList(@RequestParam(required = false) String region,@RequestParam(required = true) String province, @RequestParam(required = false) String startHour,
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
		
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("startHour", startHour + ":00");
		model.addAttribute("endHour", endHour + ":00");

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";
		
		model.addAttribute("region", region);
		
		List<VRpHrProvinceDropDeta3G> hrProvinceDropDeta3G = new ArrayList<VRpHrProvinceDropDeta3G>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			hrProvinceDropDeta3G.addAll(vRpHrProvinceDropDeta3GDao.filter(dates.get(day).get("startHour"), dates.get(day).get("endHour"), day, province, region));
		}
		model.addAttribute("hrProvinceDropDeta3G", hrProvinceDropDeta3G);
		model.addAttribute("province", province);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);

		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> speechDrprList = new ArrayList<Float>();
		for (VRpHrProvinceDropDeta3G vRpHrProvinceDropDeta3G : hrProvinceDropDeta3G) {
			categories.add(Integer.toString(vRpHrProvinceDropDeta3G.getHour())+":00"+df.format(vRpHrProvinceDropDeta3G.getDay()));
			speechDrprList.add(vRpHrProvinceDropDeta3G.getSpeechDrpr());
		}

		Map<String, List<Float>> csSpeechCssrSeries = new LinkedHashMap<String, List<Float>>();
		csSpeechCssrSeries.put("CS Speech CSSR --80699b", speechDrprList);
		model.addAttribute("csSpeechCssrChart", Chart.multiColumn("csSpeechCssrChart", "CS Speech CSSR", categories, csSpeechCssrSeries));
		
		return new ModelAndView("hrProvinceDropDetails3G");
	}
}
