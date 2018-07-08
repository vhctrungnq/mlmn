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
import vn.com.vhc.vmsc2.statistics.dao.VRpDyBscDcrtQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrBscDcrtQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBscDcrtQos;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrBscDcrtQos;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;

@Controller
@RequestMapping("/report/radio/bsc/tdrop/*")
public class BscTchDrpController extends BaseController{
	@Autowired
	private VRpDyBscDcrtQosDAO dyBscDcrtQosDao;
	@Autowired
	private VRpHrBscDcrtQosDAO hrBscDcrtQosDao;
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
		
		List<VRpDyBscDcrtQos> dyBscDcrtQos = dyBscDcrtQosDao.filter(df.format(startDate), df.format(endDate), bscid, region);
		
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("dyBscDcrtQos", dyBscDcrtQos);
		model.addAttribute("region", region);
		
		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> f4List = new ArrayList<Float>();
		List<Float> f5List = new ArrayList<Float>();
		List<Float> f6List = new ArrayList<Float>();
		List<Float> f7List = new ArrayList<Float>();
		List<Float> f8List = new ArrayList<Float>();
		List<Float> f9List = new ArrayList<Float>();
		List<Float> f10List = new ArrayList<Float>();
		List<Float> f11List = new ArrayList<Float>();
		List<Float> f12List = new ArrayList<Float>();
		List<Float> f13List = new ArrayList<Float>();
		List<Float> f14List = new ArrayList<Float>();
		List<Float> f15List = new ArrayList<Float>();
		for (VRpDyBscDcrtQos vRpDyBscDcrtQos : dyBscDcrtQos) {
			categories.add(df.format(vRpDyBscDcrtQos.getDay()));
			f4List.add(vRpDyBscDcrtQos.getF4());
			f5List.add(vRpDyBscDcrtQos.getF5());
			f6List.add(vRpDyBscDcrtQos.getF6());
			f7List.add(vRpDyBscDcrtQos.getF7());
			f8List.add(vRpDyBscDcrtQos.getF8());
			f9List.add(vRpDyBscDcrtQos.getF9());
			f10List.add(vRpDyBscDcrtQos.getF10());
			f11List.add(vRpDyBscDcrtQos.getF11());
			f12List.add(vRpDyBscDcrtQos.getF12());
			f13List.add(vRpDyBscDcrtQos.getF13());
			f14List.add(vRpDyBscDcrtQos.getF14());
			f15List.add(vRpDyBscDcrtQos.getF15());
		}
		
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("Low SS DL", f4List);
		series.put("Low SS UL", f5List);
		series.put("Low SS UL/DL", f6List);
		series.put("bad Quality DL", f7List);
		series.put("Bad Quality UL", f8List);
		series.put("Bad Quality UL/DL", f9List);
		series.put("Suddenly Lost Connections", f10List);
		series.put("Excessive TA", f11List);
		series.put("FER DL", f12List);
		series.put("FER UL", f13List);
		series.put("FER UL/DL", f14List);
		series.put("Other", f15List);
		
		model.addAttribute("chart", Chart.stackedColumn("chart", "Drop Reason (%)", categories, series));

		return new ModelAndView("dyBscTchDrp");
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
		
		List<VRpHrBscDcrtQos> hrBscDcrtQosList = new ArrayList<VRpHrBscDcrtQos>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			hrBscDcrtQosList.addAll(hrBscDcrtQosDao.filter(dates.get(day).get("startHour"), dates.get(day).get("endHour"), day, bscid, region));
		}
		model.addAttribute("hrBscDcrtQosList", hrBscDcrtQosList);
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
		List<Float> f8List = new ArrayList<Float>();
		List<Float> f9List = new ArrayList<Float>();
		List<Float> f10List = new ArrayList<Float>();
		List<Float> f11List = new ArrayList<Float>();
		List<Float> f12List = new ArrayList<Float>();
		List<Float> f13List = new ArrayList<Float>();
		List<Float> f14List = new ArrayList<Float>();
		List<Float> f15List = new ArrayList<Float>();
		for (VRpHrBscDcrtQos vRpHrBscDcrtQos : hrBscDcrtQosList) {
			categories.add(Integer.toString(vRpHrBscDcrtQos.getHour())+":00");
			f4List.add(vRpHrBscDcrtQos.getF4());
			f5List.add(vRpHrBscDcrtQos.getF5());
			f6List.add(vRpHrBscDcrtQos.getF6());
			f7List.add(vRpHrBscDcrtQos.getF7());
			f8List.add(vRpHrBscDcrtQos.getF8());
			f9List.add(vRpHrBscDcrtQos.getF9());
			f10List.add(vRpHrBscDcrtQos.getF10());
			f11List.add(vRpHrBscDcrtQos.getF11());
			f12List.add(vRpHrBscDcrtQos.getF12());
			f13List.add(vRpHrBscDcrtQos.getF13());
			f14List.add(vRpHrBscDcrtQos.getF14());
			f15List.add(vRpHrBscDcrtQos.getF15());
		}
		
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("Low SS DL", f4List);
		series.put("Low SS UL", f5List);
		series.put("Low SS UL/DL", f6List);
		series.put("bad Quality DL", f7List);
		series.put("Bad Quality UL", f8List);
		series.put("Bad Quality UL/DL", f9List);
		series.put("Suddenly Lost Connections", f10List);
		series.put("Excessive TA", f11List);
		series.put("FER DL", f12List);
		series.put("FER UL", f13List);
		series.put("FER UL/DL", f14List);
		series.put("Other", f15List);
		
		model.addAttribute("chart", Chart.stackedColumn("chart", "Drop Reason (%)", categories, series));

		return new ModelAndView("hrBscTchDrp");
	}
}
