package vn.com.vhc.vmsc2.statistics.web.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.MessageCodesDAO;
import vn.com.vhc.vmsc2.statistics.domain.MessageCodes;
import vn.com.vhc.vmsc2.statistics.web.filter.MessageCodesFilter;

@Controller
@RequestMapping("/system/message-code/*")
public class MessageCodesController extends BaseController {
	@Autowired
	private MessageCodesDAO messageCodesDao;

	@RequestMapping("list")
	public ModelAndView list(@ModelAttribute("filter") MessageCodesFilter filter, Model model) {
		List<MessageCodes> messageCodes = messageCodesDao.filter(filter);
		model.addAttribute("messageCodesList", messageCodes);
		return new ModelAndView("messageCodesList");
	}

}
