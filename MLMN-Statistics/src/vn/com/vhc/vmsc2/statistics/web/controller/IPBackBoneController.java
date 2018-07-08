package vn.com.vhc.vmsc2.statistics.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.vmsc2.statistics.dao.HIpbbBwlistDAO;
import vn.com.vhc.vmsc2.statistics.dao.HighlightConfigDAO;
import vn.com.vhc.vmsc2.statistics.dao.VDyIpbbInternetDAO;
import vn.com.vhc.vmsc2.statistics.dao.VDyIpbbLinkDAO;
import vn.com.vhc.vmsc2.statistics.dao.VHrIpbbInternetDAO;
import vn.com.vhc.vmsc2.statistics.dao.VHrIpbbLinkDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpDyIpbbInternetDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpHrIpbbInternetDAO;
import vn.com.vhc.vmsc2.statistics.dao.VRpipbbdataDAO;
import vn.com.vhc.vmsc2.statistics.dao.VrpMnIpbbLink1DAO;
import vn.com.vhc.vmsc2.statistics.dao.VrpMnIpbbLink2DAO;
import vn.com.vhc.vmsc2.statistics.domain.HIpbbBwlist;
import vn.com.vhc.vmsc2.statistics.domain.HighlightConfig;
import vn.com.vhc.vmsc2.statistics.domain.VDyIpbbInternet;
import vn.com.vhc.vmsc2.statistics.domain.VDyIpbbLink;
import vn.com.vhc.vmsc2.statistics.domain.VHrIpbbInternet;
import vn.com.vhc.vmsc2.statistics.domain.VHrIpbbLink;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyIpbbInternet;
import vn.com.vhc.vmsc2.statistics.domain.VRpHrIpbbInternet;
import vn.com.vhc.vmsc2.statistics.domain.VRpipbbdata;
import vn.com.vhc.vmsc2.statistics.domain.VrpMnIpbbLink1;
import vn.com.vhc.vmsc2.statistics.domain.VrpMnIpbbLink2;
import vn.com.vhc.vmsc2.statistics.web.filter.CellFilter;
import vn.com.vhc.vmsc2.statistics.web.filter.IPBBFilter;
import vn.com.vhc.vmsc2.statistics.web.utils.Chart;
import vn.com.vhc.vmsc2.statistics.web.utils.DateTimeUtils;
import vn.com.vhc.vmsc2.statistics.web.utils.Helper;

@Controller
@RequestMapping("/report/core/ip-backbone/*")
public class IPBackBoneController extends BaseController{
	@Autowired
	private VRpipbbdataDAO vipbbdataDao;
	@Autowired
	private VDyIpbbLinkDAO vdylinkDao;
	@Autowired
	private VDyIpbbInternetDAO vdyinternetDao;
	@Autowired
	private VHrIpbbInternetDAO vhrinternetDao;
	@Autowired
	private VHrIpbbLinkDAO vhrlinkDao;
	@Autowired
	private HIpbbBwlistDAO IpbbBwlistDao;
	@Autowired
	private HighlightConfigDAO highlightConfigDao;
	@Autowired
	private VRpDyIpbbInternetDAO vRpDyIpbbInternetDAO;
	@Autowired
	private VRpHrIpbbInternetDAO vRpHrIpbbInternetDAO;
	@Autowired
	private VrpMnIpbbLink1DAO MnIpbbLink1Dao;
	@Autowired
	private VrpMnIpbbLink2DAO MnIpbbLink2Dao;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy HH:mm") ;
	
