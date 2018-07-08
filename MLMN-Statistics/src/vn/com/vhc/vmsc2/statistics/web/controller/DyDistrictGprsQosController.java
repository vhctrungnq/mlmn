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
import vn.com.vhc.vmsc2.statistics.dao.VRpDyDistrictGprsQosBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyDistrictGprsQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyDistrictGprsQos;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyDistrictGprsQosBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
public class DyDistrictGprsQosController extends BaseController{
	@Autowired
	private VRpDyDistrictGprsQosDAO vRpDyDistrictGprsQosDao;
	@Autowired
	private VRpDyDistrictGprsQosBhDAO vRpDyDistrictGprsQosBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping("/report/radio/district-gprs-qos/dy/list")
	public ModelAndView dyProvinceGprsQosList(@RequestParam(required = false) String region,@RequestParam(required = false) String province,@RequestParam(required = false) String district, @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, ModelMap model, HttpServletRequest request) {
		
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";
		
		List<VRpDyDistrictGprsQos> vRpDyDistrictGprsQos = vRpDyDistrictGprsQosDao.filterDetails(df.format(startDate), df.format(endDate), province, district, region);
		
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("province", province);
		model.addAttribute("district", district);
		model.addAttribute("region", region);
		model.addAttribute("vRpDyDistrictGprsQos", vRpDyDistrictGprsQos);
		return new ModelAndView("dyDistrictGprsQosList");
	}
	
	@RequestMapping("/report/radio/district-gprs-qos/dy/details")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required=true) String province,@RequestParam(required=true) String district,
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
				DateTime dt = new DateTime(endDay);
				startDay = dt.minusDays(7).toDate();
			} else {
				startDay = df.parse(startDate);
			}
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("endDate", df.format(endDay));

			List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
			model.addAttribute("regionList", regionList);
			
			if (region == null)
				region = "";
		
			List<VRpDyDistrictGprsQos> vRpDyDistrictGprsQosDetails = vRpDyDistrictGprsQosDao.filterDetails(df.format(startDay), df.format(endDay), province,district, region);
			
			model.addAttribute("vRpDyDistrictGprsQosDetails", vRpDyDistrictGprsQosDetails);
			List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
			model.addAttribute("provinceList", provinceList);
			model.addAttribute("province", province);
			model.addAttribute("district", district);
			model.addAttribute("region", region);
			
			/* Chart */
			List<String> categories = new ArrayList<String>();
			List<Float> bhDlTbfSucrList = new ArrayList<Float>();
			List<Float> bhUlTbfSucrList = new ArrayList<Float>();
			List<Float> dataLoadList = new ArrayList<Float>();
			
			for (VRpDyDistrictGprsQos vRpDyDistrictGprsQos : vRpDyDistrictGprsQosDetails) {
				categories.add(df.format(vRpDyDistrictGprsQos.getDay()));
				bhDlTbfSucrList.add(vRpDyDistrictGprsQos.getDlTbfSucr());
				bhUlTbfSucrList.add(vRpDyDistrictGprsQos.getUlTbfSucr());
				dataLoadList.add(vRpDyDistrictGprsQos.getDataload());
			}
			
			Map<String, List<Float>> dlTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
			dlTbfSucrSeries.put("DL TBF SUCR (%)--80699b", bhDlTbfSucrList);
			model.addAttribute("dlTbfSucrChart", Chart.multiColumn("dlTbfSucrChart", "DL TBF SUCR (%)", categories, dlTbfSucrSeries));

			Map<String, List<Float>> ulTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
			ulTbfSucrSeries.put("UL TBF SUCR (%)--db843d", bhUlTbfSucrList);
			model.addAttribute("ulTbfSucrChart", Chart.multiColumn("ulTbfSucrChart", "UL TBF SUCR (%)", categories, ulTbfSucrSeries));
	
			// checkBox
			String[] checkColumns = {"dlTbfReq","dlTbfSucr","ulTbfReq","ulTbfSucr","gdlTraf","gulTraf","edlTraf","eulTraf","dataload"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 7));
	
			String[] checkSeries = {"dlTbfSucr", "ulTbfSucr","dataload"};
			model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
			
			return "dyDistrictGprsQosDetails";
		} catch (ParseException e) {
			saveError(request, e.toString());
		}
		return "dyDistrictGprsQosDetails";
	}
	
	@RequestMapping("/report/radio/district-gprs-qos/dy/bhDetails")
	public String showReportBh(@RequestParam(required = false) String region,@RequestParam(required=true) String province,@RequestParam(required=true) String district,
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
				DateTime dt = new DateTime(endDay);
				startDay = dt.minusDays(7).toDate();
			} else {
				startDay = df.parse(startDate);
			}
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("endDate", df.format(endDay));

			List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
			model.addAttribute("regionList", regionList);
			
			if (region == null)
				region = "";
			
			List<VRpDyDistrictGprsQosBh> vRpDyDistrictGprsQosBhDetails = vRpDyDistrictGprsQosBhDao.filterDetails(df.format(startDay), df.format(endDay), province, district, region);
			
			model.addAttribute("vRpDyDistrictGprsQosBhDetails", vRpDyDistrictGprsQosBhDetails);
			List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
			model.addAttribute("provinceList", provinceList);
			model.addAttribute("province", province);
			model.addAttribute("district", district);
			model.addAttribute("region", region);
			
			/* Chart */
			List<String> categories = new ArrayList<String>();
			List<Float> bhDlTbfSucrList = new ArrayList<Float>();
			List<Float> bhUlTbfSucrList = new ArrayList<Float>();
			
			for (VRpDyDistrictGprsQosBh vRpDyDistrictGprsQosBh : vRpDyDistrictGprsQosBhDetails) {
				categories.add(vRpDyDistrictGprsQosBh.getBh()+":00/"+ df.format(vRpDyDistrictGprsQosBh.getDay()));
				bhDlTbfSucrList.add(vRpDyDistrictGprsQosBh.getBhDlTbfSucr());
				bhUlTbfSucrList.add(vRpDyDistrictGprsQosBh.getBhUlTbfSucr());
			}

			Map<String, List<Float>> dlTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
			dlTbfSucrSeries.put("DL TBF SUCR (%)--80699b", bhDlTbfSucrList);
			model.addAttribute("dlTbfSucrChart", Chart.multiColumn("dlTbfSucrChart", "DL TBF SUCR (%)", categories, dlTbfSucrSeries));

			Map<String, List<Float>> ulTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
			ulTbfSucrSeries.put("UL TBF SUCR (%)--db843d", bhUlTbfSucrList);
			model.addAttribute("ulTbfSucrChart", Chart.multiColumn("ulTbfSucrChart", "UL TBF SUCR (%)", categories, ulTbfSucrSeries));

			// checkBox
			String[] checkColumns = {"bhDlTbfReq","bhDlTbfSucr","bhDlTbfReq","bhUlTbfSucr","bhGdlTraf","bhGulTraf","bhEdlTraf","bhEulTraf"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 6));

			String[] checkSeries = {"bhDlTbfSucr", "bhUlTbfSucr"};
			model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
			
		}catch (ParseException e) {
		saveError(request, e.toString());
		}
		
	return "dyDistrictGprsQosBhDetails";
	}
}
