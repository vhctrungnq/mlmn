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

import vo.MDepartment;
import vo.SYS_PARAMETER;
import vo.alarm.utils.UploadTools;

import controller.BaseController;
import dao.SYS_PARAMETERDAO;

/**
 * Function        : Quan ly tham so cau hinh he thong
 * Created By      : BUIQUANG
 * Create Date     :
 * Modified By     : 
 * Modify Date     : 
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/admin/tham-so-cau-hinh/*")
public class AdminThamSoCauHinhController extends BaseController{

	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@RequestMapping(value="danh-sach")
	public ModelAndView list( @RequestParam(required = false) String code, @RequestParam(required = false) String name,
			@RequestParam(required = true) String typeCode, Model model,HttpServletRequest request) {
	
		int order = 1;
		String column = "ORDERING";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		if (code!=null&& !code.equals(""))
		{
			code=code.trim();
		}
		if (name!=null&& !name.equals(""))
		{
			name=name.trim();
		}
		List<SYS_PARAMETER> titleSystem = sysParameterDao.titleSysParameterByCode(typeCode,null); //sysParameterDao.getSPByCodeAndName("TITLE_SYSTEM", "THAM_SO_HE_THONG");
		if (titleSystem.size()>0)
		{
			model.addAttribute("titleSystem", titleSystem.get(0).getValue());
		}
		if (typeCode==null || typeCode.equals(""))
		{
			List<SYS_PARAMETER> distinctMaThamSo = sysParameterDao.distinctMaThamSo();
			model.addAttribute("distinctMaThamSo", distinctMaThamSo);
		}
		else
		{
			code = typeCode;
		}
		model.addAttribute("thamSoList", sysParameterDao.getSysParametersFilter(code,name, column, order ==1 ? "ASC" : "DESC"));
		model.addAttribute("CodeCBB", code);
		model.addAttribute("name", name);
		model.addAttribute("typeCode", typeCode);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "ThamSoCauHinh_"+dateNow;
		model.addAttribute("exportFileName", exportFileName);
		  
		return new ModelAndView("jspadmin/thamsocauhinh/thamsoList"); 
	}

	
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String show(@RequestParam(required = false) String id,@RequestParam(required = true) String typeCode,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		SYS_PARAMETER sysParameter = (id == null) ? new SYS_PARAMETER() : sysParameterDao.selectByPrimaryKey(Integer.parseInt(id));
		if (id == null)
		{
			sysParameter.setCode(typeCode);
		}
		model.addAttribute("sysParameterForm", sysParameter);
	
		List<SYS_PARAMETER> titleSystem = sysParameterDao.titleSysParameterByCode(typeCode,"ADD");
		if (titleSystem.size()>0)
		{
			model.addAttribute("title", titleSystem.get(0).getValue());
		}
		model.addAttribute("typeCode", typeCode);
		
		return "jspadmin/thamsocauhinh/thamsoForm";
	}
	
	/*private void titleAddEdit(String id, ModelMap model)
	{
		if(id != null && id != "")
			model.addAttribute("title", "thamso.sua");
		else
			model.addAttribute("title", "thamso.themoi");
	}*/
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id, 
			@RequestParam(required = true) String typeCode,
			@ModelAttribute("sysParameterForm") @Valid SYS_PARAMETER sysParameter, BindingResult result, HttpServletRequest request, ModelMap model)  {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SYS_PARAMETER> titleSystem = sysParameterDao.titleSysParameterByCode(typeCode,"ADD");
		model.addAttribute("typeCode", typeCode);
		
		//Ném lỗi
		if (result.hasErrors()) {
			if(result.hasFieldErrors("ordering"))
				model.addAttribute("orderingError", "orderingError");
			if (titleSystem.size()>0)
			{
				model.addAttribute("title", titleSystem.get(0).getValue());
			}
			
			return "jspadmin/thamsocauhinh/thamsoForm";
		}
		
		if(id == "")
		{
			sysParameter.setCreatedBy(username);
			
			sysParameterDao.insert(sysParameter);
			saveMessageKey(request, "messsage.confirm.addsuccess");
		}
		else{
			sysParameter.setModifiedBy(username);
			
			sysParameterDao.updateByPrimaryKey(sysParameter);
			saveMessageKey(request, "messsage.confirm.updatesuccess");
		}
		
		return "redirect:danh-sach.htm";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) Integer id, @RequestParam(required = true) String typeCode,
			HttpServletRequest request, Model model) {
		try {
			sysParameterDao.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch (Exception e) {
			saveMessageKey(request, "message.confirm.deleteOther");
		}
		model.addAttribute("typeCode", typeCode);
		return "redirect:danh-sach.htm";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(@RequestParam(required = true) String typeCode, Model model) {
		model.addAttribute("typeCode", typeCode);
		return "jspadmin/thamsocauhinh/thamsoUpload";
	}

	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile filePath,@RequestParam(required = true) String typeCode,  ModelMap model, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		
		if (!filePath.isEmpty()) {

			String[] ten=filePath.getOriginalFilename().split("\\.");
			String fileExtn = ten[ten.length-1];
			
			if (fileExtn.equals("xls")) {
				if (fileExtn.equals("xls")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsFile2(filePath.getInputStream());
					
					importContent(sheetData,typeCode, model, request);
					
				}
			}
			else {
				 saveMessageKey(request, "cautruc.typeFIle");
			}
		}
		else {
			saveMessageKey(request, "cautruc.emptyFile");
		}
		model.addAttribute("typeCode", typeCode);
		return "jspadmin/thamsocauhinh/thamsoUpload";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent(List sheetData,String typeCode, ModelMap model,HttpServletRequest request) {
		String createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<SYS_PARAMETER> success = new ArrayList<SYS_PARAMETER>();
		List<SYS_PARAMETER> failedList = new ArrayList<SYS_PARAMETER>();
	
		String code;
		String name;
		String value;
		String ordering;
		String dataType;
		String remark;
		
		String status1="";
		String status2="";
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 6) {
        		saveMessageKey(request, "sidebar.admin.usersUploadErrorStructuresNumber");
        		return "jspadmin/thamsocauhinh/thamsoUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		SYS_PARAMETER parameter;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			parameter = new SYS_PARAMETER();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=6; j++) {
     					data.add("");
     				}
        			if (typeCode==null||typeCode.equals("")){
        				code			= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			}else{code = typeCode;}
        			name			= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			value			= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			ordering		= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			dataType		= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			remark			= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			
        			// Kiem tra loi
        			if (code == null || name == null || value == null) {
						error = true;
						status1 = "&nbsp;- Thiếu thông tin nhập liệu bắt buộc<br>";
					}
        			
        			//---------------------------------------------------------------------------
					parameter.setCode(code);
					parameter.setName(name);
					parameter.setValue(value);
					parameter.setDataType(dataType);
					parameter.setRemark(remark);
					
        			if (ordering!=null){
        				try{
        					parameter.setOrdering(Integer.parseInt(ordering));
        				}catch(Exception ex){ error = true;
        				status2 = "&nbsp;- Dữ liệu sắp xếp là định dạng số <br>";}
        			}
        			
        			parameter.setCreatedBy(createdBy);
        			parameter.setCreateDate(new Date());
					
    				if (error) {
						failedList.add(parameter);
					} else  {
						try {
						if (sysParameterDao.getSPByCodeAndName(code, name)==null){
							sysParameterDao.insertSelective(parameter);
						}else{
							sysParameterDao.updateByCodeName(parameter);
						}
						success.add(parameter);
						} catch(Exception ex){failedList.add(parameter);}
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
		
		return "jspadmin/thamsocauhinh/thamsoUpload";
	}
	
}
