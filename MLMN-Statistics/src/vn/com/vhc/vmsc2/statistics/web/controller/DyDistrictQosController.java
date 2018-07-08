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
import vn.com.vhc.vmsc2.statistics.dao.VRpDyDistrictDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyRegionDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyDistrict;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyRegion;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;
import vn.com.vhc.vmsc2.statistics.web.utils.ModelAddtributes;


@Controller
@RequestMapping("/report/radio/district/dy/*")
public class DyDistrictQosController extends BaseController {
	@Autowired
	private VRpDyDistrictDAO vRpDyDistrictDao;
	@Autowired
	private VRpDyRegionDAO vRpDyRegionDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("list")
	public ModelAndView dyList(@RequestParam(required = false) String region,@RequestParam(required = false) String province, @RequestParam(required = false) String district, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model) {
		String[] s = ModelAddtributes.checkDate(model, startDate, endDate).split(";");
		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";
		
		List<VRpDyDistrict> vRpDyDistrict = vRpDyDistrictDao.filter(s[0],s[1], province,district, region);

		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("district", district);
		model.addAttribute("region", region);
		model.addAttribute("vRpDyDistrict", vRpDyDistrict);

		List<VRpDyRegion> vRpDyRegionList = vRpDyRegionDao.filter(s[0],s[1], region);
		model.addAttribute("vRpDyRegionList", vRpDyRegionList);
		
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyDistrict"));

		return new ModelAndView("dyDistrictList");
	}

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required = true) String province, @RequestParam(required = true) String district, @RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request) {
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

			List<VRpDyDistrict> vRpDyDistrictList = vRpDyDistrictDao.filter(df.format(startDay), df.format(endDay), province,district, region);

			model.addAttribute("vRpDyDistrictList", vRpDyDistrictList);
			model.addAttribute("province", province);
			model.addAttribute("district", district);
			model.addAttribute("region", region);
			
			List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
			model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyDistrict"));

			if (district != null && district.length() > 0) {
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

				for (VRpDyDistrict vRpDyDistrict : vRpDyDistrictList) {
					categories.add(df.format(vRpDyDistrict.getDay()));
					cssrList.add(vRpDyDistrict.getCssr());
					tTrafList.add(vRpDyDistrict.gettTraf());
					tTrafhList.add(vRpDyDistrict.gettTrafh());
					tDrprList.add(vRpDyDistrict.gettDrpr());
					tBlkrList.add(vRpDyDistrict.gettBlkr());
					sDrprList.add(vRpDyDistrict.getsDrpr());
					sBlkrList.add(vRpDyDistrict.getsBlkr());
					haftratePercentList.add(vRpDyDistrict.getHaftratePercent());
					ogHoSucrList.add(vRpDyDistrict.getOgHoSucr());
					inHoSucrList.add(vRpDyDistrict.getInHoSucr());
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
			}
		
			// checkBox
				String[] checkColumns = {"T_TRAF", "T_DRPR", "T_NBLKR", "T_HOBLKR", "CSSR", "SSR", "S_BLKR", "S_DRPR", "TSR", "HAFT_RATE", "In_Ho_Sucr", "Og_Ho_Sucr", "DATALOAD"};
				model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpDyDistrict", 8));

			return "dyDistrictQos";
		} catch (ParseException e) {
			saveError(request, e.toString());
		}
		
		return "dyDistrictQos";
	}
}