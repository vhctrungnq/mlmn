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
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnCellGprsQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.Cell;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnCellGprsQos;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
@RequestMapping("/report/radio/cell-gprs-qos/mn/*")
public class MnCellGprsQosController extends BaseController {
	@Autowired
	private VRpMnCellGprsQosDAO vRpMnCellGprsQosDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private CellDAO cellDao;

	@RequestMapping("list")
	public ModelAndView dyList(@RequestParam(required = false) String region, @RequestParam(required = false) String bscid,
			@RequestParam(required = false) String province, @RequestParam(required = false) String cellid, @RequestParam(required = false) Integer startMonth,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endMonth,
			@RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
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
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
	    model.addAttribute("monthList", monthList);
		
		if (region == null)
			region = "";
//		int startRecord = 0;
//		int order = 1;
//		String column = "YEAR, MONTH, REGION, PROVINCE, BSCID, CELLID";
//		try {
//			startRecord = (Integer
//					.parseInt(request.getParameter((new ParamEncoder("vRpMnCellGprsQos").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
//			order = Integer.parseInt(request.getParameter((new ParamEncoder("vRpMnCellGprsQos").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
//			column = request.getParameter((new ParamEncoder("vRpMnCellGprsQos").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
//		} catch (NumberFormatException e) {
//		}
//		int endRecord = startRecord + 100;

		List<VRpMnCellGprsQos> vRpMnCellGprsQos = vRpMnCellGprsQosDao.filter(startMonth, startYear, endMonth, endYear, cellid, province, bscid, region);

//		Integer totalSize = vRpMnCellGprsQosDao.countRow(startMonth, startYear, endMonth, endYear, cellid, bscid, province, region);
//		model.addAttribute("totalSize", totalSize);

		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);

		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		model.addAttribute("province", province);

		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);

		model.addAttribute("vRpMnCellGprsQos", vRpMnCellGprsQos);

		return new ModelAndView("mnCellGprsQosList");
	}

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = true) String cellid, @RequestParam(required = true) String bscid,
			@RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {

		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
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
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
	    model.addAttribute("monthList", monthList);
	    
		List<VRpMnCellGprsQos> vRpMnCellGprsQosList = vRpMnCellGprsQosDao.filter(startMonth, startYear, endMonth, endYear, bscid, cellid);

		model.addAttribute("vRpMnCellGprsQosList", vRpMnCellGprsQosList);
		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);
		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> dlTbfSucrList = new ArrayList<Float>();
		List<Float> ulTbfSucrList = new ArrayList<Float>();

		for (VRpMnCellGprsQos vRpMnCell : vRpMnCellGprsQosList) {
			categories.add(Integer.toString(vRpMnCell.getMonth()) + "/" + vRpMnCell.getYear());
			dlTbfSucrList.add(vRpMnCell.getDlTbfSucr());
			ulTbfSucrList.add(vRpMnCell.getUlTbfSucr());
		}

		Map<String, List<Float>> dlTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
		dlTbfSucrSeries.put("DL TBF SUCR (%)--80699b", dlTbfSucrList);
		model.addAttribute("dlTbfSucrChart", Chart.multiColumn("dlTbfSucrChart", "DL TBF SUCR (%)", categories, dlTbfSucrSeries));

		Map<String, List<Float>> ulTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
		ulTbfSucrSeries.put("UL TBF SUCR (%)--db843d", ulTbfSucrList);
		model.addAttribute("ulTbfSucrChart", Chart.multiColumn("ulTbfSucrChart", "UL TBF SUCR (%)", categories, ulTbfSucrSeries));

		// checkBox
		String[] checkColumns = { "DL_TBF_REQ", "DL_TBF_SUCR", "UL_TBF_REQ", "UL_TBF_SUCR", "GDL_TRAF", "GUL_TRAF", "EDL_TRAF", "EUL_TRAF", "DATALOAD" };
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpMnCellGprsQos", 6));

		return "mnCellGprsQos";
	}
}
