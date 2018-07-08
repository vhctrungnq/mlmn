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
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.VRpDyBadCell3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellTraffic3GDAO;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBadCell3G;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellTraffic3G;



@Controller
public class DyBadCell3GController extends BaseController {
	@Autowired
	private VRpDyBadCell3GDAO vRpDyBadCell3GDao;
	@Autowired
	private VRpDyCellTraffic3GDAO vRpDyBadCellTraffic3GDao;

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("/report/radio3g/bad-cell/by-cell/list")
	public ModelAndView list(@RequestParam(required = false) String bscid, @RequestParam(required = false) String cellid,
			@RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, ModelMap model) {
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}

		List<VRpDyBadCell3G> dyBadCell3GList = vRpDyBadCell3GDao.filter(bscid, cellid, df.format(startDate), df.format(endDate));

		model.addAttribute("bscid", bscid);
		model.addAttribute("cellid", cellid);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("dyBadCell3GList", dyBadCell3GList);

		return new ModelAndView("badCell3gDy");
	}

	/* Traffic */
	@RequestMapping("/report/radio3g/bad-cell-traff/by-cell/list")
	public ModelAndView listTrafficByCell(@RequestParam(required = false) String bscid, @RequestParam(required = false) String cellid,
			@RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, ModelMap model) {
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}

		List<VRpDyCellTraffic3G> dyBadCell3GList = vRpDyBadCellTraffic3GDao.filter(bscid, cellid, df.format(startDate), df.format(endDate));

		model.addAttribute("bscid", bscid);
		model.addAttribute("cellid", cellid);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("dyBadCell3GList", dyBadCell3GList);

		return new ModelAndView("badCellTraff3gDy");
	}
}
