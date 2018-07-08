package vn.com.vhc.vmsc2.statistics.web.controller; 

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyNBscUtilTrau2gDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyNBscUtilTrau2g;
import vn.com.vhc.vmsc2.statistics.web.filter.BscFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;
import vn.com.vhc.vmsc2.statistics.web.utils.ModelAddtributes;

/**
 * 
 * @author Mr. AnhNT
 * @return Load Report Level Daily, Hourly, Weekly, Monthly
 */
@Controller
@RequestMapping("/report/core/bsc-util-trau-nsn-2g/*")
public class BscUtilTrauNsn2gController extends BaseController {
	@Autowired
	private VRpDyNBscUtilTrau2gDAO bscLoadDAO;
	@Autowired
	private HighlightConfigDAO highlightDao;
	@Autowired
	private BscDAO bscDao;

	@RequestMapping(value = "hr")
	public String showReport(@RequestParam(required = false) String bscid, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, ModelMap model, HttpServletRequest request) {
		List<Bsc> listBsc = bscDao.getAll();
		bscid = ModelAddtributes.checkBscid(model, listBsc, bscid,"bsc-util-trau-nsn-2g");
		String[] s = ModelAddtributes.checkHour(model, startDate, startHour, endHour).split(";");
		List<VRpDyNBscUtilTrau2g> dyBscList = bscLoadDAO.filterHourly("V_RP_HR_N_BSC_UTIL_TRAU_2G", bscid, "", s[0], s[1], s[2]);
		model.addAttribute("dyBscList", dyBscList);
		model.addAttribute("objectname", "N");
		
		return "bscUtilTrau2G";
	}

	// Daily report
	@RequestMapping(value = "dy")
	public String showReport(@RequestParam(required = false) String bscid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model, HttpServletRequest request) {
	
		List<Bsc> listBsc = bscDao.getAll();
		bscid = ModelAddtributes.checkBscid(model, listBsc, bscid, "bsc-util-trau-nsn-2g");
		String[] s = ModelAddtributes.checkDate(model, startDate, endDate).split(";");
		List<VRpDyNBscUtilTrau2g> dyBscList = bscLoadDAO.filterDaily("V_RP_DY_N_BSC_UTIL_TRAU_2G", bscid, "", s[0], s[1]);
		model.addAttribute("dyBscList", dyBscList);
		List<HighlightConfig> highlightConfigList = highlightDao.getByKey("TT");
		model.addAttribute("highlight", Helper.highLightLine(highlightConfigList, "dyBscList"));
		model.addAttribute("objectname", "N");
		return "bscUtilTrau2G";
	}
} 
