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
import vn.com.vhc.vmsc2.statistics.dao.VRpDyLocationGprsCsBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyLocationGprsCsDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyLocationGprsCs;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyLocationGprsCsBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
public class DyLocationGprsCsController extends BaseController{
	@Autowired
	private VRpDyLocationGprsCsDAO vRpDyLocationGprsCsDao;
	@Autowired
	private VRpDyLocationGprsCsBhDAO vRpDyLocationGprsCsBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping("/report/radio/location-gprs-cs/dy/list")
	public ModelAndView dyLocationGprsCsList(@RequestParam(required = false) String region,@RequestParam(required = false) String location, @RequestParam(required = false) String day, ModelMap model,
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
			
			List<VRpDyLocationGprsCs> vRpDyLocationGprsCs = vRpDyLocationGprsCsDao.filter(location, d, region);
			List<HProvincesCode> locationList = hProvincesCodeDao.getAllLocation();
			model.addAttribute("locationList", locationList);
			model.addAttribute("day", df.format(d));
			model.addAttribute("vRpDyLocationGprsCs", vRpDyLocationGprsCs);
			model.addAttribute("region", region);
			
		}catch(ParseException e) {
			saveError(request, e.toString());
		}
		return new ModelAndView("dyLocationGprsCsList");
	}
	
	@RequestMapping("/report/radio/location-gprs-cs/dy/details")
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
			
			List<VRpDyLocationGprsCs> vRpDyLocationGprsCsDetails = vRpDyLocationGprsCsDao.filterDetails(df.format(startDay), df.format(endDay), location, region);
			
			model.addAttribute("vRpDyLocationGprsCsDetails", vRpDyLocationGprsCsDetails);
			List<HProvincesCode> locationList = hProvincesCodeDao.getAllLocation();
			model.addAttribute("locationList", locationList);
			model.addAttribute("location", location);
			model.addAttribute("region", region);
			
			/* Chart */
			List<String> categories = new ArrayList<String>();
			List<Float> csxLevel1List = new ArrayList<Float>();
			List<Float> csxLevel2List = new ArrayList<Float>();
			List<Float> csxLevel3List = new ArrayList<Float>();
			List<Float> csxLevel4List = new ArrayList<Float>();
			List<Float> mcsxLevel1List = new ArrayList<Float>();
			List<Float> mcsxLevel2List = new ArrayList<Float>();
			List<Float> mcsxLevel3List = new ArrayList<Float>();
			List<Float> mcsxLevel4List = new ArrayList<Float>();
			List<Float> mcsxLevel5List = new ArrayList<Float>();
			List<Float> mcsxLevel6List = new ArrayList<Float>();
			List<Float> mcsxLevel7List = new ArrayList<Float>();
			List<Float> mcsxLevel8List = new ArrayList<Float>();
			List<Float> mcsxLevel9List = new ArrayList<Float>();
			List<Float> dataLoadList = new ArrayList<Float>();
			
			for (VRpDyLocationGprsCs vRpDyLocationGprsCs : vRpDyLocationGprsCsDetails) {
				categories.add(df.format(vRpDyLocationGprsCs.getDay()));
				csxLevel1List.add(vRpDyLocationGprsCs.getCsxLevel1());
				csxLevel2List.add(vRpDyLocationGprsCs.getCsxLevel2());
				csxLevel3List.add(vRpDyLocationGprsCs.getCsxLevel3());
				csxLevel4List.add(vRpDyLocationGprsCs.getCsxLevel4());
				mcsxLevel1List.add(vRpDyLocationGprsCs.getMcsxLevel1());
				mcsxLevel2List.add(vRpDyLocationGprsCs.getMcsxLevel2());
				mcsxLevel3List.add(vRpDyLocationGprsCs.getMcsxLevel3());
				mcsxLevel4List.add(vRpDyLocationGprsCs.getMcsxLevel4());
				mcsxLevel5List.add(vRpDyLocationGprsCs.getMcsxLevel5());
				mcsxLevel6List.add(vRpDyLocationGprsCs.getMcsxLevel6());
				mcsxLevel7List.add(vRpDyLocationGprsCs.getMcsxLevel7());
				mcsxLevel8List.add(vRpDyLocationGprsCs.getMcsxLevel8());
				mcsxLevel9List.add(vRpDyLocationGprsCs.getMcsxLevel9());
				dataLoadList.add(vRpDyLocationGprsCs.getDataload());
			}
			Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
			series.put("CsxLevel1", csxLevel1List);
			series.put("CsxLevel2", csxLevel2List);
			series.put("CsxLevel3", csxLevel3List);
			series.put("CsxLevel4", csxLevel4List);
			series.put("McsxLevel1", mcsxLevel1List);
			series.put("McsxLevel2", mcsxLevel2List);
			series.put("McsxLevel3", mcsxLevel3List);
			series.put("McsxLevel4", mcsxLevel4List);
			series.put("McsxLevel5", mcsxLevel5List);
			series.put("McsxLevel6", mcsxLevel6List);
			series.put("McsxLevel7", mcsxLevel7List);
			series.put("McsxLevel8", mcsxLevel8List);
			series.put("McsxLevel9", mcsxLevel9List);
			series.put("Dataload", dataLoadList);

			model.addAttribute("chart", Chart.basicLine("LOCATION GPRS CS " + location + " Daily Report", categories, series));

			// checkBox
			String[] checkColumns = {"csxLevel1","csxLevel2","csxLevel3","csxLevel4","mcsxLevel1","mcsxLevel2","mcsxLevel3","mcsxLevel4","mcsxLevel5","mcsxLevel6","mcsxLevel7","mcsxLevel8","mcsxLevel9","dataload"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 4));

			String[] checkSeries = {"dataload"};
			model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
			
		}catch (ParseException e) {
		saveError(request, e.toString());
		}
		
	return "dyLocationGprsCsDetails";
	}
	
	@RequestMapping("/report/radio/location-gprs-cs/dy/bhDetails")
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
			
			List<VRpDyLocationGprsCsBh> vRpDyLocationGprsCsBhDetails = vRpDyLocationGprsCsBhDao.filterDetails(df.format(startDay), df.format(endDay), location, region);
			
			model.addAttribute("vRpDyLocationGprsCsBhDetails", vRpDyLocationGprsCsBhDetails);
			List<HProvincesCode> locationList = hProvincesCodeDao.getAllLocation();
			model.addAttribute("locationList", locationList);
			model.addAttribute("location", location);
			model.addAttribute("region", region);
			
			/* Chart */
			List<String> categories = new ArrayList<String>();
			List<Float> bhCsxLevel1List = new ArrayList<Float>();
			List<Float> bhCsxLevel2List = new ArrayList<Float>();
			List<Float> bhCsxLevel3List = new ArrayList<Float>();
			List<Float> bhCsxLevel4List = new ArrayList<Float>();
			List<Float> bhMcsxLevel1List = new ArrayList<Float>();
			List<Float> bhMcsxLevel2List = new ArrayList<Float>();
			List<Float> bhMcsxLevel3List = new ArrayList<Float>();
			List<Float> bhMcsxLevel4List = new ArrayList<Float>();
			List<Float> bhMcsxLevel5List = new ArrayList<Float>();
			List<Float> bhMcsxLevel6List = new ArrayList<Float>();
			List<Float> bhMcsxLevel7List = new ArrayList<Float>();
			List<Float> bhMcsxLevel8List = new ArrayList<Float>();
			List<Float> bhMcsxLevel9List = new ArrayList<Float>();
			
			for (VRpDyLocationGprsCsBh vRpDyLocationGprsCsBh : vRpDyLocationGprsCsBhDetails) {
				categories.add(df.format(vRpDyLocationGprsCsBh.getDay()));
				bhCsxLevel1List.add(vRpDyLocationGprsCsBh.getBhCsxLevel1());
				bhCsxLevel2List.add(vRpDyLocationGprsCsBh.getBhCsxLevel2());
				bhCsxLevel3List.add(vRpDyLocationGprsCsBh.getBhCsxLevel3());
				bhCsxLevel4List.add(vRpDyLocationGprsCsBh.getBhCsxLevel4());
				bhMcsxLevel1List.add(vRpDyLocationGprsCsBh.getBhMcsxLevel1());
				bhMcsxLevel2List.add(vRpDyLocationGprsCsBh.getBhMcsxLevel2());
				bhMcsxLevel3List.add(vRpDyLocationGprsCsBh.getBhMcsxLevel3());
				bhMcsxLevel4List.add(vRpDyLocationGprsCsBh.getBhMcsxLevel4());
				bhMcsxLevel5List.add(vRpDyLocationGprsCsBh.getBhMcsxLevel5());
				bhMcsxLevel6List.add(vRpDyLocationGprsCsBh.getBhMcsxLevel6());
				bhMcsxLevel7List.add(vRpDyLocationGprsCsBh.getBhMcsxLevel7());
				bhMcsxLevel8List.add(vRpDyLocationGprsCsBh.getBhMcsxLevel8());
				bhMcsxLevel9List.add(vRpDyLocationGprsCsBh.getBhMcsxLevel9());
			}
			Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
			series.put("Bh_Csx_Level1", bhCsxLevel1List);
			series.put("Bh_Csx_Level2", bhCsxLevel2List);
			series.put("Bh_Csx_Level3", bhCsxLevel3List);
			series.put("Bh_Csx_Level4", bhCsxLevel4List);
			series.put("Bh_Mcsx_Level1", bhMcsxLevel1List);
			series.put("Bh_Mcsx_Level2", bhMcsxLevel2List);
			series.put("Bh_Mcsx_Level3", bhMcsxLevel3List);
			series.put("Bh_Mcsx_Level4", bhMcsxLevel4List);
			series.put("Bh_Mcsx_Level5", bhMcsxLevel5List);
			series.put("Bh_Mcsx_Level6", bhMcsxLevel6List);
			series.put("Bh_Mcsx_Level7", bhMcsxLevel7List);
			series.put("Bh_Mcsx_Level8", bhMcsxLevel8List);
			series.put("Bh_Mcsx_Level9", bhMcsxLevel9List);

			model.addAttribute("chart", Chart.basicLine("LOCATION GPRS CS BH" + location + " Daily Report", categories, series));

			// checkBox
			String[] checkColumns = {"bhCsxLevel1","bhCsxLevel2","bhCsxLevel3","bhCsxLevel4","bhMcsxLevel1","bhMcsxLevel2","bhMcsxLevel3","bhMcsxLevel4","bhMcsxLevel5","bhMcsxLevel6","bhMcsxLevel7","bhMcsxLevel8","bhMcsxLevel9"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

			String[] checkSeries = {"bhCsxLevel1","bhCsxLevel2","bhCsxLevel3","bhCsxLevel4","bhMcsxLevel1","bhMcsxLevel2","bhMcsxLevel3","bhMcsxLevel4","bhMcsxLevel5","bhMcsxLevel6","bhMcsxLevel7","bhMcsxLevel8","bhMcsxLevel9"};
			model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
			
		}catch (ParseException e) {
		saveError(request, e.toString());
		}
		
	return "dyLocationGprsCsBhDetails";
	}
}
