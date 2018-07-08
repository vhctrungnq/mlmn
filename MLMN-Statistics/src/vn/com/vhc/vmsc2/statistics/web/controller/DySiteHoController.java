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
import vn.com.vhc.vmsc2.statistics.dao.VRpDySiteHoBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDySiteHoDAO;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDySiteHo;
import vn.com.vhc.vmsc2.statistics.domain.VRpDySiteHoBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
public class DySiteHoController extends BaseController{
	@Autowired
	private VRpDySiteHoDAO vRpDySiteHoDao;
	@Autowired
	private VRpDySiteHoBhDAO vRpDySiteHoBhDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping("/report/radio/site-ho/dy/list")
	public ModelAndView dySiteHoList(@RequestParam(required = false) String region,@RequestParam(required = false) String province,@RequestParam(required = false) String bscid, @RequestParam(required = false) String siteid, @RequestParam(required = false) String day, ModelMap model,
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
			
			model.addAttribute("region", region);
			
			List<VRpDySiteHo> vRpDySiteHo = vRpDySiteHoDao.filter(province,bscid, siteid, d, region);
			
			List<HProvincesCode> provinceList = hProvincesCodeDao.getAllProvince();
			model.addAttribute("provinceList", provinceList);
			model.addAttribute("province", province);
			model.addAttribute("day", df.format(d));
			model.addAttribute("vRpDySiteHo", vRpDySiteHo);
			
		}catch(ParseException e) {
			saveError(request, e.toString());
		}
		return new ModelAndView("dySiteHoList");
	}
	
	@RequestMapping("/report/radio/site-ho/dy/details")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required=true) String bscid, @RequestParam(required = true) String siteid,
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
			
			model.addAttribute("region", region);
			
			List<VRpDySiteHo> vRpDySiteHoDetails = vRpDySiteHoDao.filterDetails(df.format(startDay), df.format(endDay), bscid, siteid, region);
			
			model.addAttribute("vRpDySiteHoDetails", vRpDySiteHoDetails);
			model.addAttribute("siteid", siteid);
			model.addAttribute("bscid", bscid);
			
			/* Chart */
			List<String> categories = new ArrayList<String>();
			List<Float> inHoSucrList = new ArrayList<Float>();
			List<Float> ogHoSucrList = new ArrayList<Float>();
			
			for (VRpDySiteHo vRpDySiteHo : vRpDySiteHoDetails) {
				categories.add(df.format(vRpDySiteHo.getDay()));
				inHoSucrList.add(vRpDySiteHo.getInHoSucr());
				ogHoSucrList.add(vRpDySiteHo.getOgHoSucr());
			}
			Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
			series.put("In_Ho_Sucr", inHoSucrList);
			series.put("Og_Ho_Sucr", ogHoSucrList);

			model.addAttribute("chart", Chart.basicLine("SITE HO " + siteid + " Daily Report", categories, series));

			// checkBox
			String[] checkColumns = { "ogHoAtt", "ogHoSuc", "ogHoSucr", "inHoAtt", "inHoSuc", "inHoSucr"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 7));

			String[] checkSeries = {"ogHoSucr", "inHoSucr"};
			model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		}catch (ParseException e) {
		saveError(request, e.toString());
		}
		
	return "dySiteHoDetails";
	}
	
	@RequestMapping("/report/radio/site-ho/dy/bhDetails")
	public String showReportBh(@RequestParam(required = false) String region,@RequestParam(required=true) String bscid, @RequestParam(required = true) String siteid,
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
			
			model.addAttribute("region", region);
			
			List<VRpDySiteHoBh> vRpDySiteHoBhDetails = vRpDySiteHoBhDao.filterDetails(df.format(startDay), df.format(endDay), bscid, siteid, region);
			
			model.addAttribute("vRpDySiteHoBhDetails", vRpDySiteHoBhDetails);
			model.addAttribute("siteid", siteid);
			model.addAttribute("bscid", bscid);
			
			/* Chart */
			List<String> categories = new ArrayList<String>();
			List<Float> inHoSucrList = new ArrayList<Float>();
			List<Float> ogHoSucrList = new ArrayList<Float>();
			
			for (VRpDySiteHoBh vRpDySiteHoBh : vRpDySiteHoBhDetails) {
				categories.add(df.format(vRpDySiteHoBh.getDay()));
				inHoSucrList.add(vRpDySiteHoBh.getBhInHoSucr());
				ogHoSucrList.add(vRpDySiteHoBh.getBhOgHoSucr());
			}
			Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
			series.put("Bh_In_Ho_Sucr", inHoSucrList);
			series.put("Bh_Og_Ho_Sucr", ogHoSucrList);

			model.addAttribute("chart", Chart.basicLine("SITE HO BH " + siteid + " Daily Report", categories, series));

			// checkBox
			String[] checkColumns = { "bhOgHoAtt","bhOgHoSuc","bhOgHoSucr","bhInHoAtt","bhInHoSuc","bhInHoSucr"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 8));

			String[] checkSeries = {"bhOgHoSucr", "bhInHoSucr"};
			model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
			
		}catch (ParseException e) {
		saveError(request, e.toString());
		}
		
	return "dySiteHoBhDetails";
	}
}
