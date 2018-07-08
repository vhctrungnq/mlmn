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
import vo.FbDeptPlaces;
import vo.MDepartment;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.UploadTools;

import dao.AlAlarmWorksDAO;
import dao.AlShiftDAO;
import dao.FbDeptPlacesDAO;
import dao.MDepartmentDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUsersDAO;

@Controller
@RequestMapping("/alarmExtend")
public class AlarmExtendController extends BaseController {

	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired
	private MDepartmentDAO mDepartmentDAO;
	
	@Autowired 
	private AlShiftDAO alShiftDAO;
	
	@Autowired 
	private SysUsersDAO sysUsersDao;
	
	@Autowired
	private FbDeptPlacesDAO  FbdepplaceDAO ;
	
	@Autowired
	private AlAlarmWorksDAO alAlarmWorksDAO;
	
	
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
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
			@RequestParam(required = false) String area,
			@RequestParam(required = false) String teamProcess,
			@RequestParam(required = false) String startTime,
			@RequestParam(required = false) String endTime,
			@RequestParam (required =false) String region,
			Model model, HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		AlShift shift = getCaTrucHT(userLogin.getRegion());
		
		List<SYS_PARAMETER> sysParemeterTitle = alAlarmWorksDAO.titleForm("ALARM_EXTEND",null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		
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
		if (area==null)
		{
			area="";
		}
		else
			area = area.trim();
		if (teamProcess==null)
		{
			teamProcess="";
		}
		else
			teamProcess = teamProcess.trim();
		
		int order = 1;
		String column = "ALARM";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		if (column.equals("THOI_GIAN")) {
			column = "THOI_GIAN";
		}
		 
		Calendar cal = Calendar.getInstance();	
		cal.setTime(new Date());
		
		if (startTime == null || startTime.equals("")||(startTime!=null && !startTime.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", startTime+":00")==false))
		{
			Calendar c= Calendar.getInstance();
			c.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			startTime = df_full.format(c.getTime());
		}
		if (endTime == null || endTime.equals("")||(endTime!=null && !endTime.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", endTime+":00")==false))
		{	
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			
			endTime = df_full.format(cal.getTime());
		}
		
		List<AlAlarmWorks> alarmList = new ArrayList<AlAlarmWorks>();
		try
		{
			alarmList = alAlarmWorksDAO.getWarningList("ALARM_EXTEND","",alarm,"",teamProcess,startTime+":00",endTime+":00",area,"", column, order,region);
		}
		catch (Exception exp)
		{
			alarmList= null;
		}
		model.addAttribute("alarmList", alarmList);
		
		List<String> areaList = FbdepplaceDAO.selectAreaAll();
		model.addAttribute("areaList", areaList);
		
		List<MDepartment> teamList = mDepartmentDAO.getDepartementBySystem();
		model.addAttribute("teamList", teamList);
		model.addAttribute("alarm", alarm);
		model.addAttribute("area", area);
		model.addAttribute("teamProcess",teamProcess);
		model.addAttribute("startTime", startTime);
		model.addAttribute("endTime", endTime);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "AlarmExtend_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		return new ModelAndView("jspalarm/warning/hieuChinhMRList");
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) String id,
			@RequestParam (required = true) String note,
			@RequestParam (required = true) String region,
			HttpServletRequest request, Model model) {
		//Duong dan tra ve
		String path="";
		
		if (note.equals("caTruc"))
		{
			path="redirect:/caTruc/list.htm?warningTp=ALARM_EXTEND&region="+region;
		}
		else if (note.equalsIgnoreCase("filter")) {
			path= "redirect:/caTruc/listFilter.htm?warningTp=ALARM_EXTEND&region="+region;
		}
		else
			path="redirect:list.htm";
		
		//Xoa
		
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
		}else
		{
			checkCaTruc=true;
		}
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
	public String showForm(@RequestParam(required = false) String id,@RequestParam (required = true) String note,@RequestParam (required = true) String region,
			 ModelMap model, HttpServletRequest request) {
		
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SYS_PARAMETER> sysParemeterTitle = alAlarmWorksDAO.titleForm("ALARM_EXTEND","ADD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleForm", sysParemeterTitle.get(0).getValue());
		}
		AlAlarmWorks alarmExtend = new AlAlarmWorks();
		String team="";
		String dept="";
		if (id!=null)
		{
			alarmExtend = alAlarmWorksDAO.selectByPrimaryKey(Integer.parseInt(id));
			model.addAttribute("startTime", alarmExtend.getStartDateStr() );
			model.addAttribute("endTime", alarmExtend.getEndDateStr() );
			if (alarmExtend.getTeamProcess()!=null)
				team=alarmExtend.getTeamProcess().toUpperCase();
			else
				team="";
			dept=alarmExtend.getDept();
			model.addAttribute("userProcess", alarmExtend.getUserProcess());
		}
		else
		{
			String stime="";
			Calendar cal = Calendar.getInstance();	
			cal.setTime(new Date());
			/*cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0)*/;
			stime = df_full.format(cal.getTime());
			
			model.addAttribute("startTime", stime);
			
		}
		model.addAttribute("alarmExtend", alarmExtend);		
		model.addAttribute("team", team);
		model.addAttribute("dept", dept);
		model.addAttribute("note", note);	
		model.addAttribute("region", region);	
		
		
		return "jspalarm/warning/hieuChinhMRForm";
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("alarmExtend") @Valid AlAlarmWorks alarmExtend, BindingResult result,
			@RequestParam(required = false) String userProcess,
			@RequestParam(required = false) String teamProcess,
			@RequestParam(required = false) String area,
			@RequestParam(required = false) String startTime,
			@RequestParam(required = false) String endTime,
			@RequestParam (required = true) String note,
			@RequestParam(required = false) String dept,
			@RequestParam(required = false) String region,
			
			ModelMap model, HttpServletRequest request) throws ParseException {
		
		//Duong dan tra ve
		String path="";
		if (note.equals("caTruc"))
		{
			path="redirect:/caTruc/list.htm?warningTp=ALARM_EXTEND"+"&region="+region;
		}
		else if (note.equalsIgnoreCase("filter")) {
			return "redirect:/caTruc/listFilter.htm?warningTp=ALARM_EXTEND"+"&region="+region;
		}
		else
			path="redirect:list.htm";
		
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		AlShift shift = getCaTrucHT(userLogin.getRegion());
		
		model.addAttribute("alarmExtend", alarmExtend);	
		
		List<SYS_PARAMETER> sysParemeterTitle = alAlarmWorksDAO.titleForm("ALARM_EXTEND","ADD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleForm", sysParemeterTitle.get(0).getValue());
		}
		/*List<MDepartment> teamList = mDepartmentDAO.getUserAlarmList();
		model.addAttribute("teamList", teamList);*/
		
		boolean checkCaTruc= false;
		int shiftID = 1;
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
		model.addAttribute("note", note);
		if (checkCaTruc==false)
		{
			saveMessageKey(request, "cautruc.KhongCoQuyen");
			model.addAttribute("region",region);
			model.addAttribute("team", teamProcess.toUpperCase());
			model.addAttribute("userProcess", userProcess);
			model.addAttribute("dept", dept);
			model.addAttribute("startTime", startTime);	
			model.addAttribute("endTime", endTime);	
			 return "jspalarm/warning/hieuChinhMRForm";
		}
		String error="";
		if (startTime==null || (startTime!=null && (startTime.equals("")||DateTools.isValid("dd/MM/yyyy HH:mm:ss", startTime+":00")==false)))
		{
			model.addAttribute("errorStartTime", "*");
			error = "alarmExtend.errorField";
			
		}
		if (endTime!=null  && !endTime.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", endTime+":00")==false)
		{
			model.addAttribute("errorendTime", "*");
			error = "alarmExtend.errorField";
		}
		System.out.println("Loi......."+result.getFieldError());
		if (result.hasErrors() || startTime.equals("")||!error.equals("")) {
			model.addAttribute("region",region);
			model.addAttribute("team", teamProcess.toUpperCase());
			model.addAttribute("userProcess", userProcess);
			model.addAttribute("dept", dept);
			model.addAttribute("startTime", startTime);	
			model.addAttribute("endTime", endTime);	
			saveMessageKey(request, "alarmExtend.errorField");
			 return "jspalarm/warning/hieuChinhMRForm";
		}
		else
		{
			AlAlarmWorks canhBao= alAlarmWorksDAO.getAlarmByContentArea(alarmExtend.getAlarm(), area,"ALARM_EXTEND",region);
			alarmExtend.setStime((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(startTime+":00"));
			if (endTime!=null && !endTime.equals(""))
			{
				alarmExtend.setEtime((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(endTime+":00"));
			}
			alarmExtend.setTeamProcess(teamProcess);
			alarmExtend.setUserProcess(userProcess);
			alarmExtend.setDept(dept);
			alarmExtend.setShiftId(shiftID);
			alarmExtend.setRegion(region);
			if (endTime!=null && !endTime.equals("") && alarmExtend.getStime().getTime()>= alarmExtend.getEtime().getTime())
			{
				saveMessageKey(request, "cautruc.sosanhNgay");
				model.addAttribute("region",region);
				model.addAttribute("team", teamProcess.toUpperCase());
				model.addAttribute("userProcess", userProcess);
				model.addAttribute("dept", dept);
				model.addAttribute("startTime", startTime);	
				model.addAttribute("endTime", endTime);	
				return "jspalarm/warning/hieuChinhMRForm";
			}
			if (alarmExtend.getId()==null)
			{
				//them moi
				if (canhBao!=null)
				{
					saveMessageKey(request, "alarmExtend.exits");
					model.addAttribute("region",region);
					model.addAttribute("team", teamProcess.toUpperCase());
					model.addAttribute("userProcess", userProcess);
					model.addAttribute("dept", dept);
					model.addAttribute("startTime", startTime);	
					model.addAttribute("endTime", endTime);	
					return "jspalarm/warning/hieuChinhMRForm";
					
				}
				else
				{
					alarmExtend.setCreateDate(new Date());
					alarmExtend.setCreatedBy(username);
					alarmExtend.setWarningType("ALARM_EXTEND");
					alAlarmWorksDAO.insertSelective(alarmExtend);
					saveMessageKey(request, "alarmExtend.insertSucceFull");
				}	
			}
			else
			{
				//sua chua
				if (canhBao==null || (canhBao!=null && canhBao.getId().equals(alarmExtend.getId())))
				{
					alarmExtend.setModifiedBy(username);
					alarmExtend.setModifyDate(new Date());
					alarmExtend.setWarningType("ALARM_EXTEND");
					alAlarmWorksDAO.update(alarmExtend);
					saveMessageKey(request, "alarmExtend.updateSuccelfull");	
				}
				else
				{
					saveMessageKey(request, "alarmExtend.exits");
					model.addAttribute("region",region);
					model.addAttribute("team", teamProcess.toUpperCase());
					model.addAttribute("userProcess", userProcess);
					model.addAttribute("dept", dept);
					model.addAttribute("startTime", startTime);	
					model.addAttribute("endTime", endTime);	
					return "jspalarm/warning/hieuChinhMRForm";
					
				}
			}
		}
		
		model.addAttribute("area", "");
		model.addAttribute("teamProcess","");
		model.addAttribute("startTime", startTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("note", note);	
		return path; 
	}
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(@RequestParam(required = false) String region, Model model) {
		
		List<SYS_PARAMETER> sysParemeterTitle = alAlarmWorksDAO.titleForm("ALARM_EXTEND","UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		}
		model.addAttribute("region", region);
		return "jspalarm/warning/hieuChinhMRUpload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("filePath") MultipartFile filePath,@RequestParam(required = false) String region, Model model, HttpServletRequest request) throws IOException, ParseException {
		
		List<SYS_PARAMETER> sysParemeterTitle = alAlarmWorksDAO.titleForm("ALARM_EXTEND","UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		}
    	String alarm = "";
		if (!filePath.isEmpty()) {

			String[] ten=filePath.getOriginalFilename().split("\\.");
			String fileExtn = ten[ten.length-1];
			/*if (fileExtn.equals("xlsx")||fileExtn.equals("xls"))
			{
				if (fileExtn.equals("xlsx")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsxFile(filePath.getInputStream());
					alarm =importContent(sheetData,model,request);
				}*/
				
				if (fileExtn.equals("xls")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsFile(filePath.getInputStream());
					alarm = importContent(sheetData,region,model,request);
				}
			/*}*/
			else
			{
				 saveMessageKey(request, "cautruc.typeFIle");
			}
		}
		else
		{
			saveMessageKey(request, "cautruc.emptyFile");
		}
		model.addAttribute("region",region);
		
		return "jspalarm/warning/hieuChinhMRUpload";
		
	}
	@SuppressWarnings({ "rawtypes" })
	    private String importContent (List sheetData,String region, Model model,HttpServletRequest request) throws IOException, ParseException {
		List<SYS_PARAMETER> sysParemeterTitle = alAlarmWorksDAO.titleForm("ALARM_EXTEND","UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		}
		model.addAttribute("region",region);
			
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
		if (checkCaTruc==false)
		{
			saveMessageKey(request, "cautruc.KhongCoQuyen");
			 return "jspalarm/warning/hieuChinhMRUpload";
		}
		 List<AlAlarmWorks> paraList= new ArrayList<AlAlarmWorks>();
	   if (sheetData.size() > 0) {
	        	// Kiem tra tieu đề của trang web
	        	List heard= (List)sheetData.get(0);
	        	if (heard.size() != 6) {
	        		saveMessageKey(request, "cautruc.loiCautruc");
	        		return "jspalarm/warning/hieuChinhMRUpload";
	        	}

	        	// Kiêm tra noi dung
	        	if (sheetData.size()>1) 
	        	{
	        		int maxsize=0;
	        		List footer= (List)sheetData.get(sheetData.size()-1);
	        		/*if (footer.size() != 6 && sheetData.size() > 2) {
	        			sheetData.remove(sheetData.size()-1);
	        			maxsize=sheetData.size();*/
	        		while (footer.size() != 6 && sheetData.size() > 2)
	        		{
	        			boolean kt=true;
	        			for (int k=0;k<footer.size();k++)
	        			{
	        				
	        				if (!footer.get(k).toString().trim().equals(""))
	        				{
	        					kt=false;
	        				}
	        			}
	        			if (footer.size() == 6 && kt == false)
	        			{
	        				break;
	        			}
	        			if (footer.size() > 6 && kt == false)
	        			{
	        				boolean kt2=true;
	        				for (int l=6;l<footer.size();l++)
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
			        			maxsize=sheetData.size()-1;
			        			footer= (List)sheetData.get(maxsize);
		        			}
		        			else
		        			{
		        				for (int j=footer.size();j<=5;j++)
		         				{
		        					footer.add("");
		         				}
		        			}
			        			
	        			}	
	        		
	        		}
	    
	        		for (int i = 1; i< sheetData.size(); i++) {
     				boolean kt=true;
     				List data= (List)sheetData.get(i);
     				for (int j=data.size();j<=5;j++)
     				{
     					data.add("");
     				}
     				AlAlarmWorks alarm = new AlAlarmWorks();
     				alarm.setAlarm(data.get(0).toString().trim());
     				alarm.setArea(data.get(2).toString().trim());
     				alarm.setTeamProcess(data.get(1).toString().trim());
     				alarm.setStimeStr(data.get(3).toString().trim());
     				alarm.setEtimeStr(data.get(4).toString().trim());
     				alarm.setResultsProcess(data.get(5).toString().trim());
     				
     				// Kiem tra kieu du lieu dâ thoa man chua
     				if (data.get(0).toString().trim().length()>500||data.get(0).toString().trim().length()<1
     						||data.get(2).toString().trim().length()>50||data.get(2).toString().trim().length()<1
     						||data.get(1).toString().trim().length()>50||data.get(1).toString().trim().length()<1
     						||data.get(3).toString().trim().length()>16||data.get(3).toString().trim().length()<1
     						||data.get(4).toString().trim().length()>16
     						||data.get(5).toString().trim().length()>1000)
     				{
     					paraList.clear();
     					paraList.add(alarm);
     					saveMessageKey(request, "cautruc.loiKichThuoc");
     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
  						model.addAttribute("alarmExtendList",paraList);
  						return "jspalarm/warning/hieuChinhMRUpload";
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
     						paraList.clear();
         					paraList.add(alarm);
     						saveMessageKey(request, "cautruc.sosanhNgay");
         					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
      						model.addAttribute("alarmExtendList",paraList);
      						return "jspalarm/warning/hieuChinhMRUpload";
     					}
     				}
     				catch (Exception exp)
     				{
     					paraList.clear();
     					paraList.add(alarm);
     					saveMessageKey(request, "cautruc.LoiDinhDangNgay");
     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
  						model.addAttribute("alarmExtendList",paraList);
  						return "jspalarm/warning/hieuChinhMRUpload";
     				}
     				String department = data.get(1).toString().trim();
     				if (department!=null && !department.equals(""))
     				{
	     				//List<MDepartment> userList = mDepartmentDAO.checkUserExcuteExits("MODULE_ALARM",data.get(1).toString().trim());
     					List<MDepartment> departmentList = mDepartmentDAO.checkUserAlarmExits(department);
	     				if (departmentList.size()==0)
	     				{
	     					paraList.clear();
	     					paraList.add(alarm);
	     					saveMessageKey(request, "cautruc.KhongTonTaiNguoiDung");
	     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
	  						model.addAttribute("alarmExtendList",paraList);
	  						return "jspalarm/warning/hieuChinhMRUpload";
	     				}
     				}
     				// Kiểm tra khu vuc co ton tai khong
 					List<FbDeptPlaces> alarmTypeList = FbdepplaceDAO.checkKhuVuc(data.get(1).toString().trim(),data.get(2).toString().trim());
     				if (alarmTypeList.size()==0)
     				{
     					paraList.clear();
     					paraList.add(alarm);
     					saveMessageKey(request, "alarmExtend.KhongTonTaiKhuVuc");
     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
  						model.addAttribute("alarmExtendList",paraList);
  						return "jspalarm/warning/hieuChinhMRUpload";
     				}
     				
     				AlAlarmWorks canhBao= alAlarmWorksDAO.getAlarmByContentArea(alarm.getAlarm(), alarm.getArea(),"ALARM_EXTEND",region);
 					if (canhBao== null)
 					{
 						alarm.setStime(start);
 						alarm.setEtime(end);
 						paraList.add(alarm);
 					}
 					else
 					{
 						paraList.clear();
     					paraList.add(alarm);
     					saveMessageKey(request, "alarmExtend.exits");
     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
  						model.addAttribute("alarmExtendList",paraList);
  						return "jspalarm/warning/hieuChinhMRUpload";
 					}
     				
     			}
     			
	        	}
		        else {
		        	
		        	saveMessageKey(request, "cautruc.loiCautruc");
		        	return "jspalarm/warning/hieuChinhMRUpload";
		        }
	        }
		 if (paraList.size()>0)
		 {
			 for (int i=0;i<paraList.size();i++)
			 {
				 paraList.get(i).setCreateDate(new Date());
				 paraList.get(i).setCreatedBy(username);
				 paraList.get(i).setShiftId(shiftID);
				 paraList.get(i).setWarningType("ALARM_EXTEND");
				 alAlarmWorksDAO.insertSelective(paraList.get(i));
			 }
			 
			saveMessageKey(request, "global.importSuccefull");
			model.addAttribute("alarmExtendList",paraList);
		 }
		 else
			saveMessageKey(request, "global.importFail");
		 return "jspalarm/warning/hieuChinhMRUpload";
	 }
}