	@RequestMapping("ipbb-data")
	public String showReport(@RequestParam(required = false) String function,
			@RequestParam(required = false) String module,
			@RequestParam(required = false) String direction, 
			@RequestParam(required = false) Date startDate,
			@RequestParam(required = false) Date endDate,
			@ModelAttribute("filter") CellFilter filter, Model model, HttpServletRequest request) {
		long currentTime = System.currentTimeMillis();
		if(function==null)
			function ="";
		if(module==null)
			module ="";
		
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		if(direction == null){
			direction = "";
		}
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("direction", direction);
		/*List<HIpbbBwlist> directionList = IpbbBwlistDao.getAllDirection();*/
		List<HIpbbBwlist> directionGrapList = new ArrayList<HIpbbBwlist>();
		String strTitle = "";
		if(module.contains("nix"))
		{
			directionGrapList = IpbbBwlistDao.getDirectInternet("_Delay","nix");
			strTitle = "NIX";
		}
		else if (module.contains("ixp"))
		{
			directionGrapList = IpbbBwlistDao.getDirectInternet("_Delay","ixp");
			strTitle = "IXP";
		}
		else 
		{
			directionGrapList = IpbbBwlistDao.getDirectInternet("_Delay","");
		}
		 
		model.addAttribute("directionList", directionGrapList);
		if(function.equals("list"))
		{
			String strStartDate = df.format(startDate);
			String strEndDate = df.format(endDate); 
			List<VRpipbbdata> vipbbdata = vipbbdataDao.getList(module,direction,strStartDate, strEndDate);

			model.addAttribute("vipbbdata", vipbbdata);
		
			List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
			model.addAttribute("highlight", Helper.highLight(highlightConfigList, "user"));
		}
		if(function.equals("grap"))
		{
			/* Chart Start */
			long time = endDate.getTime();
			DateTime dt = new DateTime(time); 
			Date startDay = dt.minusDays(30).toDate();
			
		
			Map<String, List<Float>> series = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> seriesOut = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> seriesInternet = new LinkedHashMap<String, List<Float>>();
			Map<String, List<Float>> seriesOutInternet = new LinkedHashMap<String, List<Float>>();
			List<String> categories = new ArrayList<String>();
			String chartArea = "", chartAreaInternet = "";
			String chartAreaOut = "", chartAreaOutInternet = "";
			String internet = "internet";
			
			
			long time2 = startDay.getTime();
			DateTime dt2 = new DateTime(time2);
			for(int i = 0; i < directionGrapList.size(); i++)
			{
					List<Float> inMaxUtil = new ArrayList<Float>();
					List<Float> outMaxUtil = new ArrayList<Float>();
					
					List<Float> inMbs = new ArrayList<Float>();
					List<Float> outMbs = new ArrayList<Float>();
					
					for(int j = 0; j < 30; j++){
						Date d = dt2.plusDays(j).toDate();
						String tempDate = df.format(d);
						List<VRpipbbdata> ipbbListChart = vipbbdataDao.filter(module,directionGrapList.get(i).getDirection().toString(),tempDate, tempDate);
						if(ipbbListChart.size()==0)
						{
							inMaxUtil.add(Float.parseFloat("0"));
							outMaxUtil.add(Float.parseFloat("0"));
							inMbs.add(Float.parseFloat("0"));
							outMbs.add(Float.parseFloat("0"));
						}
						else
						{
							if(ipbbListChart.get(0).getDirection().toLowerCase().contains(internet))
							{
								inMbs.add(round(ipbbListChart.get(0).getInKbitSecond()/1000,2));
								outMbs.add(round(ipbbListChart.get(0).getOutKbitSecond()/1000,2));
								
							}
							else
							{
								inMaxUtil.add(ipbbListChart.get(0).getInMaxUtilization());
								outMaxUtil.add(ipbbListChart.get(0).getOutMaxUtilization());
							}
						}
					}
					
					if(directionGrapList.get(i).getDirection().toLowerCase().contains(internet))
					{
						seriesInternet.put(directionGrapList.get(i).getDirection(), inMbs);
						seriesOutInternet.put(directionGrapList.get(i).getDirection(), outMbs);
					}
					else
					{
						series.put(directionGrapList.get(i).getDirection(), inMaxUtil);
						seriesOut.put(directionGrapList.get(i).getDirection(), outMaxUtil);
					}
					
					
				
				
			}
			for(int j = 0; j < 30; j++){
				Date d = dt2.plusDays(j).toDate();
				categories.add(df.format(d));
			}
			chartArea += Chart.basicLineIPBB("inMaxUtilizationChart", "Traffic In Max Utilization(%) Chart","(%)", categories, series );
			chartAreaOut += Chart.basicLineIPBB("outMaxUtilizationChart", "Traffic Out Max Utilization(%) Chart","(%)", categories, seriesOut  );	
			chartAreaInternet += Chart.basicLineIPBB("inMaxUtilInternet", strTitle + " Traffic In Mb/s (sum) Chart","Mbit/s", categories, seriesInternet );
			chartAreaOutInternet += Chart.basicLineIPBB("outMaxUtilInternet", strTitle + " Traffic Out Mb/s (sum) Chart","Mbit/s", categories, seriesOutInternet );
				
			if(module.toLowerCase().contains(internet))
			{
				model.addAttribute("chartdivInternet", chartAreaInternet);
				model.addAttribute("chartDivOutInternet", chartAreaOutInternet);
			}
			else
			{
				model.addAttribute("chartdiv", chartArea);
				model.addAttribute("chartDivOut", chartAreaOut);
			}
			
		 }
		model.addAttribute("function", function);
		model.addAttribute("module", module);
		return "ipbbDataList";
	}


