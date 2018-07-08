package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyBscCoreDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkBscCoreDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkBscCssrQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkBscDcrsQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkBscDcrtQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkBscHsrQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkBscPsrQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkBscTrapcomQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkBscTrapeventQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkHBscCpuQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkHBscHoQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkHBscPchQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkHBscSdcchQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkHBscTchQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.DyBscCore;
import vn.com.vhc.vmsc2.statistics.domain.WkBscCore;
import vn.com.vhc.vmsc2.statistics.domain.WkBscCssrQos;
import vn.com.vhc.vmsc2.statistics.domain.WkBscDcrsQos;
import vn.com.vhc.vmsc2.statistics.domain.WkBscDcrtQos;
import vn.com.vhc.vmsc2.statistics.domain.WkBscHsrQos;
import vn.com.vhc.vmsc2.statistics.domain.WkBscPsrQos;
import vn.com.vhc.vmsc2.statistics.domain.WkBscTrapcomQos;
import vn.com.vhc.vmsc2.statistics.domain.WkBscTrapeventQos;
import vn.com.vhc.vmsc2.statistics.domain.WkHBscCpuQos;
import vn.com.vhc.vmsc2.statistics.domain.WkHBscHoQos;
import vn.com.vhc.vmsc2.statistics.domain.WkHBscPchQos;
import vn.com.vhc.vmsc2.statistics.domain.WkHBscSdcchQos;
import vn.com.vhc.vmsc2.statistics.domain.WkHBscTchQos;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;


