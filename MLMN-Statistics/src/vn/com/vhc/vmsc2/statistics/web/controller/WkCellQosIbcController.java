package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import vn.com.vhc.vmsc2.statistics.dao.VRpWkCellIbcDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.Cell;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkCellIbc;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
@RequestMapping("/report/radio/ibc/cell/wk/*")
public class WkCellQosIbcController extends BaseController {
	@Autowired
	private VRpWkCellIbcDAO vRpWkCellIbcDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private CellDAO cellDao;

	@RequestMapping("list")
	public ModelAndView wkList(@RequestParam(required = false) String region,@RequestParam(required = false) String cellid, @RequestParam(required = false) String province,
			@RequestParam(required = false) String bscid, @RequestParam(required = false) Integer startWeek,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek,@RequestParam(required = false) Integer endYear,
			ModelMap model, HttpServletRequest request) {
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

		if (region == null)
			region = getCenter("TT2");
		model.addAttribute("region", region);
		
		List<VRpWkCellIbc> vRpWkCellIbc = vRpWkCellIbcDao.filter(startWeek,startYear, endWeek, endYear, cellid, bscid, province, region);

		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);

		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		model.addAttribute("province", province);
		
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		model.addAttribute("vRpWkCellIbc", vRpWkCellIbc);

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpWkCellIbc"));

		return new ModelAndView("wkCellIbcList");
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

		List<VRpWkCellIbc> vRpWkCellIbcList = vRpWkCellIbcDao.filter(startWeek, startYear, endWeek, endYear, bscid, cellid);

		model.addAttribute("vRpWkCellIbcList", vRpWkCellIbcList);
		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);

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

		for (VRpWkCellIbc vRpWkCellIbc : vRpWkCellIbcList) {
			categories.add("w" + vRpWkCellIbc.getWeek().toString() + "/" + vRpWkCellIbc.getYear().toString());
			cssrList.add(vRpWkCellIbc.getCssr());
			tTrafList.add(vRpWkCellIbc.gettTraf());
			tDrprList.add(vRpWkCellIbc.gettDrpr());
			tBlkrList.add(vRpWkCellIbc.gettBlkr());
			sDrprList.add(vRpWkCellIbc.getsDrpr());
			sBlkrList.add(vRpWkCellIbc.getsBlkr());
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
		String[] checkColumns = { "T_TRAF", "T_DRP", "T_DRPR", "T_NBLKS", "T_NBLKR", "T_HOBLKS", "T_HOBLKR", "CSSR", "S_DRP", "S_DRPR", "S_BLK", "S_BLKR", "DATALOAD" };
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 8));

		return "wkCellIbcQos";
	}
}
