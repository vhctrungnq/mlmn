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
import vo.CSystemConfigs;
import vo.VRpDyIbc;
import vo.VRpIbc3g;


@Controller
@RequestMapping("/report/ibc/*")
public class VRpDyIbcController {
	
	@Autowired
	VRpDyIbcDAO vRpDyIbcDAO;
	@Autowired
	VRpIbc3gDAO vRpIbc3gDAO;
	
	@RequestMapping(value = "hrList")
	public String showList(@RequestParam(required = false) String cellid,
			 @RequestParam(required = false) String startDate,
			 @RequestParam(required = false) String endDate,
			 @RequestParam(required = false) Integer startHour,
			 @RequestParam(required = false) Integer endHour,
			 @RequestParam(required = true) String network,
			 ModelMap model) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
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
;
		List<String> cellList;
		if (network.equals("2g")) {
			cellList = vRpDyIbcDAO.getListCellIbc();
			List<VRpDyIbc> dataList = vRpDyIbcDAO.getDataList(startDate, endDate, startHour, endHour, cellid);
			model.addAttribute("dataList", dataList);
		} else {
			cellList = vRpIbc3gDAO.getListCellIbc3g();
			List<VRpIbc3g> data3gList = vRpIbc3gDAO.getDataList(startDate, endDate, startHour, endHour, cellid);
			model.addAttribute("data3gList", data3gList);
		}

		model.addAttribute("cssrThreshold", vRpDyIbcDAO.getThresholdParam("kpi.ibc.cssr2g.threshold"));
		model.addAttribute("cdrThreshold", vRpDyIbcDAO.getThresholdParam("kpi.ibc.cdr2g.threshold"));
		model.addAttribute("numSdcchDropBlockThreshold", vRpDyIbcDAO.getThresholdParam("kpi.ibc.num.sdcch.drop.block.threshold"));
		model.addAttribute("numDropThreshold", vRpDyIbcDAO.getThresholdParam("kpi.ibc.num.drop2g.threshold"));
		model.addAttribute("pasr3gThreshold", vRpDyIbcDAO.getThresholdParam("kpi.ibc.pasr3g.threshold"));
		model.addAttribute("padr3gThreshold", vRpDyIbcDAO.getThresholdParam("kpi.ibc.padr3g.threshold"));
		model.addAttribute("cssr3gThreshold", vRpDyIbcDAO.getThresholdParam("kpi.ibc.cssr3g.threshold"));
		model.addAttribute("cdr3gThreshold", vRpDyIbcDAO.getThresholdParam("kpi.ibc.cdr3g.threshold"));
		model.addAttribute("psDrop3gThreShold", vRpDyIbcDAO.getThresholdParam("kpi.ibc.num.psdrop3g.threshold"));
		model.addAttribute("psFailThreshold", vRpDyIbcDAO.getThresholdParam("kpi.ibc.num.psfail.threshold"));
		model.addAttribute("csDrop3gThreshold", vRpDyIbcDAO.getThresholdParam("kpi.ibc.num.csdrop3g.threshold"));
		model.addAttribute("csFailThreshold", vRpDyIbcDAO.getThresholdParam("kpi.ibc.num.csfail.threshold"));
		model.addAttribute("rtpwThreshold", vRpDyIbcDAO.getThresholdParam("kpi.ibc.rtpw.threshold"));
		model.addAttribute("network", network); 
		model.addAttribute("cellid", cellid);
		model.addAttribute("cellList", cellList);
		model.addAttribute("startHour", startHour);
		model.addAttribute("endHour", endHour);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		
		return "jsp/ibc/dyIbc";
	}
}
