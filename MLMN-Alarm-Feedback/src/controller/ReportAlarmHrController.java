package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import vo.CTableConfig;
import vo.DyAlDetailTotalQuality;
import vo.HrAlDetaiTotalQuality;
import vo.SYS_PARAMETER;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.HelpTableConfigs;

import dao.CConfigAlarmTypeDAO;
import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.HrAlDetaiTotalQualityDAO;
import dao.HrAlFinishQualityDAO;
import dao.HrAlNonFinishQualityDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUserAreaDAO;
import dao.SysUserEquipmentNameDAO;

@Controller
@RequestMapping("/report/hr/*")
public class ReportAlarmHrController extends BaseController {
	
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired
	private CHighlightConfigsDAO cHighlightConfigsDAO;

	@Autowired
	private CConfigAlarmTypeDAO cConfigAlarmTypeDAO;
	
	@Autowired
	private HrAlNonFinishQualityDAO hrAlNonFinishQualityDAO;
	
	@Autowired
	private HrAlFinishQualityDAO hrAlFinishQualityDAO;
	
	@Autowired
	private SysUserAreaDAO sysUserAreaDAO;
	
	@Autowired
	private SysUserEquipmentNameDAO sysUserEquipmentNameDAO;
	
	@Autowired
	private HrAlDetaiTotalQualityDAO hrAlDetaiTotalQualityDAO;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
	
	@RequestMapping(value = "severity/{function}")
	public ModelAndView list(
			@RequestParam(required = false) String sdate,
			@RequestParam(required = false) String edate,
			@RequestParam(required = false) String shour,
			@RequestParam(required = false) String ehour,
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
		List<SYS_PARAMETER> sysParemeterTitle = hrAlDetaiTotalQualityDAO.titleForm("severity",function,type,null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
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
			if (!function.equalsIgnoreCase("nonfinish"))
			{
				cal.add(Calendar.DATE, -1);
			}
			sdate = df.format(cal.getTime());
			cal.add(Calendar.DATE, -29);
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
		
		/* Begin Grid*/
		String nameTable="REPORT_HR_SERVERITY_"+type;
		
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
		String url = "datahHrSeverity.htm?type="+type+"&sdate="+sdate+"&edate="+edate+"&shour="+shour+"&ehour="+ehour+
				"&network="+network+"&province="+province+
				"&vendor="+vendor+"&team="+team+
				"&district="+district+"&bscid="+bscid+"&region="+region+"&username="+username+"&function="+function;
		//Grid
		String gridReport = HelpTableConfigs.getGridPagingReportUrl(configList, "gridReport", url, null, null, "menuReport", highligh,"singlecell",null,null);
		model.addAttribute("gridReport", gridReport);		
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "ReportHrSeriverty"+type+ function + dateNow;
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
		
		return new ModelAndView("jspalarm/report/hr/reportSeverity");
	}
	
	@RequestMapping("severity/datahHrSeverity")
	public @ResponseBody 
	void processForm(@RequestParam(required = true) String type,
			@RequestParam(required = false) String sdate,
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
			@RequestParam(required = false) String function,
			HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
		
		List<HrAlDetaiTotalQuality> reportList = new ArrayList<HrAlDetaiTotalQuality>();
		reportList = hrAlDetaiTotalQualityDAO.getReportHrWithSeverity(sdate,edate,shour,ehour,network,province, vendor,team, district, bscid, region, type, function,username);	
		
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(reportList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
		//return reportList;
	 }
}
