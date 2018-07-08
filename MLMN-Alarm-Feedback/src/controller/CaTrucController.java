package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;

import c2.security.authentication.LDAP;

import com.google.gson.Gson;

import vo.AlAlarmWorks;
import vo.AlMonitorTemperatureSite;
import vo.AlShift;
import vo.AlWorkCalendar;
import vo.CHighlightConfigs;
import vo.CTableConfig;
import vo.RAlarmLog;
import vo.SYS_PARAMETER;
import vo.SmsContents;
import vo.SysDefineSmsEmail;
import vo.SysMailParameter;
import vo.SysUsers;
import vo.SysUsersCogencyLevel;
import vo.WWorkingAtShift;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;
import vo.alarm.utils.Helper;

import dao.A_ALERT_USERSDAO;
import dao.AlAlarmWorksDAO;
import dao.AlMonitorTemperatureSiteDAO;
import dao.AlShiftDAO;
import dao.AlWorkCalendarDAO;
import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.MDepartmentDAO;
import dao.RAlarmLogDAO;
import dao.SYS_PARAMETERDAO;
import dao.SmsContentsDAO;
import dao.SysDefineSmsEmailDAO;
import dao.SysUsersCogencyLevelDAO;
import dao.SysUsersDAO;
import dao.VAlHBscDAO;
import dao.VAlHCellDAO;
import dao.VAlHRncDAO;
import dao.VAlWarningSystemDAO;
import dao.WWorkingAtShiftDAO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Created by: AnhCTV
 * Created date: ..
 * Update by: ThangPX. Date:  24.07.2013
 * Content update: Form 2g,3g
*/
@Controller
@RequestMapping("/caTruc/*")
public class CaTrucController extends BaseController {

	@Autowired
	private AlAlarmWorksDAO alAlarmWorksDAO;

	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;

	@Autowired
	private MDepartmentDAO mDepartmentDAO;

	@Autowired
	private A_ALERT_USERSDAO alertUsersDao;

	@Autowired
	private AlShiftDAO alShiftDAO;

	@Autowired
	private SysUsersDAO sysUsersDao;

	@Autowired
	private VAlWarningSystemDAO vAlWarningSystemDAO;

	@Autowired
	private RAlarmLogDAO rAlarmLogDAO;
	
	@Autowired
	private VAlHBscDAO vAlHBscDAO;

	@Autowired
	private VAlHCellDAO vAlHCellDAO;

	@Autowired
	private VAlHRncDAO vAlHRncDAO;
	
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	
	@Autowired
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
	@Autowired
	private SmsContentsDAO smsContentsDAO;
	
	@Autowired
	private SysUsersCogencyLevelDAO sysUsersCogencyLevelDAO;
	
	@Autowired
	private AlMonitorTemperatureSiteDAO alMonitorTemperatureSiteDAO;
	
	@Autowired
	private AlWorkCalendarDAO alWorkCalendarDAO;
	
	@Autowired
	private SysDefineSmsEmailDAO  sysDefineSmsEmailDAO ;
	
	@Autowired
	private WWorkingAtShiftDAO wWorkingAtShiftDAO;
	
	public static String dotEmail = "@mobifone.vn";
	
