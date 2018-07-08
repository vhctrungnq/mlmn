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
import vn.com.vhc.vmsc2.statistics.dao.CellDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellTrafficQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrCellTrafficQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.Cell;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellTrafficQos;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrCellTrafficQos;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;

@Controller
@RequestMapping("/report/radio/cell/tTraffic/*")
public class CellTrafficController extends BaseController{
	@Autowired
	private VRpDyCellTrafficQosDAO dyCellTrafficQosDao;
	@Autowired
	private VRpHrCellTrafficQosDAO hrCellTrafficQosDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private CellDAO cellDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("dy")
	public ModelAndView dyList(@RequestParam(required = false) String bscid,@RequestParam(required = false) String cellid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model) {
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		List<VRpDyCellTrafficQos> dyCellTrafficQos = dyCellTrafficQosDao.filter(df.format(startDate), df.format(endDate), bscid, cellid);
		
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("dyCellTrafficQos", dyCellTrafficQos);
		model.addAttribute("cellid", cellid);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);
		
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
		for (VRpDyCellTrafficQos vRpDyCellTrafficQos : dyCellTrafficQos) {
			categories.add(df.format(vRpDyCellTrafficQos.getDay()));
			f1List.add(vRpDyCellTrafficQos.getF1());
			f2List.add(vRpDyCellTrafficQos.getF2());
			f3List.add(vRpDyCellTrafficQos.getF3());
			f4List.add(vRpDyCellTrafficQos.getF4());
			f5List.add(vRpDyCellTrafficQos.getF5());
			f6List.add(vRpDyCellTrafficQos.getF6());
			f7List.add(vRpDyCellTrafficQos.getF7());
			f8List.add(vRpDyCellTrafficQos.getF8());
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

		return new ModelAndView("dyCellTrafficQos");
	}
	
	@RequestMapping("hr")
	public ModelAndView hrList(@RequestParam(required = false) String bscid,@RequestParam(required = false) String cellid, @RequestParam(required = false) String startHour,
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
		List<VRpHrCellTrafficQos> hrCellTrafficQosList = new ArrayList<VRpHrCellTrafficQos>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			hrCellTrafficQosList.addAll(hrCellTrafficQosDao.filter(dates.get(day).get("startHour"), dates.get(day).get("endHour"), day, bscid, cellid));
		}
		model.addAttribute("hrCellTrafficQosList", hrCellTrafficQosList);
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		model.addAttribute("cellid", cellid);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);
		
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
		for (VRpHrCellTrafficQos vRpHrCellTrafficQos : hrCellTrafficQosList) {
			categories.add(Integer.toString(vRpHrCellTrafficQos.getHour())+":00"+df.format(vRpHrCellTrafficQos.getDay()));
			f1List.add(vRpHrCellTrafficQos.getF1());
			f2List.add(vRpHrCellTrafficQos.getF2());
			f3List.add(vRpHrCellTrafficQos.getF3());
			f4List.add(vRpHrCellTrafficQos.getF4());
			f5List.add(vRpHrCellTrafficQos.getF5());
			f6List.add(vRpHrCellTrafficQos.getF6());
			f7List.add(vRpHrCellTrafficQos.getF7());
			f8List.add(vRpHrCellTrafficQos.getF8());
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
		return new ModelAndView("hrCellTrafficQos");
	}
}
