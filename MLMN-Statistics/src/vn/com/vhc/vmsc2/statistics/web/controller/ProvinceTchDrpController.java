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

import vn.com.vhc.vmsc2.statistics.dao.DyProvinceDcrtQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrProvinceDcrtQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.DyProvinceDcrtQos;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HrProvinceDcrtQos;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;

@Controller
@RequestMapping("/report/radio/province/tdrop/*")
public class ProvinceTchDrpController extends BaseController{
	@Autowired
	private DyProvinceDcrtQosDAO dyProvinceDcrtQosDao;
	@Autowired
	private HrProvinceDcrtQosDAO hrProvinceDcrtQosDao;
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
		
		List<DyProvinceDcrtQos> dyProvinceDcrtQos = dyProvinceDcrtQosDao.filter(df.format(startDate), df.format(endDate), province, region);
		
		model.addAttribute("province", province);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("dyProvinceDcrtQos", dyProvinceDcrtQos);

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
		List<Float> f10List = new ArrayList<Float>();
		List<Float> f11List = new ArrayList<Float>();
		List<Float> f12List = new ArrayList<Float>();
		List<Float> f13List = new ArrayList<Float>();
		List<Float> f14List = new ArrayList<Float>();
		List<Float> f15List = new ArrayList<Float>();
		for (DyProvinceDcrtQos vRpDyProvinceDcrtQos : dyProvinceDcrtQos) {
			categories.add(df.format(vRpDyProvinceDcrtQos.getDay()));
			f1List.add(vRpDyProvinceDcrtQos.getF1());
			f2List.add(vRpDyProvinceDcrtQos.getF2());
			f3List.add(vRpDyProvinceDcrtQos.getF3());
			f4List.add(vRpDyProvinceDcrtQos.getF4());
			f5List.add(vRpDyProvinceDcrtQos.getF5());
			f6List.add(vRpDyProvinceDcrtQos.getF6());
			f7List.add(vRpDyProvinceDcrtQos.getF7());
			f8List.add(vRpDyProvinceDcrtQos.getF8());
			f9List.add(vRpDyProvinceDcrtQos.getF9());
			f10List.add(vRpDyProvinceDcrtQos.getF10());
			f11List.add(vRpDyProvinceDcrtQos.getF11());
			f12List.add(vRpDyProvinceDcrtQos.getF12());
			f13List.add(vRpDyProvinceDcrtQos.getF13());
			f14List.add(vRpDyProvinceDcrtQos.getF14());
			f15List.add(vRpDyProvinceDcrtQos.getF15());
		}
		
		Map<String, List<Float>> f1Series = new LinkedHashMap<String, List<Float>>();
		f1Series.put("Total No. of Dropperd TCH Connections--4572a7", f1List);
		model.addAttribute("f1Chart", Chart.multiColumn("f1Chart", "Total No. of Dropperd TCH Connections", categories, f1Series));

		Map<String, List<Float>> f2Series = new LinkedHashMap<String, List<Float>>();
		f2Series.put("TCH DROP (%)--aa4643", f2List);
		model.addAttribute("f2Chart", Chart.multiColumn("f2Chart", "TCH DROP (%)", categories, f2Series));

		Map<String, List<Float>> f3Series = new LinkedHashMap<String, List<Float>>();
		f3Series.put("TCH Erlang Minutes per Drop--89a54e", f3List);
		model.addAttribute("f3Chart", Chart.multiColumn("f3Chart", "TCH Erlang Minutes per Drop", categories, f3Series));

		Map<String, List<Float>> f4Series = new LinkedHashMap<String, List<Float>>();
		f4Series.put("Drop Reason, Low SS DL (%)--80699b", f4List);
		model.addAttribute("f4Chart", Chart.multiColumn("f4Chart", "Drop Reason, Low SS DL (%)", categories, f4Series));

		Map<String, List<Float>> f5Series = new LinkedHashMap<String, List<Float>>();
		f5Series.put("Drop Reason, Low SS UL (%)--db843d", f5List);
		model.addAttribute("f5Chart", Chart.multiColumn("f5Chart", "Drop Reason, Low SS UL (%)", categories, f5Series));

		Map<String, List<Float>> f6Series = new LinkedHashMap<String, List<Float>>();
		f6Series.put("Drop Reason, Low SS UL/DL (%)--89a54e", f6List);
		model.addAttribute("f6Chart", Chart.multiColumn("f6Chart", "Drop Reason, Low SS UL/DL (%)", categories, f6Series));

		Map<String, List<Float>> f7Series = new LinkedHashMap<String, List<Float>>();
		f7Series.put("Drop Reason, bad Quality DL (%))--4572a7", f7List);
		model.addAttribute("f7Chart", Chart.multiColumn("f7Chart", "Drop Reason, bad Quality DL (%)", categories, f7Series));

