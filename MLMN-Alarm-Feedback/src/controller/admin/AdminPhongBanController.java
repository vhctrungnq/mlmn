package controller.admin;

import java.io.IOException;
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
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import vo.CHighlightConfigs;
import vo.MDepartment;
import vo.SYS_PARAMETER;
import vo.SysGroupUser;
import vo.SysUserRoles;
import vo.SysUsers;
import vo.alarm.utils.Helper;
import vo.alarm.utils.NumberTools;
import vo.alarm.utils.UploadTools;

import controller.BaseController;
import dao.CHighlightConfigsDAO;
import dao.MDepartmentDAO;
import dao.SYS_PARAMETERDAO;
/**
 * Function        : Quan ly phong ban
 * Created By      : BUIQUANG
 * Create Date     :
 * Modified By     : 
 * Modify Date     : 
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/admin/phong-ban/*")
public class AdminPhongBanController extends BaseController {

	@Autowired
	private MDepartmentDAO mDepartmentDao;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
	@RequestMapping(value="danh-sach")
    public ModelAndView list(@ModelAttribute("filter") MDepartment filter, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		if(filter.getDeptCode() != null)
			filter.setDeptCode(filter.getDeptCode().trim());
		if(filter.getAbbreviated() != null)
			filter.setAbbreviated(filter.getAbbreviated().trim());
		if(filter.getDeptName() != null)
			filter.setDeptName(filter.getDeptName().trim());
		
		/*List<MDepartment> phongBanChaDistinct = mDepartmentDao.distinctNhomPhongBan();
		model.addAttribute("phongBanChaDistinct", phongBanChaDistinct);*/
		
		/*List<SYS_PARAMETER> systemList = mDepartmentDao.getSystemListBySp(); //sysParameterDao.getSysParameterByCode("NAME_SYSTEM");
		model.addAttribute("systemList", systemList);*/
		List<CHighlightConfigs> highlightConfigList = cHighlightConfigsDAO.getByKey("MENU_COLOR");
		model.addAttribute("highlight", Helper.highLightCost(highlightConfigList, "item"));
		
		List<SYS_PARAMETER> titleSystem = mDepartmentDao.titleDepartment(); //sysParameterDao.getSPByCodeAndName("TITLE_SYSTEM", "QUAN_LY_PHONG_BAN_DON_VI");
		model.addAttribute("titleSystem", titleSystem.get(0).getValue());
		
		model.addAttribute("phongBanChaDistinctCBB", filter.getPhongBanCha());
		model.addAttribute("systemCBB", filter.getSystem());
		
		int order = 1;
		String column = "ORDERING";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		model.addAttribute("trangThaiCBB", filter.getIsEnable());
		List<SYS_PARAMETER> trangThai = mDepartmentDao.getTrangThai(); //sysParameterDao.getSysParameterByCode("PB_LPA_TRANG_THAI");
		model.addAttribute("trangThaiList", trangThai);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "DSPhongBan_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		List<MDepartment> department = mDepartmentDao.getPhongBanFilter( filter.getDeptCode(), filter.getAbbreviated(), filter.getDeptName(), filter.getIsEnable(), column, order == 1 ? "ASC" : "DESC");
		model.addAttribute("departmentList",department);
		
		return new ModelAndView("jspadmin/phongBanList");
    }
	
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		MDepartment phongban = (id == null) ? new MDepartment() : mDepartmentDao.selectByID(id);
		model.addAttribute("phongBanform", phongban);
		
		List<SYS_PARAMETER> trangThai = mDepartmentDao.getTrangThai(); //sysParameterDao.getSysParameterByCode("PB_LPA_TRANG_THAI");
		model.addAttribute("trangThaiList", trangThai);
		
		//lay danh sach khu vuc
		List<SYS_PARAMETER> regionList = sysParameterDao.getRegionList();
		model.addAttribute("regionList", regionList);
		
		if(id == null){
			List<MDepartment> phongBanChaList = mDepartmentDao.getPhongBanCha();
			model.addAttribute("phongBanChaList", phongBanChaList);
		}
		else
		{
			List<MDepartment> phongBanChaList = mDepartmentDao.getDepartmentDontId(id);
			model.addAttribute("phongBanChaList", phongBanChaList);
		}
		
		model.addAttribute("trangThaiCBB", phongban.getIsEnable());
		model.addAttribute("systemCBB", phongban.getSystem());
		model.addAttribute("region", phongban.getRegion());
		
		model.addAttribute("idPhongBanCBB", phongban.getParentId());
		maPhongAddEdit(id, model);
		
		return "jspadmin/phongBanForm";
	}

	private void maPhongAddEdit(String id, ModelMap model)
	{
		if(id != null && id != "")
			model.addAttribute("maPhongAddEdit", "Y");
		else
			model.addAttribute("maPhongAddEdit", "N");
		
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id, 
				 		   @RequestParam(required = false) String phongBanCha,
				 		   @RequestParam(required = false) Integer parent_id, @ModelAttribute("phongBanform") @Valid MDepartment phongBan, BindingResult result, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		//Ném lỗi
		if (result.hasErrors()) {
			
				List<SYS_PARAMETER> trangThai = mDepartmentDao.getTrangThai(); //sysParameterDao.getSysParameterByCode("PB_LPA_TRANG_THAI");
				model.addAttribute("trangThaiList", trangThai);
				//lay danh sach khu vuc
				List<SYS_PARAMETER> regionList = sysParameterDao.getRegionList();
				model.addAttribute("regionList", regionList);	
				
				if(phongBanCha == null)
					phongBan.setParentId(parent_id);
				else
					phongBan.setParentId(null);
				
				if(id != null && id != "")
				{
					List<MDepartment> phongBanChaList = mDepartmentDao.getDepartmentDontId(id);
					model.addAttribute("phongBanChaList", phongBanChaList);
				}
				else
				{
					List<MDepartment> phongBanChaList = mDepartmentDao.getPhongBanCha();
					model.addAttribute("phongBanChaList", phongBanChaList);
				}
							
				model.addAttribute("trangThaiCBB", phongBan.getIsEnable());
				model.addAttribute("systemCBB", phongBan.getSystem());
				model.addAttribute("idPhongBanCBB", phongBan.getParentId());
				model.addAttribute("region", phongBan.getRegion());
				maPhongAddEdit(id, model);
				
				return "jspadmin/phongBanForm";
		}
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
	    Authentication authentication = securityContext.getAuthentication();

	    if(phongBanCha == null)
			phongBan.setParentId(parent_id);
		else
			phongBan.setParentId(null);
	    
		if(id == "")
		{
			if(mDepartmentDao.selectByPrimaryKey(phongBan.getDeptCode()) == null)
			{
				phongBan.setCreatedBy(authentication.getName());
				
				mDepartmentDao.insert(phongBan);
				saveMessageKey(request, "messsage.confirm.addsuccess");
			}
			else
			{
				saveMessageKey(request, "qLPhongBan.maPhongDaTonTai");
				
				List<SYS_PARAMETER> trangThai = mDepartmentDao.getTrangThai(); //sysParameterDao.getSysParameterByCode("PB_LPA_TRANG_THAI");
				model.addAttribute("trangThaiList", trangThai);
				//lay danh sach khu vuc
				List<SYS_PARAMETER> regionList = sysParameterDao.getRegionList();
				model.addAttribute("regionList", regionList);
				
				model.addAttribute("trangThaiCBB", phongBan.getIsEnable());
				model.addAttribute("systemCBB", phongBan.getSystem());
				model.addAttribute("idPhongBanCBB", phongBan.getParentId());
				
				
				
				if(id != null && id != "")
				{
					List<MDepartment> phongBanChaList = mDepartmentDao.getDepartmentDontId(id);
					model.addAttribute("phongBanChaList", phongBanChaList);
				}
				else
				{
					List<MDepartment> phongBanChaList = mDepartmentDao.getPhongBanCha();
					model.addAttribute("phongBanChaList", phongBanChaList);
				}
				
				maPhongAddEdit(id, model);
				
				return "jspadmin/phongBanForm";
			}
		}
		else
		{
			phongBan.setModifiedBy(authentication.getName());
			
			mDepartmentDao.updateByPrimaryKey(phongBan);
			saveMessageKey(request, "messsage.confirm.updatesuccess");
		}
		
		return "redirect:danh-sach.htm";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) Integer id, HttpServletRequest request, HttpServletResponse response) {
		
		int count = mDepartmentDao.countPhongBanCha(id);
		if(count > 0)
		{
			saveMessageKey(request, "message.confirm.mDepartmentTonTai");
		}
		else{
			
			try
			{
				mDepartmentDao.deleteByPrimaryKey(id);
				saveMessageKey(request, "messsage.confirm.deletesuccess");
			}
			catch(Exception e)
			{
				saveMessageKey(request, "message.confirm.deleteOther");
				}
		}
		
		return "redirect:danh-sach.htm";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm() {
		return "jspadmin/phongBanUpload";
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile filePath,  ModelMap model, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		
		if (!filePath.isEmpty()) {

			String[] ten=filePath.getOriginalFilename().split("\\.");
			String fileExtn = ten[ten.length-1];
			
			if (fileExtn.equals("xls")) {
				if (fileExtn.equals("xls")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsFile2(filePath.getInputStream());
					
					importContent(sheetData, model, request);
					
				}
			}
			else {
				 saveMessageKey(request, "cautruc.typeFIle");
			}
		}
		else {
			saveMessageKey(request, "cautruc.emptyFile");
		}
		
		return "jspadmin/phongBanUpload";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent(List sheetData, ModelMap model,HttpServletRequest request) {
		String createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<MDepartment> successList = new ArrayList<MDepartment>();
		List<MDepartment> failedList = new ArrayList<MDepartment>();
		List<MDepartment> success = new ArrayList<MDepartment>();
		
		String deptCode;
		String abbreviated;
		String deptName;
		String deptCodeParent;
		String region;
		String phone;
		String fax;
		String isEnable;
		String address;
		String ordering;
		String status1="";
		String status2="";
		String status3="";
		String status4="";
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 10) {
        		saveMessageKey(request, "sidebar.admin.usersUploadErrorStructuresNumber");
        		return "jspadmin/phongBanUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		MDepartment mDepartment;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			mDepartment = new MDepartment();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=10; j++) {
     					data.add("");
     				}
        			deptCode			= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			abbreviated			= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			deptName			= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			deptCodeParent		= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			region				= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			phone				= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			fax					= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			isEnable			= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			address				= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			ordering			= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			
        			// Kiem tra loi
        			if (deptCode == null || abbreviated == null || deptName == null 
        					|| (deptCode != null && mDepartmentDao.selectByPrimaryKey(deptCode) != null)
        					|| (region != null && sysParameterDao.checkRegionExit(region) == false )
						) {
						error = true;
						status1 = "&nbsp;- Thiếu thông tin nhập liệu bắt buộc hoặc phòng ban đã tồn tại hoặc không tồn tại khu vực<br>";
					}
        			
        			//---------------------------------------------------------------------------
        			mDepartment.setDeptCode(deptCode);
        			mDepartment.setAbbreviated(abbreviated);
        			mDepartment.setDeptName(deptName);
        			mDepartment.setRegion(region);
        			mDepartment.setAddress(address);
        			mDepartment.setFax(fax);
        			mDepartment.setPhone(phone);
        			mDepartment.setDeptCodeParent(deptCodeParent);
        			
        			if (ordering!=null){
        				try{
        				mDepartment.setOrdering(Integer.parseInt(ordering));
        				}catch(Exception ex){ error = true;
        				status3 = "&nbsp;- Dữ liệu sắp xếp là định dạng số <br>";}
        			}
        			if(isEnable != null && !isEnable.equalsIgnoreCase("y") && !isEnable.equalsIgnoreCase("n") ){
        				 error = true;
         				status4 = "&nbsp;- Dữ liệu trạng thái nhận giá trị Y/N <br>";
        			} else
        					mDepartment.setIsEnable(isEnable.toUpperCase());
						
        			mDepartment.setCreatedBy(createdBy);
        			mDepartment.setCreateDate(new Date());
					if (deptCodeParent!=null){
        				MDepartment parentDept = mDepartmentDao.selectByPrimaryKey(deptCodeParent);
        				if (parentDept!=null){
        					mDepartment.setParentId(parentDept.getId());}
        				else {
        					error=true;
        					status2 = "&nbsp;- Mã phòng ban cha không tồn tại <br>";
        				}
        			}
					
    				if (error) {
						failedList.add(mDepartment);
					} else  {
						try {
						mDepartmentDao.insertSelective(mDepartment);
						success.add(mDepartment);
						} catch(Exception ex){failedList.add(mDepartment);}
					}
        		}
        	}
		}
		
		if (failedList.size() == 0 && success.size() > 0) {
			model.addAttribute("status", "Upload thành công.");				// Upload thành công
		} else if (failedList.size() == 0 && success.size() == 0) {
			model.addAttribute("status", "Không có dữ liệu.");				// Không có dữ liệu
		} else {
			//model.addAttribute("status", "Dữ liệu người dùng không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép, định dạng dữ liệu không chính xác, tên tài khoản đã tồn tại hoặc mã phòng và nhóm truy cập không đúng.");	// Upload lỗi
			model.addAttribute("status", "Dữ liệu không hợp lệ do:<br>" + status1+status2+status3+status4);
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", success.size());
		model.addAttribute("totalNum", failedList.size()+success.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		return "jspadmin/phongBanUpload";
	}
	
}
