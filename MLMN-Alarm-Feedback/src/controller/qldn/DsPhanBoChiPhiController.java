package controller.qldn;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import controller.BaseController;
import dao.CTableConfigDAO;
import dao.QldnPhanBoChiPhiDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUserAreaDAO;
import dao.SysUserEquipmentNameDAO;
import dao.SysUsersDAO;
import vo.AlMonitorSite;
import vo.CTableConfig;
import vo.QldnPhanBoChiPhi;
import vo.SYS_PARAMETER;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.HelpTableConfigs;

@Controller
@RequestMapping("/qldn/phan-bo-chi-phi/*")
public class DsPhanBoChiPhiController extends BaseController {
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
			@RequestParam(required = false) Integer year,
			@RequestParam(required = false) String tinh,
			@RequestParam(required = false) String loaiChiPhi,
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
		if (loaiChiPhi != null)
		{
			loaiChiPhi = loaiChiPhi.trim();
		}
		else
		{
			loaiChiPhi="";
		}
		
		//do du lieu ra grid
			List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("QLDN_PHAN_BO_CHI_PHI");
		//Lay du lieu cac cot an hien cua grid 
			List<CTableConfig> listSource = cTableConfigDAO.getColumnList("QLDN_PHAN_BO_CHI_PHI");
		//url
			String url = "dataPhanBoChiPhi.htm?year="+year+"&tinh="+tinh+"&loaiChiPhi="+loaiChiPhi;
		//Grid
			String gridReport = HelpTableConfigs.getGridPagingUrl(configList, "gridReport", url, "listColumn", listSource, "menuReport", null, "multiplerowsextended","Y");
			model.addAttribute("gridReport", gridReport);   
			
	// Lay ten file export
	Calendar currentDate = Calendar.getInstance();
	SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
	String dateNow = formatter.format(currentDate.getTime());
	String exportFileName = "QLDN_PHAN_BO_CHI_PHI_"+ dateNow;
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
	model.addAttribute("loaiChiPhi", loaiChiPhi);
	return "jspQLDN/phanBoChiPhi";
	}
	
	@RequestMapping("dataPhanBoChiPhi")
	public @ResponseBody 
	void dataSuccessList(
			@RequestParam(required = false) Integer year,
			@RequestParam(required = false) String tinh,
			@RequestParam(required = false) String loaiChiPhi,
			HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
		List<QldnPhanBoChiPhi> PhanBoChiPhiList = new ArrayList<QldnPhanBoChiPhi>();
		PhanBoChiPhiList = qldnPhanBoChiPhiDao.getPhanBoChiPhiListAll(year,tinh,loaiChiPhi);
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(PhanBoChiPhiList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
	 }
	
	//Xoa
		@RequestMapping(value = "delete", method = RequestMethod.GET)
		public String onDelete(@RequestParam (required = true) String idList,
				HttpServletRequest request, Model model) {
			try {
				String[] idArray = idList.split(",");
				for (String id : idArray) {
					System.out.println(id);
					qldnPhanBoChiPhiDao.deleteByPrimaryKey(Integer.parseInt(id));
				}
				saveMessageKey(request, "messsage.confirm.deletesuccess");
			}
			catch (Exception e) {
				saveMessageKey(request, "messsage.confirm.deletefailure");
			}
			return "redirect:list.htm";
		}
		
	//Them, sua
		@RequestMapping(value = "form", method = RequestMethod.GET)
		public String form(@RequestParam (required = false) String id,
				HttpServletRequest request, Model model) { 
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Integer year;
		QldnPhanBoChiPhi PhanBoChiPhi = new QldnPhanBoChiPhi();
		if (id!=null&&!id.equals(""))
		{
			PhanBoChiPhi = qldnPhanBoChiPhiDao.selectByPrimaryKey(Integer.parseInt(id));
			
		}
		else {
			Calendar cal = Calendar.getInstance(); 
			   cal.setTime(new Date());
			
				year = cal.get(Calendar.YEAR);
			PhanBoChiPhi.setYear(year);
			
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
		model.addAttribute("PhanBoChiPhi", PhanBoChiPhi);
		
		return "jspQLDN/phanBoChiPhiForm";
		}
		
		@RequestMapping(value = "form", method = RequestMethod.POST)
		public String onSubmit(@ModelAttribute("PhanBoChiPhi") @Valid QldnPhanBoChiPhi PhanBoChiPhi, BindingResult result,
				
				
				ModelMap model, HttpServletRequest request) throws ParseException {
			

		//Ten nguoi dung dang nhap
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if (PhanBoChiPhi.getYear()==0)
		{
			model.addAttribute("errorYear", "*");
			saveMessageKey(request, "alarmExtend.errorField");
			model.addAttribute("PhanBoChiPhi", PhanBoChiPhi); 
			return "jspQLDN/phanBoChiPhiForm";
		}
		
		if (PhanBoChiPhi.getTinh()==null)
		{
			model.addAttribute("errorTinh", "*");
			saveMessageKey(request, "alarmExtend.errorField");
			model.addAttribute("PhanBoChiPhi", PhanBoChiPhi); 
			return "jspQLDN/phanBoChiPhiForm";
		}
			
			
			if (PhanBoChiPhi.getLoaiChiPhi()==null )
			{
				model.addAttribute("errorLoaiChiPhi", "*");
				saveMessageKey(request, "alarmExtend.errorField"); 
				model.addAttribute("PhanBoChiPhi", PhanBoChiPhi); 
				return "jspQLDN/phanBoChiPhiForm";
			}
			
			
			System.out.println("eror:"+result.getObjectName());
			if (result.hasErrors() ) {
				saveMessageKey(request, "alarmExtend.errorField");
				
				model.addAttribute("PhanBoChiPhi", PhanBoChiPhi); 
				return "jspQLDN/phanBoChiPhiForm";
			}
			else
			{
				Long chiphi = (long)PhanBoChiPhi.getMonth1() + (long)PhanBoChiPhi.getMonth2() + (long)PhanBoChiPhi.getMonth3() +
						(long)PhanBoChiPhi.getMonth4() + (long)PhanBoChiPhi.getMonth5() + (long)PhanBoChiPhi.getMonth6() +
						(long)PhanBoChiPhi.getMonth7() + (long)PhanBoChiPhi.getMonth8() + (long)PhanBoChiPhi.getMonth9() +
						(long)PhanBoChiPhi.getMonth10() + (long)PhanBoChiPhi.getMonth11() + (long)PhanBoChiPhi.getMonth12() ;
				PhanBoChiPhi.setChiPhi(chiphi);
				if (PhanBoChiPhi.getId()==null)
				{
					
					
					qldnPhanBoChiPhiDao.insert(PhanBoChiPhi);
					saveMessageKey(request, "alarmExtend.insertSucceFull");
				}
				else
				{
					//sua chua
					
					qldnPhanBoChiPhiDao.updateByPrimaryKey(PhanBoChiPhi);
					saveMessageKey(request, "alarmExtend.updateSuccelfull");	
				}
			}
			return "jspQLDN/phanBoChiPhiForm";
		}
	}

