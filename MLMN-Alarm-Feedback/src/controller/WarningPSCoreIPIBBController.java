package controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import vo.AlAlarmWorks;
import vo.AlShift;
import vo.MDepartment;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.HelpTableConfigs;
import dao.AlShiftDAO;
import dao.MDepartmentDAO;
import dao.SYS_PARAMETERDAO;
import dao.AlAlarmWorksDAO;
import dao.SysUsersDAO;
import dao.VAlWarningSystemDAO;
import vo.alarm.utils.*;
@Controller
@RequestMapping("/warning")
public class WarningPSCoreIPIBBController extends BaseController {
	@Autowired
	private AlAlarmWorksDAO alAlarmWorksDAO;
	
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired
	private MDepartmentDAO mDepartmentDAO;
	
	@Autowired 
	private VAlWarningSystemDAO vAlWarningSystemDAO;
	
	@Autowired 
	private SysUsersDAO sysUsersDao;
	
	@Autowired 
	private AlShiftDAO alShiftDAO;

	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyy HH:mm");
	private DateFormat df_full1 = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	// Lay id truc ca hien tai
		private AlShift getCaTrucHT(String region)
		{
			List<SYS_PARAMETER> caList = new ArrayList<SYS_PARAMETER>();
			caList = sysParameterDao.getSysParameterByCode("CA_TRUC_VALUE");
			String sang = caList.get(0).getValue();
			String chieu = caList.get(1).getValue();
			String toi = caList.get(2).getValue();
			String caT="";
			String ngayT="";
			AlShift shift = new AlShift();
			Date currentTime = new Date();
			int hour = currentTime.getHours();
			ngayT= df.format(currentTime);
			if (hour >= Integer.parseInt(sang) && hour < Integer.parseInt(chieu)) {
				caT = "Sáng";
			}
			if (hour >= Integer.parseInt(chieu) && hour < Integer.parseInt(toi)) {
				caT = "Chiều";
			}
			if (hour >= Integer.parseInt(toi) || hour < Integer.parseInt(sang)) {
				caT = "Tối";
			}
			// Lay idshift
			 shift = alShiftDAO.getCaTrucGanNhat(region);
			if (shift==null)
			{
				shift= alShiftDAO.getCaTruc(caT,ngayT,region);
			}
			return shift;
		}
		
