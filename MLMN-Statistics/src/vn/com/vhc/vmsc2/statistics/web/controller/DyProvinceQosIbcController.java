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

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyProvinceIbcDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyRegionIbcDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyProvinceIbc;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyRegionIbc;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
@RequestMapping("/report/radio/ibc/province/dy/*")
public class DyProvinceQosIbcController extends BaseController {
	@Autowired
	private VRpDyProvinceIbcDAO vRpDyProvinceIbcDao;
	@Autowired
	private VRpDyRegionIbcDAO vRpDyRegionIbcDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("list")
	public ModelAndView dyList(@RequestParam(required = false) String region,@RequestParam(required = false) String province, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model) {
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		
		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";
		
		List<VRpDyProvinceIbc> vRpDyProvinceIbc = vRpDyProvinceIbcDao.filter(df.format(startDate), df.format(endDate), province, region);

		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("region", region);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("vRpDyProvinceIbc", vRpDyProvinceIbc);

		List<VRpDyRegionIbc> vRpDyRegionList = vRpDyRegionIbcDao.filter(df.format(startDate), df.format(endDate), region);
		model.addAttribute("vRpDyRegionList", vRpDyRegionList);
		
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyProvinceIbc"));

		return new ModelAndView("dyProvinceIbcList");
	}

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required = true) String province, @RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();

		try {
			Date startDay;
			Date endDay;
			if (endDate == null) {
				endDay = new Date(currentTime - 24 * 60 * 60 * 1000);
			} else {
				endDay = df.parse(endDate);
			}
			if (startDate == null) {
				DateTime dt = new DateTime(endDay);
				startDay = dt.minusDays(7).toDate();
			} else {
				startDay = df.parse(startDate);
			}
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("endDate", df.format(endDay));

			List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
			model.addAttribute("provinceList", provinceList);
			
			List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
			model.addAttribute("regionList", regionList);
			
			if (region == null)
				region = "";

			List<VRpDyProvinceIbc> vRpDyProvinceIbcList = vRpDyProvinceIbcDao.filter(df.format(startDay), df.format(endDay), province, region);

			model.addAttribute("vRpDyProvinceIbcList", vRpDyProvinceIbcList);
			model.addAttribute("province", province);
			model.addAttribute("region", region);
			
			List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
			model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyProvinceIbc"));
			
			// start chart
			List<String> categories = new ArrayList<String>();
			List<Float> cssrList = new ArrayList<Float>();
			List<Float> tTrafList = new ArrayList<Float>();
			List<Float> tTrafhList = new ArrayList<Float>();
			List<Float> haftratePercentList = new ArrayList<Float>();
			List<Float> tDrprList = new ArrayList<Float>();
			List<Float> tBlkrList = new ArrayList<Float>();
			List<Float> sDrprList = new ArrayList<Float>();
			List<Float> sBlkrList = new ArrayList<Float>();
			List<Float> ogHoSucrList = new ArrayList<Float>();
			List<Float> inHoSucrList = new ArrayList<Float>();

			for (VRpDyProvinceIbc vRpDyProvinceIbc : vRpDyProvinceIbcList) {
				categories.add(df.format(vRpDyProvinceIbc.getDay()));
				cssrList.add(vRpDyProvinceIbc.getCssr());
				tTrafList.add(vRpDyProvinceIbc.gettTraf());
				tTrafhList.add(vRpDyProvinceIbc.gettTrafh());
				tDrprList.add(vRpDyProvinceIbc.gettDrpr());
				tBlkrList.add(vRpDyProvinceIbc.gettBlkr());
				sDrprList.add(vRpDyProvinceIbc.getsDrpr());
				sBlkrList.add(vRpDyProvinceIbc.getsBlkr());
				haftratePercentList.add(vRpDyProvinceIbc.getHaftratePercent());
				ogHoSucrList.add(vRpDyProvinceIbc.getOgHoSucr());
				inHoSucrList.add(vRpDyProvinceIbc.getInHoSucr());
			}

			Map<String, List<Float>> tDrprSeries = new LinkedHashMap<String, List<Float>>();
			tDrprSeries.put("Drop Rate (%)--4572a7", tDrprList);
			model.addAttribute("tDrprChart", Chart.multiColumn("tDrprChart", "TCH Drop Rate (%)", categories, tDrprSeries));
			
			Map<String, List<Float>> sDrprSeries = new LinkedHashMap<String, List<Float>>();
			sDrprSeries.put("Drop Rate (%)--aa4643", sDrprList);
			model.addAttribute("sDrprChart", Chart.multiColumn("sDrprChart", "SDCCH Drop Rate (%)", categories, sDrprSeries));

			Map<String, List<Float>> cssrSeries = new LinkedHashMap<String, List<Float>>();
			cssrSeries.put("CSSR (%)--89a54e", cssrList);
			model.addAttribute("cssrChart", Chart.multiColumn("cssrChart", "CSSR (%)", categories, cssrSeries));

			Map<String, List<Float>> tBlkrSeries = new LinkedHashMap<String, List<Float>>();
			tBlkrSeries.put("Block Rate (%)--80699b", tBlkrList);
			model.addAttribute("tBlkrChart", Chart.multiColumn("tBlkrChart", "TCH Block Rate (%)", categories, tBlkrSeries));

			Map<String, List<Float>> sBlkrSeries = new LinkedHashMap<String, List<Float>>();
			sBlkrSeries.put("Block Rate (%)--db843d", sBlkrList);
			model.addAttribute("sBlkrChart", Chart.multiColumn("sBlkrChart", "SDCCH Block Rate (%)", categories, sBlkrSeries));

			Map<String, List<Float>> tTrafSeries = new LinkedHashMap<String, List<Float>>();
			tTrafSeries.put("Traffic--aa4643", tTrafList);
			tTrafSeries.put("Haft Traffic--89a54e", tTrafhList);
			model.addAttribute("tTrafChart", Chart.multiColumn("tTrafChart", "Traffic", categories, tTrafSeries));
			
			// checkBox
			String[] checkColumns = { "T_TRAF", "T_DRPR", "T_NBLKR", "T_HOBLKR", "CSSR", "SSR", "S_BLKR", "S_DRPR", "TSR", "HAFT_RATE", "In_Ho_Sucr", "Og_Ho_Sucr", "DATALOAD" };
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpDyProvinceIbc", 8)); 

			return "dyProvinceQosIbc";
		} catch (ParseException e) {
			saveError(request, e.toString());
		}

		return "dyProvinceQosIbc";
	}
}
