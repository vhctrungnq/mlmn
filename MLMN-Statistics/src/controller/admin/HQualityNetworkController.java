package controller.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import bsh.ParseException;

import vn.com.vhc.vmsc2.statistics.dao.HQualityNetworkDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.SysUsersDAO;
import vn.com.vhc.vmsc2.statistics.domain.HQualityNetwork;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.SysUsers;
import vn.com.vhc.vmsc2.statistics.web.controller.BaseController;
import vn.com.vhc.vmsc2.statistics.web.utils.UploadTools;

/**
 * Function        : Quan ly quality network
 * Created By      : BUIQUANG
 * Create Date     :
 * Modified By     : 
 * Modify Date     : 11/02/2014
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/network/quality-network/*")
public class HQualityNetworkController extends BaseController{

	@Autowired
	private HQualityNetworkDAO HQualityNetworkDAO;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	@Autowired
	private SysUsersDAO sysUsersDao;
	@ModelAttribute("highlight")
	public String highlight() {
		String highlight = "";
		List<HighlightConfig> highlightConfigList2 = highlightConfigDao.getByKey("NOT_NULL");
		if (highlightConfigList2.size()>0)
		{ 
			highlight = " $(this).find('.NOT_NULL').css({"+highlightConfigList2.get(0).getStyle()+"});";
		}
		return highlight;
	}
	
	@RequestMapping(value="list")
	public ModelAndView list(@ModelAttribute("filter") HQualityNetwork filter, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		// Danh sach ten nhom
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
		boolean checkRoleManager = false;
		if (userLogin.getIsRoleManager().equals("Y")) {
			checkRoleManager = true;
		}
		model.addAttribute("checkRoleManager", checkRoleManager);		
		int order = 1;
		String column = "ORDERING";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "QualityNetwork_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		if(filter.getGroupName() != null)
			filter.setGroupName(filter.getGroupName().trim());
		if(filter.getQualityName() != null)
			filter.setQualityName(filter.getQualityName().trim());
		if(filter.getQualityCode() != null)
			filter.setQualityCode(filter.getQualityCode().trim());
		
		List<HQualityNetwork>  qualityNetworkList = HQualityNetworkDAO.getHQualityNetwork
				(
						filter.getGroupName(), 
						filter.getQualityName(), 
						filter.getQualityCode(), 
						column, 
						order ==1 ? "ASC" : "DESC"
				);
		model.addAttribute("qualityNetworkList", qualityNetworkList);

		return new ModelAndView("admin/qualityNetworkList");
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = false) Integer id, HttpServletRequest request) {

		HQualityNetworkDAO.deleteByPrimaryKey(id);
		saveMessageKey(request, "messsage.confirm.deletesuccess");
		return "redirect:list.htm";
	}
	
	private void HQualityNetworkAddEdit(String id, ModelMap model)
	{
		if(id != null && id != "")
			model.addAttribute("HQualityNetworkAddEdit", "Y");
		else
			model.addAttribute("HQualityNetworkAddEdit", "N");
	}
	
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, ModelMap model, HttpServletRequest request, HttpServletResponse response, HttpSession session, WebRequest webRequest) {
		
		HQualityNetwork qualityNetwork = (id==null) ? new HQualityNetwork() : HQualityNetworkDAO.selectByPrimaryKey(Integer.parseInt(id));
		model.addAttribute("qualityNetwork", qualityNetwork);
		HQualityNetworkAddEdit( id, model);
		
		return "admin/qualityNetworkForm";
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id, @ModelAttribute("qualityNetwork") @Valid HQualityNetwork qualityNetwork, BindingResult result, HttpServletRequest request,ModelMap model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (result.hasErrors())
		{
			if(result.hasFieldErrors("ordering"))
				model.addAttribute("orderingError", "orderingError");
			HQualityNetworkAddEdit(id, model);
			return "admin/qualityNetworkForm";
		}
		
		if(id == "")
		{
			if(HQualityNetworkDAO.checkUniqueCodeQuaNet(qualityNetwork.getQualityCode(), null).size() == 0 ){
				qualityNetwork.setCreatedBy(username);
				HQualityNetworkDAO.insert(qualityNetwork);
				saveMessageKey(request, "messsage.confirm.addsuccess");
			}
			else{
				saveMessage(request, "Quality Code đã tồn tại trong hệ thống");
				
				HQualityNetworkAddEdit(id, model);
				return "admin/qualityNetworkForm";
			}
		}
		else
		{
			if(HQualityNetworkDAO.checkUniqueCodeQuaNet(qualityNetwork.getQualityCode(), id).size()==0){
				qualityNetwork.setModifiedBy(username);
				HQualityNetworkDAO.updateByPrimaryKey(qualityNetwork);
				saveMessageKey(request, "messsage.confirm.updatesuccess");
			}
			else{
				saveMessage(request, "Quality Code đã tồn tại trong hệ thống");
				
				HQualityNetworkAddEdit(id, model);
				return "admin/qualityNetworkForm";
			}
		}
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm() {
		return "admin/qualityNetworkUpload";
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
		
		return "admin/qualityNetworkUpload";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent(List sheetData, ModelMap model,HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<HQualityNetwork> successList = new ArrayList<HQualityNetwork>();
		List<HQualityNetwork> failedList = new ArrayList<HQualityNetwork>();
		List<HQualityNetwork> success = new ArrayList<HQualityNetwork>();
		
		String groupName;
		String qualityCode;
		String qualityName;
		String qualityCondition;
		String qualityValue;
		String qualityUnit;
		String ordering;

		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 7 && heard.size() != 8) {
        		saveMessageKey(request, "sidebar.admin.qualityNetworkUploadErrorStructuresNumber");
        		
        		return "admin/qualityNetworkUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		HQualityNetwork hQualityNetwork;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			hQualityNetwork = new HQualityNetwork();
        			
        			List data= (List) sheetData.get(i);
        			if(heard.size() == 7){
	        			for (int j=data.size(); j<=7; j++) {
	     					data.add("");
	     				}
	        			groupName				= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
	        			qualityCode				= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
	        			qualityName				= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
	        			qualityCondition		= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
	        			qualityValue			= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
	        			qualityUnit				= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
	        			ordering				= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			}
	    			else{
	    				for (int j=data.size(); j<=8; j++) {
	     					data.add("");
	     				}
	    				groupName				= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
	    				qualityCode				= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
	    				qualityName				= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
	    				qualityCondition		= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
	    				qualityValue			= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
	    				qualityUnit				= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
	    				ordering				= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
	    			}
        			// Kiem tra loi
        			if (groupName == null || qualityCode == null || qualityName == null
        					
						) {
						error = true;
					}
        			
        			try{
	        			if(ordering != null){
		        			Integer a = Integer.parseInt(ordering);
		        			hQualityNetwork.setOrdering(a);
	        			}
        			}
	        		catch(Exception e){
	        			error = true;
	        		}
        			
        			//---------------------------------------------------------------------------
        			hQualityNetwork.setGroupName(groupName);
        			hQualityNetwork.setQualityCode(qualityCode);
        			hQualityNetwork.setQualityName(qualityName);
        			hQualityNetwork.setQualityCondition(qualityCondition);
        			hQualityNetwork.setQualityValue(qualityValue);
        			hQualityNetwork.setQualityUnit(qualityUnit);
        			
        			if (groupName == null && qualityCode == null && qualityName == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(hQualityNetwork);
    					} else  {
    						
    						successList.add(hQualityNetwork);
    					}
        			}
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (HQualityNetwork record: successList) {
			try {
					if(HQualityNetworkDAO.checkUniqueCodeQuaNet(record.getQualityCode(), null).size()==0){
						record.setCreatedBy(username);
						HQualityNetworkDAO.insertSelective(record);
					}
					else{
						record.setModifiedBy(username);
						HQualityNetworkDAO.updateByQualityCode(record);
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
			model.addAttribute("status", "Dữ liệu quality network không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");	// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", success.size());
		model.addAttribute("totalNum", failedList.size()+success.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		return "admin/qualityNetworkUpload";
	}
}
