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

import vo.CConfigAlarmType;
import vo.CHighlightConfigs;
import vo.CTableConfig;
import vo.CountAlarmLog;
import vo.DyAlDetailFinish;
import vo.DyAlDetailNonFinish;
import vo.DyAlDetailTotalQuality;
import vo.DyIRSR;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.HelpTableConfigs;

import com.google.gson.Gson;

import dao.AlShiftDAO;
import dao.CConfigAlarmTypeDAO;
import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.DyAlDetailFinishDAO;
import dao.DyAlDetailNonFinishDAOImpl;
import dao.DyAlDetailTotalQualityDAO;
import dao.SysUserAreaDAO;
import dao.SysUserEquipmentNameDAO;
import dao.SysUsersDAO;

@Controller
@RequestMapping("/report/*")
public class ReportIRSRController extends BaseController {
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	
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
	private DyAlDetailNonFinishDAOImpl dyAlDetailNonFinishDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private DateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");

	/*Bao cao IRSR 2G,3G
		 * @param sdate: Thoi gian bat dau
		 * @param edate: Thoi gian ket thuc
		 * @param network: Loai canh báo: 2G,3G
		 * @param team: To
		 * @param district: Quan huyen
		 * @param bscid: Nhieu bsc
		 * @param site: Nhieu site
		 * @param type: site,NE,TEAM,DISTRICT
		*/
			@RequestMapping(value = "irsr/list")
			public ModelAndView alarmTypelist(
					@RequestParam(required = false) String sdate,
					@RequestParam(required = false) String edate,
					@RequestParam(required = false) String network,
					@RequestParam(required = false) String bscid,
					@RequestParam(required = false) String team,
					@RequestParam(required = false) String district,
					@RequestParam(required = false) String site,
					@RequestParam(required = true) String type,
					@RequestParam(required = false) String dept,
					Model model, HttpServletRequest request) throws ParseException {
				
				String username = SecurityContextHolder.getContext().getAuthentication().getName();
				List<SYS_PARAMETER> sysParemeterTitle = dyAlDetailTotalQualityDAO.titleForm("IRSR",null,type,null);
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
				
				if (network!=null&&network!=""&&network!="ALL")
				{
					network=network.trim();
				}
				else
				{
					network="2G";

				}
				
				if (team!=null)
				{
					team=team.trim();
				}
				else
				{
					team="";
				}
				if (dept!=null)
				{
					dept=dept.trim();
				}
				else
				{
					dept="";
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
					site=site.trim();
				}
				else
				{
					site="";
				}
				
				
				//Lay danh sach BSC theo user
				List<String> bscList = sysUserEquipmentNameDAO.getBSCList(network,username,null);
				String bscArray="var bscList = new Array(";//Danh sach bsc cho cobobox
				       
				String cn="";
				for (String bsc : bscList) {
					bscArray = bscArray + cn +"\""+bsc+"\"";
					cn=",";
				}
				bscArray = bscArray+");";
				model.addAttribute("bscList", bscArray);
				//Lay danh sach Cell theo user
				List<String>  siteList = sysUserEquipmentNameDAO.getCellList(network,bscid,district,username);
				//String cellListStr ="";
				String cellArray="var siteList = new Array(";
				cn="";
				for (String cell :  siteList) {
				//	cellListStr = cellListStr + cn +cell;
					cellArray = cellArray + cn +"\""+cell+"\"";
					cn=",";
				}
				cellArray = cellArray+");";
				model.addAttribute("siteList", cellArray);   
				
				//Lay danh sach district theo user
				List<String> districtList = sysUserAreaDAO.getDistrictList(null,team,username);
				String districtArray="var districtList = new Array(";//Danh sach district cho cobobox
				cn="";
				for (String dis : districtList) {
					districtArray = districtArray + cn +"\""+dis+"\"";
					cn=",";
				}
				districtArray = districtArray+");";
				model.addAttribute("districtList", districtArray);
				//Lay danh sach team theo user
				List<String> teamList = sysUserAreaDAO.getSubTeamList(null,username);
				String teamArray="var teamList = new Array(";//Danh sach district cho cobobox
				cn="";
				for (String dis : teamList) {
					teamArray = teamArray + cn +"\""+dis+"\"";
					cn=",";
				}
				teamArray = teamArray+");";
				model.addAttribute("teamList", teamArray);
				
				//Lay danh sach team theo user
				List<String> deptList = sysUserAreaDAO.getDeptListParent();
				String deptArray="var deptList = new Array(";//Danh sach district cho cobobox
				cn="";
				for (String dis : deptList) {
					deptArray = deptArray + cn +"\""+dis+"\"";
					cn=",";
				}
				deptArray = deptArray+");";
				model.addAttribute("deptList", deptArray);
				/* Begin Grid*/
				String nameTable="";
				
					nameTable  = "REPORT_IRSR_"+type;
				//do du lieu ra grid
				List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid(nameTable);
				//lay du lieu column cua grid
				/*model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
				//Lay du lieu datafield cua grid
				model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));*/
				String highligh="     var cellsrenderer = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {";
					highligh += "       	return '<span style=\"margin: 4px; float: ' + columnproperties.cellsalign + '; color: #0000FF;text-decoration: underline;\">' + value + '</span>';";   	
					highligh += "         };";
				List<CHighlightConfigs> highlightConfigListCol = cHighlightConfigsDAO.getByKey("REPORT_IRSR");
				model.addAttribute("highlight", HelpTableConfigs.highLightCol(highlightConfigListCol));	
				//lay du lieu column group cua grid
				String groupColumn="";
				List<String> columnGrid = cTableConfigDAO.getGroupList(nameTable);
				if (columnGrid.size()>0)
				{
					
					String con2="";
					groupColumn =" var columngroups=[";
					for (String alarmTypeG : columnGrid) {
						groupColumn= groupColumn+		con2+"{ text: '"+alarmTypeG+"', align: 'center', name: '"+alarmTypeG+"' }";
						con2 = ",";
					}
					groupColumn= groupColumn+	"]; ";
				}
				
				//Lay duong link url de lay du lieu do vao grid
				String url="data.htm?sdate="+sdate+"&edate="+edate+
						"&network="+network+"&bscid="+bscid+"&site="+site+
						"&team="+team+"&district="+district+
						"&username="+username+"&type="+type+"&dept="+dept; 
					//Grid
					String gridReport = HelpTableConfigs.getGridPagingReportUrl(configList, "gridReport", url, null, null, "menuReport", highligh,"singlecell",groupColumn,null);
					model.addAttribute("gridReport", gridReport);	
				// Lay ten file export
				Calendar currentDate = Calendar.getInstance();
				SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
				String dateNow = formatter.format(currentDate.getTime());
				String exportFileName = "ReportIrsr"+type+  dateNow;
				model.addAttribute("exportFileName", exportFileName);
				/* End Grid*/
				
				model.addAttribute("bscid", bscid);
				model.addAttribute("network",network);
				model.addAttribute("team", team);
				model.addAttribute("district", district);
				model.addAttribute("site", site);
				model.addAttribute("dept", dept);
				model.addAttribute("type", type);
				
				return new ModelAndView("jspalarm/report/reportIrsr");
				
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
			@RequestMapping("irsr/data")
			public @ResponseBody 
			void dataAlarmID(
					@RequestParam(required = false) String sdate,
					@RequestParam(required = false) String edate,
					@RequestParam(required = false) String network,
					@RequestParam(required = false) String bscid,
					@RequestParam(required = false) String site,
					@RequestParam(required = false) String team,
					@RequestParam(required = false) String district,
					@RequestParam(required = false) String username,
					@RequestParam(required = false) String type,
					@RequestParam(required = false) String dept,
					HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
				
				
				List<DyIRSR> reportList = new ArrayList<DyIRSR>();
				
				if (type.equalsIgnoreCase("NE"))
				{
					reportList = dyAlDetailTotalQualityDAO.getReportIRSRNe(sdate,edate,network,bscid,site, team, district,username,type,dept);	
					
				}
				else if (type.equalsIgnoreCase("SITE"))
				{
					reportList = dyAlDetailTotalQualityDAO.getReportIRSRSITE(sdate,edate,network,bscid,site, team, district,username,type,dept);	
					
				}
				else
				{
					reportList = dyAlDetailTotalQualityDAO.getReportIRSRTeam(sdate,edate,network,bscid,site, team, district,username,type,dept);	
				}
				Gson gs = new Gson();
				String jsonCartList = gs.toJson(reportList);
				PrintWriter out = response.getWriter();
				response.setContentType("application/json");
				out.println(jsonCartList);
				out.close();
			 }
		
			

}
