package controller.feedback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dao.HProvinceFbDAO;
import dao.VRpHrFeedbackDAO;
import vo.VRpHrFeedback;
import vo.alarm.utils.Chart;

@Controller
@RequestMapping("/chart/feedback/hr/*")
public class HrChartFeedbackController {

	@Autowired
	private VRpHrFeedbackDAO vRpHrFeedbackDAO;
	
	@Autowired
 	private HProvinceFbDAO hProvinceFbDAO;
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping(value = "list")
	public ModelAndView showList(@RequestParam(required = false) String date, 
			@RequestParam(required = false) String province, ModelMap model) {
		// voi moi district tra lai tat ca cac cap (hour, so_luong_feed_back)
		Map<String, Map<Integer, Integer>> districtMap = new HashMap<String, Map<Integer, Integer>>();
		// map con
		Map<Integer, Integer> subMap;
		List<String> categories  = new ArrayList<String>();
		Map<String, List<Number>> series = new HashMap<String, List<Number>>();
		// day gia tri tung do
		int[] valueArray = new int[24];
		
		List<String> provinceList = hProvinceFbDAO.getAllProvince(); 
		
		if (province == null) {
			province = "HCM";
		}
		
		if (date == null) {
			date = df.format(new Date()); 
		}
		
		for (int i = 0; i < 24; i++) {
			categories.add(i + "");
		}
		
		List<VRpHrFeedback> dataList = vRpHrFeedbackDAO.getDataForChart(date, province);
		for (VRpHrFeedback record : dataList) {
			String district = record.getDistrict();
			if (districtMap.containsKey(district)) {
				districtMap.get(district).put(record.getHour(), record.getSoLuong());
			} else {
				subMap = new HashMap<Integer, Integer>();
				subMap.put(record.getHour(), record.getSoLuong());
				districtMap.put(district, subMap);
			}
		}
		
		for (String district : districtMap.keySet()) {
			subMap = districtMap.get(district);
			valueArray = new int[24];
			for (int hour : subMap.keySet()) {
				valueArray[hour] = subMap.get(hour);
			}
			ArrayList<Number> tmpList = new ArrayList<Number>();
			for (int i = 0; i < valueArray.length; i++) {
				tmpList.add(valueArray[i]);
			}
			series.put(district, tmpList);
		}
		
		String chart = Chart.newBasicLine("chart", "Bieu do feedback theo gio", "so feddback", categories, series);
		model.addAttribute("date", date);
		model.addAttribute("province", province);
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("chart", chart);
		return new ModelAndView("jspfeedback/chart/hrChartFb");
	}
}
