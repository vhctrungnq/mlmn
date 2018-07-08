package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.Bsc3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.Cell3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellToCellQos3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrCellToCellQos3GDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc3G;
import vn.com.vhc.vmsc2.statistics.domain.Cell3G;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellToCellQos3G;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrCellToCellQos3G;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;

@Controller
@RequestMapping("/report/radio3g/cell/ho-cell-to-cell/*")
public class CellHoCellToCellDetails3gController extends BaseController{
	@Autowired
	private VRpDyCellToCellQos3GDAO vRpDyCellToCellQos3GDao;
	@Autowired
	private VRpHrCellToCellQos3GDAO vRpHrCellToCellQos3GDao;
	@Autowired
	private Cell3GDAO cell3GDao;
	@Autowired
	private Bsc3GDAO bsc3GDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("dyCellToCells")
	public ModelAndView dyCellToCellsList(@RequestParam(required = false) String cellid,@RequestParam(required = false) String bscid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model) {
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		List<VRpDyCellToCellQos3G> vRpDyCellToCellQos3G = vRpDyCellToCellQos3GDao.filterCellToCells(df.format(startDate), df.format(endDate),bscid, cellid);
		
		model.addAttribute("cellid", cellid);
		List<Bsc3G> bscList = bsc3GDao.getAll();
		model.addAttribute("bscid", bscid);
		model.addAttribute("bscList", bscList);
		List<Cell3G> cellList = cell3GDao.getCellOfBsc3G(bscid);
		model.addAttribute("cellList", cellList);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("vRpDyCellToCellQos3G", vRpDyCellToCellQos3G);

		return new ModelAndView("dyCellToCellsQos3G");
	}
	
	@RequestMapping("dyCellsToCell")
	public ModelAndView dyCellsToCellList(@RequestParam(required = false) String cellid,@RequestParam(required = false) String bscid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model) {
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		List<VRpDyCellToCellQos3G> vRpDyCellToCellQos3G = vRpDyCellToCellQos3GDao.filterCellsToCell(df.format(startDate), df.format(endDate),bscid, cellid);
		
		model.addAttribute("cellid", cellid);
		List<Bsc3G> bscList = bsc3GDao.getAll();
		model.addAttribute("bscid", bscid);
		model.addAttribute("bscList", bscList);
		List<Cell3G> cellList = cell3GDao.getCellOfBsc3G(bscid);
		model.addAttribute("cellList", cellList);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("vRpDyCellToCellQos3G", vRpDyCellToCellQos3G);

		return new ModelAndView("dyCellsToCellQos3G");
	}
	
	@RequestMapping("hrCellToCells")
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
		List<VRpHrCellToCellQos3G> vRpHrCellToCellQos3G = new ArrayList<VRpHrCellToCellQos3G>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			vRpHrCellToCellQos3G.addAll(vRpHrCellToCellQos3GDao.filterCellToCells(dates.get(day).get("startHour"), dates.get(day).get("endHour"), day, bscid, cellid));
		}
		model.addAttribute("vRpHrCellToCellQos3G", vRpHrCellToCellQos3G);
		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		List<Bsc3G> bscList = bsc3GDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell3G> cellList = cell3GDao.getCellOfBsc3G(bscid);
		model.addAttribute("cellList", cellList);

		return new ModelAndView("hrCellToCellsQos3G");
	}
	
	@RequestMapping("hrCellsToCell")
	public ModelAndView hrCellsToCellList(@RequestParam(required = true) String cellid,@RequestParam(required = false) String bscid, @RequestParam(required = false) String startHour,
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
		List<VRpHrCellToCellQos3G> vRpHrCellToCellQos3G = new ArrayList<VRpHrCellToCellQos3G>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			vRpHrCellToCellQos3G.addAll(vRpHrCellToCellQos3GDao.filterCellsToCell(dates.get(day).get("startHour"), dates.get(day).get("endHour"), day, bscid, cellid));
		}
		model.addAttribute("vRpHrCellToCellQos3G", vRpHrCellToCellQos3G);
		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		List<Bsc3G> bscList = bsc3GDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell3G> cellList = cell3GDao.getCellOfBsc3G(bscid);
		model.addAttribute("cellList", cellList);

		return new ModelAndView("hrCellsToCellQos3G");
	}
}
