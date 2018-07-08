package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.ArrayList;
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

import vn.com.vhc.vmsc2.statistics.dao.HIpbbBwlistDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyIpbbDAO;
import vn.com.vhc.vmsc2.statistics.domain.HIpbbBwlist;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyIpbb;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;
import vn.com.vhc.vmsc2.statistics.web.utils.ModelAddtributes;

/**
 * 
 * @author Mr. ThangPX
 * @return Paging IPBB Report Level Daily, Hourly, Weekly, Monthly
 */
@Controller
@RequestMapping("/report/core/ipbb-data/*")
public class IPBBController extends BaseController {
	@Autowired
	private VRpDyIpbbDAO dyIpbbDAO;
	/*@Autowired
	private TableConfigDAO tbConfigsDAO;*/
	@Autowired
	private HighlightConfigDAO highlightDao;
	@Autowired
	private HIpbbBwlistDAO ipbbDao;

	@RequestMapping(value = "hr")
	public String showReport(@RequestParam(required = false) String routeid,@RequestParam(required = false) String interfaceid, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, ModelMap model, HttpServletRequest request) {
		 List<HIpbbBwlist> listRoute = ipbbDao.getAllLink();
		routeid = ModelAddtributes.checkIpbb(model , listRoute, routeid, "ipbb-data");
		interfaceid = ModelAddtributes.checkInterface(model, interfaceid);
		String[] s = ModelAddtributes.checkHour(model, startDate, startHour, endHour).split(";");
		List<VRpDyIpbb> dyIpbbList = dyIpbbDAO.filterHourly("V_RP_HR_IPBB", routeid, interfaceid, s[0], s[1], s[2]);
		// chart
		String title = "";
		List<String> categories = new ArrayList<String>();
		List<String> inBound = new ArrayList<String>();
		List<String> outBound = new ArrayList<String>();
		Float maxIn = Float.valueOf(0);
		Float avgIn = Float.valueOf(0);
		Float maxOut = Float.valueOf(0);
		Float avgOut = Float.valueOf(0);
		Float sumIn = Float.valueOf(0);
		Float sumOut = Float.valueOf(0);
		int count = 0;
		for(VRpDyIpbb ipbb : dyIpbbList)
		{
			count ++;
			sumIn += ipbb.getMaxInBound();
			sumOut += ipbb.getMaxOutBound();
			if(ipbb.getMaxInBound() > maxIn)
				maxIn = ipbb.getMaxInBound();
			if(ipbb.getMaxOutBound() > maxOut)
				maxOut = ipbb.getMaxOutBound();
			
			categories.add(ipbb.getHour().toString());
			inBound.add(ipbb.getMaxInBound().toString());
			outBound.add(ipbb.getMaxOutBound().toString());
			title= ipbb.getInterfaceName();
		}
		
		avgIn = Float.valueOf(Math.round(100*sumIn/count)/100);
		avgOut = Float.valueOf(Math.round(100*sumOut/count)/100);
		Map<String, List<String>> trafSeries = new LinkedHashMap<String, List<String>>();
		trafSeries.put("InBound Maximum: "+maxIn+" Avg: "+avgIn, inBound);
		trafSeries.put("OutBound "+maxOut+" Avg: "+avgOut, outBound);
		model.addAttribute("trafChart",Chart.StackedArea("trafChart", title, "Date: "+ s[0] + " " + s[1] +":00"+ " To:"+ s[2]+":00", "MBits per second", "M", categories, trafSeries));
		
		//model.addAttribute("trafChart", Chart.multiColumn("trafChart", "Bits per second", categories, trafSeries));

		model.addAttribute("dyIpbbList", dyIpbbList);
		return "dyIpbb";
	}

	// Daily report
	@RequestMapping(value = "dy")
	public String showReport(@RequestParam(required = false) String routeid,@RequestParam(required = false) String interfaceid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model, HttpServletRequest request) {
		List<HIpbbBwlist> listRoute = ipbbDao.getAllLink();
		
		routeid = ModelAddtributes.checkIpbb(model , listRoute, routeid, "ipbb-data");
		interfaceid = ModelAddtributes.checkInterface(model, interfaceid);
		String[] s = ModelAddtributes.checkDate2(model, startDate, endDate).split(";");
		List<VRpDyIpbb> dyIpbbList = dyIpbbDAO.filterDaily("V_RP_DY_IPBB", routeid, interfaceid, s[0], s[1]);
		model.addAttribute("dyIpbbList", dyIpbbList);
		List<HighlightConfig> highlightConfigList = highlightDao.getByKey("TT");
		model.addAttribute("highlight", Helper.highLightLine(highlightConfigList, "dyIpbbList"));
		return "dyIpbb";
	}
	// Weekly Report
		@RequestMapping(value = "wk")
		public String showReportwk(@RequestParam(required = false) String routeid,@RequestParam(required = false) String interfaceid, @RequestParam(required = false) Integer startWeek,
				@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek,
				@RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
			List<HIpbbBwlist> listRoute = ipbbDao.getAllLink(); 
			routeid = ModelAddtributes.checkIpbb(model , listRoute, routeid, "ipbb-data");
			interfaceid = ModelAddtributes.checkInterface(model, interfaceid);
			String[] s = ModelAddtributes.checkWeek(model, startWeek, endWeek, startYear, endYear).split(";");
			List<VRpDyIpbb> dyIpbbList = dyIpbbDAO.filterWeekly("V_RP_WK_IPBB", routeid, interfaceid, s[0], s[1], s[2], s[3]);
			model.addAttribute("dyIpbbList", dyIpbbList);
			List<HighlightConfig> highlightConfigList = highlightDao.getByKey("TT");
			model.addAttribute("highlight", Helper.highLightLine(highlightConfigList, "dyMscList"));
			return "dyIpbb";
		}

		// Month Report
		@RequestMapping(value = "mn")
		public String showReportmn(@RequestParam(required = false) String routeid,@RequestParam(required = false) String interfaceid, @RequestParam(required = false) Integer startMonth,
				@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endMonth,
				@RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
			 List<HIpbbBwlist> listRoute = ipbbDao.getAllLink();
			routeid = ModelAddtributes.checkIpbb(model , listRoute, routeid, "ipbb-data");
			interfaceid = ModelAddtributes.checkInterface(model, interfaceid);
			String[] s = ModelAddtributes.checkMonth(model, startMonth, endMonth, startYear, endYear).split(";");
			List<VRpDyIpbb> dyIpbbList = dyIpbbDAO.filterMonthly("V_RP_MN_IPBB", routeid, interfaceid, s[0], s[1], s[2], s[3]);
			model.addAttribute("dyIpbbList", dyIpbbList);
			List<HighlightConfig> highlightConfigList = highlightDao.getByKey("TT");
			model.addAttribute("highlight", Helper.highLightLine(highlightConfigList, "dyMscList"));
			return "dyIpbb";
		}
	
}
