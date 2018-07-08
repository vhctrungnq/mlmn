package controller.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import controller.BaseController;

import vo.CConfigAlarmType;
import vo.CHighlightConfigs;
import vo.CTableConfig;
import vo.SYS_PARAMETER;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;
import vo.alarm.utils.UploadTools;

import dao.CConfigAlarmTypeDAO;
import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.SYS_PARAMETERDAO;

/**
 * Function        : Quan ly cau hinh canh bao
 * Created By      : BUIQUANG
 * Create Date     : 24/10/2013
 * Modified By     : BUIQUANG
 * Modify Date     : 
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/admin/c-config-alarm-type/*")
public class CConfigAlarmTypeController extends BaseController{

	@Autowired
	private CConfigAlarmTypeDAO cConfigAlarmTypeDAO;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	
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
    public ModelAndView list(@ModelAttribute("filter") CConfigAlarmType filter, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "CConfigAlarmType" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		loadDataCombobox(model,filter);
		
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("C_CONFIG_ALARM_TYPE");
		//lay du lieu column cua grid
		model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
		//Lay du lieu datafield cua grid
		model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
		//Lay du lieu cac cot an hien cua grid
		List<CTableConfig> columnList = cTableConfigDAO.getColumnList("C_CONFIG_ALARM_TYPE");
		model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
		
		String filterUrl = "";
		String temp = "";
		if(filter.getVendor() != null){
			filterUrl += "vendor=" + filter.getVendor(); 
			temp = "&";
		}
		if(filter.getNode() != null){
			filterUrl += temp + "node=" + filter.getNode(); 
			temp = "&";
		}
		if(filter.getNeType() != null){
			filterUrl += temp + "neType=" + filter.getNeType();
			temp = "&";
		}
		if(filter.getBlockValue() != null)
		{
			filterUrl += temp + "blockValue=" + filter.getBlockValue();
			temp = "&";
		}
		if(filter.getAlarmMappingName() != null)
		{
			filterUrl += temp + "alarmMappingName=" + filter.getAlarmMappingName();
			temp = "&";
		}
		if(filter.getAlarmMappingId() != null)
		{
			filterUrl += temp + "alarmMappingId=" + filter.getAlarmMappingId();
		}
		if(filterUrl != "")
			filterUrl = "?" + filterUrl;
		
		//Lay duong link url de lay du lieu do vao grid
		model.addAttribute("url", "data.htm" + filterUrl);
		
		return new ModelAndView("jspadmin/cConfigAlarmTypeList");
	}
	
	@RequestMapping("/data")
	public void doGet(@RequestParam(required = false) String vendor,
			   @RequestParam(required = false) String node, 
			   @RequestParam(required = false) String neType, 
			   @RequestParam(required = false) String blockValue, 
			   @RequestParam(required = false) String alarmMappingName, 
			   @RequestParam(required = false) String alarmMappingId,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<CConfigAlarmType> cConfigAlarmTypeList = null;
		int startRecord = 0, endRecord = 0, pageSize = 100;
		String sortfield = "";
		String sortorder = "";
		int pageNum = Integer.parseInt(request.getParameter("pagenum"));
		if(pageNum == -1)
			pageNum = 1;
		if(!request.getParameter("pagesize").equals(""))
			 pageSize = Integer.parseInt(request.getParameter("pagesize"));
		else pageSize = 100;
		sortfield = request.getParameter("sortdatafield");
		sortorder = request.getParameter("sortorder");
		List<CTableConfig> columnList = cTableConfigDAO.getTableConfigGet("C_CONFIG_ALARM_TYPE", sortfield);
		List<CTableConfig> tableConfigList = cTableConfigDAO.getTableConfigGet("C_CONFIG_ALARM_TYPE", null);
		
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
			cConfigAlarmTypeList = cConfigAlarmTypeDAO.getCConfigAlarmTypeFilter( vendor, node, neType, blockValue, alarmMappingName, alarmMappingId, startRecord, endRecord, sortfield, sortorder, strWhere);
			totalRow = cConfigAlarmTypeDAO.countCConfigAlarmType( vendor, node, neType, blockValue, alarmMappingName, alarmMappingId, strWhere);
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
		String jsonCartList = gs.toJson(cConfigAlarmTypeList);
		strjson+= jsonCartList;
	    strjson += "}]";
	    
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(strjson);
		out.close();
		
	}
	
	private void configAlarmTypeAddEdit(String id, ModelMap model)
	{
		if(id != null && id != "")
			model.addAttribute("configAlarmTypeAddEdit", "Y");
		else
			model.addAttribute("configAlarmTypeAddEdit", "N");
	}
	
	private void loadDataCombobox(ModelMap model,CConfigAlarmType filter)
	{
		// Danh sach nha cung cap
		List<SYS_PARAMETER> vendorList = sysParameterDao.getVendorList();
		model.addAttribute("vendorList", vendorList);
		
		// Danh sach loai mang
		List<SYS_PARAMETER> nodeList = sysParameterDao.getNetworkTypeList();
		model.addAttribute("nodeList", nodeList);
		
		// Danh sach ne Type
		List<SYS_PARAMETER> neTypeList = sysParameterDao.getNeTypeList();
		model.addAttribute("neTypeList", neTypeList);
		
		// Danh sach raw table
		List<SYS_PARAMETER> rawTableList = sysParameterDao.getRawTableList();
		model.addAttribute("rawTableList", rawTableList);
		
		// Danh sach alarm column name
		List<SYS_PARAMETER> blockColumnNameList = sysParameterDao.getAlarmColumnNameList();
		model.addAttribute("blockColumnNameList", blockColumnNameList);
		
		// Danh sach is enable, is monitor, is send sms
		List<SYS_PARAMETER> yesNoList= sysParameterDao.getYesNoList();
		model.addAttribute("yesNoList", yesNoList);
		
		// Danh sach search
		List<SYS_PARAMETER> searchList= sysParameterDao.getSearchList();
		model.addAttribute("searchList", searchList);
		
		// Danh sach gợi nhớ alarm mapping name
		List<CConfigAlarmType> getAlarmMappingNameList = cConfigAlarmTypeDAO.getAlarmMappingNameList();
		String alarmMappingNameArray="var alarmMappingNameList = new Array(";
		String cn="";
		for (int i=0;i<getAlarmMappingNameList.size();i++) {
			alarmMappingNameArray = alarmMappingNameArray + cn +"\""+getAlarmMappingNameList.get(i).getAlarmMappingName()+"\"";
			cn=",";
		}
		alarmMappingNameArray = alarmMappingNameArray+");";
		model.addAttribute("alarmMappingNameList", alarmMappingNameArray);
		
		// Danh sach gợi nhớ alarm mapping id
		List<CConfigAlarmType> getAlarmMappingIdList = cConfigAlarmTypeDAO.getAlarmMappingIdList();
		String alarmMappingIdArray="var alarmMappingIdList = new Array(";
		String cnId="";
		for (int i=0;i<getAlarmMappingIdList.size();i++) {
			alarmMappingIdArray = alarmMappingIdArray + cnId +"\""+getAlarmMappingIdList.get(i).getAlarmMappingId()+"\"";
			cnId=",";
		}
		alarmMappingIdArray = alarmMappingIdArray+");";
		model.addAttribute("alarmMappingIdList", alarmMappingIdArray);
		
		// Danh sach gợi nhớ alarm mapping type
		List<CConfigAlarmType> getAlarmTypeList = cConfigAlarmTypeDAO.getDistinctAlarmTypeList();
		String alarmTypeArray="var alarmTypeList = new Array(";
		String cnType="";
		for (int i=0;i<getAlarmTypeList.size();i++) {
			alarmTypeArray = alarmTypeArray + cnType +"\""+getAlarmTypeList.get(i).getAlarmType()+"\"";
			cnType=",";
		}
		alarmTypeArray = alarmTypeArray+");";
		model.addAttribute("alarmTypeList", alarmTypeArray);
		
		if(filter.getBlockValue() != null)
			filter.setBlockValue(filter.getBlockValue().trim());
		if(filter.getAlarmMappingName() != null)
			filter.setAlarmMappingName(filter.getAlarmMappingName().trim());
		if(filter.getAlarmMappingId() != null)
			filter.setAlarmMappingId(filter.getAlarmMappingId().trim());
		model.addAttribute("vendorCBB", filter.getVendor());
		model.addAttribute("nodeCBB", filter.getNode());
		model.addAttribute("neTypeCBB", filter.getNeType());
		model.addAttribute("rawTableCBB", filter.getRawTable());
		model.addAttribute("blockColumnNameCBB", filter.getBlockColumnName());
		model.addAttribute("isEnableCBB", filter.getIsEnable());
		model.addAttribute("isMonitorCBB", filter.getIsMonitor());
		model.addAttribute("isSendSmsCBB", filter.getIsSendSms());
		model.addAttribute("searchCBB", filter.getSearch());
		model.addAttribute("alarmMappingNameCBB", filter.getAlarmMappingName());
		model.addAttribute("alarmMappingIdCBB", filter.getAlarmMappingId());
		model.addAttribute("alarmTypeCBB", filter.getAlarmType());
		model.addAttribute("blockValue", filter.getBlockValue());
		model.addAttribute("alarmMappingName", filter.getAlarmMappingName());
		model.addAttribute("alarmMappingId", filter.getAlarmMappingId());
		model.addAttribute("description", filter.getDescription());
		model.addAttribute("summaryType", filter.getSummaryType());
		model.addAttribute("alarmInfoColumnName", filter.getAlarmInfoColumnName());
		model.addAttribute("alarmInfoValue", filter.getAlarmInfoValue());
		model.addAttribute("isSoundAlarm", filter.getIsSoundAlarm());
	}
	
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		CConfigAlarmType configAlarmType = (id == null) ? new CConfigAlarmType() : cConfigAlarmTypeDAO.selectByPrimaryKey(Integer.parseInt(id));
		model.addAttribute("configAlarmTypeForm", configAlarmType);
		configAlarmTypeAddEdit(id, model);
		loadDataCombobox(model, configAlarmType);
		model.addAttribute("ordering", configAlarmType.getOrdering());
		return "jspadmin/cConfigAlarmTypeForm";	
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id,
			@RequestParam(required = false) String ordering, @ModelAttribute("configAlarmTypeForm") @Valid CConfigAlarmType configAlarmType, BindingResult result, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		//Ném lỗi
		if (result.hasErrors()) {
			configAlarmTypeAddEdit(id, model);
			loadDataCombobox(model, configAlarmType);
			if(result.hasFieldErrors("ordering"))
				model.addAttribute("orderingError", "orderingError");
			model.addAttribute("ordering", ordering);
			return "jspadmin/cConfigAlarmTypeForm";	
		}
		if(id == "")
		{
			try{
				if(cConfigAlarmTypeDAO.getCheckUniqueAlarmType( configAlarmType.getVendor(),  configAlarmType.getNode(),
						configAlarmType.getNeType(),  configAlarmType.getRawTable(),  configAlarmType.getBlockColumnName(),
						configAlarmType.getBlockValue(), null).size() == 0){
					cConfigAlarmTypeDAO.insert(configAlarmType);
					saveMessageKey(request, "messsage.confirm.addsuccess");
				}
				else{
					
					configAlarmTypeAddEdit(id, model);
					loadDataCombobox(model, configAlarmType);
					model.addAttribute("ordering", ordering);
					saveMessageKey(request, "messsage.confirm.cConfigAlarmType.checkUniqueKey");
					return "jspadmin/cConfigAlarmTypeForm";	
				}
			}
			catch(Exception e){
				
				configAlarmTypeAddEdit(id, model);
				loadDataCombobox(model, configAlarmType);
				model.addAttribute("ordering", ordering);
				saveMessageKey(request, "messsage.confirm.cConfigAlarmType.errorMaxLength");
				return "jspadmin/cConfigAlarmTypeForm";	
			}
		}
		else
		{
			try{
				if(cConfigAlarmTypeDAO.getCheckUniqueAlarmType( configAlarmType.getVendor(),  configAlarmType.getNode(),
						configAlarmType.getNeType(),  configAlarmType.getRawTable(),  configAlarmType.getBlockColumnName(),
						configAlarmType.getBlockValue(), id).size() == 0){
					cConfigAlarmTypeDAO.updateByPrimaryKey(configAlarmType);
					saveMessageKey(request, "messsage.confirm.updatesuccess");
				}
				else
				{
					configAlarmTypeAddEdit(id, model);
					loadDataCombobox(model, configAlarmType);
					model.addAttribute("ordering", ordering);
					saveMessageKey(request, "messsage.confirm.cConfigAlarmType.checkUniqueKey");
					return "jspadmin/cConfigAlarmTypeForm";	
				}
			}
			catch(Exception e){
				
				configAlarmTypeAddEdit(id, model);
				loadDataCombobox(model, configAlarmType);
				model.addAttribute("ordering", ordering);
				saveMessageKey(request, "messsage.confirm.cConfigAlarmType.errorMaxLength");
				return "jspadmin/cConfigAlarmTypeForm";	
			}
		}
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) Integer id, HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
			cConfigAlarmTypeDAO.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch(Exception e)
		{
			saveMessageKey(request, "message.confirm.deleteOther");
			}
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm() {
		return "jspadmin/cConfigAlarmTypeUpload";
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
		
		return "jspadmin/cConfigAlarmTypeUpload";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent(List sheetData, ModelMap model,HttpServletRequest request) {
		String createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<CConfigAlarmType> successList = new ArrayList<CConfigAlarmType>();
		List<CConfigAlarmType> failedList = new ArrayList<CConfigAlarmType>();
		List<CConfigAlarmType> success = new ArrayList<CConfigAlarmType>();
		
		String vendor;
		String node;
		String neType;
		String blockColumnName;
		String blockValue;
		String description;
		String alarmMappingName;
		String alarmMappingId;
		String alarmType;
		String isMonitor;
		String isSendSms;
		String isEnable;
		String ordering;
		String rawTable;
		String summaryType;
		String search;
		String alarmInfoColumnName;
		String alarmInfoValue;
		String brandname;
		String isSoundAlarm;
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 20) {
        		saveMessageKey(request, "sidebar.admin.configAlarmTypeUploadErrorStructuresNumber");
        		
        		return "jspadmin/cConfigAlarmTypeUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		CConfigAlarmType configAlarmType;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			configAlarmType = new CConfigAlarmType();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=20; j++) {
     					data.add("");
     				}
        			vendor					= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			node					= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			neType					= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			blockColumnName			= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			blockValue				= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			description				= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			alarmMappingName		= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			alarmMappingId			= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			alarmType				= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			isMonitor				= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			isSendSms				= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
        			isEnable				= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim();
        			ordering				= data.get(12).toString().trim().equals("")?null:data.get(12).toString().trim();
        			rawTable				= data.get(13).toString().trim().equals("")?null:data.get(13).toString().trim();
        			summaryType				= data.get(14).toString().trim().equals("")?null:data.get(14).toString().trim();
        			search					= data.get(15).toString().trim().equals("")?null:data.get(15).toString().trim();
        			alarmInfoColumnName		= data.get(16).toString().trim().equals("")?null:data.get(16).toString().trim();
        			alarmInfoValue			= data.get(17).toString().trim().equals("")?null:data.get(17).toString().trim();
        			brandname				= data.get(18).toString().trim().equals("")?null:data.get(18).toString().trim();
        			isSoundAlarm			= data.get(19).toString().trim().equals("")?null:data.get(19).toString().trim();
        			
        			// Kiem tra loi
        			if (vendor == null || node == null || neType == null || rawTable == null || blockColumnName == null || blockValue == null || alarmMappingName == null || alarmMappingId == null || alarmType == null
        					|| isMonitor == null || isSendSms == null || isEnable == null || search == null
        					|| (vendor != null && vendor.length() > 20)
        					|| (node != null && node.length() > 20)
        					|| (neType != null && neType.length() > 20)
        					|| (rawTable != null && rawTable.length() > 30)
        					|| (blockColumnName != null && blockColumnName.length() > 30)
        					|| (blockValue != null && blockValue.length() > 250)
        					|| (description != null && description.length() > 250)
        					|| (alarmMappingName != null && alarmMappingName.length() > 250)
        					|| (alarmMappingId != null && alarmMappingId.length() > 50)
        					|| (alarmType != null && alarmType.length() > 250)
        					|| (summaryType != null && summaryType.length() > 10)
        					|| (alarmInfoColumnName != null && alarmInfoColumnName.length() > 30)
        					|| (alarmInfoValue != null && alarmInfoValue.length() > 250)
        					|| (isMonitor != null && !isMonitor.toString().toLowerCase().contains("y") && !isMonitor.toString().toLowerCase().contains("n"))
        					|| (isSendSms != null && !isSendSms.toString().toLowerCase().contains("y") && !isSendSms.toString().toLowerCase().contains("n"))
        					|| (isEnable != null && !isEnable.toString().toLowerCase().contains("y") && !isEnable.toString().toLowerCase().contains("n"))
        					|| (isSoundAlarm != null && !isSoundAlarm.toString().toLowerCase().contains("y") && !isSoundAlarm.toString().toLowerCase().contains("n"))
						) {
						error = true;
					}
        			
        			/*try
	    			{*/
        				if(ordering != null){
		        			Integer a = Integer.parseInt(ordering);
		        			configAlarmType.setOrdering(a);
	        			}
	    			/*}
	    			catch(Exception e)
	    			{
	    				error = true;
	    			}*/
        			
        			//---------------------------------------------------------------------------
        			configAlarmType.setVendor(vendor);
        			configAlarmType.setNode(node);
        			configAlarmType.setNeType(neType);
        			configAlarmType.setRawTable(rawTable);
        			configAlarmType.setBlockColumnName(blockColumnName);
        			if(blockValue != null)
        				blockValue = blockValue.trim();
        			configAlarmType.setBlockValue(blockValue);
        			configAlarmType.setDescription(description);
        			configAlarmType.setAlarmMappingName(alarmMappingName);
        			configAlarmType.setAlarmMappingId(alarmMappingId);
        			configAlarmType.setAlarmType(alarmType);
        			configAlarmType.setIsMonitorName(isMonitor);
        			configAlarmType.setIsSendSmsName(isSendSms);
        			configAlarmType.setIsEnableName(isEnable);
        			if(isMonitor != null){
        				if(isMonitor.toString().toLowerCase().equals("yes"))
        					configAlarmType.setIsMonitor("Y");
        				else if(isMonitor.toString().toLowerCase().equals("no"))
        					configAlarmType.setIsMonitor("N");
        				else
        					configAlarmType.setIsMonitor(isMonitor);
        			}
        			if(isSendSms != null){
        				if(isSendSms.toString().toLowerCase().equals("yes"))
        					configAlarmType.setIsSendSms("Y");
        				else if(isSendSms.toString().toLowerCase().equals("no"))
        					configAlarmType.setIsSendSms("N");
        				else
        					configAlarmType.setIsSendSms(isSendSms);
        			}
        			if(isEnable != null){
        				if(isEnable.toString().toLowerCase().equals("yes"))
        					configAlarmType.setIsEnable("Y");
        				else if(isEnable.toString().toLowerCase().equals("no"))
        					configAlarmType.setIsEnable("N");
        				else
        					configAlarmType.setIsEnable(isEnable);
        			}
        			if(isSoundAlarm != null){
        				if(isSoundAlarm.toString().toLowerCase().equals("yes"))
        					configAlarmType.setIsSoundAlarm("Y");
        				else if(isSoundAlarm.toString().toLowerCase().equals("no"))
        					configAlarmType.setIsSoundAlarm("N");
        				else
        					configAlarmType.setIsSoundAlarm(isSoundAlarm);
        			}
        			configAlarmType.setSummaryType(summaryType);
        			configAlarmType.setSearch(search);
        			configAlarmType.setAlarmInfoColumnName(alarmInfoColumnName);
        			configAlarmType.setAlarmInfoValue(alarmInfoValue);
        			configAlarmType.setBrandname(brandname);
        			
        			if (vendor == null && node == null && neType == null && rawTable == null && blockColumnName == null && blockValue == null
        					&& isMonitor == null && isSendSms == null && isEnable == null && search == null &&
        					alarmMappingName == null && alarmMappingId == null && alarmType == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(configAlarmType);
    					} else  {
    						
    						successList.add(configAlarmType);
    					}
        			}
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (CConfigAlarmType record: successList) {
			/*try {*/
					
					if(cConfigAlarmTypeDAO.getCheckUniqueAlarmType(record.getVendor(),  record.getNode(),
							null,  record.getRawTable(),  record.getBlockColumnName(),
							record.getBlockValue(), null).size() == 0){
						record.setCreatedBy(createdBy);
						cConfigAlarmTypeDAO.insertSelective(record);
					}else{
						record.setModifiedBy(createdBy);
						cConfigAlarmTypeDAO.updateByUniqueKey(record);
					}
					success.add(record);
			/*} catch (Exception ex) {
					failedList.add(record);
			}*/
		}
		
		if (failedList.size() == 0 && successList.size() > 0) {
			model.addAttribute("status", "Upload thành công.");				// Upload thành công
		} else if (failedList.size() == 0 && successList.size() == 0) {
			model.addAttribute("status", "Không có dữ liệu.");				// Không có dữ liệu
		} else {
			model.addAttribute("status", "Dữ liệu cấu hình cảnh báo không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");	// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", success.size());
		model.addAttribute("totalNum", failedList.size()+success.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		return "jspadmin/cConfigAlarmTypeUpload";
	}
	
	@RequestMapping("exportData")
  	public ModelAndView reportAlarmLog(
  			   @RequestParam(required = false) String vendor,
			   @RequestParam(required = false) String node, 
			   @RequestParam(required = false) String neType, 
			   @RequestParam(required = false) String blockValue, 
			   @RequestParam(required = false) String alarmMappingName, 
			   @RequestParam(required = false) String alarmMappingId,
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
		
		List<CConfigAlarmType> cConfigAlarmTypeList = cConfigAlarmTypeDAO.getCConfigAlarmTypeFilter(vendor, node, neType, blockValue, alarmMappingName, alarmMappingId, null, null, sortfield, sortorder, strWhere);
		
		exportReport(tempFile, cConfigAlarmTypeList);
		
		
		// return
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String filename = "ConfigAlarmType_"+ dateNow;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();
  		return null;
  	}
	
	private void exportReport(File tempFile, List<CConfigAlarmType> cConfigAlarmTypeList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("Config Alarm Type", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			int i=0;
			
			Label label0 = new Label(i, 0, "Vendor", cellFormatHeader);
			sheet.addCell(label0);
			i++;
			Label label1 = new Label(i, 0, "Network", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "NE Type", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "Alarm Column Name", cellFormatHeader);
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "Alarm Value", cellFormatHeader);
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "Description", cellFormatHeader);
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "Alarm Mapping Name", cellFormatHeader);
			sheet.addCell(label6);
			i++;
			Label label7 = new Label(i, 0, "Alarm Mapping Id", cellFormatHeader);
			sheet.addCell(label7);
			i++;
			Label label8 = new Label(i, 0, "Alarm Type", cellFormatHeader);
			sheet.addCell(label8);
			i++;
			Label label9 = new Label(i, 0, "Monitor", cellFormatHeader);
			sheet.addCell(label9);
			i++;
			Label label10 = new Label(i, 0, "Send Sms", cellFormatHeader);
			sheet.addCell(label10);
			i++;
			Label label11 = new Label(i, 0, "Enable", cellFormatHeader);
			sheet.addCell(label11);
			i++;
			Label label12 = new Label(i, 0, "Ordering", cellFormatHeader);
			sheet.addCell(label12);
			i++;
			Label label13 = new Label(i, 0, "Raw Table", cellFormatHeader);
			sheet.addCell(label13);
			i++;
			Label label14 = new Label(i, 0, "Summary Type", cellFormatHeader);
			sheet.addCell(label14);
			i++;
			Label label15 = new Label(i, 0, "Search", cellFormatHeader);
			sheet.addCell(label15);
			i++;
			Label label16 = new Label(i, 0, "Alarm Info Column Name", cellFormatHeader);
			sheet.addCell(label16);
			i++;
			Label label17 = new Label(i, 0, "Alarm Info Value", cellFormatHeader);
			sheet.addCell(label17);
			i++;
			Label label18 = new Label(i, 0, "Brand (SMS)", cellFormatHeader);
			sheet.addCell(label18);
			i = 1;
			
			for (CConfigAlarmType record : cConfigAlarmTypeList) {
				int j=0;
				Label record0 = new Label(j, i, record.getVendor(), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,record.getNode(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getNeType(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getBlockColumnName(), cellFormat);
				sheet.addCell(record3);
				j++;
				Label record4 = new Label(j, i, record.getBlockValue(), cellFormat);
				sheet.addCell(record4);
				j++;
				Label record5 = new Label(j, i, record.getDescription(), cellFormat);
				sheet.addCell(record5);
				j++;
				Label record6 = new Label(j, i, record.getAlarmMappingName(), cellFormat);
				sheet.addCell(record6);
				j++;
				Label record7 = new Label(j, i, record.getAlarmMappingId(), cellFormat);
				sheet.addCell(record7);
				j++;
				Label record8 = new Label(j, i, record.getAlarmType(), cellFormat);
				sheet.addCell(record8);
				j++;
				Label record9 = new Label(j, i, record.getIsMonitorName(), cellFormat);
				sheet.addCell(record9);
				j++;
				Label record10 = new Label(j, i, record.getIsSendSmsName(), cellFormat);
				sheet.addCell(record10);
				j++;
				Label record11 = new Label(j, i, record.getIsEnableName(), cellFormat);
				sheet.addCell(record11);
				j++;
				Label record12 = new Label(j, i, record.getOrderingStr(), cellFormat);
				sheet.addCell(record12);
				j++;
				Label record13 = new Label(j, i, record.getRawTable(), cellFormat);
				sheet.addCell(record13);
				j++;
				Label record14 = new Label(j, i, record.getSummaryType(), cellFormat);
				sheet.addCell(record14);
				j++;
				Label record15 = new Label(j, i, record.getSearch(), cellFormat);
				sheet.addCell(record15);
				j++;
				Label record16 = new Label(j, i, record.getAlarmInfoColumnName(), cellFormat);
				sheet.addCell(record16);
				j++;
				Label record17 = new Label(j, i, record.getAlarmInfoValue(), cellFormat);
				sheet.addCell(record17);
				j++;
				Label record18 = new Label(j, i, record.getBrandname(), cellFormat);
				sheet.addCell(record18);
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
