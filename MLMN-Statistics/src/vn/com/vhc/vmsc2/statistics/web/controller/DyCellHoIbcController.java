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
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellHoBhIbcDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellHoIbcDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.Cell;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellHoBhIbc;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellHoIbc;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
public class DyCellHoIbcController extends BaseController{
	@Autowired
	private VRpDyCellHoIbcDAO vRpDyCellHoIbcDao;
	@Autowired
	private VRpDyCellHoBhIbcDAO vRpDyCellHoBhIbcDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private CellDAO cellDao;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping("/report/radio/ibc/cell-ho/dy/list")
	public ModelAndView dyCellHoList(@RequestParam(required = false) String bscid, @RequestParam(required = false) String cellid, @RequestParam(required = false) String day, ModelMap model,
			HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();
		try {
			Date d;
			if (day == null) {
				d = new Date(currentTime - 24 * 60 * 60 * 1000);
			} else {
				d = df.parse(day);
			}
			List<VRpDyCellHoIbc> vRpDyCellHoIbc = vRpDyCellHoIbcDao.filter(bscid, cellid, d);
			
			model.addAttribute("day", df.format(d));
			model.addAttribute("vRpDyCellHoIbc", vRpDyCellHoIbc);
			model.addAttribute("cellid", cellid);
			model.addAttribute("bscid", bscid);
			List<Bsc> bscList = bscDao.getAll();
			model.addAttribute("bscList", bscList);
			List<Cell> cellList = cellDao.getCellOfBsc(bscid);
			model.addAttribute("cellList", cellList);
		}catch(ParseException e) {
			saveError(request, e.toString());
		}
		return new ModelAndView("dyCellHoIbcList");
	}
	
	@RequestMapping("/report/radio/ibc/cell-ho/dy/details")
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
			
			List<VRpDyCellHoIbc> vRpDyCellHoIbcDetails = vRpDyCellHoIbcDao.filterDetails(df.format(startDay), df.format(endDay), bscid, cellid);
			
			model.addAttribute("vRpDyCellHoIbcDetails", vRpDyCellHoIbcDetails);
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
			
			for (VRpDyCellHoIbc vRpDyCellHoIbc : vRpDyCellHoIbcDetails) {
				categories.add(df.format(vRpDyCellHoIbc.getDay()));
				inHoSucrList.add(vRpDyCellHoIbc.getInHoSucr());
				ogHoSucrList.add(vRpDyCellHoIbc.getOgHoSucr());
			}
			Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
			series.put("In_Ho_Sucr", inHoSucrList);
			series.put("Og_Ho_Sucr", ogHoSucrList);

			model.addAttribute("chart", Chart.basicLine("CELL IBC HO " + cellid + " Daily Report", categories, series));

			// checkBox
			String[] checkColumns = { "ogHoAtt", "ogHoSucr", "inHoAtt", "inHoSucr"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 6));

			String[] checkSeries = {"ogHoSucr", "inHoSucr"};
			model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		}catch (ParseException e) {
		saveError(request, e.toString());
		}
		
	return "dyCellHoIbcDetails";
	}
	
	@RequestMapping("/report/radio/ibc/cell-ho/dy/bhDetails")
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
			
			List<VRpDyCellHoBhIbc> vRpDyCellHoBhIbcDetails = vRpDyCellHoBhIbcDao.filterDetails(df.format(startDay), df.format(endDay), bscid, cellid);
			
			model.addAttribute("vRpDyCellHoBhIbcDetails", vRpDyCellHoBhIbcDetails);
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
			
			for (VRpDyCellHoBhIbc vRpDyCellHoBhIbc : vRpDyCellHoBhIbcDetails) {
				categories.add(df.format(vRpDyCellHoBhIbc.getDay()));
				inHoSucrList.add(vRpDyCellHoBhIbc.getBhInHoSucr());
				ogHoSucrList.add(vRpDyCellHoBhIbc.getBhOgHoSucr());
			}
			Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
			series.put("Bh_In_Ho_Sucr", inHoSucrList);
			series.put("Bh_Og_Ho_Sucr", ogHoSucrList);

			model.addAttribute("chart", Chart.basicLine("CELL IBC HO BH " + cellid + " Daily Report", categories, series));

			// checkBox
			String[] checkColumns = { "bhOgHoAtt","bhOgHoSucr","bhInHoAtt","bhInHoSucr"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 6));

			String[] checkSeries = {"bhOgHoSucr", "bhInHoSucr"};
			model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		}catch (ParseException e) {
		saveError(request, e.toString());
		}
		
	return "dyCellHoBhIbcDetails";
	}
}
