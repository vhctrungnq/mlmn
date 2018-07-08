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
import vn.com.vhc.vmsc2.statistics.dao.VRpDyBscBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnBscBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkBscBhDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBscBh;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnBscBh;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkBscBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
public class BhBscQosController extends BaseController {
	@Autowired
	private VRpDyBscBhDAO vRpDyBscBhDao;
	@Autowired
	private VRpWkBscBhDAO vRpWkBscBhDao;
	@Autowired
	private VRpMnBscBhDAO vRpMnBscBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("/report/radio/bsc/dy/bh")
	public ModelAndView dyList( @RequestParam(required = false) String region,@RequestParam(required = true) String bscid,
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
				startDay = new Date(endDay.getTime() + 25 * 24 * 60 * 60 * 1000);
			} else {
				startDay = df.parse(startDate);
			}

			List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
			model.addAttribute("regionList", regionList);
				
			if (region == null)
				region = "";
			
			List<VRpDyBscBh> vRpDyBscBhList = vRpDyBscBhDao.filter(df.format(startDay), df.format(endDay), bscid, region);

			model.addAttribute("bscid", bscid);
			model.addAttribute("endDate", df.format(endDay));
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("vRpDyBscBhList", vRpDyBscBhList);
			model.addAttribute("region", region);
			
			/* Start Chart */
			List<String> categories = new ArrayList<String>();
			List<Float> bhCssrList = new ArrayList<Float>();
			List<Float> bhTNblksList = new ArrayList<Float>();
			List<Float> bhTNblkrList = new ArrayList<Float>();
			List<Float> bhTDrpsList = new ArrayList<Float>();
			List<Float> bhTDrprList = new ArrayList<Float>();
			List<Float> bhTTrafList = new ArrayList<Float>();
			List<Float> bhTTrafhList = new ArrayList<Float>();
			List<Float> bhTGosList = new ArrayList<Float>();
			List<Float> bhSBlksList = new ArrayList<Float>();
			List<Float> bhSBlkrList = new ArrayList<Float>();
			List<Float> bhSDrpsList = new ArrayList<Float>();
			List<Float> bhSDrprList = new ArrayList<Float>();
			for (VRpDyBscBh vRpDyBscBh : vRpDyBscBhList) {
				categories.add(df.format(vRpDyBscBh.getDay()));
				bhCssrList.add(vRpDyBscBh.getBhCssr());
				bhTNblksList.add(vRpDyBscBh.getBhTNblks());
				bhTNblkrList.add(vRpDyBscBh.getBhTNblkr());
				bhTDrpsList.add(vRpDyBscBh.getBhTDrps());
				bhTDrprList.add(vRpDyBscBh.getBhTDrpr());
				bhTTrafList.add(vRpDyBscBh.getBhTTraf());
				bhTTrafhList.add(vRpDyBscBh.getBhTTrafh());
				bhTGosList.add(vRpDyBscBh.getBhTGos());
				bhSBlksList.add(vRpDyBscBh.getBhSBlks());
				bhSBlkrList.add(vRpDyBscBh.getBhSBlkr());
				bhSDrpsList.add(vRpDyBscBh.getBhSDrps());
				bhSDrprList.add(vRpDyBscBh.getBhSDrpr());
			}
			Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
			series.put("TRAF", bhTTrafList);
			series.put("H_TRAF", bhTTrafhList);
			series.put("T_DRPS", bhTDrpsList);
			series.put("T_DRPR (%)", bhTDrprList);
			series.put("T_NBLKS", bhTNblksList);
			series.put("T_NBLKR (%)", bhTNblkrList);
			series.put("CSSR (%)", bhCssrList);
			series.put("GoS (%)", bhTGosList);
			series.put("S_DRPS", bhSDrpsList);
			series.put("S_DRPR (%)", bhSDrprList);
			series.put("S_BLKS", bhSBlksList);
			series.put("S_BLKR (%)", bhSBlkrList);

			model.addAttribute("chart", Chart.comboDualAxes("BSC BH " + bscid + " Daily Report", categories, series));
			
