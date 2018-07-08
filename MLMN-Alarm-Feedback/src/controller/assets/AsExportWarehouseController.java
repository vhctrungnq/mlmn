 package controller.assets;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import vo.AsExportWarehouse;
import vo.AsImportWarehouse; 
import vo.CHighlightConfigs;
import vo.SYS_PARAMETER; 
import vo.alarm.utils.AsExportFilter;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.UploadTools;
import controller.BaseController;
import dao.AsExportWarehouseDAO;
import dao.AsImportWarehouseDAO;
import dao.AsProposedWarehouseDAO;
import dao.AssetsManagementsDAO;
import dao.AssetsTypesDAO;
import dao.CHighlightConfigsDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUsersDAO;
 

@Controller
@RequestMapping("/assets/xuat-tai-san/*")
public class AsExportWarehouseController extends BaseController {
	@Autowired
	private AsImportWarehouseDAO asImportWarehouseDAO;
	@Autowired
	private AssetsManagementsDAO assetsManagementDao;  
	@Autowired
	private AsExportWarehouseDAO asExportWarehouseDao;  
	@Autowired
	private AssetsTypesDAO assetsTypesDAO;
	@Autowired
	private SYS_PARAMETERDAO  sysParameterDao;
	@Autowired 
	private SysUsersDAO sysUsersDao; 
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	@Autowired
	private AsProposedWarehouseDAO asProposedWarehouseDAO;
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy"); 
	private DateFormat df_little = new SimpleDateFormat("HH:mm");
	
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
	
	@RequestMapping(value = "list")
	public ModelAndView list(@ModelAttribute("filter") AsExportFilter filter,  
			@RequestParam(required = false) String sdate,
			@RequestParam(required = false) String edate,
			Model model, HttpServletRequest request) {
		
		/*boolean checkquanly  = false;
		String username = SecurityContextHolder.getContext()
				.getAuthentication().getName();*/
		//SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		
		/*List<SYS_PARAMETER> phanquyen = sysParameterDao.getUserAdmin();
		
		for(int i = 0; i< phanquyen.size(); i++){
			if(username.equals(phanquyen.get)){
				checkquanly = true;
			}
		} */
		
		List<SYS_PARAMETER> title = asExportWarehouseDao.titleForm("AS_EXPORT_WAREHOUSE_LIST");		
		model.addAttribute("title", title.get(0).getValue());
		
		int order = 0;
		String column = "";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("asExportWarehouse").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("asExportWarehouse").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}  
		
		if (sdate== null
				|| sdate.equals("")
				|| (sdate != null && !sdate.equals("") && DateTools
						.isValid("dd/MM/yyyy", sdate) == false)) { 
			
			sdate = df_full.format(DateTools.startMonth(new Date()));
		}
		filter.setSdate(sdate);
		
		if (edate == null
				|| edate.equals("")
				|| (edate != null && !edate.equals("") && DateTools
						.isValid("dd/MM/yyyy", edate) == false)) {
			Calendar cal1 = Calendar.getInstance();
			edate = df_full.format(cal1.getTime());
		}
		filter.setEdate(edate);
		
		List<AsExportWarehouse> asExportWarehouse = asExportWarehouseDao.getAsExportWarehouse(filter, order, column);
		List<String> productCode = asExportWarehouseDao.getProductCode();
		
