package controller.alarm;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import dao.AlHrNonFinishByAlClassDAO;
import dao.AlHrWarningByAlarmClassDAO;
import dao.AlHrWarningByAlarmNameDAO;
import dao.VAlHrFinishRateDAO;

import vo.AlDyRpFinishRate;
import vo.AlDyWarningByAlarmName;
import vo.AlHrNonFinishByAlClass;
import vo.AlHrWarningByAlarmClass;
import vo.alarm.utils.Chart;
import vo.alarm.utils.DateTools;
/**
 * Function        : Tong hop tat ca cac bieu do muc gio
 * Created By      : BUIQUANG
 * Create Date     : 28/11/2013
 * Modified By     : 
 * Modify Date     : 
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/alarm/slides-tong-hop-bao-cao/*")
public class SlidesTongHopBaoCaoController {

	
	@Autowired
	private AlHrNonFinishByAlClassDAO alHrNonFinishByAlClassDAO;
	@Autowired
	private VAlHrFinishRateDAO vAlHrFinishRateDAO;
	@Autowired
	private AlHrWarningByAlarmClassDAO alHrWarningByAlarmClassDAO;
	@Autowired
	private AlHrWarningByAlarmNameDAO alHrWarningByAlarmNameDAO;
	
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat hh = new SimpleDateFormat("HH");
	
	@RequestMapping(value = "{function}")
    public String list(@RequestParam(required = false) Integer count,
    				   @RequestParam(required = false) String startDate,
    				   @RequestParam(required = false) String hour,
    				   @PathVariable String function,
    				   ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(count == null){
			count = 0;	
		}
		
		Integer systemSize = 0;
		String startDateFull = "";
		// Ngay thang
		if (startDate == null || startDate.equals("")|| DateTools.isValid("dd/MM/yyyy", startDate)==false) {			

			startDate = df.format(new Date());
			hour = hh.format(new Date());
			startDateFull = df_full.format(new Date());
		}
		String html = "";
		String script = "<script type=\"text/javascript\" src=\"/mlmn/js/highchart2.3.3/highcharts.js\"></script>";
			   script += "<script type=\"text/javascript\" src=\"/mlmn/js/highchart2.3.3/modules/exporting.js\"></script>";
			   script += "<script type=\"text/javascript\" src=\"/mlmn/scripts/themes/grid.js\"></script>";
		try{
			if(function.equals("list")){
				
				List<AlHrNonFinishByAlClass> systemDistinctList = alHrNonFinishByAlClassDAO.getDistinctSystemHr(startDate);
				
				
				if(count < systemDistinctList.size()){
					if(count<4){
						html = "<tr><td class=\"wid50\"></td><td class=\"wid50\"></td></tr>";
						for(int i=0;i<4;i+=2){
							html += "<tr><td><div id=\"" + "chartdiv" + systemDistinctList.get(i).getSystem().toUpperCase() + "\" style=\"width: 98%; margin: 1em auto\"></div></td><td><div id=\"" + "chartdiv" + systemDistinctList.get(i+1).getSystem().toUpperCase() + "\" style=\"width: 98%; margin: 1em auto\"></div></td></tr>" + html;
						}
						
						for(int i=0;i<4;i++){
							script += bieuDoCanhBaoChuaKetThuc(script, model, startDate, hour, systemDistinctList.get(i).getSystem());
						}
						count = 3;
						model.addAttribute("titleAlarmType", "CẢNH BÁO CHƯA KẾT THÚC");
					}
					else{
						html = "<tr><td><div id=\"" + "chartdiv" + systemDistinctList.get(count).getSystem().toUpperCase() + "\" style=\"width: 98%;height:580px; margin: 1em auto\"></div></td></tr>";
						
						script = bieuDoCanhBaoChuaKetThuc(script, model, startDate, hour, systemDistinctList.get(count).getSystem());
						model.addAttribute("titleAlarmType", "CẢNH BÁO CHƯA KẾT THÚC " + systemDistinctList.get(count).getSystem().toUpperCase());
					}
					
					
				}
				systemSize = systemDistinctList.size();
				model.addAttribute("html", html);
				model.addAttribute("chartdivScript", script);
			}
			else if(function.equals("tl-cb-da-ket-thuc")){
				script += "<script type=\"text/javascript\" src=\"/mlmn/js/highchart2.3.3/highcharts.js\"></script>";
				script += "<script type=\"text/javascript\" src=\"/mlmn/js/highchart2.3.3/modules/exporting.js\"></script>";
				script += "<script type=\"text/javascript\" src=\"/mlmn/js/highchart2.3.3/themes/grid.js\"></script>";
				List<AlDyRpFinishRate> ptMangList = vAlHrFinishRateDAO.distinctSystemVHr(startDate, null, null);
				
				if(count < ptMangList.size()){
					if(count<4){
						html = "<tr><td class=\"wid50\"></td><td class=\"wid50\"></td></tr>";
						for(int i=0;i<4;i+=2){
							html += "<tr><td><div id=\"" + "chartdiv" + ptMangList.get(i).getSystem().toUpperCase() + "\" style=\"width: 98%; margin: 1em auto\"></div></td><td><div id=\"" + "chartdiv" + ptMangList.get(i+1).getSystem().toUpperCase() + "\" style=\"width: 98%; margin: 1em auto\"></div></td></tr>" + html;
						}
						
						for(int i=0;i<4;i++){
							script += tyLeCanhBaoDaKetThuc(script, model, startDate, ptMangList.get(i).getSystem());
						}
						count = 3;
						model.addAttribute("titleAlarmType", "TỶ LỆ CẢNH BÁO KẾT THÚC");
					}
					else{
						html = "<tr><td><div id=\"" + "chartdiv" + ptMangList.get(count).getSystem().toUpperCase() + "\" style=\"width: 98%;height:580px; margin: 1em auto\"></div></td></tr>";

						script = tyLeCanhBaoDaKetThuc(script, model, startDate, ptMangList.get(count).getSystem());
						model.addAttribute("titleAlarmType", "TỶ LỆ CẢNH BÁO KẾT THÚC " + ptMangList.get(count).getSystem().toUpperCase());
					}
				}
				systemSize = ptMangList.size();
				model.addAttribute("html", html);
				model.addAttribute("chartdivScript", script);
			}
			else if(function.equals("bd-sl-canh-bao")){
				
				List<AlHrWarningByAlarmClass> warningByAlarmClassList = alHrWarningByAlarmClassDAO.distinctSystemByDay(startDate);
				
				if(count < warningByAlarmClassList.size()){
					if(count<4){
						html = "<tr><td class=\"wid50\"></td><td class=\"wid50\"></td></tr>";
						for(int i=0;i<4;i+=2){
							html += "<tr><td><div id=\"" + "chartdiv" + warningByAlarmClassList.get(i).getSystem().toUpperCase() + "\" style=\"width: 98%; margin: 1em auto\"></div></td><td><div id=\"" + "chartdiv" + warningByAlarmClassList.get(i+1).getSystem().toUpperCase() + "\" style=\"width: 98%; margin: 1em auto\"></div></td></tr>" + html;
						}
						
						for(int i=0;i<4;i++){
							script += bieuDoSoLuongCanhBao(script, model, startDate, warningByAlarmClassList.get(i).getSystem());
						}
						count = 3;
						model.addAttribute("titleAlarmType", "SỐ LƯỢNG CẢNH BÁO");
					}
					else
					{
						html = "<tr><td><div id=\"" + "chartdiv" + warningByAlarmClassList.get(count).getSystem().toUpperCase() + "\" style=\"width: 98%;height:580px; margin: 1em auto\"></div></td></tr>";
						
						script = bieuDoSoLuongCanhBao(script, model, startDate, warningByAlarmClassList.get(count).getSystem());
						model.addAttribute("titleAlarmType", "SỐ LƯỢNG CẢNH BÁO " + warningByAlarmClassList.get(count).getSystem().toUpperCase());
					}
				}
				
				systemSize = warningByAlarmClassList.size();
				model.addAttribute("html", html);
				model.addAttribute("chartdivScript", script);
			}
			else if(function.equals("bd-sl-canh-bao-nhieu-nhat")){
				List<AlDyWarningByAlarmName> warningBySystemList = alHrWarningByAlarmNameDAO.distinctSystem(startDate, hour);
				
				if(count < warningBySystemList.size()){
					html = "<tr><td><div align=\"center\" id=\"" + "chartdiv" + warningBySystemList.get(count).getSystem().toUpperCase() + "\" style=\"width: 98%;height:580px; margin: 1em auto\"></div></td></tr>";
					
					model.addAttribute("titleAlarmType", "SỐ LƯỢNG CẢNH BÁO NHIỀU NHẤT " + warningBySystemList.get(count).getSystem().toUpperCase());
					script = bieuDoSoLuongCanhBaoNhieuNhat(script, model, startDate, hour, warningBySystemList.get(count).getSystem());
					
				}
				
				systemSize = warningBySystemList.size();
				model.addAttribute("html", html);
				model.addAttribute("chartdivScript", script);
			}
		}
		catch(Exception e){}
		// Redirect lan luot cac bieu do
		if(count == systemSize){
			if(function.equals("list"))
				response.sendRedirect("/mlmn/alarm/slides-tong-hop-bao-cao/tl-cb-da-ket-thuc.htm");
			else if(function.equals("tl-cb-da-ket-thuc"))
				response.sendRedirect("/mlmn/alarm/slides-tong-hop-bao-cao/bd-sl-canh-bao.htm");
			else if(function.equals("bd-sl-canh-bao"))
				response.sendRedirect("/mlmn/alarm/slides-tong-hop-bao-cao/bd-sl-canh-bao-nhieu-nhat.htm");
			else
				response.sendRedirect("/mlmn/alarm/slides-tong-hop-bao-cao/list.htm");
		}
		model.addAttribute("count", count);
		model.addAttribute("startDate", startDate);
		model.addAttribute("startDateFull", startDateFull);
		model.addAttribute("hour", hour);
		model.addAttribute("function", function);
			
		return "jspalarm/slidesTongHopBC";
	}
	
	private String bieuDoCanhBaoChuaKetThuc(String script, ModelMap model, String startDate, String hour, String system){
		
		try{	
			List<AlHrNonFinishByAlClass> alarmClassList = alHrNonFinishByAlClassDAO.getDistinctAlarmClassHr(startDate, system);
			
			Map<String, List<String>> series = new LinkedHashMap<String, List<String>>();
			List<String> categories = new ArrayList<String>();
			
			List<AlHrNonFinishByAlClass> minutesList = alHrNonFinishByAlClassDAO.getDistinctMinutes(startDate, hour, system);
			for(AlHrNonFinishByAlClass record : minutesList)
				categories.add(record.getHour());
			
			for(int j=0;j<alarmClassList.size();j++){
				List<AlHrNonFinishByAlClass> getList = alHrNonFinishByAlClassDAO.getBieuDo(startDate, hour, system, alarmClassList.get(j).getAlarmClass());
				List<String> seriesList = new ArrayList<String>();
				for(AlHrNonFinishByAlClass record : getList)
					seriesList.add(record.getQty().toString());
				
				series.put(alarmClassList.get(j).getAlarmClass(), seriesList);
			}
			script += Chart.StackedArea("chartdiv" + system.toUpperCase(), system.toUpperCase(), "Thời gian cảnh báo (phút)", "Số lượng cảnh báo", "Minutes", categories , series);
			
			
		}
		catch(Exception e){}
		return script;
	}
	
	private String tyLeCanhBaoDaKetThuc(String script, ModelMap model, String startDate, String system){
		try{
			List<AlDyRpFinishRate> alarmClass = vAlHrFinishRateDAO.distinctAlarmClassVHr(system);
			
			Map<String, List<String>> series = new LinkedHashMap<String, List<String>>();
			for(int j=0;j<alarmClass.size();j++){
				List<AlDyRpFinishRate> alarmClassList = vAlHrFinishRateDAO.tyLeTheoPTMangVHr(system, alarmClass.get(j).getAlarmClass(), startDate, null, null);
				
				List<String> seriesList = new ArrayList<String>();
				
				for(AlDyRpFinishRate record : alarmClassList){
					seriesList.add(record.getToDuration() + "-" + record.getRate().trim());
				}
				
				series.put(alarmClass.get(j).getAlarmClass(), seriesList);
			}
			script += Chart.logarithmicAxis("chartdiv" + system.toUpperCase(), system.toUpperCase(), "Tỷ lệ cảnh báo kết thúc (%)", "Minutes", "%", "Thời gian cảnh báo kết thúc", series);	
		}
		catch(Exception e){}
		return script;
	}
	
	private String bieuDoSoLuongCanhBao(String script, ModelMap model, String startDate, String system){
		
		try{
			List<AlHrWarningByAlarmClass> alarmClass = alHrWarningByAlarmClassDAO.distinctAlarmClass(system);
			Map<String, List<String>> series = new LinkedHashMap<String, List<String>>();
			
			List<String> categories = new ArrayList<String>();
			List<AlHrWarningByAlarmClass> hourList = alHrWarningByAlarmClassDAO.distinctHourList(system, startDate);
			for(AlHrWarningByAlarmClass recordHour : hourList){
				categories.add(recordHour.getHour().toString());
			}
			
			for(int j=0;j<alarmClass.size();j++){
				List<AlHrWarningByAlarmClass> alarmClassList = alHrWarningByAlarmClassDAO.soLuongTheoPTMang(system, alarmClass.get(j).getAlarmClass(), startDate);
				
				List<String> seriesList = new ArrayList<String>();
				for(AlHrWarningByAlarmClass record : alarmClassList){
						seriesList.add(record.getQty().toString());
				}
				
				series.put(alarmClass.get(j).getAlarmClass(), seriesList);
			}
			script += Chart.StackedArea("chartdiv" + system.toUpperCase(), system.toUpperCase(), "Thời gian cảnh báo", "Số lượng cảnh báo", "Hour", categories, series);
		}
		catch(Exception e){}
		return script;
	}
	
	private String bieuDoSoLuongCanhBaoNhieuNhat(String script, ModelMap model, String startDate, String hour, String system){
		try{
			List<AlDyWarningByAlarmName> mostList = alHrWarningByAlarmNameDAO.soLuongCBNhieuNhat(system, startDate, hour);
			
			List<String> seriesList = new ArrayList<String>();
			for(int j=0;j<mostList.size();j++){
				seriesList.add(mostList.get(j).getAlarmName() + "-" + mostList.get(j).getQty() + "-" + mostList.get(j).getRate());
			}
			
			Map<String, List<String>> cssrSeries = new LinkedHashMap<String, List<String>>();
			cssrSeries.put("cssrSeries", seriesList);
			
			script +=  Chart.pieLegend("chartdiv" + system.toUpperCase(), system.toUpperCase(), cssrSeries);
		}
		catch(Exception e){}
		
		return script;
	}
	
}
