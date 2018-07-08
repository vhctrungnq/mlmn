package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import vn.com.vhc.vmsc2.statistics.dao.HrBscCoreDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrLinksetDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrLinksetReportDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrMscQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.HrBscCore;
import vn.com.vhc.vmsc2.statistics.domain.HrLinkset;
import vn.com.vhc.vmsc2.statistics.domain.HrLinksetReport;
import vn.com.vhc.vmsc2.statistics.domain.HrMscQos;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
public class HrLinksetReportDetailController extends BaseController {
	@Autowired
	private HrLinksetReportDAO hrLinksetReportDao;
	@Autowired
	private HrLinksetDAO hrLinksetDao;
	@Autowired
	private HrMscQosDAO hrMscQosDao;
	@Autowired
	private HrBscCoreDAO hrBscCoreDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	@RequestMapping("/report/core/linkset/hr/detail")
	public String showReport(@RequestParam(required = true) String fromNode, @RequestParam(required = true) String toNode,
			@RequestParam(required = false) String day, @RequestParam(required = false) Integer hour, ModelMap model, HttpServletRequest request) {
		
		try {
			Date d;
			
			if (day == null) {
				d = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
				day = df.format(d);
			} else {
				d = df.parse(day);
			}
			if (hour == null) hour = 12;

			Map<String, String> fromNodeMap = null;
			Map<String, String> toNodeMap = null;

			if (isMsc(fromNode)) {
				fromNodeMap = mscNodeMap(d, hour, fromNode);

			} else if (isBsc(fromNode)) {
				fromNodeMap = bscNodeMap(d, hour, fromNode);
			}

			if (isMsc(toNode)) {
				toNodeMap = mscNodeMap(d, hour, toNode);
			} else if (isBsc(toNode)) {
				toNodeMap = bscNodeMap(d, hour, toNode);
			}

			List<HrLinkset> hrLinksets = hrLinksetDao.filter(d, hour, fromNode, toNode);
			HrLinksetReport hrLinksetReports = hrLinksetReportDao.selectByPrimaryKey(d, fromNode, hour, toNode);
			
			model.addAttribute("hrLinksetReports", hrLinksetReports);
			model.addAttribute("day", day);
			model.addAttribute("hour", hour);
			model.addAttribute("fromNode", fromNode);
			model.addAttribute("toNode", toNode);

			model.addAttribute("fromNodeMap", fromNodeMap);
			model.addAttribute("toNodeMap", toNodeMap);
			model.addAttribute("hrLinksets", hrLinksets);

		} catch (ParseException e) {
			saveError(request, e.toString());
		}

		return "hrLinksetReportDetailList";
	}

	private boolean isMsc(String node) {
		return node.charAt(0) == 'M';
	}

	private boolean isBsc(String node) {
		return node.charAt(0) == 'B';
	}

	private Map<String, String> mscNodeMap(Date d, Integer hour, String node) {
		Map<String, String> mscNodeMap = new LinkedHashMap<String, String>();

		HrMscQos hrMscQos = hrMscQosDao.selectByPrimaryKey(d, hour, node);

		if (hrMscQos == null)
			hrMscQos = new HrMscQos();

		mscNodeMap.put("%", "N/A");
		mscNodeMap.put("MCSSR (%)",
				"<a href=\"/VMSC2-Statistics/report/core/msc/hr/detail.htm?mscid=" + node + "&day=" + df.format(d) + "&hour=" + hour + "#mcssr\">" + Helper.float2String(hrMscQos.getMcssr()) + "%</a>");
		mscNodeMap.put("LUSR (%)", "<a href=\"/VMSC2-Statistics/report/core/msc/hr/detail.htm?mscid=" + node + "&day=" + df.format(d) + "&hour=" + hour + "#lusr\">" + Helper.float2String(hrMscQos.getLusr())
				+ "%</a>");
		mscNodeMap.put("PSR (%)", "<a href=\"/VMSC2-Statistics/report/core/msc/hr/detail.htm?mscid=" + node + "&day=" + df.format(d) + "&hour=" + hour + "#psr\">" + Helper.float2String(hrMscQos.getPsr())
				+ "%</a>");
		mscNodeMap.put("HSR (%)", "<a href=\"/VMSC2-Statistics/report/core/msc/hr/detail.htm?mscid=" + node + "&day=" + df.format(d) + "&hour=" + hour + "#hsr\">" + Helper.float2String(hrMscQos.getHsr())
				+ "%</a>");
		mscNodeMap.put("SMSSR (%)",
				"<a href=\"/VMSC2-Statistics/report/core/msc/hr/detail.htm?mscid=" + node + "&day=" + df.format(d) + "&hour=" + hour + "#smssr\">" + Helper.float2String(hrMscQos.getSmssr()) + "%</a>");
		mscNodeMap.put("AUSR (%)", "<a href=\"/VMSC2-Statistics/report/core/msc/hr/detail.htm?mscid=" + node + "&day=" + df.format(d) + "&hour=" + hour + "#ausr\">" + Helper.float2String(hrMscQos.getAusr())
				+ "%</a>");
		mscNodeMap.put("ECCR (%)", "N/A");

		return mscNodeMap;
	}

	private Map<String, String> bscNodeMap(Date d, Integer hour, String node) {
		Map<String, String> bscNodeMap = new LinkedHashMap<String, String>();

		HrBscCore hrBscCore = hrBscCoreDao.selectByPrimaryKey(node, d, hour);

		if (hrBscCore == null)
			hrBscCore = new HrBscCore();

		bscNodeMap.put("%", "N/A");
		bscNodeMap.put("CSSR (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/hr/detail.htm?bscid=" + node + "&day=" + df.format(d) + "&hour=" + hour + "#cssr\">" + Helper.float2String(hrBscCore.getCssr())
				+ "%</a>");
		bscNodeMap.put("PSR (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/hr/detail.htm?bscid=" + node + "&day=" + df.format(d) + "&hour=" + hour + "#psr\">" + Helper.float2String(hrBscCore.getPsr())
				+ "%</a>");
		bscNodeMap.put("DCRS (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/hr/detail.htm?bscid=" + node + "&day=" + df.format(d) + "&hour=" + hour + "#dcrs\">" + Helper.float2String(hrBscCore.getDcrs())
				+ "%</a>");
		bscNodeMap.put("DCRT (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/hr/detail.htm?bscid=" + node + "&day=" + df.format(d) + "&hour=" + hour + "#dcrt\">" + Helper.float2String(hrBscCore.getDcrt())
				+ "%</a>");
		bscNodeMap.put("TRAUCR (%)",
				"<a href=\"/VMSC2-Statistics/report/core/bsc/hr/detail.htm?bscid=" + node + "&day=" + df.format(d) + "&hour=" + hour + "#traucr\">" + Helper.float2String(hrBscCore.getTraucr()) + "%</a>");
		bscNodeMap.put("HSR (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/hr/detail.htm?bscid=" + node + "&day=" + df.format(d) + "&hour=" + hour + "#hsr\">" + Helper.float2String(hrBscCore.getHsr())
				+ "%</a>");

		return bscNodeMap;
	}
}
