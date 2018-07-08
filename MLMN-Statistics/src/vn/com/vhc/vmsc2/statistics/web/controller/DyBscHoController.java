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
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyBscHoBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyBscHoDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBscHo;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyBscHoBh;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
public class DyBscHoController extends BaseController{
	@Autowired
	private VRpDyBscHoDAO vRpDyBscHoDao;
	@Autowired
	private VRpDyBscHoBhDAO vRpDyBscHoBhDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDao;
	@Autowired
	private BscDAO bscDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping("/report/radio/bsc-ho/dy/list")
	public ModelAndView showReportList(@RequestParam(required = false) String region,@RequestParam(required=false) String bscid,
			@RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate, ModelMap model, HttpServletRequest request) {
			long currentTime = System.currentTimeMillis();
		
			if (endDate == null) {
				endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
			}
			if (startDate == null) {
				startDate = endDate;
			}
			model.addAttribute("startDate", df.format(startDate));
			model.addAttribute("endDate", df.format(endDate));
			
			List<HProvincesCode> regionList = hProvincesCodeDao.getAllRegion();
			model.addAttribute("regionList", regionList);
			
			if (region == null)
				region = "";
			
			List<Bsc> bscList = bscDao.getAll();
			model.addAttribute("bscList", bscList);
			
			List<VRpDyBscHo> vRpDyBscHo = vRpDyBscHoDao.filterDetails(df.format(startDate), df.format(endDate), bscid, region);

			model.addAttribute("bscid", bscid);
			model.addAttribute("region", region);
			
			model.addAttribute("vRpDyBscHo", vRpDyBscHo);
			model.addAttribute("region", region);

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyBscHo"));
		
		return new ModelAndView("dyBscHoList");
	}
	
	@RequestMapping("/report/radio/bsc-ho/dy/details")
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
			
			List<VRpDyBscHo> vRpDyBscHoDetails = vRpDyBscHoDao.filterDetails(df.format(startDay), df.format(endDay), bscid, region);
			
			model.addAttribute("vRpDyBscHoDetails", vRpDyBscHoDetails);
			model.addAttribute("bscid", bscid);
			model.addAttribute("region", region);
			
			/* Chart */
			List<String> categories = new ArrayList<String>();
			List<Float> inHoSucrList = new ArrayList<Float>();
			List<Float> ogHoSucrList = new ArrayList<Float>();
			
			for (VRpDyBscHo vRpDyBscHo : vRpDyBscHoDetails) {
				categories.add(df.format(vRpDyBscHo.getDay()));
				inHoSucrList.add(vRpDyBscHo.getInHoSucr());
				ogHoSucrList.add(vRpDyBscHo.getOgHoSucr());
			}
			Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
			series.put("In_Ho_Sucr", inHoSucrList);
			series.put("Og_Ho_Sucr", ogHoSucrList);

			model.addAttribute("chart", Chart.basicLine("BSC HO " + bscid + " Daily Report", categories, series));

			// checkBox
			String[] checkColumns = { "ogHoAtt", "ogHoSuc", "ogHoSucr", "inHoAtt", "inHoSuc", "inHoSucr"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 7));

			String[] checkSeries = {"ogHoSucr", "inHoSucr"};
			model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
			
		}catch (ParseException e) {
		saveError(request, e.toString());
		}

		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpDyBscHoDetail"));
		
	return "dyBscHoDetails";
	}
	
	@RequestMapping("/report/radio/bsc-ho/dy/bhDetails")
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
			
			List<VRpDyBscHoBh> vRpDyBscHoBhDetails = vRpDyBscHoBhDao.filterDetails(df.format(startDay), df.format(endDay), bscid, region);
			
			model.addAttribute("vRpDyBscHoBhDetails", vRpDyBscHoBhDetails);
			model.addAttribute("bscid", bscid);
			model.addAttribute("region", region);
			
			/* Chart */
			List<String> categories = new ArrayList<String>();
			List<Float> inHoSucrList = new ArrayList<Float>();
			List<Float> ogHoSucrList = new ArrayList<Float>();
			
			for (VRpDyBscHoBh vRpDyBscHoBh : vRpDyBscHoBhDetails) {
				categories.add(df.format(vRpDyBscHoBh.getDay()));
				inHoSucrList.add(vRpDyBscHoBh.getBhInHoSucr());
				ogHoSucrList.add(vRpDyBscHoBh.getBhOgHoSucr());
			}
			Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
			series.put("Bh_In_Ho_Sucr", inHoSucrList);
			series.put("Bh_Og_Ho_Sucr", ogHoSucrList);

			model.addAttribute("chart", Chart.basicLine("BSC HO BH " + bscid + " Daily Report", categories, series));

			// checkBox
			String[] checkColumns = { "bhOgHoAtt","bhOgHoSuc","bhOgHoSucr","bhInHoAtt","bhInHoSuc","bhInHoSucr"};
			model.addAttribute("checkColumns", Helper.checkColumns(checkColumns, 5));

			String[] checkSeries = {"bhOgHoSucr", "bhInHoSucr"};
			model.addAttribute("checkSeries", Chart.checkSeries(checkSeries));
		}catch (ParseException e) {
		saveError(request, e.toString());
		}
		
	return "dyBscHoBhDetails";
	}
}
