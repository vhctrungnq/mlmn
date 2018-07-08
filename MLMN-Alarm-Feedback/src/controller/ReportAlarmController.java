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
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

import a.a.a.a.a;

import com.google.gson.Gson;

import dao.AlShiftDAO;
import dao.CConfigAlarmTypeDAO;
import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.DyAlDetailFinishDAO;
import dao.DyAlDetailFinishQualityDAO;
import dao.DyAlDetailNonFinishDAO;
import dao.DyAlDetailNonFinishDAOImpl;
import dao.DyAlDetailTotalQualityDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUserAreaDAO;
import dao.SysUserEquipmentNameDAO;
import dao.SysUsersDAO;

import vo.AlShift;
import vo.CConfigAlarmType;
import vo.CHighlightConfigs;
import vo.CTableConfig;
import vo.CountAlarmLog;
import vo.DyAlDetailFinish;
import vo.DyAlDetailNonFinish;
import vo.DyAlDetailTotalQuality;
import vo.RAlarmLog;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.HelpTableConfigs;

@Controller
@RequestMapping("/report/*")
public class ReportAlarmController extends BaseController {

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
	private DyAlDetailNonFinishDAO dyAlDetailNonFinishDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
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
		
		/* Begin Grid*/
		String nameTable="";
		if (function.equals("finishLately"))
		{
			nameTable  = "REPORT_SERVERITY_"+type+"_FINISH";
		}
		else
		{
			nameTable = "REPORT_SERVERITY_"+type;
		}
		//lay du lieu column group cua grid
		String groupColumn="";
		if (function.equals("finishLately"))
		{
			List<String> columnGrid = cTableConfigDAO.getGroupList(nameTable);
			String con2="";
			groupColumn =" var columngroups=[";
			for (String alarmTypeG : columnGrid) {
				groupColumn= groupColumn+		con2+"{ text: '"+alarmTypeG+"', align: 'center', name: '"+alarmTypeG+"' }";
				con2 = ",";
			}
			groupColumn= groupColumn+	"]; ";
		}
		model.addAttribute("columngroups", groupColumn); 
		//highligh
		String highligh="     var cellsrenderer = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {";
		highligh += "       	return '<span style=\"margin: 4px; float: ' + columnproperties.cellsalign + '; color: #0000FF;text-decoration: underline;\">' + value + '</span>';";   	
		highligh += "         };";
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid(nameTable);
		//lay du lieu column cua grid
		model.addAttribute("column", HelpTableConfigs.getColumns(configList)); 
		//Lay du lieu datafield cua grid
		model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
		//Lay duong link url de lay du lieu do vao grid
		String url = "dataSeverity.htm?type="+type+"&sdate="+sdate+"&edate="+edate+
				"&network="+network+"&province="+province+
				"&vendor="+vendor+"&team="+team+
				"&district="+district+"&bscid="+bscid+"&region="+region+"&username="+username+"&function="+function;
		//Grid
		String gridReport = HelpTableConfigs.getGridPagingReportUrl(configList, "gridReport", url, null, null, "menuReport", highligh,"singlecell",groupColumn,null);
		model.addAttribute("gridReport", gridReport);		
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "ReportSeriverty"+type+ function + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		/* End Grid*/
		
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
		
		return new ModelAndView("jspalarm/report/reportSeverity");
	}
