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

import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyProvinceGprsCsBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyProvinceGprsCsDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyProvinceGprsCs;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyProvinceGprsCsBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;
import vn.com.vhc.vmsc2.statistics.web.utils.ModelAddtributes;



@Controller
public class DyProvinceGprsCsController extends BaseController {
	@Autowired
	private VRpDyProvinceGprsCsDAO vRpDyProvinceGprsCsDao;
	@Autowired
	private VRpDyProvinceGprsCsBhDAO vRpDyProvinceGprsCsBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("/report/radio/province-gprs-cs/dy/list")
	public ModelAndView dyProvinceGprsCsList(@RequestParam(required = false) String region, @RequestParam(required = false) String province,
			@RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, ModelMap model) {
		String[] s = ModelAddtributes.checkDate(model, startDate, endDate).split(";");
		
		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";
		
		List<VRpDyProvinceGprsCs> vRpDyProvinceGprsCs = vRpDyProvinceGprsCsDao.filterDetails(s[0],s[1], province, region);

		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("region", region);
		model.addAttribute("vRpDyProvinceGprsCs", vRpDyProvinceGprsCs);

		return new ModelAndView("dyProvinceGprsCsList");
	}

	@RequestMapping("/report/radio/province-gprs-cs/dy/details")
	public String showReport(@RequestParam(required = false) String region, @RequestParam(required = true) String province,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();

		try {
			Date startDay;
			Date endDay;
			Calendar cal = Calendar.getInstance();
			if (endDate == null) {
				endDay = new Date(currentTime);
				cal.add(Calendar.DATE, -30);
				startDay = cal.getTime();
			} else {
				endDay = df.parse(endDate);
				if (startDate == null) {
					cal.setTime(endDay);
					cal.add(Calendar.DATE, -30);
					startDay = cal.getTime();
				} else {
					startDay = df.parse(startDate);
				}
			}
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("endDate", df.format(endDay));
			
			List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
			model.addAttribute("regionList", regionList);
			
			if (region == null)
				region = "";

			List<VRpDyProvinceGprsCs> vRpDyProvinceGprsCsDetails = vRpDyProvinceGprsCsDao.filterDetails(df.format(startDay), df.format(endDay), province,
					region);

			model.addAttribute("vRpDyProvinceGprsCsDetails", vRpDyProvinceGprsCsDetails);
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

			for (VRpDyProvinceGprsCs vRpDyProvinceGprsCs : vRpDyProvinceGprsCsDetails) {
				categories.add(df.format(vRpDyProvinceGprsCs.getDay()));
				csxLevel1List.add(vRpDyProvinceGprsCs.getCsxLevel1());
				csxLevel2List.add(vRpDyProvinceGprsCs.getCsxLevel2());
				csxLevel3List.add(vRpDyProvinceGprsCs.getCsxLevel3());
				csxLevel4List.add(vRpDyProvinceGprsCs.getCsxLevel4());
				mcsxLevel1List.add(vRpDyProvinceGprsCs.getMcsxLevel1());
				mcsxLevel2List.add(vRpDyProvinceGprsCs.getMcsxLevel2());
				mcsxLevel3List.add(vRpDyProvinceGprsCs.getMcsxLevel3());
				mcsxLevel4List.add(vRpDyProvinceGprsCs.getMcsxLevel4());
				mcsxLevel5List.add(vRpDyProvinceGprsCs.getMcsxLevel5());
				mcsxLevel6List.add(vRpDyProvinceGprsCs.getMcsxLevel6());
				mcsxLevel7List.add(vRpDyProvinceGprsCs.getMcsxLevel7());
				mcsxLevel8List.add(vRpDyProvinceGprsCs.getMcsxLevel8());
				mcsxLevel9List.add(vRpDyProvinceGprsCs.getMcsxLevel9());
				dataLoadList.add(vRpDyProvinceGprsCs.getDataload());
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
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

			String[] checkSeries = { "dataload" };
			model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));

		} catch (ParseException e) {
			saveError(request, e.toString());
		}

		return "dyProvinceGprsCsDetails";
	}

	@RequestMapping("/report/radio/province-gprs-cs/dy/bhDetails")
	public String showReportBh(@RequestParam(required = false) String region, @RequestParam(required = true) String province,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();

		try {
			Date startDay;
			Date endDay;
			Calendar cal = Calendar.getInstance();
			if (endDate == null) {
				endDay = new Date(currentTime - 24 * 60 * 60 * 1000);
				cal.add(Calendar.DATE, -30);
				startDay = cal.getTime();
			} else {
				endDay = df.parse(endDate);
				if (startDate == null) {
					cal.setTime(endDay);
					cal.add(Calendar.DATE, -30);
					startDay = cal.getTime();
				} else {
					startDay = df.parse(startDate);
				}
			}
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("endDate", df.format(endDay));

			List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
			model.addAttribute("regionList", regionList);
			
			if (region == null)
				region = "";

			List<VRpDyProvinceGprsCsBh> vRpDyProvinceGprsCsBhDetails = vRpDyProvinceGprsCsBhDao.filterDetails(df.format(startDay), df.format(endDay), province,
					region);

			model.addAttribute("vRpDyProvinceGprsCsBhDetails", vRpDyProvinceGprsCsBhDetails);
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

			for (VRpDyProvinceGprsCsBh vRpDyProvinceGprsCsBh : vRpDyProvinceGprsCsBhDetails) {
				categories.add(vRpDyProvinceGprsCsBh.getBh() + ":00/" + df.format(vRpDyProvinceGprsCsBh.getDay()));
				bhCsxLevel1List.add(vRpDyProvinceGprsCsBh.getBhCsxLevel1());
				bhCsxLevel2List.add(vRpDyProvinceGprsCsBh.getBhCsxLevel2());
				bhCsxLevel3List.add(vRpDyProvinceGprsCsBh.getBhCsxLevel3());
				bhCsxLevel4List.add(vRpDyProvinceGprsCsBh.getBhCsxLevel4());
				bhMcsxLevel1List.add(vRpDyProvinceGprsCsBh.getBhMcsxLevel1());
				bhMcsxLevel2List.add(vRpDyProvinceGprsCsBh.getBhMcsxLevel2());
				bhMcsxLevel3List.add(vRpDyProvinceGprsCsBh.getBhMcsxLevel3());
				bhMcsxLevel4List.add(vRpDyProvinceGprsCsBh.getBhMcsxLevel4());
				bhMcsxLevel5List.add(vRpDyProvinceGprsCsBh.getBhMcsxLevel5());
				bhMcsxLevel6List.add(vRpDyProvinceGprsCsBh.getBhMcsxLevel6());
				bhMcsxLevel7List.add(vRpDyProvinceGprsCsBh.getBhMcsxLevel7());
				bhMcsxLevel8List.add(vRpDyProvinceGprsCsBh.getBhMcsxLevel8());
				bhMcsxLevel9List.add(vRpDyProvinceGprsCsBh.getBhMcsxLevel9());
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
							"mcsxLevel6", "mcsxLevel7", "mcsxLevel8", "mcsxLevel9"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 6));

			String[] checkSeries = { "mcsxLevel9sssss" };
			model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		} catch (ParseException e) {
			saveError(request, e.toString());
		}

		return "dyProvinceGprsCsBhDetails";
	}
}
