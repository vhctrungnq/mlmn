package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import vn.com.vhc.vmsc2.statistics.dao.VRpDyProvinceGprsQosBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyProvinceGprsQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyProvinceGprsQos;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyProvinceGprsQosBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
public class DyProvinceGprsQosController extends BaseController {
	@Autowired
	private VRpDyProvinceGprsQosDAO vRpDyProvinceGprsQosDao;
	@Autowired
	private VRpDyProvinceGprsQosBhDAO vRpDyProvinceGprsQosBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("/report/radio/province-gprs-qos/dy/list")
	public ModelAndView dyProvinceGprsQosList(@RequestParam(required = false) String region, @RequestParam(required = false) String province,
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

		List<VRpDyProvinceGprsQos> vRpDyProvinceGprsQos = vRpDyProvinceGprsQosDao.filterDetails(df.format(startDate), df.format(endDate), province, region);

		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("region", region);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("vRpDyProvinceGprsQos", vRpDyProvinceGprsQos);

		return new ModelAndView("dyProvinceGprsQosList");
	}

	@RequestMapping("/report/radio/province-gprs-qos/dy/details")
	public String showReport(@RequestParam(required = false) String region, @RequestParam(required = true) String province,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();

		try {
			Date startDay;
			Date endDay;
			Calendar cal = Calendar.getInstance();
			if (endDate == null) {
				endDay = new Date(currentTime - 24 * 60 * 60 * 1000);
				cal.add(Calendar.DATE, -30);
				startDay = cal.getTime();
			} else {
				endDay = df.parse(endDate);
				if (startDate == null) {
					cal.setTime(endDay);
					cal.add(Calendar.DATE, -30);
					startDay = cal.getTime();
				} else {
					startDay = df.parse(startDate);
				}
			}
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("endDate", df.format(endDay));
			
			List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
			model.addAttribute("regionList", regionList);
			
			if (region == null)
				region = "";

			List<VRpDyProvinceGprsQos> vRpDyProvinceGprsQosDetails = vRpDyProvinceGprsQosDao.filterDetails(df.format(startDay), df.format(endDay), province,
					region);

			model.addAttribute("vRpDyProvinceGprsQosDetails", vRpDyProvinceGprsQosDetails);
			List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
			model.addAttribute("provinceList", provinceList);
			model.addAttribute("province", province);
			model.addAttribute("region", region);
			
			/* Chart */
			List<String> categories = new ArrayList<String>();
			List<Float> bhUlTbfSucrList = new ArrayList<Float>();
			List<Float> bhDlTbfSucrList = new ArrayList<Float>();
			List<Float> dataLoadList = new ArrayList<Float>();

			for (VRpDyProvinceGprsQos vRpDyProvinceGprsQos : vRpDyProvinceGprsQosDetails) {
				categories.add(df.format(vRpDyProvinceGprsQos.getDay()));
				bhDlTbfSucrList.add(vRpDyProvinceGprsQos.getDlTbfSucr());
				bhUlTbfSucrList.add(vRpDyProvinceGprsQos.getUlTbfSucr());
				dataLoadList.add(vRpDyProvinceGprsQos.getDataload());
			}

			Map<String, List<Float>> dlTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
			dlTbfSucrSeries.put("DL TBF SUCR (%)--80699b", bhDlTbfSucrList);
			model.addAttribute("dlTbfSucrChart", Chart.multiColumn("dlTbfSucrChart", "DL TBF SUCR (%)", categories, dlTbfSucrSeries));

			Map<String, List<Float>> ulTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
			ulTbfSucrSeries.put("UL TBF SUCR (%)--db843d", bhUlTbfSucrList);
			model.addAttribute("ulTbfSucrChart", Chart.multiColumn("ulTbfSucrChart", "UL TBF SUCR (%)", categories, ulTbfSucrSeries));

			// checkBox
			String[] checkColumns = { "dlTbfReq", "dlTbfSucr", "ulTbfReq", "ulTbfSucr", "gdlTraf", "gulTraf", "edlTraf", "eulTraf", "dataload" };
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

			String[] checkSeries = { "dlTbfSucr", "ulTbfSucr", "dataload" };
			model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));

		} catch (ParseException e) {
			saveError(request, e.toString());
		}

		return "dyProvinceGprsQosDetails";
	}

	@RequestMapping("/report/radio/province-gprs-qos/dy/bhDetails")
	public String showReportBh(@RequestParam(required = false) String region, @RequestParam(required = true) String province,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();

		try {
			Date startDay;
			Date endDay;
			Calendar cal = Calendar.getInstance();
			if (endDate == null) {
				endDay = new Date(currentTime - 24 * 60 * 60 * 1000);
				cal.add(Calendar.DATE, -30);
				startDay = cal.getTime();
			} else {
				endDay = df.parse(endDate);
				if (startDate == null) {
					cal.setTime(endDay);
					cal.add(Calendar.DATE, -30);
					startDay = cal.getTime();
				} else {
					startDay = df.parse(startDate);
				}
			}
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("endDate", df.format(endDay));
			
			List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
			model.addAttribute("regionList", regionList);
			
			if (region == null)
				region = "";

			List<VRpDyProvinceGprsQosBh> vRpDyProvinceGprsQosBhDetails = vRpDyProvinceGprsQosBhDao.filterDetails(df.format(startDay), df.format(endDay),
					province, region);

			model.addAttribute("vRpDyProvinceGprsQosBhDetails", vRpDyProvinceGprsQosBhDetails);
			List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
			model.addAttribute("provinceList", provinceList);
			model.addAttribute("province", province);
			model.addAttribute("region", region);
			
			/* Chart */
			List<String> categories = new ArrayList<String>();
			List<Float> bhDlTbfSucrList = new ArrayList<Float>();
			List<Float> bhUlTbfSucrList = new ArrayList<Float>();

			for (VRpDyProvinceGprsQosBh vRpDyProvinceGprsQosBh : vRpDyProvinceGprsQosBhDetails) {
				categories.add(vRpDyProvinceGprsQosBh.getBh() + ":00/" + df.format(vRpDyProvinceGprsQosBh.getDay()));
				bhDlTbfSucrList.add(vRpDyProvinceGprsQosBh.getBhDlTbfSucr());
				bhUlTbfSucrList.add(vRpDyProvinceGprsQosBh.getBhUlTbfSucr());
			}

			Map<String, List<Float>> dlTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
			dlTbfSucrSeries.put("DL TBF SUCR (%)--80699b", bhDlTbfSucrList);
			model.addAttribute("dlTbfSucrChart", Chart.multiColumn("dlTbfSucrChart", "DL TBF SUCR (%)", categories, dlTbfSucrSeries));

			Map<String, List<Float>> ulTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
			ulTbfSucrSeries.put("UL TBF SUCR (%)--db843d", bhUlTbfSucrList);
			model.addAttribute("ulTbfSucrChart", Chart.multiColumn("ulTbfSucrChart", "UL TBF SUCR (%)", categories, ulTbfSucrSeries));

			// checkBox
			String[] checkColumns = { "bhDlTbfReq", "bhDlTbfSucr", "bhDlTbfReq", "bhUlTbfSucr", "bhGdlTraf", "bhGulTraf", "bhEdlTraf", "bhEulTraf" };
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 6));

			String[] checkSeries = { "bhDlTbfSucr", "bhUlTbfSucr" };
			model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));

		} catch (ParseException e) {
			saveError(request, e.toString());
		}

		return "dyProvinceGprsQosBhDetails";
	}
}