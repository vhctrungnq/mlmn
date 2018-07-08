package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.StpDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkStpAssonciationQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkStpChecksumQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkStpDestinationQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkStpM3dataQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkStpM3perfQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkStpOutOfBlueQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkStpRetransmitionQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkStpUtilisationQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.Stp;
import vn.com.vhc.vmsc2.statistics.domain.WkStpAssonciationQos;
import vn.com.vhc.vmsc2.statistics.domain.WkStpChecksumQos;
import vn.com.vhc.vmsc2.statistics.domain.WkStpDestinationQos;
import vn.com.vhc.vmsc2.statistics.domain.WkStpM3dataQos;
import vn.com.vhc.vmsc2.statistics.domain.WkStpM3perfQos;
import vn.com.vhc.vmsc2.statistics.domain.WkStpOutOfBlueQos;
import vn.com.vhc.vmsc2.statistics.domain.WkStpRetransmitionQos;
import vn.com.vhc.vmsc2.statistics.domain.WkStpUtilisationQos;

@Controller
public class WkStpQosDetailController extends BaseController {
	@Autowired
	private StpDAO stpDao;
	@Autowired
	private WkStpAssonciationQosDAO wkStpAssonciationDao;
	@Autowired
	private WkStpChecksumQosDAO wkStpChecksumQosDao;
	@Autowired
	private WkStpDestinationQosDAO wkStpDestinationQosDao;
	@Autowired
	private WkStpM3dataQosDAO wkStpM3dataQosDao;
	@Autowired
	private WkStpM3perfQosDAO wkStpM3perfQosDao;
	@Autowired
	private WkStpOutOfBlueQosDAO wkStpOutOfBlueQosDao;
	@Autowired
	private WkStpRetransmitionQosDAO wkStpRetransmitionQosDao;
	@Autowired
	private WkStpUtilisationQosDAO wkStpUtilisationQosDao;
	
	@RequestMapping("/report/core/stp/wk")
	public String showReport(@RequestParam(required = true) String stpid, @RequestParam(required = false) Integer week, @RequestParam(required = false) Integer year, ModelMap model, HttpServletRequest request) {
		int mWeek;
		int mYear;
		Calendar mCalendar = Calendar.getInstance();
		mCalendar.setFirstDayOfWeek(Calendar.MONDAY);
		if (week == null) {
			mWeek = mCalendar.get(Calendar.WEEK_OF_YEAR);
		} else {
			mWeek = week;
		}
		
		if (year == null) {
			mYear = mCalendar.get(Calendar.YEAR);;
		} else {
			mYear = year;
		}

		Stp stp = stpDao.selectByPrimaryKey(stpid);
		WkStpAssonciationQos wkStpAssonciationQos = wkStpAssonciationDao.selectByPrimaryKey(stpid, mWeek, mYear);
		WkStpChecksumQos wkStpChecksumQos = wkStpChecksumQosDao.selectByPrimaryKey(stpid, mWeek, mYear);
		WkStpDestinationQos wkStpDestinationQos = wkStpDestinationQosDao.selectByPrimaryKey(stpid, mWeek, mYear);
		WkStpM3dataQos wkStpM3dataQos = wkStpM3dataQosDao.selectByPrimaryKey(stpid, mWeek, mYear);
		WkStpM3perfQos wkStpM3perfQos = wkStpM3perfQosDao.selectByPrimaryKey(stpid, mWeek, mYear);
		WkStpOutOfBlueQos wkStpOutOfBlueQos = wkStpOutOfBlueQosDao.selectByPrimaryKey(stpid, mWeek, mYear);
		WkStpRetransmitionQos wkStpRetransmitionQos = wkStpRetransmitionQosDao.selectByPrimaryKey(stpid, mWeek, mYear);
		WkStpUtilisationQos wkStpUtilisationQos = wkStpUtilisationQosDao.selectByPrimaryKey(stpid, mWeek, mYear);

		model.addAttribute("week", mWeek);
		model.addAttribute("year", mYear);
		model.addAttribute("stp", stp);
		model.addAttribute("wkStpAssonciationQos", wkStpAssonciationQos);
		model.addAttribute("wkStpChecksumQos", wkStpChecksumQos);
		model.addAttribute("wkStpDestinationQos", wkStpDestinationQos);
		model.addAttribute("wkStpM3dataQos", wkStpM3dataQos);
		model.addAttribute("wkStpM3perfQos", wkStpM3perfQos);
		model.addAttribute("wkStpOutOfBlueQos", wkStpOutOfBlueQos);
		model.addAttribute("wkStpRetransmitionQos", wkStpRetransmitionQos);
		model.addAttribute("wkStpUtilisationQos", wkStpUtilisationQos);
		
		return "wkStpQosDetail";
	}
}
