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
import dao.SYS_PARAMETERDAO;
import dao.SysUserAreaDAO;
import dao.SysUsersDAO;
import vo.CTableConfig;
import vo.QldnTramTTDien;
import vo.alarm.utils.HelpTableConfigs;

@Controller
@RequestMapping("/qldn/bao-cao-ket-qua-kiem-tra/*")
public class QLDNBaoCaoKetQuaKiemTraController extends BaseController {
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired 
	private SysUsersDAO sysUsersDao;
	@Autowired
	private QldnTramTTDienDAO qldnTramTTDienDAO;
	
	@Autowired
	private SysUserAreaDAO sysUserAreaDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(
			
			@RequestParam(required = false) String month,
			@RequestParam(required = false) String year,
			@RequestParam(required = false) String nhom,
			@RequestParam(required = false) String nguong,
			Model model, HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Calendar cal = Calendar.getInstance();	
		cal.setTime(new Date());
		if (month!=null&&!month.equals("null"))
		{
			month=month.trim();
		}
		else
		{
			month = String.format("%02d", cal.get(Calendar.MONTH) + 1);
			
		}
		if (year!=null&&!year.equals("null"))
		{
			year=year.trim();
		}
		else
		{
			year= String.valueOf(cal.get(Calendar.YEAR));
		}
		if (nhom != null)
		{
			nhom = nhom.trim();
		}
		else
		{
			nhom="";
		}
		if (nguong != null)
		{
			nguong = nguong.trim();
		}
		else
		{
			nguong="50";
		}
		
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("QLDN_BAO_CAO_KET_QUA_KT");
	//Lay du lieu cac cot an hien cua grid 
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList("QLDN_BAO_CAO_KET_QUA_KT");
	//url
		String url = "dataBaoCaoKq.htm?month="+month+"&year="+year+"&nhom="+nhom+"&nguong="+nguong;
	//Grid
		String gridReport = HelpTableConfigs.getGridPagingUrl(configList, "gridReport", url, "listColumn", listSource, "menuReport", null, "multiplerowsextended","Y");
		model.addAttribute("gridReport", gridReport); 
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "QLDN_BAO_CAO_KET_QUA_KT_"+ dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		model.addAttribute("month", month);
		model.addAttribute("year", year);
		model.addAttribute("nguong", nguong);
		model.addAttribute("nhom", nhom);
		return "jspQLDN/baoCaoKetQuaKiemTra";
	}
	@RequestMapping("dataBaoCaoKq")
	public @ResponseBody 
	void dataSuccessList(
			@RequestParam(required = false) String month,
			@RequestParam(required = false) String year,
			@RequestParam(required = false) String nhom,
			@RequestParam(required = false) String nguong,
			HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
		
	if (month!=null && month.equals("null"))
	{
		month= "";
	}
	if (year!=null && year.equals("null"))
	{
		year= "";
	} 
	if (nguong!=null && nguong.equals("null"))
	{
		nguong= "";
	} 
	List<QldnTramTTDien> BaoCaoKetQuaKtList = new ArrayList<QldnTramTTDien>();
	BaoCaoKetQuaKtList = qldnTramTTDienDAO.getBaoCaoKetQuaKtListAll(month,year,nhom,nguong);
	Gson gs = new Gson();
	String jsonCartList = gs.toJson(BaoCaoKetQuaKtList);
	PrintWriter out = response.getWriter();
	response.setContentType("application/json");
	out.println(jsonCartList);
	out.close();
	}
		
	 	


}