package controller.assets;

import java.io.IOException;
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

import vo.AsExportWarehouse;
import vo.AsImportWarehouse;
import vo.AsImportWarehouseTmp;
import vo.AssetsInventorying;
import vo.AssetsManagements;
import vo.AssetsTypes;
import vo.AssetsWarranty;
import vo.CHighlightConfigs;
import vo.MDepartment;
import vo.SYS_PARAMETER;
import vo.VAssetsImportWarehouse;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.UploadTools;
import controller.BaseController;
import dao.AsExportWarehouseDAO;
import dao.AsImportWarehouseDAO;
import dao.AsImportWarehouseTmpDAO;
import dao.AssetsInventoryingDAO;
import dao.AssetsManagementsDAO;
import dao.AssetsTypesDAO;
import dao.AssetsWarrantyDAO;
import dao.CHighlightConfigsDAO;
import dao.SYS_PARAMETERDAO;
import dao.VAssetsImportWarehouseDAO;

@Controller
@RequestMapping("/assets/kiem-ke-tai-san/*")
public class AsInventoryingController extends BaseController{
	
	@Autowired
	private AsImportWarehouseDAO asImportWarehouseDAO;
	@Autowired
	private AssetsInventoryingDAO assetsInventoryingDAO;
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	@Autowired
	private AssetsWarrantyDAO assetsWarrantyDAO;
	@Autowired
	private AssetsTypesDAO assetsTypesDAO;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
	private AssetsManagementsDAO assetsManagementDao;
	@Autowired
	private AsExportWarehouseDAO asExportWarehouseDao;
	@Autowired
	private VAssetsImportWarehouseDAO vAssetsImportWarehouseDAO;
	@Autowired
	private AsImportWarehouseTmpDAO asImportWarehouseTmpDAO;
	
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
	
	//Danh sach tai san cua tung dot kiem ke
	@RequestMapping(value = "list")
	public ModelAndView list(@RequestParam(required = false) String dotKiemKe,
							 @RequestParam(required = false) String lech,
			 				 @ModelAttribute("filter") AssetsInventorying filter, ModelMap model, HttpServletRequest request, HttpServletResponse response)
	{
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "KiemKe_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		List<String> dotkiemke = assetsInventoryingDAO.getDotkiemke();
		model.addAttribute("dotKiemKeList", dotkiemke); 
		
		List<String> productCode = asExportWarehouseDao.getProductCode(); 
		model.addAttribute("productCodeList", productCode);
		
		List<SYS_PARAMETER> lechList = assetsInventoryingDAO.getLechList();
		model.addAttribute("lechList", lechList); 
		
		if(filter.getProductCode() != null)
			filter.setProductCode(filter.getProductCode().trim());
		if(filter.getProductName() != null)
			filter.setProductName(filter.getProductName().trim());
		if(filter.getSerialNo() != null)
			filter.setSerialNo(filter.getSerialNo().trim());
		if(filter.getDepartmentUse() != null)
			filter.setDepartmentUse(filter.getDepartmentUse().trim());
		if(filter.getDepartmentId() != null)
			filter.setDepartmentId(filter.getDepartmentId().trim());
		if(filter.getUsers() != null)
			filter.setUsers(filter.getUsers().trim());
		if(filter.getDotKiemKe() != null)
			filter.setDotKiemKe(filter.getDotKiemKe().trim());
		
		List<AssetsInventorying> assetsInventoryingList = new ArrayList<AssetsInventorying>();
		
		int order = 1;
		String column = "DOT_KIEM_KE";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		assetsInventoryingList = assetsInventoryingDAO.getAssetsInventoryingFilter(filter.getDotKiemKe(),lech,
				filter.getProductCode(), filter.getProductName(), filter.getSerialNo(), 
				filter.getDepartmentUse(), filter.getDepartmentId(), filter.getUsers(), 
				column, order==1?"ASC":"DESC");
		
		model.addAttribute("assetsInventoryingList", assetsInventoryingList);
		
		model.addAttribute("lech", lech);
		return new ModelAndView("jspassets/assetsInventorying/asInventoryingList");
	}
	
