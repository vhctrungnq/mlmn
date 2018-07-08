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
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrDistrictDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrDistrict;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
@RequestMapping("/report/radio/district/hr/*")
public class HrDistrictQosController extends BaseController {
	@Autowired
	private VRpHrDistrictDAO vRpHrDistrictDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat ddf = new SimpleDateFormat("dd/MM");

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required = true) String province, @RequestParam(required = true) String district, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
			ModelMap model) {

		if (endDate == null) {
			endDate = new Date();
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
		model.addAttribute("startHour", startHour + ":00");
		model.addAttribute("endHour", endHour + ":00");
		
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);

		List<VRpHrDistrict> vRpHrDistrictList = new ArrayList<VRpHrDistrict>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			vRpHrDistrictList.addAll(vRpHrDistrictDao.filter(dates.get(day).get("startHour"), dates.get(day).get("endHour"), df.format(day), province, district, region));
		}

		model.addAttribute("vRpHrDistrictList", vRpHrDistrictList);
		model.addAttribute("province", province);
		model.addAttribute("district", district);
		model.addAttribute("region", region);
		
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpHrDistrict"));
		
		if (district != null && district.length() > 0) {
			List<String> categories = new ArrayList<String>();
			List<Float> cssrList = new ArrayList<Float>();
			List<Float> tTrafList = new ArrayList<Float>();
			List<Float> tTrafhList = new ArrayList<Float>();
			List<Float> haftratePercentList = new ArrayList<Float>();
			List<Float> tDrprList = new ArrayList<Float>();
			List<Float> tBlkrList = new ArrayList<Float>();
			List<Float> sDrprList = new ArrayList<Float>();
			List<Float> sBlkrList = new ArrayList<Float>();
			List<Float> ogHoSucrList = new ArrayList<Float>();
			List<Float> inHoSucrList = new ArrayList<Float>();

			for (VRpHrDistrict vRpHrDistrict : vRpHrDistrictList) {
				categories.add(Integer.toString(vRpHrDistrict.getHour()) + ":00 " + ddf.format(vRpHrDistrict.getDay()));
				cssrList.add(vRpHrDistrict.getCssr());
				tTrafList.add(vRpHrDistrict.gettTraf());
				tTrafhList.add(vRpHrDistrict.gettTrafh());
				tDrprList.add(vRpHrDistrict.gettDrpr());
				tBlkrList.add(vRpHrDistrict.gettBlkr());
				sDrprList.add(vRpHrDistrict.getsDrpr());
				sBlkrList.add(vRpHrDistrict.getsBlkr());
				haftratePercentList.add(vRpHrDistrict.getHaftratePercent());
				ogHoSucrList.add(vRpHrDistrict.getOgHoSucr());
				inHoSucrList.add(vRpHrDistrict.getInHoSucr());
			}
			
			Map<String, List<Float>> tDrprSeries = new LinkedHashMap<String, List<Float>>();
			tDrprSeries.put("Drop Rate (%)--4572a7", tDrprList);
			model.addAttribute("tDrprChart", Chart.multiColumn("tDrprChart", "TCH Drop Rate (%)", categories, tDrprSeries));
			
			Map<String, List<Float>> sDrprSeries = new LinkedHashMap<String, List<Float>>();
			sDrprSeries.put("Drop Rate (%)--aa4643", sDrprList);
			model.addAttribute("sDrprChart", Chart.multiColumn("sDrprChart", "SDCCH Drop Rate (%)", categories, sDrprSeries));

			Map<String, List<Float>> cssrSeries = new LinkedHashMap<String, List<Float>>();
			cssrSeries.put("CSSR (%)--89a54e", cssrList);
			model.addAttribute("cssrChart", Chart.multiColumn("cssrChart", "CSSR (%)", categories, cssrSeries));

			Map<String, List<Float>> tBlkrSeries = new LinkedHashMap<String, List<Float>>();
			tBlkrSeries.put("Block Rate (%)--80699b", tBlkrList);
			model.addAttribute("tBlkrChart", Chart.multiColumn("tBlkrChart", "TCH Block Rate (%)", categories, tBlkrSeries));

			Map<String, List<Float>> sBlkrSeries = new LinkedHashMap<String, List<Float>>();
			sBlkrSeries.put("Block Rate (%)--db843d", sBlkrList);
			model.addAttribute("sBlkrChart", Chart.multiColumn("sBlkrChart", "SDCCH Block Rate (%)", categories, sBlkrSeries));

			Map<String, List<Float>> tTrafSeries = new LinkedHashMap<String, List<Float>>();
			tTrafSeries.put("Traffic--aa4643", tTrafList);
			tTrafSeries.put("Haft Traffic--89a54e", tTrafhList);
			model.addAttribute("tTrafChart", Chart.multiColumn("tTrafChart", "Traffic", categories, tTrafSeries));
		}
				
		// checkBox
		String[] checkColumns = { "T_TRAF", "T_DRPR", "T_NBLKR", "T_HOBLKR", "CSSR", "SSR", "S_BLKR", "S_DRPR", "TSR", "HAFT_RATE", "In_Ho_Sucr", "Og_Ho_Sucr" };
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpHrDistrict", 7));

		return "hrDistrictQos";
	}
}