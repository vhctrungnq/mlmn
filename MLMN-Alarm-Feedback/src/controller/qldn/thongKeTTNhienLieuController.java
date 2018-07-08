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
import dao.QldnThongTinMayNoDAO;
import dao.QldnTramTTNhienLieuDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUserAreaDAO;
import dao.SysUsersDAO;
import vo.CTableConfig;
import vo.QldnPhanBoChiPhi;
import vo.QldnTramTTNhienLieu;
import vo.alarm.utils.HelpTableConfigs;

@Controller
@RequestMapping("/qldn/thanh-toan-nhien-lieu-theo-tinh/*")
public class thongKeTTNhienLieuController extends BaseController {
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired 
	private SysUsersDAO sysUsersDao;
	@Autowired
	private QldnTramTTNhienLieuDAO qldnTramTTNhienLieuDAO;
	
	@Autowired
	private SysUserAreaDAO sysUserAreaDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(
			@RequestParam(required = false) String tinh,
			@RequestParam(required = false) String sdate,
			@RequestParam(required = false) String edate,
			@RequestParam(required = false) String type,
			Model model, HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (tinh != null)
		{
			tinh = tinh.trim();
		}
		else
		{
			tinh="";
		}
		
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName="";
		String tittle ="THỐNG KÊ THANH TOÁN NHIÊN LIỆU THEO TỈNH";
		if (type.equalsIgnoreCase("tinh"))
		{
			//do du lieu ra grid
			List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("QLDN_TK_TT_NHIEN_LIEU_TINH");
		//Lay du lieu cac cot an hien cua grid 
			List<CTableConfig> listSource = cTableConfigDAO.getColumnList("QLDN_TK_TT_NHIEN_LIEU_TINH");
		//url
			String url = "data.htm?sdate="+sdate+"&edate="+edate+"&tinh="+tinh+"&type="+type;
		//Grid
			String gridReport = HelpTableConfigs.getGridPagingUrl(configList, "gridReport", url, "listColumn", listSource, "menuReport", null, "multiplerowsextended","Y");
			model.addAttribute("gridReport", gridReport); 
			
			// Lay ten file export
			exportFileName = "QLDN_TK_TT_NHIEN_LIEU_TINH_"+ dateNow;
			tittle="THỐNG KÊ THANH TOÁN NHIÊN LIỆU THEO TỈNH";
			
		}
		
		if (type.equalsIgnoreCase("nhieuTinh"))
		{
			//do du lieu ra grid
			List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("QLDN_TK_TT_NHIEN_LIEU_NHIEU_TINH");
		//Lay du lieu cac cot an hien cua grid 
			List<CTableConfig> listSource = cTableConfigDAO.getColumnList("QLDN_TK_TT_NHIEN_LIEU_NHIEU_TINH");
		//url
			String url = "data.htm?sdate="+sdate+"&edate="+edate+"&tinh="+tinh+"&type="+type;
		//Grid
			String gridReport = HelpTableConfigs.getGridPagingUrl(configList, "gridReport", url, "listColumn", listSource, "menuReport", null, "multiplerowsextended","Y");
			model.addAttribute("gridReport", gridReport); 
			
			// Lay ten file export
			exportFileName = "QLDN_TK_TT_NHIEN_LIEU_NHIEU_TINH_"+ dateNow;
			tittle="THỐNG KÊ THANH TOÁN NHIÊN LIỆU THEO NHIỀU TỈNH";
			
		}
		if (type.equalsIgnoreCase("dvXHH"))
		{
			//do du lieu ra grid
			List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("QLDN_TK_DON_VI_XHH_VTT");
		//Lay du lieu cac cot an hien cua grid 
			List<CTableConfig> listSource = cTableConfigDAO.getColumnList("QLDN_TK_DON_VI_XHH_VTT");
		//url
			String url = "data.htm?sdate="+sdate+"&edate="+edate+"&tinh="+tinh+"&type="+type;
		//Grid
			String gridReport = HelpTableConfigs.getGridPagingUrl(configList, "gridReport", url, "listColumn", listSource, "menuReport", null, "multiplerowsextended","Y");
			model.addAttribute("gridReport", gridReport); 
			
			// Lay ten file export
			exportFileName = "QLDN_TK_DON_VI_XHH_VTT_"+ dateNow;
			tittle="THỐNG KÊ THEO ĐƠN VỊ XHH/VTT";
			
		}
		String cn="";
		  List<String> provinceList = sysUserAreaDAO.getProvinceList(username);
		  String provinceArray="var provinceList = new Array(";//Danh sach district cho cobobox
		  cn="";
		  for (String dis : provinceList) {
		   provinceArray = provinceArray + cn +"\""+dis+"\"";
		   cn=",";
		  }
		  provinceArray = provinceArray+");";
		  model.addAttribute("provinceList", provinceArray);
		
		model.addAttribute("sdate", sdate);
		model.addAttribute("edate", edate);
		model.addAttribute("type", type);
		model.addAttribute("tinh", tinh);
		model.addAttribute("tittle", tittle);
		
		model.addAttribute("exportFileName", exportFileName);
		return "jspQLDN/tkTtNhienLieuTinh";
		}
	@RequestMapping("data")
	public @ResponseBody 
	void dataSuccessList(
			@RequestParam(required = false) String sdate,
			@RequestParam(required = false) String edate,
			@RequestParam(required = false) String tinh,
			@RequestParam(required = false) String type,
			
			HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
		
		if (sdate!=null && sdate.equals("null"))
		{
			sdate= "";
		}
		if (edate!=null && edate.equals("null"))
		{
			edate= "";
		} 
		if (type.equalsIgnoreCase("tinh"))
		{
			List<QldnTramTTNhienLieu> dataList = new ArrayList<QldnTramTTNhienLieu>();
			dataList = qldnTramTTNhienLieuDAO.getTkTtNhienLieuTinhListAll(sdate,edate,tinh);
			Gson gs = new Gson();
			String jsonCartList = gs.toJson(dataList);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.println(jsonCartList);
			out.close();
		}
		if (type.equalsIgnoreCase("nhieuTinh"))
		{
			List<QldnTramTTNhienLieu> dataList = new ArrayList<QldnTramTTNhienLieu>();
			dataList = qldnTramTTNhienLieuDAO.getTkTtNhienLieuNhieuTinhListAll(sdate,edate,tinh);
			Gson gs = new Gson();
			String jsonCartList = gs.toJson(dataList);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.println(jsonCartList);
			out.close();
		}
		if (type.equalsIgnoreCase("dvXHH"))
		{
			List<QldnTramTTNhienLieu> dataList = new ArrayList<QldnTramTTNhienLieu>();
			dataList = qldnTramTTNhienLieuDAO.getTkTheoDonViXHHVTTListAll(sdate,edate,tinh);
			Gson gs = new Gson();
			String jsonCartList = gs.toJson(dataList);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.println(jsonCartList);
			out.close();
		}
	 }	
}
