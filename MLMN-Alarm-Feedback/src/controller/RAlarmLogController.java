/*Author: VanAnhCT
 * Date: 03/09/2013
 * @Note: Quan ly canh bao history 2G, 3G
 */
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import vo.AlAlarmWorks;
import vo.AlHandingLibrary;
import vo.AlMonitorSite;
import vo.AlShift;
import vo.CHighlightConfigs;
import vo.CTableConfig;
import vo.CountAlarmLog;
import vo.RAlarmLog;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.HelpTableConfigs;
import dao.AlAlarmTypesDAO;
import dao.AlAlarmWorksDAO;
import dao.AlHandingLibraryDAO;
import dao.AlShiftDAO;
import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.RAlarmLogDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUserAreaDAO;
import dao.SysUserEquipmentNameDAO;
import dao.SysUsersDAO;
import dao.VAlHCellDAO;

@Controller
@RequestMapping("/alarmLog")
public class RAlarmLogController extends BaseController {
	
	@Autowired
	private VAlHCellDAO vAlHCellDao;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired
	private RAlarmLogDAO rAlarmLogDAO;
	
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	
	@Autowired
	private AlAlarmTypesDAO alAlarmTypesDAO;
	
	@Autowired
	private AlHandingLibraryDAO alHandingLibraryDAO;
	
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
	private AlAlarmWorksDAO alAlarmWorksDAO;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	// Lay id truc ca hien tai
	private AlShift getCaTrucHT(String region)
	{
		List<SYS_PARAMETER> caList = new ArrayList<SYS_PARAMETER>();
		caList = sysParameterDao.getSysParameterByCode("CA_TRUC_VALUE");
		String sang = caList.get(0).getValue();
		String chieu = caList.get(1).getValue();
		String toi = caList.get(2).getValue();
		String caT="";
		String ngayT="";
		AlShift shift = new AlShift();
		Date currentTime = new Date();
		int hour = currentTime.getHours();
		ngayT= df.format(currentTime);
		if (hour >= Integer.parseInt(sang) && hour < Integer.parseInt(chieu)) {
			caT = "Sáng";
		}
		if (hour >= Integer.parseInt(chieu) && hour < Integer.parseInt(toi)) {
			caT = "Chiều";
		}
		if (hour >= Integer.parseInt(toi) || hour < Integer.parseInt(sang)) {
			caT = "Tối";
		}
		// Lay idshift
		 shift = alShiftDAO.getCaTrucGanNhat(region);
		if (shift==null)
		{
			shift= alShiftDAO.getCaTruc(caT,ngayT,region);
		}
		return shift;
	}
	/*List danh sach canh bao history 2G,3G
	 * @param sdate: Thoi gian bat dau
	 * @param edate: Thoi gian ket thuc
	 * @param bscid: Nhieu BSC
	 * @param cellid: Nhieu Cell
	 * @param vendor: Nha cung cap
	 * @param alarmName: Ten canh bao
	 * @param reload: Trang thai load form
	 * @param reloadStr: Trang thai load form
	 * @param timeLoad: Thoi gian load form
	 * function : active,history,active-ibc,history-ibc
	 */
	@RequestMapping(value = "{function}")
	public ModelAndView list(
			@RequestParam(required = false) String sdateF,
			@RequestParam(required = false) String sdateT,
			@RequestParam(required = false) String edateF,
			@RequestParam(required = false) String edateT,
			@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String cellid,
			@RequestParam(required = false) String vendor,
			@RequestParam(required = false) String district,
			@RequestParam(required = false) String alarmName,
			@RequestParam(required = false) String province,
			@RequestParam(required = false) String team,
			@RequestParam(required = false) String alarmType,
			@RequestParam(required = false) String cbalarmMappingName,
			@RequestParam(required = false) String reload,
			@RequestParam(required = false) String reloadStr,
			@RequestParam(required = false) String statusFinish,
			@RequestParam(required = false) String severity,
			@RequestParam(required = true) String netWork,
			@RequestParam(required = false) String statusView,
			@RequestParam(required = false) String duaration,
			@RequestParam(required = false) String region,
			@RequestParam(required = false) String isSoundAlarm,
			@RequestParam(required = false) String unAlarmMappingName,
			@RequestParam(required = false) String severityF,
			@PathVariable String function,
			Model model, HttpServletRequest request) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		AlShift shift = getCaTrucHT(userLogin.getRegion());
		boolean checkCaTruc= false;
		if (shift!=null)
		{
			String[] user = shift.getNhanCaVien().split(",");
			if (shift.getNhanUsername().equals(username)||userLogin.getIsRoleManager().equals("Y"))
			{
				checkCaTruc=true;
			}
			for (String item : user) {
				if (item.equals(username))
				{
					checkCaTruc=true;
					break;
				}
			}
			model.addAttribute("checkCaTruc", checkCaTruc);
		}
		