		Map<String, List<Float>> f8Series = new LinkedHashMap<String, List<Float>>();
		f8Series.put("Drop Reason, Bad Quality UL (%)--80699b", f8List);
		model.addAttribute("f8Chart", Chart.multiColumn("f8Chart", "Drop Reason, Bad Quality UL (%)", categories, f8Series));

		Map<String, List<Float>> f9Series = new LinkedHashMap<String, List<Float>>();
		f9Series.put("Drop Reason, Bad Quality UL/DL (%)--db843d", f9List);
		model.addAttribute("f9Chart", Chart.multiColumn("f9Chart", "Drop Reason, Bad Quality UL/DL (%)", categories, f9Series));

		Map<String, List<Float>> f10Series = new LinkedHashMap<String, List<Float>>();
		f10Series.put("Drop Reason, Suddenly Lost Connections (%)--aa4643", f10List);
		model.addAttribute("f10Chart", Chart.multiColumn("f10Chart", "Drop Reason, Suddenly Lost Connections (%)", categories, f10Series));

		Map<String, List<Float>> f11Series = new LinkedHashMap<String, List<Float>>();
		f11Series.put("Drop Reason, Excessive TA (%)--db843d", f11List);
		model.addAttribute("f11Chart", Chart.multiColumn("f11Chart", "Drop Reason, Excessive TA (%)", categories, f11Series));

		Map<String, List<Float>> f12Series = new LinkedHashMap<String, List<Float>>();
		f12Series.put("Drop Reason, FER DL (%)--89a54e", f12List);
		model.addAttribute("f12Chart", Chart.multiColumn("f12Chart", "Drop Reason, FER DL (%)", categories, f12Series));

		Map<String, List<Float>> f13Series = new LinkedHashMap<String, List<Float>>();
		f13Series.put("Drop Reason, FER UK (%))--4572a7", f13List);
		model.addAttribute("f13Chart", Chart.multiColumn("f13Chart", "Drop Reason, FER UK (%)", categories, f13Series));

		Map<String, List<Float>> f14Series = new LinkedHashMap<String, List<Float>>();
		f14Series.put("Drop Reason, FER UL/DL (%)--80699b", f14List);
		model.addAttribute("f14Chart", Chart.multiColumn("f14Chart", "Drop Reason, FER UL/DL (%)", categories, f14Series));

		Map<String, List<Float>> f15Series = new LinkedHashMap<String, List<Float>>();
		f15Series.put("Drop Reason, Other (%)--db843d", f15List);
		model.addAttribute("f15Chart", Chart.multiColumn("f15Chart", "Drop Reason, Other (%)", categories, f15Series));

		return new ModelAndView("dyProvinceTchDrp");
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
		