	@RequestMapping("dy-link")
	public ModelAndView Show(
			 
			 @RequestParam(required = false) Date startDate,
			 @RequestParam(required = false) Date endDate,
			 @RequestParam(required = false) String link,
			 @RequestParam(required = false) String direction,
			 @RequestParam(required = false) String function,
			
			 Model model) {
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		if(link == null){
			link = "";
		}
		if(direction == null){
			direction = "";
		}
		if(function == null)
			function = "";
		List<HIpbbBwlist> directionList = IpbbBwlistDao.getAllDirection();
		model.addAttribute("directionList", directionList);
		
		List<HIpbbBwlist> linkList = IpbbBwlistDao.getAllLink();
		model.addAttribute("linkList", linkList);
		
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("link", link);
		model.addAttribute("direction", direction);
		String titleInternetInternational = "IPBB DATA "+ direction.toUpperCase() + " INTERNATIONAL";
		String titleInternet = "IPBB DATA "+ direction.toUpperCase()  + " NATIONNAL";
		if(function.equals("badlink"))
		{
			//List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
			//model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vDyIpbbLink"));
			List<HighlightConfig> highlightConfigList = highlightConfigDao.getByKey("IPBB_UTILIZATION");
			String highlight = Helper.to_mau_link_tai(highlightConfigList, "vDyIpbbLink");
			model.addAttribute("highlight", highlight);
			
			List<VDyIpbbLink> vDyIpbbLink = vdylinkDao.filterBadLink(df.format(startDate), df.format(endDate),link,direction);
			model.addAttribute("vDyIpbbLink", vDyIpbbLink);
			model.addAttribute("function", function);
			model.addAttribute("IPBB","E");
		}
		
		else
		{
			if(direction.contains("Internet"))
			{
				model.addAttribute("titleInternetInternational", titleInternetInternational);
				model.addAttribute("titleInternet", titleInternet);
				List<VDyIpbbInternet> vdyinternet = vdyinternetDao.filter(df.format(startDate), df.format(endDate),link, direction);
				List<VDyIpbbInternet> vdyinternet2 = vdyinternetDao.filter2(df.format(startDate), df.format(endDate),link, direction);
				List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
				model.addAttribute("high", Helper.highLight(highlightConfigList, "IXP"));
				List<HighlightConfig> highlightConfig = highlightConfigDao.getAll();
				model.addAttribute("highlight", Helper.highLight(highlightConfig, "NIX"));
				model.addAttribute("vdyinternet", vdyinternet);
				model.addAttribute("vdyinternet2", vdyinternet2);
				
				model.addAttribute("IPBB","N");
				
			}
			else
			{
				
				List<VDyIpbbLink> vdylink = vdylinkDao.filter(df.format(startDate), df.format(endDate),link,direction);
				model.addAttribute("vdylink", vdylink);
				List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
				model.addAttribute("highlight", Helper.highLight(highlightConfigList, "item"));
				model.addAttribute("IPBB","Y");
			}
		}
		
		
		
		return new ModelAndView("dyIpbbLinkList");
	}
	
