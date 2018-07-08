package vn.com.vhc.vmsc2.statistics.web.controller;

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

import vn.com.vhc.vmsc2.statistics.dao.WkBscCoreDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkMscQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkRouteCoreDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkRouteCoreReportDAO;
import vn.com.vhc.vmsc2.statistics.domain.WkBscCore;
import vn.com.vhc.vmsc2.statistics.domain.WkMscQos;
import vn.com.vhc.vmsc2.statistics.domain.WkRouteCore;
import vn.com.vhc.vmsc2.statistics.domain.WkRouteCoreReport;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
@RequestMapping("/report/core/route/wk/*")
public class WkRouteCoreReportController extends BaseController {
	@Autowired
	private WkBscCoreDAO wkBscCoreDao;
	@Autowired
	private WkMscQosDAO wkMscQosDao;
	@Autowired
	private WkRouteCoreDAO wkRouteCoreDao;
	@Autowired
	private WkRouteCoreReportDAO wkRouteCoreReportDao;

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = true) String fromNode, @RequestParam(required = true) String toNode,
			@RequestParam(required = false) String week,@RequestParam(required = false) String year, ModelMap model, HttpServletRequest request) {

		Calendar now = Calendar.getInstance();
		now.setFirstDayOfWeek(Calendar.MONDAY);
		
		if (week == null)
			week = String.valueOf(now.get(Calendar.WEEK_OF_YEAR) - 2);
		
		if (year == null)
			year = String.valueOf(now.get(Calendar.YEAR));

			Map<String, String> fromNodeMap = null;
			Map<String, String> toNodeMap = null;

			if (isMsc(fromNode)) {
				fromNodeMap = mscNodeMap(week,year, fromNode);

			} else if (isBsc(fromNode)) {
				fromNodeMap = bscNodeMap(week,year, fromNode);
			}

			if (isMsc(toNode)) {
				toNodeMap = mscNodeMap(week,year, toNode);
			} else if (isBsc(toNode)) {
				toNodeMap = bscNodeMap(week, year, toNode);
			}

			List<WkRouteCore> wkRouteCores = wkRouteCoreDao.filter(week,year, fromNode, toNode);
			WkRouteCoreReport wkRouteCoreReport = wkRouteCoreReportDao.selectByPrimaryKey(fromNode, toNode, Integer.parseInt(week), Integer.parseInt(year));
			model.addAttribute("wkRouteCoreReport", wkRouteCoreReport);
			model.addAttribute("week", week);
			model.addAttribute("year", year);
			model.addAttribute("fromNode", fromNode);
			model.addAttribute("toNode", toNode);

			model.addAttribute("fromNodeMap", fromNodeMap);
			model.addAttribute("toNodeMap", toNodeMap);
			model.addAttribute("wkRouteCores", wkRouteCores);

		return "wkRouteCoreReportDetailList";
	}

	private boolean isMsc(String node) {
		return node.charAt(0) == 'M';
	}

	private boolean isBsc(String node) {
		return node.charAt(0) == 'B';
	}

	private Map<String, String> mscNodeMap(String week,String year, String node) {
		Map<String, String> mscNodeMap = new LinkedHashMap<String, String>();

		WkMscQos wkMscQos = wkMscQosDao.selectByPrimaryKey(node, Integer.parseInt(week),Integer.parseInt( year));

		if (wkMscQos == null)
			wkMscQos = new WkMscQos();

		mscNodeMap.put("%", "N/A");
		mscNodeMap.put("MCSSR (%)",
				"<a href=\"/VMSC2-Statistics/report/core/msc/wk.htm?mscid=" + node + "&week=" + Integer.parseInt(week) +"&year=" + Integer.parseInt(year) + "#mcssr\">" + Helper.float2String(wkMscQos.getMcssr()) + "%</a>");
		mscNodeMap.put("LUSR (%)", "<a href=\"/VMSC2-Statistics/report/core/msc/wk.htm?mscid=" + node + "&week=" + Integer.parseInt(week) +"&year=" + Integer.parseInt(year) + "#lusr\">" + Helper.float2String(wkMscQos.getLusr())
				+ "%</a>");
		mscNodeMap.put("PSR (%)", "<a href=\"/VMSC2-Statistics/report/core/msc/wk.htm?mscid=" + node + "&week=" + Integer.parseInt(week) +"&year=" + Integer.parseInt(year)  + "#psr\">" + Helper.float2String(wkMscQos.getPsr())
				+ "%</a>");
		mscNodeMap.put("HSR (%)", "<a href=\"/VMSC2-Statistics/report/core/msc/wk.htm?mscid=" + node + "&week=" + Integer.parseInt(week) +"&year=" + Integer.parseInt(year)  +"#hsr\">" + Helper.float2String(wkMscQos.getHsr())
				+ "%</a>");
		mscNodeMap.put("SMSSR (%)",
				"<a href=\"/VMSC2-Statistics/report/core/msc/wk.htm?mscid=" + node + "&week=" + Integer.parseInt(week) +"&year=" + Integer.parseInt(year)  + "#smssr\">" + Helper.float2String(wkMscQos.getSmssr()) + "%</a>");
		mscNodeMap.put("AUSR (%)", "<a href=\"/VMSC2-Statistics/report/core/msc/wk.htm?mscid=" + node + "&week=" + Integer.parseInt(week) +"&year=" + Integer.parseInt(year)  +"#ausr\">" + Helper.float2String(wkMscQos.getAusr())
				+ "%</a>");
		mscNodeMap.put("ECCR (%)", "N/A");

		return mscNodeMap;
	}

	private Map<String, String> bscNodeMap(String week,String year, String node) {
		Map<String, String> bscNodeMap = new LinkedHashMap<String, String>();

		WkBscCore wkBscCore = wkBscCoreDao.selectByPrimaryKey(node, Integer.parseInt(week), Integer.parseInt(year));

		if (wkBscCore == null)
			wkBscCore = new WkBscCore();

		bscNodeMap.put("%", "N/A");
		bscNodeMap.put("CSSR (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/wk.htm?bscid=" + node + "&week=" + Integer.parseInt(week) +"&year=" + Integer.parseInt(year)  +"#cssr\">" + Helper.float2String(wkBscCore.getCssr())
				+ "%</a>");
		bscNodeMap.put("PSR (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/wk.htm?bscid=" + node +"&week=" + Integer.parseInt(week) +"&year=" + Integer.parseInt(year)  + "#psr\">" + Helper.float2String(wkBscCore.getPsr())
				+ "%</a>");
		bscNodeMap.put("DCRS (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/wk.htm?bscid=" + node + "&week=" + Integer.parseInt(week) +"&year=" + Integer.parseInt(year)  + "#dcrs\">" + Helper.float2String(wkBscCore.getDcrs())
				+ "%</a>");
		bscNodeMap.put("DCRT (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/wk.htm?bscid=" + node + "&week=" + Integer.parseInt(week) +"&year=" + Integer.parseInt(year)  + "#dcrt\">" + Helper.float2String(wkBscCore.getDcrt())
				+ "%</a>");
		bscNodeMap.put("TRAUCR (%)",
				"<a href=\"/VMSC2-Statistics/report/core/bsc/wk.htm?bscid=" + node + "&week=" + Integer.parseInt(week) +"&year=" + Integer.parseInt(year)  +"#traucr\">" + Helper.float2String(wkBscCore.getTraucr()) + "%</a>");
		bscNodeMap.put("HSR (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/wk.htm?bscid=" + node + "&week=" + Integer.parseInt(week) +"&year=" + Integer.parseInt(year)  + "#hsr\">" +Helper.float2String(wkBscCore.getHsr())
				+ "%</a>");

		return bscNodeMap;
	}
}
