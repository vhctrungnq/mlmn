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
import vn.com.vhc.vmsc2.statistics.dao.VRpDyBscTrafficQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyRegionDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrBscTrafficQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBscTrafficQos;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyRegion;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrBscTrafficQos;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;

@Controller
@RequestMapping("/report/radio/bsc/tTraffic/*")
public class BscTrafficController extends BaseController{
	@Autowired
	private VRpDyBscTrafficQosDAO dyBscTrafficQosDao;
	@Autowired
	private VRpHrBscTrafficQosDAO hrBscTrafficQosDao;
	@Autowired
	private VRpDyRegionDAO vRpDyRegionDao;
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
		
		List<VRpDyBscTrafficQos> dyBscTrafficQos = dyBscTrafficQosDao.filter(df.format(startDate), df.format(endDate), bscid, region);

		List<VRpDyRegion> vRpDyRegionList = vRpDyRegionDao.filter(df.format(startDate), df.format(endDate));
		
		model.addAttribute("vRpDyRegionList", vRpDyRegionList);
		model.addAttribute("region", region);
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("dyBscTrafficQos", dyBscTrafficQos);
		
		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> f1List = new ArrayList<Float>();
		List<Float> f2List = new ArrayList<Float>();
		List<Float> f3List = new ArrayList<Float>();
		List<Float> f4List = new ArrayList<Float>();
		List<Float> f5List = new ArrayList<Float>();
		List<Float> f6List = new ArrayList<Float>();
		List<Float> f7List = new ArrayList<Float>();
		List<Float> f8List = new ArrayList<Float>();
		for (VRpDyBscTrafficQos vRpDyBscTrafficQos : dyBscTrafficQos) {
			categories.add(df.format(vRpDyBscTrafficQos.getDay()));
			f1List.add(vRpDyBscTrafficQos.getF1());
			f2List.add(vRpDyBscTrafficQos.getF2());
			f3List.add(vRpDyBscTrafficQos.getF3());
			f4List.add(vRpDyBscTrafficQos.getF4());
			f5List.add(vRpDyBscTrafficQos.getF5());
			f6List.add(vRpDyBscTrafficQos.getF6());
			f7List.add(vRpDyBscTrafficQos.getF7());
			f8List.add(vRpDyBscTrafficQos.getF8());
		}
		
		Map<String, List<Float>> f1Series = new LinkedHashMap<String, List<Float>>();
		f1Series.put("EMPD--4572a7", f1List);
		model.addAttribute("f1Chart", Chart.multiColumn("f1Chart", "EMPD", categories, f1Series));

		Map<String, List<Float>> f2Series = new LinkedHashMap<String, List<Float>>();
		f2Series.put("TCH Traffic--aa4643", f2List);
		model.addAttribute("f2Chart", Chart.multiColumn("f2Chart", "TCH Traffic", categories, f2Series));

		Map<String, List<Float>> f3Series = new LinkedHashMap<String, List<Float>>();
		f3Series.put("TCH Traffic FR--89a54e", f3List);
		model.addAttribute("f3Chart", Chart.multiColumn("f3Chart", "TCH Traffic FR", categories, f3Series));

		Map<String, List<Float>> f4Series = new LinkedHashMap<String, List<Float>>();
		f4Series.put("TCH Traffic HR--80699b", f4List);
		model.addAttribute("f4Chart", Chart.multiColumn("f4Chart", "TCH Traffic HR", categories, f4Series));

		Map<String, List<Float>> f5Series = new LinkedHashMap<String, List<Float>>();
		f5Series.put("TCH Traffic UL FullRate--db843d", f5List);
		model.addAttribute("f5Chart", Chart.multiColumn("f5Chart", "TCH Traffic UL FullRate", categories, f5Series));

		Map<String, List<Float>> f6Series = new LinkedHashMap<String, List<Float>>();
		f6Series.put("TCH Traffic UL HalfRate--89a54e", f6List);
		model.addAttribute("f6Chart", Chart.multiColumn("f6Chart", "TCH Traffic UL HalfRate", categories, f6Series));

		Map<String, List<Float>> f7Series = new LinkedHashMap<String, List<Float>>();
		f7Series.put("TCH Traffic OL FullRate--4572a7", f7List);
		model.addAttribute("f7Chart", Chart.multiColumn("f7Chart", "TCH Traffic OL FullRate", categories, f7Series));

