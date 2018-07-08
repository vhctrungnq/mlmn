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
import vn.com.vhc.vmsc2.statistics.dao.VRpDyLocationGprsQosBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyLocationGprsQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyLocationGprsQos;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyLocationGprsQosBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
public class DyLocationGprsQosController extends BaseController{
	@Autowired
	private VRpDyLocationGprsQosDAO vRpDyLocationGprsQosDao;
	@Autowired
	private VRpDyLocationGprsQosBhDAO vRpDyLocationGprsQosBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping("/report/radio/location-gprs-qos/dy/list")
	public ModelAndView dyLocationGprsQosList(@RequestParam(required = false) String region,@RequestParam(required = false) String location, @RequestParam(required = false) String day, ModelMap model,
			HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();
		try {
			Date d;
			if (day == null) {
				d = new Date(currentTime - 24 * 60 * 60 * 1000);
			} else {
				d = df.parse(day);
			}

			List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
			model.addAttribute("regionList", regionList);
			
			if (region == null)
				region = "";
			
			List<VRpDyLocationGprsQos> vRpDyLocationGprsQos = vRpDyLocationGprsQosDao.filter(location, d, region);
			
			List<HProvincesCode> locationList = hProvincesCodeDao.getAllLocation();
			model.addAttribute("locationList", locationList);
			model.addAttribute("location", location);
			model.addAttribute("day", df.format(d));
			model.addAttribute("vRpDyLocationGprsQos", vRpDyLocationGprsQos);
			
		}catch(ParseException e) {
			saveError(request, e.toString());
		}
		return new ModelAndView("dyLocationGprsQosList");
	}
	
	@RequestMapping("/report/radio/location-gprs-qos/dy/details")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required=true) String location,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();
		
		try {
			Date startDay;
			Date endDay;
			Calendar cal = Calendar.getInstance();
			if(endDate==null){
				endDay = new Date(currentTime - 24 * 60 * 60 * 1000);
				cal.add(Calendar.DATE, -30);
					startDay = cal.getTime();
			}else{
				endDay = df.parse(endDate);
				if (startDate == null) {
					cal.setTime(endDay);
					cal.add(Calendar.DATE, -30);
					startDay = cal.getTime();
				}
				else{
					startDay = df.parse(startDate);
				}
			}
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("endDate", df.format(endDay));

			List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
			model.addAttribute("regionList", regionList);
			
			if (region == null)
				region = "";
			
			List<VRpDyLocationGprsQos> vRpDyLocationGprsQosDetails = vRpDyLocationGprsQosDao.filterDetails(df.format(startDay), df.format(endDay), location, region);
			
			model.addAttribute("vRpDyLocationGprsQosDetails", vRpDyLocationGprsQosDetails);
			List<HProvincesCode> locationList = hProvincesCodeDao.getAllLocation();
			model.addAttribute("locationList", locationList);
			model.addAttribute("location", location);
			
			/* Chart */
			List<String> categories = new ArrayList<String>();
			List<Float> dlTbfSucrList = new ArrayList<Float>();
			List<Float> ulTbfSucrList = new ArrayList<Float>();
			List<Float> dataLoadList = new ArrayList<Float>();
			
			for (VRpDyLocationGprsQos vRpDyLocationGprsQos : vRpDyLocationGprsQosDetails) {
				categories.add(df.format(vRpDyLocationGprsQos.getDay()));
				dlTbfSucrList.add(vRpDyLocationGprsQos.getDlTbfSucr());
				ulTbfSucrList.add(vRpDyLocationGprsQos.getUlTbfSucr());
				dataLoadList.add(vRpDyLocationGprsQos.getDataload());
			}
			Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
			series.put("Dl_Tbf_Sucr", dlTbfSucrList);
			series.put("Ul_Tbf_Sucr", ulTbfSucrList);
			series.put("Dataload", dataLoadList);

			model.addAttribute("chart", Chart.basicLine("LOCATION GPRS QOS " + location + " Daily Report", categories, series));

			// checkBox
			String[] checkColumns = {"dlTbfReq","dlTbfSucr","ulTbfReq","ulTbfSucr","gdlTraf","gulTraf","edlTraf","eulTraf","dataload"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 4));

			String[] checkSeries = {"dlTbfSucr", "ulTbfSucr","dataload"};
			model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
			
		}catch (ParseException e) {
		saveError(request, e.toString());
		}
		
	return "dyLocationGprsQosDetails";
	}
	
	@RequestMapping("/report/radio/location-gprs-qos/dy/bhDetails")
	public String showReportBh(@RequestParam(required = false) String region,@RequestParam(required=true) String location,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();
		
		try {
			Date startDay;
			Date endDay;
			Calendar cal = Calendar.getInstance();
			if(endDate==null){
				endDay = new Date(currentTime - 24 * 60 * 60 * 1000);
				cal.add(Calendar.DATE, -30);
					startDay = cal.getTime();
			}else{
				endDay = df.parse(endDate);
				if (startDate == null) {
					cal.setTime(endDay);
					cal.add(Calendar.DATE, -30);
					startDay = cal.getTime();
				}
				else{
					startDay = df.parse(startDate);
				}
			}
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("endDate", df.format(endDay));

			List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
			model.addAttribute("regionList", regionList);
			
			if (region == null)
				region = "";
			
			List<VRpDyLocationGprsQosBh> vRpDyLocationGprsQosBhDetails = vRpDyLocationGprsQosBhDao.filterDetails(df.format(startDay), df.format(endDay), location, region);
			
			model.addAttribute("vRpDyLocationGprsQosBhDetails", vRpDyLocationGprsQosBhDetails);
			List<HProvincesCode> locationList = hProvincesCodeDao.getAllLocation();
			model.addAttribute("locationList", locationList);
			model.addAttribute("location", location);
			
			/* Chart */
			List<String> categories = new ArrayList<String>();
			List<Float> dlTbfSucrList = new ArrayList<Float>();
			List<Float> ulTbfSucrList = new ArrayList<Float>();
			
			for (VRpDyLocationGprsQosBh vRpDyLocationGprsQosBh : vRpDyLocationGprsQosBhDetails) {
				categories.add(df.format(vRpDyLocationGprsQosBh.getDay()));
				dlTbfSucrList.add(vRpDyLocationGprsQosBh.getBhDlTbfSucr());
				ulTbfSucrList.add(vRpDyLocationGprsQosBh.getBhUlTbfSucr());
			}
			Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
			series.put("Bh_Dl_Tbf_Sucr", dlTbfSucrList);
			series.put("Bh_Ul_Tbf_Sucr", ulTbfSucrList);

			model.addAttribute("chart", Chart.basicLine("LOCATION GPRS QOS BH" + location + " Daily Report", categories, series));

			// checkBox
			String[] checkColumns = {"bhDlTbfReq","bhDlTbfSucr","bhDlTbfReq","bhUlTbfSucr","bhGdlTraf","bhGulTraf","bhEdlTraf","bhEulTraf"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

			String[] checkSeries = {"bhDlTbfSucr", "bhUlTbfSucr"};
			model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
			
		}catch (ParseException e) {
		saveError(request, e.toString());
		}
		
	return "dyLocationGprsQosBhDetails";
	}
}
