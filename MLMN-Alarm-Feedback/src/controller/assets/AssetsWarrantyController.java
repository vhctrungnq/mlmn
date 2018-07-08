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

import vo.AsImportWarehouse;
import vo.AssetsManagements;
import vo.AssetsTypes;
import vo.AssetsWarranty;
import vo.CHighlightConfigs;
import vo.SYS_PARAMETER;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.UploadTools;

import dao.AsImportWarehouseDAO;
import dao.AssetsManagementsDAO;
import dao.AssetsTypesDAO;
import dao.AssetsWarrantyDAO;
import dao.CHighlightConfigsDAO;
import dao.SYS_PARAMETERDAO;

@Controller
@RequestMapping("/assets/bao-hanh-tai-san/*")
public class AssetsWarrantyController extends BaseController{

	@Autowired
	private AssetsWarrantyDAO assetsWarrantyDAO;
	@Autowired
	private AssetsTypesDAO assetsTypesDAO;
	@Autowired
	private AssetsManagementsDAO assetsManagementDao;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	@Autowired
	private AsImportWarehouseDAO asImportWarehouseDAO;
	
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
	public ModelAndView list(@RequestParam(required = false) String asTypesId,
			 				 @RequestParam(required = false) String sentDateFrom,
			 				 @RequestParam(required = false) String sentDateTo,
			 				 @ModelAttribute("filter") AssetsWarranty filter, ModelMap model, HttpServletRequest request, HttpServletResponse response)
	{
		if(filter.getProductCode() != null)
			filter.setProductCode(filter.getProductCode().trim());
		if(filter.getSerialNo() != null)
			filter.setSerialNo(filter.getSerialNo().trim());
		if(filter.getDeliveryNo() != null)
			filter.setDeliveryNo(filter.getDeliveryNo().trim());
		if(filter.getCsr() != null)
			filter.setCsr(filter.getCsr().trim());
		if(filter.getVendor() != null)
			filter.setVendor(filter.getVendor().trim());
		List<AssetsTypes> asTypesIDList = assetsTypesDAO.getAssetsTypesList();
 		model.addAttribute("asTypesIDList", asTypesIDList);
 		model.addAttribute("asTypesIDCBB", asTypesId);
 		
 		// Ngay thang
 		if (sentDateFrom == null || (!sentDateFrom.equals("") && DateTools.isValid("dd/MM/yyyy", sentDateFrom)==false)) {			

 			sentDateFrom = df.format(DateTools.startMonth(new Date()));
		
		}
 		if (sentDateTo == null || (!sentDateTo.equals("") && DateTools.isValid("dd/MM/yyyy", sentDateTo)==false))
 		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			sentDateTo = df.format(cal.getTime());
		}
 	 		
		int order = 1;
		String column = "PRODUCT_CODE";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "BaoHanh_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		List<AssetsWarranty>  assetsWarrantyList = assetsWarrantyDAO.getAssetsWarrantyFilter(asTypesId, filter.getProductCode(), filter.getSerialNo(), sentDateFrom, sentDateTo, filter.getDeliveryNo(), filter.getCsr(), filter.getVendor(), column, order==1? "ASC":"DESC");
		model.addAttribute("assetsWarrantyList", assetsWarrantyList);
		
		model.addAttribute("sentDateFrom", sentDateFrom);
		model.addAttribute("sentDateTo", sentDateTo);
		
