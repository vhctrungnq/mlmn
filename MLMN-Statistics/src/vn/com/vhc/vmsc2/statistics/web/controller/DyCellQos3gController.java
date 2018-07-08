package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCell3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellBh3GDAO;
import vn.com.vhc.vmsc2.statistics.domain.Cell3G;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCell3G;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellBh3G;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;
import vn.com.vhc.vmsc2.statistics.web.utils.ModelAddtributes;

@Controller
@RequestMapping("/report/radio3g/cell/dy/*")
public class DyCellQos3gController extends BaseController {
	@Autowired
	private VRpDyCell3GDAO vRpDyCellDao;
	@Autowired
	private VRpDyCellBh3GDAO vRpDyCellBhDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private Bsc3GDAO bsc3g;	
	@Autowired
	private Cell3GDAO cell3GDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("list")
	public String dyList(@RequestParam(required = false) String region,@RequestParam(required = false) String function, @RequestParam(required = false) String cellid,
			@RequestParam(required = false) String province, @RequestParam(required = false) String bscid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model, HttpServletRequest request) {
		
		String[] s = ModelAddtributes.checkDate(model, startDate, endDate).split(";");
		int startRecord = 0;
		int order = 1;
		int totalSize;
		List<VRpDyCell3G> vRpDyCell;
		String column = "DAY, REGION, PROVINCE, VENDOR, BSCID, CELLID";
		
		if(function==null) {
			function ="";
		}
			
		if(function.equals("")) {
			model.addAttribute("width", "width:200%");
		} else {
			model.addAttribute("width", "width:100%");
		}

		model.addAttribute("function", function);
		if (request.getParameter(new ParamEncoder("vRpDyCell").encodeParameterName(TableTagParameters.PARAMETER_PAGE)) != null) {
			startRecord = (Integer.parseInt( request.getParameter(new ParamEncoder("vRpDyCell").encodeParameterName(TableTagParameters.PARAMETER_PAGE))) - 1) * 100;	
		}
		
		if (request.getParameter((new ParamEncoder("vRpDyCell").encodeParameterName(TableTagParameters.PARAMETER_ORDER))) != null) {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("vRpDyCell").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
		}
		String columnIsChoiced = request.getParameter((new ParamEncoder("vRpDyCell").encodeParameterName(TableTagParameters.PARAMETER_SORT)));		
		column = (columnIsChoiced != null && !columnIsChoiced.equals("")) ? columnIsChoiced : column;
		int endRecord = startRecord + 100;

		if (region == null) {
			region = "";
		}
			
		
		if (cellid ==null || cellid.equals("") || !cellid.contains(" ")) {
			vRpDyCell = vRpDyCellDao.filter(s[0],s[1], cellid, province, bscid, region, startRecord,
					endRecord, column, order == 1 ? "ASC" : "DESC");
			totalSize = vRpDyCellDao.countRow(s[0],s[1], cellid, province, bscid, region);
		} else {
			String tempCellId = cellid;
			String[] strCellId = cellid.split(" ");

			cellid = "'";
			for (int i = 0; i < strCellId.length - 1; i++) {
				cellid += strCellId[i].toString() + "','";
			}
			cellid += strCellId[strCellId.length - 1].toString() + "'";
			List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
			model.addAttribute("regionList", regionList);

			vRpDyCell = vRpDyCellDao.filterArray(s[0],s[1], cellid, province, bscid, region, startRecord,
					endRecord, column, order == 1 ? "ASC" : "DESC");
			totalSize = vRpDyCellDao.countRowArray(s[0],s[1], cellid, province, bscid, region);
			cellid = tempCellId;
		}

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);

