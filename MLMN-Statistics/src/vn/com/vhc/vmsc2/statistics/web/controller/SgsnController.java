package vn.com.vhc.vmsc2.statistics.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.SgsnDAO;
import vn.com.vhc.vmsc2.statistics.dao.SysUsersDAO;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.MDepartment;
import vn.com.vhc.vmsc2.statistics.domain.Sgsn;
import vn.com.vhc.vmsc2.statistics.domain.SysUsers;
import vn.com.vhc.vmsc2.statistics.web.filter.SGSNFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.UploadTools;

import bsh.ParseException;

/**
 * Function        : Quan ly SGSN
 * Created By      : 
 * Create Date     :
 * Modified By     : BUIQUANG
 * Modify Date     : 07/02/2014
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/network/sgsn/*")
public class SgsnController extends BaseController{

	@Autowired
	private SgsnDAO sgsnDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	
	@Autowired
	private SysUsersDAO sysUsersDao;
	
	@ModelAttribute("highlight")
	public String highlight() {
		String highlight = "";
		List<HighlightConfig> highlightConfigList2 = highlightConfigDao.getByKey("NOT_NULL");
		if (highlightConfigList2.size()>0)
		{ 
			highlight = " $(this).find('.NOT_NULL').css({"+highlightConfigList2.get(0).getStyle()+"});";
		}
		return highlight;
	}
	
	@RequestMapping("list")
	public ModelAndView list(@ModelAttribute("filter") SGSNFilter filter,@RequestParam(required = false) String sgsnid,@RequestParam(required = false) String sgsnName, Model model, HttpServletRequest request) {
		
		List<Sgsn> sgsnNames = sgsnDao.getAllSGSN();
		model.addAttribute("sgsnNameList", sgsnNames);
		model.addAttribute("sgsnid", sgsnid);
		model.addAttribute("sgsnName", sgsnName);
		
		List<Sgsn> sgsns = sgsnDao.filter(filter);
		model.addAttribute("sgsnList", sgsns);
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		boolean checkRoleManager = false;
		if (userLogin.getIsRoleManager().equals("Y")) {
			checkRoleManager = true;
		}
		model.addAttribute("checkRoleManager", checkRoleManager);
		
		
		return new ModelAndView("sgsnList");
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) Integer id,HttpServletRequest request) {

		sgsnDao.deleteById(id);
		saveMessageKey(request, "messsage.confirm.deletesuccess");
		return "redirect:list.htm";
	}
	
	private void SgsnAddEdit(String sgsnid, ModelMap model)
	{
		if(sgsnid != null && sgsnid != "")
			model.addAttribute("SgsnAddEdit", "Y");
		else
			model.addAttribute("SgsnAddEdit", "N");
	}

	
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, ModelMap model) {
				
		Sgsn sgsn = (id == null) ? new Sgsn() : sgsnDao.selectById(Integer.parseInt(id));
		model.addAttribute(sgsn);
		
		SgsnAddEdit(id, model);
		
		List<MDepartment> deptList = new ArrayList<MDepartment>();
		List<MDepartment> teamList = new ArrayList<MDepartment>();
		List<MDepartment> subteamList = new ArrayList<MDepartment>();
		if(id == null){
			//Add new
			deptList = bscDao.getDept();
			teamList = bscDao.getTeamByDept(null);
			subteamList = bscDao.getSubTeamByTeam(null, null);
		}else{
			//Edit
			deptList = bscDao.getDept();
			teamList = bscDao.getTeamByDept(sgsn.getDept());
			subteamList = bscDao.getSubTeamByTeam(sgsn.getDept(), sgsn.getTeam());
		}
		model.addAttribute("deptList", deptList);
		model.addAttribute("teamList", teamList);
		model.addAttribute("subteamList", subteamList);
		
		return "sgsnForm";
	}

	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id, @Valid Sgsn sgsn, BindingResult result, HttpServletRequest request,ModelMap model) {
		List<MDepartment> deptList = bscDao.getDept();
		List<MDepartment> teamList = bscDao.getTeamByDept(sgsn.getDept()); 
		List<MDepartment> subteamList = bscDao.getSubTeamByTeam(sgsn.getDept(), sgsn.getTeam());
		model.addAttribute("deptList", deptList);
		model.addAttribute("teamList", teamList);
		model.addAttribute("subteamList", subteamList);
		
		if (result.hasErrors())
		{
			if(result.hasFieldErrors("uiAttachCapacityLicense"))
				model.addAttribute("uiAttachCapacityLicenseError", "uiAttachCapacityLicenseError");
			if(result.hasFieldErrors("gbAttachCapacityLicense"))
				model.addAttribute("gbAttachCapacityLicenseError", "gbAttachCapacityLicenseError");
			if(result.hasFieldErrors("totalAttachCapacityLicense"))
				model.addAttribute("totalAttachCapacityLicenseError", "totalAttachCapacityLicenseError");
			if(result.hasFieldErrors("thoughputCapacityLicense"))
				model.addAttribute("thoughputCapacityLicenseError", "thoughputCapacityLicenseError");
			if(result.hasFieldErrors("pdpContextCapacityLicense"))
				model.addAttribute("pdpContextCapacityLicenseError", "pdpContextCapacityLicenseError");
			SgsnAddEdit(id, model);
			return "sgsnForm";
		}
		
		if(id != null && !id.equals(""))
		{
			if(sgsnDao.checkUniqueSgsn(sgsn.getSgsnid(), id).size() == 0){
				sgsnDao.updateById(sgsn);
				saveMessageKey(request, "messsage.confirm.updatesuccess");
			}
			else{
				saveMessage(request, "SGSN ID đã tồn tại trong hệ thống");
				SgsnAddEdit(id, model);
				return "sgsnForm";
			}
		}
		else
		{
			if(sgsnDao.selectByPrimaryKey(sgsn.getSgsnid())==null){
				sgsnDao.insert(sgsn);
				saveMessageKey(request, "messsage.confirm.addsuccess");
			}
			else{
				saveMessage(request, "SGSN ID đã tồn tại trong hệ thống");
				SgsnAddEdit(id, model);
				return "sgsnForm";
			}
		}
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm() {
		return "sgsnUpload";
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
		
		return "sgsnUpload";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent(List sheetData, ModelMap model,HttpServletRequest request) {
		
		List<Sgsn> successList = new ArrayList<Sgsn>();
		List<Sgsn> failedList = new ArrayList<Sgsn>();
		List<Sgsn> success = new ArrayList<Sgsn>();
		
		String region;
		String sgsnid;
		String sgsnName;
		String gbAttachCapacityLicense;
		String totalAttachCapacityLicense;
		String thoughputCapacityLicense;
		String pdpContextCapacityLicense;
		String locationName;
		String dept;
		String team;
		String subTeam;
		String neGroup;
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 12 && heard.size() != 13) {
        		saveMessageKey(request, "sidebar.admin.hsgsnUploadErrorStructuresNumber");
        		
        		return "sgsnUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		Sgsn sgsn;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			sgsn = new Sgsn();
        			
        			List data= (List) sheetData.get(i);
        			
        			if(heard.size() == 12){
	        			for (int j=data.size(); j<=12; j++) {
	     					data.add("");
	     				}
	        			region							= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
	        			sgsnid							= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
	        			sgsnName						= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
	        			gbAttachCapacityLicense			= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
	        			totalAttachCapacityLicense		= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
	        			thoughputCapacityLicense		= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
	        			pdpContextCapacityLicense		= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
	        			locationName					= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
	        			dept							= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
	        			team							= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
	        			subTeam							= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
	        			neGroup							= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim();
        			}
        			else{
        				for (int j=data.size(); j<=13; j++) {
	     					data.add("");
	     				}
        				region							= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
	        			sgsnid							= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
	        			sgsnName						= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
	        			gbAttachCapacityLicense			= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
	        			totalAttachCapacityLicense		= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
	        			thoughputCapacityLicense		= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
	        			pdpContextCapacityLicense		= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
	        			locationName					= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
	        			dept							= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
	        			team							= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
	        			subTeam							= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim();
	        			neGroup							= data.get(12).toString().trim().equals("")?null:data.get(12).toString().trim();
        			}
        			
        			// Kiem tra loi
        			if (sgsnid == null || sgsnName == null) {
						error = true;
					}
        			
        			try
	    			{
        				if(gbAttachCapacityLicense != null){
		        			Long a = Long.parseLong(gbAttachCapacityLicense);
		        			sgsn.setGbAttachCapacityLicense(a);
	        			}
        				if(totalAttachCapacityLicense != null){
		        			Long a = Long.parseLong(totalAttachCapacityLicense);
		        			sgsn.setTotalAttachCapacityLicense(a);
	        			}
        				if(thoughputCapacityLicense != null){
		        			Long a = Long.parseLong(thoughputCapacityLicense);
		        			sgsn.setThoughputCapacityLicense(a);
	        			}
        				if(pdpContextCapacityLicense != null){
		        			Long a = Long.parseLong(pdpContextCapacityLicense);
		        			sgsn.setPdpContextCapacityLicense(a);
	        			}
	    			}
	    			catch(Exception e)
	    			{
	    				error = true;
	    			}
        			
        			//---------------------------------------------------------------------------
        			sgsn.setRegion(region);
        			sgsn.setSgsnid(sgsnid);
        			sgsn.setSgsnName(sgsnName);
        			sgsn.setLocationName(locationName);
        			sgsn.setDept(dept);
        			sgsn.setTeam(team);
        			sgsn.setSubTeam(subTeam);
        			sgsn.setNeGroup(neGroup);
        			
        			if (sgsnid == null && sgsnName == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(sgsn);
    					} else  {
    						
    						successList.add(sgsn);
    					}
        			}
        			
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (Sgsn record: successList) {
			try {
					
					if(sgsnDao.selectByPrimaryKey(record.getSgsnid()) == null){
						sgsnDao.insert(record);
					}else{
						sgsnDao.updateByPrimaryKey(record);
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
			model.addAttribute("status", "Dữ liệu sgsn không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");	// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", success.size());
		model.addAttribute("totalNum", failedList.size()+success.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		return "sgsnUpload";
	}

}
