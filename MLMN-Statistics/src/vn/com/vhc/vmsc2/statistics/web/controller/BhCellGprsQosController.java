package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellGprsQosBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnCellGprsQosBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkCellGprsQosBhDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.Cell;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellGprsQosBh;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnCellGprsQosBh;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkCellGprsQosBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
public class BhCellGprsQosController extends BaseController {
	@Autowired
	private VRpDyCellGprsQosBhDAO vRpDyCellGprsQosBhDao;
	@Autowired
	private VRpWkCellGprsQosBhDAO vRpWkCellGprsQosBhDao;
	@Autowired
	private VRpMnCellGprsQosBhDAO vRpMnCellGprsQosBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private CellDAO cellDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("/report/radio/cell-gprs-qos/dy/bh")
	public ModelAndView dyList(@RequestParam(required = true) String cellid, @RequestParam(required = true) String bscid,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();
		try {
			Date startDay, endDay;
			if (endDate == null) {
				endDay = new Date(currentTime - 24 * 60 * 60 * 1000);
			} else {
				endDay = df.parse(endDate);
			}
			if (startDate == null) {
				startDay = new Date(endDay.getTime() + 25 * 24 * 60 * 60 * 1000);
			} else {
				startDay = df.parse(startDate);
			}
			List<VRpDyCellGprsQosBh> vRpDyCellGprsQosBh = vRpDyCellGprsQosBhDao.filter(df.format(startDay), df.format(endDay), bscid, cellid);
			List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
			model.addAttribute("provinceList", provinceList);
			model.addAttribute("cellid", cellid);
			model.addAttribute("bscid", bscid);
			List<Bsc> bscList = bscDao.getAll();
			model.addAttribute("bscList", bscList);
			List<Cell> cellList = cellDao.getCellOfBsc(bscid);
			model.addAttribute("cellList", cellList);
			model.addAttribute("endDate", df.format(endDay));
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("vRpDyCellGprsQosBh", vRpDyCellGprsQosBh);
			//start chart
			List<String> categories = new ArrayList<String>();
			List<Float> bhDlTbfSucrList = new ArrayList<Float>();
			List<Float> bhUlTbfSucrList = new ArrayList<Float>();
			
			for (VRpDyCellGprsQosBh vRpDyCell : vRpDyCellGprsQosBh) {
				categories.add(df.format(vRpDyCell.getDay()));
				bhDlTbfSucrList.add(vRpDyCell.getBhDlTbfSucr());
				bhUlTbfSucrList.add(vRpDyCell.getBhUlTbfSucr());
			}
			
			Map<String, List<Float>> dlTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
			dlTbfSucrSeries.put("DL TBF SUCR (%)--80699b", bhDlTbfSucrList);
			model.addAttribute("dlTbfSucrChart", Chart.multiColumn("dlTbfSucrChart", "DL TBF SUCR (%)", categories, dlTbfSucrSeries));

			Map<String, List<Float>> ulTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
			ulTbfSucrSeries.put("UL TBF SUCR (%)--db843d", bhUlTbfSucrList);
			model.addAttribute("ulTbfSucrChart", Chart.multiColumn("ulTbfSucrChart", "UL TBF SUCR (%)", categories, ulTbfSucrSeries));

			// checkBox
			String[] checkColumns = { "DL_TBF_REQ", "DL_TBF_SUCR", "UL_TBF_REQ", "UL_TBF_SUCR", "GDL_TRAF", "GUL_TRAF", "EDL_TRAF","EUL_TRAF"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpDyCellGprsQosBh", 6));

		} catch (ParseException e) {
			saveError(request, e.toString());
		}
		return new ModelAndView("dyCellGprsQosBh");
	}

	@RequestMapping("/report/radio/cell-gprs-qos/mn/bh")
	public ModelAndView mnList(@RequestParam(required = true) String cellid, @RequestParam(required = true) String bscid,
			@RequestParam(required = false) Integer startMonth,@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear,
			ModelMap model, HttpServletRequest request) {
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

		List<VRpMnCellGprsQosBh> vRpMnCellGprsQosBh = vRpMnCellGprsQosBhDao.filter(startMonth,startYear, endMonth, endYear, bscid, cellid);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("vRpMnCellGprsQosBh", vRpMnCellGprsQosBh);
		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);
		//start chart
		List<String> categories = new ArrayList<String>();
		List<Float> bhDlTbfSucrList = new ArrayList<Float>();
		List<Float> bhUlTbfSucrList = new ArrayList<Float>();
		
		for (VRpMnCellGprsQosBh vRpMnCell : vRpMnCellGprsQosBh) {
			categories.add(Integer.toString(vRpMnCell.getMonth())+"/"+vRpMnCell.getYear());
			bhDlTbfSucrList.add(vRpMnCell.getBhDlTbfSucr());
			bhUlTbfSucrList.add(vRpMnCell.getBhUlTbfSucr());
		}
		
		Map<String, List<Float>> dlTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
		dlTbfSucrSeries.put("DL TBF SUCR (%)--80699b", bhDlTbfSucrList);
		model.addAttribute("dlTbfSucrChart", Chart.multiColumn("dlTbfSucrChart", "DL TBF SUCR (%)", categories, dlTbfSucrSeries));

		Map<String, List<Float>> ulTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
		ulTbfSucrSeries.put("UL TBF SUCR (%)--db843d", bhUlTbfSucrList);
		model.addAttribute("ulTbfSucrChart", Chart.multiColumn("ulTbfSucrChart", "UL TBF SUCR (%)", categories, ulTbfSucrSeries));

		// checkBox
		String[] checkColumns = { "DL_TBF_REQ", "DL_TBF_SUCR", "UL_TBF_REQ", "UL_TBF_SUCR", "GDL_TRAF", "GUL_TRAF", "EDL_TRAF","EUL_TRAF"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpMnCellGprsQosBh", 6));

		return new ModelAndView("mnCellGprsQosBh");
	}

	@RequestMapping("/report/radio/cell-gprs-qos/wk/bh")
	public ModelAndView wkList(@RequestParam(required = true) String cellid, @RequestParam(required = true) String bscid,
			@RequestParam(required = false) Integer startWeek,@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear,
			ModelMap model, HttpServletRequest request) {
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		if(endWeek==null){
			cal.add(Calendar.WEEK_OF_YEAR, -2);
			endWeek = cal.get(Calendar.WEEK_OF_YEAR);
			endYear = cal.get(Calendar.YEAR);
		}
		
		if (startWeek == null) {
			if (endWeek > 4) {
				startWeek = endWeek -4;
				startYear = endYear;
			}
			else {
				cal1.add(Calendar.WEEK_OF_YEAR,-6);
				startWeek = cal1.get(Calendar.WEEK_OF_YEAR);
				startYear = cal1.get(Calendar.YEAR);
			}
		}
		model.addAttribute("startWeek", startWeek);
		model.addAttribute("endWeek", endWeek);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);

		List<VRpWkCellGprsQosBh> vRpWkCellGprsQosBh = vRpWkCellGprsQosBhDao.filter(startWeek,startYear, endWeek, endYear, bscid, cellid);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("vRpWkCellGprsQosBh", vRpWkCellGprsQosBh);
		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);
		//start chart
		List<String> categories = new ArrayList<String>();
		List<Float> bhDlTbfSucrList = new ArrayList<Float>();
		List<Float> bhUlTbfSucrList = new ArrayList<Float>();
		
