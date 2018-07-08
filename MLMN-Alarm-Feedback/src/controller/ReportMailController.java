package controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import oracle.jdbc.OracleTypes;

import org.apache.poi.hssf.record.formula.functions.Hour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vo.CHighlightConfigs;
import vo.CTableConfig;
import vo.SYS_PARAMETER;
import vo.SysMailParameter;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;

import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysMailParameterDAO;
import dao.SysMailParameterMasterDAO;

@Controller
@RequestMapping("/report-mail/*")
public class ReportMailController extends BaseController {
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	
	@Autowired
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
	@Autowired
	private SysMailParameterDAO sysMailParameterDAO;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	/*tao bao cao cua email gui hang ngay hang gio. AnhCTV:30/10/2015
	 * function:DY/HR: Bao cao muc ngay, gio*/
	@RequestMapping(value = "{function}")
	public ModelAndView list(
			@RequestParam(required = false) String shour,
			@RequestParam(required = false) String sdate,
			@RequestParam(required = false) String mailid,
			@RequestParam(required = false) String blockid,
			@PathVariable String function,
			Model model, HttpServletRequest request) throws ParseException, SQLException {
		
		List<SYS_PARAMETER> sysParemeterTitle = cTableConfigDAO.titleReportMail(function,mailid,blockid);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		//Kiem tra dieu kien tim kiem 
		if (sdate == null || sdate.equals("")
				||(sdate!=null && !sdate.equals("") && DateTools
							.isValid("dd/MM/yyyy", sdate)==false))
		{
			sdate = df.format(new Date());
		}
		if (blockid == null)
		{
			blockid="";
		}
		if (shour == null) {
			shour = "0";
		} 
		//Lay tieu de mail
		
		//lay danh sach cac block trong email: ten
		List<SysMailParameter> blockList = sysMailParameterDAO.getBlockListReport(mailid,blockid);
		//Moi block:
		//lay cac cot config cho block
		//lay du lieu cho block
		//Tao grid hien thi len tren web
		List<String> gridList = new ArrayList<String>();
		for (SysMailParameter sysMailParameter : blockList) {
			String block= sysMailParameter.getBlockId();
			List<String> paramList = new ArrayList<String>();
			paramList.add(mailid);
			paramList.add(block);
			paramList.add(sdate);
			paramList.add(shour);
			
			List<CTableConfig> columnList = cTableConfigDAO.getColumnMailList(sysMailParameter.getTableName(), block);
			String dataArray = ExportTools.exportToStringArray("PK_REPORT_ALARM_MLMN.PR_REPORT_MAIL_GET(?, ?, ?, ?, ?)",paramList,columnList);
			String grid = HelpTableConfigs.getGridReportDataString(columnList, "grid"+block, dataArray, null, null, "menu"+block, null,"N",null,null);
			gridList.add(grid);
		}
		
		model.addAttribute("blockList", blockList);
		model.addAttribute("gridList", gridList);
		model.addAttribute("shour", shour);
		model.addAttribute("sdate", sdate);
		model.addAttribute("mailid", mailid);
		model.addAttribute("blockid", blockid);
		model.addAttribute("function", function);
		return new ModelAndView("jspfeedback/mlmn/reportOption");
	}
	
	
}
