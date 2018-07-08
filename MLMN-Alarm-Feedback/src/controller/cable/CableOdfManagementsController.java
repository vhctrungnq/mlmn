package controller.cable;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.multipart.MultipartFile;

import vo.CHighlightConfigs;
import vo.CableOdfManagements;
import vo.CableOdfTypes;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.alarm.utils.Helper;
import vo.alarm.utils.UploadTools;
import controller.BaseController;
import dao.CHighlightConfigsDAO;
import dao.CableOdfManagementsDAO;
import dao.CableOdfTypesDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUsersDAO;
/**
 * 
 * @author QuangBD
 * @UpdateBy ThangPX
 * @UpdatedDate 01/04/2014
 * @Purpose Display Cable List and check role.
 *
 */
@Controller
@RequestMapping("/cable/thong-tin-odf-lien-tang/*")
public class CableOdfManagementsController extends BaseController{

	@Autowired
	private CableOdfManagementsDAO cableOdfManagementsDAO;
	@Autowired
	private CableOdfTypesDAO cableOdfTypesDAO;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDAO;
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	@Autowired 
	private SysUsersDAO  sysUserDao;
	
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
    public String list(@RequestParam(required = false) String schemaName,
    				   @RequestParam(required = false) String name, 
    				   @RequestParam(required = false) Integer delData,
    				   @RequestParam(required = false) String vendor, 
    				   @RequestParam(required = false) String schemaLink,
    				   @RequestParam(required = false) String nameLink,
    				   ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "ThongTinODFLienTang_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		int order = 1;
		String column = "";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		
		if(name != null)
			name = name.trim();
		
		
		List<CableOdfTypes> cableOdfTypesList = cableOdfTypesDAO.getCableOdfTypesList();
		model.addAttribute("cableOdfTypesList", cableOdfTypesList);
		
		List<CableOdfManagements> cableOdfManList =  cableOdfManagementsDAO.getCableOdfManFilter(schemaName, name, vendor, schemaLink, nameLink, column, order == 1 ? "ASC" : "DESC", delData);
		model.addAttribute("cableOdfManList", cableOdfManList);
		model.addAttribute("schemaName", schemaName);
		model.addAttribute("name", 	name);
		model.addAttribute("vendor", vendor);
		model.addAttribute("schemaLink", schemaLink);
		model.addAttribute("nameLink", nameLink);
		model.addAttribute("sizeList", 	cableOdfManList.size());
		//Check Role User(Administrator)
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SysUsers> userList = sysUserDao.checkRole(username);
		Helper.checkRole(userList, model);			
		return "jspcable/cableOdfManagementsList";
	}
	
	//Cable Odf Form
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, 
			@RequestParam(required = false) String schemaLink,
			@RequestParam(required = false) String nameLink,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		CableOdfManagements cableOdfManagements = (id == null) ? new CableOdfManagements() : cableOdfManagementsDAO.selectByPrimaryKey(Integer.parseInt(id));
		model.addAttribute("cableOdfManagementsForm", cableOdfManagements);
		
		List<SYS_PARAMETER> getIsEnableCableOdfList = sysParameterDAO.getIsEnableCableOdfList();
		model.addAttribute("getIsEnableCableOdfList", getIsEnableCableOdfList);
		
