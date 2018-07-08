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
import vn.com.vhc.vmsc2.statistics.dao.VRpDyBscBhIbcDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnBscBhIbcDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkBscBhIbcDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBscBhIbc;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnBscBhIbc;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkBscBhIbc;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
public class BhBscQosIbcController extends BaseController {
	@Autowired
	private VRpDyBscBhIbcDAO vRpDyBscBhIbcDao;
	@Autowired
	private VRpWkBscBhIbcDAO vRpWkBscBhIbcDao;
	@Autowired
	private VRpMnBscBhIbcDAO vRpMnBscBhIbcDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("/report/radio/ibc/bsc/dy/bh")
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
			
			List<VRpDyBscBhIbc> vRpDyBscBhIbcList = vRpDyBscBhIbcDao.filter(df.format(startDay), df.format(endDay), bscid, region);

			model.addAttribute("bscid", bscid);
			model.addAttribute("endDate", df.format(endDay));
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("vRpDyBscBhIbcList", vRpDyBscBhIbcList);
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
			for (VRpDyBscBhIbc vRpDyBscBhIbc : vRpDyBscBhIbcList) {
				categories.add(df.format(vRpDyBscBhIbc.getDay()));
				bhCssrList.add(vRpDyBscBhIbc.getBhCssr());
				bhTNblksList.add(vRpDyBscBhIbc.getBhTNblks());
				bhTNblkrList.add(vRpDyBscBhIbc.getBhTNblkr());
				bhTDrpsList.add(vRpDyBscBhIbc.getBhTDrps());
				bhTDrprList.add(vRpDyBscBhIbc.getBhTDrpr());
				bhTTrafList.add(vRpDyBscBhIbc.getBhTTraf());
				bhTTrafhList.add(vRpDyBscBhIbc.getBhTTrafh());
				bhTGosList.add(vRpDyBscBhIbc.getBhTGos());
				bhSBlksList.add(vRpDyBscBhIbc.getBhSBlks());
				bhSBlkrList.add(vRpDyBscBhIbc.getBhSBlkr());
				bhSDrpsList.add(vRpDyBscBhIbc.getBhSDrps());
				bhSDrprList.add(vRpDyBscBhIbc.getBhSDrpr());
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

			model.addAttribute("chart", Chart.comboDualAxes("BSC IBC BH " + bscid + " Daily Report", categories, series));
			
			// checkBox
			String[] checkColumns = {"T_DEF", "T_AVAIL", "T_ATT", "TRAF", "H_TRAF", "T_DRPS", "T_DRPR", "T_NBLKS", "T_NBLKR", "T_HOBLKS", "T_HOBLKR", "CSSR", "GoS", "S_DEF", "S_AVAIL", "S_ATT", "S_DRPS","S_DRPR", "S_BLKS", "S_BLKR"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpDyBscBhIbc", 6));

		} catch (ParseException e) {
			saveError(request, e.toString());
		}
		return new ModelAndView("dyBscBhIbc");
	}
	
	@RequestMapping("/report/radio/ibc/bsc/mn/bh")
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

		List<VRpMnBscBhIbc> vRpMnBscBhIbcList = vRpMnBscBhIbcDao.filter(startMonth,startYear, endMonth, endYear, bscid, region);

		model.addAttribute("vRpMnBscBhIbcList", vRpMnBscBhIbcList);
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
		for (VRpMnBscBhIbc vRpMnBscBhIbc : vRpMnBscBhIbcList) {
			categories.add(vRpMnBscBhIbc.getMonth()+"/"+vRpMnBscBhIbc.getYear());
			bhCssrList.add(vRpMnBscBhIbc.getBhCssr());
			bhTNblksList.add(vRpMnBscBhIbc.getBhTNblks());
			bhTNblkrList.add(vRpMnBscBhIbc.getBhTNblkr());
			bhTDrpsList.add(vRpMnBscBhIbc.getBhTDrps());
			bhTDrprList.add(vRpMnBscBhIbc.getBhTDrpr());
			bhTTrafList.add(vRpMnBscBhIbc.getBhTTraf());
			bhTTrafhList.add(vRpMnBscBhIbc.getBhTTrafh());
			bhTGosList.add(vRpMnBscBhIbc.getBhTGos());
			bhSBlksList.add(vRpMnBscBhIbc.getBhSBlks());
			bhSBlkrList.add(vRpMnBscBhIbc.getBhSBlkr());
			bhSDrpsList.add(vRpMnBscBhIbc.getBhSDrps());
			bhSDrprList.add(vRpMnBscBhIbc.getBhSDrpr());
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

		model.addAttribute("chart", Chart.comboDualAxes("BSC IBC BH " + bscid + " Monthly Report", categories, series));
		
		// checkBox
		String[] checkColumns = {"T_DEF", "T_AVAIL", "T_ATT", "T_NBLKR", "T_NBLKS", "T_HOBLKR","T_HOBLKS", "CSSR", "T_DRPR", "T_DRPS", "TRAF", "H_TRAF", "GoS", "S_DEF", "S_AVAIL", "S_ATT", "S_BLKR", "S_BLKS", "S_DRPR", "S_DRPS"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpMnBscBhIbc", 5));

		return new ModelAndView("mnBscBhIbc");
	}

	@RequestMapping("/report/radio/ibc/bsc/wk/bh")
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

		List<VRpWkBscBhIbc> vRpWkBscBhIbcList = vRpWkBscBhIbcDao.filter(startWeek, startYear,endWeek, endYear, bscid, region);

		model.addAttribute("vRpWkBscBhIbcList", vRpWkBscBhIbcList);
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
		for (VRpWkBscBhIbc vRpWkBscBhIbc : vRpWkBscBhIbcList) {
			categories.add(vRpWkBscBhIbc.getWeek()+"/"+vRpWkBscBhIbc.getYear());
			bhCssrList.add(vRpWkBscBhIbc.getBhCssr());
			bhTNblksList.add(vRpWkBscBhIbc.getBhTNblks());
			bhTNblkrList.add(vRpWkBscBhIbc.getBhTNblkr());
			bhTDrpsList.add(vRpWkBscBhIbc.getBhTDrps());
			bhTDrprList.add(vRpWkBscBhIbc.getBhTDrpr());
			bhTTrafList.add(vRpWkBscBhIbc.getBhTTraf());
			bhTTrafhList.add(vRpWkBscBhIbc.getBhTTrafh());
			bhTGosList.add(vRpWkBscBhIbc.getBhTGos());
			bhSBlksList.add(vRpWkBscBhIbc.getBhSBlks());
			bhSBlkrList.add(vRpWkBscBhIbc.getBhSBlkr());
			bhSDrpsList.add(vRpWkBscBhIbc.getBhSDrps());
			bhSDrprList.add(vRpWkBscBhIbc.getBhSDrpr());
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

		model.addAttribute("chart", Chart.comboDualAxes("BSC IBC BH " + bscid + " Monthly Report", categories, series));
		
		// checkBox
		String[] checkColumns = {"T_DEF", "T_AVAIL", "T_ATT", "T_NBLKR", "T_NBLKS", "T_HOBLKR","T_HOBLKS", "CSSR", "T_DRPR", "T_DRPS", "TRAF", "H_TRAF", "GoS", "S_DEF", "S_AVAIL", "S_ATT", "S_BLKR", "S_BLKS", "S_DRPR", "S_DRPS"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, "vRpWkBscBhIbc", 5));

		return new ModelAndView("wkBscBhIbc");
	}
}