package controller.qldn;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
import dao.QldnTramTTDienDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUsersDAO;
import vo.CTableConfig;
import vo.QLDNReportTTTram;
import vo.QldnThongTinMayNo;
import vo.alarm.utils.HelpTableConfigs;

@Controller
@RequestMapping("/qldn/thong-tin-may-no/*")
public class thongKeTTMayNoController extends BaseController {
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired 
	private SysUsersDAO sysUsersDao;
	@Autowired
	private QldnThongTinMayNoDAO qldnThongTinMayNoDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping(value = "thong-ke", method = RequestMethod.GET) 
	public ModelAndView list( 
			Model model, HttpServletRequest request) {
		
		
		String tableName="QLDN_DASHBOARD";
		// REPORT PROVICE
		String type="TINH";
		tableName="QLDN_DASHBOARD_TINH";
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid(tableName);
		//url
		String url = "data.htm?type="+type;
		//Grid
		String gridTINH = HelpTableConfigs.getGridPagingReportUrl(configList, "grid"+type, url, null, null, "Menu"+type, null,"singlecell",null,"Y");
		model.addAttribute("gridTINH", gridTINH);	
		
		// REPORT LTT (LOAI THANH TOAN)
		type="LTT";
		tableName="QLDN_DASHBOARD_LTT";
		//do du lieu ra grid
		configList = cTableConfigDAO.getTableConfigsForGrid(tableName);
		//url
		url = "data.htm?type="+type;
		//Grid
		String gridLTT = HelpTableConfigs.getGridPagingReportUrl(configList, "grid"+type, url, null, null, "Menu"+type, null,"singlecell",null,"Y");
		model.addAttribute("gridLTT", gridLTT);	
		
		// REPORT ATS
		type="ATS";
		tableName="QLDN_DASHBOARD_ATS";
		//do du lieu ra grid
		configList = cTableConfigDAO.getTableConfigsForGrid(tableName);
		//url
		url = "data.htm?type="+type;
		//Grid
		String gridATS = HelpTableConfigs.getGridPagingReportUrl(configList, "grid"+type, url, null, null, "Menu"+type, null,"singlecell",null,"Y");
		model.addAttribute("gridATS", gridATS);	
		
		// REPORT LOAI NHIEN LIEU
		type="LOAINL";
		tableName="QLDN_DASHBOARD_LOAINL";
		//do du lieu ra grid
		configList = cTableConfigDAO.getTableConfigsForGrid(tableName);
		//url
		url = "data.htm?type="+type;
		//Grid
		String gridLOAINL = HelpTableConfigs.getGridPagingReportUrl(configList, "grid"+type, url, null, null, "Menu"+type, null,"singlecell",null,"Y");
		model.addAttribute("gridLOAINL", gridLOAINL);	
		
		//REPORT HIEU MAY NO
		type="HIEUMN";
		tableName="QLDN_DASHBOARD_HIEUMN";
		//do du lieu ra grid
		configList = cTableConfigDAO.getTableConfigsForGrid(tableName);
		//url
		url = "data.htm?type="+type;
		//Grid
		String gridHIEUMN =HelpTableConfigs.getGridPagingReportUrl(configList, "grid"+type, url, null, null, "Menu"+type, null,"singlecell",null,"Y");
		model.addAttribute("gridHIEUMN", gridHIEUMN);	
		
		//REPORT CONG SUAT MAY NO
		type="CS_MN";
		tableName="QLDN_DASHBOARD_CS_MN";
		//do du lieu ra grid
		configList = cTableConfigDAO.getTableConfigsForGrid(tableName);
		//url
		url = "data.htm?type="+type;
		//Grid
		String gridCS_MN = HelpTableConfigs.getGridPagingReportUrl(configList, "grid"+type, url, null, null, "Menu"+type, null,"singlecell",null,"Y");
		model.addAttribute("gridCS_MN", gridCS_MN);	
		
		// REPORT TEN XHH
		type="TEN_XHH";
		tableName="QLDN_DASHBOARD_TEN_XHH";
		//do du lieu ra grid
		configList = cTableConfigDAO.getTableConfigsForGrid(tableName);
		//url
		url = "data.htm?type="+type;
		//Grid
		String gridTEN_XHH= HelpTableConfigs.getGridPagingReportUrl(configList, "grid"+type, url, null, null, "Menu"+type, null,"singlecell",null,"Y");
		model.addAttribute("gridTEN_XHH", gridTEN_XHH);
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		model.addAttribute("dateNow", dateNow);
				
		return new ModelAndView("jspQLDN/thongKeMayNo");
			}
		@RequestMapping("data")
		public @ResponseBody 
		void dataSuccessList(
			@RequestParam(required = true) String type,
			HttpServletRequest request, HttpServletResponse response,Model model) throws IOException {
		
			List<QldnThongTinMayNo> dataList = new ArrayList<QldnThongTinMayNo>();
			dataList = qldnThongTinMayNoDAO.getThongKeMayNo(type);
			Gson gs = new Gson();
			String jsonCartList = gs.toJson(dataList);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			out.println(jsonCartList);
			out.close();
		}
}
