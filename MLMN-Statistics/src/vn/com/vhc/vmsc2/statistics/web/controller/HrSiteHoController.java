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
import vn.com.vhc.vmsc2.statistics.dao.VRpHrSiteHoDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrSiteHo;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
@RequestMapping("/report/radio/site-ho/hr/*")
public class HrSiteHoController extends BaseController {
	@Autowired
	private VRpHrSiteHoDAO vRpHrSiteHoDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("details")
	public String sQoswReport(@RequestParam(required = false) String region,@RequestParam(required = true) String bscid, @RequestParam(required = true) String siteid,
			@RequestParam(required = false) String startHour, @RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model) {
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
		}
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("startHour", startHour);
		model.addAttribute("endHour", endHour);

		if (region == null)
			region = getCenter("TT2");*/
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

		List<VRpHrSiteHo> vRpHrSiteHoDetails = new ArrayList<VRpHrSiteHo>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			vRpHrSiteHoDetails.addAll(vRpHrSiteHoDao.filter(dates.get(day).get("startHour"), dates.get(day).get("endHour"), df.format(day), bscid, siteid, region));
		}

		model.addAttribute("vRpHrSiteHoDetails", vRpHrSiteHoDetails);
		model.addAttribute("siteid", siteid);
		model.addAttribute("bscid", bscid);
		model.addAttribute("region", region);
		
		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> inHoSucrList = new ArrayList<Float>();
		List<Float> ogHoSucrList = new ArrayList<Float>();

		for (VRpHrSiteHo vRpHrSiteHo : vRpHrSiteHoDetails) {
			categories.add(Integer.toString(vRpHrSiteHo.getHour()));
			inHoSucrList.add(vRpHrSiteHo.getInHoSucr());
			ogHoSucrList.add(vRpHrSiteHo.getOgHoSucr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("In_Ho_Sucr", inHoSucrList);
		series.put("Og_Ho_Sucr", ogHoSucrList);

		model.addAttribute("chart", Chart.basicLine("SITE HO " + siteid + " Hourly Report", categories, series));

		// checkBox
		String[] checkColumns = { "ogHoAtt", "ogHoSuc", "ogHoSucr", "inHoAtt", "inHoSuc", "inHoSucr" };
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 7));

		String[] checkSeries = { "ogHoSucr", "inHoSucr" };
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));

		return "hrSiteHoDetails";
	}
}
