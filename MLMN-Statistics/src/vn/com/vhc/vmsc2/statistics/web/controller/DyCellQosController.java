package vn.com.vhc.vmsc2.statistics.web.controller;

import java.lang.reflect.Method;
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
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellQosBhDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.Cell;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCell;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellQosBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;
import vn.com.vhc.vmsc2.statistics.web.utils.ModelAddtributes;

@Controller
@RequestMapping("/report/radio/cell/dy/*")
public class DyCellQosController extends BaseController {
	@Autowired
	private VRpDyCellDAO vRpDyCellDao;
	@Autowired
	private VRpDyCellQosBhDAO vRpDyCellQosBhDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private CellDAO cellDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("list")
	public ModelAndView dyList(@RequestParam(required = false) String region, @RequestParam(required = false) String cellid,
			@RequestParam(required = false) String province, @RequestParam(required = false) String bscid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model, HttpServletRequest request) {

		String[] s = ModelAddtributes.checkDate(model, startDate, endDate).split(";");

		if (cellid == null || cellid.length() == 0 || cellid.contains(" ") == false) {

			int startRecord = 0;
			int order = 1;
			String column = "DAY, REGION, PROVINCE, BSCID, CELLID";
			try {
				startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("vRpDyCell").encodeParameterName(TableTagParameters.PARAMETER_PAGE))))
						- 1) * 100;
				order = Integer.parseInt(request.getParameter((new ParamEncoder("vRpDyCell").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
				column = request.getParameter((new ParamEncoder("vRpDyCell").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
			} catch (NumberFormatException e) {
			}
			int endRecord = startRecord + 100;

			List<VRpDyCellQosBh> vRpDyCellQosBh = vRpDyCellQosBhDao.filter(s[0], s[1], cellid, province, bscid, region, startRecord, endRecord, column,
					order == 1 ? "ASC" : "DESC");
			Integer totalSize = vRpDyCellQosBhDao.countRow(s[0], s[1], cellid, province, bscid, region);
			model.addAttribute("totalSize", totalSize);

			List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
			model.addAttribute("provinceList", provinceList);
			List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
			model.addAttribute("regionList", regionList);

			model.addAttribute("region", region);
			model.addAttribute("province", province);
			model.addAttribute("bscid", bscid);
			model.addAttribute("cellid", cellid);

			List<Bsc> bscList = bscDao.getAll();
			model.addAttribute("bscList", bscList);

			model.addAttribute("startDate", s[0]);
			model.addAttribute("endDate", s[1]);
			model.addAttribute("vRpDyCellList", vRpDyCellQosBh);

			List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
			model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyCell"));
		} else {
			String tempCellId = cellid;
			cellid = cellid.replace(",", " ");
			while (cellid.contains("  ")) {
				cellid = cellid.replace("  ", " ");
			}
			cellid = cellid.toUpperCase();
			String[] strCellId = cellid.split(" ");

			cellid = "'";
			for (int i = 0; i < strCellId.length - 1; i++) {
				cellid += strCellId[i].toString() + "','";
			}
			cellid += strCellId[strCellId.length - 1].toString() + "'";
			int startRecord = 0;
			int order = 1;
			String column = "DAY, REGION, PROVINCE, BSCID, CELLID";
			try {
				startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("vRpDyCell").encodeParameterName(TableTagParameters.PARAMETER_PAGE))))
						- 1) * 100;
				order = Integer.parseInt(request.getParameter((new ParamEncoder("vRpDyCell").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
				column = request.getParameter((new ParamEncoder("vRpDyCell").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
			} catch (NumberFormatException e) {
			}
			int endRecord = startRecord + 100;

			List<VRpDyCellQosBh> vRpDyCellQosBh = vRpDyCellQosBhDao.filterArray(s[0], s[1], cellid, province, bscid, region, startRecord, endRecord, column,
					order == 1 ? "ASC" : "DESC");
			Integer totalSize = vRpDyCellQosBhDao.countRowArray(s[0], s[1], cellid, province, bscid, region);
			model.addAttribute("totalSize", totalSize);

			List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
			model.addAttribute("provinceList", provinceList);

			List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
			model.addAttribute("regionList", regionList);

			model.addAttribute("region", region);
			model.addAttribute("province", province);
			model.addAttribute("bscid", bscid);
			model.addAttribute("cellid", tempCellId);

			List<Bsc> bscList = bscDao.getAll();
			model.addAttribute("bscList", bscList);

			model.addAttribute("vRpDyCellList", vRpDyCellQosBh);

			List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
			model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyCell"));
		}

		return new ModelAndView("dyCellList");
	}

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = true) String bscid, @RequestParam(required = true) String cellid,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();

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

			List<VRpDyCell> vRpDyCellList = vRpDyCellDao.filter(df.format(startDay), df.format(endDay), bscid, cellid);

			model.addAttribute("vRpDyCellList", vRpDyCellList);
			model.addAttribute("cellid", cellid);
			model.addAttribute("bscid", bscid);
			List<Bsc> bscList = bscDao.getAll();
			model.addAttribute("bscList", bscList);
			List<Cell> cellList = cellDao.getCellOfBsc(bscid);
			model.addAttribute("cellList", cellList);

			List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
			model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyCell"));

			// neu tim kiem theo ten cell cu the thi xuat bieu do
			if (bscid != null && bscid.length() > 0 && cellid != null && cellid.length() > 0) {
				List<String> categories = new ArrayList<String>();
				List<Float> tDefList = new ArrayList<Float>();
				List<Float> tAvailList = new ArrayList<Float>();
				List<Float> cssrList = new ArrayList<Float>();
				List<Float> tTrafList = new ArrayList<Float>();
				List<Float> tTrafhList = new ArrayList<Float>();
				List<Float> tAttList = new ArrayList<Float>();
				List<Float> tDrpsList = new ArrayList<Float>();
				List<Float> tDrprList = new ArrayList<Float>();
				List<Float> tBlkrList = new ArrayList<Float>();
				List<Float> sAttList = new ArrayList<Float>();
				List<Float> sDrpsList = new ArrayList<Float>();
				List<Float> sDrprList = new ArrayList<Float>();
				List<Float> sBlksList = new ArrayList<Float>();
				List<Float> sBlkrList = new ArrayList<Float>();
				List<Float> cssrBaseline = new ArrayList<Float>();
				List<Float> cdrBaseline = new ArrayList<Float>();
				for (VRpDyCell vRpDyCell : vRpDyCellList) {
					categories.add(df.format(vRpDyCell.getDay()));
					tDefList.add(Helper.int2Float(vRpDyCell.gettDef()));
					tAvailList.add(Helper.int2Float(vRpDyCell.gettAvail()));
					cssrList.add(vRpDyCell.getCssr());
					tTrafList.add(vRpDyCell.gettTraf());
					tTrafhList.add(vRpDyCell.gettTrafh());
					tAttList.add(Helper.int2Float(vRpDyCell.gettAtts()));
					tDrpsList.add(Helper.int2Float(vRpDyCell.gettDrps()));
					tDrprList.add(vRpDyCell.gettDrpr());
					tBlkrList.add(vRpDyCell.gettBlkr());
					sAttList.add(Helper.int2Float(vRpDyCell.getsAtts()));
					sDrpsList.add(Helper.int2Float(vRpDyCell.getsDrps()));
					sDrprList.add(vRpDyCell.getsDrpr());
					sBlksList.add(Helper.int2Float(vRpDyCell.getsBlks()));
					sBlkrList.add(vRpDyCell.getsBlkr());
					cssrBaseline.add((float) 95);
					cdrBaseline.add((float) 3);
				}

				Map<String, List<Float>> availSeries = new LinkedHashMap<String, List<Float>>();
				availSeries.put("T_DEF", tDefList);
				availSeries.put("T_AVAIL", tAvailList);
				model.addAttribute("availChart", Chart.multiColumn("availChart", "Define-Avail TCH", categories, availSeries));

				Map<String, List<Float>> trafSeries = new LinkedHashMap<String, List<Float>>();
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
			String[] checkColumns = { "T_DEF", "T_AVAIL", "T_ATT", "T_NBLKR", "T_NBLK", "T_HOBLKR", "T_HOBLK", "T_SEIZ", "CSSR", "T_DRPR", "T_DRP", "T_TRAFF",
					"H_TRAFF", "S_DEF", "S_AVAIL", "S_ATT", "S_BLKR", "S_BLK", "S_SEIZ", "S_DRPR", "S_DRP", "DATALOAD" };
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpDyCell", 8));
			
		} catch (ParseException e) {
			saveError(request, e.toString());
		}

		return "dyCellQos";
	}

	// TRUNGNQ bieu do gia tri kpi (cssr, cdr, ...) cua mot cell 30 ngay gan nhat, ten kpi lay theo ten field
	@RequestMapping("chart")
	public String showChart(@RequestParam(required = true) String bscid, @RequestParam(required = true) String cellid,
			@RequestParam(required = true) String kpi, ModelMap model) {

		String endDate = df.format(new Date(new Date().getTime() - 24 * 60 * 60 * 1000));
		String startDate = df.format(new Date(new Date().getTime() - 31 * 24 * 60 * 60 * 1000));
		List<VRpDyCell> vRpDyCellList = vRpDyCellDao.filter(startDate, endDate, bscid, cellid);
		List<String> categories = new ArrayList<String>();
		List<Float> kpiValue = new ArrayList<Float>();
		String methodName = "get" + kpi.substring(0, 1).toUpperCase() + kpi.substring(1);
		Class<VRpDyCell> vRpDyCellClass = VRpDyCell.class;		
		try {
			Method getKpiValueMethod = vRpDyCellClass.getMethod(methodName);
			for (VRpDyCell vRpDyCell : vRpDyCellList) {
				categories.add(df.format(vRpDyCell.getDay()));
				kpiValue.add((Float) getKpiValueMethod.invoke(vRpDyCell));
			}
			Map<String, List<Float>>  series = new LinkedHashMap<String, List<Float>>();
			series.put("CSSR (%)--89a54e", kpiValue);
			model.addAttribute("chart", Chart.multiColumn("chart", "CSSR (%)", categories, series));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		//Method getKpiValueMethod = Class.forName("VRpDyCell").g
		
		return "dyCellChart";
	}

}
