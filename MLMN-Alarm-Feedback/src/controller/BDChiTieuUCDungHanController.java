package controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dao.AlAlarmWorkDetailsDAO;
import vo.AlAlarmWorkDetails;
import vo.alarm.utils.Chart;

@Controller
@RequestMapping("/chart/*")
public class BDChiTieuUCDungHanController {

	@Autowired
	private AlAlarmWorkDetailsDAO alAlarmWorkDetailsDAO;

	@RequestMapping("{function}")
	public String showUCTTList(@RequestParam(required = true) String type, ModelMap model,
			@PathVariable String function) {

		List<AlAlarmWorkDetails> ucttDataList = new ArrayList<AlAlarmWorkDetails>();
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			date = df.parse("20/5/2014");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal.setTime(date);
//		String endTime = df.format(date);
		String endTime = "20/5/2014";
		cal.add(Calendar.DATE, -4);
		String startTime = df.format(cal.getTime());
		ucttDataList = alAlarmWorkDetailsDAO.getUCDH(function, type, startTime, endTime);
		List<String> categories = new ArrayList<String>();
		for (AlAlarmWorkDetails day : alAlarmWorkDetailsDAO.getDay(startTime, endTime)) {
			categories.add(df.format(day.getNgayTruc()).substring(0, df.format(day.getNgayTruc()).lastIndexOf('/')));
		}
		Map<String, List<Number>> series = new LinkedHashMap<String, List<Number>>();
		if (type.equalsIgnoreCase("province")) {
			List<AlAlarmWorkDetails> provinceList = alAlarmWorkDetailsDAO.getAllProvince(startTime, endTime);
			for(AlAlarmWorkDetails al : provinceList) {
				if (al.getProvince() != null && !al.getProvince().equals("")) {
					series.put(al.getProvince(), new ArrayList<Number>());
				}
				
			}			
			
			for(AlAlarmWorkDetails al : ucttDataList) {
				if (al.getProvince() != null && !al.getProvince().equals("")) {
					if (function.equalsIgnoreCase("uc-thong-tin")) {
						series.get(al.getProvince()).add(al.getCt_uctt());
					} else if (function.equalsIgnoreCase("uc-sc")){
						series.get(al.getProvince()).add(al.getCt_ucsc());
					}
				}	 
			}
						
		} else if (type.equalsIgnoreCase("team_process")) {
			List<String> teamList = new ArrayList<String>();
			for (AlAlarmWorkDetails al : alAlarmWorkDetailsDAO.getAllTeam(startTime, endTime)) {
				if (al.getTeamProcess() != null && !al.getTeamProcess().equals("")) {
					teamList.add(al.getTeamProcess());
				}
			}

			for(String al : teamList) {
				if (al != null && !al.equals("")) {
					series.put(al, new ArrayList<Number>());
				}
				
			}			
			for(AlAlarmWorkDetails al : ucttDataList) {
				if (al.getTeamProcess() != null && !al.getTeamProcess().equals("")) {
					if (function.equalsIgnoreCase("uc-thong-tin")) {
						series.get(al.getTeamProcess()).add(al.getCt_uctt());
					} else if (function.equalsIgnoreCase("uc-sc")) {
						series.get(al.getTeamProcess()).add(al.getCt_ucsc());
					}
				}			
				
			}
		}
		String chartTitle = "BIỂU ĐỒ CHỈ TIÊU ỨNG CỨU " + (function.equals("uc-thong-tin") ? "ỨNG CỨU THÔNG TIN" : "ỨNG CỨU SỰ CỐ");
		chartTitle += " ĐÚNG HẠN VỚI " + (type.equalsIgnoreCase("province") ? "TỈNH, THÀNH PHỐ" : "TỔ VT");
		String chart = Chart.newBasicLine("chart", "", "%", categories, series);
		model.addAttribute("chartTitle", chartTitle);
		model.addAttribute("chart", chart);
		// startTime, endTime);
		return "jsp/BDChiTieuUCDungHan";
	}
}
