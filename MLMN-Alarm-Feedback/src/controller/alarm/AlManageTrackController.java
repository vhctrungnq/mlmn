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

import vo.AlManageProject;
import vo.AlManageProjectTrack;
import vo.CHighlightConfigs;
import vo.CTableConfig;
import vo.SYS_PARAMETER;
import vo.SysMenu;
import vo.SysUsers;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;
import vo.alarm.utils.UploadTools;
import vo.dictionary.TableConfigsHelper;

import dao.AlManageProjectDAO;
import dao.AlManageProjectTrackDAO;
import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUsersDAO;

/**
 * Function        : Theo doi du an tong dai
 * Created By      : BUIQUANG
 * Create Date     : 12/12/2013
 * Modified By     : 
 * Modify Date     : 
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/alarm/al-manage-track/*")
public class AlManageTrackController extends BaseController{

	@Autowired
	private AlManageProjectTrackDAO alManageProjectTrackDAO;
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	@Autowired
	private AlManageProjectDAO alManageProjectDAO;
	@Autowired
	private SYS_PARAMETERDAO  sysParameterDao;
	@Autowired 
	private SysUsersDAO sysUsersDao;
	
	private final String vType = "AL_MANAGE_PROJECT_TRACK_";
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
	
	@RequestMapping(value="list")
    public ModelAndView list(
    		@RequestParam(required = false) String projectCode,
    		@RequestParam(required = false) String siteName,
    		@RequestParam(required = false) String type,
    		@RequestParam(required = false) String region,
			@RequestParam(required = false) String node, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
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
			filterUrl += temp + "projectCode=" + projectCode.trim(); 
			temp = "&";
		}
		
		if(siteName != null){
			filterUrl += temp + "siteName=" + siteName.trim(); 
			temp = "&";
		}
		if(type != null){
			filterUrl += temp + "type=" + type.trim(); 
			temp = "&";
		}
		if(node != null){
			filterUrl += temp + "node=" + node.trim(); 
			temp = "&";
		}
		if(region != null){
			filterUrl += temp + "region=" + region.trim(); 
			temp = "&";
		}
		if(filterUrl != "")
			filterUrl = "?" + filterUrl;
		
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid(vType+type);
		//Lay du lieu cac cot an hien cua grid
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList(vType+type);
		String url = "data.htm" + filterUrl;
		//lay du lieu column group cua grid
		String groupColumn="";
		
		List<String> columnGrid = cTableConfigDAO.getGroupList(vType+type);
		String con2="";
		groupColumn =" var columngroups=[";
		for (String alarmTypeG : columnGrid) {
			groupColumn= groupColumn+		con2+"{ text: '"+alarmTypeG+"', align: 'center', name: '"+alarmTypeG+"' }";
			con2 = ",";
		}
		groupColumn= groupColumn+	"]; ";
		//Grid
		String gridManage = TableConfigsHelper.getGridAddAndPaging(configList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", null, null, "multiplerowsextended", groupColumn);
		model.addAttribute("gridManage", gridManage);
		model.addAttribute("projectCode", projectCode);
		model.addAttribute("siteName", siteName);
		model.addAttribute("node", node);
		model.addAttribute("type", type);
		return new ModelAndView("jspalarm/alProject/alManageTrackList");
	}
	
	private void loadData(ModelMap model,  String type){
		List<AlManageProject> alManageProjectList = alManageProjectDAO.getProjectUnfinishedList(type);
		model.addAttribute("type", type);
		model.addAttribute("projectIdList", alManageProjectList);
	}
	
	@RequestMapping("/data")
	public void data(
			@RequestParam(required = false) String projectCode,
			@RequestParam(required = false) String siteName,
			@RequestParam(required = false) String node, 	
			@RequestParam(required = false) String type, 	
			@RequestParam(required = false) String region, 
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<AlManageProjectTrack> alManageProject = null;
		int startRecord = 0, endRecord = 0, pageSize = 100;
		String sortfield = "SITEID";
		String sortorder = "";
		int pageNum = Integer.parseInt(request.getParameter("pagenum"));
		if(pageNum == -1)
			pageNum = 1;
		if(!request.getParameter("pagesize").equals(""))
			 pageSize = Integer.parseInt(request.getParameter("pagesize"));
		else pageSize = 100;
		sortfield = request.getParameter("sortdatafield");
		sortorder = request.getParameter("sortorder");
		List<CTableConfig> columnList = cTableConfigDAO.getTableConfigGet(vType+type, sortfield);
		List<CTableConfig> tableConfigList = cTableConfigDAO.getTableConfigGet(vType+type, null);
		
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
		startRecord = pageNum*pageSize;
		endRecord = startRecord + pageSize;
		int totalRow =0;
		try
		{
			alManageProject = alManageProjectTrackDAO.getAlManageTrackFilter(type,projectCode, siteName, node, startRecord, endRecord, sortfield, sortorder, strWhere,region);
			totalRow = alManageProjectTrackDAO.countAlManageTrackFilter(type,projectCode, siteName, node, strWhere,region);
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
		String jsonCartList = gs.toJson(alManageProject);
		strjson+= jsonCartList;
	    strjson += "}]";
	    
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(strjson);
		out.close();
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) Integer id,
			@RequestParam (required = true) String type,
			HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
			alManageProjectTrackDAO.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch(Exception e)
		{
			saveMessageKey(request, "message.confirm.deleteOther");
			}
		
		return "redirect:list.htm?type=" + type;
	}
	
	private void alManageTrackAddEdit(String id, ModelMap model)
	{
		if(id != null && !id.equals(""))
			model.addAttribute("alManageTrackAddEdit", "Y");
		else
			model.addAttribute("alManageTrackAddEdit", "N");
	}
	
	//FB Form
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id,
			@RequestParam(required = false) String type,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		List<AlManageProject> projectIdList= alManageProjectDAO.getAlManageProjectFilter(null, null, type, userLogin.getRegion());
		model.addAttribute("projectIdList", projectIdList);
		AlManageProjectTrack alManageTrack = (id == null) ? new AlManageProjectTrack() : alManageProjectTrackDAO.selectByPrimaryKey(Integer.parseInt(id));
		model.addAttribute("alManageTrack", alManageTrack);
		alManageTrackAddEdit( id, model);
		loadData(model,type);
		if(id != null && id != ""){
			model.addAttribute("projectIdCBB", alManageTrack.getProjectId());
			if(alManageTrack.getDeliveryPlanDateStart() != null)
				model.addAttribute("deliveryPlanDateStart", df.format(alManageTrack.getDeliveryPlanDateStart()));
			if(alManageTrack.getDeliveryPlanDateEnd() != null)
				model.addAttribute("deliveryPlanDateEnd", df.format(alManageTrack.getDeliveryPlanDateEnd()));
			if(alManageTrack.getInstallationPlanDateStart() != null)
				model.addAttribute("installationPlanDateStart", df.format(alManageTrack.getInstallationPlanDateStart()));
			if(alManageTrack.getInstallationPlanDateEnd() != null)
				model.addAttribute("installationPlanDateEnd", df.format(alManageTrack.getInstallationPlanDateEnd()));
			if(alManageTrack.getCommissioningPlanStart() != null)
				model.addAttribute("commissioningPlanStart", df.format(alManageTrack.getCommissioningPlanStart()));
			if(alManageTrack.getCommissioningPlanEnd() != null)
				model.addAttribute("commissioningPlanEnd", df.format(alManageTrack.getCommissioningPlanEnd()));
			if(alManageTrack.getIntegrationPlanStart() != null)
				model.addAttribute("integrationPlanStart", df.format(alManageTrack.getIntegrationPlanStart()));
			if(alManageTrack.getIntegrationPlanEnd() != null)
				model.addAttribute("integrationPlanEnd", df.format(alManageTrack.getIntegrationPlanEnd()));
			if(alManageTrack.getAcceptancePlanStart() != null)
				model.addAttribute("acceptancePlanStart", df.format(alManageTrack.getAcceptancePlanStart()));
			if(alManageTrack.getAcceptancePlanEnd() != null)
				model.addAttribute("acceptancePlanEnd", df.format(alManageTrack.getAcceptancePlanEnd()));
			
			model.addAttribute("projectIdCBB", alManageTrack.getProjectId());
		}
		else{
			
		}
		model.addAttribute("type", type);
		return "jspalarm/alProject/alManageTrackForm";
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id,
			@RequestParam(required = false) String deliveryPlanDateStart,
			@RequestParam(required = false) String deliveryPlanDateEnd,
			@RequestParam(required = false) String installationPlanDateStart,
			@RequestParam(required = false) String installationPlanDateEnd,
			@RequestParam(required = false) String commissioningPlanStart,
			@RequestParam(required = false) String commissioningPlanEnd,
			@RequestParam(required = false) String integrationPlanStart,
			@RequestParam(required = false) String integrationPlanEnd,
			@RequestParam(required = false) String acceptancePlanStart,
			@RequestParam(required = false) String acceptancePlanEnd,
			@RequestParam(required = false) String type,
			@ModelAttribute("alManageTrack") @Valid AlManageProjectTrack alManageProjectTrack, BindingResult result, 
			ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("type", type);
		//Ném lỗi
		if (result.hasErrors()) {
			loadData(model,type);
			alManageTrackAddEdit( id, model);
			if(result.hasFieldErrors("finishRate"))
				model.addAttribute("finishRateError", "finishRateError");
			if(result.hasFieldErrors("deliveryPlanDateStart"))
				model.addAttribute("deliveryPlanDateStartError", "deliveryPlanDateStartError");
			if(result.hasFieldErrors("deliveryPlanDateEnd"))
				model.addAttribute("deliveryPlanDateEndError", "deliveryPlanDateEndError");
			if(result.hasFieldErrors("installationPlanDateStart"))
				model.addAttribute("installationPlanDateStartError", "installationPlanDateStartError");
			if(result.hasFieldErrors("installationPlanDateEnd"))
				model.addAttribute("installationPlanDateEndError", "installationPlanDateEndError");
			if(result.hasFieldErrors("commissioningPlanStart"))
				model.addAttribute("commissioningPlanStartError", "commissioningPlanStartError");
			if(result.hasFieldErrors("commissioningPlanEnd"))
				model.addAttribute("commissioningPlanEndError", "commissioningPlanEndError");
			if(result.hasFieldErrors("integrationPlanStart"))
				model.addAttribute("integrationPlanStartError", "integrationPlanStartError");
			if(result.hasFieldErrors("integrationPlanEnd"))
				model.addAttribute("integrationPlanEndError", "integrationPlanEndError");
			if(result.hasFieldErrors("acceptancePlanStart"))
				model.addAttribute("acceptancePlanStartError", "acceptancePlanStartError");
			if(result.hasFieldErrors("acceptancePlanEnd"))
				model.addAttribute("acceptancePlanEndError", "acceptancePlanEndError");
			model.addAttribute("deliveryPlanDateStart", deliveryPlanDateStart);
			model.addAttribute("deliveryPlanDateEnd", deliveryPlanDateEnd);
			model.addAttribute("installationPlanDateStart", installationPlanDateStart);
			model.addAttribute("installationPlanDateEnd", installationPlanDateEnd);
			model.addAttribute("commissioningPlanStart", commissioningPlanStart);
			model.addAttribute("commissioningPlanEnd", commissioningPlanEnd);
			model.addAttribute("integrationPlanStart", integrationPlanStart);
			model.addAttribute("integrationPlanEnd", integrationPlanEnd);
			model.addAttribute("acceptancePlanStart", acceptancePlanStart);
			model.addAttribute("acceptancePlanEnd", acceptancePlanEnd);
			model.addAttribute("projectIdCBB", alManageProjectTrack.getProjectId());
			
			return "jspalarm/alProject/alManageTrackForm";
		}
		
		if(id.equals(""))
		{
			if(alManageProjectTrackDAO.checkSiteNameAndNodeKey(alManageProjectTrack.getSiteName(), alManageProjectTrack.getNode(), null).size() == 0){
				alManageProjectTrack.setCreatedBy(username);
				alManageProjectTrackDAO.insert(alManageProjectTrack);
				saveMessageKey(request, "messsage.confirm.addsuccess");
			}
			else{
				loadData(model,type);
				alManageTrackAddEdit( id, model);
				model.addAttribute("deliveryPlanDateStart", deliveryPlanDateStart);
				model.addAttribute("deliveryPlanDateEnd", deliveryPlanDateEnd);
				model.addAttribute("installationPlanDateStart", installationPlanDateStart);
				model.addAttribute("installationPlanDateEnd", installationPlanDateEnd);
				model.addAttribute("commissioningPlanStart", commissioningPlanStart);
				model.addAttribute("commissioningPlanEnd", commissioningPlanEnd);
				model.addAttribute("integrationPlanStart", integrationPlanStart);
				model.addAttribute("integrationPlanEnd", integrationPlanEnd);
				model.addAttribute("acceptancePlanStart", acceptancePlanStart);
				model.addAttribute("acceptancePlanEnd", acceptancePlanEnd);
				model.addAttribute("projectIdCBB", alManageProjectTrack.getProjectId());
				saveMessageKey(request, "messsage.confirm.alManageProjectTrack.checkUniqueKey");
				return "jspalarm/alProject/alManageTrackForm";
			}
		}
		else{
			if(alManageProjectTrackDAO.checkSiteNameAndNodeKey(alManageProjectTrack.getSiteName(), alManageProjectTrack.getNode(), id).size() == 0){
				alManageProjectTrack.setModifiedBy(username);
				alManageProjectTrackDAO.updateByPrimaryKey(alManageProjectTrack);
				saveMessageKey(request, "messsage.confirm.updatesuccess");
			}
			else{
				loadData(model,type);
				alManageTrackAddEdit( id, model);
				model.addAttribute("deliveryPlanDateStart", deliveryPlanDateStart);
				model.addAttribute("deliveryPlanDateEnd", deliveryPlanDateEnd);
				model.addAttribute("installationPlanDateStart", installationPlanDateStart);
				model.addAttribute("installationPlanDateEnd", installationPlanDateEnd);
				model.addAttribute("commissioningPlanStart", commissioningPlanStart);
				model.addAttribute("commissioningPlanEnd", commissioningPlanEnd);
				model.addAttribute("integrationPlanStart", integrationPlanStart);
				model.addAttribute("integrationPlanEnd", integrationPlanEnd);
				model.addAttribute("acceptancePlanStart", acceptancePlanStart);
				model.addAttribute("acceptancePlanEnd", acceptancePlanEnd);
				model.addAttribute("projectIdCBB", alManageProjectTrack.getProjectId());
				saveMessageKey(request, "messsage.confirm.alManageProjectTrack.checkUniqueKey");
				return "jspalarm/alProject/alManageTrackForm";
			}
			
		}
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(@RequestParam(required = false) String type,ModelMap model) {
		model.addAttribute("type", type);
		return "jspalarm/alProject/alManageTrackUpload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile filePath,
			@RequestParam(required = false) String type, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		
		if (!filePath.isEmpty()) {

			String[] ten=filePath.getOriginalFilename().split("\\.");
			String fileExtn = ten[ten.length-1];
			
			if (fileExtn.equals("xls")) {
				if (fileExtn.equals("xls")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsFile2(filePath.getInputStream());
					if(type.equals("UPGRADE"))
						importContentUpgradeSoftware(sheetData, model, request);
					else
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
		model.addAttribute("type", type);
		return "jspalarm/alProject/alManageTrackUpload";
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContentUpgradeSoftware(List sheetData, ModelMap model,HttpServletRequest request) {
		String createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(createdBy);
		
		List<AlManageProjectTrack> successList = new ArrayList<AlManageProjectTrack>();
		List<AlManageProjectTrack> failedList = new ArrayList<AlManageProjectTrack>();
		List<AlManageProjectTrack> success = new ArrayList<AlManageProjectTrack>();
		
		String projectCode;
		String siteName;
		String node;
		String team;
		String finishRate;
		String deliveryPlanSDate;
		String deliveryPlanEDate;
		String deliveryPlanRate;
		String deliveryPlanStatus;
		String deliveryPlanVendorStaff;
		String deliveryPlanVmsStaff;
		String deliveryPlanDescription;
		 
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 12) {
        		saveMessageKey(request, "sidebar.admin.alManageProjectUploadErrorStructuresNumber");
        		
        		return "jspalarm/alProject/alManageTrackUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		AlManageProjectTrack alManageTrack;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			alManageTrack = new AlManageProjectTrack();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=12; j++) {
     					data.add("");
     				}
        			
        			projectCode 					= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			siteName						= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			node							= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			team							= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			finishRate						= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			deliveryPlanSDate				= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			deliveryPlanEDate				= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			deliveryPlanRate				= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			deliveryPlanStatus				= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			deliveryPlanVendorStaff			= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			deliveryPlanVmsStaff			= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
        			deliveryPlanDescription			= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim();
        			 
        			// Kiem tra loi
        			if (siteName == null || node == null || projectCode == null
        					//|| (projectCode!= null && alManageProjectDAO.getObjectProjectByCode(projectCode) == null)
        					|| (projectCode!= null && alManageProjectDAO.getObjectProjectByCode("UPGRADE", projectCode.trim()) == null)
						) {
						error = true;
					}
        			
        			try
	    			{
        				if(finishRate != null){
		        			Integer a = Integer.parseInt(finishRate);
		        			alManageTrack.setFinishRate(a);
	        			}
        				if(deliveryPlanRate != null){
		        			Integer a = Integer.parseInt(deliveryPlanRate);
		        			alManageTrack.setDeliveryPlanRate(a);
	        			}
        				 
        				
	    			}
	    			catch(Exception e)
	    			{
	    				error = true;
	    			}
        			
        			try
	    			{
        				if(deliveryPlanSDate != null){
        					
        					alManageTrack.setDeliveryPlanDateStart(new SimpleDateFormat("dd/MM/yyyy").parse(deliveryPlanSDate));
	    				}
        				if(deliveryPlanEDate != null){
        					
        					alManageTrack.setDeliveryPlanDateEnd(new SimpleDateFormat("dd/MM/yyyy").parse(deliveryPlanEDate));
	    				}
        				 
	    			}
	    			catch(Exception e)
	    			{
	    				error = true;
	    			}
        			
        			//---------------------------------------------------------------------------
        			if(projectCode != null)
        				alManageTrack.setProjectCode(projectCode.trim());
        			alManageTrack.setSiteName(siteName);
        			alManageTrack.setNode(node);
        			alManageTrack.setTeam(team);
        			alManageTrack.setDescription(deliveryPlanStatus);
        			alManageTrack.setDeliveryPlanVendorStaff(deliveryPlanVendorStaff);
        			alManageTrack.setDeliveryPlanVmsStaff(deliveryPlanVmsStaff);
        			alManageTrack.setDeliveryPlanDescription(deliveryPlanDescription);
        			alManageTrack.setDeliveryPlanStatus(deliveryPlanStatus);
        			 
        			
        			if (siteName == null && node == null && projectCode == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(alManageTrack);
    					} else  {
    						
    						successList.add(alManageTrack);
    					}
        			}
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (AlManageProjectTrack record: successList) {
			try {
					AlManageProject alManageProject = alManageProjectDAO.getObjectProjectByCode("UPGRADE", record.getProjectCode());
					record.setProjectId(alManageProject.getId());
				
					if(alManageProjectTrackDAO.checkSiteNameAndNodeKey(record.getSiteName(), record.getNode(), null).size() == 0){
						record.setmaPhong(userLogin.getMaPhong());
						record.setCreatedBy(createdBy);
						alManageProjectTrackDAO.insert(record);
					}else{
						record.setModifiedBy(createdBy);
						alManageProjectTrackDAO.updateByUniqueKey(record);
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
			model.addAttribute("status", "Dữ liệu dự án tổng đài không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");	// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", success.size());
		model.addAttribute("totalNum", failedList.size()+success.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		return "jspalarm/alProject/alManageTrackUpload";
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent(List sheetData, ModelMap model,HttpServletRequest request) {
		String createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<AlManageProjectTrack> successList = new ArrayList<AlManageProjectTrack>();
		List<AlManageProjectTrack> failedList = new ArrayList<AlManageProjectTrack>();
		List<AlManageProjectTrack> success = new ArrayList<AlManageProjectTrack>();
		
		String projectCode;
		String siteName;
		String node;
		String team;
		String finishRate;
		String deliveryPlanSDate;
		String deliveryPlanEDate;
		String deliveryPlanRate;
		String deliveryPlanStatus;
		String deliveryPlanVendorStaff;
		String deliveryPlanVmsStaff;
		String deliveryPlanDescription;
		String installationPlanSDate;
		String installationPlanEDate;
		String installationPlanRate;
		String installationPlanVendorStaff;
		String installationPlanVmsStaff;
		String installationPlanStatus;
		String installationPlanDescription;
		String commissioningPlanSDate;
		String commissioningPlanEDate;
		String integrationPlanSDate;
		String integrationPlanEDate;
		String acceptancePlanSDate;
		String acceptancePlanEDate;
		String supervisor;
		String commissioningResponsible;
		String atnd;
		String manager;
		String supporter;
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 30) {
        		saveMessageKey(request, "sidebar.admin.alManageProjectUploadErrorStructuresNumber");
        		
        		return "jspalarm/alProject/alManageTrackUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		AlManageProjectTrack alManageTrack;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			alManageTrack = new AlManageProjectTrack();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=30; j++) {
     					data.add("");
     				}
        			
        			projectCode 					= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			siteName						= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			node							= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			team							= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			finishRate						= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			deliveryPlanSDate				= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			deliveryPlanEDate				= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			deliveryPlanRate				= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			deliveryPlanStatus				= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			deliveryPlanVendorStaff			= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			deliveryPlanVmsStaff			= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
        			deliveryPlanDescription			= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim();
        			installationPlanSDate			= data.get(12).toString().trim().equals("")?null:data.get(12).toString().trim();
        			installationPlanEDate			= data.get(13).toString().trim().equals("")?null:data.get(13).toString().trim();
        			installationPlanRate			= data.get(14).toString().trim().equals("")?null:data.get(14).toString().trim();
        			installationPlanVendorStaff		= data.get(15).toString().trim().equals("")?null:data.get(15).toString().trim();
        			installationPlanVmsStaff		= data.get(16).toString().trim().equals("")?null:data.get(16).toString().trim();
        			installationPlanStatus			= data.get(17).toString().trim().equals("")?null:data.get(17).toString().trim();
        			installationPlanDescription		= data.get(18).toString().trim().equals("")?null:data.get(18).toString().trim();
        			commissioningPlanSDate			= data.get(19).toString().trim().equals("")?null:data.get(19).toString().trim();
        			commissioningPlanEDate			= data.get(20).toString().trim().equals("")?null:data.get(20).toString().trim();
        			integrationPlanSDate			= data.get(21).toString().trim().equals("")?null:data.get(21).toString().trim();
        			integrationPlanEDate			= data.get(22).toString().trim().equals("")?null:data.get(22).toString().trim();
        			acceptancePlanSDate				= data.get(23).toString().trim().equals("")?null:data.get(23).toString().trim();
        			acceptancePlanEDate				= data.get(24).toString().trim().equals("")?null:data.get(24).toString().trim();
        			supervisor						= data.get(25).toString().trim().equals("")?null:data.get(25).toString().trim();
        			commissioningResponsible		= data.get(26).toString().trim().equals("")?null:data.get(26).toString().trim();
        			atnd							= data.get(27).toString().trim().equals("")?null:data.get(27).toString().trim();
        			manager							= data.get(28).toString().trim().equals("")?null:data.get(28).toString().trim();
        			supporter						= data.get(29).toString().trim().equals("")?null:data.get(29).toString().trim();
        			
        			// Kiem tra loi
        			if (siteName == null || node == null || projectCode == null
        					//|| (projectCode!= null && alManageProjectDAO.getObjectProjectByCode(projectCode) == null)
        					|| (projectCode!= null && alManageProjectDAO.getObjectProjectByCode("FOLLOW", projectCode.trim()) == null)
						) {
						error = true;
					}
        			
        			try
	    			{
        				if(finishRate != null){
		        			Integer a = Integer.parseInt(finishRate);
		        			alManageTrack.setFinishRate(a);
	        			}
        				if(deliveryPlanRate != null){
		        			Integer a = Integer.parseInt(deliveryPlanRate);
		        			alManageTrack.setDeliveryPlanRate(a);
	        			}
        				if(installationPlanRate != null){
		        			Integer a = Integer.parseInt(installationPlanRate);
		        			alManageTrack.setInstallationPlanRate(a);
	        			}
        				
	    			}
	    			catch(Exception e)
	    			{
	    				error = true;
	    			}
        			
        			try
	    			{
        				if(deliveryPlanSDate != null){
        					
        					alManageTrack.setDeliveryPlanDateStart(new SimpleDateFormat("dd/MM/yyyy").parse(deliveryPlanSDate));
	    				}
        				if(deliveryPlanEDate != null){
        					
        					alManageTrack.setDeliveryPlanDateEnd(new SimpleDateFormat("dd/MM/yyyy").parse(deliveryPlanEDate));
	    				}
        				if(installationPlanSDate != null){
        					alManageTrack.setInstallationPlanDateStart(new SimpleDateFormat("dd/MM/yyyy").parse(installationPlanSDate));
	    				}
        				if(installationPlanEDate != null){
        					alManageTrack.setInstallationPlanDateEnd(new SimpleDateFormat("dd/MM/yyyy").parse(installationPlanEDate));
	    				}
        				if(commissioningPlanSDate != null){
        					alManageTrack.setCommissioningPlanStart(new SimpleDateFormat("dd/MM/yyyy").parse(commissioningPlanSDate));
	    				}
        				if(commissioningPlanEDate != null){
        					alManageTrack.setCommissioningPlanEnd(new SimpleDateFormat("dd/MM/yyyy").parse(commissioningPlanEDate));
	    				}
        				if(integrationPlanSDate != null){
        					alManageTrack.setIntegrationPlanStart(new SimpleDateFormat("dd/MM/yyyy").parse(integrationPlanSDate));
	    				}
        				if(integrationPlanEDate != null){
        					alManageTrack.setIntegrationPlanEnd(new SimpleDateFormat("dd/MM/yyyy").parse(integrationPlanEDate));
	    				}
        				if(acceptancePlanSDate != null){
        					alManageTrack.setAcceptancePlanStart(new SimpleDateFormat("dd/MM/yyyy").parse(acceptancePlanSDate));
	    				}
        				if(acceptancePlanEDate != null){
        					alManageTrack.setAcceptancePlanEnd(new SimpleDateFormat("dd/MM/yyyy").parse(acceptancePlanEDate));
	    				}
	    			}
	    			catch(Exception e)
	    			{
	    				error = true;
	    			}
        			
        			//---------------------------------------------------------------------------
        			if(projectCode != null)
        				alManageTrack.setProjectCode(projectCode.trim());
        			alManageTrack.setSiteName(siteName);
        			alManageTrack.setNode(node);
        			alManageTrack.setTeam(team);
        			alManageTrack.setDescription(deliveryPlanStatus);
        			alManageTrack.setDeliveryPlanVendorStaff(deliveryPlanVendorStaff);
        			alManageTrack.setDeliveryPlanVmsStaff(deliveryPlanVmsStaff);
        			alManageTrack.setDeliveryPlanDescription(deliveryPlanDescription);
        			alManageTrack.setDeliveryPlanStatus(deliveryPlanStatus);
        			alManageTrack.setInstallationPlanVendorStaff(installationPlanVendorStaff);
        			alManageTrack.setInstallationPlanVmsStaff(installationPlanVmsStaff);
        			alManageTrack.setInstallationPlanStatus(installationPlanStatus);
        			alManageTrack.setInstallationPlanDescription(installationPlanDescription);
        			alManageTrack.setSupervisor(supervisor);
        			alManageTrack.setCommissioningResponsible(commissioningResponsible);
        			alManageTrack.setAtnd(atnd);
        			alManageTrack.setManager(manager);
        			alManageTrack.setSupporter(supporter);
        			
        			if (siteName == null && node == null && projectCode == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(alManageTrack);
    					} else  {
    						
    						successList.add(alManageTrack);
    					}
        			}
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (AlManageProjectTrack record: successList) {
			try {
					AlManageProject alManageProject = alManageProjectDAO.getObjectProjectByCode("FOLLOW", record.getProjectCode());
					record.setProjectId(alManageProject.getId());
				
					if(alManageProjectTrackDAO.checkSiteNameAndNodeKey(record.getSiteName(), record.getNode(), null).size() == 0){
						record.setCreatedBy(createdBy);
						alManageProjectTrackDAO.insert(record);
					}else{
						record.setModifiedBy(createdBy);
						alManageProjectTrackDAO.updateByUniqueKey(record);
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
			model.addAttribute("status", "Dữ liệu dự án tổng đài không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");	// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", success.size());
		model.addAttribute("totalNum", failedList.size()+success.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		return "jspalarm/alProject/alManageTrackUpload";
	}
	
	@RequestMapping("exportData")
  	public ModelAndView exportData(
  			@RequestParam(required = false) String type,
  			@RequestParam(required = false) String projectCode,
  			@RequestParam(required = false) String siteName,
			@RequestParam(required = false) String node, 	
			@RequestParam(required = false) String maPhong, 	
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
		
		List<AlManageProjectTrack> alManageProjectTrack = alManageProjectTrackDAO.getAlManageTrackFilter(type,projectCode, siteName, node, null, null, sortfield, sortorder, strWhere,maPhong);
		String filename = "";
		if(type.equals("UPGRADE"))
		{
			exportReportSwVersion(tempFile, alManageProjectTrack);
			filename = "UpgradeSoftware_";
		}
			
		else
		{
			exportReport(tempFile, alManageProjectTrack);
			filename = "AlManageTrack_";
		}
			
		
		// return
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		filename = filename+ dateNow;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();
  		return null;
  	}
	
	private void exportReportSwVersion(File tempFile, List<AlManageProjectTrack> alManageProjectTrack) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("Upgrade Software List", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
		 
			String label ="Project Code,Site Name,Node,Team,Finish Rate,SDate,EDate,Plan Rate,Status,Vendor,VMS,Software Version";
			String[] labelArray = label.split(",");
			for(int i=0; i < labelArray.length; i++)
			{
				Label lb = new Label(i,0,labelArray[i],cellFormatHeader);
				sheet.addCell(lb);
			}
			
			int j = 1;
				for (AlManageProjectTrack record : alManageProjectTrack) {
					int k = 0;
					Label record0 = new Label(k, j, record.getProjectCode(), cellFormat);
					sheet.addCell(record0);
					k++;
					Label record1 = new Label(k, j,record.getSiteName(), cellFormat);
					sheet.addCell(record1);
					k++;
					Label record2 = new Label(k, j, record.getNode(), cellFormat);
					sheet.addCell(record2);
					k++;
					Label record3 = new Label(k, j, record.getTeam(), cellFormat);
					sheet.addCell(record3);
					k++;
					Label record4 = new Label(k, j, record.getFinishRateStr(), cellFormat);
					sheet.addCell(record4);
					k++;
					Label record5 = new Label(k, j, record.getDeliveryPlanDateStartStr(), cellFormat);
					sheet.addCell(record5);
					k++;
					Label record6 = new Label(k, j, record.getDeliveryPlanDateEndStr(), cellFormat);
					sheet.addCell(record6);
					k++;
					Label record7 = new Label(k, j, record.getDeliveryPlanRateStr(), cellFormat);
					sheet.addCell(record7);
					k++;
					Label record8 = new Label(k, j, record.getDeliveryPlanStatus(), cellFormat);
					sheet.addCell(record8);
					k++;
					Label record9 = new Label(k, j, record.getDeliveryPlanVendorStaff(), cellFormat);
					sheet.addCell(record9);
					k++;
					Label record10 = new Label(k, j, record.getDeliveryPlanVmsStaff(), cellFormat);
					sheet.addCell(record10);
					k++;
					Label record11 = new Label(k, j, record.getDeliveryPlanDescription(), cellFormat);
					sheet.addCell(record11);
					k++;
					Label record12 = new Label(k, j, record.getInstallationPlanDateStartStr(), cellFormat);
					sheet.addCell(record12);
					k++;
					Label record13 = new Label(k, j, record.getInstallationPlanDateEndStr(), cellFormat);
					sheet.addCell(record13);
					k++;
					Label record14 = new Label(k, j, record.getInstallationPlanRateStr(), cellFormat);
					sheet.addCell(record14);
					k++;
					Label record15 = new Label(k, j, record.getInstallationPlanVendorStaff(), cellFormat);
					sheet.addCell(record15);
					k++;
					Label record16 = new Label(k, j, record.getInstallationPlanVmsStaff(), cellFormat);
					sheet.addCell(record16);
					k++;
					Label record17 = new Label(k, j, record.getInstallationPlanStatus(), cellFormat);
					sheet.addCell(record17);
					k++;
					Label record18 = new Label(k, j, record.getInstallationPlanDescription(), cellFormat);
					sheet.addCell(record18);
					k++; 
					j++;
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
	
	private void exportReport(File tempFile, List<AlManageProjectTrack> alManageProjectTrack) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("Track", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			int i=0;
			
			Label label0 = new Label(i, 0, "Project Code", cellFormatHeader);
			sheet.addCell(label0);
			i++;
			Label label1 = new Label(i, 0, "Site Name", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "Node", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "Team", cellFormatHeader);
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "Finish Rate", cellFormatHeader);
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "Delivery Plan SDate", cellFormatHeader);
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "Delivery Plan EDate", cellFormatHeader);
			sheet.addCell(label6);
			i++;
			Label label7 = new Label(i, 0, "Delivery Plan Rate", cellFormatHeader);
			sheet.addCell(label7);
			i++;
			Label label8 = new Label(i, 0, "Delivery Plan Status", cellFormatHeader);
			sheet.addCell(label8);
			i++;
			Label label9 = new Label(i, 0, "Delivery Plan Vendor", cellFormatHeader);
			sheet.addCell(label9);
			i++;
			Label label10 = new Label(i, 0, "Delivery Plan VMS", cellFormatHeader);
			sheet.addCell(label10);
			i++;
			Label label11 = new Label(i, 0, "Delivery Plan Description", cellFormatHeader);
			sheet.addCell(label11);
			i++;
			Label label12 = new Label(i, 0, "Installation Plan EDate", cellFormatHeader);
			sheet.addCell(label12);
			i++;
			Label label13 = new Label(i, 0, "Installation Plan SDate", cellFormatHeader);
			sheet.addCell(label13);
			i++;
			Label label14 = new Label(i, 0, "Installation Plan Rate", cellFormatHeader);
			sheet.addCell(label14);
			i++;
			Label label15 = new Label(i, 0, "Installation Plan Vendor", cellFormatHeader);
			sheet.addCell(label15);
			i++;
			Label label16 = new Label(i, 0, "Installation Plan VMS", cellFormatHeader);
			sheet.addCell(label16);
			i++;
			Label label17 = new Label(i, 0, "Installation Plan Status", cellFormatHeader);
			sheet.addCell(label17);
			i++;
			Label label18 = new Label(i, 0, "Installation Plan Description", cellFormatHeader);
			sheet.addCell(label18);
			i++;
			Label label19 = new Label(i, 0, "Commissioning Plan SDate", cellFormatHeader);
			sheet.addCell(label19);
			i++;
			Label label20 = new Label(i, 0, "Commissioning Plan EDate", cellFormatHeader);
			sheet.addCell(label20);
			i++;
			Label label21 = new Label(i, 0, "Integration Plan SDate", cellFormatHeader);
			sheet.addCell(label21);
			i++;
			Label label22 = new Label(i, 0, "Integration Plan EDate", cellFormatHeader);
			sheet.addCell(label22);
			i++;
			Label label23 = new Label(i, 0, "Acceptance Plan SDate", cellFormatHeader);
			sheet.addCell(label23);
			i++;
			Label label24 = new Label(i, 0, "Acceptance Plan EDate", cellFormatHeader);
			sheet.addCell(label24);
			i++;
			Label label25 = new Label(i, 0, "Installation Supervisor", cellFormatHeader);
			sheet.addCell(label25);
			i++;
			Label label26 = new Label(i, 0, "Commissioning Responsible", cellFormatHeader);
			sheet.addCell(label26);
			i++;
			Label label27 = new Label(i, 0, "ATND", cellFormatHeader);
			sheet.addCell(label27);
			i++;
			Label label28 = new Label(i, 0, "Chủ trì", cellFormatHeader);
			sheet.addCell(label28);
			i++;
			Label label29 = new Label(i, 0, "Phối hợp", cellFormatHeader);
			sheet.addCell(label29);
			i++;
			i = 1;
			
			for (AlManageProjectTrack record : alManageProjectTrack) {
				int j=0;
				Label record0 = new Label(j, i, record.getProjectCode(), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,record.getSiteName(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getNode(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getTeam(), cellFormat);
				sheet.addCell(record3);
				j++;
				Label record4 = new Label(j, i, record.getFinishRateStr(), cellFormat);
				sheet.addCell(record4);
				j++;
				Label record5 = new Label(j, i, record.getDeliveryPlanDateStartStr(), cellFormat);
				sheet.addCell(record5);
				j++;
				Label record6 = new Label(j, i, record.getDeliveryPlanDateEndStr(), cellFormat);
				sheet.addCell(record6);
				j++;
				Label record7 = new Label(j, i, record.getDeliveryPlanRateStr(), cellFormat);
				sheet.addCell(record7);
				j++;
				Label record8 = new Label(j, i, record.getDeliveryPlanStatus(), cellFormat);
				sheet.addCell(record8);
				j++;
				Label record9 = new Label(j, i, record.getDeliveryPlanVendorStaff(), cellFormat);
				sheet.addCell(record9);
				j++;
				Label record10 = new Label(j, i, record.getDeliveryPlanVmsStaff(), cellFormat);
				sheet.addCell(record10);
				j++;
				Label record11 = new Label(j, i, record.getDeliveryPlanDescription(), cellFormat);
				sheet.addCell(record11);
				j++;
				Label record12 = new Label(j, i, record.getInstallationPlanDateStartStr(), cellFormat);
				sheet.addCell(record12);
				j++;
				Label record13 = new Label(j, i, record.getInstallationPlanDateEndStr(), cellFormat);
				sheet.addCell(record13);
				j++;
				Label record14 = new Label(j, i, record.getInstallationPlanRateStr(), cellFormat);
				sheet.addCell(record14);
				j++;
				Label record15 = new Label(j, i, record.getInstallationPlanVendorStaff(), cellFormat);
				sheet.addCell(record15);
				j++;
				Label record16 = new Label(j, i, record.getInstallationPlanVmsStaff(), cellFormat);
				sheet.addCell(record16);
				j++;
				Label record17 = new Label(j, i, record.getInstallationPlanStatus(), cellFormat);
				sheet.addCell(record17);
				j++;
				Label record18 = new Label(j, i, record.getInstallationPlanDescription(), cellFormat);
				sheet.addCell(record18);
				j++;
				Label record19 = new Label(j, i, record.getCommissioningPlanStartStr(), cellFormat);
				sheet.addCell(record19);
				j++;
				Label record20 = new Label(j, i, record.getCommissioningPlanEndStr(), cellFormat);
				sheet.addCell(record20);
				j++;
				Label record21 = new Label(j, i, record.getIntegrationPlanStartStr(), cellFormat);
				sheet.addCell(record21);
				j++;
				Label record22 = new Label(j, i, record.getIntegrationPlanEndStr(), cellFormat);
				sheet.addCell(record22);
				j++;
				Label record23 = new Label(j, i, record.getAcceptancePlanStartStr(), cellFormat);
				sheet.addCell(record23);
				j++;
				Label record24 = new Label(j, i, record.getAcceptancePlanEndStr(), cellFormat);
				sheet.addCell(record24);
				j++;
				Label record25 = new Label(j, i, record.getSupervisor(), cellFormat);
				sheet.addCell(record25);
				j++;
				Label record26 = new Label(j, i, record.getCommissioningResponsible(), cellFormat);
				sheet.addCell(record26);
				j++;
				Label record27 = new Label(j, i, record.getAtnd(), cellFormat);
				sheet.addCell(record27);
				j++;
				Label record28 = new Label(j, i, record.getManager(), cellFormat);
				sheet.addCell(record28);
				j++;
				Label record29 = new Label(j, i, record.getSupporter(), cellFormat);
				sheet.addCell(record29);
				j++;
				
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
	@RequestMapping("ajax/loadprojectname")
	public @ResponseBody
	List<AlManageProject> loadProjectName(@RequestParam(required = false) String system, HttpServletRequest request) {
		AlManageProject alManaProject = alManageProjectDAO.selectByPrimaryKey(Integer.parseInt(system));
		List<AlManageProject> alManageProjectList = new ArrayList<AlManageProject>();
		alManageProjectList.add(alManaProject);
		 
		return alManageProjectList;
	}
}
