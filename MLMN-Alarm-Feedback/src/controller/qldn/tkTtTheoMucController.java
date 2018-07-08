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

import com.google.gson.Gson;

import controller.BaseController;
import dao.CTableConfigDAO;
import dao.QldnTramTTDienDAO;
import dao.QldnTramTTNhienLieuDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUserAreaDAO;
import dao.SysUsersDAO;
import vo.CTableConfig;
import vo.QldnTramTTDien;
import vo.QldnTramTTNhienLieu;
import vo.alarm.utils.HelpTableConfigs;

@Controller
@RequestMapping("/qldn/thong-ke-thanh-toan-theo-muc/*")
public class tkTtTheoMucController extends BaseController {
	
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired 
	private SysUsersDAO sysUsersDao;
	@Autowired
	private QldnTramTTNhienLieuDAO qldnTramTTNhienLieuDAO;
	@Autowired
	private QldnTramTTDienDAO qldnTramTTDienDAO;
	@Autowired
	private SysUserAreaDAO sysUserAreaDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(
			@RequestParam(required = false) String tinh,
			@RequestParam(required = false) Integer month,
			@RequestParam(required = false) Integer year,
			@RequestParam(required = false) Integer dinhmuc1,
			@RequestParam(required = false) Integer dinhmuc2,
			@RequestParam(required = false) String type,
			Model model, HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		
		if (month==null)
		{
			month=cal.get(Calendar.MONTH);
		}
		if (year==null)
		{
			year=cal.get(Calendar.YEAR);
		}
	
		
		if (tinh != null)
		{
			tinh = tinh.trim();
		}
		else
		{
			tinh="";
		}
		if (dinhmuc1 == null) dinhmuc1=100;
		if (dinhmuc2 == null) dinhmuc2=200;
		
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName="";
		String tittle ="THỐNG KÊ SỐ TIỀN THANH TOÁN ĐIỆN NĂNG THEO TỪNG MỨC";
		
		List<CTableConfig> configList = new ArrayList<>();
		  CTableConfig ctinh = createConfig("tinh","Tỉnh","TINH","string","textbox","textbox","align: 'center'",1);
		  configList.add(ctinh);
		  CTableConfig cdm1 = createConfig("dinhMuc1","< "+dinhmuc1,"DINH_MUC_1","number","numberinput","textbox","align: 'center',width:200",1);
		  configList.add(cdm1);
		  CTableConfig ctldm1 = createConfig("tlDM1","Tỷ lệ < "+dinhmuc1,"TL_DM_1","number","numberinput","textbox","align: 'center',width:200",1);
		  configList.add(ctldm1);
		  CTableConfig cdm2 = createConfig("dinhMuc2",""+dinhmuc1+" - "+dinhmuc2,"DINH_MUC_2","number","numberinput","textbox","align: 'center',width:200",1);
		  configList.add(cdm2);
		  CTableConfig ctldm2 = createConfig("tlDM2","Tỷ lệ "+dinhmuc1+" - "+dinhmuc2,"TL_DM_2","number","numberinput","textbox","align: 'center',width:200",1);
		  configList.add(ctldm2);
		  CTableConfig cdm3 = createConfig("dinhMuc3","> "+dinhmuc2,"DINH_MUC_3","number","numberinput","textbox","align: 'center',width:200",1);
		  configList.add(cdm3);
		  CTableConfig ctldm3 = createConfig("tlDM3","Tỷ lệ > "+dinhmuc2,"TL_DM_3","number","numberinput","textbox","align: 'center',width:200",1);
		  configList.add(ctldm3);
		
		//url
			String url = "data.htm?month="+month+"&year="+year+"&tinh="+tinh+"&dinhmuc1="+dinhmuc1+"&dinhmuc2="+dinhmuc2+"&type="+type;
		//Grid
			String gridReport = HelpTableConfigs.getGridPagingUrl(configList, "gridReport", url, null, null, "menuReport", null, "multiplerowsextended","Y");
			model.addAttribute("gridReport", gridReport); 
			
			// Lay ten file export
			
			if (type.equalsIgnoreCase("dien"))
			{
				exportFileName = "THONG_KE_SO_TIEN_THANH_TOAN_DIEN_THEO_MUC_"+ dateNow;
			tittle="THỐNG KÊ SỐ TIỀN THANH TOÁN ĐIỆN NĂNG THEO TỪNG MỨC";
			}
			if (type.equalsIgnoreCase("nhienLieu"))
			{
				exportFileName = "THONG_KE_SO_TIEN_THANH_TOAN_NHIEN_LIEU_THEO_MUC_"+ dateNow;
			tittle="THỐNG KÊ SỐ TIỀN THANH TOÁN NHIÊN LIỆU THEO TỪNG MỨC";
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
		
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		model.addAttribute("type", type);
		model.addAttribute("tinh", tinh);
		model.addAttribute("dinhMuc1", dinhmuc1);
		model.addAttribute("dinhMuc2", dinhmuc2);
		model.addAttribute("tittle", tittle);
		
		model.addAttribute("exportFileName", exportFileName);
		return "jspQLDN/tkTtTheoMuc";
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
	
	@RequestMapping("data")
	public @ResponseBody 
	void dataSuccessList(
			@RequestParam(required = false) String month,
			@RequestParam(required = false) String year,
			@RequestParam(required = false) String tinh,
			@RequestParam(required = false) Integer dinhmuc1,
			@RequestParam(required = false) Integer dinhmuc2,
			@RequestParam(required = false) String type,
			
			HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
		
		
		if (type.equalsIgnoreCase("dien"))
		{
			List<QldnTramTTDien> dataList = new ArrayList<QldnTramTTDien>();
			dataList = qldnTramTTDienDAO.getTkTtDnTheoMucListAll(month,year,tinh,dinhmuc1,dinhmuc2,type);
			Gson gs = new Gson();
			String jsonCartList = gs.toJson(dataList);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.println(jsonCartList);
			out.close();
		}
		if (type.equalsIgnoreCase("nhienLieu"))
		{
			List<QldnTramTTNhienLieu> dataList = new ArrayList<QldnTramTTNhienLieu>();
			dataList = qldnTramTTNhienLieuDAO.getTkTtTheoMucListAll(month,year,tinh,dinhmuc1,dinhmuc2,type);
			Gson gs = new Gson();
			String jsonCartList = gs.toJson(dataList);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.println(jsonCartList);
			out.close();
		}
	}
}
