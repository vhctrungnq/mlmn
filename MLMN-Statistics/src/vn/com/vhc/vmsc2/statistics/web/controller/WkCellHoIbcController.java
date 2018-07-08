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
import vn.com.vhc.vmsc2.statistics.dao.VRpWkCellHoBhIbcDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkCellHoIbcDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.Cell;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkCellHoBhIbc;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkCellHoIbc;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
public class WkCellHoIbcController extends BaseController{
	@Autowired
	private VRpWkCellHoIbcDAO vRpWkCellHoIbcDao;
	@Autowired
	private VRpWkCellHoBhIbcDAO vRpWkCellHoBhIbcDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private CellDAO cellDao;
	
	@RequestMapping("/report/radio/ibc/cell-ho/wk/list")
	public ModelAndView dyCellHoList(@RequestParam(required = false) String bscid, @RequestParam(required = false) String cellid, @RequestParam(required = false) Integer week, @RequestParam(required = false) Integer year, ModelMap model,
			HttpServletRequest request) {
		
		DateTime dt = new DateTime();
		dt = dt.minusWeeks(1);

		if (week == null) {
			week = dt.getWeekOfWeekyear();
		}
		
		if(year == null)
			year = dt.getYear();
		
		List<VRpWkCellHoIbc> vRpWkCellHoIbc = vRpWkCellHoIbcDao.filter(bscid, cellid, week, year);

		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);
		model.addAttribute("week", week);
		model.addAttribute("year", year);
		model.addAttribute("vRpWkCellHoIbc", vRpWkCellHoIbc);
		return new ModelAndView("wkCellHoIbcList");
	}
	
	@RequestMapping("/report/radio/ibc/cell-ho/wk/details")
	public String showReport(@RequestParam(required=true) String bscid, @RequestParam(required = true) String cellid,
			@RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		
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
		
		List<VRpWkCellHoIbc> vRpWkCellHoIbcDetails = vRpWkCellHoIbcDao.filterDetails(startWeek.toString(), startYear.toString(), endWeek.toString(), endYear.toString(), bscid, cellid);
		
		model.addAttribute("vRpWkCellHoIbcDetails", vRpWkCellHoIbcDetails);
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
		
		for (VRpWkCellHoIbc vRpWkCellHoIbc : vRpWkCellHoIbcDetails) {
			categories.add(vRpWkCellHoIbc.getWeek()+"/"+vRpWkCellHoIbc.getYear());
			inHoSucrList.add(vRpWkCellHoIbc.getInHoSucr());
			ogHoSucrList.add(vRpWkCellHoIbc.getOgHoSucr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("In_Ho_Sucr", inHoSucrList);
		series.put("Og_Ho_Sucr", ogHoSucrList);

		model.addAttribute("chart", Chart.basicLine("CELL IBC HO " + cellid + " Weekly Report", categories, series));

		// checkBox
		String[] checkColumns = { "ogHoAtt", "ogHoSucr", "inHoAtt", "inHoSucr"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

		String[] checkSeries = {"ogHoSucr", "inHoSucr"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		
	return "wkCellHoIbcDetails";
	}
	
	@RequestMapping("/report/radio/ibc/cell-ho/wk/bhDetails")
	public String showReportBhDetails(@RequestParam(required=true) String bscid, @RequestParam(required = true) String cellid,
			@RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		
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
		
		List<VRpWkCellHoBhIbc> vRpWkCellHoBhIbcDetails = vRpWkCellHoBhIbcDao.filterDetails(startWeek.toString(), startYear.toString(), endWeek.toString(), endYear.toString(), bscid, cellid);
		
		model.addAttribute("vRpWkCellHoBhIbcDetails", vRpWkCellHoBhIbcDetails);
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
		
		for (VRpWkCellHoBhIbc vRpWkCellHoBhIbc : vRpWkCellHoBhIbcDetails) {
			categories.add(vRpWkCellHoBhIbc.getWeek()+"/"+vRpWkCellHoBhIbc.getYear());
			inHoSucrList.add(vRpWkCellHoBhIbc.getBhInHoSucr());
			ogHoSucrList.add(vRpWkCellHoBhIbc.getBhOgHoSucr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("Bh_In_Ho_Sucr", inHoSucrList);
		series.put("Bh_Og_Ho_Sucr", ogHoSucrList);

		model.addAttribute("chart", Chart.basicLine("CELL IBC HO BH " + cellid + " Weekly Report", categories, series));

		// checkBox
		String[] checkColumns = { "bhOgHoAtt","bhOgHoSucr","bhInHoAtt","bhInHoSucr"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

		String[] checkSeries = {"bhOgHoSucr", "bhInHoSucr"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		
	return "wkCellHoBhIbcDetails";
	}
}
