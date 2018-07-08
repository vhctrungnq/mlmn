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
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellCssrDetails3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrCellCssrDetails3GDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc3G;
import vn.com.vhc.vmsc2.statistics.domain.Cell3G;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellCssrDetails3G;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrCellCssrDetails3G;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;

@Controller
@RequestMapping("/report/radio3g/cell/cssr/*")
public class CellCssrDetails3gController extends BaseController{
	@Autowired
	private VRpDyCellCssrDetails3GDAO vRpDyCellCssrDetails3GDao;
	@Autowired
	private VRpHrCellCssrDetails3GDAO vRpHrCellCssrDetails3GDao;
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
		List<VRpDyCellCssrDetails3G> dyCellCssrDetails3G = vRpDyCellCssrDetails3GDao.filter(df.format(startDate), df.format(endDate),bscid, cellid);
		
		model.addAttribute("cellid", cellid);
		List<Bsc3G> bscList = bsc3GDao.getAll();
		model.addAttribute("bscid", bscid);
		model.addAttribute("bscList", bscList);
		List<Cell3G> cellList = cell3GDao.getCellOfBsc3G(bscid);
		model.addAttribute("cellList", cellList);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("dyCellCssrDetails3G", dyCellCssrDetails3G);

		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> csSpeechCssrList = new ArrayList<Float>();
		List<Float> psR99CssrList = new ArrayList<Float>();
		for (VRpDyCellCssrDetails3G vRpDyCellCssrDetails3G : dyCellCssrDetails3G) {
			categories.add(df.format(vRpDyCellCssrDetails3G.getDay()));
			csSpeechCssrList.add(vRpDyCellCssrDetails3G.getCsSpeechCssr());
			psR99CssrList.add(vRpDyCellCssrDetails3G.getPsR99Cssr());
		}

		Map<String, List<Float>> csSpeechCssrSeries = new LinkedHashMap<String, List<Float>>();
		csSpeechCssrSeries.put("CS Speech CSSR --80699b", csSpeechCssrList);
		model.addAttribute("csSpeechCssrChart", Chart.multiColumn("csSpeechCssrChart", "CS Speech CSSR", categories, csSpeechCssrSeries));
		
		Map<String, List<Float>> psR99CssrSeries = new LinkedHashMap<String, List<Float>>();
		psR99CssrSeries.put("R99 PS CSSR --80699b", psR99CssrList);
		model.addAttribute("psR99CssrChart", Chart.multiColumn("psR99CssrChart", "R99 PS CSSR", categories, psR99CssrSeries));

		return new ModelAndView("dyCellCssrDetails3G");
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
		List<VRpHrCellCssrDetails3G> hrCellCssrDetails3G = new ArrayList<VRpHrCellCssrDetails3G>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			hrCellCssrDetails3G.addAll(vRpHrCellCssrDetails3GDao.filter(dates.get(day).get("startHour"), dates.get(day).get("endHour"), day, bscid, cellid));
		}
		model.addAttribute("hrCellCssrDetails3G", hrCellCssrDetails3G);
		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		List<Bsc3G> bscList = bsc3GDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell3G> cellList = cell3GDao.getCellOfBsc3G(bscid);
		model.addAttribute("cellList", cellList);
		
		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> csSpeechCssrList = new ArrayList<Float>();
		List<Float> psR99CssrList = new ArrayList<Float>();
		for (VRpHrCellCssrDetails3G vRpHrCellCssrDetails3G : hrCellCssrDetails3G) {
			categories.add(Integer.toString(vRpHrCellCssrDetails3G.getHour())+":00"+df.format(vRpHrCellCssrDetails3G.getDay()));
			csSpeechCssrList.add(vRpHrCellCssrDetails3G.getCsSpeechCssr());
			psR99CssrList.add(vRpHrCellCssrDetails3G.getPsR99Cssr());
		}

		Map<String, List<Float>> csSpeechCssrSeries = new LinkedHashMap<String, List<Float>>();
		csSpeechCssrSeries.put("CS Speech CSSR --80699b", csSpeechCssrList);
		model.addAttribute("csSpeechCssrChart", Chart.multiColumn("csSpeechCssrChart", "CS Speech CSSR", categories, csSpeechCssrSeries));
		
		Map<String, List<Float>> psR99CssrSeries = new LinkedHashMap<String, List<Float>>();
		psR99CssrSeries.put("R99 PS CSSR --80699b", psR99CssrList);
		model.addAttribute("psR99CssrChart", Chart.multiColumn("psR99CssrChart", "R99 PS CSSR", categories, psR99CssrSeries));

		return new ModelAndView("hrCellCssrDetails3G");
	}
}
