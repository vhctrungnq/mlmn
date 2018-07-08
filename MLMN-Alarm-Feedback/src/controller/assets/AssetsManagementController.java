package controller.assets;

import java.io.IOException;
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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import bsh.ParseException;

import vo.AsImportWarehouse;
import vo.AssetsManagements;
import vo.AssetsTypes;
import vo.CHighlightConfigs;  
import vo.SYS_PARAMETER;
import vo.alarm.utils.AssetsManagementsFilter;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.UploadTools;
import controller.BaseController;
import dao.AsImportWarehouseDAO;
import dao.AssetsManagementsDAO;
import dao.AssetsTypesDAO;
import dao.CHighlightConfigsDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUsersDAO;
 

@Controller
@RequestMapping("/assets/danh-muc-tai-san/*")
public class AssetsManagementController extends BaseController {
	@Autowired
	private AsImportWarehouseDAO asImportWarehouseDAO;
	@Autowired
	private AssetsManagementsDAO assetsManagementDao;  
	@Autowired
	private AssetsTypesDAO assetsTypesDAO;
	@Autowired
	private SYS_PARAMETERDAO  sysParameterDao;
	@Autowired 
	private SysUsersDAO sysUsersDao; 
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
	
	@RequestMapping(value = "list")
	public ModelAndView list(@ModelAttribute("filter") AssetsManagementsFilter filter,  
			@RequestParam(required = false) String sTime,
			@RequestParam(required = false) String eTime,
			Model model, HttpServletRequest request) {
		
		List<SYS_PARAMETER> title = assetsManagementDao.titleForm("ASSETS_MANAGEMENT_LIST");		
		model.addAttribute("title", title.get(0).getValue());
		
		int order = 0;
		String column = "";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("assetsManagement").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("assetsManagement").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		} 
		
		List<AssetsTypes> asTypesIDList = assetsTypesDAO.getAssetsTypesList();
 		model.addAttribute("asTypesIDList", asTypesIDList);
 		
		/*List<AssetsTypes> asTypesNameList = assetsManagementDao.getAsTypesName(); 
		model.addAttribute("asTypesNameList", asTypesNameList);*/
		
		List<AssetsManagements> assetsManagement = assetsManagementDao.getList(filter, column, order) ;
		 