	//Danh sach tai san kiem ke lay tu phan nhap kho
	@RequestMapping(value = "kiem-ke")
	public ModelAndView kiemke(@RequestParam(required = false) String ngayKiemKe,
			@RequestParam(required = false) String dotKiemKe,
			@RequestParam(required = false) String asTypesId,
			@RequestParam(required = false) String productCode,
			@RequestParam(required = false) String insert,
			ModelMap model, HttpServletRequest request, HttpServletResponse response)
	{
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "KiemKe_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		if(insert != null){
			if(insert.equalsIgnoreCase("1")){
				vAssetsImportWarehouseDAO.insert();
			} 
		} 
		
		int order = 1;
		String column = "PRODUCT_CODE";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		AssetsInventorying kiemke = new AssetsInventorying();
		boolean edit = false;
		List<VAssetsImportWarehouse> assetsInventorying = new ArrayList<VAssetsImportWarehouse>();
		
		kiemke.setDotKiemKe(dotKiemKe);
		
		if(ngayKiemKe != null && DateTools.isValid("dd/MM/yyyy", ngayKiemKe) == true){
			try {
				kiemke.setImportDate(df.parse(ngayKiemKe));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			Calendar cal = Calendar.getInstance();	
			cal.setTime(new Date());
			
			ngayKiemKe = df.format(cal.getTime());
		}
		
		assetsInventorying = vAssetsImportWarehouseDAO.getList(asTypesId,productCode, column, order); 
		
		if(assetsInventorying.size() > 0){
			for(int i = 0; i < assetsInventorying.size();i++){
				if(assetsInventorying.get(i).getKiemke() == null){
					assetsInventorying.get(i).setKiemke(assetsInventorying.get(i).getAmount());
				}
			}
		}
		
		List<AssetsTypes> asTypesIDList = assetsTypesDAO.getAssetsTypesList();
 		model.addAttribute("asTypesIDList", asTypesIDList);
 		
 		List<String> productCodeList = asExportWarehouseDao.getProductCode(); 
		model.addAttribute("productCodeList", productCodeList);
		
		//get row_num, rowId  
 		int row_num = 0;
		String rowId = "";
		
		if(assetsInventorying.size() != 0){
			edit = true;
			for (int i = 0; i < assetsInventorying.size(); i++) {
				rowId += assetsInventorying.get(i).getId() + ",";
			}
			row_num = assetsInventorying.size();
			rowId = rowId.substring(0, rowId.lastIndexOf(","));
		}else{
			edit = false;
		} 
		
		model.addAttribute("row_num", row_num);
		model.addAttribute("rowId", rowId);
		model.addAttribute("assetsInventorying", assetsInventorying);
		model.addAttribute("ngayKiemKe", ngayKiemKe);
		model.addAttribute("dotKiemKe", dotKiemKe);
		model.addAttribute("asTypesId", asTypesId);
		model.addAttribute("edit", edit);
		
		return new ModelAndView("jspassets/assetsInventorying/assetsInventory");
	}
	
	//FB Form
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		AssetsInventorying assetsInventorying = (id == null ) ? new AssetsInventorying() : assetsInventoryingDAO.selectByPrimaryKey(Integer.parseInt(id));
		model.addAttribute("assetsInventorying", assetsInventorying);
		