	@RequestMapping("/list")
	public ModelAndView list(
			@RequestParam(required = false) String alarm,
			@RequestParam(required = false) String actionProcess,
			@RequestParam(required = false) String system,
			@RequestParam(required = false) String stime,
			@RequestParam(required = false) String etime,
			@RequestParam(required = false) String teamProcess,
			@RequestParam(required = true) String warningTp,
			@RequestParam(required = false) String region,
			Model model, HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		AlShift shift = getCaTrucHT(userLogin.getRegion());
		boolean checkCaTruc= false;
		if (shift!=null)
		{
			String[] user = shift.getNhanCaVien().split(",");
			if (shift.getNhanUsername().equals(username)||userLogin.getIsRoleManager().equals("Y"))
			{
				checkCaTruc=true;
			}
			for (String item : user) {
				if (item.equals(username))
				{
					checkCaTruc=true;
					break;
				}
			}
			model.addAttribute("checkCaTruc", checkCaTruc);
		}
		
		if (alarm==null)
		{
			alarm="";
		}
		else
			alarm = alarm.trim();
		if (actionProcess==null)
		{
			actionProcess="";
		}
		else
			actionProcess = actionProcess.trim();
		if (system==null)
		{
			system="";
		}
		else
			system = system.trim();
		if (warningTp== null)
			warningTp="";
		
		List<SYS_PARAMETER> sysParemeterTitle = alAlarmWorksDAO.titleForm(warningTp,null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleWarningList", sysParemeterTitle.get(0).getValue());
		}
		int order = 1;
		String column = "ALARM";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		if (!column.equals("WARNING_TYPEStr")&&!column.equals("THOI_GIAN")&&!column.equals("TEN_PHONG"))
		{
			column = "W."+column;
		}
		if (column.equals("THOI_GIAN")) {
			column = "THOI_GIAN";
		}
		Calendar cal = Calendar.getInstance();	
		cal.setTime(new Date());
		
		if (stime == null || stime.equals("")||(stime!=null && !stime.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", stime+":00")==false))
		{
			Calendar c= Calendar.getInstance();
			c.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			stime = df_full.format(c.getTime());
		}
		if (etime == null || etime.equals("")||(etime!=null && !etime.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", etime+":00")==false))
		{	
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			etime = df_full.format(cal.getTime());
		}
		
		model.addAttribute("alarm", alarm);
		model.addAttribute("system", system);
		model.addAttribute("actionProcess", actionProcess);
		model.addAttribute("teamProcess",teamProcess);
		model.addAttribute("startTime", stime);
		model.addAttribute("endTime", etime);
		model.addAttribute("warningTp", warningTp);
		model.addAttribute("region", region);
		
	 	List<AlAlarmWorks> warningList = new ArrayList<AlAlarmWorks>();
		try
		{
			
			warningList = alAlarmWorksDAO.getWarningList(warningTp,system,alarm,actionProcess,teamProcess,stime+":00",etime+":00","","", column, order,region);
			model.addAttribute("warningList", warningList);
		}
		catch (Exception exp)
		{
			warningList= null;
		}
		List<MDepartment> userList = mDepartmentDAO.getDepartementBySystem();
		model.addAttribute("userList", userList);
		model.addAttribute("teamProcess", teamProcess);
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "AlAlarmWork_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		return new ModelAndView("jspalarm/warning/warningList");
	}
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) String id,
			@RequestParam(required = true) String warningTp,
			@RequestParam (required = true) String note,
			@RequestParam(required = true) String region,
			HttpServletRequest request, Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		AlShift shift = getCaTrucHT(userLogin.getRegion());
		
		boolean checkCaTruc= false;
		if (shift!=null)
		{
			String[] user = shift.getNhanCaVien().split(",");
			if (shift.getNhanUsername().equals(username)||userLogin.getIsRoleManager().equals("Y"))
			{
				checkCaTruc=true;
			}
			for (String item : user) {
				if (item.equals(username))
				{
					checkCaTruc=true;
					break;
				}
			}
			model.addAttribute("checkCaTruc", checkCaTruc);
		}
		else
		{
			checkCaTruc=true;
		}
		//Duong dan tra ve
		String path="";
		if (note.equals("caTruc"))
		{
			path="redirect:/caTruc/list.htm?warningTp="+warningTp+"&region="+region;
		}
		else if (note.equalsIgnoreCase("filter")) {
			path= "redirect:/caTruc/listFilter.htm?warningTp="+warningTp+"&region="+region;
		}
		else
			path="redirect:list.htm";
		
		String stime="";
		String etime="";
		if (warningTp== null)
			warningTp="";
		
		if (checkCaTruc==true)
		{
			try {
				alAlarmWorksDAO.deleteByPrimaryKey(Integer.parseInt(id));
				saveMessageKey(request, "messsage.confirm.deletesuccess");
			}
			catch (Exception e) {
				saveMessageKey(request, "messsage.confirm.deletefailure");
			}
		}
		else
		{
			saveMessageKey(request, "cautruc.deleteFail");
		}
		return path;
	}
	
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id,
			@RequestParam(required = true) String warningTp,
			@RequestParam (required = true) String note,
			@RequestParam(required = true) String region,
			ModelMap model, HttpServletRequest request) {
		
		List<String> mscList = new ArrayList<String>();
	//	List<SYS_PARAMETER> warningTypeList = new ArrayList<SYS_PARAMETER>();
		if (warningTp== null)
			warningTp="";
		List<SYS_PARAMETER> sysParemeterTitle = alAlarmWorksDAO.titleForm(warningTp,"ADD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleWarning", sysParemeterTitle.get(0).getValue());
		}
		if (warningTp==null || warningTp.equals(""))
		{	
			
			mscList = vAlWarningSystemDAO.getSystemByTypeWarning(null);
			
		}
		else
		{
			if (warningTp.equals("SU_CO_LON"))
				mscList = vAlWarningSystemDAO.getSystemByTypeWarning(null);
			else
				mscList = vAlWarningSystemDAO.getSystemByTypeWarning(warningTp);
		}
		
		//String system ="";
		String systemArray="var systemList = new Array(";
		String cn="";
		for (String sys : mscList) {
			systemArray = systemArray + cn +"\""+sys+"\"";
			cn=",";
		}
		systemArray = systemArray+");";
		model.addAttribute("systemList", systemArray);
				
		//danh sach alarm name
		List<SYS_PARAMETER> alarmList = sysParameterDao.getAlamNameList(warningTp);
		//String bscListStr ="";
		String alarmArray="var alarmList = new Array(";
		String cn2="";
		for (SYS_PARAMETER alarm : alarmList) {
			alarmArray = alarmArray + cn2 +"\""+alarm.getValue()+"\"";
			cn2=",";
		}
		alarmArray = alarmArray+");";
		model.addAttribute("alarmList", alarmArray);
				
		AlAlarmWorks warning = new AlAlarmWorks();
		String team="";
		String dept="";
		if (id!=null)
		{
			warning = alAlarmWorksDAO.selectByPrimaryKey(Integer.parseInt(id));
			model.addAttribute("startTime", warning.getStartDateStr() );
			model.addAttribute("endTime", warning.getEndDateStr() );
			team=warning.getTeamProcess();
			dept=warning.getDept();
		}
		else
		{
			String stime="";
			Calendar cal = Calendar.getInstance();	
			cal.setTime(new Date());
			/*cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);*/
			stime = df_full.format(cal.getTime());
			
			model.addAttribute("startTime", stime);	
			
		}
		model.addAttribute("warning", warning);		
		//model.addAttribute("warningTypeList", warningTypeList);
		
		/*List<MDepartment> teamList = mDepartmentDAO.getUserAlarmList();
		model.addAttribute("teamList", teamList);*/
		
		model.addAttribute("team",team);
		model.addAttribute("dept",dept);
		
		/*List<SysUsers> userList = new ArrayList<SysUsers>();
		if (teamList.size()>0)
		{
			userList = sysUsersDao.getUserByMaPhong(teamList.get(0).getDeptCode());
		}
		model.addAttribute("userList", userList);*/
		model.addAttribute("userProcess", warning.getUserProcess());
		
		//model.addAttribute("systemList", mscList);	
		model.addAttribute("system", warning.getSystem());		
		model.addAttribute("warningTp", warningTp);
		model.addAttribute("note", note);
		model.addAttribute("region", region);
		
		return "jspalarm/warning/warningForm";
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("warning") @Valid AlAlarmWorks warning, BindingResult result,
			@RequestParam(required = true) String warningTp,
			@RequestParam(required = false) List<String> userProcess,
			@RequestParam(required = false) String teamProcess,
			@RequestParam(required = false) String stime,
			@RequestParam(required = false) String etime,
			@RequestParam (required = true) String note,
			@RequestParam (required = true) String dept,
			@RequestParam (required = true) String region,
			ModelMap model, HttpServletRequest request) throws ParseException {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		AlShift shift = getCaTrucHT(userLogin.getRegion());
		
		boolean checkCaTruc= false;
		int shiftID=1;
		if (shift!=null)
		{
			String[] user = shift.getNhanCaVien().split(",");
			if (shift.getNhanUsername().equals(username)||userLogin.getIsRoleManager().equals("Y"))
			{
				checkCaTruc=true;
			}
			for (String item : user) {
				if (item.equals(username))
				{
					checkCaTruc=true;
					break;
				}
			}
			model.addAttribute("checkCaTruc", checkCaTruc);
			shiftID = shift.getShiftId();
		}
		else
		{
			checkCaTruc=true;
		}
		
		
		
		if (stime==null || (stime!=null && stime.equals("")))
		{
			model.addAttribute("errorStartTime", "*");
		}
		if (warningTp== null)
			warningTp="";
		String userProc ="";
		if (userProcess!=null && userProcess.size()>0)
		{
			userProc= userProcess.get(0);
			for (int i=1;i<userProcess.size();i++)
			{
				userProc+=","+ userProcess.get(i);
			}
		}
		
		
		List<String> mscList = new ArrayList<String>();
		//List<SYS_PARAMETER> warningTypeList = new ArrayList<SYS_PARAMETER>();
		List<SYS_PARAMETER> sysParemeterTitle = alAlarmWorksDAO.titleForm(warningTp,"ADD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleWarning", sysParemeterTitle.get(0).getValue());
		}
		if (warningTp==null || warningTp.equals(""))
		{	
			
			mscList = vAlWarningSystemDAO.getSystemByTypeWarning(null);
			
		}
		else
		{
			if (warningTp.equals("PS_CORE"))
			{
				
				mscList = vAlWarningSystemDAO.getSystemByTypeWarning("MSC");
			
			}
			if (warningTp.equals("CS_CORE"))
			{
				
				mscList = vAlWarningSystemDAO.getSystemByTypeWarning("SGSN");
			
			}
			if (warningTp.equals("IPBB"))
			{
				
				mscList = vAlWarningSystemDAO.getSystemByTypeWarning("MSC");
				
			}
			if (warningTp.equals("SU_CO_LON"))
			{
			
				mscList = vAlWarningSystemDAO.getSystemByTypeWarning(null);
			}
		}
		//String system ="";
		String systemArray="var systemList = new Array(";
		String cn="";
		for (String sys : mscList) {
			systemArray = systemArray + cn +"\""+sys+"\"";
			cn=",";
		}
		systemArray = systemArray+");";
		//danh sach alarm name
		List<SYS_PARAMETER> alarmList = sysParameterDao.getAlamNameList(warningTp);
		//String bscListStr ="";
		String alarmArray="var alarmList = new Array(";
		String cn2="";
		for (SYS_PARAMETER alarm : alarmList) {
			alarmArray = alarmArray + cn2 +"\""+alarm.getValue()+"\"";
			cn2=",";
		}
		alarmArray = alarmArray+");";
				
