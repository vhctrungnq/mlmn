package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyDnsDAO;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyDns;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;
import vn.com.vhc.vmsc2.statistics.web.utils.ModelAddtributes;

/**
 * 
 * @author Mr. ThangPX
 * @return Nds Report Level Daily, Hourly, Weekly, Monthly
 */
@Controller
@RequestMapping("/report/core/csi-dns/*")
public class DnsController extends BaseController {
	@Autowired
	private VRpDyDnsDAO dyCsiDao; 
	@Autowired
	private HighlightConfigDAO highlightDao; 
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	@RequestMapping(value = "hr")
	public String showReport(@RequestParam(required = false) String nodeid, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, ModelMap model, HttpServletRequest request) {
		if(nodeid == null)
			nodeid = "";
		model.addAttribute("nodeid", nodeid);
		model.addAttribute("linkReport", "csi-dns");
		model.addAttribute("nodeType", "Nodeid");
		String[] s = ModelAddtributes.checkHour(model, startDate, startHour, endHour).split(";");
		List<VRpDyDns> dyCsiList = dyCsiDao.filterHourly("V_RP_HR_DNS", nodeid, s[0], s[1], s[2]);
		model.addAttribute("dyCsiList", dyCsiList);
		return "dyDns";
	}

	// Daily report
	@RequestMapping(value = "dy")
	public String showReport(@RequestParam(required = false) String nodeid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model, HttpServletRequest request) {
		if(nodeid == null)
			nodeid = "";
		model.addAttribute("nodeid", nodeid);
		model.addAttribute("linkReport", "csi-dns");
		model.addAttribute("nodeType", "Nodeid");
		String[] s = ModelAddtributes.checkDate2(model, startDate, endDate).split(";");
		List<VRpDyDns> dyCsiList = dyCsiDao.filterDaily("V_RP_DY_DNS", nodeid, s[0], s[1]);
		model.addAttribute("dyCsiList", dyCsiList);
		List<HighlightConfig> highlightConfigList = highlightDao.getByKey("AAA");
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "dyCsiList"));
		
		List<String> dnsList = new ArrayList<String>();
		for(VRpDyDns dns : dyCsiList)
		{
			dnsList.add(dns.getNodeid());
		}
		//Chart 30 ngay
		long currentTime = System.currentTimeMillis();		
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		long time = endDate.getTime();
		DateTime dt = new DateTime(time);
		Date destDay = new Date(time);
		Date startDay = dt.minusDays(30).toDate();
		Map<String, List<Float>> series1 = new LinkedHashMap<String, List<Float>>();
		Map<String, List<Float>> series2 = new LinkedHashMap<String, List<Float>>();
		
		List<String> categories = new ArrayList<String>();
		String  chartArea1 = "", chartArea2 = "";
		List<VRpDyDns> dayList = dyCsiDao.filterDaily("V_RP_DY_DNS", dnsList.get(0),df.format(startDay), df.format(destDay));
		for(VRpDyDns day: dayList)
		{
			String strDay = df.format(day.getDay());
			categories.add(strDay);
		}
		chartArea1 += Chart.basicLineNew("chart1", "Biểu đồ CPU used","%", categories, series1);
		chartArea2 += Chart.basicLineNew("chart2", "Biểu đồ Utility","%", categories, series2);
		model.addAttribute("chartdiv1", chartArea1);
		model.addAttribute("chartdiv2", chartArea2);
		return "dyDns";
	}

	// Weekly Report
	@RequestMapping(value = "wk")
	public String showReportwk(@RequestParam(required = false) String nodeid, @RequestParam(required = false) Integer startWeek,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek,
			@RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		if(nodeid == null)
			nodeid = "";
		model.addAttribute("nodeid", nodeid);
		model.addAttribute("linkReport", "csi-dns");
		model.addAttribute("nodeType", "Nodeid");
		String[] s = ModelAddtributes.checkWeek(model, startWeek, endWeek, startYear, endYear).split(";");
		List<VRpDyDns> dyCsiList = dyCsiDao.filterWeekly("V_RP_WK_DNS", nodeid, s[0], s[1], s[2], s[3]);
		model.addAttribute("dyCsiList", dyCsiList);
		List<HighlightConfig> highlightConfigList = highlightDao.getByKey("AAA");
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "dyCsiList"));
		return "dyDns";
	}

	// Month Report
	@RequestMapping(value = "mn")
	public String showReportmn(@RequestParam(required = false) String nodeid, @RequestParam(required = false) Integer startMonth,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endMonth,
			@RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		if(nodeid == null)
			nodeid = "";
		model.addAttribute("nodeid", nodeid);
		model.addAttribute("linkReport", "csi-dns");
		model.addAttribute("nodeType", "Nodeid");
		String[] s = ModelAddtributes.checkMonth(model, startMonth, endMonth, startYear, endYear).split(";");
		List<VRpDyDns> dyCsiList = dyCsiDao.filterMonthly("V_RP_MN_DNS", nodeid, s[0], s[1], s[2], s[3]);
		model.addAttribute("dyCsiList", dyCsiList);
		List<HighlightConfig> highlightConfigList = highlightDao.getByKey("AAA");
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "dyCsiList"));
		return "dyDns";
	}
}
