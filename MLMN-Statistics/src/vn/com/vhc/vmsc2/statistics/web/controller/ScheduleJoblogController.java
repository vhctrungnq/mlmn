package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.SchedulerJobLogDAO;
import vn.com.vhc.vmsc2.statistics.dao.SchedulerJobsDAO;
import vn.com.vhc.vmsc2.statistics.domain.SchedulerJobLog;
import vn.com.vhc.vmsc2.statistics.domain.SchedulerJobs;

@Controller
@RequestMapping("/log/checkjob/*")
public class ScheduleJoblogController extends BaseController{
	@Autowired
	private SchedulerJobLogDAO SchedulejoblogDAO;
	@Autowired
	private SchedulerJobsDAO SchedulejobDAO;
	@RequestMapping("list")
	public ModelAndView list(@RequestParam(required = false) String user,
							HttpServletRequest request,
							Model model) {			
		if(user == null)
			user = "";
		List<SchedulerJobLog> jobnameloglist =  SchedulejoblogDAO.filter(user);
		List<SchedulerJobs> jobnamelist =  SchedulejobDAO.getAll();
		
		model.addAttribute("jobnameloglist", jobnameloglist);
		model.addAttribute("jobnamelist", jobnamelist);
		model.addAttribute("user", user);
		return new ModelAndView("jobnameloglist");
	}
}