/*Lay du lieu tong hop so luong canh bao theo muc do canh bao
 	 * @param sdate: Thoi gian bat dau
	 * @param edate: Thoi gian ket thuc
	 * @param network: Loai canh báo: 2G,3G,PS CORE, CS CORE
	 * @param province: Tinh /thanh pho
	 * @param vendor: Nha cung cap
	 * @param team: To
	 * @param district: Quan huyen
	 * @param bscid: Nhieu bsc
	 * @param type: Tong hop cho canh bao: tong hop so luong (total), canh bao tong dong (nonfinish), canh bao lau ket thuc(finishLately), canh bao chap chon(jitter)
	 * function : 2G/3G
	 * */
	@RequestMapping("severity/dataSeverity")
	public @ResponseBody 
	void processForm(@RequestParam(required = true) String type,
			@RequestParam(required = false) String sdate,
			@RequestParam(required = false) String edate,
			@RequestParam(required = false) String network,
			@RequestParam(required = false) String province,
			@RequestParam(required = false) String vendor,
			@RequestParam(required = false) String team,
			@RequestParam(required = false) String district,
			@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String region,
			@RequestParam(required = false) String username,
			@RequestParam(required = false) String function,
			HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
		
		
		List<DyAlDetailTotalQuality> reportList = new ArrayList<DyAlDetailTotalQuality>();
		
		if (function.equals("finishLately"))
		{
			reportList = dyAlDetailTotalQualityDAO.getReportWithSeverityFinishLately(sdate,edate,network,province, vendor,team, district, bscid,region, type,username);	
		}
		else
		{
			reportList = dyAlDetailTotalQualityDAO.getReportWithSeverity(sdate,edate,network,province, vendor,team, district, bscid, region, type, function,username);	
		}
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(reportList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
		//return reportList;
	 }

	/*Tong hop so luong canh bao theo muc do canh bao
	 * @param sdate: Thoi gian bat dau
	 * @param edate: Thoi gian ket thuc
	 * @param network: Loai canh báo: 2G,3G,PS CORE, CS CORE
	 * @param province: Tinh /thanh pho
	 * @param vendor: Nha cung cap
	 * @param team: To
	 * @param district: Quan huyen
	 * @param bscid: Nhieu bsc
	 * function : total,finishlate,nonfinish
	*/
		@RequestMapping(value = "detail/{function}")
		public String detail(
				@RequestParam(required = false) String sdate,
				@RequestParam(required = false) String edate,
				@RequestParam(required = false) String bscid,
				@RequestParam(required = false) String cellid,
				@RequestParam(required = false) String vendor,
				@RequestParam(required = false) String district,
				@RequestParam(required = false) String alarmName,
				@RequestParam(required = false) String province,
				@RequestParam(required = false) String team,
				@RequestParam(required = false) String alarmType,
				@RequestParam(required = false) String alarmMappingName,
				@RequestParam(required = false) String statusFinish,
				@RequestParam(required = false) String severity,
				@RequestParam(required = true) String netWork,
				@RequestParam(required = false) String duarationTo,
				@RequestParam(required = false) String bscPort,
				@RequestParam(required = false) String btsPort,
				@RequestParam(required = false) String objType,
				@RequestParam(required = false) String region,
				@PathVariable String function,
				Model model, HttpServletRequest request) throws ParseException {
			
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			List<SYS_PARAMETER> sysParemeterTitle = dyAlDetailTotalQualityDAO.titleForm(null,function,netWork,"DETAIL");
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("titleDetail", sysParemeterTitle.get(0).getValue());
			}
			//Kiem tra dieu kien tim kiem 
			if (!function.equalsIgnoreCase("nonfinish"))
			{
				Calendar cal = Calendar.getInstance();	
				cal.setTime(new Date());
				cal.add(Calendar.DATE,-1);
				if (sdate == null || sdate.equals("")
						||(sdate!=null && !sdate.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm", sdate)==false))
				{
					sdate = df.format(cal.getTime())+" "+"00:00";
				}
				
				if (edate == null || edate.equals("")
						||(edate!=null && !edate.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm", edate)==false))
				{
					edate = df.format(cal.getTime())+" "+"23:59";
				}
				model.addAttribute("sdate", sdate);			
				model.addAttribute("edate", edate);
				
				sdate = sdate+":00";
				edate = edate+":00";
			}
			else
			{
				Calendar cal = Calendar.getInstance();	
				cal.setTime(new Date());
				
				if (edate == null || edate.equals("")
						||(edate!=null && !edate.equals("") && DateTools.isValid("dd/MM/yyyy", edate)==false))
				{
					
					edate = df.format(cal.getTime());
				}
				if (sdate == null || sdate.equals("")
						||(sdate!=null && !sdate.equals("") && DateTools.isValid("dd/MM/yyyy", sdate)==false))
				{
					cal.add(Calendar.DATE,-1);
					sdate = df.format(cal.getTime());
				}
				
				model.addAttribute("sdate", sdate);			
				model.addAttribute("edate", edate);
				
			}
			
			
			if (bscid!=null)
			{
				bscid=bscid.replaceAll(" ", "");
			}
			else
			{
				bscid="";
			}
			if (cellid!=null)
			{
				cellid=cellid.replaceAll(" ", "");
			}
			else
			{
				cellid="";
			}
			if (district!=null)
			{
				district=district.trim();
			}
			else
			{
				district="";
			}
			if (vendor!=null)
			{
				vendor=vendor.trim();
			}
			else
			{
				vendor="";
			}
			if (alarmName!=null)
			{
				alarmName=alarmName.trim();
			}
			else
			{
				alarmName="";
			}
			if (province==null)
			{
				province="";
				
			}
			else
			{
				province=province.trim();
			}
			if (team!=null)
			{
				team=team.trim();
			}
			else
			{
				team="";
			}
			if (alarmType!=null)
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
			if (severity!=null)
			{
				severity=severity.trim().toUpperCase();
			}
			else
			{
				severity="";
			}
			if (statusFinish!=null)
			{
				statusFinish=statusFinish.trim();
			}
			else
			{
				statusFinish="";
			}
			if (duarationTo==null||duarationTo.equals(""))
			{
				duarationTo="30";
			}
			
			if (bscPort!=null)
			{
				bscPort=bscPort.trim();
			}
			else
			{
				bscPort="";
			}
			
			if (btsPort!=null)
			{
				btsPort=btsPort.trim();
			}
			else
			{
				btsPort="";
			}
			
			if (objType!=null)
			{
				objType=objType.trim(); 
			}
			else
			{
				objType="";
			}
			
			if (region!=null)
			{
				region=region.trim(); 
			}
			else
			{
				region="";
			}
			
			model.addAttribute("duarationTo", duarationTo);    
			System.out.println("province: "+province);		
			System.out.println("sdate: "+sdate);		
			System.out.println("edate: "+edate);		
		
			
			//Lay danh sach BSC theo user
			List<String> bscList = sysUserEquipmentNameDAO.getBSCList(netWork,username,vendor);
			//String bscListStr ="";
			String bscArray="var bscList = new Array(";  
			String cn="";
			for (String bsc : bscList) {
			//	bscListStr = bscListStr + cn +bsc;
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
			//Lay danh sach Cell theo user
			List<String> cellList = sysUserEquipmentNameDAO.getCellList(netWork,bscid,district,username);
			//String cellListStr ="";
			String cellArray="var cellList = new Array(";
			cn="";
			for (String cell : cellList) {
			//	cellListStr = cellListStr + cn +cell;
				cellArray = cellArray + cn +"\""+cell+"\"";
				cn=",";
			}
			cellArray = cellArray+");";
			model.addAttribute("cellList", cellArray);    
			
			
			// Dem so luong theo muc do canh bao
			List<CountAlarmLog> countList = new ArrayList<CountAlarmLog>();
			int countA1=0;
			int countA2=0;
			int countA3=0;
			int countA4=0;
			int totalFinish=0;
			int total=0;
			/*try
			{*/
				countList = dyAlDetailNonFinishDAO.getCountForSeverity(sdate,edate,bscid,cellid, vendor,district, alarmName, function,netWork,username,province,team,alarmType,alarmMappingName,Integer.parseInt(duarationTo), bscPort, btsPort, objType,null,region);
				if (countList.size()>0) {
					countA1 = countList.get(0).getA1();		
					countA2 =  countList.get(0).getA2();	
					countA3 = countList.get(0).getA3();	
					countA4 = countList.get(0).getA4();	
					totalFinish  = countList.get(0).getTotalFinish();	
					total = countList.get(0).getTotal();	
				}
			/*}
			catch(Exception exp){}*/
			model.addAttribute("countA1",countA1 );
			model.addAttribute("countA2",countA2 );
			model.addAttribute("countA3",countA3 );
			model.addAttribute("countA4",countA4);
			model.addAttribute("totalFinish",totalFinish);
			model.addAttribute("total",total);
			
			System.out.println("Sdate1: "+sdate+"--- Edate1:"+edate);	
			int totalRow = 0;
			/*try
			{	*/													
				totalRow = dyAlDetailNonFinishDAO.countAlarmLog(sdate,edate,bscid,cellid, vendor,district, alarmName, function, severity,netWork,username,province,team,alarmType,alarmMappingName,statusFinish,null,Integer.parseInt(duarationTo), bscPort, btsPort, objType,null,region);
			/*}
			catch(Exception exp){}*/
			model.addAttribute("totalRows", totalRow);
			String nameTable="REPORT_DETAIL";
			if (function!=null && !function.equals(""))
			{
				if (function.equalsIgnoreCase("finish")){
					nameTable=nameTable+"_TOTAL";
				}
				else{
					nameTable=nameTable+"_"+function.toUpperCase();
				}
			}
			System.out.println("nameTable: "+nameTable);		
			
			//do du lieu ra grid
			List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid(nameTable);
			//lay du lieu column cua grid
			model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
			//Lay du lieu datafield cua grid
			model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
			//Lay duong link url de lay du lieu do vao grid
			model.addAttribute("url", "dataDetail.htm?sdate="+sdate+"&edate="+edate+
				"&bscid="+bscid+"&cellid="+cellid+
				"&vendor="+vendor+"&district="+district+
				"&alarmName="+alarmName+"&function="+function+
				"&severity="+severity+"&network="+netWork+
				"&username="+username+"&province="+province+
				"&team="+team+"&alarmType="+alarmType+
				"&alarmMappingName="+alarmMappingName+
				"&statusFinish="+statusFinish+
				"&duarationTo="+duarationTo+
				"&bscPort="+bscPort+
				"&btsPort="+btsPort+
				"&objType="+objType+
				"&region="+region); 
			
			//Lay du lieu cac cot an hien cua grid 
			List<CTableConfig> columnList = cTableConfigDAO.getColumnList(nameTable);
			model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
			
			List<CHighlightConfigs> highlightConfigListCol = cHighlightConfigsDAO.getByKeyAndKPI("R_ALARM_LOG","severity");
			model.addAttribute("highlight", HelpTableConfigs.highLightCol(highlightConfigListCol));
			
			// Lay ten file export
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
			String dateNow = formatter.format(currentDate.getTime());
			String exportFileName = "ReportDetail"+function + dateNow;
			model.addAttribute("exportFileName", exportFileName);
			/* End Grid*/
			
			model.addAttribute("bscid", bscid); 
			model.addAttribute("cellid", cellid);
			model.addAttribute("vendor", vendor);
			model.addAttribute("district", district);
			model.addAttribute("alarmName", alarmName);
			model.addAttribute("function", function);
			model.addAttribute("severity", severity);
			model.addAttribute("netWork", netWork);
			model.addAttribute("province", province);
			model.addAttribute("team", team);
			model.addAttribute("alarmType", alarmType);
			model.addAttribute("alarmMappingName", alarmMappingName);
			model.addAttribute("bscPort", bscPort);
			model.addAttribute("btsPort", btsPort);
			model.addAttribute("objType", objType);
			model.addAttribute("region", region);
			
			return "jspalarm/report/reportDetailList";
		}
		@RequestMapping("detail/dataDetail")
		public @ResponseBody 
		Map<String, Object>  processForm(@RequestParam(required = true) String sdate,
				@RequestParam(required = true) String edate,
				@RequestParam(required = true) String bscid,
				@RequestParam(required = true) String cellid,
				@RequestParam(required = true) String vendor,
				@RequestParam(required = true) String district,
				@RequestParam(required = true) String alarmName,
				@RequestParam(required = true) String function,
				@RequestParam(required = true) String severity,
				@RequestParam(required = true) String network,
				@RequestParam(required = true) String username,
				@RequestParam(required = true) String province,
				@RequestParam(required = true) String team,
				@RequestParam(required = true) String alarmType,
				@RequestParam(required = true) String alarmMappingName,
				@RequestParam(required = true) String statusFinish,
				@RequestParam(required = true) String duarationTo,
				@RequestParam(required = false) String bscPort,
				@RequestParam(required = false) String btsPort,
				@RequestParam(required = false) String objType,
				@RequestParam(required = false) String region,
				HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
		
			Map<String, Object> data = new HashMap<String, Object>();
			
			List<DyAlDetailNonFinish> alarmList = new ArrayList<DyAlDetailNonFinish>();
			int startRecord = 0, endRecord = 0;
			String sortfield = "";
			String sortorder = "";
			int pageNum = Integer.parseInt(request.getParameter("pagenum"));
			if(pageNum == -1)
				pageNum = 1;
			int pageSize = Integer.parseInt(request.getParameter("pagesize"));
			sortfield = request.getParameter("sortdatafield");
			sortorder = request.getParameter("sortorder");
			
			if(sortfield==null)
				sortfield = "SDATE";
			if(sortorder==null)
				sortorder = " DESC ";
			
			startRecord = pageNum*pageSize;
			endRecord = startRecord + pageSize;
			
			sortfield = sortfield.replaceAll("alarmType", "ALARM_TYPE")
							.replaceAll("alarmName", "ALARM_NAME")
							.replaceAll("alarmMappingId", "ALARM_MAPPING_ID")
							.replaceAll("alarmMappingName", "ALARM_MAPPING_NAME")
							.replaceAll("causebySy", "CAUSEBY_SYS")
							.replaceAll("resultContent", "RESULT_CONTENT")
							.replaceAll("createdBy", "CREATED_BY")
							.replaceAll("createDate", "CREATE_DATE")
							.replaceAll("modifiedBy", "MODIFIED_BY")
							.replaceAll("modifyDate", "MODIFY_DATE")
							.replaceAll("objType", "OBJ_TYPE");
							
			System.out.println("sortfield: "+sortfield);		
			System.out.println("Sdate1: "+sdate+"--- Edate1:"+edate);	
			
			String strWhere = HelpTableConfigs.filter(request);
			strWhere = strWhere.replaceAll("alarmType", "ALARM_TYPE")
					.replaceAll("alarmName", "ALARM_NAME")
					.replaceAll("portBsc", "BSCPORT")
					.replaceAll("portBts", "BTSPORT")
					.replaceAll("alarmMappingId", "ALARM_MAPPING_ID")
					.replaceAll("alarmMappingName", "ALARM_MAPPING_NAME")
					.replaceAll("causebySy", "CAUSEBY_SYS")
					.replaceAll("resultContent", "RESULT_CONTENT")
					.replaceAll("createdBy", "CREATED_BY")
					.replaceAll("createDate", "CREATE_DATE")
					.replaceAll("modifiedBy", "MODIFIED_BY")
					.replaceAll("modifyDate", "MODIFY_DATE")
					.replaceAll("objType", "OBJ_TYPE");
			if (function.equals("finishLately")||function.equals("jitter"))
			{
				strWhere= strWhere.replaceAll("duaration", "ROUND(to_number( NVL(EDATE,sysdate) - NVL(SDATE,sysdate) )*1440)");
			}
			if (function.equals("nonfinish"))
			{
				strWhere= strWhere.replaceAll("duaration", "ROUND(to_number( sysdate - NVL(SDATE,sysdate) )*1440)");
			}
			System.out.println("strWhere: "+strWhere);	
			int totalRow =0;
			/*try
			{*/
				totalRow = dyAlDetailNonFinishDAO.countAlarmLog(sdate,edate,bscid,cellid, vendor,district, alarmName, function, severity,network,username,province,team,alarmType,alarmMappingName,statusFinish,strWhere,Integer.parseInt(duarationTo), bscPort, btsPort, objType,null,region);
				request.setAttribute("totalrecords", totalRow);
				
				//	model.addAttribute("totalRows", totalRow);
				alarmList = dyAlDetailNonFinishDAO.getAlarmLog(sdate,edate,bscid,cellid, vendor,district, alarmName, function, severity, strWhere, startRecord,endRecord,sortfield,sortorder,network,username,province,team,alarmType,alarmMappingName,statusFinish,Integer.parseInt(duarationTo), bscPort, btsPort, objType,null,region);
			/*}
			catch(Exception exp)
			{
				alarmList=null;
			}*/
			data.put("totalRow", totalRow);
			data.put("Rows", alarmList);
			return data;
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
				List<CConfigAlarmType> alarmTypeList = cConfigAlarmTypeDAO.getAlarmType(network,null);
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
				/* Begin Grid*/
				String nameTable="";
				
				if (!typeP.equalsIgnoreCase("ALARMID")&&!typeP.equalsIgnoreCase("ALARMTYPE"))
				{
				
					if (function.equals("finishLately"))
					{
						
						nameTable  = "REPORT_ALARMTYPE_"+typeP+"_FINISH_"+network;
					}
					else
					{
						nameTable = "REPORT_ALARMTYPE_"+typeP+"_"+network;
					}
				}
				else
				{
					if (typeP.equalsIgnoreCase("ALARMID"))
					{
						nameTable = "REPORT_ALARMTYPE_"+typeP;
					}
					else
					{
						nameTable = "REPORT_ALARMTYPE";
					}
				}
				//do du lieu ra grid
				List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid(nameTable);
				//lay du lieu column cua grid
				/*model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
				//Lay du lieu datafield cua grid
				model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));*/
				String highligh="     var cellsrenderer = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {";
					highligh += "       	return '<span style=\"margin: 4px; float: ' + columnproperties.cellsalign + '; color: #0000FF;text-decoration: underline;\">' + value + '</span>';";   	
					highligh += "         };";
					
				//lay du lieu column group cua grid
				String groupColumn="";
				List<String> columnGrid = cTableConfigDAO.getGroupList(nameTable);
				if (function.equals("finishLately") && columnGrid.size()>0)
				{
					
					String con2="";
					groupColumn =" var columngroups=[";
					for (String alarmTypeG : columnGrid) {
						groupColumn= groupColumn+		con2+"{ text: '"+alarmTypeG+"', align: 'center', name: '"+alarmTypeG+"' }";
						con2 = ",";
					}
					groupColumn= groupColumn+	"]; ";
				}
				
				if (!type.equalsIgnoreCase("ALARMID")&&!type.equalsIgnoreCase("ALARMTYPE"))
				{
					//Lay duong link url de lay du lieu do vao grid
					List<String> dataList = dyAlDetailTotalQualityDAO.getReportWithAlarmAlarmtype(type,sdate,edate,network,province, vendor,team,district,bscid,username,function,region);	
					//Grid
					String gridReport = HelpTableConfigs.getGridReportData(configList, "gridReport", dataList, null, null, "menuReport", highligh,groupColumn);
					model.addAttribute("gridReport", gridReport);	
				
				}
				else
				{
				//Lay duong link url de lay du lieu do vao grid
				String url="dataAlarmID.htm?sdate="+sdate+"&edate="+edate+
						"&network="+network+"&province="+province+"&alarmType="+alarmType+
						"&vendor="+vendor+"&bscid="+bscid+"&alarmMappingName="+alarmMappingName+"&username="+username+"&function="+function+"&type="+type; 
					//Grid
					String gridReport = HelpTableConfigs.getGridPagingReportUrl(configList, "gridReport", url, null, null, "menuReport", highligh,"singlecell",null,null);
					model.addAttribute("gridReport", gridReport);	
				}	
				// Lay ten file export
				Calendar currentDate = Calendar.getInstance();
				SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
				String dateNow = formatter.format(currentDate.getTime());
				String exportFileName = "ReportAlarmType"+type+ function + dateNow;
				model.addAttribute("exportFileName", exportFileName);
				/* End Grid*/
				
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
				
				return new ModelAndView("jspalarm/report/reportAlarmType");
				
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
				
			/*	System.out.println("type: "+type);
				System.out.println("sdate: "+sdate);
				System.out.println("edate: "+edate);
				System.out.println("network: "+network);
				System.out.println("province: "+province);
				System.out.println("vendor: "+vendor);
				System.out.println("team: "+team);
				System.out.println("district: "+district);
				System.out.println("bscid: "+bscid);
				System.out.println("alarmtype: "+alarmtype);
				System.out.println("function: "+func);*/
				
				String username = SecurityContextHolder.getContext().getAuthentication().getName();
				List<String> dataChartList = dyAlDetailTotalQualityDAO.getReportSeverityChart(type,sdate,edate,network,province,vendor,team,district,bscid,region,null,alarmtype,username,func);
				//System.out.println("data: "+dataChartList);
				String data1 = HelpTableConfigs.getDataChar(dataChartList);
				System.out.println("data: "+data1);
				
				data.put("alarmType", data1);
				
				return data;
			
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
			@RequestMapping("alarmtype/dataAlarmID")
			public @ResponseBody 
			void dataAlarmID(
					@RequestParam(required = false) String sdate,
					@RequestParam(required = false) String edate,
					@RequestParam(required = false) String network,
					@RequestParam(required = false) String province,
					@RequestParam(required = false) String alarmType,
					@RequestParam(required = false) String vendor,
					@RequestParam(required = false) String bscid,
					@RequestParam(required = false) String alarmMappingName,
					@RequestParam(required = false) String username,
					@RequestParam(required = false) String function,
					@RequestParam(required = false) String type,
					HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
				
				
				List<DyAlDetailTotalQuality> reportList = new ArrayList<DyAlDetailTotalQuality>();
				reportList = dyAlDetailTotalQualityDAO.getReportWithAlarmID(sdate,edate,network,province, vendor,bscid, alarmType,alarmMappingName, function,username,type);	
				Gson gs = new Gson();
				String jsonCartList = gs.toJson(reportList);
				PrintWriter out = response.getWriter();
				response.setContentType("application/json");
				out.println(jsonCartList);
				out.close();
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
				
					// Begin Grid
					String nameTable= "REPORT_JITTER_"+function.toUpperCase();
					System.out.println("nameTable: "+nameTable);
					if (type!=null&&!type.equals(""))
					{
						nameTable= nameTable+"_"+type.toUpperCase();
					}
					//do du lieu ra grid
					List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid(nameTable);  
					//lay du lieu column cua grid
					model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
					//Lay du lieu datafield cua grid
					model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
					//Lay duong link url de lay du lieu do vao grid
					model.addAttribute("url", "dataTransmission.htm?sdate="+sdate+"&edate="+edate+"&network="+network+
							"&province="+province+"&vendor="+vendor+"&team="+team+"&district="+district+"&bscid="+bscid+
							"&username="+username+"&severity="+severity+"&alarmMappingName="+alarmMappingName+"&site="+site+
							"&quality="+quality+"&function="+function+"&type="+type+"&alarmType="+alarmType); 
							
					// Lay ten file export
					Calendar currentDate = Calendar.getInstance();
					SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
					String dateNow = formatter.format(currentDate.getTime());
					String exportFileName = "ReportJitter"+type+ function + dateNow;
					model.addAttribute("exportFileName", exportFileName);
					// End Grid
					
					
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
						return new ModelAndView("jspalarm/report/reportTransmission");
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
			
			/*Tong hop so luong canh bao trong ca truc
			 * @param sdate: Thoi gian bat dau
			 * @param edate: Thoi gian ket thuc
			 * @param network: Loai canh báo: 2G,3G,PS CORE, CS CORE
			 * @param province: Tinh /thanh pho
			 * @param vendor: Nha cung cap
			 * @param bscid: Nhieu bsc
			 * @param type: NE,PROVINCE
			*/
				@RequestMapping(value = "catruc")
				public ModelAndView reportCatruc(
						@RequestParam(required = false) String day,
						@RequestParam(required = false) String shift,
						@RequestParam(required = false) String network,
						@RequestParam(required = false) String province,
						@RequestParam(required = false) String vendor,
						@RequestParam(required = false) String bscid,
						@RequestParam(required = true) String type,
						Model model, HttpServletRequest request) throws ParseException {
					
					String username = SecurityContextHolder.getContext().getAuthentication().getName();
					List<SYS_PARAMETER> sysParemeterTitle = dyAlDetailTotalQualityDAO.titleForm("SHIFT",null,type,null);
					if(sysParemeterTitle.size() > 0)
					{
						model.addAttribute("title", sysParemeterTitle.get(0).getValue());
					}
					//Kiem tra dieu kien tim kiem 
					Calendar cal = Calendar.getInstance();	
					cal.setTime(new Date());
					if (day == null || day.equals("")
							||(day!=null && !day.equals("") && DateTools
										.isValid("dd/MM/yyyy", day)==false))
					{
						day = df.format(cal.getTime());
					}
					model.addAttribute("day", day);			
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
					
					if (network!=null&&network!="")
					{
						network=network.trim();
					}
					else
					{
						network="";
					}
					if (shift!=null)
					{
						shift=shift.trim();
					}
					else
					{
						shift="";
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
					
					/* Begin Grid*/
					String nameTable="";
					nameTable = "REPORT_SHIFT_"+type;
					
					//lay du lieu column group cua grid
					String groupColumn="";
					List<String> columnGrid = cTableConfigDAO.getGroupList(nameTable);
					String con2="";
					groupColumn =" var columngroups=[";
					for (String alarmTypeG : columnGrid) {
						groupColumn= groupColumn+		con2+"{ text: '"+alarmTypeG+"', align: 'center', name: '"+alarmTypeG+"' }";
						con2 = ",";
					}
					groupColumn= groupColumn+	"]; ";
					//highligh
					String highligh="     var cellsrenderer = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {";
					highligh += "       	return '<span style=\"margin: 4px; float: ' + columnproperties.cellsalign + '; color: #0000FF;\">' + value + '</span>';";   	
					highligh += "         };";
					//do du lieu ra grid
					List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid(nameTable);
					//Lay duong link url de lay du lieu do vao grid
					String url = "dataCatruc.htm?type="+type+"&day="+day+"&shift="+shift+
							"&network="+network+"&province="+province+
							"&vendor="+vendor+
							"&bscid="+bscid+"&username="+username;
					//Grid
					String catrucReport = HelpTableConfigs.getGridPagingReportUrl(configList, "catrucReport", url, null, null, "menuReport", highligh,"singlecell",groupColumn,null);
					model.addAttribute("catrucReport", catrucReport);		
					
					// Lay ten file export
					Calendar currentDate = Calendar.getInstance();
					SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
					String dateNow = formatter.format(currentDate.getTime());
					String exportFileName = "ReportShift"+type + dateNow;
					model.addAttribute("exportFileName", exportFileName);
					/* End Grid*/
					
					model.addAttribute("shift", shift);
					model.addAttribute("bscid", bscid);
					model.addAttribute("vendor", vendor);
					model.addAttribute("province", province);
					model.addAttribute("network",network);
					model.addAttribute("type", type);
					model.addAttribute("username", username);
					
					return new ModelAndView("jspalarm/report/reportCatrucSeverity");
				}
			/*Lay du lieu tong hop so luong canh bao theo muc do canh bao
			 	 * @param sdate: Thoi gian bat dau
				 * @param edate: Thoi gian ket thuc
				 * @param network: Loai canh báo: 2G,3G,PS CORE, CS CORE
				 * @param province: Tinh /thanh pho
				 * @param vendor: Nha cung cap
				 * @param team: To
				 * @param district: Quan huyen
				 * @param bscid: Nhieu bsc
				 * @param type: Tong hop cho canh bao: tong hop so luong (total), canh bao tong dong (nonfinish), canh bao lau ket thuc(finishLately), canh bao chap chon(jitter)
				 * function : 2G/3G
				 * */
				@RequestMapping("dataCatruc")
				public @ResponseBody 
				void dataCatruc(@RequestParam(required = true) String type,
						@RequestParam(required = false) String day,
						@RequestParam(required = false) String shift,
						@RequestParam(required = false) String network,
						@RequestParam(required = false) String province,
						@RequestParam(required = false) String vendor,
						@RequestParam(required = false) String bscid,
						@RequestParam(required = false) String username,
						HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
					
					
					List<DyAlDetailTotalQuality> reportList = new ArrayList<DyAlDetailTotalQuality>();
					
					reportList = dyAlDetailTotalQualityDAO.getReportOfShift(day,shift,network,province, vendor, bscid, type,username);	
					
					Gson gs = new Gson();
					String jsonCartList = gs.toJson(reportList);
					PrintWriter out = response.getWriter();
					response.setContentType("application/json");
					out.println(jsonCartList);
					out.close();
					//return reportList;
				 }
				
				//Them, sua
				@RequestMapping(value = "form", method = RequestMethod.GET)
				public String form(@RequestParam (required = true) String id,
						@RequestParam (required = true) String netWork,
						@RequestParam (required = true) String function,
						HttpServletRequest request, Model model) {
					String username = SecurityContextHolder.getContext().getAuthentication().getName();
					List<SYS_PARAMETER> sysParemeterTitle = dyAlDetailTotalQualityDAO.titleForm(null,function,netWork,"DETAIL_ADD");
					if(sysParemeterTitle.size() > 0)
					{
						model.addAttribute("titleForm", sysParemeterTitle.get(0).getValue());
					}
					

					DyAlDetailNonFinish rAlarmLog = new DyAlDetailNonFinish();
					String ne="";
					String dict="";
					String alarmType="";
					String province="";
					String group="";
					if (id!=null&&!id.equals(""))
					{
						rAlarmLog = dyAlDetailNonFinishDAO.selectByPrimaryKey(Integer.parseInt(id));
						if (rAlarmLog!=null)
						{
							ne = rAlarmLog.getNe();
							dict = rAlarmLog.getDistrict();
							province = rAlarmLog.getProvince();
							
							if (rAlarmLog.getAlarmType()!=null)
							{
								alarmType = rAlarmLog.getAlarmType();
							}
							if (rAlarmLog.getGroups()!=null)
							{
								group = rAlarmLog.getGroups();
							}
							model.addAttribute("sdate", rAlarmLog.getSdateStr());
							model.addAttribute("edate", rAlarmLog.getEdateStr());
							model.addAttribute("bscid", ne);		
							model.addAttribute("site", rAlarmLog.getSite());		
							model.addAttribute("cellid", rAlarmLog.getCellid());		
							model.addAttribute("vendor", rAlarmLog.getVendor());		
							model.addAttribute("alarmType", alarmType);		
							model.addAttribute("severity", rAlarmLog.getSeverity());		
							model.addAttribute("district", dict);		
							model.addAttribute("dept", rAlarmLog.getDept());		
							model.addAttribute("team", rAlarmLog.getGroups());		
							model.addAttribute("causeby", rAlarmLog.getCauseby());		
							model.addAttribute("resultContent", rAlarmLog.getResultContent());		
							model.addAttribute("alarmName", rAlarmLog.getAlarmMappingName());		
							model.addAttribute("causebySys", rAlarmLog.getCausebySys());		
						}
					}
					else
					{
						String stime="";
						Calendar cal = Calendar.getInstance();	
						cal.setTime(new Date());
						stime = df.format(cal.getTime());
						
						model.addAttribute("sdate", stime);
						
					}
					//Lay danh sach district theo user
					/*List<String> districtList = sysUserAreaDAO.getDistrictList(province,group,username);
					String districtArray="var districtList = new Array(";//Danh sach district cho cobobox
					String cn="";
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
							 */
					
					model.addAttribute("alarmLog", rAlarmLog);		
					model.addAttribute("netWork", netWork);	
					model.addAttribute("function", function);	
					
					return "jspalarm/report/nonfinishForm";
				}
				
				@RequestMapping(value = "form", method = RequestMethod.POST)
				public String onSubmit(@ModelAttribute("alarmLog") @Valid DyAlDetailNonFinish alarmLog, BindingResult result,
						@RequestParam(required = false) String netWork,
						@RequestParam(required = false) String function,
						@RequestParam(required = false) String bscid,
						@RequestParam(required = false) String sdate,
						@RequestParam(required = false) String edate,
						@RequestParam (required = false) String causebySys,
						ModelMap model, HttpServletRequest request) throws ParseException {
					
					String username = SecurityContextHolder.getContext().getAuthentication().getName();
					SysUsers userLogin = sysUsersDao.selectByPrimaryKey(username);
					model.addAttribute("alarmLog", alarmLog);	
					
					List<SYS_PARAMETER> sysParemeterTitle = dyAlDetailTotalQualityDAO.titleForm(null,function,netWork,"DETAIL_ADD");
					if(sysParemeterTitle.size() > 0)
					{
						model.addAttribute("titleForm", sysParemeterTitle.get(0).getValue());
					}
					
					model.addAttribute("causebySys", causebySys);	
					model.addAttribute("bscid", alarmLog.getNe());	
					model.addAttribute("sdate", alarmLog.getSdateStr());	
					model.addAttribute("edate", edate);	
				   
					if (alarmLog.getCauseby()!=null  && alarmLog.getCauseby().length()>=250)  
					{
						model.addAttribute("errorcauseby", "*");
						saveMessageKey(request, "message.error.overlenght");
						model.addAttribute("netWork", netWork);	
						model.addAttribute("function", function);	
						
						return "jspalarm/report/nonfinishForm";
					}
					if (alarmLog.getResultContent()!=null  && alarmLog.getResultContent().length()>=500)
					{
						model.addAttribute("erroractionProcess", "*");
						saveMessageKey(request, "message.error.overlenght");
						model.addAttribute("netWork", netWork);	
						model.addAttribute("function", function);	
						
						return "jspalarm/report/nonfinishForm";
					}
					
					if (result.hasErrors() ) {
						saveMessageKey(request, "alarmExtend.errorField");
						model.addAttribute("netWork", netWork);	
						model.addAttribute("function", function);	
						
						 return "jspalarm/report/nonfinishForm";
					}
					else
					{
						
		
						
						alarmLog.setNetwork(netWork);
						alarmLog.setCausebySys(causebySys);
						//alarmLog.setSeverity(severity);
						if (edate!=null && !edate.equals("") && alarmLog.getSdate().getTime()>= alarmLog.getEdate().getTime())
						{
							saveMessageKey(request, "cautruc.sosanhNgay");
							model.addAttribute("netWork", netWork);	
							model.addAttribute("function", function);	
							
							return "jspalarm/report/nonfinishForm";
						}
						if (alarmLog.getId()==null)
						{
							alarmLog.setCreateDate(new Date());
							alarmLog.setCreatedBy(username);
							dyAlDetailNonFinishDAO.insert(alarmLog);
							saveMessageKey(request, "alarmExtend.insertSucceFull");
						}
						else
						{
							//sua chua
							alarmLog.setModifiedBy(username);
							alarmLog.setModifyDate(new Date());
							dyAlDetailNonFinishDAO.updateByPrimaryKey(alarmLog);
							saveMessageKey(request, "alarmExtend.updateSuccelfull");	
						}
					}
					//model.addAttribute("district", "");
					model.addAttribute("severity", "");
					model.addAttribute("alarmName", "");
					//model.addAttribute("team", "");
					model.addAttribute("bscid","");	
					model.addAttribute("sdate","");	
					model.addAttribute("edate","");	
					model.addAttribute("districtList", "");    
					System.out.println("netWork "+netWork);
					
					return "redirect:detail/"+function+".htm?netWork="+netWork;
				}
				

}
