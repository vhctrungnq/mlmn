package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
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

import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkDistrictGprsCsBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkDistrictGprsCsDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkDistrictGprsCs;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkDistrictGprsCsBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
public class WkDistrictGprsCsController extends BaseController{
	@Autowired
	private VRpWkDistrictGprsCsDAO vRpWkDistrictGprsCsDao;
	@Autowired
	private VRpWkDistrictGprsCsBhDAO vRpWkDistrictGprsCsBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	
	@RequestMapping("/report/radio/district-gprs-cs/wk/list")
	public ModelAndView dyDistrictGprsCsList(@RequestParam(required = false) String region,@RequestParam(required = false) String province, @RequestParam(required = false) String district, @RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
		{
			endYear = calender.get(Calendar.YEAR);
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
		model.addAttribute("startWeek", startWeek);
		model.addAttribute("endWeek", endWeek);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		model.addAttribute("region", region);
		
		List<VRpWkDistrictGprsCs> vRpWkDistrictGprsCs = vRpWkDistrictGprsCsDao.filterDetails(startWeek.toString(), startYear.toString(), endWeek.toString(), endYear.toString(), province, district, region);

		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("district", district);
		model.addAttribute("vRpWkDistrictGprsCs", vRpWkDistrictGprsCs);
		return new ModelAndView("wkDistrictGprsCsList");
	}
	
	@RequestMapping("/report/radio/district-gprs-cs/wk/details")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required=true) String province,@RequestParam(required=true) String district,
			@RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
		{
			endYear = calender.get(Calendar.YEAR);
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
		model.addAttribute("startWeek", startWeek);
		model.addAttribute("endWeek", endWeek);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);

		model.addAttribute("region", region);
		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		List<VRpWkDistrictGprsCs> vRpWkDistrictGprsCsDetails = vRpWkDistrictGprsCsDao.filterDetails(startWeek.toString(), startYear.toString(), endWeek.toString(), endYear.toString(), province, district, region);
		
		model.addAttribute("vRpWkDistrictGprsCsDetails", vRpWkDistrictGprsCsDetails);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("district", district);
		
		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> csxLevel1List = new ArrayList<Float>();
		List<Float> csxLevel2List = new ArrayList<Float>();
		List<Float> csxLevel3List = new ArrayList<Float>();
		List<Float> csxLevel4List = new ArrayList<Float>();
		List<Float> mcsxLevel1List = new ArrayList<Float>();
		List<Float> mcsxLevel2List = new ArrayList<Float>();
		List<Float> mcsxLevel3List = new ArrayList<Float>();
		List<Float> mcsxLevel4List = new ArrayList<Float>();
		List<Float> mcsxLevel5List = new ArrayList<Float>();
		List<Float> mcsxLevel6List = new ArrayList<Float>();
		List<Float> mcsxLevel7List = new ArrayList<Float>();
		List<Float> mcsxLevel8List = new ArrayList<Float>();
		List<Float> mcsxLevel9List = new ArrayList<Float>();
		List<Float> dataLoadList = new ArrayList<Float>();
		
		for (VRpWkDistrictGprsCs vRpWkDistrictGprsCs : vRpWkDistrictGprsCsDetails) {
			categories.add(vRpWkDistrictGprsCs.getWeek()+"/"+vRpWkDistrictGprsCs.getYear());
			csxLevel1List.add(vRpWkDistrictGprsCs.getCsxLevel1());
			csxLevel2List.add(vRpWkDistrictGprsCs.getCsxLevel2());
			csxLevel3List.add(vRpWkDistrictGprsCs.getCsxLevel3());
			csxLevel4List.add(vRpWkDistrictGprsCs.getCsxLevel4());
			mcsxLevel1List.add(vRpWkDistrictGprsCs.getMcsxLevel1());
			mcsxLevel2List.add(vRpWkDistrictGprsCs.getMcsxLevel2());
			mcsxLevel3List.add(vRpWkDistrictGprsCs.getMcsxLevel3());
			mcsxLevel4List.add(vRpWkDistrictGprsCs.getMcsxLevel4());
			mcsxLevel5List.add(vRpWkDistrictGprsCs.getMcsxLevel5());
			mcsxLevel6List.add(vRpWkDistrictGprsCs.getMcsxLevel6());
			mcsxLevel7List.add(vRpWkDistrictGprsCs.getMcsxLevel7());
			mcsxLevel8List.add(vRpWkDistrictGprsCs.getMcsxLevel8());
			mcsxLevel9List.add(vRpWkDistrictGprsCs.getMcsxLevel9());
			dataLoadList.add(vRpWkDistrictGprsCs.getDataload());
		}
		Map<String, List<Float>> csxLevel1Series = new LinkedHashMap<String, List<Float>>();
		csxLevel1Series.put("CSX Level1--80699b", csxLevel1List);
		model.addAttribute("csxLevel1Chart", Chart.multiColumn("csxLevel1Chart", "CSX Level1", categories, csxLevel1Series));

