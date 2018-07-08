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
import dao.QldnLossPowerDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUserAreaDAO;
import dao.SysUserEquipmentNameDAO;
import dao.SysUsersDAO;
import vo.CTableConfig;
import vo.QldnLossPower;
import vo.QldnTramTTDien;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;
@Controller
@RequestMapping("/qldn/tong-hop-du-lieu-mat-dien-chay-may-no/*")
public class QLDNTongHopDlMatDienController extends BaseController {
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired 
	private SysUsersDAO sysUsersDao;
	@Autowired
	private QldnLossPowerDAO qldnLossPowerDAO;
	@Autowired
	private SysUserEquipmentNameDAO sysUserEquipmentNameDAO;
	
	@Autowired
	private SysUserAreaDAO sysUserAreaDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(
			
			@RequestParam(required = false) String sdate,
			@RequestParam(required = false) String edate,
			@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String cellid,
			@RequestParam(required = false) String vendor,
			@RequestParam(required = false) String province,
			@RequestParam(required = false) String district,
			@RequestParam(required = false) String region,
			
			Model model, HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (province != null)
		{
			province = province.trim();
		}
		else
		{
			province="";
		}
		
		Calendar cal = Calendar.getInstance();	
		cal.setTime(new Date());
		
		if (sdate == null || sdate.equals("")
				||(sdate!=null && !sdate.equals("") && DateTools.isValid("dd/MM/yyyy", sdate)==false))
		{
			cal.add(Calendar.DATE,-1);
			sdate = df.format(cal.getTime());
		}
		if (edate == null || edate.equals("")
				||(edate!=null && !edate.equals("") && DateTools.isValid("dd/MM/yyyy", edate)==false))
		{
			edate = sdate;
		}
		
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("QLDN_LOSS_POWER");
	//Lay du lieu cac cot an hien cua grid 
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList("QLDN_LOSS_POWER");
	//url
		String url = "dataThDlMatDien.htm?sdate="+sdate+"&edate="+edate+"&bscid="+bscid+"&cellid="+cellid+"&vendor="+vendor
				+"&province="+province+"&district="+district+"&region="+region;
	//Grid
		String gridReport = HelpTableConfigs.getGridPagingUrl(configList, "gridReport", url, "listColumn", listSource, "menuReport", null, "multiplerowsextended","Y");
		model.addAttribute("gridReport", gridReport); 
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "QLDN_LOSS_POWER_"+ dateNow;
		model.addAttribute("exportFileName", exportFileName);
		//Lay danh sach BSC theo user
				List<String> bscList = sysUserEquipmentNameDAO.getBSCList(null,username,vendor);
				String bscArray="var bscList = new Array(";//Danh sach bsc cho cobobox
				String cn="";
				for (String bsc : bscList) {
					bscArray = bscArray + cn +"\""+bsc+"\"";
					cn=",";
				}
				bscArray = bscArray+");";
				model.addAttribute("bscList", bscArray);  		  
					
		model.addAttribute("sdate", sdate);
		model.addAttribute("edate", edate);
		model.addAttribute("bscid", bscid);
		model.addAttribute("cellid", cellid);
		model.addAttribute("vendor", vendor);
		model.addAttribute("province", province);
		model.addAttribute("district", district);
		model.addAttribute("region", region);
		return "jspQLDN/thDlMatDienChayMayNo";
		}
	
	@RequestMapping("dataThDlMatDien")
		public @ResponseBody 
		void dataSuccessList(
				@RequestParam(required = false) String sdate,
				@RequestParam(required = false) String edate,
				@RequestParam(required = false) String bscid,
				@RequestParam(required = false) String cellid,
				@RequestParam(required = false) String vendor,
				@RequestParam(required = false) String province,
				@RequestParam(required = false) String district,
				@RequestParam(required = false) String region,
				
				HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
			
		if (sdate!=null && sdate.equals("null"))
		{
			sdate= "";
		}
		if (edate!=null && edate.equals("null"))
		{
			edate= "";
		} 
			List<QldnLossPower> ThDlMatDienList = new ArrayList<QldnLossPower>();
			ThDlMatDienList = qldnLossPowerDAO.getThDlMatDienListAll(sdate,edate,bscid,cellid,vendor,province,district,region);
			Gson gs = new Gson();
			String jsonCartList = gs.toJson(ThDlMatDienList);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.println(jsonCartList);
			out.close();
		 }	


	@RequestMapping("exportData")
	public ModelAndView exportData(
			@RequestParam(required = false) String sdate,
			@RequestParam(required = false) String edate,
			@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String cellid,
			@RequestParam(required = false) String vendor,
			@RequestParam(required = false) String province,
			@RequestParam(required = false) String district,
			@RequestParam(required = false) String region,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		try {
			// B1: lay du lieu
			List<QldnLossPower> ThDlMatDienList = new ArrayList<QldnLossPower>();
			ThDlMatDienList = qldnLossPowerDAO.getThDlMatDienListAll(sdate,edate,bscid,cellid,vendor,province,district,region);
			List<Object> dataObjs = new ArrayList<Object>();
			for (QldnLossPower record : ThDlMatDienList) {
				dataObjs.add(record);
			}
			// B2 thuc hien export
			String tableName="QLDN_LOSS_POWER";
			List<CTableConfig> headers = cTableConfigDAO.getColumnList(tableName);
			ExportTools.exportFileExcel("QldnLossPower", dataObjs, headers, request, response);

		} catch (Exception exp) {
			exp.printStackTrace();
		}

		return null;

	}

}
