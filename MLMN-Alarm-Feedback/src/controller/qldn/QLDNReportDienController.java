package controller.qldn;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import controller.BaseController;
import dao.CTableConfigDAO;
import dao.QldnThongTinTramDAO;
import dao.QldnTramTTDienDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUserAreaDAO;
import dao.SysUsersDAO;
import vo.AlShift;
import vo.CTableConfig;
import vo.QLDNReportTTTram;
import vo.QldnThongTinTram;
import vo.QldnTramTTDien;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;

@Controller
@RequestMapping("/dien/report/*")
public class QLDNReportDienController extends BaseController {
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
	private SysUserAreaDAO sysUserAreaDAO;
	
	@Autowired 
	private SysUsersDAO sysUsersDao;
	@Autowired
	private QldnTramTTDienDAO qldnTramTTDienDao;
	
	 @Autowired
	    private QldnThongTinTramDAO qldnThongTinTramDAO;
	 
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping(value = "thong-tin-tram", method = RequestMethod.GET) 
	public ModelAndView list(@RequestParam(required = false) String tgttTq,
			@RequestParam(required = false) Integer thangQuyTt,
			@RequestParam(required = false) Integer namTt,
			Model model, HttpServletRequest request) {
		
		
		String tableName="QLDN_DASHBOARD";
		// REPORT PROVICE
		String type="PROVINCE";
		tableName="QLDN_DASHBOARD_PROVINCE";
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid(tableName);
		//url
		String url = "dataThongTinTram.htm?type="+type;
		//Grid
		String gridPROVINCE = HelpTableConfigs.getGridPagingReportUrl(configList, "grid"+type, url, null, null, "Menu"+type, null,"singlecell",null,"Y");
		model.addAttribute("gridPROVINCE", gridPROVINCE);	
		
		// REPORT HTTT (HINH THUC THANH TOAN)
		type="HTTT";
		tableName="QLDN_DASHBOARD_HTTT";
		//do du lieu ra grid
		configList = cTableConfigDAO.getTableConfigsForGrid(tableName);
		//url
		url = "dataThongTinTram.htm?type="+type;
		//Grid
		String gridHTTT = HelpTableConfigs.getGridPagingReportUrl(configList, "grid"+type, url, null, null, "Menu"+type, null,"singlecell",null,"Y");
		model.addAttribute("gridHTTT", gridHTTT);	
		
		// REPORT NGUON CUNG CAP DIEN
		type="NGUONCCD";
		tableName="QLDN_DASHBOARD_NGUONCCD";
		//do du lieu ra grid
		configList = cTableConfigDAO.getTableConfigsForGrid(tableName);
		//url
		url = "dataThongTinTram.htm?type="+type;
		//Grid
		String gridNGUONCCD = HelpTableConfigs.getGridPagingReportUrl(configList, "grid"+type, url, null, null, "Menu"+type, null,"singlecell",null,"Y");
		model.addAttribute("gridNGUONCCD", gridNGUONCCD);	
		
		// REPORT NGUOI THANH TOAN
		type="NGUOITTDIEN";
		tableName="QLDN_DASHBOARD_NGUOITTDIEN";
		//do du lieu ra grid
		configList = cTableConfigDAO.getTableConfigsForGrid(tableName);
		//url
		url = "dataThongTinTram.htm?type="+type;
		//Grid
		String gridNGUOITTDIEN = HelpTableConfigs.getGridPagingReportUrl(configList, "grid"+type, url, null, null, "Menu"+type, null,"singlecell",null,"Y");
		model.addAttribute("gridNGUOITTDIEN", gridNGUOITTDIEN);	
		
		//REPORT LOAI DON GIA
		type="DG_LOAI";
		tableName="QLDN_DASHBOARD_DG_LOAI";
		//do du lieu ra grid
		configList = cTableConfigDAO.getTableConfigsForGrid(tableName);
		//url
		url = "dataThongTinTram.htm?type="+type;
		//Grid
		String gridDG_LOAI =HelpTableConfigs.getGridPagingReportUrl(configList, "grid"+type, url, null, null, "Menu"+type, null,"singlecell",null,"Y");
		model.addAttribute("gridDG_LOAI", gridDG_LOAI);	
		
		//REPORT THOI GIAN TT
		type="TGTT_TQ";
		tableName="QLDN_DASHBOARD_TGTT_TQ";
		//do du lieu ra grid
		configList = cTableConfigDAO.getTableConfigsForGrid(tableName);
		//url
		url = "dataThongTinTram.htm?type="+type;
		//Grid
		String gridTGTT_TQ = HelpTableConfigs.getGridPagingReportUrl(configList, "grid"+type, url, null, null, "Menu"+type, null,"singlecell",null,"Y");
		model.addAttribute("gridTGTT_TQ", gridTGTT_TQ);	
		
		// REPORT LOAI TRAM
		type="LOAITRAM";
		tableName="QLDN_DASHBOARD_LOAITRAM";
		//do du lieu ra grid
		configList = cTableConfigDAO.getTableConfigsForGrid(tableName);
		//url
		url = "dataThongTinTram.htm?type="+type;
		//Grid
		String gridLOAITRAM= HelpTableConfigs.getGridPagingReportUrl(configList, "grid"+type, url, null, null, "Menu"+type, null,"singlecell",null,"Y");
		model.addAttribute("gridLOAITRAM", gridLOAITRAM);	
		
		//THONG KE THEO TINH VA NHA CUNG CAP
		type="TINH_NHACC";
		tableName="QLDN_DASHBOARD_TINH_NHACC";
		//do du lieu ra grid
		configList = cTableConfigDAO.getTableConfigsForGrid(tableName);
		//lay du lieu column group cua grid
		String groupColumn="";
		List<String> columnGrid = cTableConfigDAO.getGroupList(tableName);
			String con2="";
			groupColumn =" var columngroups=[";
			for (String alarmTypeG : columnGrid) {
				groupColumn= groupColumn+		con2+"{ text: '"+alarmTypeG+"', align: 'center', name: '"+alarmTypeG+"' }";
				con2 = ",";
			}
			groupColumn= groupColumn+	"]; ";
		model.addAttribute("columngroups", groupColumn); 
		//url
		url = "dataThongTinTram.htm?type="+type;
		//Grid
		String gridTINH_NHACC= HelpTableConfigs.getGridPagingReportUrl(configList, "grid"+type, url, null, null, "Menu"+type, null,"singlecell",groupColumn,"Y");
		model.addAttribute("gridTINH_NHACC", gridTINH_NHACC);	
			
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		model.addAttribute("dateNow", dateNow);
		
		return new ModelAndView("jspQLDN/reportDien/thongtintram");
	}

