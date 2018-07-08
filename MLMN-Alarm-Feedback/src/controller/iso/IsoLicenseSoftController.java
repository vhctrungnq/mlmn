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
import vo.IsoLicenseSoft;
import vo.SYS_PARAMETER;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;
import vo.alarm.utils.UploadTools;

import com.google.gson.Gson;

import controller.BaseController;
import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.IsoLicenseSoftDAO;
import dao.SYS_PARAMETERDAO;

/**
 * Ten file: IsoLicenseSoftController.java
 * Muc dich: Quan ly ban quyen mem
 * @author QUANG
 * Ngay tao: 06/09/2013
 * Lich su thay doi:
 * Nguoi sua: ThangPX
 * Ngay sua: 15/04/2014
 * Muc dich: Chinh sua dieu kien tim kiem
 */

@Controller
@RequestMapping("/iso/quan-ly-ban-quyen-mem/*")
public class IsoLicenseSoftController extends BaseController{

	@Autowired
	private IsoLicenseSoftDAO isoLicenseSoftDAO;
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
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
	public ModelAndView list( @RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate, 
			   @RequestParam(required = false) String vendor, 
			   @RequestParam(required = false) String neType, 
			   @RequestParam(required = false) String neName, 
			   @RequestParam(required = false) String featureCode, 
			   @RequestParam(required = false) String featureName,  ModelMap model, HttpServletRequest request, HttpServletResponse response)
	{
		
		// Ngay thang
		if (startDate == null || startDate.equals("")|| DateTools.isValid("dd/MM/yyyy", startDate)==false) {			
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DAY_OF_MONTH, -1);
			
			startDate = df.format(cal.getTime());
		
		}
		if (endDate == null || endDate.equals("")|| DateTools.isValid("dd/MM/yyyy", endDate)==false)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			endDate = df.format(cal.getTime());
		} 
		if(vendor != null)
			vendor = vendor.trim();
		if(neType != null)
			neType = neType.trim();
		if(neName != null)
			neName = neName.trim();
		if(featureCode != null)
			featureCode = featureCode.trim();
		if(featureName != null)
			featureName = featureName.trim();
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "BanQuyenMem_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("ISO_LICENSE_SOFT");
		//lay du lieu column cua grid
		model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
		//Lay du lieu datafield cua grid
		model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
		//Lay du lieu cac cot an hien cua grid
		List<CTableConfig> columnList = cTableConfigDAO.getColumnList("ISO_LICENSE_SOFT");
		model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
		
		String filterUrl = "";
		String temp = "";
		if(startDate != null){
			filterUrl += "startDate=" + startDate; 
			temp = "&";
		}
		if(endDate != null){
			filterUrl += temp + "endDate=" + endDate; 
			temp = "&";
		}
		if(vendor != null){
			filterUrl += temp + "vendor=" + vendor; 
			temp = "&";
		}
		if(neType != null){
			filterUrl += temp + "neType=" + neType; 
			temp = "&";
		}
		if(neName != null){
			filterUrl += temp + "neName=" + neName; 
			temp = "&";
		}
		if(featureCode != null){
			filterUrl += temp + "featureCode=" + featureCode; 
			temp = "&";
		} 
		if(featureName != null){
			filterUrl += temp + "featureName=" + featureName;
		}
		if(filterUrl != "")
			filterUrl = "?" + filterUrl;
		
		//Lay duong link url de lay du lieu do vao grid
		model.addAttribute("url", "data.htm" + filterUrl);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("vendor", vendor);
		model.addAttribute("neType", neType);
		model.addAttribute("neName", neName);
		model.addAttribute("featureCode", featureCode);
		model.addAttribute("featureName", featureName);
		
		
		return new ModelAndView("jspiso/isoLicenseSoftList");
	}
			
	@RequestMapping("/data")
	public void doGet(@RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate, 
			   @RequestParam(required = false) String vendor, 
			   @RequestParam(required = false) String neType, 
			   @RequestParam(required = false) String neName, 
			   @RequestParam(required = false) String featureCode, 
			   @RequestParam(required = false) String featureName, 
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<IsoLicenseSoft> isoLicenseSoftList = null;
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
		List<CTableConfig> columnList = cTableConfigDAO.getTableConfigGet("ISO_LICENSE_SOFT", sortfield);
		List<CTableConfig> tableConfigList = cTableConfigDAO.getTableConfigGet("ISO_LICENSE_SOFT", null);
		
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
			isoLicenseSoftList = isoLicenseSoftDAO.getIsoLicenseSoftFilter( startDate, endDate, vendor, neType, neName, featureCode, featureName, startRecord, endRecord, sortfield, sortorder, strWhere);
			totalRow = isoLicenseSoftDAO.countIsoLicenseSoftFilter(startDate, endDate, vendor, neType, neName, featureCode, featureName, strWhere);
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
		String jsonCartList = gs.toJson(isoLicenseSoftList);
		strjson+= jsonCartList;
	    strjson += "}]";
	    
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(strjson);
		out.close();
		
	}
	// Lay danh sach loai thiet bi
	 	@RequestMapping("neTypeList")
	 	public @ResponseBody 
	 	List<SYS_PARAMETER> getNeType() {
	 		List<SYS_PARAMETER> neTypeList = new ArrayList<SYS_PARAMETER>();
	 		neTypeList = sysParameterDao.getEquipmentType();
	 		
	 		return neTypeList;
	 	 }
	 // Lay danh sach vendor
	   	@RequestMapping("vendorList")
	   	public @ResponseBody 
	   	List<SYS_PARAMETER> vendorList() {
	   		List<SYS_PARAMETER> vendorForResourceList = sysParameterDao.getVendorForResourceList();
	   		
	   		return vendorForResourceList;
	   	 }
	//FB Form
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String day,
			@RequestParam(required = false) String activeDate, @RequestParam(required = false) String id, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		IsoLicenseSoft isoLicenseSoft = (id == null ) ? new IsoLicenseSoft() : isoLicenseSoftDAO.selectById(Integer.parseInt(id));
		model.addAttribute("isoLicenseSoft", isoLicenseSoft);
		
		isoLicenseSoftAddEdit(id, model);
		
		List<SYS_PARAMETER> statusLicenseSoftList = sysParameterDao.getStatusIsoLicenseSoft();
		model.addAttribute("statusLicenseSoftList", statusLicenseSoftList);
		
		List<SYS_PARAMETER> vendorForResourceList = sysParameterDao.getVendorForResourceList();
		model.addAttribute("vendorForResourceList", vendorForResourceList);
		
		if(id == null)
		{
			// Ngay thang
			if (day == null || day.equals("") || DateTools.isValid("dd/MM/yyyy", day)==false) {
				day = df.format(new Date());
			}
			model.addAttribute("day", day);
		}
		else
		{
			if(isoLicenseSoft.getDay() != null)
				day = df.format(isoLicenseSoft.getDay());
			model.addAttribute("day", day);
			if(isoLicenseSoft.getActiveDate() != null)
				activeDate = df.format(isoLicenseSoft.getActiveDate());
			model.addAttribute("activeDate", activeDate);
			model.addAttribute("vendorCBB", isoLicenseSoft.getVendor());
			model.addAttribute("statusCBB", isoLicenseSoft.getStatus());
		}
		
		return "jspiso/isoLicenseSoftForm";
	}
	
	private void isoLicenseSoftAddEdit(String id, ModelMap model)
	{
		if(id != null && id != "")
			model.addAttribute("isoLicenseSoftAddEdit", "Y");
		else
			model.addAttribute("isoLicenseSoftAddEdit", "N");
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id,
			@RequestParam(required = false) String day, 
			@RequestParam(required = false) String activeDate, @ModelAttribute("isoLicenseSoft") @Valid IsoLicenseSoft isoLicenseSoft, BindingResult result, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		if (day == null || day.equals("") || DateTools.isValid("dd/MM/yyyy", day)==false)
		{
			isoLicenseSoftAddEdit(id, model);
			List<SYS_PARAMETER> statusLicenseSoftList = sysParameterDao.getStatusIsoLicenseSoft();
			model.addAttribute("statusLicenseSoftList", statusLicenseSoftList);
			List<SYS_PARAMETER> vendorForResourceList = sysParameterDao.getVendorForResourceList();
			model.addAttribute("vendorForResourceList", vendorForResourceList);
			model.addAttribute("errorDay", "*");
			model.addAttribute("day", day);
			model.addAttribute("activeDate", activeDate);
			model.addAttribute("vendorCBB", isoLicenseSoft.getVendor());
			model.addAttribute("statusCBB", isoLicenseSoft.getStatus());
			return "jspiso/isoLicenseSoftForm";
		}
		
		//Ném lỗi
		if (result.hasErrors()) {
			isoLicenseSoftAddEdit(id, model);
			List<SYS_PARAMETER> statusLicenseSoftList = sysParameterDao.getStatusIsoLicenseSoft();
			model.addAttribute("statusLicenseSoftList", statusLicenseSoftList);
			List<SYS_PARAMETER> vendorForResourceList = sysParameterDao.getVendorForResourceList();
			model.addAttribute("vendorForResourceList", vendorForResourceList);
			if(result.hasFieldErrors("day"))
				model.addAttribute("dayError", "dayError");
			if(result.hasFieldErrors("activeDate"))
				model.addAttribute("activeDateError", "activeDateError");
			if(result.hasFieldErrors("capacity"))
				model.addAttribute("capacityError", "capacityError");
			if(result.hasFieldErrors("usage"))
				model.addAttribute("usageError", "usageError");
			model.addAttribute("day", day);
			model.addAttribute("activeDate", activeDate);
			model.addAttribute("vendorCBB", isoLicenseSoft.getVendor());
			model.addAttribute("statusCBB", isoLicenseSoft.getStatus());
			return "jspiso/isoLicenseSoftForm";
		}
		
		if (activeDate != null && !activeDate.equals("") && DateTools.isValid("dd/MM/yyyy", activeDate)==false)
		{
			isoLicenseSoftAddEdit(id, model);
			List<SYS_PARAMETER> statusLicenseSoftList = sysParameterDao.getStatusIsoLicenseSoft();
			model.addAttribute("statusLicenseSoftList", statusLicenseSoftList);
			List<SYS_PARAMETER> vendorForResourceList = sysParameterDao.getVendorForResourceList();
			model.addAttribute("vendorForResourceList", vendorForResourceList);
			model.addAttribute("errorDay", "*");
			model.addAttribute("day", day);
			model.addAttribute("activeDate", activeDate);
			model.addAttribute("vendorCBB", isoLicenseSoft.getVendor());
			model.addAttribute("statusCBB", isoLicenseSoft.getStatus());
			return "jspiso/isoLicenseSoftForm";
		}
		
		if(id == "")
		{
			if(isoLicenseSoftDAO.getUpdateIsoLicenseSoft(day, isoLicenseSoft.getVendor(), isoLicenseSoft.getNe(), isoLicenseSoft.getFeatureCode(), null).size() > 0){
				saveMessageKey(request, "message.isoLicenseSoft.errorAdd");
				isoLicenseSoftAddEdit(id, model);
				List<SYS_PARAMETER> statusLicenseSoftList = sysParameterDao.getStatusIsoLicenseSoft();
				model.addAttribute("statusLicenseSoftList", statusLicenseSoftList);
				List<SYS_PARAMETER> vendorForResourceList = sysParameterDao.getVendorForResourceList();
				model.addAttribute("vendorForResourceList", vendorForResourceList);
				model.addAttribute("day", day);
				model.addAttribute("activeDate", activeDate);
				model.addAttribute("vendorCBB", isoLicenseSoft.getVendor());
				model.addAttribute("statusCBB", isoLicenseSoft.getStatus());
				return "jspiso/isoLicenseSoftForm";
			}
			else{
				isoLicenseSoft.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				isoLicenseSoftDAO.insert(isoLicenseSoft);
				saveMessageKey(request, "messsage.confirm.addsuccess");
			}
		}
		else{
			if(isoLicenseSoftDAO.getUpdateIsoLicenseSoft(day, isoLicenseSoft.getVendor(), isoLicenseSoft.getNe(), isoLicenseSoft.getFeatureCode(), id).size() > 0){
				saveMessageKey(request, "message.isoLicenseSoft.errorAdd");
				isoLicenseSoftAddEdit(id, model);
				List<SYS_PARAMETER> statusLicenseSoftList = sysParameterDao.getStatusIsoLicenseSoft();
				model.addAttribute("statusLicenseSoftList", statusLicenseSoftList);
				List<SYS_PARAMETER> vendorForResourceList = sysParameterDao.getVendorForResourceList();
				model.addAttribute("vendorForResourceList", vendorForResourceList);
				model.addAttribute("day", day);
				model.addAttribute("activeDate", activeDate);
				model.addAttribute("vendorCBB", isoLicenseSoft.getVendor());
				model.addAttribute("statusCBB", isoLicenseSoft.getStatus());
				return "jspiso/isoLicenseSoftForm";
			}
			else{
				isoLicenseSoft.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				isoLicenseSoftDAO.updateById(isoLicenseSoft);
				saveMessageKey(request, "messsage.confirm.updatesuccess");
			}
		}
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) Integer id, HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
			isoLicenseSoftDAO.deleteById(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch(Exception e)
		{
			saveMessageKey(request, "message.confirm.deleteOther");
			}
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		return "jspiso/isoLicenseSoftUpload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile filePath,  ModelMap model, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		
		if (!filePath.isEmpty()) {

			String[] ten=filePath.getOriginalFilename().split("\\.");
			String fileExtn = ten[ten.length-1];
			
			if (fileExtn.equals("xls")) {
				if (fileExtn.equals("xls")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsFile(filePath.getInputStream());
					
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
		
		return "jspiso/isoLicenseSoftUpload";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent(List sheetData, ModelMap model,HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<IsoLicenseSoft> successList = new ArrayList<IsoLicenseSoft>();
		List<IsoLicenseSoft> failedList = new ArrayList<IsoLicenseSoft>();
		List<IsoLicenseSoft> success = new ArrayList<IsoLicenseSoft>();
		
		String day;
		String vendor;
		String neType;
		String ne;
		String featureCode;
		String licenseCode;
		String featureName;
		String status;
		String activeDate;
		String capacity;
		String usage;
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 11) {
        		saveMessageKey(request, "sidebar.admin.isoLicenseSoftUploadErrorStructuresNumber");
        		
        		return "jspiso/isoLicenseSoftUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		IsoLicenseSoft isoLicenseSoft;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			isoLicenseSoft = new IsoLicenseSoft();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=11; j++) {
     					data.add("");
     				}
        			day					= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			vendor				= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			neType				= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			ne					= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			featureCode			= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			licenseCode			= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			featureName			= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			status				= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			activeDate			= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			capacity			= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			usage				= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
        			
        			// Kiem tra loi
        			if (day == null || vendor == null || ne == null || featureCode == null
        					|| (vendor != null && vendor.length() > 20)
        					|| (neType != null && neType.length() > 20)
        					|| (ne != null && ne.length() > 20)
							|| (featureCode != null && featureCode.length() > 20)
							|| (licenseCode != null && licenseCode.length() > 50)
							|| (featureName != null && featureName.length() > 250)
							|| (status != null && status.length() > 50)
							|| (capacity != null && capacity.length() > 6)
							|| (usage != null && usage.length() > 6)
							|| (status != null && !status.toString().toLowerCase().equals("on") && !status.toString().toLowerCase().equals("off"))
							|| (vendor != null && !vendor.toString().toLowerCase().equals("alcatel") && !status.toString().toLowerCase().equals("huawei")
									&& !vendor.toString().toLowerCase().equals("ericsson") && !vendor.toString().toLowerCase().equals("nokia siemens"))
						) {
						error = true;
					}
        			
        			try{
	        			if(capacity != null){
		        			Integer a = Integer.parseInt(capacity);
		        			isoLicenseSoft.setCapacity(a);
		        			System.out.println(a);
	        			}
	        			
	        			if(usage != null){
		        			Integer b = Integer.parseInt(usage);
		        			isoLicenseSoft.setUsage(b);
		        			System.out.println(b);
	        			}
	        		}
	        		catch(Exception e){
	        			error = true;
	        		}
        			
        			try
	    			{
        				if(day != null){
		    				Date simple = new SimpleDateFormat("dd/MM/yyyy").parse(day);
		    				isoLicenseSoft.setDay(new SimpleDateFormat("dd/MM/yyyy").parse(day));
		    				System.out.println(simple);
	    				}
        				
        				if(activeDate != null){
		    				Date simple = new SimpleDateFormat("dd/MM/yyyy").parse(activeDate);
		    				isoLicenseSoft.setActiveDate(new SimpleDateFormat("dd/MM/yyyy").parse(activeDate));
		    				System.out.println(simple);
	    				}
        				
	    			}
	    			catch(Exception e)
	    			{
	    				error = true;
	    			}
        			
        			//---------------------------------------------------------------------------
        			if(vendor != null)
        				vendor = vendor.toString().toUpperCase();
        			isoLicenseSoft.setVendor(vendor);
        			isoLicenseSoft.setNeType(neType);
        			isoLicenseSoft.setNe(ne);
        			isoLicenseSoft.setFeatureCode(featureCode);
        			isoLicenseSoft.setLicenseCode(licenseCode);
        			isoLicenseSoft.setFeatureName(featureName);
        			if(status != null)
        				status = status.toString().toUpperCase();
        			isoLicenseSoft.setStatus(status);
        			
        			if ( day == null && vendor == null && ne == null && featureCode == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
        					
    						failedList.add(isoLicenseSoft);
    					} else  {
    						
    						successList.add(isoLicenseSoft);
    					}
        			}
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (IsoLicenseSoft record: successList) {
			try {
				if(isoLicenseSoftDAO.getUpdateIsoLicenseSoft(df.format(record.getDay()), record.getVendor(), record.getNe(), record.getFeatureCode(), null).size() > 0)
				{
					isoLicenseSoftDAO.updateByPrimaryKeySelective(record);
				}else{
					record.setCreatedBy(username);
					isoLicenseSoftDAO.insertSelective(record);
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
			model.addAttribute("status", "Dữ liệu quản lý bản quyền mềm không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");	// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", success.size());
		model.addAttribute("totalNum", failedList.size()+success.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		return "jspiso/isoLicenseSoftUpload";
	}
	
	@RequestMapping("exportLicenseSoft")
  	public ModelAndView reportAlarmLog(@RequestParam(required = false) String startDate,
  			@RequestParam(required = false) String endDate,
  			@RequestParam(required = false) String vendor, 
		    @RequestParam(required = false) String neType, 
		    @RequestParam(required = false) String neName, 
		    @RequestParam(required = false) String featureCode, 
		    @RequestParam(required = false) String featureName,
  			@RequestParam(required = false) String sortfield,
  			@RequestParam(required = false) String sortorder,
  			@RequestParam(required = false) String strWhere,
  			HttpServletRequest request, HttpServletResponse response) throws Exception {
  		strWhere = strWhere.replace("___", "%");
  		if(featureCode != null)
  			featureCode = featureCode.trim();
  		
  		// temp file
		String basePath = request.getSession().getServletContext().getRealPath("");
		String dataDir = basePath + "/upload";
		String tempName = UUID.randomUUID().toString();
		String ext = "xls";
		String outfile = tempName + "." + ext;
		File tempFile = new File(dataDir + "/" + outfile);
		
		List<IsoLicenseSoft> isoLicenseSoftList = isoLicenseSoftDAO.getIsoLicenseSoftFilter( startDate, endDate,  vendor, neType, neName, featureCode, featureName, null, null, sortfield, sortorder, strWhere);
		
		exportReport(tempFile, isoLicenseSoftList);
		
		
		// return
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String filename = "LicenseSoft_"+ dateNow;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();
  		return null;
  	}
	
	private void exportReport(File tempFile, List<IsoLicenseSoft> isoLicenseSoftList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("License Soft", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			int i=0;
			
			Label label0 = new Label(i, 0, "Date", cellFormatHeader);
			sheet.addCell(label0);
			i++;
			Label label1 = new Label(i, 0, "Vendor", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "NE Type", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "NE Name", cellFormatHeader);
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "Feature Code", cellFormatHeader);
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "License Code", cellFormatHeader);
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "Feature Name", cellFormatHeader);
			sheet.addCell(label6);
			i++;
			Label label7 = new Label(i, 0, "Status", cellFormatHeader);
			sheet.addCell(label7);
			i++;
			Label label8 = new Label(i, 0, "Active Date", cellFormatHeader);
			sheet.addCell(label8);
			i++;
			Label label9 = new Label(i, 0, "Capacity", cellFormatHeader);
			sheet.addCell(label9);
			i++;
			Label label10 = new Label(i, 0, "Usage", cellFormatHeader);
			sheet.addCell(label10);
			
			i = 1;
			
			for (IsoLicenseSoft record : isoLicenseSoftList) {
				int j=0;
				Label record0 = new Label(j, i, record.getDayStr(), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,record.getVendor(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getNeType(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getNe(), cellFormat);
				sheet.addCell(record3);
				j++;
				Label record4 = new Label(j, i, record.getFeatureCode(), cellFormat);
				sheet.addCell(record4);
				j++;
				Label record5 = new Label(j, i, record.getLicenseCode(), cellFormat);
				sheet.addCell(record5);
				j++;
				Label record6 = new Label(j, i, record.getFeatureName(), cellFormat);
				sheet.addCell(record6);
				j++;
				Label record7 = new Label(j, i, record.getStatus(), cellFormat);
				sheet.addCell(record7);
				j++;
				Label record8 = new Label(j, i, record.getActiveDateStr(), cellFormat);
				sheet.addCell(record8);
				j++;
				Label record9 = new Label(j, i, record.getCapacityStr(), cellFormat);
				sheet.addCell(record9);
				j++;
				Label record10 = new Label(j, i, record.getUsageStr(), cellFormat);
				sheet.addCell(record10);
				
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
