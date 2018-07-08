package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.ServletException;
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

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import vo.AlAlarmWorks;
import vo.AlMonitorTemperatureSite;
import vo.AlShift;
import vo.CHighlightConfigs;
import vo.CSystemConfigs;
import vo.CTableConfig;
import vo.MDepartment;
import vo.M_FILE_ATTACHMENT;
import vo.RAlarmLog;
import vo.SYS_PARAMETER;
import vo.SmsContents;
import vo.SysFiles;
import vo.SysMailParameterMaster;
import vo.SysUsers;
import vo.WWorkingAtShift;
import vo.W_WORKING_FEEDBACKS;
import vo.W_WORKING_MANAGEMENTS;
import vo.W_WORKING_PROCESSES;
import vo.W_WORKING_TYPES;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;
import vo.alarm.utils.Helper;
import vo.alarm.utils.Mail;
import vo.alarm.utils.StringTools;
import vo.dictionary.SendMail;
import dao.CHighlightConfigsDAO;
import dao.CSystemConfigsDAO;
import dao.CTableConfigDAO;
import dao.MDepartmentDAO;
import dao.M_FILE_ATTACHMENTDAO;
import dao.SYS_PARAMETERDAO;
import dao.SmsContentsDAO;
import dao.SysDefineSmsEmailDAO;
import dao.SysMailParameterMasterDAO;
import dao.SysUserEquipmentNameDAO;
import dao.SysUsersDAO;
import dao.VAlHCellDAO;
import dao.W_WORKING_FEEDBACKSDAO;
import dao.W_WORKING_MANAGEMENTSDAO;
import dao.W_WORKING_PROCESSESDAO;
import dao.W_WORKING_TYPESDAO;

@Controller
@RequestMapping("/w_working_managements/*")
public class W_WORKING_MANAGEMENTSController extends BaseController{

	@Autowired
	private W_WORKING_MANAGEMENTSDAO working_managementDao;
	@Autowired
	private W_WORKING_TYPESDAO working_typesDao;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
	private M_FILE_ATTACHMENTDAO attachmentDao;
	@Autowired
	private W_WORKING_PROCESSESDAO processesDao;
	@Autowired
	private W_WORKING_FEEDBACKSDAO feedbacksDao;
	@Autowired
	private SysUsersDAO sysUsersDao;
	@Autowired
	private MDepartmentDAO mDepartmentDAO;
	@Autowired
	private CSystemConfigsDAO cSystemConfigsDAO;
	@Autowired
	private SysMailParameterMasterDAO sysMailParamenterMasterDAO;
	@Autowired
	private CHighlightConfigsDAO highlightConfigDao;
	
	@Autowired
	private SysUserEquipmentNameDAO sysUserEquipmentNameDAO;
	
	
	@Autowired
	private SmsContentsDAO smsContentsDAO;
	@Autowired
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	
	
	@Autowired
	private SysDefineSmsEmailDAO  sysDefineSmsEmailDAO ;
	
	
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	@RequestMapping(value = "list")
	public ModelAndView showContentList(@RequestParam(required = false) String actualDateFrom,
			 							@RequestParam(required = false) String actualDateTo,
			 							@RequestParam(required = false) String assignDateFrom,
			 							@RequestParam(required = false) String assignDateTo,
			 							@RequestParam(required = false) Integer id_Work_Types,
			 							@RequestParam(required = false) String idWorkTypes,
			 							@RequestParam(required = false) String deptName,
			 							@RequestParam(required = false) String idTeam,
			 							@RequestParam(required = false) String received,
			 							@ModelAttribute("filterContent") W_WORKING_MANAGEMENTS filter, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		
		int order = 1;
		String column = "MA_CONG_VIEC";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		
		//Update by ThangPX 20131228
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		String roleManager= userLogin.getIsRoleManager();
		model.addAttribute("roleManager", roleManager);
		
		model.addAttribute("username", username);
		
		// Get Dept/team by userLogin
		List<MDepartment> teamList = mDepartmentDAO.getDepartmentOfUser(username);
		
		
		// Neu user thuoc to -> chon mac dinh la to do
		// Neu khong -> chon tat ca
		String deptCode = "";
		if(deptName == null)
		{
			for(MDepartment dept : teamList)
			{
				deptName = dept.getDeptName();
				deptCode = dept.getDeptCode();
				
			}
			if(idTeam == null)
				idTeam = "";
		}
		
		model.addAttribute("deptName", deptName);
		model.addAttribute("deptCode", deptCode);
		model.addAttribute("teamList", teamList);
		model.addAttribute("idTeam", idTeam);		
						
		//Load Nguoi giao viec, Nguoi nhan viec, Nguoi chu tri
		List<SysUsers> fullNameList = sysUsersDao.getUserByMaPhong(deptCode);
		model.addAttribute("fullNameList", fullNameList);
		
				
		String tabSelected = "",tabUnselected="",tabSelectedA="";
		if(received==null||received.equals(""))
			received ="N";
		if(received.equalsIgnoreCase("Y"))
		{
			//filter.setNguoiChuTri(username);
			//filter.setNguoiNhanViec(username);
			if (filter.getNguoiChuTri()==null)
			{
				filter.setNguoiChuTri(username);
			}
			/*if (filter.getNguoiNhanViec()==null)
			{
				filter.setNguoiNhanViec(username);
			}*/
			tabSelected="ui-tabs-selected";
			tabUnselected="";
			tabSelectedA="";
		}
		if(received.equalsIgnoreCase("N"))
		{
			tabUnselected="ui-tabs-selected";
			tabSelected="";
			tabSelectedA="";
			if (filter.getNguoiGiaoViec()==null)
			{
				filter.setNguoiGiaoViec(username);
			}
		}
		if(received.equalsIgnoreCase("A"))
		{
			tabUnselected="";
			tabSelected="";
			tabSelectedA="ui-tabs-selected";
			
			if (roleManager.equalsIgnoreCase("Y"))
			{
				List<MDepartment> deptList = mDepartmentDAO.getDepartmentForShiftList("1");
				model.addAttribute("deptList", deptList);
			}
			
		}
		/*if(filter.getNguoiChuTri()==null)
			filter.setNguoiChuTri(username);*/
		model.addAttribute("tabSelected", tabSelected);	
		model.addAttribute("tabUnselected", tabUnselected);	
		model.addAttribute("tabSelectedA", tabSelectedA);	
		model.addAttribute("received", received);	
		
		
		
		if(filter.getMaCongViec() != null)
			filter.setMaCongViec(filter.getMaCongViec().trim());
		if(filter.getTenCongViec() != null)
			filter.setTenCongViec(filter.getTenCongViec().trim());
		if(filter.getNguoiGiaoViec() != null)
			filter.setNguoiGiaoViec(filter.getNguoiGiaoViec().trim());
		/*if(filter.getNguoiNhanViec() != null)
			filter.setNguoiNhanViec(filter.getNguoiNhanViec().trim());*/
		if(filter.getNguoiChuTri() != null)
			filter.setNguoiChuTri(filter.getNguoiChuTri().trim());
		if(filter.getStartDate() != null)
			filter.setStartDate(filter.getStartDate().trim());
		if(filter.getEndDate() != null)
			filter.setEndDate(filter.getEndDate().trim());
		
		if (actualDateFrom != null && actualDateFrom.equals("")) {
			actualDateFrom = null;
		}
		
		if (actualDateTo != null && actualDateTo.equals("")) {
			actualDateTo = null;
		}
		
		
		// Ngay thang
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		if (assignDateFrom == null || assignDateFrom.equals("") || DateTools.isValid("dd/MM/yyyy", assignDateFrom)==false) {
			
			//Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DAY_OF_MONTH, -7);
			
			assignDateFrom = df.format(cal.getTime());
		
		}
		if (assignDateTo == null || assignDateTo.equals("") || DateTools.isValid("dd/MM/yyyy", assignDateTo)==false)
		{
			cal.add(Calendar.DAY_OF_MONTH, 14);
			assignDateTo = df.format(cal.getTime());
			/*assignDateTo = df.format(new Date());*/
		}
		
		Calendar cal1 = Calendar.getInstance();
		cal1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		if (actualDateFrom != null && !DateTools.isValid("dd/MM/yyyy", actualDateFrom)) {
			/*Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DAY_OF_MONTH, -7);*/
			
			actualDateFrom = df.format(cal1.getTime());
		}
		
		if (actualDateTo != null && !DateTools.isValid("dd/MM/yyyy", actualDateTo)) {
			cal1.add(Calendar.DAY_OF_MONTH, 6);
			actualDateTo = df.format(cal1.getTime());
			//actualDateTo = df.format(new Date());
		}
		//Integer idWorkTypes; 
		String maCongViec;
		String tenCongViec;
		String nguoiGiaoViec;
		String nguoiChuTri;
	//	String nguoiNhanViec; 
		String tinhTrang;
		
		if(id_Work_Types != null)
		{
			
			List<W_WORKING_TYPES> working_typesList = working_typesDao.getWorkingTypes( null, null, filter.getNguoiGiaoViec(), filter.getNguoiChuTri(), filter.getNguoiNhanViec(), null, null, null, assignDateFrom, assignDateTo);
			model.addAttribute("getDanhMucCongViecList", working_typesList);
			maCongViec = "";
			tenCongViec = "";
			nguoiGiaoViec = filter.getNguoiGiaoViec();
			nguoiChuTri=filter.getNguoiChuTri();
			//nguoiNhanViec=filter.getNguoiNhanViec(); 
			tinhTrang= "";
			actualDateFrom="";
			actualDateTo="";
			/*List<W_WORKING_MANAGEMENTS> working_managements = working_managementDao.getWorkingManagementsFilter(id_Work_Types, null, null, filter.getNguoiGiaoViec(), filter.getNguoiChuTri(), filter.getNguoiNhanViec(), null, null, null, assignDateFrom, assignDateTo, column, order ==1 ? "ASC" : "DESC",received,username);
			model.addAttribute("working_managementsList", working_managements);
			*/
			model.addAttribute("danhMucCVCBB", id_Work_Types);
			model.addAttribute("urlForm", id_Work_Types);
		}
		else
		{
			if(idWorkTypes == ""){
				
				List<W_WORKING_TYPES> working_typesList = working_typesDao.getWorkingTypes(filter.getMaCongViec(), filter.getTenCongViec(), filter.getNguoiGiaoViec(), filter.getNguoiChuTri(), filter.getNguoiNhanViec(), filter.getTinhTrang(), actualDateFrom, actualDateTo, assignDateFrom, assignDateTo);
				model.addAttribute("getDanhMucCongViecList", working_typesList);
				idWorkTypes="";
				maCongViec = filter.getMaCongViec();
				tenCongViec = filter.getTenCongViec();
				nguoiGiaoViec = filter.getNguoiGiaoViec();
				nguoiChuTri=filter.getNguoiChuTri();
				//nguoiNhanViec=filter.getNguoiNhanViec(); 
				tinhTrang= filter.getTinhTrang();
				
				/*List<W_WORKING_MANAGEMENTS> working_managements = working_managementDao.getWorkingManagementsFilter(null, filter.getMaCongViec(), filter.getTenCongViec(), filter.getNguoiGiaoViec(), filter.getNguoiChuTri(), filter.getNguoiNhanViec(), filter.getTinhTrang(), actualDateFrom, actualDateTo, assignDateFrom, assignDateTo, column, order ==1 ? "ASC" : "DESC",received,username);
				model.addAttribute("working_managementsList", working_managements);*/
				
				model.addAttribute("danhMucCVCBB", filter.getIdWorkTypes());
				model.addAttribute("urlForm", filter.getIdWorkTypes());
			}
			else if(idWorkTypes != "" && idWorkTypes != null){
				List<W_WORKING_TYPES> working_typesList = working_typesDao.getWorkingTypes(filter.getMaCongViec(), filter.getTenCongViec(), filter.getNguoiGiaoViec(), filter.getNguoiChuTri(), filter.getNguoiNhanViec(), filter.getTinhTrang(), actualDateFrom, actualDateTo, assignDateFrom, assignDateTo);
				model.addAttribute("getDanhMucCongViecList", working_typesList);
				
				maCongViec = filter.getMaCongViec();
				tenCongViec = filter.getTenCongViec();
				nguoiGiaoViec = filter.getNguoiGiaoViec();
				nguoiChuTri=filter.getNguoiChuTri();
				//nguoiNhanViec=filter.getNguoiNhanViec(); 
				tinhTrang= filter.getTinhTrang();
				/*List<W_WORKING_MANAGEMENTS> working_managements = working_managementDao.getWorkingManagementsFilter( Integer.parseInt(idWorkTypes), filter.getMaCongViec(), filter.getTenCongViec(), filter.getNguoiGiaoViec(), filter.getNguoiChuTri(), filter.getNguoiNhanViec(), filter.getTinhTrang(), actualDateFrom, actualDateTo, assignDateFrom, assignDateTo, column, order ==1 ? "ASC" : "DESC",received,username);
				model.addAttribute("working_managementsList", working_managements);*/
				
				model.addAttribute("danhMucCVCBB", filter.getIdWorkTypes());
				model.addAttribute("urlForm", filter.getIdWorkTypes());
			}
			else
			{			
				List<W_WORKING_TYPES> working_typesList = working_typesDao.getWorkingTypes(null, null, filter.getNguoiGiaoViec(), filter.getNguoiChuTri(), filter.getNguoiNhanViec(), null, null, null, assignDateFrom, assignDateTo);
				model.addAttribute("getDanhMucCongViecList", working_typesList);
				maCongViec = "";
				tenCongViec = "";
				nguoiGiaoViec = filter.getNguoiGiaoViec();
				nguoiChuTri=filter.getNguoiChuTri();
				//nguoiNhanViec=filter.getNguoiNhanViec(); 
				tinhTrang= "";
				actualDateFrom="";
				actualDateTo="";
				
				model.addAttribute("danhMucCVCBB", id_Work_Types);
				model.addAttribute("urlForm", id_Work_Types);
			}
		}
		if (idWorkTypes==null)
			idWorkTypes="";	
		if (maCongViec==null)
			maCongViec = "";
		if (tenCongViec==null)
			tenCongViec = "";
		if (nguoiGiaoViec==null)
			nguoiGiaoViec = "";
		if (nguoiChuTri==null)
			nguoiChuTri="";
		/*if (nguoiNhanViec==null)
			nguoiNhanViec=""; */
		if (tinhTrang==null)
			tinhTrang= "";
		if (actualDateFrom==null)
			actualDateFrom="";
		if (actualDateTo==null)
			actualDateTo="";

		List<SYS_PARAMETER> sysParameterByCodeList = sysParameterDao.getSysParameterByCode("TINH_TRANG_WORKS_MANA");
		model.addAttribute("sysParameterByCodeList", sysParameterByCodeList);
		
		/*List<W_WORKING_TYPES> working_typesList = working_typesDao.getWorkingTypesAll();*/
		
		model.addAttribute("tinhTrangCBB", filter.getTinhTrang());
		model.addAttribute("nguoiChuTriCBB", filter.getNguoiChuTri());
		model.addAttribute("nguoiGiaoViecCBB", filter.getNguoiGiaoViec());
		//model.addAttribute("nguoiNhanViecCBB", filter.getNguoiNhanViec());
		
		model.addAttribute("actualDateFrom", actualDateFrom);
		model.addAttribute("actualDateTo", actualDateTo);
		model.addAttribute("assignDateFrom", assignDateFrom);
		model.addAttribute("assignDateTo", assignDateTo);
		// Get highlight configs
		
		//TAO GRID MOI
		String nameTable="W_WORKING_MANAGERMENT";
		
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid(nameTable);
		//lay du lieu column cua grid
		model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
		//Lay du lieu datafield cua grid
		model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
		//Lay duong link url de lay du lieu do vao grid
		model.addAttribute("url", "dataWork.htm?idWorkTypes="+idWorkTypes+"&maCongViec="+maCongViec+"&tenCongViec="+tenCongViec+"&nguoiGiaoViec="+nguoiGiaoViec+
				"&nguoiChuTri="+nguoiChuTri+"&nguoiNhanViec=&tinhTrang="+tinhTrang+"&actualDateFrom="+actualDateFrom+
				"&actualDateTo="+actualDateTo+"&assignDateFrom="+assignDateFrom+
				"&assignDateTo="+assignDateTo+"&received="+received+
				"&username="+username+"&deptName="+deptName); 
		//Lay du lieu cac cot an hien cua grid 
		List<CTableConfig> columnList = cTableConfigDAO.getColumnList(nameTable);
		model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
		
		List<CHighlightConfigs> highlighList = cHighlightConfigsDAO.getByKeyAndKPI("W_WORKING_MANAGERMENT","level");
		String hightlight=HelpTableConfigs.highLightRow(highlighList,"grid","colorrowrenderer");
		
		List<CHighlightConfigs> highlighListCell = cHighlightConfigsDAO.getByKeyAndKPI("W_WORKING_MANAGERMENT","finishRate");
		String highlighCell=HelpTableConfigs.highLightCol(highlighListCell,"grid","FR");
		
		
		
		List<CHighlightConfigs> highlighListCell2 = cHighlightConfigsDAO.getByKeyAndKPI("W_WORKING_MANAGERMENT","tenTinhTrang");
		String highlighTT=HelpTableConfigs.highLightCol(highlighListCell2,"grid","TT");
		
		model.addAttribute("highlight", hightlight+highlighCell+highlighTT);
		
		return new ModelAndView("jsp/works_contentList");
	}
		
