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
import vn.com.vhc.vmsc2.statistics.dao.VRpDyHlrM2000DAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrHlrM2000DAO;
import vn.com.vhc.vmsc2.statistics.domain.Hhlr;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyHlrM2000;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrHlrM2000;

@Controller
@RequestMapping("/report/core/RpHlrM2000/*")
public class HlrM2000Controller extends BaseController {
	@Autowired
	private VRpDyHlrM2000DAO RpDyHlrM2000DAO;
	@Autowired
	private VRpHrHlrM2000DAO RpHrHlrM2000DAO;
	@Autowired
	private HhlrDAO hlrDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

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

		List<VRpDyHlrM2000> DyHlrM2000 = RpDyHlrM2000DAO.filter(df.format(startDate), df.format(endDate), nodeid);
		model.addAttribute("DyHlrM2000", DyHlrM2000);

		List<Hhlr> nodeList = hlrDao.filterHlr("M2000");
		model.addAttribute("NodeList", nodeList);
		model.addAttribute("nodeid", nodeid);

		return "DyHlrM2000";
	}

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
		List<VRpHrHlrM2000> HrHlrM2000 = RpHrHlrM2000DAO.filter(startHour, startDate, endHour, endDate, nodeid);

		if (endDate.compareTo(startDate) < 0) {
			startDate = new Date();
			HrHlrM2000 = RpHrHlrM2000DAO.filter2(startHour, startDate, endHour, nodeid);
		} else if (endDate.compareTo(startDate) == 0) {

			HrHlrM2000 = RpHrHlrM2000DAO.filter2(startHour, startDate, endHour, nodeid);
		}
		List<Hhlr> nodeList = hlrDao.filterHlr("M2000");
		model.addAttribute("NodeList", nodeList);
		model.addAttribute("nodeid", nodeid);
		model.addAttribute("HrHlrM2000", HrHlrM2000);
		model.addAttribute("hourList", hourList);
		return "HrHlrM2000";
	}

}
