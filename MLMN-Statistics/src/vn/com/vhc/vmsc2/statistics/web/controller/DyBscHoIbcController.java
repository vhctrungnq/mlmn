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

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.HProvincesCodeDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyBscHoBhIbcDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyBscHoIbcDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBscHoBhIbc;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBscHoIbc;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
public class DyBscHoIbcController extends BaseController{
	@Autowired
	private VRpDyBscHoIbcDAO vRpDyBscHoIbcDao;
	@Autowired
	private VRpDyBscHoBhIbcDAO vRpDyBscHoBhIbcDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private BscDAO bscDao;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping("/report/radio/ibc/bsc-ho/dy/list")
	public ModelAndView dyBscHoList(@RequestParam(required = false) String region,@RequestParam(required = false) String bscid, @RequestParam(required = false) String day, ModelMap model,
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
			List<Bsc> bscList = bscDao.getAll();
			model.addAttribute("bscList", bscList);
			if (region == null)
				region = "";
			
			List<VRpDyBscHoIbc> vRpDyBscHoIbc = vRpDyBscHoIbcDao.filter(bscid, d, region);
			
			model.addAttribute("day", df.format(d));
			model.addAttribute("vRpDyBscHoIbc", vRpDyBscHoIbc);
			model.addAttribute("region", region);
			
		}catch(ParseException e) {
			saveError(request, e.toString());
		}
		return new ModelAndView("dyBscHoIbcList");
	}
	
	@RequestMapping("/report/radio/ibc/bsc-ho/dy/details")
	public String showReport(@RequestParam(required = false) String region,@RequestParam(required=true) String bscid,
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
			List<Bsc> bscList = bscDao.getAll();
			model.addAttribute("bscList", bscList);
			if (region == null)
				region = "";
			
			List<VRpDyBscHoIbc> vRpDyBscHoIbcDetails = vRpDyBscHoIbcDao.filterDetails(df.format(startDay), df.format(endDay), bscid, region);
			
			model.addAttribute("vRpDyBscHoIbcDetails", vRpDyBscHoIbcDetails);
			model.addAttribute("bscid", bscid);
			model.addAttribute("region", region);
			
			/* Chart */
			List<String> categories = new ArrayList<String>();
			List<Float> inHoSucrList = new ArrayList<Float>();
			List<Float> ogHoSucrList = new ArrayList<Float>();
			
			for (VRpDyBscHoIbc vRpDyBscHoIbc : vRpDyBscHoIbcDetails) {
				categories.add(df.format(vRpDyBscHoIbc.getDay()));
				inHoSucrList.add(vRpDyBscHoIbc.getInHoSucr());
				ogHoSucrList.add(vRpDyBscHoIbc.getOgHoSucr());
			}
			Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
			series.put("In_Ho_Sucr", inHoSucrList);
			series.put("Og_Ho_Sucr", ogHoSucrList);

			model.addAttribute("chart", Chart.basicLine("BSC IBC HO " + bscid + " Daily Report", categories, series));

			// checkBox
			String[] checkColumns = { "ogHoAtt", "ogHoSuc", "ogHoSucr", "inHoAtt", "inHoSuc", "inHoSucr"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 4));

			String[] checkSeries = {"ogHoSucr", "inHoSucr"};
			model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
			
		}catch (ParseException e) {
		saveError(request, e.toString());
		}
		
	return "dyBscHoIbcDetails";
	}
	
	@RequestMapping("/report/radio/ibc/bsc-ho/dy/bhDetails")
	public String showReportBh(@RequestParam(required = false) String region,@RequestParam(required=true) String bscid,
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
			List<Bsc> bscList = bscDao.getAll();
			model.addAttribute("bscList", bscList);
			if (region == null)
				region = "";
			
			List<VRpDyBscHoBhIbc> vRpDyBscHoBhIbcDetails = vRpDyBscHoBhIbcDao.filterDetails(df.format(startDay), df.format(endDay), bscid, region);
			
			model.addAttribute("vRpDyBscHoBhIbcDetails", vRpDyBscHoBhIbcDetails);
			model.addAttribute("bscid", bscid);
			model.addAttribute("region", region);
			
			/* Chart */
			List<String> categories = new ArrayList<String>();
			List<Float> inHoSucrList = new ArrayList<Float>();
			List<Float> ogHoSucrList = new ArrayList<Float>();
			
			for (VRpDyBscHoBhIbc vRpDyBscHoBhIbc : vRpDyBscHoBhIbcDetails) {
				categories.add(df.format(vRpDyBscHoBhIbc.getDay()));
				inHoSucrList.add(vRpDyBscHoBhIbc.getBhInHoSucr());
				ogHoSucrList.add(vRpDyBscHoBhIbc.getBhOgHoSucr());
			}
			Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
			series.put("Bh_In_Ho_Sucr", inHoSucrList);
			series.put("Bh_Og_Ho_Sucr", ogHoSucrList);

			model.addAttribute("chart", Chart.basicLine("BSC IBC HO BH " + bscid + " Daily Report", categories, series));

			// checkBox
			String[] checkColumns = { "bhOgHoAtt","bhOgHoSuc","bhOgHoSucr","bhInHoAtt","bhInHoSuc","bhInHoSucr"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 6));

			String[] checkSeries = {"bhOgHoSucr", "bhInHoSucr"};
			model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		}catch (ParseException e) {
		saveError(request, e.toString());
		}
		
	return "dyBscHoBhIbcDetails";
	}
}
