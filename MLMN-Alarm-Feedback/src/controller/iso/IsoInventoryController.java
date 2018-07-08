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

import vo.CHighlightConfigs;
import vo.CTableConfig;
import vo.HProvincesCode;
import vo.IsoAssetType;
import vo.IsoInventory;
import vo.IsoInventoryTemp;
import vo.MDepartment;
import vo.SYS_PARAMETER;
import vo.SysFiles;
import vo.SysUsers;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;
import vo.alarm.utils.UploadTools;
import vo.dictionary.TableConfigsHelper;

import com.google.gson.Gson;

import controller.BaseController;

import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.HProvincesCodeDAO;
import dao.IsoAssetTypeDAO;
import dao.IsoInventoryDAO;
import dao.IsoInventoryTempDAO;
import dao.MDepartmentDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysFilesDAO;
import dao.SysUsersDAO;
/**
 * Function        : Quan ly tai nguyen mang luoi
 * Created By      : BUIQUANG
 * Create Date     : 11/12/2013
 * Modified By     : THANGPX
 * Modify Date     : 05/09/2014
 * @author BUIQUANG
 * Description     : Them tim kiem theo location
 */
@Controller
@RequestMapping("/iso/iso-inventory/*")
public class IsoInventoryController extends BaseController{

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
	private IsoInventoryTempDAO isoInventoryTempDAO;
	@Autowired
	private SysFilesDAO sysFilesDAO;
	@Autowired
	private SysUsersDAO sysUsersDao;
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
			   
