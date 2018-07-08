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

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.CellDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrCellHoDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.Cell;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrCellHo;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
@RequestMapping("/report/radio/cell-ho/hr/*")
public class HrCellHoController extends BaseController {
	@Autowired
	private VRpHrCellHoDAO vRpHrCellHoDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private CellDAO cellDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("details")
	public String showReport(@RequestParam(required = false) String bscid, @RequestParam(required = false) String cellid,
			@RequestParam(required = false) String startHour, @RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model) {
		long currentTime = System.currentTimeMillis();

		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		if (endHour == null) {
			endHour = "23";
		}
		if (startHour == null) {
			startHour = "0";
		}
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("startHour", startHour);
		model.addAttribute("endHour", endHour);

		List<VRpHrCellHo> vRpHrCellHoDetails = new ArrayList<VRpHrCellHo>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			vRpHrCellHoDetails.addAll(vRpHrCellHoDao.filter(dates.get(day).get("startHour"), dates.get(day).get("endHour"), df.format(day), bscid, cellid));
		}

		model.addAttribute("vRpHrCellHoDetails", vRpHrCellHoDetails);
		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);

		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> inHoSucrList = new ArrayList<Float>();
		List<Float> ogHoSucrList = new ArrayList<Float>();

		for (VRpHrCellHo vRpHrCellHo : vRpHrCellHoDetails) {
			categories.add(Integer.toString(vRpHrCellHo.getHour()));
			inHoSucrList.add(vRpHrCellHo.getInHoSucr());
			ogHoSucrList.add(vRpHrCellHo.getOgHoSucr());
		}
		
		Map<String, List<Float>> inHoSucrSeries = new LinkedHashMap<String, List<Float>>();
		inHoSucrSeries.put("In_Ho_Sucr (%)--4572a7", inHoSucrList);
		model.addAttribute("inHoSucrChart", Chart.multiColumn("inHoSucrChart", "Incoming HO Successful Rate (%)", categories, inHoSucrSeries));

		Map<String, List<Float>> ogHoSucrSeries = new LinkedHashMap<String, List<Float>>();
		ogHoSucrSeries.put("Og_Ho_Sucr (%)--aa4643", ogHoSucrList);
		model.addAttribute("ogHoSucrChart", Chart.multiColumn("ogHoSucrChart", "Outgoing HO Successful Rate (%)", categories, ogHoSucrSeries));

		// checkBox
		String[] checkColumns = { "hoAtt", "hoSucr", "ogHoAtt", "ogHoSucr", "inHoAtt", "inHoSucr" };
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 6));

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpHrCellHoDetail"));

		return "hrCellHoDetails";
	}
}
