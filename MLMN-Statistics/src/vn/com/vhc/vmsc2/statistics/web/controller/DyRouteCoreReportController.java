package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.DyBscCoreDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyMscQosDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyRouteCoreBhDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyRouteCoreBhReportDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyRouteCoreDAO;
import vn.com.vhc.vmsc2.statistics.dao.DyRouteCoreReportDAO;
import vn.com.vhc.vmsc2.statistics.domain.DyBscCore;
import vn.com.vhc.vmsc2.statistics.domain.DyMscQos;
import vn.com.vhc.vmsc2.statistics.domain.DyRouteCore;
import vn.com.vhc.vmsc2.statistics.domain.DyRouteCoreBh;
import vn.com.vhc.vmsc2.statistics.domain.DyRouteCoreBhReport;
import vn.com.vhc.vmsc2.statistics.domain.DyRouteCoreReport;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;


@Controller
@RequestMapping("/report/core/route/dy/*")
public class DyRouteCoreReportController extends BaseController {
	@Autowired
	private DyBscCoreDAO dyBscCoreDao;
	@Autowired
	private DyMscQosDAO dyMscQosDao;
	@Autowired
	private DyRouteCoreDAO dyRouteCoreDao;
	@Autowired
	private DyRouteCoreBhDAO dyRouteCoreBhDao;
	@Autowired
	private DyRouteCoreReportDAO dyRouteCoreReportDao;
	@Autowired
	private DyRouteCoreBhReportDAO dyRouteCoreBhReportDao;

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("list")
	public ModelAndView dyList(@RequestParam(required = false) String routeid, @RequestParam(required = false) String fromNode, @RequestParam(required = false) String toNode,
			@RequestParam(required = false) String day, Model model) {
		
		try {
			if (day == null) {
				Date d = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
				day = df.format(d);
			}

			List<DyRouteCore> dyRouteCores;
			
			dyRouteCores = dyRouteCoreDao.filter(routeid, fromNode, toNode, df.parse(day));
			
			model.addAttribute("dyRouteCoreReportList", dyRouteCores);
			model.addAttribute("routeid", routeid);
			model.addAttribute("fromNode", fromNode);
			model.addAttribute("toNode", toNode);
			model.addAttribute("day", day);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ModelAndView("dyRouteCoreReportList");
	}
	
	@RequestMapping("listDnk")
	public ModelAndView dyListDnk(@RequestParam(required = false) Date day,@RequestParam(required = false) String routeid1,@RequestParam(required = false) String routeid2, Model model) {
		
		long currentTime = System.currentTimeMillis();
			if (day == null)
				day = new Date(currentTime - 24 * 60 * 60 * 1000);

			model.addAttribute("day", df.format(day));
			
			DateTime dt = new DateTime(day);
			Date startDay = dt.minusDays(30).toDate();
			
			List<DyRouteCoreBh> dyRouteCores = dyRouteCoreBhDao.filter(df.format(startDay),df.format(day));
			List<DyRouteCoreBhReport> dyRouteCoresBhReport = dyRouteCoreBhReportDao.filter(df.format(startDay),df.format(day));
			
			List<String> routeTrafList = new ArrayList<String>();
			List<String> routeInTrafList = new ArrayList<String>();
			List<String> routeOutTrafList = new ArrayList<String>();
			float totRouteTraff = 0;
			float totRouteInTraff = 0;
			float totRouteOutTraff = 0;
			for (DyRouteCoreBhReport dyRouteCoreBhReport : dyRouteCoresBhReport) {
				if (dyRouteCoreBhReport.getRouteTraf()==null)
					totRouteTraff +=0;
				else
					totRouteTraff +=dyRouteCoreBhReport.getRouteTraf();
				if (dyRouteCoreBhReport.getInRouteTraf()==null)
					totRouteInTraff +=0;
				else
					totRouteInTraff +=dyRouteCoreBhReport.getInRouteTraf();
				if (dyRouteCoreBhReport.getOutRouteTraf()==null)
					totRouteOutTraff +=0;
				else
					totRouteOutTraff +=dyRouteCoreBhReport.getOutRouteTraf();
			}
			String title="";//df.format(day);
			for (DyRouteCoreBhReport dyRouteCoreBhReport : dyRouteCoresBhReport) {
				title = df.format(dyRouteCoreBhReport.getDay());
				float rateRouteTraff;
				float rateRouteInTraff;
				float rateRouteOutTraff;
				
				if (totRouteTraff == 0)
					rateRouteTraff =0;
				else
					rateRouteTraff =Math.round(100*100*(dyRouteCoreBhReport.getRouteTraf()/totRouteTraff));
				if (totRouteInTraff == 0)
					rateRouteInTraff =0;
				else
					rateRouteInTraff =Math.round(100*100*(dyRouteCoreBhReport.getInRouteTraf()/totRouteInTraff));
				if (totRouteOutTraff == 0)
					rateRouteOutTraff =0;
				else
					rateRouteOutTraff =Math.round(100*100*(dyRouteCoreBhReport.getOutRouteTraf()/totRouteOutTraff));
				
				routeTrafList.add(dyRouteCoreBhReport.getToNode()+"-"+dyRouteCoreBhReport.getRouteTraf()+"-"+rateRouteTraff/100);
				routeInTrafList.add(dyRouteCoreBhReport.getToNode()+"-"+dyRouteCoreBhReport.getInRouteTraf()+"-"+rateRouteInTraff/100);
				routeOutTrafList.add(dyRouteCoreBhReport.getToNode()+"-"+dyRouteCoreBhReport.getOutRouteTraf()+"-"+rateRouteOutTraff/100);
			}
			
			Map<String, List<String>> routeTrafSeries = new LinkedHashMap<String, List<String>>();
			routeTrafSeries.put("routeTrafSeries", routeTrafList);
			model.addAttribute("routeTrafChart", Chart.pieChart("routeTrafChart","TRAFFIC : "+title, routeTrafSeries));
			Map<String, List<String>> routeInTrafSeries = new LinkedHashMap<String, List<String>>();
			routeInTrafSeries.put("routeInTrafSeries", routeInTrafList);
			model.addAttribute("routeInTrafChart", Chart.pieChart("routeInTrafChart","IN TRAFFIC : "+title, routeInTrafSeries));
			Map<String, List<String>> routeOutTrafSeries = new LinkedHashMap<String, List<String>>();
			routeOutTrafSeries.put("routeOutTrafSeries", routeOutTrafList);
			model.addAttribute("routeOutTrafChart", Chart.pieChart("routeOutTrafChart","OUT TRAFFIC : "+title, routeOutTrafSeries));
			
			
			int count1 = 0;
			int count2 = 0;
			String attAll = "";
			
			if (routeid2 !=null) {
				if (routeid2.length()==0) {
					List<DyRouteCoreBh> dyRouteCoresName = dyRouteCoreBhDao.filterRoute(df.format(startDay),df.format(day));
					
					for (DyRouteCoreBh v : dyRouteCoresName) {
						List<DyRouteCoreBh> dyRouteCoresChart = dyRouteCoreBhDao.filter(df.format(startDay),df.format(day),v.getRouteid());
						List<String> categories = new ArrayList<String>();
						List<Float> routeTrafColunmList = new ArrayList<Float>();
						List<Float> routeInTrafColunmList = new ArrayList<Float>();
						List<Float> routeOutTrafColunmList = new ArrayList<Float>();
			
						for (DyRouteCoreBh dyRouteCoreBh : dyRouteCoresChart) {
							categories.add(df.format(dyRouteCoreBh.getDay()));
							
							routeTrafColunmList.add(dyRouteCoreBh.getRouteTraf());
							routeInTrafColunmList.add(dyRouteCoreBh.getInRouteTraf());
							routeOutTrafColunmList.add(dyRouteCoreBh.getOutRouteTraf());
						}
						Map<String, List<Float>> routetrafDetailsSeries = new LinkedHashMap<String, List<Float>>();
						routetrafDetailsSeries.put("TRAFFIC-column-4572a7", routeTrafColunmList);
						routetrafDetailsSeries.put("INC TRAFFIC-column-aa4643", routeInTrafColunmList);
						routetrafDetailsSeries.put("OUT TRAFFIC-column-89a54e", routeOutTrafColunmList);
						Map<String, List<Float>> routeInTrafDetailsSeries = new LinkedHashMap<String, List<Float>>();
						routeInTrafDetailsSeries.put("INC TRAFFIC-column-aa4643", routeInTrafColunmList);
						Map<String, List<Float>> routeOutTrafDetailsSeries = new LinkedHashMap<String, List<Float>>();
						routeOutTrafDetailsSeries.put("OUT TRAFFIC-column-89a54e", routeOutTrafColunmList);
						count2++;
						
						model.addAttribute("trafChart"+count2, Chart.multiColumnWelcome("trafChart"+count2, "ROUTE TRAFFIC (Erl)-"+v.getRouteid(), categories, routetrafDetailsSeries,"0","100"));
						attAll +="${trafChart"+count2+"}";
					}
				}
				else {
					List<DyRouteCoreBh> dyRouteCoresChart = dyRouteCoreBhDao.filter(df.format(startDay),df.format(day),routeid2);
					
					List<String> categories = new ArrayList<String>();
					List<Float> routeTrafColunmList = new ArrayList<Float>();
					List<Float> routeInTrafColunmList = new ArrayList<Float>();
					List<Float> routeOutTrafColunmList = new ArrayList<Float>();
		
					for (DyRouteCoreBh dyRouteCoreBh : dyRouteCoresChart) {
						categories.add(df.format(dyRouteCoreBh.getDay()));
						
						routeTrafColunmList.add(dyRouteCoreBh.getRouteTraf());
						routeInTrafColunmList.add(dyRouteCoreBh.getInRouteTraf());
						routeOutTrafColunmList.add(dyRouteCoreBh.getOutRouteTraf());
					}
		
					Map<String, List<Float>> routetrafDetailsSeries = new LinkedHashMap<String, List<Float>>();
					routetrafDetailsSeries.put("TRAFFIC-column-4572a7", routeTrafColunmList);
					routetrafDetailsSeries.put("INC TRAFFIC-column-aa4643", routeInTrafColunmList);
					routetrafDetailsSeries.put("OUT TRAFFIC-column-89a54e", routeOutTrafColunmList);
					Map<String, List<Float>> routeInTrafDetailsSeries = new LinkedHashMap<String, List<Float>>();
					routeInTrafDetailsSeries.put("INC TRAFFIC-column-aa4643", routeInTrafColunmList);
					Map<String, List<Float>> routeOutTrafDetailsSeries = new LinkedHashMap<String, List<Float>>();
					routeOutTrafDetailsSeries.put("OUT TRAFFIC-column-89a54e", routeOutTrafColunmList);
					count2++;
					model.addAttribute("trafChart"+count2, Chart.multiColumnWelcome("trafChart"+count2, "ROUTE TRAFFIC (Erl)", categories, routetrafDetailsSeries,"0","100"));
					attAll +="${trafChart"+count2+"}";
				}
			}
			if (routeid1!= null) {
				if (routeid1.length() == 0) {
					List<DyRouteCoreBhReport> dyRouteCoresBhReportName = dyRouteCoreBhReportDao.filterRoute(df.format(startDay),df.format(day));
					
					for (DyRouteCoreBhReport v : dyRouteCoresBhReportName) {
						List<DyRouteCoreBhReport> dyRouteCoresBhReportChart = dyRouteCoreBhReportDao.filter(df.format(startDay),df.format(day),v.getToNode());
						
						List<String> categoriesSummary = new ArrayList<String>();
						List<Float> routeTrafSummaryList = new ArrayList<Float>();
						List<Float> routeInTrafSummaryList = new ArrayList<Float>();
						List<Float> routeOutTrafSummaryList = new ArrayList<Float>();
			
						for (DyRouteCoreBhReport dyRouteCoreBhReport : dyRouteCoresBhReportChart) {
							categoriesSummary.add(df.format(dyRouteCoreBhReport.getDay()));
							
							routeTrafSummaryList.add(dyRouteCoreBhReport.getRouteTraf());
							routeInTrafSummaryList.add(dyRouteCoreBhReport.getInRouteTraf());
							routeOutTrafSummaryList.add(dyRouteCoreBhReport.getOutRouteTraf());
						}
			
						Map<String, List<Float>> routetrafDetailsSeries = new LinkedHashMap<String, List<Float>>();
						routetrafDetailsSeries.put("TRAFFIC-column-4572a7", routeTrafSummaryList);
						routetrafDetailsSeries.put("INC TRAFFIC-column-aa4643", routeInTrafSummaryList);
						routetrafDetailsSeries.put("OUT TRAFFIC-column-89a54e", routeOutTrafSummaryList);
						Map<String, List<Float>> routeInTrafDetailsSeries = new LinkedHashMap<String, List<Float>>();
						routeInTrafDetailsSeries.put("INC TRAFFIC-column-aa4643", routeInTrafSummaryList);
						Map<String, List<Float>> routeOutTrafDetailsSeries = new LinkedHashMap<String, List<Float>>();
						routeOutTrafDetailsSeries.put("OUT TRAFFIC-column-89a54e", routeOutTrafSummaryList);
						count1++;
						model.addAttribute("trafChartReport"+count1, Chart.multiColumnWelcome("trafChartReport"+count1, "ROUTE TRAFFIC (Erl)-"+v.getToNode(), categoriesSummary, routetrafDetailsSeries,"0","100"));
						attAll +="${trafChartReport"+count1+"}";
					}
				}
				else {
					List<DyRouteCoreBhReport> dyRouteCoresBhReportChart = dyRouteCoreBhReportDao.filter(df.format(startDay),df.format(day),routeid1);
					
					List<String> categoriesSummary = new ArrayList<String>();
					List<Float> routeTrafSummaryList = new ArrayList<Float>();
					List<Float> routeInTrafSummaryList = new ArrayList<Float>();
					List<Float> routeOutTrafSummaryList = new ArrayList<Float>();
		
					for (DyRouteCoreBhReport dyRouteCoreBhReport : dyRouteCoresBhReportChart) {
						categoriesSummary.add(df.format(dyRouteCoreBhReport.getDay()));
						
						routeTrafSummaryList.add(dyRouteCoreBhReport.getRouteTraf());
						routeInTrafSummaryList.add(dyRouteCoreBhReport.getInRouteTraf());
						routeOutTrafSummaryList.add(dyRouteCoreBhReport.getOutRouteTraf());
					}
		
					Map<String, List<Float>> routetrafDetailsSeries = new LinkedHashMap<String, List<Float>>();
					routetrafDetailsSeries.put("TRAFFIC-column-4572a7", routeTrafSummaryList);
					routetrafDetailsSeries.put("INC TRAFFIC-column-aa4643", routeInTrafSummaryList);
					routetrafDetailsSeries.put("OUT TRAFFIC-column-89a54e", routeOutTrafSummaryList);
					Map<String, List<Float>> routeInTrafDetailsSeries = new LinkedHashMap<String, List<Float>>();
					routeInTrafDetailsSeries.put("INC TRAFFIC-column-aa4643", routeInTrafSummaryList);
					Map<String, List<Float>> routeOutTrafDetailsSeries = new LinkedHashMap<String, List<Float>>();
					routeOutTrafDetailsSeries.put("OUT TRAFFIC-column-89a54e", routeOutTrafSummaryList);
					count1++;
					model.addAttribute("trafChartReport"+count1, Chart.multiColumnWelcome("trafChartReport"+count1, "ROUTE TRAFFIC (Erl)", categoriesSummary, routetrafDetailsSeries,"0","100"));
					attAll +="${trafChartReport"+count1+"}";
				}
			}
			
			if(routeid2 == null) {
				List<DyRouteCoreBh> dyRouteCoresName = dyRouteCoreBhDao.filterRoute(df.format(startDay),df.format(day));
				
				for (DyRouteCoreBh v : dyRouteCoresName) {
					List<DyRouteCoreBh> dyRouteCoresChart = dyRouteCoreBhDao.filter(df.format(startDay),df.format(day),v.getRouteid());
					List<String> categories = new ArrayList<String>();
					List<Float> routeTrafColunmList = new ArrayList<Float>();
					List<Float> routeInTrafColunmList = new ArrayList<Float>();
					List<Float> routeOutTrafColunmList = new ArrayList<Float>();
		
					for (DyRouteCoreBh dyRouteCoreBh : dyRouteCoresChart) {
						categories.add(df.format(dyRouteCoreBh.getDay()));
						
						routeTrafColunmList.add(dyRouteCoreBh.getRouteTraf());
						routeInTrafColunmList.add(dyRouteCoreBh.getInRouteTraf());
						routeOutTrafColunmList.add(dyRouteCoreBh.getOutRouteTraf());
					}
					Map<String, List<Float>> routetrafDetailsSeries = new LinkedHashMap<String, List<Float>>();
					routetrafDetailsSeries.put("TRAFFIC-column-4572a7", routeTrafColunmList);
					routetrafDetailsSeries.put("INC TRAFFIC-column-aa4643", routeInTrafColunmList);
					routetrafDetailsSeries.put("OUT TRAFFIC-column-89a54e", routeOutTrafColunmList);
					Map<String, List<Float>> routeInTrafDetailsSeries = new LinkedHashMap<String, List<Float>>();
					routeInTrafDetailsSeries.put("INC TRAFFIC-column-aa4643", routeInTrafColunmList);
					Map<String, List<Float>> routeOutTrafDetailsSeries = new LinkedHashMap<String, List<Float>>();
					routeOutTrafDetailsSeries.put("OUT TRAFFIC-column-89a54e", routeOutTrafColunmList);
					count2++;
					
					model.addAttribute("trafChart"+count2, Chart.multiColumnWelcome("trafChart"+count2, "ROUTE TRAFFIC (Erl)-"+v.getRouteid(), categories, routetrafDetailsSeries,"0","100"));
					attAll +="${trafChart"+count2+"}";
				}
			}
			
			if(routeid1==null) {
				List<DyRouteCoreBhReport> dyRouteCoresBhReportName = dyRouteCoreBhReportDao.filterRoute(df.format(startDay),df.format(day));
				
				for (DyRouteCoreBhReport v : dyRouteCoresBhReportName) {
					List<DyRouteCoreBhReport> dyRouteCoresBhReportChart = dyRouteCoreBhReportDao.filter(df.format(startDay),df.format(day),v.getToNode());
					
					List<String> categoriesSummary = new ArrayList<String>();
					List<Float> routeTrafSummaryList = new ArrayList<Float>();
					List<Float> routeInTrafSummaryList = new ArrayList<Float>();
					List<Float> routeOutTrafSummaryList = new ArrayList<Float>();
		
					for (DyRouteCoreBhReport dyRouteCoreBhReport : dyRouteCoresBhReportChart) {
						categoriesSummary.add(df.format(dyRouteCoreBhReport.getDay()));
						
						routeTrafSummaryList.add(dyRouteCoreBhReport.getRouteTraf());
						routeInTrafSummaryList.add(dyRouteCoreBhReport.getInRouteTraf());
						routeOutTrafSummaryList.add(dyRouteCoreBhReport.getOutRouteTraf());
					}
		
					Map<String, List<Float>> routetrafDetailsSeries = new LinkedHashMap<String, List<Float>>();
					routetrafDetailsSeries.put("TRAFFIC-column-4572a7", routeTrafSummaryList);
					routetrafDetailsSeries.put("INC TRAFFIC-column-aa4643", routeInTrafSummaryList);
					routetrafDetailsSeries.put("OUT TRAFFIC-column-89a54e", routeOutTrafSummaryList);
					Map<String, List<Float>> routeInTrafDetailsSeries = new LinkedHashMap<String, List<Float>>();
					routeInTrafDetailsSeries.put("INC TRAFFIC-column-aa4643", routeInTrafSummaryList);
					Map<String, List<Float>> routeOutTrafDetailsSeries = new LinkedHashMap<String, List<Float>>();
					routeOutTrafDetailsSeries.put("OUT TRAFFIC-column-89a54e", routeOutTrafSummaryList);
					count1++;
					model.addAttribute("trafChartReport"+count1, Chart.multiColumnWelcome("trafChartReport"+count1, "ROUTE TRAFFIC (Erl)-"+v.getToNode(), categoriesSummary, routetrafDetailsSeries,"0","100"));
					attAll +="${trafChartReport"+count1+"}";
					
				}
			}
			model.addAttribute("dyRouteCores", dyRouteCores);
			model.addAttribute("dyRouteCoresBhReport", dyRouteCoresBhReport);
			model.addAttribute("routeid1", routeid1);
			model.addAttribute("routeid2", routeid2);
			model.addAttribute("count1", count1);
			model.addAttribute("count2", count2);
			model.addAttribute("attAll", attAll);
			
		return new ModelAndView("dyRouteCoreBhReportList");
	}

	@RequestMapping("detail")
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

			List<DyRouteCore> dyRouteCores = dyRouteCoreDao.filter(d, fromNode, toNode);
			DyRouteCoreReport dyRouteCoreReport = dyRouteCoreReportDao.selectByPrimaryKey(d, fromNode, toNode);
			model.addAttribute("dyRouteCoreReport", dyRouteCoreReport);
			model.addAttribute("day", day);
			model.addAttribute("fromNode", fromNode);
			model.addAttribute("toNode", toNode);

			model.addAttribute("fromNodeMap", fromNodeMap);
			model.addAttribute("toNodeMap", toNodeMap);
			model.addAttribute("dyRouteCores", dyRouteCores);

		} catch (ParseException e) {
			saveError(request, e.toString());
		}

		return "dyRouteCoreReportDetailList";
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
