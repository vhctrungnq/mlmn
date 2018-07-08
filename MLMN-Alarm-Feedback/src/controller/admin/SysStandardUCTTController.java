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
import vo.SysStandardUCTT;
import vo.SysStandardizedError;
import vo.alarm.utils.UploadTools;

import controller.BaseController;
import dao.SYS_PARAMETERDAO;
import dao.SysStandardUCTTDAO;
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
@RequestMapping("/admin/chuan-uctt/*")
public class SysStandardUCTTController extends BaseController{

	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
	private SysStandardUCTTDAO sysStandardUCTTDAO;
	
	@RequestMapping(value="danh-sach")
	public ModelAndView list( @RequestParam(required = false) String region, @RequestParam(required = false) String dept,
			@RequestParam(required = false) String team, Model model,HttpServletRequest request) {
	
		int order = 1;
		String column = "REGION";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		if (region!=null&& !region.equals(""))
		{
			region=region.trim();
		}
		if (dept!=null&& !dept.equals(""))
		{
			dept=dept.trim();
		}
		if (team!=null&& !team.equals(""))
		{
			team=team.trim();
		}
		
		List<SYS_PARAMETER> titleSystem = sysStandardUCTTDAO.titleSysStandardUCTT("LIST");
		if (titleSystem.size()>0)
		{
			model.addAttribute("titleSystem", titleSystem.get(0).getValue());
		}
		
		model.addAttribute("standardList", sysStandardUCTTDAO.getStandardUcttFilter(region,dept,team, column, order ==1 ? "ASC" : "DESC"));
		model.addAttribute("region", region);
		model.addAttribute("dept", dept);
		model.addAttribute("team", team);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "ChuanUCTT_"+dateNow;
		model.addAttribute("exportFileName", exportFileName);
		  
		return new ModelAndView("jspadmin/chuanUCTT/standardUCList"); 
	}
	
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String show(@RequestParam(required = false) String id,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		List<SYS_PARAMETER> titleSystem = sysStandardUCTTDAO.titleSysStandardUCTT("ADD");
		if (titleSystem.size()>0)
		{
			model.addAttribute("titleSystem", titleSystem.get(0).getValue());
		}
		
		SysStandardUCTT sysStandardUCTT = (id == null) ? new SysStandardUCTT() : sysStandardUCTTDAO.selectByPrimaryKey(Integer.parseInt(id));
		model.addAttribute("standardForm", sysStandardUCTT);
	
		return "jspadmin/chuanUCTT/standardUCForm";
	}

	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id, 
			@ModelAttribute("standardForm") @Valid SysStandardUCTT standardForm, BindingResult result, HttpServletRequest request, ModelMap model)  {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SYS_PARAMETER> titleSystem = sysStandardUCTTDAO.titleSysStandardUCTT("ADD");
		if (titleSystem.size()>0)
		{
			model.addAttribute("titleSystem", titleSystem.get(0).getValue());
		}
	
		//Ném lỗi
		if (result.hasErrors()) {
			return "jspadmin/chuanUCTT/standardUCForm";
		}
		
		if(id == "")
		{
			standardForm.setCreatedBy(username);
			standardForm.setCreatedDate(new Date());
			sysStandardUCTTDAO.insert(standardForm);
			saveMessageKey(request, "messsage.confirm.addsuccess");
		}
		else{
			standardForm.setModifiedBy(username);
			standardForm.setModifiedDate(new Date());
			sysStandardUCTTDAO.updateByPrimaryKey(standardForm);
			saveMessageKey(request, "messsage.confirm.updatesuccess");
		}
		
		return "redirect:danh-sach.htm";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) Integer id,
			HttpServletRequest request, Model model) {
		try {
			sysStandardUCTTDAO.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch (Exception e) {
			saveMessageKey(request, "message.confirm.deleteOther");
		}
		return "redirect:danh-sach.htm";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm( ModelMap model) {
		List<SYS_PARAMETER> titleSystem = sysStandardUCTTDAO.titleSysStandardUCTT("UPLOAD");
		if (titleSystem.size()>0)
		{
			model.addAttribute("titleSystem", titleSystem.get(0).getValue());
		}
		return "jspadmin/chuanUCTT/standardUCUpload";
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
		return "jspadmin/chuanUCTT/standardUCUpload";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent(List sheetData, ModelMap model,HttpServletRequest request) {
		String createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<SysStandardUCTT> success = new ArrayList<SysStandardUCTT>();
		List<SysStandardUCTT> failedList = new ArrayList<SysStandardUCTT>();
	
		String region;
		String dept;
		String team;
		String numberSite2g;
		String numberSite3g;
		String numberAlarmService;
		String uctt;
		String planResult;
		String actualResult;
		
		String status1="";
		String status2="";
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 9) {
        		saveMessageKey(request, "sidebar.admin.usersUploadErrorStructuresNumber");
        		return "jspadmin/chuanUCTT/standardUCUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		SysStandardUCTT standard;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			standard = new SysStandardUCTT();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=9; j++) {
     					data.add("");
     				}
        			
        			region					= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			dept					= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			team					= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			numberSite2g			= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			numberSite3g			= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			numberAlarmService		= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			uctt					= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			planResult				= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			actualResult			= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			
        			// Kiem tra loi
        			if (region == null || dept == null) {
						error = true;
						status1 = "&nbsp;- Thiếu thông tin nhập liệu bắt buộc<br>";
					}
        			
        			//---------------------------------------------------------------------------
        			standard.setRegion(region);
        			standard.setDept(dept);
        			standard.setTeam(team);
        			standard.setPlanResult(planResult);
        			standard.setActualResult(actualResult);
        			
        			if (numberSite2g!=null){
        				try{
        					standard.setNumberSite2g(Integer.parseInt(numberSite2g));
        				}catch(Exception ex){ error = true;
        				status2 = "&nbsp;- Dữ liệu số lượng site 2G là định dạng số <br>";}
        			}
        			if (numberSite3g!=null){
        				try{
        					standard.setNumberSite3g(Integer.parseInt(numberSite3g));
        				}catch(Exception ex){ error = true;
        				status2 = "&nbsp;- Dữ liệu số lượng site 3G là định dạng số <br>";}
        			}
        			if (numberAlarmService!=null){
        				try{
        					standard.setNumberAlarmService(Integer.parseInt(numberAlarmService));
        				}catch(Exception ex){ error = true;
        				status2 = "&nbsp;- Dữ liệu sắp xếp là định dạng số <br>";}
        			}
        			if (uctt!=null){
        				try{
        					standard.setUctt(Integer.parseInt(uctt));
        				}catch(Exception ex){ error = true;
        				status2 = "&nbsp;- Dữ liệu UCTT là định dạng số <br>";}
        			}
        			standard.setCreatedBy(createdBy);
        			
    				if (error) {
						failedList.add(standard);
					} else  {
						try {
						sysStandardUCTTDAO.insertStandardUCTT(standard);
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
		
		return "jspadmin/chuanUCTT/standardUCUpload";
	}
}
