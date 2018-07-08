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

import controller.BaseController;

import vo.CHighlightConfigs;
import vo.Cabledropet4tp;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.alarm.utils.Cabledropet4tpFilter;
import vo.alarm.utils.Helper;
import vo.alarm.utils.UploadTools;
import dao.CHighlightConfigsDAO;
import dao.Cabledropet4tpDAO;
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
@RequestMapping("/alarm/cableET4TP/*")
public class Cabledropet4tpController extends BaseController {
	@Autowired
	private Cabledropet4tpDAO cabledropet4tpDAO;  
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
	public ModelAndView list(@ModelAttribute("filter") Cabledropet4tpFilter filter, 
			@RequestParam(required = false) Integer delData, ModelMap model, HttpServletRequest request) {
		
		List<SYS_PARAMETER> title = cabledropet4tpDAO.titleForm("CABLE_DROP_ET4_TP_LIST");
		model.addAttribute("title", title.get(0).getValue());
		
		int order = 0;
		String column = "";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("cableDropeT4Tp").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("cableDropeT4Tp").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		
		if(filter.getArea() != null)
			filter.setArea(filter.getArea().trim());
		if(filter.getInfo() != null)
			filter.setInfo(filter.getInfo().trim());
	
		List<Cabledropet4tp> cabledropet4tpList = cabledropet4tpDAO.getList(filter, column, order, delData);
		
		model.addAttribute("cabledropet4tpList", cabledropet4tpList);
		model.addAttribute("filter", filter);
		model.addAttribute("sizeList", 		cabledropet4tpList==null?0:cabledropet4tpList.size());
		 
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "Cabledropet4tp_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		//Check Role User(Administrator)
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SysUsers> userList = sysUserDao.checkRole(username);
		Helper.checkRole(userList, model);		
		return new ModelAndView("jspalarm/cable/cabledropet4tpList");
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) Integer id, HttpServletRequest request) {
		
		try {
			cabledropet4tpDAO.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch (Exception e) {
			saveMessageKey(request, "messsage.confirm.deletefailure");
		}
		return "redirect:list.htm";
	}
	
	@RequestMapping(value="form", method=RequestMethod.GET)
    public String showForm(@RequestParam(required = false) Integer id, ModelMap model) {
		
		Cabledropet4tp cabledropet4tp;
		
		if (id == null) {
			List<SYS_PARAMETER> titleF = cabledropet4tpDAO.titleForm("ET4TP_NUMBER_ADD");		 
			model.addAttribute("titleF", titleF.get(0).getValue());
			cabledropet4tp = new Cabledropet4tp();
		} else {
			List<SYS_PARAMETER> titleF = cabledropet4tpDAO.titleForm("ET4TP_NUMBER_UPDATE");	
			model.addAttribute("titleF", titleF.get(0).getValue());
			cabledropet4tp = cabledropet4tpDAO.selectById(id);
		}

		model.addAttribute("cabledropet4tp", cabledropet4tp);
		
		return "jspalarm/cable/CableET4TPForm";
	}
	
	@RequestMapping(value="form", method=RequestMethod.POST)
    public String formPost(@ModelAttribute("cabledropet4tp") @Valid Cabledropet4tp cabledropet4tp, BindingResult result, 
    		ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (result.hasErrors())
		{
			List<SYS_PARAMETER> titleF = cabledropet4tpDAO.titleForm("ET4TP_NUMBER_ADD");		 
			model.addAttribute("titleF", titleF.get(0).getValue());
			model.addAttribute("cabledropet4tp", cabledropet4tp);	
			return "jspalarm/cable/CableET4TPForm";
		}
		
		if (cabledropet4tp.getId() == null) { 
			if(cabledropet4tpDAO.getEt4TpExist( cabledropet4tp.getDdfStart(),  cabledropet4tp.getDdfFinish(), null).size() == 0){
				cabledropet4tp.setCreatedBy(username);
				cabledropet4tpDAO.insert(cabledropet4tp);
				saveMessageKey(request, "contactNumber.insertSucceFull");  
			}
			else{
				List<SYS_PARAMETER> titleF = cabledropet4tpDAO.titleForm("ET4TP_NUMBER_ADD");		 
				model.addAttribute("titleF", titleF.get(0).getValue());
				
				model.addAttribute("cabledropet4tp", cabledropet4tp);
				saveMessageKey(request, "ET4TP.exits");
				
				return "jspalarm/cable/CableET4TPForm";
			}
			
			
		} else {  
			if(cabledropet4tpDAO.getEt4TpExist( cabledropet4tp.getDdfStart(),  cabledropet4tp.getDdfFinish(), cabledropet4tp.getId().toString()).size() == 0){

				cabledropet4tp.setCreatedBy(username);
				cabledropet4tpDAO.updateByPrimaryKey(cabledropet4tp);
				saveMessageKey(request, "contactNumber.updateSucceFull");
			} 
			else{
				List<SYS_PARAMETER> titleF = cabledropet4tpDAO.titleForm("ET4TP_NUMBER_UPDATE");	
				model.addAttribute("titleF", titleF.get(0).getValue());
				model.addAttribute("cabledropet4tp", cabledropet4tp);
				saveMessageKey(request, "ET4TP.exits");            
				return "jspalarm/cable/CableET4TPForm";
			}
			
			 
		}
		 return "redirect:list.htm";
	
  }	
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(Model model) {
		List<SYS_PARAMETER> titleU = cabledropet4tpDAO.titleForm("ET4TP_NUMBER_UPLOAD"); 
		model.addAttribute("titleU", titleU.get(0).getValue());
		return "jspalarm/cable/CableET4TPUpload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("filePath") MultipartFile filePath, Model model, HttpServletRequest request) throws IOException, ParseException {
		
		List<SYS_PARAMETER> titleU = cabledropet4tpDAO.titleForm("ET4TP_NUMBER_UPLOAD"); 
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
		
		return "jspalarm/cable/CableET4TPUpload"; 
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent (List sheetData, Model model, HttpServletRequest request) throws IOException, ParseException {
		
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<Cabledropet4tp> successList = new ArrayList<Cabledropet4tp>();
		List<Cabledropet4tp> failedList = new ArrayList<Cabledropet4tp>();
		List<Cabledropet4tp> success = new ArrayList<Cabledropet4tp>();

		String area;
		String ddfStart;
		String transission;
		String channelName;
		String ddfFinish;
		String description;
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 6) {
        		
        		saveMessage(request, "Định dạng tệp không đúng"); 
        		return "jspalarm/cable/CableET4TPUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		/*List footer= (List) sheetData.get(sheetData.size() - 1);
        		
        		while (footer.size() != 6 && sheetData.size() > 2) {
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
        			
        			Cabledropet4tp cabledropet4tpList = new Cabledropet4tp();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=6; j++) {
     					data.add("");
     				}
        			 
        			area			= data.get(0).toString().equals("")?null:data.get(0).toString();
        			ddfStart		= data.get(1).toString().equals("")?null:data.get(1).toString();
        			transission		= data.get(2).toString().equals("")?null:data.get(2).toString();
        			channelName		= data.get(3).toString().equals("")?null:data.get(3).toString();
        			ddfFinish		= data.get(4).toString().equals("")?null:data.get(4).toString();
        			description		= data.get(5).toString().equals("")?null:data.get(5).toString();
        			
        			// Kiem tra loi
					if (ddfStart == null || ddfFinish == null || area == null
							|| (area != null && area.length() > 40)
							|| (ddfStart != null && ddfStart.length() > 40)
							|| (transission != null && transission.length() > 40)
							|| (channelName != null && channelName.length() > 40)
							|| (ddfFinish != null && ddfFinish.length() > 40)
							|| (description != null && description.length() > 490))
					{ 
						error = true;
					}
					
					
					cabledropet4tpList.setArea(area);
					cabledropet4tpList.setDdfStart(ddfStart);
					cabledropet4tpList.setTransission(transission);
					cabledropet4tpList.setChannelName(channelName);
					cabledropet4tpList.setDdfFinish(ddfFinish);
					cabledropet4tpList.setDescription(description);
					
					if (area == null && ddfStart == null  && ddfFinish == null ) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(cabledropet4tpList);
    					} else  {
    						successList.add(cabledropet4tpList);
    					}
        			} 
				}
        	}
		}
		
		// import
		for (Cabledropet4tp item: successList) {  
			
			Cabledropet4tp cabledropet4tpOld = cabledropet4tpDAO.selectByPrimaryKey(
					item.getArea(), item.getDdfStart(), item.getDdfFinish());
			
				if (cabledropet4tpOld != null) {  
					item.setCreatedBy(username);
					item.setCreatDate(new Date());
					item.setId(cabledropet4tpOld.getId());
					
					cabledropet4tpDAO.updateByPrimaryKey(item);
				} 
				else
				{  
					item.setCreatedBy(username);
					item.setCreatDate(new Date());
					cabledropet4tpDAO.insertSelective(item);
				}
				success.add(item);
		}
		
		if (failedList.size() == 0 && successList.size() > 0) {
			model.addAttribute("status", "Upload thành công.");				// Upload thành công
		} else if (failedList.size() == 0 && successList.size() == 0) {
			model.addAttribute("status", "Không có dữ liệu.");				// Không có dữ liệu
		} else {
			model.addAttribute("status", "Dữ liệu ET4 TP không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");					// Upload lỗi
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
		String exportFileName = "ET4TP_" + dateNow;
				
		model.addAttribute("exportFileName", exportFileName);
		
		return "jspalarm/cable/CableET4TPUpload";
	}

}
