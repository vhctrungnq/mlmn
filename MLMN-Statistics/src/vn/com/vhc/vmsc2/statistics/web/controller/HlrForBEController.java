package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.HhlrDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyHlrforbeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrHlrforbeDAO;
import vn.com.vhc.vmsc2.statistics.domain.Hhlr;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyHlrforbe;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrHlrforbe;

@Controller
@RequestMapping("/report/core/hlr-forbe/*")
public class HlrForBEController extends BaseController {
	@Autowired
	private VRpHrHlrforbeDAO HrhlrforBEDao;
	@Autowired
	private VRpDyHlrforbeDAO DyhlrforBEDao;
	@Autowired
	private HhlrDAO hlrDao;

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("hr")
	public String ShowReport(@RequestParam(required = false) String nodeid, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
			ModelMap model, HttpServletRequest request) {
		
		if (endDate == null) {
			endDate = new Date();
		}
		if (startDate == null) {
			startDate = endDate;
		}
		if (nodeid == null) {
			nodeid = "";
		}
		if (endHour == null) {
			endHour = "23";
		} else {
			if (endHour.indexOf(":") > 0)
				endHour = endHour.substring(0, endHour.indexOf(":"));
			else
				endHour = "23";
		}
		if (startHour == null) {
			startHour = "0";
		} else {
			if (startHour.indexOf(":") > 0)
				startHour = startHour.substring(0, startHour.indexOf(":"));
			else
				startHour = "0";
		}
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("startHour", startHour + ":00");
		model.addAttribute("endHour", endHour + ":00");

		String[] hourList = { "0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00",
				"15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00" };
		List<VRpHrHlrforbe> Hrhlrforbe = HrhlrforBEDao.filter(startHour, startDate, endHour, endDate, nodeid);

		if (endDate.compareTo(startDate) < 0) {
			startDate = new Date();
			Hrhlrforbe = HrhlrforBEDao.filter2(startHour, startDate, endHour, nodeid);
		} else if (endDate.compareTo(startDate) == 0) {

			Hrhlrforbe = HrhlrforBEDao.filter2(startHour, startDate, endHour, nodeid);
		}
		List<Hhlr> NodeList = hlrDao.filterHlr("BE");
		model.addAttribute("NodeList", NodeList);
		model.addAttribute("nodeid", nodeid);
		model.addAttribute("Hrhlrforbe", Hrhlrforbe);
		model.addAttribute("hourList", hourList);
		return "hrHlrforBE";
	}

	@RequestMapping("dy")
	public String showReport(@RequestParam(required = false) String nodeid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model) {
		
		if (endDate == null) {
			endDate = new Date();
		}
		if (nodeid == null) {
			nodeid = "";
		}
		if (startDate == null) {
			startDate = endDate;
		}
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));

		List<VRpDyHlrforbe> Dyhlrforbe = DyhlrforBEDao.filter(df.format(startDate), df.format(endDate), nodeid);
		model.addAttribute("Dyhlrforbe", Dyhlrforbe);

		List<Hhlr> NodeList = hlrDao.filterHlr("BE");
		model.addAttribute("NodeList", NodeList);
		model.addAttribute("nodeid", nodeid);

		return "DyHlrforBE";
	}

}
