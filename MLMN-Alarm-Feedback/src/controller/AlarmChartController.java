package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vo.AlDyWarningByAlarmClass;
import vo.AlHrWarningByAlarmClass;
import vo.CConfigAlarmType;
import vo.CHighlightConfigs;
import vo.CTableConfig;
import vo.CountAlarmLog;
import vo.DyAlDetailFinish;
import vo.DyAlDetailNonFinish;
import vo.DyAlDetailTotalQuality;
import vo.HrAlDetaiTotalQuality;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.alarm.utils.Chart;
import vo.alarm.utils.DateTimeUtils;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.HelpTableConfigs;

import com.google.gson.Gson;

import dao.AlShiftDAO;
import dao.CConfigAlarmTypeDAO;
import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.DyAlDetailFinishDAO;
import dao.DyAlDetailNonFinishDAO;
import dao.DyAlDetailTotalQualityDAO;
import dao.HrAlDetaiTotalQualityDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUserAreaDAO;
import dao.SysUserEquipmentNameDAO;
import dao.SysUsersDAO;

@Controller
@RequestMapping("/chart/*")
public class AlarmChartController extends BaseController {

	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;

	@Autowired
	private SysUserEquipmentNameDAO sysUserEquipmentNameDAO;
	
	@Autowired
	private SysUserAreaDAO sysUserAreaDAO;
	
	@Autowired 
	private AlShiftDAO alShiftDAO;
	
	@Autowired 
	private SysUsersDAO sysUsersDao;
	
	@Autowired
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
	@Autowired
	private DyAlDetailTotalQualityDAO dyAlDetailTotalQualityDAO;
	
	@Autowired
	private DyAlDetailFinishDAO dyAlDetailFinishDAO;
	
	
	@Autowired
	private CConfigAlarmTypeDAO cConfigAlarmTypeDAO;
	
