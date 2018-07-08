package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.Bsc3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.Cell3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkCell3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkCellBh3GDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc3G;
import vn.com.vhc.vmsc2.statistics.domain.Cell3G;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkCell3G;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkCellBh3G;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
@RequestMapping("/report/radio3g/cell/wk/*")
public class WkCellQos3gController extends BaseController {
	@Autowired
	private VRpWkCell3GDAO vRpWkCellDao;
	@Autowired
	private VRpWkCellBh3GDAO vRpWkCellBhDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private Bsc3GDAO bsc3GDao;
	@Autowired
	private Cell3GDAO cell3GDao;

	@RequestMapping("list")
	public String wkList(@RequestParam(required = false) String region,@RequestParam(required = false) String function,@RequestParam(required = false) String cellid, @RequestParam(required = false) String province,
			@RequestParam(required = false) String bscid, @RequestParam(required = false) Integer startWeek,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek,@RequestParam(required = false) Integer endYear,
			ModelMap model, HttpServletRequest request) {
		
		if (bscid != null && bscid.length() > 0  && cellid != null && cellid.length() > 0) {
			return "redirect:detail.htm?bscid=" + bscid + "&cellid=" + cellid + "&function=" + function;
		}
		DateTime dt = new DateTime();		
		if (endWeek == null) {
			dt = dt.minusWeeks(1);
			endWeek = dt.getWeekOfWeekyear();
			endYear = dt.getYear();
		}
		if (startWeek == null) {
			startWeek = endWeek;
			startYear = endYear;
		}
		if(function==null)
			function ="";
		model.addAttribute("function", function);
		model.addAttribute("startWeek", startWeek);
		model.addAttribute("endWeek", endWeek);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);

		if (region == null)
			region = getCenter("TT2");
		model.addAttribute("region", region);
		
		int startRecord = 0;
		int order = 1;
		String column = "WEEK, YEAR, REGION, PROVINCE, VENDOR, BSCID, SITE_NAME, CELLID";
		try {
			startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("vRpWkCell").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
			order = Integer.parseInt(request.getParameter((new ParamEncoder("vRpWkCell").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("vRpWkCell").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		int endRecord = startRecord + 100;
		
		List<VRpWkCell3G> vRpWkCell = vRpWkCellDao.filter(startWeek,startYear, endWeek, endYear, cellid, bscid, province, region, startRecord, endRecord, column, order == 1 ? "ASC"
				: "DESC");

		Integer totalSize = vRpWkCellDao.countRow(startWeek,startYear, endWeek, endYear, cellid, bscid, province, region);
		model.addAttribute("totalSize", totalSize);
		
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		
		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);

		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		model.addAttribute("province", province);
		
		List<Bsc3G> bscList = bsc3GDao.getAllBsc();
		model.addAttribute("bscList", bscList);
		model.addAttribute("vRpWkCell", vRpWkCell);

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpWkCell"));

		return "wkCell3GList";
	}

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = true) String cellid,@RequestParam(required = false) String function, @RequestParam(required = true) String bscid,
			@RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		
		if (bscid == null || bscid.length() == 0  || cellid == null || cellid.length() == 0) {
			bscid = (bscid == null ? "" : bscid);
			cellid = (cellid == null ? "" : cellid);
			function = (function == null ? "" : function);
			return "redirect:list.htm?bscid=" + bscid + "&cellid=" + cellid + "&function=" + function;
		}
		DateTime dt = new DateTime();
		if (endWeek == null) {
			dt = dt.minusWeeks(1);
			endWeek = dt.getWeekOfWeekyear();
			endYear = dt.getYear();
		}
		if (startWeek == null) {
			dt = dt.minusWeeks(5);
			startWeek = dt.getWeekOfWeekyear();
			startYear = dt.getYear();
		}
		if(function==null)
			function ="";
		model.addAttribute("function", function);
		model.addAttribute("startWeek", startWeek);
		model.addAttribute("endWeek", endWeek);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);

		List<VRpWkCell3G> vRpWkCellList = vRpWkCellDao.filter(startWeek, startYear, endWeek, endYear, bscid, cellid);

		model.addAttribute("vRpWkCellList", vRpWkCellList);
		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		List<Bsc3G> bscList = bsc3GDao.getAllBsc();
		model.addAttribute("bscList", bscList);
		List<Cell3G> cellList = cell3GDao.getCellOfBsc3G(bscid);
		model.addAttribute("cellList", cellList);

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpWkCell"));

