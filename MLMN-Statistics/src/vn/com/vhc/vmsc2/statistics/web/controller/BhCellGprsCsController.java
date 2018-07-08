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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.CellDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellGprsCsBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnCellGprsCsBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkCellGprsCsBhDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.Cell;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellGprsCsBh;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnCellGprsCsBh;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkCellGprsCsBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
public class BhCellGprsCsController extends BaseController {
	@Autowired
	private VRpDyCellGprsCsBhDAO vRpDyCellGprsCsBhDao;
	@Autowired
	private VRpWkCellGprsCsBhDAO vRpWkCellGprsCsBhDao;
	@Autowired
	private VRpMnCellGprsCsBhDAO vRpMnCellGprsCsBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private CellDAO cellDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("/report/radio/cell-gprs-cs/dy/bh")
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
				startDay = new Date(endDay.getTime() + 25 * 24 * 60 * 60 * 1000);
			} else {
				startDay = df.parse(startDate);
			}
			List<VRpDyCellGprsCsBh> vRpDyCellGprsCsBh = vRpDyCellGprsCsBhDao.filter(df.format(startDay), df.format(endDay), bscid, cellid);
			List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
			model.addAttribute("provinceList", provinceList);
			model.addAttribute("cellid", cellid);
			model.addAttribute("bscid", bscid);
			List<Bsc> bscList = bscDao.getAll();
			model.addAttribute("bscList", bscList);
			List<Cell> cellList = cellDao.getCellOfBsc(bscid);
			model.addAttribute("cellList", cellList);
			model.addAttribute("endDate", df.format(endDay));
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("vRpDyCellGprsCsBh", vRpDyCellGprsCsBh);
			//start chart
			List<String> categories = new ArrayList<String>();
			List<Float> bhCsxLevel1List = new ArrayList<Float>();
			List<Float> bhCsxLevel2List = new ArrayList<Float>();
			List<Float> bhCsxLevel3List = new ArrayList<Float>();
			List<Float> bhCsxLevel4List = new ArrayList<Float>();
			List<Float> bhMcsxLevel1List = new ArrayList<Float>();
			List<Float> bhMcsxLevel2List=new ArrayList<Float>();
			List<Float> bhMcsxLevel3List = new ArrayList<Float>();
			List<Float> bhMcsxLevel4List=new ArrayList<Float>();
			List<Float> bhMcsxLevel5List = new ArrayList<Float>();
			List<Float> bhMcsxLevel6List = new ArrayList<Float>();
			List<Float> bhMcsxLevel7List=new ArrayList<Float>();
			List<Float> bhMcsxLevel8List=new ArrayList<Float>();
			List<Float> bhMcsxLevel9List=new ArrayList<Float>();
			
			for (VRpDyCellGprsCsBh vRpDyCell : vRpDyCellGprsCsBh) {
				categories.add(df.format(vRpDyCell.getDay()));
				bhCsxLevel1List.add(vRpDyCell.getBhCsxLevel1());
				bhCsxLevel2List.add(vRpDyCell.getBhCsxLevel2());
				bhCsxLevel3List.add(vRpDyCell.getBhCsxLevel3());
				bhCsxLevel4List.add(vRpDyCell.getBhCsxLevel4());
				bhMcsxLevel1List.add(vRpDyCell.getBhMcsxLevel1());
				bhMcsxLevel2List.add(vRpDyCell.getBhMcsxLevel2());
				bhMcsxLevel3List.add(vRpDyCell.getBhMcsxLevel3());
				bhMcsxLevel4List.add(vRpDyCell.getBhMcsxLevel4());
				bhMcsxLevel5List.add(vRpDyCell.getBhMcsxLevel5());
				bhMcsxLevel6List.add(vRpDyCell.getBhMcsxLevel6());
				bhMcsxLevel7List.add(vRpDyCell.getBhMcsxLevel7());
				bhMcsxLevel8List.add(vRpDyCell.getBhMcsxLevel8());
				bhMcsxLevel9List.add(vRpDyCell.getBhMcsxLevel9());
			}

			Map<String, List<Float>> csxLevel1Series = new LinkedHashMap<String, List<Float>>();
			csxLevel1Series.put("CSX Level1--80699b", bhCsxLevel1List);
			model.addAttribute("csxLevel1Chart", Chart.multiColumn("csxLevel1Chart", "CSX Level1", categories, csxLevel1Series));

			Map<String, List<Float>> csxLevel2Series = new LinkedHashMap<String, List<Float>>();
			csxLevel2Series.put("CSX Level2--80699b", bhCsxLevel2List);
			model.addAttribute("csxLevel2Chart", Chart.multiColumn("csxLevel2Chart", "CSX Level2", categories, csxLevel2Series));

			Map<String, List<Float>> csxLevel3Series = new LinkedHashMap<String, List<Float>>();
			csxLevel3Series.put("CSX Level3--80699b", bhCsxLevel3List);
			model.addAttribute("csxLevel3Chart", Chart.multiColumn("csxLevel3Chart", "CSX Level3", categories, csxLevel3Series));

			Map<String, List<Float>> csxLevel4Series = new LinkedHashMap<String, List<Float>>();
			csxLevel4Series.put("CSX Level4--80699b", bhCsxLevel4List);
			model.addAttribute("csxLevel4Chart", Chart.multiColumn("csxLevel4Chart", "CSX Level4", categories, csxLevel4Series));

			Map<String, List<Float>> mcsxLevel1Series = new LinkedHashMap<String, List<Float>>();
			mcsxLevel1Series.put("MCSX Level1--db843d", bhMcsxLevel1List);
			model.addAttribute("mcsxLevel1Chart", Chart.multiColumn("mcsxLevel1Chart", "MCSX Level1", categories, mcsxLevel1Series));

			Map<String, List<Float>> mcsxLevel2Series = new LinkedHashMap<String, List<Float>>();
			mcsxLevel2Series.put("MCSX Level2--80699b", bhMcsxLevel2List);
			model.addAttribute("mcsxLevel2Chart", Chart.multiColumn("mcsxLevel2Chart", "MCSX Level2", categories, mcsxLevel2Series));

			Map<String, List<Float>> mcsxLevel3Series = new LinkedHashMap<String, List<Float>>();
			mcsxLevel3Series.put("MCSX Level3--80699b", bhMcsxLevel3List);
			model.addAttribute("mcsxLevel3Chart", Chart.multiColumn("mcsxLevel3Chart", "MCSX Level3", categories, mcsxLevel3Series));

			Map<String, List<Float>> mcsxLevel4Series = new LinkedHashMap<String, List<Float>>();
			mcsxLevel4Series.put("MCSX Level4--db843d", bhMcsxLevel4List);
			model.addAttribute("mcsxLevel4Chart", Chart.multiColumn("mcsxLevel4Chart", "MCSX Level4", categories, mcsxLevel4Series));

			Map<String, List<Float>> mcsxLevel5Series = new LinkedHashMap<String, List<Float>>();
			mcsxLevel5Series.put("MCSX Level5--db843d", bhMcsxLevel5List);
			model.addAttribute("mcsxLevel5Chart", Chart.multiColumn("mcsxLevel5Chart", "MCSX Level5", categories, mcsxLevel5Series));

			Map<String, List<Float>> mcsxLevel6Series = new LinkedHashMap<String, List<Float>>();
			mcsxLevel6Series.put("MCSX Level6--80699b", bhMcsxLevel6List);
			model.addAttribute("mcsxLevel6Chart", Chart.multiColumn("mcsxLevel6Chart", "MCSX Level6", categories, mcsxLevel6Series));

			Map<String, List<Float>> mcsxLevel7Series = new LinkedHashMap<String, List<Float>>();
			mcsxLevel7Series.put("MCSX Level7--80699b", bhMcsxLevel7List);
			model.addAttribute("mcsxLevel7Chart", Chart.multiColumn("mcsxLevel7Chart", "MCSX Level7", categories, mcsxLevel7Series));

			Map<String, List<Float>> mcsxLevel8Series = new LinkedHashMap<String, List<Float>>();
			mcsxLevel8Series.put("MCSX Level8--db843d", bhMcsxLevel8List);
			model.addAttribute("mcsxLevel8Chart", Chart.multiColumn("mcsxLevel8Chart", "MCSX Level8", categories, mcsxLevel8Series));

			Map<String, List<Float>> mcsxLevel9Series = new LinkedHashMap<String, List<Float>>();
			mcsxLevel9Series.put("MCSX Level9--db843d", bhMcsxLevel9List);
			model.addAttribute("mcsxLevel9Chart", Chart.multiColumn("mcsxLevel9Chart", "MCSX Level9", categories, mcsxLevel9Series));

			// checkBox
			String[] checkColumns = { "csxLevel1", "csxLevel1", "csxLevel1", "csxLevel1", "mcsxLevel1", "mcsxLevel2", "mcsxLevel3", "mcsxLevel4", "mcsxLevel5",
					"mcsxLevel6", "mcsxLevel7", "mcsxLevel8", "mcsxLevel9" };
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpDyCellGprsCsBh", 6));

		} catch (ParseException e) {
			saveError(request, e.toString());
		}
		return new ModelAndView("dyCellGprsCsBh");
	}

	@RequestMapping("/report/radio/cell-gprs-cs/mn/bh")
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

		List<VRpMnCellGprsCsBh> vRpMnCellGprsCsBh = vRpMnCellGprsCsBhDao.filter(startMonth, startYear,endMonth, endYear, bscid, cellid);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("vRpMnCellGprsCsBh", vRpMnCellGprsCsBh);
		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);
		//start chart
		List<String> categories = new ArrayList<String>();
		List<Float> bhCsxLevel1List = new ArrayList<Float>();
		List<Float> bhCsxLevel2List = new ArrayList<Float>();
		List<Float> bhCsxLevel3List = new ArrayList<Float>();
		List<Float> bhCsxLevel4List = new ArrayList<Float>();
		List<Float> bhMcsxLevel1List = new ArrayList<Float>();
		List<Float> bhMcsxLevel2List=new ArrayList<Float>();
		List<Float> bhMcsxLevel3List = new ArrayList<Float>();
		List<Float> bhMcsxLevel4List=new ArrayList<Float>();
		List<Float> bhMcsxLevel5List = new ArrayList<Float>();
		List<Float> bhMcsxLevel6List = new ArrayList<Float>();
		List<Float> bhMcsxLevel7List=new ArrayList<Float>();
		List<Float> bhMcsxLevel8List=new ArrayList<Float>();
		List<Float> bhMcsxLevel9List=new ArrayList<Float>();
		
		for (VRpMnCellGprsCsBh vRpMnCell : vRpMnCellGprsCsBh) {
			categories.add(Integer.toString(vRpMnCell.getMonth()));
			bhCsxLevel1List.add(vRpMnCell.getBhCsxLevel1());
			bhCsxLevel2List.add(vRpMnCell.getBhCsxLevel2());
			bhCsxLevel3List.add(vRpMnCell.getBhCsxLevel3());
			bhCsxLevel4List.add(vRpMnCell.getBhCsxLevel4());
			bhMcsxLevel1List.add(vRpMnCell.getBhMcsxLevel1());
			bhMcsxLevel2List.add(vRpMnCell.getBhMcsxLevel2());
			bhMcsxLevel3List.add(vRpMnCell.getBhMcsxLevel3());
			bhMcsxLevel4List.add(vRpMnCell.getBhMcsxLevel4());
			bhMcsxLevel5List.add(vRpMnCell.getBhMcsxLevel5());
			bhMcsxLevel6List.add(vRpMnCell.getBhMcsxLevel6());
			bhMcsxLevel7List.add(vRpMnCell.getBhMcsxLevel7());
			bhMcsxLevel8List.add(vRpMnCell.getBhMcsxLevel8());
			bhMcsxLevel9List.add(vRpMnCell.getBhMcsxLevel9());
		}

		Map<String, List<Float>> csxLevel1Series = new LinkedHashMap<String, List<Float>>();
		csxLevel1Series.put("CSX Level1--80699b", bhCsxLevel1List);
		model.addAttribute("csxLevel1Chart", Chart.multiColumn("csxLevel1Chart", "CSX Level1", categories, csxLevel1Series));

		Map<String, List<Float>> csxLevel2Series = new LinkedHashMap<String, List<Float>>();
		csxLevel2Series.put("CSX Level2--80699b", bhCsxLevel2List);
		model.addAttribute("csxLevel2Chart", Chart.multiColumn("csxLevel2Chart", "CSX Level2", categories, csxLevel2Series));

		Map<String, List<Float>> csxLevel3Series = new LinkedHashMap<String, List<Float>>();
		csxLevel3Series.put("CSX Level3--80699b", bhCsxLevel3List);
		model.addAttribute("csxLevel3Chart", Chart.multiColumn("csxLevel3Chart", "CSX Level3", categories, csxLevel3Series));

		Map<String, List<Float>> csxLevel4Series = new LinkedHashMap<String, List<Float>>();
		csxLevel4Series.put("CSX Level4--80699b", bhCsxLevel4List);
		model.addAttribute("csxLevel4Chart", Chart.multiColumn("csxLevel4Chart", "CSX Level4", categories, csxLevel4Series));

		Map<String, List<Float>> mcsxLevel1Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel1Series.put("MCSX Level1--db843d", bhMcsxLevel1List);
		model.addAttribute("mcsxLevel1Chart", Chart.multiColumn("mcsxLevel1Chart", "MCSX Level1", categories, mcsxLevel1Series));

		Map<String, List<Float>> mcsxLevel2Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel2Series.put("MCSX Level2--80699b", bhMcsxLevel2List);
		model.addAttribute("mcsxLevel2Chart", Chart.multiColumn("mcsxLevel2Chart", "MCSX Level2", categories, mcsxLevel2Series));

		Map<String, List<Float>> mcsxLevel3Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel3Series.put("MCSX Level3--80699b", bhMcsxLevel3List);
		model.addAttribute("mcsxLevel3Chart", Chart.multiColumn("mcsxLevel3Chart", "MCSX Level3", categories, mcsxLevel3Series));

		Map<String, List<Float>> mcsxLevel4Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel4Series.put("MCSX Level4--db843d", bhMcsxLevel4List);
		model.addAttribute("mcsxLevel4Chart", Chart.multiColumn("mcsxLevel4Chart", "MCSX Level4", categories, mcsxLevel4Series));

		Map<String, List<Float>> mcsxLevel5Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel5Series.put("MCSX Level5--db843d", bhMcsxLevel5List);
		model.addAttribute("mcsxLevel5Chart", Chart.multiColumn("mcsxLevel5Chart", "MCSX Level5", categories, mcsxLevel5Series));

		Map<String, List<Float>> mcsxLevel6Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel6Series.put("MCSX Level6--80699b", bhMcsxLevel6List);
		model.addAttribute("mcsxLevel6Chart", Chart.multiColumn("mcsxLevel6Chart", "MCSX Level6", categories, mcsxLevel6Series));

		Map<String, List<Float>> mcsxLevel7Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel7Series.put("MCSX Level7--80699b", bhMcsxLevel7List);
		model.addAttribute("mcsxLevel7Chart", Chart.multiColumn("mcsxLevel7Chart", "MCSX Level7", categories, mcsxLevel7Series));

		Map<String, List<Float>> mcsxLevel8Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel8Series.put("MCSX Level8--db843d", bhMcsxLevel8List);
		model.addAttribute("mcsxLevel8Chart", Chart.multiColumn("mcsxLevel8Chart", "MCSX Level8", categories, mcsxLevel8Series));

		Map<String, List<Float>> mcsxLevel9Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel9Series.put("MCSX Level9--db843d", bhMcsxLevel9List);
		model.addAttribute("mcsxLevel9Chart", Chart.multiColumn("mcsxLevel9Chart", "MCSX Level9", categories, mcsxLevel9Series));

		// checkBox
		String[] checkColumns = { "csxLevel1", "csxLevel1", "csxLevel1", "csxLevel1", "mcsxLevel1", "mcsxLevel2", "mcsxLevel3", "mcsxLevel4", "mcsxLevel5",
				"mcsxLevel6", "mcsxLevel7", "mcsxLevel8", "mcsxLevel9" };
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpMnCellGprsCsBh", 6));
		
		return new ModelAndView("mnCellGprsCsBh");
	}

	@RequestMapping("/report/radio/cell-gprs-cs/wk/bh")
	public ModelAndView wkList(@RequestParam(required = true) String cellid, @RequestParam(required = true) String bscid,
			@RequestParam(required = false) Integer startWeek,@RequestParam(required=false) Integer startYear, @RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear,
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

		List<VRpWkCellGprsCsBh> vRpWkCellGprsCsBh = vRpWkCellGprsCsBhDao.filter(startWeek,startYear, endWeek, endYear, bscid, cellid);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("vRpWkCellGprsCsBh", vRpWkCellGprsCsBh);
		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);
		//start chart
		List<String> categories = new ArrayList<String>();
		List<Float> bhCsxLevel1List = new ArrayList<Float>();
		List<Float> bhCsxLevel2List = new ArrayList<Float>();
		List<Float> bhCsxLevel3List = new ArrayList<Float>();
		List<Float> bhCsxLevel4List = new ArrayList<Float>();
		List<Float> bhMcsxLevel1List = new ArrayList<Float>();
		List<Float> bhMcsxLevel2List=new ArrayList<Float>();
		List<Float> bhMcsxLevel3List = new ArrayList<Float>();
		List<Float> bhMcsxLevel4List=new ArrayList<Float>();
		List<Float> bhMcsxLevel5List = new ArrayList<Float>();
		List<Float> bhMcsxLevel6List = new ArrayList<Float>();
		List<Float> bhMcsxLevel7List=new ArrayList<Float>();
		List<Float> bhMcsxLevel8List=new ArrayList<Float>();
		List<Float> bhMcsxLevel9List=new ArrayList<Float>();
		
		for (VRpWkCellGprsCsBh vRpWkCell : vRpWkCellGprsCsBh) {
			categories.add(Integer.toString(vRpWkCell.getWeek()));
			bhCsxLevel1List.add(vRpWkCell.getBhCsxLevel1());
			bhCsxLevel2List.add(vRpWkCell.getBhCsxLevel2());
			bhCsxLevel3List.add(vRpWkCell.getBhCsxLevel3());
			bhCsxLevel4List.add(vRpWkCell.getBhCsxLevel4());
			bhMcsxLevel1List.add(vRpWkCell.getBhMcsxLevel1());
			bhMcsxLevel2List.add(vRpWkCell.getBhMcsxLevel2());
			bhMcsxLevel3List.add(vRpWkCell.getBhMcsxLevel3());
			bhMcsxLevel4List.add(vRpWkCell.getBhMcsxLevel4());
			bhMcsxLevel5List.add(vRpWkCell.getBhMcsxLevel5());
			bhMcsxLevel6List.add(vRpWkCell.getBhMcsxLevel6());
			bhMcsxLevel7List.add(vRpWkCell.getBhMcsxLevel7());
			bhMcsxLevel8List.add(vRpWkCell.getBhMcsxLevel8());
			bhMcsxLevel9List.add(vRpWkCell.getBhMcsxLevel9());
		}

		Map<String, List<Float>> csxLevel1Series = new LinkedHashMap<String, List<Float>>();
		csxLevel1Series.put("CSX Level1--80699b", bhCsxLevel1List);
		model.addAttribute("csxLevel1Chart", Chart.multiColumn("csxLevel1Chart", "CSX Level1", categories, csxLevel1Series));

		Map<String, List<Float>> csxLevel2Series = new LinkedHashMap<String, List<Float>>();
		csxLevel2Series.put("CSX Level2--80699b", bhCsxLevel2List);
		model.addAttribute("csxLevel2Chart", Chart.multiColumn("csxLevel2Chart", "CSX Level2", categories, csxLevel2Series));

		Map<String, List<Float>> csxLevel3Series = new LinkedHashMap<String, List<Float>>();
		csxLevel3Series.put("CSX Level3--80699b", bhCsxLevel3List);
		model.addAttribute("csxLevel3Chart", Chart.multiColumn("csxLevel3Chart", "CSX Level3", categories, csxLevel3Series));

		Map<String, List<Float>> csxLevel4Series = new LinkedHashMap<String, List<Float>>();
		csxLevel4Series.put("CSX Level4--80699b", bhCsxLevel4List);
		model.addAttribute("csxLevel4Chart", Chart.multiColumn("csxLevel4Chart", "CSX Level4", categories, csxLevel4Series));

		Map<String, List<Float>> mcsxLevel1Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel1Series.put("MCSX Level1--db843d", bhMcsxLevel1List);
		model.addAttribute("mcsxLevel1Chart", Chart.multiColumn("mcsxLevel1Chart", "MCSX Level1", categories, mcsxLevel1Series));

		Map<String, List<Float>> mcsxLevel2Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel2Series.put("MCSX Level2--80699b", bhMcsxLevel2List);
		model.addAttribute("mcsxLevel2Chart", Chart.multiColumn("mcsxLevel2Chart", "MCSX Level2", categories, mcsxLevel2Series));

		Map<String, List<Float>> mcsxLevel3Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel3Series.put("MCSX Level3--80699b", bhMcsxLevel3List);
		model.addAttribute("mcsxLevel3Chart", Chart.multiColumn("mcsxLevel3Chart", "MCSX Level3", categories, mcsxLevel3Series));

		Map<String, List<Float>> mcsxLevel4Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel4Series.put("MCSX Level4--db843d", bhMcsxLevel4List);
		model.addAttribute("mcsxLevel4Chart", Chart.multiColumn("mcsxLevel4Chart", "MCSX Level4", categories, mcsxLevel4Series));

		Map<String, List<Float>> mcsxLevel5Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel5Series.put("MCSX Level5--db843d", bhMcsxLevel5List);
		model.addAttribute("mcsxLevel5Chart", Chart.multiColumn("mcsxLevel5Chart", "MCSX Level5", categories, mcsxLevel5Series));

		Map<String, List<Float>> mcsxLevel6Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel6Series.put("MCSX Level6--80699b", bhMcsxLevel6List);
		model.addAttribute("mcsxLevel6Chart", Chart.multiColumn("mcsxLevel6Chart", "MCSX Level6", categories, mcsxLevel6Series));

		Map<String, List<Float>> mcsxLevel7Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel7Series.put("MCSX Level7--80699b", bhMcsxLevel7List);
		model.addAttribute("mcsxLevel7Chart", Chart.multiColumn("mcsxLevel7Chart", "MCSX Level7", categories, mcsxLevel7Series));

		Map<String, List<Float>> mcsxLevel8Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel8Series.put("MCSX Level8--db843d", bhMcsxLevel8List);
		model.addAttribute("mcsxLevel8Chart", Chart.multiColumn("mcsxLevel8Chart", "MCSX Level8", categories, mcsxLevel8Series));

		Map<String, List<Float>> mcsxLevel9Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel9Series.put("MCSX Level9--db843d", bhMcsxLevel9List);
		model.addAttribute("mcsxLevel9Chart", Chart.multiColumn("mcsxLevel9Chart", "MCSX Level9", categories, mcsxLevel9Series));

		// checkBox
		String[] checkColumns = { "csxLevel1", "csxLevel2", "csxLevel3", "csxLevel4", "mcsxLevel1", "mcsxLevel2", "mcsxLevel3", "mcsxLevel4", "mcsxLevel5",
				"mcsxLevel6", "mcsxLevel7", "mcsxLevel8", "mcsxLevel9" };
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpWkCellGprsCsBh", 6));
		
		
		return new ModelAndView("wkCellGprsCsBh");
	}
}
