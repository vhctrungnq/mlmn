package vn.com.vhc.vmsc2.statistics.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReportController {

	@RequestMapping("/report")
	public ModelAndView report() {
		return new ModelAndView("report");
	}
	
	@RequestMapping("/report3g")
	public ModelAndView report3g() {
		return new ModelAndView("report3g");
	}
}
