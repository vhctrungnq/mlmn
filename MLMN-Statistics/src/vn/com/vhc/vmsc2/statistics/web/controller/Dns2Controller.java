package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyDns2DAO;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyDns2;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;
import vn.com.vhc.vmsc2.statistics.web.utils.ModelAddtributes;

/**
 * 
 * @author Mr. ThangPX
 * @return Nds Report Level Daily, Hourly, Weekly, Monthly
 */
@Controller
@RequestMapping("/report/core/csi-dns-2/*")
public class Dns2Controller extends BaseController {
	@Autowired
	private VRpDyDns2DAO dyCsiDao; 
	@Autowired
	private HighlightConfigDAO highlightDao; 
	@RequestMapping(value = "dy")
	public String showReport(@RequestParam(required = false) String nodeid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model, HttpServletRequest request) {
		if(nodeid == null)
			nodeid = "";
		model.addAttribute("nodeid", nodeid);
		String[] s = ModelAddtributes.checkDate2(model, startDate, endDate).split(";");
		List<VRpDyDns2> dyCsiList = dyCsiDao.filterDaily("V_RP_DY_DNS_2", nodeid, s[0], s[1]);
		model.addAttribute("dyCsiList", dyCsiList);
		List<HighlightConfig> highlightConfigList = highlightDao.getByKey("AAA");
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "dyCsiList"));
		 
		return "dyDns2";
	}
 
}