		//model.addAttribute("warningTypeList", warningTypeList);
		/*List<MDepartment> teamList = mDepartmentDAO.getUserAlarmList();
		model.addAttribute("teamList", teamList);*/
		String teamv="";
		if (warning!=null)
			teamv = warning.getTeamProcess().toUpperCase();
		
		String system = warning.getSystem();
		boolean kt= true;
		String error="";
		if (stime==null || (stime!=null && (stime.equals("")||DateTools.isValid("dd/MM/yyyy HH:mm:ss", stime+":00")==false)))
		{
			error = "alarmExtend.errorField";
		}
		if (etime!=null  && !etime.equals("")  && DateTools.isValid("dd/MM/yyyy HH:mm:ss", etime+":00")==false)
		{
			error = "alarmExtend.errorField";
			model.addAttribute("errorendTime", "*");
		}
		if (userProc.length()>50)
		{
			error = "alarmExtend.moreUse";
		}
		if (system.length()>50||system.length()<1)
		{
			error = "alarmExtend.errorField";
			model.addAttribute("errorsystem", "*");
		}
		if (etime!=null && !etime.equals("") && warning.getStime().getTime()>= warning.getEtime().getTime())
		{
			error = "cautruc.sosanhNgay";
		}
		if (result.hasErrors() || stime.equals("")|| !error.equals("")) {
			model.addAttribute("errorStartTime", "*");
			model.addAttribute("warning", warning);	
			model.addAttribute("dept", dept);	
			model.addAttribute("team",teamv) ;
			model.addAttribute("note", note);
			model.addAttribute("userProcess", warning.getUserProcess());
			model.addAttribute("systemList", systemArray);
			model.addAttribute("alarmList", alarmArray);
			
			model.addAttribute("region", region);
			model.addAttribute("warningTp", warningTp);
			model.addAttribute("startTime", stime);	
			model.addAttribute("endTime", etime);	
			model.addAttribute("system", warning.getSystem());	
			saveMessageKey(request,error );
			 return "jspalarm/warning/warningForm";
		}
		else
		{
			AlAlarmWorks canhBao= alAlarmWorksDAO.getWarningByAlarm(warning.getAlarm(),warning.getSystem(), warningTp);
			warning.setStime((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(stime+":00"));
			if (etime!=null && !etime.equals(""))
			{
				warning.setEtime((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(etime+":00"));
			}
			warning.setTeamProcess(teamProcess);
			warning.setUserProcess(userProc);
			warning.setWarningType(warningTp);
			warning.setShiftId(shiftID);
			warning.setDept(dept);
			
			if (warning.getId()==null)
			{
				//them moi
				if (canhBao!=null)
				{
					error = "warning.exits";
					model.addAttribute("warning", warning);	
					model.addAttribute("dept", dept);	
					model.addAttribute("team",teamv) ;
					model.addAttribute("note", note);
					model.addAttribute("userProcess", warning.getUserProcess());
					model.addAttribute("systemList", systemArray);
					model.addAttribute("alarmList", alarmArray);
					
					model.addAttribute("region", region);
					model.addAttribute("warningTp", warningTp);
					model.addAttribute("startTime", stime);	
					model.addAttribute("endTime", etime);	
					model.addAttribute("system", warning.getSystem());	
					saveMessageKey(request,error );
					return "jspalarm/warning/warningForm";
					
				}
				else
				{
					warning.setCreateDate(new Date());
					warning.setCreatedBy(username);
				
					//warning.setShiftId(shiftId);
					if (checkCaTruc==true)
					{
						alAlarmWorksDAO.insertSelective(warning);
						saveMessageKey(request, "warning.insertSucceFull");
					}
					else
					{
						saveMessageKey(request, "warning.insertFail");
					}
					
				}
					
			}
			else
			{
				//sua chua
				if (canhBao==null || (canhBao!=null && canhBao.getId().equals(warning.getId())))
				{
					warning.setModifiedBy(username);
					warning.setModifyDate(new Date());
					if (checkCaTruc==true)
					{
						//alAlarmWorksDAO.updateByPrimaryKeySelective(warning);
						alAlarmWorksDAO.update(warning);
						saveMessageKey(request, "warning.updateSuccelfull");	
					}
					else
					{
						saveMessageKey(request, "warning.updateFail");
					}
				}
				else
				{
					error = "warning.exits";
					model.addAttribute("warning", warning);	
					model.addAttribute("dept", dept);	
					model.addAttribute("team",teamv) ;
					model.addAttribute("note", note);
					model.addAttribute("userProcess", warning.getUserProcess());
					model.addAttribute("systemList", systemArray);
					model.addAttribute("alarmList", alarmArray);
					
					model.addAttribute("region", region);
					model.addAttribute("warningTp", warningTp);
					model.addAttribute("startTime", stime);	
					model.addAttribute("endTime", etime);	
					model.addAttribute("system", warning.getSystem());	
					saveMessageKey(request,error );
					
				}
			}
		
		}
		//Duong dan tra ve
		String path="";
		
		if (note.equals("caTruc"))
		{
			path="redirect:/caTruc/list.htm?warningTp="+warningTp+"&region="+region;
		}
		else if (note.equalsIgnoreCase("filter")) {
			path= "redirect:/caTruc/listFilter.htm?warningTp="+warningTp+"&region="+region;
		}
		else
			path="redirect:list.htm";
		return path; 
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(@RequestParam(required = true) String warningTp, Model model) {
		if (warningTp== null)
			warningTp="";
		List<SYS_PARAMETER> sysParemeterTitle = alAlarmWorksDAO.titleForm(warningTp,"UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleWarningUpload", sysParemeterTitle.get(0).getValue());
		}
		model.addAttribute("warningTp", warningTp);
		return "jspalarm/warning/warningUpload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("filePath") MultipartFile filePath,@RequestParam(required = true) String warningTp,@RequestParam(required = false) String region, Model model, HttpServletRequest request) throws IOException, ParseException {
	
		List<SYS_PARAMETER> sysParemeterTitle = alAlarmWorksDAO.titleForm(warningTp,"UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleWarningUpload", sysParemeterTitle.get(0).getValue());
		}
		 model.addAttribute("warningTp", warningTp);
    	String content = "";
		if (!filePath.isEmpty()) {

			String[] ten=filePath.getOriginalFilename().split("\\.");
			String fileExtn = ten[ten.length-1];
			if (fileExtn.equals("xls")) {
				@SuppressWarnings("rawtypes")
				List sheetData = UploadTools.readXlsFile(filePath.getInputStream());
				content = importContent(sheetData,region, warningTp,model,request);
			}
	
			else
			{
				 saveMessageKey(request, "cautruc.typeFIle");
			}
		}
		else
		{
			saveMessageKey(request, "cautruc.emptyFile");
		}
		return "jspalarm/warning/warningUpload";
		
	}
	 @SuppressWarnings({ "rawtypes" })
	    private String importContent (List sheetData ,String region, String warningTp,Model model,HttpServletRequest request) throws IOException, ParseException {
		 List<SYS_PARAMETER> sysParemeterTitle = alAlarmWorksDAO.titleForm(warningTp,"UPLOAD");
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("titleWarningUpload", sysParemeterTitle.get(0).getValue());
			}
			
