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
import vn.com.vhc.vmsc2.statistics.dao.DyCellDcrtQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrCellDcrtQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.Cell;
import vn.com.vhc.vmsc2.statistics.domain.DyCellDcrtQos;
import vn.com.vhc.vmsc2.statistics.domain.HrCellDcrtQos;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;

@Controller
@RequestMapping("/report/radio/cell/tdrop/*")
public class CellTchDrpController extends BaseController{
	@Autowired
	private DyCellDcrtQosDAO dyCellDcrtQosDao;
	@Autowired
	private HrCellDcrtQosDAO hrCellDcrtQosDao;
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
		List<DyCellDcrtQos> dyCellDcrtQos = dyCellDcrtQosDao.filter(df.format(startDate), df.format(endDate),bscid, cellid);
		
		model.addAttribute("cellid", cellid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscid", bscid);
		model.addAttribute("bscList", bscList);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("dyCellDcrtQos", dyCellDcrtQos);

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
		for (DyCellDcrtQos vRpDyCellDcrtQos : dyCellDcrtQos) {
			categories.add(df.format(vRpDyCellDcrtQos.getDay()));
			f4List.add(vRpDyCellDcrtQos.getF4());
			f5List.add(vRpDyCellDcrtQos.getF5());
			f6List.add(vRpDyCellDcrtQos.getF6());
			f7List.add(vRpDyCellDcrtQos.getF7());
			f8List.add(vRpDyCellDcrtQos.getF8());
			f9List.add(vRpDyCellDcrtQos.getF9());
			f10List.add(vRpDyCellDcrtQos.getF10());
			f11List.add(vRpDyCellDcrtQos.getF11());
			f12List.add(vRpDyCellDcrtQos.getF12());
			f13List.add(vRpDyCellDcrtQos.getF13());
			f14List.add(vRpDyCellDcrtQos.getF14());
			f15List.add(vRpDyCellDcrtQos.getF15());
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

		return new ModelAndView("dyCellTchDrp");
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
		List<HrCellDcrtQos> hrCellDcrtQosList = new ArrayList<HrCellDcrtQos>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			hrCellDcrtQosList.addAll(hrCellDcrtQosDao.filter(dates.get(day).get("startHour"), dates.get(day).get("endHour"), day, bscid, cellid));
		}
		model.addAttribute("hrCellDcrtQosList", hrCellDcrtQosList);
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
		List<Float> f8List = new ArrayList<Float>();
		List<Float> f9List = new ArrayList<Float>();
		List<Float> f10List = new ArrayList<Float>();
		List<Float> f11List = new ArrayList<Float>();
		List<Float> f12List = new ArrayList<Float>();
		List<Float> f13List = new ArrayList<Float>();
		List<Float> f14List = new ArrayList<Float>();
		List<Float> f15List = new ArrayList<Float>();
		for (HrCellDcrtQos vRpHrCellDcrtQos : hrCellDcrtQosList) {
			categories.add(Integer.toString(vRpHrCellDcrtQos.getHour())+":00");
			f4List.add(vRpHrCellDcrtQos.getF4());
			f5List.add(vRpHrCellDcrtQos.getF5());
			f6List.add(vRpHrCellDcrtQos.getF6());
			f7List.add(vRpHrCellDcrtQos.getF7());
			f8List.add(vRpHrCellDcrtQos.getF8());
			f9List.add(vRpHrCellDcrtQos.getF9());
			f10List.add(vRpHrCellDcrtQos.getF10());
			f11List.add(vRpHrCellDcrtQos.getF11());
			f12List.add(vRpHrCellDcrtQos.getF12());
			f13List.add(vRpHrCellDcrtQos.getF13());
			f14List.add(vRpHrCellDcrtQos.getF14());
			f15List.add(vRpHrCellDcrtQos.getF15());
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

		return new ModelAndView("hrCellTchDrp");
	}
}
