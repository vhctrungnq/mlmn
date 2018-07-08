package controller.cable;

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

import vo.CHighlightConfigs;
import vo.Cabledropet4tlmgw;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.alarm.utils.Cabledropet4tlmgwFilter;
import vo.alarm.utils.Helper;
import vo.alarm.utils.UploadTools;

import controller.BaseController;
import dao.CHighlightConfigsDAO;
import dao.Cabledropet4tlmgwDAO;
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
@RequestMapping("/alarm/cableET4MGW/*")
public class CableET4TLMgwController extends BaseController{
	@Autowired
	private Cabledropet4tlmgwDAO cableET4MgwDao;
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
			System.out.println("Highligh:"+highlightConfigList2.get(0).getStyle());
			highlight = " $(this).find('.NOT_NULL').css({"+highlightConfigList2.get(0).getStyle()+"});";
		}
		return highlight;
	}

	@RequestMapping(value = "list")
	public ModelAndView list(@ModelAttribute("filter") Cabledropet4tlmgwFilter filter, 
			@RequestParam(required = false) Integer delData, ModelMap model, HttpServletRequest request) {
		
		List<SYS_PARAMETER> title = cableET4MgwDao.titleForm("CABLE_DROP_ET4_TLMGW_LIST");
		model.addAttribute("title", title.get(0).getValue());
		
		int order = 0;
		String column = "";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("cableDropeT4TlMgw").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("cableDropeT4TlMgw").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
	
		if(filter.getSystem() != null)
			filter.setSystem(filter.getSystem().trim());
		if(filter.getFrSlotPort() != null)
			filter.setFrSlotPort(filter.getFrSlotPort().trim());
		if(filter.getDdfEt4() != null)
			filter.setDdfEt4(filter.getDdfEt4().trim());
		List<Cabledropet4tlmgw> Cabledropet4tlmgwList = new ArrayList<Cabledropet4tlmgw>();
		try
		{
			Cabledropet4tlmgwList = cableET4MgwDao.getList(filter, column, order, delData);
		}
		catch (Exception exp)
		{
			Cabledropet4tlmgwList= null;
		} 
		 
		model.addAttribute("Cabledropet4tlmgwList", Cabledropet4tlmgwList);
		model.addAttribute("filter", filter);
		model.addAttribute("sizeList", 		Cabledropet4tlmgwList==null?0:Cabledropet4tlmgwList.size());
		 
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "Cabledropet4tlmgw_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		//Check Role User(Administrator)
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SysUsers> userList = sysUserDao.checkRole(username);
		Helper.checkRole(userList, model);		
		return new ModelAndView("jspalarm/cable/CableET4TLMgwList");
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) Integer id, HttpServletRequest request) {
		
		try {
			cableET4MgwDao.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch (Exception e) {
			saveMessageKey(request, "messsage.confirm.deletefailure");
		}
		return "redirect:list.htm";
	}
	
	@RequestMapping(value="form", method=RequestMethod.GET)
    public String showForm(@RequestParam(required = false) Integer id, ModelMap model) {
		
		Cabledropet4tlmgw cabledropet4tlmgw;
		
		if (id == null) {
			List<SYS_PARAMETER> titleF = cableET4MgwDao.titleForm("ET4TL_MGW_ADD");		 
			model.addAttribute("titleF", titleF.get(0).getValue());
			cabledropet4tlmgw = new Cabledropet4tlmgw();
		} else {
			List<SYS_PARAMETER> titleF = cableET4MgwDao.titleForm("ET4TL_MGW_UPDATE");	
			model.addAttribute("titleF", titleF.get(0).getValue());
			cabledropet4tlmgw = cableET4MgwDao.selectById(id);
		}

		model.addAttribute("cabledropet4tlmgw", cabledropet4tlmgw);
		
		return "jspalarm/cable/CableET4TLMgwForm";
	}
	
	@RequestMapping(value="form", method=RequestMethod.POST)
    public String formPost(@ModelAttribute("cabledropet4tlmgw") @Valid Cabledropet4tlmgw cabledropet4tlmgw, BindingResult result, 
    		ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (result.hasErrors()) {
			List<SYS_PARAMETER> titleF = cableET4MgwDao.titleForm("ET4TL_MGW_ADD");		 
			model.addAttribute("titleF", titleF.get(0).getValue());
			
			model.addAttribute("cabledropet4tlmgw", cabledropet4tlmgw);
			
			return "jspalarm/cable/CableET4TLMgwForm";
		}
		
		if (cabledropet4tlmgw.getId() == null) { 
			try{
			
				if(cableET4MgwDao.getEt4MgwExist(cabledropet4tlmgw.getSystem(), cabledropet4tlmgw.getFrSlotPort(), null).size() == 0){
				
					cabledropet4tlmgw.setCreatedBy(username);
					
					cableET4MgwDao.insert(cabledropet4tlmgw);
					saveMessageKey(request, "contactNumber.insertSucceFull"); 
				}
				else{
					List<SYS_PARAMETER> titleF = cableET4MgwDao.titleForm("ET4TL_MGW_ADD");		 
					model.addAttribute("titleF", titleF.get(0).getValue());
					
					model.addAttribute("cabledropet4tlmgw", cabledropet4tlmgw);
					saveMessageKey(request, "ET4TLMgw.exits");
					return "jspalarm/cable/CableET4TLMgwForm";
				}
			}
			catch(Exception e){
				List<SYS_PARAMETER> titleF = cableET4MgwDao.titleForm("ET4TL_MGW_ADD");		 
				model.addAttribute("titleF", titleF.get(0).getValue());
				
				model.addAttribute("cabledropet4tlmgw", cabledropet4tlmgw);
				saveMessageKey(request, "ET4TLMgw.exits");
				return "jspalarm/cable/CableET4TLMgwForm";
			}
			
		} else {  
			try{
				if(cableET4MgwDao.getEt4MgwExist(cabledropet4tlmgw.getSystem(), cabledropet4tlmgw.getFrSlotPort(), cabledropet4tlmgw.getId().toString()).size() == 0){
					cabledropet4tlmgw.setCreatedBy(username);
					cableET4MgwDao.updateByPrimaryKey(cabledropet4tlmgw);
					saveMessageKey(request, "contactNumber.updateSucceFull"); 
				}
				else{
					List<SYS_PARAMETER> titleF = cableET4MgwDao.titleForm("ET4TL_MGW_ADD");		 
					model.addAttribute("titleF", titleF.get(0).getValue());
					
					model.addAttribute("cabledropet4tlmgw", cabledropet4tlmgw);
					saveMessageKey(request, "ET4TLMgw.exits");
					return "jspalarm/cable/CableET4TLMgwForm";
				}
			}
			catch(Exception e){
				List<SYS_PARAMETER> titleF = cableET4MgwDao.titleForm("ET4TL_MGW_ADD");		 
				model.addAttribute("titleF", titleF.get(0).getValue());
				
				model.addAttribute("cabledropet4tlmgw", cabledropet4tlmgw);
				saveMessageKey(request, "ET4TLMgw.exits");
				return "jspalarm/cable/CableET4TLMgwForm";
			}
		}
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(Model model) {
		List<SYS_PARAMETER> titleU = cableET4MgwDao.titleForm("ET4TL_MGW_UPLOAD"); 
		model.addAttribute("titleU", titleU.get(0).getValue());
		return "jspalarm/cable/CableET4TLMgwUpload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("filePath") MultipartFile filePath, Model model, HttpServletRequest request) throws IOException, ParseException {
		
		List<SYS_PARAMETER> titleU = cableET4MgwDao.titleForm("ET4TL_MGW_UPLOAD"); 
		model.addAttribute("titleU", titleU.get(0).getValue());
		
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
		
		return "jspalarm/cable/CableET4TLMgwUpload"; 
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent (List sheetData, Model model, HttpServletRequest request) throws IOException, ParseException {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<Cabledropet4tlmgw> successList = new ArrayList<Cabledropet4tlmgw>();
		List<Cabledropet4tlmgw> failedList = new ArrayList<Cabledropet4tlmgw>();
		List<Cabledropet4tlmgw> success = new ArrayList<Cabledropet4tlmgw>();

		String system;
		String frSlotPort;
		String ddfEt4;
		String description;

		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 4) {
        		
        		saveMessage(request, "Định dạng tệp không đúng"); 
        		return "jspalarm/cable/CableET4TLMgwUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		/*List footer= (List) sheetData.get(sheetData.size() - 1);
        		
        		while (footer.size() != 4 && sheetData.size() > 2) {
        			boolean kt=true;
        			for (int k=0;k<footer.size();k++) {
        				if (!footer.get(k).toString().trim().equals("")) {
        					kt=false;
        				}
        			}
        			if (kt==true) {
	        			sheetData.remove(sheetData.size()-1);
	        		//	maxsize=sheetData.size();
        			}
        			else {
        				for (int j=footer.size(); j<=11; j++) {
        					footer.add("");
         				}
        			}
        			
	        		footer= (List)sheetData.get(sheetData.size()-1);
        		}*/
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			Cabledropet4tlmgw cabledropet4tlmgwList = new Cabledropet4tlmgw();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=4; j++) {
     					data.add("");
     				}
        			 
        			system			= data.get(0).toString().equals("")?null:data.get(0).toString();
        			frSlotPort		= data.get(1).toString().equals("")?null:data.get(1).toString();
        			ddfEt4			= data.get(2).toString().equals("")?null:data.get(2).toString();
        			description		= data.get(3).toString().equals("")?null:data.get(3).toString();
        			
        			// Kiem tra loi
					if (system == null || frSlotPort == null ||  ddfEt4 == null
							|| (system != null && system.length() > 40)
							|| (frSlotPort != null && frSlotPort.length() > 40)
							|| (ddfEt4 != null && ddfEt4.length() > 40)
							|| (description != null && description.length() > 500)) 
					{ 
						error = true;
					}
					
					
					cabledropet4tlmgwList.setSystem(system);
					cabledropet4tlmgwList.setFrSlotPort(frSlotPort);
					cabledropet4tlmgwList.setDdfEt4(ddfEt4);
					cabledropet4tlmgwList.setDescription(description);
					
					
					if (system == null && frSlotPort == null  && ddfEt4 == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(cabledropet4tlmgwList);
    					} else  {
    						successList.add(cabledropet4tlmgwList);
    					}
        			} 
				}
        	}
		}
		
		for (Cabledropet4tlmgw item: successList) {  
			
			Cabledropet4tlmgw cabledropet4tlmgwOld = cableET4MgwDao.selectByPrimaryKey(
					item.getSystem(), item.getFrSlotPort(), item.getDdfEt4());
			
				if (cabledropet4tlmgwOld != null) {  
					item.setCreatedBy(username);
					item.setCreatDate(new Date());
					item.setId(cabledropet4tlmgwOld.getId());
					
					cableET4MgwDao.updateByPrimaryKey(item);
				} 
				else
				{  
					item.setCreatedBy(username);
					item.setCreatDate(new Date());
					cableET4MgwDao.insertSelective(item);
				}
				success.add(item);
		}
		
		if (failedList.size() == 0 && successList.size() > 0) {
			model.addAttribute("status", "Upload thành công.");				// Upload thành công
		} else if (failedList.size() == 0 && successList.size() == 0) {
			model.addAttribute("status", "Không có dữ liệu.");				// Không có dữ liệu
		} else {
			model.addAttribute("status", "Dữ liệu ET4 TL MGW không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");					// Upload lỗi
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
		String exportFileName = "ET4TLMgw_" + dateNow;
				
		model.addAttribute("exportFileName", exportFileName);
		
		return "jspalarm/cable/CableET4TLMgwUpload";
	}
}
