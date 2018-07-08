package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
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
import vn.com.vhc.vmsc2.statistics.dao.VRpMnBscHoBhIbcDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnBscHoIbcDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnBscHoBhIbc;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnBscHoIbc;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
public class MnBscHoIbcController extends BaseController{
	@Autowired
	private VRpMnBscHoIbcDAO vRpMnBscHoIbcDao;
	@Autowired
	private VRpMnBscHoBhIbcDAO vRpMnBscHoBhIbcDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private BscDAO bscDao;
	
	@RequestMapping("/report/radio/ibc/bsc-ho/mn/list")
	public ModelAndView dyBscHoList(@RequestParam(required = false) String region,@RequestParam(required = false) String bscid, @RequestParam(required = false) Integer month, @RequestParam(required = false) Integer year, ModelMap model,
			HttpServletRequest request) {
		
		Calendar cal = Calendar.getInstance();
		if (month == null)
		{
			cal.add(Calendar.MONTH,-1);
			month = cal.get(Calendar.MONTH)+1; 
			if (year == null)
				year = cal.get(Calendar.YEAR);
		}

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
	    model.addAttribute("monthList", monthList);
		
		if (region == null)
			region = "";

		
		List<VRpMnBscHoIbc> vRpMnBscHoIbc = vRpMnBscHoIbcDao.filter(bscid, month, year, region);
		
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		model.addAttribute("vRpMnBscHoIbc", vRpMnBscHoIbc);
		return new ModelAndView("mnBscHoIbcList");
	}
	
	@RequestMapping("/report/radio/ibc/bsc-ho/mn/details")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required=true) String bscid,
			@RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		
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

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
	    model.addAttribute("monthList", monthList);
		
		if (region == null)
			region = "";

		
		List<VRpMnBscHoIbc> vRpMnBscHoIbcDetails = vRpMnBscHoIbcDao.filterDetails(startMonth.toString(), startYear.toString(), endMonth.toString(), endYear.toString(), bscid, region);
		
		model.addAttribute("vRpMnBscHoIbcDetails", vRpMnBscHoIbcDetails);
		model.addAttribute("bscid", bscid);
		
		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> inHoSucrList = new ArrayList<Float>();
		List<Float> ogHoSucrList = new ArrayList<Float>();
		
		for (VRpMnBscHoIbc vRpMnBscHoIbc : vRpMnBscHoIbcDetails) {
			categories.add(vRpMnBscHoIbc.getMonth()+"/"+vRpMnBscHoIbc.getYear());
			inHoSucrList.add(vRpMnBscHoIbc.getInHoSucr());
			ogHoSucrList.add(vRpMnBscHoIbc.getOgHoSucr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("In_Ho_Sucr", inHoSucrList);
		series.put("Og_Ho_Sucr", ogHoSucrList);

		model.addAttribute("chart", Chart.basicLine("BSC IBC HO " + bscid + " Monthly Report", categories, series));

		// checkBox
		String[] checkColumns = { "ogHoAtt", "ogHoSuc", "ogHoSucr", "inHoAtt", "inHoSuc", "inHoSucr"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

		String[] checkSeries = {"ogHoSucr", "inHoSucr"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		
	return "mnBscHoIbcDetails";
	}
	
	@RequestMapping("/report/radio/ibc/bsc-ho/mn/bhDetails")
	public String showReportBhDetails(@RequestParam(required = false) String region,@RequestParam(required=true) String bscid,
			@RequestParam(required = false) Integer startMonth, @RequestParam(required = false) Integer startYear, @RequestParam(required = false) Integer endMonth, @RequestParam(required = false) Integer endYear, ModelMap model, HttpServletRequest request) {
		
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
		model.addAttribute("endYear", startYear);

		List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
		model.addAttribute("regionList", regionList);
		
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
	    model.addAttribute("monthList", monthList);
		
		if (region == null)
			region = "";

		
		List<VRpMnBscHoBhIbc> vRpMnBscHoBhIbcDetails = vRpMnBscHoBhIbcDao.filterDetails(startMonth.toString(), startYear.toString(), endMonth.toString(), endYear.toString(), bscid, region);
		
		model.addAttribute("vRpMnBscHoBhIbcDetails", vRpMnBscHoBhIbcDetails);
		model.addAttribute("bscid", bscid);
		
		/* Chart */
		List<String> categories = new ArrayList<String>();
		List<Float> inHoSucrList = new ArrayList<Float>();
		List<Float> ogHoSucrList = new ArrayList<Float>();
		
		for (VRpMnBscHoBhIbc vRpMnBscHoBhIbc : vRpMnBscHoBhIbcDetails) {
			categories.add(vRpMnBscHoBhIbc.getMonth()+"/"+vRpMnBscHoBhIbc.getYear());
			inHoSucrList.add(vRpMnBscHoBhIbc.getBhInHoSucr());
			ogHoSucrList.add(vRpMnBscHoBhIbc.getBhOgHoSucr());
		}
		Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
		series.put("Bh_In_Ho_Sucr", inHoSucrList);
		series.put("Bh_Og_Ho_Sucr", ogHoSucrList);

		model.addAttribute("chart", Chart.basicLine("BSC IBC HO BH " + bscid + " Monthly Report", categories, series));

		// checkBox
		String[] checkColumns = { "bhOgHoAtt","bhOgHoSuc","bhOgHoSucr","bhInHoAtt","bhInHoSuc","bhInHoSucr"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

		String[] checkSeries = {"bhOgHoSucr", "bhInHoSucr"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		
	return "mnBscHoBhIbcDetails";
	}
}
