package controller.assets;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import controller.BaseController;

import vo.AsCards;
import vo.AsProposedWarehouse;
import vo.AssetsManagements;
import vo.AssetsTypes;
import vo.CHighlightConfigs;
import vo.SYS_PARAMETER;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.UploadTools;

import dao.AsCardsDAO;
import dao.AsProposedWarehouseDAO;
import dao.AssetsManagementsDAO;
import dao.AssetsTypesDAO;
import dao.AssetsWarrantyDAO;
import dao.CHighlightConfigsDAO;
import dao.SYS_PARAMETERDAO;

/**
 * Ten file: AsCardsController.java
 * Muc dich: Quan ly card
 * @author QBu
 * Ngay tao: 10/7/2013
 * Lich su thay doi:
 * 
 */

@Controller
@RequestMapping("/assets/quan-ly-card/*")
public class AsCardsController extends BaseController{

	@Autowired
	private AsCardsDAO asCardsDAO;
	@Autowired
	private AssetsTypesDAO assetsTypesDAO;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
	private AssetsWarrantyDAO assetsWarrantyDAO;
	@Autowired
	private AssetsManagementsDAO assetsManagementDao;
	@Autowired
	private AsProposedWarehouseDAO asProposedWarehouseDAO;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat df_little = new SimpleDateFormat("HH:mm");
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
	public ModelAndView list(@RequestParam(required = false) String asTypesId,
							 @RequestParam(required = false) String importDateFrom,
							 @RequestParam(required = false) String importDateTo,
							 @RequestParam(required = false) String exportDateFrom,
							 @RequestParam(required = false) String exportDateTo,
							 @RequestParam(required = false) String messagesXuatKho,
							 @ModelAttribute("filter") AsCards filter, ModelMap model, HttpServletRequest request, HttpServletResponse response){
		if(filter.getProductCode() != null)
			filter.setProductCode(filter.getProductCode().trim());
		if(filter.getProductName() != null)
			filter.setProductName(filter.getProductName().trim());
		if(filter.getSerialNo() != null)
			filter.setSerialNo(filter.getSerialNo().trim());
		if(filter.getJect() != null)
			filter.setJect(filter.getJect().trim());
		if(filter.getVendor() != null)
			filter.setVendor(filter.getVendor().trim());
		if(filter.getOrganization() != null)
			filter.setOrganization(filter.getOrganization().trim());
		if(filter.getDepartmentId() != null)
			filter.setDepartmentId(filter.getDepartmentId().trim());
		if(filter.getVotesNo() != null)
			filter.setVotesNo(filter.getVotesNo().trim());
		if(filter.getUsersEx() != null)
			filter.setUsersEx(filter.getUsersEx().trim());
		if(filter.getWarehourse() != null)
			filter.setWarehourse(filter.getWarehourse().trim());
		
		List<AssetsTypes> asTypesIDList = assetsTypesDAO.getAssetsTypesList();
 		model.addAttribute("asTypesIDList", asTypesIDList);
 		model.addAttribute("asTypesIDCBB", asTypesId);
 		
		// Ngay thang
 		if (importDateFrom != null && !importDateFrom.equals("") && DateTools.isValid("dd/MM/yyyy", importDateFrom)==false) {			

			importDateFrom = df.format(DateTools.startMonth(new Date()));
		
		}
 		if (importDateTo != null && !importDateTo.equals("") && DateTools.isValid("dd/MM/yyyy", importDateTo)==false)
 		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			importDateTo = df.format(cal.getTime());
		}
 		if (exportDateFrom != null && !exportDateFrom.equals("") && DateTools.isValid("dd/MM/yyyy", exportDateFrom)==false) {			

 			exportDateFrom = df.format(DateTools.startMonth(new Date()));
		
		}
 		if (exportDateTo != null && !exportDateTo.equals("") && DateTools.isValid("dd/MM/yyyy", exportDateTo)==false)
 		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			exportDateTo = df.format(cal.getTime());
		}
 		
		int order = 1;
		String column = "STT";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
				
		List<AsCards> asCardsList = asCardsDAO.getAsCardsFilter(asTypesId, 
				filter.getProductCode(), filter.getSerialNo(), filter.getProductName(), 
				importDateFrom, importDateTo, filter.getJect(), filter.getVendor(), 
				filter.getOrganization(), filter.getDepartmentId(), exportDateFrom, exportDateTo, 
				filter.getVotesNo(), filter.getUsersEx(), filter.getWarehourse(), column, order==1?"ASC":"DESC");
		model.addAttribute("asCardsList", asCardsList);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "NhapTaiSan_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		model.addAttribute("importDateFrom", importDateFrom);
		model.addAttribute("importDateTo", importDateTo);
		model.addAttribute("exportDateFrom", exportDateFrom);
		model.addAttribute("exportDateTo", exportDateTo);
		model.addAttribute("messagesXuatKho", messagesXuatKho);
		
		return new ModelAndView("jspassets/asCards/asCardsList");
	}
	
	//FB Form
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		AsCards asCards = (id == null ) ? new AsCards() : asCardsDAO.selectByPrimaryKey(Integer.parseInt(id));
		model.addAttribute("asCards", asCards);
		
		asCardsAddEdit(id, model);
		if(id == null)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			model.addAttribute("importDate", df.format(cal.getTime()));
			
		}
		else{
			
			model.addAttribute("asTypesIDCBB", asCards.getAsTypesId());
			if(asCards.getImportDate() != null)
				model.addAttribute("importDate", df.format(asCards.getImportDate()));
			if(asCards.getExportDate() != null)
				model.addAttribute("exportDate", df.format(asCards.getExportDate()));
			if(asCards.getProduceDate() != null)
				model.addAttribute("produceDate", df.format(asCards.getProduceDate()));
			if(asCards.getDateReturn() != null)
				model.addAttribute("dateReturn", df.format(asCards.getDateReturn()));
			if(asCards.getWarrantyDate() != null)
				model.addAttribute("warrantyDate", df.format(asCards.getWarrantyDate()));
			
			model.addAttribute("unitCBB", asCards.getUnit());
			model.addAttribute("warehourseCBB", asCards.getWarehourse());
		}
		
		List<AssetsTypes> asTypesIDList = assetsTypesDAO.getAssetsTypesList();
 		model.addAttribute("asTypesIDList", asTypesIDList);
 		String asTypesId = "";
 		if(asTypesIDList.size() > 0)
 			asTypesId = asTypesIDList.get(0).getAsTypesId();
 		List<AssetsManagements> assetsManagementList = assetsWarrantyDAO.getAssetsManagementsList(asTypesId);
 		model.addAttribute("assetsManagementList", assetsManagementList);
 		
 		List<SYS_PARAMETER> unitList = sysParameterDao.getAsImportWarehouseUnit();
 		model.addAttribute("unitList", unitList);
 		
 		List<AssetsTypes> warehourseList = assetsTypesDAO.getDistinctWarehourse();
 		model.addAttribute("warehourseList", warehourseList);
		
		return "jspassets/asCards/asCardsForm";
	}
	
	private void asCardsAddEdit(String id, ModelMap model)
	{
		if(id != null && id != "")
			model.addAttribute("asCardsAddEdit", "Y");
		else
			model.addAttribute("asCardsAddEdit", "N");
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id,
						   @RequestParam(required = false) String asTypesId,
						   @RequestParam(required = false) String importDate,
						   @RequestParam(required = false) String exportDate,
						   @RequestParam(required = false) String produceDate,
						   @RequestParam(required = false) String dateReturn,
						   @RequestParam(required = false) String warrantyDate, @ModelAttribute("asCards") @Valid AsCards asCards, BindingResult result, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		//Ném lỗi
		if (result.hasErrors()) {
			asCardsAddEdit(id, model);
			loadAgainForm(model, asTypesId, importDate, exportDate, produceDate, dateReturn, warrantyDate, asCards);
	 		
	 		if(result.hasFieldErrors("importDate"))
				model.addAttribute("importDateError", "importDateError");
	 		
	 		if(result.hasFieldErrors("exportDate"))
				model.addAttribute("exportDateError", "exportDateError");
	 		
	 		if(result.hasFieldErrors("produceDate"))
				model.addAttribute("produceDateError", "produceDateError");
	 		
	 		if(result.hasFieldErrors("dateReturn"))
				model.addAttribute("dateReturnError", "dateReturnError");
	 		
	 		if(result.hasFieldErrors("warrantyDate"))
				model.addAttribute("warrantyDateError", "warrantyDateError");
	 		
			return "jspassets/asCards/asCardsForm";
		}
		
		/*if (importDate == null || importDate.equals("") || DateTools.isValid("dd/MM/yyyy", importDate)==false)
		{
			asCardsAddEdit(id, model);
			loadAgainForm(model,asTypesId,importDate,produceDate,asCards);
			model.addAttribute("errorImportDate", "*");
			
			return "jspassets/asCards/asCardsForm";
		}*/
		
		if(id == "")
		{
			if(assetsManagementDao.selectByProductCode(asCards.getProductCode()) == null)
			{
				AssetsManagements record = new AssetsManagements();
				record.setProductCode(asCards.getProductCode());
				record.setAssetsName(asCards.getProductName());
				record.setUnit(asCards.getUnit());
				record.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				record.setAsTypesId(asTypesId);
				assetsManagementDao.insert(record);
			}else{
				assetsManagementDao.updateProName(asCards.getProductCode(),asCards.getProductName(), asCards.getUnit(), SecurityContextHolder.getContext().getAuthentication().getName());
			}
			if(asCardsDAO.getAsCardsByCodeSerial(asCards.getProductCode(), asCards.getSerialNo(), null).size() > 0){
				asCardsAddEdit(id, model);
				loadAgainForm(model, asTypesId, importDate, exportDate, produceDate, dateReturn, warrantyDate, asCards);
				
				saveMessageKey(request, "sidebar.admin.errorProductCodeAndSerialNo");
				return "jspassets/asCards/asCardsForm";
			}
			else{
				asCards.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				asCardsDAO.insert(asCards);
				saveMessageKey(request, "messsage.confirm.addsuccess");
			}
			
		}
		else{
			if(assetsManagementDao.selectByProductCode(asCards.getProductCode()) == null)
			{
				AssetsManagements record = new AssetsManagements();
				record.setProductCode(asCards.getProductCode());
				record.setAssetsName(asCards.getProductName());
				record.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				record.setAsTypesId(asTypesId);
				assetsManagementDao.insert(record);
			}else{
				assetsManagementDao.updateProName(asCards.getProductCode(),asCards.getProductName(), asCards.getUnit(), SecurityContextHolder.getContext().getAuthentication().getName());
			}
			
			if(asCardsDAO.getAsCardsByCodeSerial(asCards.getProductCode(), asCards.getSerialNo(), id).size() > 0){
				asCardsAddEdit(id, model);
				loadAgainForm(model, asTypesId, importDate, exportDate, produceDate, dateReturn, warrantyDate, asCards);
				
				saveMessageKey(request, "sidebar.admin.errorProductCodeAndSerialNo");
				return "jspassets/asCards/asCardsForm";
			}
			else{
				asCards.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				asCardsDAO.updateByPrimaryKey(asCards);
				saveMessageKey(request, "messsage.confirm.updatesuccess");
			}
		}
		
		
		return "redirect:list.htm";
	}
	
	private void loadAgainForm(ModelMap model,String asTypesId, String importDate, String exportDate, String produceDate,
			String dateReturn, String warrantyDate, AsCards asCards){
		List<AssetsManagements> assetsManagementList = assetsWarrantyDAO.getAssetsManagementsList(asTypesId);
 		model.addAttribute("assetsManagementList", assetsManagementList);
 		
 		List<AssetsTypes> asTypesIDList = assetsTypesDAO.getAssetsTypesList();
 		model.addAttribute("asTypesIDList", asTypesIDList);
 		
 		List<SYS_PARAMETER> unitList = sysParameterDao.getAsImportWarehouseUnit();
 		model.addAttribute("unitList", unitList);
 		
 		List<AssetsTypes> warehourseList = assetsTypesDAO.getDistinctWarehourse();
 		model.addAttribute("warehourseList", warehourseList);
 		
 		model.addAttribute("importDate", importDate);
 		model.addAttribute("exportDate", exportDate);
 		model.addAttribute("produceDate", produceDate);
 		model.addAttribute("dateReturn", dateReturn);
 		model.addAttribute("warrantyDate", warrantyDate);
 		model.addAttribute("unitCBB", asCards.getUnit());
 		model.addAttribute("warehourseCBB", asCards.getWarehourse());
 		model.addAttribute("asTypesIDCBB", asTypesId);
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) Integer id, HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
				asCardsDAO.deleteByPrimaryKey(id);
				saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch(Exception e)
		{
			saveMessageKey(request, "message.confirm.deleteOther");
			}
		
		return "redirect:list.htm";
	}
	
	
	@RequestMapping(value = "xuatKhoCards", method = RequestMethod.GET)
	public String xuatKhoCards(@RequestParam (required = true) Integer id, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
			AsCards count = asCardsDAO.getAmountDontUse(id.toString());
			int temp = count.getAmount();
			if(temp > 0){
				
				AsProposedWarehouse record = new AsProposedWarehouse();
				
				record.setProductCode(count.getProductCode());
				record.setProductName(count.getProductName());
				record.setSerialNo(count.getSerialNo());
				record.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				record.setSoLuong(1);
				record.setDonVi(count.getUnit());
				record.setNhaSx(count.getVendor());
				
				asProposedWarehouseDAO.insert(record);
				saveMessageKey(request, "message.confirm.xuatKhoThanhCong");
			}
			else{
				
				model.addAttribute("messagesXuatKho", "Y");
			}
			
		}
		catch(Exception e)
		{
			saveMessageKey(request, "message.confirm.xuatKhoThatBai");
			}
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		List<AssetsTypes> warehourseList = assetsTypesDAO.getDistinctWarehourse();
 		model.addAttribute("warehourseList", warehourseList);
		
		return "jspassets/asCards/asCardsUpload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam (value = "warehourse", required = true) String warehourse, @RequestParam("file") MultipartFile filePath,  ModelMap model, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		
		if (!filePath.isEmpty()) {

			String[] ten=filePath.getOriginalFilename().split("\\.");
			String fileExtn = ten[ten.length-1];
			
			if (fileExtn.equals("xls")) {
				if (fileExtn.equals("xls")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsFileForAssets(filePath.getInputStream());
					
					importContent(sheetData, warehourse, model, request);
					
				}
			}
			else {
				 saveMessageKey(request, "cautruc.typeFIle");
			}
		}
		else {
			saveMessageKey(request, "cautruc.emptyFile");
		}
		
		List<AssetsTypes> warehourseList = assetsTypesDAO.getDistinctWarehourse();
 		model.addAttribute("warehourseList", warehourseList);
 		model.addAttribute("warehourseCBB", warehourse);
		
		return "jspassets/asCards/asCardsUpload";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent(List sheetData, String warehourse, ModelMap model,HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<AsCards> successList = new ArrayList<AsCards>();
		List<AsCards> failedList = new ArrayList<AsCards>();
		List<AsCards> success = new ArrayList<AsCards>();
		
		String stt;
		String productName;
		String productCode;
		String serialNo;
		String amount;
		String importDate;
		String exportDate;
		String ject;
		String departmentId;
		String description;
		String votesNo;
		String dateReturn;
		String vendor;
		String warrantyDate;
		String asTypesId;
		String vendorSheet;
		String unit = null;
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 16) {
        		saveMessageKey(request, "sidebar.admin.asCardsUploadErrorStructuresNumber");
        		
        		return "jspassets/asCards/asCardsUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		AsCards cableTransmission;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			cableTransmission = new AsCards();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=16; j++) {
     					data.add("");
     				}
        			stt					= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			productName			= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			productCode			= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			serialNo			= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			amount				= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			importDate			= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			exportDate			= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			ject				= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			departmentId		= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			description			= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			votesNo				= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
        			dateReturn			= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim();
        			vendor				= data.get(12).toString().trim().equals("")?null:data.get(12).toString().trim();
        			warrantyDate		= data.get(13).toString().trim().equals("")?null:data.get(13).toString().trim();
        			asTypesId			= data.get(14).toString().trim().equals("")?null:data.get(14).toString().trim();
        			vendorSheet			= data.get(15).toString().trim().equals("")?null:data.get(15).toString().trim();
        			
        			
        			// Kiem tra loi
        			if (asTypesId == null || productCode == null || amount == null || serialNo == null
        					|| (asTypesId != null && asTypesId.length() > 40)
        					|| (productCode != null && productCode.length() > 40)
        					|| (serialNo != null && serialNo.length() > 40)
        					|| (serialNo != null &&  asCardsDAO.getAsCardsByCodeSerial(productCode, serialNo, null).size() > 0)
							|| (amount != null && amount.length() > 4)
							|| (description != null && description.length() > 900)
							|| (ject != null && ject.length() > 500)
							|| (votesNo != null && votesNo.length() > 90)
							|| (departmentId != null && departmentId.length() > 90)
							|| (vendor != null && vendor.length() > 240)
							|| (warehourse != null && warehourse.length() > 40)
							
						) {
						error = true;
					}
        			
        			try{
	        			if(amount != null){
		        			Integer a = Integer.parseInt(amount);
		        			cableTransmission.setAmount(a);
		        			System.out.println(a);
	        			}
	        			
	        			if(stt != null){
		        			Integer b = Integer.parseInt(stt);
		        			cableTransmission.setStt(b);
		        			System.out.println(b);
	        			}
	        		}
	        		catch(Exception e){
	        			error = true;
	        		}
        			
        			try
	    			{
        				if(importDate != null){
		    				Date simple = new SimpleDateFormat("dd/MM/yyyy").parse(importDate);
		    				cableTransmission.setImportDate(new SimpleDateFormat("dd/MM/yyyy").parse(importDate));
		    				System.out.println(simple);
	    				}
        				
        				if(exportDate != null){
		    				Date simple = new SimpleDateFormat("dd/MM/yyyy").parse(exportDate);
		    				cableTransmission.setExportDate(new SimpleDateFormat("dd/MM/yyyy").parse(exportDate));
		    				System.out.println(simple);
	    				}
        				
        				if(dateReturn != null){
		    				Date simple = new SimpleDateFormat("dd/MM/yyyy").parse(dateReturn);
		    				cableTransmission.setDateReturn(new SimpleDateFormat("dd/MM/yyyy").parse(dateReturn));
		    				System.out.println(simple);
	    				}
        				
        				if(warrantyDate != null){
		    				Date simple = new SimpleDateFormat("dd/MM/yyyy").parse(warrantyDate);
		    				cableTransmission.setWarrantyDate(new SimpleDateFormat("dd/MM/yyyy").parse(warrantyDate));
		    				System.out.println(simple);
	    				}
	    			}
	    			catch(Exception e)
	    			{
	    				error = true;
	    			}
        			
        			
    				AssetsManagements assetsManagement = assetsManagementDao.selectByProductCode(productCode);
    				if(assetsManagement != null){
    					productName = assetsManagement.getAssetsName();
    					unit = assetsManagement.getUnit();
    				}
        			
        			if(productName== null || (productName != null && productName.length() > 240))
        				error = true;
        			//---------------------------------------------------------------------------
    				cableTransmission.setAsTypesId(asTypesId);
        			cableTransmission.setProductName(productName);
        			cableTransmission.setProductCode(productCode);
        			cableTransmission.setSerialNo(serialNo);
        			cableTransmission.setUnit(unit);
        			if(vendor != null && vendor.length() > 0)
        				cableTransmission.setVendor(vendor);
        			else
        				cableTransmission.setVendor(vendorSheet);
        			
        			cableTransmission.setDescription(description);
        			cableTransmission.setJect(ject);
        			cableTransmission.setDepartmentId(departmentId);
        			cableTransmission.setWarehourse(warehourse);
        			
        			if ( asTypesId == null && productCode == null && productName == null && amount == null && serialNo == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
        					
    						failedList.add(cableTransmission);
    					} else  {
    						
    						successList.add(cableTransmission);
    					}
        			}
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (AsCards record: successList) {
			try {
				
					if(assetsTypesDAO.selectByPrimaryKey(record.getAsTypesId()) == null){
						AssetsTypes assetsTypes = new AssetsTypes();
						assetsTypes.setAsTypesId(record.getAsTypesId());
						assetsTypes.setWarehourse(warehourse);
						assetsTypesDAO.insert(assetsTypes);
					}
					
					if(assetsManagementDao.selectByProductCode(record.getProductCode()) == null)
					{
						AssetsManagements assetsManagements = new AssetsManagements();
						assetsManagements.setProductCode(record.getProductCode());
						assetsManagements.setAssetsName(record.getProductName());
						assetsManagements.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
						assetsManagements.setAsTypesId(record.getAsTypesId());
						assetsManagements.setUnit(record.getUnit());
						assetsManagementDao.insert(assetsManagements);
					}
					
					if(asCardsDAO.getAsCardsByCodeSerial(record.getProductCode(), record.getSerialNo(), null).size() > 0)
						failedList.add(record);
					else{
						record.setCreatedBy(username);
						asCardsDAO.insertSelective(record);
						
						success.add(record);
					}
			} catch (Exception ex) {
				
			}
		}
		
		if (failedList.size() == 0 && successList.size() > 0) {
			model.addAttribute("status", "Upload thành công.");				// Upload thành công
		} else if (failedList.size() == 0 && successList.size() == 0) {
			model.addAttribute("status", "Không có dữ liệu.");				// Không có dữ liệu
		} else {
			model.addAttribute("status", "Dữ liệu quản lý thiết bị không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");	// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", success.size());
		model.addAttribute("totalNum", failedList.size()+success.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		return "jspassets/asCards/asCardsUpload";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "inPhieuDocx", method = RequestMethod.GET)
	public ModelAndView xetDuyetDocx(
			@RequestParam(required = false) String asTypesId, 
			@RequestParam(required = false) String productCode, 
			@RequestParam(required = false) String productName, 
			@RequestParam(required = false) String serialNo,
			@RequestParam(required = false) String vendor, 
			@RequestParam(required = false) String boPhanSd, 
			@RequestParam(required = false) String donViSd,
			@RequestParam(required = false) String sophieu,
			@RequestParam(required = false) String importDateFrom,
			@RequestParam(required = false) String importDateTo,
			@RequestParam(required = false) String usersEx,
			@RequestParam(required = false) String ject,
			@RequestParam(required = false) String exportDateFrom,
			@RequestParam(required = false) String exportDateTo,
			@RequestParam(required = false) String warehourse,
			ModelMap model, HttpServletRequest request,HttpServletResponse response) throws JRException, IOException {
		
		
		List<AsCards> asCards = asCardsDAO.getAsCardsFilter(asTypesId, productCode, serialNo, productName, 
				importDateFrom, importDateTo, ject, vendor,
				boPhanSd, donViSd, exportDateFrom, exportDateTo, sophieu, usersEx, warehourse,
				null, null);
		

		String dateNow = df.format(new Date());
		String hour = df_little.format(new Date());
		InputStream inputStream = new FileInputStream (request.getSession().getServletContext().getRealPath("/report/xuatKhoDDH.jrxml")); 
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(asCards);
		
		Map parameters = new HashMap();
		parameters.put("dateNow", dateNow);
		
		if(asCards.size() > 0){
			parameters.put("soPhieu", asCards.get(0).getVotesNo());
			parameters.put("nguoiThucHien", asCards.get(0).getUsersEx()); 
			parameters.put("toVT",asCards.get(0).getDepartmentId());
			parameters.put("lyDoXuat", asCards.get(0).getLyDoXuat()); 
			parameters.put("nguonLayThietBi", asCards.get(0).getNguonLayTb()); 
			parameters.put("baoGomThietBi", asCards.get(0).getBaoGomThietBi());
		}
		
		parameters.put("thoiGian", hour + ", ngày " + dateNow); 
		
		JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
		
		response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		response.setHeader("Content-Disposition", "inline; filename=\"xuatKhoDDH__"+dateNow+".docx\"");
		
		JRDocxExporter exporterDocx = new JRDocxExporter();
		exporterDocx.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		OutputStream ouputStream = response.getOutputStream();
		exporterDocx.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
		exporterDocx.exportReport();	
		response.getOutputStream().flush();
		response.getOutputStream().close();
		
		return new ModelAndView("jspassets/asCards/asCardsList");
	}
}
