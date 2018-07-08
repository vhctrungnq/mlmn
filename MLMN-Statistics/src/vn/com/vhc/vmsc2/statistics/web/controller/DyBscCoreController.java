package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyBscCoreDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrBscCoreDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.DyBscCore;
import vn.com.vhc.vmsc2.statistics.domain.HrBscCore;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;


@Controller
public class DyBscCoreController extends BaseController {
	@Autowired
	private HrBscCoreDAO hrBscCoreDao;
	@Autowired
	private DyBscCoreDAO dyBscCoreDao;
	@Autowired
	private BscDAO bscDao;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat dff = new SimpleDateFormat("dd/MM");

	@RequestMapping("/report/core/bsc/dy/list")
	public ModelAndView dyListQos(@RequestParam(required = false) String bscid, @RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate, ModelMap model) {
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		
		List<DyBscCore> dyBscCore = dyBscCoreDao.filter(bscid, df.format(startDate), df.format(endDate));
		model.addAttribute("bscid", bscid);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("dyBscCore", dyBscCore);

		return new ModelAndView("dyBscCoreList");
	}

	@RequestMapping("/report/core/bsc/dy/main")
	public String showReport(@RequestParam(required = true) String bscid, @RequestParam(required = false) Date day,
			@RequestParam(required = false) Integer startHour, @RequestParam(required = false) Integer endHour, ModelMap model) {

		long currentTime = System.currentTimeMillis();

		if (day == null) {
			day = new Date(currentTime - 24 * 60 * 60 * 1000);
		}

		if (startHour == null) {
			startHour = 0;
		}

		if (endHour == null) {
			endHour = 23;
		}

		model.addAttribute("bscid", bscid);
		model.addAttribute("day", df.format(day));
		model.addAttribute("startHour", startHour);
		model.addAttribute("endHour", endHour);

		DyBscCore dyBscCore = dyBscCoreDao.selectByPrimaryKey(bscid, day);
		model.addAttribute("dyBscCore", dyBscCore);

		// chart
		List<HrBscCore> hrBscCoreList = hrBscCoreDao.filter(bscid, df.format(day), startHour, endHour);
		List<String> categories = new ArrayList<String>();
		List<Float> cssrList = new ArrayList<Float>();
		List<Float> psrList = new ArrayList<Float>();
		List<Float> dcrsList = new ArrayList<Float>();
		List<Float> dcrtList = new ArrayList<Float>();
		List<Float> traucrList = new ArrayList<Float>();
		List<Float> hsrList = new ArrayList<Float>();
		for (HrBscCore bscCore : hrBscCoreList) {
			categories.add(bscCore.getHour() + ":00 " + dff.format(bscCore.getDay()));
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
		
		List<Bsc> bscList = bscDao.getAll();
		model.addAttribute("bscList", bscList);
		Map<String, List<Float>> rightSeries = new HashMap<String, List<Float>>();
		rightSeries.put("dcrs", dcrsList);
		rightSeries.put("dcrt", dcrtList);
		rightSeries.put("traucr", traucrList);

		model.addAttribute("chart", Chart.comboDualAxes("BSC " + bscid + " Daily Report", categories, leftSeries, rightSeries));
		model.addAttribute("hrBscCoreList", hrBscCoreList);

		return "dyBscCore";
	}
}
