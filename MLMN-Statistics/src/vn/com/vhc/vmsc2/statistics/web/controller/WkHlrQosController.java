package vn.com.vhc.vmsc2.statistics.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.HlrDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkHlrAuthenQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkHlrMapQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkHlrRegisterQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkHlrSubscribersQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.Hlr;
import vn.com.vhc.vmsc2.statistics.domain.WkHlrAuthenQos;
import vn.com.vhc.vmsc2.statistics.domain.WkHlrMapQos;
import vn.com.vhc.vmsc2.statistics.domain.WkHlrRegisterQos;
import vn.com.vhc.vmsc2.statistics.domain.WkHlrSubscribersQos;


@Controller
public class WkHlrQosController extends BaseController {
	@Autowired
	private HlrDAO hlrDao;
	@Autowired
	private WkHlrAuthenQosDAO wkHlrAuthenQosDao;
	@Autowired
	private WkHlrMapQosDAO wkHlrMapQosDao;
	@Autowired
	private WkHlrRegisterQosDAO wkHlrRegisterQosDao;
	@Autowired
	private WkHlrSubscribersQosDAO wkHlrSubscribersQosDao;

	@RequestMapping("/report/core/hlr/wk")
	public String showReport(@RequestParam(required = true) String hlrid, @RequestParam(required = false) Integer year,
			@RequestParam(required = false) Integer week, ModelMap model, HttpServletRequest request) {
		DateTime dt = new DateTime();
		dt = dt.minusWeeks(1);

		if (week == null) {
			week = dt.getWeekOfWeekyear();
		}

		if (year == null)
			year = dt.getYear();

		Hlr hlr = hlrDao.selectByPrimaryKey(hlrid);
		WkHlrAuthenQos wkHlrAuthenQos = wkHlrAuthenQosDao.selectByPrimaryKey(hlrid, week, year);
		WkHlrMapQos wkHlrMapQos = wkHlrMapQosDao.selectByPrimaryKey(hlrid, week, year);
		WkHlrRegisterQos wkHlrRegisterQos = wkHlrRegisterQosDao.selectByPrimaryKey(hlrid, week, year);
		WkHlrSubscribersQos wkHlrSubscribersQos = wkHlrSubscribersQosDao.selectByPrimaryKey(hlrid, week, year);

		model.addAttribute("week", week);
		model.addAttribute("year", year);
		model.addAttribute("hlr", hlr);
		model.addAttribute("wkHlrAuthenQos", wkHlrAuthenQos);
		model.addAttribute("wkHlrMapQos", wkHlrMapQos);
		model.addAttribute("wkHlrRegisterQos", wkHlrRegisterQos);
		model.addAttribute("wkHlrSubscribersQos", wkHlrSubscribersQos);

		return "wkHlrQos";
	}
}
