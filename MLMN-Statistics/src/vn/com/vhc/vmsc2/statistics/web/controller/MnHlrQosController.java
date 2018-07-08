package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.HlrDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnHlrAuthenQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnHlrMapQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnHlrRegisterQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnHlrSubscribersQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.Hlr;
import vn.com.vhc.vmsc2.statistics.domain.MnHlrAuthenQos;
import vn.com.vhc.vmsc2.statistics.domain.MnHlrMapQos;
import vn.com.vhc.vmsc2.statistics.domain.MnHlrRegisterQos;
import vn.com.vhc.vmsc2.statistics.domain.MnHlrSubscribersQos;


@Controller
public class MnHlrQosController extends BaseController {
	@Autowired
	private HlrDAO hlrDao;
	@Autowired
	private MnHlrAuthenQosDAO mnHlrAuthenQosDao;
	@Autowired
	private MnHlrMapQosDAO mnHlrMapQosDao;
	@Autowired
	private MnHlrRegisterQosDAO mnHlrRegisterQosDao;
	@Autowired
	private MnHlrSubscribersQosDAO mnHlrSubscribersQosDao;
	
	@RequestMapping("/report/core/hlr/mn")
	public String showReport(@RequestParam(required = true) String hlrid, @RequestParam(required=false) Integer year,@RequestParam(required = false) Integer month, ModelMap model, HttpServletRequest request) {
		Calendar now = Calendar.getInstance();

		if (month == null) {
			now.add(Calendar.MONTH,-1);
			month = now.get(Calendar.MONTH)+1;
		}

		if (year == null)
			year = now.get(Calendar.YEAR);
	
			Hlr hlr = hlrDao.selectByPrimaryKey(hlrid);
			MnHlrAuthenQos mnHlrAuthenQos = mnHlrAuthenQosDao.selectByPrimaryKey(hlrid, month, year);
			MnHlrMapQos mnHlrMapQos = mnHlrMapQosDao.selectByPrimaryKey(hlrid, month, year);
			MnHlrRegisterQos mnHlrRegisterQos = mnHlrRegisterQosDao.selectByPrimaryKey(hlrid, month, year);
			MnHlrSubscribersQos mnHlrSubscribersQos = mnHlrSubscribersQosDao.selectByPrimaryKey(hlrid, month, year);

			model.addAttribute("month", month);
			model.addAttribute("year", year);
			model.addAttribute("hlr", hlr);
			model.addAttribute("mnHlrAuthenQos", mnHlrAuthenQos);
			model.addAttribute("mnHlrMapQos", mnHlrMapQos);
			model.addAttribute("mnHlrRegisterQos", mnHlrRegisterQos);
			model.addAttribute("mnHlrSubscribersQos", mnHlrSubscribersQos);
		
		return "mnHlrQos";
	}
}
