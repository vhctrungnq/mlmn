package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

import vn.com.vhc.vmsc2.statistics.dao.SystemConfigDAO;
import vn.com.vhc.vmsc2.statistics.domain.SystemConfig;
import vn.com.vhc.vmsc2.statistics.web.filter.SystemConfigFilter;


@Controller
@RequestMapping("/system/config/*")
public class SystemConfigController extends BaseController {

	private SystemConfigDAO systemConfigDao;

	@Autowired
	public void setSystemConfigDao(SystemConfigDAO systemConfigDao) {
		this.systemConfigDao = systemConfigDao;
	}

	@RequestMapping("list")
	public ModelAndView list(@ModelAttribute("filter") SystemConfigFilter filter, Model model) {
		List<SystemConfig> systemConfig = systemConfigDao.filter(filter);
		model.addAttribute("systemConfigList", systemConfig);
		
		return new ModelAndView("systemConfigList");
	}

	
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) Integer id, ModelMap model, HttpServletResponse response) {
		
		SystemConfig systemConfig = (id == null) ? new SystemConfig() : systemConfigDao.selectByPrimaryKey(id);
		model.addAttribute("systemConfig", systemConfig);
		
		return "systemConfigForm";
	}

	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@Valid SystemConfig systemConfig, BindingResult result, HttpServletRequest request,Model model,HttpServletResponse response) {	
		
		SystemConfig oldSystemConfig=systemConfigDao.exitSystemConfig(systemConfig.getParamName());
		if (!result.hasErrors())
		{
			
			if (systemConfig.getId() == null) {
				if (oldSystemConfig==null)
				{
					systemConfigDao.insert(systemConfig);
					saveMessage(request, "SystemConfig được tạo thành công");
				}
				else
				{
					saveMessage(request, "SystemConfig đã tồn tại");
					model.addAttribute(systemConfig);
					return "systemConfigForm";
				}
	
			} else {
				if (oldSystemConfig!=null && !oldSystemConfig.getId().equals(systemConfig.getId()))
				{
					saveMessage(request, "SystemConfig đã tồn tại");
					model.addAttribute(systemConfig);
					return "systemConfigForm";
				}
				else
				{
					systemConfigDao.updateByPrimaryKey(systemConfig);
					saveMessage(request, "SystemConfig được cập nhật thành công");
				}
			}
	
			return "redirect:list.htm";
		}
		else
		{
			model.addAttribute(systemConfig);
			return "systemConfigForm";
		}
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) int id, HttpServletRequest request, ModelMap model) {
		systemConfigDao.deleteByPrimaryKey(id);
		saveMessage(request, "SystemConfig được xóa thành công.");
		return "redirect:list.htm";
	}
}