		return new ModelAndView("jspassets/assetsWarrantyList");
	}
	
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, 
			@RequestParam(required = false) String sentDate,
			@RequestParam(required = false) String receivedDate,
			@RequestParam(required = false) String returnDate, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		AssetsWarranty assetsWarranty = (id == null ) ? new AssetsWarranty() : assetsWarrantyDAO.selectByPrimaryKey(Integer.parseInt(id));
		model.addAttribute("assetsWarranty", assetsWarranty);
		
		if(id == null)
		{
			// Ngay thang
			if (sentDate == null || sentDate.equals("") || DateTools.isValid("dd/MM/yyyy", sentDate)==false) {
				sentDate = df.format(new Date());
			}
			if (receivedDate == null || receivedDate.equals("") || DateTools.isValid("dd/MM/yyyy", receivedDate)==false) {
				receivedDate = df.format(new Date());
			}
			if (returnDate == null || returnDate.equals("") || DateTools.isValid("dd/MM/yyyy", returnDate)==false) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				cal.add(Calendar.DAY_OF_MONTH, 7);
				returnDate = df.format(cal.getTime());
			}
			model.addAttribute("sentDate", sentDate);
	 		model.addAttribute("receivedDate", receivedDate);
	 		model.addAttribute("returnDate", returnDate);
		}
		else{
			model.addAttribute("asTypesIDCBB", assetsWarranty.getAsTypesId());
			if(assetsWarranty.getSentDate() != null)
				model.addAttribute("sentDate", df.format(assetsWarranty.getSentDate()));
			if(assetsWarranty.getReceivedDate() != null)
				model.addAttribute("receivedDate", df.format(assetsWarranty.getReceivedDate()));
			if(assetsWarranty.getReturnDate() != null)
				model.addAttribute("returnDate", df.format(assetsWarranty.getReturnDate()));
			model.addAttribute("unitCBB", assetsWarranty.getSameUnit());
			model.addAttribute("toXuLyCBB", assetsWarranty.getDepartmentId());
			model.addAttribute("rejectCBB", assetsWarranty.getJect());
		}
		
		List<SYS_PARAMETER> toXuLyList = assetsWarrantyDAO.getDepartmentList();
 		model.addAttribute("toXuLyList", toXuLyList);
 		
 		
 		List<AssetsManagements> assetsManagementList = assetsWarrantyDAO.getAssetsManagementsList(null);
 		model.addAttribute("assetsManagementList", assetsManagementList);

 		List<SYS_PARAMETER> unitList = sysParameterDao.getSameUnitAssets();
 		model.addAttribute("unitList", unitList);
 		
 		List<SYS_PARAMETER> rejectAssetWarrantyList = assetsWarrantyDAO.getRejectAssetsWarranty();
 		model.addAttribute("rejectList", rejectAssetWarrantyList);
 		
		assetsWarrantyAddEdit(id, model);
		
		return "jspassets/assetsWarrantyForm";
	}
	
	private void assetsWarrantyAddEdit(String id, ModelMap model)
	{
		if(id != null && id != "")
			model.addAttribute("assetsWarrantyAddEdit", "Y");
		else
			model.addAttribute("assetsWarrantyAddEdit", "N");
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id,
						   @RequestParam(required = false) String sentDate,
						   @RequestParam(required = false) String receivedDate, 
						   @RequestParam(required = false) String returnDate, @ModelAttribute("assetsWarranty") @Valid AssetsWarranty assetsWarranty, BindingResult result, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		//Ném lỗi
		if (result.hasErrors()) {
			assetsWarrantyAddEdit(id, model);
			
			List<SYS_PARAMETER> toXuLyList = assetsWarrantyDAO.getDepartmentList();
	 		model.addAttribute("toXuLyList", toXuLyList);
	 		
	 		List<AssetsManagements> assetsManagementList = assetsWarrantyDAO.getAssetsManagementsList(null);
	 		model.addAttribute("assetsManagementList", assetsManagementList);
	 		
	 		List<AssetsTypes> asTypesIDList = assetsTypesDAO.getAssetsTypesList();
	 		model.addAttribute("asTypesIDList", asTypesIDList);
	 		
	 		List<SYS_PARAMETER> unitList = sysParameterDao.getSameUnitAssets();
	 		model.addAttribute("unitList", unitList);
	 		
	 		List<SYS_PARAMETER> rejectAssetWarrantyList = assetsWarrantyDAO.getRejectAssetsWarranty();
	 		model.addAttribute("rejectList", rejectAssetWarrantyList);
	 		
	 		model.addAttribute("toXuLyCBB", assetsWarranty.getDepartmentId());
	 		model.addAttribute("unitCBB", assetsWarranty.getSameUnit());
	 		model.addAttribute("sentDate", sentDate);
	 		model.addAttribute("receivedDate", receivedDate);
	 		model.addAttribute("returnDate", returnDate);
	 		model.addAttribute("rejectCBB", assetsWarranty.getJect());
			
			return "jspassets/assetsWarrantyForm";
		}
		
		if(id == "")
		{
			assetsWarranty.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			assetsWarrantyDAO.insert(assetsWarranty);
			saveMessageKey(request, "messsage.confirm.addsuccess");
		}
		else{
			assetsWarranty.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			assetsWarrantyDAO.updateByPrimaryKey(assetsWarranty);
			saveMessageKey(request, "messsage.confirm.updatesuccess");
		}
		
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) Integer id, HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
			assetsWarrantyDAO.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch(Exception e)
		{
			saveMessageKey(request, "message.confirm.deleteOther");
			}
		
		return "redirect:list.htm";
	}
	
	@RequestMapping("loadAssetsManagementsByAsId")
	public @ResponseBody
	List<AssetsManagements> loadAssetsManagementsByAsId(@RequestParam(required = false) String asTypesId, HttpServletRequest request) {
		List<AssetsManagements> record = assetsWarrantyDAO.getAssetsManagementsList(asTypesId);
		return record;
	}
	
	@RequestMapping("ajax/getListSeriByProCode")
	public @ResponseBody
	List<AsImportWarehouse> getListSeriByProCode(@RequestParam(required = false) String productCode, HttpServletRequest request) {
		String encodedurl = "";
		try {
			encodedurl = URLDecoder.decode(productCode, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		List<AsImportWarehouse> record= asImportWarehouseDAO.listSeriByProCode(encodedurl);
		return record;
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		return "jspassets/assetsWarrantyUpload";
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
		
		
		return "jspassets/assetsWarrantyUpload";
	}
	
	private String importContent(List sheetData, ModelMap model,HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<AssetsWarranty> successList = new ArrayList<AssetsWarranty>();
		List<AssetsWarranty> failedList = new ArrayList<AssetsWarranty>();
		List<AssetsWarranty> success = new ArrayList<AssetsWarranty>();
		
		String csr;
		String function;
		String productCode;
		String vendor;
		String rState;
		String serialNo;
		String serialNoScan;
		String sentDate;
		String sameUnit;
		String ject;
		String serialNoRep;
		String receivedDate;
		String deliveryNo;
		String qty;
		String departmentId;
		String returnDate;
		String description;
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 17) {
        		saveMessageKey(request, "sidebar.admin.assetsWarrantyUploadErrorStructuresNumber");
        		
        		return "jspassets/assetsWarrantyUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		AssetsWarranty cableTransmission;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			cableTransmission = new AssetsWarranty();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=17; j++) {
     					data.add("");
     				}

        			csr					= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			function			= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			productCode			= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			vendor				= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			rState				= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			serialNo			= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			serialNoScan		= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			sentDate			= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			sameUnit			= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			ject				= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			serialNoRep			= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
        			receivedDate		= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim();
        			deliveryNo			= data.get(12).toString().trim().equals("")?null:data.get(12).toString().trim();
        			qty					= data.get(13).toString().trim().equals("")?null:data.get(13).toString().trim();
        			departmentId		= data.get(14).toString().trim().equals("")?null:data.get(14).toString().trim();
        			returnDate			= data.get(15).toString().trim().equals("")?null:data.get(15).toString().trim();
        			description			= data.get(16).toString().trim().equals("")?null:data.get(16).toString().trim();
        			
        			
        			// Kiem tra loi
        			if (csr == null || productCode == null || serialNo == null
        					|| (productCode != null && productCode.length() > 40)
        					|| (serialNo != null && serialNo.length() > 40)
							|| (serialNoScan != null && serialNoScan.length() > 40)
							|| (sameUnit != null && sameUnit.length() > 40)
							|| (rState != null && rState.length() > 40)
							|| (ject != null && ject.length() > 500)
							|| (csr != null && csr.length() > 40)
							|| (function != null && function.length() > 40)
							|| (serialNoRep != null && serialNoRep.length() > 40)
							|| (deliveryNo != null && deliveryNo.length() > 40)
							|| (departmentId != null && departmentId.length() > 40)
							|| (description != null && description.length() > 900)
							|| (vendor != null && vendor.length() > 40)
						) {
						error = true;
					}
        			
        			try{
	        			if(qty != null){
		        			Integer a = Integer.parseInt(qty);
		        			cableTransmission.setQty(Integer.parseInt(qty));
		        			System.out.println(a);
	        			}
	        		}
	        		catch(Exception e){
	        			error = true;
	        		}
        			
        			try
	    			{
        				if(sentDate != null){
		    				Date simple = new SimpleDateFormat("dd/MM/yyyy").parse(sentDate);
		    				cableTransmission.setSentDate(new SimpleDateFormat("dd/MM/yyyy").parse(sentDate));
		    				System.out.println(simple);
	    				}
        				
        				if(receivedDate != null){
		    				Date simple = new SimpleDateFormat("dd/MM/yyyy").parse(receivedDate);
		    				cableTransmission.setReceivedDate(new SimpleDateFormat("dd/MM/yyyy").parse(receivedDate));
		    				System.out.println(simple);
	    				}
        				
        				if(returnDate != null){
		    				Date simple = new SimpleDateFormat("dd/MM/yyyy").parse(returnDate);
		    				cableTransmission.setReturnDate(new SimpleDateFormat("dd/MM/yyyy").parse(returnDate));
		    				System.out.println(simple);
	    				}
	    			}
	    			catch(Exception e)
	    			{
	    				error = true;
	    			}
        			
        			if(ject != null){
        				if(ject.toString().toLowerCase().equals("còn hạn bh"))
        					ject = "CON_HAN_BH";
        				else if(ject.toString().toLowerCase().equals("hết hạn bh"))
        					ject = "HET_HAN_BH";
        				else
        					error = true;
        			}
        			
        			//--------------------------------------------------------------------------- 
        			
        			cableTransmission.setCsr(csr);
        			cableTransmission.setFunction(function);
        			cableTransmission.setProductCode(productCode);
        			cableTransmission.setVendor(vendor);
        			cableTransmission.setSerialNo(serialNo);
        			cableTransmission.setSerialNoScan(serialNoScan);
        			cableTransmission.setrState(rState);
        			cableTransmission.setSameUnit(sameUnit);
        			cableTransmission.setJect(ject);
        			cableTransmission.setSerialNoRep(serialNoRep);
        			cableTransmission.setDeliveryNo(deliveryNo);
        			cableTransmission.setDescription(description);
        				
        			cableTransmission.setDepartmentId(departmentId);
        			
    				
        			if (csr == null && productCode == null && serialNo == null) {
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
		for (AssetsWarranty record: successList) {
			try {
				/*List<AssetsWarranty> items = assetsWarrantyDAO.testAssetsWarranty(record.getProductCode(),record.getSerialNo());
	    			
				if(items.size() == 0){*/
					
					record.setCreatedBy(username);
					assetsWarrantyDAO.insert(record);
				/*}
				else{
					record.setModifiedBy(username);
					assetsWarrantyDAO.updateByProCodeSerNo(record);
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
			model.addAttribute("status", "Dữ liệu bảo hành tài sản không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");	// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", successList.size());
		model.addAttribute("totalNum", failedList.size()+successList.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		return "jspassets/assetsWarrantyUpload";
	}
	
	
}
