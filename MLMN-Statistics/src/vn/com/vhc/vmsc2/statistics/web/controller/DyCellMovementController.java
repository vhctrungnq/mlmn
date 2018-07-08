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
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellMovementDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellMovement;
import vn.com.vhc.vmsc2.statistics.web.utils.ModelAddtributes;

/**
 * 
 * @author Mr. ThangPX
 * @return Authencation Report Level Daily, Hourly 
 */
@Controller 
@RequestMapping("/report/radio/cell/traffic-movment/*")
public class DyCellMovementController extends BaseController {
	@Autowired
	private VRpDyCellMovementDAO dyCellDAO;
	@Autowired
	private BscDAO bscDao; 

	@RequestMapping(value = "hr")
	public String showReport(@RequestParam(required = false) String bscid,@RequestParam(required = false) String cellid, @RequestParam(required = false) String startHour,
			@RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate, ModelMap model, HttpServletRequest request) {

		if(cellid == null)
			cellid = "";
		List<Bsc> bscList = bscDao.getAll();
		bscid = ModelAddtributes.checkBscid(model, bscList, bscid, "traffic-movment");
		String[] s = ModelAddtributes.checkHour(model, startDate, startHour, endHour).split(";");
		List<VRpDyCellMovement> cellMovementList = dyCellDAO.getTrafficMovementHr(bscid,cellid,s[0],s[1], s[2]);
		model.addAttribute("cellMovementList", cellMovementList);
		model.addAttribute("cellid", cellid);
		return "dyCellTrafficMovement";
	}

	// Daily report
	@RequestMapping(value = "dy")
	public String showReport(@RequestParam(required = false) String bscid,@RequestParam(required = false) String cellid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model, HttpServletRequest request) {
		if(cellid == null)
			cellid = "";
		List<Bsc> bscList = bscDao.getAll();
		bscid = ModelAddtributes.checkBscid(model, bscList, bscid, "traffic-movment");
		String[] s = ModelAddtributes.checkDate2(model, startDate, endDate).split(";");
		
		List<VRpDyCellMovement> cellMovementList = dyCellDAO.getTrafficMovementDy(bscid,cellid,s[0],s[1]);
		
		
		 model.addAttribute("cellMovementList", cellMovementList);
		 model.addAttribute("cellid", cellid);
		 return "dyCellTrafficMovement";
	}
  
}
