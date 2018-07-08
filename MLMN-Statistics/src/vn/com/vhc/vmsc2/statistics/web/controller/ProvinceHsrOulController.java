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

import vn.com.vhc.vmsc2.statistics.dao.DyProvinceHsrOulQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrProvinceHsrOulQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.DyProvinceHsrOulQos;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HrProvinceHsrOulQos;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;

@Controller
@RequestMapping("/report/radio/province/ho/*")
public class ProvinceHsrOulController extends BaseController{
	@Autowired
	private DyProvinceHsrOulQosDAO dyProvinceHsrOulQosDao;
	@Autowired
	private HrProvinceHsrOulQosDAO hrProvinceHsrOulQosDao;
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
		
		List<DyProvinceHsrOulQos> dyProvinceHsrOulQos = dyProvinceHsrOulQosDao.filter(df.format(startDate), df.format(endDate), province, region);
		
		model.addAttribute("province", province);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("dyProvinceHsrOulQos", dyProvinceHsrOulQos);
		
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
		List<Float> f9List = new ArrayList<Float>();
		for (DyProvinceHsrOulQos vRpDyProvinceHsrOulQos : dyProvinceHsrOulQos) {
			categories.add(df.format(vRpDyProvinceHsrOulQos.getDay()));
			f1List.add(Helper.int2Float(vRpDyProvinceHsrOulQos.getF1()));
			f2List.add(vRpDyProvinceHsrOulQos.getF2());
			f3List.add(Helper.int2Float(vRpDyProvinceHsrOulQos.getF3()));
			f4List.add(vRpDyProvinceHsrOulQos.getF4());
			f5List.add(vRpDyProvinceHsrOulQos.getF5());
			f6List.add(vRpDyProvinceHsrOulQos.getF6());
			f7List.add(vRpDyProvinceHsrOulQos.getF7());
			f8List.add(vRpDyProvinceHsrOulQos.getF8());
			f9List.add(vRpDyProvinceHsrOulQos.getF9());
		}
		
		Map<String, List<Float>> f1Series = new LinkedHashMap<String, List<Float>>();
		f1Series.put("No. of Handover Attempts to UL--4572a7", f1List);
		model.addAttribute("f1Chart", Chart.multiColumn("f1Chart", "No. of Handover Attempts to UL", categories, f1Series));

		Map<String, List<Float>> f2Series = new LinkedHashMap<String, List<Float>>();
		f2Series.put("Handover OL to UL Success (%)--aa4643", f2List);
		model.addAttribute("f2Chart", Chart.multiColumn("f2Chart", "Handover OL to UL Success (%)", categories, f2Series));

		Map<String, List<Float>> f3Series = new LinkedHashMap<String, List<Float>>();
		f3Series.put("No. of Handover Attempts to UL (BQ) (%)--89a54e", f3List);
		model.addAttribute("f3Chart", Chart.multiColumn("f3Chart", "No. of Handover Attempts to UL (BQ) (%)", categories, f3Series));

		Map<String, List<Float>> f4Series = new LinkedHashMap<String, List<Float>>();
		f4Series.put("Handover OL to UL Success (BQ)(%)--80699b", f4List);
		model.addAttribute("f4Chart", Chart.multiColumn("f4Chart", "Handover OL to UL Success (BQ)(%)", categories, f4Series));

		Map<String, List<Float>> f5Series = new LinkedHashMap<String, List<Float>>();
		f5Series.put("No. of Handover Attempts to OL--db843d", f5List);
		model.addAttribute("f5Chart", Chart.multiColumn("f5Chart", "No. of Handover Attempts to OL", categories, f5Series));

		Map<String, List<Float>> f6Series = new LinkedHashMap<String, List<Float>>();
		f6Series.put("Handover UL to OL Success (%)--89a54e", f6List);
		model.addAttribute("f6Chart", Chart.multiColumn("f6Chart", "Handover UL to OL Success (%)", categories, f6Series));

		Map<String, List<Float>> f7Series = new LinkedHashMap<String, List<Float>>();
		f7Series.put("No. of Handover Attempts to OL (BQ)--4572a7", f7List);
		model.addAttribute("f7Chart", Chart.multiColumn("f7Chart", "No. of Handover Attempts to OL (BQ)", categories, f7Series));

		Map<String, List<Float>> f8Series = new LinkedHashMap<String, List<Float>>();
		f8Series.put("Hanover UL to OL Success (BQ)(%)--80699b", f8List);
		model.addAttribute("f8Chart", Chart.multiColumn("f8Chart", "Hanover UL to OL Success (BQ)(%)", categories, f8Series));

