package vn.com.vhc.vmsc2.statistics.web.controller;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.CellDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrCellDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.Cell;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrCell;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;

@Controller
@RequestMapping("/report/radio/cell/hr/*")
public class HrCellQosController extends BaseController {
	@Autowired
	private VRpHrCellDAO vRpHrCellDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private CellDAO cellDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat ddf = new SimpleDateFormat("dd/MM");

	@RequestMapping("detail")
	public String showReport(
			@RequestParam(required = false) String bscid, 
			@RequestParam(required = false) String cellid,
			@RequestParam(required = false) String startHour, 
			@RequestParam(required = false) String endHour, 
			@RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, 
			ModelMap model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();

		if (endDate == null) {
			endDate = new Date(currentTime);
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

		List<VRpHrCell> vRpHrCellList = new ArrayList<VRpHrCell>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			vRpHrCellList.addAll(vRpHrCellDao.filter(dates.get(day).get("startHour"), dates.get(day).get("endHour"), day, bscid, cellid));
		}
		model.addAttribute("vRpHrCellList", vRpHrCellList);
		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpHrCell"));
		
		// neu tim kiem theo ten cell cu the thi xuat bieu do
		if (bscid != null && bscid.length() > 0 && cellid != null && cellid.length() > 0) {
			List<String> categories = new ArrayList<String>();
			List<Float> tDefList = new ArrayList<Float>();
			List<Float> tAvailList = new ArrayList<Float>();
			List<Float> cssrList = new ArrayList<Float>();
			List<Float> tTrafList = new ArrayList<Float>();
			List<Float> tTrafhList = new ArrayList<Float>();
			List<Float> tCapList = new ArrayList<Float>();
			List<Float> tDrprList = new ArrayList<Float>();
			List<Float> tBlkrList = new ArrayList<Float>();
			List<Float> sDrprList = new ArrayList<Float>();
			List<Float> sBlkrList = new ArrayList<Float>();
			List<Float> cssrBaseline = new ArrayList<Float>();
			List<Float> cdrBaseline = new ArrayList<Float>();
	
			Map<String, VRpHrCell> vRpHrCellMap = new LinkedHashMap<String, VRpHrCell>();
			for (VRpHrCell vRpHrCell : vRpHrCellList) {
				vRpHrCellMap.put(Integer.toString(vRpHrCell.getHour()) + ":00 " + ddf.format(vRpHrCell.getDay()), vRpHrCell);
			}
	
			for (Date day : dates.keySet()) {
				String sHour = dates.get(day).get("startHour");
				String eHour = dates.get(day).get("endHour");
	
				if (sHour.isEmpty())
					sHour = "0";
				if (eHour.isEmpty())
					eHour = "23";
	
				int sHourNum = Integer.parseInt(sHour);
				int eHourNum = Integer.parseInt(eHour);
	
				for (int i = sHourNum; i <= eHourNum; i++) {
					String cat = Integer.toString(i) + ":00 " + ddf.format(day);
					VRpHrCell vRpHrCell = vRpHrCellMap.get(cat);
	
					categories.add(cat);
					if (vRpHrCell == null) {
						tDefList.add(null);
						tAvailList.add(null);
						cssrList.add(null);
						tTrafList.add(null);
						tTrafhList.add(null);
						tCapList.add(null);
						tDrprList.add(null);
						tBlkrList.add(null);
						sDrprList.add(null);
						sBlkrList.add(null);
					} else {
						tDefList.add(Helper.int2Float(vRpHrCell.gettDef()));
						tAvailList.add(Helper.bigdecimal2Float(vRpHrCell.gettAvail()));
						cssrList.add(vRpHrCell.getCssr());
						tTrafList.add(vRpHrCell.gettTraf());
						tTrafhList.add(vRpHrCell.gettTrafh());
						tCapList.add(vRpHrCell.gettCap());
						tDrprList.add(vRpHrCell.gettDrpr());
						tBlkrList.add(vRpHrCell.gettBlkr());
						sDrprList.add(vRpHrCell.getsDrpr());
						sBlkrList.add(vRpHrCell.getsBlkr());
					}
					cssrBaseline.add((float) 95);
					cdrBaseline.add((float) 3);
				}
			}
	
			Map<String, List<Float>> availSeries = new LinkedHashMap<String, List<Float>>();
			availSeries.put("T_DEF", tDefList);
			availSeries.put("T_AVAIL", tAvailList);
			model.addAttribute("availChart", Chart.multiColumn("availChart", "Define-Avail TCH", categories, availSeries));
	
			Map<String, List<Float>> trafSeries = new LinkedHashMap<String, List<Float>>();
			trafSeries.put("Capacity-area", tCapList);
			trafSeries.put("TRAF", tTrafList);
			trafSeries.put("Half Rate", tTrafhList);
			model.addAttribute("trafChart", Chart.multiColumn("trafChart", "Traffic (Erlang)", categories, trafSeries));
	
			Map<String, List<Float>> tDrprSeries = new LinkedHashMap<String, List<Float>>();
			tDrprSeries.put("Drop Rate (%)--4572a7", tDrprList);
			tDrprSeries.put("baseline-line-FF0000", cdrBaseline);
			model.addAttribute("tDrprChart", Chart.multiColumn("tDrprChart", "TCH Drop Rate (%)", categories, tDrprSeries));
	
			Map<String, List<Float>> sDrprSeries = new LinkedHashMap<String, List<Float>>();
			sDrprSeries.put("Drop Rate (%)--aa4643", sDrprList);
			model.addAttribute("sDrprChart", Chart.multiColumn("sDrprChart", "SDCCH Drop Rate (%)", categories, sDrprSeries));
	
			Map<String, List<Float>> cssrSeries = new LinkedHashMap<String, List<Float>>();
			cssrSeries.put("CSSR (%)--89a54e", cssrList);
			cssrSeries.put("baseline-line-FF0000", cssrBaseline);
			model.addAttribute("cssrChart", Chart.multiColumn("cssrChart", "CSSR (%)", categories, cssrSeries));
	
			Map<String, List<Float>> tBlkrSeries = new LinkedHashMap<String, List<Float>>();
			tBlkrSeries.put("Block Rate (%)--80699b", tBlkrList);
			model.addAttribute("tBlkrChart", Chart.multiColumn("tBlkrChart", "TCH Block Rate (%)", categories, tBlkrSeries));
	
			Map<String, List<Float>> sBlkrSeries = new LinkedHashMap<String, List<Float>>();
			sBlkrSeries.put("Block Rate (%)--db843d", sBlkrList);
			model.addAttribute("sBlkrChart", Chart.multiColumn("sBlkrChart", "SDCCH Block Rate (%)", categories, sBlkrSeries));
			
		}
		// checkBox
		String[] checkColumns = { "T_DEF", "T_AVAIL", "T_ATT", "T_NBLKR", "T_NBLKS", "T_HOBLKR", "T_HOBLKS", "T_SEIZ", "CSSR", "", "T_DRPR", "T_DRPS", "", "TRAFF", "H_TRAF", "S_DEF", "S_AVAIL",
				"S_ATT", "S_BLKR", "S_BLKS", "S_SEIZ", "", "S_DRPR", "S_DRPS", "DOWNTIME", "MEANHOLDTIME" , "SDCCHMEANHOLDTIME"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpHrCell", 8));
		return "hrCellQos";
	}
}
