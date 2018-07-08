package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.HrStpAssonciationQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrStpChecksumQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrStpDestinationQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrStpM3dataQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrStpM3perfQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrStpOutOfBlueQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrStpRetransmitionQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrStpUtilisationQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.StpDAO;
import vn.com.vhc.vmsc2.statistics.domain.HrStpAssonciationQos;
import vn.com.vhc.vmsc2.statistics.domain.HrStpChecksumQos;
import vn.com.vhc.vmsc2.statistics.domain.HrStpDestinationQos;
import vn.com.vhc.vmsc2.statistics.domain.HrStpM3dataQos;
import vn.com.vhc.vmsc2.statistics.domain.HrStpM3perfQos;
import vn.com.vhc.vmsc2.statistics.domain.HrStpOutOfBlueQos;
import vn.com.vhc.vmsc2.statistics.domain.HrStpRetransmitionQos;
import vn.com.vhc.vmsc2.statistics.domain.HrStpUtilisationQos;
import vn.com.vhc.vmsc2.statistics.domain.Stp;




@Controller
public class HrStpQosDetailController extends BaseController {
	@Autowired
	private StpDAO stpDao;
	@Autowired
	private HrStpAssonciationQosDAO hrStpAssonciationDao;
	@Autowired
	private HrStpChecksumQosDAO hrStpChecksumQosDao;
	@Autowired
	private HrStpDestinationQosDAO hrStpDestinationQosDao;
	@Autowired
	private HrStpM3dataQosDAO hrStpM3dataQosDao;
	@Autowired
	private HrStpM3perfQosDAO hrStpM3perfQosDao;
	@Autowired
	private HrStpOutOfBlueQosDAO hrStpOutOfBlueQosDao;
	@Autowired
	private HrStpRetransmitionQosDAO hrStpRetransmitionQosDao;
	@Autowired
	private HrStpUtilisationQosDAO hrStpUtilisationQosDao;

	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("/report/core/stp/hr")
	public String showReport(@RequestParam(required = true) String stpid, @RequestParam(required = false) String day, @RequestParam(required = false) Integer hour, ModelMap model, HttpServletRequest request) {
		
		try {
			Date mDay;
			int mHour;
			if (day == null) {
				long currentTime = System.currentTimeMillis();
				mDay = new Date(currentTime - 24 * 60 * 60 * 1000);
			} else {
				mDay = dateFormat.parse(day);
			}
			if (hour == null) mHour = 12;
			else mHour = hour;
			Stp stp = stpDao.selectByPrimaryKey(stpid);
			HrStpAssonciationQos hrStpAssonciationQos = hrStpAssonciationDao.selectByPrimaryKey(mDay, mHour, stpid);
			HrStpChecksumQos hrStpChecksumQos = hrStpChecksumQosDao.selectByPrimaryKey(mDay, mHour, stpid);
			HrStpDestinationQos hrStpDestinationQos = hrStpDestinationQosDao.selectByPrimaryKey(mDay, mHour, stpid);
			HrStpM3dataQos hrStpM3dataQos = hrStpM3dataQosDao.selectByPrimaryKey(mDay, mHour, stpid);
			HrStpM3perfQos hrStpM3perfQos = hrStpM3perfQosDao.selectByPrimaryKey(mDay, mHour, stpid);
			HrStpOutOfBlueQos hrStpOutOfBlueQos = hrStpOutOfBlueQosDao.selectByPrimaryKey(mDay, mHour, stpid);
			HrStpRetransmitionQos hrStpRetransmitionQos = hrStpRetransmitionQosDao.selectByPrimaryKey(mDay, mHour, stpid);
			HrStpUtilisationQos hrStpUtilisationQos = hrStpUtilisationQosDao.selectByPrimaryKey(mDay, mHour, stpid);

			model.addAttribute("day", dateFormat.format(mDay));
			model.addAttribute("hour", mHour);
			model.addAttribute("stp", stp);
			model.addAttribute("hrStpAssonciationQos", hrStpAssonciationQos);
			model.addAttribute("hrStpChecksumQos", hrStpChecksumQos);
			model.addAttribute("hrStpDestinationQos", hrStpDestinationQos);
			model.addAttribute("hrStpM3dataQos", hrStpM3dataQos);
			model.addAttribute("hrStpM3perfQos", hrStpM3perfQos);
			model.addAttribute("hrStpOutOfBlueQos", hrStpOutOfBlueQos);
			model.addAttribute("hrStpRetransmitionQos", hrStpRetransmitionQos);
			model.addAttribute("hrStpUtilisationQos", hrStpUtilisationQos);
		} catch (ParseException e) {
			saveError(request, e.toString());
		}
		
		return "hrStpQosDetail";
	}
}
