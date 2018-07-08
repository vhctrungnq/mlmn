package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.SchedulerJobsDAO;
import vn.com.vhc.vmsc2.statistics.domain.SchedulerJobs;

@Controller
@RequestMapping("/log/checkjob/*")
public class SchedulerJobsController extends BaseController{
	@Autowired
	private SchedulerJobsDAO SchedulejobDAO;

	@RequestMapping("detail")
	public ModelAndView list(@RequestParam(required = false) String user,
							Model model) {	
		if(user == null)
			user = "";
		
		List<SchedulerJobs> jobnamelist1 =  SchedulejobDAO.filter(user);
		List<SchedulerJobs> jobnamelist =  SchedulejobDAO.getAll();
		
		model.addAttribute("jobnamelist1", jobnamelist1);
		model.addAttribute("jobnamelist", jobnamelist);
		model.addAttribute("user", user);
		return new ModelAndView("jobnamelist");
	}
}