	@RequestMapping("/dataWork")
	public @ResponseBody 
	Map<String, Object> dataWork(@RequestParam(required = false) Integer idWorkTypes,@RequestParam(required = false) String maCongViec,
			@RequestParam(required = false) String tenCongViec, @RequestParam(required = false) String nguoiGiaoViec,
			@RequestParam(required = false) String nguoiChuTri, 
			@RequestParam(required = false) String nguoiNhanViec, @RequestParam(required = false) String tinhTrang,
			@RequestParam(required = false) String actualDateFrom,@RequestParam(required = false) String actualDateTo,
			@RequestParam(required = false) String assignDateFrom,
			@RequestParam(required = false) String assignDateTo,@RequestParam(required = false) String received,
			@RequestParam(required = false) String username,
			@RequestParam(required = false) String deptName,
			HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
	
		Map<String, Object> data = new HashMap<String, Object>();
		
		List<W_WORKING_MANAGEMENTS> dataList = new ArrayList<W_WORKING_MANAGEMENTS>();
		int startRecord = 0, endRecord = 0;
		String sortfield = "";
		String sortorder = "";
		int pageNum = Integer.parseInt(request.getParameter("pagenum"));
		if(pageNum == -1)
			pageNum = 1;
		int pageSize = Integer.parseInt(request.getParameter("pagesize"));
		sortfield = request.getParameter("sortdatafield");
		sortorder = request.getParameter("sortorder");

		startRecord = pageNum*pageSize;
		endRecord = startRecord + pageSize;
		
		String nameTable="W_WORKING_MANAGERMENT";
		List<CTableConfig> columnList = null;
		List<CTableConfig> tableConfigList = null;
		columnList = cTableConfigDAO.getTableConfigGet(nameTable, sortfield);
		tableConfigList = cTableConfigDAO.getTableConfigGet(nameTable, null);
		
		// Tim kiem tren grid
		String strWhere = HelpTableConfigs.filter(request);
		for(CTableConfig column: tableConfigList)
		{
			strWhere = strWhere.toUpperCase().replaceAll(column.getDataField().toUpperCase(), column.getTableColumn());
		}
		// Sap xep
		for(CTableConfig column: columnList)
		{
			sortfield = column.getTableColumn();
			break;
		}
		
			int totalRow =0;
			/*try
			{*/
				totalRow = working_managementDao.countWorkingManagement(idWorkTypes, maCongViec, tenCongViec, nguoiGiaoViec, nguoiChuTri, nguoiNhanViec, 
						tinhTrang, actualDateFrom, actualDateTo, assignDateFrom, assignDateTo,received,username,deptName);
				//	model.addAttribute("totalRows", totalRow);
				dataList =  working_managementDao.getWorkingManagementsFilter(idWorkTypes, maCongViec, tenCongViec, nguoiGiaoViec, nguoiChuTri, nguoiNhanViec, 
						tinhTrang, actualDateFrom, actualDateTo, assignDateFrom, assignDateTo, sortfield, sortorder,received,username,deptName,strWhere,startRecord,endRecord);
			/*}
			catch(Exception exp)
			{
				alarmList=null;
			}*/
			data.put("totalRow", totalRow);
			data.put("Rows", dataList);
		return data;
	 }

	
	
	@RequestMapping(value = "deleteContent", method = RequestMethod.GET)
	public String deleteContent(@RequestParam (required = true) Integer id,@RequestParam(required = false) String wwmFkId,
			@RequestParam(required = false) String note,
			@RequestParam(required = false) String received,
			HttpServletRequest request, HttpServletResponse response) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		int countChild = working_managementDao.countChild(id);
		//check quyen danh gia cong viec
		Integer isAllowRate = working_managementDao.checkRoleAllowRate(username,id);
		Integer isAllowDelete = working_managementDao.checkRoleAllowDelete(username,id,received);		
		if (countChild==0)
		{
		
			W_WORKING_MANAGEMENTS record = working_managementDao.selectByPrimaryKey(id);
			
			/*if ( (record.getNguoiGiaoViec().equalsIgnoreCase(username)&&received.equals("N"))
					||(record.getNguoiChuTri().equalsIgnoreCase(username)&&isAllowRate==1&&received.equals("Y") )
					||(received.equals("A")&&userLogin.getIsRoleManager().equals("Y"))
					)*/
			if(isAllowDelete>0||(note.equals("form")&&(userLogin.getIsRoleManager().equals("Y")||record.getNguoiGiaoViec().equalsIgnoreCase(username)||record.getNguoiChuTri().equalsIgnoreCase(username))))
			{
				Delete3Table(Integer.toString(id));
				working_managementDao.deleteByPrimaryKey(id);
				working_managementDao.deleteWorkingManagerMail(id);
				saveMessageKey(request, "messsage.confirm.deletesuccess");
			}
			else
			{
				saveMessageKey(request, "messsage.confirm.deletework.errorRol");
			}
			/*if(record.getTinhTrang() != null){
				if(record.getTinhTrang().equals("DANG_THUC_HIEN")){
					saveMessageKey(request, "message.confirm.congViec.dangThucHien");
				}
				else if(record.getTinhTrang().equals("DA_THUC_HIEN")){
					saveMessageKey(request, "message.confirm.congViec.daThucHien");
				}
				else{
					Delete3Table(Integer.toString(id));
					
					working_managementDao.deleteByPrimaryKey(id);
					working_managementDao.deleteWorkingManagerMail(id);
					saveMessageKey(request, "messsage.confirm.deletesuccess");
				}
			}
			else{*/
				
			/*}*/
			}
		else
		{
			saveMessageKey(request, "messsage.confirm.deletework.error");
		}
		
