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
import vn.com.vhc.vmsc2.statistics.dao.DyBscCoreDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyBscCssrQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyBscDcrsQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyBscDcrtQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyBscHsrQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyBscPsrQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyBscTrapcomQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyBscTrapeventQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyHBscCpuQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyHBscHoQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyHBscPchQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyHBscSdcchQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyHBscTchQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.DyBscCore;
import vn.com.vhc.vmsc2.statistics.domain.DyBscCssrQos;
import vn.com.vhc.vmsc2.statistics.domain.DyBscDcrsQos;
import vn.com.vhc.vmsc2.statistics.domain.DyBscDcrtQos;
import vn.com.vhc.vmsc2.statistics.domain.DyBscHsrQos;
import vn.com.vhc.vmsc2.statistics.domain.DyBscPsrQos;
import vn.com.vhc.vmsc2.statistics.domain.DyBscTrapcomQos;
import vn.com.vhc.vmsc2.statistics.domain.DyBscTrapeventQos;
import vn.com.vhc.vmsc2.statistics.domain.DyHBscCpuQos;
import vn.com.vhc.vmsc2.statistics.domain.DyHBscHoQos;
import vn.com.vhc.vmsc2.statistics.domain.DyHBscPchQos;
import vn.com.vhc.vmsc2.statistics.domain.DyHBscSdcchQos;
import vn.com.vhc.vmsc2.statistics.domain.DyHBscTchQos;


@Controller
public class DyBscCoreDetailController extends BaseController {
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private DyBscCoreDAO dyBscCoreDao;
	@Autowired
	private DyBscCssrQosDAO dyBscCssrQosDao;
	@Autowired
	private DyBscDcrtQosDAO dyBscDcrtQosDao;
	@Autowired
	private DyBscDcrsQosDAO dyBscDcrsQosDao;
	@Autowired
	private DyBscPsrQosDAO dyBscPsrQosDao;
	@Autowired
	private DyBscHsrQosDAO dyBscHsrQosDao;
	@Autowired
	private DyBscTrapeventQosDAO dyBscTrapeventQosDao;
	@Autowired
	private DyBscTrapcomQosDAO dyBscTrapcomQosDao;
	@Autowired
	private DyHBscCpuQosDAO dyHBscCpuQosDao;
	@Autowired
	private DyHBscHoQosDAO dyHBscHoQosDao;
	@Autowired
	private DyHBscPchQosDAO dyHBscPchQosDao;
	@Autowired
	private DyHBscSdcchQosDAO dyHBscSdcchQosDao;
	@Autowired
	private DyHBscTchQosDAO dyHBscTchQosDao;

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("/report/core/bsc/dy/detail")
	public String showReport(@RequestParam(required = true) String bscid, @RequestParam(required = false) String day, ModelMap model, HttpServletRequest request) {

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

			DyBscCore dyBscCore = dyBscCoreDao.selectByPrimaryKey(bscid, d);
			model.addAttribute("day", df.format(d));
			model.addAttribute("dyBscCore", dyBscCore);
			
			if (bscid.endsWith("H")) {
				DyHBscCpuQos dyHBscCpuQos = dyHBscCpuQosDao.selectByPrimaryKey(bscid, d);
				DyHBscHoQos dyHBscHoQos = dyHBscHoQosDao.selectByPrimaryKey(bscid, d);
				DyHBscPchQos dyHBscPchQos = dyHBscPchQosDao.selectByPrimaryKey(bscid, d);
				DyHBscSdcchQos dyHBscSdcchQos = dyHBscSdcchQosDao.selectByPrimaryKey(bscid, d);
				DyHBscTchQos dyHBscTchQos = dyHBscTchQosDao.selectByPrimaryKey(bscid, d);

				model.addAttribute("dyHBscCpuQos", dyHBscCpuQos);
				model.addAttribute("dyHBscHoQos", dyHBscHoQos);
				model.addAttribute("dyHBscPchQos", dyHBscPchQos);
				model.addAttribute("dyHBscSdcchQos", dyHBscSdcchQos);
				model.addAttribute("dyHBscTchQos", dyHBscTchQos);
				return "dyBscCoreDetailH";
				
			} else {
				DyBscCssrQos dyBscCssrQos = dyBscCssrQosDao.selectByPrimaryKey(bscid, d);
				DyBscDcrtQos dyBscDcrtQos = dyBscDcrtQosDao.selectByPrimaryKey(bscid, d);
				DyBscPsrQos dyBscPsrQos = dyBscPsrQosDao.selectByPrimaryKey(bscid, d);
				DyBscHsrQos dyBscHsrQos = dyBscHsrQosDao.selectByPrimaryKey(bscid, d);
				DyBscDcrsQos dyBscDcrsQos = dyBscDcrsQosDao.selectByPrimaryKey(bscid, d);
				List<DyBscTrapeventQos> dyBscTrapeventQosList = dyBscTrapeventQosDao.select(bscid, d);
				DyBscTrapcomQos dyBscTrapcomQos = dyBscTrapcomQosDao.selectByPrimaryKey(bscid, d);

				model.addAttribute("dyBscCssrQos", dyBscCssrQos);
				model.addAttribute("dyBscDcrtQos", dyBscDcrtQos);
				model.addAttribute("dyBscDcrsQos", dyBscDcrsQos);
				model.addAttribute("dyBscPsrQos", dyBscPsrQos);
				model.addAttribute("dyBscHsrQos", dyBscHsrQos);
				model.addAttribute("dyBscTrapeventQosList", dyBscTrapeventQosList);
				model.addAttribute("dyBscTrapcomQos", dyBscTrapcomQos);
			}
		} catch (ParseException e) {
			saveError(request, e.toString());
		}

		return "dyBscCoreDetail";
	}
}
