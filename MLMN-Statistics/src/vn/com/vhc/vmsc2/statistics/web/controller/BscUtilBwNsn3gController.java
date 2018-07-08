package vn.com.vhc.vmsc2.statistics.web.controller; 

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.Bsc3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyNBscUtilBw3gDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc3G;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyNBscUtilBw3g;
import vn.com.vhc.vmsc2.statistics.web.filter.Bsc3gFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;
import vn.com.vhc.vmsc2.statistics.web.utils.ModelAddtributes;

/**
 * 
 * @author Mr. AnhNT
 * @return Load Report Level Daily, Hourly, Weekly, Monthly
 */
@Controller
@RequestMapping("/report/core/bsc-util-bw-nsn-3g/*")
public class BscUtilBwNsn3gController extends BaseController {
	@Autowired
	private VRpDyNBscUtilBw3gDAO bscLoadDAO;
	@Autowired
	private HighlightConfigDAO highlightDao;
	@Autowired
	private Bsc3GDAO bscDao;

	@RequestMapping(value = "hr")
	public String showReport(@RequestParam(required = false) String bscid, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, ModelMap model, HttpServletRequest request) {
		Bsc3gFilter bscFilter = new Bsc3gFilter();
		List<Bsc3G> listBsc = bscDao.filter(bscFilter);
		bscid = ModelAddtributes.checkRnc(model, listBsc, bscid,"bsc-util-bw-nsn-3g");
		String[] s = ModelAddtributes.checkHour(model, startDate, startHour, endHour).split(";");
		List<VRpDyNBscUtilBw3g> dyBscList = bscLoadDAO.filterHourly("V_RP_HR_N_BSC_UTIL_BW_3G", bscid, "", s[0], s[1], s[2]);
		model.addAttribute("dyBscList", dyBscList);
		model.addAttribute("objectname", "N");
		
		return "bscUtilBwNsn3G";
	}

	// Daily report
	@RequestMapping(value = "dy")
	public String showReport(@RequestParam(required = false) String bscid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model, HttpServletRequest request) {
		Bsc3gFilter bscFilter = new Bsc3gFilter();
		List<Bsc3G> listBsc = bscDao.filter(bscFilter);
		bscid = ModelAddtributes.checkRnc(model, listBsc, bscid,"bsc-util-bw-nsn-3g");
		String[] s = ModelAddtributes.checkDate(model, startDate, endDate).split(";");
		List<VRpDyNBscUtilBw3g> dyBscList = bscLoadDAO.filterDaily("V_RP_DY_N_BSC_UTIL_BW_3G", bscid, "", s[0], s[1]);
		model.addAttribute("dyBscList", dyBscList);
		List<HighlightConfig> highlightConfigList = highlightDao.getByKey("TT");
		model.addAttribute("highlight", Helper.highLightLine(highlightConfigList, "dyBscList"));
		model.addAttribute("objectname", "N");
		return "bscUtilBwNsn3G";
	}
} 
