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

import vn.com.vhc.vmsc2.statistics.dao.ConvertLogDAO;
import vn.com.vhc.vmsc2.statistics.domain.ConvertLog;
import vn.com.vhc.vmsc2.statistics.web.filter.FileControlFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;


@Controller
public class ConvertLogController extends BaseController {
	
	@Autowired
	private ConvertLogDAO convertLogDao;
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	@Autowired
	public void setConvertLogDao(ConvertLogDAO convertLogDao) {
		this.convertLogDao = convertLogDao;
	}

	@RequestMapping("/log/convert")
	public String filter(@ModelAttribute("filter") FileControlFilter filter,@RequestParam(required = false) String startDate,@RequestParam(required = false) String endDate, Model model, HttpServletRequest request) {
	
		List<ConvertLog> convertLogs=null;
		Integer totalSize = null;
		if ((endDate!=null && !endDate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy HH:mm", endDate)==false)||(startDate!=null && !startDate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy HH:mm", startDate)==false))
		{
				totalSize = null;
				convertLogs = null;
		}
		else
		{
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
			convertLogs = convertLogDao.filter(filter);
			totalSize = convertLogDao.countRow(filter);
		}
		
		model.addAttribute("convertLogList", convertLogs);
		model.addAttribute("totalSize", totalSize);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("statusCode", filter.getStatusCode());
		model.addAttribute("fileName", filter.getFileName());
		
		return "convertLogList";
	}
}