		/* Chart Start */
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
		for (VRpWkCell3G vRpWkCell : vRpWkCellList) {
			categories.add(vRpWkCell.getWeek()+"/"+vRpWkCell.getYear());
			speechCssrList.add(vRpWkCell.getSpeechCssr());
			cs64CssrList.add(vRpWkCell.getCs64Cssr());
			psr99CssrList.add(vRpWkCell.getPsr99Cssr());
			hsupaCssrList.add(vRpWkCell.getHsupaCssr());
			hsdpaCssrList.add(vRpWkCell.getHsdpaCssr());
			speechDrprList.add(vRpWkCell.getSpeechDrpr());
			cs64DrprList.add(vRpWkCell.getCs64Drpr());
			psr99DrprList.add(vRpWkCell.getPsr99Drpr());
			hsupaDrprList.add(vRpWkCell.getHsupaDrpr());
			hsdpaDrprList.add(vRpWkCell.getHsdpaDrpr());
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
		String[] checkColumns = {"CELL_DOWNTIME","HS_DOWNTIME","EUL_DOWNTIME","SPEECH_TRAFF","CS64_TRAFF","PSR99_UL_TRAFF","PSR99_DL_TRAFF","HSUPA_UL_TRAFF","HSDPA_DL_TRAFF","PSR99_UL_THROUGHTPUT","PSR99_DL_THROUGHTPUT","HSUPA_UL_THROUGHTPUT","HSDPA_DL_THROUGHTPUT","SPEECH_CSSR","CS64_CSSR","PSR99_CSSR","HSUPA_CSSR","HSDPA_CSSR","SPEECH_DROP","SPEECH_DRPR","CS64_DROP","CS64_DRPR","PSR99_DROP","PSR99_DRPR","HSUPA_DROP","HSUPA_DRPR","HSDPA_DROP","HSDPA_DRPR","SPEECH_SHO_SR_OUT","SHO_SR_OUT","SHO_SR_IN","CS_IRAT_HO_SR","PS_IRAT_HO_SR","DATALOAD"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpWkCell", 9));

		return "wkCellQos3G";
	}
	
	@RequestMapping("bhDetails")
	public String showReportBhDetails(@RequestParam(required = true) String cellid,@RequestParam(required = false) String function, @RequestParam(required = true) String bscid,
			@RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		DateTime dt = new DateTime();
		if (endWeek == null) {
			dt = dt.minusWeeks(1);
			endWeek = dt.getWeekOfWeekyear();
			endYear = dt.getYear();
		}
		if (startWeek == null) {
			dt = dt.minusWeeks(5);
			startWeek = dt.getWeekOfWeekyear();
			startYear = dt.getYear();
		}
		if(function==null)
			function ="";
		model.addAttribute("function", function);
		model.addAttribute("startWeek", startWeek);
		model.addAttribute("endWeek", endWeek);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);

		List<VRpWkCellBh3G> vRpWkCellList = vRpWkCellBhDao.filter(startWeek, startYear, endWeek, endYear, bscid, cellid);

		model.addAttribute("vRpWkCellList", vRpWkCellList);
		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		List<Bsc3G> bscList = bsc3GDao.getAllBsc();
		model.addAttribute("bscList", bscList);
		List<Cell3G> cellList = cell3GDao.getCellOfBsc3G(bscid);
		model.addAttribute("cellList", cellList);

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpWkCell"));

		/* Chart Start */
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
		// TRUNGNQ neu user nhap bsc va cell thi xuat ra bieu do chi tiet !!!
		if (bscid != null && bscid.length() > 0 && cellid != null && cellid.length() > 0) {
			for (VRpWkCellBh3G vRpWkCell : vRpWkCellList) {
				categories.add(vRpWkCell.getWeek()+"/"+vRpWkCell.getYear());
				speechCssrList.add(vRpWkCell.getSpeechCssr());
				cs64CssrList.add(vRpWkCell.getCs64Cssr());
				psr99CssrList.add(vRpWkCell.getPsr99Cssr());
				hsupaCssrList.add(vRpWkCell.getHsupaCssr());
				hsdpaCssrList.add(vRpWkCell.getHsdpaCssr());
				speechDrprList.add(vRpWkCell.getSpeechDrpr());
				cs64DrprList.add(vRpWkCell.getCs64Drpr());
				psr99DrprList.add(vRpWkCell.getPsr99Drpr());
				hsupaDrprList.add(vRpWkCell.getHsupaDrpr());
				hsdpaDrprList.add(vRpWkCell.getHsdpaDrpr());
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
		//String[] checkColumns = {"CELL_DOWNTIME","HS_DOWNTIME","EUL_DOWNTIME","SPEECH_TRAFF","CS64_TRAFF","PSR99_UL_TRAFF","PSR99_DL_TRAFF","HSUPA_UL_TRAFF","HSDPA_DL_TRAFF","PSR99_UL_THROUGHTPUT","PSR99_DL_THROUGHTPUT","HSUPA_UL_THROUGHTPUT","HSDPA_DL_THROUGHTPUT","SPEECH_CSSR","CS64_CSSR","PSR99_CSSR","HSUPA_CSSR","HSDPA_CSSR","SPEECH_DROP","SPEECH_DRPR","CS64_DROP","CS64_DRPR","PSR99_DROP","PSR99_DRPR","HSUPA_DROP","HSUPA_DRPR","HSDPA_DROP","HSDPA_DRPR","SPEECH_SHO_SR_OUT","SHO_SR_OUT","SHO_SR_IN","CS_IRAT_HO_SR","PS_IRAT_HO_SR"};
		String[] checkColumns = {"SPEECH_TRAFF","CS64_TRAFF","PSR99_UL_TRAFF","PSR99_DL_TRAFF","HSUPA_UL_TRAFF","HSDPA_DL_TRAFF","PSR99_UL_THROUGHTPUT","PSR99_DL_THROUGHTPUT","HSUPA_UL_THROUGHTPUT","HSDPA_DL_THROUGHTPUT","SPEECH_CSSR","CS64_CSSR","PSR99_CSSR","HSUPA_CSSR","HSDPA_CSSR","SPEECH_DROP","SPEECH_DRPR","CS64_DROP","CS64_DRPR","PSR99_DROP","PSR99_DRPR","HSUPA_DROP","HSUPA_DRPR","HSDPA_DROP","HSDPA_DRPR"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpWkCell", 9));

		return "wkCellQosBh3G";
	}
}
