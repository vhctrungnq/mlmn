package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bsh.ParseException;

import vo.CSystemConfigs;
import vo.SYS_PARAMETER;

import dao.CSystemConfigsDAO;
import dao.SysUsersDAO;

@Controller
@RequestMapping("/system/config/*")
public class SystemConfigController extends BaseController {

	@Autowired
	private CSystemConfigsDAO cSystemConfigsDAO;

	@Autowired 
	private SysUsersDAO sysUsersDao;
	
	@ModelAttribute("configTypeList")
	public List<SYS_PARAMETER> configTypeList() {
		return cSystemConfigsDAO.getConfigTypeList();
	}
	@RequestMapping("list")
	public ModelAndView list(@RequestParam(required = false) String paramName,
			@RequestParam(required = false) String paramValue,
			@RequestParam(required = false) String configType,
			Model model, HttpServletRequest request) {
		
		List<SYS_PARAMETER> sysParemeterTitle = cSystemConfigsDAO.titleSystemConfig(null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		int order = 0;
		String column = "PARAM_NAME";
		try {
			order = Integer.parseInt(request.getParameter((new ParamEncoder("systemConfig").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
			column = request.getParameter((new ParamEncoder("systemConfig").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
		} catch (NumberFormatException e) {
		}
		List<CSystemConfigs> systemConfigList = new ArrayList<CSystemConfigs>();
		try
		{
			systemConfigList = cSystemConfigsDAO.getSystemConfigList(paramName,paramValue,configType, column, order);	
		}
		catch (Exception exp)
		{
			systemConfigList= null;
		}
		model.addAttribute("systemConfigList", systemConfigList);
		model.addAttribute("paramName", paramName);
		model.addAttribute("paramValue", paramValue);
		model.addAttribute("configType", configType);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "SystemConfig_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
				
		return new ModelAndView("jsp/CauHinh/systemConfigList");
	}

	
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) Integer  id, ModelMap model, HttpServletResponse response) {
		
		List<SYS_PARAMETER> sysParemeterTitle = cSystemConfigsDAO.titleSystemConfig("ADD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
		}
		CSystemConfigs systemConfig =  new CSystemConfigs();
		if (id !=null)
		{
			systemConfig = cSystemConfigsDAO.selectByID(id);
			if (systemConfig!=null)
			{
				model.addAttribute("oldParamName", systemConfig.getParamName());
				model.addAttribute("configType", systemConfig.getConfigType());
			}
		}
		
		model.addAttribute("systemConfig", systemConfig);
		
		
		return "jsp/CauHinh/systemConfigForm";
	}

	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("systemConfig") @Valid CSystemConfigs systemConfig, 
			BindingResult result,
			@RequestParam(required = false) String oldParamName,
			@RequestParam(required = false) String configType,
			HttpServletRequest request,Model model,HttpServletResponse response) throws ParseException {	
		
		List<SYS_PARAMETER> sysParemeterTitle = cSystemConfigsDAO.titleSystemConfig("ADD");
		String title="";
		if(sysParemeterTitle.size() > 0)
		{
			title=sysParemeterTitle.get(0).getValue();
		}
		
		if (!result.hasErrors())
		{
			CSystemConfigs oldSystemConfig = cSystemConfigsDAO.selectByPrimaryKey(systemConfig.getParamName());
			systemConfig.setConfigType(configType);
			if (oldParamName == null || oldParamName.equals("")) {
				if (oldSystemConfig ==null)
				{
					cSystemConfigsDAO.insert(systemConfig);
					saveMessageKey(request, "systemConfig.insertSucceFull");
				}
				else
				{
					saveMessageKey(request, "systemConfig.exits");
					model.addAttribute("systemConfig", systemConfig);
					model.addAttribute("titleF", title);
					model.addAttribute("oldParamName", oldParamName);
					model.addAttribute("configType", configType);
					return "jsp/CauHinh/systemConfigForm";
				}
	
			} else {
				
					cSystemConfigsDAO.updateByPrimaryKey(systemConfig);
					saveMessageKey(request, "systemConfig.updateSucceFull");
			}
	
			return "redirect:list.htm";
		}
		else
		{
			model.addAttribute("systemConfig", systemConfig);
			model.addAttribute("oldParamName", oldParamName);
			model.addAttribute("titleF", title);
			model.addAttribute("configType", configType);
			return "jsp/CauHinh/systemConfigForm";
		}
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) Integer  id, HttpServletRequest request, ModelMap model) {
		
		try {
			cSystemConfigsDAO.deleteById(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch (Exception e) {
			saveMessageKey(request, "messsage.confirm.deletefailure");
		}
		return "redirect:list.htm";
	
	}
}