	@RequestMapping("dataThongTinTram")
	public @ResponseBody 
	void dataSuccessList(
			@RequestParam(required = true) String type,
			HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
		
		if (type.equalsIgnoreCase("TINH_NHACC")) {
			List<QLDNReportTTTram> dataList = new ArrayList<QLDNReportTTTram>();
			dataList = qldnTramTTDienDao.getReportTTTramTinhNhaCC();
			Gson gs = new Gson();
			String jsonCartList = gs.toJson(dataList);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.println(jsonCartList);
			out.close();
		}
		else
		{
			List<QLDNReportTTTram> dataList = new ArrayList<QLDNReportTTTram>();
			dataList = qldnTramTTDienDao.getReportTTTram(type);
			Gson gs = new Gson();
			String jsonCartList = gs.toJson(dataList);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.println(jsonCartList);
			out.close();
		}
	 }
	
	/*tao doi tuong config table*/
	public CTableConfig createConfig(String dataField,String columnName,String tableColumn,String dataType,String columnType,String filterType,String style,Integer ordering) {
		CTableConfig table = new CTableConfig();
		table.setDataField(dataField);
		table.setColumnName(columnName);
		table.setTableColumn(tableColumn);
		table.setDataType(dataType);
		table.setColumnType(columnType);
		table.setFilterType(filterType);
		table.setStyle(style);
		table.setOrdering(ordering);
		return table;
	}
	/*bao cao số lượng trạm chưa thanh toán tiền điện  theo tháng
	Thống kê chi phí điện theo tháng
	Theo dõi thanh toán điện theo tháng
	@type: CHUA_TT,DA_TT,CHI_PHI_TT,CHENH_LECH_TRAM*/
	@RequestMapping(value = "thanh-toan-dien", method = RequestMethod.GET) 
	public ModelAndView list(
			@RequestParam(required = false) String region,
			@RequestParam(required = false) String province,
			@RequestParam(required = false) String tgttTq,
			@RequestParam(required = false) Integer thangQuyTtF,
			@RequestParam(required = false) Integer namTtF,
			@RequestParam(required = false) Integer thangQuyTtTo,
			@RequestParam(required = false) Integer namTtTo,
			@RequestParam(required = false) String type,
			Model model, HttpServletRequest request) {
		
		List<SYS_PARAMETER> sysParemeterTitle = sysParameterDao.gettitleReportQLDN("RP_THANH_TOAN_DIEN",type);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		
		if (thangQuyTtF==null)
		{
			thangQuyTtF=1;
		}
		if (namTtF==null)
		{
			namTtF=cal.get(Calendar.YEAR);
		}
		if (thangQuyTtTo==null)
		{
			thangQuyTtTo=cal.get(Calendar.MONTH);
		}
		if (namTtTo==null)
		{
			namTtTo=cal.get(Calendar.YEAR);
		}
		if (tgttTq!=null&&!tgttTq.equals("null"))
		{
			tgttTq=tgttTq.trim();
		}
		else
		{
			tgttTq="Tháng";
		}
		if (region!=null&&!region.equals("null"))
		{
			region=region.trim();
		}
		else
		{
			region="";
		}
		if (province!=null&&!province.equals("null"))
		{
			province=province.trim();
		}
		else
		{
			province="";
		}
		
		
		String tableName="QLDN_RP_THANH_TOAN_DIEN";
		if (type!=null&&!type.equals("")) {
			tableName=tableName+"_"+type;
		}
		//do du lieu ra grid
		List<CTableConfig> configList = new ArrayList<>();
		String highlight="";
		if (type!=null &&!type.equals("DA_TT")&&!type.equals("CHENH_LECH_TRAM"))
		{
			CTableConfig ctinh = createConfig("TINH","Tỉnh","TINH","string","textbox","textbox","align: 'center',width:100",1);
			configList.add(ctinh);
			int startY = namTtF;
			int endY= namTtTo;
			int startM = thangQuyTtF;
			int endM= thangQuyTtTo;
			while ((startY<endY)||(startY==endY&&startM<=endM))
			{
				CTableConfig datafield= createConfig("T"+startM+"_"+startY,"T"+startM+"/"+startY,"T"+startM+"_"+startY,"number","textbox","textbox","align: 'center'",1);
				configList.add(datafield);
				if (startM==12)
				{   
					startM=1;
					startY++;
				}
				else
					startM++;
			}
			CTableConfig datafield= createConfig("TONG","Tổng","TONG","number","textbox","textbox","align: 'center'",1);
			configList.add(datafield);
		}
		else if (type!=null &&type.equals("CHENH_LECH_TRAM"))
		{
			CTableConfig ctinh = createConfig("ID_TRAM","Trạm","ID_TRAM","string","textbox","textbox","align: 'center', cellclassname: cellclass,width:100",1);
			configList.add(ctinh);
			int startY = namTtF;
			int endY= namTtTo;
			int startM = thangQuyTtF;
			int endM= thangQuyTtTo;
			//int count=0;
			while ((startY<endY)||(startY==endY&&startM<=endM))
			{
				CTableConfig datafield= createConfig("T"+startM+"_"+startY,"T"+startM+"/"+startY,"T"+startM+"_"+startY,"number","textbox","textbox","align: 'center'",1);
				configList.add(datafield);
				if (startM==12)
				{   
					startM=1;
					startY++;
				}
				else
					startM++;
			}
			CTableConfig datafield= createConfig("CLASS_VALUE","CLASS_VALUE","CLASS_VALUE","number","textbox","textbox","align: 'center',hidden: true",1);
			configList.add(datafield);
			
			highlight="var cellclass = function (row, columnfield, value) {";
			highlight+="var MIN_VALUE = $('#gridReport').jqxGrid('getrowdata', row).CLASS_VALUE;";
			highlight+="if (MIN_VALUE == 1) {return 'red';}";
			highlight+=" if (MIN_VALUE == 2) {return 'yellow';} ";
			highlight+=" if (MIN_VALUE == 3) {return 'green';} ";
			highlight+="};";
		}
		else
		{
			CTableConfig ctinh = createConfig("TINH","Tỉnh","TINH","string","textbox","textbox","align: 'center',width:100",1);
			configList.add(ctinh);
			int startY = namTtF;
			int endY= namTtTo;
			int startM = thangQuyTtF;
			int endM= thangQuyTtTo;
			while ((startY<endY)||(startY==endY&&startM<=endM))
			{
				CTableConfig datafield= createConfig("T"+startM+"_"+startY+"_SOLUONG","Tổng trạm TT "+startM+"/"+startY,"T"+startM+"_"+startY+"_SOLUONG","String","textbox","textbox","align: 'center'",1);
				configList.add(datafield);
				CTableConfig datafield2= createConfig("T"+startM+"_"+startY+"_TIENDIENTB","TB tháng/Trạm "+startM+"/"+startY,"T"+startM+"_"+startY+"_TIENDIENTB","String","textbox","textbox","align: 'center'",1);
				configList.add(datafield2);
				if (startM==12)
				{   
					startM=1;
					startY++;
				}
				else
					startM++;
			}
		}
		//Lay duong link url de lay du lieu do vao grid
	 	List<String> dataList = qldnTramTTDienDao.getReportThanhToanDien(type,region,province,tgttTq, thangQuyTtF.toString(),namTtF.toString(),thangQuyTtTo.toString(),namTtTo.toString());	
		//Grid
		String gridReport = HelpTableConfigs.getGridReportData(configList, "gridReport", dataList, null, null, "menuReport", highlight,null);
		model.addAttribute("gridReport", gridReport);	
		
		model.addAttribute("type", type);
		model.addAttribute("region", region);
		model.addAttribute("province", province);
		model.addAttribute("tgttTq", tgttTq);
		model.addAttribute("thangQuyTtF", thangQuyTtF);
		model.addAttribute("namTtF", namTtF);
		model.addAttribute("thangQuyTtTo", thangQuyTtTo);
		model.addAttribute("namTtTo", namTtTo);
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		model.addAttribute("dateNow", dateNow);
		
		return new ModelAndView("jspQLDN/reportDien/ttdien");
	}
	
