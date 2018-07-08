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

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import vo.AlDyWarningByAlarmClass;
import vo.AlHrWarningByAlarmClass;
import vo.SYS_PARAMETER;
import vo.alarm.utils.Chart;
import vo.alarm.utils.DateTools;

import dao.AlDyRpFinishRateDAO;
import dao.AlDyWarningByAlarmClassDAO;
import dao.AlHrWarningByAlarmClassDAO;
import dao.SYS_PARAMETERDAO;
/**
 * Function        : Bieu do canh bao theo gio, theo ngay
 * Created By      : BUIQUANG
 * Create Date     : 28/11/2013
 * Modified By     : 
 * Modify Date     : 
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/alarm/bieu-do-canh-bao/*")
public class AlHrWarningByAlarmClassController {

	@Autowired
 	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
	private AlHrWarningByAlarmClassDAO alHrWarningByAlarmClassDAO;
	@Autowired
	private AlDyWarningByAlarmClassDAO alDyWarningByAlarmClassDAO;
	@Autowired
	private AlDyRpFinishRateDAO alDyRpFinishRateDAO;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping(value = "{function}")
    public String list(@RequestParam(required = false) String startDate,
    				   @RequestParam(required = false) String endDate,
    				   @RequestParam(required = false) String autoRefresh,
    				   @PathVariable String function, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		try{
			
			// Lay ten file export
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
			String dateNow = formatter.format(currentDate.getTime());
			String exportFileName = "SoLuongCB_" + dateNow;
			model.addAttribute("exportFileName", exportFileName);
		
			int order = 1;
			String column = "SYSTEM";
			try {
				order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
				column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
			} catch (NumberFormatException e) {
			}
 		
 			List<SYS_PARAMETER> titleSystem = alHrWarningByAlarmClassDAO.titleFormHrAlarmClass();
 			model.addAttribute("titleSystem", titleSystem.get(0).getValue());
 			
 			List<AlHrWarningByAlarmClass> warningByAlarmClassList = null;
 			if(function.equals("theo-gio")){
 				if (startDate == null || startDate.equals("") || DateTools.isValid("dd/MM/yyyy", startDate)==false)
 				{
 					startDate = df.format(new Date());
 				}
 				
 				warningByAlarmClassList = alHrWarningByAlarmClassDAO.distinctSystemByDay(startDate);
 				
 				List<AlHrWarningByAlarmClass> soLuongCBList = alHrWarningByAlarmClassDAO.getHrWarningByAcList(startDate, column, order == 1 ? "ASC" : "DESC");
 				model.addAttribute("soLuongCBList", soLuongCBList);
 				
 			}
			else if(function.equals("theo-ngay")){
				// Ngay thang
				if (startDate == null || startDate.equals("")||DateTools.isValid("dd/MM/yyyy", startDate)==false) {
					
					startDate = df.format(DateTools.startMonth(new Date()));
				
				}
				if (endDate == null || endDate.equals("")||DateTools.isValid("dd/MM/yyyy", endDate)==false)
				{
					Calendar cal = Calendar.getInstance();	
					cal.setTime(new Date());
					endDate = df.format(cal.getTime());
				}
				
				warningByAlarmClassList = alDyWarningByAlarmClassDAO.distinctSystem(startDate, endDate);
				
				List<AlDyWarningByAlarmClass> soLuongCBList = alDyWarningByAlarmClassDAO.getDyWarningByAcList(startDate, endDate, column, order == 1 ? "ASC" : "DESC");
				model.addAttribute("soLuongCBList", soLuongCBList);
				
			}
 			
	 		
			String html = "";
			if((warningByAlarmClassList.size()%2) == 1){
				int j = warningByAlarmClassList.size() - 1;
				for(int i=j-1;i>0;i-=2){
					html = "<tr><td><div id=\"" + "chartdiv" + warningByAlarmClassList.get(i-1).getSystem().toUpperCase() + "\" style=\"width: 98%; margin: 1em auto\"></div></td><td><div id=\"" + "chartdiv" + warningByAlarmClassList.get(i).getSystem().toUpperCase() + "\" style=\"width: 98%; margin: 1em auto\"></div></td></tr>" + html;
				}
				html += "<tr><td colspan=\"2\"><div id=\"" + "chartdiv" + warningByAlarmClassList.get(j).getSystem().toUpperCase() + "\" style=\"width: 98%; margin: 1em auto\"></div></td></tr>";
			}
			else{
				for(int i=0;i<warningByAlarmClassList.size() - 1;i+=2){
					html += "<tr><td><div id=\"" + "chartdiv" + warningByAlarmClassList.get(i).getSystem().toUpperCase() + "\" style=\"width: 98%; margin: 1em auto\"></div></td><td><div id=\"" + "chartdiv" + warningByAlarmClassList.get(i+1).getSystem().toUpperCase() + "\" style=\"width: 98%; margin: 1em auto\"></div></td></tr>";
				}
			}
			model.addAttribute("html", html);
 		
		
			String script = "";
			if(function.equals("theo-gio")){
				for(int i=0;i<warningByAlarmClassList.size();i++){

					List<AlHrWarningByAlarmClass> alarmClass = alHrWarningByAlarmClassDAO.distinctAlarmClass(warningByAlarmClassList.get(i).getSystem());
					Map<String, List<String>> series = new LinkedHashMap<String, List<String>>();
					
					List<String> categories = new ArrayList<String>();
					List<AlHrWarningByAlarmClass> hourList = alHrWarningByAlarmClassDAO.distinctHourList(warningByAlarmClassList.get(i).getSystem(), startDate);
					for(AlHrWarningByAlarmClass recordHour : hourList){
						categories.add(recordHour.getHour().toString());
					}
					
					for(int j=0;j<alarmClass.size();j++){
						List<AlHrWarningByAlarmClass> alarmClassList = alHrWarningByAlarmClassDAO.soLuongTheoPTMang(warningByAlarmClassList.get(i).getSystem(), alarmClass.get(j).getAlarmClass(), startDate);
						
						List<String> seriesList = new ArrayList<String>();
						for(AlHrWarningByAlarmClass record : alarmClassList){
								seriesList.add(record.getQty().toString());
						}
						
						series.put(alarmClass.get(j).getAlarmClass(), seriesList);
					}
					script += Chart.StackedArea("chartdiv" + warningByAlarmClassList.get(i).getSystem().toUpperCase(), warningByAlarmClassList.get(i).getSystem().toUpperCase(), "Thời gian cảnh báo", "Số lượng cảnh báo", "Hour", categories, series);
				}
			}
			else if(function.equals("theo-ngay")){
				for(int i=0;i<warningByAlarmClassList.size();i++){
					List<AlDyWarningByAlarmClass> alarmClass = alDyWarningByAlarmClassDAO.distinctAlarmClass(warningByAlarmClassList.get(i).getSystem(), startDate, endDate);
					
					List<AlDyWarningByAlarmClass> dayList = alDyWarningByAlarmClassDAO.distinctDayBySystem(warningByAlarmClassList.get(i).getSystem(), startDate, endDate);
					
					Map<String, List<String>> series = new LinkedHashMap<String, List<String>>();
					List<String> categories = new ArrayList<String>();
					for(AlDyWarningByAlarmClass record : dayList){
						categories.add(record.getDayStr());
					}
					
					for(int j=0;j<alarmClass.size();j++){
						List<AlDyWarningByAlarmClass> alarmClassList = alDyWarningByAlarmClassDAO.listByBieuDo(warningByAlarmClassList.get(i).getSystem(), alarmClass.get(j).getAlarmClass(), startDate, endDate);
						
						List<String> seriesList = new ArrayList<String>();
						
						for(AlDyWarningByAlarmClass record : alarmClassList){
							seriesList.add(record.getQty().toString());
						}
						
						series.put(alarmClass.get(j).getAlarmClass(), seriesList);
					}
					script += Chart.StackedArea("chartdiv" + warningByAlarmClassList.get(i).getSystem().toUpperCase(), warningByAlarmClassList.get(i).getSystem().toUpperCase(), "", "Số lượng cảnh báo", "", categories , series);
				}
			}
			
			model.addAttribute("chartdivScript", script);
 		}
		catch(Exception e)
		{}
 		
		if(autoRefresh == null)
		{
			List<SYS_PARAMETER> autoRefreshForFinishRate = alDyRpFinishRateDAO.autoRefreshForFinishRate();
			if(autoRefreshForFinishRate.size() > 0){
				autoRefresh = autoRefreshForFinishRate.get(0).getValue();
			}
			
		}
		
		model.addAttribute("autoRefresh", autoRefresh);

		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("function", function);
		
		return "jspalarm/bieuDoCanhBao";
	}
}
