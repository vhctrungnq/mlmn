package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.DyLinksetDAO;
import vn.com.vhc.vmsc2.statistics.domain.DyLinkset;


@Controller
public class DyLinksetReportController extends BaseController {
	@Autowired
	private DyLinksetDAO dyLinksetDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("/report/core/linkset/dy/list")
	public ModelAndView dyList(@RequestParam(required = false) String linksetid,@RequestParam(required = false) String fromNode, @RequestParam(required = false) String toNode,
			@RequestParam(required = false) String day, Model model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();
		try {
			Date d;
			if (day == null) {
				d = new Date(currentTime - 24 * 60 * 60 * 1000);
				day = df.format(d);
			} else {
				d = df.parse(day);
			}
	
			List<DyLinkset> dyLinksetReportList = dyLinksetDao.filter(d,fromNode, toNode, linksetid);
	
			model.addAttribute("dyLinksetReportList", dyLinksetReportList);
			model.addAttribute("fromNode", fromNode);
			model.addAttribute("toNode", toNode);
			model.addAttribute("day", day);
		} catch (ParseException e){
			saveError(request, e.toString());
		}

		return new ModelAndView("dyLinksetReportList");
	}
}
