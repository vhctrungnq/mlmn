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

import vo.RalarmCsiLog;
import vo.alarm.utils.DateTimeUtils;
import vo.alarm.utils.RalarmCsiLogFilter;
import dao.HRouterDAO;
import dao.RalarmCsiLogDAO;

@Controller
public class AlCSILogController extends BaseController {
	
	@Autowired
	private RalarmCsiLogDAO alarmCsiLogDAO ;
	@Autowired
	private HRouterDAO hRouterDao;
	
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy");

	@Autowired
	public void setRalarmCsiLogDAO(RalarmCsiLogDAO alarmCsiLogDAO) {
		this.alarmCsiLogDAO = alarmCsiLogDAO;
	}

	@RequestMapping("/log/csi/list")
	public String filter(@ModelAttribute("filter") RalarmCsiLogFilter filter,
			@RequestParam(required = false) String csiType,
			@RequestParam(required = false) String sdate,
			@RequestParam(required = false) String edate,
			@RequestParam(required = false) String reload,
			@RequestParam(required = false) String reloadStr,
			@RequestParam(required = false) String timeLoad,
			Model model, HttpServletRequest request) {		
		
		List<RalarmCsiLog> alarmLog=null;
		
		if(csiType == null || csiType == ""){
			csiType = "CMD";
		}
		
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
		
		int order = 2;
		String column = "day";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder(
					"item")
					.encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item")
					.encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		if ((edate!=null && !edate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy HH:mm", edate)==false)||(sdate!=null && !sdate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy HH:mm", sdate)==false))
		{
				model.addAttribute("alarmLogList", alarmLog);
		}else{
			if (sdate == null ||sdate.equals("")|| DateTimeUtils.isValid("dd/MM/yyyy HH:mm", sdate)==false) {
				Calendar cal = Calendar.getInstance();	
				if(edate!=null && !edate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy HH:mm", edate)==true)
				{
					cal.add(Calendar.HOUR_OF_DAY, 0);
				}
				else
				{
					cal.setTime(new Date());
					cal.add(Calendar.HOUR_OF_DAY, 0);
				}
				sdate = df_full.format(cal.getTime())+" "+"00:00";
			}
			filter.setSdate(sdate);
			
			if (edate == null || edate.equals("")|| DateTimeUtils.isValid("dd/MM/yyyy HH:mm", edate)==false) {
				Calendar cal = Calendar.getInstance();
				edate = df_full.format(cal.getTime())+" "+"23:59";
			} 
			filter.setEdate(edate);
			
		}
		
		filter.setCsiType(csiType);
		alarmLog = alarmCsiLogDAO.filter(filter, column, order);
		
		model.addAttribute("csiType", csiType);
		model.addAttribute("alarmLogList", alarmLog);
		model.addAttribute("filter", filter);
		model.addAttribute("reload", reload);
		model.addAttribute("reloadStr", reloadStr);
		model.addAttribute("timeLoad", timeLoad);
		
		return "jsp/alCsiLogList";
	}
}