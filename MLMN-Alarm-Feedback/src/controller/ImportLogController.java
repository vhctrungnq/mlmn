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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vo.ImportLog;
import vo.SYS_PARAMETER;
import vo.alarm.utils.DateTimeUtils;

import dao.ImportLogDAO;

@Controller
public class ImportLogController extends BaseController {
	@Autowired
	private ImportLogDAO importLogDao;
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	
	@RequestMapping("/log/import")
	public String filter(@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate, 
			@RequestParam(required = false) String statusCode,
			@RequestParam(required = false) String fileName,
			Model model, HttpServletRequest request) {
		List<SYS_PARAMETER> sysParemeterTitle = importLogDao.titleImportLog();
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		
		int order = 0;
		String column = "IMPORT_TIME";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("importLog").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("importLog").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		int startRecord = 0;
		try {
			startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("importLog").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
		} catch (NumberFormatException e) {
		}
		int endRecord = startRecord + 100;
		
		if(fileName == null){
			fileName="";
		}
		else
			fileName= fileName.trim();
		
		if(statusCode == null){
			statusCode="";
		}
		else
			statusCode= statusCode.trim();
		
		List<ImportLog> importLogs = null;
		Integer totalSize = 0;
		if ((endDate!=null && !endDate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy HH:mm", endDate)==false)||(startDate!=null && !startDate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy HH:mm", startDate)==false))
		{
				importLogs = null;
				totalSize = 0;
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
			//filter.setStartDate(startDate);
			
			if (endDate == null || endDate.equals("")|| DateTimeUtils.isValid("dd/MM/yyyy HH:mm", endDate)==false) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_MONTH, 1);
				endDate = df_full.format(cal.getTime());
			}
			//filter.setEndDate(endDate);	
			try
			{
				importLogs = importLogDao.filter(startDate,endDate,statusCode,fileName,startRecord,endRecord,column,order);
				totalSize = importLogDao.countRow(startDate,endDate,statusCode,fileName);
			}
			catch (Exception exp)
			{
				totalSize = 0;
				importLogs = null;
			}
		} 
		model.addAttribute("importLogList", importLogs);
		model.addAttribute("totalSize", totalSize);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("startRecord", startRecord);
		model.addAttribute("statusCode",statusCode);
		model.addAttribute("fileName", fileName);
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "FileImportLog_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		return "jsp/QuanLyLog/importLogList";
	}
}