		Map<String, List<Float>> f9Series = new LinkedHashMap<String, List<Float>>();
		f9Series.put("Data Availability (%)--db843d", f9List);
		model.addAttribute("f9Chart", Chart.multiColumn("f9Chart", "Data Availability (%)", categories, f9Series));
		return new ModelAndView("dyProvinceHsrOul");
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
		
		List<HrProvinceHsrOulQos> hrProvinceHsrOulQosList = new ArrayList<HrProvinceHsrOulQos>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			hrProvinceHsrOulQosList.addAll(hrProvinceHsrOulQosDao.filter(dates.get(day).get("startHour"), dates.get(day).get("endHour"), day, province, region));
		}
		model.addAttribute("hrProvinceHsrOulQosList", hrProvinceHsrOulQosList);
		model.addAttribute("province", province);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		
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
		List<Float> f9List = new ArrayList<Float>();
		for (HrProvinceHsrOulQos vRpHrProvinceHsrOulQos : hrProvinceHsrOulQosList) {
			categories.add(Integer.toString(vRpHrProvinceHsrOulQos.getHour())+":00"+df.format(vRpHrProvinceHsrOulQos.getDay()));
			f1List.add(Helper.int2Float(vRpHrProvinceHsrOulQos.getF1()));
			f2List.add(vRpHrProvinceHsrOulQos.getF2());
			f3List.add(Helper.int2Float(vRpHrProvinceHsrOulQos.getF3()));
			f4List.add(vRpHrProvinceHsrOulQos.getF4());
			f5List.add(vRpHrProvinceHsrOulQos.getF5());
			f6List.add(vRpHrProvinceHsrOulQos.getF6());
			f7List.add(vRpHrProvinceHsrOulQos.getF7());
			f8List.add(vRpHrProvinceHsrOulQos.getF8());
			f9List.add(vRpHrProvinceHsrOulQos.getF9());
		}
		
		Map<String, List<Float>> f1Series = new LinkedHashMap<String, List<Float>>();
		f1Series.put("No. of Handover Attempts to UL--4572a7", f1List);
		model.addAttribute("f1Chart", Chart.multiColumn("f1Chart", "No. of Handover Attempts to UL", categories, f1Series));

		Map<String, List<Float>> f2Series = new LinkedHashMap<String, List<Float>>();
		f2Series.put("Handover OL to UL Success (%)--aa4643", f2List);
		model.addAttribute("f2Chart", Chart.multiColumn("f2Chart", "Handover OL to UL Success (%)", categories, f2Series));

		Map<String, List<Float>> f3Series = new LinkedHashMap<String, List<Float>>();
		f3Series.put("No. of Handover Attempts to UL (BQ) (%)--89a54e", f3List);
		model.addAttribute("f3Chart", Chart.multiColumn("f3Chart", "No. of Handover Attempts to UL (BQ) (%)", categories, f3Series));

		Map<String, List<Float>> f4Series = new LinkedHashMap<String, List<Float>>();
		f4Series.put("Handover OL to UL Success (BQ)(%)--80699b", f4List);
		model.addAttribute("f4Chart", Chart.multiColumn("f4Chart", "Handover OL to UL Success (BQ)(%)", categories, f4Series));

		Map<String, List<Float>> f5Series = new LinkedHashMap<String, List<Float>>();
		f5Series.put("No. of Handover Attempts to OL--db843d", f5List);
		model.addAttribute("f5Chart", Chart.multiColumn("f5Chart", "No. of Handover Attempts to OL", categories, f5Series));

		Map<String, List<Float>> f6Series = new LinkedHashMap<String, List<Float>>();
		f6Series.put("Handover UL to OL Success (%)--89a54e", f6List);
		model.addAttribute("f6Chart", Chart.multiColumn("f6Chart", "Handover UL to OL Success (%)", categories, f6Series));

		Map<String, List<Float>> f7Series = new LinkedHashMap<String, List<Float>>();
		f7Series.put("No. of Handover Attempts to OL (BQ)--4572a7", f7List);
		model.addAttribute("f7Chart", Chart.multiColumn("f7Chart", "No. of Handover Attempts to OL (BQ)", categories, f7Series));

		Map<String, List<Float>> f8Series = new LinkedHashMap<String, List<Float>>();
		f8Series.put("Hanover UL to OL Success (BQ)(%)--80699b", f8List);
		model.addAttribute("f8Chart", Chart.multiColumn("f8Chart", "Hanover UL to OL Success (BQ)(%)", categories, f8Series));

		Map<String, List<Float>> f9Series = new LinkedHashMap<String, List<Float>>();
		f9Series.put("Data Availability (%)--db843d", f9List);
		model.addAttribute("f9Chart", Chart.multiColumn("f9Chart", "Data Availability (%)", categories, f9Series));
		return new ModelAndView("hrProvinceHsrOul");
	}
}
