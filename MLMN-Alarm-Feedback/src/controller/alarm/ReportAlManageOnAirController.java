package controller.alarm;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;

import dao.AlManageOnAirDAO;
import dao.CTableConfigDAO;

import vo.AlManageOnAir2g3g;
import vo.CTableConfig;
import vo.alarm.utils.DateTools;
import vo.dictionary.TableConfigsHelper;

/**
 * Function        : Bao cao ngay, tong hop cua quan ly lich phat song
 * Created By      : BUIQUANG
 * Create Date     : 27/11/2013
 * Modified By     : 
 * Modify Date     : 
 * @author BUIQUANG
 * Description     :
 */

@Controller
@RequestMapping("/alarm/report-al-manage-on-air/*")
public class ReportAlManageOnAirController {

	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	@Autowired
	private AlManageOnAirDAO alManageOnAirDAO;
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	
	@RequestMapping(value = "{function}")
    public String list(@RequestParam(required = false) String startDate,
					   @RequestParam(required = false) String endDate,
					   @RequestParam(required = false) String year, 	   
					   @PathVariable String function,  ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "RPOnAir_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		// Ngay thang
		if (startDate == null || startDate.equals("")|| DateTools.isValid("dd/MM/yyyy", startDate)==false) {			
			
			startDate = df.format(DateTools.startMonth(new Date()));
		
		}
		if (endDate == null || endDate.equals("")|| DateTools.isValid("dd/MM/yyyy", endDate)==false)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			endDate = df.format(cal.getTime());
		}
		