			// checkBox
			String[] checkColumns = {"T_DEF", "T_AVAIL", "T_ATT", "T_NBLKR", "T_NBLKS", "T_HOBLKR","T_HOBLKS", "CSSR", "T_DRPR", "T_DRPS", "TRAF", "H_TRAF", "GoS", "S_DEF", "S_AVAIL", "S_ATT", "S_BLKR", "S_BLKS", "S_DRPR", "S_DRPS"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpDyBscBh", 6));

		} catch (ParseException e) {
			saveError(request, e.toString());
		}
		return new ModelAndView("dyBscBh");
	}
	
	@RequestMapping("/report/radio/bsc/mn/bh")
	public ModelAndView mnList( @RequestParam(required = false) String region,@RequestParam(required = true) String bscid,
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

		List<VRpMnBscBh> vRpMnBscBhList = vRpMnBscBhDao.filter(startMonth,startYear, endMonth, endYear, bscid, region);

		model.addAttribute("vRpMnBscBhList", vRpMnBscBhList);
		model.addAttribute("bscid", bscid);
		model.addAttribute("region", region);
		/* Start Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> bhCssrList = new ArrayList<Float>();
		List<Float> bhTNblksList = new ArrayList<Float>();
		List<Float> bhTNblkrList = new ArrayList<Float>();
		List<Float> bhTDrpsList = new ArrayList<Float>();
		List<Float> bhTDrprList = new ArrayList<Float>();
		List<Float> bhTTrafList = new ArrayList<Float>();
		List<Float> bhTTrafhList = new ArrayList<Float>();
		List<Float> bhTGosList = new ArrayList<Float>();
		List<Float> bhSBlksList = new ArrayList<Float>();
		List<Float> bhSBlkrList = new ArrayList<Float>();
		List<Float> bhSDrpsList = new ArrayList<Float>();
		List<Float> bhSDrprList = new ArrayList<Float>();
		for (VRpMnBscBh vRpMnBscBh : vRpMnBscBhList) {
			categories.add(vRpMnBscBh.getMonth()+"/"+vRpMnBscBh.getYear());
			bhCssrList.add(vRpMnBscBh.getBhCssr());
			bhTNblksList.add(vRpMnBscBh.getBhTNblks());
			bhTNblkrList.add(vRpMnBscBh.getBhTNblkr());
			bhTDrpsList.add(vRpMnBscBh.getBhTDrps());
			bhTDrprList.add(vRpMnBscBh.getBhTDrpr());
			bhTTrafList.add(vRpMnBscBh.getBhTTraf());
			bhTTrafhList.add(vRpMnBscBh.getBhTTrafh());
			bhTGosList.add(vRpMnBscBh.getBhTGos());
			bhSBlksList.add(vRpMnBscBh.getBhSBlks());
			bhSBlkrList.add(vRpMnBscBh.getBhSBlkr());
			bhSDrpsList.add(vRpMnBscBh.getBhSDrps());
			bhSDrprList.add(vRpMnBscBh.getBhSDrpr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("TRAF", bhTTrafList);
		series.put("H_TRAF", bhTTrafhList);
		series.put("T_DRPS", bhTDrpsList);
		series.put("T_DRPR (%)", bhTDrprList);
		series.put("T_NBLKS", bhTNblksList);
		series.put("T_NBLKR (%)", bhTNblkrList);
		series.put("CSSR (%)", bhCssrList);
		series.put("GoS (%)", bhTGosList);
		series.put("S_DRPS", bhSDrpsList);
		series.put("S_DRPR (%)", bhSDrprList);
		series.put("S_BLKS", bhSBlksList);
		series.put("S_BLKR (%)", bhSBlkrList);

		model.addAttribute("chart", Chart.comboDualAxes("BSC BH " + bscid + " Monthly Report", categories, series));
		
		// checkBox
		String[] checkColumns = {"T_DEF", "T_AVAIL", "T_ATT", "T_NBLKR", "T_NBLKS", "T_HOBLKR","T_HOBLKS", "CSSR", "T_DRPR", "T_DRPS", "TRAF", "H_TRAF", "GoS", "S_DEF", "S_AVAIL", "S_ATT", "S_BLKR", "S_BLKS", "S_DRPR", "S_DRPS"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpMnBscBh", 5));

		return new ModelAndView("mnBscBh");
	}

	@RequestMapping("/report/radio/bsc/wk/bh")
	public ModelAndView wkList( @RequestParam(required = false) String region,@RequestParam(required = true) String bscid,
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

		List<VRpWkBscBh> vRpWkBscBhList = vRpWkBscBhDao.filter(startWeek, startYear,endWeek, endYear, bscid, region);

		model.addAttribute("vRpWkBscBhList", vRpWkBscBhList);
		model.addAttribute("bscid", bscid);
		model.addAttribute("region", region);
		/* Start Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> bhCssrList = new ArrayList<Float>();
		List<Float> bhTNblksList = new ArrayList<Float>();
		List<Float> bhTNblkrList = new ArrayList<Float>();
		List<Float> bhTDrpsList = new ArrayList<Float>();
		List<Float> bhTDrprList = new ArrayList<Float>();
		List<Float> bhTTrafList = new ArrayList<Float>();
		List<Float> bhTTrafhList = new ArrayList<Float>();
		List<Float> bhTGosList = new ArrayList<Float>();
		List<Float> bhSBlksList = new ArrayList<Float>();
		List<Float> bhSBlkrList = new ArrayList<Float>();
		List<Float> bhSDrpsList = new ArrayList<Float>();
		List<Float> bhSDrprList = new ArrayList<Float>();
		for (VRpWkBscBh vRpWkBscBh : vRpWkBscBhList) {
			categories.add(vRpWkBscBh.getWeek()+"/"+vRpWkBscBh.getYear());
			bhCssrList.add(vRpWkBscBh.getBhCssr());
			bhTNblksList.add(vRpWkBscBh.getBhTNblks());
			bhTNblkrList.add(vRpWkBscBh.getBhTNblkr());
			bhTDrpsList.add(vRpWkBscBh.getBhTDrps());
			bhTDrprList.add(vRpWkBscBh.getBhTDrpr());
			bhTTrafList.add(vRpWkBscBh.getBhTTraf());
			bhTTrafhList.add(vRpWkBscBh.getBhTTrafh());
			bhTGosList.add(vRpWkBscBh.getBhTGos());
			bhSBlksList.add(vRpWkBscBh.getBhSBlks());
			bhSBlkrList.add(vRpWkBscBh.getBhSBlkr());
			bhSDrpsList.add(vRpWkBscBh.getBhSDrps());
			bhSDrprList.add(vRpWkBscBh.getBhSDrpr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("TRAF", bhTTrafList);
		series.put("H_TRAF", bhTTrafhList);
		series.put("T_DRPS", bhTDrpsList);
		series.put("T_DRPR (%)", bhTDrprList);
		series.put("T_NBLKS", bhTNblksList);
		series.put("T_NBLKR (%)", bhTNblkrList);
		series.put("CSSR (%)", bhCssrList);
		series.put("GoS (%)", bhTGosList);
		series.put("S_DRPS", bhSDrpsList);
		series.put("S_DRPR (%)", bhSDrprList);
		series.put("S_BLKS", bhSBlksList);
		series.put("S_BLKR (%)", bhSBlkrList);

		model.addAttribute("chart", Chart.comboDualAxes("BSC BH " + bscid + " Monthly Report", categories, series));
		
		// checkBox
		String[] checkColumns = {"T_DEF", "T_AVAIL", "T_ATT", "T_NBLKR", "T_NBLKS", "T_HOBLKR","T_HOBLKS", "CSSR", "T_DRPR", "T_DRPS", "TRAF", "H_TRAF", "GoS", "S_DEF", "S_AVAIL", "S_ATT", "S_BLKR", "S_BLKS", "S_DRPR", "S_DRPS"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpWkBscBh", 5));

		return new ModelAndView("wkBscBh");
	}
}