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

import vn.com.vhc.vmsc2.statistics.dao.ImportLogDAO;
import vn.com.vhc.vmsc2.statistics.domain.ImportLog;
import vn.com.vhc.vmsc2.statistics.web.filter.FileControlFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;

@Controller
public class ImportLogController extends BaseController {
	@Autowired
	private ImportLogDAO importLogDao;
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	@Autowired
	public void ImportLogDao(ImportLogDAO importLogDao) {
		this.importLogDao = importLogDao;
	}
	
	@RequestMapping("/log/import")
	public String filter(@ModelAttribute("filter") FileControlFilter filter,@RequestParam(required = false) String startDate,@RequestParam(required = false) String endDate, @RequestParam(required = false) String fileName, @RequestParam(required = false) String nodeName, Model model, HttpServletRequest request) {
		
		List<ImportLog> importLogs = null;
		
		if ((endDate!=null && !endDate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy HH:mm", endDate)==false)||(startDate!=null && !startDate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy HH:mm", startDate)==false))
		{
				importLogs = null;
				model.addAttribute("importLogs", importLogs);
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
			importLogs = importLogDao.filter(filter);
		} 
		model.addAttribute("importLogList", importLogs);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		return "importLogList";
	}
}
