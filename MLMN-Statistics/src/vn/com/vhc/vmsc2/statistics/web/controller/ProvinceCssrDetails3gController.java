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
import vn.com.vhc.vmsc2.statistics.dao.VRpDyProvinceCssrDeta3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrProvinceCssrDeta3GDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyProvinceCssrDeta3G;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrProvinceCssrDeta3G;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;

@Controller
@RequestMapping("/report/radio3g/province/cssr/*")
public class ProvinceCssrDetails3gController extends BaseController{
	@Autowired
	private VRpDyProvinceCssrDeta3GDAO vRpDyProvinceCssrDeta3GDao;
	@Autowired
	private VRpHrProvinceCssrDeta3GDAO vRpHrProvinceCssrDeta3GDao;
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
		
		List<VRpDyProvinceCssrDeta3G> dyProvinceCssrDeta3G = vRpDyProvinceCssrDeta3GDao.filter(df.format(startDate), df.format(endDate), province, region);
		
		model.addAttribute("province", province);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("dyProvinceCssrDeta3G", dyProvinceCssrDeta3G);
		
		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> csSpeechCssrList = new ArrayList<Float>();
		List<Float> psR99CssrList = new ArrayList<Float>();
		for (VRpDyProvinceCssrDeta3G vRpDyProvinceCssrDeta3G : dyProvinceCssrDeta3G) {
			categories.add(df.format(vRpDyProvinceCssrDeta3G.getDay()));
			csSpeechCssrList.add(vRpDyProvinceCssrDeta3G.getCsSpeechCssr());
			psR99CssrList.add(vRpDyProvinceCssrDeta3G.getPsR99Cssr());
		}

		Map<String, List<Float>> csSpeechCssrSeries = new LinkedHashMap<String, List<Float>>();
		csSpeechCssrSeries.put("CS Speech CSSR --80699b", csSpeechCssrList);
		model.addAttribute("csSpeechCssrChart", Chart.multiColumn("csSpeechCssrChart", "CS Speech CSSR", categories, csSpeechCssrSeries));
		
		Map<String, List<Float>> psR99CssrSeries = new LinkedHashMap<String, List<Float>>();
		psR99CssrSeries.put("R99 PS CSSR --80699b", psR99CssrList);
		model.addAttribute("psR99CssrChart", Chart.multiColumn("psR99CssrChart", "R99 PS CSSR", categories, psR99CssrSeries));
		
		return new ModelAndView("dyProvinceCssrDetails3G");
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
		
		List<VRpHrProvinceCssrDeta3G> hrProvinceCssrDeta3G = new ArrayList<VRpHrProvinceCssrDeta3G>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			hrProvinceCssrDeta3G.addAll(vRpHrProvinceCssrDeta3GDao.filter(dates.get(day).get("startHour"), dates.get(day).get("endHour"), day, province, region));
		}
		model.addAttribute("hrProvinceCssrDeta3G", hrProvinceCssrDeta3G);
		model.addAttribute("province", province);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);

		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> csSpeechCssrList = new ArrayList<Float>();
		List<Float> psR99CssrList = new ArrayList<Float>();
		for (VRpHrProvinceCssrDeta3G vRpHrProvinceCssrDeta3G : hrProvinceCssrDeta3G) {
			categories.add(Integer.toString(vRpHrProvinceCssrDeta3G.getHour())+":00"+df.format(vRpHrProvinceCssrDeta3G.getDay()));
			csSpeechCssrList.add(vRpHrProvinceCssrDeta3G.getCsSpeechCssr());
			psR99CssrList.add(vRpHrProvinceCssrDeta3G.getPsR99Cssr());
		}

		Map<String, List<Float>> csSpeechCssrSeries = new LinkedHashMap<String, List<Float>>();
		csSpeechCssrSeries.put("CS Speech CSSR --80699b", csSpeechCssrList);
		model.addAttribute("csSpeechCssrChart", Chart.multiColumn("csSpeechCssrChart", "CS Speech CSSR", categories, csSpeechCssrSeries));
		
		Map<String, List<Float>> psR99CssrSeries = new LinkedHashMap<String, List<Float>>();
		psR99CssrSeries.put("R99 PS CSSR --80699b", psR99CssrList);
		model.addAttribute("psR99CssrChart", Chart.multiColumn("psR99CssrChart", "R99 PS CSSR", categories, psR99CssrSeries));
		
		return new ModelAndView("hrProvinceCssrDetails3G");
	}
}
