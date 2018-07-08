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
import vn.com.vhc.vmsc2.statistics.dao.HrMscQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrRouteCoreDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrRouteCoreReportDAO;
import vn.com.vhc.vmsc2.statistics.domain.HrBscCore;
import vn.com.vhc.vmsc2.statistics.domain.HrMscQos;
import vn.com.vhc.vmsc2.statistics.domain.HrRouteCore;
import vn.com.vhc.vmsc2.statistics.domain.HrRouteCoreReport;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
@RequestMapping("/report/core/route/hr/*")
public class HrRouteCoreReportController extends BaseController {
	@Autowired
	private HrBscCoreDAO hrBscCoreDao;
	@Autowired
	private HrMscQosDAO hrMscQosDao;
	@Autowired
	private HrRouteCoreDAO hrRouteCoreDao;
	@Autowired
	private HrRouteCoreReportDAO hrRouteCoreReportDao;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = true) String fromNode, @RequestParam(required = true) String toNode,
			@RequestParam(required = false) String day,@RequestParam(required = false) String hour, ModelMap model, HttpServletRequest request) {

		try {
			Date d;
			
			if (day == null) {
				d = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
				day = df.format(d);
			} else {
				d = df.parse(day);
			}

			if(hour==null){
				hour=String.valueOf(12);
			}
			Map<String, String> fromNodeMap = null;
			Map<String, String> toNodeMap = null;

			if (isMsc(fromNode)) {
				fromNodeMap = mscNodeMap(d,hour, fromNode);

			} else if (isBsc(fromNode)) {
				fromNodeMap = bscNodeMap(d,hour, fromNode);
			}

			if (isMsc(toNode)) {
				toNodeMap = mscNodeMap(d,hour, toNode);
			} else if (isBsc(toNode)) {
				toNodeMap = bscNodeMap(d, hour, toNode);
			}

			List<HrRouteCore> hrRouteCores = hrRouteCoreDao.filter(d,hour, fromNode, toNode);
			HrRouteCoreReport hrRouteCoreReport = hrRouteCoreReportDao.selectByPrimaryKey(d, fromNode, Integer.parseInt(hour), toNode);
			model.addAttribute("hrRouteCoreReport", hrRouteCoreReport);
			model.addAttribute("day", day);
			model.addAttribute("hour", hour);
			model.addAttribute("fromNode", fromNode);
			model.addAttribute("toNode", toNode);

			model.addAttribute("fromNodeMap", fromNodeMap);
			model.addAttribute("toNodeMap", toNodeMap);
			model.addAttribute("hrRouteCores", hrRouteCores);
			

		} catch (ParseException e) {
			saveError(request, e.toString());
		}

		return "hrRouteCoreReportDetailList";
	}

	private boolean isMsc(String node) {
		return node.charAt(0) == 'M';
	}

	private boolean isBsc(String node) {
		return node.charAt(0) == 'B';
	}

	private Map<String, String> mscNodeMap(Date d,String hour, String node) {
		Map<String, String> mscNodeMap = new LinkedHashMap<String, String>();

		HrMscQos hrMscQos = hrMscQosDao.selectByPrimaryKey(d, Integer.parseInt(hour), node);

		if (hrMscQos == null)
			hrMscQos = new HrMscQos();

		mscNodeMap.put("%", "N/A");
		mscNodeMap.put("MCSSR (%)",
				"<a href=\"/VMSC2-Statistics/report/core/msc/hr.htm?mscid=" + node + "&day=" + df.format(d) +"&hour=" + Integer.parseInt(hour) + "#mcssr\">" + Helper.float2String(hrMscQos.getMcssr()) + "%</a>");
		mscNodeMap.put("LUSR (%)", "<a href=\"/VMSC2-Statistics/report/core/msc/hr.htm?mscid=" + node + "&day=" + df.format(d) +"&hour=" + Integer.parseInt(hour) + "#lusr\">" + Helper.float2String(hrMscQos.getLusr())
				+ "%</a>");
		mscNodeMap.put("PSR (%)", "<a href=\"/VMSC2-Statistics/report/core/msc/hr.htm?mscid=" + node + "&day=" + df.format(d) +"&hour=" + Integer.parseInt(hour) + "#psr\">" + Helper.float2String(hrMscQos.getPsr())
				+ "%</a>");
		mscNodeMap.put("HSR (%)", "<a href=\"/VMSC2-Statistics/report/core/msc/hr.htm?mscid=" + node + "&day=" + df.format(d) + "&hour=" + Integer.parseInt(hour) +"#hsr\">" + Helper.float2String(hrMscQos.getHsr())
				+ "%</a>");
		mscNodeMap.put("SMSSR (%)",
				"<a href=\"/VMSC2-Statistics/report/core/msc/hr.htm?mscid=" + node + "&day=" + df.format(d) +"&hour=" + Integer.parseInt(hour) + "#smssr\">" + Helper.float2String(hrMscQos.getSmssr()) + "%</a>");
		mscNodeMap.put("AUSR (%)", "<a href=\"/VMSC2-Statistics/report/core/msc/hr.htm?mscid=" + node + "&day=" + df.format(d) + "&hour=" + Integer.parseInt(hour) +"#ausr\">" + Helper.float2String(hrMscQos.getAusr())
				+ "%</a>");
		mscNodeMap.put("ECCR (%)", "N/A");

		return mscNodeMap;
	}

	private Map<String, String> bscNodeMap(Date d,String hour, String node) {
		Map<String, String> bscNodeMap = new LinkedHashMap<String, String>();

		HrBscCore hrBscCore = hrBscCoreDao.selectByPrimaryKey(node, d, Integer.parseInt(hour));

		if (hrBscCore == null)
			hrBscCore = new HrBscCore();

		bscNodeMap.put("%", "N/A");
		bscNodeMap.put("CSSR (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/hr.htm?bscid=" + node + "&day=" + df.format(d) + "&hour=" + Integer.parseInt(hour) +"#cssr\">" + Helper.float2String(hrBscCore.getCssr())
				+ "%</a>");
		bscNodeMap.put("PSR (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/hr.htm?bscid=" + node + "&day=" + df.format(d) +"&hour=" + Integer.parseInt(hour) + "#psr\">" + Helper.float2String(hrBscCore.getPsr())
				+ "%</a>");
		bscNodeMap.put("DCRS (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/hr.htm?bscid=" + node + "&day=" + df.format(d) +"&hour=" + Integer.parseInt(hour) + "#dcrs\">" + Helper.float2String(hrBscCore.getDcrs())
				+ "%</a>");
		bscNodeMap.put("DCRT (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/hr.htm?bscid=" + node + "&day=" + df.format(d) +"&hour=" + Integer.parseInt(hour) + "#dcrt\">" + Helper.float2String(hrBscCore.getDcrt())
				+ "%</a>");
		bscNodeMap.put("TRAUCR (%)",
				"<a href=\"/VMSC2-Statistics/report/core/bsc/hr.htm?bscid=" + node + "&day=" + df.format(d) + "&hour=" + Integer.parseInt(hour) +"#traucr\">" + Helper.float2String(hrBscCore.getTraucr()) + "%</a>");
		bscNodeMap.put("HSR (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/hr.htm?bscid=" + node + "&day=" + df.format(d) +"&hour=" + Integer.parseInt(hour) + "#hsr\">" + Helper.float2String(hrBscCore.getHsr())
				+ "%</a>");

		return bscNodeMap;
	}
}
