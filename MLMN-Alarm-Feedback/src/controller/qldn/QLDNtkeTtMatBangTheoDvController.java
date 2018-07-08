package controller.qldn;

import java.io.IOException;
import java.io.PrintWriter;
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
import dao.QldnQlMatBangDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUserAreaDAO;
import dao.SysUserEquipmentNameDAO;
import dao.SysUsersDAO;
import vo.CTableConfig;
import vo.QldnQlMatBang;
import vo.alarm.utils.HelpTableConfigs;

@Controller
@RequestMapping("/qldn/thong-ke-tt-mat-bang-theo-dv/*")
public class QLDNtkeTtMatBangTheoDvController extends BaseController {
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired
	private QldnQlMatBangDAO qldnQlMatBangDao;
	
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	
	@Autowired
	private SysUserEquipmentNameDAO sysUserEquipmentNameDAO;
	
	@Autowired 
	private SysUsersDAO sysUsersDao;
	
	@Autowired
	  private SysUserAreaDAO sysUserAreaDAO;
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(
			@RequestParam(required = false) Integer year,
			@RequestParam(required = false) String tinh,
			
			Model model, HttpServletRequest request) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Calendar cal = Calendar.getInstance(); 
		   cal.setTime(new Date());
		   
		if (year == null) {
			year = cal.get(Calendar.YEAR);
		}
		
		if (tinh != null)
		{
			tinh = tinh.trim();
		}
		else
		{
			tinh="";
		}
		//do du lieu ra grid
		List<CTableConfig> configList1 = cTableConfigDAO.getTableConfigsForGrid("QLDN_TK_MAT_BANG_THEO_DV_XHH");
		List<CTableConfig> configList2 = cTableConfigDAO.getTableConfigsForGrid("QLDN_TK_MAT_BANG_THEO_DV_KHAC");
		//Lay du lieu cac cot an hien cua grid 
		List<CTableConfig> listSource1 = cTableConfigDAO.getColumnList("QLDN_TK_MAT_BANG_THEO_DV_XHH");
		List<CTableConfig> listSource2 = cTableConfigDAO.getColumnList("QLDN_TK_MAT_BANG_THEO_DV_KHAC");
		//url
		String url1 = "dataTkeTtMatBangTheoDv.htm?year="+year+"&tinh="+tinh+"&type=XHH";
		String url2 = "dataTkeTtMatBangTheoDv.htm?year="+year+"&tinh="+tinh+"&type=KHAC";
		//Grid
		String gridReport1 = HelpTableConfigs.getGridPagingUrl(configList1, "gridReport1", url1, "listColumn", listSource1, "menuReport1", null, "multiplerowsextended","Y");
		String gridReport2 = HelpTableConfigs.getGridPagingUrl(configList2, "gridReport2", url2, "listColumn", listSource2, "menuReport2", null, "multiplerowsextended","Y");
		model.addAttribute("gridReport1", gridReport1);  
		model.addAttribute("gridReport2", gridReport2); 
		
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName1 = "QLDN_TK_MAT_BANG_THEO_DV_XHH_"+ dateNow;
		String exportFileName2 = "QLDN_TK_MAT_BANG_THEO_DV_KHAC_"+ dateNow;
		model.addAttribute("exportFileName1", exportFileName1);
		model.addAttribute("exportFileName2", exportFileName2);
		
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
		
		model.addAttribute("year", year);
		model.addAttribute("tinh", tinh);
		
		return "jspQLDN/tkeTtMatBangTheoDv";
		}
		@RequestMapping("dataTkeTtMatBangTheoDv")
		public @ResponseBody 
		void dataSuccessList(
				@RequestParam(required = false) Integer year,
				@RequestParam(required = false) String tinh,
				@RequestParam(required = false) String type,
		
				HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
			List<QldnQlMatBang> dataList = new ArrayList<QldnQlMatBang>();
			if(type.equals("XHH"))
			{
				dataList = qldnQlMatBangDao.gettkTtMatBangTheoDvXHHListAll(year,tinh);
			}
			else
			{
				dataList = qldnQlMatBangDao.gettkTtMatBangTheoDvKhacListAll(year,tinh);
			}
			Gson gs = new Gson();
			String jsonCartList = gs.toJson(dataList);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.println(jsonCartList);
			out.close();
 }
}
