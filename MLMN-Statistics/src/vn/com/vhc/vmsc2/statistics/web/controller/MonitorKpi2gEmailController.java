package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.MonitorKpi2gMailDAO;
import vn.com.vhc.vmsc2.statistics.dao.MonitorKpi3gMailDAO;

import vn.com.vhc.vmsc2.statistics.domain.MonitorKpi2gMail;
import vn.com.vhc.vmsc2.statistics.domain.MonitorKpi3gMail;
@Controller
@RequestMapping("/giam-sat/kpi/*")
public class MonitorKpi2gEmailController {
	@Autowired
	private MonitorKpi2gMailDAO monitorKpi2gMailDAO;
	@Autowired
	private MonitorKpi3gMailDAO monitorKpi3gMailDAO;
	
	@RequestMapping(value = "list")
	public String showList(
			 @RequestParam(required = false) String startDate,
			 @RequestParam(required = false) String endDate,
			 @RequestParam(required = true) String network,
			 ModelMap model) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		if (startDate == null) {
			startDate = df.format(new Date());
		}
		if (endDate == null) {
			endDate = startDate;
		}
		if (network.equals("2G")) {
			List<MonitorKpi2gMail> data2gList = monitorKpi2gMailDAO.getDataList(startDate, endDate);
			model.addAttribute("dataList", data2gList);
		} else {
			List<MonitorKpi3gMail> data3gList = monitorKpi3gMailDAO.getDataList(startDate, endDate);
			model.addAttribute("dataList", data3gList);
		}

		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("network", network);
		
		return "mail/giamSat2gMail";
	}
}
