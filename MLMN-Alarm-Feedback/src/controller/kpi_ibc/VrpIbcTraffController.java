package controller.kpi_ibc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dao.VRpDyIbcDAO;
import dao.VRpIbc3gDAO;
import vo.VRpDyIbc;
import vo.VRpIbc3g;

@Controller
@RequestMapping("/report/ibc-traffic/*")
public class VrpIbcTraffController {
	@Autowired
	VRpDyIbcDAO vRpDyIbcDAO;
	@Autowired
	VRpIbc3gDAO vRpIbc3gDAO;

	@RequestMapping(value = "hrList")
	public String showList(@RequestParam(required = false) String cellid,
			 @RequestParam(required = false) String startDate,
			 @RequestParam(required = false) String endDate,
			 @RequestParam(required = true) String network,
			 ModelMap model) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		if (startDate == null) {
			startDate = df.format(new Date());
			
		}
		
		if (endDate == null) {
			endDate = startDate;
		}
		
;
		List<String> cellList;
		model.addAttribute("trafficThreshold", vRpDyIbcDAO.getThresholdParam("kpi.ibc.traff.threshold"));
		model.addAttribute("data3gThreshold", vRpDyIbcDAO.getThresholdParam("kpi.ibc.data3g.threshold"));
		if (network.equals("2g")) {
			cellList = vRpDyIbcDAO.getListCellIbc();
			List<VRpDyIbc> dataList = vRpDyIbcDAO.getTrafficDataList(startDate, endDate, cellid);
			model.addAttribute("dataList", dataList);
		} else {
			cellList = vRpIbc3gDAO.getListCellIbc3g();
			List<VRpIbc3g> data3gList = vRpIbc3gDAO.getTraffic3gDataList(startDate, endDate,  cellid);
			model.addAttribute("data3gList", data3gList);
		}
	
		model.addAttribute("network", network); 
		model.addAttribute("cellid", cellid);
		model.addAttribute("cellList", cellList);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		return "jsp/ibc/ibcTraff";
	}
}
