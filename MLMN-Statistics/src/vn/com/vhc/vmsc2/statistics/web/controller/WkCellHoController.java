package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkCellHoBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkCellHoDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.Cell;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkCellHo;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkCellHoBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;

@Controller
public class WkCellHoController extends BaseController {
	@Autowired
	private VRpWkCellHoDAO vRpWkCellHoDao;
	@Autowired
	private VRpWkCellHoBhDAO vRpWkCellHoBhDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private CellDAO cellDao;

	@RequestMapping("/report/radio/cell-ho/wk/list")
	public ModelAndView showReportList(@RequestParam(required = false) String bscid, @RequestParam(required = false) String cellid,
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

		/*int startRecord = 0;
		int order = 1;
		String column = "YEAR, WEEK, REGION, BSCID, CELLID";
		try {
			startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("vRpWkCellHo").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
			order = Integer.parseInt(request.getParameter((new ParamEncoder("vRpWkCellHo").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("vRpWkCellHo").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		int endRecord = startRecord + 100;*/

		/*List<VRpWkCellHo> vRpWkCellHo = vRpWkCellHoDao.filter(bscid, cellid, week, year, startRecord, endRecord, column, order == 1 ? "ASC" : "DESC");
		Integer totalSize = vRpWkCellHoDao.countRow(bscid, cellid, week, year);
		model.addAttribute("totalSize", totalSize);*/

		List<VRpWkCellHo> vRpWkCellHo = vRpWkCellHoDao.filterDetails(startWeek.toString(), startYear.toString(), endWeek.toString(), endYear.toString(),
				bscid, cellid);
		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);
		model.addAttribute("vRpWkCellHo", vRpWkCellHo);

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpWkCellHo"));

		return new ModelAndView("wkCellHoList");
	}

	@RequestMapping("/report/radio/cell-ho/wk/details")
	public String showReport(@RequestParam(required = true) String bscid, @RequestParam(required = true) String cellid,
			@RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {

		DateTime dt = new DateTime();
		if (endWeek == null) {
			dt = dt.minusWeeks(1);
			endWeek = dt.getWeekOfWeekyear();
			endYear = dt.getYear();
		}
		if (startWeek == null) {
			dt = dt.minusWeeks(5);
			startWeek = dt.getWeekOfWeekyear();
			startYear = dt.getYear();
		}
		model.addAttribute("startWeek", startWeek);
		model.addAttribute("endWeek", endWeek);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);

		List<VRpWkCellHo> vRpWkCellHoDetails = vRpWkCellHoDao.filterDetails(startWeek.toString(), startYear.toString(), endWeek.toString(), endYear.toString(),
				bscid, cellid);

		model.addAttribute("vRpWkCellHoDetails", vRpWkCellHoDetails);
		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);

		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> inHoSucrList = new ArrayList<Float>();
		List<Float> ogHoSucrList = new ArrayList<Float>();

		for (VRpWkCellHo vRpWkCellHo : vRpWkCellHoDetails) {
			categories.add(vRpWkCellHo.getWeek() + "/" + vRpWkCellHo.getYear());
			inHoSucrList.add(vRpWkCellHo.getInHoSucr());
			ogHoSucrList.add(vRpWkCellHo.getOgHoSucr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("In_Ho_Sucr", inHoSucrList);
		series.put("Og_Ho_Sucr", ogHoSucrList);

		model.addAttribute("chart", Chart.basicLine("CELL HO " + cellid + " Weekly Report", categories, series));

		// checkBox
		String[] checkColumns = { "ogHoAtt", "ogHoSucr", "inHoAtt", "inHoSucr" };
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

		String[] checkSeries = { "ogHoSucr", "inHoSucr" };
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpWkCellHoDetail"));

		return "wkCellHoDetails";
	}

	@RequestMapping("/report/radio/cell-ho/wk/bhDetails")
	public String showReportBhDetails(@RequestParam(required = true) String bscid, @RequestParam(required = true) String cellid,
			@RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {

		DateTime dt = new DateTime();
		if (endWeek == null) {
			dt = dt.minusWeeks(1);
			endWeek = dt.getWeekOfWeekyear();
			endYear = dt.getYear();
		}
		if (startWeek == null) {
			dt = dt.minusWeeks(5);
			startWeek = dt.getWeekOfWeekyear();
			startYear = dt.getYear();
		}
		model.addAttribute("startWeek", startWeek);
		model.addAttribute("endWeek", endWeek);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);

		List<VRpWkCellHoBh> vRpWkCellHoBhDetails = vRpWkCellHoBhDao.filterDetails(startWeek.toString(), startYear.toString(), endWeek.toString(),
				endYear.toString(), bscid, cellid);

		model.addAttribute("vRpWkCellHoBhDetails", vRpWkCellHoBhDetails);
		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);

		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> inHoSucrList = new ArrayList<Float>();
		List<Float> ogHoSucrList = new ArrayList<Float>();

		for (VRpWkCellHoBh vRpWkCellHoBh : vRpWkCellHoBhDetails) {
			categories.add(vRpWkCellHoBh.getWeek() + "/" + vRpWkCellHoBh.getYear());
			inHoSucrList.add(vRpWkCellHoBh.getBhInHoSucr());
			ogHoSucrList.add(vRpWkCellHoBh.getBhOgHoSucr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("Bh_In_Ho_Sucr", inHoSucrList);
		series.put("Bh_Og_Ho_Sucr", ogHoSucrList);

		model.addAttribute("chart", Chart.basicLine("CELL HO BH " + cellid + " Weekly Report", categories, series));

		// checkBox
		String[] checkColumns = { "bhOgHoAtt", "bhOgHoSucr", "bhInHoAtt", "bhInHoSucr" };
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

		String[] checkSeries = { "bhOgHoSucr", "bhInHoSucr" };
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));

		return "wkCellHoBhDetails";
	}
}