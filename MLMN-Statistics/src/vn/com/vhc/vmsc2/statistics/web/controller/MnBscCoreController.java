package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.BscDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyBscCoreDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnBscCoreDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnBscCssrQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnBscDcrsQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnBscDcrtQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnBscHsrQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnBscPsrQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnBscTrapcomQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnBscTrapeventQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnHBscCpuQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnHBscHoQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnHBscPchQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnHBscSdcchQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnHBscTchQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.DyBscCore;
import vn.com.vhc.vmsc2.statistics.domain.MnBscCore;
import vn.com.vhc.vmsc2.statistics.domain.MnBscCssrQos;
import vn.com.vhc.vmsc2.statistics.domain.MnBscDcrsQos;
import vn.com.vhc.vmsc2.statistics.domain.MnBscDcrtQos;
import vn.com.vhc.vmsc2.statistics.domain.MnBscHsrQos;
import vn.com.vhc.vmsc2.statistics.domain.MnBscPsrQos;
import vn.com.vhc.vmsc2.statistics.domain.MnBscTrapcomQos;
import vn.com.vhc.vmsc2.statistics.domain.MnBscTrapeventQos;
import vn.com.vhc.vmsc2.statistics.domain.MnHBscCpuQos;
import vn.com.vhc.vmsc2.statistics.domain.MnHBscHoQos;
import vn.com.vhc.vmsc2.statistics.domain.MnHBscPchQos;
import vn.com.vhc.vmsc2.statistics.domain.MnHBscSdcchQos;
import vn.com.vhc.vmsc2.statistics.domain.MnHBscTchQos;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;


@Controller
@RequestMapping("/report/core/bsc/mn/*")
public class MnBscCoreController extends BaseController {
	@Autowired
	private BscDAO bscDao;
	@Autowired
	private DyBscCoreDAO dyBscCoreDao;
	@Autowired
	private MnBscCoreDAO mnBscCoreDao;
	@Autowired
	private MnBscCssrQosDAO mnBscCssrQosDao;
	@Autowired
	private MnBscDcrtQosDAO mnBscDcrtQosDao;
	@Autowired
	private MnBscDcrsQosDAO mnBscDcrsQosDao;
	@Autowired
	private MnBscPsrQosDAO mnBscPsrQosDao;
	@Autowired
	private MnBscHsrQosDAO mnBscHsrQosDao;
	@Autowired
	private MnBscTrapcomQosDAO mnBscTrapcomQosDao;
	@Autowired
	private MnBscTrapeventQosDAO mnBscTrapeventQosDao;
	@Autowired
	private MnHBscCpuQosDAO mnHBscCpuQosDao;
	@Autowired
	private MnHBscHoQosDAO mnHBscHoQosDao;
	@Autowired
	private MnHBscPchQosDAO mnHBscPchQosDao;
	@Autowired
	private MnHBscSdcchQosDAO mnHBscSdcchQosDao;
	@Autowired
	private MnHBscTchQosDAO mnHBscTchQosDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("main")
	public String mainReport(@RequestParam(required = true) String bscid, @RequestParam(required = false) Integer month,
			@RequestParam(required = false) Integer year, @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
			ModelMap model) {

		Bsc bsc = bscDao.selectByPrimaryKey(bscid);
		model.addAttribute("bsc", bsc);

		Calendar calendar = Calendar.getInstance();

		if (month == null) {
			calendar.add(Calendar.MONTH,-1);
			month = calendar.get(Calendar.MONTH)+1;
			year = calendar.get(Calendar.YEAR);
		}

		calendar.clear();
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.YEAR, year);

		startDate = calendar.getTime();
		int lastDate = calendar.getActualMaximum(Calendar.DATE);
		calendar.set(Calendar.DATE, lastDate);
		endDate = calendar.getTime();

