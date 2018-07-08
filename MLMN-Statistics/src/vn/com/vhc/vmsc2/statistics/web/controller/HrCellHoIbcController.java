package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.CellDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrCellHoIbcDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.Cell;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrCellHoIbc;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
@RequestMapping("/report/radio/ibc/cell-ho/hr/*")
public class HrCellHoIbcController extends BaseController {
	@Autowired
	private VRpHrCellHoIbcDAO vRpHrCellHoIbcDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private CellDAO cellDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("details")
	public String showReport(@RequestParam(required = true) String bscid, @RequestParam(required = true) String cellid,
			@RequestParam(required = false) String startHour, @RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model) {
		long currentTime = System.currentTimeMillis();

		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		if (endHour == null) {
			endHour = "23";
		} else {
			if(endHour.indexOf(":") >0)
				endHour = endHour.substring(0, endHour.indexOf(":"));
			else
				endHour = "23";
			}
		if (startHour == null) {
			startHour = "0";
		} else {	
			if(startHour.indexOf(":") >0)
				startHour = startHour.substring(0, startHour.indexOf(":"));
			else
				startHour = "0";
		}
		String[] hourList = {"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
		model.addAttribute("hourList", hourList);
		
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("startHour", startHour);
		model.addAttribute("endHour", endHour);

		List<VRpHrCellHoIbc> vRpHrCellHoIbcDetails = new ArrayList<VRpHrCellHoIbc>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			vRpHrCellHoIbcDetails.addAll(vRpHrCellHoIbcDao.filter(dates.get(day).get("startHour"), dates.get(day).get("endHour"), df.format(day), bscid, cellid));
		}

		model.addAttribute("vRpHrCellHoIbcDetails", vRpHrCellHoIbcDetails);
		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);

		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> inHoSucrList = new ArrayList<Float>();
		List<Float> ogHoSucrList = new ArrayList<Float>();

		for (VRpHrCellHoIbc vRpHrCellHoIbc : vRpHrCellHoIbcDetails) {
			categories.add(Integer.toString(vRpHrCellHoIbc.getHour()));
			inHoSucrList.add(vRpHrCellHoIbc.getInHoSucr());
			ogHoSucrList.add(vRpHrCellHoIbc.getOgHoSucr());
		}
		
		Map<String, List<Float>> inHoSucrSeries = new LinkedHashMap<String, List<Float>>();
		inHoSucrSeries.put("In_Ho_Sucr (%)--4572a7", inHoSucrList);
		model.addAttribute("inHoSucrChart", Chart.multiColumn("inHoSucrChart", "Incoming HO Successful Rate (%)", categories, inHoSucrSeries));

		Map<String, List<Float>> ogHoSucrSeries = new LinkedHashMap<String, List<Float>>();
		ogHoSucrSeries.put("Og_Ho_Sucr (%)--aa4643", ogHoSucrList);
		model.addAttribute("ogHoSucrChart", Chart.multiColumn("ogHoSucrChart", "Outgoing HO Successful Rate (%)", categories, ogHoSucrSeries));

		// checkBox
		String[] checkColumns = { "hoAtt", "hoSucr", "ogHoAtt", "ogHoSucr", "inHoAtt", "inHoSucr" };
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 6));

		return "hrCellHoIbcDetails";
	}
}
