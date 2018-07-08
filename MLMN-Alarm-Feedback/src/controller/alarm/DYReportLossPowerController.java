package controller.alarm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controller.BaseController;

import vo.DyReportLosspowerByDay;
import vo.DyReportLosspowerByTeam;
import vo.SYS_PARAMETER;
import vo.alarm.utils.DateTools;

import dao.AlShiftDAO;
import dao.DyReportLosspowerByDayDAO;
import dao.DyReportLosspowerByTeamDAO;
import dao.SYS_PARAMETERDAO;

@Controller
@RequestMapping("/reportLossPower/*")
public class DYReportLossPowerController extends BaseController{
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired 
	private AlShiftDAO alShiftDAO;
	
	@Autowired 
	private DyReportLosspowerByTeamDAO dyReportLosspowerByTeamDAO;
	
	@Autowired 
	private DyReportLosspowerByDayDAO dyReportLosspowerByDayDAO;
	
	private DateFormat df_full2 = new SimpleDateFormat("dd/MM/yyyy");
		
	@RequestMapping("reportTeam")
	public ModelAndView list(
			@RequestParam(required = false) String startTime,
			@RequestParam(required = false) String endTime,
			Model model, HttpServletRequest request) {
		
		Calendar cal = Calendar.getInstance();	 
		cal.setTime(new Date(cal.getTimeInMillis() - 24 * 60 * 60 * 1000));
		if (endTime == null || endTime.equals("")||(endTime!=null && !endTime.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", endTime+" 00:00:00")==false))
		{	
			  
			endTime = df_full2.format(cal.getTime());
		}
		if (startTime == null || startTime.equals("")||(startTime!=null && !startTime.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", startTime+" 00:00:00")==false))
		{
			
			startTime = endTime;
		}
		List<SYS_PARAMETER> sysParemeterTitle = dyReportLosspowerByTeamDAO.titleReportLossPowerByTeam();
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		List<DyReportLosspowerByTeam> repotList = new ArrayList<DyReportLosspowerByTeam>();
		int countAll=0;
		try
		{
			repotList = dyReportLosspowerByTeamDAO.createReportWithTeam(startTime,endTime);
			countAll = dyReportLosspowerByTeamDAO.getCountAllLoss(startTime,endTime);
		}
		catch(Exception exp)
		{
			
		}
		
		model.addAttribute("startTime", startTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("repotList", repotList);
		model.addAttribute("total", countAll);
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "ReportLossPowerWithTeam_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		return new ModelAndView("jsp/report/reportMD/reportWithTeam");
	}
	
	@RequestMapping("reportDay")
	public ModelAndView reportDay(
			@RequestParam(required = false) String startTime,
			@RequestParam(required = false) String endTime,
			Model model, HttpServletRequest request) {
		List<SYS_PARAMETER> sysParemeterTitle = dyReportLosspowerByDayDAO.titleReportLossPowerByDay();
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		Calendar cal = Calendar.getInstance();	 
		cal.setTime(new Date(cal.getTimeInMillis() - 24 * 60 * 60 * 1000));
		if (endTime == null || endTime.equals("")||(endTime!=null && !endTime.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", endTime+" 00:00:00")==false))
		{	
			  
			endTime = df_full2.format(cal.getTime());
		}
		if (startTime == null || startTime.equals("")||(startTime!=null && !startTime.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", startTime+" 00:00:00")==false))
		{
			
			startTime = endTime;
		}
		
		List<DyReportLosspowerByDay> repotList = new ArrayList<DyReportLosspowerByDay>();
		int countAll=0;
		try
		{
			repotList = dyReportLosspowerByDayDAO.createReportWithDay(startTime,endTime);
			countAll = dyReportLosspowerByTeamDAO.getCountAllLoss(startTime,endTime);
		}
		catch(Exception exp)
		{
			
		}
		
		model.addAttribute("startTime", startTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("repotList", repotList);
		model.addAttribute("total", countAll);
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "ReportLossPowerWithDay_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		return new ModelAndView("jsp/report/reportMD/reportWithDay");
	}
}
