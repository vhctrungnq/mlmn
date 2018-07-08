package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrBscCoreDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrBscCssrQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrBscDcrsQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrBscDcrtQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrBscHsrQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrBscPsrQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrBscTrapcomQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrBscTrapeventQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrHBscCpuQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrHBscHoQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrHBscPchQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrHBscSdcchQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrHBscTchQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HrBscCore;
import vn.com.vhc.vmsc2.statistics.domain.HrBscCssrQos;
import vn.com.vhc.vmsc2.statistics.domain.HrBscDcrsQos;
import vn.com.vhc.vmsc2.statistics.domain.HrBscDcrtQos;
import vn.com.vhc.vmsc2.statistics.domain.HrBscHsrQos;
import vn.com.vhc.vmsc2.statistics.domain.HrBscPsrQos;
import vn.com.vhc.vmsc2.statistics.domain.HrBscTrapcomQos;
import vn.com.vhc.vmsc2.statistics.domain.HrBscTrapeventQos;
import vn.com.vhc.vmsc2.statistics.domain.HrHBscCpuQos;
import vn.com.vhc.vmsc2.statistics.domain.HrHBscHoQos;
import vn.com.vhc.vmsc2.statistics.domain.HrHBscPchQos;
import vn.com.vhc.vmsc2.statistics.domain.HrHBscSdcchQos;
import vn.com.vhc.vmsc2.statistics.domain.HrHBscTchQos;


@Controller
public class HrBscCoreController extends BaseController {
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private HrBscCoreDAO hrBscCoreDao;
	@Autowired
	private HrBscCssrQosDAO hrBscCssrQosDao;
	@Autowired
	private HrBscDcrtQosDAO hrBscDcrtQosDao;
	@Autowired
	private HrBscDcrsQosDAO hrBscDcrsQosDao;
	@Autowired
	private HrBscPsrQosDAO hrBscPsrQosDao;
	@Autowired
	private HrBscHsrQosDAO hrBscHsrQosDao;
	@Autowired
	private HrBscTrapcomQosDAO hrBscTrapcomQosDao;
	@Autowired
	private HrBscTrapeventQosDAO hrBscTrapeventQosDao;
	@Autowired
	private HrHBscCpuQosDAO hrHBscCpuQosDao;
	@Autowired
	private HrHBscHoQosDAO hrHBscHoQosDao;
	@Autowired
	private HrHBscPchQosDAO hrHBscPchQosDao;
	@Autowired
	private HrHBscSdcchQosDAO hrHBscSdcchQosDao;
	@Autowired
	private HrHBscTchQosDAO hrHBscTchQosDao;

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("/report/core/bsc/hr/detail")
	public String showReport(@RequestParam(required = true) String bscid, @RequestParam(required = false) String day,
			@RequestParam(required = false) Integer hour, ModelMap model, HttpServletRequest request) {

		Bsc bsc = bscDao.selectByPrimaryKey(bscid);
		model.addAttribute("bsc", bsc);

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

			model.addAttribute("day", df.format(d));
			model.addAttribute("hour", hour);

			if (bscid.endsWith("H")) {
				HrBscCore hrBscCore = hrBscCoreDao.selectByPrimaryKey(bscid, d, hour);
				HrHBscCpuQos hrHBscCpuQos = hrHBscCpuQosDao.selectByPrimaryKey(bscid, d, hour);
				HrHBscHoQos hrHBscHoQos = hrHBscHoQosDao.selectByPrimaryKey(bscid, d, hour);
				HrHBscPchQos hrHBscPchQos = hrHBscPchQosDao.selectByPrimaryKey(bscid, d, hour);
				HrHBscSdcchQos hrHBscSdcchQos = hrHBscSdcchQosDao.selectByPrimaryKey(bscid, d, hour);
				HrHBscTchQos hrHBscTchQos = hrHBscTchQosDao.selectByPrimaryKey(bscid, d, hour);

				model.addAttribute("hrHBscCpuQos", hrHBscCpuQos);
				model.addAttribute("hrHBscHoQos", hrHBscHoQos);
				model.addAttribute("hrHBscPchQos", hrHBscPchQos);
				model.addAttribute("hrHBscSdcchQos", hrHBscSdcchQos);
				model.addAttribute("hrHBscTchQos", hrHBscTchQos);
				model.addAttribute("hrBscCore", hrBscCore);
				return "hrBscCoreDetailH";
			} else {
				HrBscCore hrBscCore = hrBscCoreDao.selectByPrimaryKey(bscid, d, hour);
				HrBscCssrQos hrBscCssrQos = hrBscCssrQosDao.selectByPrimaryKey(bscid, d, hour);
				HrBscDcrtQos hrBscDcrtQos = hrBscDcrtQosDao.selectByPrimaryKey(bscid, d, hour);
				HrBscPsrQos hrBscPsrQos = hrBscPsrQosDao.selectByPrimaryKey(bscid, d, hour);
				HrBscHsrQos hrBscHsrQos = hrBscHsrQosDao.selectByPrimaryKey(bscid, d, hour);
				HrBscDcrsQos hrBscDcrsQos = hrBscDcrsQosDao.selectByPrimaryKey(bscid, d, hour);
				HrBscTrapcomQos hrBscTrapcomQos = hrBscTrapcomQosDao.selectByPrimaryKey(bscid, d, hour);
				List<HrBscTrapeventQos> hrBscTrapeventQosList = hrBscTrapeventQosDao.select(bscid, d, hour);

				model.addAttribute("hrBscCore", hrBscCore);
				model.addAttribute("hrBscCssrQos", hrBscCssrQos);
				model.addAttribute("hrBscDcrtQos", hrBscDcrtQos);
				model.addAttribute("hrBscDcrsQos", hrBscDcrsQos);
				model.addAttribute("hrBscPsrQos", hrBscPsrQos);
				model.addAttribute("hrBscHsrQos", hrBscHsrQos);
				model.addAttribute("hrBscTrapcomQos", hrBscTrapcomQos);
				model.addAttribute("hrBscTrapeventQosList", hrBscTrapeventQosList);
				return "hrBscCoreDetail";
			}

		} catch (ParseException e) {
			saveError(request, e.toString());
		}

		return "";
	}
}