		model.addAttribute("assetsManagement", assetsManagement);
		model.addAttribute("filter", filter);
		 
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "DanhMucTaiSan_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		return new ModelAndView("jspassets/assetsManagement/list");
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) Integer id, HttpServletRequest request) {
		
		AssetsManagements assetsManagement = assetsManagementDao.selectByPrimaryKey(id);
		
		List<AsImportWarehouse> asImportWarehouse = asImportWarehouseDAO.testAsImportWarehouse(
				assetsManagement.getProductCode(), null);
		
		if(asImportWarehouse.size() > 0){
			saveMessageKey(request, "messsage.confirm.deletefail");
		}else{
			try {
				assetsManagementDao.deleteByPrimaryKey(id);
				saveMessageKey(request, "messsage.confirm.deletesuccess");
			}
			catch (Exception e) {
				saveMessageKey(request, "messsage.confirm.deletefailure");
			}
		} 
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value="form", method=RequestMethod.GET)
    public String showForm(@RequestParam(required = false) Integer id, ModelMap model) {
		
		AssetsManagements assetsManagement;
		
		List<SYS_PARAMETER> titleAdd = assetsManagementDao.titleForm("ASSETS_MANAGEMENT_ADD");	
		List<SYS_PARAMETER> titleUpdate = assetsManagementDao.titleForm("ASSETS_MANAGEMENT_UPDATE"); 
		List<SYS_PARAMETER> unitList = assetsManagementDao.unitList();
		
		List<AssetsTypes> asTypesNameList = assetsManagementDao.getAsTypesName(); 
		
		if (id == null) {  
			model.addAttribute("titleF", titleAdd.get(0).getValue());
			assetsManagement = new AssetsManagements();
		} else { 
			model.addAttribute("titleF", titleUpdate.get(0).getValue());
			assetsManagement = assetsManagementDao.selectByPrimaryKey(id);
		} 
		
		model.addAttribute("unitList", unitList);
		model.addAttribute("asTypesNameList", asTypesNameList);
		model.addAttribute("assetsManagement", assetsManagement);
		
		return "jspassets/assetsManagement/form";
	}
	
	@RequestMapping(value="form", method=RequestMethod.POST)
    public String formPost(@ModelAttribute("assetsManagement") @Valid AssetsManagements assetsManagement, BindingResult result, 
    		ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		AssetsManagements assetsManagement_Old = 
						assetsManagementDao.selectByProductCode(assetsManagement.getProductCode());
		
		List<SYS_PARAMETER> titleAdd = assetsManagementDao.titleForm("ASSETS_MANAGEMENT_ADD");	
		List<SYS_PARAMETER> titleUpdate = assetsManagementDao.titleForm("ASSETS_MANAGEMENT_UPDATE");
		List<SYS_PARAMETER> unitList = assetsManagementDao.unitList();
	
		List<AssetsTypes> asTypesNameList = assetsManagementDao.getAsTypesName();
		
		model.addAttribute("asTypesNameList", asTypesNameList);    
		
		if (assetsManagement.getId() == null) { 
			if (result.hasErrors()) { 
				model.addAttribute("titleF", titleAdd.get(0).getValue()); 
				model.addAttribute("assetsManagement", assetsManagement); 
				model.addAttribute("unitList", unitList);
				
				return "jspassets/assetsManagement/form";
			}
			
			if(assetsManagement_Old != null){ 
				model.addAttribute("titleF", titleAdd.get(0).getValue());
				model.addAttribute("assetsManagement", assetsManagement); 
				model.addAttribute("unitList", unitList);
				saveMessageKey(request, "assetsManagement.exits");
				
				return "jspassets/assetsManagement/form";
			}
			
			assetsManagement.setCreateDate(new Date());
			assetsManagement.setCreatedBy(username);
			
			assetsManagementDao.insertSelective(assetsManagement);
			saveMessageKey(request, "assetsManagement.insertSucceFull"); 
		} else {  
			if (result.hasErrors()) {  
				model.addAttribute("titleF", titleUpdate.get(0).getValue());
				model.addAttribute("assetsManagement", assetsManagement);
				model.addAttribute("unitList", unitList);
				
				return "jspassets/assetsManagement/form";
			} 
			
			if(assetsManagement_Old != null && !assetsManagement_Old.getId().equals(assetsManagement.getId())){
				model.addAttribute("titleF", titleUpdate.get(0).getValue());
				model.addAttribute("assetsManagement", assetsManagement);
				model.addAttribute("unitList", unitList);
				
				saveMessageKey(request, "assetsManagement.exits"); 
				return "jspassets/assetsManagement/form";
			}
			
			assetsManagement.setModifyDate(new Date());
			assetsManagement.setModifiedBy(username);
			
			assetsManagementDao.updateByPrimaryKey(assetsManagement);
			saveMessageKey(request, "assetsManagement.updateSucceFull"); 
		}
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(Model model) {
		List<SYS_PARAMETER> titleU = assetsManagementDao.titleForm("ASSETS_MANAGEMENT_UPLOAD"); 
		model.addAttribute("titleU", titleU.get(0).getValue());
		return "jspassets/assetsManagement/upload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("filePath") MultipartFile filePath, Model model, HttpServletRequest request) throws IOException, ParseException {
		
		List<SYS_PARAMETER> titleU = assetsManagementDao.titleForm("ASSETS_MANAGEMENT_UPLOAD"); 
		if (!filePath.isEmpty()) { 
			String[] ten=filePath.getOriginalFilename().split("\\.");
			String fileExtn = ten[ten.length-1];
			
			if (fileExtn.equals("xlsx")||fileExtn.equals("xls")) {
				if (fileExtn.equals("xlsx")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsxFile(filePath.getInputStream());
					importContent(sheetData,model,request);
				}
				
				if (fileExtn.equals("xls")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsFile(filePath.getInputStream());
					importContent(sheetData,model,request);
				}
			}
			else {
				 saveMessageKey(request, "cautruc.typeFIle");
			}
		}
		else {
			saveMessageKey(request, "cautruc.emptyFile");
		}
		model.addAttribute("titleU", titleU.get(0).getValue());
		return "jspassets/assetsManagement/upload"; 
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent (List sheetData, Model model, HttpServletRequest request) throws IOException, ParseException {
		List<SYS_PARAMETER> titleU = assetsManagementDao.titleForm("ASSETS_MANAGEMENT_UPLOAD");
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<AssetsManagements> successList = new ArrayList<AssetsManagements>();
		List<AssetsManagements> failedList = new ArrayList<AssetsManagements>();
		List<AssetsManagements> success = new ArrayList<AssetsManagements>();

		String asTypesId;
		String productCode;
		String assetsName; 
		String unit;
		String ordering;
		String description;
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 6) {
        		model.addAttribute("titleU", titleU.get(0).getValue());
        		saveMessageKey(request, "Định dạng tệp không đúng"); 
        		return "jspassets/assetsManagement/upload";
        	}
        	
        	if (sheetData.size() > 1) {
        		AssetsManagements assetsManagement;
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false; 
        			
        			assetsManagement = new AssetsManagements();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=6; j++) {
     					data.add("");
     				} 
        			
        			asTypesId 		= data.get(0).toString().equals("")?null:data.get(0).toString();
        			productCode		= data.get(1).toString().equals("")?null:data.get(1).toString(); 
        			assetsName		= data.get(2).toString().equals("")?null:data.get(2).toString();
        			unit		= data.get(3).toString().equals("")?null:data.get(3).toString(); 
        			ordering	= data.get(4).toString().equals("")?null:data.get(4).toString();
        			description		= data.get(5).toString().equals("")?null:data.get(5).toString(); 
        			
					if (asTypesId == null || productCode == null || assetsName == null
							|| (ordering != null && DateTools.isValidNumber(ordering) == false)
							|| (asTypesId != null && asTypesId.length() >= 50)
							|| (productCode != null && productCode.length() >= 50)
							|| (assetsName != null && assetsName.length() >= 255) 
							|| (unit != null && unit.length() >= 50)
							|| (ordering != null && ordering.length() >= 5)
							|| (description != null && description.length() >= 1000)
							|| (asTypesId != null && assetsManagementDao.getCountAssetsTypes(asTypesId) == 0)) 
					{ 
						error = true;
					}
					
					
					assetsManagement.setAsTypesId(asTypesId);
					assetsManagement.setProductCode(productCode);
					assetsManagement.setAssetsName(assetsName); 
					assetsManagement.setUnit(unit);
					try{
						assetsManagement.setOrdering(Integer.parseInt(ordering));
					}catch(NumberFormatException e){}
					
					assetsManagement.setDescription(description);
					
					if (asTypesId == null && productCode == null  && assetsName == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(assetsManagement);
    					} else  {
    						successList.add(assetsManagement);
    					}
        			} 
				}
        	}
		}
		 
		for (AssetsManagements item: successList) {  
			AssetsTypes assetsTypes = new AssetsTypes();
			SYS_PARAMETER sysParameter = new SYS_PARAMETER();
			AssetsManagements assetsManagementOld = assetsManagementDao.selectByProductCode(item.getProductCode());
					
				if (assetsManagementOld != null) { 
					item.setModifiedBy(username);
					item.setModifyDate(new Date());
					item.setId(assetsManagementOld.getId());
					
					assetsManagementDao.updateByPrimaryKeySelective(item);
				} 
				else
				{ 
					if(assetsTypesDAO.selectByPrimaryKey(item.getAsTypesId()) == null){
						assetsTypes.setAsTypesName(item.getAsTypesId());
						assetsTypes.setAsTypesId(item.getAsTypesId());
						
						assetsTypesDAO.insertSelective(assetsTypes);
					} 
					item.setCreatedBy(username);
					item.setCreateDate(new Date());
					
					assetsManagementDao.insertSelective(item);
				}
				
				List<SYS_PARAMETER> sysParameterOld = sysParameterDao.getSPByCodeAndName("ASSETS_MANAGEMENT_UNIT", item.getUnit());
				
				if(sysParameterOld.size() == 0){
					sysParameter.setCode("ASSETS_MANAGEMENT_UNIT");
					sysParameter.setName(item.getUnit());
					sysParameter.setValue(item.getUnit());
					
					sysParameterDao.insertSelective(sysParameter);
				}
				
				success.add(item);
		}
		
		if (failedList.size() == 0 && successList.size() > 0) {
			model.addAttribute("status", "Upload thành công.");				// Upload thành công
		} else if (failedList.size() == 0 && successList.size() == 0) {
			model.addAttribute("status", "Không có dữ liệu.");				// Không có dữ liệu
		} else {
			model.addAttribute("status", "Danh mục thiết bị không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");					// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", successList.size());
		model.addAttribute("totalNum", failedList.size()+successList.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList); 
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "DanhMucTaiSan_" + dateNow;
				
		model.addAttribute("exportFileName", exportFileName);
		model.addAttribute("titleU", titleU.get(0).getValue());		
		return "jspassets/assetsManagement/upload";
	}
}
