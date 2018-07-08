package controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vo.SYS_PARAMETER;
import vo.SchedulerJobLog;
import vo.SchedulerJobs;

import dao.SchedulerJobLogDAO;
import dao.SchedulerJobsDAO;

@Controller
@RequestMapping("/log/checkjob/*")
public class ScheduleJoblogController extends BaseController{
	@Autowired
	private SchedulerJobLogDAO schedulejoblogDAO;
	
	@Autowired
	private SchedulerJobsDAO schedulejobDAO;
	@RequestMapping("list")
	public ModelAndView list(@RequestParam(required = false) String user,
							HttpServletRequest request,
							Model model) {	
		List<SYS_PARAMETER> sysParemeterTitle = schedulejobDAO.titleFormList();
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		int order = 0;
		String column = "LOG_ID";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("user").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("user").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		int startRecord = 0;
		try {
			startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("user").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
		} catch (NumberFormatException e) {
		}
		int endRecord = startRecord + 100;
	
		if(user == null)
			user = "";
		List<SchedulerJobLog> jobnameloglist =null;
		Integer totalSize = 0;
		try
		{
			
			jobnameloglist = schedulejoblogDAO.filter(user,startRecord,endRecord,column,order);
			totalSize = schedulejoblogDAO.countRow(user);
			
		}
		catch(Exception exp)
		{
			totalSize = 0;
			jobnameloglist = null;
		}
		
		model.addAttribute("jobnameloglist", jobnameloglist);
		
		List<SchedulerJobs> jobnamelist =  schedulejobDAO.getJobName();
		model.addAttribute("jobnamelist", jobnamelist);
		model.addAttribute("user", user);
		model.addAttribute("totalSize", totalSize);	
		model.addAttribute("startRecord", startRecord);
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "SchedulerJobLog_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
				
		return new ModelAndView("jsp/QuanLyLog/jobnameloglist");
	}
	
	@RequestMapping("detail")
	public ModelAndView detail(@RequestParam(required = false) String user,
							HttpServletRequest request,
							Model model) {	
		
		List<SYS_PARAMETER> sysParemeterTitle = schedulejobDAO.titleFormDetail();
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		int order = 0;
		String column = "OWNER";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("user").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("user").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		int startRecord = 0;
		try {
			startRecord = (Integer.parseInt(request.getParameter((new ParamEncoder("user").encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) - 1) * 100;
		} catch (NumberFormatException e) {
		}
		int endRecord = startRecord + 100;
	
		if(user == null)
			user = "";
		
		List<SchedulerJobs> jobnamelist1 =null;
		Integer totalSize = 0;
		try
		{
			
			jobnamelist1 = schedulejobDAO.filter(user,startRecord,endRecord,column,order);
			totalSize = schedulejobDAO.countRow(user);
			
		}
		catch(Exception exp)
		{
			totalSize = 0;
			jobnamelist1 = null;
		}
		
		model.addAttribute("jobnamelist1", jobnamelist1);
		
		List<SchedulerJobs> jobnamelist =  schedulejobDAO.getJobName();
		model.addAttribute("jobnamelist", jobnamelist);
		model.addAttribute("user", user);
		model.addAttribute("totalSize", totalSize);	
		model.addAttribute("startRecord", startRecord);
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "SchedulerJob_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		return new ModelAndView("jsp/QuanLyLog/jobnamelist");
	}
}

