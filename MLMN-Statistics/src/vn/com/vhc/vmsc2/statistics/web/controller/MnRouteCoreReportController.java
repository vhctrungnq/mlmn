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
import vn.com.vhc.vmsc2.statistics.dao.MnMscQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnRouteCoreDAO;
import vn.com.vhc.vmsc2.statistics.dao.MnRouteCoreReportDAO;
import vn.com.vhc.vmsc2.statistics.domain.MnBscCore;
import vn.com.vhc.vmsc2.statistics.domain.MnMscQos;
import vn.com.vhc.vmsc2.statistics.domain.MnRouteCore;
import vn.com.vhc.vmsc2.statistics.domain.MnRouteCoreReport;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
@RequestMapping("/report/core/route/mn/*")
public class MnRouteCoreReportController extends BaseController {
	@Autowired
	private MnBscCoreDAO mnBscCoreDao;
	@Autowired
	private MnMscQosDAO mnMscQosDao;
	@Autowired
	private MnRouteCoreDAO mnRouteCoreDao;
	@Autowired
	private MnRouteCoreReportDAO mnRouteCoreReportDao;

	@RequestMapping("detail")
	public String showReport(@RequestParam(required = true) String fromNode, @RequestParam(required = true) String toNode,
			@RequestParam(required = false) String month,@RequestParam(required = false) String year, ModelMap model, HttpServletRequest request) {

		Calendar now = Calendar.getInstance();

		if (month == null){
			now.add(Calendar.MONTH,-1);
			month = String.valueOf(now.get(Calendar.MONTH)+1);
		}

		if (year == null)
			year = String.valueOf(now.get(Calendar.YEAR));
		
			Map<String, String> fromNodeMap = null;
			Map<String, String> toNodeMap = null;

			if (isMsc(fromNode)) {
				fromNodeMap = mscNodeMap(month,year, fromNode);

			} else if (isBsc(fromNode)) {
				fromNodeMap = bscNodeMap(month,year, fromNode);
			}

			if (isMsc(toNode)) {
				toNodeMap = mscNodeMap(month,year, toNode);
			} else if (isBsc(toNode)) {
				toNodeMap = bscNodeMap(month, year, toNode);
			}

			List<MnRouteCore> mnRouteCores = mnRouteCoreDao.filter(month,year, fromNode, toNode);
			MnRouteCoreReport mnRouteCoreReport = mnRouteCoreReportDao.selectByPrimaryKey(fromNode, Integer.parseInt(month), toNode,Integer.parseInt( year));
			model.addAttribute("mnRouteCoreReport", mnRouteCoreReport);

			model.addAttribute("month", month);
			model.addAttribute("year", year);
			model.addAttribute("fromNode", fromNode);
			model.addAttribute("toNode", toNode);

			model.addAttribute("fromNodeMap", fromNodeMap);
			model.addAttribute("toNodeMap", toNodeMap);
			model.addAttribute("mnRouteCores", mnRouteCores);

		return "mnRouteCoreReportDetailList";
	}

	private boolean isMsc(String node) {
		return node.charAt(0) == 'M';
	}

	private boolean isBsc(String node) {
		return node.charAt(0) == 'B';
	}

