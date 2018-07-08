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
import vn.com.vhc.vmsc2.statistics.dao.VRpDyProvinceBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnProvinceBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkProvinceBhDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyProvinceBh;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnProvinceBh;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkProvinceBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
public class BhProvinceQosController extends BaseController {
	@Autowired
	private VRpDyProvinceBhDAO vRpDyProvinceBhDao;
	@Autowired
	private VRpWkProvinceBhDAO vRpWkProvinceBhDao;
	@Autowired
	private VRpMnProvinceBhDAO vRpMnProvinceBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("/report/radio/province/dy/bh")
	public ModelAndView dyList( @RequestParam(required = false) String region,@RequestParam(required = true) String province,
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
			model.addAttribute("regionList", regionList);
			
			if (region == null)
				region = "";
			
			List<VRpDyProvinceBh> vRpDyProvinceBhList = vRpDyProvinceBhDao.filter(df.format(startDay), df.format(endDay), province, region);
			List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
			model.addAttribute("provinceList", provinceList);
			model.addAttribute("province", province);
			model.addAttribute("region", region);
			model.addAttribute("endDate", df.format(endDay));
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("vRpDyProvinceBhList", vRpDyProvinceBhList);
			
			List<String> categories = new ArrayList<String>();
			List<Float> bhCssrList = new ArrayList<Float>();
			List<Float> bhTNblkrList = new ArrayList<Float>();
			List<Float> bhTDrprList = new ArrayList<Float>();
			List<Float> bhSBlkrList = new ArrayList<Float>();
			List<Float> bhSDrprList = new ArrayList<Float>();
			for (VRpDyProvinceBh vRpDyProvinceBh : vRpDyProvinceBhList) {
				categories.add(df.format(vRpDyProvinceBh.getDay()));
				bhCssrList.add(vRpDyProvinceBh.getBhCssr());
				bhTNblkrList.add(vRpDyProvinceBh.getBhTNblkr());
				bhTDrprList.add(vRpDyProvinceBh.getBhTDrpr());
				bhSBlkrList.add(vRpDyProvinceBh.getBhSBlkr());
				bhSDrprList.add(vRpDyProvinceBh.getBhSDrpr());
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
			String[] checkColumns = {"T_DEF", "T_AVAIL", "T_ATT", "T_NBLKR", "T_NBLKS", "T_HOBLKR", "T_HOBLKS", "CSSR", "T_DRPR", "T_DRPS", "TRAF", "H_TRAF",  "GoS", "S_DEF", "S_AVAIL", "S_ATT", "S_BLKR", "S_BLKS","S_DRPR", "S_DRPS"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpDyProvinceBh", 6));

		} catch (ParseException e) {
			saveError(request, e.toString());
		}
		return new ModelAndView("dyProvinceBh");
	}
	
	@RequestMapping("/report/radio/province/mn/bh")
	public ModelAndView mnList( @RequestParam(required = false) String region,@RequestParam(required = true) String province,
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
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";

		List<VRpMnProvinceBh> vRpMnProvinceBhList = vRpMnProvinceBhDao.filter(startMonth, startYear,endMonth, endYear, province, region);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("vRpMnProvinceBhList", vRpMnProvinceBhList);
		model.addAttribute("province", province);
		model.addAttribute("region", region);
		
		List<String> categories = new ArrayList<String>();
		List<Float> bhCssrList = new ArrayList<Float>();
		List<Float> bhTNblkrList = new ArrayList<Float>();
		List<Float> bhTDrprList = new ArrayList<Float>();
		List<Float> bhSBlkrList = new ArrayList<Float>();
		List<Float> bhSDrprList = new ArrayList<Float>();
		for (VRpMnProvinceBh vRpMnProvinceBh : vRpMnProvinceBhList) {
			categories.add(vRpMnProvinceBh.getMonth()+"/"+ vRpMnProvinceBh.getYear());
			bhCssrList.add(vRpMnProvinceBh.getBhCssr());
			bhTNblkrList.add(vRpMnProvinceBh.getBhTNblkr());
			bhTDrprList.add(vRpMnProvinceBh.getBhTDrpr());
			bhSBlkrList.add(vRpMnProvinceBh.getBhSBlkr());
			bhSDrprList.add(vRpMnProvinceBh.getBhSDrpr());
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
		String[] checkColumns = {"T_DEF", "T_AVAIL", "T_ATT", "T_NBLKR", "T_NBLKS", "T_HOBLKR", "T_HOBLKS", "CSSR", "T_DRPR", "T_DRPS", "TRAF", "H_TRAF",  "GoS", "S_DEF", "S_AVAIL", "S_ATT", "S_BLKR", "S_BLKS","S_DRPR", "S_DRPS"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpMnProvinceBh", 5));

		return new ModelAndView("mnProvinceBh");
	}

	@RequestMapping("/report/radio/province/wk/bh")
	public ModelAndView wkList( @RequestParam(required = false) String region,@RequestParam(required = true) String province,
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
		model.addAttribute("regionList", regionList);
		
		if (region == null)
			region = "";

		List<VRpWkProvinceBh> vRpWkProvinceBhList = vRpWkProvinceBhDao.filter(startWeek,startYear, endWeek, endYear, province, region);
		List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
		model.addAttribute("provinceList", provinceList);
		model.addAttribute("vRpWkProvinceBhList", vRpWkProvinceBhList);
		model.addAttribute("province", province);
		model.addAttribute("region", region);
		
		/* Start Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> bhCssrList = new ArrayList<Float>();
		List<Float> bhTNblkrList = new ArrayList<Float>();
		List<Float> bhTDrprList = new ArrayList<Float>();
		List<Float> bhSBlkrList = new ArrayList<Float>();
		List<Float> bhSDrprList = new ArrayList<Float>();
		for (VRpWkProvinceBh vRpWkProvinceBh : vRpWkProvinceBhList) {
			categories.add(vRpWkProvinceBh.getWeek()+"/"+vRpWkProvinceBh.getYear());
			bhCssrList.add(vRpWkProvinceBh.getBhCssr());
			bhTNblkrList.add(vRpWkProvinceBh.getBhTNblkr());
			bhTDrprList.add(vRpWkProvinceBh.getBhTDrpr());
			bhSBlkrList.add(vRpWkProvinceBh.getBhSBlkr());
			bhSDrprList.add(vRpWkProvinceBh.getBhSDrpr());
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
		String[] checkColumns = {"T_DEF", "T_AVAIL", "T_ATT", "T_NBLKR", "T_NBLKS", "T_HOBLKR", "T_HOBLKS", "CSSR", "T_DRPR", "T_DRPS", "TRAF", "H_TRAF",  "GoS", "S_DEF", "S_AVAIL", "S_ATT", "S_BLKR", "S_BLKS","S_DRPR", "S_DRPS"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpWkProvinceBh", 5));

		return new ModelAndView("wkProvinceBh");
	}
}
