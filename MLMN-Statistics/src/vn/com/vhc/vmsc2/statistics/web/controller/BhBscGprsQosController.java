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
import vn.com.vhc.vmsc2.statistics.dao.VRpDyBscGprsQosBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpMnBscGprsQosBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpWkBscGprsQosBhDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBscGprsQosBh;
import vn.com.vhc.vmsc2.statistics.domain.VRpMnBscGprsQosBh;
import vn.com.vhc.vmsc2.statistics.domain.VRpWkBscGprsQosBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;



@Controller
public class BhBscGprsQosController extends BaseController {
	@Autowired
	private VRpDyBscGprsQosBhDAO vRpDyBscGprsQosBhDao;
	@Autowired
	private VRpWkBscGprsQosBhDAO vRpWkBscGprsQosBhDao;
	@Autowired
	private VRpMnBscGprsQosBhDAO vRpMnBscGprsQosBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("/report/radio/bsc-gprs-qos/dy/bh")
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
			
			List<VRpDyBscGprsQosBh> vRpDyBscGprsQosBh = vRpDyBscGprsQosBhDao.filter(df.format(startDay), df.format(endDay), bscid, region);

			model.addAttribute("region", region);
			model.addAttribute("bscid", bscid);
			model.addAttribute("endDate", df.format(endDay));
			model.addAttribute("startDate", df.format(startDay));
			model.addAttribute("vRpDyBscGprsQosBh", vRpDyBscGprsQosBh);
			//start chart
			List<String> categories = new ArrayList<String>();
			List<Float> bhDlTbfSucrList = new ArrayList<Float>();
			List<Float> bhUlTbfSucrList = new ArrayList<Float>();
			
			for (VRpDyBscGprsQosBh vRpDyBsc : vRpDyBscGprsQosBh) {
				categories.add(vRpDyBsc.getBh()+":00/"+ df.format(vRpDyBsc.getDay()));
				bhDlTbfSucrList.add(vRpDyBsc.getBhDlTbfSucr());
				bhUlTbfSucrList.add(vRpDyBsc.getBhUlTbfSucr());
			}
			
			Map<String, List<Float>> dlTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
			dlTbfSucrSeries.put("DL TBF SUCR (%)--80699b", bhDlTbfSucrList);
			model.addAttribute("dlTbfSucrChart", Chart.multiColumn("dlTbfSucrChart", "DL TBF SUCR (%)", categories, dlTbfSucrSeries));

