package vn.com.vhc.vmsc2.statistics.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import bsh.ParseException;
import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.SysUsersDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.MDepartment;
import vn.com.vhc.vmsc2.statistics.domain.SysUsers;
import vn.com.vhc.vmsc2.statistics.web.utils.UploadTools;

/**
 * Danh sach province
 * @author BUIQUANG
 * create date:
 * change history: 10/2013
 */

@Controller
@RequestMapping("/network/province/*")
public class ProvincesController extends BaseController {
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao; 
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	@Autowired
	private BscDAO bscDao;
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
	
	@RequestMapping(value="list")
	public ModelAndView list(@ModelAttribute("filter") HProvincesCode filter, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		boolean checkRoleManager = false;
		if (userLogin.getIsRoleManager().equals("Y")) {
			checkRoleManager = true;
		}
		model.addAttribute("checkRoleManager", checkRoleManager);
		// Danh sách phòng ban
		/*List<MDepartment> maPhongList = mDepartmentDAO.getMDepartmentForProvince();*/
		List<MDepartment> deptList = bscDao.getDept();
		model.addAttribute("deptList", deptList);
				
		int order = 1;
		String column = "CODE";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "Province_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		if(filter.getCode() != null)
			filter.setCode(filter.getCode().trim());
		if(filter.getBranch() != null)
			filter.setBranch(filter.getBranch().trim());
		if(filter.getLocation() != null)
			filter.setLocation(filter.getLocation().trim());
		if(filter.getProvince() != null)
			filter.setProvince(filter.getProvince().trim());
		if(filter.getDistrict() != null)
			filter.setDistrict(filter.getDistrict().trim());
		
		//List<HProvincesCode> provinces = hProvincesCodeDao.filter(filter);
		List<HProvincesCode> provincesList= hProvincesCodeDao.getHProvincesCodeFilter(
				filter.getDept(),  
				filter.getCode(), 
				filter.getBranch(), 
				filter.getLocation(), 
				filter.getProvince(), 
				filter.getDistrict(), 
				column, 
				order ==1 ? "ASC" : "DESC");
		model.addAttribute("provinceList", provincesList);
		model.addAttribute("deptCBB", filter.getDept());
		return new ModelAndView("provinceList");
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = false) String id, HttpServletRequest request) {

		hProvincesCodeDao.deleteById(id);
		saveMessageKey(request, "messsage.confirm.deletesuccess");
		return "redirect:list.htm";
	}

	private void HProvincesCodeAddEdit(String id, ModelMap model)
	{
		if(id != null && id != "")
			model.addAttribute("HProvincesCodeAddEdit", "Y");
		else
			model.addAttribute("HProvincesCodeAddEdit", "N");
	}
	
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session, WebRequest webRequest) {

		HProvincesCode province = (id==null) ? new HProvincesCode() : hProvincesCodeDao.selectById(id);
		model.addAttribute("province", province);
		HProvincesCodeAddEdit( id, model);
		/*// Danh sách phòng ban
		List<MDepartment> maPhongList = mDepartmentDAO.getMDepartmentForProvince();
		model.addAttribute("maPhongList", maPhongList);*/
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
			teamList = bscDao.getTeamByDept(province.getDept());
			subteamList = bscDao.getSubTeamByTeam(province.getDept(), province.getTeam());
		}
		model.addAttribute("deptList", deptList);
		model.addAttribute("teamList", teamList);
		model.addAttribute("subteamList", subteamList);
		
		return "provinceForm";
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id, @ModelAttribute("province") @Valid HProvincesCode province, BindingResult result, HttpServletRequest request,ModelMap model) {
		
		List<MDepartment> deptList = bscDao.getDept();
		List<MDepartment> teamList = bscDao.getTeamByDept(province.getDept()); 
		List<MDepartment> subteamList = bscDao.getSubTeamByTeam(province.getDept(), province.getTeam());
		model.addAttribute("deptList", deptList);
		model.addAttribute("teamList", teamList);
		model.addAttribute("subteamList", subteamList);
		
		if (result.hasErrors())
		{
			HProvincesCodeAddEdit(id, model);
			return "provinceForm";
		}
		if(id == "")
		{
			if(hProvincesCodeDao.selectByPrimaryKey(province.getCode(),province.getRegion())==null){
				hProvincesCodeDao.insert(province);
				saveMessageKey(request, "messsage.confirm.addsuccess");
			}
			else{
				saveMessage(request, "Code đã tồn tại trong hệ thống");
				
				HProvincesCodeAddEdit(id, model);
				return "provinceForm";
			}
		}
		else
		{
			if(hProvincesCodeDao.checkUniqueProvinceCode(province.getCode(),province.getRegion(), id).size()==0){
				hProvincesCodeDao.updateById(province);
				saveMessageKey(request, "messsage.confirm.updatesuccess");
			}
			else{
				saveMessage(request, "Code đã tồn tại trong hệ thống");
				
				HProvincesCodeAddEdit(id, model);
				return "provinceForm";
			}
		}
			
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm() {
		return "provinceUpload";
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
		
		return "provinceUpload";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent(List sheetData, ModelMap model,HttpServletRequest request) {
		
		List<HProvincesCode> successList = new ArrayList<HProvincesCode>();
		List<HProvincesCode> failedList = new ArrayList<HProvincesCode>();
		List<HProvincesCode> success = new ArrayList<HProvincesCode>();
		
		String region;
		String location;
		String branch;
		String code;
		String province;
		String district;
		String dept;
		String team;
		String groups;

		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 9 && heard.size() != 10) {
        		saveMessageKey(request, "sidebar.admin.provinceUploadErrorStructuresNumber");
        		
        		return "provinceUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		HProvincesCode provinceCode;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			provinceCode = new HProvincesCode();
        			
        			List data= (List) sheetData.get(i);
        			if(heard.size() == 9){
	        			for (int j=data.size(); j<=9; j++) {
	     					data.add("");
	     				}
	        			region				= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
	        			location			= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
	        			branch				= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
	        			code				= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
	        			province			= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
	        			district			= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
	        			dept				= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
	        			team				= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
	        			groups				= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			}
	    			else{
	    				for (int j=data.size(); j<=10; j++) {
	     					data.add("");
	     				}
	        			region				= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
	        			location			= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
	        			branch				= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
	        			code				= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
	        			province			= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
	        			district			= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
	        			dept				= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
	        			team				= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
	        			groups				= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
	    			}
        			// Kiem tra loi
        			if (code == null || region == null
        					/*|| (code != null && code.length() > 15)
        					|| (region != null && region.length() > 5)
        					|| (branch != null && branch.length() > 50)
        					|| (location != null && location.length() > 50)
        					|| (province != null && province.length() > 50)
        					|| (district != null && district.length() > 50)
        					|| (dept != null && dept.length() > 50)*/
        					
						) {
						error = true;
					}
        			
        			//---------------------------------------------------------------------------
        			provinceCode.setCode(code);
        			provinceCode.setRegion(region);
        			provinceCode.setBranch(branch);
        			provinceCode.setLocation(location);
        			provinceCode.setProvince(province);
        			provinceCode.setDistrict(district);
        			provinceCode.setDept(dept);
        			provinceCode.setTeam(team);
        			provinceCode.setGroups(groups);
        			if (code == null && region == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(provinceCode);
    					} else  {
    						
    						successList.add(provinceCode);
    					}
        			}
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (HProvincesCode record: successList) {
			try {
					if(hProvincesCodeDao.selectByPrimaryKey(record.getCode(), record.getRegion()) == null){
						hProvincesCodeDao.insertSelective(record);
					}
					else{
						hProvincesCodeDao.updateByPrimaryKeySelective(record);
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
			model.addAttribute("status", "Dữ liệu province không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");	// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", success.size());
		model.addAttribute("totalNum", failedList.size()+success.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		return "provinceUpload";
	}

}