		assetsInventoryingFormAddEdit(id, model);
		if(id == null)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			model.addAttribute("importDate", df.format(cal.getTime()));
		}
		else{
			model.addAttribute("asTypesIDCBB", assetsInventorying.getAsTypesId());
			if(assetsInventorying.getImportDate() != null)
				model.addAttribute("importDate", df.format(assetsInventorying.getImportDate()));
			if(assetsInventorying.getProduceDate() != null)
				model.addAttribute("produceDate", df.format(assetsInventorying.getProduceDate()));
			model.addAttribute("unitCBB", assetsInventorying.getUnit());
		}
		
		List<AsImportWarehouse> serialNo = asExportWarehouseDao.getSeriNoByProCode(assetsInventorying.getProductCode());
		model.addAttribute("serialNoList", serialNo);
		
		List<AssetsTypes> asTypesIDList = assetsTypesDAO.getAssetsTypesList();
 		model.addAttribute("asTypesIDList", asTypesIDList);
 		
 		String asTypesId = "";
 		if(asTypesIDList.size() > 0)
 			asTypesId = asTypesIDList.get(0).getAsTypesId();
 		
 		List<AssetsManagements> assetsManagementList = assetsWarrantyDAO.getAssetsManagementsList(asTypesId);
 		model.addAttribute("assetsManagementList", assetsManagementList);
 		
 		List<MDepartment> toXuLyList = assetsWarrantyDAO.getDepartmentDetailList();
 		model.addAttribute("toXuLyList", toXuLyList);
 		
 		List<SYS_PARAMETER> unitList = sysParameterDao.getAsImportWarehouseUnit();
 		model.addAttribute("unitList", unitList);
		
		return "jspassets/assetsInventoryingForm";
	}
	
	private void assetsInventoryingFormAddEdit(String id, ModelMap model)
	{
		if(id != null && id != "")
			model.addAttribute("assetsInventoryingFormAddEdit", "Y");
		else
			model.addAttribute("assetsInventoryingFormAddEdit", "N");
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id,
						   @RequestParam(required = false) String asTypesId,
						   @RequestParam(required = false) String importDate,
						   @RequestParam(required = false) String produceDate, 
						   @ModelAttribute("assetsInventorying") @Valid AssetsInventorying assetsInventorying, 
						   BindingResult result, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		//Ném lỗi
		if (result.hasErrors()) {
			assetsInventoryingFormAddEdit(id, model);
			
			List<AsImportWarehouse> serialNo = asExportWarehouseDao.getSeriNoByProCode(assetsInventorying.getProductCode());
			model.addAttribute("serialNoList", serialNo);
			
	 		List<AssetsManagements> assetsManagementList = assetsWarrantyDAO.getAssetsManagementsList(asTypesId);
	 		model.addAttribute("assetsManagementList", assetsManagementList);
	 		
	 		List<AssetsTypes> asTypesIDList = assetsTypesDAO.getAssetsTypesList();
	 		model.addAttribute("asTypesIDList", asTypesIDList);
	 		
	 		List<SYS_PARAMETER> unitList = sysParameterDao.getAsImportWarehouseUnit();
	 		model.addAttribute("unitList", unitList);
	 		
	 		if(result.hasFieldErrors("importDate"))
				model.addAttribute("importDateError", "importDateError");
	 		if (importDate == null || importDate.equals("") || DateTools.isValid("dd/MM/yyyy", importDate)==false)
			{
				model.addAttribute("errorImportDate", "*");
			}

	 		model.addAttribute("importDate", importDate);
	 		model.addAttribute("produceDate", produceDate);
	 		model.addAttribute("unitCBB", assetsInventorying.getUnit());
	 		model.addAttribute("asTypesIDCBB", asTypesId);
			
			return "jspassets/assetsInventoryingForm";
		}
		
		if(id == "")
		{
			AsImportWarehouse slsosach = assetsInventoryingDAO.getSlSoSach(
					assetsInventorying.getProductCode(), assetsInventorying.getSerialNo());
			
			AsExportWarehouse slDangSd = assetsInventoryingDAO.getSlDangSd(
					assetsInventorying.getProductCode(), assetsInventorying.getSerialNo());
			
			AssetsWarranty slBaoHanh = assetsInventoryingDAO.getSlBaohanh(
					assetsInventorying.getProductCode(), assetsInventorying.getSerialNo());
			
			//Luu so luong so sach theo tung dot kiem ke
			if(slsosach != null){
				assetsInventorying.setSlSoSach(slsosach.getAmount());
			}
			//Luu so luong dang su dung theo tung dot kiem ke
			if(slDangSd != null){
				assetsInventorying.setSlDangSd(slDangSd.getAmountEx());
			}
			//Luu so luong bao hanh theo tung dot kiem ke
			if(slBaoHanh != null){
				assetsInventorying.setSlBaoHanh(slBaoHanh.getQty());
			}
			
			assetsInventorying.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			assetsInventoryingDAO.insert(assetsInventorying);
			saveMessageKey(request, "messsage.confirm.addsuccess");
			
		}
		else{
			AsImportWarehouse slsosach = assetsInventoryingDAO.getSlSoSach(
					assetsInventorying.getProductCode(), assetsInventorying.getSerialNo());
			
			AsExportWarehouse slDangSd = assetsInventoryingDAO.getSlDangSd(
					assetsInventorying.getProductCode(), assetsInventorying.getSerialNo());
			
			AssetsWarranty slBaoHanh = assetsInventoryingDAO.getSlBaohanh(
					assetsInventorying.getProductCode(), assetsInventorying.getSerialNo());
			
			//Luu so luong so sach theo tung dot kiem ke
			if(slsosach != null){
				assetsInventorying.setSlSoSach(slsosach.getAmount());
			}
			//Luu so luong dang su dung theo tung dot kiem ke
			if(slDangSd != null){
				assetsInventorying.setSlDangSd(slDangSd.getAmountEx());
			}
			//Luu so luong bao hanh theo tung dot kiem ke
			if(slBaoHanh != null){
				assetsInventorying.setSlBaoHanh(slBaoHanh.getQty());
			}
			 
			assetsInventorying.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			assetsInventoryingDAO.updateByPrimaryKey(assetsInventorying);
			saveMessageKey(request, "messsage.confirm.updatesuccess");
		}
		 
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) Integer id, HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
			assetsInventoryingDAO.deleteByPrimaryKey(id);
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
		
		return "jspassets/assetsInventorying/assetsInventoryingUpload";
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
		
		return "jspassets/assetsInventorying/assetsInventoryingUpload";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent(List sheetData, ModelMap model,HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<AssetsInventorying> successList = new ArrayList<AssetsInventorying>();
		List<AssetsInventorying> failedList = new ArrayList<AssetsInventorying>();
		List<AssetsInventorying> success = new ArrayList<AssetsInventorying>();
		
		/*String asTypesId;*/
		String dotkiemke;//Dot kiem ke
		String ngaykiemke;//ngay kiem ke
		String mataisan;//Ma tai san
		String tentaisan;//Ten tai san
		String soserial;//so serial
		String donvitinh;//don vi tinh
		String kiemke;// so luong kiem ke 
		String donvisudung;//don vi su dung
		String chitietDVSD;//chi tiet don vi su dung 
		String nguoisudung;//ten nguoi su dung tai san
		String nguoikiemke;//ten nguoi kiem ke
		String ghichu;// ghi chu
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 12) {
        		saveMessageKey(request, "sidebar.admin.assetsInventoryingUploadErrorStructuresNumber");
        		
        		return "jspassets/assetsInventorying/assetsInventoryingUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		AssetsInventorying danhsachkiemke;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			danhsachkiemke = new AssetsInventorying();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=12; j++) {
     					data.add("");
     				} 
        			
        		//	asTypesId		= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			dotkiemke		= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			ngaykiemke		= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			mataisan		= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			tentaisan		= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			soserial		= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			donvitinh		= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			kiemke			= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			donvisudung		= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			chitietDVSD		= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			nguoisudung		= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			nguoikiemke		= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
        			ghichu			= data.get(11).toString().trim().equals("")?null:data.get(11).toString().trim();
        			
        			// Kiem tra loi
        			if (dotkiemke == null || ngaykiemke == null || mataisan == null || tentaisan == null
        					|| (dotkiemke != null && dotkiemke.length() > 90)
        					|| (ngaykiemke != null && DateTools.isValid("dd/MM/yyyy", ngaykiemke) == false)
        					|| (mataisan != null && mataisan.length() > 40)
        					|| (tentaisan != null && tentaisan.length() > 200)
							|| (kiemke != null && DateTools.isValidNumber(kiemke) == false)
							|| (donvitinh != null && donvitinh.length() > 40 && sysParameterDao.asImportWarehouseByUnit(donvitinh).size() == 0)
							|| (soserial != null && soserial.length() > 40)
							|| (donvisudung != null && donvisudung.length() > 40)
							|| (chitietDVSD != null && chitietDVSD.length() > 40)
							|| (nguoikiemke != null && nguoikiemke.length() > 40)
							|| (nguoisudung != null && nguoisudung.length() > 40)
							|| (ghichu != null && ghichu.length() > 900)
						//	|| (asTypesId != null && assetsTypesDAO.getAssetsTypesByAsId(asTypesId).size() == 0)
						) {
						error = true;
					}
        			
        			//--------------------------------------------------------------------------- 
        			
        			if(kiemke != null && DateTools.isValidNumber(kiemke) == true){
	        			Integer a = Integer.parseInt(kiemke);
	        			danhsachkiemke.setAmount(a);
        			}else{
        				Integer a = Integer.parseInt("0");
	        			danhsachkiemke.setAmount(a);
        			}
        			
        			if(ngaykiemke != null && DateTools.isValid("dd/MM/yyyy", ngaykiemke) == true){
        				Date simple;
						try {
							simple = df.parse(ngaykiemke);
							danhsachkiemke.setImportDate(simple);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
        			} 
        			
        			//danhsachkiemke.setAsTypesId(asTypesId);
        			danhsachkiemke.setDotKiemKe(dotkiemke);
        			danhsachkiemke.setProductCode(mataisan);
        			danhsachkiemke.setProductName(tentaisan);
        			danhsachkiemke.setSerialNo(soserial);
        			danhsachkiemke.setUnit(donvitinh); 
        			danhsachkiemke.setDepartmentUse(donvisudung);
        			danhsachkiemke.setDepartmentId(chitietDVSD);
        			danhsachkiemke.setUsers(nguoisudung);
        			
        			if(nguoikiemke != null){
        				danhsachkiemke.setCreatedBy(nguoikiemke);
        			}else{
        				danhsachkiemke.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        			} 
        			
        			danhsachkiemke.setDescription(ghichu);
        			
        			if (dotkiemke == null && ngaykiemke == null && mataisan == null && tentaisan == null && kiemke == null) {
        				// nothing
        				//System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
        					
    						failedList.add(danhsachkiemke);
    					} else  {
    						
    						successList.add(danhsachkiemke);
    					}
        			}
        		}
        	}
		}
		
	//	System.out.println("successList.size(): " + successList.size());
		
		for (AssetsInventorying record: successList) {
			try {
				/*if(assetsManagementDao.selectByProductCode(record.getProductCode()) == null)
				{
					AssetsManagements assetsManagements = new AssetsManagements();
					assetsManagements.setProductCode(record.getProductCode());
					assetsManagements.setAssetsName(record.getProductName());
					//assetsManagements.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
					assetsManagements.setAsTypesId(record.getAsTypesId());
					assetsManagementDao.insert(assetsManagements);
				}*/
				AsImportWarehouse slsosach = assetsInventoryingDAO.getSlSoSach(
						record.getProductCode(), record.getSerialNo());
				
				AsExportWarehouse slDangSd = assetsInventoryingDAO.getSlDangSd(
						record.getProductCode(), record.getSerialNo());
				
				AssetsWarranty slBaoHanh = assetsInventoryingDAO.getSlBaohanh(
						record.getProductCode(), record.getSerialNo());
				
				//Luu so luong so sach theo tung dot kiem ke
				if(slsosach != null){
					record.setSlSoSach(slsosach.getAmount());
				}
				//Luu so luong dang su dung theo tung dot kiem ke
				if(slDangSd != null){
					record.setSlDangSd(slDangSd.getAmountEx());
				}
				//Luu so luong bao hanh theo tung dot kiem ke
				if(slBaoHanh != null){
					record.setSlBaoHanh(slBaoHanh.getQty());
				}
				
				record.setCreatedBy(username);
				record.setCreateDate(new Date()); 
				assetsInventoryingDAO.insert(record);

				success.add(record);
			} catch (Exception ex) {
				
			}
		}
		
		if (failedList.size() == 0 && successList.size() > 0) {
			model.addAttribute("status", "Upload thành công.");				// Upload thành công
		} else if (failedList.size() == 0 && successList.size() == 0) {
			model.addAttribute("status", "Không có dữ liệu.");				// Không có dữ liệu
		} else {
			model.addAttribute("status", "Dữ liệu kiểm kê thiết bị không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");	// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", successList.size());
		model.addAttribute("totalNum", failedList.size()+successList.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		return "jspassets/assetsInventorying/assetsInventoryingUpload";
	}
	
	@RequestMapping(value = "form/save-kiemke")
	public @ResponseBody
	Map<String, Object> saveData(String key_kiemke, String key_id, 
			String key_asTypesId, String key_dotKiemKe, String key_ngayKiemKe,
			HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> data = new HashMap<String, Object>();
		 
		String[] value_kiemke = key_kiemke.split(",");
		String[] value_Id = key_id.split(",");
		AssetsInventorying record = new AssetsInventorying();
		AsImportWarehouseTmp importTmp = new AsImportWarehouseTmp();
		
		record.setDotKiemKe(key_dotKiemKe);

		for (int i = 0; i < value_Id.length; i++) {
			List<VAssetsImportWarehouse> getData = vAssetsImportWarehouseDAO.getDataById(value_Id[i]); 
			
			if(DateTools.isValid("dd/mm/yyyy", key_ngayKiemKe) == true && key_ngayKiemKe != null){
				try {
					record.setImportDate(df.parse(key_ngayKiemKe));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			record.setProductCode(getData.get(0).getProductCode());
			record.setProductName(getData.get(0).getProductName());
			record.setSerialNo(getData.get(0).getSerialNo());
			
			if(getData.get(0).getDangSd() != null){
				record.setSlDangSd(getData.get(0).getDangSd());
			}else{
				record.setSlDangSd(Integer.parseInt("0"));
			}
			
			if(getData.get(0).getAmount() != null){
				record.setSlSoSach(getData.get(0).getAmount());
			}else{
				record.setSlSoSach(Integer.parseInt("0"));
			}
			
			if(getData.get(0).getBaoHanh() != null){
				record.setSlBaoHanh(getData.get(0).getBaoHanh());
			}else{
				record.setSlBaoHanh(Integer.parseInt("0"));
			}  
			
			record.setDepartmentId(getData.get(0).getDepartmentId());
			record.setDepartmentUse(getData.get(0).getOrganization());
			
			if(value_kiemke[i] != null && DateTools.isValidNumber(value_kiemke[i]) == true){
				record.setAmount(Integer.parseInt(value_kiemke[i]));
				importTmp.setKiemKe(Integer.parseInt(value_kiemke[i]));
			}else{
				record.setAmount(Integer.parseInt("0"));
				importTmp.setKiemKe(Integer.parseInt("0"));
			}
			
			record.setCreateDate(new Date());
			record.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			
			importTmp.setId(Integer.parseInt(value_Id[i]));
			
			asImportWarehouseTmpDAO.updateByPrimaryKeySelective(importTmp);
			assetsInventoryingDAO.insertSelective(record);
			
			//System.out.println("insert thanh cong");
		}
		
		data.put("status", "success");
		return data;
	}
}
