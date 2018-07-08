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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.DyHlrAuthenQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyHlrMapQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyHlrRegisterQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyHlrSubscribersQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HlrDAO;
import vn.com.vhc.vmsc2.statistics.domain.DyHlrAuthenQos;
import vn.com.vhc.vmsc2.statistics.domain.DyHlrMapQos;
import vn.com.vhc.vmsc2.statistics.domain.DyHlrRegisterQos;
import vn.com.vhc.vmsc2.statistics.domain.DyHlrSubscribersQos;
import vn.com.vhc.vmsc2.statistics.domain.Hlr;
import vn.com.vhc.vmsc2.statistics.web.filter.HlrFilter;


@Controller
public class DyHlrQosController extends BaseController {
	@Autowired
	private HlrDAO hlrDao;
	@Autowired
	private DyHlrAuthenQosDAO dyHlrAuthenQosDao;
	@Autowired
	private DyHlrMapQosDAO dyHlrMapQosDao;
	@Autowired
	private DyHlrRegisterQosDAO dyHlrRegisterQosDao;
	@Autowired
	private DyHlrSubscribersQosDAO dyHlrSubscribersQosDao;

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping("/report/core/hlr/dy/list")
	public ModelAndView dyList(@ModelAttribute("filter") HlrFilter filter, Model model) {
		List<Hlr> dyHlrs = hlrDao.filter(filter);
		model.addAttribute("dyHlrQosList", dyHlrs);
		return new ModelAndView("dyHlrQosList");
	}

	@RequestMapping("/report/core/hlr/dy")
	public String showReport(@RequestParam(required = true) String hlrid, @RequestParam(required = false) String day, ModelMap model, HttpServletRequest request) {
		try {
			Date d;
			if (day == null) {
				long currentTime = System.currentTimeMillis();
				d = new Date(currentTime - 24 * 60 * 60 * 1000);
			} else {
				d = df.parse(day);
			}

			Hlr hlr = hlrDao.selectByPrimaryKey(hlrid);
			DyHlrAuthenQos dyHlrAuthenQos = dyHlrAuthenQosDao.selectByPrimaryKey(d, hlrid);
			DyHlrMapQos dyHlrMapQos = dyHlrMapQosDao.selectByPrimaryKey(d, hlrid);
			DyHlrRegisterQos dyHlrRegisterQos = dyHlrRegisterQosDao.selectByPrimaryKey(d, hlrid);
			DyHlrSubscribersQos dyHlrSubscribersQos = dyHlrSubscribersQosDao.selectByPrimaryKey(d, hlrid);

			model.addAttribute("day", df.format(d));
			model.addAttribute("hlr", hlr);
			model.addAttribute("dyHlrAuthenQos", dyHlrAuthenQos);
			model.addAttribute("dyHlrMapQos", dyHlrMapQos);
			model.addAttribute("dyHlrRegisterQos", dyHlrRegisterQos);
			model.addAttribute("dyHlrSubscribersQos", dyHlrSubscribersQos);
		} catch (ParseException e){
			saveError(request, e.toString());
		}
		
		return "dyHlrQos";
	}
}