		List<SYS_PARAMETER> sysParemeterTitle = rAlarmLogDAO.titleForm(function,netWork,null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		//reload
		if (reloadStr==null)
		{
			reload="Y";
			reloadStr="Y";
		}
		if (isSoundAlarm==null||isSoundAlarm.equals(""))
		{
			isSoundAlarm="Y";
		}
		String sdateTemp="";
		String edateTemp="";
		if (function.equalsIgnoreCase("shift"))
		{
			Calendar cal = Calendar.getInstance();	
			cal.setTime(new Date());
			
			sdateTemp = df.format(cal.getTime());
			edateTemp = df.format(cal.getTime());
			List<SYS_PARAMETER> caList = new ArrayList<SYS_PARAMETER>();
			caList = sysParameterDao.getSysParameterByCode("CA_TRUC_VALUE");
			String sang = caList.get(0).getValue();
			String chieu = caList.get(1).getValue();
			String toi = caList.get(2).getValue();
			Date currentTime = new Date();
			int hour = currentTime.getHours();
			
			if (hour >= Integer.parseInt(sang) && hour < Integer.parseInt(chieu)) {
				sdateTemp=sdateTemp+" "+sang+":00";
				edateTemp=edateTemp+" "+chieu+":00";
			}
			if (hour >= Integer.parseInt(chieu) && hour < Integer.parseInt(toi)) {
				sdateTemp=sdateTemp+" "+chieu+":00";
				edateTemp=edateTemp+" "+toi+":00";
			}
			if (hour >= Integer.parseInt(toi) || hour < Integer.parseInt(sang)) {
				sdateTemp=sdateTemp+" "+toi+":00";
				cal.add(Calendar.DATE,1);
				edateTemp=df.format(cal.getTime())+" "+sang+":00";
			}
			
		}
		
		//Kiem tra dieu kien tim kiem 
		if (sdateF == null || sdateF.equals("")
				||(sdateF!=null && !sdateF.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm", sdateF)==false))
		{
			Calendar cal = Calendar.getInstance();	
			cal.setTime(new Date());
			if (!function.equalsIgnoreCase("shift"))
			{
				if (function.equalsIgnoreCase("active"))
				{
					cal.add(Calendar.DATE,-30);
				}
				else
				{
					cal.add(Calendar.DATE,-1);
				}
				sdateF = df.format(cal.getTime())+" "+"00:00";
			}
			else
			{
				sdateF = sdateTemp;
			}
		}
		
		if (sdateT == null || sdateT.equals("")
				||(sdateT!=null && !sdateT.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm", sdateT)==false))
		{
			
			if (!function.equalsIgnoreCase("shift"))
			{
				Calendar cal = Calendar.getInstance();	
				cal.setTime(new Date());
				sdateT = df.format(cal.getTime())+" "+"23:59";
			}
			else
			{
				sdateT = edateTemp;
			}
		}
		
		model.addAttribute("sdateF", sdateF);			
		model.addAttribute("sdateT", sdateT);
		sdateF = sdateF+":00";
		sdateT = sdateT+":00";
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
		if (alarmType!=null)
		{
			alarmType=alarmType.trim();
		}
		else
		{
			alarmType="";
		}
		if (severity!=null)
		{
			severity=severity.trim();
			severityF = severity;
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
		if (statusView!=null)
		{
			statusView=statusView.trim();
		}
		else
		{
			if (function.equalsIgnoreCase("shift"))
			{
				statusView="Y";
			}
			else
			{
				statusView="";
			}
		}
		if (edateF!=null)
		{
			edateF=edateF.trim();
		}
		else
		{
			edateF="";
		}
		if (edateT!=null)
		{
			edateT=edateT.trim();
		}
		else
		{
			edateT="";
		}
		
		int duarationInt=0;
		try
		{
			duarationInt = Integer.parseInt(duaration);
		}
		catch(Exception exp)
		{
			duarationInt=0;
		}
		if (region!=null)
		{
			region=region.trim();
		}
		else
		{
			region="";
		}
		if (cbalarmMappingName!=null)
		{
			cbalarmMappingName=cbalarmMappingName.trim();
		}
		else
		{
			cbalarmMappingName="";
		}
		if (unAlarmMappingName!=null)
		{
			unAlarmMappingName=unAlarmMappingName.trim();
		}
		else
		{
			unAlarmMappingName="";
		}
		if (severityF!=null)
		{
			severityF=severityF.trim();
		}
		else
		{
			severityF="";
		}
		//Lay danh sach district theo user
		List<String> districtList = sysUserAreaDAO.getDistrictList(province,team,username);
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
		//Lay danh sach BSC theo user
		List<String> bscList = sysUserEquipmentNameDAO.getBSCList(netWork,username,vendor);
		String bscArray="var bscList = new Array(";//Danh sach bsc cho cobobox
		cn="";
		for (String bsc : bscList) {
			bscArray = bscArray + cn +"\""+bsc+"\"";
			cn=",";
		}
		bscArray = bscArray+");";
		model.addAttribute("bscList", bscArray);  
		//Lay danh sach Cell theo user
		//List<String> cellList = sysUserEquipmentNameDAO.getCellList(netWork,bscid,district,username);
		List<String> cellList = new ArrayList<String>();
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
		
		//danh sach alarm name
		List<SYS_PARAMETER> alarmList = sysParameterDao.getAlamNameList(netWork);
		//String bscListStr ="";
		String alarmArray="var alarmList = new Array(";
		String cn2="";
		for (SYS_PARAMETER alarm : alarmList) {
			alarmArray = alarmArray + cn2 +"\""+alarm.getValue()+"\"";
			cn2=",";
		}
		alarmArray = alarmArray+");";
		model.addAttribute("alarmList", alarmArray);
		
		//lay danh sach khu vuc
		List<SYS_PARAMETER> regionList = sysParameterDao.getRegionList();
		model.addAttribute("regionList", regionList);
				
		// Dem so luong theo muc do canh bao
		List<CountAlarmLog> countList = new ArrayList<CountAlarmLog>();
		int countA1=0;
		int countA2=0;
		int countA3=0;
		int countA4=0;
		int totalFinish=0;
		int totalNonFinish=0;
		int total=0;
		/*try {*/
			countList = rAlarmLogDAO.getCountForSeverity(sdateF,sdateT,edateF,edateT,bscid,cellid, vendor,district, alarmName, function, netWork,username,province,team,alarmType,cbalarmMappingName,statusFinish,statusView,String.valueOf(duarationInt),region,unAlarmMappingName);
			if (countList.size()>0) {
				countA1 = countList.get(0).getA1();		
				countA2 =  countList.get(0).getA2();	
				countA3 = countList.get(0).getA3();	
				countA4 = countList.get(0).getA4();	
				totalFinish  = countList.get(0).getTotalFinish();	
				totalNonFinish= countList.get(0).getTotalNonFinish();
				total = countList.get(0).getTotal();
				}
		/*	}
		catch (Exception exp) {
			
			}*/
		
		model.addAttribute("countA1",countA1 );
		model.addAttribute("countA2",countA2 );
		model.addAttribute("countA3",countA3 );
		model.addAttribute("countA4",countA4);
		model.addAttribute("totalFinish",totalFinish);
		model.addAttribute("totalNonFinish",totalNonFinish);
		model.addAttribute("total",total);
		
	//	System.out.println("Sdate1: "+sdateF+"--- Edate1:"+sdateT);	
		/*try
		{*/											
			int totalRow = rAlarmLogDAO.countRAlarmLog(sdateF,sdateT,edateF,edateT,bscid,cellid, vendor,district, alarmName, function, severityF,netWork,username,province,team,alarmType,cbalarmMappingName,statusFinish,null,statusView,String.valueOf(duarationInt),region,unAlarmMappingName);
			model.addAttribute("totalRows", totalRow);
		/*}
		catch (Exception exp) {
			
		}*/
		String nameTable="R_ALARM_LOG";
		if (!netWork.equals("2G")&&!netWork.equals("3G"))		
		{
			nameTable =nameTable+netWork;
		}
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid(nameTable);
		//lay du lieu column cua grid
		model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
		//Lay du lieu datafield cua grid
		model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
		//Lay duong link url de lay du lieu do vao grid
		model.addAttribute("url", "data.htm?sdateF="+sdateF+"&sdateT="+sdateT+"&edateF="+edateF+"&edateT="+edateT+
				"&bscid="+bscid+"&cellid="+cellid+
				"&vendor="+vendor+"&district="+district+
				"&alarmName="+alarmName+"&function="+function+
				"&severity="+severityF+"&netWork="+netWork+
				"&username="+username+"&province="+province+
				"&team="+team+"&alarmType="+alarmType+
				"&alarmMappingName="+cbalarmMappingName+
				"&statusFinish="+statusFinish+
				"&statusView="+statusView+
				"&duaration="+duarationInt+
				"&region="+region+
				"&unAlarmMappingName="+unAlarmMappingName
				); 
		//Lay du lieu cac cot an hien cua grid 
		List<CTableConfig> columnList = cTableConfigDAO.getColumnList(nameTable);
		model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
		
		List<CHighlightConfigs> highlightConfigListCol = cHighlightConfigsDAO.getByKeyAndKPI("R_ALARM_LOG","severity");
		model.addAttribute("highlight", HelpTableConfigs.highLightCol(highlightConfigListCol));
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "AlarmLog"+ function + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		/*- kiem tra neu trang thai cho phep bao am thanh bat
		- lay danh sach canh bao moi xuat hien thuoc loai canh bao am thanh. dem so luong canh bao moi
		- neu so luong cb moi >0 hien thị poup ra danh sach canh bao am thanh và chay mot ban nhac am bao
		- Cho phép tat bao am thanh. thi cap nhat trang thai cảnh bao da duoc bao am thanh. tat poup*/
		if (isSoundAlarm.equals("Y")){
			List<CTableConfig> configListAlert = cTableConfigDAO.getTableConfigsForGrid("R_ALARM_LOG_ALERT");
			//Lay du lieu cac cot an hien cua grid 
			List<CTableConfig> listSourceAlert = cTableConfigDAO.getColumnList("R_ALARM_LOG_ALERT");
			//url
			String url = "dataAlarmAlert.htm?sdateF="+sdateF+"&sdateT="+sdateT+"&edateF="+edateF+"&edateT="+edateT+
				"&bscid="+bscid+"&cellid="+cellid+
				"&vendor="+vendor+"&district="+district+
				"&alarmName="+alarmName+"&function="+function+
				"&severity="+severityF+"&netWork="+netWork+
				"&username="+username+"&province="+province+
				"&team="+team+"&alarmType="+alarmType+
				"&alarmMappingName="+cbalarmMappingName+
				"&statusFinish="+statusFinish+
				"&statusView="+statusView+
				"&region="+region+
				"&unAlarmMappingName="+unAlarmMappingName;
			//Grid
			String gridAlert = HelpTableConfigs.getGridPagingUrl(configListAlert, "gridAlert", url, null, listSourceAlert, "menuAlert", null, "multiplerowsextended","Y");
			model.addAttribute("gridAlert", gridAlert);		
			List<Integer> idAlertStr =  rAlarmLogDAO.getIdAlarmAlert(sdateF,sdateT,edateF,edateT,bscid,cellid, vendor,district, alarmName, function, severity,netWork,username,province,team,alarmType,cbalarmMappingName,statusFinish,statusView,region,unAlarmMappingName);
			String idAlert ="";
			String con="";
			for (Integer id : idAlertStr) {
				idAlert = idAlert+con+id.toString();
				con=",";
			}
			model.addAttribute("countAlert", idAlertStr.size());	
			model.addAttribute("idAlert", idAlert);	
		}
		
		model.addAttribute("duaration", duarationInt);
		model.addAttribute("bscid", bscid);
		model.addAttribute("cellid", cellid);
		model.addAttribute("vendor", vendor);
		model.addAttribute("district", district);
		model.addAttribute("alarmName", alarmName);
		model.addAttribute("reload", reload);
		//model.addAttribute("timeLoad", timeLoad);
		model.addAttribute("reloadStr", reloadStr);
		model.addAttribute("function", function);
		model.addAttribute("severity", severity);
		model.addAttribute("netWork", netWork);
		model.addAttribute("province", province);
		model.addAttribute("team", team);
		model.addAttribute("alarmType", alarmType);
		model.addAttribute("cbalarmMappingName", cbalarmMappingName);
		model.addAttribute("statusView", statusView);
		model.addAttribute("edateF", edateF);
		model.addAttribute("edateT", edateT);
		model.addAttribute("region", region);
		model.addAttribute("isSoundAlarm", isSoundAlarm);
		model.addAttribute("unAlarmMappingName", unAlarmMappingName);
		model.addAttribute("severityF", severityF);
		return new ModelAndView("jspalarm/QLCanhBao/rAlarmLogList");
	}

	@RequestMapping("data")
	public @ResponseBody 
	Map<String, Object> processForm(@RequestParam(required = true) String sdateF,
			@RequestParam(required = true) String sdateT,
			@RequestParam(required = true) String edateF,
			@RequestParam(required = true) String edateT,
			@RequestParam(required = true) String bscid,
			@RequestParam(required = true) String cellid,
			@RequestParam(required = true) String vendor,
			@RequestParam(required = true) String district,
			@RequestParam(required = true) String alarmName,
			@RequestParam(required = true) String function,
			@RequestParam(required = true) String severity,
			@RequestParam(required = true) String netWork,
			@RequestParam(required = true) String username,
			@RequestParam(required = true) String province,
			@RequestParam(required = true) String team,
			@RequestParam(required = true) String alarmType,
			@RequestParam(required = true) String alarmMappingName,
			@RequestParam(required = true) String statusFinish,
			@RequestParam(required = true) String statusView,
			@RequestParam(required = true) String duaration,
			@RequestParam(required = true) String region,
			@RequestParam(required = false) String unAlarmMappingName,
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
		
		String nameTable="R_ALARM_LOG";
		if (!netWork.equals("2G")&&!netWork.equals("3G"))		
		{
			nameTable =nameTable+netWork;
		}
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
			System.out.println("netWork: "+netWork);	
			int totalRow =0;
			/*try
			{*/
				totalRow = rAlarmLogDAO.countRAlarmLog(sdateF,sdateT,edateF,edateT,bscid,cellid, vendor,district, alarmName, function, severity,netWork,username,province,team,alarmType,alarmMappingName,statusFinish,strWhere,statusView,duaration,region,unAlarmMappingName);
				//	model.addAttribute("totalRows", totalRow);
				alarmList = rAlarmLogDAO.getRAlarmLog(sdateF,sdateT,edateF,edateT,bscid,cellid, vendor,district, alarmName, function, severity, strWhere, startRecord,endRecord,sortfield,sortorder,netWork,username,province,team,alarmType,alarmMappingName,statusFinish,statusView,duaration,region,unAlarmMappingName);
			/*}
			catch(Exception exp)
			{
				alarmList=null;
			}*/
			data.put("totalRow", totalRow);
			data.put("Rows", alarmList);
		return data;
	 }
	@RequestMapping("dataAlarmAlert")
	public @ResponseBody 
	void  dataAlarmAlert(@RequestParam(required = true) String sdateF,
			@RequestParam(required = true) String sdateT,
			@RequestParam(required = true) String edateF,
			@RequestParam(required = true) String edateT,
			@RequestParam(required = true) String bscid,
			@RequestParam(required = true) String cellid,
			@RequestParam(required = true) String vendor,
			@RequestParam(required = true) String district,
			@RequestParam(required = true) String alarmName,
			@RequestParam(required = true) String function,
			@RequestParam(required = true) String severity,
			@RequestParam(required = true) String netWork,
			@RequestParam(required = true) String username,
			@RequestParam(required = true) String province,
			@RequestParam(required = true) String team,
			@RequestParam(required = true) String alarmType,
			@RequestParam(required = true) String alarmMappingName,
			@RequestParam(required = true) String statusFinish,
			@RequestParam(required = true) String statusView,
			@RequestParam(required = true) String region,
			@RequestParam(required = false) String unAlarmMappingName,
			HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
	
		List<RAlarmLog> alarmList = new ArrayList<RAlarmLog>();
		alarmList = rAlarmLogDAO.getAlarmAlert(sdateF,sdateT,edateF,edateT,bscid,cellid, vendor,district, alarmName, function, severity,netWork,username,province,team,alarmType,alarmMappingName,statusFinish,statusView,region,unAlarmMappingName);
		model.addAttribute("countAlert", alarmList.size());	
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(alarmList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
	 }

	
	//Xoa
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) String idList,
			@RequestParam (required = true) String netWork,
			@RequestParam (required = true) String function,
			HttpServletRequest request, Model model) {
		try {
			String[] idArray = idList.split(",");
			for (String id : idArray) {
				System.out.println(id);
				rAlarmLogDAO.deleteAlarmLog(Long.parseLong(id), function);
			}
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch (Exception e) {
			saveMessageKey(request, "messsage.confirm.deletefailure");
		}
		return "redirect:"+function+".htm?netWork="+netWork;
	}
	
	//cap nhat trang thai ket thuc huy nhan tin
	//@RequestMapping(value = "ack", method = RequestMethod.GET)
	@RequestMapping("ack")
	 	public @ResponseBody
	 		String onAck(@RequestParam (required = true) String idList,
			@RequestParam (required = true) String idEndList,
			@RequestParam (required = true) String netWork,
			@RequestParam (required = true) String functionA,
			@RequestParam (required = true) String inshift,
			@RequestParam (required = true) String etime,
			@RequestParam (required = true) String region,
			HttpServletRequest request, Model model) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		
		try {
			if (inshift.equals("Y"))
			{
				AlShift shift = getCaTrucHT(userLogin.getRegion());
				int shiftID = 1;
				if (shift!=null)
				{
					shiftID = shift.getShiftId();
				}
				//xac nhan nhung canh bao chua ket thuc
				String[] idArray = idList.split(",");
				for (String id : idArray) {
					System.out.println("id=......."+id);
					if (!id.equals("")){
						rAlarmLogDAO.ackAlarmLog(Long.parseLong(id),shiftID,functionA,username,"N",etime);
					}
				}
				// xac nhan nhung canh bao da ket thuc
				String[] idEndArray = idEndList.split(",");
				for (String id : idEndArray) {
					System.out.println("id=......."+id);
					if (!id.equals("")){
						rAlarmLogDAO.ackAlarmLog(Long.parseLong(id),shiftID, functionA,username,"Y",etime);
					}
				}
			}
			else
			{
				//xac nhan nhung canh bao chua ket thuc
				System.out.println("idList=......."+idList);
				String[] idArray = idList.split(",");
				for (String id : idArray) {
					System.out.println("id=......."+id);
					if (!id.equals("")){
						rAlarmLogDAO.ackAlarmLog(Long.parseLong(id),null, functionA,username,"N",etime);
					}
				}
				// xac nhan nhung canh bao da ket thuc
				String[] idEndArray = idEndList.split(",");
				for (String id : idEndArray) {
					System.out.println("id=......."+id);
					if (!id.equals("")){
						rAlarmLogDAO.ackAlarmLog(Long.parseLong(id),null, functionA,username,"Y",etime);
					}
				}
			}
		}
		catch (Exception e) {
			return "0";
		}
		return "1";
	}

	// Bo xac nhan 
	@RequestMapping(value = "unack", method = RequestMethod.GET)
	public String unack(@RequestParam (required = true) String idList,
			@RequestParam (required = true) String idEndList,
			@RequestParam (required = true) String netWork,
			@RequestParam (required = true) String function,
			HttpServletRequest request, Model model) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		try {
				String[] idArray = idList.split(",");
				for (String id : idArray) {
					System.out.println("id=......."+id);
					if (!id.equals("")){
						rAlarmLogDAO.unAckAlarmLog(Long.parseLong(id),function,username,"N");
					}
				}
				// xac nhan nhung canh bao da ket thuc
				String[] idEndArray = idEndList.split(",");
				for (String id : idEndArray) {
					System.out.println("id=......."+id);
					if (!id.equals("")){
						rAlarmLogDAO.unAckAlarmLog(Long.parseLong(id), function,username,"Y");
					}
				}
			
			}
		catch (Exception e) {
			saveMessageKey(request, "messsage.confirm.ackfailure");
		}
		return "redirect:"+function+".htm?netWork="+netWork;
	}
	//Them, sua
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String form(@RequestParam (required = true) String id,
			@RequestParam (required = true) String netWork,
			@RequestParam (required = true) String function,
			HttpServletRequest request, Model model) {
		List<SYS_PARAMETER> sysParemeterTitle = rAlarmLogDAO.titleForm(function,netWork,"ADD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleForm", sysParemeterTitle.get(0).getValue());
		}

		RAlarmLog rAlarmLog = new RAlarmLog();
		String ne="";
		String dict="";
		String alarmType="";
		if (id!=null&&!id.equals(""))
		{
			rAlarmLog = rAlarmLogDAO.selectByPrimaryKey(Long.parseLong(id),function);
			if (rAlarmLog!=null)
			{
				ne = rAlarmLog.getNe();
				dict = rAlarmLog.getDistrict();
				
				if (rAlarmLog.getAlarmType()!=null)
				{
					alarmType = rAlarmLog.getAlarmType();
				}
				model.addAttribute("sdate", rAlarmLog.getSdateStr());
				model.addAttribute("edate", rAlarmLog.getEdateStr());
				model.addAttribute("mdSdate", rAlarmLog.getMdSdateStr());
				model.addAttribute("bscid", ne);		
				model.addAttribute("site", rAlarmLog.getSite());		
				model.addAttribute("cellid", rAlarmLog.getCellid());		
				model.addAttribute("ackTime", rAlarmLog.getAckTimeStr());		
				model.addAttribute("vendor", rAlarmLog.getVendor());		
				model.addAttribute("alarmType", alarmType);		
				model.addAttribute("severity", rAlarmLog.getSeverity());		
				model.addAttribute("district", dict);		
				model.addAttribute("dept", rAlarmLog.getDept());		
				model.addAttribute("team", rAlarmLog.getGroups());		
				model.addAttribute("ackStatus", rAlarmLog.getAckStatus());		
				model.addAttribute("ackUser", rAlarmLog.getAckUser());		
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
		
		model.addAttribute("alarmLog", rAlarmLog);		
		model.addAttribute("netWork", netWork);	
		model.addAttribute("function", function);	
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("AL_HANDLING_LIBRARY");
		//lay du lieu column cua grid
		model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
		//Lay du lieu datafield cua grid
		model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
		//Lay duong link url de lay du lieu do vao grid
		model.addAttribute("url", "dataHandling.htm?alarmType="+alarmType); 
		//Lay du lieu cac cot an hien cua grid 
		List<CTableConfig> columnList = cTableConfigDAO.getColumnList("AL_HANDLING_LIBRARY");
		model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));	
				
		return "jspalarm/QLCanhBao/rAlarmLogEdit";
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@ModelAttribute("alarmLog") @Valid RAlarmLog alarmLog, BindingResult result,
			@RequestParam(required = false) String netWork,
			@RequestParam(required = false) String function,
			@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String sdate,
			@RequestParam(required = false) String edate,
			@RequestParam(required = false) String ackTime,
			@RequestParam(required = false) String mdSdate,
			@RequestParam(required = false) String statusView,
			@RequestParam(required = false) String ackStatus,
			@RequestParam(required = false) String ackUser,
			@RequestParam (required = false) String causebySys,
			ModelMap model, HttpServletRequest request) throws ParseException {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		
		model.addAttribute("alarmLog", alarmLog);	
		
		List<SYS_PARAMETER> sysParemeterTitle = rAlarmLogDAO.titleForm(function,netWork,"ADD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleForm", sysParemeterTitle.get(0).getValue());
		}
		
		model.addAttribute("ackStatus", ackStatus);		
		model.addAttribute("ackUser", ackUser);		
		model.addAttribute("causebySys", causebySys);	
		model.addAttribute("bscid", alarmLog.getNe());	
		model.addAttribute("sdate", alarmLog.getSdateStr());	
		model.addAttribute("edate", edate);	
		model.addAttribute("ackTime",ackTime);	

		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("AL_HANDLING_LIBRARY");
		//lay du lieu column cua grid
		model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
		//Lay du lieu datafield cua grid
		model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
		//Lay duong link url de lay du lieu do vao grid
		model.addAttribute("url", "dataHandling.htm?alarmType="+alarmLog.getAlarmType()); 
		//Lay du lieu cac cot an hien cua grid 
		List<CTableConfig> columnList = cTableConfigDAO.getColumnList("AL_HANDLING_LIBRARY");
		model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));	
		
