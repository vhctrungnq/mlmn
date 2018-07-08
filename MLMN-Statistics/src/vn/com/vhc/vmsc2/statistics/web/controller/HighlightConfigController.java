package vn.com.vhc.vmsc2.statistics.web.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.web.filter.HighlightFilter;

@Controller
@RequestMapping("/report/radio/highlight-config/*")
public class HighlightConfigController extends BaseController {
	@Autowired
	private HighlightConfigDAO highlightConfigDao;

	@RequestMapping("list")
	public ModelAndView list(@ModelAttribute("filter") HighlightFilter filter, Model model) {
		List<HighlightConfig> highlightConfigList = highlightConfigDao.filter(filter);
		model.addAttribute("highlightConfigList", highlightConfigList);
		
		return new ModelAndView("highlightConfigList");
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) String id, HttpServletRequest request) {

		highlightConfigDao.deleteByPrimaryKey(id);
		saveMessage(request, "HighlightConfig đã được xóa thành công.");

		return "redirect:list.htm";
	}

	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, ModelMap model) {

		HighlightConfig highlightConfig = (id == null)? new HighlightConfig() : highlightConfigDao.selectByPrimaryKey(id);
		model.addAttribute("highlightConfig", highlightConfig);
		int tick = 1;
		
		if (id == null) {
			tick = 0; 		// Them moi
		}
		
		model.addAttribute("tick", tick);
		return "highlightConfigForm";
	}

	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("highlightConfig") HighlightConfig highlightConfig,@RequestParam(required = false) int tick, HttpServletRequest request,Model model) {
		if (highlightConfig.getId() == null) {
			if (highlightConfigDao.selectByPrimaryKey(highlightConfig.getId()) == null) {
				highlightConfigDao.insert(highlightConfig);
				saveMessage(request, "HighlightConfig được thêm mới thành công.");
			} else {
				saveMessage(request, "HighlightConfig đã tồn tại.");
				model.addAttribute("tick", 0);
				return "highlightConfigForm";
				
			}
		} else {
			highlightConfigDao.updateByPrimaryKey(highlightConfig);
			saveMessage(request, "HighlightConfig được cập nhật thành công.");
		}
		return "redirect:list.htm";
	}

}
