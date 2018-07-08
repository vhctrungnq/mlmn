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
import vn.com.vhc.vmsc2.statistics.dao.VRpDyBscCssrDetails3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrBscCssrDetails3GDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc3G;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBscCssrDetails3G;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrBscCssrDetails3G;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;

@Controller
@RequestMapping("/report/radio3g/rnc/cssr/*")
public class BscCssrDetails3gController extends BaseController{
	@Autowired
	private VRpDyBscCssrDetails3GDAO vRpDyBscCssrDetails3GDao;
	@Autowired
	private VRpHrBscCssrDetails3GDAO vRpHrBscCssrDetails3GDao;
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
		
		List<VRpDyBscCssrDetails3G> dyBscCssrDetails3G = vRpDyBscCssrDetails3GDao.filter(df.format(startDate), df.format(endDate), bscid, region);
		
		model.addAttribute("bscid", bscid);
		List<Bsc3G> bscList = bsc3GDao.getAll();
		model.addAttribute("bscList", bscList);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("dyBscCssrDetails3G", dyBscCssrDetails3G);
		model.addAttribute("region", region);
		
		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> csSpeechCssrList = new ArrayList<Float>();
		List<Float> psR99CssrList = new ArrayList<Float>();
		for (VRpDyBscCssrDetails3G vRpDyBscCssrDetails3G : dyBscCssrDetails3G) {
			categories.add(df.format(vRpDyBscCssrDetails3G.getDay()));
			csSpeechCssrList.add(vRpDyBscCssrDetails3G.getCsSpeechCssr());
			psR99CssrList.add(vRpDyBscCssrDetails3G.getPsR99Cssr());
		}

		Map<String, List<Float>> csSpeechCssrSeries = new LinkedHashMap<String, List<Float>>();
		csSpeechCssrSeries.put("CS Speech CSSR --80699b", csSpeechCssrList);
		model.addAttribute("csSpeechCssrChart", Chart.multiColumn("csSpeechCssrChart", "CS Speech CSSR", categories, csSpeechCssrSeries));
		
		Map<String, List<Float>> psR99CssrSeries = new LinkedHashMap<String, List<Float>>();
		psR99CssrSeries.put("R99 PS CSSR --80699b", psR99CssrList);
		model.addAttribute("psR99CssrChart", Chart.multiColumn("psR99CssrChart", "R99 PS CSSR", categories, psR99CssrSeries));
		
		return new ModelAndView("dyBscCssrDetails3G");
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
		
		List<VRpHrBscCssrDetails3G> hrBscCssrDetails3G = new ArrayList<VRpHrBscCssrDetails3G>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			hrBscCssrDetails3G.addAll(vRpHrBscCssrDetails3GDao.filter(dates.get(day).get("startHour"), dates.get(day).get("endHour"), day, bscid, region));
		}
		model.addAttribute("hrBscCssrDetails3G", hrBscCssrDetails3G);
		model.addAttribute("bscid", bscid);
		List<Bsc3G> bscList = bsc3GDao.getAll();
		model.addAttribute("bscList", bscList);
		model.addAttribute("region", region);
		
		
		List<String> categories = new ArrayList<String>();
		List<Float> csSpeechCssrList = new ArrayList<Float>();
		List<Float> psR99CssrList = new ArrayList<Float>();
		for (VRpHrBscCssrDetails3G vRpHrBscCssrDetails3G : hrBscCssrDetails3G) {
			categories.add(Integer.toString(vRpHrBscCssrDetails3G.getHour())+":00"+df.format(vRpHrBscCssrDetails3G.getDay()));
			csSpeechCssrList.add(vRpHrBscCssrDetails3G.getCsSpeechCssr());
			psR99CssrList.add(vRpHrBscCssrDetails3G.getPsR99Cssr());
		}

		Map<String, List<Float>> csSpeechCssrSeries = new LinkedHashMap<String, List<Float>>();
		csSpeechCssrSeries.put("CS Speech CSSR --80699b", csSpeechCssrList);
		model.addAttribute("csSpeechCssrChart", Chart.multiColumn("csSpeechCssrChart", "CS Speech CSSR", categories, csSpeechCssrSeries));
		
		Map<String, List<Float>> psR99CssrSeries = new LinkedHashMap<String, List<Float>>();
		psR99CssrSeries.put("R99 PS CSSR --80699b", psR99CssrList);
		model.addAttribute("psR99CssrChart", Chart.multiColumn("psR99CssrChart", "R99 PS CSSR", categories, psR99CssrSeries));
		
		return new ModelAndView("hrBscCssrDetails3G");
	}
}
