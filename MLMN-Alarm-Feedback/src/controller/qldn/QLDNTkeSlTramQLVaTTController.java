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
import dao.QldnPhanBoChiPhiDAO;
import dao.QldnQlMatBangDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUserAreaDAO;
import dao.SysUserEquipmentNameDAO;
import dao.SysUsersDAO;
import vo.CTableConfig;
import vo.QldnPhanBoChiPhi;
import vo.QldnQlMatBang;
import vo.alarm.utils.HelpTableConfigs;

@Controller
@RequestMapping("/qldn/thong-ke-sl-tram-quan-ly-tt/*")
public class QLDNTkeSlTramQLVaTTController extends BaseController {
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
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("QLDN_TK_SL_TRAM_QL_TT");
		//Lay du lieu cac cot an hien cua grid 
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList("QLDN_TK_SL_TRAM_QL_TT");
		//url
		String url = "dataTkeSlTramQlTt.htm?year="+year+"&tinh="+tinh;
		//Grid
		String gridReport = HelpTableConfigs.getGridPagingUrl(configList, "gridReport", url, "listColumn", listSource, "menuReport", null, "multiplerowsextended","Y");
		model.addAttribute("gridReport", gridReport);   
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "QLDN_TK_SL_TRAM_QL_TT_"+ dateNow;
		model.addAttribute("exportFileName", exportFileName);
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
		
		return "jspQLDN/tkeSlTramQlVaTT";
		}
		@RequestMapping("dataTkeSlTramQlTt")
		public @ResponseBody 
		void dataSuccessList(
			@RequestParam(required = false) Integer year,
			@RequestParam(required = false) String tinh,
			
			HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
		List<QldnQlMatBang> tkSlTramQLVaTTList = new ArrayList<QldnQlMatBang>();
		tkSlTramQLVaTTList = qldnQlMatBangDao.gettkSlTramQLVaTTListAll(year,tinh);
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(tkSlTramQLVaTTList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
	 }
}