	/*bao cao tieu thu dien nang CHENH_LECH_TRAM*/
	@RequestMapping(value = "chenh-lech_tram", method = RequestMethod.GET) 
	public ModelAndView chenhLechTram(
			/*@RequestParam(required = false) String region,
			@RequestParam(required = false) String province,*/
			@RequestParam(required = false) String daiVT,
			@RequestParam(required = false) String tgttTq,
			@RequestParam(required = false) Integer thangQuyTtF,
			@RequestParam(required = false) Integer namTtF,
			@RequestParam(required = false) Integer thangQuyTtTo,
			@RequestParam(required = false) Integer namTtTo,
			/*@RequestParam(required = false) String k1,
			@RequestParam(required = false) String k2,
			@RequestParam(required = false) String k3,*/
			Model model, HttpServletRequest request) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		SysUsers userLogin = sysUsersDao.selectByUsename(username);
		List<SYS_PARAMETER> sysParemeterTitle = sysParameterDao.gettitleReportQLDN("RP_THANH_TOAN_DIEN","CHENH_LECH_TRAM");
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		if (thangQuyTtTo==null)
		{
			thangQuyTtTo=cal.get(Calendar.MONTH);
			if (thangQuyTtTo==0) thangQuyTtTo=1;
		}
		if (namTtTo==null)
		{
			namTtTo=cal.get(Calendar.YEAR);
		}
		int monCunrent = cal.get(Calendar.MONTH);
		if (monCunrent>=7)
		{
			if (thangQuyTtF==null)
			{
				thangQuyTtF=1;
			}
			if (namTtF==null)
			{
				namTtF=cal.get(Calendar.YEAR);
			}
			
		}
		else
		{
			if (namTtF==null)
			{
				namTtF=cal.get(Calendar.YEAR)-1;
			}
			if (thangQuyTtF==null)
			{
				cal.add(Calendar.MONTH,-7);
				thangQuyTtF=cal.get(Calendar.MONTH);
				if (thangQuyTtF==0) thangQuyTtF=1;
			}
			
		}
		
