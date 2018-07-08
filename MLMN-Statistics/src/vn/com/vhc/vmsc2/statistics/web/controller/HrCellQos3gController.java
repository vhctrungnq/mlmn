package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.Bsc3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.Cell3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrCell3GDAO;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrCell3G;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;

@Controller
@RequestMapping("/report/radio3g/cell/hr/*")
public class HrCellQos3gController extends BaseController {
	@Autowired
	private VRpHrCell3GDAO vRpHrCellDao;
	@Autowired
	private Bsc3GDAO bsc3g;
	@Autowired
	private Cell3GDAO cell3g;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat ddf = new SimpleDateFormat("dd/MM");

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = false) String bscid, @RequestParam(required = false) String function,
			@RequestParam(required = false) String cellid, @RequestParam(required = false) String startHour, @RequestParam(required = false) String endHour,
			@RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, ModelMap model, HttpServletRequest request) {

		Integer beginIndex = Integer.valueOf(1);
	    int pageSize = 50;
	    model.addAttribute("pageSize", Integer.valueOf(pageSize));
	    String page = request.getParameter(new ParamEncoder("vRpHrCell").encodeParameterName("p"));
	    if (page != null) {
	      beginIndex = Integer.valueOf((Integer.parseInt(page) - 1) * pageSize + 1);
	    }
	    
	    Integer endIndex = Integer.valueOf(beginIndex.intValue() + pageSize - 1);
	    
	    if (function == null)
	      function = "";
	    model.addAttribute("function", function);
	    if (endDate == null) {
	      endDate = new Date();
	    }
	    if (startDate == null) {
	      startDate = endDate;
	    }
	    if (endHour == null) {
	      endHour = "23";
	    }
	    else if (endHour.indexOf(":") > 0) {
	      endHour = endHour.substring(0, endHour.indexOf(":"));
	    } else {
	      endHour = "23";
	    }
	    if (startHour == null) {
	      startHour = "0";
	    }
	    else if (startHour.indexOf(":") > 0) {
	      startHour = startHour.substring(0, startHour.indexOf(":"));
	    } else {
	      startHour = "0";
	    }
	    String[] hourList = { "0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00" };
	    model.addAttribute("hourList", hourList);
	    
	    model.addAttribute("startDate", df.format(startDate));
	    model.addAttribute("endDate", df.format(endDate));
	    model.addAttribute("startHour", startHour + ":00");
	    model.addAttribute("endHour", endHour + ":00");
	    
	    List<VRpHrCell3G> vRpHrCellList = vRpHrCellDao.filter(startHour, startDate, endHour, 
	      endDate, bscid, cellid, beginIndex, endIndex);
	    

	    int size = 0;
	    size = vRpHrCellDao.countRecordNumber(startHour, startDate, endHour, endDate, bscid, cellid);
	    
	    model.addAttribute("totalSize", Integer.valueOf(size));
	    model.addAttribute("vRpHrCellList", vRpHrCellList);
	    model.addAttribute("cellid", cellid);
	    model.addAttribute("bscid", bscid);
	    List<String> bscList = bsc3g.getAllBscName();
	    model.addAttribute("bscList", bscList);
	    List<String> cellList = null;
	    if ((bscid != null) && (bscid.length() > 0)) {
	      cellList = cell3g.getCellids3G(bscid);
	    }
	    model.addAttribute("cellList", cellList);

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpHrCell"));

		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> speechCssrList = new ArrayList<Float>();
		List<Float> cs64CssrList = new ArrayList<Float>();
		List<Float> psr99CssrList = new ArrayList<Float>();
		List<Float> hsupaCssrList = new ArrayList<Float>();
		List<Float> hsdpaCssrList = new ArrayList<Float>();
		List<Float> speechDrprList = new ArrayList<Float>();
		List<Float> cs64DrprList = new ArrayList<Float>();
		List<Float> psr99DrprList = new ArrayList<Float>();
		List<Float> hsupaDrprList = new ArrayList<Float>();
		List<Float> hsdpaDrprList = new ArrayList<Float>();
		List<Float> speechCssrBaseline = new ArrayList<Float>();
		List<Float> speechDrprBaseline = new ArrayList<Float>();

		// TRUNGNQ neu user nhap bsc va cell thi xuat ra bieu do chi tiet !!!
		if (bscid != null && bscid.length() > 0 && cellid != null && cellid.length() > 0) {
			Map<String, VRpHrCell3G> vRpHrCellMap = new LinkedHashMap<String, VRpHrCell3G>();

			for (VRpHrCell3G vRpHrCell : vRpHrCellList) {
				vRpHrCellMap.put(Integer.toString(vRpHrCell.getHour()) + ":00 " + ddf.format(vRpHrCell.getDay()), vRpHrCell);
			}
			Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
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
					VRpHrCell3G vRpHrCell = vRpHrCellMap.get(cat);

					categories.add(cat);
					if (vRpHrCell == null) {
						speechCssrList.add(null);
						cs64CssrList.add(null);
						psr99CssrList.add(null);
						hsupaCssrList.add(null);
						hsdpaCssrList.add(null);
						speechDrprList.add(null);
						cs64DrprList.add(null);
						psr99DrprList.add(null);
						hsupaDrprList.add(null);
						hsdpaDrprList.add(null);
					} else {
						speechCssrList.add(vRpHrCell.getSpeechCssr());
						cs64CssrList.add(vRpHrCell.getCs64Cssr());
						psr99CssrList.add(vRpHrCell.getPsr99Cssr());
						hsupaCssrList.add(vRpHrCell.getHsupaCssr());
						hsdpaCssrList.add(vRpHrCell.getHsdpaCssr());
						speechDrprList.add(vRpHrCell.getSpeechDrpr());
						cs64DrprList.add(vRpHrCell.getCs64Drpr());
						psr99DrprList.add(vRpHrCell.getPsr99Drpr());
						hsupaDrprList.add(vRpHrCell.getHsupaDrpr());
						hsdpaDrprList.add(vRpHrCell.getHsdpaDrpr());
					}
					speechCssrBaseline.add((float) 95);
					speechDrprBaseline.add((float) 3);
				}
			}
		}

		Map<String, List<Float>> speechCssrSeries = new LinkedHashMap<String, List<Float>>();
		speechCssrSeries.put("Speech CSSR (%)--4572a7", speechCssrList);
		model.addAttribute("speechCssrChart", Chart.multiColumn("speechCssrChart", "Speech CSSR (%)", categories, speechCssrSeries));

		Map<String, List<Float>> cs64CssrSeries = new LinkedHashMap<String, List<Float>>();
		cs64CssrSeries.put("CS64 CSSR (%)--aa4643", cs64CssrList);
		model.addAttribute("cs64CssrChart", Chart.multiColumn("cs64CssrChart", "CS64 CSSR (%)", categories, cs64CssrSeries));

		Map<String, List<Float>> psr99CssrSeries = new LinkedHashMap<String, List<Float>>();
		psr99CssrSeries.put("PS R99 CSSR (%)--89a54e", psr99CssrList);
		model.addAttribute("psr99CssrChart", Chart.multiColumn("psr99CssrChart", "PS R99 CSSR (%)", categories, psr99CssrSeries));

		Map<String, List<Float>> hsupaCssrSeries = new LinkedHashMap<String, List<Float>>();
		hsupaCssrSeries.put("PS HSUPA CSSR (%)--80699b", hsupaCssrList);
		model.addAttribute("hsupaCssrChart", Chart.multiColumn("hsupaCssrChart", "PS HSUPA CSSR (%)", categories, hsupaCssrSeries));

		Map<String, List<Float>> hsdpaCssrSeries = new LinkedHashMap<String, List<Float>>();
		hsdpaCssrSeries.put("PS HSDPA CSSR (%)--db843d", hsdpaCssrList);
		model.addAttribute("hsdpaCssrChart", Chart.multiColumn("hsdpaCssrChart", "PS HSDPA CSSR (%)", categories, hsdpaCssrSeries));

		Map<String, List<Float>> speechDrprSeries = new LinkedHashMap<String, List<Float>>();
		speechDrprSeries.put("Speech DCR (%)--4572a7", speechDrprList);
		model.addAttribute("speechDrprChart", Chart.multiColumn("speechDrprChart", "Speech DCR (%)", categories, speechDrprSeries));

		Map<String, List<Float>> cs64DrprSeries = new LinkedHashMap<String, List<Float>>();
		cs64DrprSeries.put("CS64 DCR (%)--aa4643", cs64DrprList);
		model.addAttribute("cs64DrprChart", Chart.multiColumn("cs64DrprChart", "CS64 DCR (%)", categories, cs64DrprSeries));

		Map<String, List<Float>> psr99DrprSeries = new LinkedHashMap<String, List<Float>>();
		psr99DrprSeries.put("PS R99 DCR (%)--89a54e", psr99DrprList);
		model.addAttribute("psr99DrprChart", Chart.multiColumn("psr99DrprChart", "PS R99 DCR (%)", categories, psr99DrprSeries));

		Map<String, List<Float>> hsupaDrprSeries = new LinkedHashMap<String, List<Float>>();
		hsupaDrprSeries.put("PS HSUPA DCR (%)--80699b", hsupaDrprList);
		model.addAttribute("hsupaDrprChart", Chart.multiColumn("hsupaDrprChart", "PS HSUPA DCR (%)", categories, hsupaDrprSeries));

		Map<String, List<Float>> hsdpaDrprSeries = new LinkedHashMap<String, List<Float>>();
		hsdpaDrprSeries.put("PS HSDPA DCR (%)--db843d", hsdpaDrprList);
		model.addAttribute("hsdpaDrprChart", Chart.multiColumn("hsdpaDrprChart", "PS HSDPA DCR (%)", categories, hsdpaDrprSeries));

		// checkBox
		String[] checkColumns = { "CELL_DOWNTIME", "HS_DOWNTIME", "EUL_DOWNTIME", "SPEECH_TRAFF", "CS64_TRAFF", "PSR99_UL_TRAFF", "PSR99_DL_TRAFF",
				"HSUPA_UL_TRAFF", "HSDPA_DL_TRAFF", "PSR99_UL_THROUGHTPUT", "PSR99_DL_THROUGHTPUT", "HSUPA_UL_THROUGHTPUT", "HSDPA_DL_THROUGHTPUT", "",
				"SPEECH_CSSR", "CS64_CSSR", "PSR99_CSSR", "HSUPA_CSSR", "HSDPA_CSSR", "SPEECH_DROP", "", "SPEECH_DRPR", "CS64_DROP", "CS64_DRPR", "PSR99_DROP",
				"PSR99_DRPR", "HSUPA_DROP", "HSUPA_DRPR", "HSDPA_DROP", "HSDPA_DRPR", "", "SPEECH_SHO_SR_OUT", "SHO_SR_OUT", "SHO_SR_IN", "CS_IRAT_HO_SR",
				"PS_IRAT_HO_SR" };
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpHrCell", 9));

		return "hrCellQos3G";
	}
}