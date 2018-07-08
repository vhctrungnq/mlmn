package controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vo.W_WORKING_TYPES;

import dao.W_WORKING_TYPESDAO;

@Controller
@RequestMapping("/w_working_types/*")
public class W_WORKING_TYPESContronller extends BaseController{

	@Autowired
	private W_WORKING_TYPESDAO working_typesDao;
	
	@RequestMapping("list")
	public ModelAndView list(@ModelAttribute("filter") W_WORKING_TYPES filter, Model model, HttpServletRequest request) {
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "DanhMucCongViec_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		if(filter.getName() != null)
			filter.setName(filter.getName().trim());
		if(filter.getOrderingStr() != null && filter.getOrderingStr() != "")
			filter.setOrderingStr(filter.getOrderingStr().trim());
		
		try
		{
			int ordering;
			if(filter.getOrderingStr() != null && filter.getOrderingStr() != "")
				ordering = Integer.parseInt(filter.getOrderingStr());
			List<W_WORKING_TYPES> working_types = working_typesDao.getWorkingTypesFilter(filter.getName(), filter.getOrderingStr());
			model.addAttribute("working_typesList", working_types);
		}
		catch(Exception e){
			List<W_WORKING_TYPES> working_types = null;
			model.addAttribute("working_typesList", working_types);
		}
		
		
		return new ModelAndView("jsp/working_typesList");
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) Integer id, HttpServletRequest request, HttpServletResponse response) {
		
		/*List<W_WORKING_MANAGEMENTS> id_works_managements = working_managementDao.getIdWorksTypes(Integer.toString(id));
		if(id_works_managements.size()>0)
		{
			for(int i=0;i<id_works_managements.size();i++)
			{
				
				
				Delete3Table(Integer.toString(id_works_managements.get(i).getId()));
				
				working_managementDao.deleteByPrimaryKey(id_works_managements.get(i).getId());  // Xóa ở bảng W_WORKING_MANAGEMENTS
			}
		}*/
		try{
			working_typesDao.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch(Exception e){
			saveMessage(request, "Xóa thất bại, dữ liệu đã được dùng ở nơi khác.");
		}

		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) Integer id, ModelMap model, HttpServletRequest request) {
		
		W_WORKING_TYPES working_types = (id == null) ? new W_WORKING_TYPES() : working_typesDao.selectByPrimaryKey(id);
		model.addAttribute("w_working_types",working_types);
		
		if(id == null){
			model.addAttribute("WorksAddEdit", "Y");
		}
		else
		{
			model.addAttribute("WorksAddEdit", "N");
		}
		
		return "jsp/working_typesForm";
	}

	
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("w_working_types") @Valid W_WORKING_TYPES working_types, BindingResult result, ModelMap model, HttpServletRequest request) {
		
		//Ném lỗi
		if (result.hasErrors()) {
			if(working_types.getId() == null){
				model.addAttribute("WorksAddEdit", "Y");
			}
			else
			{
				model.addAttribute("WorksAddEdit", "N");
			}
			if(result.hasFieldErrors("ordering"))
				model.addAttribute("orderingError", "orderingError");
			
            return "jsp/working_typesForm";
		}
		
		SecurityContext securityContext = SecurityContextHolder.getContext();
	    Authentication authentication = securityContext.getAuthentication();


		if(working_typesDao.selectByPrimaryKey(working_types.getId())== null)
		{
			if(working_typesDao.countNameTogether(working_types.getName()) == 0){
				working_types.setCreatedBy(authentication.getName());
				
				working_typesDao.insert(working_types);
				saveMessageKey(request, "messsage.confirm.addsuccess");
			}
			else
			{
				if(working_types.getId() == null){
					model.addAttribute("WorksAddEdit", "Y");
				}
				else
				{
					model.addAttribute("WorksAddEdit", "N");
				}
				
				saveMessageKey(request, "messsage.confirm.loaiCongViecTonTai");
				return "jsp/working_typesForm";
			}
		}
		else
		{
			if(working_typesDao.countNameDontId(working_types) == 0){
				working_types.setModifiedBy(authentication.getName());
				
				working_typesDao.updateByPrimaryKey(working_types);
				saveMessageKey(request, "messsage.confirm.updatesuccess");
			}
			else
			{
				if(working_types.getId() == null){
					model.addAttribute("WorksAddEdit", "Y");
				}
				else
				{
					model.addAttribute("WorksAddEdit", "N");
				}
				
				saveMessageKey(request, "messsage.confirm.loaiCongViecTonTai");
				return "jsp/working_typesForm";
			}
		}
		return "redirect:list.htm";
	}
	
	//FORM DANH SÁCH CÔNG VIỆC - works_contentList.jsp
	@RequestMapping(value="{fileName}", method = RequestMethod.GET)
	@ResponseBody
	public byte[] getNameInJSP(@PathVariable String fileName) throws IOException {
 
		File file = new File("C:\\uploads\\" + fileName);
		FileInputStream is = new FileInputStream(file);
		/*Image image =*/
		byte[] content = new byte[(int) file.length()];

		// Read in the bytes
	    int offset = 0;
	    int numRead = 0;
	    while (offset < content.length
	           && (numRead=is.read(content, offset, content.length-offset)) >= 0) {
	        offset += numRead;
	    }
	    

		is.close();
		return content;
		/*return image.getData();*/
	}
	
		
}