		for (VRpWkCellGprsQosBh vRpWkCell : vRpWkCellGprsQosBh) {
			categories.add(Integer.toString(vRpWkCell.getWeek()));
			bhDlTbfSucrList.add(vRpWkCell.getBhDlTbfSucr());
			bhUlTbfSucrList.add(vRpWkCell.getBhUlTbfSucr());
		}
		
		Map<String, List<Float>> dlTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
		dlTbfSucrSeries.put("DL TBF SUCR (%)--80699b", bhDlTbfSucrList);
		model.addAttribute("dlTbfSucrChart", Chart.multiColumn("dlTbfSucrChart", "DL TBF SUCR (%)", categories, dlTbfSucrSeries));

		Map<String, List<Float>> ulTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
		ulTbfSucrSeries.put("UL TBF SUCR (%)--db843d", bhUlTbfSucrList);
		model.addAttribute("ulTbfSucrChart", Chart.multiColumn("ulTbfSucrChart", "UL TBF SUCR (%)", categories, ulTbfSucrSeries));

		// checkBox
		String[] checkColumns = { "DL_TBF_REQ", "DL_TBF_SUCR", "UL_TBF_REQ", "UL_TBF_SUCR", "GDL_TRAF", "GUL_TRAF", "EDL_TRAF","EUL_TRAF"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpWkCellGprsQosBh", 6));

		return new ModelAndView("wkCellGprsQosBh");
	}
}
