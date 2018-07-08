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
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyBscGprsQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBscGprsQos;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
@RequestMapping("/report/radio/bsc-gprs-qos/dy/*")
public class DyBscGprsQosController extends BaseController {
	@Autowired
	private VRpDyBscGprsQosDAO vRpDyBscGprsQosDao;
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("list")
	public ModelAndView dyList(@RequestParam(required = false) String region, @RequestParam(required = false) String bscid,
			@RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, ModelMap model) {
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
		
		List<VRpDyBscGprsQos> vRpDyBscGprsQos = vRpDyBscGprsQosDao.filter(df.format(startDate), df.format(endDate), bscid, region);

		model.addAttribute("bscid", bscid);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("vRpDyBscGprsQos", vRpDyBscGprsQos);

		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		model.addAttribute("region", region);
		
		return new ModelAndView("dyBscGprsQosList");
	}

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = false) String region, @RequestParam(required = true) String bscid,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request) {
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
				startDay = new Date(endDay.getTime() + 25 * 24 * 60 * 60 * 1000);
			} else {
				startDay = df.parse(startDate);
			}
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("endDate", df.format(endDay));

			List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
			model.addAttribute("regionList", regionList);
			
			if (region == null)
				region = "";

			List<VRpDyBscGprsQos> vRpDyBscGprsQosList = vRpDyBscGprsQosDao.filter(df.format(startDay), df.format(endDay), bscid, region);

			model.addAttribute("vRpDyBscGprsQosList", vRpDyBscGprsQosList);
			model.addAttribute("bscid", bscid);

			List<Bsc> bscList = bscDao.getAll();
			model.addAttribute("bscList", bscList);
			model.addAttribute("region", region);
			
			// chart
			List<String> categories = new ArrayList<String>();
			List<Float> bhDlTbfSucrList = new ArrayList<Float>();
			List<Float> bhUlTbfSucrList = new ArrayList<Float>();
			List<Float> dataloadList = new ArrayList<Float>();

			for (VRpDyBscGprsQos vRpDyBsc : vRpDyBscGprsQosList) {
				categories.add(df.format(vRpDyBsc.getDay()));
				bhDlTbfSucrList.add(vRpDyBsc.getDlTbfSucr());
				bhUlTbfSucrList.add(vRpDyBsc.getUlTbfSucr());
				dataloadList.add(vRpDyBsc.getDataload());
			}

			Map<String, List<Float>> dlTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
			dlTbfSucrSeries.put("DL TBF SUCR (%)--80699b", bhDlTbfSucrList);
			model.addAttribute("dlTbfSucrChart", Chart.multiColumn("dlTbfSucrChart", "DL TBF SUCR (%)", categories, dlTbfSucrSeries));

			Map<String, List<Float>> ulTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
			ulTbfSucrSeries.put("UL TBF SUCR (%)--db843d", bhUlTbfSucrList);
			model.addAttribute("ulTbfSucrChart", Chart.multiColumn("ulTbfSucrChart", "UL TBF SUCR (%)", categories, ulTbfSucrSeries));

			// checkBox
			String[] checkColumns = { "dlTbfReq", "dlTbfSucr", "ulTbfReq", "ulTbfSucr", "gdlTraf", "gulTraf", "edlTraf", "eulTraf",
					"dataload" };
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpDyBscGprsQos", 5));
		} catch (ParseException e) {
			saveError(request, e.toString());
		}

		return "dyBscGprsQos";
	}
}
