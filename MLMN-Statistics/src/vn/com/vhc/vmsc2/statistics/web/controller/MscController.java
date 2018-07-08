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
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import bsh.ParseException;

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.MscDAO;
import vn.com.vhc.vmsc2.statistics.dao.SYS_PARAMETERDAO;
import vn.com.vhc.vmsc2.statistics.dao.SysUsersDAO;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.MDepartment;
import vn.com.vhc.vmsc2.statistics.domain.Msc;
import vn.com.vhc.vmsc2.statistics.domain.SYS_PARAMETER;
import vn.com.vhc.vmsc2.statistics.domain.SysUsers;
import vn.com.vhc.vmsc2.statistics.web.filter.MscFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.UploadTools;

/**
 * Function        : Quan ly MSC
 * Created By      : 
 * Create Date     :
 * Modified By     : BUIQUANG
 * Modify Date     : 07/02/2014
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/network/msc/*")
public class MscController extends BaseController {
	@Autowired
	private MscDAO mscDao;
	@Autowired
	private SYS_PARAMETERDAO SysParameterDAO;
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
	public ModelAndView list(@ModelAttribute("filter") MscFilter filter, ModelMap model) {
		List<Msc> mscs = mscDao.filter(filter);
		model.addAttribute("mscList", mscs);
		
		loadData(model);
		model.addAttribute("vendorCBB", filter.getVendor());
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		boolean checkRoleManager = false;
		if (userLogin.getIsRoleManager().equals("Y")) {
			checkRoleManager = true;
		}
		model.addAttribute("checkRoleManager", checkRoleManager);
		
		return new ModelAndView("mscList");
	}
	
	private void loadData(ModelMap model){
		List<SYS_PARAMETER> vendorList= SysParameterDAO.getVendorList();
		model.addAttribute("vendorList", vendorList);
	}

	private void MscAddEdit(String id, ModelMap model)
	{
		if(id != null && id != "")
			model.addAttribute("MscAddEdit", "Y");
		else
			model.addAttribute("MscAddEdit", "N");
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) Integer id, HttpServletRequest request) {

		mscDao.deleteById(id);
		saveMessageKey(request, "messsage.confirm.deletesuccess");

		return "redirect:list.htm";
	}

	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, ModelMap model) {

		Msc msc = (id == null) ? new Msc() : mscDao.selectById(Integer.parseInt(id));
		model.addAttribute(msc);
		loadData(model);
		model.addAttribute("vendorCBB", msc.getVendor());
		MscAddEdit(id, model);
		
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
			teamList = bscDao.getTeamByDept(msc.getDept());
			subteamList = bscDao.getSubTeamByTeam(msc.getDept(), msc.getTeam());
		}
		model.addAttribute("deptList", deptList);
		model.addAttribute("teamList", teamList);
		model.addAttribute("subteamList", subteamList);
		
		return "mscForm";
	}

	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id, @Valid Msc msc, BindingResult result, HttpServletRequest request, ModelMap model) {
		List<MDepartment> deptList = bscDao.getDept();
		List<MDepartment> teamList = bscDao.getTeamByDept(msc.getDept()); 
		List<MDepartment> subteamList = bscDao.getSubTeamByTeam(msc.getDept(), msc.getTeam());
		model.addAttribute("deptList", deptList);
		model.addAttribute("teamList", teamList);
		model.addAttribute("subteamList", subteamList);
		
		if (result.hasErrors())
		{
			loadData(model);
			model.addAttribute("vendorCBB", msc.getVendor());
			MscAddEdit(id, model);
			return "mscForm";
		}
		
		if(id == "")
		{
			Msc oldMsc= mscDao.selectByPrimaryKey(msc.getMscid());
			if (oldMsc == null) {
				msc.setRegion("TT6");
				mscDao.insert(msc);
				saveMessageKey(request, "messsage.confirm.addsuccess");
			} else {
				saveMessage(request, "MSC đã tồn tại");
				model.addAttribute(msc);
				MscAddEdit(id, model);
				loadData(model);
				model.addAttribute("vendorCBB", msc.getVendor());
				return "mscForm";
			}
		}
		else{
			
			if(mscDao.checkUniqueMsc(msc.getMscid(), id).size() == 0){
				
				mscDao.updateById(msc);
				saveMessageKey(request, "messsage.confirm.updatesuccess");
			}
			else {
				saveMessage(request, "MSC đã tồn tại");
				model.addAttribute(msc);
				MscAddEdit(id, model);
				loadData(model);
				model.addAttribute("vendorCBB", msc.getVendor());
				return "mscForm";
			}
		}
		
		return "redirect:list.htm";
	}

	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm() {
		return "mscUpload";
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
		
		return "mscUpload";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent(List sheetData, ModelMap model,HttpServletRequest request) {
		
		List<Msc> successList = new ArrayList<Msc>();
		List<Msc> failedList = new ArrayList<Msc>();
		List<Msc> success = new ArrayList<Msc>();
		
		String mscid;
		String vendor;
		String location;
		String locationName;
		String msuCapacity;
		String longitude;
		String latitude;
		String dept;
		String team;
		String subTeam;
		String neGroup;
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 11 && heard.size() != 12) {
        		saveMessageKey(request, "sidebar.admin.hMscUploadErrorStructuresNumber");
        		
        		return "mscUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		Msc msc;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			msc = new Msc();
        			
        			List data= (List) sheetData.get(i);
        			
        			if(heard.size() == 11){
	        			for (int j=data.size(); j<=11; j++) {
	     					data.add("");
	     				}
	        			mscid					= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
	        			vendor					= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
	        			location				= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
	        			locationName			= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
	        			msuCapacity				= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
	        			longitude				= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
	        			latitude				= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
	        			dept					= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
	        			team					= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
	        			subTeam					= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
	        			neGroup					= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
        			}
        			else{
        				mscid					= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
	        			vendor					= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
	        			location				= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
	        			locationName			= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
	        			msuCapacity				= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
	        			longitude				= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
	        			latitude				= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
	        			dept					= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
	        			team					= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
	        			subTeam					= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
	        			neGroup					= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim();
        			}
        			
        			// Kiem tra loi
        			if (mscid == null) {
						error = true;
					}
        			
        			try
	    			{
        				if(msuCapacity != null){
		        			Integer a = Integer.parseInt(msuCapacity);
		        			msc.setMsuCapacity(a);
	        			}
	    			}
	    			catch(Exception e)
	    			{
	    				error = true;
	    			}
        			
        			//---------------------------------------------------------------------------
        			msc.setMscid(mscid);
        			msc.setVendor(vendor);
        			msc.setLocation(location);
        			msc.setLocationName(locationName);
        			msc.setLongitude(longitude);
        			msc.setLatitude(latitude);
        			msc.setDept(dept);
        			msc.setTeam(team);
        			msc.setSubTeam(subTeam);
        			msc.setNeGroup(neGroup);
        			
    				if (error) {
						failedList.add(msc);
					} else  {
						successList.add(msc);
					}
        			
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (Msc record: successList) {
			try {
					
					if(mscDao.selectByPrimaryKey(record.getMscid()) == null){
						record.setRegion("TT6");
						mscDao.insert(record);
					}else{
						mscDao.updateByPrimaryKey(record);
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
			model.addAttribute("status", "Dữ liệu msc không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");	// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", success.size());
		model.addAttribute("totalNum", failedList.size()+success.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		return "mscUpload";
	}
}