	@Autowired
	private HrAlDetaiTotalQualityDAO hrAlDetaiTotalQualityDAO;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
/*Tong hop so luong canh bao theo muc do canh bao
 * @param sdate: Thoi gian bat dau
 * @param edate: Thoi gian ket thuc
 * @param network: Loai canh báo: 2G,3G,PS CORE, CS CORE
 * @param province: Tinh /thanh pho
 * @param vendor: Nha cung cap
 * @param team: To
 * @param district: Quan huyen
 * @param bscid: Nhieu bsc
 * @param type: NE,TEAM,PROVINCE
 * function : total,finishlate,nonfinish
*/
	@RequestMapping(value = "severity/{function}")
	public ModelAndView list(
			@RequestParam(required = false) String sdate,
			@RequestParam(required = false) String edate,
			@RequestParam(required = false) String network,
			@RequestParam(required = false) String province,
			@RequestParam(required = false) String vendor,
			@RequestParam(required = false) String team,
			@RequestParam(required = false) String district,
			@RequestParam(required = false) String bscid,
			@RequestParam(required = true) String type,
			@RequestParam(required = false) String region,
			@PathVariable String function,
			Model model, HttpServletRequest request) throws ParseException {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SYS_PARAMETER> sysParemeterTitle = dyAlDetailTotalQualityDAO.titleForm("severity",function,type,null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		//Kiem tra dieu kien tim kiem 
		Calendar cal = Calendar.getInstance();	
		cal.setTime(new Date());
		String sdateChar ="";
		String edateChar="";
		String sdateCharLabel="";
		if (edate == null || edate.equals("")
				||(edate!=null && !edate.equals("") && DateTools
							.isValid("dd/MM/yyyy", edate)==false))
		{
			edate = df.format(cal.getTime());
		}
		
		if (sdate == null || sdate.equals("")
				||(sdate!=null && !sdate.equals("") && DateTools
							.isValid("dd/MM/yyyy", sdate)==false))
		{
			if (!function.equalsIgnoreCase("nonfinish"))
			{
				cal.add(Calendar.DATE, -1);
			}
			sdate = df.format(cal.getTime());
			cal.add(Calendar.DATE, -29);
			sdateChar = df2.format(cal.getTime());
			sdateCharLabel = df.format(cal.getTime());
		}
		else
		{
			Calendar cal2 = Calendar.getInstance();	
			cal2.setTime(df.parse(edate));
			cal2.add(Calendar.DATE, -30);
			sdateChar = df2.format(cal2.getTime());
			sdateCharLabel = df.format(cal2.getTime());
		}
		edateChar = df2.format(df.parse(edate));
		System.out.println("sdateChar:"+sdateChar);
		System.out.println("edateChar:"+edateChar);
		model.addAttribute("sdate", sdate);			
		model.addAttribute("edate", edate);
		
		
		if (bscid!=null)
		{
			bscid=bscid.replaceAll(" ", "");
		}
		else
		{
			bscid="";
		}
		
		if (vendor!=null)
		{
			vendor=vendor.trim();
		}
		else
		{
			vendor="";
		}
		
		if (province!=null)
		{
			province=province.trim();
		}
		else
		{
			province="";
		}
		
		if (team!=null)
		{
			team=team.trim();
		}
		else
		{
			team="";
		}
		
		if (district!=null)
		{
			district=district.trim();
		}
		else
		{
			district="";
		}
		
		if (network!=null&&network!=""&&network!="ALL")
		{
			network=network.trim();
		}
		else
		{
			network="2G";

		}
		
		if (region!=null)
		{
			region=region.trim();
		}
		else
		{
			region="";
		}
		//Lay danh sach province theo user
		List<String> provinceList = sysUserAreaDAO.getProvinceList(username);
		String provinceArray="var provinceList = new Array(";//Danh sach district cho cobobox
		String cn="";
		for (String dis : provinceList) {
			provinceArray = provinceArray + cn +"\""+dis+"\"";
			cn=",";
		}
		provinceArray = provinceArray+");";
		model.addAttribute("provinceList", provinceArray);
		
		if (type.equalsIgnoreCase("TEAM")&&province.equals("")&&provinceList.size()>0)		
		{
			province = provinceList.get(0);
		}
		//Lay danh sach district theo user
		List<String> districtList = sysUserAreaDAO.getDistrictList(province,team,username);
		String districtArray="var districtList = new Array(";//Danh sach district cho cobobox
		cn="";
		for (String dis : districtList) {
			districtArray = districtArray + cn +"\""+dis+"\"";
			cn=",";
		}
		districtArray = districtArray+");";
		model.addAttribute("districtList", districtArray);
		//Lay danh sach team theo user
		List<String> teamList = sysUserAreaDAO.getSubTeamList(province,username);
		String teamArray="var teamList = new Array(";//Danh sach district cho cobobox
		cn="";
		for (String dis : teamList) {
			teamArray = teamArray + cn +"\""+dis+"\"";
			cn=",";
		}
		teamArray = teamArray+");";
		model.addAttribute("teamList", teamArray);
		
		//Lay danh sach BSC theo user
		List<String> bscList = sysUserEquipmentNameDAO.getBSCList(network,username,vendor);
		String bscArray="var bscList = new Array(";//Danh sach bsc cho cobobox
		       
		cn="";
		for (String bsc : bscList) {
			bscArray = bscArray + cn +"\""+bsc+"\"";
			cn=",";
		}
		bscArray = bscArray+");";
		model.addAttribute("bscList", bscArray);
		//Lay du lieu bao cao so luong ve bieu do
		List<String> dataChartList = dyAlDetailTotalQualityDAO.getReportSeverityChart(type,sdateChar,edateChar,network,province,vendor,team,district,bscid,region,"A1",null,username,function);
		List<String> dataChartList2 = dyAlDetailTotalQualityDAO.getReportSeverityChart(type,sdateChar,edateChar,network,province,vendor,team,district,bscid,region,"A2",null,username,function);
		List<String> dataChartList3 = dyAlDetailTotalQualityDAO.getReportSeverityChart(type,sdateChar,edateChar,network,province,vendor,team,district,bscid,region,"A3",null,username,function);
		List<String> dataChartList4 = dyAlDetailTotalQualityDAO.getReportSeverityChart(type,sdateChar,edateChar,network,province,vendor,team,district,bscid,region,"A4",null,username,function);
		String chart="";
		String chart2="";
		String chart3="";
		String chart4="";
		if (type.equals("NE"))
		{
			
			//neu khong chon bsc thi lay danh sach tat ca bsc neu khong thi lay cac bsc duoc chon
			if (bscid!=null && !bscid.equals(""))
			{
				bscList = new ArrayList<String>();
				String[] bscL = bscid.split(",");
				for (String item : bscL) {
					bscList.add(item);
				}
				//model.addAttribute("bscL", bscL);	
			}
			// Severity A1
			chart = HelpTableConfigs.lineChart("severityA1","SEVERITY A1", sdateCharLabel+ " - "+edate,dataChartList,sdateChar,edateChar,"0","80", bscList,"10");
			// Severity A2
			chart2 = HelpTableConfigs.lineChart("severityA2","SEVERITY A2", sdateCharLabel+ " - "+edate,dataChartList2,sdateChar,edateChar,"0","80", bscList,"10");
			// Severity A3
			chart3 = HelpTableConfigs.lineChart("severityA3","SEVERITY A3", sdateCharLabel+ " - "+edate,dataChartList3,sdateChar,edateChar,"0","80", bscList,"10");
			// Severity A4
			chart4 = HelpTableConfigs.lineChart("severityA4","SEVERITY A4", sdateCharLabel+ " - "+edate,dataChartList4,sdateChar,edateChar,"0","80", bscList,"10");
			
			
		}
		if (type.equals("DISTRICT")||type.equals("TEAM"))
		{
			
			
			if (team!=null && !team.equals(""))
			{
				teamList = new ArrayList<String>();
				//remove nhung bsc khong duoc chon
				String[] teamL = team.split(",");
				for (String item : teamL) {
					teamList.add(item);
				}
				//model.addAttribute("bscL", bscL);	
			}
			
			//model.addAttribute("teamL", teamL);
			// Severity A1
			chart = HelpTableConfigs.lineChart("severityA1","SEVERITY A1", sdateCharLabel+ " - "+edate,dataChartList,sdateChar,edateChar,"0","80", teamList,"10");
			// Severity A2
			chart2 = HelpTableConfigs.lineChart("severityA2","SEVERITY A2", sdateCharLabel+ " - "+edate,dataChartList2,sdateChar,edateChar,"0","80", teamList,"10");
			// Severity A3
			chart3 = HelpTableConfigs.lineChart("severityA3","SEVERITY A3", sdateCharLabel+ " - "+edate,dataChartList3,sdateChar,edateChar,"0","80", teamList,"10");
			// Severity A4
			chart4 = HelpTableConfigs.lineChart("severityA4","SEVERITY A4", sdateCharLabel+ " - "+edate,dataChartList4,sdateChar,edateChar,"0","80", teamList,"10");
			
			if (district!=null && !district.equals(""))
			{
				districtList = new ArrayList<String>();
				String[] districtL = district.split(",");
				for (String item : districtL) {
					districtList.add(item);
				}
				//model.addAttribute("districtL", districtL);		
			}
			
			String chartdis;
			String chartdis2;
			String chartdis3;
			String chartdis4;
			List<String> dataChartListdis = dyAlDetailTotalQualityDAO.getReportSeverityChart("DISTRICT",sdateChar,edateChar,network,province,vendor,team,district,bscid,region,"A1",null,username,function);
			List<String> dataChartList2dis = dyAlDetailTotalQualityDAO.getReportSeverityChart("DISTRICT",sdateChar,edateChar,network,province,vendor,team,district,bscid,region,"A2",null,username,function);
			List<String> dataChartList3dis = dyAlDetailTotalQualityDAO.getReportSeverityChart("DISTRICT",sdateChar,edateChar,network,province,vendor,team,district,bscid,region,"A3",null,username,function);
			List<String> dataChartList4dis = dyAlDetailTotalQualityDAO.getReportSeverityChart("DISTRICT",sdateChar,edateChar,network,province,vendor,team,district,bscid,region,"A4",null,username,function);
			// Severity A1
			chartdis = HelpTableConfigs.lineChart("severityA1Dis","SEVERITY A1", sdateCharLabel+ " - "+edate,dataChartListdis,sdateChar,edateChar,"0","80", districtList,"10");
			// Severity A2
			chartdis2 = HelpTableConfigs.lineChart("severityA2Dis","SEVERITY A2", sdateCharLabel+ " - "+edate,dataChartList2dis,sdateChar,edateChar,"0","80", districtList,"10");
			// Severity A3
			chartdis3 = HelpTableConfigs.lineChart("severityA3Dis","SEVERITY A3", sdateCharLabel+ " - "+edate,dataChartList3dis,sdateChar,edateChar,"0","80", districtList,"10");
			// Severity A4
			chartdis4 = HelpTableConfigs.lineChart("severityA4Dis","SEVERITY A4", sdateCharLabel+ " - "+edate,dataChartList4dis,sdateChar,edateChar,"0","80", districtList,"10");
			model.addAttribute("severityA1Dis", chartdis);
			model.addAttribute("severityA2Dis", chartdis2);
			model.addAttribute("severityA3Dis", chartdis3);
			model.addAttribute("severityA4Dis", chartdis4);
		}
		
		if (type.equals("PROVINCE"))
		{
			if (province!=null && !province.equals(""))
			{
				provinceList = new ArrayList<String>();
				//remove nhung bsc khong duoc chon
				String[] provinceL = province.split(",");
				for (String item : provinceL) {
					provinceList.add(item);
				}
				//model.addAttribute("provinceL", provinceL);
			}
			// Severity A1
			chart = HelpTableConfigs.lineChart("severityA1","SEVERITY A1", sdateCharLabel+ " - "+edate,dataChartList,sdateChar,edateChar,"0","80", provinceList,"10");
			// Severity A2
			chart2 = HelpTableConfigs.lineChart("severityA2","SEVERITY A2", sdateCharLabel+ " - "+edate,dataChartList2,sdateChar,edateChar,"0","80", provinceList,"10");
			// Severity A3
			chart3 = HelpTableConfigs.lineChart("severityA3","SEVERITY A3", sdateCharLabel+ " - "+edate,dataChartList3,sdateChar,edateChar,"0","80", provinceList,"10");
			// Severity A4
			chart4 = HelpTableConfigs.lineChart("severityA4","SEVERITY A4", sdateCharLabel+ " - "+edate,dataChartList4,sdateChar,edateChar,"0","80", provinceList,"10");
			
		}
		if (type.equals("REGION"))
		{
			List<String> regionList = new ArrayList<String>();
			if (region!=null && !region.equals(""))
			{
				//remove nhung bsc khong duoc chon
				String[] regionL = region.split(",");
				for (String item : regionL) {
					regionList.add(item);
				}
			}
			else {
				List<SYS_PARAMETER> regionLt= sysParameterDao.getRegionList();
				for (SYS_PARAMETER sys_PARAMETER : regionLt) {
					regionList.add(sys_PARAMETER.getName());
				}
			}
			// Severity A1
			chart = HelpTableConfigs.lineChart("severityA1","SEVERITY A1", sdateCharLabel+ " - "+edate,dataChartList,sdateChar,edateChar,"0","80", regionList,"10");
			// Severity A2
			chart2 = HelpTableConfigs.lineChart("severityA2","SEVERITY A2", sdateCharLabel+ " - "+edate,dataChartList2,sdateChar,edateChar,"0","80", regionList,"10");
			// Severity A3
			chart3 = HelpTableConfigs.lineChart("severityA3","SEVERITY A3", sdateCharLabel+ " - "+edate,dataChartList3,sdateChar,edateChar,"0","80", regionList,"10");
			// Severity A4
			chart4 = HelpTableConfigs.lineChart("severityA4","SEVERITY A4", sdateCharLabel+ " - "+edate,dataChartList4,sdateChar,edateChar,"0","80", regionList,"10");
			
		}
		if (type.equals("ALL"))
		{
			List<String> itemList = new ArrayList<String>();
			itemList.add("ALL");
			
			// Severity A1
			chart = HelpTableConfigs.lineChart("severityA1","SEVERITY A1", sdateCharLabel+ " - "+edate,dataChartList,sdateChar,edateChar,"0","80", itemList,"10");
			// Severity A2
			chart2 = HelpTableConfigs.lineChart("severityA2","SEVERITY A2", sdateCharLabel+ " - "+edate,dataChartList2,sdateChar,edateChar,"0","80", itemList,"10");
			// Severity A3
			chart3 = HelpTableConfigs.lineChart("severityA3","SEVERITY A3", sdateCharLabel+ " - "+edate,dataChartList3,sdateChar,edateChar,"0","80", itemList,"10");
			// Severity A4
			chart4 = HelpTableConfigs.lineChart("severityA4","SEVERITY A4", sdateCharLabel+ " - "+edate,dataChartList4,sdateChar,edateChar,"0","80", itemList,"10");
			
		}
		//ve bieu do
		model.addAttribute("severityA1", chart);
		model.addAttribute("severityA2", chart2);
		model.addAttribute("severityA3", chart3);
		model.addAttribute("severityA4", chart4);
			
		model.addAttribute("bscid", bscid);
		model.addAttribute("vendor", vendor);
		model.addAttribute("function", function);
		model.addAttribute("province", province);
		model.addAttribute("network",network);
		model.addAttribute("team", team);
		model.addAttribute("district", district);
		model.addAttribute("type", type);
		model.addAttribute("username", username);
		model.addAttribute("region", region);
		
		return new ModelAndView("jspalarm/chart/chartSeverity");
	}

	/*Tong hop so luong canh bao theo loai canh bao
		 * @param sdate: Thoi gian bat dau
		 * @param edate: Thoi gian ket thuc
		 * @param network: Loai canh báo: 2G,3G,PS CORE, CS CORE
		 * @param province: Tinh /thanh pho
		 * @param vendor: Nha cung cap
		 * @param team: To
		 * @param district: Quan huyen
		 * @param bscid: Nhieu bsc
		 * @param type: ALARMID,NE,PROVINCE,TEAM,DISTRICT
		 * function : total,finishlate,nonfinish
		*/
			@RequestMapping(value = "alarmtype/{function}")
			public ModelAndView alarmTypelist(
					@RequestParam(required = false) String sdate,
					@RequestParam(required = false) String edate,
					@RequestParam(required = false) String network,
					@RequestParam(required = false) String province,
					@RequestParam(required = false) String vendor,
					@RequestParam(required = false) String bscid,
					@RequestParam(required = false) String team,
					@RequestParam(required = false) String district,
					@RequestParam(required = false) String alarmType,
					@RequestParam(required = false) String alarmMappingName,
					@RequestParam(required = true) String type,
					@RequestParam(required = false) String region,
					@PathVariable String function,
					Model model, HttpServletRequest request) throws ParseException {
				String typeP = type;
				if (type.equalsIgnoreCase("DISTRICT"))
				{
					typeP="TEAM";
				}
				String username = SecurityContextHolder.getContext().getAuthentication().getName();
				List<SYS_PARAMETER> sysParemeterTitle = dyAlDetailTotalQualityDAO.titleForm("alarmtype",function,typeP,null);
				if(sysParemeterTitle.size() > 0)
				{
					model.addAttribute("title", sysParemeterTitle.get(0).getValue());
				}
				//Kiem tra dieu kien tim kiem 
				Calendar cal = Calendar.getInstance();	
				cal.setTime(new Date());
				String sdateChar ="";
				String edateChar="";
				String sdateCharLabel="";
				if (edate == null || edate.equals("")
						||(edate!=null && !edate.equals("") && DateTools
									.isValid("dd/MM/yyyy", edate)==false))
				{
					edate = df.format(cal.getTime());
				}
				
				if (sdate == null || sdate.equals("")
						||(sdate!=null && !sdate.equals("") && DateTools
									.isValid("dd/MM/yyyy", sdate)==false))
				{
					cal.add(Calendar.DATE, -1);
					sdate = df.format(cal.getTime());
					cal.add(Calendar.DATE, -29);
					sdateChar = df2.format(cal.getTime());
					sdateCharLabel = df.format(cal.getTime());
				}
				else
				{
					Calendar cal2 = Calendar.getInstance();	
					cal2.setTime(df.parse(edate));
					cal2.add(Calendar.DATE, -30);
					sdateChar = df2.format(cal2.getTime());
					sdateCharLabel = df.format(cal2.getTime());
				}
				edateChar = df2.format(df.parse(edate));
				System.out.println("sdateChar:"+sdateChar);
				System.out.println("edateChar:"+edateChar);
				model.addAttribute("sdate", sdate);			
				model.addAttribute("edate", edate);
				
				if (bscid!=null)
				{
					bscid=bscid.replaceAll(" ", "");
				}
				else
				{
					bscid="";
				}
				
				if (vendor!=null&&vendor!="ALL")
				{
					vendor=vendor.trim();
				}
				else
				{
					vendor="";
				}
				
				if (province!=null)
				{
					province=province.trim();
				}
				else
				{
					province="";
				}
				if (network!=null&&network!=""&&network!="ALL")
				{
					network=network.trim();
				}
				else
				{
					network="2G";

				}
				if (alarmType!=null&&alarmType!="ALL")
				{
					alarmType=alarmType.trim();
				}
				else
				{
					alarmType="";
				}
				if (alarmMappingName!=null)
				{
					alarmMappingName=alarmMappingName.trim();
				}
				else
				{
					alarmMappingName="";
				}
				if (team!=null)
				{
					team=team.trim();
				}
				else
				{
					team="";
				}
				
				if (district!=null)
				{
					district=district.trim();
				}
				else
				{
					district="";
				}
				
				if (region!=null)
				{
					region=region.trim();
				}
				else
				{
					region="";
				}
				//Lay danh sach BSC theo user
				List<String> bscList = sysUserEquipmentNameDAO.getBSCList(network,username,vendor);
				String bscArray="var bscList = new Array(";//Danh sach bsc cho cobobox
				       
				String cn="";
				for (String bsc : bscList) {
					bscArray = bscArray + cn +"\""+bsc+"\"";
					cn=",";
				}
				bscArray = bscArray+");";
				model.addAttribute("bscList", bscArray);
				
				//Lay danh sach province theo user
				List<String> provinceList = sysUserAreaDAO.getProvinceList(username);
				String provinceArray="var provinceList = new Array(";//Danh sach district cho cobobox
				 cn="";
				for (String dis : provinceList) {
					provinceArray = provinceArray + cn +"\""+dis+"\"";
					cn=",";
				}
				provinceArray = provinceArray+");";
				model.addAttribute("provinceList", provinceArray);
				
				if (typeP.equalsIgnoreCase("TEAM")&&province.equals("")&&provinceList.size()>0)		
				{
					province = provinceList.get(0);
				}
				//Lay danh sach district theo user
				List<String> districtList = sysUserAreaDAO.getDistrictList(province,team,username);
				String districtArray="var districtList = new Array(";//Danh sach district cho cobobox
				cn="";
				for (String dis : districtList) {
					districtArray = districtArray + cn +"\""+dis+"\"";
					cn=",";
				}
				districtArray = districtArray+");";
				model.addAttribute("districtList", districtArray);
				//Lay danh sach team theo user
				List<String> teamList = sysUserAreaDAO.getSubTeamList(province,username);
				String teamArray="var teamList = new Array(";//Danh sach district cho cobobox
				cn="";
				for (String dis : teamList) {
					teamArray = teamArray + cn +"\""+dis+"\"";
					cn=",";
				}
				teamArray = teamArray+");";
				model.addAttribute("teamList", teamArray);
				
				/* Begin Char*/
				List<CConfigAlarmType> alarmTypeList = new ArrayList<CConfigAlarmType>();
				if (alarmType != null && !alarmType.equals("") ){
					CConfigAlarmType cAlarm = new CConfigAlarmType();
					cAlarm.setAlarmType(alarmType);
					alarmTypeList.add(cAlarm);
				}
				else
				{
					alarmTypeList = cConfigAlarmTypeDAO.getAlarmType(network,null);
				}
				int countAlarmType = alarmTypeList.size();
				model.addAttribute("countAlarmType", countAlarmType);
				model.addAttribute("alarmTypeList", alarmTypeList);
				
				String chart="";
				String charTemp="";
				if (type.equals("NE"))
				{
					
					//neu khong chon bsc thi lay danh sach tat ca bsc neu khong thi lay cac bsc duoc chon
					if (bscid!=null && !bscid.equals(""))
					{
						bscList = new ArrayList<String>();
						String[] bscL = bscid.split(",");
						for (String item : bscL) {
							bscList.add(item);
						}
						
					}
					
					model.addAttribute("sierie", "var sierie = "+HelpTableConfigs.getSeriesChar(bscList));
					
				}
				if (type.equals("TEAM"))
				{
					if (team!=null && !team.equals(""))
					{
						teamList = new ArrayList<String>();
						//remove nhung bsc khong duoc chon
						String[] teamL = team.split(",");
						for (String item : teamL) {
							teamList.add(item);
						}
						//model.addAttribute("bscL", bscL);	
					}
					model.addAttribute("sierie", "var sierie = "+HelpTableConfigs.getSeriesChar(teamList));
					
				}
				if (type.equals("DISTRICT"))
				{
					
					
					if (district!=null && !district.equals(""))
					{
						districtList = new ArrayList<String>();
						String[] districtL = district.split(",");
						for (String item : districtL) {
							districtList.add(item);
						}
						//model.addAttribute("districtL", districtL);		
					}
					
					model.addAttribute("sierie", "var sierie = "+HelpTableConfigs.getSeriesChar(districtList));
					
				}
				
				if (type.equals("PROVINCE"))
				{
					if (province!=null && !province.equals(""))
					{
						provinceList = new ArrayList<String>();
						//remove nhung bsc khong duoc chon
						String[] provinceL = province.split(",");
						for (String item : provinceL) {
							provinceList.add(item);
						}
						//model.addAttribute("provinceL", provinceL);
					}
					model.addAttribute("sierie", "var sierie = "+HelpTableConfigs.getSeriesChar(provinceList));
				}
				if (type.equals("REGION"))
				{
					List<String> regionList = new ArrayList<String>();
					if (region!=null && !region.equals(""))
					{
						//remove nhung bsc khong duoc chon
						String[] regionL = region.split(",");
						for (String item : regionL) {
							regionList.add(item);
						}
					}
					else {
						List<SYS_PARAMETER> regionLt= sysParameterDao.getRegionList();
						for (SYS_PARAMETER sys_PARAMETER : regionLt) {
							regionList.add(sys_PARAMETER.getName());
						}
					}
					model.addAttribute("sierie", "var sierie = "+HelpTableConfigs.getSeriesChar(regionList));
				}
				if (type.equals("ALL"))
				{
					List<String> itemList = new ArrayList<String>();
					itemList.add("ALL");
					
					model.addAttribute("sierie", "var sierie = "+HelpTableConfigs.getSeriesChar(itemList));	
				}
				model.addAttribute("chart",chart);    
				
				/* End Char*/
				model.addAttribute("bscid", bscid);
				model.addAttribute("vendor", vendor);
				model.addAttribute("function", function);
				model.addAttribute("province", province);
				model.addAttribute("network",network);
				model.addAttribute("team", team);
				model.addAttribute("district", district);
				model.addAttribute("alarmType", alarmType);
				model.addAttribute("alarmMappingName", alarmMappingName);
				model.addAttribute("type", type);
				model.addAttribute("region", region);
				
				model.addAttribute("description", sdateCharLabel+ " - "+edate);
				model.addAttribute("minDay", sdateChar);
				model.addAttribute("maxDay", edateChar);
				
				return new ModelAndView("jspalarm/chart/chartAlarmType");
				
			}
		/*Lay du lieu tong hop so luong canh bao theo loai canh bao
		 	 * @param sdate: Thoi gian bat dau
			 * @param edate: Thoi gian ket thuc
			 * @param network: Loai canh báo: 2G,3G,PS CORE, CS CORE
			 * @param province: Tinh /thanh pho
			 * @param vendor: Nha cung cap
			 * @param team: To
			 * @param district: Quan huyen
			 * @param bscid: Nhieu bsc
			 * @param type: Tong hop cho canh bao: tong hop so luong (total), canh bao tong dong (nonfinish), canh bao lau ket thuc(finishLately), canh bao chap chon(jitter)
			 * function : team,province,ne,district
			 * */
			@RequestMapping("alarmtype/dataAlarmTypeChar")
			public @ResponseBody 
			Map<String, Object> dataAlarmType(@RequestParam(required = true) String type,
					@RequestParam(required = false) String sdate,
					@RequestParam(required = false) String edate,
					@RequestParam(required = false) String network,
					@RequestParam(required = false) String province,
					@RequestParam(required = false) String alarmtype,
					@RequestParam(required = false) String vendor,
					@RequestParam(required = false) String bscid,
					@RequestParam(required = false) String team,
					@RequestParam(required = false) String district,
					@RequestParam(required = false) String region,
					@RequestParam(required = false) String func,
					HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
				
				Map<String, Object> data = new HashMap<String, Object>();
				
				String username = SecurityContextHolder.getContext().getAuthentication().getName();
				List<String> dataChartList = dyAlDetailTotalQualityDAO.getReportSeverityChart(type,sdate,edate,network,province,vendor,team,district,bscid,region,null,alarmtype,username,func);
				//System.out.println("data: "+dataChartList);
				String data1 = HelpTableConfigs.getDataChar(dataChartList);
				System.out.println("data: "+data1);
				
				data.put("alarmType", data1);
				
				return data;
			
			 }
			
			
			
			/**
			 * Tong hop so luong canh bao theo loai canh bao chap chon
			 * @param sdate: Thoi gian bat dau
			 * @param edate: Thoi gian ket thuc
			 * @param network: Loai canh báo: 2G,3G,PS CORE, CS CORE
			 * @param province: Tinh /thanh pho
			 * @param vendor: Nha cung cap
			 * @param team: To
			 * @param district: Quan huyen
			 * @param bscid: Nhieu bsc
			 * @param type: ALARMID,NE,PROVINCE,TEAM,DISTRICT
			 * function : total,finishlate,nonfinish
			*/
			@RequestMapping(value = "jitter/{function}")
			public ModelAndView reportTransmissionlist(
						@RequestParam(required = false) String sdate,
						@RequestParam(required = false) String edate,
						@RequestParam(required = false) String network,
						@RequestParam(required = false) String province,
						@RequestParam(required = false) String vendor,
						@RequestParam(required = false) String bscid,
						@RequestParam(required = false) String team,
						@RequestParam(required = false) String district,
						@RequestParam(required = false) String alarmType,
						@RequestParam(required = false) String alarmMappingName,
						@RequestParam(required = false) String severity,
						@RequestParam(required = false) String site,
						@RequestParam(required = false) String qualityLimit,
						@RequestParam(required = false) String type,
						@PathVariable String function,
						Model model, HttpServletRequest request) throws ParseException {
					
					String username = SecurityContextHolder.getContext().getAuthentication().getName();
					List<SYS_PARAMETER> sysParemeterTitle = dyAlDetailTotalQualityDAO.titleForm("jitter",function,type,null);
					if(sysParemeterTitle.size() > 0)
					{
						model.addAttribute("title", sysParemeterTitle.get(0).getValue());
					}
					//Kiem tra dieu kien tim kiem 
					Calendar cal = Calendar.getInstance();	
					cal.setTime(new Date());
					String sdateChar ="";
					String edateChar="";
					String sdateCharLabel="";
					if (edate == null || edate.equals("")
							||(edate!=null && !edate.equals("") && DateTools
										.isValid("dd/MM/yyyy", edate)==false))
					{
						edate = df.format(cal.getTime());
					}
					
					if (sdate == null || sdate.equals("")
							||(sdate!=null && !sdate.equals("") && DateTools
										.isValid("dd/MM/yyyy", sdate)==false))
					{
						cal.add(Calendar.DATE, -1);
						sdate = df.format(cal.getTime());
						cal.add(Calendar.DATE, -29);
						sdateChar = df2.format(cal.getTime());
						sdateCharLabel = df.format(cal.getTime());
					}
					else
					{
						Calendar cal2 = Calendar.getInstance();	
						cal2.setTime(df.parse(edate));
						cal2.add(Calendar.DATE, -30);
						sdateChar = df2.format(cal2.getTime());
						sdateCharLabel = df.format(cal2.getTime());
					}
					edateChar = df2.format(df.parse(edate));
					System.out.println("sdateChar:"+sdateChar);
					System.out.println("edateChar:"+edateChar);
					model.addAttribute("sdate", sdate);			
					model.addAttribute("edate", edate);
					int quality=5;
					if (qualityLimit!=null&& !qualityLimit.equals(""))
					{
						try
						{
							quality = Integer.parseInt(qualityLimit);
						}
						catch(Exception exp)
						{
							quality=5;
						}
					}
					if (bscid!=null)
					{
						bscid=bscid.replaceAll(" ", "");
					}
					else
					{
						bscid="";
					}
					if (site!=null)
					{
						site=site.replaceAll(" ", "");
					}
					else
					{
						site="";
					}
					
					if (vendor!=null)
					{
						vendor=vendor.trim();
					}
					else
					{
						vendor="";
					}
					
					if (province!=null)
					{
						province=province.trim();
					}
					else
					{
						province="";
					}
					if (network!=null&&network!=""&&network!="ALL")
					{
						network=network.trim();
					}
					else
					{
						network="2G";

					}
					if (alarmMappingName!=null)
					{
						alarmMappingName=alarmMappingName.trim();
					}
					else
					{
						alarmMappingName="";
					}
					if (team!=null)
					{
						team=team.trim();
					}
					else
					{
						team="";
					}
					
					if (district!=null)
					{
						district=district.trim();
					}
					else
					{
						district="";
					}
					if (site!=null)
					{
						site=site.replaceAll(" ", "");
					}
					else
					{
						site="";
					}
					if (severity!=null)
					{
						severity=severity.trim();
					}
					else
					{
						severity="";
					}
					if (alarmType!=null)
					{
						alarmType=alarmType.trim();
					}
					else
					{
						alarmType="";
					}
					
					System.out.println("Da vao day, type ="+type+", funtion: "+function);
					
					//Lay danh sach province theo user
					List<String> provinceList = sysUserAreaDAO.getProvinceList(username);
					String provinceArray="var provinceList = new Array(";//Danh sach district cho cobobox
					String cn="";
					for (String dis : provinceList) {
						provinceArray = provinceArray + cn +"\""+dis+"\"";
						cn=",";
					}
					provinceArray = provinceArray+");";
					model.addAttribute("provinceList", provinceArray);
					if (type.equalsIgnoreCase("TEAM")&&province.equals("")&&provinceList.size()>0)		
					{
						province = provinceList.get(0);
					}
					//Lay danh sach BSC theo user
					List<String> bscList = sysUserEquipmentNameDAO.getBSCList(network,username,vendor);
					String bscArray="var bscList = new Array(";//Danh sach bsc cho cobobox
					       
					cn="";
					for (String bsc : bscList) {
						bscArray = bscArray + cn +"\""+bsc+"\"";
						cn=",";
					}
					bscArray = bscArray+");";
					model.addAttribute("bscList", bscArray);
					//Lay danh sach district theo user
					List<String> districtList = sysUserAreaDAO.getDistrictList(province,team,username);
					String districtArray="var districtList = new Array(";//Danh sach district cho cobobox
					cn="";
					for (String dis : districtList) {
						districtArray = districtArray + cn +"\""+dis+"\"";
						cn=",";
					}
					districtArray = districtArray+");";
					model.addAttribute("districtList", districtArray);
					//Lay danh sach team theo user
					List<String> teamList = sysUserAreaDAO.getSubTeamList(province,username);
					String teamArray="var teamList = new Array(";//Danh sach district cho cobobox
					cn="";
					for (String dis : teamList) {
						teamArray = teamArray + cn +"\""+dis+"\"";
						cn=",";
					}
					teamArray = teamArray+");";
					model.addAttribute("teamList", teamArray);
					if (type!=null && !type.equals(""))
					{
						// Begin Char
						List<String> dataChartList = dyAlDetailFinishDAO.getReporTransmissionChart(type,sdateChar,edateChar,network,province,vendor,team,district,bscid,username,quality,function);
						String chart="";
						if (type.equals("NE"))
						{
							
							//neu khong chon bsc thi lay danh sach tat ca bsc neu khong thi lay cac bsc duoc chon
							if (bscid!=null && !bscid.equals(""))
							{
								bscList = new ArrayList<String>();
								String[] bscL = bscid.split(",");
								for (String item : bscL) {
									bscList.add(item);
								}
								//model.addAttribute("bscL", bscL);	
							}
							chart = HelpTableConfigs.lineChart("jitterChar","TRANSMISSION", sdateCharLabel+ " - "+edate,dataChartList,sdateChar,edateChar,"0","80", bscList,"10");
							
						}
						if (type.equals("DISTRICT")||type.equals("TEAM"))
						{
							if (team!=null && !team.equals(""))
							{
								teamList = new ArrayList<String>();
								//remove nhung bsc khong duoc chon
								String[] teamL = team.split(",");
								for (String item : teamL) {
									teamList.add(item);
								}
							}
							
							chart = HelpTableConfigs.lineChart("jitterChar","TRANSMISSION", sdateCharLabel+ " - "+edate,dataChartList,sdateChar,edateChar,"0","80", teamList,"10");
							
							if (district!=null && !district.equals(""))
							{
								districtList = new ArrayList<String>();
								String[] districtL = district.split(",");
								for (String item : districtL) {
									districtList.add(item);
								}
								//model.addAttribute("districtL", districtL);		
							}
							
							String chartdis; 
							List<String> dataChartListdis = dyAlDetailFinishDAO.getReporTransmissionChart("DISTRICT",sdateChar,edateChar,network,province,vendor,team,district,bscid,username,quality,function);
							chartdis = HelpTableConfigs.lineChart("jitterCharDis","TRANSMISSION", sdateCharLabel+ " - "+edate,dataChartListdis,sdateChar,edateChar,"0","80", districtList,"10");
							model.addAttribute("jitterCharDis", chartdis);
						}
						
						if (type.equals("PROVINCE"))
						{
							if (province!=null && !province.equals(""))
							{
								provinceList = new ArrayList<String>();
								//remove nhung bsc khong duoc chon
								String[] provinceL = province.split(",");
								for (String item : provinceL) {
									provinceList.add(item);
								}
								//model.addAttribute("provinceL", provinceL);
							}
							// Severity A1
							chart = HelpTableConfigs.lineChart("jitterChar","TRANSMISSION", sdateCharLabel+ " - "+edate,dataChartList,sdateChar,edateChar,"0","80", provinceList,"10");
						}
						//ve bieu do
						model.addAttribute("jitterChar", chart);
					}
				
					model.addAttribute("bscid", bscid);
					model.addAttribute("vendor", vendor);
					model.addAttribute("function", function);
					model.addAttribute("province", province);
					model.addAttribute("network",network);
					model.addAttribute("team", team);
					model.addAttribute("district", district);
					model.addAttribute("alarmMappingName", alarmMappingName);
					model.addAttribute("type", type);
					model.addAttribute("severity", severity);
					model.addAttribute("site", site);
					model.addAttribute("qualityLimit", quality);
					model.addAttribute("alarmType", alarmType);
					
					if (type == null|| type.equals(""))
					{
						return new ModelAndView("jspalarm/report/reportTransmissionDetail");
					}
					else
					{
						return new ModelAndView("jspalarm/chart/chartTransmission");
					}
				}
			
			/*Lay du lieu tong hop so luong canh bao theo loai canh bao
		 	 * @param sdate: Thoi gian bat dau
			 * @param edate: Thoi gian ket thuc
			 * @param network: Loai canh báo: 2G,3G,PS CORE, CS CORE
			 * @param province: Tinh /thanh pho
			 * @param vendor: Nha cung cap
			 * @param team: To
			 * @param district: Quan huyen
			 * @param bscid: Nhieu bsc
			 * @param type: Tong hop cho canh bao: tong hop so luong (total), canh bao tong dong (nonfinish), canh bao lau ket thuc(finishLately), canh bao chap chon(jitter)
			 * function : team,province,ne,district
			 * @note: Hien tai chi su dung cho funtion = "total"
			 * */
			@RequestMapping("jitter/dataTransmission")
			public @ResponseBody
			void dataTransmission(
					@RequestParam(required = false) String sdate,
					@RequestParam(required = false) String edate,
					@RequestParam(required = false) String network,
					@RequestParam(required = false) String province,
					@RequestParam(required = false) String vendor,
					@RequestParam(required = false) String team,
					@RequestParam(required = false) String district,
					@RequestParam(required = false) String bscid,
					@RequestParam(required = false) String username,
					@RequestParam(required = false) String severity,
					@RequestParam(required = false) String alarmMappingName,
					@RequestParam(required = false) String site,
					@RequestParam(required = false) Integer quality,
					@RequestParam(required = false) String function,
					@RequestParam(required = false) String type,
					@RequestParam(required = false) String alarmType,
					HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
				
				
				List<DyAlDetailFinish> reportList = new ArrayList<DyAlDetailFinish>();
				if (type==null||type.equals(""))
				{
					reportList = dyAlDetailFinishDAO.getReportDataTransmission(sdate,edate,network,province, vendor,bscid,site, team,district,alarmMappingName,severity,quality,function,username,alarmType);	
				}
				else
				{
					reportList = dyAlDetailFinishDAO.getReportTransmissionAbis(sdate,edate,network,province, vendor,team, district, bscid, type,quality, function,username);	
				}
				Gson gs = new Gson();
				String jsonCartList = gs.toJson(reportList);
				PrintWriter out = response.getWriter();
				response.setContentType("application/json");
				out.println(jsonCartList);
				out.close();
			 }
			
	/*	@RequestMapping(value = "hr/{function}")
		public ModelAndView  list(@RequestParam(required = false) String sdate,
						@RequestParam(required = false) String edate,
						@RequestParam(required = false) String shour,
						@RequestParam(required = false) String ehour,
						@RequestParam(required = false) String network,
						@RequestParam(required = false) String province,
						@RequestParam(required = false) String vendor,
						@RequestParam(required = false) String team,
						@RequestParam(required = false) String district,
						@RequestParam(required = false) String bscid,
						@RequestParam(required = false) String region,
						@RequestParam(required = false) String username,
    				   @RequestParam(required = false) String alarmtype,
    				   @RequestParam(required = false) String type,
    				   @PathVariable String function, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
				
			List<SYS_PARAMETER> sysParemeterTitle = hrAlDetaiTotalQualityDAO.titleFormChartHr(function,type,alarmtype);
			if (sysParemeterTitle.size()>0){
				model.addAttribute("titleSystem", sysParemeterTitle.get(0).getValue());
 			}
 			String sdateChar ="";
 			String edateChar="";
 			String sdateCharLabel="";
 			//Kiem tra dieu kien tim kiem 
 			Calendar cal = Calendar.getInstance();	
 			cal.setTime(new Date());
 			if (edate == null || edate.equals("")
 					||(edate!=null && !edate.equals("") && DateTools
 								.isValid("dd/MM/yyyy", edate)==false))
 			{
 				edate = df.format(cal.getTime());
 			}
 			
 			if (sdate == null || sdate.equals("")
 					||(sdate!=null && !sdate.equals("") && DateTools
 								.isValid("dd/MM/yyyy", sdate)==false))
 			{
 				sdate = df.format(cal.getTime());
 			}
 			
 			try {
 				sdateChar = df2.format(df.parse(sdate));
				edateChar = df2.format(df.parse(edate));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 			if (ehour == null) {
 				ehour = "23";
 			} else {
 				ehour = ehour.substring(0, ehour.indexOf(":"));
 			}
 			if (shour == null) {
 				shour = "0";
 			} else {
 				shour = shour.substring(0, shour.indexOf(":"));
 			}
 			sdateChar= shour+" "+sdateChar;
 			edateChar= ehour+" "+edateChar;
 			sdateCharLabel = shour+ ":00 "+sdate;
 			String edateCharLabel = ehour+ ":00 "+sdate;
 			System.out.println("sdateChar:"+sdateChar);
 			System.out.println("edateChar:"+edateChar);
 			
 			model.addAttribute("sdate", sdate);			
 			model.addAttribute("edate", edate);
 			model.addAttribute("shour", shour + ":00");
 			model.addAttribute("ehour", ehour + ":00");
 			if (bscid!=null)
 			{
 				bscid=bscid.replaceAll(" ", "");
 			}
 			else
 			{
 				bscid="";
 			}
 			
 			if (vendor!=null)
 			{
 				vendor=vendor.trim();
 			}
 			else
 			{
 				vendor="";
 			}
 			
 			if (province!=null)
 			{
 				province=province.trim();
 			}
 			else
 			{
 				province="";
 			}
 			
 			if (team!=null)
 			{
 				team=team.trim();
 			}
 			else
 			{
 				team="";
 			}
 			
 			if (district!=null)
 			{
 				district=district.trim();
 			}
 			else
 			{
 				district="";
 			}
 			
 			if (network!=null&&network!=""&&network!="ALL")
 			{
 				network=network.trim();
 			}
 			else
 			{
 				network="2G";

 			}
 			
 			if (region!=null)
 			{
 				region=region.trim();
 			}
 			else
 			{
 				region="";
 			}
 			//Lay danh sach province theo user
 			List<String> provinceList = sysUserAreaDAO.getProvinceList(username);
 			String provinceArray="var provinceList = new Array(";//Danh sach district cho cobobox
 			String cn="";
 			for (String dis : provinceList) {
 				provinceArray = provinceArray + cn +"\""+dis+"\"";
 				cn=",";
 			}
 			provinceArray = provinceArray+");";
 			model.addAttribute("provinceList", provinceArray);
 			
 			if (type.equalsIgnoreCase("TEAM")&&province.equals("")&&provinceList.size()>0)		
 			{
 				province = provinceList.get(0);
 			}
 			//Lay danh sach district theo user
 			List<String> districtList = sysUserAreaDAO.getDistrictList(province,team,username);
 			String districtArray="var districtList = new Array(";//Danh sach district cho cobobox
 			cn="";
 			for (String dis : districtList) {
 				districtArray = districtArray + cn +"\""+dis+"\"";
 				cn=",";
 			}
 			districtArray = districtArray+");";
 			model.addAttribute("districtList", districtArray);
 			//Lay danh sach team theo user
 			List<String> teamList = sysUserAreaDAO.getSubTeamList(province,username);
 			String teamArray="var teamList = new Array(";//Danh sach district cho cobobox
 			cn="";
 			for (String dis : teamList) {
 				teamArray = teamArray + cn +"\""+dis+"\"";
 				cn=",";
 			}
 			teamArray = teamArray+");";
 			model.addAttribute("teamList", teamArray);
 			
 			//Lay danh sach BSC theo user
 			List<String> bscList = sysUserEquipmentNameDAO.getBSCList(network,username,vendor);
 			String bscArray="var bscList = new Array(";//Danh sach bsc cho cobobox
 			       
 			cn="";
 			for (String bsc : bscList) {
 				bscArray = bscArray + cn +"\""+bsc+"\"";
 				cn=",";
 			}
 			bscArray = bscArray+");";
 			model.addAttribute("bscList", bscArray);
 			//ve bieu do
 			List<String> seriesList = new ArrayList<String>();
	 		if (type.equalsIgnoreCase("severity"))
	 		{
	 			List<SYS_PARAMETER> severityParam = sysParameterDao.getSeverityListChart();
	 			for (SYS_PARAMETER sys_PARAMETER : severityParam) {
	 				seriesList.add(sys_PARAMETER.getValue());
				}
	 		}
 			String chartdis= null;
 			List<String> dataChartList = hrAlDetaiTotalQualityDAO.getReportHrChart(function,type,alarmtype,sdate,edate,shour,ehour,network,province,vendor,team,district,bscid,region,username);
 			chartdis = HelpTableConfigs.lineChart("lineChart",alarmtype.toUpperCase()+"-"+type.toUpperCase(), sdateCharLabel+ " - "+edateCharLabel,dataChartList,sdateChar,edateChar,"0","80", seriesList,"10");
 			model.addAttribute("lineChart", chartdis);
		 	
 			model.addAttribute("bscid", bscid);
 			model.addAttribute("vendor", vendor);
 			model.addAttribute("function", function);
 			model.addAttribute("province", province);
 			model.addAttribute("network",network);
 			model.addAttribute("team", team);
 			model.addAttribute("district", district);
 			model.addAttribute("type", type);
 			model.addAttribute("username", username);
 			model.addAttribute("region", region);
 			return new ModelAndView("jspalarm/chart/hrReportChart");
	}*/
			private DateFormat ddf = new SimpleDateFormat("dd/MM");

	@RequestMapping(value = "hr/{function}")
	public ModelAndView  list(@RequestParam(required = false) String startHour,
					@RequestParam(required = false) String endHour,
					@RequestParam(required = false) String startDate,
					@RequestParam(required = false) String endDate,
	    			@RequestParam(required = false) String alarmtype,
	    			@PathVariable String function, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
					
				List<SYS_PARAMETER> sysParemeterTitle = hrAlDetaiTotalQualityDAO.titleFormChartHr(function,null,alarmtype);
				String titleChart="";
				if (sysParemeterTitle.size()>0){
					titleChart =sysParemeterTitle.get(0).getValue();
				}
				model.addAttribute("titleSystem", titleChart);
	 			
				if (startDate == null || startDate.equals("") || DateTools.isValid("dd/MM/yyyy", startDate)==false)
 				{
 					startDate = df.format(new Date());
 				}
				if (endDate == null || endDate.equals("") || DateTools.isValid("dd/MM/yyyy", endDate)==false)
 				{
					endDate = df.format(new Date());
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
				model.addAttribute("startDate", startDate);
				model.addAttribute("endDate", endDate);
				model.addAttribute("startHour", startHour + ":00");
				model.addAttribute("endHour", endHour + ":00");
				Date sdate;
				try {
					sdate = df.parse(startDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					sdate = new Date();
				}
				Date edate;
				try {
					edate = df.parse(endDate);
				} catch (ParseException e) {
					edate = new Date();
				}
				//Grid
				int order = 1;
				String column = "DAY";
				try {
					order = Integer.parseInt(request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_ORDER))));
					column = request.getParameter((new ParamEncoder("item").encodeParameterName(TableTagParameters.PARAMETER_SORT)));
				} catch (NumberFormatException e) {
				}
				
				List<HrAlDetaiTotalQuality> dataChartList = hrAlDetaiTotalQualityDAO.getReportHrChartSeverity(function,alarmtype,sdate,edate,startHour,endHour,column, order == 1 ? "ASC" : "DESC");
				Map<Date, Map<String, String>> dates = DateTimeUtils.rangeDate(sdate, startHour, edate, endHour);
				// chart
				List<String> categories = new ArrayList<String>();
				List<Integer> A1List = new ArrayList<Integer>();
				List<Integer> A2List = new ArrayList<Integer>();
				List<Integer> A3List = new ArrayList<Integer>();
				List<Integer> A4List = new ArrayList<Integer>();
				List<Integer> totalList = new ArrayList<Integer>();
				
				Map<String, HrAlDetaiTotalQuality> charMap = new LinkedHashMap<String, HrAlDetaiTotalQuality>();
				for (HrAlDetaiTotalQuality hrQuality : dataChartList) {
					charMap.put(Integer.toString(hrQuality.getHour()) + ":00 " + ddf.format(hrQuality.getDay()), hrQuality);
				}
				for (Date day : dates.keySet()) {
					String sHour = dates.get(day).get("startHour");
					String eHour = dates.get(day).get("endHour");

					if (sHour.isEmpty())
						sHour = "0";
					if (eHour.isEmpty())
						eHour = "23";

					int sHourNum = Integer.parseInt(sHour);
					int eHourNum = Integer.parseInt(eHour);

					for (int i = sHourNum; i <= eHourNum; i++) {
						String cat = Integer.toString(i) + ":00 " + ddf.format(day);
						HrAlDetaiTotalQuality item = charMap.get(cat);
						categories.add(cat);
						if (item == null) {
							A1List.add(null);
							A2List.add(null);
							A3List.add(null);
							A4List.add(null);
							totalList.add(null);

						} else {
							A1List.add(item.geta1());
							A2List.add(item.geta2());
							A3List.add(item.geta3());
							A4List.add(item.geta4());
							totalList.add(item.getTotal());
						}
					}
				}
				
				Map<String, List<Integer>> availSeries = new LinkedHashMap<String, List<Integer>>();
				availSeries.put("A1", A1List);
				availSeries.put("A2", A2List);
				availSeries.put("A3", A3List);
				availSeries.put("A4", A4List);
				availSeries.put("Total", totalList);
				model.addAttribute("availChart", Chart.basicLineInter("availChart", titleChart, "Quality", categories, availSeries));

				model.addAttribute("dataChartList", dataChartList);
	 			model.addAttribute("function", function);
	 			model.addAttribute("alarmtype", alarmtype);
	 			String exportFileName="";
	 			exportFileName="alarm_"+function;
	 			if(alarmtype !=null){
	 				exportFileName=exportFileName+"_"+alarmtype;
	 			}
	 			model.addAttribute("exportFileName", exportFileName+"_"+df.format(new Date()));
	 			return new ModelAndView("jspalarm/chart/hrReportChart");
		}
	
}

