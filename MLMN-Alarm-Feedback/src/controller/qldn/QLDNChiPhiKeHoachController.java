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
import dao.SYS_PARAMETERDAO;
import dao.SysUserAreaDAO;
import dao.SysUserEquipmentNameDAO;
import dao.SysUsersDAO;
import vo.CTableConfig;
import vo.QldnPhanBoChiPhi;
import vo.alarm.utils.HelpTableConfigs;


	@Controller
	@RequestMapping("/qldn/chi-phi-ke-hoach/*")
	public class QLDNChiPhiKeHoachController extends BaseController {
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
				@RequestParam(required = false) Integer syear,
				@RequestParam(required = false) Integer eyear,
				Model model, HttpServletRequest request) {
			
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			//do du lieu ra grid
			List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("QLDN_CHI_PHI_KE_HOACH");
		//Lay du lieu cac cot an hien cua grid 
			List<CTableConfig> listSource = cTableConfigDAO.getColumnList("QLDN_CHI_PHI_KE_HOACH");
		//url
			String url = "dataPhanBoChiPhi.htm?syear="+syear+"&eyear="+eyear;
		//Grid
			String gridReport = HelpTableConfigs.getGridPagingUrl(configList, "gridReport", url, "listColumn", listSource, "menuReport", null, "multiplerowsextended","Y");
			model.addAttribute("gridReport", gridReport); 
			
			// Lay ten file export
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
			String dateNow = formatter.format(currentDate.getTime());
			String exportFileName = "QLDN_PHAN_BO_CHI_PHI_"+ dateNow;
			model.addAttribute("exportFileName", exportFileName);
			  
			
			model.addAttribute("syear", syear);
			model.addAttribute("eyear", eyear);
			return "jspQLDN/chiPhiKeHoach";
			}
			
			@RequestMapping("dataPhanBoChiPhi")
			public @ResponseBody 
			void dataSuccessList(
					@RequestParam(required = false) String syear,
					@RequestParam(required = false) String eyear,
					
					HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
				
				if (syear!=null && syear.equals("null"))
				{
					syear="";
				}
				if (eyear!=null && eyear.equals("null"))
				{
					eyear="";
				}
				List<QldnPhanBoChiPhi> ChiPhiKeHoachList = new ArrayList<QldnPhanBoChiPhi>();
				ChiPhiKeHoachList = qldnPhanBoChiPhiDao.getChiPhiKeHoachListAll(syear,eyear);
				Gson gs = new Gson();
				String jsonCartList = gs.toJson(ChiPhiKeHoachList);
				PrintWriter out = response.getWriter();
				response.setContentType("application/json");
				out.println(jsonCartList);
				out.close();
			 }	
	

}
