package controller.assets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import controller.BaseController;

import vo.AsExportWarehouse;
import vo.AsImportWarehouse;
import vo.AsProposedWarehouse;
import vo.AssetsManagements;
import vo.AssetsTypes;
import vo.CHighlightConfigs;
import vo.SYS_PARAMETER;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.UploadTools;

import dao.AsImportWarehouseDAO;
import dao.AsProposedWarehouseDAO;
import dao.AssetsManagementsDAO;
import dao.AssetsTypesDAO;
import dao.AssetsWarrantyDAO;
import dao.CHighlightConfigsDAO;
import dao.SYS_PARAMETERDAO;
/**
 * Ten file: AsImportWarehouseController.java
 * Muc dich: Quan ly nhap kho tai san
 * @author QBu
 * Ngay tao:
 * Lich su thay doi:
 *  11/06/2013, QBu, cap nhat thay doi theo yeu cau khach hang
 */
@Controller
@RequestMapping("/assets/quan-ly-nhap-kho-tai-san/*")
public class AsImportWarehouseController extends BaseController{

	@Autowired
	private AsImportWarehouseDAO asImportWarehouseDAO;
	@Autowired
	private AssetsWarrantyDAO assetsWarrantyDAO;
	@Autowired
	private AssetsTypesDAO assetsTypesDAO;
	@Autowired
	private AssetsManagementsDAO assetsManagementDao;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
	private AsProposedWarehouseDAO asProposedWarehouseDAO;
	
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
	public ModelAndView list(@RequestParam(required = false) String asTypesId,
							 @RequestParam(required = false) String importDateFrom,
							 @RequestParam(required = false) String importDateTo,
							 @RequestParam(required = false) String messagesXuatKho,
							 @ModelAttribute("filter") AsImportWarehouse filter, ModelMap model, HttpServletRequest request, HttpServletResponse response)
	{
		
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
		/*String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if(username.equals("admin"))
			model.addAttribute("loginAdmin", "Y");
		else
			model.addAttribute("loginAdmin", "N");*/
		
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
 		
		int order = 1;
		String column = "ID_ASSETS";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		
				
		List<AsImportWarehouse> asImportWarehouseList = asImportWarehouseDAO.getAsImportWarehouseFilter(asTypesId, filter.getProductCode(), filter.getSerialNo(), filter.getProductName(), 
				importDateFrom, importDateTo, filter.getJect(), filter.getVendor(), column, order==1?"ASC":"DESC");
		model.addAttribute("asImportWarehouseList", asImportWarehouseList);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "NhapTaiSan_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
				
		model.addAttribute("importDateFrom", importDateFrom);
		model.addAttribute("importDateTo", importDateTo);
		model.addAttribute("messagesXuatKho", messagesXuatKho);
		
		return new ModelAndView("jspassets/asImportWarehouseList");
	}
	
	//FB Form
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		AsImportWarehouse asImportWarehouse = (id == null ) ? new AsImportWarehouse() : asImportWarehouseDAO.selectByPrimaryKey(Integer.parseInt(id));
		model.addAttribute("asImportWarehouse", asImportWarehouse);
		