	private Map<String, String> mscNodeMap(String month,String year, String node) {
		Map<String, String> mscNodeMap = new LinkedHashMap<String, String>();

		MnMscQos mnMscQos = mnMscQosDao.selectByPrimaryKey(Integer.parseInt(month), node, Integer.parseInt(year));

		if (mnMscQos == null)
			mnMscQos = new MnMscQos();

		mscNodeMap.put("%", "N/A");
		mscNodeMap.put("MCSSR (%)",
				"<a href=\"/VMSC2-Statistics/report/core/msc/mn.htm?mscid=" + node + "&month=" + Integer.parseInt(month) +"&year=" + Integer.parseInt(year) + "#mcssr\">" + Helper.float2String(mnMscQos.getMcssr()) + "%</a>");
		mscNodeMap.put("LUSR (%)", "<a href=\"/VMSC2-Statistics/report/core/msc/mn.htm?mscid=" + node + "&month=" + Integer.parseInt(month) +"&year=" + Integer.parseInt(year) + "#lusr\">" + Helper.float2String(mnMscQos.getLusr())
				+ "%</a>");
		mscNodeMap.put("PSR (%)", "<a href=\"/VMSC2-Statistics/report/core/msc/mn.htm?mscid=" + node + "&month=" + Integer.parseInt(month) +"&year=" + Integer.parseInt(year)  + "#psr\">" + Helper.float2String(mnMscQos.getPsr())
				+ "%</a>");
		mscNodeMap.put("HSR (%)", "<a href=\"/VMSC2-Statistics/report/core/msc/mn.htm?mscid=" + node + "&month=" + Integer.parseInt(month) +"&year=" + Integer.parseInt(year)  +"#hsr\">" + Helper.float2String(mnMscQos.getHsr())
				+ "%</a>");
		mscNodeMap.put("SMSSR (%)",
				"<a href=\"/VMSC2-Statistics/report/core/msc/mn.htm?mscid=" + node + "&month=" + Integer.parseInt(month) +"&year=" + Integer.parseInt(year)  + "#smssr\">" + Helper.float2String(mnMscQos.getSmssr()) + "%</a>");
		mscNodeMap.put("AUSR (%)", "<a href=\"/VMSC2-Statistics/report/core/msc/mn.htm?mscid=" + node + "&month=" + Integer.parseInt(month) +"&year=" + Integer.parseInt(year)  +"#ausr\">" + Helper.float2String(mnMscQos.getAusr())
				+ "%</a>");
		mscNodeMap.put("ECCR (%)", "N/A");

		return mscNodeMap;
	}

	private Map<String, String> bscNodeMap(String month,String year, String node) {
		Map<String, String> bscNodeMap = new LinkedHashMap<String, String>();

		MnBscCore mnBscCore = mnBscCoreDao.selectByPrimaryKey(node, Integer.parseInt(month), Integer.parseInt(year));

		if (mnBscCore == null)
			mnBscCore = new MnBscCore();

		bscNodeMap.put("%", "N/A");
		bscNodeMap.put("CSSR (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/mn.htm?bscid=" + node + "&month=" + Integer.parseInt(month) +"&year=" + Integer.parseInt(year)  +"#cssr\">" + Helper.float2String(mnBscCore.getCssr())
				+ "%</a>");
		bscNodeMap.put("PSR (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/mn.htm?bscid=" + node +"&month=" + Integer.parseInt(month) +"&year=" + Integer.parseInt(year)  + "#psr\">" + Helper.float2String(mnBscCore.getPsr())
				+ "%</a>");
		bscNodeMap.put("DCRS (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/mn.htm?bscid=" + node + "&month=" + Integer.parseInt(month) +"&year=" + Integer.parseInt(year)  + "#dcrs\">" + Helper.float2String(mnBscCore.getDcrs())
				+ "%</a>");
		bscNodeMap.put("DCRT (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/mn.htm?bscid=" + node + "&month=" + Integer.parseInt(month) +"&year=" + Integer.parseInt(year)  + "#dcrt\">" + Helper.float2String(mnBscCore.getDcrt())
				+ "%</a>");
		bscNodeMap.put("TRAUCR (%)",
				"<a href=\"/VMSC2-Statistics/report/core/bsc/mn.htm?bscid=" + node + "&month=" + Integer.parseInt(month) +"&year=" + Integer.parseInt(year)  +"#traucr\">" + Helper.float2String(mnBscCore.getTraucr()) + "%</a>");
		bscNodeMap.put("HSR (%)", "<a href=\"/VMSC2-Statistics/report/core/bsc/mn.htm?bscid=" + node + "&month=" + Integer.parseInt(month) +"&year=" + Integer.parseInt(year)  + "#hsr\">" +Helper.float2String(mnBscCore.getHsr())
				+ "%</a>");
//ĐÃ XONG
		return bscNodeMap;
	}
}