			   @RequestParam(required = false) String location,
			   @RequestParam(required = false) String status, ModelMap model, HttpServletRequest request, HttpServletResponse response)
	{
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "ThietBi_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		//xoa danh sach du lieu cua user o bang iso_inventory_temp
		try{
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			isoInventoryDAO.deleteDataIsoInventoryTemp(username);
		}
		catch(Exception e){}
		
		boolean rolesManagerDeptCode = false;
		boolean rolesManagerTeam = false;
		boolean rolesManagerSubTeam = false;
		SysUsers user = sysUsersDao.selectSysUsersByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		if(user.getIsRoleManager() != null && user.getIsRoleManager().equals("Y")){
			
		}
		else{
			if(user.getSubTeam() != null && !user.getSubTeam().equals("")){
				subTeam = user.getSubTeam();
				rolesManagerSubTeam = true;
			}
			if(user.getTeam() != null && !user.getTeam().equals("")){
				team = user.getTeam();
				rolesManagerTeam = true;
			}
			if(user.getMaPhong() != null && !user.getMaPhong().equals("")){
				deptCode = user.getMaPhong();
				rolesManagerDeptCode = true;
			}
		}
		model.addAttribute("rolesManagerDeptCode", rolesManagerDeptCode);
		model.addAttribute("rolesManagerTeam", rolesManagerTeam);
		model.addAttribute("rolesManagerSubTeam", rolesManagerSubTeam);
		
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
		if(location != null){
			filterUrl += temp + "location=" + location.trim();
			temp = "&";
		}
		if(status != null){
			filterUrl += temp + "status=" + status.trim();
		}
		if(filterUrl != "")
			filterUrl = "?" + filterUrl;
		
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("ISO_INVENTORY");
		//Lay du lieu cac cot an hien cua grid
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList("ISO_INVENTORY");
		String url = "data.htm" + filterUrl;
		//Grid
		String gridManage = TableConfigsHelper.getGridAddAndPaging(configList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", "Yes", null, "multiplerowsextended", null);
		model.addAttribute("gridManage", gridManage);
		
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
		model.addAttribute("location", location);
		return new ModelAndView("jspiso/isoInventoryList");
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
				@RequestParam(required = false) String location,
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
		List<CTableConfig> columnList = cTableConfigDAO.getTableConfigGet("ISO_INVENTORY", sortfield);
		List<CTableConfig> tableConfigList = cTableConfigDAO.getTableConfigGet("ISO_INVENTORY", null);
		
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
					inputStatus, status, location, startRecord, endRecord, sortfield, sortorder, strWhere);
			totalRow = isoInventoryDAO.countIsoEquipmentFilter(deptCode, team, subTeam, province, district, neType, ne, neGroup, locationName, vendor, productCode, productName, seriNo, inputStatus, status, location, strWhere);
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
			model.addAttribute("vendorCBB", isoInventory.getVendor());
		}
		else{
			// Ngay thang
			if (initializeDate == null || initializeDate.equals("") || DateTools.isValid("dd/MM/yyyy", initializeDate)==false) {
				initializeDate = df.format(new Date());
			}
			model.addAttribute("initializeDate", initializeDate);
		}
		return "jspiso/isoInventoryForm";
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
			return "jspiso/isoInventoryForm";
		}
		
		if(id.equals(""))
		{
			try{
				if(isoInventoryDAO.getUpdateIsoInventory(isoInventory.getProductCode(), isoInventory.getSeriNo(), null).size() == 0){
					isoInventory.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
					isoInventory.setInputStatus("DEVICE");
					int idResult = isoInventoryDAO.insertAndResult(isoInventory);
					
					if(fileId != "" && fileId != null){
						String[] fileList = fileId.split(",");
						for(int i=0;i<fileList.length;i++){
							SysFiles record = new SysFiles();
							record.setMappingId(idResult);
							record.setFileType("ISO");
							if(!fileList[i].equals(""))
								record.setId(Integer.parseInt(fileList[i]));
							sysFilesDAO.updateByPrimaryKeySelective(record);
						}
					}
					
					saveMessageKey(request, "messsage.confirm.addsuccess");
				}
				else{
					isoEquipmentAddEdit(id, model);
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
					saveMessageKey(request, "messsage.confirm.errorIsoInventoryUniqueProductCodeSeri");
					return "jspiso/isoEquipmentForm";
				}
			}
			catch(Exception e){
				isoEquipmentAddEdit(id, model);
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
				if(isoInventoryDAO.getUpdateIsoInventory(isoInventory.getProductCode(), isoInventory.getSeriNo(), id).size() == 0){
					isoInventory.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
					isoInventoryDAO.updateByPrimaryKey(isoInventory);
					
					if(fileId != "" && fileId != null){
						String[] fileList = fileId.split(",");
						for(int i=0;i<fileList.length;i++){
							SysFiles record = new SysFiles();
							record.setMappingId(Integer.parseInt(id));
							record.setFileType("ISO");
							if(!fileList[i].equals(""))
								record.setId(Integer.parseInt(fileList[i]));
							sysFilesDAO.updateByPrimaryKeySelective(record);
						}
					}
					saveMessageKey(request, "messsage.confirm.updatesuccess");
				}
				else{
					isoEquipmentAddEdit(id, model);
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
					saveMessageKey(request, "messsage.confirm.errorIsoInventoryUniqueProductCodeSeri");
					return "jspiso/isoEquipmentForm";
				}
			}
			catch(Exception e){
				isoEquipmentAddEdit(id, model);
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
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm( ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileNameInsert = "ListInsert_" + dateNow;
		String exportFileNameUpdate = "ListUpdate_" + dateNow;
		model.addAttribute("exportFileNameInsert", exportFileNameInsert);
		model.addAttribute("exportFileNameUpdate", exportFileNameUpdate);
				
		List<IsoInventory> insertList = isoInventoryDAO.getIsoInventoryTemp("INSERT", username);
		model.addAttribute("insertList", insertList);
		List<IsoInventory> updateList = isoInventoryDAO.getIsoInventoryTemp("UPDATE", username);
		model.addAttribute("updateList", updateList);
		return "jspiso/isoInventoryUpload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload( @RequestParam("file") MultipartFile filePath,
			@RequestParam(required = false) String action,
			@RequestParam(required = false) String totalNum, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if(action.equals("import")) {
			if (!filePath.isEmpty()) {
	
				String[] ten=filePath.getOriginalFilename().split("\\.");
				String fileExtn = ten[ten.length-1];
				
				if (fileExtn.equals("xls")) {
					if (fileExtn.equals("xls")) {
						@SuppressWarnings("rawtypes")
						List sheetData = UploadTools.readXlsFile2(filePath.getInputStream());
						//xoa danh sach du lieu cua user o bang iso_inventory_temp
						try{
							isoInventoryDAO.deleteDataIsoInventoryTemp(username);
						}
						catch(Exception e){}
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
		}
		else if(action.equals("update")){
			try{
				
				isoInventoryDAO.getDataForIsoInventory("UPDATE", username);
				
				List<IsoInventory> insertList = isoInventoryDAO.getIsoInventoryTemp("INSERT", username);
				model.addAttribute("insertList", insertList);
				List<IsoInventory> updateList = isoInventoryDAO.getIsoInventoryTemp("UPDATE", username);
				model.addAttribute("updateList", updateList);
				model.addAttribute("totalNum", totalNum);
				model.addAttribute("insertNum", insertList.size());
				model.addAttribute("updateNum", updateList.size());
				saveMessageKey(request, "messsage.confirm.updatesuccess");
			}
			catch(Exception e){
				
				saveMessage(request, "Sửa thất bại! Dữ liệu có nhiều bản ghi trùng product code và seri no");
			}
		}
		else if(action.equals("insert")){
			try{
				
				isoInventoryDAO.getDataForIsoInventory("INSERT", username);	
				
				List<IsoInventory> insertList = isoInventoryDAO.getIsoInventoryTemp("INSERT", username);
				model.addAttribute("insertList", insertList);
				List<IsoInventory> updateList = isoInventoryDAO.getIsoInventoryTemp("UPDATE", username);
				model.addAttribute("updateList", updateList);
				model.addAttribute("totalNum", totalNum);
				model.addAttribute("insertNum", insertList.size());
				model.addAttribute("updateNum", updateList.size());
				saveMessageKey(request, "messsage.confirm.addsuccess");
			}
			catch(Exception e){}
		}
		else if(action.equals("move")){
			try{
				isoInventoryDAO.getDataForIsoInventory("MOVE", username);
				
				List<IsoInventory> insertList = isoInventoryDAO.getIsoInventoryTemp("INSERT", username);
				model.addAttribute("insertList", insertList);
				List<IsoInventory> updateList = isoInventoryDAO.getIsoInventoryTemp("UPDATE", username);
				model.addAttribute("updateList", updateList);
				model.addAttribute("totalNum", totalNum);
				model.addAttribute("insertNum", insertList.size());
				model.addAttribute("updateNum", updateList.size());
				saveMessageKey(request, "message.confirm.isoInventoryMove");
			}
			catch(Exception e){
				saveMessage(request, "Di chuyển thất bại! Dữ liệu có nhiều bản ghi trùng product code và seri no");
			}
		}
		
		return "jspiso/isoInventoryUpload";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	private String importContent(List sheetData, ModelMap model,HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<IsoInventoryTemp> successList = new ArrayList<IsoInventoryTemp>();
		List<IsoInventoryTemp> failedList = new ArrayList<IsoInventoryTemp>();
		List<IsoInventoryTemp> success = new ArrayList<IsoInventoryTemp>();
		
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
        		
        		return "jspiso/isoInventoryUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		IsoInventoryTemp isoInventory;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			isoInventory = new IsoInventoryTemp();
        			
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
        			if (productCode == null || seriNo == null
							|| (status != null && !status.toString().toLowerCase().equals("đang sử dụng") && !status.toString().toLowerCase().equals("không sử dụng")
							&& !status.toString().toLowerCase().equals("bảo hành")&& !status.toString().toLowerCase().equals("trong kho"))
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
        			isoInventory.setInputStatus("DEVICE");
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
        				else if(status.toString().toLowerCase().equals("bảo hành"))
        					isoInventory.setStatus("B");
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
        			isoInventory.setDescription(status);
        			
        			if ( productCode == null && seriNo == null) {
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
		for (IsoInventoryTemp record: successList) {
			try {	
				List<IsoInventory> isoInvenList = isoInventoryDAO.getUpdateIsoInventory(record.getProductCode(), record.getSeriNo(), null);
				if(isoInvenList.size() > 0)
				{
					record.setModifiedBy(username);
					record.setModifyDate(new Date()); 
					record.setStatusName(isoInvenList.get(0).getStatusName()); 
				    record.setDescription(isoInvenList.get(0).getStatusName());
					record.setInputStatusName(isoInvenList.get(0).getInputStatusName());
					record.setInputStatus(isoInvenList.get(0).getInputStatus());
					record.setCodeAssetType("UPDATE");
					isoInventoryTempDAO.insertSelective(record);
				}else{
					record.setCreatedBy(username);
					record.setCodeAssetType("INSERT");
					
					isoInventoryTempDAO.insertSelective(record);
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
			model.addAttribute("status", "Dữ liệu quản lý không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác hoặc dữ liệu trong file bị trùng lặp");	// Upload lỗi
		}
		
		// Them du lieu combobox cho iso inventory
		try{
			isoInventoryDAO.insertInfoForIsoInventory();
		}
		catch(Exception e){}
		
		model.addAttribute("failNum", failedList.size());
		//model.addAttribute("successNum", success.size());
		model.addAttribute("totalNum", failedList.size() + success.size());
		//model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		List<IsoInventory> insertList = isoInventoryDAO.getIsoInventoryTemp("INSERT", username);
		model.addAttribute("insertNum", insertList.size());
		model.addAttribute("insertList", insertList);
		List<IsoInventory> updateList = isoInventoryDAO.getIsoInventoryTemp("UPDATE", username);
		model.addAttribute("updateNum", updateList.size());
		model.addAttribute("updateList", updateList);
		
		return "jspiso/isoInventoryUpload";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) String idList, HttpServletRequest request, HttpServletResponse response) {
		
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
		
		return "redirect:list.htm";
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
 // Lay danh sach trang thai
   	@RequestMapping("location-list")
   	public @ResponseBody 
   	List<IsoInventory> getlocationList() {
   		List<IsoInventory> locationList = isoInventoryDAO.getLocationList();
   		
   		return locationList;
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
   	
   	// Lay danh sach input status
   	@RequestMapping("inputStatusList")
   	public @ResponseBody 
   	List<SYS_PARAMETER> inputStatusList() {
   		List<SYS_PARAMETER> inputStatusList = sysParameterDao.getInputStatusList();
   		
   		return inputStatusList;
   	 }
   	
  	@RequestMapping("exportResource")
  	public ModelAndView exportResource(
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
			   @RequestParam(required = false) String location,
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
		
		List<IsoInventory> isoEquipmentList = isoInventoryDAO.getIsoEquipmentFilter(deptCode, team, subTeam, province, district, neType, ne, neGroup, locationName, vendor, productCode, productName, seriNo, inputStatus, status, location, null, null, sortfield, sortorder, strWhere);
		exportReportEquipment(tempFile, isoEquipmentList);
		
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
  	
  	private void exportReportEquipment(File tempFile, List<IsoInventory> isoEquipmentList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("Equipment Report", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
		    
			int i=0;
			Label label0 = new Label(i, 0, "Department", cellFormatHeader);
			sheet.addCell(label0);  
			i++;
			Label label1 = new Label(i, 0, "Team", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "Sub Team", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "Province", cellFormatHeader);
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "District", cellFormatHeader);
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "NE Type", cellFormatHeader);
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "NE Group", cellFormatHeader);
			sheet.addCell(label6);
			i++;
			Label label7 = new Label(i, 0, "Location Name", cellFormatHeader);
			sheet.addCell(label7);
			i++;
			Label label8 = new Label(i, 0, "Location", cellFormatHeader);
			sheet.addCell(label8);
			i++;
			Label label9 = new Label(i, 0, "NE Name", cellFormatHeader);
			sheet.addCell(label9);
			i++;
			Label label10 = new Label(i, 0, "Product Code", cellFormatHeader);
			sheet.addCell(label10);
			i++;
			Label label11 = new Label(i, 0, "Seri No", cellFormatHeader);
			sheet.addCell(label11);
			i++;
			Label label12 = new Label(i	, 0, "Product Date", cellFormatHeader);
			sheet.addCell(label12);
			i++;
			Label label13 = new Label(i, 0, "NE Parent", cellFormatHeader);
			sheet.addCell(label13);
			i++;
			Label label14 = new Label(i, 0, "Product Name", cellFormatHeader);
			sheet.addCell(label14);
			i++;
			Label label15 = new Label(i, 0, "Status", cellFormatHeader);
			sheet.addCell(label15);
			i++;
			Label label16 = new Label(i, 0, "Vendor", cellFormatHeader);
			sheet.addCell(label16);
			i++;
			Label label17 = new Label(i, 0, "Rack", cellFormatHeader);
			sheet.addCell(label17);
			i++;
			Label label18 = new Label(i, 0, "Subrack", cellFormatHeader);
			sheet.addCell(label18);
			i++;
			Label label19 = new Label(i, 0, "Slot", cellFormatHeader);
			sheet.addCell(label19);
			i++;
			Label label20 = new Label(i, 0, "Software Version", cellFormatHeader);
			sheet.addCell(label20);
			i++;
			Label label21 = new Label(i, 0, "Detection Date", cellFormatHeader);
			sheet.addCell(label21);
			i++;
			Label label22 = new Label(i, 0, "Last Equipment Name", cellFormatHeader);
			sheet.addCell(label22);
			i++;
			Label label23 = new Label(i, 0, "Last Equipment Date", cellFormatHeader);
			sheet.addCell(label23);
			i++;
			Label label24 = new Label(i, 0, "OM IP", cellFormatHeader);
			sheet.addCell(label24);
			i++;
			Label label25 = new Label(i, 0, "NSEI", cellFormatHeader);
			sheet.addCell(label25);
			i++;
			Label label26 = new Label(i, 0, "SPC", cellFormatHeader);
			sheet.addCell(label26);
			i++;
			Label label27 = new Label(i, 0, "BSCID/RNCID", cellFormatHeader);
			sheet.addCell(label27);
			i++;
			Label label28 = new Label(i, 0, "Power", cellFormatHeader);
			sheet.addCell(label28);
			i++;
			Label label29 = new Label(i, 0, "Default Current (A)", cellFormatHeader);
			sheet.addCell(label29);
			i++;
			Label label30 = new Label(i, 0, "Default Voltage (V)", cellFormatHeader);
			sheet.addCell(label30);
			i++;
			Label label31 = new Label(i, 0, "Real Current (A/Ah)", cellFormatHeader);
			sheet.addCell(label31);
			i++;
			Label label32 = new Label(i, 0, "Load", cellFormatHeader);
			sheet.addCell(label32);
			i++;
			Label label33 = new Label(i, 0, "Battery", cellFormatHeader);
			sheet.addCell(label33);
			i++;
			Label label34 = new Label(i, 0, "Battery Duration", cellFormatHeader);
			sheet.addCell(label34);
			i++;
			Label label35 = new Label(i, 0, "Maintenance Interval (month)", cellFormatHeader);
			sheet.addCell(label35);
			i++;
			Label label36 = new Label(i, 0, "Maintenance Date", cellFormatHeader);
			sheet.addCell(label36);
			i++;
			Label label37 = new Label(i, 0, "Maintenance Result", cellFormatHeader);
			sheet.addCell(label37);
			i++;
			Label label38 = new Label(i, 0, "Maintenance Supervisor", cellFormatHeader);
			sheet.addCell(label38);
			i++;
			Label label39 = new Label(i, 0, "Maintenance Comment", cellFormatHeader);
			sheet.addCell(label39);
			i++;
			Label label40 = new Label(i, 0, "Contract Number", cellFormatHeader);
			sheet.addCell(label40);
			i++;
			Label label41 = new Label(i, 0, "Contract Date", cellFormatHeader);
			sheet.addCell(label41);
			i++;
			Label label42 = new Label(i, 0, "Description", cellFormatHeader);
			sheet.addCell(label42);
			
			i = 1;
			
			for (IsoInventory record : isoEquipmentList) {
				int j=0;
				Label record0 = new Label(j, i, record.getDeptCode(), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,record.getTeam(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getSubTeam(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getProvince(), cellFormat);
				sheet.addCell(record3);
				j++;
				Label record4 = new Label(j, i, record.getDistrict(), cellFormat);
				sheet.addCell(record4);
				j++;
				Label record5 = new Label(j, i, record.getNeType(), cellFormat);
				sheet.addCell(record5);
				j++;
				Label record6 = new Label(j, i, record.getNeGroup(), cellFormat);
				sheet.addCell(record6);
				j++;
				Label record7 = new Label(j, i, record.getLocationName(), cellFormat);
				sheet.addCell(record7);
				j++;
				Label record8 = new Label(j, i, record.getLocation(), cellFormat);
				sheet.addCell(record8);
				j++;
				Label record9 = new Label(j, i, record.getNe(), cellFormat);
				sheet.addCell(record9);
				j++;
				Label record10 = new Label(j, i, record.getProductCode(), cellFormat);
				sheet.addCell(record10);
				j++;
				Label record11 = new Label(j, i, record.getSeriNo(), cellFormat);
				sheet.addCell(record11);
				j++;
				Label record12 = new Label(j, i, record.getProductDateStr(), cellFormat);
				sheet.addCell(record12);
				j++;
				Label record13 = new Label(j, i, record.getNeParent(), cellFormat);
				sheet.addCell(record13);
				j++;
				Label record14 = new Label(j, i, record.getProductName(), cellFormat);
				sheet.addCell(record14);
				j++;
				Label record15 = new Label(j, i, record.getStatusName(), cellFormat);
				sheet.addCell(record15);
				j++;
				Label record16 = new Label(j, i, record.getVendor(), cellFormat);
				sheet.addCell(record16);
				j++;
				Label record17 = new Label(j, i, record.getRack(), cellFormat);
				sheet.addCell(record17);
				j++;
				Label record18 = new Label(j, i, record.getSubrack(), cellFormat);
				sheet.addCell(record18);
				j++;
				Label record19 = new Label(j, i, record.getSlot(), cellFormat);
				sheet.addCell(record19);
				j++;
				Label record20 = new Label(j, i, record.getSwVersion(), cellFormat);
				sheet.addCell(record20);
				j++;
				Label record21 = new Label(j, i, record.getInitializeDateStr(), cellFormat);
				sheet.addCell(record21);
				j++;
				Label record22 = new Label(j, i, record.getNeOld(), cellFormat);
				sheet.addCell(record22);
				j++;
				Label record23 = new Label(j, i, record.getUpdatedStr(), cellFormat);
				sheet.addCell(record23);
				j++;
				Label record24 = new Label(j, i, record.getOmIp(), cellFormat);
				sheet.addCell(record24);
				j++;
				Label record25 = new Label(j, i, record.getNsei(), cellFormat);
				sheet.addCell(record25);
				j++;
				Label record26 = new Label(j, i, record.getSpc(), cellFormat);
				sheet.addCell(record26);
				j++;
				Label record27 = new Label(j, i, record.getBscidRncid(), cellFormat);
				sheet.addCell(record27);
				j++;
				Label record28 = new Label(j, i, record.getPowerStr(), cellFormat);
				sheet.addCell(record28);
				j++;
				Label record29 = new Label(j, i, record.getDefaultCurrentStr(), cellFormat);
				sheet.addCell(record29);
				j++;
				Label record30 = new Label(j, i, record.getDefaultVoltageStr(), cellFormat);
				sheet.addCell(record30);
				j++;
				Label record31 = new Label(j, i, record.getRealCurrentStr(), cellFormat);
				sheet.addCell(record31);
				j++;
				Label record32 = new Label(j, i, record.getLoadFollowStr(), cellFormat);
				sheet.addCell(record32);
				j++;
				Label record33 = new Label(j, i, record.getBatteryStr(), cellFormat);
				sheet.addCell(record33);
				j++;
				Label record34 = new Label(j, i, record.getBatteryDurationStr(), cellFormat);
				sheet.addCell(record34);
				j++;
				Label record35 = new Label(j, i, record.getMaintenanceIntervalStr(), cellFormat);
				sheet.addCell(record35);
				j++;
				Label record36 = new Label(j, i, record.getMaintenanceDateStr(), cellFormat);
				sheet.addCell(record36);
				j++;
				Label record37 = new Label(j, i, record.getMaintenanceResult(), cellFormat);
				sheet.addCell(record37);
				j++;
				Label record38 = new Label(j, i, record.getMaintenanceSupervisor(), cellFormat);
				sheet.addCell(record38);
				j++;
				Label record39 = new Label(j, i, record.getMaintenanceComment(), cellFormat);
				sheet.addCell(record39);
				j++;
				Label record40 = new Label(j, i, record.getContractNumber(), cellFormat);
				sheet.addCell(record40);
				j++;
				Label record41 = new Label(j, i, record.getContractDateStr(), cellFormat);
				sheet.addCell(record41);
				j++;
				Label record42 = new Label(j, i, record.getDescription(), cellFormat);
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