		 String username = SecurityContextHolder.getContext().getAuthentication().getName();
		 SysUsers userLogin = sysUsersDao.selectByUsename(username);
			AlShift shift = getCaTrucHT(userLogin.getRegion());
			//	A_ALERT_USERS user = alertUsersDao.getUserByUsername(username);
		 	String systemType="";
			
		 	if (warningTp== null)
				warningTp="";
			if (warningTp!=null && !warningTp.equals(""))
			{
			
				if (warningTp.equals("PS_CORE"))
				{
					
					systemType="MSC";
				}
				if (warningTp.equals("IPBB"))
				{
				
					systemType="MSC";
				}
			}
		 model.addAttribute("warningTp", warningTp);
		 model.addAttribute("region", region);
			boolean checkCaTruc= false;
			int shiftID=1;
			if (shift!=null)
			{
				String[] user = shift.getNhanCaVien().split(",");
				if (shift.getNhanUsername().equals(username)||userLogin.getIsRoleManager().equals("Y"))
				{
					checkCaTruc=true;
				}
				for (String item : user) {
					if (item.equals(username))
					{
						checkCaTruc=true;
						break;
					}
				}
				model.addAttribute("checkCaTruc", checkCaTruc);
				shiftID = shift.getShiftId();
			}
			else
			{
				checkCaTruc=true;
			}
			if (checkCaTruc==false)
			{
				saveMessageKey(request, "cautruc.Uploadfail");
				return "jspalarm/warning/warningUpload";
			}
		 List<AlAlarmWorks> paraList= new ArrayList<AlAlarmWorks>();
	     List<AlAlarmWorks> errorlist= new ArrayList<AlAlarmWorks>();
	   if (sheetData.size() > 0) {
	        	// Kiem tra tieu đề của trang web
	        	List heard= (List)sheetData.get(0);
	        	if (heard.size() != 8) {
	        		saveMessageKey(request, "cautruc.loiCautruc");
	        		return "jspalarm/warning/warningUpload";
	        	}

	        	// Kiêm tra noi dung
	        	if (sheetData.size()>1) 
	        	{
	        		int maxsize=0;
	        		List footer= (List)sheetData.get(sheetData.size()-1);
	        		/*if (footer.size() != 9 && sheetData.size() > 2) {
	        			sheetData.remove(sheetData.size()-1);
	        			maxsize=sheetData.size();*/
	        		while (footer.size() != 8 && sheetData.size() > 2)
	        		{
	        			System.out.println(footer.size());
	        			boolean kt=true;
	        			for (int k=0;k<footer.size();k++)
	        			{
	        				if (!footer.get(k).toString().trim().equals(""))
	        				{
	        					kt=false;
	        				}
	        			}
	        			if (footer.size() == 8 && kt == false)
	        			{
	        				break;
	        			}
	        			if (footer.size() > 8 && kt == false)
	        			{
	        				boolean kt2=true;
	        				for (int l=8;l<footer.size();l++)
	        					if (!footer.get(l).toString().trim().equals(""))
		        				{
		        					kt2=false;
		        					break;
		        				}
	        				if (kt2==false)
	        				{
		        				model.addAttribute("errorContent","Import không thành công. Cấu trúc dữ liệu trong file không đúng định dạng cho phép");
		  						return "jspalarm/warning/hieuChinhMRUpload";
	        				}
	        				else
	        				{
	        					break;
	        				}
	        			}
	        			else
	        			{
		        			if (kt==true)
		        			{
			        			sheetData.remove(sheetData.size()-1);
			        			maxsize=sheetData.size();
		        			}
		        			else
		        			{
		        				for (int j=footer.size();j<=7;j++)
		         				{
		        					footer.add("");
		         				}
		        			}
			        			footer= (List)sheetData.get(sheetData.size()-1);
	        			}
	        		}
	    
     			for (int i = 1; i< sheetData.size(); i++) {
     				boolean kt=true;
     				List data= (List)sheetData.get(i);
     				for (int j=data.size();j<=7;j++)
     				{
     					data.add("");
     				}
     				
     				AlAlarmWorks warning = new AlAlarmWorks();
 					warning.setAlarm(data.get(0).toString().trim());
 					warning.setSystem(data.get(1).toString().trim());
 					warning.setAlarmInfo(data.get(2).toString().trim());
 					warning.setStimeStr(data.get(3).toString().trim());
 					warning.setEtimeStr(data.get(4).toString().trim());
 					warning.setCauseby(data.get(5).toString().trim());
 					warning.setActionProcess(data.get(6).toString().trim());
 					warning.setTeamProcess(data.get(7).toString().trim());
 					
     				// Kiem tra kieu du lieu dâ thoa man chua
     				if (data.get(0).toString().trim().length()>255||data.get(0).toString().trim().length()<1
     						||data.get(1).toString().trim().length()>50||data.get(1).toString().trim().length()<1
     						||data.get(2).toString().trim().length()>1000
     						||data.get(3).toString().trim().length()>16||data.get(3).toString().trim().length()<1
     						||data.get(4).toString().trim().length()>16
     						||data.get(5).toString().trim().length()>255
     						||data.get(6).toString().trim().length()>1000
     						||data.get(7).toString().trim().length()>50)
     				{
     					errorlist.clear();
     					errorlist.add(warning);
     					saveMessageKey(request, "cautruc.loiKichThuoc");
     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
  						model.addAttribute("warningList",errorlist);
  						return "jspalarm/warning/warningUpload";
     				}
     				
     				Date start= null;
     				Date end= null;
     				try
     				{
     					start = (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(data.get(3).toString().trim()+":00");
     					if (data.get(4).toString().trim()!=null && !data.get(4).toString().trim().equals(""))
     					{
     						end  = (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(data.get(4).toString().trim()+":00");
     					}
     					if (end!=null && start.getTime()>=end.getTime())
     					{
     						errorlist.clear();
         					errorlist.add(warning);
     						saveMessageKey(request, "cautruc.sosanhNgay");
         					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
      						model.addAttribute("warningList",errorlist);
      						return "jspalarm/warning/warningUpload";
     					}
     				}
     				catch (Exception exp)
     				{
     					errorlist.clear();
     					errorlist.add(warning);
     					saveMessageKey(request, "cautruc.LoiDinhDangNgay");
     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
  						model.addAttribute("warningList",errorlist);
  						return "jspalarm/warning/warningUpload";
     				}
     				// Kiểm tra thiết bị, nguười xử lý upload vào có tồn tại không
     				/*String[] systemList = data.get(1).toString().split(",");
     				for (int l=0;l<systemList.length;l++)
     				{
	     				List<String> mscList = vAlWarningSystemDAO.checkSystemExits(systemList[l].trim(),systemType);
	     				if (mscList.size()==0)
	     				{
	     					errorlist.clear();
	     					errorlist.add(warning);
	     					saveMessageKey(request, "cautruc.KhongTonTaiThietBi");
	     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
	  						model.addAttribute("warningList",errorlist);
	  						return "jspalarm/warning/warningUpload";
	     				}
     				}*/
     				String department = data.get(7).toString().trim();
     				if (department!=null && !department.equals(""))
     				{
	     				/*List<MDepartment> userList = mDepartmentDAO.checkUserExcuteExits("MODULE_ALARM",data.get(7).toString().trim());*/
     					List<MDepartment> departmentList = mDepartmentDAO.checkUserAlarmExits(department);
	     				if (departmentList.size()==0)
	     				{
	     					errorlist.clear();
	     					errorlist.add(warning);
	     					saveMessageKey(request, "cautruc.KhongTonTaiNguoiDung");
	     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
	  						model.addAttribute("warningList",errorlist);
	  						return "jspalarm/warning/warningUpload";
	     				}
     				}
     				
     				//Khởi tạo một cảnh báo mới
     				AlAlarmWorks alarm = new AlAlarmWorks();
     				alarm.setAlarm(data.get(0).toString().trim());
     				if (warningTp.equals("PS_CORE"))
     				{
     					alarm.setWarningType("PS_CORE");
     				}
     				if (warningTp.equals("IPBB"))
     				{
     					alarm.setWarningType("IPBB");
     				}
     				if (warningTp.equals("SU_CO_LON"))
     				{
     					alarm.setWarningType("SU_CO_LON");
     				}
     				alarm.setSystem(data.get(1).toString().trim());
     				alarm.setAlarmInfo(data.get(2).toString().trim());
     				alarm.setStime(start);
     				alarm.setEtime(end);
     				alarm.setCauseby(data.get(5).toString().trim());
     				alarm.setActionProcess(data.get(6).toString().trim());
 					alarm.setTeamProcess(data.get(7).toString().trim());
 					
 					AlAlarmWorks canhBao= alAlarmWorksDAO.getWarningByAlarm(alarm.getAlarm(),alarm.getSystem(), alarm.getWarningType());
 					if (canhBao== null)
 					{
 						paraList.add(alarm);
 						errorlist.add(warning);
 					}
 					else
 					{
 						errorlist.clear();
     					errorlist.add(warning);
     					saveMessageKey(request, "warning.exits");
     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
  						model.addAttribute("warningList",errorlist);
  						return "jspalarm/warning/warningUpload";
 					}
     				
     			}
     			
	        	}
		        else {
		        	
		        	saveMessageKey(request, "cautruc.loiCautruc");
		        	return "jspalarm/warning/warningUpload";
		        }
	        }
		 if (paraList.size()>0)
		 {
			 for (int i=0;i<paraList.size();i++)
			 {
				 AlAlarmWorks canhBao= alAlarmWorksDAO.getWarningByAlarm(paraList.get(i).getAlarm(),paraList.get(i).getSystem(), paraList.get(i).getWarningType());
				 if (canhBao==null)
				 {
					 paraList.get(i).setCreateDate(new Date());
					 paraList.get(i).setCreatedBy(username);
					 paraList.get(i).setShiftId(shiftID);
					 alAlarmWorksDAO.insertSelective(paraList.get(i));
				 }
				 else
				 {
					 paraList.get(i).setModifyDate(new Date());
					 paraList.get(i).setModifiedBy(username);
					 alAlarmWorksDAO.updateByPrimaryKeySelective(paraList.get(i));
					 errorlist.remove(i);
				 }
			 }
			 
			saveMessageKey(request, "global.importSuccefull");
			model.addAttribute("warningList",errorlist);
		 }
		 else
			saveMessageKey(request, "global.importFail");
		 return "jspalarm/warning/warningUpload";
	 }
}
