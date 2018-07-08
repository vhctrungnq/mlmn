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
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCell3GMovementDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc3G;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCell3GMovement;
import vn.com.vhc.vmsc2.statistics.web.utils.ModelAddtributes;

/**
 * 
 * @author Mr. ThangPX
 * @return Authencation Report Level Daily, Hourly 
 */
@Controller 
@RequestMapping("/report/radio/cell/traffic-movment-3g/*")
public class DyCellMovement3gController extends BaseController {
	@Autowired
	private VRpDyCell3GMovementDAO dyCellDAO;
	@Autowired
	private Bsc3GDAO bscDao;

	@RequestMapping(value = "hr")
	public String showReport(@RequestParam(required = false) String bscid,@RequestParam(required = false) String cellid, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, ModelMap model, HttpServletRequest request) {

		if(cellid == null)
			cellid = "";
		List<Bsc3G> rncList = bscDao.getAll();
		bscid = ModelAddtributes.checkRnc(model, rncList, bscid, "traffic-movment-3g");
		String[] s = ModelAddtributes.checkHour(model, startDate, startHour, endHour).split(";");
		List<VRpDyCell3GMovement> cellMovementList = dyCellDAO.getTrafficMovementHr(bscid,cellid,s[0],s[1], s[2]);
		model.addAttribute("cellMovementList", cellMovementList);
		model.addAttribute("cellid", cellid); 
		return "dyCellTrafficMovement3g";
	}

	// Daily report
	@RequestMapping(value = "dy")
	public String showReport(@RequestParam(required = false) String bscid,@RequestParam(required = false) String cellid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model, HttpServletRequest request) {
		if(cellid == null)
			cellid = "";
		List<Bsc3G> rncList = bscDao.getAll();
		bscid = ModelAddtributes.checkRnc(model, rncList, bscid, "traffic-movment-3g");
		String[] s = ModelAddtributes.checkDate2(model, startDate, endDate).split(";");
		
		List<VRpDyCell3GMovement> cellMovementList = dyCellDAO.getTrafficMovementDy(bscid,cellid,s[0],s[1]);
		
		
		 model.addAttribute("cellMovementList", cellMovementList);
		 model.addAttribute("cellid", cellid);
		 return "dyCellTrafficMovement3g";
	}
  
}
