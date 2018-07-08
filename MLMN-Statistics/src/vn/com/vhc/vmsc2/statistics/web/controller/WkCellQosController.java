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
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.CellDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkCellDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.Cell;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkCell;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;

@Controller
@RequestMapping("/report/radio/cell/wk/*")
public class WkCellQosController extends BaseController {
	@Autowired
	private VRpWkCellDAO vRpWkCellDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private CellDAO cellDao;

	@RequestMapping("list")
	public ModelAndView wkList(@RequestParam(required = false) String region, @RequestParam(required = false) String cellid,
			@RequestParam(required = false) String province, @RequestParam(required = false) String bscid, @RequestParam(required = false) Integer startWeek,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek,
			@RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
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
		model.addAttribute("startWeek", startWeek);
		model.addAttribute("endWeek", endWeek);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);

		int startRecord = 0;
		int order = 1;
		String column = "YEAR, WEEK, REGION, PROVINCE, BSCID, CELLID";
		try {
			startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("vRpWkCell").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
			order = Integer.parseInt(request.getParameter((new ParamEncoder("vRpWkCell").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("vRpWkCell").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		int endRecord = startRecord + 100;

		List<VRpWkCell> vRpWkCell ;
		if (cellid == null|| cellid.length()==0 || cellid.contains(" ")==false) 
		{
			vRpWkCell = vRpWkCellDao.filter(startWeek, startYear, endWeek, endYear, cellid, bscid, province, region, startRecord, endRecord,
					column, order == 1 ? "ASC" : "DESC");
			
			model.addAttribute("cellid", cellid);
		}
		else
		{
			String tempCellId = cellid;
			cellid = cellid.replace(",", " ");
			while(cellid.equalsIgnoreCase("  "))
			{
				cellid = cellid.replace(" ", "  ");
			}
			cellid = cellid.toUpperCase();
			String[] strCellId = cellid.split(" ");
			cellid = "'";
			for (int i =0; i<strCellId.length-1;i++) {
				cellid += strCellId[i].toString()+"','";
			}
			cellid += strCellId[strCellId.length-1].toString()+"'";
			vRpWkCell = vRpWkCellDao.filter(startWeek, startYear, endWeek, endYear, cellid, bscid, province, region, startRecord, endRecord,
					column, order == 1 ? "ASC" : "DESC");
			model.addAttribute("cellid", tempCellId);
		}
		Integer totalSize = vRpWkCellDao.countRow(startWeek, startYear, endWeek, endYear, cellid, bscid, province, region);
		model.addAttribute("totalSize", totalSize);

		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);

		List<HProvincesCode> regionList=hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		model.addAttribute("bscid", bscid);
		model.addAttribute("province", province);
		model.addAttribute("region", region);

		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		model.addAttribute("vRpWkCell", vRpWkCell);

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpWkCell"));

		return new ModelAndView("wkCellList");
	}

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = true) String cellid, @RequestParam(required = true) String bscid,
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
		model.addAttribute("startWeek", startWeek);
		model.addAttribute("endWeek", endWeek);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);

		List<VRpWkCell> vRpWkCellList = vRpWkCellDao.filter(startWeek, startYear, endWeek, endYear, bscid, cellid);

		model.addAttribute("vRpWkCellList", vRpWkCellList);
		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpWkCell"));

		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> cssrList = new ArrayList<Float>();
		List<Float> tTrafList = new ArrayList<Float>();
		List<Float> tDrprList = new ArrayList<Float>();
		List<Float> tBlkrList = new ArrayList<Float>();
		List<Float> sDrprList = new ArrayList<Float>();
		List<Float> sBlkrList = new ArrayList<Float>();
		List<Float> cssrBaseline = new ArrayList<Float>();
		List<Float> cdrBaseline = new ArrayList<Float>();

		for (VRpWkCell vRpWkCell : vRpWkCellList) {
			categories.add("w" + vRpWkCell.getWeek().toString() + "/" + vRpWkCell.getYear().toString());
			cssrList.add(vRpWkCell.getCssr());
			tTrafList.add(vRpWkCell.gettTraf());
			tDrprList.add(vRpWkCell.gettDrpr());
			tBlkrList.add(vRpWkCell.gettBlkr());
			sDrprList.add(vRpWkCell.getsDrpr());
			sBlkrList.add(vRpWkCell.getsBlkr());
			cssrBaseline.add((float) 95);
			cdrBaseline.add((float) 3);
		}
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

		// checkBox
		String[] checkColumns = { "T_TRAF", "T_NBLKR", "T_NBLKS", "T_HOBLKS", "T_HOBLKR", "CSSR", "T_DRPR", "T_DRPS", "S_BLKR", "S_BLKS", "S_DRPR", "S_DRPS", "DATALOAD" };
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpWkCell", 8));

		return "wkCellQos";
	}
}
