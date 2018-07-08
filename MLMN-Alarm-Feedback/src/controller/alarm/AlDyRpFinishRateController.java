package controller.alarm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vo.AlDyRpFinishRate;
import vo.SYS_PARAMETER;
import vo.alarm.utils.Chart;
import vo.alarm.utils.DateTools;

import dao.AlDyRpFinishRateDAO;
import dao.VAlHrFinishRateDAO;
/**
 * Function        : Bieu do ty le canh bao da ket thuc
 * Created By      : BUIQUANG
 * Create Date     : 28/11/2013
 * Modified By     : 
 * Modify Date     : 
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/alarm/ty-le-canh-bao-da-ket-thuc/*")
public class AlDyRpFinishRateController {

	@Autowired
	private AlDyRpFinishRateDAO alDyRpFinishRateDAO;
	@Autowired
	private VAlHrFinishRateDAO vAlHrFinishRateDAO;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat df_little = new SimpleDateFormat("MM/yyyy");
	private DateFormat df_hh = new SimpleDateFormat("HH:mm");
	
	@RequestMapping(value = "{function}")
    public String list(
    				   @RequestParam(required = false) String startDate,
					   @RequestParam(required = false) String endDate,
					   @RequestParam(required = false) String startMinutes,
    				   @RequestParam(required = false) String endMinutes,
    				   @RequestParam(required = false) String autoRefresh,
					   @PathVariable String function, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		String timeNow = df_hh.format(new Date());
		if(startMinutes== null && endMinutes == null){
			List<SYS_PARAMETER> khoangTgKetThuc = alDyRpFinishRateDAO.khoangTgKetThuc();
			if(khoangTgKetThuc.size() > 1){
				startMinutes = khoangTgKetThuc.get(0).getValue();
				endMinutes = khoangTgKetThuc.get(1).getValue();
			}else{
				startMinutes = "0";
				endMinutes = "300";
			}
		}
		try{
			/*if (startMinutes == null || startMinutes.equals("") || DateTools.isValidNumber(startMinutes)==false)
			{
				startMinutes = "0";
			}
			
			if (endMinutes == null || endMinutes.equals("") || DateTools.isValidNumber(endMinutes)==false)
			{
				endMinutes = "300";
			}*/
			
			String script = "";
			
			List<AlDyRpFinishRate> ptMangList = null;
			if(function.equals("hien-tai")){
				List<SYS_PARAMETER> titleSystem = alDyRpFinishRateDAO.titleFormFinishRateCurrent();
				model.addAttribute("titleSystem", titleSystem.get(0).getValue());
				
				if (startDate == null || startDate.equals("") || DateTools.isValid("dd/MM/yyyy", startDate)==false)
				{
					startDate = df.format(new Date());
				}
				
				ptMangList = vAlHrFinishRateDAO.distinctSystemVHr(startDate, startMinutes, endMinutes);
				
				for(int i=0;i<ptMangList.size();i++){
					List<AlDyRpFinishRate> alarmClass = vAlHrFinishRateDAO.distinctAlarmClassVHr(ptMangList.get(i).getSystem());
					Map<String, List<String>> series = new LinkedHashMap<String, List<String>>();
					for(int j=0;j<alarmClass.size();j++){
						List<AlDyRpFinishRate> alarmClassList = vAlHrFinishRateDAO.tyLeTheoPTMangVHr(ptMangList.get(i).getSystem(), alarmClass.get(j).getAlarmClass(), startDate, startMinutes, endMinutes);
						List<String> seriesList = new ArrayList<String>();
						
						for(AlDyRpFinishRate record : alarmClassList){
							seriesList.add(record.getToDuration() + "-" + record.getRate().trim());
						}
						
						series.put(alarmClass.get(j).getAlarmClass(), seriesList);
					}
					script += Chart.logarithmicAxis("chartdiv" + ptMangList.get(i).getSystem().toUpperCase(), ptMangList.get(i).getSystem().toUpperCase(), "Tỷ lệ cảnh báo kết thúc (%)", "Minutes", "%", "Thời gian cảnh báo kết thúc", series);
				}
				
				
			}
			else if(function.equals("theo-ngay")){
				
				List<SYS_PARAMETER> titleSystem = alDyRpFinishRateDAO.titleFormFinishRateOther();//sysParameterDao.getSPByCodeAndName("TITLE_REPORT_WEEKS", "TY_LE_CB_DA_KET_THUC_TRONG_NGAY");
				model.addAttribute("titleSystem", titleSystem.get(0).getValue());
				
				if (startDate == null || startDate.equals("") || DateTools.isValid("dd/MM/yyyy", startDate)==false)
				{
					//startDate = df.format(new Date());
					
					Calendar cal = Calendar.getInstance();
					cal.setTime(new Date());
					cal.add(Calendar.DAY_OF_MONTH, -1);
					
					startDate = df.format(cal.getTime());
				}
				
				ptMangList = alDyRpFinishRateDAO.distinctSystem(startDate, null, null, startMinutes, endMinutes);
				
				for(int i=0;i<ptMangList.size();i++){
					List<AlDyRpFinishRate> alarmClass = alDyRpFinishRateDAO.distinctAlarmClass(ptMangList.get(i).getSystem());
					
					Map<String, List<String>> series = new LinkedHashMap<String, List<String>>();
					for(int j=0;j<alarmClass.size();j++){
						List<AlDyRpFinishRate> alarmClassList = alDyRpFinishRateDAO.tyLeTheoPTMang(ptMangList.get(i).getSystem(), alarmClass.get(j).getAlarmClass(), startDate, null, null, startMinutes, endMinutes);
						
						List<String> seriesList = new ArrayList<String>();
						
						for(AlDyRpFinishRate record : alarmClassList){
							seriesList.add(record.getToDuration() + "-" + record.getRate().trim());
						}
						
						series.put(alarmClass.get(j).getAlarmClass(), seriesList);
					}
					script += Chart.logarithmicAxis("chartdiv" + ptMangList.get(i).getSystem().toUpperCase(), ptMangList.get(i).getSystem().toUpperCase(), "Tỷ lệ cảnh báo kết thúc (%)", "Minutes", "%", "Thời gian cảnh báo kết thúc", series);
				}
				
			}
			else if(function.equals("theo-thang")){
				
				List<SYS_PARAMETER> titleSystem = alDyRpFinishRateDAO.titleFormFinishRateOther();//sysParameterDao.getSPByCodeAndName("TITLE_REPORT_WEEKS", "TY_LE_CB_DA_KET_THUC_TRONG_THANG");
				model.addAttribute("titleSystem", titleSystem.get(0).getValue());
				
				// Ngay thang
				if (startDate == null || startDate.equals("")||DateTools.isValid("MM/yyyy", startDate)==false) {
					
					startDate = df_little.format(DateTools.startMonth(new Date()));
				
				}
				String start = "01/" + startDate;
				/*if (endDate == null || endDate.equals("")||DateTools.isValid("dd/MM/yyyy", endDate)==false)
				{
					Calendar cal = Calendar.getInstance();	
					cal.setTime(new Date());
					endDate = df.format(cal.getTime());
				}*/
				
				ptMangList = alDyRpFinishRateDAO.distinctSystem(null, start, endDate, startMinutes, endMinutes);
				
				for(int i=0;i<ptMangList.size();i++){
					List<AlDyRpFinishRate> alarmClass = alDyRpFinishRateDAO.distinctAlarmClass(ptMangList.get(i).getSystem());
					Map<String, List<String>> series = new LinkedHashMap<String, List<String>>();
					for(int j=0;j<alarmClass.size();j++){
						List<AlDyRpFinishRate> alarmClassList = alDyRpFinishRateDAO.tyLeTheoPTMang(ptMangList.get(i).getSystem(), alarmClass.get(j).getAlarmClass(), null, start, endDate, startMinutes, endMinutes);
						
						List<String> seriesList = new ArrayList<String>();
						
						for(AlDyRpFinishRate record : alarmClassList){
							seriesList.add(record.getToDuration() + "-" + record.getRate().trim());
						}
						
						series.put(alarmClass.get(j).getAlarmClass(), seriesList);
					}
					script += Chart.logarithmicAxis("chartdiv" + ptMangList.get(i).getSystem().toUpperCase(), ptMangList.get(i).getSystem().toUpperCase(), "Tỷ lệ cảnh báo kết thúc (%)", "Minutes", "%", "Thời gian cảnh báo kết thúc", series);
				}
				
			}
			model.addAttribute("chartdivScript", script);
			
			String html = "";
			if((ptMangList.size()%2) == 1){
				int j = ptMangList.size() - 1;
				for(int i=j-1;i>0;i-=2){
					html = "<tr><td><div id=\"" + "chartdiv" + ptMangList.get(i-1).getSystem().toUpperCase() + "\" style=\"width: 98%; margin: 1em auto\"></div></td><td><div id=\"" + "chartdiv" + ptMangList.get(i).getSystem().toUpperCase() + "\" style=\"width: 98%; margin: 1em auto\"></div></td></tr>" + html;
				}
				html += "<tr><td colspan=\"2\"><div id=\"" + "chartdiv" + ptMangList.get(j).getSystem().toUpperCase() + "\" style=\"width: 98%; margin: 1em auto\"></div></td></tr>";
			}
			else{
				for(int i=0;i<ptMangList.size() - 1;i+=2){
					html += "<tr><td><div id=\"" + "chartdiv" + ptMangList.get(i).getSystem().toUpperCase() + "\" style=\"width: 98%; margin: 1em auto\"></div></td><td><div id=\"" + "chartdiv" + ptMangList.get(i+1).getSystem().toUpperCase() + "\" style=\"width: 98%; margin: 1em auto\"></div></td></tr>";
				}
			}
			model.addAttribute("html", html);
			
		}
		catch(Exception e){}
			
		if(autoRefresh == null){
			List<SYS_PARAMETER> autoRefreshForFinishRate = alDyRpFinishRateDAO.autoRefreshForFinishRate();
			if(autoRefreshForFinishRate.size() > 0){
				autoRefresh = autoRefreshForFinishRate.get(0).getValue();
			}
		}
		
		model.addAttribute("autoRefresh", autoRefresh);
		model.addAttribute("function", function);
		model.addAttribute("startMinutes", startMinutes);
		model.addAttribute("endMinutes", endMinutes);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("timeNow", timeNow);
		
		
		return "jspalarm/tyLeCBKetThucTrongNgay";
	}
}
