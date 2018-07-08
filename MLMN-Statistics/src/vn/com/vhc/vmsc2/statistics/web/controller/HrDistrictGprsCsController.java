package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrDistrictGprsCsDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrDistrictGprsCs;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
@RequestMapping("/report/radio/district-gprs-cs/hr/*")
public class HrDistrictGprsCsController extends BaseController {
	@Autowired
	private VRpHrDistrictGprsCsDAO vRpHrDistrictGprsCsDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("details")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required = true) String province,@RequestParam(required = true) String district, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
			ModelMap model) {
		long currentTime = System.currentTimeMillis();

		if (endDate == null) {
			endDate = new Date(currentTime);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		if (endHour == null) {
			endHour = "23";
		} else {
			if(endHour.indexOf(":") >0)
				endHour = endHour.substring(0, endHour.indexOf(":"));
			else
				endHour = "23";
			}
		if (startHour == null) {
			startHour = "0";
		} else {	
			if(startHour.indexOf(":") >0)
				startHour = startHour.substring(0, startHour.indexOf(":"));
			else
				startHour = "0";
		}
		String[] hourList = {"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
		model.addAttribute("hourList", hourList);
		
		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";
		
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("startHour", startHour);
		model.addAttribute("endHour", endHour);

		List<VRpHrDistrictGprsCs> vRpHrDistrictGprsCsDetails = new ArrayList<VRpHrDistrictGprsCs>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			vRpHrDistrictGprsCsDetails.addAll(vRpHrDistrictGprsCsDao.filter(dates.get(day).get("startHour"), dates.get(day).get("endHour"), df.format(day), province,district, region));
		}

		model.addAttribute("vRpHrDistrictGprsCsDetails", vRpHrDistrictGprsCsDetails);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("district", district);
		model.addAttribute("region", region);
		
		if (district != null && district.length() > 0) {
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

			for (VRpHrDistrictGprsCs vRpHrDistrictGprsCs : vRpHrDistrictGprsCsDetails) {
				categories.add(vRpHrDistrictGprsCs.getHour().toString()+":00/"+vRpHrDistrictGprsCs.getDay());
				csxLevel1List.add(vRpHrDistrictGprsCs.getCsxLevel1());
				csxLevel2List.add(vRpHrDistrictGprsCs.getCsxLevel2());
				csxLevel3List.add(vRpHrDistrictGprsCs.getCsxLevel3());
				csxLevel4List.add(vRpHrDistrictGprsCs.getCsxLevel4());
				mcsxLevel1List.add(vRpHrDistrictGprsCs.getMcsxLevel1());
				mcsxLevel2List.add(vRpHrDistrictGprsCs.getMcsxLevel2());
				mcsxLevel3List.add(vRpHrDistrictGprsCs.getMcsxLevel3());
				mcsxLevel4List.add(vRpHrDistrictGprsCs.getMcsxLevel4());
				mcsxLevel5List.add(vRpHrDistrictGprsCs.getMcsxLevel5());
				mcsxLevel6List.add(vRpHrDistrictGprsCs.getMcsxLevel6());
				mcsxLevel7List.add(vRpHrDistrictGprsCs.getMcsxLevel7());
				mcsxLevel8List.add(vRpHrDistrictGprsCs.getMcsxLevel8());
				mcsxLevel9List.add(vRpHrDistrictGprsCs.getMcsxLevel9());
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

		}
		
		// checkBox
		String[] checkColumns = { "csxLevel1", "csxLevel2", "csxLevel3", "csxLevel4", "mcsxLevel1", "mcsxLevel2", "mcsxLevel3", "mcsxLevel4", "mcsxLevel5",
				"mcsxLevel6", "mcsxLevel7", "mcsxLevel8", "mcsxLevel9" };
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

		String[] checkSeries = { "csxLevel1", "csxLevel2", "csxLevel3", "csxLevel4", "mcsxLevel1", "mcsxLevel2", "mcsxLevel3", "mcsxLevel4", "mcsxLevel5",
				"mcsxLevel6", "mcsxLevel7", "mcsxLevel8", "mcsxLevel9" };
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));

		return "hrDistrictGprsCsDetails";
	}
}
