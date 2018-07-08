package controller.feedback;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import vo.CTableConfig;
import vo.FbProcess;
import vo.FbType;
import vo.HProvinceFb;
import vo.MDepartment;
import vo.SYS_PARAMETER;
import vo.SysMailParameter;
import vo.SysReportBlock;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;
import controller.BaseController;
import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.FbProcessDAO;
import dao.FbTypeDAO;
import dao.HProvinceFbDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysMailParameterDAO;
import dao.SysReportBlockDAO;

@Controller
@RequestMapping("/report-feedback/*")
public class reportFBMLMNController extends BaseController {
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	
	@Autowired
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
	@Autowired
	private SysReportBlockDAO sysReportBlockDAO;
	
	@Autowired
 	private FbProcessDAO fbProcessDAO;
	
	@Autowired
 	private FbTypeDAO FbTypeDAO;
	
	@Autowired
 	private HProvinceFbDAO hProvinceFbDAO;
 	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	/*tao bao cao cua email gui hang ngay hang gio. AnhCTV:30/10/2015
	 * function:dy/week/month/year/option: Bao cao muc ngay, tuan, thang, nam,tuy chon */
	@RequestMapping(value = "test/{function}")
	public ModelAndView list(
			@RequestParam(required = false) String sdate,
			@RequestParam(required = false) String edate,
			@RequestParam(required = false) String sweek,
			@RequestParam(required = false) String eweek,
			@RequestParam(required = false) String smonth,
			@RequestParam(required = false) String emonth,
			@RequestParam(required = false) String syear,
			@RequestParam(required = false) String eyear,
			@RequestParam(required = false) String region,
			@RequestParam(required = false) String province,
			@RequestParam(required = false) String fbType,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String sqlWhere,
			@PathVariable String function,
			Model model, HttpServletRequest request) throws ParseException, SQLException {
		
		List<SYS_PARAMETER> sysParemeterTitle = sysReportBlockDAO.titleRpFeedbackMLMN(function);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		//Kiem tra dieu kien tim kiem 
		Calendar cal = Calendar.getInstance();
		
		 if (function.equalsIgnoreCase("week")){
			DateTime dt = new DateTime();
			if (eweek == null) {
				dt = dt.minusWeeks(1);
				eweek = String.valueOf(dt.getWeekOfWeekyear());
				eyear = String.valueOf(dt.getYear());
			}
			if (sweek == null) {
				sweek = eweek;
				syear = eyear;
			}
			
		} else if (function.equalsIgnoreCase("month")){
			
			String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		    model.addAttribute("monthList", monthList);
			
			if (emonth == null) {
				emonth = String.valueOf(cal.get(Calendar.MONTH));
				eyear = String.valueOf(cal.get(Calendar.YEAR));
			}
			if (smonth == null) {
				smonth = emonth;
				syear = eyear;
			}
			
		}
		else if (function.equalsIgnoreCase("quarter")){
			
			String[] quarterList = {"1", "2", "3", "4"};
		    model.addAttribute("quarterList", quarterList);
			
			if (emonth == null) {
				emonth = String.valueOf((cal.get(Calendar.MONTH)-1)/3+1);
				eyear = String.valueOf(cal.get(Calendar.YEAR));
			}
			if (smonth == null) {
				smonth = emonth;
				syear = eyear;
			}
			
		}
		else if (function.equalsIgnoreCase("year")){
			if (eyear == null) {
				eyear = String.valueOf(cal.get(Calendar.YEAR));
			}
			if (syear == null) {
				syear = eyear;
			}
		}
		else
		{
			if (sdate == null || sdate.equals("")||(sdate!=null && !sdate.equals("") && DateTools.isValid("dd/MM/yyyy", sdate)==false))
			{
				sdate = df.format(new Date());
			}
			if (edate == null || edate.equals("")||(edate!=null && !edate.equals("") && DateTools.isValid("dd/MM/yyyy", edate)==false))
			{
				edate = df.format(new Date());
			}
		}
		
		//lay danh sach cac block trong email: ten
		List<SysReportBlock> blockList = sysReportBlockDAO.getBlockListReport("FEEDBACK",function);
		//Moi block:
		//lay cac cot config cho block
		//lay du lieu cho block
		//Tao grid hien thi len tren web
		List<String> gridList = new ArrayList<String>();
		for (SysReportBlock block : blockList) {
			List<String> paramList = new ArrayList<String>();
			paramList.add(sdate==null?"":sdate);
			paramList.add(edate==null?"":edate);
			paramList.add(sweek==null?"":sweek);
			paramList.add(eweek==null?"":eweek);
			paramList.add(smonth==null?"":smonth);
			paramList.add(emonth==null?"":emonth);
			paramList.add(syear==null?"":syear);
			paramList.add(eyear==null?"":eyear);
			paramList.add(region==null?"":region);
			paramList.add(province==null?"":province);
			paramList.add(fbType==null?"":fbType);
			paramList.add(status==null?"":status);
			paramList.add(sqlWhere==null?"":sqlWhere);
			paramList.add(function);
			paramList.add("1");
			String feedbackType = "FEEDBACK_"+function.toUpperCase();
			List<CTableConfig> columnList = cTableConfigDAO.getColumnReportBlock(feedbackType, block.getBlockId());
			String dataArray = ExportTools.exportToStringArray(block.getSqlWhere(),paramList,columnList);
			String grid = HelpTableConfigs.getGridReportDataString(columnList, "grid"+block.getBlockId(), dataArray, block.getBlockId(), columnList, "menu"+block.getBlockId(), null,"Y",columnList.get(0).getGroupFuntion(),null);
			//String grid = HelpTableConfigs.getTreeGridReportDataString(columnList, "grid"+block.getBlockId(), dataArray, block.getBlockId(), columnList, "menu"+block.getBlockId(), null,"Y",columnList.get(0).getGroupFuntion());
			gridList.add(grid);
		}
		
		model.addAttribute("blockList", blockList);
		model.addAttribute("gridList", gridList);
		model.addAttribute("sdate", sdate);
		model.addAttribute("edate", edate);
		model.addAttribute("sweek", sweek);
		model.addAttribute("eweek", eweek);
		model.addAttribute("smonth", smonth);
		model.addAttribute("emonth", emonth);
		model.addAttribute("syear", syear);
		model.addAttribute("eyear", eyear);
		model.addAttribute("region", region);
		model.addAttribute("province", province);
		model.addAttribute("fbType", fbType);
		model.addAttribute("status", status);
		model.addAttribute("sqlWhere", sqlWhere);
		model.addAttribute("function", function);
		model.addAttribute("funct", function);
		
		
		return new ModelAndView("jspfeedback/mlmn/reportOption");
	}
	
}