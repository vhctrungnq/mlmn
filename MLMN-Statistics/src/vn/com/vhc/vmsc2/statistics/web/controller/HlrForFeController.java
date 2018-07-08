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
import vn.com.vhc.vmsc2.statistics.dao.VRpDyHlrForFeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrHlrForFeDAO;
import vn.com.vhc.vmsc2.statistics.domain.Hhlr;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyHlrForFe;

import vn.com.vhc.vmsc2.statistics.domain.VRpHrHlrForFe;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;

@Controller
@RequestMapping("/report/RpHrHrlFor-Fe/*")
public class HlrForFeController extends BaseController {
	@Autowired
	private VRpHrHlrForFeDAO HrhlrforFEDao;
	@Autowired
	private VRpDyHlrForFeDAO VRpDyHlrForFeDAO;
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
		List<VRpHrHlrForFe> HrhlrFe = HrhlrforFEDao.filter(startHour, startDate, endHour, endDate, nodeid);

		if (endDate.compareTo(startDate) < 0) {
			startDate = new Date();
			HrhlrFe = HrhlrforFEDao.filter2(startHour, startDate, endHour, nodeid);
		} else if (endDate.compareTo(startDate) == 0) {

			HrhlrFe = HrhlrforFEDao.filter2(startHour, startDate, endHour, nodeid);
		}
		List<Hhlr> NodeList = hlrDao.filterHlr("FE");
		model.addAttribute("NodeList", NodeList);
		model.addAttribute("nodeid", nodeid);
		model.addAttribute("HrhlrFe", HrhlrFe);
		model.addAttribute("hourList", hourList);
		return "HrNGHLRForFe";
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

		List<VRpDyHlrForFe> VRpDyHlrForFe = VRpDyHlrForFeDAO.filter(df.format(startDate), df.format(endDate), nodeid);
		model.addAttribute("VRpDyHlrForFe", VRpDyHlrForFe);

		List<Hhlr> nodeList = hlrDao.filterHlr("FE");
		model.addAttribute("NodeList", nodeList);
		model.addAttribute("nodeid", nodeid);

		/* Chart Start */
		long time = System.currentTimeMillis();
		DateTime dt = new DateTime(time- 24 * 60 * 60 * 1000);
		Date destDay = new Date(time - 24 * 60 * 60 * 1000);
		Date startDay = dt.minusDays(30).toDate();
		
	
		Map<String, List<String>> series = new LinkedHashMap<String, List<String>>();
		List<String> categories = new ArrayList<String>();
		String chartArea = "";

		for(int i = 0; i < nodeList.size(); i++)
		{
			
			List<VRpDyHlrForFe> dyList = VRpDyHlrForFeDAO.filter(df.format(startDay), df.format(destDay),nodeList.get(i).getHlrid());
			List<String> inMaxcpList = new ArrayList<String>();
			for(VRpDyHlrForFe record : dyList){
				if(record.getAvgCpuUsage()==null)
					record.setAvgCpuUsage(Float.parseFloat("0"));
				inMaxcpList.add(record.getAvgCpuUsage().toString());
				
			
			}
			series.put(nodeList.get(i).getHlrid(), inMaxcpList);
			
		}
		List<VRpDyHlrForFe> hrlDay = VRpDyHlrForFeDAO.filterDay(df.format(startDay), df.format(destDay));
		for(int j = 0; j < hrlDay.size(); j++){
			categories.add(df.format(hrlDay.get(j).getDay()));
		}
		
		chartArea += Chart.chartBasicLine("chart", "Biểu đồ Average CPU Usage (%)", categories, series,"%");
		model.addAttribute("chartdiv", chartArea);
		return "DyNGHLRForFe";
	}
}
