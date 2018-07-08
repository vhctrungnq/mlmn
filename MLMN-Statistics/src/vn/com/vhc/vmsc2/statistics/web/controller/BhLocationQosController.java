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

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyLocationBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnLocationBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkLocationBhDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyLocationBh;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnLocationBh;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkLocationBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
public class BhLocationQosController extends BaseController {
	@Autowired
	private VRpDyLocationBhDAO vRpDyLocationBhDao;
	@Autowired
	private VRpWkLocationBhDAO vRpWkLocationBhDao;
	@Autowired
	private VRpMnLocationBhDAO vRpMnLocationBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("/report/radio/location/dy/bh")
	public ModelAndView dyList( @RequestParam(required = false) String region,@RequestParam(required = true) String location,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate, ModelMap model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();
		try {
			Date startDay, endDay;
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

			List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
			model.addAttribute("regionList",regionList);
			if(region == null){
				region="";
			}
				
			List<VRpDyLocationBh> vRpDyLocationBhList = vRpDyLocationBhDao.filter(df.format(startDay), df.format(endDay),location, region);
			List<HProvincesCode> locationList = hProvincesCodeDao.getAllLocation();
			model.addAttribute("locationList", locationList);
			model.addAttribute("location", location);
			model.addAttribute("endDate", df.format(endDay));
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("vRpDyLocationBhList", vRpDyLocationBhList);
			
			/* Start Chart */
			List<String> categories = new ArrayList<String>();
			List<Float> bhCssrList = new ArrayList<Float>();
			List<Float> bhTNblkrList = new ArrayList<Float>();
			List<Float> bhTDrprList = new ArrayList<Float>();
			List<Float> bhSBlkrList = new ArrayList<Float>();
			List<Float> bhSDrprList = new ArrayList<Float>();
			
			for (VRpDyLocationBh vRpDyLocationBh : vRpDyLocationBhList) {
				categories.add(df.format(vRpDyLocationBh.getDay()));
				bhCssrList.add(vRpDyLocationBh.getBhCssr());
				bhTNblkrList.add(vRpDyLocationBh.getBhTNblkr());
				bhTDrprList.add(vRpDyLocationBh.getBhTDrpr());
				bhSBlkrList.add(vRpDyLocationBh.getBhSBlkr());
				bhSDrprList.add(vRpDyLocationBh.getBhSDrpr());
			}
			
			Map<String, List<Float>> tDrprSeries = new LinkedHashMap<String, List<Float>>();
			tDrprSeries.put("Drop Rate (%)--4572a7", bhTDrprList);
			model.addAttribute("tDrprChart", Chart.multiColumn("tDrprChart", "TCH Drop Rate (%)", categories, tDrprSeries));
			
			Map<String, List<Float>> sDrprSeries = new LinkedHashMap<String, List<Float>>();
			sDrprSeries.put("Drop Rate (%)--aa4643", bhSDrprList);
			model.addAttribute("sDrprChart", Chart.multiColumn("sDrprChart", "SDCCH Drop Rate (%)", categories, sDrprSeries));

			Map<String, List<Float>> cssrSeries = new LinkedHashMap<String, List<Float>>();
			cssrSeries.put("CSSR (%)--89a54e", bhCssrList);
			model.addAttribute("cssrChart", Chart.multiColumn("cssrChart", "CSSR (%)", categories, cssrSeries));

			Map<String, List<Float>> tBlkrSeries = new LinkedHashMap<String, List<Float>>();
			tBlkrSeries.put("Block Rate (%)--80699b", bhTNblkrList);
			model.addAttribute("tBlkrChart", Chart.multiColumn("tBlkrChart", "TCH Block Rate (%)", categories, tBlkrSeries));

			Map<String, List<Float>> sBlkrSeries = new LinkedHashMap<String, List<Float>>();
			sBlkrSeries.put("Block Rate (%)--db843d", bhSBlkrList);
			model.addAttribute("sBlkrChart", Chart.multiColumn("sBlkrChart", "SDCCH Block Rate (%)", categories, sBlkrSeries));
			
			// checkBox
			String[] checkColumns = {"T_DEF", "T_AVAIL", "T_ATT", "TRAF", "H_TRAF", "CSSR", "T_DRPS", "T_DRPR", "T_NBLKS", "T_NBLKR", "T_HOBLKS", "T_HOBLKR", "GoS", "S_DEF", "S_AVAIL", "S_ATT", "S_DRPS","S_DRPR", "S_BLKS", "S_BLKR"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

		} catch (ParseException e) {
			saveError(request, e.toString());
		}
		return new ModelAndView("dyLocationBh");
	}
	
	@RequestMapping("/report/radio/location/mn/bh")
	public ModelAndView mnList( @RequestParam(required = false) String region,@RequestParam(required = true) String location,
			@RequestParam(required = false) Integer startMonth,@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear,
			ModelMap model, HttpServletRequest request) {
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		if(endMonth==null){
			cal.add(Calendar.MONTH,-1);
			endMonth = cal.get(Calendar.MONTH)+1;
			endYear = cal.get(Calendar.YEAR);
		}
		
		if (startMonth == null) {
			if (endMonth > 3) {
				startMonth = endMonth -3;
				startYear = endYear;
			}
			else {
				cal1.add(Calendar.MONTH,-3);
				startMonth = cal1.get(Calendar.MONTH)+1;
				startYear = cal1.get(Calendar.YEAR);
			}
		}
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		model.addAttribute("monthList", monthList);
		
		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList",regionList);
		if(region == null){
			region="";
		}

		List<VRpMnLocationBh> vRpMnLocationBhList = vRpMnLocationBhDao.filter(startMonth,startYear, endMonth, endYear, location, region);
		List<HProvincesCode> locationList = hProvincesCodeDao.getAllLocation();
		model.addAttribute("locationList", locationList);
		model.addAttribute("vRpMnLocationBhList", vRpMnLocationBhList);
		model.addAttribute("location", location);
		
		/* Start Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> bhCssrList = new ArrayList<Float>();
		List<Float> bhTNblkrList = new ArrayList<Float>();
		List<Float> bhTDrprList = new ArrayList<Float>();
		List<Float> bhSBlkrList = new ArrayList<Float>();
		List<Float> bhSDrprList = new ArrayList<Float>();
		
		for (VRpMnLocationBh vRpMnLocationBh : vRpMnLocationBhList) {
			categories.add(vRpMnLocationBh.getMonth()+"/"+ vRpMnLocationBh.getYear());
			bhCssrList.add(vRpMnLocationBh.getBhCssr());
			bhTNblkrList.add(vRpMnLocationBh.getBhTNblkr());
			bhTDrprList.add(vRpMnLocationBh.getBhTDrpr());
			bhSBlkrList.add(vRpMnLocationBh.getBhSBlkr());
			bhSDrprList.add(vRpMnLocationBh.getBhSDrpr());
		}
		
		Map<String, List<Float>> tDrprSeries = new LinkedHashMap<String, List<Float>>();
		tDrprSeries.put("Drop Rate (%)--4572a7", bhTDrprList);
		model.addAttribute("tDrprChart", Chart.multiColumn("tDrprChart", "TCH Drop Rate (%)", categories, tDrprSeries));
		
		Map<String, List<Float>> sDrprSeries = new LinkedHashMap<String, List<Float>>();
		sDrprSeries.put("Drop Rate (%)--aa4643", bhSDrprList);
		model.addAttribute("sDrprChart", Chart.multiColumn("sDrprChart", "SDCCH Drop Rate (%)", categories, sDrprSeries));

		Map<String, List<Float>> cssrSeries = new LinkedHashMap<String, List<Float>>();
		cssrSeries.put("CSSR (%)--89a54e", bhCssrList);
		model.addAttribute("cssrChart", Chart.multiColumn("cssrChart", "CSSR (%)", categories, cssrSeries));

		Map<String, List<Float>> tBlkrSeries = new LinkedHashMap<String, List<Float>>();
		tBlkrSeries.put("Block Rate (%)--80699b", bhTNblkrList);
		model.addAttribute("tBlkrChart", Chart.multiColumn("tBlkrChart", "TCH Block Rate (%)", categories, tBlkrSeries));

		Map<String, List<Float>> sBlkrSeries = new LinkedHashMap<String, List<Float>>();
		sBlkrSeries.put("Block Rate (%)--db843d", bhSBlkrList);
		model.addAttribute("sBlkrChart", Chart.multiColumn("sBlkrChart", "SDCCH Block Rate (%)", categories, sBlkrSeries));
		/* End Chart */
		
		// checkBox
		String[] checkColumns = {"T_DEF", "T_AVAIL", "T_ATT", "TRAF", "H_TRAF", "CSSR", "T_DRPS", "T_DRPR", "T_NBLKS", "T_NBLKR", "T_HOBLKS", "T_HOBLKR", "GoS", "S_DEF", "S_AVAIL", "S_ATT", "S_DRPS","S_DRPR", "S_BLKS", "S_BLKR"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));
		
		return new ModelAndView("mnLocationBh");
	}

