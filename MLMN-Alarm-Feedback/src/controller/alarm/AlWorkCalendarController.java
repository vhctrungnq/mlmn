package controller.alarm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import controller.BaseController;

import vo.AlShift;
import vo.AlWorkCalendar;
import vo.CHighlightConfigs;
import vo.CTableConfig;
import vo.MDepartment;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;
import vo.alarm.utils.UploadTools;
import vo.dictionary.TableConfigsHelper;

import dao.AlShiftDAO;
import dao.AlWorkCalendarDAO;
import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.MDepartmentDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUserRolesDAO;
import dao.SysUsersDAO;

/**
 * Function        : Dinh nghia lich truc ca
 * Created By      : BUIQUANG
 * Create Date     : 30/11/2013
 * Modified By     : 
 * Modify Date     : 
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/alarm/al-work-calendar/*")
public class AlWorkCalendarController extends BaseController{

	@Autowired
	private AlWorkCalendarDAO alWorkCalendarDAO;
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
	private SysUsersDAO sysUsersDao;
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	@Autowired 
	private AlShiftDAO alShiftDAO;
	@Autowired
	private MDepartmentDAO mDepartmentDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	@ModelAttribute("highlight")
	public String highlight() {
		String highlight = "";
		List<CHighlightConfigs> highlightConfigList2 = cHighlightConfigsDAO.getByKey("NOT_NULL");
		if (highlightConfigList2.size()>0)
		{ 
			highlight = " $(this).find('.NOT_NULL').css({"+highlightConfigList2.get(0).getStyle()+"});";
		}
		return highlight;
	}
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
		
	
	@RequestMapping(value="list")
    public ModelAndView list(
    		@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate, 	   
			@RequestParam(required = false) String shift,
			@RequestParam(required = false) String deptCode,
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String region,
    		@ModelAttribute("filter") AlWorkCalendar filter, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		model.addAttribute("isRoleManager", userLogin.getIsRoleManager());
		AlShift shiftCK = getCaTrucHT(region);
		boolean checkCaTruc = false;
		if ((shiftCK!=null && (shiftCK.getNhanUsername().equals(username)||shiftCK.getNhanCaVien().contains(username)))||userLogin.getIsRoleManager().equals("Y") )
		{
			checkCaTruc = true;
			model.addAttribute("checkCaTruc", checkCaTruc);
		}
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		// Ngay thang
		if (startDate == null || startDate.equals("")|| DateTools.isValid("dd/MM/yyyy", startDate)==false) {			
			startDate = df.format(cal.getTime());
		}
		if (endDate == null || endDate.equals("")|| DateTools.isValid("dd/MM/yyyy", endDate)==false)
		{
			cal.add(Calendar.DAY_OF_MONTH, 6);
			endDate = df.format(cal.getTime());
		}
		
		String filterUrl = "";
		String temp = "";
		if(startDate != null){
			filterUrl += "startDate=" + startDate.trim(); 
			temp = "&";
		}
		if(endDate != null){
			filterUrl += temp + "endDate=" + endDate.trim(); 
			temp = "&";
		}
		if(shift != null){
			filterUrl += temp + "shift=" + shift.trim(); 
			temp = "&";
		}
		if(deptCode != null){
			filterUrl += temp + "deptCode=" + deptCode.trim(); 
			temp = "&";
		}
		if(email != null){
			filterUrl += temp + "email=" + email.trim(); 
		}
		if(region != null){
			filterUrl += temp + "region=" + region.trim(); 
		}
		if(filterUrl != "")
			filterUrl = "?" + filterUrl;
		loadData(model);
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("AL_WORK_CALENDAR");
		//Lay du lieu cac cot an hien cua grid
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList("AL_WORK_CALENDAR");
		String url = "data.htm" + filterUrl;
		//Grid
		String gridManage = TableConfigsHelper.getGridAddAndPaging(configList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", "Yes", null, "multiplerowsextended", null);
		model.addAttribute("gridManage", gridManage);
		
		//lay danh sach khu vuc
		List<SYS_PARAMETER> regionList = sysParameterDao.getRegionList();
		model.addAttribute("regionList", regionList);
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("shift", shift);
		model.addAttribute("deptCode", deptCode);
		model.addAttribute("email", email);
		model.addAttribute("region", region);
		return new ModelAndView("jspalarm/diary/alWorkCalendarList");
	}
	
	@RequestMapping("/data")
	public void data(@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate, 	   
			@RequestParam(required = false) String shift,
			@RequestParam(required = false) String deptCode,
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String region,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<AlWorkCalendar> alWorkCalendar = null;
		int startRecord = 0, endRecord = 0, pageSize = 100;
		String sortfield = "";
		String sortorder = "";
		int pageNum = Integer.parseInt(request.getParameter("pagenum"));
		if(pageNum == -1)
			pageNum = 1;
		if(!request.getParameter("pagesize").equals(""))
			 pageSize = Integer.parseInt(request.getParameter("pagesize"));
		else pageSize = 100;
		sortfield = request.getParameter("sortdatafield");
		if(request.getParameter("sortorder") != null)
			sortorder = request.getParameter("sortorder");
		List<CTableConfig> columnList = null;
		if(sortfield != null && !sortfield.equals(""))
			columnList = cTableConfigDAO.getTableConfigGet("AL_WORK_CALENDAR", sortfield);
		List<CTableConfig> tableConfigList = cTableConfigDAO.getTableConfigGet("AL_WORK_CALENDAR", null);
		
		// Tim kiem tren grid
		String strWhere = HelpTableConfigs.filter(request);
		for(CTableConfig column: tableConfigList)
		{
			strWhere = strWhere.toUpperCase().replaceAll(column.getDataField().toUpperCase(), column.getTableColumn());
		}
		
		// Sap xep
		if(columnList != null)
			for(CTableConfig column: columnList)
			{
				sortfield = column.getTableColumn();
				break;
			}
		startRecord = pageNum*pageSize;
		endRecord = startRecord + pageSize;
		int totalRow =0;
		try
		{
			alWorkCalendar = alWorkCalendarDAO.getAlWorkCalendarFilter(startDate, endDate, shift, deptCode, email,region, startRecord, endRecord, sortfield, sortorder, strWhere);
			totalRow = alWorkCalendarDAO.countAlWorkCalendarFilter(startDate, endDate, shift, deptCode, email,region, strWhere);
		}
		catch(Exception exp)
		{
		}
		String strjson = "[{\"TotalRows\":\""+totalRow+"\"},";
		strWhere = strWhere.replace("%", "___");
		strjson += "{\"strWhere\":\""+strWhere+"\"},";
		strjson += "{\"sortfield\":\""+sortfield+"\"},";
		strjson += "{\"sortorder\":\""+sortorder+"\"},";
	    strjson += "{\"Rows\":" ;
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(alWorkCalendar);
		strjson+= jsonCartList;
	    strjson += "}]";
	    
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(strjson);
		out.close();
	}
	
	private void loadData(ModelMap model){
		List<SYS_PARAMETER> caTrucList= alWorkCalendarDAO.getCaTrucList();
		model.addAttribute("shiftList", caTrucList);
		
		List<MDepartment> deptCodeList = mDepartmentDao.getDepartmentForShiftList("1,2");
		model.addAttribute("deptCodeList", deptCodeList);
		
		// Danh sach gợi nhớ email
		List<AlWorkCalendar>  emailUsersList = alWorkCalendarDAO.getEmailListFromUsers();
		String emailUsersArray="var emailUsersList = new Array(";
		String cn="";
		for (int i=0;i<emailUsersList.size();i++) {
			emailUsersArray = emailUsersArray + cn +"\""+emailUsersList.get(i).getEmail()+"\"";
			cn=",";
		}
		emailUsersArray = emailUsersArray+");";
		model.addAttribute("emailUsersList", emailUsersArray);
	}
	
	
	
	//FB Form
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id,
			@RequestParam(required = false) String startDate, 
			@RequestParam(required = false) String endDate,@RequestParam(required = false) String region, 
			@RequestParam(required = false) String day, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		AlWorkCalendar alWorkCalendar = (id == null) ? new AlWorkCalendar() : alWorkCalendarDAO.selectByPrimaryKey(Integer.parseInt(id));
		
		alWorkCalendarAddEdit( id, model);
		loadData(model);
		if(id != null && id != ""){
			if(alWorkCalendar.getDay() != null)
				model.addAttribute("day", df.format(alWorkCalendar.getDay()));
			model.addAttribute("shiftCBB", alWorkCalendar.getShift());
			model.addAttribute("functionCBB", alWorkCalendar.getFunction());
			model.addAttribute("region", alWorkCalendar.getRegion());
		}
		else{
			alWorkCalendar.setRegion(region);
			model.addAttribute("day", df.format(new Date()));
			model.addAttribute("region", region);
		}
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("alWorkCalendar", alWorkCalendar);
		
		return "jspalarm/diary/alWorkCalendarForm";
	}
	
	private void alWorkCalendarAddEdit(String id, ModelMap model)
	{
		if(id != null && !id.equals(""))
			model.addAttribute("alWorkCalendarAddEdit", "Y");
		else
			model.addAttribute("alWorkCalendarAddEdit", "N");
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id,
			@RequestParam(required = false) String day,
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate,
			@RequestParam(required = false) String function,
			@RequestParam(required = false) String region,
			@ModelAttribute("alWorkCalendar") @Valid AlWorkCalendar alWorkCalendar, BindingResult result, 
			ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		//Ném lỗi
		if (result.hasErrors()) {
			loadData(model);
			alWorkCalendarAddEdit( id, model);
			if(result.hasFieldErrors("day"))
				model.addAttribute("dayError", "dayError");
			
			model.addAttribute("day", day);
			model.addAttribute("shiftCBB", alWorkCalendar.getShift());
			model.addAttribute("functionCBB", alWorkCalendar.getFunction());
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("region", alWorkCalendar.getRegion());
			return "jspalarm/diary/alWorkCalendarForm";
		}
		
		if (day == null || day.equals("") || DateTools.isValid("dd/MM/yyyy", day)==false)
		{
			loadData(model);
			alWorkCalendarAddEdit( id, model);
			model.addAttribute("day", day);
			model.addAttribute("shiftCBB", alWorkCalendar.getShift());
			model.addAttribute("functionCBB", alWorkCalendar.getFunction());
			model.addAttribute("errorDay", "Bạn chưa nhập ngày");
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("region", alWorkCalendar.getRegion());
			return "jspalarm/diary/alWorkCalendarForm";
		}
		
		/*if (sysUsersDao.checkEmailOfShift(alWorkCalendar.getEmail()).size() == 0){
			loadData(model);
			alWorkCalendarAddEdit( id, model);
			model.addAttribute("day", day);
			model.addAttribute("shiftCBB", alWorkCalendar.getShift());
			model.addAttribute("functionCBB", alWorkCalendar.getFunction());
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("region", alWorkCalendar.getRegion());
			saveMessageKey(request, "messsage.confirm.alWorkCalendar.emailExists");
			return "jspalarm/diary/alWorkCalendarForm";
		}

		if (alWorkCalendar.getEmailBefore() != null && !alWorkCalendar.getEmailBefore().equals("") && sysUsersDao.checkEmailOfShift(alWorkCalendar.getEmailBefore()).size() == 0){
			loadData(model);
			alWorkCalendarAddEdit( id, model);
			model.addAttribute("day", day);
			model.addAttribute("shiftCBB", alWorkCalendar.getShift());
			model.addAttribute("functionCBB", alWorkCalendar.getFunction());
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("region", alWorkCalendar.getRegion());
			saveMessageKey(request, "messsage.confirm.alWorkCalendar.emailBeforeExists");
			return "jspalarm/diary/alWorkCalendarForm";
		}*/
		
		// Kiem tra email co duoi vms.com.vn k? Neu k thi them duoi vao
		if(alWorkCalendar.getEmail() != null && alWorkCalendar.getEmail().toUpperCase().indexOf("@") < 0)
			alWorkCalendar.setEmail(alWorkCalendar.getEmail() + "@mobifone.vn");
		if(alWorkCalendar.getEmailBefore() != null && !alWorkCalendar.getEmailBefore().equals("") && alWorkCalendar.getEmailBefore().toUpperCase().indexOf("@") < 0)
			alWorkCalendar.setEmailBefore(alWorkCalendar.getEmailBefore() + "@mobifone.vn");
		
		if(id.equals(""))
		{
			if(alWorkCalendarDAO.checkCalendarPrimaryKey(alWorkCalendar.getEmail(), day, alWorkCalendar.getShift(), alWorkCalendar.getFunction(), null,alWorkCalendar.getRegion()).size() == 0){
				alWorkCalendar.setCreatedBy(username);
				alWorkCalendar.setRegion(region);
				alWorkCalendarDAO.insert(alWorkCalendar);
				saveMessageKey(request, "messsage.confirm.addsuccess");
			}
			else{
				loadData(model);
				alWorkCalendarAddEdit( id, model);
				model.addAttribute("day", day);
				model.addAttribute("shiftCBB", alWorkCalendar.getShift());
				model.addAttribute("functionCBB", alWorkCalendar.getFunction());
				model.addAttribute("startDate", startDate);
				model.addAttribute("endDate", endDate);
				model.addAttribute("region", alWorkCalendar.getRegion());
				saveMessageKey(request, "messsage.confirm.alWorkCalendar.checkUniqueKey");
				return "jspalarm/diary/alWorkCalendarForm";
			}
		}
		else{
			if(alWorkCalendarDAO.checkCalendarPrimaryKey(alWorkCalendar.getEmail(), day, alWorkCalendar.getShift(), alWorkCalendar.getFunction(), id,alWorkCalendar.getRegion()).size() == 0){
				alWorkCalendar.setModifiedBy(username);
				alWorkCalendar.setModifyDate(new Date());
				
				alWorkCalendarDAO.updateByPrimaryKeySelective(alWorkCalendar);
				saveMessageKey(request, "messsage.confirm.updatesuccess");
			}
			else{
				loadData(model);
				alWorkCalendarAddEdit( id, model);
				model.addAttribute("day", day);
				model.addAttribute("shiftCBB", alWorkCalendar.getShift());
				model.addAttribute("functionCBB", alWorkCalendar.getFunction());
				model.addAttribute("startDate", startDate);
				model.addAttribute("endDate", endDate);
				model.addAttribute("region", alWorkCalendar.getRegion());
				saveMessageKey(request, "messsage.confirm.alWorkCalendar.checkUniqueKey");
				return "jspalarm/diary/alWorkCalendarForm";
			}
		}
		if(startDate != null && !startDate.equals("") && endDate != null && !endDate.equals("")){
			return "redirect:list.htm?startDate="+startDate+"&endDate="+endDate+"&region="+alWorkCalendar.getRegion();
		}	
		else
			return "redirect:list.htm?region="+alWorkCalendar.getRegion();
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) String idList, 
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate, 	 
			@RequestParam(required = false) String region,
			HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
		
			String[] idArray = idList.split(",");
			for (String id : idArray) {
				alWorkCalendarDAO.deleteByPrimaryKey(Integer.parseInt(id));
			}
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch(Exception e)
		{
			saveMessageKey(request, "message.confirm.deleteOther");
			}
		
		//return "redirect:list.htm";
		return "redirect:list.htm?startDate="+startDate+"&endDate="+endDate+"&region="+region;
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate,
			@RequestParam(required = false) String region,
			ModelMap model, HttpServletRequest request, HttpServletResponse response
			) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("region", region);
		return "jspalarm/diary/alWorkCalendarUpload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate,
			@RequestParam(required = false) String region,
			@RequestParam("file") MultipartFile filePath,  ModelMap model, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("region", region);
		if (!filePath.isEmpty()) {

			String[] ten=filePath.getOriginalFilename().split("\\.");
			String fileExtn = ten[ten.length-1];
			
			if (fileExtn.equals("xls")) {
				if (fileExtn.equals("xls")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsFile2(filePath.getInputStream());
					
					importContent(sheetData,region, model, request);
					
				}
			}
			else {
				 saveMessageKey(request, "cautruc.typeFIle");
			}
		}
		else {
			saveMessageKey(request, "cautruc.emptyFile");
		}
		
		return "jspalarm/diary/alWorkCalendarUpload";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent(List sheetData, String region, ModelMap model,HttpServletRequest request) {
		String createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<AlWorkCalendar> successList = new ArrayList<AlWorkCalendar>();
		List<AlWorkCalendar> failedList = new ArrayList<AlWorkCalendar>();
		List<AlWorkCalendar> success = new ArrayList<AlWorkCalendar>();
		
		String email;
		String day;
		String shift;
		String function;
		String emailBefore;
		String description;
		String regionI;
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 7 && heard.size() != 13) {
        		saveMessageKey(request, "sidebar.admin.alWorkCalendarUploadErrorStructuresNumber");
        		
        		return "jspalarm/diary/alWorkCalendarUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		AlWorkCalendar alWorkCalendar;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			alWorkCalendar = new AlWorkCalendar();
        			
        			List data= (List) sheetData.get(i);
        			
        			if(heard.size() == 7){
	        			for (int j=data.size(); j<=7; j++) {
	     					data.add("");
	     				}
	        			regionI 				= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
	        			email				= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
	        			day					= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
	        			shift				= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
	        			function			= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
	        			emailBefore			= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
	        			description			= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
	        			
        			}
        			else{
        				for (int j=data.size(); j<=13; j++) {
	     					data.add("");
	     				}
	        			email				= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
	        			day					= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
	        			shift				= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
	        			function			= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
	        			emailBefore			= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
	        			description			= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim();
	        			regionI 				= data.get(13).toString().trim().equals("")?null:data.get(13).toString().trim();
	        			
        			}
        			
        			// Kiem tra loi
        			if (email == null || day == null || shift == null|| regionI == null
        					//|| (email != null && email.length() > 100)
        					//|| (shift != null && shift.length() > 100)
        					//|| (function != null && function.length() > 100)
        					//|| (description != null && description.length() > 250)
        					/*|| (email != null && sysUsersDao.checkEmailOfShift(email).size() == 0)
        					|| (emailBefore != null && sysUsersDao.checkEmailOfShift(emailBefore).size() == 0)*/
        					|| (regionI != null && (sysParameterDao.checkRegionExit(regionI) == false||!regionI.equalsIgnoreCase(region)) )
						) {
						error = true;
					}
        			
        			try
	    			{
        				if(day != null){
        					alWorkCalendar.setDay(new SimpleDateFormat("dd/MM/yyyy").parse(day));
	    				}
	    			}
	    			catch(Exception e)
	    			{
	    				error = true;
	    			}
        			
        			//---------------------------------------------------------------------------
        			alWorkCalendar.setEmail(email);
        			alWorkCalendar.setShift(shift);
        			alWorkCalendar.setFunction(function);
        			alWorkCalendar.setDescription(description);
        			alWorkCalendar.setEmailBefore(emailBefore);
        			alWorkCalendar.setRegion(regionI);
        			if (email == null && day == null && shift == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(alWorkCalendar);
    					} else  {
    						
    						successList.add(alWorkCalendar);
    					}
        			}
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (AlWorkCalendar record: successList) {
			try {
					
					// Kiem tra email co duoi vms.com.vn k? Neu k thi them duoi vao
					if(record.getEmail() != null && !record.getEmail().equals("") && record.getEmail().toUpperCase().indexOf("@") < 0)
						record.setEmail(record.getEmail() + "@mobifone.vn");
					if(record.getEmailBefore() != null && !record.getEmailBefore().equals("") && record.getEmailBefore().toUpperCase().indexOf("@") < 0)
						record.setEmailBefore(record.getEmailBefore() + "@mobifone.vn");
					List<AlWorkCalendar> checkRecord = alWorkCalendarDAO.checkCalendarPrimaryKey(record.getEmail(), df.format(record.getDay()), record.getShift(), record.getFunction(), null,record.getRegion());
					if(checkRecord.size() == 0){
						record.setCreatedBy(createdBy);
						alWorkCalendarDAO.insert(record);
					}else{
						record.setModifiedBy(createdBy);
						record.setId(checkRecord.get(0).getId());
						alWorkCalendarDAO.updateByPrimaryKey(record);
					}
					success.add(record);
			} catch (Exception ex) {
					failedList.add(record);
			}
		}
		
		if (failedList.size() == 0 && successList.size() > 0) {
			model.addAttribute("status", "Upload thành công.");				// Upload thành công
		} else if (failedList.size() == 0 && successList.size() == 0) {
			model.addAttribute("status", "Không có dữ liệu.");				// Không có dữ liệu
		} else {
			model.addAttribute("status", "Dữ liệu lịch trực ca không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép, định dạng dữ liệu không chính xác hoặc khu vực không hợp lệ");	// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", success.size());
		model.addAttribute("totalNum", failedList.size()+success.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		return "jspalarm/diary/alWorkCalendarUpload";
	}
	
	@RequestMapping("exportData")
  	public ModelAndView exportData(
  			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate, 	   
			@RequestParam(required = false) String shift,
			@RequestParam(required = false) String deptCode,
			@RequestParam(required = false) String email, 
			@RequestParam(required = false) String region, 
  			@RequestParam(required = false) String sortfield,
  			@RequestParam(required = false) String sortorder,
  			@RequestParam(required = false) String strWhere,
  			HttpServletRequest request, HttpServletResponse response) throws Exception {
  		strWhere = strWhere.replace("___", "%");
  		
  		// temp file
		String basePath = request.getSession().getServletContext().getRealPath("");
		String dataDir = basePath + "/upload";
		String tempName = UUID.randomUUID().toString();
		String ext = "xls";
		String outfile = tempName + "." + ext;
		File tempFile = new File(dataDir + "/" + outfile);
		
		List<AlWorkCalendar> alWorkCalendar = alWorkCalendarDAO.getAlWorkCalendarFilter(startDate, endDate, shift, deptCode, email, region, null, null, sortfield, sortorder, strWhere);
		exportReport(tempFile, alWorkCalendar);
		
		// return
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String filename = "AlWorkCalendar_"+ dateNow;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();
  		return null;
  	}
	
	private void exportReport(File tempFile, List<AlWorkCalendar> alWorkCalendar) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("AlWorkCalendar", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			int i=0;
			Label label0 = new Label(i, 0, "Ngày", cellFormatHeader);
			sheet.addCell(label0);
			i++;
			Label label1 = new Label(i, 0, "Ca", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "Phòng", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "Tổ", cellFormatHeader);
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "Họ tên", cellFormatHeader);
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "Email", cellFormatHeader);
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "Điện thoại", cellFormatHeader);
			sheet.addCell(label6);
			i++;
			Label label7 = new Label(i, 0, "Chức vụ", cellFormatHeader);
			sheet.addCell(label7);
			i++;
			Label label8 = new Label(i, 0, "Email nhân viên đổi ca", cellFormatHeader);
			sheet.addCell(label8);
			i++;
			Label label9 = new Label(i, 0, "Nhân viên đổi ca", cellFormatHeader);
			sheet.addCell(label9);
			i++;
			Label label10 = new Label(i, 0, "Chức năng", cellFormatHeader);
			sheet.addCell(label10);
			i++;
			Label label11 = new Label(i, 0, "Ghi chú", cellFormatHeader);
			sheet.addCell(label11);
			i++;
			Label label12 = new Label(i, 0, "Khu vực", cellFormatHeader);
			sheet.addCell(label12);
			i = 1;
			
			for (AlWorkCalendar record : alWorkCalendar) {
				int j=0;
				Label record0 = new Label(j, i, record.getDayStr(), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,record.getShift(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getDeptCode(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getTeam(), cellFormat);
				sheet.addCell(record3);
				j++;
				Label record4 = new Label(j, i, record.getFullname(), cellFormat);
				sheet.addCell(record4);
				j++;
				Label record5 = new Label(j, i, record.getEmail(), cellFormat);
				sheet.addCell(record5);
				j++;
				Label record6 = new Label(j, i, record.getPhone(), cellFormat);
				sheet.addCell(record6);
				j++;
				Label record7 = new Label(j, i, record.getPosition(), cellFormat);
				sheet.addCell(record7);
				j++;
				Label record8 = new Label(j, i, record.getEmailBefore(), cellFormat);
				sheet.addCell(record8);
				j++;
				Label record9 = new Label(j, i, record.getFullnameBefore(), cellFormat);
				sheet.addCell(record9);
				j++;
				Label record10 = new Label(j, i, record.getFunction(), cellFormat);
				sheet.addCell(record10);
				j++;
				Label record11 = new Label(j, i, record.getDescription(), cellFormat);
				sheet.addCell(record11);
				j++;
				Label record12 = new Label(j, i, record.getRegion(), cellFormat);
				sheet.addCell(record12);
				i++;
			}
			workbook.write();
			workbook.close();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Lay danh sach function
   	@RequestMapping("functionList")
   	public @ResponseBody 
   	List<SYS_PARAMETER> functionList() {
   		List<SYS_PARAMETER> functionList= alWorkCalendarDAO.getFunctionShiftList();
   		
   		return functionList;
   	 }
   	
   	// Lay danh sach shift
   	@RequestMapping("shiftList")
   	public @ResponseBody 
   	List<SYS_PARAMETER> shiftList() {
   		List<SYS_PARAMETER> shiftList= alWorkCalendarDAO.getCaTrucList();
   		
   		return shiftList;
   	 }
   	
   	@RequestMapping("alWorkCalendarById")
	public @ResponseBody
	AlWorkCalendar alWorkCalendarById(
			@RequestParam(required = false) String id, HttpServletRequest request) {
   		AlWorkCalendar record = alWorkCalendarDAO.selectByPrimaryKey(Integer.parseInt(id));
		return record;
	}
   	
   	//FB Form
  	@RequestMapping(value = "confirm")
  	public @ResponseBody
  	int confirm(
  			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate, 	   
			@RequestParam(required = false) String shift,
			@RequestParam(required = false) String deptCode,
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String region,
  			ModelMap model, HttpServletRequest request, HttpServletResponse response) {
  		String username = SecurityContextHolder.getContext().getAuthentication().getName();
  		try {
			alWorkCalendarDAO.updateEmailBefore(startDate, endDate, shift, deptCode, email, username,region);
			saveMessage(request, "Xác nhận đổi ca thành công!");
			
  		}
  		catch (Exception e) {
  			saveMessage(request, "Xác nhận đổi ca thất bại!");
			return 0;
		}
  		
  		return 1;
  	}
}
