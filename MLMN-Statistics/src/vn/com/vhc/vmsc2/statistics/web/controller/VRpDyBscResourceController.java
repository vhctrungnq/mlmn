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

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyBscResuorceDAO;

import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBscResuorce;

@Controller
@RequestMapping("/report/core/bscresource/*")
public class VRpDyBscResourceController extends BaseController{
	@Autowired
	private VRpDyBscResuorceDAO RpDyBscResuorceDAO;
	@Autowired
	private BscDAO bscDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping("dy")
	public String showReport(@RequestParam(required = false) String bscid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model) {
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (bscid == null) {
			bscid = "";
		}
		if (startDate == null) {
			startDate = endDate;
		}
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));

		List<VRpDyBscResuorce> RpDyBscResuorce = RpDyBscResuorceDAO.filter(df.format(startDate), df.format(endDate), bscid);
		model.addAttribute("RpDyBscResuorce", RpDyBscResuorce);

		List<Bsc> BscList = bscDAO.getAll();
		model.addAttribute("BscList", BscList);
		model.addAttribute("bscid", bscid);

		return "dyBscResuorce";
	}
}
