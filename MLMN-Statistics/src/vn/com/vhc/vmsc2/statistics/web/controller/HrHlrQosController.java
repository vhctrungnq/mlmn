package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.HlrDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrHlrAuthenQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrHlrMapQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrHlrRegisterQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrHlrSubscribersQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.Hlr;
import vn.com.vhc.vmsc2.statistics.domain.HrHlrAuthenQos;
import vn.com.vhc.vmsc2.statistics.domain.HrHlrMapQos;
import vn.com.vhc.vmsc2.statistics.domain.HrHlrRegisterQos;
import vn.com.vhc.vmsc2.statistics.domain.HrHlrSubscribersQos;


@Controller
public class HrHlrQosController extends BaseController {
	@Autowired
	private HlrDAO hlrDao;
	@Autowired
	private HrHlrAuthenQosDAO hrHlrAuthenQosDao;
	@Autowired
	private HrHlrMapQosDAO hrHlrMapQosDao;
	@Autowired
	private HrHlrRegisterQosDAO hrHlrRegisterQosDao;
	@Autowired
	private HrHlrSubscribersQosDAO hrHlrSubscribersQosDao;

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("/report/core/hlr/hr")
	public String showReport(@RequestParam(required = true) String hlrid, @RequestParam(required = false) String day,
			@RequestParam(required = false) Integer hour, ModelMap model, HttpServletRequest request) {
		
		Hlr hlr = hlrDao.selectByPrimaryKey(hlrid);
		model.addAttribute("hlr", hlr);

		try {
			Date d;
			if (day == null) {
				long currentTime = System.currentTimeMillis();
				d = new Date(currentTime - 24 * 60 * 60 * 1000);
			} else {
				d = df.parse(day);
			}

			if (hour == null)
				hour = 10;

			HrHlrAuthenQos hrHlrAuthenQos = hrHlrAuthenQosDao.selectByPrimaryKey(d, hlrid, hour);
			HrHlrMapQos hrHlrMapQos = hrHlrMapQosDao.selectByPrimaryKey(d, hlrid, hour);
			HrHlrRegisterQos hrHlrRegisterQos = hrHlrRegisterQosDao.selectByPrimaryKey(d, hlrid, hour);
			HrHlrSubscribersQos hrHlrSubscribersQos = hrHlrSubscribersQosDao.selectByPrimaryKey(d, hlrid, hour);

			model.addAttribute("day", df.format(d));
			model.addAttribute("hour", hour);
			model.addAttribute("hrHlrAuthenQos", hrHlrAuthenQos);
			model.addAttribute("hrHlrMapQos", hrHlrMapQos);
			model.addAttribute("hrHlrRegisterQos", hrHlrRegisterQos);
			model.addAttribute("hrHlrSubscribersQos", hrHlrSubscribersQos);
		} catch (ParseException e) {
			saveError(request, e.toString());
		}
		
		return "hrHlrQos";
	}
}