		if (tgttTq!=null&&!tgttTq.equals("null"))
		{
			tgttTq=tgttTq.trim();
		}
		else
		{
			tgttTq="Tháng";
		}
		if (daiVT!=null&&!daiVT.equals("null"))
		{
			if (userLogin.getIsRoleManager().equalsIgnoreCase("N"))
			{
				daiVT=daiVT.trim();
			}
			else
				daiVT="";
		}
		else
		{
			daiVT=userLogin.getMaPhong();
		}
		
		/*if (k1!=null&&!k1.equals("null"))
		{
			k1=k1.trim();
		}
		else
		{
			k1="2";
		}
		if (k2!=null&&!k2.equals("null"))
		{
			k2=k2.trim();
		}
		else
		{
			k2="2";
		}
		if (k3!=null&&!k3.equals("null"))
		{
			k3=k3.trim();
		}
		else
		{
			k3="0.5";
		}*/
		String cn="";
		List<String> daiVTList = sysUserAreaDAO.getDaiVTArrayList(username);
		String daiVTArray="var daiVTList = new Array(";//Danh sach district cho cobobox
		cn="";
		for (String dis : daiVTList) {
			daiVTArray = daiVTArray + cn +"\""+dis+"\"";
			cn=",";
		}
		daiVTArray = daiVTArray+");";
		model.addAttribute("daiVTList", daiVTArray);
		String tableName="QLDN_RP_THANH_TOAN_DIEN_CHENH_LECH_TRAM";
		
