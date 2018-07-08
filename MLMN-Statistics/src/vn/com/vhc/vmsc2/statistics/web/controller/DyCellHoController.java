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
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellHoBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellHoDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.Cell;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellHo;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellHoBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
public class DyCellHoController extends BaseController{
	@Autowired
	private VRpDyCellHoDAO vRpDyCellHoDao;
	@Autowired
	private VRpDyCellHoBhDAO vRpDyCellHoBhDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private CellDAO cellDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping("/report/radio/cell-ho/dy/list")
	public ModelAndView showReportList(@RequestParam(required=false) String bscid, @RequestParam(required = false) String cellid,
			@RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, ModelMap model, HttpServletRequest request) {
			long currentTime = System.currentTimeMillis();
			
			if (endDate == null) {
				endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
			}
			if (startDate == null) {
				startDate = endDate;
			}
			/*int startRecord = 0;
			int order = 1;
			String column = "DAY, REGION, BSCID, CELLID";
			try {
				startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("vRpDyCellHo").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
				order = Integer.parseInt(request.getParameter((new ParamEncoder("vRpDyCellHo").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
				column = request.getParameter((new ParamEncoder("vRpDyCellHo").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
			} catch (NumberFormatException e) {
			}
			int endRecord = startRecord + 100;*/

			//List<VRpDyCellHo> vRpDyCellHo = vRpDyCellHoDao.filter(bscid, cellid, d, startRecord, endRecord, column, order == 1 ? "ASC" : "DESC");
			List<VRpDyCellHo> vRpDyCellHo = vRpDyCellHoDao.filterDetails(df.format(startDate), df.format(endDate), bscid, cellid);
			//Integer totalSize = vRpDyCellHoDao.countRow(bscid, cellid, d);
			//model.addAttribute("totalSize", totalSize);
			List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
			model.addAttribute("regionList", regionList);
			model.addAttribute("startDate", df.format(startDate));
			model.addAttribute("endDate", df.format(endDate));
			model.addAttribute("vRpDyCellHo", vRpDyCellHo);
			model.addAttribute("cellid", cellid);
			model.addAttribute("bscid", bscid);
			List<Bsc> bscList = bscDao.getAll();
			model.addAttribute("bscList", bscList);
			List<Cell> cellList = cellDao.getCellOfBsc(bscid);
			model.addAttribute("cellList", cellList);

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyCellHo"));
		
		return new ModelAndView("dyCellHoList");
	}
	
	@RequestMapping("/report/radio/cell-ho/dy/details")
	public String showReport(@RequestParam(required=true) String bscid, @RequestParam(required = true) String cellid,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();
		
		try {
			Date startDay;
			Date endDay;
			Calendar cal = Calendar.getInstance();
			if(endDate==null){
				endDay = new Date(currentTime - 24 * 60 * 60 * 1000);
				cal.add(Calendar.DATE, -30);
					startDay = cal.getTime();
			}else{
				endDay = df.parse(endDate);
				if (startDate == null) {
					cal.setTime(endDay);
					cal.add(Calendar.DATE, -30);
					startDay = cal.getTime();
				}
				else{
					startDay = df.parse(startDate);
				}
			}
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("endDate", df.format(endDay));
			
			List<VRpDyCellHo> vRpDyCellHoDetails = vRpDyCellHoDao.filterDetails(df.format(startDay), df.format(endDay), bscid, cellid);
			
			model.addAttribute("vRpDyCellHoDetails", vRpDyCellHoDetails);
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
			
			for (VRpDyCellHo vRpDyCellHo : vRpDyCellHoDetails) {
				categories.add(df.format(vRpDyCellHo.getDay()));
				inHoSucrList.add(vRpDyCellHo.getInHoSucr());
				ogHoSucrList.add(vRpDyCellHo.getOgHoSucr());
			}
			Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
			series.put("In_Ho_Sucr", inHoSucrList);
			series.put("Og_Ho_Sucr", ogHoSucrList);

			model.addAttribute("chart", Chart.basicLine("CELL HO " + cellid + " Daily Report", categories, series));

			// checkBox
			String[] checkColumns = { "ogHoAtt", "ogHoSucr", "inHoAtt", "inHoSucr"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 6));

			String[] checkSeries = {"ogHoSucr", "inHoSucr"};
			model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		}catch (ParseException e) {
		saveError(request, e.toString());
		}

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyCellHoDetail"));
		
	return "dyCellHoDetails";
	}
	
	@RequestMapping("/report/radio/cell-ho/dy/bhDetails")
	public String showReportBh(@RequestParam(required=true) String bscid, @RequestParam(required = true) String cellid,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();
		
		try {
			Date startDay;
			Date endDay;
			Calendar cal = Calendar.getInstance();
			if(endDate==null){
				endDay = new Date(currentTime - 24 * 60 * 60 * 1000);
				cal.add(Calendar.DATE, -30);
					startDay = cal.getTime();
			}else{
				endDay = df.parse(endDate);
				if (startDate == null) {
					cal.setTime(endDay);
					cal.add(Calendar.DATE, -30);
					startDay = cal.getTime();
				}
				else{
					startDay = df.parse(startDate);
				}
			}
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("endDate", df.format(endDay));
			
			List<VRpDyCellHoBh> vRpDyCellHoBhDetails = vRpDyCellHoBhDao.filterDetails(df.format(startDay), df.format(endDay), bscid, cellid);
			
			model.addAttribute("vRpDyCellHoBhDetails", vRpDyCellHoBhDetails);
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
			
			for (VRpDyCellHoBh vRpDyCellHoBh : vRpDyCellHoBhDetails) {
				categories.add(df.format(vRpDyCellHoBh.getDay()));
				inHoSucrList.add(vRpDyCellHoBh.getBhInHoSucr());
				ogHoSucrList.add(vRpDyCellHoBh.getBhOgHoSucr());
			}
			Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
			series.put("Bh_In_Ho_Sucr", inHoSucrList);
			series.put("Bh_Og_Ho_Sucr", ogHoSucrList);

			model.addAttribute("chart", Chart.basicLine("CELL HO BH " + cellid + " Daily Report", categories, series));

			// checkBox
			String[] checkColumns = { "bhOgHoAtt","bhOgHoSucr","bhInHoAtt","bhInHoSucr"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 6));

			String[] checkSeries = {"bhOgHoSucr", "bhInHoSucr"};
			model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		}catch (ParseException e) {
		saveError(request, e.toString());
		}
		
	return "dyCellHoBhDetails";
	}
}
