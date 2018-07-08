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
@RequestMapping("/qldn/tong-hop-chi-phi-theo-tram/*")
public class QLDNTongHopCHiPhiTramController extends BaseController {
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
			@RequestParam(required = false) String tram,
			@RequestParam(required = false) String nhom,
			@RequestParam(required = false) String tinh,
			Model model, HttpServletRequest request) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (tram != null)
		{
			tram = tram.trim();
		}
		else
		{
			tram="";
		}
		if (nhom != null)
		{
			nhom = nhom.trim();
		}
		else
		{
			nhom="";
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
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("QLDN_TH_CHI_PHI_TRAM");
	//Lay du lieu cac cot an hien cua grid 
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList("QLDN_TH_CHI_PHI_TRAM");
	//url
		String url = "dataTHChiPhiTram.htm?smonth="+smonth+"&emonth="+emonth+"&tram="+tram+"&nhom="+nhom+"&tinh="+tinh;
	//Grid
		String gridReport = HelpTableConfigs.getGridPagingUrl(configList, "gridReport", url, "listColumn", listSource, "menuReport", null, "multiplerowsextended","Y");
		model.addAttribute("gridReport", gridReport); 
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "QLDN_TH_CHI_PHI_TRAM_"+ dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		  
		String cn1="";
		  List<String> provinceList = sysUserAreaDAO.getProvinceList(username);
		  String provinceArray="var provinceList = new Array(";//Danh sach district cho cobobox
		  cn1="";
		  for (String dis : provinceList) {
		   provinceArray = provinceArray + cn1 +"\""+dis+"\"";
		   cn1=",";
		  }
		  provinceArray = provinceArray+");";
		  model.addAttribute("provinceList", provinceArray);
		  
		//Lay danh sach team theo user
			String cn = "";
			  List<String> teamList = sysUserAreaDAO.getSubTeamList(tinh,username);
			  String teamArray="var teamList = new Array(";//Danh sach district cho cobobox
			  cn="";
			  for (String dis : teamList) {
			   teamArray = teamArray + cn +"\""+dis+"\"";
			   cn=",";
			  }
			  teamArray = teamArray+");";
			  model.addAttribute("teamList", teamArray);
		
		
		model.addAttribute("smonth", smonth);
		model.addAttribute("emonth", emonth);
		model.addAttribute("tram", tram);
		model.addAttribute("nhom", nhom);
		model.addAttribute("tinh", tinh);
		return "jspQLDN/thChiPhiTram";
		}
		
		@RequestMapping("dataTHChiPhiTram")
		public @ResponseBody 
		void dataSuccessList(
				@RequestParam(required = false) String smonth,
				@RequestParam(required = false) String emonth,
				@RequestParam(required = false) String tram,
				@RequestParam(required = false) String nhom,
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
			List<QldnPhanBoChiPhi> THChiPhiTramList = new ArrayList<QldnPhanBoChiPhi>();
			THChiPhiTramList = qldnPhanBoChiPhiDao.getTHChiPhiTramListAll(smonth,emonth,tram,nhom,tinh);
			Gson gs = new Gson();
			String jsonCartList = gs.toJson(THChiPhiTramList);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.println(jsonCartList);
			out.close();
		 }	


}
