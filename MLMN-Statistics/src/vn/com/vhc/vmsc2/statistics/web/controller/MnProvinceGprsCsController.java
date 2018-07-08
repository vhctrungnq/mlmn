package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
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

import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnProvinceGprsCsBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnProvinceGprsCsDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnProvinceGprsCs;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnProvinceGprsCsBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
public class MnProvinceGprsCsController extends BaseController {
	@Autowired
	private VRpMnProvinceGprsCsDAO vRpMnProvinceGprsCsDao;
	@Autowired
	private VRpMnProvinceGprsCsBhDAO vRpMnProvinceGprsCsBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;

	@RequestMapping("/report/radio/province-gprs-cs/mn/list")
	public ModelAndView dyProvinceGprsCsList(@RequestParam(required = false) String region, @RequestParam(required = false) String province,
			@RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model) {

		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
		{
			endYear = calender.get(Calendar.YEAR);
		}
		Calendar cal = Calendar.getInstance();
		if (endMonth == null) {
			cal.add(Calendar.MONTH, -1);
			endMonth = cal.get(Calendar.MONTH) + 1;
			endYear = cal.get(Calendar.YEAR);
		}

		if (startMonth == null) {
			startMonth = endMonth;
			startYear = endYear;
		}
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);


		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";
		
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
	    model.addAttribute("monthList", monthList);

