package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.MnStpAssonciationQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnStpChecksumQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnStpDestinationQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnStpM3dataQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnStpM3perfQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnStpOutOfBlueQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnStpRetransmitionQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnStpUtilisationQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.StpDAO;
import vn.com.vhc.vmsc2.statistics.domain.MnStpAssonciationQos;
import vn.com.vhc.vmsc2.statistics.domain.MnStpChecksumQos;
import vn.com.vhc.vmsc2.statistics.domain.MnStpDestinationQos;
import vn.com.vhc.vmsc2.statistics.domain.MnStpM3dataQos;
import vn.com.vhc.vmsc2.statistics.domain.MnStpM3perfQos;
import vn.com.vhc.vmsc2.statistics.domain.MnStpOutOfBlueQos;
import vn.com.vhc.vmsc2.statistics.domain.MnStpRetransmitionQos;
import vn.com.vhc.vmsc2.statistics.domain.MnStpUtilisationQos;
import vn.com.vhc.vmsc2.statistics.domain.Stp;




@Controller
public class MnStpQosDetailController extends BaseController {
	@Autowired
	private StpDAO stpDao;
	@Autowired
	private MnStpAssonciationQosDAO mnStpAssonciationDao;
	@Autowired
	private MnStpChecksumQosDAO mnStpChecksumQosDao;
	@Autowired
	private MnStpDestinationQosDAO mnStpDestinationQosDao;
	@Autowired
	private MnStpM3dataQosDAO mnStpM3dataQosDao;
	@Autowired
	private MnStpM3perfQosDAO mnStpM3perfQosDao;
	@Autowired
	private MnStpOutOfBlueQosDAO mnStpOutOfBlueQosDao;
	@Autowired
	private MnStpRetransmitionQosDAO mnStpRetransmitionQosDao;
	@Autowired
	private MnStpUtilisationQosDAO mnStpUtilisationQosDao;

	@RequestMapping("/report/core/stp/mn")
	public String showReport(@RequestParam(required = true) String stpid, @RequestParam(required = false) String month, @RequestParam(required = false) String year, ModelMap model, HttpServletRequest request) {
		Calendar mCalendar = Calendar.getInstance();
		int mMonth;
		int mYear;
		
		if (month == null) {
			mCalendar.add(Calendar.MONTH,-1);
			mMonth = mCalendar.get(Calendar.MONTH)+1;
		} else {
			mMonth = Integer.parseInt(month);
		}
		
		if (year == null) {
			mYear = mCalendar.get(Calendar.YEAR);
		} else {
			mYear = Integer.parseInt(year);
		}

		Stp stp = stpDao.selectByPrimaryKey(stpid);
		MnStpAssonciationQos mnStpAssonciationQos = mnStpAssonciationDao.selectByPrimaryKey(mMonth, stpid, mYear);
		MnStpChecksumQos mnStpChecksumQos = mnStpChecksumQosDao.selectByPrimaryKey(mMonth, stpid, mYear);
		MnStpDestinationQos mnStpDestinationQos = mnStpDestinationQosDao.selectByPrimaryKey(mMonth, stpid, mYear);
		MnStpM3dataQos mnStpM3dataQos = mnStpM3dataQosDao.selectByPrimaryKey(mMonth, stpid, mYear);
		MnStpM3perfQos mnStpM3perfQos = mnStpM3perfQosDao.selectByPrimaryKey(mMonth, stpid, mYear);
		MnStpOutOfBlueQos mnStpOutOfBlueQos = mnStpOutOfBlueQosDao.selectByPrimaryKey(mMonth, stpid, mYear);
		MnStpRetransmitionQos mnStpRetransmitionQos = mnStpRetransmitionQosDao.selectByPrimaryKey(mMonth, stpid, mYear);
		MnStpUtilisationQos mnStpUtilisationQos = mnStpUtilisationQosDao.selectByPrimaryKey(mMonth, stpid, mYear);
//đã xong
		model.addAttribute("month", mMonth);
		model.addAttribute("year", mYear);
		model.addAttribute("stp", stp);
		model.addAttribute("mnStpAssonciationQos", mnStpAssonciationQos);
		model.addAttribute("mnStpChecksumQos", mnStpChecksumQos);
		model.addAttribute("mnStpDestinationQos", mnStpDestinationQos);
		model.addAttribute("mnStpM3dataQos", mnStpM3dataQos);
		model.addAttribute("mnStpM3perfQos", mnStpM3perfQos);
		model.addAttribute("mnStpOutOfBlueQos", mnStpOutOfBlueQos);
		model.addAttribute("mnStpRetransmitionQos", mnStpRetransmitionQos);
		model.addAttribute("mnStpUtilisationQos", mnStpUtilisationQos);
		
		return "mnStpQosDetail";
	}
}
