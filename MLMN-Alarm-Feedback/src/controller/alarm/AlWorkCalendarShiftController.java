package controller.alarm;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import controller.BaseController;

import dao.AlWorkCalendarDAO;
import dao.CHighlightConfigsDAO;
import dao.CSystemConfigsDAO;
import dao.MDepartmentDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUsersDAO;
import dao.W_WORKING_MANAGEMENTSDAO;

import vn.com.vhc.alarm.core.AL_Global;
import vo.AlWorkCalendar;
import vo.AlWorkCalendarShift;
import vo.CHighlightConfigs;
import vo.CSystemConfigs;
import vo.MDepartment;
import vo.SYS_PARAMETER;
import vo.SendMailJob;
import vo.SysMailParameterMaster;
import vo.SysUsers;
import vo.W_WORKING_MANAGEMENTS;
import vo.alarm.utils.Helper;
import vo.alarm.utils.Mail;
import vo.alarm.utils.SendMailSetting;


/**
 * Function        : Hien thi so ca dien tu
 * Created By      : BUIQUANG
 * Create Date     : 04/12/2013
 * Modified By     : 
 * Modify Date     : 
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/alarm/al-work-calendar-shift/*")
public class AlWorkCalendarShiftController extends BaseController{
	@Autowired
	private MDepartmentDAO mDepartmentDao;
	@Autowired
	private AlWorkCalendarDAO alWorkCalendarDAO;
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
	@Autowired
	private W_WORKING_MANAGEMENTSDAO working_managementDao;
	
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired
	private CSystemConfigsDAO cSystemConfigsDAO;
	

	
	@Autowired
	private SysUsersDAO sysUsersDao;
	private static DataSource dataSource = null;
	private static Logger logger = Logger.getLogger(SendMailJob.class.getName());
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	public AlWorkCalendarShiftController(){}
	@RequestMapping(value="list",method = RequestMethod.GET)
    public ModelAndView listGet(
    		@RequestParam(required = false) String action,
    		@RequestParam(required = false) Integer year,
			@RequestParam(required = false) Integer week, 	   
			@RequestParam(required = false) String department,
			@RequestParam(required = false) String team,
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String tenCongViec,
			@RequestParam(required = false) String noiDung,
			@RequestParam(required = false) Integer idwork,
			@RequestParam(required = false) String subject,
			@RequestParam(required = false) String headerContent,
			@RequestParam(required = false) String shiftMode,
			@RequestParam(required = false) String region,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Calendar sDateCalendar = Calendar.getInstance();
		sDateCalendar.setFirstDayOfWeek(Calendar.MONDAY);
		sDateCalendar.setMinimalDaysInFirstWeek(4);
		if(week == null)
			week = sDateCalendar.get(Calendar.WEEK_OF_YEAR);
		if (year == null)
			year = sDateCalendar.get(Calendar.YEAR);
		
		SysUsers userManager = sysUsersDao.selectByUsename(username);
		model.addAttribute("isRoleManager", userManager.getIsRoleManager());
		
		if ((region==null || region.equals("")))
		{
			region = userManager.getRegion();
		}
		if ((department==null || department.equals("")))
		{
			department = userManager.getMaPhong();
		}
		if(department != null && !department.equals(""))
		{
			MDepartment mDepartment = mDepartmentDao.selectByPrimaryKey(department);
			model.addAttribute("departmentName", mDepartment.getDeptName().toUpperCase());
		}
		if(team != null && !team.equals(""))
		{
			MDepartment mDepartment = mDepartmentDao.selectByPrimaryKey(team);
			model.addAttribute("teamName", mDepartment.getDeptName());
		}
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "ShiftList_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
				
		// Close shift, Open shift
		
		//lay danh sach khu vuc
		List<SYS_PARAMETER> regionList = sysParameterDao.getRegionList();
		model.addAttribute("regionList", regionList);
		
				
		List<AlWorkCalendar> daysOfWeekList = alWorkCalendarDAO.getDaysOfWeek(week, year);
		
		List<AlWorkCalendar> statusShiftMode = alWorkCalendarDAO.getStatusShiftMode( daysOfWeekList.get(0).getDayStr(),  daysOfWeekList.get(6).getDayStr());
		if (statusShiftMode.size() > 0) {
			if(statusShiftMode.get(0).getShiftMode().equals("Y"))
				shiftMode = "Đóng ca trực";
			else
				shiftMode = "Mở ca trực";
		}
		else{
			shiftMode = "Đóng ca trực";
		}
		
		String mondayValue = daysOfWeekList.get(0).getDayStr();
		model.addAttribute("mondayValue", mondayValue);
		String tuesdayValue = daysOfWeekList.get(1).getDayStr();
		model.addAttribute("tuesdayValue", tuesdayValue);
		String wednesdayValue = daysOfWeekList.get(2).getDayStr();
		model.addAttribute("wednesdayValue", wednesdayValue);
		String thursdayValue = daysOfWeekList.get(3).getDayStr();
		model.addAttribute("thursdayValue", thursdayValue);
		String fridayValue = daysOfWeekList.get(4).getDayStr();
		model.addAttribute("fridayValue", fridayValue);
		String saturdayValue = daysOfWeekList.get(5).getDayStr();
		model.addAttribute("saturdayValue", saturdayValue);
		String sundayValue = daysOfWeekList.get(6).getDayStr();
		model.addAttribute("sundayValue", sundayValue);
		String dateShift = daysOfWeekList.get(0).getDayStr().substring(0, 5) + " - " + daysOfWeekList.get(6).getDayStr();
		model.addAttribute("dateShift", dateShift);
		
		List<MDepartment> departmentList = mDepartmentDao.getDepartmentForShiftList("1");
		model.addAttribute("departmentList", departmentList);
		
		List<MDepartment> teamList = mDepartmentDao.loadTeamByDepartment(department);
		model.addAttribute("teamList", teamList);
		
		List<AlWorkCalendarShift> alWorkCalendarShiftList = alWorkCalendarDAO.getAlWorkCalendarShift(week, year, department, team,region);
		model.addAttribute("alWorkCalendarShiftList", alWorkCalendarShiftList);
		
		List<CHighlightConfigs> highlightConfigList = cHighlightConfigsDAO.getByKey("SHIFT_COLOR");
		model.addAttribute("highlight", Helper.highLightRows(highlightConfigList, "item"));
		
		List<CHighlightConfigs> highlightConfigTruongCa = cHighlightConfigsDAO.getByKey("TRUONG_CA");
		model.addAttribute("highlightTruongCa", Helper.highLightRows_TruongCa(highlightConfigTruongCa, "item"));
		
		String tinyTips = "";
		List<AlWorkCalendar> idUsersList = alWorkCalendarDAO.getIdUsersList(mondayValue);
		for(int i=0;i<idUsersList.size();i++){
			SysUsers user = sysUsersDao.selectByID(idUsersList.get(i).getIdUser().toString());
			tinyTips += tinyTipsFun(user);
		}
		List<AlWorkCalendar> idUsersBeforeList = alWorkCalendarDAO.getIdUsersBeforeList(mondayValue);
		for(int i=0;i<idUsersBeforeList.size();i++){
			SysUsers user = sysUsersDao.selectByID(idUsersBeforeList.get(i).getIdUser().toString());
			SysUsers userBefore = sysUsersDao.selectByID(idUsersBeforeList.get(i).getIdUserBefore().toString());
			tinyTips += tinyTipsFunBefore(user, userBefore);
		}
		
		model.addAttribute("tinyTips", tinyTips);
		List<SysMailParameterMaster> emailDefault = alWorkCalendarDAO.getEmailDefaultForShift();
		if(emailDefault.size() > 0)
			{
				if (email == null || email.equals(""))
				{
					email = emailDefault.get(0).getMailTo();
				}
				if (subject == null || subject.equals(""))
				{
					subject = emailDefault.get(0).getMailName();
					if (subject!=null)
					{
						subject = subject.replace("#W#", " " + week);
						subject = subject.replace("#Y#", " " + year);
						if(department != null && !department.equals(""))
							subject = subject.replace("#DEPARTMENT#", department.toLowerCase());
						else
							subject = subject.replace("#DEPARTMENT#", "");
					}
				}
				if (headerContent == null || headerContent.equals(""))
				{
					headerContent = emailDefault.get(0).getContentHeader();
					if (headerContent!=null)
					{
						headerContent = headerContent.replace("#W#"," " + week);
						headerContent = headerContent.replace("#Y#"," " + year);
						if(department != null && !department.equals(""))
							headerContent = headerContent.replace("#DEPARTMENT#", department.toLowerCase());
						else
							headerContent = headerContent.replace("#DEPARTMENT#", "");
					}
				}
				
			}
		if (idwork!=null && !idwork.equals(""))
		{
			W_WORKING_MANAGEMENTS work= working_managementDao.selectByPrimaryKey(idwork);
			if (work!=null && work.getNguoiGiaoViec().equals(username))
			{
				model.addAttribute("tenCongViec", work.getTenCongViec());
				model.addAttribute("noiDung", work.getNoiDung());
				model.addAttribute("idwork", work.getId());
			}
			else
			{
				saveMessageKey(request, "messsage.confirm.khongCoQuyenSua");
			}
		}
	
		List<W_WORKING_MANAGEMENTS> workList = working_managementDao.getWorkInShift(year, week, department,team,region);
		model.addAttribute("workList", workList);
		
		//HEAD
		List<SYS_PARAMETER> head =  sysParameterDao.getShiftHeader(region);
		String headStr="TRUNG TÂM THÔNG TIN DI ĐỘNG";
		if (head.size()>0)
		{
			headStr = head.get(0).getValue();
		}
		model.addAttribute("head", headStr);
		model.addAttribute("year", year);
		model.addAttribute("shiftMode", shiftMode);
		model.addAttribute("week", week);
		model.addAttribute("department", department);
		model.addAttribute("team", team);
		model.addAttribute("email", email);
		model.addAttribute("subject", subject);
		model.addAttribute("headerContent", headerContent);
		model.addAttribute("region", region);
		return new ModelAndView("jspalarm/diary/alWorkCalendarShiftList");
	}
	@RequestMapping(value="list",method = RequestMethod.POST)
    public String list(
    		@RequestParam(required = false) String action,
    		@RequestParam(required = false) Integer year,
			@RequestParam(required = false) Integer week, 	   
			@RequestParam(required = false) String department,
			@RequestParam(required = false) String team,
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String tenCongViec,
			@RequestParam(required = false) String noiDung,
			@RequestParam(required = false) Integer idwork,
			@RequestParam(required = false) String subject,
			@RequestParam(required = false) String headerContent,
			@RequestParam(required = false) String shiftMode,
			@RequestParam(required = false) String region,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userManager = sysUsersDao.selectByUsename(username);
		model.addAttribute("isRoleManager", userManager.getIsRoleManager());
		
		
		Calendar sDateCalendar = Calendar.getInstance();
		if(week == null)
			week = sDateCalendar.get(Calendar.WEEK_OF_YEAR);
		if (year == null)
			year = sDateCalendar.get(Calendar.YEAR);
		String departmentName = "";
		String teamName = "";
		if(department != null && !department.equals(""))
		{
			MDepartment mDepartment = mDepartmentDao.selectByPrimaryKey(department);
			departmentName = mDepartment.getDeptName().toUpperCase();
			model.addAttribute("departmentName", departmentName);
		}
		if(team != null && !team.equals(""))
		{
			MDepartment mDepartment = mDepartmentDao.selectByPrimaryKey(team);
			teamName = mDepartment.getDeptName();
			model.addAttribute("teamName",teamName );
		}
		
		//lay danh sach khu vuc
		List<SYS_PARAMETER> regionList = sysParameterDao.getRegionList();
		model.addAttribute("regionList", regionList);
		if ((region==null || region.equals("")))
		{
			region = userManager.getRegion();
		}
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "ShiftList_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
			
		List<AlWorkCalendar> daysOfWeekList = alWorkCalendarDAO.getDaysOfWeek(week, year);
		
		String mondayValue = daysOfWeekList.get(0).getDayStr();
		model.addAttribute("mondayValue", mondayValue);
		String tuesdayValue = daysOfWeekList.get(1).getDayStr();
		model.addAttribute("tuesdayValue", tuesdayValue);
		String wednesdayValue = daysOfWeekList.get(2).getDayStr();
		model.addAttribute("wednesdayValue", wednesdayValue);
		String thursdayValue = daysOfWeekList.get(3).getDayStr();
		model.addAttribute("thursdayValue", thursdayValue);
		String fridayValue = daysOfWeekList.get(4).getDayStr();
		model.addAttribute("fridayValue", fridayValue);
		String saturdayValue = daysOfWeekList.get(5).getDayStr();
		model.addAttribute("saturdayValue", saturdayValue);
		String sundayValue = daysOfWeekList.get(6).getDayStr();
		model.addAttribute("sundayValue", sundayValue);
		String dateShift = daysOfWeekList.get(0).getDayStr().substring(0, 5) + " - " + daysOfWeekList.get(6).getDayStr();
		model.addAttribute("dateShift", dateShift);
		
		List<MDepartment> departmentList = mDepartmentDao.getDepartmentForShiftList("1");
		model.addAttribute("departmentList", departmentList);
		
		List<MDepartment> teamList = mDepartmentDao.loadTeamByDepartment(department);
		model.addAttribute("teamList", teamList);
		
		List<AlWorkCalendarShift> alWorkCalendarShiftList = alWorkCalendarDAO.getAlWorkCalendarShift(week, year, department, team,region);
		model.addAttribute("alWorkCalendarShiftList", alWorkCalendarShiftList);
		
		List<CHighlightConfigs> highlightConfigList = cHighlightConfigsDAO.getByKey("SHIFT_COLOR");
		model.addAttribute("highlight", Helper.highLightRows(highlightConfigList, "item"));
		
		List<CHighlightConfigs> highlightConfigTruongCa = cHighlightConfigsDAO.getByKey("TRUONG_CA");
		model.addAttribute("highlightTruongCa", Helper.highLightRows_TruongCa(highlightConfigTruongCa, "item"));
		
		String contentRooter = "";
		List<SysMailParameterMaster> emailDefault = alWorkCalendarDAO.getEmailDefaultForShift();
		if(emailDefault.size() > 0) {
			contentRooter = emailDefault.get(0).getContentRooter();
		}
		if(emailDefault.size() > 0 && (email == null || email.equals("")))
			email = emailDefault.get(0).getMailTo();
		
		//HEAD
		List<SYS_PARAMETER> head =  sysParameterDao.getShiftHeader(region);
		String headStr="TRUNG TÂM THÔNG TIN DI ĐỘNG";
		if (head.size()>0)
		{
			headStr = head.get(0).getValue();
		}
		model.addAttribute("head", headStr);
				
		model.addAttribute("year", year);
		model.addAttribute("week", week);
		model.addAttribute("department", department);
		model.addAttribute("team", team);
		model.addAttribute("email", email);
		model.addAttribute("subject", subject);
		model.addAttribute("region", region);
		model.addAttribute("headerContent", headerContent);
		
		String tinyTips = "";
		List<AlWorkCalendar> idUsersList = alWorkCalendarDAO.getIdUsersList(mondayValue);
		for(int i=0;i<idUsersList.size();i++){
			SysUsers user = sysUsersDao.selectByID(idUsersList.get(i).getIdUser().toString());
			tinyTips += tinyTipsFun(user);
		}
		List<AlWorkCalendar> idUsersBeforeList = alWorkCalendarDAO.getIdUsersBeforeList(mondayValue);
		for(int i=0;i<idUsersBeforeList.size();i++){
			SysUsers user = sysUsersDao.selectByID(idUsersBeforeList.get(i).getIdUser().toString());
			SysUsers userBefore = sysUsersDao.selectByID(idUsersBeforeList.get(i).getIdUserBefore().toString());
			tinyTips += tinyTipsFunBefore(user, userBefore);
		}
		
		model.addAttribute("tinyTips", tinyTips);
		
		
		// CHinh sua /them moi noi dung trong ca truc
		if(action != null && action != "" && action.equals("saveContent")) {
			/*try {*/
				String estimateDate = daysOfWeekList.get(0).getDayStr();
				String actualDate = daysOfWeekList.get(6).getDayStr();
				
				System.out.println("estimateDate: "+estimateDate);
				System.out.println("actualDate: "+actualDate);
				System.out.println("year: "+year);
				System.out.println("week: "+week);
				System.out.println("team: "+team);
				System.out.println("department: "+department);
				System.out.println("idwork: "+idwork);
				if (idwork==null)
				{
					idwork=0;
				}
				working_managementDao.insertWorkInShift(tenCongViec,noiDung, year, week, department,team,estimateDate, actualDate, username,idwork,region);
				saveMessageKey(request, "vAlRbLossPower.insertSucceFull");
				noiDung="";
				tenCongViec="";
				idwork=null;
				
			/*} catch (Exception e) {
				saveMessageKey(request, "cableAttributesMaster.UpdateError");
			}*/
			
		}
		StringBuffer path= request.getRequestURL();
		System.out.println("path: "+path.toString());
		System.out.println("path1: "+request.getLocalAddr());
		System.out.println("path2: "+request.getRequestURL());
		System.out.println("path3: "+request.getLocalName());
		System.out.println("path3: "+request.getPathInfo());
		System.out.println("path3: "+request.getRequestURI());
		System.out.println("path3: "+request.getServletPath());
		List<W_WORKING_MANAGEMENTS> workList = working_managementDao.getWorkInShift(year, week, department,team,region);
		model.addAttribute("workList", workList);
		System.out.println("size: "+workList.size());
		if(action != null && action != "" && action.equals("sendMail")) {
			try {
				if(email != null && !email.equals(""))
					email = email.replace(";", ",");
				sendMail(email, subject, headerContent, contentRooter, week, dateShift, daysOfWeekList, alWorkCalendarShiftList,workList, departmentName, teamName, noiDung, username, path.toString(), headStr);
				noiDung="";
				tenCongViec="";
				saveMessageKey(request, "messsage.confirm.sendMailSuccess");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				saveMessageKey(request, "messsage.confirm.sendMailFall");
			}
			
		}
		// Close, Open Shift
		if(action != null && action != "" && action.equals("shiftMode")) {
			
			if(shiftMode!= null && !shiftMode.equals("") && shiftMode.equalsIgnoreCase("Mở ca trực"))
			{
				alWorkCalendarDAO.updateShiftMode(daysOfWeekList.get(0).getDayStr(), daysOfWeekList.get(6).getDayStr(), "Y");
				
				saveMessage(request, "Mở ca trực thành công");
			}
			else if(shiftMode!= null && !shiftMode.equals("") && shiftMode.equalsIgnoreCase("Đóng ca trực")){
				alWorkCalendarDAO.updateShiftMode(daysOfWeekList.get(0).getDayStr(), daysOfWeekList.get(6).getDayStr(), "N");
				
				saveMessage(request, "Đóng ca trực thành công");
			}
			
		}
		List<AlWorkCalendar> statusShiftMode = alWorkCalendarDAO.getStatusShiftMode( daysOfWeekList.get(0).getDayStr(),  daysOfWeekList.get(6).getDayStr());
		if (statusShiftMode.size() > 0) {
			if(statusShiftMode.get(0).getShiftMode().equals("Y"))
				shiftMode = "Đóng ca trực";
			else
				shiftMode = "Mở ca trực";
		}
		else{
			shiftMode = "Đóng ca trực";
		}
			model.addAttribute("regionList", regionList);
		
		model.addAttribute("shiftMode", shiftMode);
		model.addAttribute("tenCongViec", tenCongViec);
		model.addAttribute("noiDung", noiDung);
		model.addAttribute("idwork", idwork);
		return "jspalarm/diary/alWorkCalendarShiftList";
	}
	
	private void sendMail(
			String mailTo, 
			String subject,
			String contentHeader,
			String contentRooter,
			Integer week, 
			String dateShift, 
			List<AlWorkCalendar> daysOfWeekList,
			List<AlWorkCalendarShift> alWorkCalendarShiftList,
			List<W_WORKING_MANAGEMENTS> workList,
			String department,
			String team,
			String noiDung,String username,String path,String headStr
			) throws SQLException{
		
		AlWorkCalendarShiftController.loadSystemConfig();
		
		//LAY THONG SO CAI DAT GUI MAIL
		/*Properties props = AL_Global.MAIL_CONFIG;*/
		List<CSystemConfigs> p = cSystemConfigsDAO.getSystemConfigMail();
		Properties props = new Properties();
		for (CSystemConfigs item: p) {
			props.put(item.getParamName(), item.getParamValue());
			//System.out.println(item.getName() + "-" + item.getValue());
		}
		// LAY THONG SO MAY CHU GUI MAIL
		String userFrom = AL_Global.SHIFT_CONFIG.getProperty(SendMailSetting.SENDMAIL_USER_FROM);
		String password = AL_Global.SHIFT_CONFIG.getProperty(SendMailSetting.SENDMAIL_PASSWORD_FROM);
		
		String content = "";
		if(contentHeader != null){
			content = "<span>" + contentHeader + "</span>";
		}
		content += "<br>";
		
    	//TAO DU LIEU MAIL
		content += "<table style=\"width:100%;\">";
		content += "<tr>";
		content += "	<td>";
		content += "		<table>";
		content += "			<tr>";
		content += "				<td align=\"center\" style = \"font-family:Times New Roman,Georgia,Serif;font-size:20px;\">";
		content += headStr;
		content += "				</td>";
		content += "			</tr>";
		if(department != null && !department.equals("")){
			content += "			<tr>";
			content += "				<td align=\"center\" style = \"text-align:center;\">";
			content += "					<b style = \"font-family:Times New Roman,Georgia,Serif;font-size:15px;\">";
			content += department.toUpperCase();
			content += "					</b>";
			content += "				</td>";
			content += "			</tr>";
		}
		if(team != null && !team.equals("")){
			content += "			<tr>";
			content += "				<td align=\"center\" style = \"font-family:Times New Roman,Georgia,Serif;font-size:15px;text-align:center;\">";
			content += "					<b>";
			content += team;
			content += "					</b>";
			content += "				</td>";
			content += "			</tr>";
		}
		
		content += "		</table>";
		content += "	</td>";	
		content += "</tr>";
		content += "</table>";
        content += "<table style=\"width:100%;\">";
        content += "	<tr>";
        content += "		<td align=\"center\">";
        content += "			<b style = \"font-family:Times New Roman,Georgia,Serif;font-size:25px;\">LỊCH TRỰC CA</b>";
        content += "		</td>";
        content += "	</tr>";	
        content += "	<tr>";
        content += "		<td align=\"center\">";
        content += "			<b>	Tuần " + week + " (" + dateShift + ")</b>";
        content += "		</td>";
        content += "	</tr>";
        content += "</table>";
        content += "<br>";
        content = getDataBlockTop(content, workList,noiDung,username,week); 
		content += "<table style=\"border:1px solid  #0099CC; width:100%;\" cellpadding=\"5\" cellspacing=\"0\" name = \"item\">";
		content += "	<thead>";
		content += "		<tr align=\"center\" style=\"font-weight: bold;background-color: #00B050;color:white;\">";
		/*content += "			<th rowspan=\"2\" style=\"border: 1px solid #c4cde0\">Ca trực</th>";*/
		content += "			<th rowspan=\"2\" style=\"border: 1px solid #c4cde0;width:120px;\">Chức năng</th>";
		content += "			<th style=\"border: 1px solid #c4cde0\">THỨ 2</th>";
		content += "			<th style=\"border: 1px solid #c4cde0\">THỨ 3</th>";
		content += "			<th style=\"border: 1px solid #c4cde0\">THỨ 4</th>";
		content += "			<th style=\"border: 1px solid #c4cde0\">THỨ 5</th>";
		content += "			<th style=\"border: 1px solid #c4cde0\">THỨ 6</th>";
		content += "			<th style=\"border: 1px solid #c4cde0\">THỨ 7</th>";
		content += "			<th style=\"border: 1px solid #c4cde0\">CN</th>";
		content += "		</tr>";
		content += "		<tr align=\"center\" style=\"font-weight: bold;background-color: #00B050;color:white;\">";
		content += "			<th style=\"border: 1px solid #c4cde0\">" + daysOfWeekList.get(0).getDayStr() + "</th>";
		content += "			<th style=\"border: 1px solid #c4cde0\">" + daysOfWeekList.get(1).getDayStr() + "</th>";
		content += "			<th style=\"border: 1px solid #c4cde0\">" + daysOfWeekList.get(2).getDayStr() + "</th>";
		content += "			<th style=\"border: 1px solid #c4cde0\">" + daysOfWeekList.get(3).getDayStr() + "</th>";
		content += "			<th style=\"border: 1px solid #c4cde0\">" + daysOfWeekList.get(4).getDayStr() + "</th>";
		content += "			<th style=\"border: 1px solid #c4cde0\">" + daysOfWeekList.get(5).getDayStr() + "</th>";
		content += "			<th style=\"border: 1px solid #c4cde0\">" + daysOfWeekList.get(6).getDayStr() + "</th>";
		content += "		</tr>";
		content += "	</thead>";
		content += "	<tbody>";
		content = getDataBlock(content, alWorkCalendarShiftList);
		content += "	</tbody>";
		content += "</table>";
		content += "<br>";
		content += "Người gửi: " + SecurityContextHolder.getContext().getAuthentication().getName();
		content += "<br>";
		content += "Thời gian gửi: " + df_full.format(new Date());
		if (path!=null && !path.equals(""))
		{
			content += " <br> Để biết thêm chi tiết vui lòng kích vào đường link này: "+path;
		}
		if(contentRooter != null)
			content += "<br>" + contentRooter;
		//gui mails
		try
		{
			Mail mail = new Mail(props, null, userFrom, password,
					mailTo,null,null, subject, content);
			
			String result = mail.send();
			if(result != null)
			{
				logger.warn("Gui mail that bai");
				System.out.println("Gui mail that bai");
			}
		}
		catch(Exception ex)
		{
			logger.warn("Gui mail that bai");
			System.out.println("Gui mail that bai");
		}
	}
	
	
	private String tinyTipsFun(SysUsers user){
			String tinyTips = "";
			tinyTips += "<script type=\"text/javascript\">";
			tinyTips += "$(document).ready(function() {";
			tinyTips +="$('a.imgTip_" +  user.getId() + "').tinyTips('green',";
			tinyTips +="'<b>Họ tên:</b> "+ user.getFullname() +"<br/>' +";
			tinyTips +="'<b>Điện thoại:</b> " + user.getPhone() + "<br/>' +";
			tinyTips +="'<b>Email:</b> "+ user.getEmail() +"<br/>' +";
			tinyTips +="'<b>Phòng:</b> " + user.getMaPhong() + "<br/>'  +";
			if(user.getTeam() != null && !user.getTeam().equals(""))
				tinyTips +="'<b>Tổ:</b> " + user.getTeam() + "<br/>'";
			else
				tinyTips +="'<b>Tổ:</b><br/>'";
			tinyTips +=");";
			tinyTips += "});</script>";
		return tinyTips;
	}
	
	private String tinyTipsFunBefore(SysUsers user, SysUsers userBefore){
			String tinyTips = "";
			tinyTips += "<script type=\"text/javascript\">";
			tinyTips += "$(document).ready(function() {";
			tinyTips +="$('a.imgTip_" +  user.getId() + userBefore.getId() + "').tinyTips('light',";
			tinyTips +="'<b>Họ tên:</b> "+ userBefore.getFullname() +"<br/>' +";
			tinyTips +="'<b>Điện thoại:</b> " + userBefore.getPhone() + "<br/>' +";
			tinyTips +="'<b>Email:</b> "+ userBefore.getEmail() +"<br/>' +";
			tinyTips +="'<b>Phòng:</b> " + userBefore.getMaPhong() + "<br/>' +";
			if(user.getTeam() != null && !user.getTeam().equals(""))
				tinyTips +="'<b>Tổ:</b> " + userBefore.getTeam() + "<br/>' +";
			else
				tinyTips +="'<b>Tổ:</b><br/>'";
			tinyTips +="'<b>Trực thay cho:</b> " + user.getFullname() + "<br/>'";
			tinyTips +=");";
			tinyTips += "});</script>";
		return tinyTips;
	}
	
	// Lay danh sach to theo phong ban
   	@RequestMapping("loadTeam")
   	public @ResponseBody 
   	List<MDepartment> loadTeam(@RequestParam(required = false) String department, HttpServletRequest request, HttpServletResponse response) {
   		List<MDepartment> loadTeamList = mDepartmentDao.loadTeamByDepartment(department.trim());
   		return loadTeamList;
   	}
   	
   	//Lay noi dung
  	public static String getDataBlock(String content, List<AlWorkCalendarShift> alWorkCalendarShiftList){
  		
  		/*//SO COT TREN GRID
		Integer countColumn= 9;
		String columnGroup= null;
		String valueColumnGroup=null;
		int sttGroup=1;*/
  		
  		String valueColumnGroup="";
  		int sttGroup=1;
  		int countType=0;
  		String valuefuntion="";
  		for(int i=0;i<alWorkCalendarShiftList.size();i++){
  			
  		//Nhom du lieu
  			String value =alWorkCalendarShiftList.get(i).getShift();
  			String funtion=alWorkCalendarShiftList.get(i).getFunction();
			if (valueColumnGroup==null||(!valueColumnGroup.equals(value)))
			{
				valueColumnGroup = value;
				content=content + "<tr><td style=\"background-color : #ffecb4; font-weight: bold;\" colspan=\"8\">"+sttGroup+". "+valueColumnGroup+"</td></tr>";
				sttGroup++;	
				countType=0;
				if (funtion!=null && !funtion.equals(""))
				{
					int index= funtion.lastIndexOf(" ");
					if (index>0)
					{
						valuefuntion = funtion.substring(0, index);
					}
					else
					{
						valuefuntion = funtion;
					}
				}
			}
			
			
			if (funtion!=null)
			{
				String valueTemp=funtion;
				int index= funtion.lastIndexOf(" ");
				if (index>0)
				{
					valueTemp = funtion.substring(0, index);
				}
				if (!valuefuntion.equalsIgnoreCase(valueTemp))
				{
					countType++;
					valuefuntion=valueTemp;
				}
			}
			System.out.println(" funtion value:"+valuefuntion);
			String style;
			if (countType%2==0)
			{//
				style="style=\"border: 1px solid #c4cde0;background-color:#e5eefe;\"";
			}
			else
			{
				style="style=\"border: 1px solid #c4cde0\"";
			}
			content += "<tr>";
  			/*content += "<td  align=\"left\" style=\"border: 1px solid #c4cde0\">";
  			if(alWorkCalendarShiftList.get(i).getShift() != null)
  			content += alWorkCalendarShiftList.get(i).getShift();
  	        content += "</td>";*/
  	        content += "<td align=\"left\" width:\"180px\" "+ style+">";
  	        if(alWorkCalendarShiftList.get(i).getFunction() != null)
  	        	content += alWorkCalendarShiftList.get(i).getFunction();
	        content += "</td>";
	        content += "<td align=\"center\" "+ style+">";
	        if(alWorkCalendarShiftList.get(i).getMondayBefore() != null && !alWorkCalendarShiftList.get(i).getMondayBefore().equals(""))
	        	content += "<div style=\"text-decoration:line-through\">" + alWorkCalendarShiftList.get(i).getMonday() + "</div>" + alWorkCalendarShiftList.get(i).getMondayBefore();
	        else if(alWorkCalendarShiftList.get(i).getMonday() != null)
	        	content += alWorkCalendarShiftList.get(i).getMonday();
	        content += "</td>";
	        content += "<td align=\"center\" "+ style+">";
	        if(alWorkCalendarShiftList.get(i).getTuesdayBefore() != null && !alWorkCalendarShiftList.get(i).getTuesdayBefore().equals(""))
	        	content += "<div style=\"text-decoration:line-through\">" + alWorkCalendarShiftList.get(i).getTuesday() + "</div>" + alWorkCalendarShiftList.get(i).getTuesdayBefore();
	        else if(alWorkCalendarShiftList.get(i).getTuesday() != null)
	        	content += alWorkCalendarShiftList.get(i).getTuesday();
	        content += "</td>";
	        content += "<td align=\"center\" "+ style+">";
	        if(alWorkCalendarShiftList.get(i).getWednesdayBefore() != null && !alWorkCalendarShiftList.get(i).getWednesdayBefore().equals(""))
	        	content += "<div style=\"text-decoration:line-through\">" + alWorkCalendarShiftList.get(i).getWednesday() + "</div>" + alWorkCalendarShiftList.get(i).getWednesdayBefore();
	        else if (alWorkCalendarShiftList.get(i).getWednesday() != null)
	        	content += alWorkCalendarShiftList.get(i).getWednesday();
	        content += "</td>";
	        content += "<td align=\"center\" "+ style+">";
	        if(alWorkCalendarShiftList.get(i).getThursdayBefore() != null && !alWorkCalendarShiftList.get(i).getThursdayBefore().equals(""))
	        	content += "<div style=\"text-decoration:line-through\">" + alWorkCalendarShiftList.get(i).getThursday() + "</div>" + alWorkCalendarShiftList.get(i).getThursdayBefore();
	        else if(alWorkCalendarShiftList.get(i).getThursday() != null)
	        	content += alWorkCalendarShiftList.get(i).getThursday();
	        content += "</td>";
	        content += "<td align=\"center\" "+ style+">";
	        if(alWorkCalendarShiftList.get(i).getFridayBefore() != null && !alWorkCalendarShiftList.get(i).getFridayBefore().equals(""))
	        	content += "<div style=\"text-decoration:line-through\">" + alWorkCalendarShiftList.get(i).getFriday() + "</div>" + alWorkCalendarShiftList.get(i).getFridayBefore();
	        else if(alWorkCalendarShiftList.get(i).getFriday() != null)
	        	content += alWorkCalendarShiftList.get(i).getFriday();
	        content += "</td>";
	        content += "<td align=\"center\" "+ style+">";
	        if(alWorkCalendarShiftList.get(i).getSaturdayBefore() != null && !alWorkCalendarShiftList.get(i).getSaturdayBefore().equals(""))
	        	content += "<div style=\"text-decoration:line-through\">" + alWorkCalendarShiftList.get(i).getSaturday() + "</div>" + alWorkCalendarShiftList.get(i).getSaturdayBefore();
	        else if(alWorkCalendarShiftList.get(i).getSaturday() != null)
	        	content += alWorkCalendarShiftList.get(i).getSaturday();
	        content += "</td>";
	        content += "<td align=\"center\" "+ style+">";
	        if(alWorkCalendarShiftList.get(i).getSundayBefore() != null && !alWorkCalendarShiftList.get(i).getSundayBefore().equals(""))
	        	content += "<div style=\"text-decoration:line-through\">" + alWorkCalendarShiftList.get(i).getSunday() + "</div>" + alWorkCalendarShiftList.get(i).getSundayBefore();
	        else if(alWorkCalendarShiftList.get(i).getSunday() != null)
	        	content += alWorkCalendarShiftList.get(i).getSunday();
	        content += "</td>";
  	        content += "</tr>";	
  		}
  		return content;
	}
  	
  	private String getDataBlockTop(String content,
			List<W_WORKING_MANAGEMENTS> workList,String noiDung,String username,Integer week) {

  		String valueColumnGroup="";
  		int sttGroup=1;
  		content=content + "<table>";
  		if (noiDung!=null && !noiDung.equals(""))
  		{
  			content=content + "<tr><td colspan=\"2\">"+noiDung+"</td></tr>";
  		}
  		for(int i=0;i<workList.size();i++){
  			
  		//Nhom du lieu
  			if (username.equalsIgnoreCase(workList.get(i).getNguoiGiaoViec()))
  			{
	  			String value =workList.get(i).getTenCongViec();
	  			
				if (valueColumnGroup==null||(!valueColumnGroup.equals(value)))
				{
					valueColumnGroup = value;
					content=content + "<tr><td style=\" font-weight: bold;\" colspan=\"2\">"+valueColumnGroup+" tuần "+week+"</td></tr>";
					sttGroup++;	
				}
				
	  			/*content += "<td  align=\"left\" style=\"border: 1px solid #c4cde0\">";
	  			if(alWorkCalendarShiftList.get(i).getShift() != null)
	  			content += alWorkCalendarShiftList.get(i).getShift();
	  	        content += "</td>";*/
				if (workList.get(i).getNoiDung()!=null)
				{
					content += "<tr><td style=\" width: 50px;\"></td><td align=\"left\">"+workList.get(i).getNoiDung()+"</td></tr>";
				}
  			}
  		}
  		content=content + "</table>";
  		return content;
	}
   	
   	static {
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			dataSource = (DataSource) ctx.lookup("java:/vmsc6_alarm");
			logger.info("Init Datasource success.");
		} catch (NamingException e) {
			logger.error("Cannot init Datasource: " + e.getMessage());
		}
	}
   	
   	public static void loadSystemConfig() throws SQLException {
		Connection con = null;
		AL_Global.SHIFT_CONFIG.clear();

		try {
			con = dataSource.getConnection();
			Statement st = con.createStatement();
			ResultSet result = st.executeQuery("Select PARAM_NAME, PARAM_VALUE from c_system_configs");
			String key = null;
			String value = null;
			while (result.next()) {
				key = result.getString(1);
				value = result.getString(2);
				if (key != null && value != null) {
					AL_Global.SHIFT_CONFIG.setProperty(key, value);
				}
			}
			result.close();
			st.close();
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}
   	
   	
   	//Xoa
  	@RequestMapping(value = "deleteNoiDung", method = RequestMethod.GET)
  	public String deleteNoiDung(@RequestParam (required = true) String idwork,@RequestParam (required = true) String user,@RequestParam (required = true) String region,
  			HttpServletRequest request, Model model) {
  		String username = SecurityContextHolder.getContext().getAuthentication().getName();
  		try {
  			if (username.equals(user))
  			{
  				working_managementDao.deleteByPrimaryKey(Integer.parseInt(idwork));
  				saveMessageKey(request, "messsage.confirm.deletesuccess");
  			}
  			else
  			{
  				saveMessageKey(request, "messsage.confirm.khongCoQuyenXoa");
  			}
  		}
  		catch (Exception e) {
  			saveMessageKey(request, "messsage.confirm.deletefailure");
  		}
  		model.addAttribute("region", region);
  		return "redirect:list.htm";
  	}
  	
   	
}
