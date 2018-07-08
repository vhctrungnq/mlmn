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
import vn.com.vhc.vmsc2.statistics.dao.VRpHrLocationGprsCsDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrLocationGprsCs;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
@RequestMapping("/report/radio/location-gprs-cs/hr/*")
public class HrLocationGprsCsController extends BaseController {
	@Autowired
	private VRpHrLocationGprsCsDAO vRpHrLocationGprsCsDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("details")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required = true) String location, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
			ModelMap model) {
		long currentTime = System.currentTimeMillis();

		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		/*if (endHour == null) {
			endHour = "23";
		}
		if (startHour == null) {
			startHour = "0";
		}*/
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

/*		if (region == null)
			region = getCenter("TT2");*/

		List<VRpHrLocationGprsCs> vRpHrLocationGprsCsDetails = new ArrayList<VRpHrLocationGprsCs>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			vRpHrLocationGprsCsDetails.addAll(vRpHrLocationGprsCsDao.filter(dates.get(day).get("startHour"), dates.get(day).get("endHour"), df.format(day), location, region));
		}

		model.addAttribute("vRpHrLocationGprsCsDetails", vRpHrLocationGprsCsDetails);
		List<HProvincesCode> locationList = hProvincesCodeDao.getAllLocation();
		model.addAttribute("locationList", locationList);
		model.addAttribute("location", location);

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

		for (VRpHrLocationGprsCs vRpHrLocationGprsCs : vRpHrLocationGprsCsDetails) {
			categories.add(vRpHrLocationGprsCs.getHour().toString());
			csxLevel1List.add(vRpHrLocationGprsCs.getCsxLevel1());
			csxLevel2List.add(vRpHrLocationGprsCs.getCsxLevel2());
			csxLevel3List.add(vRpHrLocationGprsCs.getCsxLevel3());
			csxLevel4List.add(vRpHrLocationGprsCs.getCsxLevel4());
			mcsxLevel1List.add(vRpHrLocationGprsCs.getMcsxLevel1());
			mcsxLevel2List.add(vRpHrLocationGprsCs.getMcsxLevel2());
			mcsxLevel3List.add(vRpHrLocationGprsCs.getMcsxLevel3());
			mcsxLevel4List.add(vRpHrLocationGprsCs.getMcsxLevel4());
			mcsxLevel5List.add(vRpHrLocationGprsCs.getMcsxLevel5());
			mcsxLevel6List.add(vRpHrLocationGprsCs.getMcsxLevel6());
			mcsxLevel7List.add(vRpHrLocationGprsCs.getMcsxLevel7());
			mcsxLevel8List.add(vRpHrLocationGprsCs.getMcsxLevel8());
			mcsxLevel9List.add(vRpHrLocationGprsCs.getMcsxLevel9());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("Csx_Level1", csxLevel1List);
		series.put("Csx_Level2", csxLevel2List);
		series.put("Csx_Level3", csxLevel3List);
		series.put("Csx_Level4", csxLevel4List);
		series.put("Mcsx_Level1", mcsxLevel1List);
		series.put("Mcsx_Level2", mcsxLevel2List);
		series.put("Mcsx_Level3", mcsxLevel3List);
		series.put("Mcsx_Level4", mcsxLevel4List);
		series.put("Mcsx_Level5", mcsxLevel5List);
		series.put("Mcsx_Level6", mcsxLevel6List);
		series.put("Mcsx_Level7", mcsxLevel7List);
		series.put("Mcsx_Level8", mcsxLevel8List);
		series.put("Mcsx_Level9", mcsxLevel9List);

		model.addAttribute("chart", Chart.basicLine("LOCATION GPRS CS" + location + " Houly Report", categories, series));

		// checkBox
		String[] checkColumns = { "csxLevel1", "csxLevel2", "csxLevel3", "csxLevel4", "mcsxLevel1", "mcsxLevel2", "mcsxLevel3", "mcsxLevel4", "mcsxLevel5",
				"mcsxLevel6", "mcsxLevel7", "mcsxLevel8", "mcsxLevel9" };
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

		String[] checkSeries = { "csxLevel1", "csxLevel2", "csxLevel3", "csxLevel4", "mcsxLevel1", "mcsxLevel2", "mcsxLevel3", "mcsxLevel4", "mcsxLevel5",
				"mcsxLevel6", "mcsxLevel7", "mcsxLevel8", "mcsxLevel9" };
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));

		return "hrLocationGprsCsDetails";
	}
}