	// Su dung ham ma hoa Md5 de ma hoa mat khau
	private Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat df_full2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	//Chỉnh sửa thành tab ngày 15/08/2014. AnhCTV
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String warningTp,@RequestParam(required = false) String region,ModelMap model, HttpServletRequest request) {
		
		List<SYS_PARAMETER> sysParemeterTitle = alShiftDAO.titleCaTrucList(null,null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		model.addAttribute("isRoleManager", userLogin.getIsRoleManager());
		
		List<SYS_PARAMETER> caList = new ArrayList<SYS_PARAMETER>();
		caList = sysParameterDao.getSysParameterByCode("CA_TRUC_VALUE");
		List<SYS_PARAMETER> caListName = new ArrayList<SYS_PARAMETER>();
		caListName = sysParameterDao.getSysParameterByCode("CA_TRUC_NAME");

		List<SysUsers> nvList = new ArrayList<SysUsers>();
		nvList = sysUsersDao.selectAllSysUsers();

		// Kiểm tra ca trực
		String caT = "";
		String startTruc = "";
		String endTruc = "";
		String ngayT = "";

		String sang = caList.get(0).getValue();
		String chieu = caList.get(1).getValue();
		String toi = caList.get(2).getValue();

		Date currentTime = new Date();
		int hour = currentTime.getHours();
		ngayT = df_full.format(currentTime);
		if (hour >= Integer.parseInt(sang) && hour < Integer.parseInt(chieu)) {
			caT = "Sáng";
			startTruc = ngayT + " " + sang + ":00:00";
			endTruc = ngayT + " " + chieu + ":00:00";
		}
		if (hour >= Integer.parseInt(chieu) && hour < Integer.parseInt(toi)) {
			caT = "Chiều";
			startTruc = ngayT + " " + chieu + ":00:00";
			endTruc = ngayT + " " + toi + ":00:00";
		}
		if (hour >= Integer.parseInt(toi) || hour < Integer.parseInt(sang)) {
			caT = "Tối";
			startTruc = ngayT + " " + toi + ":00:00";
			endTruc = df_full.format(DateTools.addDate(new Date(), 1, 0, 0, 0))
					+ " " + sang + ":00:00";
		}
		if (warningTp==null||warningTp.equals("")){
			warningTp="WOTK_SHIFT";
		}
		// Lay idshift
	if (region!=null && !region.equals("")&& !region.equals(",")){
		
		AlShift shift = alShiftDAO.getCaTrucGanNhat(region);
		//model.addAttribute("globalCaTruc", shift);
		AlShift newShift = new AlShift();
		Integer shiftid = null;
		boolean checkCaTruc = false;
		boolean checkTruongCa = false;
		if (shift != null && shift.getShiftId()!=null) {
			model.addAttribute("first", false);
			shiftid = shift.getShiftId();
			caT = shift.getNhanCaTruc();
			ngayT = shift.getNhanDateStr();
			newShift.setGiaoCaTruc(shift.getNhanCaStr());
			newShift.setGiaoCaVien(shift.getNhanCaVien());
			newShift.setGiaoNgayTruc(shift.getNhanNgayTruc());
			newShift.setGiaoUsername(shift.getNhanUsername());
			model.addAttribute("ca", shift.getNhanCaTruc());
			model.addAttribute("ngayTruc", shift.getNhanDateStr());
			model.addAttribute("truongCa", shift.getNhanUsername());
			model.addAttribute("caVien", shift.getNhanCaVien());
			model.addAttribute("giaoNgayTruc", shift.getNhanDateStr());
			model.addAttribute("giaoCaTruc", shift.getNhanCaTruc());
			model.addAttribute("giaoCaTrucStr", shift.getNhanCaTruc());
			model.addAttribute("giaoUsername", shift.getNhanUsername());
			String nhanCatruc= caTrucKeTiep(shift.getNhanCaTruc());
			String nhanNgayTruc = "";
			String nhanCaTruong ="";
			String nhanCaVien="";
			String nhanUCTT="";
			if (shift.getNhanCaTruc()!=null && shift.getNhanCaTruc().equalsIgnoreCase("TỐI")) {
				nhanNgayTruc = df_full.format(DateTools.addDate(shift.getNhanNgayTruc(), 1, 0, 0, 0));
			} else
			{
				nhanNgayTruc = shift.getNhanDateStr();
			}
			
			
			List<AlWorkCalendar> nextShift = alWorkCalendarDAO.getShift(nhanNgayTruc,nhanCatruc,region);
			if (nextShift.size()>0)
			{
				String con1="";
				String con2="";
				String con3="";
				for (AlWorkCalendar alWorkCalendar : nextShift) {
					String funtion=alWorkCalendar.getFunction();
					
					if (funtion!=null && funtion.toLowerCase().contains("trưởng ca")&&!nhanCaTruong.contains(alWorkCalendar.getEmail()))
					{
						nhanCaTruong+= con1+ alWorkCalendar.getEmail();
						con1=",";
					}
					/*else if (funtion.contains("ca viên")&&!nhanCaVien.contains(alWorkCalendar.getEmail()))*/
					else if (!nhanCaVien.contains(alWorkCalendar.getEmail()))
					{
						nhanCaVien+= con2+ alWorkCalendar.getEmail();
						con2=",";
					}
					else
					{
						if(!nhanUCTT.contains(alWorkCalendar.getEmail()))
						{
							nhanUCTT+= con3+ alWorkCalendar.getEmail();
							con3=",";
						}
					}
				}
			}
			newShift.setNhanUsername(nhanCaTruong);
			newShift.setNhanCaVien(nhanCaVien);
			model.addAttribute("nhanNgayTruc", nhanNgayTruc);
			model.addAttribute("nhanCaTruc",nhanCatruc);
			model.addAttribute("nhanUCTT",nhanUCTT);

			String[] user = shift.getNhanCaVien().split(",");
			if (shift.getNhanUsername().equals(username)) {
				checkCaTruc = true;
			}
			for (String item : user) {
				if (item.equals(username)) {
					checkCaTruc = true;
					break;
				}
			}
			model.addAttribute("checkCaTruc", checkCaTruc);
			if (username.equalsIgnoreCase(shift.getNhanUsername()))
			{
				checkTruongCa = true;
			}
			model.addAttribute("checkTruongCa", checkTruongCa);
		}
		else {
			model.addAttribute("first", true);
			model.addAttribute("checkCaTruc", true);
			model.addAttribute("ca", caT);
			model.addAttribute("ngayTruc", ngayT);
			model.addAttribute("giaoCaTruc", caT);
			model.addAttribute("giaoCaTrucStr", caT);
			model.addAttribute("giaoNgayTruc", ngayT);
			model.addAttribute("nhanCaTruc", caTrucKeTiep(caT));
			
			if (caT.equalsIgnoreCase("TỐI")) {
				try {
					model.addAttribute(
							"nhanNgayTruc",
							df_full.format(DateTools.addDate(
									df_full.parse(ngayT), 1, 0, 0, 0)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			} else
				model.addAttribute("nhanNgayTruc", ngayT);
			shiftid = 0;
		}
		// in ra man hinh
		model.addAttribute("catruc", newShift);
		model.addAttribute("truongCaList", nvList);
		model.addAttribute("caVienList", nvList);
		model.addAttribute("caList", caListName);

		model.addAttribute("passwordGiao", "");
		model.addAttribute("passwordNhan", "");
		// model.addAttribute("nhanNgayTruc", ngayT);
	}
		List<SysDefineSmsEmail> alarmList = new ArrayList<SysDefineSmsEmail>();
 		alarmList = sysDefineSmsEmailDAO.getListAll("SMS", null, "S");
 		String groupArray="var groupList = new Array(";
		String cn="";
		for (SysDefineSmsEmail sys : alarmList) {
			groupArray = groupArray + cn +"\""+sys.getGroupName()+"\"";
			cn=",";
		}
		groupArray = groupArray+");";
		model.addAttribute("groupList", groupArray);
		
		if (warningTp==null||warningTp.equals(""))
		{
			warningTp="WOTK_SHIFT";
		}
		else
		{
			warningTp=warningTp.trim();
		}
		String tableName="";
		String fileName="";
		
		if (warningTp.equalsIgnoreCase("2G")||warningTp.equalsIgnoreCase("3G")||warningTp.equalsIgnoreCase("SU_CO_LON")||warningTp.equalsIgnoreCase("SU_CO_KHAC")||warningTp.equalsIgnoreCase("LOI_TRUYEN_DAN"))
		{
			tableName = "ALARM_WORK_2G";
			fileName="CanhBao"+warningTp;
		}
		else if (warningTp.equalsIgnoreCase("PS_CORE")||warningTp.equalsIgnoreCase("CS_CORE")||warningTp.equalsIgnoreCase("IPBB"))
		{
			tableName = "ALARM_WORK_WARNING";
			fileName="CanhBao"+warningTp;
		}
		else if (warningTp.equalsIgnoreCase("ALARM_EXTEND"))
		{
			tableName = "ALARM_WORK_HCMR";
			fileName="HieuChinhMoRong";
		}
		else if (warningTp.equalsIgnoreCase("TEMPERATURE_SITE"))
		{
			tableName = "AL_MONITOR_TEMPERATURE";
			fileName="GSNhietDoSite";
		}
		else if (warningTp.equalsIgnoreCase("WOTK_SHIFT"))
		{
			tableName = "W_WORKING_AT_SHIFT_CVCD";
			fileName="CongViecCoDinh";
		}
		
		
		if (!warningTp.equalsIgnoreCase("BAN_GIAO_CA"))
		{
		
			// Lay ten file export
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
			String dateNow = formatter.format(currentDate.getTime());
			String exportFileName= fileName+ dateNow;
			model.addAttribute("exportFileName", exportFileName);
			
			List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid(tableName);
			//lay du lieu column cua grid
			model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
			//Lay du lieu datafield cua grid
			model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
			//Lay duong link url de lay du lieu do vao grid
			model.addAttribute("url", "dataListFilter.htm?warningtype="+warningTp+"&shiftid="+caT+"&ngayTKTo="+ngayT+"&ngayTK="+ngayT+"&type=L"+"&tableName="+tableName+"&region="+region); 
			//Lay du lieu cac cot an hien cua grid 
			List<CTableConfig> columnList = cTableConfigDAO.getColumnList(tableName);
			model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
			/*List<CHighlightConfigs> highlighList = cHighlightConfigsDAO.getByKeyAndKPI("ALARM_WORK", "ETIME");
			String hightlight=HelpTableConfigs.highLightRow(highlighList,"grid","colorrowrenderer");*/
			
			List<CHighlightConfigs> highlighList = cHighlightConfigsDAO.getByKeyAndKPI("ALARM_WORK", "conditionPaint");
			String hightlight=HelpTableConfigs.highLightOption(highlighList,"grid","colorrowrenderer2");
			
			model.addAttribute("highlight", hightlight);
			model.addAttribute("caTK", caT);
			model.addAttribute("ngayTKTo",ngayT);
			model.addAttribute("ngayTK", ngayT);
			model.addAttribute("type", "L");
			
			if (warningTp.equalsIgnoreCase("WOTK_SHIFT"))
			{
				String url2 = "dataList.htm?warningtype=cvcl&shiftid="+caT+"&ngayTKTo="+ngayT+"&ngayTK="+ngayT+"&type=L"+"&region="+region;
				String grid2 = HelpTableConfigs.getGridPagingUrl(configList, "grid2", url2, "jqxlistbox2", columnList, "menujqx2", null, "multiplerowsextended","Y");
				String exportFileName2 = "CongViecCanLam"+dateNow;
				model.addAttribute("grid2", grid2);
				model.addAttribute("exportFileName2", exportFileName2);
			}
		}
		model.addAttribute("warningTp", warningTp);
		model.addAttribute("region",region);
		
		return "jspalarm/catruc/CaTrucList";
	}
	@RequestMapping("/dataList")
	public void doGet(@RequestParam(required = true) String warningtype,
			@RequestParam(required = true) String shiftid,
			@RequestParam(required = true) String ngayTKTo,
			@RequestParam(required = true) String ngayTK,
			@RequestParam(required = true) String region,
			@RequestParam(required = true) String type,
			
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		if (warningtype.equalsIgnoreCase("TEMPERATURE_SITE"))
		{
			List<AlMonitorTemperatureSite> monitorSiteList = new ArrayList<AlMonitorTemperatureSite>();
			monitorSiteList = alMonitorTemperatureSiteDAO.getMonitorTemperatureSiteShift(shiftid,ngayTKTo,ngayTK,type,region);
			Gson gs = new Gson();
			String jsonCartList = gs.toJson(monitorSiteList);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.println(jsonCartList);
			out.close();
		}
		else if (warningtype.equalsIgnoreCase("NOI_DUNG"))
		{
			List<AlShift> larmWorkList = alShiftDAO.getNoiDungAtShif(shiftid,ngayTKTo,ngayTK,type,region);
			Gson gs = new Gson();
			String jsonCartList = gs.toJson(larmWorkList);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.println(jsonCartList);
			out.close();
		}
		else if (warningtype.equalsIgnoreCase("WOTK_SHIFT"))// conf viec co dinh trong ca
		{
			List<WWorkingAtShift> dataList = new ArrayList<WWorkingAtShift>();
			dataList = wWorkingAtShiftDAO.getListFilter("cvcd",ngayTK,ngayTKTo,null,null,shiftid,region);
			Gson gs = new Gson();
			String jsonCartList = gs.toJson(dataList);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.println(jsonCartList);
			out.close();
		}
		else if (warningtype.equalsIgnoreCase("cvcl"))
		{
			List<WWorkingAtShift> dataList = new ArrayList<WWorkingAtShift>();
			dataList = wWorkingAtShiftDAO.getListFilter(null,ngayTK,ngayTKTo,null,null,shiftid,region);
			Gson gs = new Gson();
			String jsonCartList = gs.toJson(dataList);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.println(jsonCartList);
			out.close();
		}
		else
		{
			List<AlAlarmWorks> larmWorkList = alAlarmWorksDAO.getWarningListAtShif(warningtype, shiftid,ngayTKTo,ngayTK,type,region);
			Gson gs = new Gson();
			String jsonCartList = gs.toJson(larmWorkList);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.println(jsonCartList);
			out.close();
		}
		
	}
	
	
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String submit(@ModelAttribute("catruc") @Valid AlShift catruc,
			BindingResult result, @RequestParam(required = false) String ca,
			@RequestParam(required = false) String truongCa,
			@RequestParam(required = false) String ngayTruc,
			@RequestParam(required = false) String caVien,
			@RequestParam(required = false) String giaoNgayTruc,
			@RequestParam(required = false) String nhanCaTruc,
			@RequestParam(required = false) String nhanNgayTruc,
			@RequestParam(required = false) String passwordGiao,
			@RequestParam(required = false) String passwordNhan,
			@RequestParam(required = false) String noiDung,
			@RequestParam(required = true) 	String warningTp,
			@RequestParam(required = false) String region,
			Model model, HttpServletRequest request) throws ParseException {
	
		String username = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		String maPhong=userLogin.getMaPhong();
		// A_ALERT_USERS user = alertUsersDao.getUserByUsername(username);
		List<SYS_PARAMETER> sysParemeterTitle = alShiftDAO.titleCaTrucList(null,null);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		
		List<SysDefineSmsEmail> alarmList = new ArrayList<SysDefineSmsEmail>();
 		alarmList = sysDefineSmsEmailDAO.getListAll("SMS", null, "S");
 		String groupArray="var groupList = new Array(";
		String cn="";
		for (SysDefineSmsEmail sys : alarmList) {
			groupArray = groupArray + cn +"\""+sys.getGroupName()+"\"";
			cn=",";
		}
		groupArray = groupArray+");";
		model.addAttribute("groupList", groupArray);
		
		List<SYS_PARAMETER> caList = new ArrayList<SYS_PARAMETER>();
		caList = sysParameterDao.getSysParameterByCode("CA_TRUC_VALUE");
		
		List<SYS_PARAMETER> caListName = new ArrayList<SYS_PARAMETER>();
		caListName = sysParameterDao.getSysParameterByCode("CA_TRUC_NAME");
		
		List<SysUsers> nvList = new ArrayList<SysUsers>();
		nvList = sysUsersDao.selectAllSysUsers();
		// Kiểm tra ca trực
		String caT = "";
		String startTruc = "";
		String endTruc = "";
		String ngayT = "";
		String xacnhanTC = "";
		String sang = caList.get(0).getValue();
		String chieu = caList.get(1).getValue();
		String toi = caList.get(2).getValue();

		Date currentTime = new Date();
		int hour = currentTime.getHours();
		ngayT = df_full.format(currentTime);

		if (hour >= Integer.parseInt(sang) && hour < Integer.parseInt(chieu)) {
			caT = "Sáng";
			startTruc = ngayT + " " + sang + ":00:00";
			endTruc = ngayT + " " + chieu + ":00:00";
		}
		if (hour >= Integer.parseInt(chieu) && hour < Integer.parseInt(toi)) {
			caT = "Chiều";
			startTruc = ngayT + " " + chieu + ":00:00";
			endTruc = ngayT + " " + toi + ":00:00";
		}
		if (hour >= Integer.parseInt(toi) || hour < Integer.parseInt(sang)) {
			caT = "Tối";
			startTruc = ngayT + " " + toi + ":00:00";
			endTruc = df_full.format(DateTools.addDate(new Date(), 1, 0, 0, 0))
					+ " " + sang + ":00:00";
		}

		// Lay idshift
		AlShift shift = alShiftDAO.getCaTrucGanNhat(region);
	//	AlShift shift = new AlShift();
		Integer shiftid = null;
		boolean checkCaTruc = false;
		boolean checkTruongCa = false;
		if (shift != null && shift.getShiftId()!=null ) {
			model.addAttribute("first", false);
			caT=shift.getNhanCaTruc();
			ngayT= shift.getNhanDateStr();
			model.addAttribute("truongCa", shift.getNhanUsername());
			model.addAttribute("caVien", shift.getNhanCaVien());
			model.addAttribute("giaoCaTrucStr", shift.getNhanCaTruc());
			String[] user = shift.getNhanCaVien().split(",");
			if (shift.getNhanUsername().equals(username) || userLogin.getIsRoleManager().equals("Y")) {
				checkCaTruc = true;
			}
			for (String item : user) {
				if (item.equals(username)) {
					checkCaTruc = true;
					break;
				}
			}
			model.addAttribute("checkCaTruc", checkCaTruc);
			shiftid = shift.getShiftId();
			if (username.equalsIgnoreCase(shift.getNhanUsername()))
			{
				checkTruongCa = true;
			}
			model.addAttribute("checkTruongCa", checkTruongCa);
		} 
		else {
			model.addAttribute("first", true);
			model.addAttribute("checkCaTruc", true);
		}
		model.addAttribute("ca",caT);
		model.addAttribute("ngayTruc", ngayT);
		model.addAttribute("truongCaList", nvList);
		model.addAttribute("caVienList", nvList);
		model.addAttribute("caList", caListName);
		
		model.addAttribute("giaoNgayTruc", giaoNgayTruc);
		model.addAttribute("nhanCaTruc", nhanCaTruc);
		model.addAttribute("catruc", catruc);
		model.addAttribute("giaoCaTruc", catruc.getGiaoCaTruc());
		model.addAttribute("nhanNgayTruc", nhanNgayTruc);
		model.addAttribute("nhanUsername", catruc.getNhanUsername());
		model.addAttribute("giaoUsername", catruc.getGiaoUsername());
		model.addAttribute("warningTp", warningTp);
		model.addAttribute("noiDung", noiDung);
		model.addAttribute("region", region);
		
			// Xu ly chuyen giao ca
			// Chuyển giao ca
		//if (catruc.getGiaoUsername().equals(username)||userLogin.getIsRoleManager().equals("Y")||userLogin.getIsRoleSystem().equals("Y")||(userLogin.getModule()!=null && userLogin.getModule().contains("DIARY_NOC"))){
		if (catruc.getGiaoUsername().equals(username)||userLogin.getIsRoleManager().equals("Y")||userLogin.getIsRoleSystem().equals("Y")){
				
			boolean ktUser = true;
			List<SysUsers> userTN = sysUsersDao.checkUser(catruc.getNhanUsername());
			if (userTN.size() == 0)
			{
				saveMessageKey(request, "catruc.insertFail");
				model.addAttribute("errorBanGiao", "Ca trưởng nhận không hợp lệ");
				model.addAttribute("xacnhanTC", "bottom");
				
				return "jspalarm/catruc/CaTrucList";
			}
			
			List<SysUsers> userTG = sysUsersDao.checkUser(catruc.getGiaoUsername());
			if (userTG.size() == 0)
			{
				saveMessageKey(request, "catruc.insertFail");
				model.addAttribute("errorBanGiao", "Ca trưởng giao không hợp lệ");
				model.addAttribute("xacnhanTC", "bottom");
				
				return "jspalarm/catruc/CaTrucList";
			}
			
			if (catruc.getNhanCaVien()!=null&&!catruc.getNhanCaVien().equals(""))
			{
				String nhanCaVien = catruc.getNhanCaVien().replace(" ", ",");
				String nhanCV="";
				String kn="";
				String[] userCaVienNhan = nhanCaVien.split(",");
				if (userCaVienNhan != null && userCaVienNhan.length > 0) {
					for (int i = 0; i < userCaVienNhan.length; i++) {
						//Kiem tra tung ca vien co hop le ko
						if (!userCaVienNhan[i].equals(""))
						{
							String userN= userCaVienNhan[i].trim();
							List<SysUsers> user = sysUsersDao.checkUser(userN);
							if (user.size() == 0)
							{
								ktUser=false;
							}
							nhanCV+=kn+userN;
							kn=",";
						}
							
					}
					nhanCaVien=nhanCV;
				}
				
			}
			else
			{
				ktUser=false;
			}
			if (ktUser==false)
			{
				saveMessageKey(request, "catruc.insertFail");
				model.addAttribute("errorBanGiao", "Ca viên nhận không hợp lệ");
				model.addAttribute("xacnhanTC", "bottom");
				
				return "jspalarm/catruc/CaTrucList";
			}
			
			if (catruc.getGiaoCaTruc() == null || (catruc.getGiaoCaTruc() != null && catruc.getGiaoCaTruc().equals(""))) {
				saveMessageKey(request, "catruc.insertFail");
				model.addAttribute("errorStartTime", "*");
				model.addAttribute("errorBanGiao", "Ca trực giao không hợp lệ");
				model.addAttribute("xacnhanTC", "bottom");
				
				return "jspalarm/catruc/CaTrucList";
			}
			if (giaoNgayTruc == null || (giaoNgayTruc!=null && DateTools.isValid("dd/MM/yyyy HH:mm:ss", giaoNgayTruc+" 00:00:00")==false)||(giaoNgayTruc != null && giaoNgayTruc.equals("")))
			{
				saveMessageKey(request, "catruc.insertFail");
				model.addAttribute("errorStartTime", "*");
				model.addAttribute("errorBanGiao", "Ngày giao ca trực không đúng định dạng");
				model.addAttribute("xacnhanTC", "bottom");
				
				return "jspalarm/catruc/CaTrucList";
			}
			if (nhanNgayTruc == null|| (nhanNgayTruc!=null && DateTools.isValid("dd/MM/yyyy HH:mm:ss", nhanNgayTruc+" 00:00:00")==false)|| (nhanNgayTruc != null && nhanNgayTruc.equals("")))
			{
				model.addAttribute("errorEndTime", "*");
				model.addAttribute("errorBanGiao", "Ngày nhận ca trực không đúng định dạng");
				saveMessageKey(request, "alarmExtend.errorField");
				
				return "jspalarm/catruc/CaTrucList";
			}
			if (nhanCaTruc == null || (nhanCaTruc != null && nhanCaTruc.equals(""))) {
	
				saveMessageKey(request, "catruc.insertFail");
				model.addAttribute("errorEndTime", "*");
				model.addAttribute("errorBanGiao", "Ca trực bên nhận không hợp lệ");
				model.addAttribute("xacnhanTC", "bottom");
				
				return "jspalarm/catruc/CaTrucList";
			}
			
			if (result.hasErrors() || giaoNgayTruc.equals("")
					|| giaoNgayTruc.equals("") || catruc.getNhanCaVien().length() >= 100) {
	
				saveMessageKey(request, "catruc.insertFail");
				if (catruc.getNhanCaVien().length() >= 100) {
					model.addAttribute("errorBanGiao", "Lựa chọn quá nhiều ca viên");
				} else {
					model.addAttribute("errorBanGiao",
							"Chưa nhập đầy đủ các thông tin bắt buộc");
				}
				
				model.addAttribute("xacnhanTC", "bottom");
				
				return "jspalarm/catruc/CaTrucList";
			} else {
	
				Date giaoDate = null;
				Date nhanDate = null;
				try {
					giaoDate = (new SimpleDateFormat("dd/MM/yyyy")).parse(giaoNgayTruc);
					nhanDate = (new SimpleDateFormat("dd/MM/yyyy")).parse(nhanNgayTruc);
					catruc.setGiaoNgayTruc(giaoDate);
					catruc.setNhanNgayTruc(nhanDate);
				} catch (Exception exp) {
	
					saveMessageKey(request, "catruc.insertFail");
					model.addAttribute("xacnhanTC", "bottom");
					model.addAttribute("errorBanGiao",
							"Ngày tháng không đúng định dạng");
					return "jspalarm/catruc/CaTrucList";
				}
				
				String newCa = caTrucKeTiep(catruc.getGiaoCaTruc());
				if ((nhanNgayTruc.equals(giaoNgayTruc) && !nhanCaTruc.equals(newCa))
						|| (nhanDate.getTime() < giaoDate.getTime())) {
	
					// Ca truc tiep thep không hợp lệ
					saveMessageKey(request, "catruc.insertFail");
					model.addAttribute("errorBanGiao",
							"Ca trực tiếp theo không hợp lệ");
					model.addAttribute("xacnhanTC", "bottom");
					
					return "jspalarm/catruc/CaTrucList";
				}
				boolean kt= true;
				if (userLogin.getIsRoleSystem()!=null && !userLogin.getIsRoleSystem().equals("Y")&&!userLogin.getIsRoleManager().equals("Y")){	
					boolean checkPassGiao = checkPassLogin(catruc.getGiaoUsername(),passwordGiao);
					boolean checkPassNhan = checkPassLogin(catruc.getNhanUsername(),passwordNhan);
					if (checkPassGiao==false&&checkPassNhan==false)
					{
						kt= false;
						model.addAttribute("errorBanGiao",
								"Mật khẩu hoặc trưởng ca giao và trưởng ca nhận không chính xác");
					}
					else if (checkPassGiao==false&&checkPassNhan==true)
					{
						kt= false;
						model.addAttribute("errorBanGiao",
								"Mật khẩu hoặc trưởng ca giao không chính xác");
					}
					else if (checkPassGiao==true&&checkPassNhan==false)
					{
						kt= false;
						model.addAttribute("errorBanGiao",
								"Mật khẩu hoặc trưởng ca nhận không chính xác");
					}
					
					
					int ketquaCV = alShiftDAO.getAlShiftWorkCheck(giaoNgayTruc,catruc.getGiaoCaTruc(),catruc.getGiaoUsername(),catruc.getGiaoCaVien(),noiDung,region);
					if (ketquaCV == 1)//Công việc cố định trong ca:  Giao ca mà trường Thời gian hoàn thành rỗng thì Alert và không cho giao ca
					{
						kt= false;
						model.addAttribute("errorBanGiao",
								"Các công việc cố định trong ca chưa hoàn thành.");
					}
					else if (ketquaCV == 2)
					{
						kt= false;
						model.addAttribute("errorBanGiao",
								"Công việc thực hiện trong ca của ca trực không có. ");
					}
					else if (ketquaCV == 3)
					{
						kt= false;
						model.addAttribute("errorBanGiao",
								"Chưa nhập nội dung bàn giao ca.");
					}
					
					if (kt== false)
					{
						model.addAttribute("xacnhanTC", "bottom");
						saveMessageKey(request, "catruc.insertFail");
						return "jspalarm/catruc/CaTrucList";
					}
				}
				// Hợp lệ thì thêm mới
				//catruc.setNoiDung(noiDung);
				//update noi dung ca truc
				AlShift giaoShift = alShiftDAO.getCaTruc(catruc.getGiaoCaTruc(),giaoNgayTruc,region);
				if (giaoShift!=null)
				{
					giaoShift.setNoiDung(noiDung);
					alShiftDAO.updateByPrimaryKeySelective(giaoShift);
				}
				//tao ca truc moi
				AlShift oldShift = new AlShift();
				oldShift = alShiftDAO.checkCaTruc(catruc.getGiaoCaTruc(),
						giaoNgayTruc,region);
				if (oldShift == null) {
					catruc.setCreateDate(new Date());
					catruc.setCreatedBy(username);
					catruc.setNoiDung(noiDung);
					catruc.setRegion(region);
					//alShiftDAO.insertSelective(catruc);
					alShiftDAO.insertShiftAndWork(catruc,maPhong);
					model.addAttribute("first", false);
				} else {
					catruc.setModifiedBy(username);
					catruc.setModifyDate(new Date());
					alShiftDAO.updateByPrimaryKeySelective(catruc);
				}
				saveMessageKey(request, "catruc.insertSuccesfull");
				
				model.addAttribute("globalCaTruc", catruc);
				//RequestContextHolder.currentRequestAttributes().setAttribute("globalCaTruc", catruc, RequestAttributes.SCOPE_SESSION);
					
				// Hien thi danh sach
				AlShift newShift1 = new AlShift();
				caT = nhanCaTruc;
				ngayT = catruc.getNhanDateStr();
				newShift1.setGiaoCaTruc(catruc.getNhanCaTruc());
				newShift1.setGiaoCaVien(catruc.getNhanCaVien());
				newShift1.setGiaoNgayTruc(catruc.getNhanNgayTruc());
				newShift1.setGiaoUsername(catruc.getNhanUsername());
				model.addAttribute("ca", nhanCaTruc);
				model.addAttribute("ngayTruc", catruc.getNhanDateStr());
				model.addAttribute("truongCa", catruc.getNhanUsername());
				model.addAttribute("caVien", catruc.getNhanCaVien());
				model.addAttribute("giaoNgayTruc", catruc.getNhanDateStr());
				model.addAttribute("giaoCaTruc", catruc.getNhanCaTruc());
				model.addAttribute("giaoCaTrucStr", catruc.getNhanCaTruc());
				model.addAttribute("giaoUsername", catruc.getNhanUsername());
				if (username.equalsIgnoreCase(catruc.getNhanUsername()))
				{
					checkTruongCa = true;
				}
				model.addAttribute("checkTruongCa", checkTruongCa);
				String[] user = catruc.getNhanCaVien().split(",");
				
				if (shift!=null && shift.getNhanUsername()!=null && shift.getNhanUsername().equals(username)) {
					checkCaTruc = true;
				}
				for (String item : user) {
					if (item.equals(username)) {
						checkCaTruc = true;
						break;
					}
				}
				model.addAttribute("checkCaTruc", checkCaTruc);
				if (catruc.getNhanCaTruc().equalsIgnoreCase("SÁNG")) {
					startTruc = nhanNgayTruc + " " + sang + ":00:00";
					endTruc = nhanNgayTruc + " " + chieu + ":00:00";
				}
				if (catruc.getNhanCaTruc().equalsIgnoreCase("CHIỀU")) {
	
					startTruc = nhanNgayTruc + " " + chieu + ":00:00";
					endTruc = nhanNgayTruc + " " + toi + ":00:00";
				}
				if (catruc.getNhanCaTruc().equalsIgnoreCase("TỐI")) {
					startTruc = nhanNgayTruc + " " + toi + ":00:00";
					endTruc = df_full.format(DateTools.addDate(new Date(), 1, 0, 0,
							0)) + " " + sang + ":00:00";
				}
	
				String nhanCatruc= caTrucKeTiep(newShift1.getGiaoCaTruc());
				String nhanCaTruong ="";
				String nhanCaVien="";
				String nhanUCTT="";
				if (newShift1.getGiaoCaTruc().equalsIgnoreCase("TỐI")) {
					nhanNgayTruc = df_full.format(DateTools.addDate(newShift1.getGiaoNgayTruc(), 1, 0, 0, 0));
				} else
				{
					nhanNgayTruc = newShift1.getGiaoDateStr();
				}
				List<AlWorkCalendar> nextShift = alWorkCalendarDAO.getShift(nhanNgayTruc,nhanCatruc,region);
				if (nextShift.size()>0)
				{
					String con1="";
					String con2="";
					String con3="";
					for (AlWorkCalendar alWorkCalendar : nextShift) {
						String funtion=alWorkCalendar.getFunction();
						
						if (funtion!=null && funtion.toLowerCase().contains("trưởng ca")&&!nhanCaTruong.contains(alWorkCalendar.getEmail()))
						{
							nhanCaTruong+= con1+ alWorkCalendar.getEmail();
							con1=",";
						}
						/*else if (funtion.contains("ca viên")&&!nhanCaVien.contains(alWorkCalendar.getEmail()))*/
						else if (!nhanCaVien.contains(alWorkCalendar.getEmail()))
						{
							nhanCaVien+= con2+ alWorkCalendar.getEmail();
							con2=",";
						}
						else
						{
							if (!nhanUCTT.contains(alWorkCalendar.getEmail()))
							{
								nhanUCTT+= con3+ alWorkCalendar.getEmail();
								con3=",";
							}
						}
					}
				}
				newShift1.setNhanUsername(nhanCaTruong);
				newShift1.setNhanCaVien(nhanCaVien);
				model.addAttribute("nhanNgayTruc", nhanNgayTruc);
				model.addAttribute("nhanCaTruc",nhanCatruc);
				model.addAttribute("nhanUCTT",nhanUCTT);
				model.addAttribute("catruc", newShift1);
				model.addAttribute("truongCaList", nvList);
				model.addAttribute("caVienList", nvList);
				model.addAttribute("caList", caListName);
				model.addAttribute("noiDung", "");
				
				model.addAttribute("passwordGiao", null);
				model.addAttribute("passwordNhan", null);
				// model.addAttribute("nhanNgayTruc", nhanNgayTruc);
				model.addAttribute("xacnhanTC", "");
			}
		}
		else{
			saveMessageKey(request, "catruc.insertFail");
			model.addAttribute("errorBanGiao", "Người dùng đăng nhập không có quyền giao ca");
			model.addAttribute("xacnhanTC", "bottom");
		
		}
		// Load lại form
			return "jspalarm/catruc/CaTrucList";

	}
	private boolean checkPassLogin(String username, String password) {
		List<SysUsers> userList = sysUsersDao.getUserByUsername(username);
		boolean isDb = false;
		if (userList.size()>0)
		{
			try {
				SysUsers user = userList.get(0);
					if(user.getLoginBy() != null && !user.getLoginBy().equals("")){
						// 1. Neu LOGIN_BY = 1 la dang nhap bang email thi xac thuc qua LDAP, k check user, pass tren db cua minh
						if(user.getLoginBy().equals("1")){
							try {
								if(
										(LDAP.authentication(username + dotEmail, password) || LDAP.authentication(username, password))
										&& password != null && !password.equals("")
								){
									//logger.info("LOGIN LDAP SUCCESS");
									return true;
								}
								else{
									return false;
								}
							} catch (Exception ex) {
								return false;
							}
						}
						// 2. Neu LOGIN_BY = 2 la dang nhap bang username thi xac thuc qua password tren db cua minh, k check LDAP
						else if(user.getLoginBy().equals("2")){
							if (passwordEncoder.isPasswordValid(user.getPassword(), password, null) == true 
									&& password != null && !password.equals("")) {
									isDb = true;
									//logger.info("LOGIN DB SUCCESS");
									return true;
								}
						}
						// 2. Neu LOGIN_BY = 3 la dang nhap bang username & email thi nguoi dung co the nhap 1 trong 2 dieu kien tren deu duoc.
						else {
							if (password != null && !password.equals("")&&passwordEncoder.isPasswordValid(user.getPassword(), password, null) == true 
									) {
									isDb = true;
								}
							if (!isDb) {
								try {
									if(
											(LDAP.authentication(username + dotEmail, password) || LDAP.authentication(username, password))
											&& password != null && !password.equals("")
									){
										//logger.info("LOGIN LDAP SUCCESS");
										return true;  
									}
									else{
										return false;
									}
								} catch (Exception ex) {
									return false;
								}
							} else {
								//logger.info("LOGIN DB SUCCESS");
								return true;
							}
						}
						
					}
					else{
						if (passwordEncoder.isPasswordValid(user.getPassword(), password, null) == true 
								&& password != null && !password.equals("")) {
								isDb = true;
							}
						if (!isDb) {
							try {
								if(
										(LDAP.authentication(username + dotEmail, password) || LDAP.authentication(username, password))
										&& password != null && !password.equals("")
								){
									return true;
								}
								else{
									return false;
								}
							} catch (Exception ex) {
								return false;
							}
						} else {
							//logger.info("LOGIN DB SUCCESS");
							return true;
						}
					}
					
			} catch (Exception e) {
				return false;
			}
		}
		
			return false;
	}
	private String caTrucKeTiep(String caHT) {
		String newCa = "";
		if (caHT != null) {
			if (caHT.startsWith("S"))
				newCa = "Chiều";
			if (caHT.startsWith("C")) {
				newCa = "Tối";
			}
			if (caHT.startsWith("T")) {
				newCa = "Sáng";
			}
		}
		return newCa;
	}

	private AlShift getCaTrucHT(String region) {
		List<SYS_PARAMETER> caList = new ArrayList<SYS_PARAMETER>();
		caList = sysParameterDao.getSysParameterByCode("CA_TRUC_VALUE");
		String sang = caList.get(0).getValue();
		String chieu = caList.get(1).getValue();
		String toi = caList.get(2).getValue();
		String caT = "";
		String ngayT = "";
		AlShift shift = new AlShift();
		Date currentTime = new Date();
		int hour = currentTime.getHours();
		ngayT = df_full.format(currentTime);
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
		if (shift == null) {
			shift = alShiftDAO.getCaTruc(caT, ngayT,region);
		}
		return shift;
	}

	@RequestMapping(value = "delete23G", method = RequestMethod.GET)
	public String onDelete(@RequestParam(required = true) String id,@RequestParam(required = false) String warningTp,@RequestParam(required = false) String note,@RequestParam(required = false) String region,
			HttpServletRequest request, Model model) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		AlShift shift = getCaTrucHT(userLogin.getRegion());
		boolean checkCaTruc = false;
		
		if (shift != null) {
			String[] user = shift.getNhanCaVien().split(",");
			if (shift.getNhanUsername().equals(username)|| userLogin.getIsRoleManager().equals("Y")) {
				checkCaTruc = true;
			}
			for (String item : user) {
				if (item.equals(username)) {
					checkCaTruc = true;
					break;
				}
			}
			model.addAttribute("checkCaTruc", checkCaTruc);
		}
		if (checkCaTruc == true) {
			/*try {*/
				alAlarmWorksDAO.deleteByPrimaryKey(Integer.parseInt(id));
				alAlarmWorksDAO.SP_PUSH_SCL_LL(Integer.parseInt(id));
				saveMessageKey(request, "messsage.confirm.deletesuccess");
			/*} catch (Exception e) {
				saveMessageKey(request, "messsage.confirm.deletefailure");
			}*/
		} else {
			saveMessageKey(request, "cautruc.deleteFail");
		}
		if (note!=null && note.equalsIgnoreCase("filter"))
		{
			return "redirect:listFilter.htm?warningTp="+warningTp+"&region="+region;
		}
		else
		{
			return "redirect:list.htm?warningTp="+warningTp+"&region="+region;
		}
	}
	// Lay du lieu alarm Log
	@RequestMapping("data")
	public @ResponseBody 
	List<RAlarmLog> data(@RequestParam(required = false) String warningTp,
			@RequestParam(required = false) String day,
			@RequestParam(required = false) String alarmTypeTK,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String operator,
			@RequestParam(required = false) String system,
			HttpServletRequest request, HttpServletResponse response,Model model) {
		System.out.println("status: "+status);
		if (operator==null||operator.equals("null"))
		{
			operator="";
		}
		if (system==null||system.equals("null"))
		{
			system="";
		}
		List<RAlarmLog> alarmSameList2G = new ArrayList<RAlarmLog>();
		try
		{
			alarmSameList2G = rAlarmLogDAO.getAlarmLogAtShift(warningTp,day,alarmTypeTK,status,operator,system);
		}
		catch (Exception exp){}
		return alarmSameList2G;
	 }
	
	@RequestMapping(value = "form23G", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id,
			@RequestParam(required = true) String warningTp,
			@RequestParam(required = true) String region,
			@RequestParam(required = false) String note,
			ModelMap model,
			HttpServletRequest request) {

		List<SYS_PARAMETER> sysParemeterTitle = alShiftDAO.titleCaTrucList(warningTp,"ADD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
		}
		model.addAttribute("note", note);
		AlShift shift = getCaTrucHT(region);
		if (shift!=null)
		{
			int idShift= shift.getShiftId();
			model.addAttribute("idShift", idShift);
		}
		String sdate = null;
		String edate = null;
		String mdSdate=null;
		String contactTime=null;
		String isLeaseLine="";
		String operator="";
		String system="";
		AlAlarmWorks record = new AlAlarmWorks();
		
		List<String> bscTLList = vAlWarningSystemDAO.getSystemByTypeWarning(warningTp);
		//String bscListStr ="";
		String bscArray="var bscTLList = new Array(";
		String operatorArray="var operatorList = new Array(";
		String cn="";
		for (String bsc : bscTLList) {
			bscArray = bscArray + cn +"\""+bsc+"\"";
			operatorArray = operatorArray + cn +"\""+bsc+"\"";
			cn=",";
		}
		bscArray = bscArray+");";
		operatorArray = operatorArray+");";
		model.addAttribute("bscTLList", bscArray);
		model.addAttribute("operatorList", operatorArray);
		//danh sach alarm name
		List<SYS_PARAMETER> alarmList = sysParameterDao.getAlamNameList(warningTp);
		//String bscListStr ="";
		String alarmArray="var alarmList = new Array(";
		String cn2="";
		for (SYS_PARAMETER alarm : alarmList) {
			alarmArray = alarmArray + cn2 +"\""+alarm.getValue()+"\"";
			cn2=",";
		}
		alarmArray = alarmArray+");";
		model.addAttribute("alarmList", alarmArray);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		sdate = df_full2.format(cal.getTime());
		String day = df_full.format(cal.getTime());
		String alarmTypeTK="";
		
		if (warningTp == null)
			warningTp = "";
		
		if (id == null) {
			
			alarmTypeTK= "SERVICE";
		}
		else
		{
			record = alAlarmWorksDAO.selectByPrimaryKey(Integer.parseInt(id));
			operator = record.getOperator();
			system = record.getSystem();
			sdate = record.getStartDateStr();
			edate = record.getEndDateStr();
			mdSdate = record.getMDSdateStr();
			day = sdate.substring(0,10);
			alarmTypeTK=record.getAlarmType();
			contactTime = record.getContactTimeStr();
			if (record.getLeaseLine()!=null && !record.getLeaseLine().equals(""))
			{
				isLeaseLine = "Y";
			}
			
		}
		//String system ="";
		String systemArray="var systemList = new Array(";
		cn="";
		List<String> systemList = new ArrayList<String>();
		try{
		systemList = vAlHCellDAO.getAllCellByBsc(warningTp,operator,null);
		for (String sys : systemList) {
			systemArray = systemArray + cn +"\""+sys+"\"";
			cn=",";
		}}
		catch(Exception exp){}
		systemArray = systemArray+");";
		model.addAttribute("mdSdate", mdSdate);
		model.addAttribute("systemList", systemArray);
		model.addAttribute("oldSdate", sdate);
		model.addAttribute("oldEdate", edate);
		model.addAttribute("oldOperator",operator);
		model.addAttribute("oldSystem",system);
		model.addAttribute("sdate", sdate);
		model.addAttribute("edate", edate);
		model.addAttribute("warningTp", warningTp);
		model.addAttribute("day", day);
		model.addAttribute("alarmTypeTK", alarmTypeTK);
		model.addAttribute("bscidTK", operator);
		model.addAttribute("cellidTK", system);
		model.addAttribute("contactTime", contactTime);
		model.addAttribute("isLeaseLine", isLeaseLine);
		model.addAttribute("standardName", record.getStandardName());
		model.addAttribute("statusUCTT", record.getStatusUCTT());
		model.addAttribute("region", region);
		model.addAttribute("alAlarmWorks", record);
		
		//Danh sach cac alarmLog
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("R_ALARM_LOG_SHIFT");
		//lay du lieu column cua grid
		model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
		//Lay du lieu datafield cua grid
		model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
		//Lay duong link url de lay du lieu do vao grid
		model.addAttribute("url", "data.htm?warningTp="+warningTp+"&day="+day+
				"&alarmTypeTK="+alarmTypeTK+"&status=&operator="+operator+"&system="+system); 
		//Lay du lieu cac cot an hien cua grid 
		List<CTableConfig> columnList = cTableConfigDAO.getColumnList("R_ALARM_LOG_SHIFT");
		model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
			
		return "jspalarm/catruc/CB2GForm";
	}
	public void addAttribute(AlAlarmWorks alAlarmWorks,String sdate,String edate,String operator,String oldOperator,String oldSystem,
			String oldSdate, String oldEdate,String causebySystem, String teamProcess, String countSite, String userProcess,
			String warningTp,String bscidTK, String cellidTK, String alarmTypeTK,  String status,  String day,  String dept,
			String actionProcess, String alarmType, String mdSdate, String note, String isLeaseLine, String leaseLine,
			String contactTime,String isSendLeaseLine,String region,ModelMap model)
	{
		model.addAttribute("alAlarmWorks", alAlarmWorks);
		model.addAttribute("sdate", sdate);
		model.addAttribute("edate", edate);
		model.addAttribute("mdSdate", mdSdate);
		model.addAttribute("operator", operator);
		model.addAttribute("causebySystem", causebySystem);
		model.addAttribute("warningTp", warningTp);
		model.addAttribute("oldSdate", oldSdate);
		model.addAttribute("oldEdate", oldEdate);
		model.addAttribute("oldOperator",oldOperator);
		model.addAttribute("oldSystem",oldSystem);
		model.addAttribute("status", status);
		model.addAttribute("alarmType", alarmType);
		model.addAttribute("day", day);
		model.addAttribute("isSendLeaseLine", isSendLeaseLine);
		model.addAttribute("alarmTypeTK", alarmTypeTK);
		model.addAttribute("system", alAlarmWorks.getSystem());
		model.addAttribute("bscidTK", bscidTK);
		model.addAttribute("cellidTK", cellidTK);
		model.addAttribute("contactTime", contactTime);
		model.addAttribute("isLeaseLine", isLeaseLine);
		model.addAttribute("region", region);
	}

	@RequestMapping(value = "form23G", method = RequestMethod.POST)
	public String submitForm(
			@ModelAttribute("alAlarmWorks") @Valid AlAlarmWorks alAlarmWorks,
			BindingResult result, @RequestParam(required = false) String sdate,
			@RequestParam(required = false) String edate,
			@RequestParam(required = false) String operator,
			@RequestParam(required = false) String oldOperator,
			@RequestParam(required = false) String oldSystem,
			@RequestParam(required = false) String oldSdate,
			@RequestParam(required = false) String oldEdate,
			@RequestParam(required = false) String causebySystem,
			@RequestParam(required = false) String teamProcess,
			@RequestParam(required = false) String countSite,
			@RequestParam(required = false) String userProcess,
			@RequestParam(required = true) String warningTp, 
			@RequestParam(required = false) String bscidTK, 
			@RequestParam(required = false) String cellidTK,
			@RequestParam(required = false) String alarmTypeTK, 
			@RequestParam(required = false) String status, 
			@RequestParam(required = false) String day, 
			@RequestParam(required = false) String dept,
			@RequestParam(required = false) String actionProcess,
			@RequestParam(required = false) String alarmType,
			@RequestParam(required = false) String mdSdate,
			@RequestParam(required = false) String note,
			@RequestParam(required = false) String isLeaseLine,
			@RequestParam(required = false) String leaseLine,
			@RequestParam(required = false) String contactTime,
			@RequestParam(required = false) String isSendLeaseLine,
			@RequestParam(required = false) String region,
			@RequestParam(required = false) String shiftRegion,
			@RequestParam(required = false) String statusUCTT,
			ModelMap model,
			HttpServletRequest request) throws ParseException {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		
		List<CHighlightConfigs> highlightConfigList = cHighlightConfigsDAO.getByKey("SHIFT_ID");
		List<SYS_PARAMETER> sysParemeterTitle = alShiftDAO.titleCaTrucList(warningTp,"ADD");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleF", sysParemeterTitle.get(0).getValue());
		}
		model.addAttribute("note", note);
		System.out.println("Loai canh bao:/.............."+warningTp);
		System.out.println("bscidTK: "+bscidTK);
		System.out.println("cellidTK: "+cellidTK);
		System.out.println("alarmTypeTK: "+alarmTypeTK);
		System.out.println("status: "+status);
		System.out.println("day: "+day);
		boolean type2G=true;
		// lay style
		String style = "";
		for (CHighlightConfigs item: highlightConfigList) {
			style += item.getStyle().toString();
		}
		
		style = style.replaceAll("'", "");
		style = style.replaceAll(",", ";");
		
		List<String> systemList = new ArrayList<String>();
		
		List<String> bscTLList = vAlWarningSystemDAO.getSystemByTypeWarning(warningTp);
		//String bscListStr ="";
		String bscArray="var bscTLList = new Array(";
		String operatorArray="var operatorList = new Array(";
		String cn="";
		for (String bsc : bscTLList) {
			bscArray = bscArray + cn +"\""+bsc+"\"";
			operatorArray = operatorArray + cn +"\""+bsc+"\"";
			cn=",";
		}
		bscArray = bscArray+");";
		operatorArray = operatorArray+");";
		
		systemList = vAlHCellDAO.getAllCellByBsc(warningTp,operator,null);
		//String system ="";
		String systemArray="var systemList = new Array(";
		cn="";
		for (String sys : systemList) {
			systemArray = systemArray + cn +"\""+sys+"\"";
			cn=",";
		}
		systemArray = systemArray+");";
		
		List<SYS_PARAMETER> alarmList = sysParameterDao.getAlamNameList(warningTp);
		String alarmArray="var alarmList = new Array(";
		String cn2="";
		for (SYS_PARAMETER alarm : alarmList) {
			alarmArray = alarmArray + cn2 +"\""+alarm.getValue()+"\"";
			cn2=",";
		}
		alarmArray = alarmArray+");";
		model.addAttribute("alarmList", alarmArray);
		
		//Danh sach cac alarmLog
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("R_ALARM_LOG_SHIFT");
		//Lay du lieu cac cot an hien cua grid 
		List<CTableConfig> columnList = cTableConfigDAO.getColumnList("R_ALARM_LOG_SHIFT");
		
		if (warningTp.equals("2G")) {	
			type2G=true;
		}
		if (warningTp.equals("3G")) {
			
			type2G=false;
		}
		if (region==null || region.equals("") || region.equals(",")){
			region = shiftRegion;
		}
		AlShift shift = getCaTrucHT(region);
		boolean checkCaTruc = false;
		int shiftID = 0;
		if (shift != null) {
			String[] user = shift.getNhanCaVien().split(",");
			if (shift.getNhanUsername().equals(username)||userLogin.getIsRoleManager().equals("Y")) {
				checkCaTruc = true;
			}
			for (String item : user) {
				if (item.equals(username)) {
					checkCaTruc = true;
					break;
				}
			}
			shiftID = shift.getShiftId();
		}
		
		if (checkCaTruc == false|| (isLeaseLine.equals("Y") && (sdate.equals("")||contactTime.equals("")||alAlarmWorks.getSystem()==null||alAlarmWorks.getSiteList()==null||leaseLine.equals("")||alAlarmWorks.getActionProcess()==null)))
		{
		//	saveMessageKey(request, "vAlAlarmLog.isLeaseLineCheckFail");
			//saveMessageKey(request, "alarmExtend.errorField");
			addAttribute(alAlarmWorks, sdate, edate, operator, oldOperator, oldSystem, oldSdate, oldEdate, causebySystem, teamProcess, countSite, userProcess, warningTp, bscidTK, cellidTK, alarmTypeTK, status, day, dept, actionProcess, alarmType, mdSdate, note,
					isLeaseLine, leaseLine, contactTime, isSendLeaseLine, region, model);
			model.addAttribute("bscTLList", bscArray);
			model.addAttribute("operatorList", operatorArray);
			model.addAttribute("systemList", systemArray);
			model.addAttribute("bscidList", bscTLList);
			
			//lay du lieu column cua grid
			model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
			//Lay du lieu datafield cua grid
			model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
			//Lay duong link url de lay du lieu do vao grid
			model.addAttribute("url", "data.htm?warningTp="+warningTp+"&day="+sdate.substring(0,10)+
					"&alarmTypeTK=&status=&operator="+oldOperator+"&system="+oldSystem); 
			model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
			return "jspalarm/catruc/CB2GForm";
		}
		if (sdate==null || (sdate!=null && (sdate.equals("")||DateTools.isValid("dd/MM/yyyy HH:mm:ss", sdate+":00")==false)))
			{
			model.addAttribute("errorsdate", "*");
			//saveMessageKey(request, "alarmExtend.errorField");
			addAttribute(alAlarmWorks, sdate, edate, operator, oldOperator, oldSystem, oldSdate, oldEdate, causebySystem, teamProcess, countSite, userProcess, warningTp, bscidTK, cellidTK, alarmTypeTK, status, day, dept, actionProcess, alarmType, mdSdate, note,
					isLeaseLine, leaseLine, contactTime, isSendLeaseLine, region, model);
			model.addAttribute("bscTLList", bscArray);
			model.addAttribute("operatorList", operatorArray);
			model.addAttribute("systemList", systemArray);
			model.addAttribute("bscidList", bscTLList);
			//lay du lieu column cua grid
			model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
			//Lay du lieu datafield cua grid
			model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
			//Lay duong link url de lay du lieu do vao grid
			if (sdate!=null&&!sdate.equals(""))
			{
				sdate = sdate.substring(0,10);
			}
			model.addAttribute("url", "data.htm?warningTp="+warningTp+"&day="+sdate+
					"&alarmTypeTK=&status=&operator="+oldOperator+"&system="+oldSystem); 
			model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
			return "jspalarm/catruc/CB2GForm";
		}
		if (edate!=null && !edate.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", edate+":00")==false)
		{
			model.addAttribute("erroredate", "*");
			//saveMessageKey(request, "alarmExtend.errorField");
			addAttribute(alAlarmWorks, sdate, edate, operator, oldOperator, oldSystem, oldSdate, oldEdate, causebySystem, teamProcess, countSite, userProcess, warningTp, bscidTK, cellidTK, alarmTypeTK, status, day, dept, actionProcess, alarmType, mdSdate, note,
					isLeaseLine, leaseLine, contactTime, isSendLeaseLine, region, model);
			model.addAttribute("bscTLList", bscArray);
			model.addAttribute("operatorList", operatorArray);
			model.addAttribute("systemList", systemArray);
			model.addAttribute("bscidList", bscTLList);
			//lay du lieu column cua grid
			model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
			//Lay du lieu datafield cua grid
			model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
			//Lay duong link url de lay du lieu do vao grid
			model.addAttribute("url", "data.htm?warningTp="+warningTp+"&day="+sdate.substring(0,10)+
					"&alarmTypeTK=&status=&operator="+oldOperator+"&system="+oldSystem); 
			model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
			return "jspalarm/catruc/CB2GForm";
		}
		if (mdSdate!=null && !mdSdate.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", mdSdate+":00")==false)
		{
			model.addAttribute("errormdSdate", "*");
			//saveMessageKey(request, "alarmExtend.errorField");
			addAttribute(alAlarmWorks, sdate, edate, operator, oldOperator, oldSystem, oldSdate, oldEdate, causebySystem, teamProcess, countSite, userProcess, warningTp, bscidTK, cellidTK, alarmTypeTK, status, day, dept, actionProcess, alarmType, mdSdate, note,
					isLeaseLine, leaseLine, contactTime, isSendLeaseLine, region, model);
			model.addAttribute("bscTLList", bscArray);
			model.addAttribute("operatorList", operatorArray);
			model.addAttribute("systemList", systemArray);
			model.addAttribute("bscidList", bscTLList);
			//lay du lieu column cua grid
			model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
			//Lay du lieu datafield cua grid
			model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
			//Lay duong link url de lay du lieu do vao grid
			model.addAttribute("url", "data.htm?warningTp="+warningTp+"&day="+sdate.substring(0,10)+
					"&alarmTypeTK=&status=&operator="+oldOperator+"&system="+oldSystem); 
			model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
			return "jspalarm/catruc/CB2GForm";
		}
		if (contactTime!=null && !contactTime.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm:ss", contactTime+":00")==false)
		{
			model.addAttribute("errorcontactTime", "*");
			//saveMessageKey(request, "alarmExtend.errorField");
			addAttribute(alAlarmWorks, sdate, edate, operator, oldOperator, oldSystem, oldSdate, oldEdate, causebySystem, teamProcess, countSite, userProcess, warningTp, bscidTK, cellidTK, alarmTypeTK, status, day, dept, actionProcess, alarmType, mdSdate, note,
					isLeaseLine, leaseLine, contactTime, isSendLeaseLine, region, model);
			model.addAttribute("bscTLList", bscArray);
			model.addAttribute("operatorList", operatorArray);
			model.addAttribute("systemList", systemArray);
			model.addAttribute("bscidList", bscTLList);
			//lay du lieu column cua grid
			model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
			//Lay du lieu datafield cua grid
			model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
			//Lay duong link url de lay du lieu do vao grid
			model.addAttribute("url", "data.htm?warningTp="+warningTp+"&day="+sdate.substring(0,10)+
					"&alarmTypeTK=&status=&operator="+oldOperator+"&system="+oldSystem); 
			model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
			return "jspalarm/catruc/CB2GForm";
		}
		
		System.out.println(result.getFieldError());
		if (result.hasErrors()) {
			//saveMessageKey(request, "alarmExtend.errorField");
			addAttribute(alAlarmWorks, sdate, edate, operator, oldOperator, oldSystem, oldSdate, oldEdate, causebySystem, teamProcess, countSite, userProcess, warningTp, bscidTK, cellidTK, alarmTypeTK, status, day, dept, actionProcess, alarmType, mdSdate, note,
					isLeaseLine, leaseLine, contactTime, isSendLeaseLine, region, model);
			model.addAttribute("bscTLList", bscArray);
			model.addAttribute("operatorList", operatorArray);
			model.addAttribute("systemList", systemArray);
			model.addAttribute("bscidList", bscTLList);
			//lay du lieu column cua grid
			model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
			//Lay du lieu datafield cua grid
			model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
			//Lay duong link url de lay du lieu do vao grid
			model.addAttribute("url", "data.htm?warningTp="+warningTp+"&day="+sdate.substring(0,10)+
					"&alarmTypeTK=&status=&operator="+oldOperator+"&system="+oldSystem); 
			model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
			return "jspalarm/catruc/CB2GForm";
		} else {
			
			if (!warningTp.equals("")) {
				alAlarmWorks.setWarningType(warningTp);
			}
			
			if (sdate != null && !sdate.equals("")) {
				alAlarmWorks.setStime((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(sdate+":00"));
			}
			if (edate != null && !edate.equals("")) {
				alAlarmWorks.setEtime((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(edate+":00"));
			}
			if (mdSdate != null && !mdSdate.equals("")) {
				alAlarmWorks.setMdSdate((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(mdSdate+":00"));
			}
			if (contactTime != null && !contactTime.equals("")) {
				alAlarmWorks.setContactTime((new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).parse(contactTime+":00"));
			}
			if ((!edate.equals("")&&!sdate.equals("")&& alAlarmWorks.getStime().getTime()> alAlarmWorks.getEtime().getTime()))
			{
			//	saveMessageKey(request, "cautruc.sosanhNgayMD");
				addAttribute(alAlarmWorks, sdate, edate, operator, oldOperator, oldSystem, oldSdate, oldEdate, causebySystem, teamProcess, countSite, userProcess, warningTp, bscidTK, cellidTK, alarmTypeTK, status, day, dept, actionProcess, alarmType, mdSdate, note,
						isLeaseLine, leaseLine, contactTime, isSendLeaseLine, region, model);
				model.addAttribute("bscTLList", bscArray);
				model.addAttribute("operatorList", operatorArray);
				model.addAttribute("systemList", systemArray);
				model.addAttribute("bscidList", bscTLList);
				//lay du lieu column cua grid
				model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
				//Lay du lieu datafield cua grid
				model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
				//Lay duong link url de lay du lieu do vao grid
				model.addAttribute("url", "data.htm?warningTp="+warningTp+"&day="+sdate.substring(0,10)+
						"&alarmTypeTK=&status=&operator="+oldOperator+"&system="+oldSystem); 
				model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
					return "jspalarm/catruc/CB2GForm";
			}
			
			System.out.println("++++++: "+alAlarmWorks.getIsSpecial());
			alAlarmWorks.setCausebySystem(causebySystem);
			alAlarmWorks.setOperator(operator);
			alAlarmWorks.setActionProcess(actionProcess);
			alAlarmWorks.setLeaseLine(leaseLine);
			alAlarmWorks.setIsSendLeaseLine(isSendLeaseLine);
			if (oldEdate==null || (oldEdate!=null&&oldEdate.equals(""))||alAlarmWorks.getId() == null)
			{
				alAlarmWorks.setShiftId(shiftID);
				alAlarmWorks.setCreatedBy(username);
				alAlarmWorks.setCreateDate(new Date());
			}
			else
			{
				alAlarmWorks.setModifiedBy(username);
				alAlarmWorks.setModifyDate(new Date());
			}
			alAlarmWorks.setTeamProcess(teamProcess);
			alAlarmWorks.setUserProcess(userProcess);
			alAlarmWorks.setDept(dept);
			alAlarmWorks.setAlarmType(alarmType);
			alAlarmWorks.setStatusUCTT(statusUCTT);
			alAlarmWorksDAO.insertAlarmWork(alAlarmWorks);
			
		}
		if (note!=null && note.equalsIgnoreCase("filter"))
		{
			return "redirect:listFilter.htm?warningTp="+warningTp+"&region="+region;
		}
		else
		{
			return "redirect:list.htm?warningTp="+warningTp+"&region="+region;
		}
		
	}
	
	
	@RequestMapping(value = "listFilter")
	public String submitFilter(@RequestParam(required = false) String warningTp,
			@RequestParam(required = false) String ngayTK,
			@RequestParam(required = false) String ngayTKTo,
			@RequestParam(required = false) String caTK,
			@RequestParam(required = false) String region,
			Model model, HttpServletRequest request) throws ParseException {
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
			
		}else
		{
			checkCaTruc=true;
		}
		model.addAttribute("checkCaTruc", checkCaTruc);
		// A_ALERT_USERS user = alertUsersDao.getUserByUsername(username);
		if (caTK!=null)
		{
			caTK = caTK.trim();
		}
		else
		{
			caTK="";
		}
		List<SYS_PARAMETER> sysParemeterTitle = alShiftDAO.titleCaTrucList(null,"FILTER");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("titleTK", sysParemeterTitle.get(0).getValue());
		}
		List<SYS_PARAMETER> caList = new ArrayList<SYS_PARAMETER>();
		caList = sysParameterDao.getSysParameterByCode("CA_TRUC_VALUE");
		List<SYS_PARAMETER> caListName = new ArrayList<SYS_PARAMETER>();
		caListName = sysParameterDao.getSysParameterByCode("CA_TRUC_NAME");
		model.addAttribute("caList", caListName);

		if (ngayTK == null || ngayTK.equals("")||(ngayTK!=null && !ngayTK.equals("") && DateTools.isValid("dd/MM/yyyy", ngayTK)==false))
		{
			ngayTK = df_full.format(new Date());
		}
		
		if (ngayTKTo == null || ngayTKTo.equals("")||(ngayTKTo!=null && !ngayTKTo.equals("") && DateTools.isValid("dd/MM/yyyy", ngayTKTo)==false))
		{
			ngayTKTo = df_full.format(new Date());
		}
		
		if (warningTp==null||warningTp.equals(""))
		{
			warningTp="WOTK_SHIFT";
		}
		else
		{
			warningTp=warningTp.trim();
		}
		String tableName="";
		String fileName="";
		
		if (warningTp.equalsIgnoreCase("2G")||warningTp.equalsIgnoreCase("3G")||warningTp.equalsIgnoreCase("SU_CO_LON")||warningTp.equalsIgnoreCase("SU_CO_KHAC")||warningTp.equalsIgnoreCase("LOI_TRUYEN_DAN"))
		{
			tableName = "ALARM_WORK_2G_SHIFT";
			fileName="CanhBao"+warningTp;
		}
		else if (warningTp.equalsIgnoreCase("PS_CORE")||warningTp.equalsIgnoreCase("CS_CORE")||warningTp.equalsIgnoreCase("IPBB"))
		{
			tableName = "ALARM_WORK_WARNING_SHIFT";
			fileName="CanhBao"+warningTp;
		}
		else if (warningTp.equalsIgnoreCase("ALARM_EXTEND"))
		{
			tableName = "ALARM_WORK_HCMR_SHIFT";
			fileName="HieuChinhMoRong";
		}
		else if (warningTp.equalsIgnoreCase("TEMPERATURE_SITE"))
		{
			tableName = "AL_MONITOR_TEMPERATURE_SHIFT";
			fileName="GSNhietDoSite";
		}
		else if (warningTp.equalsIgnoreCase("NOI_DUNG"))
		{
			tableName = "NOI_DUNG_BAN_GIAO_SHIFT";
			fileName="NoiDungBanGiao";
		}
		else if (warningTp.equalsIgnoreCase("WOTK_SHIFT"))
		{
			tableName = "W_WORKING_AT_SHIFT_CVCL";
			fileName="CongViecCoDinh";
		}
		
		
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName= fileName+ dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid(tableName);
		//Lay du lieu cac cot an hien cua grid 
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList(tableName);
		List<CHighlightConfigs> highlighList = cHighlightConfigsDAO.getByKeyAndKPI("ALARM_WORK", "ETIME");
		String hightlight=HelpTableConfigs.highLightRow(highlighList,"grid","colorrowrenderer");
		String url = "dataListFilter.htm?warningtype="+warningTp+"&shiftid="+caTK+"&ngayTKTo="+ngayTKTo+"&ngayTK="+ngayTK+"&type=F"+"&tableName="+tableName+"&region="+region;
		String grid ="";
		if (warningTp.equalsIgnoreCase("NOI_DUNG")||warningTp.equalsIgnoreCase("WOTK_SHIFT"))
		{
			grid = HelpTableConfigs.getGridPaginglargerData(configList, "grid", url, "jqxlistbox", listSource, "menujqx", null, "multiplerowsextended","Y");
			if (warningTp.equalsIgnoreCase("WOTK_SHIFT"))
			{
				String url2 = "dataListFilter.htm?warningtype=cvcl&shiftid="+caTK+"&ngayTKTo="+ngayTKTo+"&ngayTK="+ngayTK+"&type=F"+"&tableName="+tableName+"&region="+region;
				String grid2 = HelpTableConfigs.getGridPaginglargerData(configList, "grid2", url2, "jqxlistbox2", listSource, "menujqx2", null, "multiplerowsextended","Y");
				String exportFileName2 = "CongViecCanLam"+dateNow;
				model.addAttribute("grid2", grid2);
				model.addAttribute("exportFileName2", exportFileName2);
			}
		}
		else
		{
			grid = HelpTableConfigs.getGridPaginglargerData(configList, "grid", url,"jqxlistbox", listSource, "menujqx", hightlight, null, "Y");
		}
		model.addAttribute("grid", grid);
		model.addAttribute("warningTp", warningTp);
		model.addAttribute("ngayTK", ngayTK);
		model.addAttribute("caTK", caTK);
		model.addAttribute("ngayTKTo", ngayTKTo);
		model.addAttribute("region", region);
		
			
		return "jspalarm/catruc/caTrucFilter";

	}
	
	@RequestMapping("/dataListFilter")
	public @ResponseBody 
	Map<String, Object> dataListFilter(@RequestParam(required = true) String warningtype,
			@RequestParam(required = true) String shiftid,
			@RequestParam(required = true) String ngayTKTo,
			@RequestParam(required = true) String ngayTK,
			@RequestParam(required = true) String type,
			@RequestParam(required = true) String tableName,
			@RequestParam(required = true) String region,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		int totalRow =0;
		if (warningtype.equalsIgnoreCase("TEMPERATURE_SITE"))
		{
			List<AlMonitorTemperatureSite> monitorSiteList = new ArrayList<AlMonitorTemperatureSite>();
			monitorSiteList = alMonitorTemperatureSiteDAO.getMonitorTemperatureSiteShift(shiftid,ngayTKTo,ngayTK,type,region);
			data.put("totalRow", monitorSiteList.size());
			data.put("Rows", monitorSiteList);
		}
		else if (warningtype.equalsIgnoreCase("NOI_DUNG"))
		{
			List<AlShift> larmWorkList = alShiftDAO.getNoiDungAtShif(shiftid,ngayTKTo,ngayTK,type,region);
			data.put("totalRow", larmWorkList.size());
			data.put("Rows", larmWorkList);
		}
		else if (warningtype.equalsIgnoreCase("WOTK_SHIFT"))// conf viec co dinh trong ca
		{
			List<WWorkingAtShift> dataList = new ArrayList<WWorkingAtShift>();
			dataList = wWorkingAtShiftDAO.getListFilter("cvcd",ngayTK,ngayTKTo,null,null,shiftid,region);
			data.put("totalRow", dataList.size());
			data.put("Rows", dataList);
		}
		else if (warningtype.equalsIgnoreCase("cvcl"))
		{
			List<WWorkingAtShift> dataList = new ArrayList<WWorkingAtShift>();
			dataList = wWorkingAtShiftDAO.getListFilter(null,ngayTK,ngayTKTo,null,null,shiftid,region);
			data.put("totalRow", dataList.size());
			data.put("Rows", dataList);
		}
		else
		{
			int startRecord = 0, endRecord = 0;
			String sortfield = "";
			String sortorder = "";
			int pageNum = Integer.parseInt(request.getParameter("pagenum"));
			if(pageNum == -1)
				pageNum = 1;
			int pageSize = Integer.parseInt(request.getParameter("pagesize"));
			sortfield = request.getParameter("sortdatafield");
			sortorder = request.getParameter("sortorder");
			startRecord = pageNum*pageSize;
			endRecord = startRecord + pageSize;
			
			List<CTableConfig> columnList = null;
			List<CTableConfig> tableConfigList = null;
			columnList = cTableConfigDAO.getTableConfigGet(tableName, sortfield);
			tableConfigList = cTableConfigDAO.getTableConfigGet(tableName, null);
			
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
			/*try
			{*/

				totalRow = alAlarmWorksDAO.countWarningListShif(warningtype, shiftid,ngayTKTo,ngayTK,type,strWhere,region);
				List<AlAlarmWorks> alarmList = alAlarmWorksDAO.getWarningListShif(warningtype, shiftid,ngayTKTo,ngayTK,type,strWhere, startRecord,endRecord,sortfield,sortorder,region);
			/*}
			catch(Exception exp)
			{
				alarmList=null;
			}*/
			data.put("totalRow", totalRow);
			data.put("Rows", alarmList);
		}
		
		return data;
		
	}
	
	
	@RequestMapping("sendSMS")
	public @ResponseBody
	Map<String, Object> insertSMS(@RequestBody SmsContents smsContents) {
	
		String isdn = smsContents.getIsdn();
		String alarmType = smsContents.getAlarmType();
		String mess = smsContents.getMessage();
		System.out.println("isdn: "+isdn);
		System.out.println("alarmType: "+alarmType);
		System.out.println("mess: "+mess);
		List<SysDefineSmsEmail> userList = new ArrayList<SysDefineSmsEmail>();
		SmsContents record = new SmsContents();
		record.setAlarmType(alarmType);
		record.setMessage(mess);
		record.setCreateTime(new Date());
		String[] groupList = isdn.split(",");
		String kt="";
 		String con="";
 		List<String> phoneTrue = new ArrayList<String>();
 		String phoneTo="";
 		String temp="";
		for (String group : groupList) {
			
			if (group!=null && !group.equals(""))
			{
			    System.out.println("send nhom");
			    	userList = sysDefineSmsEmailDAO.getListAll("SMS", group.trim(), null);
			    	if (userList.size()>0)
			    	{
			    		phoneTo="";
			     		temp="";
				    	for (SysDefineSmsEmail user : userList) {
				    		System.out.println("phone:"+user.getGroupUser());
				    		if (user.getGroupUser()!=null && !user.getGroupUser().equals(""))
				    		{
					    		String[] phoneList= user.getGroupUser().replaceAll(";",",").split(",");
					    		
					    		for (String phone : phoneList) {
					    			phone=phone.trim();
					    			Integer count= smsContentsDAO.checkIsdn(phone);
							    	if (count!=null && count==0)
							    	{
							    		kt = kt+con+phone;
							    		con=",";
							    	}
							    	else
							    	{
							    		int ktnum=1;
							    		for (String ph : phoneTrue) {
											if (ph.equals(phone))
											{
												ktnum=0;
												break;
											}
										}
							    		if (ktnum==1)
							    		{
							    			/*phoneTo+=temp+phone;
								    		temp=",";*/
							    			SysUsers user1 = sysUsersDao.getUserByPhone(phone);
								    		if (user1!=null && user1.getUsername()!=null){
								    			record.setUsername(user1.getUsername());
								    		}
								    		else
								    		{
								    			record.setUsername(phone);
								    		}
							    			record.setIsdn(phone);
								    		smsContentsDAO.sendMail(record);
								    		phoneTrue.add(phone);
							    		}
							    		
							    	}
								}
					    		
				    		}
						}
			    		/*record.setUsername(phone);
				    	if (!phoneTo.equals(""))
				    	{
				    		record.setIsdn(phoneTo);
				    		smsContentsDAO.sendMail(record);
				    	}*/
			    	}
			    	else
			    	{
			    		System.out.println("send sdt");
			    		group=group.trim();
				    	Integer count= smsContentsDAO.checkIsdn(group);
				    	if (count!=null && count==0)
				    	{
				    		kt = kt+con+group;
				    		con=",";
				    	}
				    	else
				    	{
				    		int ktnum=1;
				    		for (String ph : phoneTrue) {
								if (ph.equals(group))
								{
									ktnum=0;
									break;
								}
							}
				    		if (ktnum==1)
				    		{
				    			SysUsers user = sysUsersDao.getUserByPhone(group);
					    		if (user!=null && user.getUsername()!=null){
					    			record.setUsername(user.getUsername());
					    		}
					    		else
					    		{
					    			record.setUsername(group);
					    		}
					    		phoneTrue.add(group);
					    		record.setIsdn(group);
					    		smsContentsDAO.sendMail(record);
				    		}
				    		
				    		
				    		
				    	}
				    		
			    	}
			    }
		}
		if (kt.equals(""))
		{
			kt="1";
		}
		Map<String, Object> data = new HashMap<String, Object>();
	 		data.put("value", kt);
		return data;
		
	}
	
	
	
	@RequestMapping(value = "updateEnd", method = RequestMethod.GET)
	public String updateEnd(@RequestParam(required = true) String warningTp,@RequestParam(required = false) String ca,@RequestParam(required = false) String ngayTruc,@RequestParam(required = false) String region,
			HttpServletRequest request, Model model) {

		String username = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		AlShift shift = getCaTrucHT(region);
		boolean checkCaTruc = false;
		
		if (shift != null) {
			String[] user = shift.getNhanCaVien().split(",");
			if (shift.getNhanUsername().equals(username)|| userLogin.getIsRoleManager().equals("Y")) {
				checkCaTruc = true;
			}
			for (String item : user) {
				if (item.equals(username)) {
					checkCaTruc = true;
					break;
				}
			}
			model.addAttribute("checkCaTruc", checkCaTruc);
		}
		if (checkCaTruc == true) {
			/*try {*/
				alAlarmWorksDAO.upateEndTimeAlarm(warningTp,ca,ngayTruc,username);
				saveMessageKey(request, "messsage.confirm.updateuccess");
			/*} catch (Exception e) {
				saveMessageKey(request, "messsage.confirm.deletefailure");
			}*/
		} else {
			saveMessageKey(request, "cautruc.updateFail");
		}
		return "redirect:list.htm?warningTp="+warningTp+"&region="+region;
	}

	@RequestMapping("export")
	public String export(@RequestParam(required = true) String warningtype,
			@RequestParam(required = false) String shiftid,
			@RequestParam(required = false) String ngayTKTo,
			@RequestParam(required = false) String ngayTK,
			@RequestParam(required = false) String type,
			@RequestParam(required = false) String exportFileName,
			@RequestParam(required = false) String region,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if (warningtype==null)
			warningtype = "";
		if (region==null)
			region = "";
		if (shiftid==null)
			shiftid = "";
		if (ngayTKTo==null)
			ngayTKTo = "";
		if (ngayTK==null)
			ngayTK="";
		// temp file
		String basePath = request.getSession().getServletContext().getRealPath("");
		String dataDir = basePath + "/upload";

		String tempName = UUID.randomUUID().toString();

		String ext = "xls";

		String outfile = tempName + "." + ext;
		// return
		// Lay ten file export		
		File tempFile = new File(dataDir + "/" + outfile);
		if (warningtype.equalsIgnoreCase("TEMPERATURE_SITE"))
		{
			List<String> paramList = new ArrayList<String>();
			paramList.add(shiftid);
			paramList.add(ngayTK);
			paramList.add(ngayTKTo);
			paramList.add(type);
			paramList.add(region);
			
			String tableName="AL_MONITOR_TEMPERATURE";
			if (type.equalsIgnoreCase("F")){
				tableName=tableName+"_SHIFT";
			}
			
			List<CTableConfig> columnList = cTableConfigDAO.getColumnList(tableName);
			ExportTools.exportToExcel("PK_AL_SHIFT_MLMN.PR_MONITOR_SITE_SHIFT(?, ?, ?, ?, ?, ?)",paramList,tempFile,exportFileName, "TheoDoiNhietDoSite","THEO DÕI NHIỆT ĐỘ SITE",columnList,"Y");
		}
		else if (warningtype.equalsIgnoreCase("NOI_DUNG"))
		{
			List<String> paramList = new ArrayList<String>();
			paramList.add(shiftid);
			paramList.add(ngayTK);
			paramList.add(ngayTKTo);
			paramList.add(type);
			paramList.add(region);
			
			List<CTableConfig> columnList = cTableConfigDAO.getColumnList("NOI_DUNG_BAN_GIAO_SHIFT");
			ExportTools.exportToExcel("PK_AL_SHIFT_MLMN.PR_ND_BAN_GIAO_AT_SHIFT(?, ?, ?, ?, ?,?)",paramList,tempFile,exportFileName, "NoiDungBanGiaoCa","NỘI DUNG BÀN GIAO TRONG CA",columnList,"Y");
		
		}
		else if (warningtype.equalsIgnoreCase("WOTK_SHIFT"))// conf viec co dinh trong ca
		{
			List<String> paramList = new ArrayList<String>();
			paramList.add("cvcd");
			paramList.add(ngayTKTo);
			paramList.add(ngayTK);
			paramList.add(null);
			paramList.add(null);
			paramList.add(shiftid);
			paramList.add(region);

			List<CTableConfig> columnList = cTableConfigDAO.getColumnList("W_WORKING_AT_SHIFT_CVCD");
			ExportTools.exportToExcel("PK_AL_SHIFT_MLMN.PR_W_WORK_AT_SHIFT_GET(?, ?, ?, ?, ?,?,?,?)",paramList,tempFile,exportFileName, "CongVieCoDinh","DANH SÁCH CÔNG VIỆC CỐ ĐỊNH TRONG CA",columnList,"Y");
		
		}
		else
		{
			
			String tableName="";
			String sheetName="";
			String title="";
			if (warningtype.equals("2G")||warningtype.equals("3G")||warningtype.equals("SU_CO_LON")||warningtype.equals("LOI_TRUYEN_DAN")||warningtype.equals("SU_CO_KHAC"))
			{
				tableName="ALARM_WORK_2G";
				sheetName="ALARM_"+warningtype;
				title="DANH SÁCH ALARM "+warningtype;
			}
			else if (warningtype.equals("PS_CORE")||warningtype.equals("CS_CORE")||warningtype.equals("IPBB"))
			{
				tableName="ALARM_WORK_WARNING";
				sheetName="ALARM_"+warningtype;
				title="DANH SÁCH ALARM "+warningtype;
			}
			else if (warningtype.equals("ALARM_EXTEND"))
			{
				tableName="ALARM_WORK_HCMR";
				sheetName="HieuChinhMoRong";
				title="DANH SÁCH HIỆU CHỈNH MỞ RỘNG";
			}
			
			if (type.equalsIgnoreCase("F"))
			{
				tableName=tableName+"_SHIFT";
			}
			
			List<String> paramList = new ArrayList<String>();
			paramList.add(warningtype);
			paramList.add(shiftid);
			paramList.add(ngayTK);
			paramList.add(ngayTKTo);
			paramList.add(type);
			paramList.add(region);
			List<CTableConfig> columnList = cTableConfigDAO.getColumnList(tableName);
			ExportTools.exportToExcel("PK_AL_SHIFT_MLMN.PR_AL_WORK_GET_AT_SHIFT(?, ?, ?, ?, ?,?,?)",paramList,tempFile,exportFileName, sheetName,title,columnList,"N");
		
		}
		
		
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + exportFileName + "." + ext + "\"");
		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());
		tempFile.delete();
		
		return null;
	}
	
	/*Bao cao chi tieu uctt dung han. AnhCTV:31/03/2016
	 * function= chi-tiet: chi tiet uctt dung han trong ca truc
	 * 		   = su-co-dung-han: Bao cao chi tieu uctt dung han, so luong ung cuu su co dung han
	 * 		   = dieu-hanh-uctt: Tong hop dieu hanh uctt*/
	@RequestMapping(value = "uctt/{function}")
	public ModelAndView list(
			@RequestParam(required = false) String day,
			@RequestParam(required = false) String region,
			@PathVariable String function,
			Model model, HttpServletRequest request) throws ParseException, SQLException {
		
		List<SYS_PARAMETER> sysParemeterTitle = cTableConfigDAO.titleReportUCTT(function,region);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		//Kiem tra dieu kien tim kiem 
		if (day == null || day.equals("")
				||(day!=null && !day.equals("") && DateTools.isValid("dd/MM/yyyy", day)==false))
		{
			day = df_full.format(new Date());
		}
		if (region == null)
		{
			region="";
		}
		
		List<String> paramList = new ArrayList<String>();
		paramList.add(day);
		paramList.add(region);
		paramList.add(function);
		
		String tableName = "UCTT_"+function.toUpperCase().replace("-", "_");
		
		List<CTableConfig> columnList = cTableConfigDAO.getTableConfigsForGrid(tableName);
		String groupHeader="";
		if (function.equals("dieu-hanh-uctt"))
		{
			List<String> columnGrid = cTableConfigDAO.getGroupList(tableName);
			String con2="";
			groupHeader =" var columngroups=[";
			for (String alarmTypeG : columnGrid) {
				groupHeader= groupHeader+		con2+"{ text: '"+alarmTypeG+"', align: 'center', name: '"+alarmTypeG+"' }";
				con2 = ",";
			}
			groupHeader= groupHeader+	"]; ";
		}
		String dataArray = ExportTools.exportToStringArray("PK_MLMN_REPORT_UCTT.PR_REPORT_UCTT(?, ?, ?, ?)",paramList,columnList);
		String grid = HelpTableConfigs.getGridReportDataString(columnList, "grid", dataArray, null, null, "menuJQ", null,null,null,groupHeader);
			
		model.addAttribute("grid", grid);
		model.addAttribute("day", day);
		model.addAttribute("region", region);
		model.addAttribute("function", function);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName= tableName+ dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		return new ModelAndView("jspalarm/catruc/UcttForm");
	}
}
