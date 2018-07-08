package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.CellDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkCellToCellQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.Cell;
import vn.com.vhc.vmsc2.statistics.domain.WkCellToCellQos;



@Controller
public class WkCellToCellHoController extends BaseController {
	@Autowired
	private WkCellToCellQosDAO wkCellToCellQosDao;
	@Autowired
	private CellDAO cellDao;
	@Autowired
	private BscDAO bscDao;

	@RequestMapping("/report/radio/cell-to-cells/wk/list")
	public ModelAndView wkCellToCellsList(@RequestParam(required = false) String bscid, @RequestParam(required = false) String fromCell,
			@RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {

		DateTime dt = new DateTime();
		if (endWeek == null) {
			dt = dt.minusWeeks(1);
			endWeek = dt.getWeekOfWeekyear();
			endYear = dt.getYear();
		}
		if (startWeek == null) {
			startWeek = endWeek;
			startYear = endYear;
		}
		model.addAttribute("startWeek", startWeek);
		model.addAttribute("endWeek", endWeek);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);

		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);
		model.addAttribute("bscid", bscid);
		model.addAttribute("fromCell", fromCell);
	
		List<WkCellToCellQos> wkCellToCellsQos = wkCellToCellQosDao.filterCellToCells(bscid, fromCell, startWeek, startYear, endWeek, endYear);

		model.addAttribute("wkCellToCellsQos", wkCellToCellsQos);

		return new ModelAndView("wkCellToCellsList");
	}

	@RequestMapping("/report/radio/cells-to-cell/wk/list")
	public ModelAndView wkCellsToCellList(@RequestParam(required = false) String bscid, @RequestParam(required = false) String toCell,
			@RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {

		DateTime dt = new DateTime();
		if (endWeek == null) {
			dt = dt.minusWeeks(1);
			endWeek = dt.getWeekOfWeekyear();
			endYear = dt.getYear();
		}
		if (startWeek == null) {
			startWeek = endWeek;
			startYear = endYear;
		}
		model.addAttribute("startWeek", startWeek);
		model.addAttribute("endWeek", endWeek);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);

		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);
		model.addAttribute("bscid", bscid);
		model.addAttribute("toCell", toCell);

		List<WkCellToCellQos> wkCellsToCellQos = wkCellToCellQosDao.filterCellsToCell(bscid, toCell, startWeek, startYear, endWeek, endYear);

		model.addAttribute("wkCellsToCellQos", wkCellsToCellQos);

		return new ModelAndView("wkCellsToCellList");
	}
}
