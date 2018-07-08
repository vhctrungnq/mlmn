package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.HIpbbBwlistDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyIpbbSlaDAO;
import vn.com.vhc.vmsc2.statistics.domain.HIpbbBwlist;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyIpbbSla;
import vn.com.vhc.vmsc2.statistics.web.filter.IPBBFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;

@Controller
@RequestMapping("/report/core/ip-backbone-sla/*")
public class IpbbSlaController extends BaseController{
	@Autowired
	private VRpDyIpbbSlaDAO vipbbdataDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	@Autowired
	private HIpbbBwlistDAO IpbbBwlistDao;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	
	@RequestMapping("dy")
	public ModelAndView Show(
			 @RequestParam(required = false) Date startDate,
			 @RequestParam(required = false) Date endDate,
			 @RequestParam(required = false) String direction,
			 @RequestParam(required = false) String link,
			 Model model) {
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		if(direction == null){
			direction = "";
		}
		if(link == null){
			link = "";
		}
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("direction", direction);
		model.addAttribute("link", link);
		List<HIpbbBwlist> directionList = IpbbBwlistDao.getAllDirection();
		model.addAttribute("directionList", directionList);
		
		
		String title = " SLA DAILY";
		model.addAttribute("title", title);
		List<VRpDyIpbbSla> ipbbSlaList = vipbbdataDao.filterDaily("V_RP_DY_IPBB_SLA",direction, link, df.format(startDate), df.format(endDate));

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "dyIpbbSla"));
		
		model.addAttribute("ipbbSlaList",ipbbSlaList);
		model.addAttribute("level","dy");
		model.addAttribute("dy", "ui-tabs-selected");
		return new ModelAndView("dyIpbbSla");
	}
	
	@RequestMapping("hr")
	public String Showreport(
			 @ModelAttribute("filter") IPBBFilter filter,
			 @RequestParam(required = false) String startDate,
			 @RequestParam(required = false) String endDate,
			 @RequestParam(required = false) String direction,
			 @RequestParam(required = false) String link,
			 Model model) {
		
		if(direction == null){
			direction = "";
		}
		if(link == null){
			link = "";
		}
		String title = " SLA HOURLY";
		model.addAttribute("title", title);
		Date edate ;
		long currentTime = System.currentTimeMillis();
		if (endDate==null) {
			edate = new Date(currentTime - 24 * 60 * 60 * 1000);
			endDate = df.format(edate).toString();
		}
		if (startDate.equalsIgnoreCase("")) {
			startDate = endDate;
		}
		if(startDate.equalsIgnoreCase(endDate))
		{
			startDate = startDate + " 00:00";
			endDate = endDate + " 23:59";
			
		}
		
		List<HIpbbBwlist> directionList = IpbbBwlistDao.getAllDirection();
		model.addAttribute("directionList", directionList);
		
		List<VRpDyIpbbSla> ipbbSlaList = vipbbdataDao.filterHourly("V_RP_HR_IPBB_SLA", direction, link, startDate,endDate);
		model.addAttribute("ipbbSlaList",ipbbSlaList);
		model.addAttribute("level","hr");
		model.addAttribute("startDate",startDate);
		model.addAttribute("endDate",endDate);
		model.addAttribute("hr", "ui-tabs-selected");
		return "dyIpbbSla";
	}
	
}
