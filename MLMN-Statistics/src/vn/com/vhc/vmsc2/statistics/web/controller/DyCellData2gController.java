package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.ParseException;
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
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.CellDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyCellData2gDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.Cell;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyCellData2g;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;
import vn.com.vhc.vmsc2.statistics.web.utils.ModelAddtributes;


@Controller
@RequestMapping("/report/radio/cell-data/dy/*")
public class DyCellData2gController extends BaseController {
	@Autowired
	private VRpDyCellData2gDAO vRpDyCellData2gDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private CellDAO cellDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("list")
	public ModelAndView dyList(@RequestParam(required = false) String region, @RequestParam(required = false) String cellid,
			@RequestParam(required = false) String province, @RequestParam(required = false) String bscid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model, HttpServletRequest request) {
		String[] s = ModelAddtributes.checkDate(model, startDate, endDate).split(";");

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
	
		List<VRpDyCellData2g> vRpDyCellData2g = vRpDyCellData2gDao.filter(s[0],s[1], cellid, province, bscid, region);

		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);

		model.addAttribute("province", province);
		model.addAttribute("bscid", bscid);
		model.addAttribute("cellid", cellid);

		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);

		model.addAttribute("vRpDyCellData2g", vRpDyCellData2g);
		model.addAttribute("region", region);
		
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyCell"));
		
		return new ModelAndView("dyCellData2gList");
	}

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = true) String cellid, @RequestParam(required = true) String bscid,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();

		try {
			Date startDay;
			Date endDay;
			if (endDate == null) {
				endDay = new Date(currentTime);
			} else {
				endDay = df.parse(endDate);
			}
			if (startDate == null) {
				startDay = endDay;
			} else {
				startDay = df.parse(startDate);
			}
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("endDate", df.format(endDay));

			List<VRpDyCellData2g> vRpDyCellData2gList = vRpDyCellData2gDao.filter(df.format(startDay), df.format(endDay), bscid, cellid);

			model.addAttribute("vRpDyCellData2gList", vRpDyCellData2gList);
			model.addAttribute("cellid", cellid);
			model.addAttribute("bscid", bscid);
			List<Bsc> bscList = bscDao.getAll();
			model.addAttribute("bscList", bscList);
			List<Cell> cellList = cellDao.getCellOfBsc(bscid);
			model.addAttribute("cellList", cellList);
			
			if (bscid != null && bscid.length() > 0 && cellid != null && cellid.length() > 0) {
				List<String> categories = new ArrayList<String>();
				List<Float> packetChAllocSucrList = new ArrayList<Float>();
				List<Float> ulTbfSucrList = new ArrayList<Float>();
				List<Float> dlTbfSucrList = new ArrayList<Float>();
				for (VRpDyCellData2g vRpDyBsc : vRpDyCellData2gList) {
					categories.add(df.format(vRpDyBsc.getDay()));
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
			}
			
			// checkBox
			String[] checkColumns = {"PDCH_ALLOCATED","PDCH_USED","PACKET_CH_ALLOC_ATT","PACKET_CH_ALLOC_FAIL","PACKET_CH_ALLOC_SUCR","UL_TBF_REQ","UL_TBF_FAIL","UL_TBF_SUCR","DL_TBF_REQ","DL_TBF_FAIL","DL_TBF_SUCR","GPRS_UL_DATA","GPRS_DL_DATA","EDGE_UL_DATA","EDGE_DL_DATA","GPRS_UL_DATA_THROUGHPUT","GPRS_DL_DATA_THROUGHPUT","EDGE_UL_DATA_THROUGHPUT","EDGE_DL_DATA_THROUGHPUT"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpDyCell", 8));
			
		} catch (ParseException e) {
			saveError(request, e.toString());
		}

		return "dyCellData2g";
	}
}
