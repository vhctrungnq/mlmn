package controller.iso;

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
import vo.CHighlightConfigs;
import vo.CTableConfig;
import vo.HProvincesCode;
import vo.IsoAssetType;
import vo.IsoInventory;
import vo.MDepartment;
import vo.SYS_PARAMETER;
import vo.SysFiles;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.HelpTableConfigs;
import vo.alarm.utils.UploadTools;

import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.HProvincesCodeDAO;
import dao.IsoAssetTypeDAO;
import dao.IsoInventoryDAO;
import dao.MDepartmentDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysFilesDAO;

/**
 * Function        : Quan ly thiet bi, card du phong, tai nguyen mang luoi
 * Created By      : BUIQUANG
 * Create Date     : 27/08/2013
 * Modified By     : BUIQUANG
 * Modify Date     : 02/12/2013
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/iso/quan-ly-thiet-bi/*")
public class IsoEquipmentController extends BaseController{

	@Autowired
	private IsoInventoryDAO isoInventoryDAO;
	@Autowired
	private MDepartmentDAO mDepartmentDAO;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDAO;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
	private IsoAssetTypeDAO isoAssetTypeDAO;
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	@Autowired
	private SysFilesDAO sysFilesDAO;
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
	
	@RequestMapping("list")
	public ModelAndView list(
			   @RequestParam(required = false) String deptCode,
			   @RequestParam(required = false) String team,
			   @RequestParam(required = false) String subTeam,
			   @RequestParam(required = false) String province, 
			   @RequestParam(required = false) String district,
			   @RequestParam(required = false) String neType,
			   @RequestParam(required = false) String ne,
			   @RequestParam(required = false) String neGroup,
			   @RequestParam(required = false) String locationName,
			   @RequestParam(required = false) String vendor,
			   @RequestParam(required = false) String productCode,
			   @RequestParam(required = false) String productName,
			   @RequestParam(required = false) String seriNo,
			   @RequestParam(required = false) String inputStatus,
			   @RequestParam(required = false) String status, ModelMap model, HttpServletRequest request, HttpServletResponse response)
	{
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "ThietBi_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		String filterUrl = "";
		String temp = "";
		if(deptCode != null){
			filterUrl += temp + "deptCode=" + deptCode.trim(); 
			temp = "&";
		}
		if(team != null){
			filterUrl += temp + "team=" + team.trim(); 
			temp = "&";
		}
		if(subTeam != null){
			filterUrl += temp + "subTeam=" + subTeam.trim(); 
			temp = "&";
		}
		if(province != null){
			filterUrl += temp + "province=" + province.trim(); 
			temp = "&";
		}
		if(district != null){
			filterUrl += temp + "district=" + district.trim(); 
			temp = "&";
		}
		if(neType != null){
			filterUrl += temp + "neType=" + neType.trim(); 
			temp = "&";
		}
		if(ne != null){
			filterUrl += temp + "ne=" + ne.trim(); 
			temp = "&";
		}
		if(neGroup != null){
			filterUrl += temp + "neGroup=" + neGroup.trim(); 
			temp = "&";
		}
		if(locationName != null){
			filterUrl += temp + "locationName=" + locationName.trim(); 
			temp = "&";
		}
		if(vendor != null){
			filterUrl += temp + "vendor=" + vendor.trim(); 
			temp = "&";
		}
		if(productCode != null){
			filterUrl += temp + "productCode=" + productCode.trim();
			temp = "&";
		}
		if(productName != null){
			filterUrl += temp + "productName=" + productName.trim();
			temp = "&";
		}
		if(seriNo != null){
			filterUrl += temp + "seriNo=" + seriNo.trim();
			temp = "&";
		}
		if(inputStatus != null){
			filterUrl += temp + "inputStatus=" + inputStatus.trim();
			temp = "&";
		}
		if(status != null){
			filterUrl += temp + "status=" + status.trim();
		}
		if(filterUrl != "")
			filterUrl = "?" + filterUrl;
		
		/*
		 * Quản lý thiết bị
		 */
		if(inputStatus != null && inputStatus.equals("DEVICE")){
			//do du lieu ra grid
			List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("ISO_INVENTORY_EQUIPMENT");
			//lay du lieu column cua grid
			model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
			//Lay du lieu datafield cua grid
			model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
			//Lay du lieu cac cot an hien cua grid
			List<CTableConfig> columnList = cTableConfigDAO.getColumnList("ISO_INVENTORY_EQUIPMENT");
			model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
		}
		/*
		 * Quản lý tài nguyên mạng lưới
		 */
		else if(inputStatus != null && inputStatus.equals("USAGE"))
		{
			//do du lieu ra grid
			List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("ISO_INVENTORY_RESOURCE");
			//lay du lieu column cua grid
			model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
			//Lay du lieu datafield cua grid
			model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
			//Lay du lieu cac cot an hien cua grid
			List<CTableConfig> columnList = cTableConfigDAO.getColumnList("ISO_INVENTORY_RESOURCE");
			model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
		}
		/*
		 * Quản lý thiết bị/card dự phòng
		 */
		else
		{
			//do du lieu ra grid
			List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("ISO_INVENTORY_RESOURCE");
			//lay du lieu column cua grid
			model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
			//Lay du lieu datafield cua grid
			model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
			//Lay du lieu cac cot an hien cua grid
			List<CTableConfig> columnList = cTableConfigDAO.getColumnList("ISO_INVENTORY_RESOURCE");
			model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
		}
		
		//Lay duong link url de lay du lieu do vao grid
		model.addAttribute("url", "data.htm" + filterUrl);
		model.addAttribute("deptCode", deptCode);
		model.addAttribute("team", team);
		model.addAttribute("subTeam", subTeam);
		model.addAttribute("province", province);
		model.addAttribute("district", district);
		model.addAttribute("neType", neType);
		model.addAttribute("neName", ne);
		model.addAttribute("neGroup", neGroup);
		model.addAttribute("locationName", locationName);
		model.addAttribute("vendor", vendor);
		model.addAttribute("productCode", productCode);
		model.addAttribute("productName", productName);
		model.addAttribute("seriNo", seriNo);
		model.addAttribute("inputStatus", inputStatus);
		model.addAttribute("status", status);
		
		return new ModelAndView("jspiso/isoEquipmentList");
	}
	
	@RequestMapping("/data")
	public void doGet(
				@RequestParam(required = false) String deptCode,
				@RequestParam(required = false) String team,
				@RequestParam(required = false) String subTeam,
				@RequestParam(required = false) String province, 
				@RequestParam(required = false) String district,
				@RequestParam(required = false) String neType,
				@RequestParam(required = false) String ne,
				@RequestParam(required = false) String neGroup,
				@RequestParam(required = false) String locationName,
				@RequestParam(required = false) String vendor,
				@RequestParam(required = false) String productCode,
				@RequestParam(required = false) String productName,
				@RequestParam(required = false) String seriNo,
				@RequestParam(required = false) String inputStatus,
				@RequestParam(required = false) String status,
				ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		List<IsoInventory> isoEquipmentList = null;
		int startRecord = 0, endRecord = 0, pageSize = 100;
		String sortfield = "DAY";
		String sortorder = "";
		int pageNum = Integer.parseInt(request.getParameter("pagenum"));
		if(pageNum == -1)
			pageNum = 1;
		if(!request.getParameter("pagesize").equals(""))
			 pageSize = Integer.parseInt(request.getParameter("pagesize"));
		else pageSize = 100;
		sortfield = request.getParameter("sortdatafield");
		sortorder = request.getParameter("sortorder");
		List<CTableConfig> columnList = null;
		List<CTableConfig> tableConfigList = null;
		if(inputStatus != null && inputStatus.equals("DEVICE")){
			columnList = cTableConfigDAO.getTableConfigGet("ISO_INVENTORY_EQUIPMENT", sortfield);
			tableConfigList = cTableConfigDAO.getTableConfigGet("ISO_INVENTORY_EQUIPMENT", null);
		}
		else
		{
			columnList = cTableConfigDAO.getTableConfigGet("ISO_INVENTORY_RESOURCE", sortfield);
			tableConfigList = cTableConfigDAO.getTableConfigGet("ISO_INVENTORY_RESOURCE", null);
		}
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
			isoEquipmentList = isoInventoryDAO.getIsoEquipmentFilter(deptCode, team, subTeam, province, district, 
					neType, ne, neGroup, locationName, vendor, productCode, productName, seriNo, 
					inputStatus, status, "", startRecord, endRecord, sortfield, sortorder, strWhere);
			totalRow = isoInventoryDAO.countIsoEquipmentFilter(deptCode, team, subTeam, province, district, neType, ne, neGroup, locationName, vendor, productCode, productName, seriNo, inputStatus, status, "", strWhere);
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
		String jsonCartList = gs.toJson(isoEquipmentList);
		strjson+= jsonCartList;
	    strjson += "}]";
	    
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(strjson);
		out.close();
	}
	
	
	//FB Form
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, 
			@RequestParam(required = false) String inputStatus,
			@RequestParam(required = false) String initializeDate,
			@RequestParam(required = false) String productDate,
			@RequestParam(required = false) String maintenanceDate,
			@RequestParam(required = false) String contractDate,
			@RequestParam(required = false) String updated, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		IsoInventory isoInventory = (id == null ) ? new IsoInventory() : isoInventoryDAO.selectByPrimaryKey(Integer.parseInt(id));
		
		model.addAttribute("isoInventory", isoInventory);
		String temp = "";
		if(isoInventory.getProvince() != null && isoInventory.getProvince() != "" && isoInventory.getProvince().length() > 1)
			temp = isoInventory.getProvince();
		loadCombobox(model, temp);
		isoEquipmentAddEdit(id, model);
		
		if(id != null && id != ""){
			String fileList = "";
			List<SysFiles> sysFilesList = sysFilesDAO.getSysFilesByMapping(isoInventory.getId().toString());
			String hello = "";
			for(int i=0;i<sysFilesList.size();i++){
				fileList += hello + sysFilesList.get(i).getId();
				hello = ",";
			}
			isoInventory.setFileId(fileList);
			if(isoInventory.getInitializeDate() != null)
				model.addAttribute("initializeDate", df.format(isoInventory.getInitializeDate()));
			if(isoInventory.getProductDate() != null)
				model.addAttribute("productDate", df.format(isoInventory.getProductDate()));
			if(isoInventory.getMaintenanceDate() != null)
				model.addAttribute("maintenanceDate", df.format(isoInventory.getMaintenanceDate()));
			if(isoInventory.getContractDate() != null)
				model.addAttribute("contractDate", df.format(isoInventory.getContractDate()));
			if(isoInventory.getUpdated() != null)
				model.addAttribute("updated", df.format(isoInventory.getUpdated()));
			model.addAttribute("neTypeCBB", isoInventory.getNeType());
			model.addAttribute("statusCBB", isoInventory.getStatus());
			model.addAttribute("neGroupCBB", isoInventory.getNeGroup());
			model.addAttribute("tinhThanhCBB", isoInventory.getProvince());
			model.addAttribute("quanHuyenCBB", isoInventory.getDistrict());
			model.addAttribute("provinceCBB", temp);
			model.addAttribute("deptCodeCBB", isoInventory.getDeptCode());
		}
		else{
			// Ngay thang
			if (initializeDate == null || initializeDate.equals("") || DateTools.isValid("dd/MM/yyyy", initializeDate)==false) {
				initializeDate = df.format(new Date());
			}
			model.addAttribute("initializeDate", initializeDate);
		}
		model.addAttribute("inputStatus", inputStatus);
		return "jspiso/isoEquipmentForm";
	}
	
	private void loadCombobox(ModelMap model, String province){
		try{
			List<MDepartment> mDepartmentList = mDepartmentDAO.getDepartmentByEquipment();
			model.addAttribute("mDepartmentList", mDepartmentList);
			
			List<HProvincesCode> hProvinceCodeList = hProvincesCodeDAO.getHProvinceCodeByEquiment();
			model.addAttribute("hProvinceCodeList", hProvinceCodeList);
			
			if((province == null || province == "") && hProvinceCodeList.size() > 0){
				province = hProvinceCodeList.get(0).getProvince();
			}
			
			List<HProvincesCode> quanhuyenList= hProvincesCodeDAO.getHProvincesCodeList(province);
			model.addAttribute("quanhuyenList", quanhuyenList);
			
			List<SYS_PARAMETER> neTypeList = sysParameterDao.getEquipmentType();
			model.addAttribute("neTypeList", neTypeList);
			
			List<IsoAssetType> codeAssetTypeList = isoAssetTypeDAO.getIsoAssetTypeList();
			model.addAttribute("codeAssetTypeList", codeAssetTypeList);
			
			List<SYS_PARAMETER> statusList = sysParameterDao.getStatusEquipmentType();
			model.addAttribute("statusList", statusList);
			
			List<SYS_PARAMETER> vendorForResourceList = sysParameterDao.getVendorForResourceList();
			model.addAttribute("vendorForResourceList", vendorForResourceList);
		}
		catch(Exception e){}
	}
	
	private void isoEquipmentAddEdit(String id, ModelMap model)
	{
		if(id != null && !id.equals(""))
			model.addAttribute("isoEquipmentAddEdit", "Y");
		else
			model.addAttribute("isoEquipmentAddEdit", "N");
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id,
			@RequestParam(required = false) String productDate,
			@RequestParam(required = false) String initializeDate,
			@RequestParam(required = false) String updated,
			@RequestParam(required = false) String maintenanceDate,
			@RequestParam(required = false) String contractDate,
			@RequestParam(required = false) String inputStatus,
			@RequestParam(required = false) String fileId,
			@ModelAttribute("isoInventory") @Valid IsoInventory isoInventory, BindingResult result, 
			ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		
		//Ném lỗi
		if (result.hasErrors()) {
			isoEquipmentAddEdit(id, model);
			if(result.hasFieldErrors("power"))
				model.addAttribute("powerError", "powerError");
			if(result.hasFieldErrors("defaultCurrent"))
				model.addAttribute("defaultCurrentError", "defaultCurrentError");
			if(result.hasFieldErrors("defaultVoltage"))
				model.addAttribute("defaultVoltageError", "defaultVoltageError");
			if(result.hasFieldErrors("realCurrent"))
				model.addAttribute("realCurrentError", "realCurrentError");
			if(result.hasFieldErrors("loadFollow"))
				model.addAttribute("loadFollowError", "loadFollowError");
			if(result.hasFieldErrors("battery"))
				model.addAttribute("batteryError", "batteryError");
			if(result.hasFieldErrors("batteryDuration"))
				model.addAttribute("batteryDurationError", "batteryDurationError");
			if(result.hasFieldErrors("maintenanceInterval"))
				model.addAttribute("maintenanceIntervalError", "maintenanceIntervalError");
			if(result.hasFieldErrors("productDate"))
				model.addAttribute("productDateError", "productDateError");
			if(result.hasFieldErrors("initializeDate"))
				model.addAttribute("initializeDateError", "initializeDateError");
			if(result.hasFieldErrors("updated"))
				model.addAttribute("updatedError", "updatedError");
			if(result.hasFieldErrors("maintenanceDate"))
				model.addAttribute("maintenanceDateError", "maintenanceDateError");
			if(result.hasFieldErrors("contractDate"))
				model.addAttribute("contractDateError", "contractDateError");
			
			model.addAttribute("inputStatus", inputStatus);
			model.addAttribute("productDate", productDate);
			model.addAttribute("initializeDate", initializeDate);
			model.addAttribute("updated", updated);
			model.addAttribute("maintenanceDate", maintenanceDate);
			model.addAttribute("contractDate", contractDate);
			model.addAttribute("neGroupCBB", isoInventory.getNeGroup());
			model.addAttribute("tinhThanhCBB", isoInventory.getProvince());
			model.addAttribute("quanHuyenCBB", isoInventory.getDistrict());
			String temp = "";
			if(isoInventory.getProvince() != null && isoInventory.getProvince() != "" && isoInventory.getProvince().length() > 1)
				temp = isoInventory.getProvince();
			loadCombobox(model, temp);
			model.addAttribute("provinceCBB", temp);
			return "jspiso/isoEquipmentForm";
		}
		
		if(id.equals(""))
		{
			try{
				isoInventory.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				int idResult = isoInventoryDAO.insertAndResult(isoInventory);
				
				if(fileId != "" && fileId != null){
					String[] fileList = fileId.split(",");
					for(int i=0;i<fileList.length;i++){
						SysFiles record = new SysFiles();
						record.setMappingId(idResult);
						if(!fileList[i].equals(""))
							record.setId(Integer.parseInt(fileList[i]));
						sysFilesDAO.updateByPrimaryKeySelective(record);
					}
				}
				
				saveMessageKey(request, "messsage.confirm.addsuccess");
			}
			catch(Exception e){
				isoEquipmentAddEdit(id, model);
				model.addAttribute("inputStatus", inputStatus);
				model.addAttribute("productDate", productDate);
				model.addAttribute("initializeDate", initializeDate);
				model.addAttribute("updated", updated);
				model.addAttribute("maintenanceDate", maintenanceDate);
				model.addAttribute("contractDate", contractDate);
				model.addAttribute("neGroupCBB", isoInventory.getNeGroup());
				model.addAttribute("tinhThanhCBB", isoInventory.getProvince());
				model.addAttribute("quanHuyenCBB", isoInventory.getDistrict());
				String temp = "";
				if(isoInventory.getProvince() != null && isoInventory.getProvince() != "" && isoInventory.getProvince().length() > 1)
					temp = isoInventory.getProvince();
				loadCombobox(model, temp);
				model.addAttribute("provinceCBB", temp);
				saveMessageKey(request, "messsage.confirm.errorIsoInventoryUnique");
				return "jspiso/isoEquipmentForm";
			}
		}
		else{
			try{
				isoInventory.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				isoInventoryDAO.updateByPrimaryKey(isoInventory);
				
				if(fileId != "" && fileId != null){
					String[] fileList = fileId.split(",");
					for(int i=0;i<fileList.length;i++){
						SysFiles record = new SysFiles();
						record.setMappingId(Integer.parseInt(id));
						if(!fileList[i].equals(""))
							record.setId(Integer.parseInt(fileList[i]));
						sysFilesDAO.updateByPrimaryKeySelective(record);
					}
				}
				saveMessageKey(request, "messsage.confirm.updatesuccess");
			}
			catch(Exception e){
				isoEquipmentAddEdit(id, model);
				model.addAttribute("inputStatus", inputStatus);
				model.addAttribute("productDate", productDate);
				model.addAttribute("initializeDate", initializeDate);
				model.addAttribute("updated", updated);
				model.addAttribute("maintenanceDate", maintenanceDate);
				model.addAttribute("contractDate", contractDate);
				model.addAttribute("neGroupCBB", isoInventory.getNeGroup());
				model.addAttribute("tinhThanhCBB", isoInventory.getProvince());
				model.addAttribute("quanHuyenCBB", isoInventory.getDistrict());
				String temp = "";
				if(isoInventory.getProvince() != null && isoInventory.getProvince() != "" && isoInventory.getProvince().length() > 1)
					temp = isoInventory.getProvince();
				loadCombobox(model, temp);
				model.addAttribute("provinceCBB", temp);
				saveMessageKey(request, "messsage.confirm.errorIsoInventoryUnique");
				return "jspiso/isoEquipmentForm";
			}
		}
		
		return "redirect:list.htm?inputStatus=" + inputStatus;
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(@RequestParam(required = false) String inputStatus, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("inputStatus", inputStatus);
		return "jspiso/isoEquipmentUpload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam(required = false) String inputStatus, @RequestParam("file") MultipartFile filePath,  ModelMap model, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		
		if (!filePath.isEmpty()) {

			String[] ten=filePath.getOriginalFilename().split("\\.");
			String fileExtn = ten[ten.length-1];
			
			if (fileExtn.equals("xls")) {
				if (fileExtn.equals("xls")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsFile2(filePath.getInputStream());
					
					importContent(sheetData, inputStatus, model, request);
					
				}
			}
			else {
				 saveMessageKey(request, "cautruc.typeFIle");
			}
		}
		else {
			saveMessageKey(request, "cautruc.emptyFile");
		}
		model.addAttribute("inputStatus", inputStatus);
		return "jspiso/isoEquipmentUpload";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	private String importContent(List sheetData, String inputStatus, ModelMap model,HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<IsoInventory> successList = new ArrayList<IsoInventory>();
		List<IsoInventory> failedList = new ArrayList<IsoInventory>();
		List<IsoInventory> success = new ArrayList<IsoInventory>();
		
		String deptCode;
		String team;
		String subTeam;
		String province;
		String district;
		String neType;
		String neGroup;
		String locationName;
		String location;
		String ne;
		String productCode;
		String seriNo;
		String productDate;
		String neParent;
		String productName;
		String status;
		String vendor;
		String rack;
		String subrack;
		String slot;
		String swVersion;
		String initializeDate;
		String neOld;
		String updated;
		String omIp;
		String nsei;
		String spc;
		String bscidRncid;
		String power;
		String defaultCurrent;
		String defaultVoltage;
		String realCurrent;
		String load;
		String battery;
		String batteryDuration;
		String maintenanceInterval;
		String maintenanceDate;
		String maintenanceResult;
		String maintenanceSupervisor;
		String maintenanceComment;
		String contractNumber;
		String contractDate;
		String description;
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 43) {
        		saveMessageKey(request, "sidebar.admin.isoInventoryUploadErrorStructuresNumber");
        		
        		return "jspiso/isoEquipmentUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		IsoInventory isoInventory;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			isoInventory = new IsoInventory();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=43; j++) {
     					data.add("");
     				}
        			deptCode				= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			team					= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			subTeam					= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			province				= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			district				= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			neType					= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			neGroup					= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			locationName			= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			location				= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			ne						= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			productCode				= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
        			seriNo					= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim();
        			productDate				= data.get(12).toString().trim().equals("")?null:data.get(12).toString().trim();
        			neParent				= data.get(13).toString().trim().equals("")?null:data.get(13).toString().trim();
        			productName				= data.get(14).toString().trim().equals("")?null:data.get(14).toString().trim();
        			status					= data.get(15).toString().trim().equals("")?null:data.get(15).toString().trim();
        			vendor					= data.get(16).toString().trim().equals("")?null:data.get(16).toString().trim();
        			rack					= data.get(17).toString().trim().equals("")?null:data.get(17).toString().trim();
        			subrack					= data.get(18).toString().trim().equals("")?null:data.get(18).toString().trim();
        			slot					= data.get(19).toString().trim().equals("")?null:data.get(19).toString().trim();
        			swVersion				= data.get(20).toString().trim().equals("")?null:data.get(20).toString().trim();
        			initializeDate			= data.get(21).toString().trim().equals("")?null:data.get(21).toString().trim();
        			neOld					= data.get(22).toString().trim().equals("")?null:data.get(22).toString().trim();
        			updated					= data.get(23).toString().trim().equals("")?null:data.get(23).toString().trim();
        			omIp					= data.get(24).toString().trim().equals("")?null:data.get(24).toString().trim();
        			nsei					= data.get(25).toString().trim().equals("")?null:data.get(25).toString().trim();
        			spc						= data.get(26).toString().trim().equals("")?null:data.get(26).toString().trim();
        			bscidRncid				= data.get(27).toString().trim().equals("")?null:data.get(27).toString().trim();
        			power					= data.get(28).toString().trim().equals("")?null:data.get(28).toString().trim();
        			defaultCurrent			= data.get(29).toString().trim().equals("")?null:data.get(29).toString().trim();
        			defaultVoltage			= data.get(30).toString().trim().equals("")?null:data.get(30).toString().trim();
        			realCurrent				= data.get(31).toString().trim().equals("")?null:data.get(31).toString().trim();
        			load					= data.get(32).toString().trim().equals("")?null:data.get(32).toString().trim();
        			battery					= data.get(33).toString().trim().equals("")?null:data.get(33).toString().trim();
        			batteryDuration			= data.get(34).toString().trim().equals("")?null:data.get(34).toString().trim();
        			maintenanceInterval		= data.get(35).toString().trim().equals("")?null:data.get(35).toString().trim();
        			maintenanceDate			= data.get(36).toString().trim().equals("")?null:data.get(36).toString().trim();
        			maintenanceResult		= data.get(37).toString().trim().equals("")?null:data.get(37).toString().trim();
        			maintenanceSupervisor	= data.get(38).toString().trim().equals("")?null:data.get(38).toString().trim();
        			maintenanceComment		= data.get(39).toString().trim().equals("")?null:data.get(39).toString().trim();
        			contractNumber			= data.get(40).toString().trim().equals("")?null:data.get(40).toString().trim();
        			contractDate			= data.get(41).toString().trim().equals("")?null:data.get(41).toString().trim();
        			description				= data.get(42).toString().trim().equals("")?null:data.get(42).toString().trim();
        			
        			// Kiem tra loi
        			if (productCode == null || seriNo == null || ne == null
        					//|| (deptCode != null && deptCode.length() > 50)
        					//|| (team != null && team.length() > 50)
        					//|| (subTeam != null && subTeam.length() > 50)
        					//|| (province != null && province.length() > 50)
        					//|| (district != null && district.length() > 20)
							//|| (neType != null && neType.length() > 20)
							//|| (codeAssetType != null && codeAssetType.length() > 50)
							//|| (locationName != null && locationName.length() > 100)
							//|| (location != null && location.length() > 50)
							//|| (ne != null && ne.length() > 100)
							//|| (productCode != null && productCode.length() > 50)
							//|| (seriNo != null && seriNo.length() > 100)
							//|| (neParent != null && neParent.length() > 30)
							//|| (productName != null && productName.length() > 30)
							//|| (vendor != null && vendor.length() > 20)
							//|| (rack != null && rack.length() > 20)
							//|| (subrack != null && subrack.length() > 20)
							//|| (slot != null && slot.length() > 20)
							//|| (swVersion != null && swVersion.length() > 30)
							//|| (neOld != null && neOld.length() > 30)
							//|| (omIp != null && omIp.length() > 20)
							//|| (nsei != null && nsei.length() > 30)
							//|| (spc != null && spc.length() > 30)
							//|| (bscidRncid != null && bscidRncid.length() > 20)
							//|| (maintenanceResult != null && maintenanceResult.length() > 250)
							//|| (maintenanceSupervisor != null && maintenanceSupervisor.length() > 50)
							//|| (maintenanceComment != null && maintenanceComment.length() > 250)
							//|| (contractNumber != null && contractNumber.length() > 30)
							//|| (description != null && description.length() > 250)
							|| (status != null && !status.toString().toLowerCase().equals("đang sử dụng") && !status.toString().toLowerCase().equals("không sử dụng")&& !status.toString().toLowerCase().equals("trong kho"))
						) {
						error = true;
					}
        			
        			try{
	        			if(power != null){
		        			Integer a = Integer.parseInt(power);
		        			isoInventory.setPower(a);
	        			}
	        			if(defaultCurrent != null){
		        			Integer a = Integer.parseInt(defaultCurrent);
		        			isoInventory.setDefaultCurrent(a);
	        			}
	        			if(defaultVoltage != null){
		        			Integer a = Integer.parseInt(defaultVoltage);
		        			isoInventory.setDefaultVoltage(a);
	        			}
	        			if(realCurrent != null){
		        			Integer a = Integer.parseInt(realCurrent);
		        			isoInventory.setRealCurrent(a);
	        			}
	        			if(load != null){
		        			Integer a = Integer.parseInt(load);
		        			isoInventory.setLoadFollow(a);
	        			}
	        			if(battery != null){
		        			Integer a = Integer.parseInt(battery);
		        			isoInventory.setBattery(a);
	        			}
	        			if(batteryDuration != null){
		        			Integer a = Integer.parseInt(batteryDuration);
		        			isoInventory.setBatteryDuration(a);
	        			}
	        			if(maintenanceInterval != null){
		        			Integer a = Integer.parseInt(maintenanceInterval);
		        			isoInventory.setMaintenanceInterval(a);
	        			}
	        			
	        		}
	        		catch(Exception e){
	        			error = true;
	        		}
        			
        			try
	    			{
        				if(productDate != null){
		    				Date simple = new SimpleDateFormat("dd/MM/yyyy").parse(productDate);
		    				isoInventory.setProductDate(new SimpleDateFormat("dd/MM/yyyy").parse(productDate));
	    				}
        				if(initializeDate != null){
		    				Date simple = new SimpleDateFormat("dd/MM/yyyy").parse(initializeDate);
		    				isoInventory.setInitializeDate(new SimpleDateFormat("dd/MM/yyyy").parse(initializeDate));
	    				}
        				if(updated != null){
		    				Date simple = new SimpleDateFormat("dd/MM/yyyy").parse(updated);
		    				isoInventory.setUpdated(new SimpleDateFormat("dd/MM/yyyy").parse(updated));
	    				}
        				if(maintenanceDate != null){
		    				Date simple = new SimpleDateFormat("dd/MM/yyyy").parse(maintenanceDate);
		    				isoInventory.setMaintenanceDate(new SimpleDateFormat("dd/MM/yyyy").parse(maintenanceDate));
	    				}
        				if(contractDate != null){
		    				Date simple = new SimpleDateFormat("dd/MM/yyyy").parse(contractDate);
		    				isoInventory.setContractDate(new SimpleDateFormat("dd/MM/yyyy").parse(contractDate));
	    				}
	    			}
	    			catch(Exception e)
	    			{
	    				error = true;
	    			}
        			
        			//---------------------------------------------------------------------------
        			isoInventory.setInputStatus(inputStatus);
        			isoInventory.setDeptCode(deptCode);
        			isoInventory.setTeam(team);
        			isoInventory.setSubTeam(subTeam);
        			isoInventory.setProvince(province);
        			isoInventory.setDistrict(district);
        			isoInventory.setNeType(neType);
        			isoInventory.setLocationName(locationName);
        			isoInventory.setLocation(location);
        			isoInventory.setNe(ne);
        			isoInventory.setProductCode(productCode);
        			isoInventory.setSeriNo(seriNo);
        			isoInventory.setNeParent(neParent);
        			isoInventory.setProductName(productName);
        			isoInventory.setNeGroup(neGroup);
        			if(status != null){
        				if(status.toString().toLowerCase().equals("đang sử dụng"))
        					isoInventory.setStatus("Y");
        				else if(status.toString().toLowerCase().equals("không sử dụng"))
        					isoInventory.setStatus("N");
        				else if(status.toString().toLowerCase().equals("trong kho"))
        					isoInventory.setStatus("T");
        			}
        			isoInventory.setStatusName(status);
        			if(vendor != null)
        				vendor = vendor.toString().toUpperCase();
        			isoInventory.setVendor(vendor);
        			isoInventory.setRack(rack);
        			isoInventory.setSubrack(subrack);
        			isoInventory.setSlot(slot);
        			isoInventory.setSwVersion(swVersion);
        			isoInventory.setNeOld(neOld);
        			isoInventory.setOmIp(omIp);
        			isoInventory.setNsei(nsei);
        			isoInventory.setSpc(spc);
        			isoInventory.setBscidRncid(bscidRncid);
        			isoInventory.setMaintenanceResult(maintenanceResult);
        			isoInventory.setMaintenanceSupervisor(maintenanceSupervisor);
        			isoInventory.setMaintenanceComment(maintenanceComment);
        			isoInventory.setContractNumber(contractNumber);
        			isoInventory.setDescription(description);
        			
        			if ( productCode == null && seriNo == null && ne == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
        					
    						failedList.add(isoInventory);
    					} else  {
    						
    						successList.add(isoInventory);
    					}
        			}
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (IsoInventory record: successList) {
			try {
				if(isoInventoryDAO.getUpdateIsoInventory(record.getNe(), record.getProductCode(), record.getSeriNo()).size() > 0)
				{
					isoInventoryDAO.updateByMutiKey(record);
				}else{
					record.setCreatedBy(username);
					isoInventoryDAO.insertSelective(record);
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
			model.addAttribute("status", "Dữ liệu quản lý không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");	// Upload lỗi
		}
		
		// Them du lieu combobox cho iso inventory
		try{
			isoInventoryDAO.insertInfoForIsoInventory();
		}
		catch(Exception e){}
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", success.size());
		model.addAttribute("totalNum", failedList.size()+success.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		return "jspiso/isoEquipmentUpload";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) String idList,
			@RequestParam(required = false) String inputStatus, HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
			String[] idArray = idList.split(",");
			for (String id : idArray) {
				isoInventoryDAO.deleteByPrimaryKey(Integer.parseInt(id));
				sysFilesDAO.deleteSysFilesByMappingId(Integer.parseInt(id));
			}
			
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch(Exception e)
		{
			saveMessageKey(request, "message.confirm.deleteOther");
			}
		
		return "redirect:list.htm?inputStatus=" + inputStatus;
	}
	
	// Lay danh sach loai thiet bi
 	@RequestMapping("dsLoaiThietBi")
 	public @ResponseBody 
 	List<SYS_PARAMETER> getNeType() {
 		List<SYS_PARAMETER> neTypeList = new ArrayList<SYS_PARAMETER>();
 		neTypeList = sysParameterDao.getEquipmentType();
 		
 		return neTypeList;
 	 }
 	
 	// Lay danh sach don vi quan ly
  	@RequestMapping("dsDonViQuanLy")
  	public @ResponseBody 
  	List<MDepartment> dsDonViQuanLy() {
  		List<MDepartment> mDepartmentList = mDepartmentDAO.getDepartmentByEquipment();
  		
  		return mDepartmentList;
  	 }
  	
  	// Lay danh sach dia diem
   	@RequestMapping("dsDiaDiem")
   	public @ResponseBody 
   	List<HProvincesCode> dsDiaDiem() {
   		List<HProvincesCode> hProvinceCodeList = hProvincesCodeDAO.getHProvinceCodeByEquiment();
   		
   		return hProvinceCodeList;
   	 }
   	
   	// Lay danh sach trang thai
   	@RequestMapping("dsTrangThai")
   	public @ResponseBody 
   	List<SYS_PARAMETER> dsTrangThai() {
   		List<SYS_PARAMETER> dsTrangThai = sysParameterDao.getStatusEquipmentType();
   		
   		return dsTrangThai;
   	 }
   	
   	// Lay danh sach team
   	@RequestMapping("teamList")
   	public @ResponseBody 
   	List<IsoInventory> teamList() {
   		List<IsoInventory> teamList = isoInventoryDAO.getTeamList();
   		
   		return teamList;
   	 }
   	
   	// Lay danh sach sub team
   	@RequestMapping("subTeamList")
   	public @ResponseBody 
   	List<IsoInventory> subTeamList() {
   		List<IsoInventory> subTeamList = isoInventoryDAO.getSubTeamList();
   		
   		return subTeamList;
   	 }
   	
   	// Lay danh sach nhom thiet bi
   	@RequestMapping("neGroup")
   	public @ResponseBody 
   	List<IsoAssetType> neGroup() {
   		List<IsoAssetType> codeAssetTypeList = isoAssetTypeDAO.getIsoAssetTypeList();
   		
   		return codeAssetTypeList;
   	 }
   	
   	// Lay danh sach vendor
   	@RequestMapping("vendorList")
   	public @ResponseBody 
   	List<SYS_PARAMETER> vendorList() {
   		List<SYS_PARAMETER> vendorForResourceList = sysParameterDao.getVendorForResourceList();
   		
   		return vendorForResourceList;
   	 }
   	
   	// Lay danh sach district theo province
   	@RequestMapping("loadQuanHuyen")
   	public @ResponseBody 
   	List<HProvincesCode> loadQuanHuyen(@RequestParam(required = false) String province, HttpServletRequest request) {
   		List<HProvincesCode> quanhuyenList = hProvincesCodeDAO.getHProvincesCodeList(province);
   		return quanhuyenList;
   	 }
   	
   	
  	@RequestMapping("exportResource")
  	public ModelAndView reportAlarmLog(
  			@RequestParam(required = false) String deptCode,
			   @RequestParam(required = false) String team,
			   @RequestParam(required = false) String subTeam,
			   @RequestParam(required = false) String province, 
			   @RequestParam(required = false) String district,
			   @RequestParam(required = false) String neType,
			   @RequestParam(required = false) String ne,
			   @RequestParam(required = false) String neGroup,
			   @RequestParam(required = false) String locationName,
			   @RequestParam(required = false) String vendor,
			   @RequestParam(required = false) String productCode,
			   @RequestParam(required = false) String productName,
			   @RequestParam(required = false) String seriNo,
			   @RequestParam(required = false) String inputStatus,
			   @RequestParam(required = false) String status,
			   @RequestParam(required = false) String sortfield,
			   @RequestParam(required = false) String sortorder,
			   @RequestParam(required = false) String strWhere,
  			HttpServletRequest request, HttpServletResponse response) throws Exception {
  		strWhere = strWhere.replace("___", "%");
  		if(locationName != null)
  			locationName = locationName.trim();
  		if(productCode != null)
  			productCode = productCode.trim();
  		if(productName != null)
  			productName = productName.trim();
  		if(seriNo != null)
  			seriNo = seriNo.trim();
  		
  		// temp file
		String basePath = request.getSession().getServletContext().getRealPath("");
		String dataDir = basePath + "/upload";
		String tempName = UUID.randomUUID().toString();
		String ext = "xls";
		String outfile = tempName + "." + ext;
		File tempFile = new File(dataDir + "/" + outfile);
		
		List<IsoInventory> isoEquipmentList = isoInventoryDAO.getIsoEquipmentFilter(deptCode, team, subTeam, province, district, neType, ne, neGroup, locationName, vendor, productCode, productName, seriNo, inputStatus, status, "", null, null, sortfield, sortorder, strWhere);
		if(inputStatus.equals("DEVICE"))
			exportReportEquipment(tempFile, isoEquipmentList);
		else
			exportReportResource(tempFile, isoEquipmentList);
		
		// return
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String filename = "Resource_"+ dateNow;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();
  		return null;
  	}
  	
  	private void exportReportResource(File tempFile, List<IsoInventory> isoEquipmentList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("Resource Report", 0);
			int i=0;
			
			Label label0 = new Label(i, 0, "NE Parent");
			sheet.addCell(label0);
			i++;
			Label label1 = new Label(i, 0, "NE Name");
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "Product Name");
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "Product Code");
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "Seri No");
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "Rack");
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "Subrack");
			sheet.addCell(label6);
			i++;
			Label label7 = new Label(i, 0, "Slot");
			sheet.addCell(label7);
			i++;
			Label label8 = new Label(i, 0, "Product Date");
			sheet.addCell(label8);
			i++;
			Label label9 = new Label(i, 0, "Software vs");
			sheet.addCell(label9);
			i++;
			Label label10 = new Label(i, 0, "Detection Date");
			sheet.addCell(label10);
			i++;
			Label label11 = new Label(i, 0, "Old NE Name");
			sheet.addCell(label11);
			i++;
			Label label12 = new Label(i	, 0, "Last Detection Date");
			sheet.addCell(label12);
			i++;
			Label label13 = new Label(i, 0, "Description");
			sheet.addCell(label13);
			i++;
			Label label14 = new Label(i, 0, "NE Type");
			sheet.addCell(label14);
			i++;
			Label label15 = new Label(i, 0, "Vendor");
			sheet.addCell(label15);
			i++;
			Label label16 = new Label(i, 0, "Status");
			sheet.addCell(label16);
			i++;
			Label label17 = new Label(i, 0, "District");
			sheet.addCell(label17);
			i++;
			Label label18 = new Label(i, 0, "Province");
			sheet.addCell(label18);
			i++;
			Label label19 = new Label(i, 0, "Department");
			sheet.addCell(label19);
			
			i = 1;
			
			for (IsoInventory record : isoEquipmentList) {
				int j=0;
				Label NE_PARENT = new Label(j, i, record.getNeParent());
				sheet.addCell(NE_PARENT);
				j++;
				Label NE = new Label(j, i,record.getNe());
				sheet.addCell(NE);
				j++;
				Label PRODUCT_NAME = new Label(j, i, record.getProductName());
				sheet.addCell(PRODUCT_NAME);
				j++;
				Label PRODUCT_CODE = new Label(j, i, record.getProductCode());
				sheet.addCell(PRODUCT_CODE);
				j++;
				Label SERI_NO = new Label(j, i, record.getSeriNo());
				sheet.addCell(SERI_NO);
				j++;
				Label RACK = new Label(j, i, record.getRack());
				sheet.addCell(RACK);
				j++;
				Label SUBRACK = new Label(j, i, record.getSubrack());
				sheet.addCell(SUBRACK);
				j++;
				Label SLOT = new Label(j, i, record.getSlot());
				sheet.addCell(SLOT);
				j++;
				Label PRODUCT_DATE = new Label(j, i, record.getProductDateStr());
				sheet.addCell(PRODUCT_DATE);
				j++;
				Label SW_VERSION = new Label(j, i, record.getSwVersion());
				sheet.addCell(SW_VERSION);
				j++;
				Label INITIALIZE_DATE = new Label(j, i, record.getInitializeDateStr());
				sheet.addCell(INITIALIZE_DATE);
				j++;
				Label NE_OLD = new Label(j, i, record.getNeOld());
				sheet.addCell(NE_OLD);
				j++;
				Label UPDATED = new Label(j, i, record.getUpdatedStr());
				sheet.addCell(UPDATED);
				j++;
				Label DESCRIPTION = new Label(j, i, record.getDescription());
				sheet.addCell(DESCRIPTION);
				j++;
				Label NE_TYPE = new Label(j, i, record.getNeType());
				sheet.addCell(NE_TYPE);
				j++;
				Label VENDOR = new Label(j, i, record.getVendor());
				sheet.addCell(VENDOR);
				j++;
				Label STATUS = new Label(j, i, record.getStatusName());
				sheet.addCell(STATUS);
				j++;
				Label DISTRICT = new Label(j, i, record.getDistrict());
				sheet.addCell(DISTRICT);
				j++;
				Label PROVINCE = new Label(j, i, record.getProvince());
				sheet.addCell(PROVINCE);
				j++;
				Label DEPT_CODE = new Label(j, i, record.getDeptCode());
				sheet.addCell(DEPT_CODE);
				
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
  	
  	private void exportReportEquipment(File tempFile, List<IsoInventory> isoEquipmentList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("Equipment Report", 0);
			int i=0;
			
			Label label0 = new Label(i, 0, "Department");
			sheet.addCell(label0);
			i++;
			Label label1 = new Label(i, 0, "Team");
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "Sub Team");
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "Province");
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "District");
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "Ne Type");
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "NE Group");
			sheet.addCell(label6);
			i++;
			Label label7 = new Label(i, 0, "Location Name");
			sheet.addCell(label7);
			i++;
			Label label8 = new Label(i, 0, "Location");
			sheet.addCell(label8);
			i++;
			Label label9 = new Label(i, 0, "NE Name");
			sheet.addCell(label9);
			i++;
			Label label10 = new Label(i, 0, "Product Code");
			sheet.addCell(label10);
			i++;
			Label label11 = new Label(i, 0, "Seri No");
			sheet.addCell(label11);
			i++;
			Label label12 = new Label(i	, 0, "Product Date");
			sheet.addCell(label12);
			i++;
			Label label13 = new Label(i, 0, "NE Parent");
			sheet.addCell(label13);
			i++;
			Label label14 = new Label(i, 0, "Product Name");
			sheet.addCell(label14);
			i++;
			Label label15 = new Label(i, 0, "Status");
			sheet.addCell(label15);
			i++;
			Label label16 = new Label(i, 0, "Vendor");
			sheet.addCell(label16);
			i++;
			Label label17 = new Label(i, 0, "Rack");
			sheet.addCell(label17);
			i++;
			Label label18 = new Label(i, 0, "Subrack");
			sheet.addCell(label18);
			i++;
			Label label19 = new Label(i, 0, "Slot");
			sheet.addCell(label19);
			i++;
			Label label20 = new Label(i, 0, "Software Version");
			sheet.addCell(label20);
			i++;
			Label label21 = new Label(i, 0, "Detection Date");
			sheet.addCell(label21);
			i++;
			Label label22 = new Label(i, 0, "Last Equipment Name");
			sheet.addCell(label22);
			i++;
			Label label23 = new Label(i, 0, "Last Equipment Date");
			sheet.addCell(label23);
			i++;
			Label label24 = new Label(i, 0, "OM IP");
			sheet.addCell(label24);
			i++;
			Label label25 = new Label(i, 0, "NSEI");
			sheet.addCell(label25);
			i++;
			Label label26 = new Label(i, 0, "SPC");
			sheet.addCell(label26);
			i++;
			Label label27 = new Label(i, 0, "BSCID/RNCID");
			sheet.addCell(label27);
			i++;
			Label label28 = new Label(i, 0, "Power");
			sheet.addCell(label28);
			i++;
			Label label29 = new Label(i, 0, "Default Current (A)");
			sheet.addCell(label29);
			i++;
			Label label30 = new Label(i, 0, "Default Voltage (V)");
			sheet.addCell(label30);
			i++;
			Label label31 = new Label(i, 0, "Real Current (A/Ah)");
			sheet.addCell(label31);
			i++;
			Label label32 = new Label(i, 0, "Load");
			sheet.addCell(label32);
			i++;
			Label label33 = new Label(i, 0, "Battery");
			sheet.addCell(label33);
			i++;
			Label label34 = new Label(i, 0, "Battery Duration");
			sheet.addCell(label34);
			i++;
			Label label35 = new Label(i, 0, "Maintenance Interval (month)");
			sheet.addCell(label35);
			i++;
			Label label36 = new Label(i, 0, "Maintenance Date");
			sheet.addCell(label36);
			i++;
			Label label37 = new Label(i, 0, "Maintenance Result");
			sheet.addCell(label37);
			i++;
			Label label38 = new Label(i, 0, "Maintenance Supervisor");
			sheet.addCell(label38);
			i++;
			Label label39 = new Label(i, 0, "Maintenance Comment");
			sheet.addCell(label39);
			i++;
			Label label40 = new Label(i, 0, "Contract Number");
			sheet.addCell(label40);
			i++;
			Label label41 = new Label(i, 0, "Contract Date");
			sheet.addCell(label41);
			i++;
			Label label42 = new Label(i, 0, "Description");
			sheet.addCell(label42);
			
			i = 1;
			
			for (IsoInventory record : isoEquipmentList) {
				int j=0;
				Label record0 = new Label(j, i, record.getDeptCode());
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,record.getTeam());
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getSubTeam());
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getProvince());
				sheet.addCell(record3);
				j++;
				Label record4 = new Label(j, i, record.getDistrict());
				sheet.addCell(record4);
				j++;
				Label record5 = new Label(j, i, record.getNeType());
				sheet.addCell(record5);
				j++;
				Label record6 = new Label(j, i, record.getNeGroup());
				sheet.addCell(record6);
				j++;
				Label record7 = new Label(j, i, record.getLocationName());
				sheet.addCell(record7);
				j++;
				Label record8 = new Label(j, i, record.getLocation());
				sheet.addCell(record8);
				j++;
				Label record9 = new Label(j, i, record.getNe());
				sheet.addCell(record9);
				j++;
				Label record10 = new Label(j, i, record.getProductCode());
				sheet.addCell(record10);
				j++;
				Label record11 = new Label(j, i, record.getSeriNo());
				sheet.addCell(record11);
				j++;
				Label record12 = new Label(j, i, record.getProductDateStr());
				sheet.addCell(record12);
				j++;
				Label record13 = new Label(j, i, record.getNeParent());
				sheet.addCell(record13);
				j++;
				Label record14 = new Label(j, i, record.getProductName());
				sheet.addCell(record14);
				j++;
				Label record15 = new Label(j, i, record.getStatus());
				sheet.addCell(record15);
				j++;
				Label record16 = new Label(j, i, record.getVendor());
				sheet.addCell(record16);
				j++;
				Label record17 = new Label(j, i, record.getRack());
				sheet.addCell(record17);
				j++;
				Label record18 = new Label(j, i, record.getSubrack());
				sheet.addCell(record18);
				j++;
				Label record19 = new Label(j, i, record.getSlot());
				sheet.addCell(record19);
				j++;
				Label record20 = new Label(j, i, record.getSwVersion());
				sheet.addCell(record20);
				j++;
				Label record21 = new Label(j, i, record.getInitializeDateStr());
				sheet.addCell(record21);
				j++;
				Label record22 = new Label(j, i, record.getNeOld());
				sheet.addCell(record22);
				j++;
				Label record23 = new Label(j, i, record.getUpdatedStr());
				sheet.addCell(record23);
				j++;
				Label record24 = new Label(j, i, record.getOmIp());
				sheet.addCell(record24);
				j++;
				Label record25 = new Label(j, i, record.getNsei());
				sheet.addCell(record25);
				j++;
				Label record26 = new Label(j, i, record.getSpc());
				sheet.addCell(record26);
				j++;
				Label record27 = new Label(j, i, record.getBscidRncid());
				sheet.addCell(record27);
				j++;
				Label record28 = new Label(j, i, record.getPowerStr());
				sheet.addCell(record28);
				j++;
				Label record29 = new Label(j, i, record.getDefaultCurrentStr());
				sheet.addCell(record29);
				j++;
				Label record30 = new Label(j, i, record.getDefaultVoltageStr());
				sheet.addCell(record30);
				j++;
				Label record31 = new Label(j, i, record.getRealCurrentStr());
				sheet.addCell(record31);
				j++;
				Label record32 = new Label(j, i, record.getLoadFollowStr());
				sheet.addCell(record32);
				j++;
				Label record33 = new Label(j, i, record.getBatteryStr());
				sheet.addCell(record33);
				j++;
				Label record34 = new Label(j, i, record.getBatteryDurationStr());
				sheet.addCell(record34);
				j++;
				Label record35 = new Label(j, i, record.getMaintenanceIntervalStr());
				sheet.addCell(record35);
				j++;
				Label record36 = new Label(j, i, record.getMaintenanceDateStr());
				sheet.addCell(record36);
				j++;
				Label record37 = new Label(j, i, record.getMaintenanceResult());
				sheet.addCell(record37);
				j++;
				Label record38 = new Label(j, i, record.getMaintenanceSupervisor());
				sheet.addCell(record38);
				j++;
				Label record39 = new Label(j, i, record.getMaintenanceComment());
				sheet.addCell(record39);
				j++;
				Label record40 = new Label(j, i, record.getContractNumber());
				sheet.addCell(record40);
				j++;
				Label record41 = new Label(j, i, record.getContractDateStr());
				sheet.addCell(record41);
				j++;
				Label record42 = new Label(j, i, record.getDescription());
				sheet.addCell(record42);
				
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
