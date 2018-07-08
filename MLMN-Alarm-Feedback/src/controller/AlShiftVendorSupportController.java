 package controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import vn.com.vhc.alarm.utils.AL_DateTools; 
import vo.AlManageProject;
import vo.AlShift;
import vo.AlShiftVendorSupport; 
import vo.CHighlightConfigs;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.alarm.utils.AlShiftVendorFilter;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.UploadTools;
import bsh.ParseException;
import dao.AlManageProjectDAO;
import dao.AlShiftDAO;
import dao.AlShiftVendorSupportDAO;
import dao.CHighlightConfigsDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUsersDAO;

@Controller
@RequestMapping("/system/shiftVendorSupport/*")
public class AlShiftVendorSupportController extends BaseController {
	@Autowired
	private AlShiftVendorSupportDAO alShiftVendorDao;  
	@Autowired
	private SYS_PARAMETERDAO  sysParameterDao;
	@Autowired 
	private SysUsersDAO sysUsersDao;
	@Autowired 
	private AlShiftDAO alShiftDAO;
	@Autowired
	private AlManageProjectDAO alManageProjectDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
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
	
	@RequestMapping(value = "list")
	public ModelAndView list(@ModelAttribute("filter") AlShiftVendorFilter filter,  
			@RequestParam(required = false) String sTime,
			@RequestParam(required = false) String eTime,
			@RequestParam(required = false) String regionTk,
			Model model, HttpServletRequest request) {
		
		List<SYS_PARAMETER> title = alShiftVendorDao.titleForm("SHIFT_VENDOR_SUPPORT_LIST");		
		model.addAttribute("title", title.get(0).getValue());
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		if (regionTk==null ||regionTk.equals("")){
			regionTk =userLogin.getRegion();
		}
		boolean checkRegion=false;
		if (userLogin!=null&&regionTk!=null&regionTk.equalsIgnoreCase(userLogin.getRegion())){
			checkRegion= true;
		}
		model.addAttribute("checkRegion", checkRegion);
		model.addAttribute("isRoleManager", userLogin.getIsRoleManager());
		model.addAttribute("regionTk", regionTk);
		int order = 0;
		String column = "";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("alShiftVendor").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("alShiftVendor").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		if (sTime== null
				|| sTime.equals("")
				|| (sTime != null && !sTime.equals("") && DateTools
						.isValid("dd/MM/yyyy HH:mm", sTime) == false)) {
		 
			sTime = "01/01/"+ Calendar.getInstance().get(Calendar.YEAR) +" 00:00";
		}
		filter.setsTime(sTime);
		
		if (eTime == null
				|| eTime.equals("")
				|| (eTime != null && !eTime.equals("") && DateTools
						.isValid("dd/MM/yyyy HH:mm", eTime) == false)) { 
			eTime = "31/12/"+ Calendar.getInstance().get(Calendar.YEAR) +" 23:59";
		}
		filter.seteTime(eTime);
		filter.setRegion(regionTk);
		List<AlShiftVendorSupport> shiftVendorList = new ArrayList<AlShiftVendorSupport>();
		try
		{
			shiftVendorList = alShiftVendorDao.getList(filter, column, order);
		}
		catch (Exception exp)
		{
			shiftVendorList= null;
		}  
		
		List<SYS_PARAMETER> vendorList = sysParameterDao.getSysParameterByCode("VENDOR");
		List<SYS_PARAMETER> supportTypeList = sysParameterDao.getSysParameterByCode("SUPPORT_TYPE");
		model.addAttribute("vendorList", vendorList); 
		model.addAttribute("supportTypeList", supportTypeList);
		
		model.addAttribute("shiftVendorList", shiftVendorList);
		model.addAttribute("sTime", filter.getsTime());
		model.addAttribute("eTime", filter.geteTime()); 
		model.addAttribute("filter", filter);
		 
		//lay danh sach khu vuc
		List<SYS_PARAMETER> regionList = sysParameterDao.getRegionList();
		model.addAttribute("regionList", regionList);
				
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "ListVendorSupport_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		return new ModelAndView("jsp/ShiftFes/vendorSupportList");
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) Integer id, HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		try {
			alShiftVendorDao.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch (Exception e) {
			saveMessageKey(request, "messsage.confirm.deletefailure");
		}
		return "redirect:list.htm";
	}
	
