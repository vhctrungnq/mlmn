package controller.qldn;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import dao.SYS_PARAMETERDAO;
import dao.SysUserAreaDAO;
import dao.SysUserEquipmentNameDAO;
import dao.SysUsersDAO;
import vo.CTableConfig;
import vo.QldnPhanBoChiPhi;
import vo.alarm.utils.HelpTableConfigs;

@Controller
@RequestMapping("/qldn/tong-hop-chi-phi-theo-tinh/*")
public class QLDNTongHopChiPhiTinhController extends BaseController {
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired
	private QldnPhanBoChiPhiDAO qldnPhanBoChiPhiDao;
	
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
			@RequestParam(required = false) Integer smonth,
			@RequestParam(required = false) Integer emonth,
			@RequestParam(required = false) String tinh,
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
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("QLDN_TH_CHI_PHI_TINH");
	//Lay du lieu cac cot an hien cua grid 
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList("QLDN_TH_CHI_PHI_TINH");
	//url
		String url = "dataTHChiPhiTinh.htm?smonth="+smonth+"&emonth="+emonth+"&tinh="+tinh;
	//Grid
		String gridReport = HelpTableConfigs.getGridPagingUrl(configList, "gridReport", url, "listColumn", listSource, "menuReport", null, "multiplerowsextended","Y");
		model.addAttribute("gridReport", gridReport); 
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "QLDN_TH_CHI_PHI_TINH_"+ dateNow;
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
		
		model.addAttribute("smonth", smonth);
		model.addAttribute("emonth", emonth);
		model.addAttribute("tinh", tinh);
		return "jspQLDN/thChiPhiTinh";
		}
		
		@RequestMapping("dataTHChiPhiTinh")
		public @ResponseBody 
		void dataSuccessList(
				@RequestParam(required = false) String smonth,
				@RequestParam(required = false) String emonth,
				@RequestParam(required = false) String tinh,
				HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
			
			if (smonth!=null && smonth.equals("null"))
			{
				smonth="";
			}
			if (emonth!=null && emonth.equals("null"))
			{
				emonth="";
			}
			List<QldnPhanBoChiPhi> THChiPhiTinhList = new ArrayList<QldnPhanBoChiPhi>();
			THChiPhiTinhList = qldnPhanBoChiPhiDao.getTHChiPhiTinhListAll(smonth,emonth,tinh);
			Gson gs = new Gson();
			String jsonCartList = gs.toJson(THChiPhiTinhList);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.println(jsonCartList);
			out.close();
		 }	


}

