package vn.com.vhc.vmsc2.statistics.web.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.ui.ModelMap;

import vn.com.vhc.vmsc2.statistics.domain.Bsc;
import vn.com.vhc.vmsc2.statistics.domain.Bsc3G;
import vn.com.vhc.vmsc2.statistics.domain.HIpbbBwlist;
import vn.com.vhc.vmsc2.statistics.domain.HProvincesCode;
import vn.com.vhc.vmsc2.statistics.domain.Msc;
import vn.com.vhc.vmsc2.statistics.domain.Sgsn;
/**
 * 
 * @author Mr. ThangPX
 * @return ModelAddtributes Check Level Daily, Hourly, Weekly, Monthly
 */
public class ModelAddtributes {

	private static DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	public static String checkWeek(ModelMap model, Integer startWeek, Integer endWeek, Integer startYear, Integer endYear) {
		DateTime dt = new DateTime();
		if (endWeek == null) {
			dt = dt.minusWeeks(1);
			endWeek = dt.getWeekOfWeekyear();
			endYear = dt.getYear();
		}
		if (startWeek == null) {
			startWeek = endWeek;
			startYear = endYear;
		}
		model.addAttribute("startWeek", startWeek);
		model.addAttribute("endWeek", endWeek);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		model.addAttribute("title", "WEEKLY");
		model.addAttribute("level", "wk");
		model.addAttribute("wk", "ui-tabs-selected");
		return startWeek + ";" + endWeek + ";" + startYear + ";" + endYear;
	}
	public static String checkMonth(ModelMap model,Integer startMonth, Integer endMonth, Integer startYear, Integer endYear) {
		Calendar cal = Calendar.getInstance();
		/*Calendar cal1 = Calendar.getInstance();*/
		if(endMonth==null){
			cal.add(Calendar.MONTH,-1);
			endMonth = cal.get(Calendar.MONTH)+1;
			endYear = cal.get(Calendar.YEAR);
		}
		
		if (startMonth == null) {
			startMonth = endMonth;
			startYear = endYear;
		}
		String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		model.addAttribute("monthList", monthList);
		model.addAttribute("startMonth", startMonth);
		model.addAttribute("endMonth", endMonth);
		model.addAttribute("startYear", startYear);
		model.addAttribute("endYear", endYear);
		model.addAttribute("level", "mn");
		model.addAttribute("mn", "ui-tabs-selected");
		model.addAttribute("title", "MONTHLY");
		return startMonth + ";" + endMonth + ";" + startYear + ";" + endYear;
		
	}
	public static String checkDate(ModelMap model, Date startDate, Date endDate) {
		long currentTime = System.currentTimeMillis();		
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("title", "DAILY");
		model.addAttribute("level", "dy");
		model.addAttribute("dy", "ui-tabs-selected");
		
		return df.format(startDate)+ ";" + df.format(endDate);
	}
	public static String checkDate2(ModelMap model, Date startDate, Date endDate) {
		long currentTime = System.currentTimeMillis();		
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("title", "DAILY");
		model.addAttribute("level", "dy");
		model.addAttribute("dy", "ui-tabs-selected");
		
		return df.format(startDate)+ ";" + df.format(endDate);
	}
	public static String checkHour(ModelMap model, Date startDate, String startHour, String endHour)
	{
		long currentTime = System.currentTimeMillis();		
		
		if (startDate == null) {
			startDate = new Date(currentTime);
		}
		
		if (endHour == null) {
			endHour = "23";
		} else {
			if(endHour.indexOf(":") >0)
				endHour = endHour.substring(0, endHour.indexOf(":"));
			else
				endHour = "0";
			}
		if (startHour == null) {
			startHour = "0";
		} else {	
			if(startHour.indexOf(":") >0)
				startHour = startHour.substring(0, startHour.indexOf(":"));
			else
				startHour = "0";
		}
	    String[] hourList = {"0:00", "1:00", "2:00", "3:00", "4:00", "5:00", "6:00", "7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};
	    model.addAttribute("title", "HOURLY");
		model.addAttribute("level", "hr");
		model.addAttribute("hr", "ui-tabs-selected");
	    model.addAttribute("hourList", hourList);
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("startHour", startHour + ":00");
		model.addAttribute("endHour", endHour + ":00");
		return df.format(startDate) + ";" + startHour +";" + endHour;
	}
	public static String checkMsc(ModelMap model, List<Msc> mscList,String mscid, String linkReport)
	{
		if(mscid == null){
			mscid = "";
		}
		model.addAttribute("mscList", mscList);
		model.addAttribute("mscid", mscid);
		model.addAttribute("linkReport", linkReport);
		model.addAttribute("nodeType", "Mscid");
		return mscid;
	}
	public static String checkSgsn(ModelMap model, List<Sgsn> sgsnList,String sgsnid, String linkReport)
	{
		if(sgsnid == null){
			sgsnid = "";
		}
		model.addAttribute("sgsnList", sgsnList);
		model.addAttribute("sgsnid", sgsnid);
		model.addAttribute("linkReport", linkReport);
		model.addAttribute("nodeType", "Sgsnid");
		return sgsnid;
	}
	public static String checkBscid(ModelMap model,  List<Bsc> bscList,  String bscid,String linkReport)
	{
		
		if(bscid == null)
			bscid = "";
		model.addAttribute("bscid", bscid);
		model.addAttribute("bscList", bscList);
		model.addAttribute("linkReport", linkReport);
		model.addAttribute("nodeType", "Bscid");
		model.addAttribute("objectname", "Y");
		return bscid;
	}
	public static String checkRnc(ModelMap model,  List<Bsc3G> bscList,  String bscid,String linkReport)
	{
		
		if(bscid == null)
			bscid = "";
		model.addAttribute("bscid", bscid);
		model.addAttribute("bscList", bscList);
		model.addAttribute("linkReport", linkReport);
		model.addAttribute("nodeType", "Bscid");
		model.addAttribute("objectname", "Y");
		return bscid;
	}
	public static String checkIpbb(ModelMap model, List<HIpbbBwlist> routeList,String routeid, String linkReport)
	{
		if(routeid == null){
			routeid = "";
		}
		model.addAttribute("routeList", routeList);
		model.addAttribute("routeid", routeid);
		model.addAttribute("linkReport", linkReport);
		model.addAttribute("nodeType", "ipbb");
		return routeid;
	}
	public static String checkInterface(ModelMap model,String interfaceid)
	{
		
		if(interfaceid == null){
			interfaceid = "";
		}
		model.addAttribute("interfaceid", interfaceid);
		return interfaceid;
	}
	public static String checkRegion(ModelMap model,  List<HProvincesCode> regionList,  String region, String linkReport)
	{
		
		if(region == null)
			region = "";
		model.addAttribute("region", region);
		model.addAttribute("regionList", regionList);
		model.addAttribute("linkReport", linkReport);
		return region;
	}
	public static String checkRouteid(ModelMap model,String routeid)
	{
		
		if(routeid == null)
			routeid = "";
		model.addAttribute("routeid", routeid);
		model.addAttribute("nodeType", "Routeid");
		return routeid;
	}
	public static String checkRouteName(ModelMap model,String routeName)
	{
		
		if(routeName == null)
			routeName = "";
		model.addAttribute("routename", routeName);
		return routeName;
	}
	public static String checkLinkid(ModelMap model,String linkid)
	{
		
		if(linkid == null)
			linkid = "";
		model.addAttribute("nodeType", "s7link");
		model.addAttribute("linkid", linkid);
		model.addAttribute("node", "Linkid");
		return linkid;
	}
	public static String checkLinksetid(ModelMap model,String linksetid)
	{
		
		if(linksetid == null)
			linksetid = "";
		model.addAttribute("nodeType", "s7link");
		model.addAttribute("linksetid", linksetid);
		model.addAttribute("node", "Linksetid");
		return linksetid;
	}
	public static String checkLinkSetName(ModelMap model,String linksetName)
	{
		
		if(linksetName == null)
			linksetName = "";
		model.addAttribute("linksetname", linksetName);
		return linksetName;
	}
	public static String checkType(ModelMap model,String type)
	{
		
		if(type == null)
			type = "";
		model.addAttribute("type", type);
		return type;
	}
	public static String checkRangeHour(ModelMap model, Date startDate, Date endDate, String startHour, String endHour){
		long currentTime = System.currentTimeMillis();
		if (endDate == null) {
			endDate = new Date(currentTime - 24 * 60 * 60 * 1000);
		}
		if (startDate == null) {
			startDate = endDate;
		}
		if (endHour == null) {
			endHour = "23";
		} else {
			endHour = endHour.substring(0, endHour.indexOf(":"));
		}
		if (startHour == null) {
			startHour = "0";
		} else {
			startHour = startHour.substring(0, startHour.indexOf(":"));
		}
		model.addAttribute("startDate", df.format(startDate));
		model.addAttribute("endDate", df.format(endDate));
		model.addAttribute("startHour", startHour + ":00");
		model.addAttribute("endHour", endHour + ":00");
		return df.format(startDate) + ";" + df.format(endDate) + ";" + startHour +";" + endHour;
	}
}