		//do du lieu ra grid
		List<CTableConfig> configList = new ArrayList<>();
		String highlight="";
		CTableConfig ctinh = createConfig("ID_TRAM","Trạm","ID_TRAM","string","textbox","textbox","align: 'center', cellclassname: cellclass,width:100",1);
			configList.add(ctinh);
			int startY = namTtF;
			int endY= namTtTo;
			int startM = thangQuyTtF;
			int endM= thangQuyTtTo;
			//int count=0;
			while ((startY<endY)||(startY==endY&&startM<=endM))
			{
				CTableConfig datafield= createConfig("T"+startM+"_"+startY,"T"+startM+"/"+startY,"T"+startM+"_"+startY,"number","textbox","textbox","align: 'center'",1);
				configList.add(datafield);
				if (startM==12)
				{   
					startM=1;
					startY++;
				}
				else
					startM++;
			}
			CTableConfig datafield= createConfig("CLASS_VALUE","CLASS_VALUE","CLASS_VALUE","number","textbox","textbox","align: 'center',hidden: true",1);
			configList.add(datafield);
			
			highlight="var cellclass = function (row, columnfield, value) {";
			highlight+="var MIN_VALUE = $('#gridReport').jqxGrid('getrowdata', row).CLASS_VALUE;";
			highlight+="if (MIN_VALUE == 1) {return 'red';}";
			highlight+=" if (MIN_VALUE == 2) {return 'yellow';} ";
			highlight+=" if (MIN_VALUE == 3) {return 'green';} ";
			highlight+="};";
		
		//Lay duong link url de lay du lieu do vao grid
	 	List<String> dataList = qldnTramTTDienDao.getReportChenhLechTram(daiVT,tgttTq, thangQuyTtF.toString(),namTtF.toString(),thangQuyTtTo.toString(),namTtTo.toString());	
		//Grid
		String gridReport = HelpTableConfigs.getGridReportData(configList, "gridReport", dataList, null, null, "menuReport", highlight,null);
		model.addAttribute("gridReport", gridReport);	
		
