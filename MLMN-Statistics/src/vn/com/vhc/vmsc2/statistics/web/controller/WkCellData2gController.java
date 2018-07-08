package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.CellDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkCellData2gDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.Cell;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkCellData2g;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
@RequestMapping("/report/radio/cell-data/wk/*")
public class WkCellData2gController extends BaseController {
	@Autowired
	private VRpWkCellData2gDAO vRpWkCellGprsQosDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private CellDAO cellDao;

	@RequestMapping("list")
	public ModelAndView wkList(@RequestParam(required = false) String region, @RequestParam(required = false) String bscid,
			@RequestParam(required = false) String province, @RequestParam(required = false) String cellid, @RequestParam(required = false) Integer startWeek,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek,
			@RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		
		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
		{
			endYear = calender.get(Calendar.YEAR);
		}
		DateTime dt = new DateTime();
		if (endWeek == null) {
			dt = dt.minusWeeks(1);
			endWeek = dt.getWeekOfWeekyear();
			endYear = dt.getYear();
		}
		if (startWeek == null) {
			startWeek = endWeek;
			startYear = endYear;
		}
		model.addAttribute("startWeek", startWeek);
		model.addAttribute("endWeek", endWeek);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";
		
		model.addAttribute("region", region);

		List<VRpWkCellData2g> vRpWkCellGprsQos = vRpWkCellGprsQosDao.filter(startWeek, startYear, endWeek, endYear, cellid, province, bscid, region);

		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);

		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		model.addAttribute("province", province);

		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);

		model.addAttribute("vRpWkCellData2g", vRpWkCellGprsQos);

		return new ModelAndView("wkCellData2gList");
	}

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = true) String cellid, @RequestParam(required = true) String bscid,
			@RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {

		Calendar calender = Calendar.getInstance();
		if (endYear ==null || endYear < 1900)
		{
			endYear = calender.get(Calendar.YEAR);
		}
		DateTime dt = new DateTime();
		if (endWeek == null) {
			dt = dt.minusWeeks(1);
			endWeek = dt.getWeekOfWeekyear();
			endYear = dt.getYear();
		}
		if (startWeek == null) {
			dt = dt.minusWeeks(5);
			startWeek = dt.getWeekOfWeekyear();
			startYear = dt.getYear();
		}
		model.addAttribute("startWeek", startWeek);
		model.addAttribute("endWeek", endWeek);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);

		List<VRpWkCellData2g> vRpWkCellGprsQosList = vRpWkCellGprsQosDao.filter(startWeek, startYear, endWeek, endYear, bscid, cellid);

		model.addAttribute("vRpWkCellGprsQosList", vRpWkCellGprsQosList);
		model.addAttribute("cellid", cellid);
		model.addAttribute("bscid", bscid);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		List<Cell> cellList = cellDao.getCellOfBsc(bscid);
		model.addAttribute("cellList", cellList);
		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> packetChAllocSucrList = new ArrayList<Float>();
		List<Float> ulTbfSucrList = new ArrayList<Float>();
		List<Float> dlTbfSucrList = new ArrayList<Float>();
		for (VRpWkCellData2g vRpDyBsc : vRpWkCellGprsQosList) {
			categories.add(vRpDyBsc.getWeek()+"/"+vRpDyBsc.getYear());
			packetChAllocSucrList.add(vRpDyBsc.getPacketChAllocSucr());
			ulTbfSucrList.add(vRpDyBsc.getUlTbfSucr());
			dlTbfSucrList.add(vRpDyBsc.getDlTbfSucr());

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
		
		// checkBox
		String[] checkColumns = {"PDCH_ALLOCATED","PDCH_USED","PACKET_CH_ALLOC_ATT","PACKET_CH_ALLOC_FAIL","PACKET_CH_ALLOC_SUCR","UL_TBF_REQ","UL_TBF_FAIL","UL_TBF_SUCR","DL_TBF_REQ","DL_TBF_FAIL","DL_TBF_SUCR","GPRS_UL_DATA","GPRS_DL_DATA","EDGE_UL_DATA","EDGE_DL_DATA","GPRS_UL_DATA_THROUGHPUT","GPRS_DL_DATA_THROUGHPUT","EDGE_UL_DATA_THROUGHPUT","EDGE_DL_DATA_THROUGHPUT"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpWkCell", 7));

		return "wkCellData2g";
	}
}
