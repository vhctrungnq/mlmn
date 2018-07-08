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

import vo.AlDyWarningByAlarmName;
import vo.AlHrWarningByAlarmName;
import vo.SYS_PARAMETER;
import vo.alarm.utils.Chart;
import vo.alarm.utils.DateTools;

import dao.AlDyRpFinishRateDAO;
import dao.AlDyWarningByAlarmNameDAO;
import dao.AlHrWarningByAlarmNameDAO;
import dao.SYS_PARAMETERDAO;
/**
 * Function        : Bieu do so luong canh bao nhieu nhat theo gio, theo ngay, theo thang
 * Created By      : BUIQUANG
 * Create Date     : 28/11/2013
 * Modified By     : 
 * Modify Date     : 
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/alarm/bieu-do-cb-nhieu-nhat/*")
public class AlDyWarningByAlarmNameController {

	@Autowired
	private AlDyWarningByAlarmNameDAO alDyWarningByAlarmNameDAO;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
	private AlHrWarningByAlarmNameDAO alHrWarningByAlarmNameDAO;
	@Autowired
	private AlDyRpFinishRateDAO alDyRpFinishRateDAO;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping(value = "{function}")
    public String list(@RequestParam(required = false) String startDate,
    				   @RequestParam(required = false) String endDate,
    				   @RequestParam(required = false) String hour,
    				   @RequestParam(required = false) String autoRefresh,
    				   @PathVariable String function, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		List<AlDyWarningByAlarmName> warningBySystemList = null;
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
			
			List<SYS_PARAMETER> titleSystem = alHrWarningByAlarmNameDAO.titleFormHrAlamName();
			model.addAttribute("titleSystem", titleSystem.get(0).getValue());
			
			if(function.equals("theo-gio")){
				
				if (startDate == null || startDate.equals("") || DateTools.isValid("dd/MM/yyyy", startDate)==false)
				{
					startDate = df.format(new Date());
				}
				
				List<SYS_PARAMETER> hourList = sysParameterDao.getListHourOnDay();
				model.addAttribute("hourList", hourList);
				
				warningBySystemList = alHrWarningByAlarmNameDAO.distinctSystem(startDate, hour);
				
				List<AlHrWarningByAlarmName> soLuongCBList = alHrWarningByAlarmNameDAO.getHrWarningAnList(startDate, hour, column, order == 1 ? "ASC" : "DESC");
				model.addAttribute("soLuongCBList", soLuongCBList);
			}
			else if(function.equals("theo-ngay")){
				
				if (startDate == null || startDate.equals("") || DateTools.isValid("dd/MM/yyyy", startDate)==false)
				{
					startDate = df.format(new Date());
				}
				
				warningBySystemList = alDyWarningByAlarmNameDAO.distinctSystem(startDate, null, null);
				
				List<AlDyWarningByAlarmName> soLuongCBList = alDyWarningByAlarmNameDAO.getDyWarningAnList(startDate, null, null, column, order == 1 ? "ASC" : "DESC");
				model.addAttribute("soLuongCBList", soLuongCBList);
			}
			else if(function.equals("theo-thang")){
				
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
				
				warningBySystemList = alDyWarningByAlarmNameDAO.distinctSystem(null, startDate, endDate);
				
				List<AlDyWarningByAlarmName> soLuongCBList = alDyWarningByAlarmNameDAO.getDyWarningAnList(null, startDate, endDate, column, order == 1 ? "ASC" : "DESC");
				model.addAttribute("soLuongCBList", soLuongCBList);
			}
		
		
			String html = "";
			for(int i=0;i<warningBySystemList.size();i++){
				html += "<tr><td colspan=\"2\"><div align=\"center\" id=\"" + "chartdiv" + warningBySystemList.get(i).getSystem().toUpperCase() + "\" style=\"width: 98%; margin: 1em auto\"></div></td></tr>";
			}
			
			model.addAttribute("html", html);
			
			String script = "";
			for(int i=0;i<warningBySystemList.size();i++){
				List<AlDyWarningByAlarmName> mostList = null;
				
				if(function.equals("theo-gio")){
					mostList = alHrWarningByAlarmNameDAO.soLuongCBNhieuNhat(warningBySystemList.get(i).getSystem(), startDate, hour);
				}
				else if(function.equals("theo-ngay")){
					mostList = alDyWarningByAlarmNameDAO.soLuongCBNhieuNhat(warningBySystemList.get(i).getSystem(), startDate, null, null);
				}
				else if(function.equals("theo-thang")){
					mostList = alDyWarningByAlarmNameDAO.soLuongCBNhieuNhat(warningBySystemList.get(i).getSystem(), null, startDate, endDate);
				}
				
				List<String> seriesList = new ArrayList<String>();
				for(int j=0;j<mostList.size();j++){
					seriesList.add(mostList.get(j).getAlarmName() + "-" + mostList.get(j).getQty() + "-" + mostList.get(j).getRate());
				}
				
				Map<String, List<String>> cssrSeries = new LinkedHashMap<String, List<String>>();
				cssrSeries.put("cssrSeries", seriesList);
				
				script +=  Chart.pieLegend("chartdiv" + warningBySystemList.get(i).getSystem().toUpperCase(), warningBySystemList.get(i).getSystem().toUpperCase(), cssrSeries);
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
		model.addAttribute("function", function);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("hour", hour);
		
		return "jspalarm/cbNhieuNhat";
	}
}