		List<VRpMnProvinceGprsCs> vRpMnProvinceGprsCs = vRpMnProvinceGprsCsDao.filterDetails(startMonth.toString(), startYear.toString(), endMonth.toString(),
				endYear.toString(), province, region);

		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("region", region);
		model.addAttribute("vRpMnProvinceGprsCs", vRpMnProvinceGprsCs);
		return new ModelAndView("mnProvinceGprsCsList");
	}

	@RequestMapping("/report/radio/province-gprs-cs/mn/details")
	public String showReport(@RequestParam(required = false) String region, @RequestParam(required = true) String province,
			@RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {

		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
		{
			endYear = calender.get(Calendar.YEAR);
		}
		if (endMonth == null) {
			cal.add(Calendar.MONTH, -1);
			endMonth = cal.get(Calendar.MONTH) + 1;
			endYear = cal.get(Calendar.YEAR);
		}

		if (startMonth == null) {
			if (endMonth > 3) {
				startMonth = endMonth - 3;
				startYear = endYear;
			} else {
				cal1.add(Calendar.MONTH, -3);
				startMonth = cal1.get(Calendar.MONTH) + 1;
				startYear = cal1.get(Calendar.YEAR);
			}
		}
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);


		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
	    model.addAttribute("monthList", monthList);
		
		if (region == null)
			region = "";

		List<VRpMnProvinceGprsCs> vRpMnProvinceGprsCsDetails = vRpMnProvinceGprsCsDao.filterDetails(startMonth.toString(), startYear.toString(),
				endMonth.toString(), endYear.toString(), province, region);

		model.addAttribute("vRpMnProvinceGprsCsDetails", vRpMnProvinceGprsCsDetails);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("region", region);
		
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

		for (VRpMnProvinceGprsCs vRpMnProvinceGprsCs : vRpMnProvinceGprsCsDetails) {
			categories.add(vRpMnProvinceGprsCs.getMonth() + "/" + vRpMnProvinceGprsCs.getYear());
			csxLevel1List.add(vRpMnProvinceGprsCs.getCsxLevel1());
			csxLevel2List.add(vRpMnProvinceGprsCs.getCsxLevel2());
			csxLevel3List.add(vRpMnProvinceGprsCs.getCsxLevel3());
			csxLevel4List.add(vRpMnProvinceGprsCs.getCsxLevel4());
			mcsxLevel1List.add(vRpMnProvinceGprsCs.getMcsxLevel1());
			mcsxLevel2List.add(vRpMnProvinceGprsCs.getMcsxLevel2());
			mcsxLevel3List.add(vRpMnProvinceGprsCs.getMcsxLevel3());
			mcsxLevel4List.add(vRpMnProvinceGprsCs.getMcsxLevel4());
			mcsxLevel5List.add(vRpMnProvinceGprsCs.getMcsxLevel5());
			mcsxLevel6List.add(vRpMnProvinceGprsCs.getMcsxLevel6());
			mcsxLevel7List.add(vRpMnProvinceGprsCs.getMcsxLevel7());
			mcsxLevel8List.add(vRpMnProvinceGprsCs.getMcsxLevel8());
			mcsxLevel9List.add(vRpMnProvinceGprsCs.getMcsxLevel9());
			dataLoadList.add(vRpMnProvinceGprsCs.getDataload());
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
		String[] checkColumns = { "csxLevel1", "csxLevel2", "csxLevel3", "csxLevel4", "mcsxLevel1", "mcsxLevel2", "mcsxLevel3", "mcsxLevel4", "mcsxLevel5",
				"mcsxLevel6", "mcsxLevel7", "mcsxLevel8", "mcsxLevel9", "dataload" };
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 6));

		String[] checkSeries = { "dataload" };
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));

		return "mnProvinceGprsCsDetails";
	}

	@RequestMapping("/report/radio/province-gprs-cs/mn/bhDetails")
	public String showReportBhDetails(@RequestParam(required = false) String region, @RequestParam(required = true) String province,
			@RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {

		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
		{
			endYear = calender.get(Calendar.YEAR);
		}
		if (endMonth == null) {
			cal.add(Calendar.MONTH, -1);
			endMonth = cal.get(Calendar.MONTH) + 1;
			endYear = cal.get(Calendar.YEAR);
		}

		if (startMonth == null) {
			if (endMonth > 3) {
				startMonth = endMonth - 3;
				startYear = endYear;
			} else {
				cal1.add(Calendar.MONTH, -3);
				startMonth = cal1.get(Calendar.MONTH) + 1;
				startYear = cal1.get(Calendar.YEAR);
			}
		}
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", startYear);


		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
	    model.addAttribute("monthList", monthList);
		
		if (region == null)
			region = "";

		List<VRpMnProvinceGprsCsBh> vRpMnProvinceGprsCsBhDetails = vRpMnProvinceGprsCsBhDao.filterDetails(startMonth.toString(), startYear.toString(),
				endMonth.toString(), endYear.toString(), province, region);

		model.addAttribute("vRpMnProvinceGprsCsBhDetails", vRpMnProvinceGprsCsBhDetails);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("region", region);
		
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

		for (VRpMnProvinceGprsCsBh vRpMnProvinceGprsCsBh : vRpMnProvinceGprsCsBhDetails) {
			categories.add(vRpMnProvinceGprsCsBh.getMonth() + "/" + vRpMnProvinceGprsCsBh.getYear());
			bhCsxLevel1List.add(vRpMnProvinceGprsCsBh.getBhCsxLevel1());
			bhCsxLevel2List.add(vRpMnProvinceGprsCsBh.getBhCsxLevel2());
			bhCsxLevel3List.add(vRpMnProvinceGprsCsBh.getBhCsxLevel3());
			bhCsxLevel4List.add(vRpMnProvinceGprsCsBh.getBhCsxLevel4());
			bhMcsxLevel1List.add(vRpMnProvinceGprsCsBh.getBhMcsxLevel1());
			bhMcsxLevel2List.add(vRpMnProvinceGprsCsBh.getBhMcsxLevel2());
			bhMcsxLevel3List.add(vRpMnProvinceGprsCsBh.getBhMcsxLevel3());
			bhMcsxLevel4List.add(vRpMnProvinceGprsCsBh.getBhMcsxLevel4());
			bhMcsxLevel5List.add(vRpMnProvinceGprsCsBh.getBhMcsxLevel5());
			bhMcsxLevel6List.add(vRpMnProvinceGprsCsBh.getBhMcsxLevel6());
			bhMcsxLevel7List.add(vRpMnProvinceGprsCsBh.getBhMcsxLevel7());
			bhMcsxLevel8List.add(vRpMnProvinceGprsCsBh.getBhMcsxLevel8());
			bhMcsxLevel9List.add(vRpMnProvinceGprsCsBh.getBhMcsxLevel9());
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
		String[] checkColumns = { "bhCsxLevel1", "bhCsxLevel2", "bhCsxLevel3", "bhCsxLevel4", "bhMcsxLevel1", "bhMcsxLevel2", "bhMcsxLevel3", "bhMcsxLevel4",
				"bhMcsxLevel5", "bhMcsxLevel6", "bhMcsxLevel7", "bhMcsxLevel8", "bhMcsxLevel9" };
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 6));

		String[] checkSeries = { "bhCsxLevel1", "bhCsxLevel2", "bhCsxLevel3", "bhCsxLevel4", "bhMcsxLevel1", "bhMcsxLevel2", "bhMcsxLevel3", "bhMcsxLevel4",
				"bhMcsxLevel5", "bhMcsxLevel6", "bhMcsxLevel7", "bhMcsxLevel8", "bhMcsxLevel9" };
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));

		return "mnProvinceGprsCsBhDetails";
	}
}
