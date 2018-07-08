package controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vo.HRouter;
import vo.RAlarmRouterLog;
import vo.alarm.utils.DateTimeUtils;
import vo.alarm.utils.RAlarmRouterLogFilter;
import dao.HRouterDAO;
import dao.RAlarmRouterLogDAO;



@Controller
public class AlRouterLogController extends BaseController {
	
	@Autowired
	private RAlarmRouterLogDAO alarmRouterLogDao;
	@Autowired
	private HRouterDAO hRouterDao;
	
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy");

	@Autowired
	public void setAlarmRouterLogDAO(RAlarmRouterLogDAO alarmRouterLogDao) {
		this.alarmRouterLogDao = alarmRouterLogDao;
	}

	@RequestMapping("/log/router")
	public String filter(@ModelAttribute("filter") RAlarmRouterLogFilter filter,
			@RequestParam(required = false) String sDate,
			@RequestParam(required = false) String eDate,
			@RequestParam(required = false) String ip,
			@RequestParam(required = false) String routerName,
			@RequestParam(required = false) String alarmInfo,
			@RequestParam(required = false) String reload,
			@RequestParam(required = false) String reloadStr,
			@RequestParam(required = false) String timeLoad,
			Model model, HttpServletRequest request) {		
		
		List<RAlarmRouterLog> alarmRouterLog=null;
		
		if (reloadStr==null)
		{
			reload="Y";
			reloadStr="Y";
		}
		if (timeLoad == null) {
			timeLoad = "180";
		}
		else
		{
			try
			{
				int timeLoadInt = Integer.parseInt(timeLoad);
			}
			catch(Exception exp)
			{
				timeLoad = "180";
			}
		}
		
		if(ip == null){
			ip = "";
		}
		if(routerName == null){
			routerName = "";
		}
		
		if(alarmInfo == null)
			alarmInfo = "";
		int order = 2;
		String column = "SDATE";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder(
					"item")
					.encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item")
					.encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
/*		
		if (ip != null && !ip.equals(""))
			ip = ip.replace(" ", ",");
		
		if (routerName != null && !routerName.equals(""))
			routerName = routerName.replace(" ", ",");*/
		List<HRouter> routerNameList = hRouterDao.getRouterName(routerName);
		model.addAttribute("routerNameList",routerNameList);
		
		List<HRouter> routerIdList = hRouterDao.getRouterId();
		model.addAttribute("routerIdList", routerIdList);
		
		if ((eDate!=null && !eDate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy HH:mm", eDate)==false)||(sDate!=null && !sDate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy HH:mm", sDate)==false))
		{
				model.addAttribute("alarmRouterLogList", alarmRouterLog);
		}else{
			if (sDate == null ||sDate.equals("")|| DateTimeUtils.isValid("dd/MM/yyyy HH:mm", sDate)==false) {
				Calendar cal = Calendar.getInstance();	
				if(eDate!=null && !eDate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy HH:mm", eDate)==true)
				{
					cal.add(Calendar.HOUR_OF_DAY, -1);
				}
				else
				{
					cal.setTime(new Date());
					cal.add(Calendar.HOUR_OF_DAY, -1);
				}
				sDate = df_full.format(cal.getTime())+" "+"00:00";
			}
			filter.setsDate(sDate);
			
			if (eDate == null || eDate.equals("")|| DateTimeUtils.isValid("dd/MM/yyyy HH:mm", eDate)==false) {
				Calendar cal = Calendar.getInstance();
				eDate = df_full.format(cal.getTime())+" "+"23:59";
			}
			
			filter.seteDate(eDate);
			filter.setRouterName(routerName);
			filter.setColumn(column);
			filter.setOrder(order);
			
		}
		alarmRouterLog = alarmRouterLogDao.filter(filter);
		model.addAttribute("alarmRouterLogList", alarmRouterLog);
		model.addAttribute("sDate", sDate);
		model.addAttribute("eDate", filter.geteDate());
		model.addAttribute("ip", ip);
		model.addAttribute("routerName", routerName);
		model.addAttribute("alarmInfo", alarmInfo);
		model.addAttribute("reload", reload);
		model.addAttribute("reloadStr", reloadStr);
		model.addAttribute("timeLoad", timeLoad);
		
		return "jsp/alRouterLogList";
	}
}