@Controller
@RequestMapping("/report/core/bsc/wk/*")
public class WkBscCoreController extends BaseController {
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private DyBscCoreDAO dyBscCoreDao;
	@Autowired
	private WkBscCoreDAO wkBscCoreDao;
	@Autowired
	private WkBscCssrQosDAO wkBscCssrQosDao;
	@Autowired
	private WkBscDcrtQosDAO wkBscDcrtQosDao;
	@Autowired
	private WkBscDcrsQosDAO wkBscDcrsQosDao;
	@Autowired
	private WkBscPsrQosDAO wkBscPsrQosDao;
	@Autowired
	private WkBscHsrQosDAO wkBscHsrQosDao;
	@Autowired
	private WkBscTrapcomQosDAO wkBscTrapcomQosDao;
	@Autowired
	private WkBscTrapeventQosDAO wkBscTrapeventQosDao;
	@Autowired
	private WkHBscCpuQosDAO wkHBscCpuQosDao;
	@Autowired
	private WkHBscHoQosDAO wkHBscHoQosDao;
	@Autowired
	private WkHBscPchQosDAO wkHBscPchQosDao;
	@Autowired
	private WkHBscSdcchQosDAO wkHBscSdcchQosDao;
	@Autowired
	private WkHBscTchQosDAO wkHBscTchQosDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("main")
	public String mainReport(@RequestParam(required = true) String bscid, @RequestParam(required = false) Integer week,
			@RequestParam(required = false) Integer year, @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
			ModelMap model) {

		Bsc bsc = bscDao.selectByPrimaryKey(bscid);
		model.addAttribute("bsc", bsc);

		DateTime dt = new DateTime();
		dt = dt.minusWeeks(1);

		if (week == null) {
			week = dt.getWeekOfWeekyear();
		}
		
		if(year == null)
			year = dt.getYear();

		startDate = dt.withDayOfWeek(1).toDate();
		endDate = dt.withDayOfWeek(7).toDate();

		model.addAttribute("bscid", bscid);
		model.addAttribute("week", week);
		model.addAttribute("year", year);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));

		WkBscCore wkBscCore = wkBscCoreDao.selectByPrimaryKey(bscid, week, year);
		model.addAttribute("wkBscCore", wkBscCore);

		// chart
		List<DyBscCore> dyBscCoreList = dyBscCoreDao.filter(bscid, df.format(startDate), df.format(endDate));
		List<String> categories = new ArrayList<String>();
		List<Float> cssrList = new ArrayList<Float>();
		List<Float> psrList = new ArrayList<Float>();
		List<Float> dcrsList = new ArrayList<Float>();
		List<Float> dcrtList = new ArrayList<Float>();
		List<Float> traucrList = new ArrayList<Float>();
		List<Float> hsrList = new ArrayList<Float>();
		for (DyBscCore bscCore : dyBscCoreList) {
			categories.add(df.format(bscCore.getDay()));
			cssrList.add(bscCore.getCssr());
			psrList.add(bscCore.getPsr());
			dcrsList.add(bscCore.getDcrs());
			dcrtList.add(bscCore.getDcrt());
			traucrList.add(bscCore.getTraucr());
			hsrList.add(bscCore.getHsr());
		}
		Map<String, List<Float>> leftSeries = new HashMap<String, List<Float>>();
		leftSeries.put("cssr", cssrList);
		leftSeries.put("psr", psrList);
		leftSeries.put("hsr", hsrList);
		
		Map<String, List<Float>> rightSeries = new HashMap<String, List<Float>>();
		rightSeries.put("dcrs", dcrsList);
		rightSeries.put("dcrt", dcrtList);
		rightSeries.put("traucr", traucrList);

		model.addAttribute("chart", Chart.comboDualAxes("BSC " + bscid + " Weekly Report", categories, leftSeries, rightSeries));
		model.addAttribute("dyBscCoreList", dyBscCoreList);

		return "wkBscCore";
	}

	@RequestMapping("detail")
	public String detailReport(@RequestParam(required = true) String bscid, @RequestParam(required = false) Integer year,
			@RequestParam(required = false) Integer week, ModelMap model, HttpServletRequest request) {

		Bsc bsc = bscDao.selectByPrimaryKey(bscid);
		model.addAttribute("bsc", bsc);

		DateTime dt = new DateTime();
		dt = dt.minusWeeks(1);

		if (week == null) {
			week = dt.getWeekOfWeekyear();
		}
		
		if(year == null)
			year = dt.getYear();

		model.addAttribute("week", week);
		model.addAttribute("year", year);

		if (bscid.endsWith("H")) {
			WkBscCore wkBscCore = wkBscCoreDao.selectByPrimaryKey(bscid, week, year);
			WkHBscCpuQos wkHBscCpuQos = wkHBscCpuQosDao.selectByPrimaryKey(bscid, week, year);
			WkHBscHoQos wkHBscHoQos = wkHBscHoQosDao.selectByPrimaryKey(bscid, week, year);
			WkHBscPchQos wkHBscPchQos = wkHBscPchQosDao.selectByPrimaryKey(bscid, week, year);
			WkHBscSdcchQos wkHBscSdcchQos = wkHBscSdcchQosDao.selectByPrimaryKey(bscid, week, year);
			WkHBscTchQos wkHBscTchQos = wkHBscTchQosDao.selectByPrimaryKey(bscid, week, year);

			model.addAttribute("wkBscCore", wkBscCore);
			model.addAttribute("wkHBscCpuQos", wkHBscCpuQos);
			model.addAttribute("wkHBscHoQos", wkHBscHoQos);
			model.addAttribute("wkHBscPchQos", wkHBscPchQos);
			model.addAttribute("wkHBscSdcchQos", wkHBscSdcchQos);
			model.addAttribute("wkHBscTchQos", wkHBscTchQos);
			return "wkBscCoreDetailH";
		} else {
			WkBscCore wkBscCore = wkBscCoreDao.selectByPrimaryKey(bscid, week, year);
			WkBscCssrQos wkBscCssrQos = wkBscCssrQosDao.selectByPrimaryKey(bscid, week, year);
			WkBscDcrtQos wkBscDcrtQos = wkBscDcrtQosDao.selectByPrimaryKey(bscid, week, year);
			WkBscPsrQos wkBscPsrQos = wkBscPsrQosDao.selectByPrimaryKey(bscid, week, year);
			WkBscHsrQos wkBscHsrQos = wkBscHsrQosDao.selectByPrimaryKey(bscid, week, year);
			WkBscDcrsQos wkBscDcrsQos = wkBscDcrsQosDao.selectByPrimaryKey(bscid, week, year);
			WkBscTrapcomQos wkBscTrapcomQos = wkBscTrapcomQosDao.selectByPrimaryKey(bscid, week, year);
			List<WkBscTrapeventQos> wkBscTrapeventQosList = wkBscTrapeventQosDao.select(bscid, week, year);

			model.addAttribute("wkBscCore", wkBscCore);
			model.addAttribute("wkBscCssrQos", wkBscCssrQos);
			model.addAttribute("wkBscDcrtQos", wkBscDcrtQos);
			model.addAttribute("wkBscDcrsQos", wkBscDcrsQos);
			model.addAttribute("wkBscPsrQos", wkBscPsrQos);
			model.addAttribute("wkBscHsrQos", wkBscHsrQos);
			model.addAttribute("wkBscTrapcomQos", wkBscTrapcomQos);
			model.addAttribute("wkBscTrapeventQos", wkBscTrapeventQosList);
			return "wkBscCoreDetail";
		}
	}
}
