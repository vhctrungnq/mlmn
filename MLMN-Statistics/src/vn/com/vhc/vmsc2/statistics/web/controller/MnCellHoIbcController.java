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
import vn.com.vhc.vmsc2.statistics.dao.VRpMnCellHoBhIbcDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnCellHoIbcDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.Cell;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnCellHoBhIbc;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnCellHoIbc;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
public class MnCellHoIbcController extends BaseController{
	@Autowired
	private VRpMnCellHoIbcDAO vRpMnCellHoIbcDao;
	@Autowired
	private VRpMnCellHoBhIbcDAO vRpMnCellHoBhIbcDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private CellDAO cellDao;
	
	@RequestMapping("/report/radio/ibc/cell-ho/mn/list")
	public ModelAndView dyCellHoList(@RequestParam(required = false) String bscid, @RequestParam(required = false) String cellid, @RequestParam(required = false) Integer month, @RequestParam(required = false) Integer year, ModelMap model,
			HttpServletRequest request) {
		
		Calendar cal = Calendar.getInstance();
		if (month == null)
		{
			cal.add(Calendar.MONTH,-1);
			month = cal.get(Calendar.MONTH)+1; 
			if (year == null)
				year = cal.get(Calendar.YEAR);
		}
		List<VRpMnCellHoIbc> vRpMnCellHoIbc = vRpMnCellHoIbcDao.filter(bscid, cellid, month, year);

		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		model.addAttribute("vRpMnCellHoIbc", vRpMnCellHoIbc);
		return new ModelAndView("mnCellHoIbcList");
	}
	
	@RequestMapping("/report/radio/ibc/cell-ho/mn/details")
	public String showReport(@RequestParam(required=true) String bscid, @RequestParam(required = true) String cellid,
			@RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		if(endMonth==null){
			cal.add(Calendar.MONTH,-1);
			endMonth = cal.get(Calendar.MONTH)+1;
			endYear = cal.get(Calendar.YEAR);
		}
		
		if (startMonth == null) {
			if (endMonth > 3) {
				startMonth = endMonth -3;
				startYear = endYear;
			}
			else {
				cal1.add(Calendar.MONTH,-3);
				startMonth = cal1.get(Calendar.MONTH)+1;
				startYear = cal1.get(Calendar.YEAR);
			}
		}
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		
		List<VRpMnCellHoIbc> vRpMnCellHoIbcDetails = vRpMnCellHoIbcDao.filterDetails(startMonth.toString(), startYear.toString(), endMonth.toString(), endYear.toString(), bscid, cellid);
		
		model.addAttribute("vRpMnCellHoIbcDetails", vRpMnCellHoIbcDetails);
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
		
		for (VRpMnCellHoIbc vRpMnCellHoIbc : vRpMnCellHoIbcDetails) {
			categories.add(vRpMnCellHoIbc.getMonth()+"/"+vRpMnCellHoIbc.getYear());
			inHoSucrList.add(vRpMnCellHoIbc.getInHoSucr());
			ogHoSucrList.add(vRpMnCellHoIbc.getOgHoSucr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("In_Ho_Sucr", inHoSucrList);
		series.put("Og_Ho_Sucr", ogHoSucrList);

		model.addAttribute("chart", Chart.basicLine("CELL IBC HO " + cellid + " Monthly Report", categories, series));

		// checkBox
		String[] checkColumns = { "ogHoAtt", "ogHoSucr", "inHoAtt", "inHoSucr"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

		String[] checkSeries = {"ogHoSucr", "inHoSucr"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		
	return "mnCellHoIbcDetails";
	}
	
	@RequestMapping("/report/radio/ibc/cell-ho/mn/bhDetails")
	public String showReportBhDetails(@RequestParam(required=true) String bscid, @RequestParam(required = true) String cellid,
			@RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		if(endMonth==null){
			cal.add(Calendar.MONTH,-1);
			endMonth = cal.get(Calendar.MONTH)+1;
			endYear = cal.get(Calendar.YEAR);
		}
		
		if (startMonth == null) {
			if (endMonth > 3) {
				startMonth = endMonth -3;
				startYear = endYear;
			}
			else {
				cal1.add(Calendar.MONTH,-3);
				startMonth = cal1.get(Calendar.MONTH)+1;
				startYear = cal1.get(Calendar.YEAR);
			}
		}
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", startYear);
		
		List<VRpMnCellHoBhIbc> vRpMnCellHoBhIbcDetails = vRpMnCellHoBhIbcDao.filterDetails(startMonth.toString(), startYear.toString(), endMonth.toString(), endYear.toString(), bscid, cellid);
		
		model.addAttribute("vRpMnCellHoBhIbcDetails", vRpMnCellHoBhIbcDetails);
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
		
		for (VRpMnCellHoBhIbc vRpMnCellHoBhIbc : vRpMnCellHoBhIbcDetails) {
			categories.add(vRpMnCellHoBhIbc.getMonth()+"/"+vRpMnCellHoBhIbc.getYear());
			inHoSucrList.add(vRpMnCellHoBhIbc.getBhInHoSucr());
			ogHoSucrList.add(vRpMnCellHoBhIbc.getBhOgHoSucr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("Bh_In_Ho_Sucr", inHoSucrList);
		series.put("Bh_Og_Ho_Sucr", ogHoSucrList);

		model.addAttribute("chart", Chart.basicLine("CELL IBC HO BH " + cellid + " Monthly Report", categories, series));

		// checkBox
		String[] checkColumns = { "bhOgHoAtt","bhOgHoSucr","bhInHoAtt","bhInHoSucr"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

		String[] checkSeries = {"bhOgHoSucr", "bhInHoSucr"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		
	return "mnCellHoBhIbcDetails";
	}
}
