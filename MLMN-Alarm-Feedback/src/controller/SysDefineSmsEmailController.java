package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import vo.AlAlarmWorks;
import vo.AlHandingLibrary;
import vo.AlShift;
import vo.CTableConfig;
import vo.FbDeptPlaces;
import vo.MDepartment;
import vo.SYS_PARAMETER;
import vo.SysDefineSmsEmail;
import vo.SysUsers;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.HelpTableConfigs;
import vo.alarm.utils.UploadTools;

import dao.AlAlarmTypesDAO;
import dao.AlShiftDAO;
import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.FbDeptPlacesDAO;
import dao.MDepartmentDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysDefineSmsEmailDAO;
import dao.SysUsersDAO;

@Controller
@RequestMapping("/define")
public class SysDefineSmsEmailController extends BaseController {
	
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired
	private MDepartmentDAO mDepartmentDAO;
	
	@Autowired 
	private AlShiftDAO alShiftDAO;
	
	@Autowired 
	private SysUsersDAO sysUsersDao;
	
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	
	@Autowired
	private AlAlarmTypesDAO alAlarmTypesDAO;
	
	@Autowired
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
	
	@Autowired
	private SysDefineSmsEmailDAO  sysDefineSmsEmailDAO ;
	/*List danh sach thu vien xu ly
	 * @param sdate: Thoi gian bat dau
	 * function : 2G/3G
	 */
	@RequestMapping("/list")
	public ModelAndView list(
			@RequestParam(required = false) String groupName,
			@RequestParam(required = true) String type,
			Model model, HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		
		List<SYS_PARAMETER> sysParemeterTitle = sysDefineSmsEmailDAO.titleForm("DEFINE",type,null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		if (groupName==null)
		{
			groupName="";
		}
		else
			groupName = groupName.trim();
		if (type==null)
		{
			type="";
		}
		else
			type = type.trim();
		String tablename="SYS_DEFINE";
		if (type!=null && !type.equals(""))
		{
			tablename+="_"+type.toUpperCase();
		}
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid(tablename);
		//Lay du lieu cac cot an hien cua grid 
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList(tablename);
		//url
		String url = "data.htm?type="+type+"&groupName="+groupName;
		//Grid
		String griddefine = HelpTableConfigs.getGridPagingUrl(configList, "griddefine", url, "listdefine", listSource, "Menu", null, "multiplerowsextended","Y");
		model.addAttribute("griddefine", griddefine);	
				
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = tablename+"-"+ dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		model.addAttribute("groupName", groupName);
		model.addAttribute("type", type);
		
		return new ModelAndView("jspalarm/cauhinh/sysDefineList");
	}
	@RequestMapping("data")
	public @ResponseBody 
	void dataSuccessList(@RequestParam(required = false) String type,
			@RequestParam(required = false) String groupName,
			HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
		
		List<SysDefineSmsEmail> dataList = new ArrayList<SysDefineSmsEmail>();
		dataList = sysDefineSmsEmailDAO.getListAll(type,groupName,null);
		Gson gs = new Gson();
		String jsonDataList = gs.toJson(dataList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonDataList);
		out.close();
	 }
	
	//Xoa
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) String idList,@RequestParam (required = true) String type,
			HttpServletRequest request, Model model) {
		try {
			String[] idArray = idList.split(",");
			for (String id : idArray) {
				System.out.println(id);
				sysDefineSmsEmailDAO.deleteByPrimaryKey(Integer.parseInt(id));
			}
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch (Exception e) {
			saveMessageKey(request, "messsage.confirm.deletefailure");
		}
		return "redirect:list.htm?type="+type;
	}
	
	//Them, sua
		@RequestMapping(value = "form", method = RequestMethod.GET)
		public String form(@RequestParam (required = true) String id,
				@RequestParam (required = true) String type,
				HttpServletRequest request, Model model) {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			List<SYS_PARAMETER> sysParemeterTitle = sysDefineSmsEmailDAO.titleForm("DEFINE",type,"ADD");
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
			}
			SysDefineSmsEmail record = new SysDefineSmsEmail();
			
			if (id!=null&&!id.equals(""))
			{
				record = sysDefineSmsEmailDAO.selectByPrimaryKey(Integer.parseInt(id));
			}
			
			model.addAttribute("define", record);		
			model.addAttribute("type",type);	
				
			return "jspalarm/cauhinh/sysDefineForm";
		}
		
		@RequestMapping(value = "form", method = RequestMethod.POST)
		public String onSubmit(@ModelAttribute("define") @Valid SysDefineSmsEmail define, BindingResult result,
				@RequestParam(required = false) String type,
				@RequestParam(required = false) String isEnable,
				ModelMap model, HttpServletRequest request) throws ParseException {
			
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			List<SYS_PARAMETER> sysParemeterTitle = sysDefineSmsEmailDAO.titleForm("DEFINE",type,"ADD");
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
			}
			
			model.addAttribute("type",type);	
			model.addAttribute("isEnable",isEnable);	
		
			System.out.println("eror:"+result.getObjectName());
			if (result.hasErrors() ) {
				saveMessageKey(request, "define.errorField");
				 return "jspalarm/cauhinh/sysDefineForm";
			}
			else
			{
				define.setIsEnable(isEnable);
				define.setGroupType(type);
				SysDefineSmsEmail canhBao= sysDefineSmsEmailDAO.checkExits(type, define.getGroupName(),"C");
				
				/*try
				{*/
					if (define.getId()==null)
					{
						if (canhBao!= null)
						{
							saveMessageKey(request, "define.exits");
							return "jspalarm/cauhinh/sysDefineForm";
						}
						else
						{
							define.setCreateDate(new Date());
							define.setCreatedBy(username);
							sysDefineSmsEmailDAO.insert(define);
							saveMessageKey(request, "define.insertSucceFull");
						}
					}
					else
					{
						//sua chua
						if (canhBao!= null&&!canhBao.getId().equals(define.getId()))
						{
							saveMessageKey(request, "define.exits");
							return "jspalarm/cauhinh/sysDefineForm";
						}
						else
						{
							define.setModifiedBy(username);
							define.setModifyDate(new Date());
							sysDefineSmsEmailDAO.updateByPrimaryKey(define);
							saveMessageKey(request, "define.updateSuccelfull");	
						}
						
					}
				/*}
				catch(Exception exp)
				{
					saveMessageKey(request, "cautruc.loiKichThuoc");
				}*/
			}
			return "redirect:list.htm";
		}
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(@RequestParam(required = false) String type,Model model) {
		
		List<SYS_PARAMETER> sysParemeterTitle = sysDefineSmsEmailDAO.titleForm("DEFINE",type,"UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		}
		return "jspalarm/cauhinh/sysDefineUpload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("filePath") MultipartFile filePath, 
			@RequestParam(required = false) String type,
			Model model, HttpServletRequest request) throws IOException, ParseException {
		
		List<SYS_PARAMETER> sysParemeterTitle = sysDefineSmsEmailDAO.titleForm("DEFINE",type,"UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		}
		
		model.addAttribute("type",type);	
	
    	String alarm = "";
		if (!filePath.isEmpty()) {

			String[] ten=filePath.getOriginalFilename().split("\\.");
			String fileExtn = ten[ten.length-1];
			
			if (fileExtn.equals("xls")) {
				@SuppressWarnings("rawtypes")
				List sheetData = UploadTools.readXlsFile(filePath.getInputStream());
				alarm = importContent(sheetData,type,model,request);
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
		return "jspalarm/cauhinh/sysDefineUpload";
		
	}
	@SuppressWarnings({ "rawtypes" })
	    private String importContent (List sheetData,String type,Model model,HttpServletRequest request) throws IOException, ParseException {
		List<SYS_PARAMETER> sysParemeterTitle = sysDefineSmsEmailDAO.titleForm("DEFINE",type,"UPLOAD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleU", sysParemeterTitle.get(0).getValue());
		}
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		/*AlShift shift = getCaTrucHT();
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
			 return "jspalarm/cauhinh/sysDefineUpload";
		}*/
		 List<SysDefineSmsEmail> paraList= new ArrayList<SysDefineSmsEmail>();
	   if (sheetData.size() > 0) {
	        	// Kiem tra tieu đề của trang web
	        	List heard= (List)sheetData.get(0);
	        	if (heard.size() != 6) {
	        		saveMessageKey(request, "cautruc.loiCautruc");
	        		return "jspalarm/cauhinh/sysDefineUpload";
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
		  						return "jspalarm/cauhinh/sysDefineUpload";
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
     				SysDefineSmsEmail alarm = new SysDefineSmsEmail();
     				alarm.setGroupName(data.get(0).toString().trim());
     				alarm.setGroupUser(data.get(2).toString().trim());
     				alarm.setIsEnable(data.get(1).toString().trim());
     				//alarm.setOrdering(data.get(3).toString().trim());
     				alarm.setGroupType(type);
     			
     				// Kiểm tra khu vuc co ton tai khong
 				
     				SysDefineSmsEmail canhBao= sysDefineSmsEmailDAO.checkExits(alarm.getGroupType(), alarm.getGroupName(),"C");
 					if (canhBao!= null)
 					{
 						paraList.clear();
     					paraList.add(alarm);
     					saveMessageKey(request, "define.exits");
     					model.addAttribute("errorContent","Import không thành công. Quá trình import dữ liệu trong file bị lỗi tại bản ghi:");
  						model.addAttribute("defineList",paraList);
  						return "jspalarm/cauhinh/sysDefineUpload";
 					}
     				
     			}
     			
	        	}
		        else {
		        	
		        	saveMessageKey(request, "cautruc.loiCautruc");
		        	return "jspalarm/cauhinh/sysDefineUpload";
		        }
	        }
		 if (paraList.size()>0)
		 {
			 for (int i=0;i<paraList.size();i++)
			 {
				 paraList.get(i).setCreateDate(new Date());
				 paraList.get(i).setCreatedBy(username);
				 sysDefineSmsEmailDAO.insertSelective(paraList.get(i));
			 }
			 
			saveMessageKey(request, "global.importSuccefull");
			model.addAttribute("defineList",paraList);
		 }
		 else
			saveMessageKey(request, "global.importFail");
		 return "jspalarm/cauhinh/sysDefineUpload";
	 }
	
	
	
	
}
