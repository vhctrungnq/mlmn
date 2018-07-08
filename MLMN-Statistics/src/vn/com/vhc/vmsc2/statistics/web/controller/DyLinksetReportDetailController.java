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

import vn.com.vhc.vmsc2.statistics.dao.DyBscCoreDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyLinksetDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyLinksetReportDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyMscQosDAO;
import vn.com.vhc.vmsc2.statistics.domain.DyBscCore;
import vn.com.vhc.vmsc2.statistics.domain.DyLinkset;
import vn.com.vhc.vmsc2.statistics.domain.DyLinksetReport;
import vn.com.vhc.vmsc2.statistics.domain.DyMscQos;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
public class DyLinksetReportDetailController extends BaseController {
	@Autowired
	private DyLinksetDAO dyLinksetDao;
	@Autowired
	private DyMscQosDAO dyMscQosDao;
	@Autowired
	private DyBscCoreDAO dyBscCoreDao;
	@Autowired
	private DyLinksetReportDAO dyLinksetReportDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	@RequestMapping("/report/core/linkset/dy/detail")
	public String showReport(@RequestParam(required = true) String fromNode, @RequestParam(required = true) String toNode,
			@RequestParam(required = false) String day, ModelMap model, HttpServletRequest request) {
		
		try {
			Date d;
			
			if (day == null) {
				d = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
				day = df.format(d);
			} else {
				d = df.parse(day);
			}

			Map<String, String> fromNodeMap = null;
			Map<String, String> toNodeMap = null;

			if (isMsc(fromNode)) {
				fromNodeMap = mscNodeMap(d, fromNode);

			} else if (isBsc(fromNode)) {
				fromNodeMap = bscNodeMap(d, fromNode);
			}

			if (isMsc(toNode)) {
				toNodeMap = mscNodeMap(d, toNode);
			} else if (isBsc(toNode)) {
				toNodeMap = bscNodeMap(d, toNode);
			}

			List<DyLinkset> dyLinksets = dyLinksetDao.filter(d, fromNode, toNode);
			DyLinksetReport dyLinksetReports = dyLinksetReportDao.selectByPrimaryKey(d, fromNode, toNode);
			
			model.addAttribute("dyLinksetReports", dyLinksetReports);
			model.addAttribute("day", day);
			model.addAttribute("fromNode", fromNode);
			model.addAttribute("toNode", toNode);

			model.addAttribute("fromNodeMap", fromNodeMap);
			model.addAttribute("toNodeMap", toNodeMap);
			model.addAttribute("dyLinksets", dyLinksets);

		} catch (ParseException e) {
			saveError(request, e.toString());
		}

		return "dyLinksetReportDetailList";
	}

	private boolean isMsc(String node) {
		return node.charAt(0) == 'M';
	}

	private boolean isBsc(String node) {
		return node.charAt(0) == 'B';
	}

	private Map<String, String> mscNodeMap(Date d, String node) {
		Map<String, String> mscNodeMap = new LinkedHashMap<String, String>();

		DyMscQos dyMscQos = dyMscQosDao.selectByPrimaryKey(d, node);

		if (dyMscQos == null)
			dyMscQos = new DyMscQos();

		mscNodeMap.put("%", "N/A");
		mscNodeMap.put("MCSSR (%)",
				"<a href=\"/VMSC2-Statistics/report/core/msc/dy/detail.htm?mscid=" + node + "&day=" + df.format(d) + "#mcssr\">" + Helper.float2String(dyMscQos.getMcssr()) + "%</a>");
		mscNodeMap.put("LUSR (%)", "<a href=\"/VMSC2-Statistics/report/core/msc/dy/detail.htm?mscid=" + node + "&day=" + df.format(d) + "#lusr\">" + Helper.float2String(dyMscQos.getLusr())
				+ "%</a>");
		mscNodeMap.put("PSR (%)", "<a href=\"/VMSC2-Statistics/report/core/msc/dy/detail.htm?mscid=" + node + "&day=" + df.format(d) + "#psr\">" + Helper.float2String(dyMscQos.getPsr())
				+ "%</a>");
		mscNodeMap.put("HSR (%)", "<a href=\"/VMSC2-Statistics/report/core/msc/dy/detail.htm?mscid=" + node + "&day=" + df.format(d) + "#hsr\">" + Helper.float2String(dyMscQos.getHsr())
				+ "%</a>");
		mscNodeMap.put("SMSSR (%)",
				"<a href=\"/VMSC2-Statistics/report/core/msc/dy/detail.htm?mscid=" + node + "&day=" + df.format(d) + "#smssr\">" + Helper.float2String(dyMscQos.getSmssr()) + "%</a>");
		mscNodeMap.put("AUSR (%)", "<a href=\"/VMSC2-Statistics/report/core/msc/dy/detail.htm?mscid=" + node + "&day=" + df.format(d) + "#ausr\">" + Helper.float2String(dyMscQos.getAusr())
				+ "%</a>");
		mscNodeMap.put("ECCR (%)", "N/A");

		return mscNodeMap;
	}

	private Map<String, String> bscNodeMap(Date d, String node) {
		Map<String, String> bscNodeMap = new LinkedHashMap<String, String>();

		DyBscCore dyBscCore = dyBscCoreDao.selectByPrimaryKey(node, d);

		if (dyBscCore == null)
			dyBscCore = new DyBscCore();

		bscNodeMap.put("%", "N/A");
		bscNodeMap.put("CSSR (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/dy/detail.htm?bscid=" + node + "&day=" + df.format(d) + "#cssr\">" + Helper.float2String(dyBscCore.getCssr())
				+ "%</a>");
		bscNodeMap.put("PSR (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/dy/detail.htm?bscid=" + node + "&day=" + df.format(d) + "#psr\">" + Helper.float2String(dyBscCore.getPsr())
				+ "%</a>");
		bscNodeMap.put("DCRS (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/dy/detail.htm?bscid=" + node + "&day=" + df.format(d) + "#dcrs\">" + Helper.float2String(dyBscCore.getDcrs())
				+ "%</a>");
		bscNodeMap.put("DCRT (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/dy/detail.htm?bscid=" + node + "&day=" + df.format(d) + "#dcrt\">" + Helper.float2String(dyBscCore.getDcrt())
				+ "%</a>");
		bscNodeMap.put("TRAUCR (%)",
				"<a href=\"/VMSC2-Statistics/report/core/bsc/dy/detail.htm?bscid=" + node + "&day=" + df.format(d) + "#traucr\">" + Helper.float2String(dyBscCore.getTraucr()) + "%</a>");
		bscNodeMap.put("HSR (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/dy/detail.htm?bscid=" + node + "&day=" + df.format(d) + "#hsr\">" + Helper.float2String(dyBscCore.getHsr())
				+ "%</a>");

		return bscNodeMap;
	}
}
