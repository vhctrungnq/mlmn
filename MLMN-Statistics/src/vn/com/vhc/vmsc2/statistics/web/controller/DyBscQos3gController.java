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

import vn.com.vhc.vmsc2.statistics.dao.Bsc3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyBsc3GDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyRegion3GDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBsc3G;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyRegion3G;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;
import vn.com.vhc.vmsc2.statistics.web.utils.ModelAddtributes;


@Controller
@RequestMapping("/report/radio3g/rnc/dy/*")
public class DyBscQos3gController extends BaseController {
	@Autowired
	private VRpDyBsc3GDAO vRpDyBscDao; 
	@Autowired
	private Bsc3GDAO bsc3g;
	@Autowired
	private VRpDyRegion3GDAO vRpDyRegionDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping("list")
	public ModelAndView dyList(@RequestParam(required = false) String region,@RequestParam(required = false) String bscid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model) {
		String[] s = ModelAddtributes.checkDate(model, startDate, endDate).split(";");

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		if (region == null)
			region = "";
		
		List<VRpDyBsc3G> vRpDyBsc = vRpDyBscDao.filter(s[0],s[1], bscid, region);

		List<VRpDyRegion3G> vRpDyRegionList = vRpDyRegionDao.filter(s[0],s[1], region);
		
		model.addAttribute("bscid", bscid);
		List<String> bscList = bsc3g.getAllBscName();
		model.addAttribute("bscList", bscList);
		model.addAttribute("vRpDyBsc", vRpDyBsc);
		model.addAttribute("vRpDyRegionList", vRpDyRegionList);
		model.addAttribute("region", region);
		
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyBsc"));
		return new ModelAndView("dyBsc3GList");
	}

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required = false) String bscid, @RequestParam(required = false) String startDate,
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
				startDay = new Date(endDay.getTime() + 25 * 24 * 60 * 60 * 1000);
			} else {
				startDay = df.parse(startDate);
			}
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("endDate", df.format(endDay));

			List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
			model.addAttribute("regionList", regionList);
			
			List<VRpDyBsc3G> vRpDyBscList = vRpDyBscDao.filter(df.format(startDay), df.format(endDay), bscid, region);

			model.addAttribute("vRpDyBscList", vRpDyBscList);
			model.addAttribute("bscid", bscid);
			List<String> bscList = bsc3g.getAllBscName();
			model.addAttribute("bscList", bscList);
			List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
			model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyBsc"));
			model.addAttribute("region", region);
			
			/* Chart Start */
			List<String> categories = new ArrayList<String>();
			List<Float> speechCssrList = new ArrayList<Float>();
			List<Float> cs64CssrList = new ArrayList<Float>();
			List<Float> psr99CssrList = new ArrayList<Float>();
			List<Float> hsupaCssrList = new ArrayList<Float>();
			List<Float> hsdpaCssrList = new ArrayList<Float>();
			List<Float> speechDrprList = new ArrayList<Float>();
			List<Float> cs64DrprList = new ArrayList<Float>();
			List<Float> psr99DrprList = new ArrayList<Float>();
			List<Float> hsupaDrprList = new ArrayList<Float>();
			List<Float> hsdpaDrprList = new ArrayList<Float>();
			for (VRpDyBsc3G vRpDyBsc : vRpDyBscList) {
				categories.add(df.format(vRpDyBsc.getDay()));
				speechCssrList.add(vRpDyBsc.getSpeechCssr());
				cs64CssrList.add(vRpDyBsc.getCs64Cssr());
				psr99CssrList.add(vRpDyBsc.getPsr99Cssr());
				hsupaCssrList.add(vRpDyBsc.getHsupaCssr());
				hsdpaCssrList.add(vRpDyBsc.getHsdpaCssr());
				speechDrprList.add(vRpDyBsc.getSpeechDrpr());
				cs64DrprList.add(vRpDyBsc.getCs64Drpr());
				psr99DrprList.add(vRpDyBsc.getPsr99Drpr());
				hsupaDrprList.add(vRpDyBsc.getHsupaDrpr());
				hsdpaDrprList.add(vRpDyBsc.getHsdpaDrpr());
			}

			Map<String, List<Float>> speechCssrSeries = new LinkedHashMap<String, List<Float>>();
			speechCssrSeries.put("Speech CSSR (%)--4572a7", speechCssrList);
			model.addAttribute("speechCssrChart", Chart.multiColumn("speechCssrChart", "Speech CSSR (%)", categories, speechCssrSeries));
			
			Map<String, List<Float>> cs64CssrSeries = new LinkedHashMap<String, List<Float>>();
			cs64CssrSeries.put("CS64 CSSR (%)--aa4643", cs64CssrList);
			model.addAttribute("cs64CssrChart", Chart.multiColumn("cs64CssrChart", "CS64 CSSR (%)", categories, cs64CssrSeries));

			Map<String, List<Float>> psr99CssrSeries = new LinkedHashMap<String, List<Float>>();
			psr99CssrSeries.put("PS R99 CSSR (%)--89a54e", psr99CssrList);
			model.addAttribute("psr99CssrChart", Chart.multiColumn("psr99CssrChart", "PS R99 CSSR (%)", categories, psr99CssrSeries));

			Map<String, List<Float>> hsupaCssrSeries = new LinkedHashMap<String, List<Float>>();
			hsupaCssrSeries.put("PS HSUPA CSSR (%)--80699b", hsupaCssrList);
			model.addAttribute("hsupaCssrChart", Chart.multiColumn("hsupaCssrChart", "PS HSUPA CSSR (%)", categories, hsupaCssrSeries));

			Map<String, List<Float>> hsdpaCssrSeries = new LinkedHashMap<String, List<Float>>();
			hsdpaCssrSeries.put("PS HSDPA CSSR (%)--db843d", hsdpaCssrList);
			model.addAttribute("hsdpaCssrChart", Chart.multiColumn("hsdpaCssrChart", "PS HSDPA CSSR (%)", categories, hsdpaCssrSeries));
			
			Map<String, List<Float>> speechDrprSeries = new LinkedHashMap<String, List<Float>>();
			speechDrprSeries.put("Speech DCR (%)--4572a7", speechDrprList);
			model.addAttribute("speechDrprChart", Chart.multiColumn("speechDrprChart", "Speech DCR (%)", categories, speechDrprSeries));
			
			Map<String, List<Float>> cs64DrprSeries = new LinkedHashMap<String, List<Float>>();
			cs64DrprSeries.put("CS64 DCR (%)--aa4643", cs64DrprList);
			model.addAttribute("cs64DrprChart", Chart.multiColumn("cs64DrprChart", "CS64 DCR (%)", categories, cs64DrprSeries));

			Map<String, List<Float>> psr99DrprSeries = new LinkedHashMap<String, List<Float>>();
			psr99DrprSeries.put("PS R99 DCR (%)--89a54e", psr99DrprList);
			model.addAttribute("psr99DrprChart", Chart.multiColumn("psr99DrprChart", "PS R99 DCR (%)", categories, psr99DrprSeries));

			Map<String, List<Float>> hsupaDrprSeries = new LinkedHashMap<String, List<Float>>();
			hsupaDrprSeries.put("PS HSUPA DCR (%)--80699b", hsupaDrprList);
			model.addAttribute("hsupaDrprChart", Chart.multiColumn("hsupaDrprChart", "PS HSUPA DCR (%)", categories, hsupaDrprSeries));

			Map<String, List<Float>> hsdpaDrprSeries = new LinkedHashMap<String, List<Float>>();
			hsdpaDrprSeries.put("PS HSDPA DCR (%)--db843d", hsdpaDrprList);
			model.addAttribute("hsdpaDrprChart", Chart.multiColumn("hsdpaDrprChart", "PS HSDPA DCR (%)", categories, hsdpaDrprSeries));
			
			// checkBox
			String[] checkColumns = {"SPEECH_TRAFF","CS64_TRAFF","PSR99_UL_TRAFF","PSR99_DL_TRAFF","HSUPA_UL_TRAFF","HSDPA_DL_TRAFF","","SPEECH_CSSR","CS64_CSSR","PSR99_CSSR","HSUPA_CSSR","HSDPA_CSSR","","SPEECH_DRPR","CS64_DRPR","PSR99_DRPR","HSUPA_DRPR","HSDPA_DRPR","PSR99_UL_THROUGHTPUT","PSR99_DL_THROUGHTPUT","HSUPA_UL_THROUGHTPUT","HSDPA_DL_THROUGHTPUT","CS_IRAT_HO_SR","PS_IRAT_HO_SR","SOFT_HO_SR","CELL_DOWNTIME","HS_DOWNTIME","EUL_DOWNTIME","DATALOAD"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpDyBsc", 8));
			
		} catch (ParseException e) {
			saveError(request, e.toString());
		}

		return "dyBscQos3G";
	}
}
