package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/*import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.CellDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnCellToCellQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.Cell;
import vn.com.vhc.vmsc2.statistics.domain.MnCellToCellQos;



@Controller
public class MnCellToCellHoController extends BaseController {
	@Autowired
	private MnCellToCellQosDAO mnCellToCellQosDAO;
	@Autowired
	private CellDAO cellDao;
	@Autowired
	private BscDAO bscDao;

	@RequestMapping("/report/radio/cell-to-cells/mn/list")
	public ModelAndView mnCellToCellsList(@RequestParam(required = false) String bscid, @RequestParam(required = false) String fromCell,
			@RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {

		Calendar cal = Calendar.getInstance();
		/*Calendar cal1 = Calendar.getInstance();*/
		if (endMonth == null) {
			cal.add(Calendar.MONTH, -1);
			endMonth = cal.get(Calendar.MONTH) + 1;
			endYear = cal.get(Calendar.YEAR);
		}

		if (startMonth == null) {
			startMonth = endMonth;
			startYear = endYear;
		}

		/*int startRecord = 0;
		int order = 1;
		String column = "YEAR,MONTH,BSCID, TO_CELL";
		try {
			startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("mnCellToCells").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
			order = Integer.parseInt(request.getParameter((new ParamEncoder("mnCellToCells").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("mnCellToCells").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		int endRecord = startRecord + 100;*/

//		List<MnCellToCellQos> mnCellToCellsQos = mnCellToCellQosDAO.filterCellToCells(bscid, fromCell, startMonth, startYear, endMonth, endYear, startRecord,
//				endRecord, column, order == 1 ? "ASC" : "DESC");
		
		List<MnCellToCellQos> mnCellToCellsQos = mnCellToCellQosDAO.filterCellToCells(bscid, fromCell, startMonth, startYear, endMonth, endYear);

//		Integer totalSize = mnCellToCellQosDAO.countFromCell(bscid, fromCell, startMonth, startYear, endMonth, endYear);
//		model.addAttribute("totalSize", totalSize);

		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);

		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);
		model.addAttribute("bscid", bscid);
		model.addAttribute("fromCell", fromCell);
		model.addAttribute("mnCellToCellsQos", mnCellToCellsQos);
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		model.addAttribute("monthList", monthList);

		return new ModelAndView("mnCellToCellsList");
	}

	@RequestMapping("/report/radio/cells-to-cell/mn/list")
	public ModelAndView mnCellsToCellList(@RequestParam(required = false) String bscid, @RequestParam(required = false) String toCell,
			@RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {

		Calendar cal = Calendar.getInstance();
		if (endMonth == null) {
			cal.add(Calendar.MONTH, -1);
			endMonth = cal.get(Calendar.MONTH) + 1;
			endYear = cal.get(Calendar.YEAR);
		}

		if (startMonth == null) {
				startMonth = endMonth;
				startYear = endYear;
		}

		/*int startRecord = 0;
		int order = 1;
		String column = "YEAR,MONTH,BSCID, FROM_CELL";
		try {
			startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("mnCellsToCell").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
			order = Integer.parseInt(request.getParameter((new ParamEncoder("mnCellsToCell").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("mnCellsToCell").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		int endRecord = startRecord + 100;*/

//		List<MnCellToCellQos> mnCellToCellsQos = mnCellToCellQosDAO.filterCellsToCell(bscid, toCell, startMonth, startYear, endMonth, endYear, startRecord,
//				endRecord, column, order == 1 ? "ASC" : "DESC");
		
		List<MnCellToCellQos> mnCellToCellsQos = mnCellToCellQosDAO.filterCellsToCell(bscid, toCell, startMonth, startYear, endMonth, endYear);

//		Integer totalSize = mnCellToCellQosDAO.countToCell(bscid, toCell, startMonth, startYear, endMonth, endYear);
//		model.addAttribute("totalSize", totalSize);

		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);

		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);
		model.addAttribute("bscid", bscid);
		model.addAttribute("toCell", toCell);
		model.addAttribute("mnCellToCellsQos", mnCellToCellsQos);
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		model.addAttribute("monthList", monthList);

		return new ModelAndView("mnCellToCellsList");
	}
}