		Map<String, List<Float>> csxLevel2Series = new LinkedHashMap<String, List<Float>>();
		csxLevel2Series.put("CSX Level2--80699b", csxLevel2List);
		model.addAttribute("csxLevel2Chart", Chart.multiColumn("csxLevel2Chart", "CSX Level2", categories, csxLevel2Series));

		Map<String, List<Float>> csxLevel3Series = new LinkedHashMap<String, List<Float>>();
		csxLevel3Series.put("CSX Level3--80699b", csxLevel3List);
		model.addAttribute("csxLevel3Chart", Chart.multiColumn("csxLevel3Chart", "CSX Level3", categories, csxLevel3Series));

		Map<String, List<Float>> csxLevel4Series = new LinkedHashMap<String, List<Float>>();
		csxLevel4Series.put("CSX Level4--80699b", csxLevel4List);
		model.addAttribute("csxLevel4Chart", Chart.multiColumn("csxLevel4Chart", "CSX Level4", categories, csxLevel4Series));

		Map<String, List<Float>> mcsxLevel1Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel1Series.put("MCSX Level1--db843d", mcsxLevel1List);
		model.addAttribute("mcsxLevel1Chart", Chart.multiColumn("mcsxLevel1Chart", "MCSX Level1", categories, mcsxLevel1Series));

		Map<String, List<Float>> mcsxLevel2Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel2Series.put("MCSX Level2--80699b", mcsxLevel2List);
		model.addAttribute("mcsxLevel2Chart", Chart.multiColumn("mcsxLevel2Chart", "MCSX Level2", categories, mcsxLevel2Series));

		Map<String, List<Float>> mcsxLevel3Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel3Series.put("MCSX Level3--80699b", mcsxLevel3List);
		model.addAttribute("mcsxLevel3Chart", Chart.multiColumn("mcsxLevel3Chart", "MCSX Level3", categories, mcsxLevel3Series));

		Map<String, List<Float>> mcsxLevel4Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel4Series.put("MCSX Level4--db843d", mcsxLevel4List);
		model.addAttribute("mcsxLevel4Chart", Chart.multiColumn("mcsxLevel4Chart", "MCSX Level4", categories, mcsxLevel4Series));

		Map<String, List<Float>> mcsxLevel5Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel5Series.put("MCSX Level5--db843d", mcsxLevel5List);
		model.addAttribute("mcsxLevel5Chart", Chart.multiColumn("mcsxLevel5Chart", "MCSX Level5", categories, mcsxLevel5Series));

		Map<String, List<Float>> mcsxLevel6Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel6Series.put("MCSX Level6--80699b", mcsxLevel6List);
		model.addAttribute("mcsxLevel6Chart", Chart.multiColumn("mcsxLevel6Chart", "MCSX Level6", categories, mcsxLevel6Series));

		Map<String, List<Float>> mcsxLevel7Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel7Series.put("MCSX Level7--80699b", mcsxLevel7List);
		model.addAttribute("mcsxLevel7Chart", Chart.multiColumn("mcsxLevel7Chart", "MCSX Level7", categories, mcsxLevel7Series));

		Map<String, List<Float>> mcsxLevel8Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel8Series.put("MCSX Level8--db843d", mcsxLevel8List);
		model.addAttribute("mcsxLevel8Chart", Chart.multiColumn("mcsxLevel8Chart", "MCSX Level8", categories, mcsxLevel8Series));

		Map<String, List<Float>> mcsxLevel9Series = new LinkedHashMap<String, List<Float>>();
		mcsxLevel9Series.put("MCSX Level9--db843d", mcsxLevel9List);
		model.addAttribute("mcsxLevel9Chart", Chart.multiColumn("mcsxLevel9Chart", "MCSX Level9", categories, mcsxLevel9Series));

