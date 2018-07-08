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

import vn.com.vhc.vmsc2.statistics.dao.VRpDyPASR3gDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrPASR3gDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnPASR3gDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpQrPASR3gDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkPASR3gDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpYrPASR3gDAO;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrPASR3g;

@Controller
@RequestMapping("/report/pasr/*")
public class VRpPASRController {
	
	@Autowired
	private VRpHrPASR3gDAO vRpHrPASR3gDAO;
	@Autowired
	private VRpDyPASR3gDAO vRpDyPASR3gDAO;
	@Autowired
	private VRpWkPASR3gDAO vRpWkPASR3gDAO;
	@Autowired
	private VRpMnPASR3gDAO vRpMnPASR3gDAO;
	@Autowired
	private VRpQrPASR3gDAO vRpQrPASR3gDAO;
	@Autowired
	private VRpYrPASR3gDAO vRpYrPASR3gDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping("hrList")
	public String hrList(@RequestParam(required = false) String bscid,
						 @RequestParam(required = false) String cellid,
						 @RequestParam(required = false) String startDate,
						 @RequestParam(required = false) String endDate,
						 @RequestParam(required = false) Integer startHour,
						 @RequestParam(required = false) Integer endHour,
						 ModelMap model) {
		
		if (startDate == null) {
			startDate = df.format(new Date());
			
		}
		
		if (endDate == null) {
			endDate = startDate;
		}
		
		if (startHour == null) {
			startHour = 0;
		}
		
		if (endHour == null) {
			endHour = 23;
		}
		
/*		Integer[] hourList = new Integer[24];
		for (int i = 0; i < 23; i++) {
			hourList[i] = i;
		}*/
		List<String> bscList = vRpHrPASR3gDAO.getAllBsc();
//		List<String> cellList = vRpHrPASR3gDAO.getAllCellByBsc(bscid);
		List<VRpHrPASR3g> hrDataList = vRpHrPASR3gDAO.getHrData(bscid, cellid, startHour, startDate, endHour, endDate);
	
		
		model.addAttribute("bscList", bscList);
		model.addAttribute("bscid", bscid);
		model.addAttribute("cellid", cellid);
		model.addAttribute("startHour", startHour);
		model.addAttribute("endHour", endHour);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("hrDataList", hrDataList);
		return "pasr/hrList";
	}
	
	public String dyList(@RequestParam(required = false) String bscid,
						 @RequestParam(required = false) String celllid,
						 @RequestParam(required = false) Date startDate,
						 @RequestParam(required = false) Date endDate) {
		
		return "pasr/dyList";
	}
}