		String filterUrl = "";
		String temp = "";
		if(startDate != null){
			filterUrl += temp + "startDate=" + startDate.trim();
			temp = "&";
		}
		if(endDate != null)
		{
			filterUrl += temp + "endDate=" + endDate.trim();
		}
		if(filterUrl != "")
			filterUrl = "?" + filterUrl;
		
		
		if(function.equals("theo-ngay"))
		{
			//do du lieu ra grid
			List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("V_AL_MANAGE_ON_AIR_2G_3G");
			//Lay du lieu cac cot an hien cua grid
			List<CTableConfig> listSource = cTableConfigDAO.getColumnList("V_AL_MANAGE_ON_AIR_2G_3G");
			String url = "dataTotal.htm" + filterUrl;
			//lay du lieu column group cua grid
			String groupColumn="";
			groupColumn = columnGroup(groupColumn, "V_AL_MANAGE_ON_AIR_2G_3G");
			//Grid
			String gridReport = TableConfigsHelper.getGridReportDontPaging(configList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", "Yes", "Yes", "multiplerowsextended", groupColumn);
			model.addAttribute("gridReport", gridReport);
			
			//do du lieu ra grid
			List<CTableConfig> configListProjectCode  = cTableConfigDAO.getTableConfigsForGrid("V_AL_MANAGE_ON_AIR_DATE_BY_CODE");
			//Lay du lieu cac cot an hien cua grid
			List<CTableConfig> listSourceProjectCode  = cTableConfigDAO.getColumnList("V_AL_MANAGE_ON_AIR_DATE_BY_CODE");
			String urlProjectCode = "dataTotalByProjectCode.htm" + filterUrl;
			//Grid
			String gridReportProjectCode = TableConfigsHelper.getGridReportDontPaging(configListProjectCode, "jqxgridSiteType", urlProjectCode, "jqxlistboxSiteType", listSourceProjectCode, "MenuSiteType", "Yes", "Yes", "multiplerowsextended", groupColumn);
			model.addAttribute("gridReportSiteType", gridReportProjectCode);
		}
		else if(function.equals("luy-tien")){
			//do du lieu ra grid
			List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("V_AL_MANAGE_ON_AIR_2G_3G_PROJECT_TYPE");
			//Lay du lieu cac cot an hien cua grid
			List<CTableConfig> listSource = cTableConfigDAO.getColumnList("V_AL_MANAGE_ON_AIR_2G_3G_PROJECT_TYPE");
			String url = "dataProjectType.htm" + filterUrl;
			//lay du lieu column group cua grid
			String groupColumn="";
			groupColumn = columnGroup(groupColumn, "V_AL_MANAGE_ON_AIR_2G_3G_PROJECT_TYPE");
			//Grid
			String gridReport = TableConfigsHelper.getGridReportDontPaging(configList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", "Yes", "Yes", "multiplerowsextended", groupColumn);
			model.addAttribute("gridReport", gridReport);
			
			//do du lieu ra grid
			List<CTableConfig> configListSiteType = cTableConfigDAO.getTableConfigsForGrid("V_AL_MANAGE_ON_AIR_2G_3G_SITE_TYPE");
			//Lay du lieu cac cot an hien cua grid
			List<CTableConfig> listSourceSiteType = cTableConfigDAO.getColumnList("V_AL_MANAGE_ON_AIR_2G_3G_SITE_TYPE");
			String urlSiteType = "dataSynthesis.htm" + filterUrl;
			//Grid
			String gridReportSiteType = TableConfigsHelper.getGridReportDontPaging(configListSiteType, "jqxgridSiteType", urlSiteType, "jqxlistboxSiteType", listSourceSiteType, "MenuSiteType", "Yes", "Yes", "multiplerowsextended", groupColumn);
			model.addAttribute("gridReportSiteType", gridReportSiteType);
			
			/*//do du lieu ra grid
			List<CTableConfig> configListProjectCode = cTableConfigDAO.getTableConfigsForGrid("V_AL_MANAGE_ON_AIR_2G_3G_SYNTHESIS");
			//Lay du lieu cac cot an hien cua grid
			List<CTableConfig> listSourceProjectCode = cTableConfigDAO.getColumnList("V_AL_MANAGE_ON_AIR_2G_3G_SYNTHESIS");
			String urlProjectCode = "dataSynthesis.htm" + filterUrl;
			//lay du lieu column group cua grid
			String groupColumnProjectCode="";
			groupColumnProjectCode = columnGroup(groupColumnProjectCode, "V_AL_MANAGE_ON_AIR_2G_3G_SYNTHESIS");
			//Grid
			String gridReportProjectCode = TableConfigsHelper.getGridReportDontPaging(configListProjectCode, "jqxgridSiteType", urlProjectCode, "jqxlistboxSiteType", listSourceProjectCode, "Menu", "Yes", "Yes", "multiplerowsextended", groupColumnProjectCode);
			model.addAttribute("gridReportSiteType", gridReportProjectCode);*/
			
		}
		else if(function.equals("tong-hop")){
			//do du lieu ra grid
			List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("V_AL_MANAGE_ON_AIR_2G_3G_SYNTHESIS");
			//Lay du lieu cac cot an hien cua grid
			List<CTableConfig> listSource = cTableConfigDAO.getColumnList("V_AL_MANAGE_ON_AIR_2G_3G_SYNTHESIS");
			String url = "dataSynthesis.htm" + filterUrl;
			//lay du lieu column group cua grid
			String groupColumn="";
			groupColumn = columnGroup(groupColumn, "V_AL_MANAGE_ON_AIR_2G_3G_SYNTHESIS");
			//Grid
			String gridReport = TableConfigsHelper.getGridReportDontPaging(configList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", "Yes", "Yes", "multiplerowsextended", groupColumn);
			model.addAttribute("gridReport", gridReport);
		}
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("year", year);
		model.addAttribute("function", function);
		return "jspalarm/alProject/reportAlManageOnAirList";
	}
	
	// Tab theo ngay theo du an chung
	@RequestMapping("/dataTotal")
	public void dataTotal(@RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate, 	   
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<AlManageOnAir2g3g> alManageOnAir2g3g = null;
		
		try
		{
			alManageOnAir2g3g = alManageOnAirDAO.getAlManageOnAir2g3g( startDate, endDate);
		}
		catch(Exception exp)
		{
		}
		
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(alManageOnAir2g3g);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
		
	}
	// Tab theo ngay theo du an con
	@RequestMapping("/dataTotalByProjectCode")
	public void dataTotalByProjectCode(@RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate, 	   
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<AlManageOnAir2g3g> alManageOnAir2g3g = null;
		
		try
		{
			alManageOnAir2g3g = alManageOnAirDAO.getAlManageOnAirByCode( startDate, endDate);
		}
		catch(Exception exp)
		{
		}
		
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(alManageOnAir2g3g);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
		
	}
	
	// Tab luy tien
	@RequestMapping("/dataProjectType")
	public void dataProjectType(
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate,
			@RequestParam(required = false) String year, 	   
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<AlManageOnAir2g3g> alManageOnAir2g3g = null;
		
		try
		{
			alManageOnAir2g3g = alManageOnAirDAO.getReportProjectType(startDate, endDate, year);
		}
		catch(Exception exp)
		{
		}
		
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(alManageOnAir2g3g);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
		
	}
	
	// Tab luy tien
	@RequestMapping("/dataSiteType")
	public void dataSiteType(
			@RequestParam(required = false) String year,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<AlManageOnAir2g3g> alManageOnAir2g3g = null;
		
		try
		{
			alManageOnAir2g3g = alManageOnAirDAO.getReportSiteType(year);
		}
		catch(Exception exp)
		{
		}
		
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(alManageOnAir2g3g);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
	}
	
	private String columnGroup(String groupColumn, String tableName){
		//lay du lieu column group cua grid
		List<String> columnGrid = cTableConfigDAO.getGroupList(tableName);
		String con2="";
		groupColumn =" var columngroups=[";
		for (String alarmTypeG : columnGrid) {
			groupColumn= groupColumn+		con2+"{ text: '"+alarmTypeG+"', align: 'center', name: '"+alarmTypeG+"' }";
			con2 = ",";
		}
		groupColumn= groupColumn+	"]; ";
		
		return groupColumn;
	}
	
	// Tab tong hop
	@RequestMapping("/dataSynthesis")
	public void dataSynthesis(
			@RequestParam(required = false) String year,
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<AlManageOnAir2g3g> alManageOnAir2g3g = null;
		
		try
		{
			alManageOnAir2g3g = alManageOnAirDAO.getReportSynthesis(startDate, endDate, year);
		}
		catch(Exception exp)
		{
		}
		
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(alManageOnAir2g3g);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
	}
			
}
