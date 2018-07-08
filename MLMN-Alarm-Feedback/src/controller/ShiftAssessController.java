package controller;

import java.io.File;
import java.io.FileInputStream;
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
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import vo.AlShiftAssess;
import vo.CHighlightConfigs;
import vo.CTableConfig;
import vo.CountAlarmLog;
import vo.DyAlDetailNonFinish;
import vo.DyAlDetailTotalQuality;
import vo.RAlarmLog;
import vo.SYS_PARAMETER;
import vo.W_WORKING_MANAGEMENTS;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;

import dao.AlShiftAssessDAO;
import dao.AlShiftAssessDAOImpl;
import dao.AlShiftDAO;
import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.RAlarmLogDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUserAreaDAO;
import dao.SysUsersDAO;
import dao.W_WORKING_MANAGEMENTSDAO;

@Controller
@RequestMapping("/assess/*")
public class ShiftAssessController extends BaseController {
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired 
	private AlShiftDAO alShiftDAO;
	
	@Autowired
	private RAlarmLogDAO rAlarmLogDAO;
	
	@Autowired
	private W_WORKING_MANAGEMENTSDAO working_managementDao;
	
	@Autowired 
	private SysUsersDAO sysUsersDao;
	
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
	@Autowired
	private AlShiftAssessDAO alShiftAssessDAO;
	
