package controller.admin;

import java.io.IOException;
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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import vo.SYS_PARAMETER;
import vo.SysStandardizedError;
import vo.alarm.utils.UploadTools;

import controller.BaseController;
import dao.SYS_PARAMETERDAO;
import dao.SysStandardizedErrorDAO;
/**
 * Function        : Quan ly loi chuan hoa
 * Created By      : AnhCTV
 * Create Date     :
 * Modified By     : 
 * Modify Date     : 
 * @author AnhCTV
 * Description     :
 */
@Controller
@RequestMapping("/admin/chuan-hoa-loi/*")
public class SysStandardizedErrorController extends BaseController{

	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
	private SysStandardizedErrorDAO sysStandardizedErrorDao;
	
	@RequestMapping(value="danh-sach")
	public ModelAndView list( @RequestParam(required = false) String vendor, @RequestParam(required = false) String network,
			@RequestParam(required = false) String alarmName, Model model,HttpServletRequest request) {
	
		int order = 1;
		String column = "ORDERING";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		if (vendor!=null&& !vendor.equals(""))
		{
			vendor=vendor.trim();
		}
		if (network!=null&& !network.equals(""))
		{
			network=network.trim();
		}
		if (alarmName!=null&& !alarmName.equals(""))
		{
			alarmName=alarmName.trim();
		}
		
		List<SYS_PARAMETER> titleSystem = sysStandardizedErrorDao.titleSysStandardizedError("LIST");
		if (titleSystem.size()>0)
		{
			model.addAttribute("titleSystem", titleSystem.get(0).getValue());
		}
		
		model.addAttribute("standardList", sysStandardizedErrorDao.getStandardizedErrorFilter(vendor,network,alarmName, column, order ==1 ? "ASC" : "DESC"));
		model.addAttribute("vendor", vendor);
		model.addAttribute("network", network);
		model.addAttribute("alarmName", alarmName);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "ChuanHoaLoi_"+dateNow;
		model.addAttribute("exportFileName", exportFileName);
		  
		return new ModelAndView("jspadmin/chuanhoaloi/standardList"); 
	}
	
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String show(@RequestParam(required = false) String id,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		List<SYS_PARAMETER> titleSystem = sysStandardizedErrorDao.titleSysStandardizedError("ADD");
		if (titleSystem.size()>0)
		{
			model.addAttribute("titleSystem", titleSystem.get(0).getValue());
		}
		
		SysStandardizedError sysStandardError = (id == null) ? new SysStandardizedError() : sysStandardizedErrorDao.selectByPrimaryKey(Integer.parseInt(id));
		model.addAttribute("standardForm", sysStandardError);
	
		return "jspadmin/chuanhoaloi/standardForm";
	}

	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id, 
			@ModelAttribute("standardForm") @Valid SysStandardizedError standardForm, BindingResult result, HttpServletRequest request, ModelMap model)  {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SYS_PARAMETER> titleSystem = sysStandardizedErrorDao.titleSysStandardizedError("ADD");
		if (titleSystem.size()>0)
		{
			model.addAttribute("titleSystem", titleSystem.get(0).getValue());
		}
	
		//Ném lỗi
		if (result.hasErrors()||(standardForm!=null && (standardForm.getVendor()==null||standardForm.getNetwork()==null||standardForm.getAlarmNameMapping()==null))) {
			if(result.hasFieldErrors("ordering"))
				model.addAttribute("orderingError", "orderingError");
			if (standardForm!=null &&standardForm.getVendor()==null)
				model.addAttribute("evendor", "*");
			if (standardForm!=null &&standardForm.getNetwork()==null)
				model.addAttribute("enetwork", "*");
			if (standardForm!=null &&standardForm.getAlarmNameMapping()==null)
				model.addAttribute("ealarmNameMapping", "*");
			
			return "jspadmin/chuanhoaloi/standardForm";
		}
		
		if(id == "")
		{
			standardForm.setCreatedBy(username);
			
			sysStandardizedErrorDao.insert(standardForm);
			saveMessageKey(request, "messsage.confirm.addsuccess");
		}
		else{
			standardForm.setModifiedBy(username);
			
			sysStandardizedErrorDao.updateByPrimaryKey(standardForm);
			saveMessageKey(request, "messsage.confirm.updatesuccess");
		}
		
		return "redirect:danh-sach.htm";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) Integer id,
			HttpServletRequest request, Model model) {
		try {
			sysStandardizedErrorDao.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch (Exception e) {
			saveMessageKey(request, "message.confirm.deleteOther");
		}
		return "redirect:danh-sach.htm";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm( ModelMap model) {
		List<SYS_PARAMETER> titleSystem = sysStandardizedErrorDao.titleSysStandardizedError("UPLOAD");
		if (titleSystem.size()>0)
		{
			model.addAttribute("titleSystem", titleSystem.get(0).getValue());
		}
		return "jspadmin/chuanhoaloi/standardUpload";
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
		return "jspadmin/chuanhoaloi/standardUpload";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent(List sheetData, ModelMap model,HttpServletRequest request) {
		String createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<SysStandardizedError> success = new ArrayList<SysStandardizedError>();
		List<SysStandardizedError> failedList = new ArrayList<SysStandardizedError>();
	
		String vendor;
		String network;
		String alarmName;
		String alarmNameMapping;
		String description;
		String ordering;
		
		String status1="";
		String status2="";
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 6) {
        		saveMessageKey(request, "sidebar.admin.usersUploadErrorStructuresNumber");
        		return "jspadmin/chuanhoaloi/standardUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		SysStandardizedError standard;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			standard = new SysStandardizedError();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=6; j++) {
     					data.add("");
     				}
        			
        			vendor			= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			network			= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			alarmName		= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			alarmNameMapping= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			description		= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			ordering		= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			
        			// Kiem tra loi
        			if (vendor == null || network == null || alarmNameMapping == null) {
						error = true;
						status1 = "&nbsp;- Thiếu thông tin nhập liệu bắt buộc<br>";
					}
        			
        			//---------------------------------------------------------------------------
        			standard.setVendor(vendor);
        			standard.setNetwork(network);
        			standard.setAlarmName(alarmName);
        			standard.setAlarmNameMapping(alarmNameMapping);
        			standard.setDescription(description);
					
        			if (ordering!=null){
        				try{
        					standard.setOrdering(Integer.parseInt(ordering));
        				}catch(Exception ex){ error = true;
        				status2 = "&nbsp;- Dữ liệu sắp xếp là định dạng số <br>";}
        			}
        			
        			standard.setCreatedBy(createdBy);
        			standard.setCreatedDate(new Date());
					
    				if (error) {
						failedList.add(standard);
					} else  {
						try {
						sysStandardizedErrorDao.insertStandardError(standard);
						success.add(standard);
						} catch(Exception ex){failedList.add(standard);}
					}
        		}
        	}
		}
		
		if (failedList.size() == 0 && success.size() > 0) {
			model.addAttribute("status", "Upload thành công.");				// Upload thành công
		} else if (failedList.size() == 0 && success.size() == 0) {
			model.addAttribute("status", "Không có dữ liệu.");				// Không có dữ liệu
		} else {
			//model.addAttribute("status", "Dữ liệu người dùng không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép, định dạng dữ liệu không chính xác, tên tài khoản đã tồn tại hoặc mã phòng và nhóm truy cập không đúng.");	// Upload lỗi
			model.addAttribute("status", "Dữ liệu không hợp lệ do:<br>" + status1+status2);
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", success.size());
		model.addAttribute("totalNum", failedList.size()+success.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		return "jspadmin/chuanhoaloi/standardUpload";
	}
}