		asImportWarehouseAddEdit(id, model);
		if(id == null)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			model.addAttribute("importDate", df.format(cal.getTime()));
			
		}
		else{
			List<AsExportWarehouse> asExtByCodeSerial = asImportWarehouseDAO.getAsExtByCodeSerial(asImportWarehouse.getProductCode(), asImportWarehouse.getSerialNo());
			if(asExtByCodeSerial.size() > 0){
				//String highlight = "$(\"#amount\").attr('disabled','disabled');";
				model.addAttribute("amountExport","Y");
			}
			
			model.addAttribute("asTypesIDCBB", asImportWarehouse.getAsTypesId());
			if(asImportWarehouse.getImportDate() != null)
				model.addAttribute("importDate", df.format(asImportWarehouse.getImportDate()));
			if(asImportWarehouse.getProduceDate() != null)
				model.addAttribute("produceDate", df.format(asImportWarehouse.getProduceDate()));
			if(asImportWarehouse.getWarrantyDate() != null)
				model.addAttribute("warrantyDate", df.format(asImportWarehouse.getWarrantyDate()));
			model.addAttribute("unitCBB", asImportWarehouse.getUnit());
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
 		
		
		return "jspassets/asImportWarehouseForm";
	}
	
	private void asImportWarehouseAddEdit(String id, ModelMap model)
	{
		if(id != null && id != "")
			model.addAttribute("asImportWarehouseAddEdit", "Y");
		else
			model.addAttribute("asImportWarehouseAddEdit", "N");
	}
	
	private void loadAgainForm(ModelMap model,String asTypesId, String importDate, String produceDate,AsImportWarehouse asImportWarehouse){
		List<AssetsManagements> assetsManagementList = assetsWarrantyDAO.getAssetsManagementsList(asTypesId);
 		model.addAttribute("assetsManagementList", assetsManagementList);
 		
 		List<AssetsTypes> asTypesIDList = assetsTypesDAO.getAssetsTypesList();
 		model.addAttribute("asTypesIDList", asTypesIDList);
 		
 		List<SYS_PARAMETER> unitList = sysParameterDao.getAsImportWarehouseUnit();
 		model.addAttribute("unitList", unitList);
 		
 		model.addAttribute("importDate", importDate);
 		model.addAttribute("produceDate", produceDate);
 		model.addAttribute("unitCBB", asImportWarehouse.getUnit());
 		model.addAttribute("asTypesIDCBB", asTypesId);
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id,
						   @RequestParam(required = false) String asTypesId,
						   @RequestParam(required = false) String importDate,
						   @RequestParam(required = false) String produceDate,
						   @RequestParam(required = false) String warrantyDate, @ModelAttribute("asImportWarehouse") @Valid AsImportWarehouse asImportWarehouse, BindingResult result, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		//Ném lỗi
		if (result.hasErrors()) {
			asImportWarehouseAddEdit(id, model);
			loadAgainForm(model,asTypesId,importDate,produceDate,asImportWarehouse);
	 		
	 		if(result.hasFieldErrors("importDate"))
				model.addAttribute("importDateError", "importDateError");
	 		if(result.hasFieldErrors("produceDate"))
				model.addAttribute("produceDateError", "produceDateError");
	 		if(result.hasFieldErrors("warrantyDate"))
				model.addAttribute("warrantyDateError", "warrantyDateError");
	 		
			return "jspassets/asImportWarehouseForm";
		}
		
		if (importDate == null || importDate.equals("") || DateTools.isValid("dd/MM/yyyy", importDate)==false)
		{
			asImportWarehouseAddEdit(id, model);
			loadAgainForm(model,asTypesId,importDate,produceDate,asImportWarehouse);
			model.addAttribute("errorImportDate", "*");
			List<AsExportWarehouse> asExtByCodeSerial = asImportWarehouseDAO.getAsExtByCodeSerial(asImportWarehouse.getProductCode(), asImportWarehouse.getSerialNo());
			if(asExtByCodeSerial.size() > 0){
				model.addAttribute("amountExport","Y");
			}
			return "jspassets/asImportWarehouseForm";
		}
		
		if(id == "")
		{
			if(assetsManagementDao.selectByProductCode(asImportWarehouse.getProductCode()) == null)
			{
				AssetsManagements record = new AssetsManagements();
				record.setProductCode(asImportWarehouse.getProductCode());
				record.setAssetsName(asImportWarehouse.getProductName());
				record.setUnit(asImportWarehouse.getUnit());
				record.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				record.setAsTypesId(asTypesId);
				assetsManagementDao.insert(record);
			}else{
				asImportWarehouseDAO.updateProName(asImportWarehouse.getProductCode(),asImportWarehouse.getProductName(), asImportWarehouse.getUnit(), SecurityContextHolder.getContext().getAuthentication().getName());
			}
			
			if(asImportWarehouse.getSerialNo() != "" && asImportWarehouseDAO.getAsImpByCodeSerial(asImportWarehouse.getProductCode(), asImportWarehouse.getSerialNo(), null).size() > 0){
				asImportWarehouseAddEdit(id, model);
				loadAgainForm(model,asTypesId,importDate,produceDate,asImportWarehouse);
		 		
		 		if(result.hasFieldErrors("importDate"))
					model.addAttribute("importDateError", "importDateError");
		 		if(result.hasFieldErrors("produceDate"))
					model.addAttribute("produceDateError", "produceDateError");
		 		if(result.hasFieldErrors("warrantyDate"))
					model.addAttribute("warrantyDateError", "warrantyDateError");
		 		saveMessageKey(request, "messsage.confirm.asImportWarehouse");
				return "jspassets/asImportWarehouseForm";
			}
			
			asImportWarehouse.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			asImportWarehouseDAO.insert(asImportWarehouse);
			saveMessageKey(request, "messsage.confirm.addsuccess");
			
		}
		else{
			if(assetsManagementDao.selectByProductCode(asImportWarehouse.getProductCode()) == null)
			{
				AssetsManagements record = new AssetsManagements();
				record.setProductCode(asImportWarehouse.getProductCode());
				record.setAssetsName(asImportWarehouse.getProductName());
				record.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				record.setAsTypesId(asTypesId);
				assetsManagementDao.insert(record);
			}else{
				asImportWarehouseDAO.updateProName(asImportWarehouse.getProductCode(),asImportWarehouse.getProductName(), asImportWarehouse.getUnit(), SecurityContextHolder.getContext().getAuthentication().getName());
			}
			
			if(asImportWarehouse.getSerialNo() != "" && asImportWarehouseDAO.getAsImpByCodeSerial(asImportWarehouse.getProductCode(), asImportWarehouse.getSerialNo(), id).size() > 0){
				asImportWarehouseAddEdit(id, model);
				loadAgainForm(model,asTypesId,importDate,produceDate,asImportWarehouse);
		 		
		 		if(result.hasFieldErrors("importDate"))
					model.addAttribute("importDateError", "importDateError");
		 		if(result.hasFieldErrors("produceDate"))
					model.addAttribute("produceDateError", "produceDateError");
		 		if(result.hasFieldErrors("warrantyDate"))
					model.addAttribute("warrantyDateError", "warrantyDateError");
		 		saveMessageKey(request, "messsage.confirm.asImportWarehouse");
				return "jspassets/asImportWarehouseForm";
			}
			asImportWarehouse.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			asImportWarehouseDAO.updateByPrimaryKey(asImportWarehouse);
			saveMessageKey(request, "messsage.confirm.updatesuccess");
		}
		
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) Integer id, HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
			AsImportWarehouse asImportWarehouse = asImportWarehouseDAO.selectByPrimaryKey(id);
			List<AsExportWarehouse> asExtByCodeSerial = asImportWarehouseDAO.getAsExtByCodeSerial(asImportWarehouse.getProductCode(), asImportWarehouse.getSerialNo());
			if(asExtByCodeSerial.size() > 0){
				saveMessageKey(request, "messsage.confirm.deleteDontSuccess");
			}else{
				asImportWarehouseDAO.deleteByPrimaryKey(id);
				saveMessageKey(request, "messsage.confirm.deletesuccess");
			}
			
		}
		catch(Exception e)
		{
			saveMessageKey(request, "message.confirm.deleteOther");
			}
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "xuatKho", method = RequestMethod.GET)
	public String xuatKho(@RequestParam (required = true) Integer id, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
			int count = asImportWarehouseDAO.getAmountDontUse(id.toString());
			if(count == 0){
				AsImportWarehouse asImportWarehouse = asImportWarehouseDAO.selectByPrimaryKey(id);
				AsProposedWarehouse record = new AsProposedWarehouse();
				
				record.setProductCode(asImportWarehouse.getProductCode());
				record.setProductName(asImportWarehouse.getProductName());
				record.setSerialNo(asImportWarehouse.getSerialNo());
				record.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				record.setSoLuong(1);
				record.setDonVi(asImportWarehouse.getUnit());
				record.setNhaSx(asImportWarehouse.getVendor());
				
				asProposedWarehouseDAO.insert(record);
				saveMessageKey(request, "message.confirm.xuatKhoThanhCong");
			}
			else{
				//saveMessageKey(request, "message.confirm.xuatKhoChuaSd");
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
		
		return "jspassets/asImportWarehouseUpload";
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
		
		
		return "jspassets/asImportWarehouseUpload";
	}
	
	private String importContent(List sheetData, ModelMap model,HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<AsImportWarehouse> successList = new ArrayList<AsImportWarehouse>();
		List<AsImportWarehouse> failedList = new ArrayList<AsImportWarehouse>();
		List<AsImportWarehouse> success = new ArrayList<AsImportWarehouse>();
		
		String asTypesId;
		String productName;
		String productCode;
		String serialNo;
		String amount;
		String unit;
		String importDate;
		String produceDate;
		String vendor;
		String warrantyDate;
		String ject;
		String description;
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 12) {
        		saveMessageKey(request, "sidebar.admin.asImportWarehouseUploadErrorStructuresNumber");
        		
        		return "jspassets/asImportWarehouseUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		AsImportWarehouse cableTransmission;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			cableTransmission = new AsImportWarehouse();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=12; j++) {
     					data.add("");
     				}
        			asTypesId			= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			productName			= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			productCode			= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			serialNo			= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			amount				= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			unit				= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			importDate			= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			produceDate			= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			vendor				= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			warrantyDate		= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			ject				= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
        			description			= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim();
        			
        			
        			// Kiem tra loi
        			if (asTypesId == null || productCode == null || importDate == null || amount == null || serialNo == null
        					|| (asTypesId != null && asTypesId.length() > 40)
        					|| (productName != null && productName.length() > 240)
        					|| (productCode != null && productCode.length() > 40)
        					|| (serialNo != null && serialNo.length() > 40)
        					|| (serialNo != null && asImportWarehouseDAO.getAsImpByCodeSerial(productCode, serialNo, null).size() > 0)
							|| (amount != null && amount.length() > 4)
							|| (unit != null && unit.length() > 40 && sysParameterDao.asImportWarehouseByUnit(unit).size() == 0)
							|| (vendor != null && vendor.length() > 240)
							|| (description != null && description.length() > 900)
							|| (ject != null && ject.length() > 500)
							|| (asTypesId != null && assetsTypesDAO.getAssetsTypesByAsId(asTypesId).size() == 0)
						) {
						error = true;
					}
        			
        			try{
	        			if(amount != null){
		        			Integer a = Integer.parseInt(amount);
		        			cableTransmission.setAmount(a);
		        			System.out.println(a);
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
        				
        				if(produceDate != null){
		    				Date simple = new SimpleDateFormat("dd/MM/yyyy").parse(produceDate);
		    				cableTransmission.setProduceDate(new SimpleDateFormat("dd/MM/yyyy").parse(produceDate));
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
        			
        			
        			//--------------------------------------------------------------------------- 
        			cableTransmission.setAsTypesId(asTypesId);
        			cableTransmission.setProductName(productName);
        			cableTransmission.setProductCode(productCode);
        			cableTransmission.setSerialNo(serialNo);
        			cableTransmission.setUnit(unit);
        			cableTransmission.setVendor(vendor);
        			cableTransmission.setDescription(description);
        			cableTransmission.setJect(ject);
        			
        			if (asTypesId == null && productCode == null && productName == null && importDate == null && amount == null) {
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
		for (AsImportWarehouse record: successList) {
			try {
				//List<AsImportWarehouse> items = asImportWarehouseDAO.testAsImportWarehouse(record.getProductCode(),record.getProductName());
	    			
				//if(items.size() == 0){
					
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
					
					record.setCreatedBy(username);
					asImportWarehouseDAO.insert(record);
				//}
				/*else{
					record.setModifiedBy(username);
					asImportWarehouseDAO.updateByProCodeSerNo(record);
				}*/
				
				success.add(record);
			} catch (Exception ex) {
				
			}
		}
		
		if (failedList.size() == 0 && successList.size() > 0) {
			model.addAttribute("status", "Upload thành công.");				// Upload thành công
		} else if (failedList.size() == 0 && successList.size() == 0) {
			model.addAttribute("status", "Không có dữ liệu.");				// Không có dữ liệu
		} else {
			model.addAttribute("status", "Dữ liệu nhập kho thiết bị không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");	// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", successList.size());
		model.addAttribute("totalNum", failedList.size()+successList.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		return "jspassets/asImportWarehouseUpload";
	}
	
	@RequestMapping("ajax/getAsManagementByProCode")
	public @ResponseBody
	AssetsManagements getAsManagementByProCode(@RequestParam(required = false) String productCode, HttpServletRequest request) {
		String encodedurl = "";
		try {
			encodedurl = URLDecoder.decode(productCode, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		AssetsManagements record = asImportWarehouseDAO.getAsManagementByProCode(encodedurl);
		return record;
	}
	
	@RequestMapping("loadAssetsManagementsByAsId")
	public @ResponseBody
	List<AssetsManagements> loadAssetsManagementsByAsId(@RequestParam(required = false) String asTypesId, HttpServletRequest request) {
		List<AssetsManagements> record = assetsWarrantyDAO.getAssetsManagementsList(asTypesId);
		return record;
	}
}
