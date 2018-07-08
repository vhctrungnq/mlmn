package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VrpMnIpbbLink1DAO;
import vn.com.vhc.vmsc2.statistics.dao.VrpMnIpbbLink2DAO;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VrpMnIpbbLink1;
import vn.com.vhc.vmsc2.statistics.domain.VrpMnIpbbLink2;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;

@Controller
@RequestMapping("/report/core/ip-backbone/*")
public class MnIpbbLinkCotroller {
	@Autowired
	private VrpMnIpbbLink1DAO MnIpbbLink1Dao;
	@Autowired
	private VrpMnIpbbLink2DAO MnIpbbLink2Dao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;

	@RequestMapping("mn_ipbblink")
	public ModelAndView mnList(
			@RequestParam(required = false) Integer startMonth,
			@RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endMonth,
			@RequestParam(required = false) Integer endYear, 
			ModelMap model, HttpServletRequest request) {
		Calendar cal = Calendar.getInstance(); 
		if(endMonth==null){
			cal.add(Calendar.MONTH,-1);
			endMonth = cal.get(Calendar.MONTH)+1;
			endYear = cal.get(Calendar.YEAR);
		}
		
		if (startMonth == null) { 
			startMonth = endMonth;
			startYear = endYear;
		}
		
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		List<VrpMnIpbbLink1> MnIpbblink1 = MnIpbbLink1Dao.filter(startMonth,startYear, endMonth, endYear);
		List<VrpMnIpbbLink2> MnIpbblink2 = MnIpbbLink2Dao.filter(startMonth,startYear, endMonth, endYear);
		
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLightKpis(highlightConfigList, "MnIpbblink1"));
		List<HighlightConfig> highlightConfigList1 = highlightConfigDao.getAll();
		model.addAttribute("highlight1", Helper.highLightKpis(highlightConfigList1, "MnIpbblink2"));

		
		model.addAttribute("MnIpbblink1", MnIpbblink1);
		model.addAttribute("MnIpbblink2", MnIpbblink2);
		model.addAttribute("monthList", monthList);

		return new ModelAndView("mnIpbblinkList");
	}
}
