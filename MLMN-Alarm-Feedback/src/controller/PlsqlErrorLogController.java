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
import org.springframework.web.servlet.ModelAndView;

import vo.PlsqlErrorLog;
import vo.SYS_PARAMETER;
import vo.alarm.utils.DateTimeUtils;

import dao.PlsqlErrorLogDAO;


@Controller
public class PlsqlErrorLogController extends BaseController {
	
	@Autowired
	private PlsqlErrorLogDAO plsqlErrorLogDao;
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	@RequestMapping("/log/plsqlError")
	public ModelAndView filter(@ModelAttribute("filter") PlsqlErrorLog filter,@RequestParam(required = false) String startDate,@RequestParam(required = false) String endDate, Model model, HttpServletRequest request) {
		
		List<SYS_PARAMETER> sysParemeterTitle = plsqlErrorLogDao.titleForm();
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		int order = 0;
		String column = "LOG_TIME";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("plsqlErrorLog").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("plsqlErrorLog").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		int startRecord = 0;
		try {
			startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("plsqlErrorLog").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
		} catch (NumberFormatException e) {
		}
		int endRecord = startRecord + 100;
		
		List<PlsqlErrorLog> plsqlErrorLogs = null;
		Integer totalSize = 0;
		if ((endDate!=null && !endDate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy HH:mm", endDate)==false)||(startDate!=null && !startDate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy HH:mm", startDate)==false))
		{
				totalSize = 0;
				plsqlErrorLogs = null;
		}else{
			if (startDate == null ||startDate.equals("")|| DateTimeUtils.isValid("dd/MM/yyyy HH:mm", startDate)==false) {
				Calendar cal = Calendar.getInstance();	
				if(endDate!=null && !endDate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy HH:mm", endDate)==true)
				{
					cal.add(Calendar.DAY_OF_MONTH, -1);
				}
				else
				{
					cal.setTime(new Date());
					cal.add(Calendar.DAY_OF_MONTH, -1);
				}
				startDate = df_full.format(cal.getTime());
			}
		//	filter.setStartDate(startDate);
			
			if (endDate == null || endDate.equals("")|| DateTimeUtils.isValid("dd/MM/yyyy HH:mm", endDate)==false) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_MONTH, 1);
				endDate = df_full.format(cal.getTime());
			}
		//	filter.setEndDate(endDate);
			try
			{
				plsqlErrorLogs = plsqlErrorLogDao.filter(filter,startDate,endDate,startRecord,endRecord,column,order);
				totalSize = plsqlErrorLogDao.countRow(filter,startDate,endDate);
			}
			catch(Exception exp)
			{
				totalSize = 0;
				plsqlErrorLogs = null;
			}
		
		}
		model.addAttribute("plsqlErrorLogList", plsqlErrorLogs);
		model.addAttribute("totalSize", totalSize);
		model.addAttribute("endDate", endDate);
		model.addAttribute("startDate", startDate);
		model.addAttribute("filter", filter);
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "CoreLog_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
				
		return new ModelAndView("jsp/QuanLyLog/plsqlErrorLogList");
	}
}