		//Lay danh sach district theo user
		List<String> districtList = sysUserAreaDAO.getDistrictList(alarmLog.getProvince(),alarmLog.getGroups(),username);
		String districtArray="var districtList = new Array(";//Danh sach district cho cobobox
		String cn="";
		for (String dis : districtList) {
			districtArray = districtArray + cn +"\""+dis+"\"";
			cn=",";
		}
		districtArray = districtArray+");";
		model.addAttribute("districtList", districtArray);
		//Lay danh sach team theo user
		List<String> teamList = sysUserAreaDAO.getSubTeamList(alarmLog.getProvince(),username);
		String teamArray="var teamList = new Array(";//Danh sach district cho cobobox
		cn="";
		for (String dis : teamList) {
			teamArray = teamArray + cn +"\""+dis+"\"";
			cn=",";
		}
		teamArray = teamArray+");";
		model.addAttribute("teamList", teamArray);
		
		AlShift shift = getCaTrucHT(userLogin.getRegion());
		boolean checkCaTruc= false;
		int shiftID = 1;
		if (shift!=null)
		{
			String[] user = shift.getNhanCaVien().split(",");
			if (shift.getNhanUsername().equals(username)||userLogin.getIsRoleManager().equals("Y"))
			{
				checkCaTruc=true;
			}
			for (String item : user) {
				if (item.equals(username))
				{
					checkCaTruc=true;
					break;
				}
			}
			model.addAttribute("checkCaTruc", checkCaTruc);
			shiftID = shift.getShiftId();
		}
		else
		{
			checkCaTruc=true;
		}
		