		List<CableOdfTypes> cableOdfTypesList = cableOdfTypesDAO.getCableOdfTypesList();
		model.addAttribute("cableOdfTypesList", cableOdfTypesList);
		model.addAttribute("trangThaiCBB", cableOdfManagements.getIsEnable());
		model.addAttribute("idOdfTypesCBB", cableOdfManagements.getIdOdfTypes());
		tenSoDoAddEdit(id, model);
		model.addAttribute("schemaLink", schemaLink);
		model.addAttribute("nameLink", nameLink);
		return "jspcable/cableOdfManagementsForm";
	}
	
	private void tenSoDoAddEdit(String id, ModelMap model){
		if(id != null && id != "")
			model.addAttribute("tenSoDoAddEdit", "Y");
		else
			model.addAttribute("tenSoDoAddEdit", "N");
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id, 
			@RequestParam(required = false) String schemaLink,
			@RequestParam(required = false) String nameLink,
			@ModelAttribute("cableOdfManagementsForm") @Valid CableOdfManagements cableOdfManagements, BindingResult result, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		//Ném lỗi
		if (result.hasErrors()) {
			
			List<SYS_PARAMETER> getIsEnableCableOdfList = sysParameterDAO.getIsEnableCableOdfList();
			model.addAttribute("getIsEnableCableOdfList", getIsEnableCableOdfList);
			
			List<CableOdfTypes> cableOdfTypesList = cableOdfTypesDAO.getCableOdfTypesList();
			model.addAttribute("cableOdfTypesList", cableOdfTypesList);
			
			model.addAttribute("trangThaiCBB", cableOdfManagements.getIsEnable());
			model.addAttribute("idOdfTypesCBB", cableOdfManagements.getIdOdfTypes());
			tenSoDoAddEdit(id, model);
			
			
			if(result.hasFieldErrors("ordering"))
				model.addAttribute("sapXepError", "sapXepError");
			if (result.hasFieldErrors("port"))
				model.addAttribute("portError", "portError");
			model.addAttribute("schemaLink", schemaLink);
			model.addAttribute("nameLink", nameLink);
            return "jspcable/cableOdfManagementsForm";
		}
		
		if(id == "")
		{
			if(cableOdfManagementsDAO.testInsertPortSchemaname(cableOdfManagements.getIdOdfTypes().toString(),cableOdfManagements.getPort().toString(), null).size() == 0)
			{
				cableOdfManagements.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				
				cableOdfManagementsDAO.insert(cableOdfManagements);
				saveMessageKey(request, "messsage.confirm.addsuccess");
			}
			else
			{
				saveMessageKey(request, "message.schemaName.cableOdfManagementsTonTai");
				
				List<SYS_PARAMETER> getIsEnableCableOdfList = sysParameterDAO.getIsEnableCableOdfList();
				model.addAttribute("getIsEnableCableOdfList", getIsEnableCableOdfList);
				
				List<CableOdfTypes> cableOdfTypesList = cableOdfTypesDAO.getCableOdfTypesList();
				model.addAttribute("cableOdfTypesList", cableOdfTypesList);
				tenSoDoAddEdit(id, model);
				model.addAttribute("trangThaiCBB", cableOdfManagements.getIsEnable());
				model.addAttribute("idOdfTypesCBB", cableOdfManagements.getIdOdfTypes());
				model.addAttribute("schemaLink", schemaLink);
				model.addAttribute("nameLink", nameLink);
	            return "jspcable/cableOdfManagementsForm";
			}
		}
		else
		{
			if(cableOdfManagementsDAO.testInsertPortSchemaname(cableOdfManagements.getIdOdfTypes().toString(),cableOdfManagements.getPort().toString(), id).size() == 0)
			{
				cableOdfManagements.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				
				cableOdfManagementsDAO.updateByPrimaryKey(cableOdfManagements);
				saveMessageKey(request, "messsage.confirm.updatesuccess");
			}
			else{
				saveMessageKey(request, "message.schemaName.cableOdfManagementsTonTai");
				
				List<SYS_PARAMETER> getIsEnableCableOdfList = sysParameterDAO.getIsEnableCableOdfList();
				model.addAttribute("getIsEnableCableOdfList", getIsEnableCableOdfList);
				
				List<CableOdfTypes> cableOdfTypesList = cableOdfTypesDAO.getCableOdfTypesList();
				model.addAttribute("cableOdfTypesList", cableOdfTypesList);
				tenSoDoAddEdit(id, model);
				model.addAttribute("trangThaiCBB", cableOdfManagements.getIsEnable());
				model.addAttribute("idOdfTypesCBB", cableOdfManagements.getIdOdfTypes());
				model.addAttribute("schemaLink", schemaLink);
				model.addAttribute("nameLink", nameLink);
	            return "jspcable/cableOdfManagementsForm";
			}
		}
		
		if(schemaLink != null && !schemaLink.equals("")){
			if(nameLink != null && !nameLink.equals(""))
				return "redirect:list.htm?schemaLink="+schemaLink+"&nameLink="+nameLink;
			else
				return "redirect:list.htm?schemaLink="+schemaLink;
		}	
		else
			return "redirect:list.htm";
	}
	
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) Integer id, 
			@RequestParam(required = false) String schemaLink,
			@RequestParam(required = false) String nameLink,
			ModelMap model,
			HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
			cableOdfManagementsDAO.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch(Exception e)
		{
			saveMessageKey(request, "message.confirm.deleteOther");
			}
		if(schemaLink != null && !schemaLink.equals("")){
			if(nameLink != null && !nameLink.equals(""))
				return "redirect:list.htm?schemaLink="+schemaLink+"&nameLink="+nameLink;
			else
				return "redirect:list.htm?schemaLink="+schemaLink;
		}	
		else
			return "redirect:list.htm";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(@RequestParam(required = false) String schemaLink,
			@RequestParam(required = false) String nameLink,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("schemaLink", schemaLink);
		model.addAttribute("nameLink", nameLink);
		cableOdfTypesList(model);
		return "jspcable/cableOdfManagementsUpload";
	}
	
	private void cableOdfTypesList(ModelMap model){
		List<CableOdfTypes> cableOdfTypesList = cableOdfTypesDAO.getCableOdfTypesList();
		model.addAttribute("cableOdfTypesList", cableOdfTypesList);
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam (required = true) Integer idOdfTypes, 
			@RequestParam(required = false) String schemaLink,
			@RequestParam(required = false) String nameLink,
			@RequestParam("file") MultipartFile filePath,  ModelMap model, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		model.addAttribute("schemaLink", schemaLink);
		model.addAttribute("nameLink", nameLink);
		if (!filePath.isEmpty()) {

			String[] ten=filePath.getOriginalFilename().split("\\.");
			String fileExtn = ten[ten.length-1];
			
			if (fileExtn.equals("xls")) {
				if (fileExtn.equals("xls")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsFile2(filePath.getInputStream());
					
					importContent(sheetData,idOdfTypes, model,request);
					
				}
			}
			else {
				 saveMessageKey(request, "cautruc.typeFIle");
			}
		}
		else {
			saveMessageKey(request, "cautruc.emptyFile");
		}
		
		cableOdfTypesList(model);
		model.addAttribute("idOdfTypesCBB", idOdfTypes);
		
		return "jspcable/cableOdfManagementsUpload";
	}
	
	private String importContent(List sheetData, Integer idOdfTypes, ModelMap model,HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<CableOdfManagements> successList = new ArrayList<CableOdfManagements>();
		List<CableOdfManagements> failedList = new ArrayList<CableOdfManagements>();
		List<CableOdfManagements> success = new ArrayList<CableOdfManagements>();
		
		String name;
		String port;
		String vendor;
		String isEnable;
		String ordering;
		String description;
		CableOdfTypes cableOdfTypes = cableOdfTypesDAO.selectByPrimaryKey(idOdfTypes);
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 6) {
        		saveMessageKey(request, "sidebar.admin.cableOdfManagementsUploadErrorStructuresNumber");
        		cableOdfTypesList(model);
        		model.addAttribute("idOdfTypesCBB", idOdfTypes);
        		
        		return "jspcable/cableOdfManagementsUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		CableOdfManagements cableTransmission;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			cableTransmission = new CableOdfManagements();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=6; j++) {
     					data.add("");
     				}
        			name					= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			port					= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			vendor					= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			isEnable				= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			ordering				= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			description				= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			
        			cableTransmission.setName(name);
        			
        			cableTransmission.setDescription(description);
        			cableTransmission.setIdOdfTypes(idOdfTypes);
        			
        			// Kiem tra loi
        			if (port == null 
							//|| (name != null && name.length() > 250)
							//|| port.length() > 4
							//|| (ordering != null && ordering.length() > 4)
							//|| (description != null && description.length() > 500)
						) {
						error = true;
					}
        			
        			try{
	        			if(port != null){
		        			Integer a = Integer.parseInt(port);
		        			System.out.println(a);
	        			}
	        		}
	        		catch(Exception e){
	        			error = true;
	        		}
        			
        			if(isEnable != null && !isEnable.toLowerCase().equals("sử dụng")
        					&& !isEnable.toLowerCase().equals("không sử dụng")
        					&& !isEnable.toLowerCase().equals("lỗi")
        					&& !isEnable.toLowerCase().equals("khác")){
        				error = true;
        			}
        			
        			try{
	        			if(ordering != null){
		        			Integer b = Integer.parseInt(ordering);
		        			System.out.println(b);
	        			}
	        		}
	        		catch(Exception e){
	        			error = true;
	        		}
        			
        			//--------------------------------------------------------------------------- 
        			
    				cableTransmission.setSchemaName(cableOdfTypes.getSchemaName());
    				cableTransmission.setIsEnableStr(isEnable);
    				cableTransmission.setVendor(vendor);
        			if ((name == null && port == null)) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
        					//cableTransmission.setIsEnable(isEnable);
        					cableTransmission.setPortStr(port);
    						cableTransmission.setOrderingStr(ordering);
        					
    						failedList.add(cableTransmission);
    					} else  {
    						if(isEnable != null){
	    						if(isEnable.toLowerCase().equals("sử dụng"))
	    							cableTransmission.setIsEnable("Y");
	    						else  if(isEnable.toLowerCase().equals("không sử dụng"))
	    							cableTransmission.setIsEnable("N");	
	    						else  if(isEnable.toLowerCase().equals("lỗi"))
	    							cableTransmission.setIsEnable("E");	
	    						else  if(isEnable.toLowerCase().equals("khác"))
	    							cableTransmission.setIsEnable("O");	
    						}
    						else{
    							cableTransmission.setIsEnable("Y");
    						}
    						cableTransmission.setPort(Integer.parseInt(port));
    						if(ordering != null)
    							cableTransmission.setOrdering(Integer.parseInt(ordering));
    						
    						successList.add(cableTransmission);
    					}
        			}
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (CableOdfManagements record: successList) {
			try {
				List<CableOdfManagements> items = cableOdfManagementsDAO.testInsertPortSchemaname(idOdfTypes.toString(), record.getPort().toString(), null);
	    			
				if(items.size() == 0){
					record.setCreatedBy(username);
					cableOdfManagementsDAO.insert(record);
				}
				else{
					record.setModifiedBy(username);
					record.setId(items.get(0).getId());
					cableOdfManagementsDAO.updateByPrimaryKey(record);
				}
				
				record.setSchemaName(cableOdfTypes.getSchemaName());
				success.add(record);
			} catch (Exception ex) {
				//failedList.add(record);
			}
		}
		
		if (failedList.size() == 0 && successList.size() > 0) {
			model.addAttribute("status", "Upload thành công.");				// Upload thành công
		} else if (failedList.size() == 0 && successList.size() == 0) {
			model.addAttribute("status", "Không có dữ liệu.");				// Không có dữ liệu
		} else {
			model.addAttribute("status", "Dữ liệu cable không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");	// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", successList.size());
		model.addAttribute("totalNum", failedList.size()+successList.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		cableOdfTypesList(model);
		model.addAttribute("idOdfTypesCBB", idOdfTypes);
		
		return "jspcable/cableOdfManagementsUpload";
	}

}
