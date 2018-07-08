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

import vn.com.vhc.vmsc2.statistics.dao.DyStpAssonciationQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyStpChecksumQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyStpDestinationQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyStpM3dataQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyStpM3perfQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyStpOutOfBlueQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyStpRetransmitionQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyStpUtilisationQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.StpDAO;
import vn.com.vhc.vmsc2.statistics.domain.DyStpAssonciationQos;
import vn.com.vhc.vmsc2.statistics.domain.DyStpChecksumQos;
import vn.com.vhc.vmsc2.statistics.domain.DyStpDestinationQos;
import vn.com.vhc.vmsc2.statistics.domain.DyStpM3dataQos;
import vn.com.vhc.vmsc2.statistics.domain.DyStpM3perfQos;
import vn.com.vhc.vmsc2.statistics.domain.DyStpOutOfBlueQos;
import vn.com.vhc.vmsc2.statistics.domain.DyStpRetransmitionQos;
import vn.com.vhc.vmsc2.statistics.domain.DyStpUtilisationQos;
import vn.com.vhc.vmsc2.statistics.domain.Stp;
import vn.com.vhc.vmsc2.statistics.web.filter.StpFilter;

@Controller
public class DyStpQosController extends BaseController {
	@Autowired
	private StpDAO stpDao;
	@Autowired
	private DyStpAssonciationQosDAO dyStpAssonciationDao;
	@Autowired
	private DyStpChecksumQosDAO dyStpChecksumQosDao;
	@Autowired
	private DyStpDestinationQosDAO dyStpDestinationQosDao;
	@Autowired
	private DyStpM3dataQosDAO dyStpM3dataQosDao;
	@Autowired
	private DyStpM3perfQosDAO dyStpM3perfQosDao;
	@Autowired
	private DyStpOutOfBlueQosDAO dyStpOutOfBlueQosDao;
	@Autowired
	private DyStpRetransmitionQosDAO dyStpRetransmitionQosDao;
	@Autowired
	private DyStpUtilisationQosDAO dyStpUtilisationQosDao;

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("/report/core/stp/dy/list")
	public ModelAndView dyList(@ModelAttribute("filter") StpFilter filter, Model model) {
		List<Stp> dyStps = stpDao.filter(filter);
		model.addAttribute("dyStpQosList", dyStps);
		return new ModelAndView("dyStpQosList");
	}

	@RequestMapping("/report/core/stp/dy")
	public String showReport(@RequestParam(required = false) String stpid, @RequestParam(required = false) String day, ModelMap model,
			HttpServletRequest request) {
		try {
			Date d;
			if (day == null) {
				long currentTime = System.currentTimeMillis();
				d = new Date(currentTime - 24 * 60 * 60 * 1000);
			} else {
				d = df.parse(day);
			}

			Stp stp = stpDao.selectByPrimaryKey(stpid);
			DyStpAssonciationQos dyStpAssonciationQos = dyStpAssonciationDao.selectByPrimaryKey(d, stpid);
			DyStpChecksumQos dyStpChecksumQos = dyStpChecksumQosDao.selectByPrimaryKey(d, stpid);
			DyStpDestinationQos dyStpDestinationQos = dyStpDestinationQosDao.selectByPrimaryKey(d, stpid);
			DyStpM3dataQos dyStpM3dataQos = dyStpM3dataQosDao.selectByPrimaryKey(d, stpid);
			DyStpM3perfQos dyStpM3perfQos = dyStpM3perfQosDao.selectByPrimaryKey(d, stpid);
			DyStpOutOfBlueQos dyStpOutOfBlueQos = dyStpOutOfBlueQosDao.selectByPrimaryKey(d, stpid);
			DyStpRetransmitionQos dyStpRetransmitionQos = dyStpRetransmitionQosDao.selectByPrimaryKey(d, stpid);
			DyStpUtilisationQos dyStpUtilisationQos = dyStpUtilisationQosDao.selectByPrimaryKey(d, stpid);

			model.addAttribute("day", df.format(d));
			model.addAttribute("stp", stp);
			model.addAttribute("dyStpAssonciationQos", dyStpAssonciationQos);
			model.addAttribute("dyStpChecksumQos", dyStpChecksumQos);
			model.addAttribute("dyStpDestinationQos", dyStpDestinationQos);
			model.addAttribute("dyStpM3dataQos", dyStpM3dataQos);
			model.addAttribute("dyStpM3perfQos", dyStpM3perfQos);
			model.addAttribute("dyStpOutOfBlueQos", dyStpOutOfBlueQos);
			model.addAttribute("dyStpRetransmitionQos", dyStpRetransmitionQos);
			model.addAttribute("dyStpUtilisationQos", dyStpUtilisationQos);
		} catch (ParseException e) {
			saveError(request, e.toString());
		}

		return "dyStpQosDetail";
	}
}
