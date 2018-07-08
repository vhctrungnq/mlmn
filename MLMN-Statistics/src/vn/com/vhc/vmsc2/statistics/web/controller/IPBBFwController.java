package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.DyIpbbFwDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.HrIpbbFwDAO;
import vn.com.vhc.vmsc2.statistics.dao.RouteDAO;
import vn.com.vhc.vmsc2.statistics.domain.DyIpbbFw;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.HrIpbbFw;
import vn.com.vhc.vmsc2.statistics.domain.Route;
import vn.com.vhc.vmsc2.statistics.web.filter.RouteFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;

@Controller
@RequestMapping("/report/core/ip-backbone-fw/*")
public class IPBBFwController extends BaseController{
	
	@Autowired
    private RouteDAO routeDao;;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	@Autowired
	private DyIpbbFwDAO dyIpbbFwDAO;
	@Autowired
	private HrIpbbFwDAO hrIpbbFwDAO;
	 
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat dfull = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	@RequestMapping("dy")
	public ModelAndView ShowReport(
			 
			 @RequestParam(required = false) Date startDate,
			 @RequestParam(required = false) Date endDate,
			 @RequestParam(required = false) String routeid,
			 @RequestParam(required = false) String scp,
			
			 Model model) {
		String title = "IP BACKBONE/FW DATA";
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		if(routeid == null){
			routeid = "";
		}
		if(scp == null){
			scp = "";
		}
		
		
		List<Route> routeList = routeDao.getRouteName();
		List<Route> scpList = routeDao.getSPCName();
		
		List<DyIpbbFw> dyIpbbFw = dyIpbbFwDAO.filter(df.format(startDate), df.format(endDate),routeid, scp);
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		
		model.addAttribute("title",title);
		model.addAttribute("routeList", routeList);
		model.addAttribute("scpList", scpList);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("routeid", routeid);
		model.addAttribute("scp", scp);
		
		
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "dyIpbbFw"));
		model.addAttribute("dyIpbbFw", dyIpbbFw);
		
		
		/* Chart Start */
		long time = endDate.getTime();
		DateTime dt = new DateTime(time); 
		Date startDay = dt.minusDays(30).toDate();
		
	
		Map<String, List<Float>> seriesLink1 = new LinkedHashMap<String, List<Float>>();
		Map<String, List<Float>> seriesLink2 = new LinkedHashMap<String, List<Float>>();
		Map<String, List<Float>> seriesLink3 = new LinkedHashMap<String, List<Float>>();
		Map<String, List<Float>> seriesLink4 = new LinkedHashMap<String, List<Float>>();
		 
		
		List<String> categories = new ArrayList<String>();
		String chartAreaLink1 = "", chartAreaLink2 = "", chartAreaLink3 = "", chartAreaLink4 = "" ;
		
		
		long time2 = startDay.getTime();
		DateTime dt2 = new DateTime(time2); 
		for(int j = 0; j < 30; j++){
			Date d = dt2.plusDays(j).toDate();
			categories.add(df.format(d));
		}
		String html  = "";
		for(int k=0; k<routeList.size();k++)
		{
			RouteFilter routeCondition = new RouteFilter();
			routeCondition.setRoutename(routeList.get(k).getRouteid());
			List<Route> scpList3 = routeDao.filter(routeCondition);
			for(int i = 0; i < scpList3.size(); i++)
			{
				List<Float> link1List = new ArrayList<Float>();
				List<Float> link2List = new ArrayList<Float>();
				List<Float> link3List = new ArrayList<Float>();
				List<Float> link4List = new ArrayList<Float>();
				for(int j = 0; j < 30; j++){
					Date d = dt2.plusDays(j).toDate(); 
					List<DyIpbbFw> dyIpbbFwList = dyIpbbFwDAO.filter(df.format(d), df.format(d),routeList.get(k).getRouteid(), scpList3.get(i).getDev());
					
					
					if(dyIpbbFwList.size()==0)
					{ 
						link1List.add(Float.parseFloat("0"));
						link2List.add(Float.parseFloat("0"));
						link3List.add(Float.parseFloat("0"));
						link4List.add(Float.parseFloat("0"));
					}
					else
					{ 
						link1List.add(dyIpbbFwList.get(0).getCpuUtil());
						link2List.add(dyIpbbFwList.get(0).getMemmoryUtil());
						link3List.add(dyIpbbFwList.get(0).getAvgSession());
						link4List.add(dyIpbbFwList.get(0).getTotalSession());
					}
					
				}
				 
				seriesLink1.put(scpList3.get(i).getDev(), link1List);
				seriesLink2.put(scpList3.get(i).getDev(), link2List);
				seriesLink3.put(scpList3.get(i).getDev(), link3List);
				seriesLink4.put(scpList3.get(i).getDev(), link4List);
			}
			chartAreaLink1 += Chart.basicLineIPBB("chartMaxLink1"+routeList.get(k).getRouteid(), "CPU Utilization Average (%), "+ routeList.get(k).getRouteid(),"(%)", categories, seriesLink1);
			chartAreaLink2 += Chart.basicLineIPBB("chartMaxLink2"+routeList.get(k).getRouteid(), "Memory Utilization Average (%), "+routeList.get(k).getRouteid(),"(%)", categories, seriesLink2);		
			chartAreaLink3 += Chart.basicLineIPBB("chartMaxLink3"+routeList.get(k).getRouteid(), "Session Average, "+routeList.get(k).getRouteid(),"", categories, seriesLink3 );
			chartAreaLink4 += Chart.basicLineIPBB("chartMaxLink4"+routeList.get(k).getRouteid(), "Session Utility (%), "+routeList.get(k).getRouteid(),"", categories, seriesLink4);
			model.addAttribute("chartAreaLink1" , chartAreaLink1);
			model.addAttribute("chartAreaLink2" , chartAreaLink2);
			model.addAttribute("chartAreaLink3" , chartAreaLink3);
			model.addAttribute("chartAreaLink4" , chartAreaLink4);
			html += "<br><div id='chartMaxLink1"+routeList.get(k).getRouteid()+ "'style='width: 100%; margin: 1em auto'></div>" ;
			html += "<br><div id='chartMaxLink2"+routeList.get(k).getRouteid()+ "'style='width: 100%; margin: 1em auto'></div>" ;
			html += "<br><div id='chartMaxLink3"+routeList.get(k).getRouteid()+ "'style='width: 100%; margin: 1em auto'></div>" ;
			html += "<br><div id='chartMaxLink4"+routeList.get(k).getRouteid()+ "'style='width: 100%; margin: 1em auto'></div>" ;
			
		}
		
		model.addAttribute("html" , html); 
		return new ModelAndView("dyIpbbFwList");
	}
	
	@RequestMapping("hr")
	public ModelAndView HourlyReport(
			 @RequestParam(required = false) String startDate,
			 @RequestParam(required = false) String endDate,
			 @RequestParam(required = false) String routeid,
			 @RequestParam(required = false) String scp,
			 Model model) {
		
		String title = "IP BACKBONE/FW DATA";
		List<HrIpbbFw> hrIpbbFw = null;
		Date edate ;
		long currentTime = System.currentTimeMillis();
		if(endDate==null)
		{
			edate = new Date(currentTime - 24 * 60 * 60 * 1000);
			endDate = df.format(edate).toString();
		}
		if (startDate==null) {
			startDate = endDate;
		}
		if(startDate.equalsIgnoreCase(endDate))
		{
			startDate = startDate + " 00:00";
			endDate = endDate + " 23:55";
			
		}
		
		
		if(routeid == null){
			routeid = "";
		}
		if(scp == null){
			scp = "";
		}
		Date sDate = null, eDate = null;
		try {
			sDate = dfull.parse(startDate);
			eDate = dfull.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		hrIpbbFw = hrIpbbFwDAO.filter(dfull.format(sDate), dfull.format(eDate),routeid, scp);
		List<Route> routeList = routeDao.getRouteName();
		List<Route> scpList = routeDao.getSPCName();
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		
		model.addAttribute("title",title);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("routeList", routeList);
		model.addAttribute("scpList", scpList);
		model.addAttribute("routeid", routeid);
		model.addAttribute("scp", scp);
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "hrIpbbFw"));
		model.addAttribute("hrIpbbFw", hrIpbbFw);
		
		return new ModelAndView("hrIpbbFwList");
	}

}