		model.addAttribute("productCodeList", productCode);
		model.addAttribute("asExportWarehouse", asExportWarehouse);
		model.addAttribute("filter", filter);
		model.addAttribute("sdate", sdate);
		model.addAttribute("edate", edate);
	//	model.addAttribute("checkquanly", checkquanly);
		 
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "XuatHoantraTaiSan_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		return new ModelAndView("jspassets/asExportWarehouse/list");
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) String id, HttpServletRequest request) {
		
		try {
			asExportWarehouseDao.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch (Exception e) {
			saveMessageKey(request, "messsage.confirm.deletefailure");
		}
		return "redirect:list.htm";
	}
	
	@RequestMapping(value="form", method=RequestMethod.GET)
    public String showForm(@RequestParam(required = false) String id, ModelMap model) {
		
		AsExportWarehouse asExportWarehouse = (id == null ) ? new AsExportWarehouse() : asExportWarehouseDao.selectByPrimaryKey(id);
		
		asExportWarehouseAddEdit(id, model); 
		List<SYS_PARAMETER> unitList = assetsManagementDao.unitList();
		
		if(id == null){
			asExportWarehouse.setExportDate(new Date());
		} 
		 
		List<AsImportWarehouse> serialNo = asExportWarehouseDao.getSeriNoByProCode(asExportWarehouse.getProductCode());
		List<AsImportWarehouse> vendor = asImportWarehouseDAO.getNSX(asExportWarehouse.getProductCode(), asExportWarehouse.getSerialNo());
		
		if(vendor.size() != 0){
			model.addAttribute("vendorList", vendor);
		}
		
		if(serialNo.size() != 0){
			model.addAttribute("serialNoList", serialNo);
		} 
		
		model.addAttribute("asExportWarehouse", asExportWarehouse);
		model.addAttribute("unitList", unitList);  
		model.addAttribute("dateReturn", asExportWarehouse.getNgayTraTS());  
		model.addAttribute("exportDate", asExportWarehouse.getNgayXuatTS());
		model.addAttribute("asExportWarehouse", asExportWarehouse);
		
		return "jspassets/asExportWarehouse/form";
	}
	
	private void asExportWarehouseAddEdit(String id, ModelMap model)
	{
		if(id != null && id != "")
			model.addAttribute("asExportWarehouseAddEdit", "Y");//Form sửa
		else
			model.addAttribute("asExportWarehouseAddEdit", "N");//Form thêm
	}
	
	@RequestMapping(value="form", method=RequestMethod.POST)
    public String formPost(@RequestParam(required = false) String id,@ModelAttribute("asExportWarehouse") @Valid AsExportWarehouse asExportWarehouse, 
    		 BindingResult result,ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName(); 
		
		List<AsImportWarehouse> serialNo = asExportWarehouseDao.getSeriNoByProCode(asExportWarehouse.getProductCode());
		//List<AsImportWarehouse> mataisan = asExportWarehouseDao.getMataisan();
		List<SYS_PARAMETER> unitList = assetsManagementDao.unitList(); 
		List<AsImportWarehouse> vendor = asImportWarehouseDAO.getNSX(asExportWarehouse.getProductCode(), asExportWarehouse.getSerialNo());
		
		if(result.hasErrors()){
			asExportWarehouseAddEdit(id, model); 
			
			if(vendor.size() != 0){
				model.addAttribute("vendorList", vendor);
			}
			
			if(serialNo.size() != 0){
				model.addAttribute("serialNoList", serialNo);
			}
			model.addAttribute("unitList", unitList);
			model.addAttribute("asExportWarehouse", asExportWarehouse);  
			model.addAttribute("dateReturn", asExportWarehouse.getNgayTraTS());  
			model.addAttribute("exportDate", asExportWarehouse.getNgayXuatTS());
			
			return "jspassets/asExportWarehouse/form";
		}else{
			int slImport=0,slExport=0,slReturn=0,slConlai=0;
			boolean ktloi = false;
			List<AsImportWarehouse> importWarehouseOld= asImportWarehouseDAO.testAsImportWarehouse(asExportWarehouse.getProductCode(), null);
			//Kiem tra Ma tai san co ton tai trong kho chua?
			if(importWarehouseOld.size() == 0){
				asExportWarehouseAddEdit(id, model); 
				
				if(vendor.size() != 0){
					model.addAttribute("vendorList", vendor);
				}
				
				if(serialNo.size() != 0){
					model.addAttribute("serialNoList", serialNo);
				}
				model.addAttribute("unitList", unitList);
				saveMessageKey(request, "asExportWarehouse.khongtontai");
				model.addAttribute("asExportWarehouse", asExportWarehouse);  
				model.addAttribute("dateReturn", asExportWarehouse.getNgayTraTS());  
				model.addAttribute("exportDate", asExportWarehouse.getNgayXuatTS());
				
				return "jspassets/asExportWarehouse/form"; 
			}
			//Kiem tra ngay tra < ngay xuat?
			if(asExportWarehouse.getNgayTraTS() != null){
				if(asExportWarehouse.getNgayTraTS().compareTo(asExportWarehouse.getNgayXuatTS()) < 0){ 
					asExportWarehouseAddEdit(id, model); 
					
					model.addAttribute("serialNoList", serialNo);
					model.addAttribute("unitList", unitList); 
					model.addAttribute("ktNgaytra", "Ngày trả không đúng");  
					model.addAttribute("asExportWarehouse", asExportWarehouse);  
					model.addAttribute("dateReturn", asExportWarehouse.getNgayTraTS());  
					model.addAttribute("exportDate", asExportWarehouse.getNgayXuatTS());
					
					return "jspassets/asExportWarehouse/form";
				}
			}  
			//form them
			if (asExportWarehouse.getId() == null ||asExportWarehouse.getId() == "") {    
					AsImportWarehouse sumAmout = asExportWarehouseDao.getAmountByProCode(asExportWarehouse.getProductCode(),asExportWarehouse.getNgayXuatTS());
					AsExportWarehouse sumAmoutEx = asExportWarehouseDao.getAmountExByProCode(asExportWarehouse.getProductCode(),asExportWarehouse.getSerialNo());
					if(asExportWarehouse.getAmountEx() == null)  asExportWarehouse.setAmountEx(0);
					if(asExportWarehouse.getAmountReturn() == null)  asExportWarehouse.setAmountReturn(0);
					if(sumAmout.getAmount() == null)   sumAmout.setAmount(0); 
					if(sumAmoutEx.getAmountReturn() == null) sumAmoutEx.setAmountReturn(0);
					if(sumAmoutEx.getAmountEx() == null) sumAmoutEx.setAmountEx(0);
					
					slImport = sumAmout.getAmount(); // tong so luong nhap kho trong DB
					slExport = sumAmoutEx.getAmountEx(); // tong so luong xuat kho trong DB
					slReturn = sumAmoutEx.getAmountReturn(); // tong so luong tra lai kho trong DB
					slConlai = slImport - slExport + slReturn;// tong so luong con lai trong kho 
					
					if(slConlai < 0) slConlai = 0;
				//Kiem tra sl xuat < sl con lai va sl tra lai < sl xuat?
				if(asExportWarehouse.getAmountEx() > slConlai){
					model.addAttribute("slConlai", "Số lượng còn lại trong kho: "+slConlai+" .Không đủ để xuất");
					ktloi = true;
				}
				
				//So luong xuat phai lon hon 0
				if(asExportWarehouse.getAmountEx() < 1){
					model.addAttribute("slxuat", "Số lượng xuất ít nhất = 1");
					ktloi = true;
				}
				
				//So luong tra lai < so luong xuat
				if(asExportWarehouse.getAmountReturn() > asExportWarehouse.getAmountEx()){
					model.addAttribute("sltralai", "Số lượng trả lại không đúng");
					ktloi = true;
				}
				
				if(ktloi == true){
					asExportWarehouseAddEdit(id, model);  
					
					if(vendor.size() != 0){
						model.addAttribute("vendorList", vendor);
					}
					
					if(serialNo.size() != 0){
						model.addAttribute("serialNoList", serialNo);
					}
					model.addAttribute("unitList", unitList); 
					model.addAttribute("asExportWarehouse", asExportWarehouse);  
					model.addAttribute("dateReturn", asExportWarehouse.getNgayTraTS());  
					model.addAttribute("exportDate", asExportWarehouse.getNgayXuatTS()); 
					
					return "jspassets/asExportWarehouse/form";
				}
				
				asExportWarehouse.setCreateDate(new Date());
				asExportWarehouse.setCreatedBy(username); 
				
				asExportWarehouseDao.insertSelective(asExportWarehouse);
				saveMessageKey(request, "asExportWarehouse.insertSucceFull"); 
			} 
			//form sua
			else {     
				AsImportWarehouse sumAmout = asExportWarehouseDao.getAmountByProCode(asExportWarehouse.getProductCode(),asExportWarehouse.getNgayXuatTS());
				AsExportWarehouse sumAmoutEx = asExportWarehouseDao.getAmountExByProCode(asExportWarehouse.getProductCode(),asExportWarehouse.getSerialNo());
				if(asExportWarehouse.getAmountEx() == null)  asExportWarehouse.setAmountEx(0);
				if(asExportWarehouse.getAmountReturn() == null)  asExportWarehouse.setAmountReturn(0); 
				if(sumAmout.getAmount() == null)   sumAmout.setAmount(0); 
				sumAmoutEx.setAmountReturn(0);
				sumAmoutEx.setAmountEx(0);
				
				slImport = sumAmout.getAmount(); // tong so luong nhap kho
				slExport = sumAmoutEx.getAmountEx(); // tong so luong xuat kho
				slReturn = sumAmoutEx.getAmountReturn(); // tong so luong tra lai kho
				slConlai = slImport - slExport + slReturn;// tong so luong con lai trong kho
				if(slConlai < 0) slConlai = 0; 
				
				//Kiem tra sl xuat < sl con lai va sl tra lai < sl xuat?
				if(asExportWarehouse.getAmountEx() > slConlai){
					model.addAttribute("slConlai", "Số lượng còn lại trong kho: "+slConlai+" .Không đủ để xuất");
					ktloi = true;
				}
				
				//So luong xuat phai lon hon 0
				if(asExportWarehouse.getAmountEx() < 1){
					model.addAttribute("slxuat", "Số lượng xuất ít nhất = 1");
					ktloi = true;
				}
				
				if(asExportWarehouse.getAmountReturn() > asExportWarehouse.getAmountEx()){
					model.addAttribute("sltralai", "Số lượng trả lại không đúng");
					ktloi = true;
				}
				
				if(ktloi == true){
					asExportWarehouseAddEdit(id, model); 
					
					if(vendor.size() != 0){
						model.addAttribute("vendorList", vendor);
					}
					
					if(serialNo.size() != 0){
						model.addAttribute("serialNoList", serialNo);
					}
					model.addAttribute("unitList", unitList);
					model.addAttribute("asExportWarehouse", asExportWarehouse);  
					model.addAttribute("dateReturn", asExportWarehouse.getNgayTraTS());  
					model.addAttribute("exportDate", asExportWarehouse.getNgayXuatTS()); 
					
					return "jspassets/asExportWarehouse/form";
				}
				
				asExportWarehouse.setModifyDate(new Date());
				asExportWarehouse.setModifiedBy(username);
				
				asExportWarehouseDao.updateByPrimaryKeySelective(asExportWarehouse);
				saveMessageKey(request, "asExportWarehouse.updateSucceFull"); 
			}
		}
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		return "jspassets/asExportWarehouse/upload";
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
		
		
		return "jspassets/asExportWarehouse/upload";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent(List sheetData, ModelMap model,HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<AsExportWarehouse> successList = new ArrayList<AsExportWarehouse>();
		List<AsExportWarehouse> failedList = new ArrayList<AsExportWarehouse>();
		List<AsExportWarehouse> success = new ArrayList<AsExportWarehouse>();
		
		String productCode;//ma tai san
		String serialNo; // so serial
		String unit;// don vi tinh
		String amountEx;// so luong xuat
		String exportDate;// ngay xuat
		String votesNo;// so phieu xuat
		String usersEx;// nguoi xuat
		String amountReturn;// so luong tra lai
		String dateReturn;// ngay tra
		String vendor;// nha san xuat
		String organization;// Bo phan su dung
		String departmentId; // don vi su dung
		String users;//nguoi su dung
		String description; // ghi chu
		String lydoxuat;
		String nguonlayTB;
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 16) {
        		saveMessageKey(request, "sidebar.admin.asExportWarehouseUploadErrorStructuresNumber");
        		
        		return "jspassets/asExportWarehouse/upload";
        	}
        	
        	if (sheetData.size() > 1) {
        		AsExportWarehouse asExportWarehouse;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			asExportWarehouse = new AsExportWarehouse();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=16; j++) {
     					data.add("");
     				} 
        			
        			productCode			= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			serialNo			= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			unit				= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			amountEx			= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			exportDate			= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			votesNo			  	= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			usersEx				= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			amountReturn		= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			dateReturn			= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			vendor				= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			organization		= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
        			departmentId		= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim();
        			users				= data.get(12).toString().trim().equals("")?null:data.get(12).toString().trim();
        			description			= data.get(13).toString().trim().equals("")?null:data.get(13).toString().trim();
        			lydoxuat			= data.get(14).toString().trim().equals("")?null:data.get(14).toString().trim();
        			nguonlayTB			= data.get(15).toString().trim().equals("")?null:data.get(15).toString().trim();
        			
        			// Kiem tra loi
        			if (productCode == null || amountEx == null || exportDate == null || votesNo == null
							|| (productCode != null && productCode.length() > 40)
							|| (serialNo != null && serialNo.length() > 40)
							|| (unit != null && unit.length() > 40)
							|| (vendor != null && vendor.length() > 245)
							|| (votesNo != null && votesNo.length() > 80)
							|| (organization != null && organization.length() > 80)
							|| (departmentId != null && departmentId.length() > 80)
							|| (usersEx != null && usersEx.length() > 80)
							|| (users != null && users.length() > 80)
							|| (lydoxuat != null && lydoxuat.length() > 90)
							|| (description != null && description.length() > 900) 
							|| (amountEx != null && DateTools.isValidNumber(amountEx) == false)
	        				|| (amountReturn != null && DateTools.isValidNumber(amountReturn) == false)
	        				|| (exportDate != null && DateTools.isValid("dd/MM/yyyy", exportDate)== false)
	        				|| (dateReturn != null && DateTools.isValid("dd/MM/yyyy", dateReturn) == false)
	        				|| (nguonlayTB != null && nguonlayTB.length() > 90)
						) {
						error = true;
					}
        			
        			int slImportOld=0,slExportOld=0,slReturnOld=0,slConlai=0; 
        			if((asImportWarehouseDAO.testAsImportWarehouse(productCode, serialNo).size() == 0)){
        				error = true;
        			}else{
        				if(exportDate != null && DateTools.isValid("dd/MM/yyyy", exportDate)== true){
        					AsImportWarehouse sumAmoutOld = asExportWarehouseDao.getAmountByProCode(productCode,exportDate);
        					AsExportWarehouse sumAmoutExOld = asExportWarehouseDao.getAmountExByProCode(productCode,serialNo);
            				
            				if(sumAmoutOld.getAmount() == null)   sumAmoutOld.setAmount(0); 
            				if(sumAmoutExOld.getAmountReturn() == null) sumAmoutExOld.setAmountReturn(0);
            				if(sumAmoutExOld.getAmountEx() == null) sumAmoutExOld.setAmountEx(0);
            				
            				slImportOld = sumAmoutOld.getAmount(); // tong so luong nhap kho
            				slExportOld = sumAmoutExOld.getAmountEx(); // tong so luong xuat kho
            				slReturnOld = sumAmoutExOld.getAmountReturn(); // tong so luong tra lai kho
            				slConlai = slImportOld - slExportOld + slReturnOld;// tong so luong con lai trong kho
        				}
        				
        				if(slConlai < 0){
        					slConlai = 0;
        				}
        				
        				int slExport=0,slReturn=0;
        				
        				if(amountEx == null || amountEx == "" || (amountEx != null && DateTools.isValidNumber(amountEx) == false)){
        					slExport = Integer.parseInt("0");
        				}else{
        					slExport = Integer.parseInt(amountEx);
        				}
        				
        				if(amountReturn == null || amountReturn == "" || (amountReturn != null && DateTools.isValidNumber(amountReturn) == false)){
        					slReturn = Integer.parseInt("0");
        				}else{
        					slReturn = Integer.parseInt(amountReturn);
        				}
        				 
        				if( slExport > slConlai || slImportOld == 0 || slReturn > slExport){
        					 error = true;
        				}
        			} 
        			
        			//---------------------------------------------------------------------------  
        			
        			asExportWarehouse.setProductCode(productCode);
        			asExportWarehouse.setSerialNo(serialNo);
        			
        			if(amountEx == null || amountEx == "" || (amountEx != null && DateTools.isValidNumber(amountEx) == false)){
        				asExportWarehouse.setAmountEx(Integer.parseInt("0"));
        			}else{
        				asExportWarehouse.setAmountEx(Integer.parseInt(amountEx));
        			}
        			
        			asExportWarehouse.setUnit(unit);
        			
        			try {
        				if(exportDate != null){
        					asExportWarehouse.setExportDate(df_full.parse(exportDate));
        				} 
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        			
        			asExportWarehouse.setVendor(vendor);
        			asExportWarehouse.setVotesNo(votesNo);
        			asExportWarehouse.setOrganization(organization);
        			asExportWarehouse.setDepartmentId(departmentId);
        			asExportWarehouse.setUsersEx(usersEx);
        			asExportWarehouse.setUsers(users) ;
        			asExportWarehouse.setDescription(description);
        			asExportWarehouse.setLyDoXuat(lydoxuat);
        			asExportWarehouse.setNguonLayTB(nguonlayTB);
        			
        			if(amountReturn == null || amountReturn == "" || (amountReturn != null && DateTools.isValidNumber(amountReturn) == false)){
        				asExportWarehouse.setAmountReturn(Integer.parseInt("0"));
        			}else{
        				asExportWarehouse.setAmountReturn(Integer.parseInt(amountReturn));
        			}
        				
        			try {
        				if(dateReturn != null){
        					asExportWarehouse.setDateReturn(df_full.parse(dateReturn));
        				} 
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
    				
        			if (amountEx == null && productCode == null && exportDate == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
        					
    						failedList.add(asExportWarehouse);
    					} else  {
    						
    						successList.add(asExportWarehouse);
    					}
        			}
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size()); 
		System.out.println("failedList.size(): " + failedList.size());
		
		for (AsExportWarehouse record: successList) {
			try {
				record.setCreatedBy(username);
				record.setCreateDate(new Date());
				asExportWarehouseDao.insertSelective(record);
				
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
			model.addAttribute("status", "Dữ liệu xuất kho thiết bị không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép, định dạng dữ liệu không chính xác hoặc thiết bị không còn trong kho.");	// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", success.size());
		model.addAttribute("totalNum", failedList.size()+success.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		return "jspassets/asExportWarehouse/upload";
	}
	
	@RequestMapping("/ajax/getImportWarehouseByProCode")
	public @ResponseBody
	AsImportWarehouse getImportWarehouseByProCode(@RequestParam(required = false) String productCode, HttpServletRequest request) {
		String encodedurl = "";
		try {
			encodedurl = URLDecoder.decode(productCode, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		AsImportWarehouse record = asExportWarehouseDao.getAllByProCode(encodedurl);
		return record;
	} 
	
	@RequestMapping("/ajax/getSeriNoByProCode")
	public @ResponseBody
	List<AsImportWarehouse> getSeriNoByProCode(@RequestParam(required = false) String productCode, HttpServletRequest request) {
		String encodedurl = "";
		try {
			encodedurl = URLDecoder.decode(productCode, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		List<AsImportWarehouse> record = asExportWarehouseDao.getSeriNoByProCode(encodedurl);
		return record;
	}
	
	@RequestMapping("/ajax/getNXSBySerial")
	public @ResponseBody
	List<AsImportWarehouse> getNXSBySerial(@RequestParam(required = false) String productCode, 
			@RequestParam(required = false) String serialNo,
			HttpServletRequest request) {
		String encodedurl = "";
		String encodedurl1 = "";
		try {
			encodedurl = URLDecoder.decode(productCode, "UTF-8");
			encodedurl1 = URLDecoder.decode(serialNo, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		List<AsImportWarehouse> record = asImportWarehouseDAO.getNSX(encodedurl, encodedurl1);
		return record;
	}
	
	@RequestMapping("/ajax/getMaTaiSan")
	public @ResponseBody
	List<String> getMataisan(@RequestParam String term) {
		return asExportWarehouseDao.getMataisan(term);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "inPhieuDocx", method = RequestMethod.GET)
	public ModelAndView xetDuyetDocx(@RequestParam(required = false) String productCode, @RequestParam(required = false) String assetsName, @RequestParam(required = false) String serialNo,
			@RequestParam(required = false) String vendor, @RequestParam(required = false) String boPhanSd, @RequestParam(required = false) String donViSd,
			@RequestParam(required = false) String sophieu,@RequestParam(required = false) String sdate,@RequestParam(required = false) String edate,@RequestParam(required = false) String usersEx,
			ModelMap model, HttpServletRequest request,HttpServletResponse response) throws JRException, IOException {
		
		AsExportFilter filter = new AsExportFilter();
		productCode = URLDecoder.decode(productCode, "UTF-8");filter.setProductCode(productCode);
		assetsName = URLDecoder.decode(assetsName, "UTF-8");filter.setAssetsName(assetsName);
		edate = URLDecoder.decode(edate, "UTF-8");filter.setEdate(edate);
		sdate = URLDecoder.decode(sdate, "UTF-8");filter.setSdate(sdate);
		serialNo = URLDecoder.decode(serialNo, "UTF-8");filter.setSerialNo(serialNo);
		vendor = URLDecoder.decode(vendor, "UTF-8");filter.setVendor(vendor);
		boPhanSd = URLDecoder.decode(boPhanSd, "UTF-8");filter.setOrganization(boPhanSd);
		donViSd = URLDecoder.decode(donViSd, "UTF-8");filter.setDepartmentId(donViSd);
		sophieu = URLDecoder.decode(sophieu, "UTF-8"); filter.setVotesNo(sophieu);
		usersEx = URLDecoder.decode(usersEx, "UTF-8");filter.setUsersEx(usersEx);
		
		int order = 0;
		String column = "";
		List<AsExportWarehouse> asExportWarehouse = asExportWarehouseDao.getAsExportWarehouse(filter, order, column);
		

		String dateNow = df_full.format(new Date());
		String hour = df_little.format(new Date());
		InputStream inputStream = new FileInputStream (request.getSession().getServletContext().getRealPath("/report/xuatKhoDDH.jrxml")); 
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(asExportWarehouse);
		
		Map parameters = new HashMap();
		parameters.put("dateNow", dateNow);
		
		if(asExportWarehouse.size() > 0){
			parameters.put("soPhieu", asExportWarehouse.get(0).getVotesNo());
			parameters.put("nguoiThucHien", asExportWarehouse.get(0).getUsersEx()); 
			parameters.put("toVT",asExportWarehouse.get(0).getDepartmentId());
			parameters.put("lyDoXuat", asExportWarehouse.get(0).getLyDoXuat()); 
			parameters.put("nguonLayThietBi", asExportWarehouse.get(0).getNguonLayTB()); 
			parameters.put("baoGomThietBi", asExportWarehouse.get(0).getBaoGomTB());
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
		
		return new ModelAndView("jspassets/asExportWarehouse/list");
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "inPhieuXls", method = RequestMethod.GET)
	public ModelAndView xetDuyetXls(@RequestParam(required = false) String productCode, @RequestParam(required = false) String assetsName, @RequestParam(required = false) String serialNo,
			@RequestParam(required = false) String vendor, @RequestParam(required = false) String boPhanSd, @RequestParam(required = false) String donViSd,
			@RequestParam(required = false) String sophieu,@RequestParam(required = false) String sdate,@RequestParam(required = false) String edate,@RequestParam(required = false) String usersEx,
			ModelMap model, HttpServletRequest request,HttpServletResponse response) throws JRException, IOException {
		
		AsExportFilter filter = new AsExportFilter();
		productCode = URLDecoder.decode(productCode, "UTF-8");filter.setProductCode(productCode);
		assetsName = URLDecoder.decode(assetsName, "UTF-8");filter.setAssetsName(assetsName);
		edate = URLDecoder.decode(edate, "UTF-8");filter.setEdate(edate);
		sdate = URLDecoder.decode(sdate, "UTF-8");filter.setSdate(sdate);
		serialNo = URLDecoder.decode(serialNo, "UTF-8");filter.setSerialNo(serialNo);
		vendor = URLDecoder.decode(vendor, "UTF-8");filter.setVendor(vendor);
		boPhanSd = URLDecoder.decode(boPhanSd, "UTF-8");filter.setOrganization(boPhanSd);
		donViSd = URLDecoder.decode(donViSd, "UTF-8");filter.setDepartmentId(donViSd);
		sophieu = URLDecoder.decode(sophieu, "UTF-8"); filter.setVotesNo(sophieu);
		usersEx = URLDecoder.decode(usersEx, "UTF-8");filter.setUsersEx(usersEx);
		
		int order = 0;
		String column = "";
		List<AsExportWarehouse> asExportWarehouse = asExportWarehouseDao.getAsExportWarehouse(filter, order, column);
		

		String dateNow = df_full.format(new Date());
		String hour = df_little.format(new Date());
		InputStream inputStream = new FileInputStream (request.getSession().getServletContext().getRealPath("/report/xuatKhoDDH.jrxml")); 
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(asExportWarehouse);
		
		Map parameters = new HashMap();
		parameters.put("dateNow", dateNow);
		parameters.put("nguoiThucHien", URLDecoder.decode(usersEx, "UTF-8"));
		
		if(asExportWarehouse.size() > 0){
			parameters.put("toVT",asExportWarehouse.get(0).getDepartmentId());
			parameters.put("lyDoXuat", asExportWarehouse.get(0).getLyDoXuat()); 
			parameters.put("nguonLayThietBi", asExportWarehouse.get(0).getNguonLayTB()); 
		}
		
		parameters.put("thoiGian", hour + ", ngày " + dateNow);   
		
		JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition", "attachment; filename=\"xuatKhoDDH_"+dateNow+".xls\"");
		JRXlsExporter exporterXLS = new JRXlsExporter();
		//JRDocxExporter exporterDOCX = new JRDocxExporter();
		exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
		exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
		exporterXLS.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
		exporterXLS.setParameter(JRXlsExporterParameter.OFFSET_X, 0);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.FALSE);
		exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		exporterXLS.exportReport();	
		response.getOutputStream().flush();
		response.getOutputStream().close();
	 
		return new ModelAndView("jspassets/asExportWarehouse/list");
	}
}