	@RequestMapping("/report/radio/location/wk/bh")
	public ModelAndView wkList(@RequestParam(required = false) String region, @RequestParam(required = true) String location,
			@RequestParam(required = false) Integer startWeek,@RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endWeek, @RequestParam(required = false) Integer endYear,
			ModelMap model, HttpServletRequest request) {
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();
		if(endWeek==null){
			cal.add(Calendar.WEEK_OF_YEAR, -2);
			endWeek = cal.get(Calendar.WEEK_OF_YEAR);
			endYear = cal.get(Calendar.YEAR);
		}
		
		if (startWeek == null) {
			if (endWeek > 4) {
				startWeek = endWeek -4;
				startYear = endYear;
			}
			else {
				cal1.add(Calendar.WEEK_OF_YEAR,-6);
				startWeek = cal1.get(Calendar.WEEK_OF_YEAR);
				startYear = cal1.get(Calendar.YEAR);
			}
		}
		model.addAttribute("startWeek", startWeek);
		model.addAttribute("endWeek", endWeek);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList",regionList);
		
		if(region == null){
			region="";
		}

		List<VRpWkLocationBh> vRpWkLocationBhList = vRpWkLocationBhDao.filter(startWeek,startYear, endWeek, endYear, location, region);
		List<HProvincesCode> locationList = hProvincesCodeDao.getAllLocation();
		model.addAttribute("locationList", locationList);
		model.addAttribute("vRpWkLocationBhList", vRpWkLocationBhList);
		model.addAttribute("location", location);
		/* Start Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> bhCssrList = new ArrayList<Float>();
		List<Float> bhTNblkrList = new ArrayList<Float>();
		List<Float> bhTDrprList = new ArrayList<Float>();
		List<Float> bhSBlkrList = new ArrayList<Float>();
		List<Float> bhSDrprList = new ArrayList<Float>();
		
		for (VRpWkLocationBh vRpWkLocationBh : vRpWkLocationBhList) {
			categories.add(vRpWkLocationBh.getWeek()+"/"+vRpWkLocationBh.getYear());
			bhCssrList.add(vRpWkLocationBh.getBhCssr());
			bhTNblkrList.add(vRpWkLocationBh.getBhTNblkr());
			bhTDrprList.add(vRpWkLocationBh.getBhTDrpr());
			bhSBlkrList.add(vRpWkLocationBh.getBhSBlkr());
			bhSDrprList.add(vRpWkLocationBh.getBhSDrpr());
		}
		
		Map<String, List<Float>> tDrprSeries = new LinkedHashMap<String, List<Float>>();
		tDrprSeries.put("Drop Rate (%)--4572a7", bhTDrprList);
		model.addAttribute("tDrprChart", Chart.multiColumn("tDrprChart", "TCH Drop Rate (%)", categories, tDrprSeries));
		
		Map<String, List<Float>> sDrprSeries = new LinkedHashMap<String, List<Float>>();
		sDrprSeries.put("Drop Rate (%)--aa4643", bhSDrprList);
		model.addAttribute("sDrprChart", Chart.multiColumn("sDrprChart", "SDCCH Drop Rate (%)", categories, sDrprSeries));

		Map<String, List<Float>> cssrSeries = new LinkedHashMap<String, List<Float>>();
		cssrSeries.put("CSSR (%)--89a54e", bhCssrList);
		model.addAttribute("cssrChart", Chart.multiColumn("cssrChart", "CSSR (%)", categories, cssrSeries));

		Map<String, List<Float>> tBlkrSeries = new LinkedHashMap<String, List<Float>>();
		tBlkrSeries.put("Block Rate (%)--80699b", bhTNblkrList);
		model.addAttribute("tBlkrChart", Chart.multiColumn("tBlkrChart", "TCH Block Rate (%)", categories, tBlkrSeries));

		Map<String, List<Float>> sBlkrSeries = new LinkedHashMap<String, List<Float>>();
		sBlkrSeries.put("Block Rate (%)--db843d", bhSBlkrList);
		model.addAttribute("sBlkrChart", Chart.multiColumn("sBlkrChart", "SDCCH Block Rate (%)", categories, sBlkrSeries));
		
		// checkBox
		String[] checkColumns = {"T_DEF", "T_AVAIL", "T_ATT", "TRAF", "H_TRAF", "CSSR", "T_DRPS", "T_DRPR", "T_NBLKS", "T_NBLKR", "T_HOBLKS", "T_HOBLKR", "GoS", "S_DEF", "S_AVAIL", "S_ATT", "S_DRPS","S_DRPR", "S_BLKS", "S_BLKR"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));
		
		return new ModelAndView("wkLocationBh");
	}
}
