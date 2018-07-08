package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.CellDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrCellData2gDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.Cell;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrCellData2g;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
@RequestMapping("/report/radio/cell-data/hr/*")
public class HrCellData2gController extends BaseController {
	@Autowired
	private VRpHrCellData2gDAO vRpHrCellData2gDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private CellDAO cellDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat ddf = new SimpleDateFormat("dd/MM");

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = false) String bscid, @RequestParam(required = false) String cellid,
			@RequestParam(required = false) String startHour, @RequestParam(required = false) String endHour, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();

		if (endDate == null) {
			endDate = new Date(currentTime);
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
		model.addAttribute("startHour", startHour + ":00");
		model.addAttribute("endHour", endHour + ":00");

		List<VRpHrCellData2g> vRpHrCellList = new ArrayList<VRpHrCellData2g>();
		Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(startDate, startHour, endDate, endHour);
		for (Date day : dates.keySet()) {
			vRpHrCellList.addAll(vRpHrCellData2gDao.filter(dates.get(day).get("startHour"), dates.get(day).get("endHour"), day, bscid, cellid));
		}
		model.addAttribute("vRpHrCellList", vRpHrCellList);
		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpHrCell"));
		
		// chart
		if (cellid != null && cellid.length() > 0) {
			List<String> categories = new ArrayList<String>();
			List<Float> packetChAllocSucrList = new ArrayList<Float>();
			List<Float> ulTbfSucrList = new ArrayList<Float>();
			List<Float> dlTbfSucrList = new ArrayList<Float>();

			Map<String, VRpHrCellData2g> vRpHrCellMap = new LinkedHashMap<String, VRpHrCellData2g>();
			for (VRpHrCellData2g vRpHrCell : vRpHrCellList) {
				vRpHrCellMap.put(Integer.toString(vRpHrCell.getHour()) + ":00 " + ddf.format(vRpHrCell.getDay()), vRpHrCell);
			}

			for (Date day : dates.keySet()) {
				String sHour = dates.get(day).get("startHour");
				String eHour = dates.get(day).get("endHour");

				if (sHour.isEmpty())
					sHour = "0";
				if (eHour.isEmpty())
					eHour = "23";

				int sHourNum = Integer.parseInt(sHour);
				int eHourNum = Integer.parseInt(eHour);

				for (int i = sHourNum; i <= eHourNum; i++) {
					String cat = Integer.toString(i) + ":00 " + ddf.format(day);
					VRpHrCellData2g vRpHrCell = vRpHrCellMap.get(cat);

					categories.add(cat);
					if (vRpHrCell == null) {
						packetChAllocSucrList.add(null);
						ulTbfSucrList.add(null);
						dlTbfSucrList.add(null);
					} else {
						packetChAllocSucrList.add(vRpHrCell.getPacketChAllocSucr());
						ulTbfSucrList.add(vRpHrCell.getUlTbfSucr());
						dlTbfSucrList.add(vRpHrCell.getDlTbfSucr());
					}
				}
			}

			Map<String, List<Float>> tDrprSeries = new LinkedHashMap<String, List<Float>>();
			tDrprSeries.put("Packet CH Alloc (%)--4572a7", packetChAllocSucrList);
			model.addAttribute("tDrprChart", Chart.multiColumn("tDrprChart", "Packet CH Alloc (%)", categories, tDrprSeries));
			
			Map<String, List<Float>> sDrprSeries = new LinkedHashMap<String, List<Float>>();
			sDrprSeries.put("UL TBF Rate (%)--aa4643", ulTbfSucrList);
			model.addAttribute("sDrprChart", Chart.multiColumn("sDrprChart", "UL TBF Rate (%)", categories, sDrprSeries));

			Map<String, List<Float>> cssrSeries = new LinkedHashMap<String, List<Float>>();
			cssrSeries.put("DL TBL Rate (%)--89a54e", dlTbfSucrList);
			model.addAttribute("cssrChart", Chart.multiColumn("cssrChart", "DL TBL Rate (%)", categories, cssrSeries));
		}
		

		// checkBox
		String[] checkColumns = {"PDCH_ALLOCATED","PDCH_USED","PACKET_CH_ALLOC_ATT","PACKET_CH_ALLOC_FAIL","PACKET_CH_ALLOC_SUCR","UL_TBF_REQ","UL_TBF_FAIL","UL_TBF_SUCR","DL_TBF_REQ","DL_TBF_FAIL","DL_TBF_SUCR","GPRS_UL_DATA","GPRS_DL_DATA","EDGE_UL_DATA","EDGE_DL_DATA","GPRS_UL_DATA_THROUGHPUT","GPRS_DL_DATA_THROUGHPUT","EDGE_UL_DATA_THROUGHPUT","EDGE_DL_DATA_THROUGHPUT"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpHrCell", 8));

		return "hrCellData2g";
	}
}