			Map<String, List<Float>> ulTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
			ulTbfSucrSeries.put("UL TBF SUCR (%)--db843d", bhUlTbfSucrList);
			model.addAttribute("ulTbfSucrChart", Chart.multiColumn("ulTbfSucrChart", "UL TBF SUCR (%)", categories, ulTbfSucrSeries));
			// checkBox
			String[] checkColumns = {"bhDlTbfReq","bhDlTbfSucr","bhDlTbfSucc","bhUlTbfReq","bhUlTbfSucr","bhUlTbfSucc","bhGdlTraf","bhGulTraf","bhEdlTraf","bhEulTraf"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 6));

			String[] checkSeries ={"bhDlTbfSucr","bhUlTbfSucr"};
			model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
			//end chart


		} catch (ParseException e) {
			saveError(request, e.toString());
		}
		return new ModelAndView("dyBscGprsQosBh");
	}
	
	@RequestMapping("/report/radio/bsc-gprs-qos/mn/bh")
	public ModelAndView mnList(@RequestParam(required = false) String region,@RequestParam(required = true) String bscid,
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

		List<VRpMnBscGprsQosBh> vRpMnBscGprsQosBh = vRpMnBscGprsQosBhDao.filter(startMonth, startYear,endMonth, endYear, bscid, region);

		model.addAttribute("vRpMnBscGprsQosBh", vRpMnBscGprsQosBh);
		model.addAttribute("bscid", bscid);
		model.addAttribute("region", region);
		//start chart
		List<String> categories = new ArrayList<String>();
		List<Float> bhDlTbfSucrList = new ArrayList<Float>();
		List<Float> bhUlTbfSucrList = new ArrayList<Float>();
		
		for (VRpMnBscGprsQosBh vRpMnBsc : vRpMnBscGprsQosBh) {
			categories.add(Integer.toString(vRpMnBsc.getMonth())+"/"+vRpMnBsc.getYear());
			bhDlTbfSucrList.add(vRpMnBsc.getBhDlTbfSucr());
			bhUlTbfSucrList.add(vRpMnBsc.getBhUlTbfSucr());
		}
		Map<String, List<Float>> dlTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
		dlTbfSucrSeries.put("DL TBF SUCR (%)--80699b", bhDlTbfSucrList);
		model.addAttribute("dlTbfSucrChart", Chart.multiColumn("dlTbfSucrChart", "DL TBF SUCR (%)", categories, dlTbfSucrSeries));

		Map<String, List<Float>> ulTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
		ulTbfSucrSeries.put("UL TBF SUCR (%)--db843d", bhUlTbfSucrList);
		model.addAttribute("ulTbfSucrChart", Chart.multiColumn("ulTbfSucrChart", "UL TBF SUCR (%)", categories, ulTbfSucrSeries));
		// checkBox
		String[] checkColumns = {"bhDlTbfReq","bhDlTbfSucr","bhDlTbfSucc","bhUlTbfReq","bhUlTbfSucr","bhUlTbfSucc","bhGdlTraf","bhGulTraf","bhEdlTraf","bhEulTraf"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 6));

		String[] checkSeries ={"bhDlTbfSucr","bhUlTbfSucr"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		//end chart


		return new ModelAndView("mnBscGprsQosBh");
	}

	@RequestMapping("/report/radio/bsc-gprs-qos/wk/bh")
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

		List<VRpWkBscGprsQosBh> vRpWkBscGprsQosBh = vRpWkBscGprsQosBhDao.filter(startWeek,startYear, endWeek, endYear, bscid, region);

		model.addAttribute("vRpWkBscGprsQosBh", vRpWkBscGprsQosBh);
		model.addAttribute("bscid", bscid);
		model.addAttribute("region", region);
		//start chart
		List<String> categories = new ArrayList<String>();
		List<Float> bhDlTbfSucrList = new ArrayList<Float>();
		List<Float> bhUlTbfSucrList = new ArrayList<Float>();
		
		for (VRpWkBscGprsQosBh vRpWkBsc : vRpWkBscGprsQosBh) {
			categories.add(Integer.toString(vRpWkBsc.getWeek())+"/"+vRpWkBsc.getYear());
			bhDlTbfSucrList.add(vRpWkBsc.getBhDlTbfSucr());
			bhUlTbfSucrList.add(vRpWkBsc.getBhUlTbfSucr());
		}
		
		Map<String, List<Float>> dlTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
		dlTbfSucrSeries.put("DL TBF SUCR (%)--80699b", bhDlTbfSucrList);
		model.addAttribute("dlTbfSucrChart", Chart.multiColumn("dlTbfSucrChart", "DL TBF SUCR (%)", categories, dlTbfSucrSeries));

		Map<String, List<Float>> ulTbfSucrSeries = new LinkedHashMap<String, List<Float>>();
		ulTbfSucrSeries.put("UL TBF SUCR (%)--db843d", bhUlTbfSucrList);
		model.addAttribute("ulTbfSucrChart", Chart.multiColumn("ulTbfSucrChart", "UL TBF SUCR (%)", categories, ulTbfSucrSeries));
		
		// checkBox
		String[] checkColumns = {"bhDlTbfReq","bhDlTbfSucr","bhDlTbfSucc","bhUlTbfReq","bhUlTbfSucr","bhUlTbfSucc","bhGdlTraf","bhGulTraf","bhEdlTraf","bhEulTraf"};
		model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 6));

		String[] checkSeries ={"bhDlTbfSucr","bhUlTbfSucr"};
		model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		//end chart

		return new ModelAndView("wkBscGprsQosBh");
	}
}