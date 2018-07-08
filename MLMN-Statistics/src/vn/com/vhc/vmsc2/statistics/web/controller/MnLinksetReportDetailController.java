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

import vn.com.vhc.vmsc2.statistics.dao.MnBscCoreDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnLinksetDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnLinksetReportDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnMscQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.MnBscCore;
import vn.com.vhc.vmsc2.statistics.domain.MnLinkset;
import vn.com.vhc.vmsc2.statistics.domain.MnLinksetReport;
import vn.com.vhc.vmsc2.statistics.domain.MnMscQos;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
public class MnLinksetReportDetailController extends BaseController {
	@Autowired
	private MnLinksetReportDAO mnLinksetReportDao;
	@Autowired
	private MnLinksetDAO mnLinksetDao;
	@Autowired
	private MnMscQosDAO mnMscQosDao;
	@Autowired
	private MnBscCoreDAO mnBscCoreDao;
	
	@RequestMapping("/report/core/linkset/mn/detail")
	public String showReport(@RequestParam(required = true) String fromNode, @RequestParam(required = true) String toNode,
			@RequestParam(required = false) Integer month, @RequestParam(required = false) Integer year, ModelMap model, HttpServletRequest request) {

		Calendar mCalendar = Calendar.getInstance();
		if (month == null) {
			mCalendar.add(Calendar.MONTH,-1);
			month = mCalendar.get(Calendar.MONTH)+1;
		} 
		if (year == null) {
			year = mCalendar.get(Calendar.YEAR);;
		}
		
		Map<String, String> fromNodeMap = null;
		Map<String, String> toNodeMap = null;

		if (isMsc(fromNode)) {
			fromNodeMap = mscNodeMap(month, year, fromNode);

		} else if (isBsc(fromNode)) {
			fromNodeMap = bscNodeMap(month, year, fromNode);
		}

		if (isMsc(toNode)) {
			toNodeMap = mscNodeMap(month, year, toNode);
		} else if (isBsc(toNode)) {
			toNodeMap = bscNodeMap(month, year, toNode);
		}

		List<MnLinkset> mnLinksets = mnLinksetDao.filter(month, year, fromNode, toNode);
		MnLinksetReport mnLinksetReports = mnLinksetReportDao.selectByPrimaryKey(fromNode, month, toNode, year);
		
		model.addAttribute("mnLinksetReports", mnLinksetReports);
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		model.addAttribute("fromNode", fromNode);
		model.addAttribute("toNode", toNode);

		model.addAttribute("fromNodeMap", fromNodeMap);
		model.addAttribute("toNodeMap", toNodeMap);
		model.addAttribute("mnLinksets", mnLinksets);

		return "mnLinksetReportDetailList";
	}

	private boolean isMsc(String node) {
		return node.charAt(0) == 'M';
	}

	private boolean isBsc(String node) {
		return node.charAt(0) == 'B';
	}

	private Map<String, String> mscNodeMap(Integer month, Integer year, String node) {
		Map<String, String> mscNodeMap = new LinkedHashMap<String, String>();

		MnMscQos mnMscQos = mnMscQosDao.selectByPrimaryKey(month, node, year);

		if (mnMscQos == null)
			mnMscQos = new MnMscQos();

		mscNodeMap.put("%", "N/A");
		mscNodeMap.put("MCSSR (%)",
				"<a href=\"/VMSC2-Statistics/report/core/msc/mn/detail.htm?mscid=" + node + "&month=" + month + "&year=" + year + "#mcssr\">" + Helper.float2String(mnMscQos.getMcssr()) + "%</a>");
		mscNodeMap.put("LUSR (%)", "<a href=\"/VMSC2-Statistics/report/core/msc/mn/detail.htm?mscid=" + node + "&month=" + month + "&year=" + year + "#lusr\">" + Helper.float2String(mnMscQos.getLusr())
				+ "%</a>");
		mscNodeMap.put("PSR (%)", "<a href=\"/VMSC2-Statistics/report/core/msc/mn/detail.htm?mscid=" + node + "&month=" + month + "&year=" + year + "#psr\">" + Helper.float2String(mnMscQos.getPsr())
				+ "%</a>");
		mscNodeMap.put("HSR (%)", "<a href=\"/VMSC2-Statistics/report/core/msc/mn/detail.htm?mscid=" + node + "&month=" + month + "&year=" + year + "#hsr\">" + Helper.float2String(mnMscQos.getHsr())
				+ "%</a>");
		mscNodeMap.put("SMSSR (%)",
				"<a href=\"/VMSC2-Statistics/report/core/msc/mn/detail.htm?mscid=" + node + "&month=" + month + "&year=" + year + "#smssr\">" + Helper.float2String(mnMscQos.getSmssr()) + "%</a>");
		mscNodeMap.put("AUSR (%)", "<a href=\"/VMSC2-Statistics/report/core/msc/mn/detail.htm?mscid=" + node + "&month=" + month + "&year=" + year + "#ausr\">" + Helper.float2String(mnMscQos.getAusr())
				+ "%</a>");
		mscNodeMap.put("ECCR (%)", "N/A");

		return mscNodeMap;
	}

	private Map<String, String> bscNodeMap(Integer month, Integer year, String node) {
		Map<String, String> bscNodeMap = new LinkedHashMap<String, String>();

		MnBscCore mnBscCore = mnBscCoreDao.selectByPrimaryKey(node, month, year);

		if (mnBscCore == null)
			mnBscCore = new MnBscCore();

		bscNodeMap.put("%", "N/A");
		bscNodeMap.put("CSSR (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/mn/detail.htm?bscid=" + node + "&day=" + month + "&year=" + year + "#cssr\">" + Helper.float2String(mnBscCore.getCssr())
				+ "%</a>");
		bscNodeMap.put("PSR (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/mn/detail.htm?bscid=" + node + "&day=" + month + "&year=" + year + "#psr\">" + Helper.float2String(mnBscCore.getPsr())
				+ "%</a>");
		bscNodeMap.put("DCRS (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/mn/detail.htm?bscid=" + node + "&day=" + month + "&year=" + year + "#dcrs\">" + Helper.float2String(mnBscCore.getDcrs())
				+ "%</a>");
		bscNodeMap.put("DCRT (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/mn/detail.htm?bscid=" + node + "&day=" + month + "&year=" + year + "#dcrt\">" + Helper.float2String(mnBscCore.getDcrt())
				+ "%</a>");
		bscNodeMap.put("TRAUCR (%)",
				"<a href=\"/VMSC2-Statistics/report/core/bsc/mn/detail.htm?bscid=" + node + "&day=" + month + "&year=" + year + "#traucr\">" + Helper.float2String(mnBscCore.getTraucr()) + "%</a>");
		bscNodeMap.put("HSR (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/mn/detail.htm?bscid=" + node + "&day=" + month + "&year=" + year + "#hsr\">" + Helper.float2String(mnBscCore.getHsr())
				+ "%</a>");

		return bscNodeMap;
	}
}
