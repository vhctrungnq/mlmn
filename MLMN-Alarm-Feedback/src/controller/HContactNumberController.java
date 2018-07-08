package controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView; 
 
import vo.CHighlightConfigs;
import vo.HContactNumber;
import vo.SYS_PARAMETER;
import vo.alarm.utils.HContactNumberFilter;
import vo.alarm.utils.UploadTools;
import bsh.ParseException; 
import dao.CHighlightConfigsDAO;
import dao.HContactNumberDAO;
import dao.SysUsersDAO;

@Controller
@RequestMapping("/system/contactNumber/*")
public class HContactNumberController extends BaseController {
	@Autowired
	private HContactNumberDAO hContactNumberDao;  
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
	public ModelAndView list(@ModelAttribute("filter") HContactNumberFilter filter, Model model, HttpServletRequest request) {
		
		List<SYS_PARAMETER> title = hContactNumberDao.titleForm("CONTACT_NUMBER_LIST");		
		model.addAttribute("title", title.get(0).getValue());
		loadData(model,filter.getTypeError());
		int order = 0;
		String column = "";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("contactNumber").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("contactNumber").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
	
		List<HContactNumber> contactNumberList = new ArrayList<HContactNumber>();
		try
		{
			contactNumberList = hContactNumberDao.getList(filter, column, order);
		}
		catch (Exception exp)
		{
			contactNumberList= null;
		}
		
		
		
		model.addAttribute("contactNumberList", contactNumberList);
		model.addAttribute("filter", filter);
		 
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "ContactNumber_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		return new ModelAndView("jsp/ContactNumber/contactNumberList");
	}
	 
