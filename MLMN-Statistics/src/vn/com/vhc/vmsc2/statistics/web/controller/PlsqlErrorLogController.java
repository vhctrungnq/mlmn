package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.PlsqlErrorLogDAO;
import vn.com.vhc.vmsc2.statistics.domain.PlsqlErrorLog;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;


@Controller
public class PlsqlErrorLogController extends BaseController {
	
	@Autowired
	private PlsqlErrorLogDAO plsqlErrorLogDao;
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	@Autowired
	public void setplsqlErrorLogDao(PlsqlErrorLogDAO plsqlErrorLogDao) {
		this.plsqlErrorLogDao = plsqlErrorLogDao;
	}

	@RequestMapping("/log/plsqlError")
	public ModelAndView filter(@ModelAttribute("filter") PlsqlErrorLog filter,@RequestParam(required = false) String startDate,@RequestParam(required = false) String endDate, Model model, HttpServletRequest request) {
		/*int startRecord = 0;
		try {
			startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("plsqlErrorLog").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
		} catch (NumberFormatException e) {
		}
		int endRecord = startRecord + 100;

		filter.setStartRecord(startRecord);
		filter.setEndRecord(endRecord);*/
		
		List<PlsqlErrorLog> plsqlErrorLogs = null;
		Integer totalSize = null;
		if ((endDate!=null && !endDate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy HH:mm", endDate)==false)||(startDate!=null && !startDate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy HH:mm", startDate)==false))
		{
				totalSize = null;
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
			filter.setStartDate(startDate);
			
			if (endDate == null || endDate.equals("")|| DateTimeUtils.isValid("dd/MM/yyyy HH:mm", endDate)==false) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_MONTH, 1);
				endDate = df_full.format(cal.getTime());
			}
			filter.setEndDate(endDate);
			/*totalSize = plsqlErrorLogDao.countRow(filter);*/
			plsqlErrorLogs = plsqlErrorLogDao.filter(filter);
		}
		model.addAttribute("plsqlErrorLogList", plsqlErrorLogs);
		model.addAttribute("totalSize", totalSize);
		model.addAttribute("endDate", endDate);
		model.addAttribute("startDate", startDate);

		return new ModelAndView("plsqlErrorLogList");
	}
}
