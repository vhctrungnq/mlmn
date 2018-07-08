package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.VRpDyOptimalDAO;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyOptimal;

/**
 * @author VHC-TRUNGNGAONGO
 * Bao cao optimal
 */

@Controller
@RequestMapping("/report/optimal/*")
public class VRpOptimalController {
	
	@Autowired
	private VRpDyOptimalDAO vRpDyOptimalDAO;
	@RequestMapping("dy")
	public String showDyList(@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -1);
		if (startDate == null) {	
			startDate = df.format(cal.getTime());
		} else {
			try {
				df.parse(startDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				startDate = df.format(cal.getTime());
			}
		}
		
		if (endDate == null) {
			endDate = startDate;
		} else {
			try {
				df.parse(endDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				endDate = startDate;
			}
		}
		List<VRpDyOptimal> dataList = vRpDyOptimalDAO.getDyData(startDate, endDate);
		model.addAttribute("dataList", dataList);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("exportFileName", "Bao cao optimal ngay " + startDate.replace("/", "-"));
		return "optimal/dyList";
	}

}
