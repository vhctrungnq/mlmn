package controller.alarm;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import controller.BaseController;

import vo.AlManageProject;
import vo.CTableConfig;
import vo.MDepartment;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.dictionary.TableConfigsHelper;

import dao.AlManageProjectDAO;
import dao.CTableConfigDAO;
import dao.MDepartmentDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUsersDAO;


/**
 * Function        : Quan ly du an (phat song site, cell) va du an dang trien khai (So ca)
 * Created By      : BUIQUANG
 * Create Date     : 22/11/2013
 * Modified By     : 
 * Modify Date     : 
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/alarm/al-manage-project/*")
public class AlManageProjectController extends BaseController{

	@Autowired
	private AlManageProjectDAO alManageProjectDAO;
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired
	private MDepartmentDAO mDepartmentDao;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired 
	private SysUsersDAO sysUsersDao;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping(value="list")
    public ModelAndView list(
    		@RequestParam(required = false) String projectCode,
			@RequestParam(required = false) String projectName,
			@RequestParam(required = false) String region,
			@RequestParam(required = false) String type, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		if (region==null ||region.equals("")){
			region =userLogin.getRegion();
		}
		boolean checkRegion=false;
		if (userLogin!=null&&region!=null&region.equalsIgnoreCase(userLogin.getRegion())){
			checkRegion= true;
		}
		model.addAttribute("checkRegion", checkRegion);
		model.addAttribute("region", region);
		//lay danh sach khu vuc
		List<SYS_PARAMETER> regionList = sysParameterDao.getRegionList();
		model.addAttribute("regionList", regionList);
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "ALManage_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
				
		String filterUrl = "";
		String temp = "";
		if(projectCode != null){
			filterUrl += "projectCode=" + projectCode.trim(); 
			temp = "&";
		}
		if(projectName != null){
			filterUrl += temp + "projectName=" + projectName.trim(); 
			temp = "&";
		}
		if(type != null){
			filterUrl += temp + "type=" + type.trim(); 
			temp = "&";
		}
		if(region != null){
			filterUrl += temp + "region=" + region.trim(); 
			temp = "&";
		}
		
		if(filterUrl != "")
			filterUrl = "?" + filterUrl;
		if(type.equals("ON_AIR")){
			//do du lieu ra grid
			List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("AL_MANAGE_PROJECT_ON_AIR");
			//Lay du lieu cac cot an hien cua grid
			List<CTableConfig> listSource = cTableConfigDAO.getColumnList("AL_MANAGE_PROJECT_ON_AIR");
			String url = "data.htm" + filterUrl;
			//Grid
			String gridManage = TableConfigsHelper.getGridAddDontPaging(configList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", "Yes", null, "singlecell", null);
			model.addAttribute("gridManage", gridManage);
		}
		else
			//if(type.equals("FOLLOW"))
		{
			//do du lieu ra grid
			List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("AL_MANAGE_PROJECT_FOLLOW");  
			//Lay du lieu cac cot an hien cua grid
			List<CTableConfig> listSource = cTableConfigDAO.getColumnList("AL_MANAGE_PROJECT_FOLLOW");
			String url = "data.htm" + filterUrl;
			//Grid
			String gridManage = TableConfigsHelper.getGridAddDontPaging(configList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", "Yes", null, "singlecell", null);
			model.addAttribute("gridManage", gridManage);
		}
		model.addAttribute("projectCode", projectCode);
		model.addAttribute("projectName", projectName);
		model.addAttribute("type", type);
		model.addAttribute("region", region);
		
		
		
		return new ModelAndView("jspalarm/alProject/alManageProjectList");
	}
	
	@RequestMapping("/data")
	public void data(@RequestParam(required = false) String projectCode,
			@RequestParam(required = false) String projectName, 	   
			@RequestParam(required = false) String fileName,
			@RequestParam(required = false) String type,
			@RequestParam(required = false) String region,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<AlManageProject> alManageProjectList= alManageProjectDAO.getAlManageProjectFilter(projectCode, projectName, type, region);
		
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(alManageProjectList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) Integer id,
			@RequestParam (required = true) String type, HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
			alManageProjectDAO.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch(Exception e)
		{
			saveMessageKey(request, "message.confirm.deleteOther");
			}
		
		return "redirect:list.htm?type=" + type;
	}
	
	private void alManageProjectAddEdit(String id, ModelMap model)
	{
		if(id != null && !id.equals(""))
			model.addAttribute("alManageProjectAddEdit", "Y");
		else
			model.addAttribute("alManageProjectAddEdit", "N");
	}
	
	private void loadData(ModelMap model,String type)
	{
		// Danh sach goi nho loai du an
		List<AlManageProject> alManageProjectType = alManageProjectDAO.distinctProjectType(type);
		String projectTypeArray="var projectTypeList = new Array(";
		String cnId="";
		for (int i=0;i<alManageProjectType.size();i++) {
			projectTypeArray = projectTypeArray + cnId +"\""+alManageProjectType.get(i).getProjectType()+"\"";
			cnId=",";
		}
		projectTypeArray = projectTypeArray+");";
		model.addAttribute("projectTypeList", projectTypeArray);
		
		// Danh sach goi nho doi tac thuc hien
		List<AlManageProject> alManageVendor = alManageProjectDAO.distinctVendor(type);
		String vendorArray="var vendorList = new Array(";
		String vendor="";
		for (int i=0;i<alManageVendor.size();i++) {
			vendorArray = vendorArray + vendor +"\""+alManageVendor.get(i).getVendor()+"\"";
			vendor=",";
		}
		vendorArray = vendorArray+");";
		model.addAttribute("vendorList", vendorArray);
		
		// Danh sach goi nho to quan ly
		List<MDepartment> teamList = mDepartmentDao.getDepartmentForShiftList(null);
		String teamArray="var teamList = new Array(";
		String team="";
		for (int i=0;i<teamList.size();i++) {
			teamArray = teamArray + team +"\""+teamList.get(i).getDeptName()+"\"";
			team=",";
		}
		teamArray = teamArray+");";
		model.addAttribute("teamList", teamArray);
	}
	
	// Lay danh sach teamList
   	@RequestMapping("teamList")
   	public @ResponseBody 
   	List<MDepartment> teamList() {
   		List<MDepartment> teamList= mDepartmentDao.getDepartmentForShiftList(null);
   		
   		return teamList;
   	 }
	
	//FB Form
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, 
			@RequestParam(required = false) String type,
			@RequestParam(required = false) String projectType,
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		AlManageProject alManageProject = (id == null) ? new AlManageProject() : alManageProjectDAO.selectByPrimaryKey(Integer.parseInt(id));
		model.addAttribute("alManageProject", alManageProject);
		alManageProjectAddEdit( id, model);
		loadData(model, type);
		
		if(id != null && id != ""){
			if(alManageProject.getStartDate() != null)
				model.addAttribute("startDate", df.format(alManageProject.getStartDate()));
			if(alManageProject.getEndDate() != null)
				model.addAttribute("endDate", df.format(alManageProject.getEndDate()));
			model.addAttribute("team", alManageProject.getTeam());
		}
		else{
			model.addAttribute("startDate", df.format(new Date()));
		}
		model.addAttribute("type", type);
		model.addAttribute("projectType", projectType);
		///
		List<SYS_PARAMETER> projectTypeList = sysParameterDao.getSPByCodeAndName("AL_MANAGE_PROJECT", "");
		model.addAttribute("projectTypeList", projectTypeList);
		return "jspalarm/alProject/alManageProjectForm";
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id,
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate,
			@RequestParam(required = false) String type,
			@RequestParam(required = false) String projectType,
			@ModelAttribute("alManageProject") @Valid AlManageProject alManageProject, BindingResult result, 
			ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		//Ném lỗi
		try{
			
		
		if (result.hasErrors()) {
			alManageProjectAddEdit( id, model);
			if(result.hasFieldErrors("startDate"))
				model.addAttribute("startDateError", "startDateError");
			if(result.hasFieldErrors("endDate"))
				model.addAttribute("endDateError", "endDateError");
			
			model.addAttribute("team", alManageProject.getTeam());
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("type", type); 
			model.addAttribute("projectType", projectType);
			
			loadData(model, type);
			return "jspalarm/alProject/alManageProjectForm";
		}
		
		if(alManageProject.getProjectCode() != null)
			alManageProject.setProjectCode(alManageProject.getProjectCode().trim());
		if(alManageProject.getProjectName() != null)
			alManageProject.setProjectName(alManageProject.getProjectName().trim());
		if(alManageProject.getProjectType() != null)
			alManageProject.setProjectType(alManageProject.getProjectType().trim());
		
		if(id.equals(""))
		{
			if(alManageProjectDAO.checkProjectCode(alManageProject.getProjectCode(), type, null).size() == 0){
				alManageProject.setCreatedBy(username);
				alManageProject.setRegion(userLogin.getRegion());
				if(type.equals("ON_AIR"))
					alManageProject.setType("ON_AIR");
				else if(type.equals("FOLLOW"))
				{
					alManageProject.setType("FOLLOW");
				}
					
				
				alManageProjectDAO.insert(alManageProject);
				saveMessageKey(request, "messsage.confirm.addsuccess");
			}
			else{
				alManageProjectAddEdit( id, model);
				model.addAttribute("team", alManageProject.getTeam());
				model.addAttribute("startDate", startDate);
				model.addAttribute("endDate", endDate);
				model.addAttribute("type", type);
				model.addAttribute("projectType", projectType);
				
				loadData(model, type);
				saveMessageKey(request, "messsage.confirm.alManageProject.checkUniqueKey");
				return "jspalarm/alProject/alManageProjectForm";
			}
		}
		else{
			if(alManageProjectDAO.checkProjectCode(alManageProject.getProjectCode(), type, id).size() == 0){
				if(type.equals("ON_AIR"))
					alManageProject.setType("ON_AIR");
				else if(type.equals("FOLLOW"))
					alManageProject.setType("FOLLOW");
				alManageProject.setModifiedBy(username);
				alManageProjectDAO.updateByPrimaryKey(alManageProject);
				saveMessageKey(request, "messsage.confirm.updatesuccess");
			}
			else{
				alManageProjectAddEdit( id, model);
				model.addAttribute("team", alManageProject.getTeam());
				model.addAttribute("startDate", startDate);
				model.addAttribute("endDate", endDate);
				model.addAttribute("type", type);
			 
				loadData(model, type);
				saveMessageKey(request, "messsage.confirm.alManageProject.checkUniqueKey");
				return "jspalarm/alProject/alManageProjectForm";
			}
			
		}
		}
		catch(Exception ex){
			System.out.println(ex.toString());
			alManageProjectAddEdit( id, model);
			model.addAttribute("team", alManageProject.getTeam());
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			model.addAttribute("type", type); 
			model.addAttribute("projectType", projectType);
			
			loadData(model, type);
			return "jspalarm/alProject/alManageProjectForm";
		}
		return "redirect:list.htm?type=" + type;
	}
	
	
	
}
