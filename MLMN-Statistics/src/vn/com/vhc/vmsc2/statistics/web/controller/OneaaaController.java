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
import vn.com.vhc.vmsc2.statistics.dao.VRpDyOneaaaDAO;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyOneaaa;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;
import vn.com.vhc.vmsc2.statistics.web.utils.ModelAddtributes;

/**
 * 
 * @author Mr. ThangPX
 * @return OneAAA Report Level Daily, Hourly, Weekly, Monthly
 */
@Controller
@RequestMapping("/report/core/csi-oneaaa/*")
public class OneaaaController extends BaseController {
	@Autowired
	private VRpDyOneaaaDAO dyCsiDao;
	@Autowired
	private HighlightConfigDAO highlightDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	@RequestMapping(value = "hr")
	public String showReport(@RequestParam(required = false) String nodeid, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, ModelMap model, HttpServletRequest request) {
		if(nodeid == null)
			nodeid = "";
		model.addAttribute("nodeid", nodeid);
		model.addAttribute("linkReport", "csi-oneaaa");
		model.addAttribute("nodeType", "Nodeid");
		String[] s = ModelAddtributes.checkHour(model, startDate, startHour, endHour).split(";");
		List<VRpDyOneaaa> dyCsiList = dyCsiDao.filterHourly("V_RP_HR_ONEAAA", nodeid, s[0], s[1], s[2]);
		model.addAttribute("dyCsiList", dyCsiList);
		return "dyOneaaa";
	}

	// Daily report
	@RequestMapping(value = "dy")
	public String showReport(@RequestParam(required = false) String nodeid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model, HttpServletRequest request) {
		if(nodeid == null)
			nodeid = "";
		model.addAttribute("nodeid", nodeid);
		model.addAttribute("linkReport", "csi-oneaaa");
		model.addAttribute("nodeType", "Nodeid");
		String[] s = ModelAddtributes.checkDate2(model, startDate, endDate).split(";");
		List<VRpDyOneaaa> dyCsiList = dyCsiDao.filterDaily("V_RP_DY_ONEAAA", nodeid, s[0], s[1]);
		model.addAttribute("dyCsiList", dyCsiList);
		List<HighlightConfig> highlightConfigList = highlightDao.getByKey("AAA");
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "dyCsiList"));
		
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
		List<String> categories = new ArrayList<String>();
		String  chartArea1 = "" ;
		List<VRpDyOneaaa> dyList = dyCsiDao.filterDaily("V_RP_DY_ONEAAA", "",df.format(startDay), df.format(destDay));
		List<Float> aaaLoadList = new ArrayList<Float>();
		List<Float> aaaRessrList = new ArrayList<Float>();
		
		for(VRpDyOneaaa record : dyList){
			if(record.getAaaLoad()==null)
				record.setAaaLoad(Float.parseFloat("0"));
			if(record.getAaaRessr()==null)
				record.setAaaRessr(Float.parseFloat("0"));
		aaaLoadList.add(record.getAaaLoad());
		aaaRessrList.add(record.getAaaRessr());
		//Add categories
			String strDay = df.format(record.getDay());
			categories.add(strDay);
		}
		series1.put("AAA_LOAD", aaaLoadList);
		series1.put("AAA_RESSR", aaaRessrList);
		
		chartArea1 += Chart.basicLineNew("chart1", "Biểu đồ AAA_LOAD & AAA_RESSR của AAA_HCM","%", categories, series1); 
		model.addAttribute("chartdiv1", chartArea1); 
		
		// Bieu do CSI CMD
		
		return "dyOneaaa";
	}

	// Weekly Report
	@RequestMapping(value = "wk")
	public String showReportwk(@RequestParam(required = false) String nodeid, @RequestParam(required = false) Integer startWeek,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek,
			@RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		if(nodeid == null)
			nodeid = "";
		model.addAttribute("nodeid", nodeid);
		model.addAttribute("linkReport", "csi-oneaaa");
		model.addAttribute("nodeType", "Nodeid");
		String[] s = ModelAddtributes.checkWeek(model, startWeek, endWeek, startYear, endYear).split(";");
		List<VRpDyOneaaa> dyCsiList = dyCsiDao.filterWeekly("V_RP_WK_ONEAAA", nodeid, s[0], s[1], s[2], s[3]);
		model.addAttribute("dyCsiList", dyCsiList);
		List<HighlightConfig> highlightConfigList = highlightDao.getByKey("AAA");
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "dyCsiList"));
		
		
		return "dyOneaaa";
	}

	// Month Report
	@RequestMapping(value = "mn")
	public String showReportmn(@RequestParam(required = false) String nodeid, @RequestParam(required = false) Integer startMonth,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endMonth,
			@RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		if(nodeid == null)
			nodeid = "";
		model.addAttribute("nodeid", nodeid);
		model.addAttribute("linkReport", "csi-oneaaa");
		model.addAttribute("nodeType", "Nodeid");
		String[] s = ModelAddtributes.checkMonth(model, startMonth, endMonth, startYear, endYear).split(";");
		List<VRpDyOneaaa> dyCsiList = dyCsiDao.filterMonthly("V_RP_MN_ONEAAA", nodeid, s[0], s[1], s[2], s[3]);
		model.addAttribute("dyCsiList", dyCsiList);
		List<HighlightConfig> highlightConfigList = highlightDao.getByKey("AAA");
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "dyCsiList"));
		return "dyOneaaa";
	}
}
