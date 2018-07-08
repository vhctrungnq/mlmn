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
import vn.com.vhc.vmsc2.statistics.dao.DyCellDcrsQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrCellDcrsQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.Cell;
import vn.com.vhc.vmsc2.statistics.domain.DyCellDcrsQos;
import vn.com.vhc.vmsc2.statistics.domain.HrCellDcrsQos;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;

@Controller
@RequestMapping("/report/radio/cell/sdrop/*")
public class CellSdcchDropController extends BaseController{
	@Autowired
	private DyCellDcrsQosDAO dyCellDcrsQosDao;
	@Autowired
	private HrCellDcrsQosDAO hrCellDcrsQosDao;
	@Autowired
	private CellDAO cellDao;
	@Autowired
	private BscDAO bscDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("dy")
	public ModelAndView dyList(@RequestParam(required = false) String cellid,@RequestParam(required = false) String bscid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model) {
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		List<DyCellDcrsQos> dyCellDcrsQos = dyCellDcrsQosDao.filter(df.format(startDate), df.format(endDate),bscid, cellid);
		
		model.addAttribute("cellid", cellid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscid", bscid);
		model.addAttribute("bscList", bscList);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("dyCellDcrsQos", dyCellDcrsQos);
		
		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> f4List = new ArrayList<Float>();
		List<Float> f5List = new ArrayList<Float>();
		List<Float> f6List = new ArrayList<Float>();
		List<Float> f7List = new ArrayList<Float>();
		for (DyCellDcrsQos vRpDyCellDcrsQos : dyCellDcrsQos) {
			categories.add(df.format(vRpDyCellDcrsQos.getDay()));
			f4List.add(vRpDyCellDcrsQos.getF4());
			f5List.add(vRpDyCellDcrsQos.getF5());
			f6List.add(vRpDyCellDcrsQos.getF6());
			f7List.add(vRpDyCellDcrsQos.getF7());
		}

		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("Low SS", f4List);
		series.put("Bad Quality", f5List);
		series.put("Excessive TA", f6List);
		series.put("Other", f7List);
		
		model.addAttribute("chart", Chart.stackedColumn("chart", "Drop Reason (%)", categories, series));
		
		return new ModelAndView("dyCellSdcchDrp");
	}
	
	@RequestMapping("hr")
	public ModelAndView hrList(@RequestParam(required = true) String cellid,@RequestParam(required = false) String bscid, @RequestParam(required = false) String startHour,
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
		List<HrCellDcrsQos> hrCellDcrsQosList = new ArrayList<HrCellDcrsQos>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			hrCellDcrsQosList.addAll(hrCellDcrsQosDao.filter(dates.get(day).get("startHour"), dates.get(day).get("endHour"), day, bscid, cellid));
		}
		model.addAttribute("hrCellDcrsQosList", hrCellDcrsQosList);
		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);
		
		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> f4List = new ArrayList<Float>();
		List<Float> f5List = new ArrayList<Float>();
		List<Float> f6List = new ArrayList<Float>();
		List<Float> f7List = new ArrayList<Float>();
		for (HrCellDcrsQos vRpHrCellDcrsQos : hrCellDcrsQosList) {
			categories.add(Integer.toString(vRpHrCellDcrsQos.getHour())+":00");
			f4List.add(vRpHrCellDcrsQos.getF4());
			f5List.add(vRpHrCellDcrsQos.getF5());
			f6List.add(vRpHrCellDcrsQos.getF6());
			f7List.add(vRpHrCellDcrsQos.getF7());
		}

		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("Low SS", f4List);
		series.put("Bad Quality", f5List);
		series.put("Excessive TA", f6List);
		series.put("Other", f7List);
		
		model.addAttribute("chart", Chart.stackedColumn("chart", "Drop Reason (%)", categories, series));
		
		return new ModelAndView("hrCellSdcchDrp");
	}
}