		// checkBox
		String[] checkColumns = {"csxLevel1","csxLevel2","csxLevel3","csxLevel4","mcsxLevel1","mcsxLevel2","mcsxLevel3","mcsxLevel4","mcsxLevel5","mcsxLevel6","mcsxLevel7","mcsxLevel8","mcsxLevel9","dataload"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 8));

		String[] checkSeries = {"csxLevel1","csxLevel2","csxLevel3","csxLevel4","mcsxLevel1","mcsxLevel2","mcsxLevel3","mcsxLevel4","mcsxLevel5","mcsxLevel6","mcsxLevel7","mcsxLevel8","mcsxLevel9","dataload"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		
	return "wkDistrictGprsCsDetails";
	}
	
	@RequestMapping("/report/radio/district-gprs-cs/wk/bhDetails")
	public String showReportBhDetails(@RequestParam(required = false) String region,@RequestParam(required=true) String province,@RequestParam(required=true) String district,
			@RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
		{
			endYear = calender.get(Calendar.YEAR);
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
		model.addAttribute("startWeek", startWeek);
		model.addAttribute("endWeek", endWeek);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		model.addAttribute("region", region);
		
		List<VRpWkDistrictGprsCsBh> vRpWkDistrictGprsCsBhDetails = vRpWkDistrictGprsCsBhDao.filterDetails(startWeek.toString(), startYear.toString(), endWeek.toString(), endYear.toString(), province, district, region);
		
		model.addAttribute("vRpWkDistrictGprsCsBhDetails", vRpWkDistrictGprsCsBhDetails);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("district", district);
		
		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> bhCsxLevel1List = new ArrayList<Float>();
		List<Float> bhCsxLevel2List = new ArrayList<Float>();
		List<Float> bhCsxLevel3List = new ArrayList<Float>();
		List<Float> bhCsxLevel4List = new ArrayList<Float>();
		List<Float> bhMcsxLevel1List = new ArrayList<Float>();
		List<Float> bhMcsxLevel2List = new ArrayList<Float>();
		List<Float> bhMcsxLevel3List = new ArrayList<Float>();
		List<Float> bhMcsxLevel4List = new ArrayList<Float>();
		List<Float> bhMcsxLevel5List = new ArrayList<Float>();
		List<Float> bhMcsxLevel6List = new ArrayList<Float>();
		List<Float> bhMcsxLevel7List = new ArrayList<Float>();
		List<Float> bhMcsxLevel8List = new ArrayList<Float>();
		List<Float> bhMcsxLevel9List = new ArrayList<Float>();
		
		for (VRpWkDistrictGprsCsBh vRpWkDistrictGprsCsBh : vRpWkDistrictGprsCsBhDetails) {
			categories.add(vRpWkDistrictGprsCsBh.getWeek()+"/"+vRpWkDistrictGprsCsBh.getYear());
			bhCsxLevel1List.add(vRpWkDistrictGprsCsBh.getBhCsxLevel1());
			bhCsxLevel2List.add(vRpWkDistrictGprsCsBh.getBhCsxLevel2());
			bhCsxLevel3List.add(vRpWkDistrictGprsCsBh.getBhCsxLevel3());
			bhCsxLevel4List.add(vRpWkDistrictGprsCsBh.getBhCsxLevel4());
			bhMcsxLevel1List.add(vRpWkDistrictGprsCsBh.getBhMcsxLevel1());
			bhMcsxLevel2List.add(vRpWkDistrictGprsCsBh.getBhMcsxLevel2());
			bhMcsxLevel3List.add(vRpWkDistrictGprsCsBh.getBhMcsxLevel3());
			bhMcsxLevel4List.add(vRpWkDistrictGprsCsBh.getBhMcsxLevel4());
			bhMcsxLevel5List.add(vRpWkDistrictGprsCsBh.getBhMcsxLevel5());
			bhMcsxLevel6List.add(vRpWkDistrictGprsCsBh.getBhMcsxLevel6());
			bhMcsxLevel7List.add(vRpWkDistrictGprsCsBh.getBhMcsxLevel7());
			bhMcsxLevel8List.add(vRpWkDistrictGprsCsBh.getBhMcsxLevel8());
			bhMcsxLevel9List.add(vRpWkDistrictGprsCsBh.getBhMcsxLevel9());
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
		String[] checkColumns = {"bhCsxLevel1","bhCsxLevel2","bhCsxLevel3","bhCsxLevel4","bhMcsxLevel1","bhMcsxLevel2","bhMcsxLevel3","bhMcsxLevel4","bhMcsxLevel5","bhMcsxLevel6","bhMcsxLevel7","bhMcsxLevel8","bhMcsxLevel9"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 8));

		String[] checkSeries = {"bhCsxLevel1","bhCsxLevel2","bhCsxLevel3","bhCsxLevel4","bhMcsxLevel1","bhMcsxLevel2","bhMcsxLevel3","bhMcsxLevel4","bhMcsxLevel5","bhMcsxLevel6","bhMcsxLevel7","bhMcsxLevel8","bhMcsxLevel9"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		
	return "wkDistrictGprsCsBhDetails";
	}
}