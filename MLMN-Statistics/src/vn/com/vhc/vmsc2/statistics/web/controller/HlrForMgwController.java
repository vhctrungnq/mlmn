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

import vn.com.vhc.vmsc2.statistics.dao.HhlrDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyHlrForMgwDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrHlrForMgwDAO;
import vn.com.vhc.vmsc2.statistics.domain.Hhlr;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyHlrForMgw;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrHlrForMgw;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;

@Controller
@RequestMapping("/report/core/hlr-for-mgw/*")
public class HlrForMgwController extends BaseController {
	@Autowired
	private VRpHrHlrForMgwDAO vRpHrHlrForMgwDAO;
	@Autowired
	private VRpDyHlrForMgwDAO vRpDyHlrForMgwDAO;
	@Autowired
	private HhlrDAO hlrDao;

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("hr")
	public String ShowReport(@RequestParam(required = false) String nodeid, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
			ModelMap model, HttpServletRequest request) {
		
		if (endDate == null) {
			endDate = new Date();
		}
		if (startDate == null) {
			startDate = endDate;
		}
		if (nodeid == null) {
			nodeid = "";
		}
		if (endHour == null) {
			endHour = "23";
		} else {
			if (endHour.indexOf(":") > 0)
				endHour = endHour.substring(0, endHour.indexOf(":"));
			else
				endHour = "23";
		}
		if (startHour == null) {
			startHour = "0";
		} else {
			if (startHour.indexOf(":") > 0)
				startHour = startHour.substring(0, startHour.indexOf(":"));
			else
				startHour = "0";
		}
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("startHour", startHour + ":00");
		model.addAttribute("endHour", endHour + ":00");

		String[] hourList = { "0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00",
				"15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00" };
		List<VRpHrHlrForMgw> vRpHrHlrForMgw = vRpHrHlrForMgwDAO.filter(startHour, startDate, endHour, endDate, nodeid);

		if (endDate.compareTo(startDate) < 0) {
			startDate = new Date();
			vRpHrHlrForMgw = vRpHrHlrForMgwDAO.filter2(startHour, startDate, endHour, nodeid);
		} else if (endDate.compareTo(startDate) == 0) {

			vRpHrHlrForMgw = vRpHrHlrForMgwDAO.filter2(startHour, startDate, endHour, nodeid);
		}
		List<Hhlr> nodeList = hlrDao.filterHlr("MGW");
		model.addAttribute("NodeList", nodeList);
		model.addAttribute("nodeid", nodeid);
		model.addAttribute("vRpHrHlrForMgw", vRpHrHlrForMgw);
		model.addAttribute("hourList", hourList);
		return "hrHlrForMgwList";
	}

	@RequestMapping("dy")
	public String showReport(@RequestParam(required = false) String nodeid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model) {
		
		if (endDate == null) {
			endDate = new Date();
		}
		if (nodeid == null) {
			nodeid = "";
		}
		if (startDate == null) {
			startDate = endDate;
		}
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));

		List<VRpDyHlrForMgw> vRpDyHlrForMgw = vRpDyHlrForMgwDAO.filter(df.format(startDate), df.format(endDate), nodeid);
		model.addAttribute("vRpDyHlrForMgw", vRpDyHlrForMgw);

		List<Hhlr> nodeList = hlrDao.filterHlr("MGW");
		model.addAttribute("NodeList", nodeList);
		model.addAttribute("nodeid", nodeid);

		/* Chart Start */
		long time = endDate.getTime();
		DateTime dt = new DateTime(time);
		Date destDay = new Date(time);
		Date startDay = dt.minusDays(30).toDate();
		
		Map<String, List<Float>> series1 = new LinkedHashMap<String, List<Float>>();
		Map<String, List<Float>> series2 = new LinkedHashMap<String, List<Float>>();
		Map<String, List<Float>> series3 = new LinkedHashMap<String, List<Float>>();
		List<String> categories = new ArrayList<String>();
		String  chartArea1 = "", chartArea2 = "", chartArea3 = "";
		
		for(int i = 0; i < nodeList.size(); i++)
		{
			
			List<VRpDyHlrForMgw> dyList = vRpDyHlrForMgwDAO.filter(df.format(startDay), df.format(destDay),nodeList.get(i).getHlrid());
			
			List<Float> cpuUssageList = new ArrayList<Float>();
			List<Float> cpuUssageFlowList = new ArrayList<Float>();
			List<Float> ipFlowList = new ArrayList<Float>();
			
			
			for(VRpDyHlrForMgw record : dyList){
				if(record.getMaxOccupiedRateOfCpu()==null)
					record.setMaxOccupiedRateOfCpu(Float.parseFloat("0"));
				if(record.getUsageFlow()==null)
					record.setUsageFlow(Float.parseFloat("0"));
				if(record.getIpFlow()==null)
					record.setIpFlow(Float.parseFloat("0"));
				
				
				
				cpuUssageList.add(record.getMaxOccupiedRateOfCpu());
				cpuUssageFlowList.add(record.getUsageFlow());
				ipFlowList.add(record.getIpFlow());
				
			
			}
			series1.put(nodeList.get(i).getHlrid(), cpuUssageList);
			series2.put(nodeList.get(i).getHlrid(), cpuUssageFlowList);
			series3.put(nodeList.get(i).getHlrid(), ipFlowList);
			
			
		}
		List<VRpDyHlrForMgw> mscDay = vRpDyHlrForMgwDAO.filterDay(df.format(startDay), df.format(destDay));
		for(int j = 0; j < mscDay.size(); j++){
			String strDay = df.format(mscDay.get(j).getDay());
			strDay = strDay.substring(0, strDay.lastIndexOf("/"));
			categories.add(strDay);
		}
		chartArea1 += Chart.basicLineNew("chart1", "Average CPU Ussage (%)","%", categories, series1);
		chartArea2 += Chart.basicLineNew("chart2", "TC Ussage(of VPD board) (%)","%", categories, series2);
		chartArea3 += Chart.basicLineNew("chart3", "GE IP Flow Ussage (%)","%", categories, series3);
		model.addAttribute("chartdiv1", chartArea1);
		model.addAttribute("chartdiv2", chartArea2);
		model.addAttribute("chartdiv3", chartArea3);
		return "dyHlrForMgwList";
	}
}