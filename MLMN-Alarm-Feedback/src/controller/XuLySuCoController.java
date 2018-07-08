package controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import dao.CTableConfigDAO;
import dao.RAlarmLogDAO;
import vo.CTableConfig;
import vo.RAlarmLog;
import vo.SYS_PARAMETER;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;

@Controller
@RequestMapping("xu-ly-su-co/*")
public class XuLySuCoController {

	@Autowired
	private RAlarmLogDAO rAlarmLog;
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping(value = "list")
	public ModelAndView showList(@RequestParam(required = false) String region,
			@RequestParam(required = false) String dept, @RequestParam(required = false) String sumLevel,
			@RequestParam(required = false) String start, @RequestParam(required = false) String end,
			@RequestParam(required = false) String syear,
			@RequestParam(required = false) String eyear,
			 ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {

		List<String> regionList = rAlarmLog.getRegion();
		List<String> deptList = rAlarmLog.getDept();
		
		String title = "Chỉ tiêu cảnh báo ";
		String tableName = "";
		String url = "";
		Calendar cal = Calendar.getInstance();
		
		 if (sumLevel.equalsIgnoreCase("week")){
			 title += " mức tuần";
			tableName = "WK_CANH_BAO";
			DateTime dt = new DateTime();
			if (end == null) {
				dt = dt.minusWeeks(1);
				end = String.valueOf(dt.getWeekOfWeekyear());
				eyear = String.valueOf(dt.getYear());
			}
			if (start == null) {
				start = end;
				syear = eyear;
			}
			
		} else if (sumLevel.equalsIgnoreCase("month")){
			tableName = "MN_CANH_BAO";
			title += " mức tháng";
			String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		    model.addAttribute("monthList", monthList);
			
			if (end == null) {
				end = String.valueOf(cal.get(Calendar.MONTH));
				eyear = String.valueOf(cal.get(Calendar.YEAR));
			}
			if (start == null) {
				start = end;
				syear = eyear;
			}
			
		}
		else if (sumLevel.equalsIgnoreCase("year")){
			tableName = "YR_CANH_BAO";
			title += " mức năm";
			if (eyear == null) {
				eyear = String.valueOf(cal.get(Calendar.YEAR));
			}
			if (syear == null) {
				syear = eyear;
			}
		}
		else
		{
			title += " mức ngày";
			tableName = "DY_CANH_BAO";
			if (start == null || start.equals("")||(start!=null && !start.equals("") && DateTools.isValid("dd/MM/yyyy", start)==false))
			{
				start = df.format(new Date());
			}
			if (end == null || end.equals("")||(end!=null && !end.equals("") && DateTools.isValid("dd/MM/yyyy", end)==false))
			{
				end = df.format(new Date());
			}
		}
		// do du lieu ra grid
		List<CTableConfig> columnList = cTableConfigDAO.getTableConfigsForGrid(tableName);	
		// Lay du lieu cac cot an hien cua grid
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList(tableName);
 		url = "data.htm?sumLevel=" + sumLevel + "&region=" + region + "&dept=" + dept + "&start=" +start + "&end=" +end +"&syear=" +syear + "&eyear=" +eyear;
 		String gridManage = HelpTableConfigs.getGridPagingUrl(columnList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", null,"multiplerowsextended", "Y");
 		/*
 		List<String> paramList = new ArrayList<String>();
		paramList.add(sumLevel==null?"":sumLevel);
		paramList.add(region==null?"":region);
		paramList.add(dept==null?"":dept);
		paramList.add(start==null?"":start);
		paramList.add(end==null?"":end);
		paramList.add(syear==null?"":syear);
		paramList.add(eyear==null?"":eyear);
		String dataArray = ExportTools.exportToStringArray("PK_XU_LY_CANH_BAO.PR_GET_CANH_BAO(?,?,?,?,?,?,?,?)",paramList,columnList);
		String gridManage = HelpTableConfigs.getGridReportDataString(columnList, "jqxgrid", dataArray, "jqxlistbox", columnList, "Menu", null,"Y",columnList.get(0).getGroupFuntion(),null);
		*/
 		model.addAttribute("gridManage", gridManage);
		model.addAttribute("regionList", regionList);
		model.addAttribute("deptList", deptList);
		model.addAttribute("dept", dept);
		model.addAttribute("region", region);
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		model.addAttribute("title", title);
		model.addAttribute("sumLevel", sumLevel);
		model.addAttribute("syear", syear);
		model.addAttribute("eyear", eyear);
//		model.addAttribute("dataList",dataList);
		return new ModelAndView("jsp/xuLySuCoList");
	}
	
	@RequestMapping("data")
	public void data(@RequestParam(required = false) String sumLevel, @RequestParam(required = false) String region,
			@RequestParam(required = false) String dept, @RequestParam(required = false) String start,
			@RequestParam(required = false) String end, @RequestParam(required = false) String syear,
			@RequestParam(required = false) String eyear, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		List<RAlarmLog> dataList = new ArrayList<RAlarmLog>();
		dataList = rAlarmLog.getTyLeCanhBao(sumLevel, region, dept, start, end,syear,eyear);
		Gson gs = new Gson();
		PrintWriter out;
		out = response.getWriter();
		String jsonDataList = gs.toJson(dataList);
		response.setContentType("application/json");
		out.println(jsonDataList);
		out.close();
	}
}
