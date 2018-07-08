package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.ArrayList;
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
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkBscGprsQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkBscGprsQos;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
@RequestMapping("/report/radio/bsc-gprs-qos/wk/*")
public class WkBscGprsQosController extends BaseController {
	@Autowired
	private VRpWkBscGprsQosDAO vRpWkBscGprsQosDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;

	@RequestMapping("list")
	public ModelAndView dyList(@RequestParam(required = false) String region,@RequestParam(required = false) String bscid, @RequestParam(required = false) Integer startWeek,
			@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek,
			@RequestParam(required = false) Integer endYear, ModelMap model) {
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
		
		List<VRpWkBscGprsQos> vRpWkBscGprsQos = vRpWkBscGprsQosDao.filter(startWeek,startYear, endWeek, endYear, bscid, region);

		model.addAttribute("bscid", bscid);

		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		
		model.addAttribute("vRpWkBscGprsQos", vRpWkBscGprsQos);

		return new ModelAndView("wkBscGprsQosList");
	}

	@RequestMapping("detail")
	public String showReport( @RequestParam(required = false) String region,@RequestParam(required = true) String bscid,
			@RequestParam(required = false) Integer startWeek, @RequestParam(required = false) Integer startYear,@RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear,
			ModelMap model, HttpServletRequest request) {

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

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		if (region == null)
			region = "";
		
		model.addAttribute("region", region);
		
		List<VRpWkBscGprsQos> vRpWkBscGprsQosList = vRpWkBscGprsQosDao.filter(startWeek, startYear,endWeek, endYear, bscid, region);

		model.addAttribute("vRpWkBscGprsQosList", vRpWkBscGprsQosList);
		model.addAttribute("bscid", bscid);
		// chart
		List<String> categories = new ArrayList<String>();
		List<Float> bhDlTbfSucrList = new ArrayList<Float>();
		List<Float> bhUlTbfSucrList = new ArrayList<Float>();
		List<Float> dataloadList = new ArrayList<Float>();
		
		for (VRpWkBscGprsQos vRpWkBsc : vRpWkBscGprsQosList) {
			categories.add(Integer.toString(vRpWkBsc.getWeek())+"/"+vRpWkBsc.getYear());
			bhDlTbfSucrList.add(vRpWkBsc.getDlTbfSucr());
			bhUlTbfSucrList.add(vRpWkBsc.getUlTbfSucr());
			dataloadList.add(vRpWkBsc.getDataload());
		}

		Map<String, List<Float>> dlTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
		dlTbfSucrSeries.put("DL TBF SUCR (%)--80699b", bhDlTbfSucrList);
		model.addAttribute("dlTbfSucrChart", Chart.multiColumn("dlTbfSucrChart", "DL TBF SUCR (%)", categories, dlTbfSucrSeries));

		Map<String, List<Float>> ulTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
		ulTbfSucrSeries.put("UL TBF SUCR (%)--db843d", bhUlTbfSucrList);
		model.addAttribute("ulTbfSucrChart", Chart.multiColumn("ulTbfSucrChart", "UL TBF SUCR (%)", categories, ulTbfSucrSeries));

		// checkBox
		String[] checkColumns = {"DL_TBF_REQ","DL_TBF_SUCR","UL_TBF_REQ","UL_TBF_SUCR","GDL_TRAF","GUL_TRAF","EDL_TRAF","EUL_TRAF","DATALOAD"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpWkBscGprsQos", 6));

		return "wkBscGprsQos";
	}
}