		Map<String, List<Float>> f8Series = new LinkedHashMap<String, List<Float>>();
		f8Series.put("TCH Traffic OL HalfRate--80699b", f8List);
		model.addAttribute("f8Chart", Chart.multiColumn("f8Chart", "TCH Traffic OL HalfRate", categories, f8Series));
		return new ModelAndView("dyBscTrafficQos");
	}
	
	@RequestMapping("hr")
	public ModelAndView hrList(@RequestParam(required = false) String region,@RequestParam(required = false) String bscid, @RequestParam(required = false) String startHour,
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
		
		List<VRpHrBscTrafficQos> hrBscTrafficQosList = new ArrayList<VRpHrBscTrafficQos>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			hrBscTrafficQosList.addAll(hrBscTrafficQosDao.filter(dates.get(day).get("startHour"), dates.get(day).get("endHour"), day, bscid, region));
		}
		model.addAttribute("hrBscTrafficQosList", hrBscTrafficQosList);
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);

		List<VRpDyRegion> vRpDyRegionList = vRpDyRegionDao.filter(df.format(startDate), df.format(endDate));
		
		model.addAttribute("vRpDyRegionList", vRpDyRegionList);
		model.addAttribute("region", region);
		
		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> f1List = new ArrayList<Float>();
		List<Float> f2List = new ArrayList<Float>();
		List<Float> f3List = new ArrayList<Float>();
		List<Float> f4List = new ArrayList<Float>();
		List<Float> f5List = new ArrayList<Float>();
		List<Float> f6List = new ArrayList<Float>();
		List<Float> f7List = new ArrayList<Float>();
		List<Float> f8List = new ArrayList<Float>();
		for (VRpHrBscTrafficQos vRpHrBscTrafficQos : hrBscTrafficQosList) {
			categories.add(Integer.toString(vRpHrBscTrafficQos.getHour())+":00"+df.format(vRpHrBscTrafficQos.getDay()));
			f1List.add(vRpHrBscTrafficQos.getF1());
			f2List.add(vRpHrBscTrafficQos.getF2());
			f3List.add(vRpHrBscTrafficQos.getF3());
			f4List.add(vRpHrBscTrafficQos.getF4());
			f5List.add(vRpHrBscTrafficQos.getF5());
			f6List.add(vRpHrBscTrafficQos.getF6());
			f7List.add(vRpHrBscTrafficQos.getF7());
			f8List.add(vRpHrBscTrafficQos.getF8());
		}
		
		Map<String, List<Float>> f1Series = new LinkedHashMap<String, List<Float>>();
		f1Series.put("EMPD--4572a7", f1List);
		model.addAttribute("f1Chart", Chart.multiColumn("f1Chart", "EMPD", categories, f1Series));

		Map<String, List<Float>> f2Series = new LinkedHashMap<String, List<Float>>();
		f2Series.put("TCH Traffic--aa4643", f2List);
		model.addAttribute("f2Chart", Chart.multiColumn("f2Chart", "TCH Traffic", categories, f2Series));

		Map<String, List<Float>> f3Series = new LinkedHashMap<String, List<Float>>();
		f3Series.put("TCH Traffic FR--89a54e", f3List);
		model.addAttribute("f3Chart", Chart.multiColumn("f3Chart", "TCH Traffic FR", categories, f3Series));

		Map<String, List<Float>> f4Series = new LinkedHashMap<String, List<Float>>();
		f4Series.put("TCH Traffic HR--80699b", f4List);
		model.addAttribute("f4Chart", Chart.multiColumn("f4Chart", "TCH Traffic HR", categories, f4Series));

		Map<String, List<Float>> f5Series = new LinkedHashMap<String, List<Float>>();
		f5Series.put("TCH Traffic UL FullRate--db843d", f5List);
		model.addAttribute("f5Chart", Chart.multiColumn("f5Chart", "TCH Traffic UL FullRate", categories, f5Series));

		Map<String, List<Float>> f6Series = new LinkedHashMap<String, List<Float>>();
		f6Series.put("TCH Traffic UL HalfRate--89a54e", f6List);
		model.addAttribute("f6Chart", Chart.multiColumn("f6Chart", "TCH Traffic UL HalfRate", categories, f6Series));

		Map<String, List<Float>> f7Series = new LinkedHashMap<String, List<Float>>();
		f7Series.put("TCH Traffic OL FullRate--4572a7", f7List);
		model.addAttribute("f7Chart", Chart.multiColumn("f7Chart", "TCH Traffic OL FullRate", categories, f7Series));

		Map<String, List<Float>> f8Series = new LinkedHashMap<String, List<Float>>();
		f8Series.put("TCH Traffic OL HalfRate--80699b", f8List);
		model.addAttribute("f8Chart", Chart.multiColumn("f8Chart", "TCH Traffic OL HalfRate", categories, f8Series));
		return new ModelAndView("hrBscTrafficQos");
	}
}