	private void loadData(ModelMap model)
	{
		// Danh sach goi nho cong viec
		List<AlManageProject> jobTitleList = alManageProjectDAO.getProjectUnfinishedList("FOLLOW");		
		String jobTitleArray="var jobTitleList = new Array(";
		String cnId="";
		for (int i=0;i<jobTitleList.size();i++) {
			jobTitleArray = jobTitleArray + cnId +"\""+jobTitleList.get(i).getProjectName()+"\"";
			cnId=",";
		}
		jobTitleArray = jobTitleArray+");";
		model.addAttribute("jobTitleList", jobTitleArray);
	}
	
	@RequestMapping(value="form", method=RequestMethod.GET)
    public String showForm(@RequestParam(required = false) Integer id, ModelMap model) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		AlShiftVendorSupport alShiftVendorSupport;
		String sdate = "", edate = "";
		
		List<SYS_PARAMETER> titleAdd = alShiftVendorDao.titleForm("SHIFT_VENDOR_SUPPORT_ADD");
		List<SYS_PARAMETER> titleUpdate = alShiftVendorDao.titleForm("SHIFT_VENDOR_SUPPORT_UPDATE");
		List<SYS_PARAMETER> vendorList = sysParameterDao.getSysParameterByCode("VENDOR");
		List<SYS_PARAMETER> supportTypeList = sysParameterDao.getSysParameterByCode("SUPPORT_TYPE");
		
		
		loadData(model);
		if (id == null) {
					 
			model.addAttribute("titleF", titleAdd.get(0).getValue());
			alShiftVendorSupport = new AlShiftVendorSupport();
			alShiftVendorSupport.setRegion(userLogin.getRegion());
			sdate = dataFormat.format(new Date()); 
		} else {
				
			model.addAttribute("titleF", titleUpdate.get(0).getValue());
			alShiftVendorSupport = alShiftVendorDao.selectByPrimaryKey(id);
			
			sdate = dataFormat.format(alShiftVendorSupport.getStime());
			if(alShiftVendorSupport.getEtime()!= null)
			{
				edate = dataFormat.format(alShiftVendorSupport.getEtime());
				model.addAttribute("edate", edate);
			}
				
		}   
		
		model.addAttribute("sdate", sdate);  
		model.addAttribute("vendorList", vendorList); 
		model.addAttribute("supportTypeList", supportTypeList);
		model.addAttribute("alShiftVendorSupport", alShiftVendorSupport);
		
