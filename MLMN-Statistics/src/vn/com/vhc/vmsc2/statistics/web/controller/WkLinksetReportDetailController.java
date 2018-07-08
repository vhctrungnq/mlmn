package vn.com.vhc.vmsc2.statistics.web.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.vhc.vmsc2.statistics.dao.WkBscCoreDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkLinksetDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkLinksetReportDAO;
import vn.com.vhc.vmsc2.statistics.dao.WkMscQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.WkBscCore;
import vn.com.vhc.vmsc2.statistics.domain.WkLinkset;
import vn.com.vhc.vmsc2.statistics.domain.WkLinksetReport;
import vn.com.vhc.vmsc2.statistics.domain.WkMscQos;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
public class WkLinksetReportDetailController extends BaseController {
	@Autowired
	private WkLinksetReportDAO wkLinksetReportDao;
	@Autowired
	private WkLinksetDAO wkLinksetDao;
	@Autowired
	private WkMscQosDAO wkMscQosDao;
	@Autowired
	private WkBscCoreDAO wkBscCoreDao;

	@RequestMapping("/report/core/linkset/wk/detail")
	public String showReport(@RequestParam(required = true) String fromNode, @RequestParam(required = true) String toNode,
			@RequestParam(required = false) Integer week, @RequestParam(required = false) Integer year, ModelMap model, HttpServletRequest request) {
		
		DateTime dt = new DateTime();
		dt = dt.minusWeeks(1);

		if (week == null) {
			week = dt.getWeekOfWeekyear();
		}
		
		if(year == null)
			year = dt.getYear();
		
		Map<String, String> fromNodeMap = null;
		Map<String, String> toNodeMap = null;

		if (isMsc(fromNode)) {
			fromNodeMap = mscNodeMap(week, year, fromNode);

		} else if (isBsc(fromNode)) {
			fromNodeMap = bscNodeMap(week, year, fromNode);
		}

		if (isMsc(toNode)) {
			toNodeMap = mscNodeMap(week, year, toNode);
		} else if (isBsc(toNode)) {
			toNodeMap = bscNodeMap(week, year, toNode);
		}

		List<WkLinkset> wkLinksets = wkLinksetDao.filter(week, year, fromNode, toNode);
		WkLinksetReport wkLinksetReports = wkLinksetReportDao.selectByPrimaryKey(fromNode, toNode, week, year);
		
		model.addAttribute("wkLinksetReports", wkLinksetReports);
		model.addAttribute("week", week);
		model.addAttribute("year", year);
		model.addAttribute("fromNode", fromNode);
		model.addAttribute("toNode", toNode);

		model.addAttribute("fromNodeMap", fromNodeMap);
		model.addAttribute("toNodeMap", toNodeMap);
		model.addAttribute("wkLinksets", wkLinksets);

		return "wkLinksetReportDetailList";
	}

	private boolean isMsc(String node) {
		return node.charAt(0) == 'M';
	}

	private boolean isBsc(String node) {
		return node.charAt(0) == 'B';
	}

	private Map<String, String> mscNodeMap(Integer week, Integer year, String node) {
		Map<String, String> mscNodeMap = new LinkedHashMap<String, String>();

		WkMscQos wkMscQos = wkMscQosDao.selectByPrimaryKey(node, week, year);

		if (wkMscQos == null)
			wkMscQos = new WkMscQos();

		mscNodeMap.put("%", "N/A");
		mscNodeMap.put("MCSSR (%)",
				"<a href=\"/VMSC2-Statistics/report/core/msc/mn/detail.htm?mscid=" + node + "&month=" + week + "&year=" + year + "#mcssr\">" + Helper.float2String(wkMscQos.getMcssr()) + "%</a>");
		mscNodeMap.put("LUSR (%)", "<a href=\"/VMSC2-Statistics/report/core/msc/mn/detail.htm?mscid=" + node + "&month=" + week + "&year=" + year + "#lusr\">" + Helper.float2String(wkMscQos.getLusr())
				+ "%</a>");
		mscNodeMap.put("PSR (%)", "<a href=\"/VMSC2-Statistics/report/core/msc/mn/detail.htm?mscid=" + node + "&month=" + week + "&year=" + year + "#psr\">" + Helper.float2String(wkMscQos.getPsr())
				+ "%</a>");
		mscNodeMap.put("HSR (%)", "<a href=\"/VMSC2-Statistics/report/core/msc/mn/detail.htm?mscid=" + node + "&month=" + week + "&year=" + year + "#hsr\">" + Helper.float2String(wkMscQos.getHsr())
				+ "%</a>");
		mscNodeMap.put("SMSSR (%)",
				"<a href=\"/VMSC2-Statistics/report/core/msc/mn/detail.htm?mscid=" + node + "&month=" + week + "&year=" + year + "#smssr\">" + Helper.float2String(wkMscQos.getSmssr()) + "%</a>");
		mscNodeMap.put("AUSR (%)", "<a href=\"/VMSC2-Statistics/report/core/msc/mn/detail.htm?mscid=" + node + "&month=" + week + "&year=" + year + "#ausr\">" + Helper.float2String(wkMscQos.getAusr())
				+ "%</a>");
		mscNodeMap.put("ECCR (%)", "N/A");

		return mscNodeMap;
	}

	private Map<String, String> bscNodeMap(Integer week, Integer year, String node) {
		Map<String, String> bscNodeMap = new LinkedHashMap<String, String>();

		WkBscCore wkBscCore = wkBscCoreDao.selectByPrimaryKey(node, week, year);

		if (wkBscCore == null)
			wkBscCore = new WkBscCore();

		bscNodeMap.put("%", "N/A");
		bscNodeMap.put("CSSR (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/mn/detail.htm?bscid=" + node + "&day=" + week + "&year=" + year + "#cssr\">" + Helper.float2String(wkBscCore.getCssr())
				+ "%</a>");
		bscNodeMap.put("PSR (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/mn/detail.htm?bscid=" + node + "&day=" + week + "&year=" + year + "#psr\">" + Helper.float2String(wkBscCore.getPsr())
				+ "%</a>");
		bscNodeMap.put("DCRS (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/mn/detail.htm?bscid=" + node + "&day=" + week + "&year=" + year + "#dcrs\">" + Helper.float2String(wkBscCore.getDcrs())
				+ "%</a>");
		bscNodeMap.put("DCRT (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/mn/detail.htm?bscid=" + node + "&day=" + week + "&year=" + year + "#dcrt\">" + Helper.float2String(wkBscCore.getDcrt())
				+ "%</a>");
		bscNodeMap.put("TRAUCR (%)",
				"<a href=\"/VMSC2-Statistics/report/core/bsc/mn/detail.htm?bscid=" + node + "&day=" + week + "&year=" + year + "#traucr\">" + Helper.float2String(wkBscCore.getTraucr()) + "%</a>");
		bscNodeMap.put("HSR (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/mn/detail.htm?bscid=" + node + "&day=" + week + "&year=" + year + "#hsr\">" + Helper.float2String(wkBscCore.getHsr())
				+ "%</a>");

		return bscNodeMap;
	}
}
