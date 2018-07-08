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

import vn.com.vhc.vmsc2.statistics.dao.DyProvinceTrafficQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrProvinceTrafficQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyRegionDAO;
import vn.com.vhc.vmsc2.statistics.domain.DyProvinceTrafficQos;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HrProvinceTrafficQos;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyRegion;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;

@Controller
@RequestMapping("/report/radio/province/tTraffic/*")
public class ProvinceTrafficController extends BaseController{
	@Autowired
	private DyProvinceTrafficQosDAO dyProvinceTrafficQosDao;
	@Autowired
	private HrProvinceTrafficQosDAO hrProvinceTrafficQosDao;
	@Autowired
	private VRpDyRegionDAO vRpDyRegionDao;
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
		
		List<DyProvinceTrafficQos> dyProvinceTrafficQos = dyProvinceTrafficQosDao.filter(df.format(startDate), df.format(endDate), province, region);
		
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("dyProvinceTrafficQos", dyProvinceTrafficQos);

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
		for (DyProvinceTrafficQos vRpDyProvinceTrafficQos : dyProvinceTrafficQos) {
			categories.add(df.format(vRpDyProvinceTrafficQos.getDay()));
			f1List.add(vRpDyProvinceTrafficQos.getF1());
			f2List.add(vRpDyProvinceTrafficQos.getF2());
			f3List.add(vRpDyProvinceTrafficQos.getF3());
			f4List.add(vRpDyProvinceTrafficQos.getF4());
			f5List.add(vRpDyProvinceTrafficQos.getF5());
			f6List.add(vRpDyProvinceTrafficQos.getF6());
			f7List.add(vRpDyProvinceTrafficQos.getF7());
			f8List.add(vRpDyProvinceTrafficQos.getF8());
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

		return new ModelAndView("dyProvinceTrafficQos");
	}
	
	@RequestMapping("hr")
	public ModelAndView hrList(@RequestParam(required = false) String region,@RequestParam(required = false) String province, @RequestParam(required = false) String startHour,
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
		
		List<HrProvinceTrafficQos> hrProvinceTrafficQosList = new ArrayList<HrProvinceTrafficQos>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			hrProvinceTrafficQosList.addAll(hrProvinceTrafficQosDao.filter(dates.get(day).get("startHour"), dates.get(day).get("endHour"), day, province, region));
		}
		model.addAttribute("hrProvinceTrafficQosList", hrProvinceTrafficQosList);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
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
		for (HrProvinceTrafficQos vRpHrProvinceTrafficQos : hrProvinceTrafficQosList) {
			categories.add(Integer.toString(vRpHrProvinceTrafficQos.getHour())+":00"+df.format(vRpHrProvinceTrafficQos.getDay()));
			f1List.add(vRpHrProvinceTrafficQos.getF1());
			f2List.add(vRpHrProvinceTrafficQos.getF2());
			f3List.add(vRpHrProvinceTrafficQos.getF3());
			f4List.add(vRpHrProvinceTrafficQos.getF4());
			f5List.add(vRpHrProvinceTrafficQos.getF5());
			f6List.add(vRpHrProvinceTrafficQos.getF6());
			f7List.add(vRpHrProvinceTrafficQos.getF7());
			f8List.add(vRpHrProvinceTrafficQos.getF8());
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
		return new ModelAndView("hrProvinceTrafficQos");
	}
}
