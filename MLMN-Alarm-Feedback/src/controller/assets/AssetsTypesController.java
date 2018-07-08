package controller.assets;

import java.text.SimpleDateFormat;
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
import org.springframework.web.servlet.ModelAndView;

import controller.BaseController;

import vo.AssetsTypes;

import dao.AssetsTypesDAO;

@Controller
@RequestMapping("/assets/quan-ly-loai-tai-san/*")
public class AssetsTypesController extends BaseController{

	@Autowired
	private AssetsTypesDAO assetsTypesDAO;
	
	@RequestMapping("list")
	public ModelAndView list(@ModelAttribute("filter") AssetsTypes filter, ModelMap model, HttpServletRequest request, HttpServletResponse response)
	{
		if(filter.getAsTypesId() != null)
			filter.setAsTypesId(filter.getAsTypesId().trim());
		if(filter.getAsTypesName() != null)
			filter.setAsTypesName(filter.getAsTypesName().trim());
		
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
		String exportFileName = "LoaiTaiSan_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		List<AssetsTypes> assetsTypesList = assetsTypesDAO.getAssetsTypesFilter(filter.getAsTypesId(), filter.getAsTypesName(), column,  order==1? "ASC" : "DESC");
		model.addAttribute("assetsTypesList", assetsTypesList);
		
		
		return new ModelAndView("jspassets/assetsTypesList");
	}
	
	//FB Form
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		AssetsTypes assetsTypes = (id == null ) ? new AssetsTypes() : assetsTypesDAO.selectById(Integer.parseInt(id));
		model.addAttribute("assetsTypes", assetsTypes);
		
		assetsTypesAddEdit(id, model);
		
		return "jspassets/assetsTypesForm";
	}
	
	private void assetsTypesAddEdit(String id, ModelMap model)
	{
		if(id != null && id != "")
			model.addAttribute("assetsTypesAddEdit", "Y");
		else
			model.addAttribute("assetsTypesAddEdit", "N");
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id, @ModelAttribute("assetsTypes") @Valid AssetsTypes assetsTypes, BindingResult result, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		//Ném lỗi
		if (result.hasErrors()) {
			assetsTypesAddEdit(id, model);
			
			if(result.hasFieldErrors("ordering"))
				model.addAttribute("sapXepError", "sapXepError");
			return "jspassets/assetsTypesForm";
		}
		
		if(id == "")
		{
			if(assetsTypesDAO.selectByPrimaryKey(assetsTypes.getAsTypesId()) == null){
				assetsTypes.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				assetsTypesDAO.insert(assetsTypes);
				saveMessageKey(request, "messsage.confirm.addsuccess");
			}
			else{
				saveMessageKey(request, "message.assetsTypes.maLoaiTaiSanTonTai");
				assetsTypesAddEdit(id, model);
				return "jspassets/assetsTypesForm";
			}
		}
		else{
			assetsTypes.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			
			assetsTypesDAO.updateByPrimaryKey(assetsTypes);
			saveMessageKey(request, "messsage.confirm.updatesuccess");
		}
		
		
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) Integer id, HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
			assetsTypesDAO.deleteById(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch(Exception e)
		{
			saveMessageKey(request, "message.confirm.deleteOther");
			}
		
		return "redirect:list.htm";
	}
}
