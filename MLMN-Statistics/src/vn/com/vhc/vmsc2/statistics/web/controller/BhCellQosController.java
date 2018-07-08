package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnCellBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkCellBhDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.Cell;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellBh;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnCellBh;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkCellBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
public class BhCellQosController extends BaseController {
	@Autowired
	private VRpDyCellBhDAO vRpDyCellBhDao;
	@Autowired
	private VRpWkCellBhDAO vRpWkCellBhDao;
	@Autowired
	private VRpMnCellBhDAO vRpMnCellBhDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private CellDAO cellDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("/report/radio/cell/dy/bh")
	public ModelAndView dyList(@RequestParam(required = true) String cellid, @RequestParam(required = true) String bscid,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();
		try {
			Date startDay, endDay;
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
			List<VRpDyCellBh> vRpDyCellBhList = vRpDyCellBhDao.filter(df.format(startDay), df.format(endDay), bscid, cellid);

			model.addAttribute("cellid", cellid);
			model.addAttribute("bscid", bscid);
			List<Bsc> bscList = bscDao.getAll();
			model.addAttribute("bscList", bscList);
			List<Cell> cellList = cellDao.getCellOfBsc(bscid);
			model.addAttribute("cellList", cellList);
			model.addAttribute("endDate", df.format(endDay));
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("vRpDyCellBhList", vRpDyCellBhList);

			List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
			model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyCellBh"));
			
			/* Start Chart */
			List<String> categories = new ArrayList<String>();
			List<Float> bhCssrList = new ArrayList<Float>();
			List<Float> bhTNblksList = new ArrayList<Float>();
			List<Float> bhTNblkrList = new ArrayList<Float>();
			List<Float> bhTDrpsList = new ArrayList<Float>();
			List<Float> bhTDrprList = new ArrayList<Float>();
			List<Float> bhTTrafList = new ArrayList<Float>();
			List<Float> bhTTrafhList = new ArrayList<Float>();
			List<Float> bhTGosList = new ArrayList<Float>();
			List<Float> bhSBlksList = new ArrayList<Float>();
			List<Float> bhSBlkrList = new ArrayList<Float>();
			List<Float> bhSDrpsList = new ArrayList<Float>();
			List<Float> bhSDrprList = new ArrayList<Float>();
			for (VRpDyCellBh vRpDyCellBh : vRpDyCellBhList) {
				categories.add(df.format(vRpDyCellBh.getDay()));
				bhCssrList.add(vRpDyCellBh.getBhCssr());
				bhTNblksList.add(vRpDyCellBh.getBhTNblks());
				bhTNblkrList.add(vRpDyCellBh.getBhTNblkr());
				bhTDrpsList.add(vRpDyCellBh.getBhTDrps());
				bhTDrprList.add(vRpDyCellBh.getBhTDrpr());
				bhTTrafList.add(vRpDyCellBh.getBhTTraf());
				bhTTrafhList.add(vRpDyCellBh.getBhTTrafh());
				bhTGosList.add(vRpDyCellBh.getBhTGos());
				bhSBlksList.add(vRpDyCellBh.getBhSBlks());
				bhSBlkrList.add(vRpDyCellBh.getBhSBlkr());
				bhSDrpsList.add(vRpDyCellBh.getBhSDrps());
				bhSDrprList.add(vRpDyCellBh.getBhSDrpr());
			}
			Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
			series.put("TRAF", bhTTrafList);
			series.put("H_TRAF", bhTTrafhList);
			series.put("T_DRPS", bhTDrpsList);
			series.put("T_DRPR (%)", bhTDrprList);
			series.put("T_NBLKS", bhTNblksList);
			series.put("T_NBLKR (%)", bhTNblkrList);
			series.put("CSSR (%)", bhCssrList);
			series.put("GoS (%)", bhTGosList);
			series.put("S_DRPS", bhSDrpsList);
			series.put("S_DRPR (%)", bhSDrprList);
			series.put("S_BLKS", bhSBlksList);
			series.put("S_BLKR (%)", bhSBlkrList);

			model.addAttribute("chart", Chart.comboDualAxes("CELL BH " + cellid + " Daily Report", categories, series));
			
			// checkBox
			String[] checkColumns = {"T_DEF", "T_AVAIL", "T_ATT", "T_NBLKR", "T_NBLKS", "T_HOBLKR", "T_HOBLKS", "CSSR", "T_DRPR", "T_DRPS", "TRAF", "H_TRAF", "GoS", "S_DEF", "S_AVAIL", "S_ATT", "S_BLKR", "S_BLKS","S_DRPR", "S_DRPS"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpDyCellBh", 9));

		} catch (ParseException e) {
			saveError(request, e.toString());
		}
		return new ModelAndView("dyCellBh");
	}

	@RequestMapping("/report/radio/cell/mn/bh")
	public ModelAndView mnList(@RequestParam(required = true) String cellid, @RequestParam(required = true) String bscid,
			@RequestParam(required = false) Integer startMonth,@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear,
			ModelMap model, HttpServletRequest request) {
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		if(endMonth==null){
			cal.add(Calendar.MONTH,-1);
			endMonth = cal.get(Calendar.MONTH)+1;
			endYear = cal.get(Calendar.YEAR);
		}
		
		if (startMonth == null) {
			if (endMonth > 3) {
				startMonth = endMonth -3;
				startYear = endYear;
			}
			else {
				cal1.add(Calendar.MONTH,-3);
				startMonth = cal1.get(Calendar.MONTH)+1;
				startYear = cal1.get(Calendar.YEAR);
			}
		}
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);

		List<VRpMnCellBh> vRpMnCellBhList = vRpMnCellBhDao.filter(startMonth,startYear, endMonth, endYear, bscid, cellid);

		model.addAttribute("vRpMnCellBhList", vRpMnCellBhList);
		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpMnCellBh"));
		
		/* Start Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> bhCssrList = new ArrayList<Float>();
		List<Float> bhTNblksList = new ArrayList<Float>();
		List<Float> bhTNblkrList = new ArrayList<Float>();
		List<Float> bhTDrpsList = new ArrayList<Float>();
		List<Float> bhTDrprList = new ArrayList<Float>();
		List<Float> bhTTrafList = new ArrayList<Float>();
		List<Float> bhTTrafhList = new ArrayList<Float>();
		List<Float> bhTGosList = new ArrayList<Float>();
		List<Float> bhSBlksList = new ArrayList<Float>();
		List<Float> bhSBlkrList = new ArrayList<Float>();
		List<Float> bhSDrpsList = new ArrayList<Float>();
		List<Float> bhSDrprList = new ArrayList<Float>();
		for (VRpMnCellBh vRpMnCellBh : vRpMnCellBhList) {
			categories.add(vRpMnCellBh.getMonth()+"/"+vRpMnCellBh.getYear());
			bhCssrList.add(vRpMnCellBh.getBhCssr());
			bhTNblksList.add(vRpMnCellBh.getBhTNblks());
			bhTNblkrList.add(vRpMnCellBh.getBhTNblkr());
			bhTDrpsList.add(vRpMnCellBh.getBhTDrps());
			bhTDrprList.add(vRpMnCellBh.getBhTDrpr());
			bhTTrafList.add(vRpMnCellBh.getBhTTraf());
			bhTTrafhList.add(vRpMnCellBh.getBhTTrafh());
			bhTGosList.add(vRpMnCellBh.getBhTGos());
			bhSBlksList.add(vRpMnCellBh.getBhSBlks());
			bhSBlkrList.add(vRpMnCellBh.getBhSBlkr());
			bhSDrpsList.add(vRpMnCellBh.getBhSDrps());
			bhSDrprList.add(vRpMnCellBh.getBhSDrpr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("TRAF", bhTTrafList);
		series.put("H_TRAF", bhTTrafhList);
		series.put("T_DRPS", bhTDrpsList);
		series.put("T_DRPR (%)", bhTDrprList);
		series.put("T_NBLKS", bhTNblksList);
		series.put("T_NBLKR (%)", bhTNblkrList);
		series.put("CSSR (%)", bhCssrList);
		series.put("GoS (%)", bhTGosList);
		series.put("S_DRPS", bhSDrpsList);
		series.put("S_DRPR (%)", bhSDrprList);
		series.put("S_BLKS", bhSBlksList);
		series.put("S_BLKR (%)", bhSBlkrList);

		model.addAttribute("chart", Chart.comboDualAxes("CELL BH " + cellid + " Monthly Report", categories, series));
		
		// checkBox
		String[] checkColumns = {"T_DEF", "T_AVAIL", "T_ATT", "T_NBLKR", "T_NBLKS", "T_HOBLKR", "T_HOBLKS", "CSSR", "T_DRPR", "T_DRPS", "TRAF", "H_TRAF", "GoS", "S_DEF", "S_AVAIL", "S_ATT", "S_BLKR", "S_BLKS","S_DRPR", "S_DRPS"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpMnCellBh", 6));
		
		return new ModelAndView("mnCellBh");
	}

	@RequestMapping("/report/radio/cell/wk/bh")
	public ModelAndView wkList(@RequestParam(required = true) String cellid, @RequestParam(required = true) String bscid,
			@RequestParam(required = false) Integer startWeek,@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear,
			ModelMap model, HttpServletRequest request) {
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		if(endWeek==null){
			cal.add(Calendar.WEEK_OF_YEAR, -2);
			endWeek = cal.get(Calendar.WEEK_OF_YEAR);
			endYear = cal.get(Calendar.YEAR);
		}
		
		if (startWeek == null) {
			if (endWeek > 4) {
				startWeek = endWeek -4;
				startYear = endYear;
			}
			else {
				cal1.add(Calendar.WEEK_OF_YEAR,-6);
				startWeek = cal1.get(Calendar.WEEK_OF_YEAR);
				startYear = cal1.get(Calendar.YEAR);
			}
		}
		model.addAttribute("startWeek", startWeek);
		model.addAttribute("endWeek", endWeek);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);

		List<VRpWkCellBh> vRpWkCellBhList = vRpWkCellBhDao.filter(startWeek, startYear,endWeek, endYear, bscid, cellid);

		model.addAttribute("vRpWkCellBhList", vRpWkCellBhList);
		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpWkCellBh"));
		
		/* Start Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> bhCssrList = new ArrayList<Float>();
		List<Float> bhTNblksList = new ArrayList<Float>();
		List<Float> bhTNblkrList = new ArrayList<Float>();
		List<Float> bhTDrpsList = new ArrayList<Float>();
		List<Float> bhTDrprList = new ArrayList<Float>();
		List<Float> bhTTrafList = new ArrayList<Float>();
		List<Float> bhTTrafhList = new ArrayList<Float>();
		List<Float> bhTGosList = new ArrayList<Float>();
		List<Float> bhSBlksList = new ArrayList<Float>();
		List<Float> bhSBlkrList = new ArrayList<Float>();
		List<Float> bhSDrpsList = new ArrayList<Float>();
		List<Float> bhSDrprList = new ArrayList<Float>();
		for (VRpWkCellBh vRpWkCellBh : vRpWkCellBhList) {
			categories.add(vRpWkCellBh.getWeek()+"/"+vRpWkCellBh.getYear());
			bhCssrList.add(vRpWkCellBh.getBhCssr());
			bhTNblksList.add(vRpWkCellBh.getBhTNblks());
			bhTNblkrList.add(vRpWkCellBh.getBhTNblkr());
			bhTDrpsList.add(vRpWkCellBh.getBhTDrps());
			bhTDrprList.add(vRpWkCellBh.getBhTDrpr());
			bhTTrafList.add(vRpWkCellBh.getBhTTraf());
			bhTTrafhList.add(vRpWkCellBh.getBhTTrafh());
			bhTGosList.add(vRpWkCellBh.getBhTGos());
			bhSBlksList.add(vRpWkCellBh.getBhSBlks());
			bhSBlkrList.add(vRpWkCellBh.getBhSBlkr());
			bhSDrpsList.add(vRpWkCellBh.getBhSDrps());
			bhSDrprList.add(vRpWkCellBh.getBhSDrpr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("TRAF", bhTTrafList);
		series.put("H_TRAF", bhTTrafhList);
		series.put("T_DRPS", bhTDrpsList);
		series.put("T_DRPR (%)", bhTDrprList);
		series.put("T_NBLKS", bhTNblksList);
		series.put("T_NBLKR (%)", bhTNblkrList);
		series.put("CSSR (%)", bhCssrList);
		series.put("GoS (%)", bhTGosList);
		series.put("S_DRPS", bhSDrpsList);
		series.put("S_DRPR (%)", bhSDrprList);
		series.put("S_BLKS", bhSBlksList);
		series.put("S_BLKR (%)", bhSBlkrList);

		model.addAttribute("chart", Chart.comboDualAxes("CELL BH " + cellid + " Monthly Report", categories, series));

		// checkBox
		String[] checkColumns = {"T_DEF", "T_AVAIL", "T_ATT", "T_NBLKR", "T_NBLKS", "T_HOBLKR", "T_HOBLKS", "CSSR", "T_DRPR", "T_DRPS", "TRAF", "H_TRAF", "GoS", "S_DEF", "S_AVAIL", "S_ATT", "S_BLKR", "S_BLKS","S_DRPR", "S_DRPS"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpWkCellBh", 6));

		return new ModelAndView("wkCellBh");
	}
}