	@RequestMapping("hr-internet")
	public String Showreport(
			 @ModelAttribute("filter") IPBBFilter filter,
			 @RequestParam(required = false) String startDate,
			 @RequestParam(required = false) String endDate,
			 @RequestParam(required = false) String link,
			 @RequestParam(required = false) String direction,
			
			 Model model) {
		
		if(link == null){
			link = "";
		}
		if(direction == null){
			direction = "";
		}
		
		List<VHrIpbbInternet> vhrinternet = null;
		if ((endDate!=null && !endDate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy HH:mm", endDate)==false)||(startDate!=null && !startDate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy HH:mm", startDate)==false))
		{
				vhrinternet = null;
				model.addAttribute("vhrinternet", vhrinternet);
		}else{
			if (startDate == null ||startDate.equals("")|| DateTimeUtils.isValid("dd/MM/yyyy HH:mm", startDate)==false) {
				Calendar cal = Calendar.getInstance();	
				if(endDate!=null && !endDate.equals("") && DateTimeUtils.isValid("dd/MM/yyyy HH:mm", endDate)==true)
				{
					cal.add(Calendar.DAY_OF_MONTH, -1);
				}
				else
				{
					cal.setTime(new Date());
					cal.add(Calendar.DAY_OF_MONTH, -1);
				}
				startDate = df_full.format(cal.getTime());
			}
			filter.setStartDate(startDate);
			
			if (endDate == null || endDate.equals("")|| DateTimeUtils.isValid("dd/MM/yyyy HH:mm", endDate)==false) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_MONTH, 1);
				endDate = df_full.format(cal.getTime());
			}
			filter.setEndDate(endDate);
			
			vhrinternet = vhrinternetDao.filter(filter);	
		
		}
		vhrinternet = vhrinternetDao.filter(filter);	
		if(link.contains("IXP")){
			vhrinternet = vhrinternetDao.filter(filter);
			model.addAttribute("vhrinternet", vhrinternet);
			List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
			model.addAttribute("highlight", Helper.highLight(highlightConfigList, "IXP"));
			model.addAttribute("HR", "N");
		}	
		else
		{
			vhrinternet = vhrinternetDao.filter(filter);
			model.addAttribute("vhrinternet2", vhrinternet);
			List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
			model.addAttribute("highlight", Helper.highLight(highlightConfigList, "NIX"));
			model.addAttribute("HR", "Y");
		}
		
		List<HIpbbBwlist> directionList = IpbbBwlistDao.getAllDirection();
		model.addAttribute("directionList", directionList);
		
		List<HIpbbBwlist> linkList = IpbbBwlistDao.getAllLink();
		model.addAttribute("linkList", linkList);
		
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("link", link);
		model.addAttribute("direction", direction);
		return "hrIpbbInternet";
	}
	
	@RequestMapping("hr-link")
	public String filter(
			 @ModelAttribute("filter") IPBBFilter ipbb,
			 @RequestParam(required = false) String startDate,
			 @RequestParam(required = false) String endDate,
			 @RequestParam(required = false) String link,
			 @RequestParam(required = false) String direction,
			 Model model, HttpServletRequest request) {
		
		if(link == null){
			link = "";
		}
		if(direction == null){
			direction = "";
		}
		
		List<VHrIpbbLink> vhrlink = null;
		if(startDate.equalsIgnoreCase(endDate))
		{
			startDate = startDate + " 00:00";
			endDate = endDate + " 23:59";
			
		}
		
	
		ipbb.setStartDate(startDate);
		ipbb.setEndDate(endDate);
		ipbb.setDirection(direction);
		ipbb.setLink(link);
		
		vhrlink = vhrlinkDao.filter(ipbb);
		List<HIpbbBwlist> directionList = IpbbBwlistDao.getAllDirection();
		List<HIpbbBwlist> linkList = IpbbBwlistDao.getAllLink();
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		
		model.addAttribute("linkList", linkList);
		model.addAttribute("directionList", directionList);
		model.addAttribute("vhrlink", vhrlink);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("link", link);
		model.addAttribute("direction", direction);
		
		
		
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "user"));	
	
		return "hrIpbbLink";
	}
	 
	@RequestMapping("dy_delay_internet")
	public ModelAndView ShowReport(
			 
			 @RequestParam(required = false) Date startDate,
			 @RequestParam(required = false) Date endDate,
			 @RequestParam(required = false) String link,
			 @RequestParam(required = false) String direction,
			
			 Model model) {
		String title = "IP BACKBONE DELAY HƯỚNG INTERNET";
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		if(link == null){
			link = "";
		}
		if(direction == null){
			direction = "";
		}
		
		
		List<HIpbbBwlist> directionList = IpbbBwlistDao.getDirectInternet();
		List<HIpbbBwlist> linkList = IpbbBwlistDao.getLinkInternet();
		
		List<VRpDyIpbbInternet> vRpDyIpbbInternet = vRpDyIpbbInternetDAO.filter(df.format(startDate), df.format(endDate),link, direction);
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		
		model.addAttribute("title",title);
		model.addAttribute("directionList", directionList);
		model.addAttribute("linkList", linkList);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("link", link);
		model.addAttribute("direction", direction);
		model.addAttribute("highlight", Helper.highLightKpis(highlightConfigList, "vRpDyIpbbInternet"));
		model.addAttribute("vRpDyIpbbInternet", vRpDyIpbbInternet);
		
		
		/* Chart Start */
		long time = endDate.getTime();
		DateTime dt = new DateTime(time- 24 * 60 * 60 * 1000);
		Date destDay = new Date(time - 24 * 60 * 60 * 1000);
		Date startDay = dt.minusDays(30).toDate();
		
	
		Map<String, List<String>> seriesLink1 = new LinkedHashMap<String, List<String>>();
		Map<String, List<String>> seriesLink2 = new LinkedHashMap<String, List<String>>();
		List<String> categories = new ArrayList<String>();
		String chartAreaLink1 = "";
		String chartAreaLink2 = "";
		for(int i = 0; i < directionList.size(); i++)
		{
			
			List<VRpDyIpbbInternet> dyIpbbInternet = vRpDyIpbbInternetDAO.filter(df.format(startDay), df.format(destDay),"", directionList.get(i).getDirection().toString());
			List<String> link1List = new ArrayList<String>();
			List<String> link2List = new ArrayList<String>();
			for(VRpDyIpbbInternet record : dyIpbbInternet){
				if( record.getDelayMs1Max() == null)
					record.setDelayMs1Max(Float.parseFloat("0"));
				link1List.add(record.getDelayMs1Max().toString());
				link2List.add(record.getDelayMs2Max().toString());
			
			}
			seriesLink1.put(directionList.get(i).getDirection(), link1List);
			seriesLink2.put(directionList.get(i).getDirection(), link2List);
			
		}
		List<VRpDyIpbbInternet> ipbbDay = vRpDyIpbbInternetDAO.filter1( df.format(startDay), df.format(destDay));
		for(int j = 0; j < ipbbDay.size(); j++){
			categories.add(df.format(ipbbDay.get(j).getDay()));
		}
		
		chartAreaLink1 += Chart.timebasicLine("chartMaxLink1", "Chart MAX DELAYTIME LINK1 (ms)", categories, seriesLink1,"(ms)");
		chartAreaLink2 += Chart.timebasicLine("chartMaxLink2", "Chart MAX DELAYTIME LINK2 (ms)", categories, seriesLink2,"(ms)");		
			
		model.addAttribute("chartAreaLink1", chartAreaLink1);
		model.addAttribute("chartAreaLink2", chartAreaLink2);
		//Monthly report 
		int startMonth = dt.getMonthOfYear();
		int endMonth = startMonth;
		int startYear = dt.getYear();
		int endYear = startYear;
		List<VrpMnIpbbLink1> MnIpbblink1 = MnIpbbLink1Dao.filter(startMonth,startYear, endMonth, endYear);
		List<VrpMnIpbbLink2> MnIpbblink2 = MnIpbbLink2Dao.filter(startMonth,startYear, endMonth, endYear);
		
		 
		model.addAttribute("highlight2", Helper.highLightIPBB(highlightConfigList, "MnIpbblink1"));
		model.addAttribute("highlight3", Helper.highLightIPBB(highlightConfigList, "MnIpbblink2"));

		
		model.addAttribute("MnIpbblink1", MnIpbblink1);
		model.addAttribute("MnIpbblink2", MnIpbblink2); 

		
		return new ModelAndView("dyIpbbInternetList");
	}
	
	@RequestMapping("hr_delay_internet")
	public ModelAndView HourlyReport(
			 @ModelAttribute("filter") IPBBFilter ipbb,
			 @RequestParam(required = false) String startDate,
			 @RequestParam(required = false) String endDate,
			 @RequestParam(required = false) String link,
			 @RequestParam(required = false) String direction,
			 Model model, HttpServletRequest request) {
		
		String title = "IP BACKBONE DELAY HƯỚNG INTERNET";
		List<VRpHrIpbbInternet> vRpHrIpbbInternet = null;
		Date edate ;
		long currentTime = System.currentTimeMillis();
		if (endDate.equalsIgnoreCase("")) {
			edate = new Date(currentTime - 24 * 60 * 60 * 1000);
			endDate = df.format(edate).toString();
		}
		if (startDate.equalsIgnoreCase("")) {
			startDate = endDate;
		}
		if(startDate.equalsIgnoreCase(endDate))
		{
			startDate = startDate + " 00:00";
			endDate = endDate + " 23:59";
			
		}
		
		
		if(link == null){
			link = "";
		}
		if(direction == null){
			direction = "";
		}
		ipbb.setStartDate(startDate);
		ipbb.setEndDate(endDate);
		ipbb.setDirection(direction);
		ipbb.setLink(link);
		
		vRpHrIpbbInternet = vRpHrIpbbInternetDAO.filter(ipbb);
		List<HIpbbBwlist> directionList = IpbbBwlistDao.getDirectInternet();
		List<HIpbbBwlist> linkList = IpbbBwlistDao.getLinkInternet();
		List<HighlightConfig> highlightConfigList = highlightConfigDao.getAll();
		
		model.addAttribute("title",title);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("directionList", directionList);
		model.addAttribute("linkList", linkList);
		model.addAttribute("link", link);
		model.addAttribute("direction", direction);
		model.addAttribute("highlight", Helper.highLight(highlightConfigList, "vRpHrIpbbInternet"));
		model.addAttribute("vRpHrIpbbInternet", vRpHrIpbbInternet);
		
		return new ModelAndView("hrIpbbInternetList");
	}
	public static float round(float value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (float) tmp / factor;
	}
}
