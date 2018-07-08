package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.CellDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnCellHoBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnCellHoDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.Cell;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnCellHo;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnCellHoBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
public class MnCellHoController extends BaseController {
	@Autowired
	private VRpMnCellHoDAO vRpMnCellHoDao;
	@Autowired
	private VRpMnCellHoBhDAO vRpMnCellHoBhDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private CellDAO cellDao;

	@RequestMapping("/report/radio/cell-ho/mn/list")
	public ModelAndView showReportList(@RequestParam(required = false) String bscid, @RequestParam(required = false) String cellid,
			@RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {

		Calendar cal = Calendar.getInstance();
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
		{
			endYear = calender.get(Calendar.YEAR);
		}
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
		String column = "YEAR, MONTH, REGION, BSCID, CELLID";
		try {
			startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("vRpMnCellHo").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
			order = Integer.parseInt(request.getParameter((new ParamEncoder("vRpMnCellHo").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("vRpMnCellHo").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		int endRecord = startRecord + 100;
		*/

		/*List<VRpMnCellHo> vRpMnCellHo = vRpMnCellHoDao.filter(bscid, cellid, month, year, startRecord, endRecord, column, order == 1 ? "ASC" : "DESC");
		Integer totalSize = vRpMnCellHoDao.countRow(bscid, cellid, month, year);
		model.addAttribute("totalSize", totalSize);*/
		
		List<VRpMnCellHo> vRpMnCellHo = vRpMnCellHoDao.filterDetails(startMonth.toString(), startYear.toString(), endMonth.toString(),
				endYear.toString(), bscid, cellid);

		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		model.addAttribute("vRpMnCellHo", vRpMnCellHo);
		
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
	    model.addAttribute("monthList", monthList);

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpMnCellHo"));

		return new ModelAndView("mnCellHoList");
	}

	@RequestMapping("/report/radio/cell-ho/mn/details")
	public String showReport(@RequestParam(required = true) String bscid, @RequestParam(required = true) String cellid,
			@RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {

		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
		{
			endYear = calender.get(Calendar.YEAR);
		}
		if (endMonth == null) {
			cal.add(Calendar.MONTH, -1);
			endMonth = cal.get(Calendar.MONTH) + 1;
			endYear = cal.get(Calendar.YEAR);
		}

		if (startMonth == null) {
			if (endMonth > 3) {
				startMonth = endMonth - 3;
				startYear = endYear;
			} else {
				cal1.add(Calendar.MONTH, -3);
				startMonth = cal1.get(Calendar.MONTH) + 1;
				startYear = cal1.get(Calendar.YEAR);
			}
		}
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);

		List<VRpMnCellHo> vRpMnCellHoDetails = vRpMnCellHoDao.filterDetails(startMonth.toString(), startYear.toString(), endMonth.toString(),
				endYear.toString(), bscid, cellid);

		model.addAttribute("vRpMnCellHoDetails", vRpMnCellHoDetails);
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

		for (VRpMnCellHo vRpMnCellHo : vRpMnCellHoDetails) {
			categories.add(vRpMnCellHo.getMonth() + "/" + vRpMnCellHo.getYear());
			inHoSucrList.add(vRpMnCellHo.getInHoSucr());
			ogHoSucrList.add(vRpMnCellHo.getOgHoSucr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("In_Ho_Sucr", inHoSucrList);
		series.put("Og_Ho_Sucr", ogHoSucrList);

		model.addAttribute("chart", Chart.basicLine("CELL HO " + cellid + " Monthly Report", categories, series));

		// checkBox
		String[] checkColumns = { "HO_ATT", "HO_SUCR", "INTRA_HO_ATT", "INTRA_HO_SUCR", "OG_HO_ATT", "OG_HO_SUCR", "IN_HO_ATT", "IN_HO_SUCR", "HO_REVERSION", "HO_LOST"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpMnCellHoDetail", 5));

		String[] checkSeries = { "ogHoSucr", "inHoSucr" };
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpMnCellHoDetail"));
		
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
	    model.addAttribute("monthList", monthList);

		return "mnCellHoDetails";
	}

	@RequestMapping("/report/radio/cell-ho/mn/bhDetails")
	public String showReportBhDetails(@RequestParam(required = true) String bscid, @RequestParam(required = true) String cellid,
			@RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {

		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
		{
			endYear = calender.get(Calendar.YEAR);
		}
		if (endMonth == null) {
			cal.add(Calendar.MONTH, -1);
			endMonth = cal.get(Calendar.MONTH) + 1;
			endYear = cal.get(Calendar.YEAR);
		}

		if (startMonth == null) {
			if (endMonth > 3) {
				startMonth = endMonth - 3;
				startYear = endYear;
			} else {
				cal1.add(Calendar.MONTH, -3);
				startMonth = cal1.get(Calendar.MONTH) + 1;
				startYear = cal1.get(Calendar.YEAR);
			}
		}
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", startYear);

		List<VRpMnCellHoBh> vRpMnCellHoBhDetails = vRpMnCellHoBhDao.filterDetails(startMonth.toString(), startYear.toString(), endMonth.toString(),
				endYear.toString(), bscid, cellid);

		model.addAttribute("vRpMnCellHoBhDetails", vRpMnCellHoBhDetails);
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

		for (VRpMnCellHoBh vRpMnCellHoBh : vRpMnCellHoBhDetails) {
			categories.add(vRpMnCellHoBh.getMonth() + "/" + vRpMnCellHoBh.getYear());
			inHoSucrList.add(vRpMnCellHoBh.getBhInHoSucr());
			ogHoSucrList.add(vRpMnCellHoBh.getBhOgHoSucr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("Bh_In_Ho_Sucr", inHoSucrList);
		series.put("Bh_Og_Ho_Sucr", ogHoSucrList);

		model.addAttribute("chart", Chart.basicLine("CELL HO BH " + cellid + " Monthly Report", categories, series));
		
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
	    model.addAttribute("monthList", monthList);
	    
		// checkBox
		String[] checkColumns = { "bhOgHoAtt", "bhOgHoSucr", "bhInHoAtt", "bhInHoSucr" };
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

		String[] checkSeries = { "bhOgHoSucr", "bhInHoSucr" };
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));

		return "mnCellHoBhDetails";
	}
}