		if (checkCaTruc==false)
		{
			saveMessageKey(request, "cautruc.KhongCoQuyen");
			model.addAttribute("netWork", netWork);	
			model.addAttribute("function", function);	
			
			 return "jspalarm/QLCanhBao/rAlarmLogEdit";
		}
		if (edate!=null  && !edate.equals("")  && DateTools.isValid("dd/MM/yyyy HH:mm:ss", edate+":00")==false)
		{
			model.addAttribute("erroredate", "*");
			saveMessageKey(request, "alarmExtend.errorField");
			model.addAttribute("netWork", netWork);	
			model.addAttribute("function", function);	
			
			return "jspalarm/QLCanhBao/rAlarmLogEdit";
		}
		if (mdSdate!=null  && !mdSdate.equals("")  && DateTools.isValid("dd/MM/yyyy HH:mm:ss", mdSdate+":00")==false)
		{
			model.addAttribute("errormdSdate", "*");
			saveMessageKey(request, "alarmExtend.errorField");
			model.addAttribute("netWork", netWork);	
			model.addAttribute("function", function);	
			
			return "jspalarm/QLCanhBao/rAlarmLogEdit";
		}
		if (ackTime!=null  && !ackTime.equals("")  && DateTools.isValid("dd/MM/yyyy HH:mm:ss", ackTime+":00")==false)
		{
			model.addAttribute("errorackTime", "*");
			saveMessageKey(request, "alarmExtend.errorField");
			model.addAttribute("netWork", netWork);	
			model.addAttribute("function", function);	
			
			return "jspalarm/QLCanhBao/rAlarmLogEdit";
		}
		if (ackUser!=null  && ackUser.length()>=50)
		{
			model.addAttribute("errorackUser", "*");
			saveMessageKey(request, "message.error.overlenght");
			model.addAttribute("netWork", netWork);	
			model.addAttribute("function", function);	
			
			return "jspalarm/QLCanhBao/rAlarmLogEdit";
		}
		if (alarmLog.getCauseby()!=null  && alarmLog.getCauseby().length()>=250)
		{
			model.addAttribute("errorcauseby", "*");
			saveMessageKey(request, "message.error.overlenght");
			model.addAttribute("netWork", netWork);	
			model.addAttribute("function", function);	
			
			return "jspalarm/QLCanhBao/rAlarmLogEdit";
		}
		if (alarmLog.getActionProcess()!=null  && alarmLog.getActionProcess().length()>=350)
		{
			model.addAttribute("erroractionProcess", "*");
			saveMessageKey(request, "message.error.overlenght");
			model.addAttribute("netWork", netWork);	
			model.addAttribute("function", function);	
			
			return "jspalarm/QLCanhBao/rAlarmLogEdit";
		}
		if (alarmLog.getDescription()!=null  && alarmLog.getDescription().length()>=350)
		{
			model.addAttribute("errordescription", "*");
			saveMessageKey(request, "message.error.overlenght");
			model.addAttribute("netWork", netWork);	
			model.addAttribute("function", function);	
			
			return "jspalarm/QLCanhBao/rAlarmLogEdit";
		}
		if (result.hasErrors() ) {
			saveMessageKey(request, "alarmExtend.errorField");
			model.addAttribute("netWork", netWork);	
			model.addAttribute("function", function);	
			
			 return "jspalarm/QLCanhBao/rAlarmLogEdit";
		}
		else
		{
			
			if (edate!=null && !edate.equals(""))
			{
				alarmLog.setEdate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(edate+":00"));
			}
			if (mdSdate!=null && !mdSdate.equals(""))
			{
				alarmLog.setMdSdate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(mdSdate+":00"));
			}
			if (ackTime!=null && !ackTime.equals(""))
			{
				alarmLog.setAckTime((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(ackTime+":00"));
			}
			alarmLog.setAckStatus(ackStatus);
			alarmLog.setAckUser(ackUser);
			alarmLog.setCausebySys(causebySys);
			alarmLog.setNetwork(netWork);
			alarmLog.setStatusView(statusView);
			alarmLog.setShiftId(shiftID);
			if (edate!=null && !edate.equals("") && alarmLog.getSdate().getTime()>= alarmLog.getEdate().getTime())
			{
				saveMessageKey(request, "cautruc.sosanhNgay");
				model.addAttribute("netWork", netWork);	
				model.addAttribute("function", function);	
				
				return "jspalarm/QLCanhBao/rAlarmLogEdit";
			}
			if (alarmLog.getId()==null)
			{
				alarmLog.setCreateDate(new Date());
				alarmLog.setCreatedBy(username);
				rAlarmLogDAO.insert(alarmLog,function);
				saveMessageKey(request, "alarmExtend.insertSucceFull");
			}
			else
			{
				//sua chua
				alarmLog.setModifiedBy(username);
				alarmLog.setModifyDate(new Date());
				rAlarmLogDAO.update(alarmLog,function);
				saveMessageKey(request, "alarmExtend.updateSuccelfull");	
			}
		}
		model.addAttribute("severity", "");
		model.addAttribute("alarmName", "");
		model.addAttribute("bscid","");	
		model.addAttribute("sdate","");	
		model.addAttribute("edate","");	
		model.addAttribute("districtList", "");    
		
		return "redirect:"+function+".htm?netWork="+netWork;
	}
	
	@RequestMapping("insertHanding")
	public @ResponseBody
	String insertHanding(@RequestBody AlHandingLibrary handling) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		handling.setCreateDate(new Date());
		handling.setCreatedBy(username);
		
			alHandingLibraryDAO.insertSelective(handling);
			return "1";
	}
	
	@RequestMapping("dataHandling")
	public @ResponseBody 
	List<AlHandingLibrary> dataHandling(@RequestParam(required = false) String alarmType,HttpServletRequest request, HttpServletResponse response,Model model) {
	
		List<AlHandingLibrary> handingList = new ArrayList<AlHandingLibrary>();
		handingList = alHandingLibraryDAO.getHandingListAll(null, alarmType, null, null);
		return handingList;
	 }
	
	
	@RequestMapping("insertAlarmWork")
	public @ResponseBody
	String insertAlarmWork(@RequestBody AlAlarmWorks alarmWork) throws ParseException {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		AlShift shift = getCaTrucHT(userLogin.getRegion());
		int shiftID = 1;
		if (shift!=null)
		{
			shiftID = shift.getShiftId();
		}
		System.out.println("getStimeStr: "+alarmWork.getStimeStr());
		System.out.println("getEtimeStr: "+alarmWork.getEtimeStr());
		alarmWork.setStime((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(alarmWork.getStimeStr()));
		
		if (alarmWork.getAlarm()==null &&alarmWork.getAlarm().equals(""))
		{
			return "0";
		}
		if (alarmWork.getEtimeStr()!=null &&!alarmWork.getEtimeStr().equals("")&& DateTools.isValid("dd/MM/yyyy HH:mm:ss", alarmWork.getEtimeStr())==false)
		{
			return "0";
		}
		if (alarmWork.getContactDateStr()!=null &&!alarmWork.getContactDateStr().equals(""))
		{
			if ( DateTools.isValid("dd/MM/yyyy HH:mm:ss", alarmWork.getContactDateStr())==false)
			{
				return "0";
			}
			else
			{
				alarmWork.setContactTime((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(alarmWork.getContactDateStr()));
			}
		}
		
		if (alarmWork.getEtimeStr()!=null &&!alarmWork.getEtimeStr().equals(""))
		{
			alarmWork.setEtime((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(alarmWork.getEtimeStr()));
			if (alarmWork.getStime().getTime()> alarmWork.getEtime().getTime())
			{
				return "0";
			}
		}
		if (alarmWork.getMdSdateStr()!=null &&!alarmWork.getMdSdateStr().equals(""))
		{
			alarmWork.setMdSdate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(alarmWork.getMdSdateStr()));
		}
		alarmWork.setShiftId(shiftID);
		alarmWork.setCreateDate(new Date());
		alarmWork.setCreatedBy(username);
		alarmWork.setRegion(userLogin.getRegion());
		//alAlarmWorksDAO.insertSelective(alarmWork);
		alAlarmWorksDAO.insertAlarmWork(alarmWork);
		
		
		return "1";
	}
	
	@RequestMapping("updateStatusAlerted")
	public @ResponseBody
	String updateStatusAlerted(@RequestParam(required = false) String idList,@RequestParam(required = false) String type) throws ParseException {
		System.out.println("idList: "+idList);
		rAlarmLogDAO.updateStatusAlerted(idList,type);
		return "1";
	}
}
