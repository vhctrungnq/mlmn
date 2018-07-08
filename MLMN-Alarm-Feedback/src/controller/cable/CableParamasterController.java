package controller.cable;

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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vo.CableParameter;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.alarm.utils.Helper;

import controller.BaseController;
import dao.CableParameterDAO;
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
@RequestMapping("/alarm/cableParameter/*")
public class CableParamasterController extends BaseController{
	@Autowired
	private CableParameterDAO cableParameterDAO;
	@Autowired 
	private SysUsersDAO  sysUserDao;
	
	@RequestMapping(value="danh-sach")
	public ModelAndView list( @RequestParam(required = false) String code, @RequestParam(required = false) String name, @RequestParam(required = false) String value, ModelMap model,HttpServletRequest request) {
	
		int order = 1;
		String column = "CODE";
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
		
		List<SYS_PARAMETER> titleSystem = cableParameterDAO.titleForm(null); //cableParameterDAO.getSPByCodeAndName("TITLE_SYSTEM", "THAM_SO_HE_THONG");
		if(titleSystem.size() > 0)
		{
			model.addAttribute("titleSystem", titleSystem.get(0).getValue());
		}
		
		
		List<CableParameter> distinctMaThamSo = cableParameterDAO.distinctMaThamSo();
		model.addAttribute("distinctMaThamSo", distinctMaThamSo);
	
		model.addAttribute("thamSoList", cableParameterDAO.getSysParametersFilter(code,name, column, order ==1 ? "ASC" : "DESC"));
		model.addAttribute("CodeCBB", code);
		model.addAttribute("name", name);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "CableParamaster_"+dateNow;
		model.addAttribute("exportFileName", exportFileName);
		//Check Role User(Administrator)
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SysUsers> userList = sysUserDao.checkRole(username);
		Helper.checkRole(userList, model);		  
		return new ModelAndView("jspalarm/cable/thamsoList"); 
	}

	
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String show(@RequestParam(required = false) String id,  ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		CableParameter sysParameter = (id == null) ? new CableParameter() : cableParameterDAO.selectByPrimaryKey(Integer.parseInt(id));
		model.addAttribute("sysParameterForm", sysParameter);
	
		titleAddEdit( id,  model);
		
		return "jspalarm/cable/thamsoForm";
	}
	
	private void titleAddEdit(String id, ModelMap model)
	{
		
		if(id != null && id != "")
		{
			List<SYS_PARAMETER> titleSystem = cableParameterDAO.titleForm("EDIT"); //cableParameterDAO.getSPByCodeAndName("TITLE_SYSTEM", "THAM_SO_HE_THONG");
			if(titleSystem.size() > 0)
			{
				model.addAttribute("title", titleSystem.get(0).getValue());
				model.addAttribute("titleE","EDIT");
			}
		}
		else
		{
			List<SYS_PARAMETER> titleSystem = cableParameterDAO.titleForm("ADD"); //cableParameterDAO.getSPByCodeAndName("TITLE_SYSTEM", "THAM_SO_HE_THONG");
			if(titleSystem.size() > 0)
			{
				model.addAttribute("title", titleSystem.get(0).getValue());
				model.addAttribute("titleE","ADD");
			}
		}
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id, @ModelAttribute("sysParameterForm") @Valid CableParameter sysParameter, BindingResult result, HttpServletRequest request, ModelMap model)  {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//Ném lỗi
		if (result.hasErrors()) {
			if(result.hasFieldErrors("ordering"))
				model.addAttribute("orderingError", "orderingError");
			titleAddEdit( id,  model);
			
			return "jspalarm/cable/thamsoForm";
		}
		
		if(id == "")
		{
			sysParameter.setCreatedBy(username);
			
			cableParameterDAO.insert(sysParameter);
			saveMessageKey(request, "messsage.confirm.addsuccess");
		}
		else{
			sysParameter.setModifiedBy(username);
			
			cableParameterDAO.updateByPrimaryKey(sysParameter);
			saveMessageKey(request, "messsage.confirm.updatesuccess");
		}
		
		return "redirect:danh-sach.htm";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) Integer id, HttpServletRequest request, Model model) {
		try {
			cableParameterDAO.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch (Exception e) {
			saveMessageKey(request, "message.confirm.deleteOther");
		}
		
		return "redirect:danh-sach.htm";
	}
	
}