		return "jsp/ShiftFes/vendorSupportForm";
	}
	
	@RequestMapping(value="form", method=RequestMethod.POST)
    public String formPost(@ModelAttribute("alShiftVendorSupport") @Valid AlShiftVendorSupport alShiftVendorSupport,
			BindingResult result,@RequestParam(required = false) String sdate,
			@RequestParam(required = false) String edate,ModelMap model, HttpServletRequest request, HttpServletResponse response) { 
		
		List<SYS_PARAMETER> titleAdd = alShiftVendorDao.titleForm("SHIFT_VENDOR_SUPPORT_ADD");
		List<SYS_PARAMETER> titleUpdate = alShiftVendorDao.titleForm("SHIFT_VENDOR_SUPPORT_UPDATE");
		List<SYS_PARAMETER> vendorList = sysParameterDao.getSysParameterByCode("VENDOR"); 
		List<SYS_PARAMETER> supportTypeList = sysParameterDao.getSysParameterByCode("SUPPORT_TYPE"); 
		boolean error = false;
		loadData(model);
		if(sdate == null || (sdate != null && !DateTools.isValid("dd/MM/yyyy HH:mm", sdate))){ 
			model.addAttribute("titleF", titleAdd.get(0).getValue());
			model.addAttribute("supportTypeList", supportTypeList);
			model.addAttribute("vendorList", vendorList);
			model.addAttribute("sdate", sdate);
			model.addAttribute("edate", edate);
			model.addAttribute("alShiftVendorSupport", alShiftVendorSupport); 
			model.addAttribute("SDateError", "Thông tin chưa được nhập hoặc thông tin nhập vào sai định dạng");
			error = true; 
		}else{
			try {
				alShiftVendorSupport.setStime(dataFormat.parse(sdate));
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if( (!edate.equals("") && edate != null && !DateTools.isValid("dd/MM/yyyy HH:mm", edate))){ 
			model.addAttribute("titleF", titleAdd.get(0).getValue());
			model.addAttribute("supportTypeList", supportTypeList);
			model.addAttribute("vendorList", vendorList);
			model.addAttribute("sdate", sdate);
			model.addAttribute("edate", edate);
			model.addAttribute("alShiftVendorSupport", alShiftVendorSupport); 
			model.addAttribute("EDateError", "Thông tin chưa được nhập hoặc thông tin nhập vào sai định dạng");
			error = true; 
		}else{ 
			try {
					alShiftVendorSupport.setEtime(dataFormat.parse(edate));
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			
		}  
		
		if(error == true){
			return "jsp/ShiftFes/vendorSupportForm";
		}
		
		if (alShiftVendorSupport.getId() == null) { 
			if (result.hasErrors()) {
						 
				model.addAttribute("titleF", titleAdd.get(0).getValue());
				model.addAttribute("supportTypeList", supportTypeList);
				model.addAttribute("vendorList", vendorList);
				model.addAttribute("sdate", sdate);
				model.addAttribute("edate", edate);
				model.addAttribute("alShiftVendorSupport", alShiftVendorSupport); 
				
				return "jsp/ShiftFes/vendorSupportForm";
			} 
			
			List<AlShiftVendorSupport> alShiftVendorOld = alShiftVendorDao.getListOld(sdate, 
					edate, alShiftVendorSupport.getVendor(), alShiftVendorSupport.getSystem(), 
					alShiftVendorSupport.getFullname(), alShiftVendorSupport.getPhone(), alShiftVendorSupport.getEmail(), alShiftVendorSupport.getIdNumber());
			
			if(alShiftVendorOld.size() != 0){
				model.addAttribute("titleF", titleAdd.get(0).getValue());
				model.addAttribute("supportTypeList", supportTypeList);
				model.addAttribute("vendorList", vendorList);
				model.addAttribute("sdate", sdate);
				model.addAttribute("edate", edate);
				model.addAttribute("alShiftVendorSupport", alShiftVendorSupport);
				saveMessageKey(request, "alShiftVendorSupport.exits");
				
				return "jsp/ShiftFes/vendorSupportForm";
			}
			
			alShiftVendorDao.insert(alShiftVendorSupport);
			saveMessageKey(request, "alShiftVendorSupport.insertSucceFull");  
			
		} else {  
			if (result.hasErrors()) { 
					
				model.addAttribute("titleF", titleUpdate.get(0).getValue());
				model.addAttribute("supportTypeList", supportTypeList);
				model.addAttribute("vendorList", vendorList);
				model.addAttribute("sdate", sdate);
				model.addAttribute("edate", edate);
				model.addAttribute("alShiftVendorSupport", alShiftVendorSupport);
				saveMessageKey(request, "alShiftVendorSupport.exits"); 
				
				return "jsp/ShiftFes/vendorSupportForm";
			} 
			
			List<AlShiftVendorSupport> alShiftVendorOld = alShiftVendorDao.getListOld(sdate, 
					edate, alShiftVendorSupport.getVendor(), alShiftVendorSupport.getSystem(), 
					alShiftVendorSupport.getFullname(), alShiftVendorSupport.getPhone(), alShiftVendorSupport.getEmail(), alShiftVendorSupport.getIdNumber());

			if(alShiftVendorOld.size() != 0 && !alShiftVendorOld.get(0).getId().equals(alShiftVendorSupport.getId())){
				model.addAttribute("titleF", titleUpdate.get(0).getValue());
				model.addAttribute("supportTypeList", supportTypeList);
				model.addAttribute("vendorList", vendorList);
				model.addAttribute("sdate", sdate);
				model.addAttribute("edate", edate);
				model.addAttribute("alShiftVendorSupport", alShiftVendorSupport);
				saveMessageKey(request, "alShiftVendorSupport.exits");
				
				return "jsp/ShiftFes/vendorSupportForm";
			}
			
			alShiftVendorDao.updateByPrimaryKey(alShiftVendorSupport);
			saveMessageKey(request, "alShiftVendorSupport.updateSucceFull"); 
		}
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(Model model) {
		List<SYS_PARAMETER> titleU = alShiftVendorDao.titleForm("SHIFT_VENDOR_SUPPORT_UPLOAD"); 
		model.addAttribute("titleU", titleU.get(0).getValue());
		return "jsp/ShiftFes/vendorSupportUpload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("filePath") MultipartFile filePath, Model model, HttpServletRequest request) throws IOException, ParseException {
		List<SYS_PARAMETER> titleU = alShiftVendorDao.titleForm("SHIFT_VENDOR_SUPPORT_UPLOAD"); 
		if (!filePath.isEmpty()) { 
			String[] ten=filePath.getOriginalFilename().split("\\.");
			String fileExtn = ten[ten.length-1];
			
			if (fileExtn.equals("xlsx")||fileExtn.equals("xls")) {
				if (fileExtn.equals("xlsx")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsxFile(filePath.getInputStream());
					importContent(sheetData,model,request);
				}
				
				if (fileExtn.equals("xls")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsFile(filePath.getInputStream());
					importContent(sheetData,model,request);
				}
			}
			else {
				 saveMessageKey(request, "cautruc.typeFIle");
			}
		}
		else {
			saveMessageKey(request, "cautruc.emptyFile");
		}
		model.addAttribute("titleU", titleU.get(0).getValue());
		return "jsp/ShiftFes/vendorSupportUpload"; 
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent (List sheetData, Model model, HttpServletRequest request) throws IOException, ParseException {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		
		List<SYS_PARAMETER> titleU = alShiftVendorDao.titleForm("SHIFT_VENDOR_SUPPORT_UPLOAD");  
		
		List<AlShiftVendorSupport> successList = new ArrayList<AlShiftVendorSupport>();
		List<AlShiftVendorSupport> failedList = new ArrayList<AlShiftVendorSupport>();
		List<AlShiftVendorSupport> success = new ArrayList<AlShiftVendorSupport>();

		String stime,etime,vendor,system,fullname,phone,email,idNumber
				,jobTitle,region,supportType,description,device,deviceNumber,leader; 
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 15) {
        		model.addAttribute("titleU", titleU.get(0).getValue());
        		saveMessageKey(request, "Định dạng tệp không đúng"); 
        		return "jsp/ShiftFes/vendorSupportUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		List footer= (List) sheetData.get(sheetData.size() - 1);
        		
        		while (footer.size() != 15 && sheetData.size() > 2) {
        			boolean kt=true;
        			for (int k=0;k<footer.size();k++) {
        				if (!footer.get(k).toString().trim().equals("")) {
        					kt=false;
        				}
        			}
        			if (kt==true) {
	        			sheetData.remove(sheetData.size()-1);  
        			}
        			else {
        				for (int j=footer.size(); j<=4; j++) {
        					footer.add("");
         				}
        			}
        			
	        		footer= (List)sheetData.get(sheetData.size()-1);
        		}
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			AlShiftVendorSupport alShiftVendorList = new AlShiftVendorSupport();
        			
        			List data= (List) sheetData.get(i);
        			for (int j=data.size(); j<=14; j++) {
     					data.add("");
     				}
        			
        			stime 		= data.get(0).toString().equals("")?null:data.get(0).toString();
        			etime		= data.get(1).toString().equals("")?null:data.get(1).toString(); 
        			vendor		= data.get(2).toString().equals("")?null:data.get(2).toString();
        			system		= data.get(3).toString().equals("")?null:data.get(3).toString();
        			fullname	= data.get(4).toString().equals("")?null:data.get(4).toString(); 
        			phone 		= data.get(5).toString().equals("")?null:data.get(5).toString();
        			email		= data.get(6).toString().equals("")?null:data.get(6).toString(); 
        			idNumber	= data.get(7).toString().equals("")?null:data.get(7).toString();
        			jobTitle	= data.get(8).toString().equals("")?null:data.get(8).toString();
        			region		= data.get(9).toString().equals("")?userLogin.getRegion():data.get(9).toString();
        			supportType = data.get(10).toString().equals("")?null:data.get(10).toString();
        			device		= data.get(11).toString().equals("")?null:data.get(11).toString(); 
        			deviceNumber= data.get(12).toString().equals("")?null:data.get(12).toString();
        			leader		= data.get(13).toString().equals("")?null:data.get(13).toString();
        			description	= data.get(14).toString().equals("")?null:data.get(14).toString(); 
        			
					if (stime == null ||  vendor == null ||  fullname == null
							|| (stime != null && AL_DateTools.isValid("dd/MM/yyyy HH:mm", stime) == false)
							|| (etime != null && AL_DateTools.isValid("dd/MM/yyyy HH:mm", etime) == false)
							|| (region != null && !region.equalsIgnoreCase(userLogin.getRegion()) ))
					{ 
						error = true;
					}
					
					Date sdate = null; Date edate = null;
	       			try {
	       				if(stime != null && DateTools.isValid("dd/MM/yyyy HH:mm", stime) == true){
	       					sdate = dataFormat.parse(stime); 
	       				}  
	       				if(etime != null && DateTools.isValid("dd/MM/yyyy HH:mm", etime) == true){
	       					edate = dataFormat.parse(etime);
	       				}
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	       			
	       			alShiftVendorList.setDescription(description);
					alShiftVendorList.setDevice(device);
					alShiftVendorList.setDeviceNumber(deviceNumber);
					alShiftVendorList.setEmail(email);
					alShiftVendorList.setEtime(edate);
					alShiftVendorList.setFullname(fullname);
					alShiftVendorList.setIdNumber(idNumber);
					alShiftVendorList.setJobTitle(jobTitle);
					alShiftVendorList.setLeader(leader);
					alShiftVendorList.setPhone(phone);
					alShiftVendorList.setRegion(region);
					alShiftVendorList.setStime(sdate);
					alShiftVendorList.setSupportType(supportType);
					alShiftVendorList.setSystem(system);
					alShiftVendorList.setVendor(vendor);
					
					if (error) {
						failedList.add(alShiftVendorList);
					} else  {
						successList.add(alShiftVendorList);
					} 
				}
        	}
		}
		String eTime = null ;
		for (AlShiftVendorSupport cab: successList) {  
			if(cab.getEtime() != null)
				eTime = dataFormat.format(cab.getEtime());
			List<AlShiftVendorSupport> alShiftVendorOld = alShiftVendorDao.getListOld(dataFormat.format(cab.getStime()),eTime , 
					cab.getVendor(), cab.getSystem(), cab.getFullname(), cab.getPhone() , cab.getEmail() , cab.getIdNumber());
				
				if (alShiftVendorOld.size() != 0) { 
					cab.setId(alShiftVendorOld.get(0).getId());
					alShiftVendorDao.updateByPrimaryKeySelective(cab);
				} 
				else
				{ 
					alShiftVendorDao.insertSelective(cab);
				}
				success.add(cab); 
		} 
		
		if (failedList.size() == 0 && successList.size() > 0) {
			model.addAttribute("status", "Upload thành công.");				// Upload thành công
		} else if (failedList.size() == 0 && successList.size() == 0) {
			model.addAttribute("status", "Không có dữ liệu.");				// Không có dữ liệu
		} else {
			model.addAttribute("status", "Dữ liệu ĐHKT không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác, khu vực không hợp lệ");					// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", successList.size());
		model.addAttribute("totalNum", failedList.size()+successList.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "ListVendorSupport_" + dateNow;
				
		model.addAttribute("exportFileName", exportFileName);
		model.addAttribute("titleU", titleU.get(0).getValue());		
		return "jsp/ShiftFes/vendorSupportUpload";
	}
}