		List<HrProvinceDcrtQos> hrProvinceDcrtQosList = new ArrayList<HrProvinceDcrtQos>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			hrProvinceDcrtQosList.addAll(hrProvinceDcrtQosDao.filter(dates.get(day).get("startHour"), dates.get(day).get("endHour"), day, province, region));
		}
		model.addAttribute("hrProvinceDcrtQosList", hrProvinceDcrtQosList);
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
		List<Float> f10List = new ArrayList<Float>();
		List<Float> f11List = new ArrayList<Float>();
		List<Float> f12List = new ArrayList<Float>();
		List<Float> f13List = new ArrayList<Float>();
		List<Float> f14List = new ArrayList<Float>();
		List<Float> f15List = new ArrayList<Float>();
		for (HrProvinceDcrtQos vRpHrProvinceDcrtQos : hrProvinceDcrtQosList) {
			categories.add(Integer.toString(vRpHrProvinceDcrtQos.getHour())+":00"+df.format(vRpHrProvinceDcrtQos.getDay()));
			f1List.add(vRpHrProvinceDcrtQos.getF1());
			f2List.add(vRpHrProvinceDcrtQos.getF2());
			f3List.add(vRpHrProvinceDcrtQos.getF3());
			f4List.add(vRpHrProvinceDcrtQos.getF4());
			f5List.add(vRpHrProvinceDcrtQos.getF5());
			f6List.add(vRpHrProvinceDcrtQos.getF6());
			f7List.add(vRpHrProvinceDcrtQos.getF7());
			f8List.add(vRpHrProvinceDcrtQos.getF8());
			f9List.add(vRpHrProvinceDcrtQos.getF9());
			f10List.add(vRpHrProvinceDcrtQos.getF10());
			f11List.add(vRpHrProvinceDcrtQos.getF11());
			f12List.add(vRpHrProvinceDcrtQos.getF12());
			f13List.add(vRpHrProvinceDcrtQos.getF13());
			f14List.add(vRpHrProvinceDcrtQos.getF14());
			f15List.add(vRpHrProvinceDcrtQos.getF15());
		}
		
		Map<String, List<Float>> f1Series = new LinkedHashMap<String, List<Float>>();
		f1Series.put("Total No. of Dropperd TCH Connections--4572a7", f1List);
		model.addAttribute("f1Chart", Chart.multiColumn("f1Chart", "Total No. of Dropperd TCH Connections", categories, f1Series));

		Map<String, List<Float>> f2Series = new LinkedHashMap<String, List<Float>>();
		f2Series.put("TCH DROP (%)--aa4643", f2List);
		model.addAttribute("f2Chart", Chart.multiColumn("f2Chart", "TCH DROP (%)", categories, f2Series));

		Map<String, List<Float>> f3Series = new LinkedHashMap<String, List<Float>>();
		f3Series.put("TCH Erlang Minutes per Drop--89a54e", f3List);
		model.addAttribute("f3Chart", Chart.multiColumn("f3Chart", "TCH Erlang Minutes per Drop", categories, f3Series));

		Map<String, List<Float>> f4Series = new LinkedHashMap<String, List<Float>>();
		f4Series.put("Drop Reason, Low SS DL (%)--80699b", f4List);
		model.addAttribute("f4Chart", Chart.multiColumn("f4Chart", "Drop Reason, Low SS DL (%)", categories, f4Series));

		Map<String, List<Float>> f5Series = new LinkedHashMap<String, List<Float>>();
		f5Series.put("Drop Reason, Low SS UL (%)--db843d", f5List);
		model.addAttribute("f5Chart", Chart.multiColumn("f5Chart", "Drop Reason, Low SS UL (%)", categories, f5Series));

		Map<String, List<Float>> f6Series = new LinkedHashMap<String, List<Float>>();
		f6Series.put("Drop Reason, Low SS UL/DL (%)--89a54e", f6List);
		model.addAttribute("f6Chart", Chart.multiColumn("f6Chart", "Drop Reason, Low SS UL/DL (%)", categories, f6Series));

		Map<String, List<Float>> f7Series = new LinkedHashMap<String, List<Float>>();
		f7Series.put("Drop Reason, bad Quality DL (%))--4572a7", f7List);
		model.addAttribute("f7Chart", Chart.multiColumn("f7Chart", "Drop Reason, bad Quality DL (%)", categories, f7Series));

		Map<String, List<Float>> f8Series = new LinkedHashMap<String, List<Float>>();
		f8Series.put("Drop Reason, Bad Quality UL (%)--80699b", f8List);
		model.addAttribute("f8Chart", Chart.multiColumn("f8Chart", "Drop Reason, Bad Quality UL (%)", categories, f8Series));

		Map<String, List<Float>> f9Series = new LinkedHashMap<String, List<Float>>();
		f9Series.put("Drop Reason, Bad Quality UL/DL (%)--db843d", f9List);
		model.addAttribute("f9Chart", Chart.multiColumn("f9Chart", "Drop Reason, Bad Quality UL/DL (%)", categories, f9Series));

		Map<String, List<Float>> f10Series = new LinkedHashMap<String, List<Float>>();
		f10Series.put("Drop Reason, Suddenly Lost Connections (%)--aa4643", f10List);
		model.addAttribute("f10Chart", Chart.multiColumn("f10Chart", "Drop Reason, Suddenly Lost Connections (%)", categories, f10Series));

		Map<String, List<Float>> f11Series = new LinkedHashMap<String, List<Float>>();
		f11Series.put("Drop Reason, Excessive TA (%)--db843d", f11List);
		model.addAttribute("f11Chart", Chart.multiColumn("f11Chart", "Drop Reason, Excessive TA (%)", categories, f11Series));

		Map<String, List<Float>> f12Series = new LinkedHashMap<String, List<Float>>();
		f12Series.put("Drop Reason, FER DL (%)--89a54e", f12List);
		model.addAttribute("f12Chart", Chart.multiColumn("f12Chart", "Drop Reason, FER DL (%)", categories, f12Series));

		Map<String, List<Float>> f13Series = new LinkedHashMap<String, List<Float>>();
		f13Series.put("Drop Reason, FER UK (%))--4572a7", f13List);
		model.addAttribute("f13Chart", Chart.multiColumn("f13Chart", "Drop Reason, FER UK (%)", categories, f13Series));

		Map<String, List<Float>> f14Series = new LinkedHashMap<String, List<Float>>();
		f14Series.put("Drop Reason, FER UL/DL (%)--80699b", f14List);
		model.addAttribute("f14Chart", Chart.multiColumn("f14Chart", "Drop Reason, FER UL/DL (%)", categories, f14Series));

		Map<String, List<Float>> f15Series = new LinkedHashMap<String, List<Float>>();
		f15Series.put("Drop Reason, Other (%)--db843d", f15List);
		model.addAttribute("f15Chart", Chart.multiColumn("f15Chart", "Drop Reason, Other (%)", categories, f15Series));

		return new ModelAndView("hrProvinceTchDrp");
	}
}
