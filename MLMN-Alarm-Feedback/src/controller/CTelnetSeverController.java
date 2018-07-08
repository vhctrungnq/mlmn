package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vo.CHighlightConfigs;
import vo.CTableConfig;
import vo.CTelnetServers;
import vo.SYS_PARAMETER;
import vo.SmsContents;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.HelpTableConfigs;

import com.google.gson.Gson;

import dao.AlAlarmTypesDAO;
import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.CTelnetServersDAO;
import dao.SYS_PARAMETERDAO;
/*
 * Created by: AnhCTV
 * Created date: 13/11/2013
*/
@Controller
@RequestMapping("/telnet/*")
public class CTelnetSeverController extends BaseController {
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired
	private CTelnetServersDAO cTelnetServersDAO;
	
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	
	@Autowired
	private AlAlarmTypesDAO alAlarmTypesDAO;
	
	@Autowired
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	/*List giam sat he thong
	 * @param day: ngay
	 * @param edate: Thoi gian ket thuc
	 * function : detail/total
	 */
	@RequestMapping(value = "{function}")
	public ModelAndView list(
			@RequestParam(required = false) String day,
			@RequestParam(required = false) String hourFrom,
			@RequestParam(required = false) String hourTo,
			@RequestParam(required = false) String reload,
			@RequestParam(required = false) String reloadStr,
			Model model, HttpServletRequest request) {
		
		List<SYS_PARAMETER> sysParemeterTitle = cTelnetServersDAO.titleForm(null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		Calendar cal = Calendar.getInstance();	
		cal.setTime(new Date());
		int hour=1;
		if (day == null || day.equals("")
				||(day!=null && !day.equals("") && DateTools.isValid("dd/MM/yyyy", day)==false))
		{
			day = df.format(cal.getTime());
			hour = cal.get(Calendar.HOUR_OF_DAY);
		}
		System.out.println(" Hour: "+hour);
		model.addAttribute("day", day);			
		int hourF=hour;
		if (hourFrom!=null&& !hourFrom.equals(""))
		{
			try
			{
				hourF = Integer.parseInt(hourFrom);
			}
			catch(Exception exp)
			{
				hourF=hour;
			}
		}
		int hourT=hour;
		if (hourTo!=null&& !hourTo.equals(""))
		{
			try
			{
				hourT = Integer.parseInt(hourTo);
			}
			catch(Exception exp)
			{
				hourT=hour;
			}
		}
		
		//reload
		if (reloadStr==null)
		{
			reload="Y";
			reloadStr="Y";
		}
			
		//grid DETAIL
		List<CTableConfig> configListdetail = cTableConfigDAO.getTableConfigsForGrid("C_TELNET_SERVER");
		//Lay du lieu cac cot an hien cua grid 
		List<CTableConfig> listSourcedetail = cTableConfigDAO.getColumnList("C_TELNET_SERVER");
		//url
		String urldetail = "data.htm?day="+day+"&hourF="+hourF+"&hourT="+hourT+"&type=detail";
		
		List<CHighlightConfigs> highlightConfigListCol = cHighlightConfigsDAO.getByKeyAndKPI("C_TELNET_SERVER","moniterStatus");
		String highlight =  HelpTableConfigs.highLightCol(highlightConfigListCol,"griddetail");	
		
		String griddetail = HelpTableConfigs.getGridPagingUrl(configListdetail, "griddetail", urldetail, "jqxlistboxdetail", listSourcedetail, "menudetail", highlight, null,null);
		model.addAttribute("griddetail", griddetail);	
		
		//grid Alarm
		List<CTableConfig> configListReportAlarm = cTableConfigDAO.getTableConfigsForGrid("C_TELNET_SERVER_REPORT_ALARM");
		//Lay du lieu cac cot an hien cua grid 
	//	List<CTableConfig> listSourceReport = cTableConfigDAO.getColumnList("C_TELNET_SERVER_REPORT");
		//url
		String urlAlarm = "data.htm?day="+day+"&hourF="+hourF+"&hourT="+hourT+"&type=alarm";
		//Grid
		
		String highlighAlarm="  var cellclass = function (row, columnfield, value) {";
		highlighAlarm += " 		var duaration = $('#gridAlarm').jqxGrid('getrowdata', row).duaration;";
		highlighAlarm += " 		if (duaration >= 10){";
		highlighAlarm += "         return 'red';";
		highlighAlarm += "     		}";
		highlighAlarm += "  	};";
			
		String gridAlarm = HelpTableConfigs.getGridPagingReportUrl(configListReportAlarm, "gridAlarm", urlAlarm ,null, null, "menuAlarm", highlighAlarm, null,null,null);
		model.addAttribute("gridAlarm", gridAlarm);	
		
		//grid kpi
		//url
		List<CTableConfig> configListReport = cTableConfigDAO.getTableConfigsForGrid("C_TELNET_SERVER_REPORT");
		String urlKPI = "data.htm?day="+day+"&hourF="+hourF+"&hourT="+hourT+"&type=KPI";
		//Grid
		
		List<CHighlightConfigs> highlightConfigListKPI = cHighlightConfigsDAO.getByKeyAndKPI("C_TELNET_SERVER","different");
		String highlighKPI =  HelpTableConfigs.highLightCol(highlightConfigListKPI,"gridKPI");	
		String gridKPI = HelpTableConfigs.getGridPagingReportUrl(configListReport, "gridKPI", urlKPI ,null, null, "menuKPI", highlighKPI, null,null,null);
		model.addAttribute("gridKPI", gridKPI);	

		//grid Inventory
		String urlInvent = "data.htm?day="+day+"&hourF="+hourF+"&hourT="+hourT+"&type=Invent";
		String highlighInvent=HelpTableConfigs.highLightCol(highlightConfigListKPI,"gridInvent")	;
		String gridInvent = HelpTableConfigs.getGridPagingReportUrl(configListReport, "gridInvent", urlInvent ,null, null, "menuInvent", highlighInvent, null,null,null);
		model.addAttribute("gridInvent", gridInvent);	
				
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		
		String exportFileNamedetail = "TELNET_SERVER_"+ dateNow;
		model.addAttribute("exportFileNamedetail", exportFileNamedetail);
		
		String exportFileNameAlarm = "TELNET_SERVER_ALARM_"+ dateNow;
		model.addAttribute("exportFileNameAlarm", exportFileNameAlarm);
		
		String exportFileNameKPI = "TELNET_SERVER_KPI_"+ dateNow;
		model.addAttribute("exportFileNameKPI", exportFileNameKPI);
		
		String exportFileNameInvent = "TELNET_SERVER_INVENTORY_"+ dateNow;
		model.addAttribute("exportFileNameInvent", exportFileNameInvent);
	
		model.addAttribute("hourTo", hourT);
		model.addAttribute("hourFrom", hourF);
		model.addAttribute("day", day);
		model.addAttribute("reload", reload);
		model.addAttribute("reloadStr", reloadStr);

		
		return new ModelAndView("jspalarm/GSHeThong/telnetServer");
	}
	
	@RequestMapping("data")
	public @ResponseBody 
	void dataSuccessList(@RequestParam(required = false) String day,
			@RequestParam(required = false) String hourF,
			@RequestParam(required = false) String hourT,
			@RequestParam(required = false) String type,
			HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
		
		List<CTelnetServers> contentList = new ArrayList<CTelnetServers>();
		if (type.equals("detail"))
		{
			contentList = cTelnetServersDAO.getTelnetServerDetail();
		}
		else
		{
			contentList = cTelnetServersDAO.getTelnetServerType(day, hourF,hourT,type);
		}
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(contentList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
	 }
	
	
	
	

	
}
