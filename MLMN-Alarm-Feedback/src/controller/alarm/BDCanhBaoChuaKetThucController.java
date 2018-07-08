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

import dao.AlDyNonFinishByAlClassDAO;
import dao.AlDyRpFinishRateDAO;
import dao.AlHrNonFinishByAlClassDAO;
import dao.SYS_PARAMETERDAO;

import vo.AlDyNonFinishByAlClass;
import vo.AlHrNonFinishByAlClass;
import vo.SYS_PARAMETER;
import vo.alarm.utils.Chart;
import vo.alarm.utils.DateTools;
/**
 * Function        : Bieu do canh bao chua ket thuc
 * Created By      : BUIQUANG
 * Create Date     : 28/11/2013
 * Modified By     : 
 * Modify Date     : 
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/alarm/bieu-do-canh-bao-chua-ket-thuc/*")
public class BDCanhBaoChuaKetThucController {

	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
	private AlHrNonFinishByAlClassDAO alHrNonFinishByAlClassDAO;
	@Autowired
	private AlDyNonFinishByAlClassDAO alDyNonFinishByAlClassDAO;
	@Autowired
	private AlDyRpFinishRateDAO alDyRpFinishRateDAO;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat df_hh = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private DateFormat hh = new SimpleDateFormat("HH");
	
	@RequestMapping(value = "{function}")
    public String list(@RequestParam(required = false) String startDate,
					   @RequestParam(required = false) String endDate,
					   @RequestParam(required = false) String hour,
					   @RequestParam(required = false) String autoRefresh,
					   @PathVariable String function, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		String timeNow = df_hh.format(new Date());
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "SoLuongCB_ChuaKetThuc_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		int order = 1;
		String column = "SYSTEM";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		try{
			
			List<SYS_PARAMETER> titleSystem = alHrNonFinishByAlClassDAO.titleFormHR();
			model.addAttribute("titleSystem", titleSystem.get(0).getValue());
			
			List<AlHrNonFinishByAlClass> systemDistinctList = null;
			if(function.equals("hien-tai")){
				
				startDate = df.format(new Date());
				systemDistinctList = alHrNonFinishByAlClassDAO.getDistinctSystemHr(startDate);
				
				hour = hh.format(new Date());
				
			}
			else if(function.equals("theo-gio")){
				
				if (startDate == null || startDate.equals("") || DateTools.isValid("dd/MM/yyyy", startDate)==false)
				{
					startDate = df.format(new Date());
				}
				
				List<SYS_PARAMETER> hourList = sysParameterDao.getListHourOnDay();
				model.addAttribute("hourList", hourList);
				
				systemDistinctList = alHrNonFinishByAlClassDAO.getDistinctSystemHr(startDate);
				
				List<AlHrNonFinishByAlClass> soLuongCBList = alHrNonFinishByAlClassDAO.getListCb(startDate, hour, column, order== 1 ? "ASC" : "DESC");
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
				
				systemDistinctList = alDyNonFinishByAlClassDAO.getDistinctSystemDy(startDate, endDate);
				
				List<AlDyNonFinishByAlClass> soLuongCBList = alDyNonFinishByAlClassDAO.getListCB(startDate, endDate, column, order==1 ? "ASC" : "DESC");
				model.addAttribute("soLuongCBList", soLuongCBList);
				
			}
			
			String html = "";
			if((systemDistinctList.size()%2) == 1){
				int j = systemDistinctList.size() - 1;
				for(int i=j-1;i>0;i-=2){
					html = "<tr><td><div id=\"" + "chartdiv" + systemDistinctList.get(i-1).getSystem().toUpperCase() + "\" style=\"width: 98%; margin: 1em auto\"></div></td><td><div id=\"" + "chartdiv" + systemDistinctList.get(i).getSystem().toUpperCase() + "\" style=\"width: 98%; margin: 1em auto\"></div></td></tr>" + html;
				}
				html += "<tr><td colspan=\"2\"><div id=\"" + "chartdiv" + systemDistinctList.get(j).getSystem().toUpperCase() + "\" style=\"width: 98%; margin: 1em auto\"></div></td></tr>";
			}
			else{
				for(int i=0;i<systemDistinctList.size() - 1;i+=2){
					html += "<tr><td><div id=\"" + "chartdiv" + systemDistinctList.get(i).getSystem().toUpperCase() + "\" style=\"width: 98%; margin: 1em auto\"></div></td><td><div id=\"" + "chartdiv" + systemDistinctList.get(i+1).getSystem().toUpperCase() + "\" style=\"width: 98%; margin: 1em auto\"></div></td></tr>";
				}
			}
			model.addAttribute("html", html);
			
			String script = "";
			
				
				if(function.equals("hien-tai")){
					for(int i=0;i<systemDistinctList.size();i++){
						
						List<AlHrNonFinishByAlClass> alarmClassList = alHrNonFinishByAlClassDAO.getDistinctAlarmClassHr(startDate, systemDistinctList.get(i).getSystem());
						
						Map<String, List<String>> series = new LinkedHashMap<String, List<String>>();
						List<String> categories = new ArrayList<String>();
						
						List<AlHrNonFinishByAlClass> minutesList = alHrNonFinishByAlClassDAO.getDistinctMinutes(startDate, hour, systemDistinctList.get(i).getSystem());
						for(AlHrNonFinishByAlClass record : minutesList)
							categories.add(record.getHour());
						
						for(int j=0;j<alarmClassList.size();j++){
							List<AlHrNonFinishByAlClass> getList = alHrNonFinishByAlClassDAO.getBieuDo(startDate, hour, systemDistinctList.get(i).getSystem(), alarmClassList.get(j).getAlarmClass());
							List<String> seriesList = new ArrayList<String>();
							for(AlHrNonFinishByAlClass record : getList)
								seriesList.add(record.getQty().toString());
							
							series.put(alarmClassList.get(j).getAlarmClass(), seriesList);
						}
						script += Chart.StackedArea("chartdiv" + systemDistinctList.get(i).getSystem().toUpperCase(), systemDistinctList.get(i).getSystem().toUpperCase(), "Thời gian cảnh báo (phút)", "Số lượng cảnh báo", "Minutes", categories , series);
					}
				}
				else if(function.equals("theo-gio")){
					for(int i=0;i<systemDistinctList.size();i++){
						
						List<AlHrNonFinishByAlClass> alarmClassList = alHrNonFinishByAlClassDAO.getDistinctAlarmClassHr(startDate, systemDistinctList.get(i).getSystem());
						
						Map<String, List<String>> series = new LinkedHashMap<String, List<String>>();
						List<String> categories = new ArrayList<String>();
						
						
						if(hour != null && hour != ""){
							List<AlHrNonFinishByAlClass> minutesList = alHrNonFinishByAlClassDAO.getDistinctMinutes(startDate, hour, systemDistinctList.get(i).getSystem());
							for(AlHrNonFinishByAlClass record : minutesList)
								categories.add(record.getHour());
						}
						else{
							List<AlHrNonFinishByAlClass> hourList = alHrNonFinishByAlClassDAO.getDistinctHour(startDate, systemDistinctList.get(i).getSystem());
							for(AlHrNonFinishByAlClass record : hourList)
								categories.add(record.getHour());
						}
						
						
						for(int j=0;j<alarmClassList.size();j++){
							List<AlHrNonFinishByAlClass> getList = alHrNonFinishByAlClassDAO.getBieuDo(startDate, hour, systemDistinctList.get(i).getSystem(), alarmClassList.get(j).getAlarmClass());
							List<String> seriesList = new ArrayList<String>();
							for(AlHrNonFinishByAlClass record : getList)
								seriesList.add(record.getQty().toString());
							
							series.put(alarmClassList.get(j).getAlarmClass(), seriesList);
						}
						
						if(hour != null && hour != ""){
							script += Chart.StackedArea("chartdiv" + systemDistinctList.get(i).getSystem().toUpperCase(), systemDistinctList.get(i).getSystem().toUpperCase(), "Thời gian cảnh báo (phút)", "Số lượng cảnh báo", "Minutes", categories , series);
						}
						else{
							script += Chart.StackedArea("chartdiv" + systemDistinctList.get(i).getSystem().toUpperCase(), systemDistinctList.get(i).getSystem().toUpperCase(), "Thời gian cảnh báo (giờ)", "Số lượng cảnh báo", "Hour", categories , series);
						}
					}
					
				}
				else if(function.equals("theo-ngay")){
					for(int i=0;i<systemDistinctList.size();i++){
						List<AlDyNonFinishByAlClass> alarmClassList = alDyNonFinishByAlClassDAO.getDistinctAlarmClassDy(startDate, endDate, systemDistinctList.get(i).getSystem());
						
						Map<String, List<String>> series = new LinkedHashMap<String, List<String>>();
						
						List<String> categories = new ArrayList<String>();
						List<AlDyNonFinishByAlClass> dayList = alDyNonFinishByAlClassDAO.getDayListDy(startDate, endDate, systemDistinctList.get(i).getSystem());
						for(AlDyNonFinishByAlClass record : dayList)
							categories.add(record.getDayStr());
						
						for(int j=0;j<alarmClassList.size();j++){
							List<AlDyNonFinishByAlClass> getList = alDyNonFinishByAlClassDAO.getBieuDo(startDate, endDate, systemDistinctList.get(i).getSystem(), alarmClassList.get(j).getAlarmClass());
							
							List<String> seriesList = new ArrayList<String>();
							for(AlDyNonFinishByAlClass record : getList)
								seriesList.add(record.getQty().toString());
							
							series.put(alarmClassList.get(j).getAlarmClass(), seriesList);
						}
						
						script += Chart.StackedArea("chartdiv" + systemDistinctList.get(i).getSystem().toUpperCase(), systemDistinctList.get(i).getSystem().toUpperCase(), "", "Số lượng cảnh báo", "", categories , series);
					}
				}
				
				model.addAttribute("chartdivScript", script);
		}
		catch(Exception e){
			
		}
		
		if(autoRefresh == null){
			List<SYS_PARAMETER> autoRefreshForFinishRate = alDyRpFinishRateDAO.autoRefreshForFinishRate();
			if(autoRefreshForFinishRate.size() > 0){
				autoRefresh = autoRefreshForFinishRate.get(0).getValue();
			}
		}
		
		model.addAttribute("autoRefresh", autoRefresh);
		
		model.addAttribute("function", function);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("hour", hour);
		model.addAttribute("timeNow", timeNow);
		
		return "jspalarm/bDCanhBaoChuaKetThuc";
	}
}