	@Autowired
	private SysUserAreaDAO sysUserAreaDAO;
	
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping(value = "cham-diem-ca-truc")
	public ModelAndView list(
			@RequestParam(required = false) String function,
			@RequestParam(required = false) String dateF,
			@RequestParam(required = false) String dateT,
			@RequestParam(required = false) String shift,
			@RequestParam(required = false) String dept,
			@RequestParam(required = false) String team,
			@RequestParam(required = false) String groups,
			@RequestParam(required = false) String users,
			@RequestParam(required = false) Integer startWeek,
			@RequestParam(required = false) Integer endWeek,
			@RequestParam(required = false) Integer startMonth, 
			@RequestParam(required = false) Integer startYear,
			@RequestParam(required = false) Integer endMonth, 
			@RequestParam(required = false) Integer endYear,
			@RequestParam(required = false) String type,
			@RequestParam(required = false) String network,
			@RequestParam(required = false) String neType,
			Model model, HttpServletRequest request) throws ParseException {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SYS_PARAMETER> sysParemeterTitle = alShiftAssessDAO.titleForm(function,null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		//Kiem tra dieu kien tim kiem 
		
		Calendar cal = Calendar.getInstance();	
		if (function.equalsIgnoreCase("week")) {
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
		}
		else if (function.equalsIgnoreCase("month")) {
			if (endMonth == null) {
				cal.add(Calendar.MONTH, -1);
				endMonth = cal.get(Calendar.MONTH) + 1;
				endYear = cal.get(Calendar.YEAR);
			}

			if (startMonth == null) {
				startMonth = endMonth;
				startYear = endYear;
			}
			model.addAttribute("startMonth", startMonth);
			model.addAttribute("endMonth", endMonth);
			model.addAttribute("startYear", startYear);
			model.addAttribute("endYear", endYear);
		}
		else
		{
			//cal.setTime(new Date());
			/*String dateFChar ="";
			String dateTChar="";
			String dateFCharLabel="";*/
			if (type!=null && !type.equals("")&&type.equals("WEEK_DETAIL")) {
				cal = Calendar.getInstance();	
				cal.set(Calendar.YEAR, startYear); 
				cal.set(Calendar.WEEK_OF_YEAR, startWeek);        
				cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
				dateF = df.format(cal.getTime());
				
				cal.add(Calendar.DATE,6);
				dateT = df.format(cal.getTime());
			}
			else if (type!=null && !type.equals("")&&type.equals("MONTH_DETAIL")) {
				Calendar c1 =  Calendar.getInstance();	
				c1.set(Calendar.YEAR, startYear); 
				c1.set(Calendar.MONTH, startMonth-1);        
				c1.set(Calendar.DAY_OF_MONTH, 1);
				dateF = df.format(c1.getTime());
				
				Calendar c2 =  Calendar.getInstance();	
				c2.set(Calendar.YEAR, endYear); 
				c2.set(Calendar.MONTH, endMonth-1);        
				c2.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				dateT = df.format(c2.getTime());
			}
			else
			{
				if (dateT == null || dateT.equals("")
						||(dateT!=null && !dateT.equals("") && DateTools
									.isValid("dd/MM/yyyy", dateT)==false))
				{
					cal.setTime(new Date());
					dateT = df.format(cal.getTime());
				}
				
				if (dateF == null || dateF.equals("")
						||(dateF!=null && !dateF.equals("") && DateTools
									.isValid("dd/MM/yyyy", dateF)==false))
				{
					dateF = df.format(cal.getTime());
					/*dateFChar = df.format(cal.getTime());
					dateFCharLabel = df.format(cal.getTime());*/
				}
			}
				
			//dateTChar = df.format(df.parse(dateT));
			System.out.println("dateFChar:"+dateF);
			System.out.println("dateTChar:"+dateT);
			model.addAttribute("dateF", dateF);			
			model.addAttribute("dateT", dateT);
		}
		if (shift!=null)
		{
			shift=shift.trim();
		}
		else
		{
			shift="";
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
		if (groups!=null)
		{
			groups=groups.trim();
		}
		else
		{
			groups="";
		}
		if (users!=null)
		{
			users=users.trim();
		}
		else
		{
			users="";
		}
		if (network!=null)
		{
			network=network.trim();
		}
		else
		{
			network="";
		}
		if (neType!=null)
		{
			neType=neType.trim();
		}
		else
		{
			neType="";
		}		
				List<SYS_PARAMETER> caListName = new ArrayList<SYS_PARAMETER>();
				caListName = sysParameterDao.getSysParameterByCode("CA_TRUC_NAME");
				model.addAttribute("caList", caListName);
				String cn="";
				//Lay danh sach team theo user
				List<String> teamList = sysUserAreaDAO.getSubTeamList(null,null);
				String teamArray="var teamList = new Array(";//Danh sach district cho cobobox
				cn="";
				for (String dis : teamList) {
					teamArray = teamArray + cn +"\""+dis+"\"";
					cn=",";
				}
				teamArray = teamArray+");";
				model.addAttribute("teamList", teamArray);
				
				String nameTable="SHIFT_ASSESS";
				if (!function.equalsIgnoreCase(""))
				{
					nameTable  = nameTable+"_"+function.toUpperCase();
				}
				
				//highligh
				String highligh="     var cellsrenderer = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {";
				highligh += "       	return '<span style=\"margin: 4px; float: ' + columnproperties.cellsalign + '; color: #0000FF;text-decoration: underline;\">' + value + '</span>';";   	
				highligh += "         };";
				//do du lieu ra grid
				List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid(nameTable);
				//lay du lieu column cua grid
				model.addAttribute("column", HelpTableConfigs.getColumns(configList)); 
				List<CTableConfig> listSource = cTableConfigDAO.getColumnList(nameTable);
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
				//Lay du lieu datafield cua grid
				model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
				String url = "dataList.htm?function="+function+"&dateF="+dateF+"&dateT="+dateT+
						"&shift="+shift+"&dept="+dept+"&network="+network+"&neType="+neType+
						"&team="+team+"&groups="+groups+"&users="+users+"&startWeek="+startWeek+
						"&endWeek="+endWeek+"&startMonth="+startMonth+"&endMonth="+endMonth+"&startYear="+startYear+"&endYear="+endYear;
				//Grid
				String gridReport = HelpTableConfigs.getGridPagingReportUrl(configList, "grid", url, "jqxlistbox", listSource, "menuReport", highligh,"singlecell",groupColumn,null);
				model.addAttribute("grid", gridReport);		
				
				// Lay ten file export
				Calendar currentDate = Calendar.getInstance();
				SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
				String dateNow = formatter.format(currentDate.getTime());
				String exportFileName = "ChamDiemCaTruc_"+ function + dateNow;
				model.addAttribute("exportFileName", exportFileName);
				
				model.addAttribute("dept", dept);
				model.addAttribute("shift", shift);
				model.addAttribute("team", team);
				model.addAttribute("groups", groups);
				model.addAttribute("users", users);
				model.addAttribute("function", function);
				model.addAttribute("username", username);
				model.addAttribute("network", network);
				model.addAttribute("neType", neType);
			
				
				return new ModelAndView("jspalarm/report/reportShiftAssess");
	}
	@RequestMapping("dataList")
	public @ResponseBody 
	void dataList(@RequestParam(required = true) String function,
			@RequestParam(required = false) String dateF,
			@RequestParam(required = false) String dateT,
			@RequestParam(required = false) String shift,
			@RequestParam(required = false) String dept,
			@RequestParam(required = false) String network,
			@RequestParam(required = false) String neType,
			@RequestParam(required = false) String team,
			@RequestParam(required = false) String groups,
			@RequestParam(required = false) String users,
			@RequestParam(required = false) String username,
			@RequestParam(required = false) String startWeek,
			@RequestParam(required = false) String endWeek,
			@RequestParam(required = false) String startMonth, 
			@RequestParam(required = false) String endMonth, 
			@RequestParam(required = false) String startYear,
			@RequestParam(required = false) String endYear,
			HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
		
		
		List<AlShiftAssess> reportList = new ArrayList<AlShiftAssess>();
		reportList = alShiftAssessDAO.getReportShiftAssess(function,dateF,dateT,shift, dept,team, groups, users,username,startWeek,endWeek,startMonth,endMonth,startYear,endYear,network,neType);	
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
		@RequestMapping(value = "detail")
		public String detail(
				@RequestParam(required = false) String function,
				@RequestParam(required = false) String dateF,
				@RequestParam(required = false) String dateT,
				@RequestParam(required = false) String catruc,
				@RequestParam(required = false) Integer startWeek,
				@RequestParam(required = false) Integer endWeek,
				@RequestParam(required = false) Integer startMonth,
				@RequestParam(required = false) Integer endMonth,
				@RequestParam(required = false) Integer startYear,
				@RequestParam(required = false) Integer endYear,
				@RequestParam(required = false) String users,
				@RequestParam(required = false) String status,
				@RequestParam(required = false) String network,
				@RequestParam(required = false) String neType,
				@RequestParam(required = false) String severity,
				@RequestParam(required = false) String columnheader,
				@RequestParam(required = false) String type,
				Model model, HttpServletRequest request) throws ParseException {
			
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			List<SYS_PARAMETER> sysParemeterTitle = alShiftAssessDAO.titleForm(type,"DETAIL");
			if(sysParemeterTitle.size() > 0)
			{
				model.addAttribute("title", sysParemeterTitle.get(0).getValue());
			}
			if (function!=null && !function.equals(""))
			{
				Calendar cal = Calendar.getInstance();	
				if (function.equals("week")) {
					cal.set(Calendar.YEAR, startYear); 
					cal.set(Calendar.WEEK_OF_YEAR, startWeek);        
					cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
					dateF = df.format(cal.getTime());
					
					cal.add(Calendar.DATE,6);
					dateT = df.format(cal.getTime());
				}
				else if (function.equals("month")) {
					Calendar c1 =  Calendar.getInstance();	
					c1.set(Calendar.YEAR, startYear); 
					c1.set(Calendar.MONTH, startMonth-1);        
					c1.set(Calendar.DAY_OF_MONTH, 1);
					dateF = df.format(c1.getTime());
					
					Calendar c2 =  Calendar.getInstance();	
					c2.set(Calendar.YEAR, endYear); 
					c2.set(Calendar.MONTH, endMonth-1);        
					c2.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
					dateT = df.format(c2.getTime());
				}
				else
				{
					if (dateT == null || dateT.equals("")
							||(dateT!=null && !dateT.equals("") && DateTools
										.isValid("dd/MM/yyyy", dateT)==false))
					{
						cal.setTime(new Date());
						dateT = df.format(cal.getTime());
					}
					
					if (dateF == null || dateF.equals("")
							||(dateF!=null && !dateF.equals("") && DateTools
										.isValid("dd/MM/yyyy", dateF)==false))
					{
						dateF = df.format(cal.getTime());
					}
				}
					
				//dateTChar = df.format(df.parse(dateT));
				if (type!=null && type.equalsIgnoreCase("alarm"))
				{
					List<SYS_PARAMETER> caList = new ArrayList<SYS_PARAMETER>();
					caList = sysParameterDao.getSysParameterByCode("CA_TRUC_VALUE");
					String sang = caList.get(0).getValue();
					String chieu = caList.get(1).getValue();
					String toi = caList.get(2).getValue();
					if (catruc !=null && catruc.startsWith("S"))
					{
						dateF= dateF+" "+sang+":30:00";
						dateT= dateT+" "+chieu+":30:00";
					}
					else if (catruc !=null && catruc.startsWith("C"))
					{
						dateF= dateF+" "+chieu+":30:00";
						dateT= dateT+" "+toi+":30:00";
					}
					else if (catruc !=null && catruc.startsWith("T"))
					{
						dateF= dateF+" "+toi+":30:00";
						
						Date dto=df.parse(dateT);
						Calendar cal3 = Calendar.getInstance();	
						cal3.setTime(dto);
						cal3.add(Calendar.DATE,1);
						dateT= df.format(cal3.getTime())+" "+sang+":30:00";
					}
					else
					{
						dateF= dateF+" 00:00:00";
						dateT= dateT+" 23:59:00";
					}
					
				}
			}
			else
			{
				if (type!=null && type.equalsIgnoreCase("alarm"))
				{
					if (dateF == null || dateF.equals("")
							||(dateF!=null && !dateF.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm", dateF)==false))
					{
						Calendar cal = Calendar.getInstance();	
						cal.setTime(new Date());
						dateF = df.format(cal.getTime())+" "+"00:00";
						
					}
					
					if (dateT == null || dateT.equals("")
							||(dateT!=null && !dateT.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm", dateT)==false))
					{
						
						Calendar cal = Calendar.getInstance();	
						cal.setTime(new Date());
						dateT = df.format(cal.getTime())+" "+"23:59";
					}
				}
				else
				{
					if (dateF == null || dateF.equals("")
							||(dateF!=null && !dateF.equals("") && DateTools.isValid("dd/MM/yyyy", dateF)==false))
					{
						Calendar cal = Calendar.getInstance();	
						cal.setTime(new Date());
						cal.add(Calendar.DATE,-1);
						dateF = df.format(cal.getTime());
						
					}
					
					if (dateT == null || dateT.equals("")
							||(dateT!=null && !dateT.equals("") && DateTools.isValid("dd/MM/yyyy", dateT)==false))
					{
						
						Calendar cal = Calendar.getInstance();	
						cal.setTime(new Date());
						dateT = df.format(cal.getTime());
					}
				}
			}
			System.out.println("dateFChar:"+dateF);
			System.out.println("dateTChar:"+dateT);
			
			model.addAttribute("dateF", dateF);			
			model.addAttribute("dateT", dateT);
			if (catruc!=null)
			{
				catruc=catruc.trim();
			}
			else
			{
				catruc="";
			}
			if (users!=null)
			{
				users=users.trim();
			}
			else
			{
				users="";
			}
			if (status!=null)
			{
				status=status.trim();
			}
			else
			{
				status="";
			}
			if (network!=null)
			{
				network=network.trim();
			}
			else
			{
				network="";
			}
			if (neType!=null)
			{
				neType=neType.trim();
			}
			else
			{
				neType="";
			}
			if (severity!=null)
			{
				severity=severity.trim();
			}
			else
			{
				severity="";
			}
			if (columnheader!=null)
			{
				columnheader=columnheader.trim();
			}
			else
			{
				columnheader="";
			}
			List<SYS_PARAMETER> caListName = new ArrayList<SYS_PARAMETER>();
			caListName = sysParameterDao.getSysParameterByCode("CA_TRUC_NAME");
			model.addAttribute("caList", caListName);
			
			List<SYS_PARAMETER> statusList = new ArrayList<SYS_PARAMETER>();
			if (type!=null && type.equalsIgnoreCase("alarm"))
			{
				statusList = sysParameterDao.getSysParameterByCode("STATUS_FINISH");
			}
			else
			{
				statusList = sysParameterDao.getSysParameterByCode("ASSESS_WORK");
				if (status.equals("Y"))
				{
					status="Đạt";
				}
			}
			model.addAttribute("statusList", statusList);
			
			
			String nameTable="";
			if (type!=null && type.equalsIgnoreCase("alarm"))
			{
					nameTable="ALARM_IN_ASSESS";
			}
			else
			{
				nameTable="WORK_IN_ASSESS";
			}
			
			//do du lieu ra grid
			List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid(nameTable);
			//lay du lieu column cua grid
			model.addAttribute("column", HelpTableConfigs.getColumns(configList)); 
			List<CTableConfig> listSource = cTableConfigDAO.getColumnList(nameTable);
			//Lay du lieu datafield cua grid
			model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
			if (type!=null && type.equalsIgnoreCase("alarm"))
			{
				model.addAttribute("url", "dataAlarmDetail.htm?function="+function+"&dateF="+dateF+"&dateT="+dateT+
						"&catruc="+catruc+"&users="+users+"&status="+status+"&network="+network+"&neType="+neType+"&severity="+severity+
						"&columnheader="+columnheader+"&type="+type); 
				model.addAttribute("listSource", HelpTableConfigs.getListSource(listSource));
			}
			else
			{
				String url = "dataWorkDetail.htm?function="+function+"&dateF="+dateF+"&dateT="+dateT+
						"&catruc="+catruc+"&users="+users+"&status="+status+"&columnheader="+columnheader+
						"&type="+type;
				//Grid
				String gridReport = HelpTableConfigs.getGridPagingUrl(configList, "jqxgrid", url,"jqxlistbox", listSource, "Menu", null, "singlecell","Y");
				model.addAttribute("gridDetail", gridReport);		
			}
			// Lay ten file export
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
			String dateNow = formatter.format(currentDate.getTime());
			String exportFileName = "CHI_TIET_"+ function + dateNow;
			model.addAttribute("exportFileName", exportFileName);
			
			
			model.addAttribute("catruc", catruc);
			model.addAttribute("users", users);
			model.addAttribute("function", function);
			model.addAttribute("status", status);
			model.addAttribute("type", type);
			model.addAttribute("network", network);
			model.addAttribute("neType", neType);
			model.addAttribute("severity", severity);
			model.addAttribute("columnheader", columnheader);
			
			return "jspalarm/report/reportDetailAssess";
		}
		@RequestMapping("dataWorkDetail")
		public @ResponseBody 
		void dataWorkDetail(@RequestParam(required = false) String function,
				@RequestParam(required = false) String dateF,
				@RequestParam(required = false) String dateT,
				@RequestParam(required = false) String catruc,
				@RequestParam(required = false) String users,
				@RequestParam(required = false) String status,
				@RequestParam(required = false) String columnheader,
				@RequestParam(required = false) String type,
				HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
			
			Gson gs = new Gson();
			String jsonCartList = "";
			List<W_WORKING_MANAGEMENTS> dataList = working_managementDao.getWorkingDetailInAssess(dateF,dateT,catruc, users,type,status,columnheader);
			jsonCartList = gs.toJson(dataList);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.println(jsonCartList);
			out.close();
			
		 }
		
		@RequestMapping("dataAlarmDetail")
		public @ResponseBody 
		Map<String, Object> dataAlarmDetail(@RequestParam(required = false) String function,
				@RequestParam(required = false) String dateF,
				@RequestParam(required = false) String dateT,
				@RequestParam(required = false) String catruc,
				@RequestParam(required = false) String users,
				@RequestParam(required = false) String status,
				@RequestParam(required = false) String network,
				@RequestParam(required = false) String neType,
				@RequestParam(required = false) String severity,
				@RequestParam(required = false) String columnheader,
				@RequestParam(required = false) String type,
				HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
		
			Map<String, Object> data = new HashMap<String, Object>();
			List<RAlarmLog> alarmList = new ArrayList<RAlarmLog>();
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
			
			String nameTable="ALARM_IN_ASSESS";
			List<CTableConfig> columnList = null;
			List<CTableConfig> tableConfigList = null;
			columnList = cTableConfigDAO.getTableConfigGet(nameTable, sortfield);
			tableConfigList = cTableConfigDAO.getTableConfigGet(nameTable, null);
			
			// Tim kiem tren grid
			String strWhere = HelpTableConfigs.filter(request);
			for(CTableConfig column: tableConfigList)
			{
				strWhere = strWhere.toUpperCase().replaceAll(column.getDataField().toUpperCase(), column.getTableColumn());
			}
			// Sap xep
			for(CTableConfig column: columnList)
			{
				sortfield = column.getTableColumn();
				break;
			}
				System.out.println("strWhere: "+strWhere);	
				System.out.println("function: "+function);	
			int totalRow =0;
			try
			{
				totalRow = rAlarmLogDAO.countAlarmDetailAssess(dateF,dateT,catruc, users,status,network,neType,severity,columnheader,strWhere);	
				alarmList = rAlarmLogDAO.getAlarmDetailAssess(dateF,dateT,catruc, users,status,network,neType,severity,columnheader,strWhere,startRecord,endRecord,sortfield,sortorder);	
			}
			catch(Exception exp)
			{
				alarmList=null;
			}
				data.put("totalRow", totalRow);
				data.put("Rows", alarmList);
			return data;
		 }
		
		@RequestMapping("export")
		public String export(@RequestParam(required = false) String function,
				@RequestParam(required = false) String dateF,
				@RequestParam(required = false) String dateT,
				@RequestParam(required = false) String catruc,
				@RequestParam(required = false) String users,
				@RequestParam(required = false) String status,
				@RequestParam(required = false) String network,
				@RequestParam(required = false) String neType,
				@RequestParam(required = false) String severity,
				@RequestParam(required = false) String columnheader,
				@RequestParam(required = false) String type,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			
			if (catruc==null)
				catruc = "";
			if (users==null)
				users = "";
			if (status==null)
				status = "";
			// temp file
			String basePath = request.getSession().getServletContext().getRealPath("");
			String dataDir = basePath + "/upload";

			String tempName = UUID.randomUUID().toString();

			String ext = "xls";

			String outfile = tempName + "." + ext;
			// return
			// Lay ten file export
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
			String dateNow = formatter.format(currentDate.getTime());
			String exportFileName ="";		
			File tempFile = new File(dataDir + "/" + outfile);
			if (type!=null && type.equalsIgnoreCase("alarm"))
			{
				exportFileName = "DanhSachAlarm" + dateNow;
				List<String> paramList = new ArrayList<String>();
				paramList.add(dateF);
				paramList.add(dateT);
				paramList.add(catruc);
				paramList.add(users);
				paramList.add(status);
				paramList.add(network);
				paramList.add(neType);
				paramList.add(severity);
				paramList.add(columnheader);
				paramList.add("");
				paramList.add("");
				paramList.add("");
				paramList.add("");
				paramList.add("");
				List<CTableConfig> columnList = cTableConfigDAO.getColumnList("ALARM_IN_ASSESS");
				ExportTools.exportToExcel("PK_AL_SHIFT_MLMN.PR_ALARM_ASSESS_GET(?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?,?,?)",paramList,tempFile,exportFileName, "DanhSachAlarm","Danh sách cảnh báo",columnList,"N");
				
			}
			else
			{
				exportFileName = "DanhSachCV" + dateNow;
				List<String> paramList = new ArrayList<String>();
				paramList.add(dateF);
				paramList.add(dateT);
				paramList.add(catruc);
				paramList.add(users);
				paramList.add(status);
				paramList.add(type);
				paramList.add(columnheader);
				List<CTableConfig> columnList = cTableConfigDAO.getColumnList("WORK_IN_ASSESS");
				ExportTools.exportToExcel("PK_AL_SHIFT_MLMN.PR_WORKING_ASSESS_GET(?, ?, ?, ?, ?,?,?,?)",paramList,tempFile,exportFileName, "DanhSachCongViec","Danh sách công việc",columnList,"Y");
			
			}
			response.setContentType("application/ms-excel");
			response.setContentLength((int) tempFile.length());
			response.setHeader("Content-Disposition", "attachment; filename=\"" + exportFileName + "." + ext + "\"");
			FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());
			tempFile.delete();
			
			return null;
		}

}