		model.addAttribute("totalSize", totalSize);

		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);

		model.addAttribute("province", province);
		model.addAttribute("bscid", bscid);
		model.addAttribute("cellid", cellid);
		List<String> bscList = bsc3g.getAllBscName();
		model.addAttribute("bscList", bscList);
		model.addAttribute("vRpDyCellList", vRpDyCell);
		model.addAttribute("region", region);
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyCell"));
		return "dyCell3GList";
	}

	@RequestMapping("bhList")
	public String dyBhList(@RequestParam(required = false) String region,@RequestParam(required = false) String function, @RequestParam(required = false) String cellid,
			@RequestParam(required = false) String province, @RequestParam(required = false) String bscid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model, HttpServletRequest request) {
		
		if (bscid != null && bscid.length() > 0  && cellid != null && cellid.length() > 0) {
			return "redirect:bhDetails.htm?bscid=" + bscid + "&cellid=" + cellid + "&function=" + function;
		}
		model.addAttribute("function", function);
		if(function.equals(""))
			model.addAttribute("width", "width:200%");
		else
			model.addAttribute("width", "width:100%");
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime);
		}
		if (startDate == null) {
			startDate = endDate;
		}

		int startRecord = 0;
		int order = 1;
		String column = "DAY, REGION, PROVINCE, VENDOR, BSCID, SITE_NAME, CELLID";
		try {
			startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("vRpDyCell").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
			order = Integer.parseInt(request.getParameter((new ParamEncoder("vRpDyCell").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("vRpDyCell").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		int endRecord = startRecord + 100;

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";

		List<VRpDyCellBh3G> vRpDyCell = vRpDyCellBhDao.filter(df.format(startDate), df.format(endDate), cellid, province, bscid, region, startRecord,
				endRecord, column, order == 1 ? "ASC" : "DESC");
		Integer totalSize = vRpDyCellBhDao.countRow(df.format(startDate), df.format(endDate), cellid, province, bscid, region);
		model.addAttribute("totalSize", totalSize);

		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);

		model.addAttribute("province", province);
		model.addAttribute("bscid", bscid);
		model.addAttribute("cellid", cellid);

		List<String> bscList = bsc3g.getAllBscName();
		model.addAttribute("bscList", bscList);

		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("vRpDyCellList", vRpDyCell);

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyCell"));
		model.addAttribute("region", region);

		return "dyCellBh3GList";
	}

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = false) String bscid,@RequestParam(required = false) String function, @RequestParam(required = false) String cellName,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request) {
		
		long currentTime = System.currentTimeMillis();

		model.addAttribute("function", function);
		try {
			Date startDay;
			Date endDay;
			if (endDate == null) {
				endDay = new Date(currentTime);
			} else {
				endDay = df.parse(endDate);
			}
			if (startDate == null) {
				startDay = endDay;
			} else {
				startDay = df.parse(startDate);
			}
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("endDate", df.format(endDay));

			List<VRpDyCell3G> vRpDyCellList = vRpDyCellDao.filter(df.format(startDay), df.format(endDay), bscid, cellName);

			model.addAttribute("vRpDyCellList", vRpDyCellList);
			model.addAttribute("cellName", cellName);
			model.addAttribute("bscid", bscid);
			List<String> bscList = bsc3g.getAllBscName();
			model.addAttribute("bscList", bscList);
			List<String> cellList = cell3GDao.getCellids3G(bscid);
			model.addAttribute("cellList", cellList);

			List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
			model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyCell"));

			/* Chart Start */
			if (cellName != null && cellName.length() > 0) {
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
				for (VRpDyCell3G vRpDyCell : vRpDyCellList) {
					categories.add(df.format(vRpDyCell.getDay()));
					speechCssrList.add(vRpDyCell.getSpeechCssr());
					cs64CssrList.add(vRpDyCell.getCs64Cssr());
					psr99CssrList.add(vRpDyCell.getPsr99Cssr());
					hsupaCssrList.add(vRpDyCell.getHsupaCssr());
					hsdpaCssrList.add(vRpDyCell.getHsdpaCssr());
					speechDrprList.add(vRpDyCell.getSpeechDrpr());
					cs64DrprList.add(vRpDyCell.getCs64Drpr());
					psr99DrprList.add(vRpDyCell.getPsr99Drpr());
					hsupaDrprList.add(vRpDyCell.getHsupaDrpr());
					hsdpaDrprList.add(vRpDyCell.getHsdpaDrpr());
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
			}
			
			// checkBox
			String[] checkColumns = { "CELL_DOWNTIME", "HS_DOWNTIME", "EUL_DOWNTIME", "SPEECH_TRAFF", "CS64_TRAFF", "PSR99_UL_TRAFF", "PSR99_DL_TRAFF",
					"HSUPA_UL_TRAFF", "HSDPA_DL_TRAFF", "PSR99_UL_THROUGHTPUT", "PSR99_DL_THROUGHTPUT", "HSUPA_UL_THROUGHTPUT", "HSDPA_DL_THROUGHTPUT", "",
					"SPEECH_CSSR", "CS64_CSSR", "PSR99_CSSR", "HSUPA_CSSR", "HSDPA_CSSR", "SPEECH_DROP", "", "SPEECH_DRPR", "CS64_DROP", "CS64_DRPR",
					"PSR99_DROP", "PSR99_DRPR", "HSUPA_DROP", "HSUPA_DRPR", "HSDPA_DROP", "HSDPA_DRPR", "", "SPEECH_SHO_SR_OUT", "SHO_SR_OUT", "SHO_SR_IN",
					"CS_IRAT_HO_SR", "PS_IRAT_HO_SR", "DATALOAD" };
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpDyCell", 11));

		} catch (ParseException e) {
			saveError(request, e.toString());
		}

		return "dyCellQos3G";
	}

	@RequestMapping("bhDetails")
	public String showReportBhDetails(@RequestParam(required = false) String bscid, @RequestParam(required = false) String cellid,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request) {
		
		if (bscid == null || bscid.length() == 0  || cellid == null || cellid.length() == 0) {
			bscid = (bscid == null ? "" : bscid);
			cellid = (cellid == null ? "" : cellid);
			return "redirect:bhList.htm?bscid=" + bscid + "&cellid=" + cellid;
		}
		
		long currentTime = System.currentTimeMillis();

		try {
			Date startDay;
			Date endDay;
			if (endDate == null) {
				endDay = new Date(currentTime - 24 * 60 * 60 * 1000);
			} else {
				endDay = df.parse(endDate);
			}
			if (startDate == null) {
				DateTime dt = new DateTime(endDay);
				startDay = dt.minusDays(7).toDate();
			} else {
				startDay = df.parse(startDate);
			}
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("endDate", df.format(endDay));

			List<VRpDyCellBh3G> vRpDyCellList = vRpDyCellBhDao.filter(df.format(startDay), df.format(endDay), bscid, cellid);

			model.addAttribute("vRpDyCellList", vRpDyCellList);
			model.addAttribute("cellid", cellid);
			model.addAttribute("bscid", bscid);
			List<String> bscList = bsc3g.getAllBscName();
			model.addAttribute("bscList", bscList);
			List<Cell3G> cellList = cell3GDao.getCellOfBsc3G(bscid);
			model.addAttribute("cellList", cellList);

			List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
			model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyCell"));

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
			for (VRpDyCellBh3G vRpDyCell : vRpDyCellList) {
				categories.add(vRpDyCell.getHour() + df.format(vRpDyCell.getDay()));
				speechCssrList.add(vRpDyCell.getSpeechCssr());
				cs64CssrList.add(vRpDyCell.getCs64Cssr());
				psr99CssrList.add(vRpDyCell.getPsr99Cssr());
				hsupaCssrList.add(vRpDyCell.getHsupaCssr());
				hsdpaCssrList.add(vRpDyCell.getHsdpaCssr());
				speechDrprList.add(vRpDyCell.getSpeechDrpr());
				cs64DrprList.add(vRpDyCell.getCs64Drpr());
				psr99DrprList.add(vRpDyCell.getPsr99Drpr());
				hsupaDrprList.add(vRpDyCell.getHsupaDrpr());
				hsdpaDrprList.add(vRpDyCell.getHsdpaDrpr());
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
			String[] checkColumns = { "SPEECH_TRAFF", "CS64_TRAFF", "PSR99_UL_TRAFF", "PSR99_DL_TRAFF", "HSUPA_UL_TRAFF", "HSDPA_DL_TRAFF",
					"PSR99_UL_THROUGHTPUT", "PSR99_DL_THROUGHTPUT", "HSUPA_UL_THROUGHTPUT", "HSDPA_DL_THROUGHTPUT", "SPEECH_CSSR", "CS64_CSSR", "PSR99_CSSR",
					"HSUPA_CSSR", "HSDPA_CSSR", "SPEECH_DROP", "SPEECH_DRPR", "CS64_DROP", "CS64_DRPR", "PSR99_DROP", "PSR99_DRPR", "HSUPA_DROP", "HSUPA_DRPR",
					"HSDPA_DROP", "HSDPA_DRPR" };
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpDyCell", 12));

		} catch (ParseException e) {
			saveError(request, e.toString());
		}

		return "dyCellQosBh3G";
	}
}
