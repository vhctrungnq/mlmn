package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.Bsc3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.Cell3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellDropDetails3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrCellDropDetails3GDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc3G;
import vn.com.vhc.vmsc2.statistics.domain.Cell3G;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellDropDetails3G;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrCellDropDetails3G;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;

@Controller
@RequestMapping("/report/radio3g/cell/drop/*")
public class CellDropDetails3gController extends BaseController{
	@Autowired
	private VRpDyCellDropDetails3GDAO vRpDyCellDropDetails3GDao;
	@Autowired
	private VRpHrCellDropDetails3GDAO vRpHrCellDropDetails3GDao;
	@Autowired
	private Cell3GDAO cell3GDao;
	@Autowired
	private Bsc3GDAO bsc3GDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("dy")
	public ModelAndView dyList(@RequestParam(required = false) String cellid,@RequestParam(required = false) String bscid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model) {
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		List<VRpDyCellDropDetails3G> dyCellDropDetails3G = vRpDyCellDropDetails3GDao.filter(df.format(startDate), df.format(endDate),bscid, cellid);
		
		model.addAttribute("cellid", cellid);
		List<Bsc3G> bscList = bsc3GDao.getAll();
		model.addAttribute("bscid", bscid);
		model.addAttribute("bscList", bscList);
		List<Cell3G> cellList = cell3GDao.getCellOfBsc3G(bscid);
		model.addAttribute("cellList", cellList);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("dyCellDropDetails3G", dyCellDropDetails3G);

		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> speechDrprList = new ArrayList<Float>();
		for (VRpDyCellDropDetails3G vRpDyCellDropDetails3G : dyCellDropDetails3G) {
			categories.add(df.format(vRpDyCellDropDetails3G.getDay()));
			speechDrprList.add(vRpDyCellDropDetails3G.getSpeechDrpr());
		}

		Map<String, List<Float>> csSpeechCssrSeries = new LinkedHashMap<String, List<Float>>();
		csSpeechCssrSeries.put("CS Speech CSSR --80699b", speechDrprList);
		model.addAttribute("csSpeechCssrChart", Chart.multiColumn("csSpeechCssrChart", "CS Speech CSSR", categories, csSpeechCssrSeries));

		return new ModelAndView("dyCellDropDetails3G");
	}
	
	@RequestMapping("hr")
	public ModelAndView hrList(@RequestParam(required = true) String cellid,@RequestParam(required = false) String bscid, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
			ModelMap model) {
		long currentTime = System.currentTimeMillis();

		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		if (endHour == null) {
			endHour = "23";
		} else {
			endHour = endHour.substring(0, endHour.indexOf(":"));
		}
		if (startHour == null) {
			startHour = "0";
		} else {
			startHour = startHour.substring(0, startHour.indexOf(":"));
		}
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("startHour", startHour + ":00");
		model.addAttribute("endHour", endHour + ":00");
		List<VRpHrCellDropDetails3G> hrCellDropDetails3G = new ArrayList<VRpHrCellDropDetails3G>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			hrCellDropDetails3G.addAll(vRpHrCellDropDetails3GDao.filter(dates.get(day).get("startHour"), dates.get(day).get("endHour"), day, bscid, cellid));
		}
		model.addAttribute("hrCellDropDetails3G", hrCellDropDetails3G);
		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		List<Bsc3G> bscList = bsc3GDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell3G> cellList = cell3GDao.getCellOfBsc3G(bscid);
		model.addAttribute("cellList", cellList);
		
		List<String> categories = new ArrayList<String>();
		List<Float> speechDrprList = new ArrayList<Float>();
		for (VRpHrCellDropDetails3G vRpHrCellDropDetails3G : hrCellDropDetails3G) {
			categories.add(Integer.toString(vRpHrCellDropDetails3G.getHour())+":00"+df.format(vRpHrCellDropDetails3G.getDay()));
			speechDrprList.add(vRpHrCellDropDetails3G.getSpeechDrpr());
		}

		Map<String, List<Float>> csSpeechCssrSeries = new LinkedHashMap<String, List<Float>>();
		csSpeechCssrSeries.put("CS Speech CSSR --80699b", speechDrprList);
		model.addAttribute("csSpeechCssrChart", Chart.multiColumn("csSpeechCssrChart", "CS Speech CSSR", categories, csSpeechCssrSeries));

		return new ModelAndView("hrCellDropDetails3G");
	}
}