		String path="redirect:list.htm?received="+received;
		if (note.equals("form"))
		{
			path="redirect:formContentDetails.htm?id="+wwmFkId+"&note=form";
		}
		return path;
	}
	
	private void Delete3Table(String str)
	{
		List<W_WORKING_FEEDBACKS> id_worksFeedbacks = feedbacksDao.getIdWorks(str);
		if(id_worksFeedbacks.size()>0)
		{
			for(int k=0;k<id_worksFeedbacks.size();k++)
			{
				feedbacksDao.deleteByPrimaryKey(id_worksFeedbacks.get(k).getId()); //Xóa ở bảng W_WORKING_FEEDBACKS
			}
		}
		
		List<M_FILE_ATTACHMENT> id_worksAttachment = attachmentDao.getIdWorks(str);
		if(id_worksAttachment.size()>0)
		{
			for(int n=0;n<id_worksAttachment.size();n++)
			{
				attachmentDao.deleteByPrimaryKey(id_worksAttachment.get(n).getId()); //Xóa ở bảng M_FILE_ATTACHMENT
			}
		}
		
		List<W_WORKING_PROCESSES> id_worksProcesses = processesDao.getIdWorks(str);
		if(id_worksProcesses.size()>0)
		{
			for(int j=0;j<id_worksProcesses.size();j++)
			{
				processesDao.deleteByPrimaryKey(id_worksProcesses.get(j).getId()); // Xóa ở bảng W_WORKING_PROCESSES
			}
		}
		
	}
	
	
	@RequestMapping(value = "formContent", method = RequestMethod.GET)
	public String showFormContent(@RequestParam(required = false) Integer id,
								  @RequestParam(required = false) Integer idWorkTypes, 
								  @RequestParam(required = false) String assignDate,
								  @RequestParam(required = false) String deptCode,
								  @RequestParam(required = false) String wwmFkId,
								  @RequestParam(required = false) String estimateDate, 
								  @RequestParam(required = false) String note, 
								  @RequestParam(required = false) String maCVCha, 
								  @RequestParam(required = false) String received,
								  @RequestParam(required = false) String  region,
								  ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		W_WORKING_MANAGEMENTS working_managements =  new W_WORKING_MANAGEMENTS() ;
		if (id != null)
		{
			working_managements = working_managementDao.selectByPrimaryKey(id);
		}
		else
		{
			if (wwmFkId!=null&&!wwmFkId.equals("") )
			{
				working_managements = working_managementDao.selectByPrimaryKey(Integer.parseInt(wwmFkId));
				working_managements.setId(null);
				working_managements.setWwmFkId(Integer.parseInt(wwmFkId));
				working_managements.setNguoiGiaoViec(username);
			}
		}
		String isSite="N";
		if (working_managements!=null && working_managements.getSite() !=null)
		{
			 isSite="Y";
		}
		model.addAttribute("isSite", isSite);
		
		//working_managements.setIdWorkTypes(idWorkTypes);
		//working_managements.setWwmFkId(wwmFkId);
		model.addAttribute("w_working_managements", working_managements);
		model.addAttribute("wwmFkId", wwmFkId);
		model.addAttribute("maCVCha", maCVCha);
		model.addAttribute("received", received);
		if(deptCode == null)
			deptCode = "";
		List<SysUsers> fullNameList = sysUsersDao.getUserByMaPhong(deptCode);
		String ccEmailArray="var ccEmailList = new Array(";//Danh sach district cho cobobox
		String cn="";
		for (SysUsers dis : fullNameList) {
			ccEmailArray = ccEmailArray + cn +"\""+dis.getEmail()+"\"";
			cn=",";
		}
		ccEmailArray = ccEmailArray+");";
		model.addAttribute("ccEmailList", ccEmailArray);
		model.addAttribute("fullNameList", fullNameList);
		
		
		List<W_WORKING_TYPES> working_typesList = working_typesDao.getWorkingTypesAll();
		model.addAttribute("getDanhMucCongViecList", working_typesList);
		
		
		if(id == null)
		{
			model.addAttribute("ContentWorksAddEdit", "Y");
			model.addAttribute("nguoiGiaoViecCBB", SecurityContextHolder.getContext().getAuthentication().getName());
			model.addAttribute("danhMucCVCBB", idWorkTypes);
			
			Calendar cal = Calendar.getInstance();
			// Ngay thang
			if (assignDate == null || assignDate.equals("") || DateTools.isValid("dd/MM/yyyy HH:mm:ss", assignDate)==false) {
					
				cal.setTime(new Date());
				assignDate = df_full.format(cal.getTime());
			
			}
			
			if (estimateDate == null || estimateDate.equals("") || DateTools.isValid("dd/MM/yyyy HH:mm:ss", estimateDate)==false)
			{
				cal.setLenient(false);
				cal.roll(Calendar.DAY_OF_MONTH, 1);
				estimateDate = df_full.format(cal.getTime());
			}
			
			model.addAttribute("assignDate", assignDate);
	    	model.addAttribute("estimateDate", estimateDate);	
	    	
	    	if (wwmFkId !=null && !wwmFkId.equals(""))
	    	{
	    		//model.addAttribute("ContentWorksAddEdit", "N");
				model.addAttribute("nguoiGiaoViecCBB", working_managements.getNguoiGiaoViec());
		    	model.addAttribute("nguoiChuTriCBB", working_managements.getNguoiChuTri());
		    	model.addAttribute("nguoiNhanViecCBB", working_managements.getNguoiNhanViec());
		    	model.addAttribute("ccEmail", working_managements.getCcEmail());
		    	model.addAttribute("danhMucCVCBB", working_managements.getIdWorkTypes());
		    	
		    	model.addAttribute("assignDate", working_managements.getNgayGiaoViec());
		    	model.addAttribute("estimateDate", working_managements.getNgayGiaoHoanThanh());
		    	
		    	
		    	Calendar currentDate = Calendar.getInstance();
				SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyyhhmmss");
				String dateNow = formatter.format(currentDate.getTime());
				String path="";
				CSystemConfigs config = cSystemConfigsDAO.selectByPrimaryKey("upload.dir");
				if (config!=null)
				{
					path=config.getParamValue();
				}
		    	String fileList = "";
		    	String hello = "";
				List<M_FILE_ATTACHMENT> file_attachment1 = attachmentDao.fileName_file(wwmFkId);
				
				for (M_FILE_ATTACHMENT record : file_attachment1) {
					
					String pathsource=record.getFilePath();
					String fileOld=record.getFileName();
					String fileName = dateNow+"_"+ fileOld.substring(fileOld.indexOf("_"));
					String uploadPath=path.concat("/work");
			        // Tạo ra các thư mục nếu nó không tồn tại
			        File uploadDir = new File(uploadPath);
			        if (!uploadDir.exists()) {
			            uploadDir.mkdirs();
			        }
			        String pathdest = uploadPath.concat("/").concat(fileName);
					copyFileUsingApacheCommonsIO( pathsource,  pathdest);
					record.setId(null);
					record.setFileName(fileName);
					record.setFilePath(pathdest);
					
					attachmentDao.insertAndResult(record);
					fileList += hello + record.getId();
					hello = ",";
					
				}
				
				model.addAttribute("file_attachment", fileList);
	    	}
		}
		else
		{
			model.addAttribute("ContentWorksAddEdit", "N");
			model.addAttribute("nguoiGiaoViecCBB", working_managements.getNguoiGiaoViec());
	    	model.addAttribute("nguoiChuTriCBB", working_managements.getNguoiChuTri());
	    	model.addAttribute("nguoiNhanViecCBB", working_managements.getNguoiNhanViec());
	    	model.addAttribute("ccEmail", working_managements.getCcEmail());
	    	model.addAttribute("danhMucCVCBB", working_managements.getIdWorkTypes());
	    	
	    	model.addAttribute("assignDate", working_managements.getNgayGiaoViec());
	    	model.addAttribute("estimateDate", working_managements.getNgayGiaoHoanThanh());
	    	
	    	String fileList = "";
			List<M_FILE_ATTACHMENT> file_attachment1 = attachmentDao.fileName_file(Integer.toString(id));
			String hello = "";
			for(int i=0;i<file_attachment1.size();i++){
				fileList += hello + file_attachment1.get(i).getId();
				hello = ",";
			}
			
			model.addAttribute("file_attachment", fileList);
	    	
			
		}
		model.addAttribute("deptCode", deptCode);
		model.addAttribute("note", note);
		
		List<String> cellList = sysUserEquipmentNameDAO.getSiteList(null,null,null,null);
		//String cellListStr ="";
		String cellArray="var cellList = new Array(";
		cn="";
		for (String cell : cellList) {
		//	cellListStr = cellListStr + cn +cell;
			cellArray = cellArray + cn +"\""+cell+"\"";
			cn=",";
		}
		cellArray = cellArray+");";
		model.addAttribute("cellList", cellArray);    
		
		Date now = new Date();
		String status="";
		if(working_managements.getActualDate() == null && working_managements.getEstimateDate() != null )
		{
			if(now.compareTo(working_managements.getEstimateDate()) > 0)
			{
				status="QUA_HAN";
			}
		}
		else if(working_managements.getActualDate() != null)
		{
			status="HOAN_THANH";
		}
		model.addAttribute("status", status);
		
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		
		if (working_managements.getCaTruc()==null 
				&&working_managements.getNguoiNhanViec()!=null && working_managements.getNguoiGiaoViec()!=null&&working_managements.getNguoiChuTri()!=null
				&&(working_managements.getNguoiNhanViec().concat(",").contains(username+",")||working_managements.getNguoiChuTri().equals(username))
				&&!working_managements.getNguoiGiaoViec().equals(username)&&!userLogin.getIsRoleManager().equals("Y"))
		{
			model.addAttribute("statusUpdate", "N");
		}
		else
		{
			model.addAttribute("statusUpdate", "Y");
		}
		model.addAttribute("region", region);
		return "jsp/works_contentForm";
	}
	
	private static final String UPLOAD_DIRECTORY = "upload";
    private static final int THRESHOLD_SIZE = 1024 * 1024 * 3; // 3MB
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
    private static final int REQUEST_SIZE = 1024 * 1024 * 50; // 50MB
	
	@RequestMapping(value = "formContent", method = RequestMethod.POST)
	public String onSubmitContent(@RequestParam(required = false) String id,
			@RequestParam(required = false) String assignDate,
			@RequestParam(required = false) String estimateDate,
			@RequestParam(required = false) String action,
			@RequestParam(required = false) String deptCode,
			@RequestParam(required = false) Integer wwmFkId,
			@RequestParam(required = false) String fileId,
			@RequestParam(required = false) String note, 
			 @RequestParam(required = false) String maCVCha, 
			 @RequestParam(required = false) String isSite,  
			 @RequestParam(required = false) String statusUpdate,
			 @RequestParam(required = false) String  status,
			 @RequestParam(required = false) String  nguoiNhanViec,
			 @RequestParam(required = false) String  received,
			 @RequestParam(required = false) String  region,
			@ModelAttribute("w_working_managements") @Valid W_WORKING_MANAGEMENTS working_managements, BindingResult result, HttpServletRequest request, HttpServletResponse response,
			//@RequestParam("file") MultipartFile file,
			ModelMap model ) throws ServletException, IOException, ParseException{
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if (working_managements.getSite() !=null &&!working_managements.getSite().equals(""))
		{
			isSite="Y";
		}
		model.addAttribute("isSite", isSite);
		model.addAttribute("received", received);
		String cn="";
		
		String path="redirect:list.htm?received="+received;
		if (note.equals("form"))
		{
			path="redirect:formContentDetails.htm?id="+wwmFkId+"&type=";
		}
		else if (note.equals("cvcl"))
		{
			path="redirect:/working/list.htm?type=&region="+region;
		}
		else if (note.equals("shif"))
		{
			path="redirect:/caTruc/list.htm?region="+region;
		}
	
		if (nguoiNhanViec!=null && !nguoiNhanViec.equals(""))
		{
			working_managements.setNguoiNhanViec(nguoiNhanViec);
		}
		
		if (result.hasErrors()||(statusUpdate!=null && statusUpdate.equals("N"))) {
			
			if(id.equals(""))
			{
				model.addAttribute("ContentWorksAddEdit", "Y");
			}
			else
			{
				model.addAttribute("ContentWorksAddEdit", "N");
			}
			model.addAttribute("deptCode", deptCode);
			model.addAttribute("note", note);
			model.addAttribute("maCVCha", maCVCha);
			model.addAttribute("statusUpdate", statusUpdate);
			model.addAttribute("status", status);
			model.addAttribute("region", region);
			
			/*if (statusUpdate!=null && statusUpdate.equals("N"))
			{
				saveMessageKey(request, "messsage.confirm.dontRolEdit");		
			}*/
			
			if (result.hasFieldErrors("assignDate"))
				model.addAttribute("assignDateError", "assignDateError");
			if (result.hasFieldErrors("estimateDate"))
				model.addAttribute("estimateDateError", "estimateDateError");
			
			List<SysUsers> fullNameList = sysUsersDao.getUserByMaPhong(deptCode);
			String ccEmailArray="var ccEmailList = new Array(";//Danh sach district cho cobobox
			cn="";
			for (SysUsers dis : fullNameList) {
				ccEmailArray = ccEmailArray + cn +"\""+dis.getEmail()+"\"";
				cn=",";
			}
			ccEmailArray = ccEmailArray+");";
			model.addAttribute("ccEmailList", ccEmailArray);
			model.addAttribute("fullNameList", fullNameList);
			
			List<W_WORKING_TYPES> working_typesList = working_typesDao.getWorkingTypesAll();
			model.addAttribute("getDanhMucCongViecList", working_typesList);
			
			model.addAttribute("nguoiChuTriCBB", working_managements.getNguoiChuTri());
			model.addAttribute("nguoiNhanViecCBB", working_managements.getNguoiNhanViec());
			model.addAttribute("nguoiGiaoViecCBB", working_managements.getNguoiGiaoViec());
			model.addAttribute("danhMucCVCBB", working_managements.getIdWorkTypes());
			model.addAttribute("assignDate", assignDate);
			model.addAttribute("estimateDate", estimateDate);
			model.addAttribute("ccEmail", working_managements.getCcEmail());
			if (working_managements.getId()!=null)
			{
				String fileList = "";
				List<M_FILE_ATTACHMENT> file_attachment1 = attachmentDao.fileName_file(working_managements.getId().toString());
				String hello = "";
				for(int i=0;i<file_attachment1.size();i++){
					fileList += hello + file_attachment1.get(i).getId();
					hello = ",";
				}
				model.addAttribute("file_attachment", fileList);
			}
			List<String> cellList = sysUserEquipmentNameDAO.getSiteList(null,null,null,null);
			//String cellListStr ="";
			String cellArray="var cellList = new Array(";
			cn="";
			for (String cell : cellList) {
			//	cellListStr = cellListStr + cn +cell;
				cellArray = cellArray + cn +"\""+cell+"\"";
				cn=",";
			}
			cellArray = cellArray+");";
			model.addAttribute("cellList", cellArray);    
			saveMessageKey(request, "messsage.confirm.notFull");	
            return "jsp/works_contentForm";
		}
		
		try
		{
			if(!assignDate.equals(""))
				working_managements.setAssignDate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(assignDate));
			if(!estimateDate.equals(""))
				working_managements.setEstimateDate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(estimateDate));
		}
		catch(Exception e){
		}

		List<SYS_PARAMETER> sysParameterByCodeList = sysParameterDao.getSysParameterByCode("TINH_TRANG_WORKS_MANA");
		model.addAttribute("sysParameterByCodeList", sysParameterByCodeList);
		if (working_managements.getNguoiChuTri()!=null)
		{
			SysUsers userLogin = sysUsersDao.selectByPrimaryKey(working_managements.getNguoiChuTri());
			working_managements.setDept(userLogin.getMaPhong());
		}
		
		String filePath=null;
		Integer id_mana= null;
		if (working_managements.getActualDate()!=null )
		{
			if (working_managements.getEstimateDate().getTime() - working_managements.getActualDate().getTime() < 0)
			{
				working_managements.setAssessResult("Không đạt");
			}
			else
			{
				working_managements.setAssessResult("Đạt");
			}
		}
		
		if(working_managementDao.selectByPrimaryKey(working_managements.getId()) == null)
		{
			
			working_managements.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			working_managements.setTinhTrang(sysParameterByCodeList.get(0).getValue());
			
			id_mana = working_managementDao.insertAndResult(working_managements);		
			
			if(fileId != "" && fileId != null){
				String[] fileList = fileId.split(",");
				for(int i=0;i<fileList.length;i++){
					M_FILE_ATTACHMENT record = new M_FILE_ATTACHMENT();
					record.setIdWorkMana(id_mana);
					if(!fileList[i].equals(""))
						record.setId(Integer.parseInt(fileList[i]));
					attachmentDao.updateByPrimaryKeySelective(record);
				}
			}
			/*//Lấy thông tin file rồi thêm vào bảng M_FILE_ATTACHMENT
			
			if (!ServletFileUpload.isMultipartContent(request)) {
			    return "jsp/works_contentForm";
			}
			filePath = tepCongVan(file, id_mana, request);*/
			if (!action.equalsIgnoreCase("sendMail"))
			{
				saveMessageKey(request, "messsage.confirm.addsuccess");		
			}	
			working_managementDao.saveManagerSend(working_managements.getId(),working_managements.getNguoiChuTri(),working_managements.getNguoiNhanViec(),working_managements.getFinishRate(),working_managements.getNguoiGiaoViec());
			
		}
		else
		{
			working_managements.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			working_managements.setTinhTrang(sysParameterByCodeList.get(1).getValue());
			working_managementDao.updateByPrimaryKey(working_managements);
			/*filePath = tepCongVan(file, working_managements.getId(), request);*/
			id_mana=working_managements.getId();
			System.out.println("fileId: "+fileId);
			if(fileId != "" && fileId != null){
				String[] fileList = fileId.split(",");
				for(int i=0;i<fileList.length;i++){
					M_FILE_ATTACHMENT record = new M_FILE_ATTACHMENT();
					record.setIdWorkMana(id_mana);
					if(!fileList[i].equals(""))
						record.setId(Integer.parseInt(fileList[i]));
					attachmentDao.updateByPrimaryKeySelective(record);
				}
			}
			if (!action.equalsIgnoreCase("sendMail"))
			{
				saveMessageKey(request, "messsage.confirm.updatesuccess");
			}
			working_managementDao.saveManagerSend(working_managements.getId(),working_managements.getNguoiChuTri(),working_managements.getNguoiNhanViec(),working_managements.getFinishRate(),working_managements.getNguoiGiaoViec());
			
		}
		
		// Gui mail. AnhCTV 28/12/2013
		if (action.equalsIgnoreCase("sendMail"))
		{
			//Gui mail
			String kq = sendMailWork(working_managements,id_mana,deptCode);
			if (kq.equalsIgnoreCase("F"))
			{
				saveMessageKey(request, "messsage.confirm.mailFall");
			}
			else
			{
				saveMessageKey(request, "messsage.confirm.mailsuccess");
			}
			//Gui tin nhan
			String kqnt= sendSMS(working_managements);
		}
		
		return path;
	}
	
	private String tepCongVan(MultipartFile file, int id_mana, HttpServletRequest request){
		// Tên file
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyyhhmmss");
		String dateNow = formatter.format(currentDate.getTime());
		String fileName = dateNow + id_mana + "_" + StringTools.removeAccent(file.getOriginalFilename());
		String path="";
		CSystemConfigs config = cSystemConfigsDAO.selectByPrimaryKey("upload.dir");
		if (config!=null)
		{
			path=config.getParamValue();
		}
		if(file.getOriginalFilename() != "") 
		{
			
			// Cấu hình một số cài đặt
	        DiskFileItemFactory factory = new DiskFileItemFactory();
	        factory.setSizeThreshold(THRESHOLD_SIZE);
	        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
			
	        ServletFileUpload upload = new ServletFileUpload(factory);
	        upload.setFileSizeMax(MAX_FILE_SIZE);
	        upload.setSizeMax(REQUEST_SIZE); 
	        // Xây dựng đường dẫn thư mục để lưu trữ các tập tin tải lên
	      //  String uploadPath = request.getSession().getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
	       String uploadPath=path.concat("/work");
	        // Tạo ra các thư mục nếu nó không tồn tại
	        File uploadDir = new File(uploadPath);
	        if (!uploadDir.exists()) {
	            uploadDir.mkdir();
	        }
	        
	        String filePath = uploadPath.concat("/").concat(fileName);
	        File dest = new File(filePath);
	        try {
	        	
	        	file.transferTo(dest);  //Copy file	
	        	
	        	M_FILE_ATTACHMENT record = new M_FILE_ATTACHMENT();
				record.setFileName(fileName);
				record.setIdWorkMana(id_mana);
				record.setFilePath(filePath);
				
				attachmentDao.insert(record);
	        	
	        } catch (IllegalStateException e) {
	            e.printStackTrace();     
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return filePath;
		}
		return null;
	}
	
	@RequestMapping(value = "formContentDetails", method = RequestMethod.GET)
	public String showFormContentDetails(@RequestParam(required = false) Integer id,
			@RequestParam(required = false) String tinhTrang,
			@RequestParam(required = false) String type,
			@RequestParam(required = false) Integer wwmFkId,
			@RequestParam(required = false) String note,
			@RequestParam(required = false) String received,
			ModelMap model, HttpServletRequest request, HttpServletResponse response){
		
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("username", username);
		
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		
		
		List<MDepartment> deptList = mDepartmentDAO.getDepartmentOfUser(username);
		model.addAttribute("wwmFkId", wwmFkId);
		model.addAttribute("note", note);
		// Neu user thuoc to -> chon mac dinh la to do
		// Neu khong -> chon tat ca
		String deptCode = "";
		for(MDepartment dept : deptList)
		{
			deptCode = dept.getDeptCode();
			
		}
		W_WORKING_MANAGEMENTS w_working_details = (id == null) ? new W_WORKING_MANAGEMENTS() : working_managementDao.selectJoinByPrimaryKey(id);
		if (w_working_details!=null&& w_working_details.getFinishRate()==null)
		{
			w_working_details.setFinishRate(0);
		}
		
		model.addAttribute("w_working_details", w_working_details);
		model.addAttribute("type", type);
		
		Date now = new Date();
		String status="";
		if(w_working_details!=null && w_working_details.getActualDate() == null && w_working_details.getEstimateDate() != null )
		{
			if(now.compareTo(w_working_details.getEstimateDate()) > 0)
			{
				status="QUA_HAN";
				W_WORKING_MANAGEMENTS record = new W_WORKING_MANAGEMENTS();
				record.setTinhTrang("QUA_HAN");
				record.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				record.setId(id);
				working_managementDao.updateTinhTrangCV(record);
				w_working_details.setTenTinhTrang("Quá hạn");
			}
		}
		else if(w_working_details.getActualDate() != null)
		{
				status="DA_THUC_HIEN";
		}
		model.addAttribute("status", "");
		
		//check quyen danh gia cong viec
		Integer isAllowRate = working_managementDao.checkRoleAllowRate(username,id);
		model.addAttribute("isAllowRate", isAllowRate);
				
		int statusUpdate = 0;
		String nguoiGiaoViec=w_working_details.getNguoiGiaoViec();
		//truong hop 1:User giao việc (bất kể cũng là chủ trì, nhận việc, trực ca) edit tất cả trừ mã công việc, người giao việc, thời gian còn lại, LonLat (khi edit tên trạm cũng tự đổi theo)
		//user la nguoi giao viec
		//user co quyen admin
		//user la thu truong: nguoi quan ly nguoi chu tri
		if (isAllowRate==1)
		{
			statusUpdate=1;
			
			List<SysUsers> fullNameList = sysUsersDao.getUserByMaPhong(deptCode);
			String ccEmailArray="var ccEmailList = new Array(";//Danh sach district cho cobobox
			String cn="";
			for (SysUsers dis : fullNameList) {
				ccEmailArray = ccEmailArray + cn +"\""+dis.getEmail()+"\"";
				cn=",";
			}
			ccEmailArray = ccEmailArray+");";
			model.addAttribute("ccEmailList", ccEmailArray);
			
			if (w_working_details.getId()!=null)
			{
				String fileList = "";
				List<M_FILE_ATTACHMENT> file_attachment1 = attachmentDao.fileName_file(Integer.toString(w_working_details.getId()));
				String hello = "";
				for(int i=0;i<file_attachment1.size();i++){
					fileList += hello + file_attachment1.get(i).getId();
					hello = ",";
				}
				
				model.addAttribute("file_attachment", fileList);
			}
			List<String> cellList = sysUserEquipmentNameDAO.getSiteList(null,null,null,null);
			//String cellListStr ="";
			String cellArray="var cellList = new Array(";
			cn="";
			for (String cell : cellList) {
			//	cellListStr = cellListStr + cn +cell;
				cellArray = cellArray + cn +"\""+cell+"\"";
				cn=",";
			}
			cellArray = cellArray+");";
			model.addAttribute("cellList", cellArray);    
			
			
		}
		//truong hop 2:edit tất cả trừ mã công việc, người giao việc, thời gian còn lại, LonLat, người chủ trì, người nhận việc, tên việc, ngày giao việc, hạn xử lí, tóm tat nội dung, nội dung công việc, tệp đính kèm
		//ca truc, chu tri hoăc nguoi nhan viec (khong phai la nguoi giao viec) cua cong viec con han
		else if ((w_working_details.getCaTruc()!=null||!status.equals("QUA_HAN"))
				&&((w_working_details.getNguoiNhanViec()!=null &&w_working_details.getNguoiNhanViec().concat(",").contains(username+",")&&!w_working_details.getNguoiNhanViec().equals(nguoiGiaoViec))
					||(w_working_details.getNguoiChuTri()!=null&&w_working_details.getNguoiChuTri().equals(username)&&!w_working_details.getNguoiChuTri().equals(nguoiGiaoViec))
				))
		{
			statusUpdate=2;

			List<M_FILE_ATTACHMENT> file_attachment1 = attachmentDao.fileName_file(Integer.toString(id));
			model.addAttribute("file_attachment", file_attachment1);
		}
		//truong hop 3: không edit được gì cả trừ nhận xét công việc
		//chu tri hoac nguoi nhan viec cua cong viec qua han
		else if (status.equals("QUA_HAN")&&((w_working_details.getNguoiNhanViec()!=null &&w_working_details.getNguoiNhanViec().concat(",").contains(username+",")&&!w_working_details.getNguoiNhanViec().equals(nguoiGiaoViec))
				||(w_working_details.getNguoiChuTri()!=null&&w_working_details.getNguoiChuTri().equals(username)&&!w_working_details.getNguoiChuTri().equals(nguoiGiaoViec))))
		{
			statusUpdate=3;

			List<M_FILE_ATTACHMENT> file_attachment1 = attachmentDao.fileName_file(Integer.toString(id));
			model.addAttribute("file_attachment", file_attachment1);
		}
		model.addAttribute("statusUpdate", statusUpdate);
		
		List<SYS_PARAMETER> getTinhtrangList = sysParameterDao.getSysParameterByCode("TINH_TRANG_WORKS_MANA");
		model.addAttribute("getTinhtrangList", getTinhtrangList);
		model.addAttribute("tinhTrang", w_working_details.getTinhTrang());
		
		
		List<SYS_PARAMETER> getDanhGiaList = sysParameterDao.getSysParameterByCode("ASSESS_WORK");
		model.addAttribute("getDanhGiaList", getDanhGiaList);
		String assessResult="";
		if (w_working_details.getAssessResult()!=null)
		{
			assessResult = w_working_details.getAssessResult();
		}
		else
		{
			assessResult = "Không đạt";
		}
		model.addAttribute("assessResult",assessResult);
		
		SysUsers getEmailOfNguoiGiaoViec = sysUsersDao.selectSysUsersByUsername(w_working_details.getNguoiGiaoViec());
		model.addAttribute("getEmailOfNguoiGiaoViec", getEmailOfNguoiGiaoViec);
		
		/*SysUsers getEmailOfNguoiChuTri = sysUsersDao.selectSysUsersByUsername(w_working_details.getNguoiChuTri());
		model.addAttribute("getEmailOfNguoiChuTri", getEmailOfNguoiChuTri);
		
		SysUsers getEmailOfNguoiNhanViec = sysUsersDao.selectSysUsersByUsername(w_working _details.getNguoiNhanViec());
		model.addAttribute("getEmailOfNguoiNhanViec", getEmailOfNguoiNhanViec);*/
		List<W_WORKING_MANAGEMENTS> mailList = working_managementDao.getMailList(w_working_details.getId());
		String mailCtr="";
		String mailNTH="";
		String con="";
	    for (W_WORKING_MANAGEMENTS w_WORKING_MANAGEMENTS : mailList) {
	    	mailCtr = w_WORKING_MANAGEMENTS.getNguoiChuTri();
	    	mailNTH = mailNTH+con+w_WORKING_MANAGEMENTS.getNguoiNhanViec();
	    	con=",";
		}
	    model.addAttribute("getEmailOfNguoiChuTri", mailCtr);
	    model.addAttribute("getEmailOfNguoiNhanViec", mailNTH);
	    
	    W_WORKING_PROCESSES w_working_processes = processesDao.selectIdWorksMaxCreateDate(Integer.toString(id));
	    model.addAttribute("w_working_processes", w_working_processes);
	    
	    List<W_WORKING_PROCESSES> workingProcessesListByIdWorks = processesDao.getWorkingProcessesListByIdWorks(id);
	    model.addAttribute("workingProcessesListByIdWorks", workingProcessesListByIdWorks);
	    
	    W_WORKING_TYPES w_working_types = working_typesDao.selectByPrimaryKey(w_working_details.getIdWorkTypes());
	    model.addAttribute("w_working_typesByIdWorks", w_working_types);
	    
		//Người chủ trì mới xem đc tiến độ công việc
	    
	    SysUsers getFullNameByUserLogin = sysUsersDao.selectSysUsersByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
	    model.addAttribute("userFullName", getFullNameByUserLogin);
	    
		if(getFullNameByUserLogin.getUsername().equals(w_working_details.getNguoiChuTri()))
		{
			model.addAttribute("userIsNguoiChuTri", "Y");
		}
		else
		{
			model.addAttribute("userIsNguoiChuTri", "N");
		}
		
		
		/*String fileList = "";
		List<M_FILE_ATTACHMENT> file_attachment1 = attachmentDao.fileName_file(Integer.toString(id));
		String hello = "";
		for(int i=0;i<file_attachment1.size();i++){
			fileList += hello + file_attachment1.get(i).getId();
			hello = ",";
		}
		model.addAttribute("file_attachment", fileList);*/
		
		//Lấy danh sách thảo luận góp ý công việc
		
		List<W_WORKING_FEEDBACKS> feedbacks = feedbacksDao.getworkingFeedbacksList(id);
		model.addAttribute("feedbacksList", feedbacks);
	    
	    model.addAttribute("username", SecurityContextHolder.getContext().getAuthentication().getName());
	    
	    // Lay danh sach Tien do cong viec
	    int order = 1;
		String column = "CREATE_DATE";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		List<W_WORKING_PROCESSES> works_processes = processesDao.getWorkingProcessesListFilter("", "", id, column, order == 1 ? "ASC" : "DESC");
		model.addAttribute("works_processesList", works_processes);
		model.addAttribute("id_work_mana", id);
		
		//ay danh sach cong viec lien quan
		int order2 = 1;
		String column2 = "MA_CONG_VIEC";
		try {
			order2 = Integer.parseInt(request.getParameter((new ParamEncoder("item2").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column2 = request.getParameter((new ParamEncoder("item2").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		List<W_WORKING_MANAGEMENTS> works_childrenList =working_managementDao.getWorkingChildren(id,order == 1 ? "ASC" : "DESC",column2);
		
		model.addAttribute("works_childrenList", works_childrenList);
		model.addAttribute("deptCode", deptCode);
		model.addAttribute("received", received);
		return "jsp/works_contentDetails";
	}
	
	
	@RequestMapping(value = "formContentDetails", method = RequestMethod.POST)
	public String onSubmitContentDetails(@RequestParam(required = false) Integer id,
			@RequestParam(required = false) String actualDate,
			@RequestParam(required = false) Integer id_work_types, 
			@RequestParam(required = false) String finishRate,
			@RequestParam(required = false) String danhGia,
			@RequestParam(required = false) String tinhTrang,
			@RequestParam(required = false) String action,
			@RequestParam(required = false) String type,
			@RequestParam(required = false) String note,
			@RequestParam(required = false) String isAllowRate,
			@RequestParam(required = false) Integer statusUpdate,
			@RequestParam(required = false) String assessResult,
			@RequestParam(required = false) String assignDate,
			@RequestParam(required = false) String estimateDate,
			@RequestParam(required = false) String received,
			@RequestParam(required = false) String fileId,
			@RequestParam(required = false) String  nguoiNhanViec,
			
			@ModelAttribute("w_working_details") @Valid W_WORKING_MANAGEMENTS w_working_details, BindingResult result, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws ParseException{
		
		
			
		List<SYS_PARAMETER> getTinhtrangList = sysParameterDao.getSysParameterByCode("TINH_TRANG_WORKS_MANA");
		model.addAttribute("getTinhtrangList", getTinhtrangList);
		List<SYS_PARAMETER> getDanhGiaList = sysParameterDao.getSysParameterByCode("ASSESS_WORK");
		model.addAttribute("getDanhGiaList", getDanhGiaList);
		model.addAttribute("assessResult", assessResult);
		model.addAttribute("type", type);
		model.addAttribute("note", note);
		model.addAttribute("received", received);
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<MDepartment> deptList = mDepartmentDAO.getDepartmentOfUser(username);
		model.addAttribute("username", username);
		model.addAttribute("isAllowRate", isAllowRate);
		model.addAttribute("statusUpdate", statusUpdate);
		model.addAttribute("tinhTrang", tinhTrang);
		model.addAttribute("finishRate", finishRate);
		model.addAttribute("actualDate", actualDate);
		model.addAttribute("id_work_types", id_work_types);
		
		
		// Neu user thuoc to -> chon mac dinh la to do
		// Neu khong -> chon tat ca
		String deptCode = "";
		for(MDepartment dept : deptList)
		{
			deptCode = dept.getDeptCode();
			
		}
		model.addAttribute("deptCode", deptCode);
		
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
		if (actualDate!=null&&!actualDate.equals(""))
		{
			try{
				w_working_details.setActualDate(df.parse(actualDate));
			}
			catch(Exception e)
			{
				w_working_details.setActualDate(df1.parse(actualDate));
			}
		}
		w_working_details.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());		
		w_working_details.setId(id);
		w_working_details.setTinhTrang(tinhTrang);
		w_working_details.setAssessResult(assessResult);
		if(finishRate == null||finishRate.equalsIgnoreCase(""))
			finishRate = "0";
		if(danhGia == null)
			danhGia = "";
		w_working_details.setFinishRate(Integer.parseInt(finishRate));
		w_working_details.setDanhGia(danhGia);
		if (statusUpdate==1)
		{
			if (result.hasErrors()) {
				if (result.hasFieldErrors("assignDate"))
					model.addAttribute("assignDateError", "assignDateError");
				if (result.hasFieldErrors("estimateDate"))
					model.addAttribute("estimateDateError", "estimateDateError");
				return "jsp/works_contentDetails";
			}
			
			try
			{
				if(!assignDate.equals(""))
					w_working_details.setAssignDate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(assignDate));
				if(!estimateDate.equals(""))
					w_working_details.setEstimateDate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(estimateDate));
			}
			catch(Exception e){
			}
			if (nguoiNhanViec!=null && !nguoiNhanViec.equals(""))
			{
				w_working_details.setNguoiNhanViec(nguoiNhanViec);
			}
			
			if (w_working_details.getNguoiChuTri()!=null)
			{
				SysUsers userLogin = sysUsersDao.selectByPrimaryKey(w_working_details.getNguoiChuTri());
				w_working_details.setDept(userLogin.getMaPhong());
			}
			
			Integer id_mana= null;
			if (w_working_details.getActualDate()!=null )
			{
				if (w_working_details.getEstimateDate().getTime() - w_working_details.getActualDate().getTime() < 0)
				{
					w_working_details.setAssessResult("Không đạt");
				}
				else
				{
					w_working_details.setAssessResult("Đạt");
				}
			}
			w_working_details.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			working_managementDao.updateByPrimaryKey(w_working_details);
			/*filePath = tepCongVan(file, working_managements.getId(), request);*/
			id_mana=w_working_details.getId();
			System.out.println("fileId: "+fileId);
			if(fileId != "" && fileId != null){
				String[] fileList = fileId.split(",");
				for(int i=0;i<fileList.length;i++){
					M_FILE_ATTACHMENT record = new M_FILE_ATTACHMENT();
					record.setIdWorkMana(id_mana);
					if(!fileList[i].equals(""))
						record.setId(Integer.parseInt(fileList[i]));
					attachmentDao.updateByPrimaryKeySelective(record);
				}
			}
			if (!action.equalsIgnoreCase("sendMail"))
			{
				saveMessageKey(request, "messsage.confirm.updatesuccess");
			}
			working_managementDao.saveManagerSend(w_working_details.getId(),w_working_details.getNguoiChuTri(),w_working_details.getNguoiNhanViec(),w_working_details.getFinishRate(),w_working_details.getNguoiGiaoViec());
			
		}
		else
		{
				working_managementDao.updateByprimaryKeyForActualDate(w_working_details);
				
				if (!action.equals("send"))
				{
					saveMessage(request, "Cập nhật trạng thái công việc thành công.");
				}
				working_managementDao.saveManagerSend(w_working_details.getId(),w_working_details.getNguoiChuTri(),w_working_details.getNguoiNhanViec(),w_working_details.getFinishRate(),w_working_details.getNguoiGiaoViec());
		
		}
		
		/*W_WORKING_MANAGEMENTS w_working_details = (id == null) ? new W_WORKING_MANAGEMENTS() : working_managementDao.selectJoinByPrimaryKey(id);
		model.addAttribute("w_working_details", w_working_details);
		
		working_managementDao.saveManagerSend(w_working_details.getId(),w_working_details.getNguoiChuTri(),w_working_details.getNguoiNhanViec(),w_working_details.getFinishRate(),w_working_details.getNguoiGiaoViec());
		*/	
		if (action.equals("send")) {
			
			String kq = sendMailWork(w_working_details,id,deptCode);
			//Gui tin nhan
			String kqnt= sendSMS(w_working_details);
			if (kq.equalsIgnoreCase("F"))
			{
				saveMessageKey(request, "messsage.confirm.mailFall");
			}
			else
			{
				saveMessageKey(request, "messsage.confirm.mailsuccess");
			}
			
		}
		
		return "redirect:formContentDetails.htm?id=" + id+"&type="+type;
	}
	
	//FORM TIẾN ĐỘ CÔNG VIỆC - works_processesList.jsp
	@RequestMapping(value = "processes") // Sử dụng cho cả method GET VÀ POST
	public ModelAndView showProcessesList(@ModelAttribute("filterProcesses") W_WORKING_PROCESSES filter,
			@RequestParam(required = false) Integer id_work_mana,
			 ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		model.addAttribute("id_work_mana", id_work_mana);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "TienDoCongViec_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
				
		if(filter.getTitle() != null)
			filter.setTitle(filter.getTitle().trim());
		if(filter.getRemark() != null)
			filter.setRemark(filter.getRemark().trim());
		if(filter.getAssess() != null)
			filter.setAssess(filter.getAssess().trim());
		
		int order = 1;
		String column = "CREATE_DATE";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		List<W_WORKING_PROCESSES> works_processes = processesDao.getWorkingProcessesListFilter(filter.getTitle(), filter.getAssess(), id_work_mana, column, order == 1 ? "ASC" : "DESC");
		model.addAttribute("works_processesList", works_processes);
		
		List<SYS_PARAMETER> sysParameterByCodeList = sysParameterDao.getSysParameterByCode("TINH_TRANG_WORKS_MANA");
		model.addAttribute("sysParameterByCodeList", sysParameterByCodeList);
		
		model.addAttribute("tinhTrangCBB", filter.getAssess());
		
		return new ModelAndView("jsp/works_processesList");
	}
	
	@RequestMapping(value = "deleteProcesses", method = RequestMethod.GET)
	public String onDeleteProcesses(@RequestParam (required = true) Integer id,
			@RequestParam(required = false) Integer id_work_mana, HttpServletRequest request, HttpServletResponse response) {
		try
		{
			W_WORKING_FEEDBACKS filter = new W_WORKING_FEEDBACKS();
			filter.setIdProcesses(id);
			List<W_WORKING_FEEDBACKS> feedbacksListByIdProcesses = feedbacksDao.filter(filter);
			
			for(int i=0;i<feedbacksListByIdProcesses.size();i++)
			{
				feedbacksDao.deleteByPrimaryKey(feedbacksListByIdProcesses.get(i).getId());
			}
			processesDao.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch(Exception e)
		{
			saveMessageKey(request, "messsage.confirm.deletefailure");
		}

		return "redirect:formContentDetails.htm?id=" + id_work_mana;
	}
	
	@RequestMapping(value = "formProcesses", method = RequestMethod.GET)
	public String showFormProcesses(@RequestParam(required = false) Integer id,
			@RequestParam(required = false) Integer id_work_mana, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		model.addAttribute("id_work_mana",id_work_mana);
		if(id == null){
			model.addAttribute("ProcessesAddEdit", "Y");
		}
		else
		{
			model.addAttribute("ProcessesAddEdit", "N");
		}
		List<SYS_PARAMETER> sysParameterByCodeList = sysParameterDao.getSysParameterByCode("TINH_TRANG_WORKS_MANA");
		model.addAttribute("sysParameterByCodeList", sysParameterByCodeList);
		
		W_WORKING_PROCESSES working_processes = (id == null) ? new W_WORKING_PROCESSES() : processesDao.selectByPrimaryKey(id);
		working_processes.setIdWorks(id_work_mana);
		model.addAttribute("w_working_processes",working_processes);
		model.addAttribute("tinhTrangCBB", working_processes.getAssess());
		
		return "jsp/works_processesForm";
	}

	
	
	@RequestMapping(value = "formProcesses", method = RequestMethod.POST)
	public String onSubmitProcesses(@ModelAttribute("w_working_processes") @Valid W_WORKING_PROCESSES works_processes,
			 BindingResult result, ModelMap model, HttpServletRequest request, 
			 @RequestParam(required = false) String actualDate,
			 @RequestParam(required = false) String estimateDate, HttpServletResponse response) throws ParseException {
		String username= SecurityContextHolder.getContext().getAuthentication().getName();
		//Ném lỗi
		if (result.hasErrors()) {
			if(works_processes.getId() == null){
				model.addAttribute("ProcessesAddEdit", "Y");
			}
			else
			{
				model.addAttribute("ProcessesAddEdit", "N");
			}
			if(result.hasFieldErrors("estimateDate"))
				model.addAttribute("estimateDateError", "estimateDateError");
			if(result.hasFieldErrors("actualDate"))
				model.addAttribute("actualDateError", "actualDateError");
			model.addAttribute("tinhTrangCBB", works_processes.getAssess());
			
			List<SYS_PARAMETER> sysParameterByCodeList = sysParameterDao.getSysParameterByCode("TINH_TRANG_WORKS_MANA");
			model.addAttribute("sysParameterByCodeList", sysParameterByCodeList);
			
            return "jsp/works_processesForm";
		}
		try
		{
			if(!actualDate.equals(""))
				works_processes.setActualDate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(actualDate));
			if(!estimateDate.equals(""))
				works_processes.setEstimateDate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(estimateDate));
		}
		catch(Exception e){}	
		
		if(processesDao.selectByPrimaryKey(works_processes.getId())== null)
		{
			works_processes.setCreatedBy(username);
			
			processesDao.insert(works_processes);
			saveMessageKey(request, "messsage.confirm.addsuccess");
			
			// Update trang thai cong viec
			List<W_WORKING_PROCESSES> updateTrangThai = processesDao.getWorkingProcessesListByIdWorks(works_processes.getIdWorks());
			if(updateTrangThai.size() > 0){
				W_WORKING_MANAGEMENTS record = new W_WORKING_MANAGEMENTS();
				record.setTinhTrang("DANG_THUC_HIEN");
				record.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				record.setId(works_processes.getIdWorks());
				working_managementDao.updateTinhTrangCV(record);
			}
		}
		else
		{
			works_processes.setModifiedBy(username);
			
			processesDao.updateByPrimaryKey(works_processes);
			saveMessageKey(request, "messsage.confirm.updatesuccess");
		}
		return "redirect:formContentDetails.htm?id=" + works_processes.getIdWorks();
	}
	
	@RequestMapping("/exportTepCongVan")
	public ModelAndView export(@RequestParam(required = true) String filePath,
			@RequestParam(required = false) boolean isSvgFileName, @RequestParam(required = false) String filename, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		try{
			File imageTempFile = new File(filePath);
			response.setContentLength((int) imageTempFile.length());
			response.setHeader("Content-Disposition", "attachment; filename=\"" + StringTools.removeAccent(filename) + "\"");
	
			FileCopyUtils.copy(new FileInputStream(imageTempFile), response.getOutputStream());
		}
		catch(Exception e){}
		return null;
	}
	
	@RequestMapping("deleteFile")
	public @ResponseBody
	List<M_FILE_ATTACHMENT> deleteFile(@RequestParam(required = false) String id,
			@RequestParam(required = false) String idWorkMana,
			HttpServletRequest request, HttpServletResponse response){
		
		attachmentDao.deleteByPrimaryKey(Integer.parseInt(id));
		List<M_FILE_ATTACHMENT> record = null;
		try{
			record = attachmentDao.fileName_file(idWorkMana);
		}
		catch(Exception e){}
		
		return record;
	}
/* phuong thuc gui mail cong viec. AnhCTV: 27/12/2013*/
	
	private String sendMailWork(W_WORKING_MANAGEMENTS working, Integer idMana,String deptCode) {
		
		
		//cau hinh mail
		List<CSystemConfigs> p = cSystemConfigsDAO.getSystemConfigMail();
		Properties props = new Properties();
		for (CSystemConfigs item: p) {
			props.put(item.getParamName(), item.getParamValue());
			//System.out.println(item.getName() + "-" + item.getValue());
		}
		String password="";
		String userFrom = "";
		List<CSystemConfigs> configList= cSystemConfigsDAO.getSystemConfigMailDefault();
		for (CSystemConfigs cSystemConfigs : configList) {
			if (cSystemConfigs.getParamName().equalsIgnoreCase("sendmail.userFrom"))
			{
				userFrom =  cSystemConfigs.getParamValue();
			}
			if (cSystemConfigs.getParamName().equalsIgnoreCase("sendmail.passwordFrom"))
			{
				password =  cSystemConfigs.getParamValue();
			}
			
		}
		
		List<SysMailParameterMaster> emailDefault = sysMailParamenterMasterDAO.getMailParameterMasterListAll(null,"W_WORKING_MANAGEMENTS");
		String headerContent="";
		String footerContent="";
		String mailToDefault="";
		if(emailDefault.size() > 0) {
			headerContent = emailDefault.get(0).getContentHeader();
			footerContent = emailDefault.get(0).getContentRooter();
			mailToDefault = emailDefault.get(0).getMailTo();
		}
		
		String mailTo="";
		String ngChutri ="";
		String ngNhanViec ="";
		String ngGiaoViec="";
		String con="";
		String subjet ="";
		String errorSend="";
		String status="";
		String ccEmail="";
		// nguoi nhan
		if (working.getNguoiChuTri()!=null &&!working.getNguoiChuTri().equals(""))
		{
			ngChutri = working.getNguoiChuTri();
			SysUsers getEmailOfNguoiGiaoViec = sysUsersDao.selectSysUsersByUsername(ngChutri);
			if (getEmailOfNguoiGiaoViec!=null)
			{
				ngChutri =getEmailOfNguoiGiaoViec.getEmail();
				mailTo+=con+ngChutri;
				con=",";
			}
		}
		if (working.getNguoiNhanViec()!=null &&!working.getNguoiNhanViec().equals(""))
		{
			ngNhanViec = working.getNguoiNhanViec();
			String[] userL = ngNhanViec.split(",");
			for (String user : userL) {
				SysUsers getEmailOfNguoiGiaoViec = sysUsersDao.selectSysUsersByUsername(user.trim());
				if (getEmailOfNguoiGiaoViec!=null)
					{
						mailTo+=con+getEmailOfNguoiGiaoViec.getEmail();
						con=",";
					}
			}
		}
		if (working.getCcEmail()!=null &&!working.getCcEmail().equals(""))
		{
			ccEmail = working.getCcEmail();
		}
		
		// subject
		if (working.getNguoiGiaoViec()!=null &&!working.getNguoiGiaoViec().equals(""))
		{
			ngGiaoViec = working.getNguoiGiaoViec();
		//	ngGiaoViec = ngGiaoViec.substring(ngGiaoViec.indexOf("(")+1, ngGiaoViec.indexOf(")")-1);
			List<SysUsers> userChuTriList = sysUsersDao.getUserByUsername(ngGiaoViec);
			if (userChuTriList.size()>0)
			{
				if (userChuTriList.get(0).getSubTeam()!=null&&!userChuTriList.get(0).getSubTeam().equals(""))
				{
					ngGiaoViec = userChuTriList.get(0).getSubTeam();
				}
				else
				{
					if (userChuTriList.get(0).getTeam()!=null&&!userChuTriList.get(0).getTeam().equals(""))
					{
						ngGiaoViec = userChuTriList.get(0).getTeam();
					}
					else
					{
						if (userChuTriList.get(0).getAbbreviated()!=null&&!userChuTriList.get(0).getAbbreviated().equals(""))
						{
							ngGiaoViec = userChuTriList.get(0).getAbbreviated();
						}
						else
						{
							ngGiaoViec = userChuTriList.get(0).getMaPhong();
						}
					}
				}
				subjet+=ngGiaoViec;
				
			}
		}
		subjet+=". Giao việc: ";
		if (working.getTenCongViec()!=null &&!working.getTenCongViec().equals(""))
		{
			subjet+=working.getTenCongViec();
		}
		// content
		String content=null;
		content= noidungMail(working,headerContent,footerContent,deptCode);
		
		//file dinh kem
		List<M_FILE_ATTACHMENT> file_attachment1 = attachmentDao.fileName_file(String.valueOf(idMana));
		List<String> fileName = new ArrayList<String>();
		for (M_FILE_ATTACHMENT file : file_attachment1) {
			if (file!=null)
			{
				fileName.add(file.getFilePath());
			}
		}
		
		//gui mail
		try
		{
			if (mailTo==null||(mailToDefault!=null && mailToDefault.equals("")))
			{
				mailTo = mailToDefault;
			}
			Mail mail = new Mail(props, fileName, userFrom, password,
					mailTo,ccEmail,null, subjet, content);
			/*Mail mail = new Mail(props, fileName, userFrom, password,
					"caoanhhus@gmail.com", subjet, content);
			*/
			String result = mail.send();
			
			if (result != null) {
				errorSend="Error in sending mail";
				status="F";
				
			}
			
		}
		catch(Exception ex)
		{
			System.out.println("Gui mail that bai");
			errorSend="Error in sending mail";
			status="F";
		}
		cSystemConfigsDAO.saveMail(subjet,mailTo,errorSend,status,null,"Gửi bằng tay",null);
		
		return status;
	}


	private String noidungMail(W_WORKING_MANAGEMENTS working,
			String headerContent, String footerContent,String deptCode) {
		
		String content ="";
		if (headerContent!=null&&!headerContent.equalsIgnoreCase("null"))
		{
			content = "<span>"+headerContent+"</span>";
		}
		
		content=content + "<br/>";
		content=content + "<center><div>";
		content=content +"<table style=\" border-collapse:collapse;border:1px solid;width:100%;font-size: 9pt;\" cellpadding=\"5\" cellspacing=\"0\" name = \"W_WORKING_MANAGEMENTS\" >";
		// header
		content=content + "<thead>";
		content=content + "<tr align=\"center\" style=\"font-weight: bold;background-color: #00B050;color:white;\">";
		content=content + "<th style=\"border:1px solid;\" >Tên công việc</th>";
		content=content + "<th style=\"border:1px solid;\" >Người giao</th>";
		content=content + "<th style=\"border:1px solid;\" >Người chủ trì</th>";
		content=content + "<th style=\"border:1px solid;\" > Người thực hiện </th>";
		content=content + "<th style=\"border:1px solid;\" > Ngày giao</th>";
		content=content + "<th style=\"border:1px solid;\" > Hạn thực hiện </th>";
		content=content + "<th style=\"border:1px solid;\" > Nội dung công việc </th>";
		content=content + "</tr>";
		content=content + "</thead>";
		//body
		String ngaygiao="";
		if (working.getNgayGiaoViec()!=null&&!working.getNgayGiaoViec().equals(""))
		{
			ngaygiao=working.getNgayGiaoViec();
		}
		String ngayHT="";
		if (working.getNgayGiaoHoanThanh()!=null&&!working.getNgayGiaoHoanThanh().equals(""))
		{
			ngayHT=working.getNgayGiaoHoanThanh();
		}
		String noiDung="";
		if (working.getNoiDung()!=null&&!working.getNoiDung().equals(""))
		{
			noiDung=working.getNoiDung();
		}
		String nguoiNhan="";
		if (working.getNguoiNhanViec()!=null&&!working.getNguoiNhanViec().equals(""))
		{
			nguoiNhan=working.getNguoiNhanViec();
		}
		String nguoiCtri="";
		if (working.getNguoiChuTri()!=null&&!working.getNguoiChuTri().equals(""))
		{
			nguoiCtri=working.getNguoiChuTri()+" <p style=\"color:red;\">("+deptCode+")</p>";
		}
		String nguoiGiao="";
		if (working.getNguoiGiaoViec()!=null&&!working.getNguoiGiaoViec().equals(""))
		{
			nguoiGiao=working.getNguoiGiaoViec()+" <p style=\"color:red;\">("+deptCode+")</p>";
		}
		content=content + "<tbody>";
		content=content + "<tr>";
		content=content +"<td style=\"border:1px solid;width:300px;\">"+working.getTenCongViec()+"</td>";
		content=content +"<td style=\"border:1px solid;width:150px;\">"+nguoiGiao+"</td>";
		content=content +"<td style=\"border:1px solid;width:150px;\">"+nguoiCtri+"</td>";
		content=content +"<td style=\"border:1px solid;width:180px;\">"+nguoiNhan+"</td>";
		
		content=content +"<td style=\"border:1px solid;width:130px;\">"+ngaygiao+"</td>";
		content=content +"<td style=\"border:1px solid;width:130px;\">"+ngayHT+"</td>";
		content=content +"<td style=\"border:1px solid;width:500px;\">"+noiDung+"</td>";
		content=content + "</tr>";
		content=content + "</tbody>";
		content=content +"</table>";
		content=content +"</div></center>";
		if (footerContent!=null&&!footerContent.equalsIgnoreCase("null"))
		{
			content=content + "<br/><span>"+footerContent+"</span>";
		}
		
		return content;
		
	}		
	
	private String sendSMS(W_WORKING_MANAGEMENTS working) {
		SmsContents record = new SmsContents();
		
		String ngChutri="";
		String ngNhanViec="";
		String smsTo="";
		String con="";
		// nguoi nhan
		if (working.getNguoiChuTri()!=null &&!working.getNguoiChuTri().equals(""))
		{
			ngChutri = working.getNguoiChuTri();
			SysUsers getEmailOfNguoiGiaoViec = sysUsersDao.selectSysUsersByUsername(ngChutri);
			if (getEmailOfNguoiGiaoViec!=null)
			{
				ngChutri =getEmailOfNguoiGiaoViec.getPhone();
				smsTo+=con+ngChutri;
				con=",";
			}
		}
		if (working.getNguoiNhanViec()!=null &&!working.getNguoiNhanViec().equals(""))
		{
			ngNhanViec = working.getNguoiNhanViec();
			String[] userL = ngNhanViec.split(",");
			for (String user : userL) {
				SysUsers getEmailOfNguoiGiaoViec = sysUsersDao.selectSysUsersByUsername(user.trim());
				if (getEmailOfNguoiGiaoViec!=null)
					{
					smsTo+=con+getEmailOfNguoiGiaoViec.getPhone();
						con=",";
					}
			}
		}
		record.setIsdn(smsTo);
		record.setAlarmType("W_WORKING_MANAGEMENTS");
		if (working.getId()!=null )
		{
			record.setMessage(String.valueOf(working.getId()));
		}
		record.setCreateTime(new Date());
		try
		{
			working_managementDao.sendSMS(record);
		}
		catch(Exception exp)
		{
			return "F";
		}
		return "T";
	}
	
	@RequestMapping(value="upload/docpa", method = RequestMethod.POST)
	public @ResponseBody
	Integer uploadProccess(MultipartHttpServletRequest req, HttpServletRequest request, HttpServletResponse response) throws IOException {

		MultipartFile file = req.getFile("Filedata");
		
		// Tên file
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyyhhmmss");
		String dateNow = formatter.format(currentDate.getTime());
		String fileName = dateNow+"_"+StringTools.removeAccent(file.getOriginalFilename());
		String path="";
		CSystemConfigs config = cSystemConfigsDAO.selectByPrimaryKey("upload.dir");
		if (config!=null)
		{
			path=config.getParamValue();
		}
		if(file.getOriginalFilename() != "") 
		{
			
			// Cấu hình một số cài đặt
	        DiskFileItemFactory factory = new DiskFileItemFactory();
	        factory.setSizeThreshold(THRESHOLD_SIZE);
	        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
			
	        ServletFileUpload upload = new ServletFileUpload(factory);
	        upload.setFileSizeMax(MAX_FILE_SIZE);
	        upload.setSizeMax(REQUEST_SIZE); 
	        // Xây dựng đường dẫn thư mục để lưu trữ các tập tin tải lên
	      //  String uploadPath = request.getSession().getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;
	       String uploadPath=path.concat("/work");
	        // Tạo ra các thư mục nếu nó không tồn tại
	        File uploadDir = new File(uploadPath);
	        if (!uploadDir.exists()) {
	            uploadDir.mkdirs();
	        }
	        
	        String filePath = uploadPath.concat("/").concat(fileName);
	        File dest = new File(filePath);
	        try {
	        	
	        	file.transferTo(dest);  //Copy file	
	        	
	        	M_FILE_ATTACHMENT record = new M_FILE_ATTACHMENT();
				record.setFileName(fileName);
				record.setFilePath(filePath);
				
				attachmentDao.insertAndResult(record);
				return record.getId();
	        } catch (IllegalStateException e) {
	            e.printStackTrace();     
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
		return null;
	}
	
	@RequestMapping(value="getFile", method = RequestMethod.GET)
    public @ResponseBody 
    M_FILE_ATTACHMENT dsgui(@RequestParam(required = false) Integer fileId, HttpServletRequest request, HttpServletResponse response) {
		
		M_FILE_ATTACHMENT file = attachmentDao.selectByPrimaryKey(fileId);
		
		return file;
	}
	
	
	@RequestMapping(value="download", method = RequestMethod.GET)
	public HttpEntity<byte[]> download(@RequestParam(required = false) Integer id, @RequestParam(required = false) String newpass, Model model) {
		
		List<SYS_PARAMETER> directoryFolder = sysParameterDao.getSPByCodeAndName("SYSTEM_UPLOAD", "upload.directory");
		String rootFolderImage = directoryFolder.get(0).getValue();
		
		M_FILE_ATTACHMENT fileA = attachmentDao.selectByPrimaryKey(id);
		/*String fileName = fileA.getFileName();
		String filePath = fileA.getFilePath();
		
		String[] sList = fileName.split(".");
		System.out.println("fileName: "+fileName);
		System.out.println("size: "+sList.length);
		//String fileExtend = sList[sList.length];
		String fileExtend = "";
		try
		{
			fileExtend= fileName.substring(fileName.indexOf("."), fileName.length());
		}
		catch(Exception exp){}
		File file = new File(filePath);
		
		if (!file.exists()) {
			file = new File(rootFolderImage.concat("noprofile.png"));
		}
		
		byte documentBody[] = null;
		try {
			FileInputStream fin = new FileInputStream(file);
			documentBody = new byte[(int)file.length()];
			
			fin.read(documentBody);
			
			fin.close();
		} catch(Exception e) {
				return null;
		}

	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(new MediaType("application", fileExtend));
	    header.set("Content-Disposition", "attachment; filename=\"" +fileName + "\"");
	    header.setContentLength(documentBody.length);

	    return new HttpEntity<byte[]>(documentBody, header);*/
		String fileName = fileA.getFileName();
		String filePath = fileA.getFilePath();
		
		String[] sList = fileName.split(".");
		System.out.println("fileName: "+fileName);
		System.out.println("size: "+sList.length);
		//String fileExtend = sList[sList.length];
		String fileExtend = "";
		try
		{
			//fileExtend= filePath.substring(filePath.indexOf("."), filePath.length());
				fileExtend= filePath.substring(filePath.indexOf(".")+1, filePath.length());
		}
		catch(Exception exp){}
		File file = new File(filePath);
		
		if (!file.exists()) {
			file = new File(rootFolderImage.concat("noprofile.png"));
		}
		
		byte documentBody[] = null;
		try {
			FileInputStream fin = new FileInputStream(file);
			documentBody = new byte[(int)file.length()];
			
			fin.read(documentBody);
			
			fin.close();
		} catch(Exception e) {
				return null;
		}

	    HttpHeaders header = new HttpHeaders();
	    header.setContentType(new MediaType("application", fileExtend));
	    header.set("Content-Disposition", "attachment; filename=\"" +StringTools.removeAccent(fileName) + "\"");
	    header.setContentLength(documentBody.length);

	    return new HttpEntity<byte[]>(documentBody, header);
	}
	
	private void copyFileUsingApacheCommonsIO(String pathsource, String pathdest)
	         throws IOException {
		File source = new File(pathsource);
		File dest = new File(pathdest); 
	    FileUtils.copyFile(source, dest);
	}
	
	
	@RequestMapping("export")
	public String export(@RequestParam(required = false) Integer idWorkTypes,@RequestParam(required = false) String maCongViec,
			@RequestParam(required = false) String tenCongViec, @RequestParam(required = false) String nguoiGiaoViec,
			@RequestParam(required = false) String nguoiChuTri, 
			@RequestParam(required = false) String nguoiNhanViec, @RequestParam(required = false) String tinhTrang,
			@RequestParam(required = false) String actualDateFrom,@RequestParam(required = false) String actualDateTo,
			@RequestParam(required = false) String assignDateFrom,
			@RequestParam(required = false) String assignDateTo,@RequestParam(required = false) String column,
			@RequestParam(required = false) String order,@RequestParam(required = false) String received,
			@RequestParam(required = false) String username,
			@RequestParam(required = false) String deptName,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if (maCongViec==null)
			maCongViec = "";
		if (tenCongViec==null)
			tenCongViec = "";
		if (nguoiGiaoViec==null)
			nguoiGiaoViec = "";
		if (nguoiChuTri==null)
			nguoiChuTri="";
		if (nguoiNhanViec==null)
			nguoiNhanViec=""; 
		if (tinhTrang==null)
			tinhTrang= "";
		if (actualDateFrom==null)
			actualDateFrom="";
		if (actualDateTo==null)
			actualDateTo="";
		// temp file
		String basePath = request.getSession().getServletContext().getRealPath("");
		String dataDir = basePath + "/upload";

		String tempName = UUID.randomUUID().toString();

		String ext = "xls";

		String outfile = tempName + "." + ext;
		// return
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "DanhSachCongViec_" + dateNow;
				
		File tempFile = new File(dataDir + "/" + outfile);
		/*List<W_WORKING_MANAGEMENTS> dataList = working_managementDao.getWorkingManagementsFilter(idWorkTypes, maCongViec, tenCongViec, nguoiGiaoViec, nguoiChuTri, nguoiNhanViec, 
				tinhTrang, actualDateFrom, actualDateTo, assignDateFrom, assignDateTo, column, order,received,username);*/
		//exportworkMana(tempFile, dataList);
		List<String> paramList = new ArrayList<String>();
		String idWorkStr="";
		if (idWorkTypes!=null)
		{
			idWorkStr=String.valueOf(idWorkTypes);
		}
		paramList.add(idWorkStr);
		paramList.add(maCongViec);
		paramList.add(tenCongViec);
		paramList.add(nguoiGiaoViec);
		paramList.add(nguoiChuTri);
		paramList.add(nguoiNhanViec);
		paramList.add(tinhTrang);
		paramList.add(actualDateFrom);
		paramList.add(actualDateTo);
		paramList.add(assignDateFrom);
		paramList.add(assignDateTo);
		paramList.add(received);
		paramList.add(username);
		paramList.add(deptName);
		paramList.add(column);
		paramList.add(order);
		paramList.add(null);
		paramList.add(null);
		paramList.add(null);
		List<CTableConfig> columnList = cTableConfigDAO.getColumnList("W_WORKING_MANAGERMENT");
		ExportTools.exportToExcel("PK_W_WORKING_MANAGEMENTS_MLMN.GET_WORKING_MANAGEMENTS_FILTER(?, ?, ?, ?, ?,?, ?, ?, ?, ?,?, ?, ?, ?, ?,?,?,?,?,?)",paramList,tempFile,exportFileName, "DanhSachCongViec","Danh sách công việc",columnList,"Y");
		
		
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + exportFileName + "." + ext + "\"");
		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());
		tempFile.delete();
		
		return null;
	}
	private void exportworkMana(File tempFile, List<W_WORKING_MANAGEMENTS> dataList ) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("Works", 0);
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			Label label0 = new Label(0, 0, "Mã công việc",cellFormatHeader);
			sheet.addCell(label0);
			Label label1 = new Label(1, 0, "Tên công việc",cellFormatHeader);
			sheet.addCell(label1);
			Label label2 = new Label(2, 0, "Tóm tắt nội dung",cellFormatHeader);
			sheet.addCell(label2);
			Label label3 = new Label(3, 0, "Nội dung",cellFormatHeader);
			sheet.addCell(label3);
			Label label4 = new Label(4, 0, "Người giao việc",cellFormatHeader);
			sheet.addCell(label4);
			Label label38 = new Label(5, 0, "Người chủ trì",cellFormatHeader);
			sheet.addCell(label38);
			Label label5 = new Label(6, 0, "Người nhận việc",cellFormatHeader);
			sheet.addCell(label5);
			Label label6 = new Label(7, 0, "Ngày phân công",cellFormatHeader);
			sheet.addCell(label6);
			Label label7 = new Label(8, 0, "Ngày KT dự tính",cellFormatHeader);
			sheet.addCell(label7);
			Label label8 = new Label(9, 0, "Ngày KT thực tế",cellFormatHeader);
			sheet.addCell(label8);
			Label label9 = new Label(10, 0, "Trạng thái",cellFormatHeader);
			sheet.addCell(label9);
			Label label10 = new Label(11	, 0, "% HT",cellFormatHeader);
			sheet.addCell(label10);
			Label label11 = new Label(12, 0, "Comments",cellFormatHeader);
			sheet.addCell(label11);
			Label label12 = new Label(13, 0, "Đánh giá",cellFormatHeader);
			sheet.addCell(label12);
			Label label13 = new Label(14, 0, "Người tạo",cellFormatHeader);
			sheet.addCell(label13);
			Label label14 = new Label(15, 0, "CC Mail",cellFormatHeader);
			sheet.addCell(label14);
			Label label15 = new Label(16, 0, "Tên trạm",cellFormatHeader);
			sheet.addCell(label15);
			Label label16 = new Label(17, 0, "Kinh độ",cellFormatHeader);
			sheet.addCell(label16);
			Label label17 = new Label(18, 0, "Vĩ độ",cellFormatHeader);
			sheet.addCell(label17);
			Label label18 = new Label(19, 0, "Ngày tạo",cellFormatHeader);
			sheet.addCell(label18);
			Label label19 = new Label(20, 0, "Người sửa",cellFormatHeader);
			sheet.addCell(label19);
			Label label20 = new Label(21, 0, "Ngày sửa",cellFormatHeader);
			sheet.addCell(label20);
			Label label21 = new Label(22, 0, "Mã loại công việc",cellFormatHeader);
			sheet.addCell(label21);
			Label label22 = new Label(23, 0, "Phòng ban",cellFormatHeader);
			sheet.addCell(label22);
			Label label23 = new Label(24, 0, "Tổ",cellFormatHeader);
			sheet.addCell(label23);
			Label label24 = new Label(25, 0, "Ngày trực",cellFormatHeader);
			sheet.addCell(label24);
			Label label40 = new Label(26, 0, "Ca trực",cellFormatHeader);
			sheet.addCell(label40);
			Label label41 = new Label(27, 0, "Tên công việc cha",cellFormatHeader);
			sheet.addCell(label41);

			int i = 1;
			for (W_WORKING_MANAGEMENTS workMana : dataList) {
				Label maCongViec = new Label(0, i,workMana.getMaCongViec(), cellFormat);
				sheet.addCell(maCongViec);
				Label tenCongViec = new Label(1, i, ExportTools.unescapeHtml(workMana.getTenCongViec()), cellFormat);
				sheet.addCell(tenCongViec);
				Label tomTatNoiDung = new Label(2, i, ExportTools.unescapeHtml(workMana.getTomTatNoiDung()), cellFormat);
				sheet.addCell(tomTatNoiDung);
				Label noiDung = new Label(3, i, ExportTools.unescapeHtml(workMana.getNoiDung()), cellFormat);
				sheet.addCell(noiDung);
				Label tenNguoiGiaoViec = new Label(4, i, workMana.getTenNguoiGiaoViec(), cellFormat);
				sheet.addCell(tenNguoiGiaoViec);
				Label tenNguoiChuTri = new Label(5, i, workMana.getTenNguoiChuTri(), cellFormat);
				sheet.addCell(tenNguoiChuTri);
				Label tenNguoiNhanViec = new Label(6, i, workMana.getTenNguoiNhanViec(), cellFormat);
				sheet.addCell(tenNguoiNhanViec);
				Label assignDate = new Label(7, i, workMana.getAssignDateStr(), cellFormat);
				sheet.addCell(assignDate);
				Label estimateDate = new Label(8, i, workMana.getEstimateDateStr(), cellFormat);
				sheet.addCell(estimateDate);
				Label actualDate = new Label(9, i, workMana.getActualDateStr(), cellFormat);
				sheet.addCell(actualDate);
				Label tenTinhTrang = new Label(10, i, workMana.getTenTinhTrang(), cellFormat);
				sheet.addCell(tenTinhTrang);
				jxl.write.Number finishRate = new jxl.write.Number(11, i, workMana.getFinishRate()==null?0:workMana.getFinishRate(), cellFormat);
				sheet.addCell(finishRate);
				Label comments = new Label(12, i, workMana.getComments(), cellFormat);
				sheet.addCell(comments);
				Label danhGia = new Label(13, i, workMana.getDanhGia(), cellFormat);
				sheet.addCell(danhGia);
				Label createdBy = new Label(14, i, workMana.getCreatedBy(), cellFormat);
				sheet.addCell(createdBy);
				Label ccEmail = new Label(15, i, workMana.getCcEmail(), cellFormat);
				sheet.addCell(ccEmail);
				Label site = new Label(16, i, workMana.getSite(), cellFormat);
				sheet.addCell(site);
				Label longitude = new Label(17, i, workMana.getLongitude(), cellFormat);
				sheet.addCell(longitude);
				Label latitude = new Label(18, i, workMana.getLatitude(), cellFormat);
				sheet.addCell(latitude);
				Label createDate = new Label(19, i, workMana.getCreateDateStr(), cellFormat);
				sheet.addCell(createDate);
				Label modifiedBy = new Label(20, i, workMana.getModifiedBy(), cellFormat);
				sheet.addCell(modifiedBy);
				Label modifyDate = new Label(21, i, workMana.getModifyDateStr(), cellFormat);
				sheet.addCell(modifyDate);
				jxl.write.Number idWorkTypes = new jxl.write.Number(22, i, workMana.getIdWorkTypes()==null?0:workMana.getIdWorkTypes(), cellFormat);
				sheet.addCell(idWorkTypes);
				Label dept = new Label(23, i, workMana.getDept(), cellFormat);
				sheet.addCell(dept);
				Label team = new Label(24, i, workMana.getTeam(), cellFormat);
				sheet.addCell(team);
				Label dayShift = new Label(25, i, workMana.getDayShiftStr(), cellFormat);
				sheet.addCell(dayShift);
				Label caTruc = new Label(26, i, workMana.getCaTruc(), cellFormat);
				sheet.addCell(caTruc);
				Label workParentName = new Label(27, i, workMana.getWorkParentName(), cellFormat);
				sheet.addCell(workParentName);
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
	
	
}