		model.addAttribute("bscid", bscid);
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));

		MnBscCore mnBscCore = mnBscCoreDao.selectByPrimaryKey(bscid, month, year);
		model.addAttribute("mnBscCore", mnBscCore);

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

		model.addAttribute("chart", Chart.comboDualAxes("BSC " + bscid + " Monthly Report", categories, leftSeries, rightSeries));
		model.addAttribute("dyBscCoreList", dyBscCoreList);

		return "mnBscCore";
	}

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = true) String bscid, @RequestParam(required = false) Integer year,
			@RequestParam(required = false) Integer month, ModelMap model, HttpServletRequest request) {

		Bsc bsc = bscDao.selectByPrimaryKey(bscid);
		model.addAttribute("bsc", bsc);

		Calendar now = Calendar.getInstance();

		if (month == null){
			now.add(Calendar.MONTH,-1);
			month = now.get(Calendar.MONTH)+1;
		}

		if (year == null)
			year = now.get(Calendar.YEAR);

		model.addAttribute("month", month);
		model.addAttribute("year", year);

		if (bscid.endsWith("H")) {
			MnBscCore mnBscCore = mnBscCoreDao.selectByPrimaryKey(bscid, month, year);
			MnHBscCpuQos mnHBscCpuQos = mnHBscCpuQosDao.selectByPrimaryKey(bscid, month, year);
			MnHBscHoQos mnHBscHoQos = mnHBscHoQosDao.selectByPrimaryKey(bscid, month, year);
			MnHBscPchQos mnHBscPchQos = mnHBscPchQosDao.selectByPrimaryKey(bscid, month, year);
			MnHBscSdcchQos mnHBscSdcchQos = mnHBscSdcchQosDao.selectByPrimaryKey(bscid, month, year);
			MnHBscTchQos mnHBscTchQos = mnHBscTchQosDao.selectByPrimaryKey(bscid, month, year);

			model.addAttribute("mnBscCore", mnBscCore);
			model.addAttribute("mnHBscCpuQos", mnHBscCpuQos);
			model.addAttribute("mnHBscHoQos", mnHBscHoQos);
			model.addAttribute("mnHBscPchQos", mnHBscPchQos);
			model.addAttribute("mnHBscSdcchQos", mnHBscSdcchQos);
			model.addAttribute("mnHBscTchQos", mnHBscTchQos);
			return "mnBscCoreDetailH";
		} else {
			MnBscCore mnBscCore = mnBscCoreDao.selectByPrimaryKey(bscid, month, year);
			MnBscCssrQos mnBscCssrQos = mnBscCssrQosDao.selectByPrimaryKey(bscid, month, year);
			MnBscDcrtQos mnBscDcrtQos = mnBscDcrtQosDao.selectByPrimaryKey(bscid, month, year);
			MnBscPsrQos mnBscPsrQos = mnBscPsrQosDao.selectByPrimaryKey(bscid, month, year);
			MnBscHsrQos mnBscHsrQos = mnBscHsrQosDao.selectByPrimaryKey(bscid, month, year);
			MnBscDcrsQos mnBscDcrsQos = mnBscDcrsQosDao.selectByPrimaryKey(bscid, month, year);
			MnBscTrapcomQos mnBscTrapcomQos = mnBscTrapcomQosDao.selectByPrimaryKey(bscid, month, year);
			List<MnBscTrapeventQos> mnBscTrapeventQosList = mnBscTrapeventQosDao.select(bscid, month, year);

			model.addAttribute("mnBscCore", mnBscCore);
			model.addAttribute("mnBscCssrQos", mnBscCssrQos);
			model.addAttribute("mnBscDcrtQos", mnBscDcrtQos);
			model.addAttribute("mnBscDcrsQos", mnBscDcrsQos);
			model.addAttribute("mnBscPsrQos", mnBscPsrQos);
			model.addAttribute("mnBscHsrQos", mnBscHsrQos);
			model.addAttribute("mnBscTrapcomQos", mnBscTrapcomQos);
			model.addAttribute("mnBscTrapeventQos", mnBscTrapeventQosList);
			return "mnBscCoreDetail";
		}
	}
}
