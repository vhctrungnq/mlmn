package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import vn.com.vhc.vmsc2.statistics.dao.DyCellToCellQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.Cell;
import vn.com.vhc.vmsc2.statistics.domain.DyCellToCellQos;

@Controller
public class DyCellToCellHoController extends BaseController {
	@Autowired
	private DyCellToCellQosDAO dyCellToCellQosDAO;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private CellDAO cellDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("/report/radio/cell-to-cells/dy/list")
	public ModelAndView dyCellToCellsList(@RequestParam(required = false) String bscid, @RequestParam(required = false) String fromCell,
			@RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, ModelMap model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}

		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));

		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);
		model.addAttribute("bscid", bscid);
		model.addAttribute("fromCell", fromCell);

		/*int startRecord = 0;
		int order = 1;
		String column = "DAY, BSCID, TO_CELL";
		try {
			startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("dyCellToCells").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
			order = Integer.parseInt(request.getParameter((new ParamEncoder("dyCellToCells").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("dyCellToCells").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		int endRecord = startRecord + 100;*/

		//List<DyCellToCellQos> dyCellToCellsQos = dyCellToCellQosDAO.filterCellToCells(bscid, fromCell, startDate, endDate, startRecord, endRecord, column,
		//		order == 1 ? "ASC" : "DESC");
		
		List<DyCellToCellQos> dyCellToCellsQos = dyCellToCellQosDAO.filterCellToCells(bscid, fromCell, startDate, endDate);

		model.addAttribute("dyCellToCellsQos", dyCellToCellsQos);
		
		Integer totalSize = dyCellToCellQosDAO.countFromCell(bscid, fromCell, startDate, endDate);
		model.addAttribute("totalSize", totalSize);
		
		return new ModelAndView("dyCellToCellsList");
	}

	@RequestMapping("/report/radio/cells-to-cell/dy/list")
	public ModelAndView dyCellsToCellList(@RequestParam(required = false) String bscid, @RequestParam(required = false) String toCell,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();
		try {
			Date startDay;
			Date endDay;
			if (endDate == null) {
				endDay = new Date(currentTime - 24 * 60 * 60 * 1000);
			} else {
				endDay = df.parse(endDate);
			}
			if (startDate == null) {
				startDay = endDay;
			} else {
				startDay = df.parse(startDate);
			}
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("endDate", df.format(endDay));
			model.addAttribute("bscid", bscid);
			model.addAttribute("toCell", toCell);
			List<Bsc> bscList = bscDao.getAll();
			model.addAttribute("bscList", bscList);
			List<Cell> cellList = cellDao.getCellOfBsc(bscid);
			model.addAttribute("cellList", cellList);

			/*int startRecord = 0;
			int order = 1;
			String column = "DAY, BSCID, FROM_CELL";
			try {
				startRecord = (Integer
						.parseInt(request.getParameter((new ParamEncoder("dyCellsToCell").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
				order = Integer.parseInt(request.getParameter((new ParamEncoder("dyCellsToCell").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
				column = request.getParameter((new ParamEncoder("dyCellsToCell").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
			} catch (NumberFormatException e) {
			}
			int endRecord = startRecord + 100;*/

//			List<DyCellToCellQos> dyCellsToCellQos = dyCellToCellQosDAO.filterCellsToCell(bscid, toCell, df.format(startDay).toString(), df.format(endDay)
//					.toString(), startRecord, endRecord, column, order == 1 ? "ASC" : "DESC");
			
			List<DyCellToCellQos> dyCellsToCellQos = dyCellToCellQosDAO.filterCellsToCell(bscid, toCell, df.format(startDay).toString(), df.format(endDay).toString());

			model.addAttribute("dyCellsToCellQos", dyCellsToCellQos);

//			Integer totalSize = dyCellToCellQosDAO.countToCell(bscid, toCell, startDate, endDate);
//			model.addAttribute("totalSize", totalSize);
			
		} catch (ParseException e) {
			saveError(request, e.toString());
		}
		return new ModelAndView("dyCellsToCellList");
	}
}