		/*model.addAttribute("k1", k1);
		model.addAttribute("k2", k2);
		model.addAttribute("k3", k3);*/
		model.addAttribute("daiVT", daiVT);
		model.addAttribute("tgttTq", tgttTq);
		model.addAttribute("thangQuyTtF", thangQuyTtF);
		model.addAttribute("namTtF", namTtF);
		model.addAttribute("thangQuyTtTo", thangQuyTtTo);
		model.addAttribute("namTtTo", namTtTo);
		model.addAttribute("isRolaManager", userLogin.getIsRoleManager());
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		model.addAttribute("dateNow", dateNow);
		
		return new ModelAndView("jspQLDN/reportDien/chenhLechTram");
	}
	
	/*Thống kê từng tháng số tiền thanh toán so với tháng trước
	Thống kê từng tháng điện năng tiêu thụ so với định mức điện
	@type: THANG,DINH_MUC*/
	@RequestMapping(value = "checnh-lech-thang", method = RequestMethod.GET) 
	public ModelAndView reportThang(
			@RequestParam(required = false) String region,
			@RequestParam(required = false) String province,
			@RequestParam(required = false) String tgttTq,
			@RequestParam(required = false) Integer thangQuy,
			@RequestParam(required = false) Integer nam,
			@RequestParam(required = false) String tyLe,
			@RequestParam(required = false) String type,
			Model model, HttpServletRequest request) {
		
		List<SYS_PARAMETER> sysParemeterTitle = sysParameterDao.gettitleReportQLDN("RP_THANH_TOAN_DIEN_CHENH_LECH",type);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		
		if (thangQuy==null)
		{
			thangQuy=cal.get(Calendar.MONTH);
		}
		if (nam==null)
		{
			nam=cal.get(Calendar.YEAR);
		}
		if (tgttTq!=null&&!tgttTq.equals("null"))
		{
			tgttTq=tgttTq.trim();
		}
		else
		{
			tgttTq="Tháng";
		}
		if (region!=null&&!region.equals("null"))
		{
			region=region.trim();
		}
		else
		{
			region="";
		}
		if (province!=null&&!province.equals("null"))
		{
			province=province.trim();
		}
		else
		{
			province="";
		}
		if (tyLe!=null&&!tyLe.equals("null"))
		{
			tyLe=tyLe.trim();
		}
		else
		{
			tyLe="";
		}
		
		
		String tableName="QLDN_DASHBOARD_PROVINCE";
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid(tableName);
		//url
		String url = "dataChenhLechDien.htm?region="+region+"&province="+province+"&tgttTq="+tgttTq+"&thangQuy="+thangQuy+"&nam="+nam+"&tyLe="+tyLe+"&type="+type;
		//Grid
		String grid = HelpTableConfigs.getGridPagingReportUrl(configList, "grid", url, null, null, "Menu", null,"singlecell",null,"Y");
		model.addAttribute("grid", grid);	
		
		model.addAttribute("type", type);
		model.addAttribute("region", region);
		model.addAttribute("province", province);
		model.addAttribute("tgttTq", tgttTq);
		model.addAttribute("thangQuy", thangQuy);
		model.addAttribute("nam", nam);
		model.addAttribute("tyLe", tyLe);
		
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = tableName+type+ dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		return new ModelAndView("jspQLDN/reportDien/chenhLechDien");
	}
	
	@RequestMapping("dataChenhLechDien")
	public @ResponseBody 
	void dataChenhLechDien(@RequestParam(required = false) String region,
			@RequestParam(required = false) String province,
			@RequestParam(required = false) String tgttTq,
			@RequestParam(required = false) String thangQuy,
			@RequestParam(required = false) String nam,
			@RequestParam(required = false) String tyLe,
			@RequestParam(required = false) String type,
			HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
		
		List<QldnTramTTDien> dataList = new ArrayList<QldnTramTTDien>();
		dataList = qldnTramTTDienDao.getChenhLechDienList(region,province,tgttTq,thangQuy,nam,tyLe,type);
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(dataList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
	 }
	
	@RequestMapping("exportChenhLech")
	public ModelAndView exportChenhLech(@RequestParam(required = false) String region,
			@RequestParam(required = false) String province,
			@RequestParam(required = false) String tgttTq,
			@RequestParam(required = false) String thangQuy,
			@RequestParam(required = false) String nam,
			@RequestParam(required = false) String tyLe,
			@RequestParam(required = false) String type, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		try {
			// B1: lay du lieu
			List<QldnTramTTDien> dataList = new ArrayList<QldnTramTTDien>();
			dataList = qldnTramTTDienDao.getChenhLechDienDetail(region,province,tgttTq,thangQuy,nam,tyLe,type);
			List<Object> dataObjs = new ArrayList<Object>();
			for (QldnTramTTDien record : dataList) {
				dataObjs.add(record);
			}
			// B2 thuc hien export
			String tableName="QLDN_CHI_TIET_CHENH_LECH_DIEN";
			if (type!=null&&!type.equals(""))
			{
				tableName= tableName+"_"+type.toUpperCase();
			}
			List<CTableConfig> headers = cTableConfigDAO.getColumnList(tableName);
			ExportTools.exportFileExcel("QldnTramTTDien", dataObjs, headers, request, response);

		} catch (Exception exp) {
			exp.printStackTrace();
		}

		return null;

	}
	
	// DoaiNH viết quản lí thông tin trạm ngày 29-09-2017
	 @RequestMapping("thanh-toan-nhieu-ky")
	    public ModelAndView listQuanLiThongTinTram(
		    @RequestParam(required = false) String tinh,
		    @RequestParam(required = false) String loaitram,
		    @RequestParam(required = false) String nguonccd,
		    @RequestParam(required = false) String httt,
		    @RequestParam(required = false) String nguoittdien,
		    @RequestParam(required = false) String dgLoai,
		    @RequestParam(required = false) String makh,
		    @RequestParam(required = false) String idTram,
		    @RequestParam(required = false) String thangquy,
		    @RequestParam(required = false) String nam,
		    @RequestParam(required = false) String thangquy_t,
		    @RequestParam(required = false) String nam_t,
		    Model model, HttpServletRequest request, HttpServletResponse response){
		
		
		model.addAttribute("tinh", tinh);
		model.addAttribute("loaitram", loaitram);
		model.addAttribute("nguonccd", nguonccd);
		model.addAttribute("httt", httt);
		model.addAttribute("nguoittdien", nguoittdien);  
		model.addAttribute("dgLoai", dgLoai); 
		model.addAttribute("makh", makh); 
		model.addAttribute("idTram", idTram); 
		model.addAttribute("thangquy", thangquy); 
		model.addAttribute("nam", nam); 
		model.addAttribute("thangquy_t", thangquy_t); 
		model.addAttribute("nam_t", nam_t); 
		
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "QLDN_THÔNG_TIN_TRẠM"+ dateNow;
		model.addAttribute("exportFileName", exportFileName); 
		  
		return new ModelAndView("jspQLDN/reportDien/thanhToanNhieuKy");
		
		
	    }
	    @RequestMapping("dataTTNhieuKy")
	    public void dataTTNhieuKy( @RequestParam(required = false) String tinh,
		    @RequestParam(required = false) String loaitram,
		    @RequestParam(required = false) String nguonccd,
		    @RequestParam(required = false) String httt,
		    @RequestParam(required = false) String nguoittdien,
		    @RequestParam(required = false) String dgLoai,
		    @RequestParam(required = false) String makh,
		    @RequestParam(required = false) String idTram,
		    @RequestParam(required = false) String thangquy,
		    @RequestParam(required = false) String nam,
		    @RequestParam(required = false) String thangquy_t,
		    @RequestParam(required = false) String nam_t,
		    Model model, HttpServletRequest request, HttpServletResponse response)
		    throws IOException {
	    	if(tinh == null) {
	    	    tinh = "";
	    	}
	    	if(loaitram == null ) {
	    	    loaitram = "";
	    	}
	    	if(nguonccd == null) {
	    	    nguonccd = "";
	    	}
	    	if(httt == null ) {
	    	    httt = "";
	    	}
	    	if(nguoittdien == null) {
	    	    nguoittdien = "";
	    	}
	    	if(dgLoai == null) {
	    	    dgLoai = "";
	    	}
	    	if(makh == null) {
	    	    makh = "";
	    	}
	    	if(idTram == null) {
	    	    idTram = "";
	    	}
	    	if (thangquy == null) {
	    	    thangquy = "";
	    	}
	    	if (nam == null) {
	    	    nam = "";
	    	}
	    	if (thangquy_t == null) {
	    	    thangquy_t = "";
	    	}
	    	if (nam_t == null) {
	    	    nam_t = "";
	    	}
		List<QldnThongTinTram> dataList = new ArrayList<QldnThongTinTram>();
		dataList =qldnThongTinTramDAO.getTTNhieuKyList(tinh, loaitram, nguonccd, httt, nguoittdien, dgLoai, makh, idTram, thangquy, nam,thangquy_t,nam_t);
		Gson gs = new Gson();
		String jsonDataList = gs.toJson(dataList);
		PrintWriter out;
		out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonDataList);
		out.close();
	    }
	   @RequestMapping("exportData_Export_ThongTin_Tram")
	    public ModelAndView exportData(@RequestParam(required = false) String tinh,
		    @RequestParam(required = false) String loaitram,
		    @RequestParam(required = false) String nguonccd,
		    @RequestParam(required = false) String httt,
		    @RequestParam(required = false) String nguoittdien,
		    @RequestParam(required = false) String dgLoai,
		    @RequestParam(required = false) String makh,
		    @RequestParam(required = false) String idTram,
		    @RequestParam(required = false) String thangquy,
		    @RequestParam(required = false) String nam,
		    @RequestParam(required = false) String thangquy_t,
		    @RequestParam(required = false) String nam_t, HttpServletRequest request, HttpServletResponse response)
	      throws Exception {
	     
	    try {
	      // check username
	      
	      // B1: lay du lieu
	      List<QldnThongTinTram> dataList = new ArrayList<QldnThongTinTram>();
	      dataList = qldnThongTinTramDAO.getTTNhieuKyList(tinh, loaitram, nguonccd, httt, nguoittdien, dgLoai, makh, idTram, thangquy, nam,thangquy_t,nam_t);
	      List<Object> dataObjs = new ArrayList<Object>();
	      for (QldnThongTinTram record : dataList) {
	       dataObjs.add(record);
	      }
	      // B2 thuc hien export
	      
	     List<CTableConfig> headers = new ArrayList<CTableConfig>() ;
	     headers = cTableConfigDAO.getColumnList("QLDN_THONG_TIN_TRAM_EXPORT");
	      ExportTools.exportFileExcel("QldnThongTinTram", dataObjs, headers, request, response);

	    } catch (Exception exp) {
	      exp.printStackTrace();
	     }

	     return null;

	    }
	   @RequestMapping("daiVTXuLy")
	 	public @ResponseBody
	 		String ondaiVTXuLy(@RequestParam (required = true) String idTram,
	 				//@RequestParam (required = true) String tgttTq,
	 				@RequestParam (required = true) String thangQuyTt,
	 				@RequestParam (required = true) String namTt,
	 				@RequestParam (required = true) String nguyenNhan,
			@RequestParam (required = true) String kqToKt,
			HttpServletRequest request, Model model) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		/*try {*/
				//xac nhan nhung canh bao chua ket thuc
			qldnTramTTDienDao.updateDVTXuLy(idTram,"Tháng",thangQuyTt,namTt,nguyenNhan,kqToKt);	
			/*String[] idArray = idTram.split(",");
				for (String tram : idArray) {
					System.out.println("IDtram=......."+tram);
					if (!tram.equals("")){
						
					}
				}*/
		/*}
		catch (Exception e) {
			return "0";
		}*/
		return "1";
	}

	   
}