	@RequestMapping(value = "getteam")
	public @ResponseBody
	List<String> getTeam(@RequestParam String term) {
		
		List<String> getTypeError = hContactNumberDao.getTypeError(term);
		return getTypeError;
	}
	private void loadData(Model model,String type)
	{
		// Danh sach goi nho To vien thong
		List<String> teamList = hContactNumberDao.getTypeError(type);
		String teamArray="var teamList = new Array(";
		String cnId="";
		for (int i=0;i<teamList.size();i++) {
			teamArray = teamArray + cnId +"\""+teamList.get(i)+"\"";
			cnId=",";
		}
		teamArray = teamArray+");";
		model.addAttribute("teamList", teamArray);
		
		// Danh sach goi nho chuc vu
		List<SYS_PARAMETER> regencyList = hContactNumberDao.getRegency();
		String regencyArray="var regencyList = new Array(";
		String regencyId="";
		for (int i=0;i<regencyList.size();i++) {
			regencyArray = regencyArray + regencyId +"\""+regencyList.get(i).getValue()+"\"";
			regencyId=",";
		}
		regencyArray = regencyArray+");"; 
		model.addAttribute("regencyList", regencyArray);
		 
	}
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) Integer id, HttpServletRequest request) {
		
		try {
			hContactNumberDao.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch (Exception e) {
			saveMessageKey(request, "messsage.confirm.deletefailure");
		}
		return "redirect:list.htm";
	}
	
	@RequestMapping(value="form", method=RequestMethod.GET)
    public String showForm(@RequestParam(required = false) Integer id, Model  model) {
		
		HContactNumber contactNumber;
		loadData(model,"");
		List<SYS_PARAMETER> titleAdd = hContactNumberDao.titleForm("CONTACT_NUMBER_ADD");	
		List<SYS_PARAMETER> titleUpdate = hContactNumberDao.titleForm("CONTACT_NUMBER_UPDATE");
		/*List<SYS_PARAMETER> getTypeError = hContactNumberDao.getTypeError();
		List<SYS_PARAMETER> getRegency = hContactNumberDao.getRegency();
		model.addAttribute("getTypeErrorList", getTypeError);
		model.addAttribute("getRegencyList", getRegency);*/
		if (id == null) {  
			model.addAttribute("titleF", titleAdd.get(0).getValue());
			contactNumber = new HContactNumber();
		} else { 
			model.addAttribute("titleF", titleUpdate.get(0).getValue());
			contactNumber = hContactNumberDao.selectByPrimaryKey(id);
		} 
		
		
		model.addAttribute("contactNumber", contactNumber);
		
		return "jsp/ContactNumber/contactNumberForm";
	}
	
	@RequestMapping(value="form", method=RequestMethod.POST)
    public String formPost(@ModelAttribute("contactNumber") @Valid HContactNumber contactNumber, BindingResult result, 
    		ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		HContactNumber OldcontactNumber = hContactNumberDao.selectByPrimaryKey(contactNumber.getArea(), contactNumber.getName(), contactNumber.getPhoneNumber());
		
		List<SYS_PARAMETER> titleAdd = hContactNumberDao.titleForm("CONTACT_NUMBER_ADD");	
		List<SYS_PARAMETER> titleUpdate = hContactNumberDao.titleForm("CONTACT_NUMBER_UPDATE");
		/*List<SYS_PARAMETER> getTypeError = hContactNumberDao.getTypeError();
		List<SYS_PARAMETER> getRegency = hContactNumberDao.getRegency();
		
		model.addAttribute("getTypeErrorList", getTypeError);
		model.addAttribute("getRegencyList", getRegency);*/
		
		if (contactNumber.getId() == null) { 
			if (result.hasErrors()) { 
				model.addAttribute("titleF", titleAdd.get(0).getValue()); 
				model.addAttribute("contactNumber", contactNumber); 
				
				return "jsp/ContactNumber/contactNumberForm";
			}
			
			if(OldcontactNumber != null){ 
				model.addAttribute("titleF", titleAdd.get(0).getValue());
				model.addAttribute("contactNumber", contactNumber); 
				saveMessageKey(request, "contactNumber.exits");
				
				return "jsp/ContactNumber/contactNumberForm";
			}
			
			contactNumber.setUpdateDate(new Date());
			contactNumber.setUpdateBy(username);
			
			hContactNumberDao.insert(contactNumber);
			saveMessageKey(request, "contactNumber.insertSucceFull"); 
		} else {  
			if (result.hasErrors()) {  
				model.addAttribute("titleF", titleUpdate.get(0).getValue());
				model.addAttribute("contactNumber", contactNumber);
				
				return "jsp/ContactNumber/contactNumberForm";
			} 
			
			if(OldcontactNumber != null && !OldcontactNumber.getId().equals(contactNumber.getId())){
				model.addAttribute("titleF", titleUpdate.get(0).getValue());
				model.addAttribute("contactNumber", contactNumber);
				
				saveMessageKey(request, "contactNumber.exits"); 
				return "jsp/ContactNumber/contactNumberForm";
			}
			
			contactNumber.setUpdateDate(new Date());
			contactNumber.setUpdateBy(username);
			
			hContactNumberDao.updateByPrimaryKey(contactNumber);
			saveMessageKey(request, "contactNumber.updateSucceFull"); 
		}
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(Model model) {
		List<SYS_PARAMETER> titleU = hContactNumberDao.titleForm("CONTACT_NUMBER_UPLOAD"); 
		model.addAttribute("titleU", titleU.get(0).getValue());
		return "jsp/ContactNumber/contactNumberUpload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("filePath") MultipartFile filePath, Model model, HttpServletRequest request) throws IOException, ParseException {
		
		List<SYS_PARAMETER> titleU = hContactNumberDao.titleForm("CONTACT_NUMBER_UPLOAD"); 
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
		return "jsp/ContactNumber/contactNumberUpload"; 
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent (List sheetData, Model model, HttpServletRequest request) throws IOException, ParseException {
		List<SYS_PARAMETER> titleU = hContactNumberDao.titleForm("CONTACT_NUMBER_UPLOAD"); 
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<HContactNumber> successList = new ArrayList<HContactNumber>();
		List<HContactNumber> failedList = new ArrayList<HContactNumber>();
		List<HContactNumber> success = new ArrayList<HContactNumber>();

		String area;
		String typeError;
		String name;
		String phoneNumber; 
		String email;
		String regency;
		String ghiChu;
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 7) {
        		model.addAttribute("titleU", titleU.get(0).getValue());
        		saveMessageKey(request, "Định dạng tệp không đúng"); 
        		return "jsp/ContactNumber/contactNumberUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		List footer= (List) sheetData.get(sheetData.size() - 1);
        		
        		while (footer.size() != 7 && sheetData.size() > 2) {
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
        				for (int j=footer.size(); j<=4; j++) {
        					footer.add("");
         				}
        			}
        			
	        		footer= (List)sheetData.get(sheetData.size()-1);
        		}
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			HContactNumber contactNumberList = new HContactNumber();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=6; j++) {
     					data.add("");
     				}
        			
        			area 		= data.get(0).toString().equals("")?null:data.get(0).toString();
        			typeError	= data.get(1).toString().equals("")?null:data.get(1).toString();
        			name		= data.get(2).toString().equals("")?null:data.get(2).toString(); 
        			phoneNumber	= data.get(3).toString().equals("")?null:data.get(3).toString(); 
        			email		= data.get(4).toString().equals("")?null:data.get(4).toString(); 
        			regency		= data.get(5).toString().equals("")?null:data.get(5).toString(); 
        			ghiChu		= data.get(6).toString().equals("")?null:data.get(6).toString(); 
        			
					if (area == null || name == null || phoneNumber == null 
							|| (area != null && area.length() >= 500)
							|| (name != null && name.length() >= 200)
							|| (phoneNumber != null && phoneNumber.length() >= 100)
							|| (typeError != null && typeError.length() >= 100)
							|| (email != null && email.length() >= 90)
							|| (ghiChu != null && ghiChu.length() >= 900)
							|| (regency != null && regency.length() >= 200)) 
					{ 
						error = true;
					} 
					
					contactNumberList.setArea(area);
					contactNumberList.setName(name);
					contactNumberList.setPhoneNumber(phoneNumber);
					contactNumberList.setRegency(regency);
					contactNumberList.setTypeError(typeError);
					contactNumberList.setEmail(email);
					contactNumberList.setGhiChu(ghiChu);
					
					if (area == null && name == null  && phoneNumber == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(contactNumberList);
    					} else  {
    						successList.add(contactNumberList);
    					}
        			} 
				}
        	}
		}
		 
		for (HContactNumber item: successList) {  
			
			HContactNumber contactNumberOld = hContactNumberDao.selectByPrimaryKey(item.getArea(), item.getName(), item.getPhoneNumber());
				
				if (contactNumberOld != null) { 
					item.setUpdateDate(new Date());
					item.setUpdateBy(username); 
					item.setId(contactNumberOld.getId());
					
					hContactNumberDao.updateByPrimaryKey(item);
				} 
				else
				{ 
					item.setUpdateDate(new Date());
					item.setUpdateBy(username);
					
					hContactNumberDao.insertSelective(item);
				}
				success.add(item);
		}
		
		if (failedList.size() == 0 && successList.size() > 0) {
			model.addAttribute("status", "Upload thành công.");				// Upload thành công
		} else if (failedList.size() == 0 && successList.size() == 0) {
			model.addAttribute("status", "Không có dữ liệu.");				// Không có dữ liệu
		} else {
			model.addAttribute("status", "Dữ liệu không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");					// Upload lỗi
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
		String exportFileName = "ContactNumber_" + dateNow;
				
		model.addAttribute("exportFileName", exportFileName);
		model.addAttribute("titleU", titleU.get(0).getValue());		
		return "jsp/ContactNumber/contactNumberUpload";
	}
}
