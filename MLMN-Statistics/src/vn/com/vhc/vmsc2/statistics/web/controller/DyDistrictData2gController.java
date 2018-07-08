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

import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyDistrictData2gDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyRegionData2gDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyDistrictData2g;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyRegionData2g;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;
import vn.com.vhc.vmsc2.statistics.web.utils.ModelAddtributes;


@Controller
@RequestMapping("/report/radio/district-data/dy/*")
public class DyDistrictData2gController extends BaseController {
	@Autowired
	private VRpDyDistrictData2gDAO vRpDyDistrictData2gDao;
	@Autowired
	private VRpDyRegionData2gDAO vRpDyRegionData2gDao;
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
		
		List<VRpDyDistrictData2g> vRpDyDistrict = vRpDyDistrictData2gDao.filter(s[0],s[1], province,district, region);

		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("district", district);
		model.addAttribute("region", region);
		model.addAttribute("vRpDyDistrict", vRpDyDistrict);

		List<VRpDyRegionData2g> vRpDyRegionList = vRpDyRegionData2gDao.filter(s[0],s[1], region);
		model.addAttribute("vRpDyRegionList", vRpDyRegionList);
		
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyDistrict"));

		return new ModelAndView("dyDistrictData2gList");
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
				startDay = endDay;
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

			List<VRpDyDistrictData2g> vRpDyDistrictList = vRpDyDistrictData2gDao.filter(df.format(startDay), df.format(endDay), province,district, region);

			model.addAttribute("vRpDyDistrictList", vRpDyDistrictList);
			model.addAttribute("province", province);
			model.addAttribute("district", district);
			model.addAttribute("region", region);
			
			List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
			model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyDistrict"));

			if (district != null && district.length() > 0) {
				List<String> categories = new ArrayList<String>();
				List<Float> packetChAllocSucrList = new ArrayList<Float>();
				List<Float> ulTbfSucrList = new ArrayList<Float>();
				List<Float> dlTbfSucrList = new ArrayList<Float>();
				for (VRpDyDistrictData2g vRpDyBsc : vRpDyDistrictList) {
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
			String[] checkColumns = {"GPRS_UL_DATA","GPRS_DL_DATA","EDGE_UL_DATA","EDGE_DL_DATA","GPRS_UL_DATA_THROUGHPUT","GPRS_DL_DATA_THROUGHPUT","EDGE_UL_DATA_THROUGHPUT","EDGE_DL_DATA_THROUGHPUT","UL_TBF_SUCR","DL_TBF_SUCR","PACKET_CH_ALLOC_SUCR"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpDyDistrict", 9));

			return "dyDistrictData2g";
		} catch (ParseException e) {
			saveError(request, e.toString());
		}

		return "dyDistrictData2g";
	}
}