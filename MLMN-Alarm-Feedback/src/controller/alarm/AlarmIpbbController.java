package controller.alarm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vo.CTableConfig;
import vo.RAlarmLogIpbb;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;
import vo.dictionary.TableConfigsHelper;

import com.google.gson.Gson;

import dao.CTableConfigDAO;
import dao.RAlarmLogIpbbDAO;
import dao.SYS_PARAMETERDAO;
/**
 * Function        : QUAN LY IPBB LOG
 * Created By      : THANGPX
 * Create Date     : 18/06/2014
 * Modified By     : 
 * Modify Date     :  
 * Description     :
 */
@Controller
@RequestMapping("/alarmLog/other/ipbb")
public class AlarmIpbbController {

	@Autowired
	RAlarmLogIpbbDAO rAlarmLogIpbbDAO;
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	@RequestMapping(value = "{function}")
    public String list(@RequestParam(required = false) String startDate,
					   @RequestParam(required = false) String endDate, 	
					   @PathVariable String function,
					   @RequestParam(required = false) String host, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "IpbbLog_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		// Ngay thang
		if (startDate == null || startDate.equals("")|| DateTools.isValid("dd/MM/yyyy HH:mm", startDate)==false) {			

			startDate = df.format(DateTools.minusDay(new Date(),1));
		
		}
		if (endDate == null || endDate.equals("")|| DateTools.isValid("dd/MM/yyyy  HH:mm", endDate)==false)
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
		if(host != null){
			filterUrl += temp + "host=" + host.trim();
			temp = "&";
		}
		if(function != null){
			filterUrl += temp + "function=" + function.trim();
			temp = "&";
		}
		if(endDate != null)
		{
			filterUrl += temp + "endDate=" + endDate.trim();
		}
		if(filterUrl != "")
			filterUrl = "?" + filterUrl;
		
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("R_ALARM_LOG_IPBB");
		//Lay du lieu cac cot an hien cua grid
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList("R_ALARM_LOG_IPBB");
		String url = "data.htm" + filterUrl;
		//Grid
		String gridReport = TableConfigsHelper.getGridReportPaging(configList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", "Yes", null, "singlecell", null);
		
		model.addAttribute("function", function);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("host", host);
		model.addAttribute("gridReport", gridReport);
		return "jspalarm/QLCanhBao/rAlarmLogIpbb";
	}
	
	// Tab tong hop
	@RequestMapping("/data")
	public void dataTotal(@RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate, 	   
			   @RequestParam(required = false) String host,  
			   @RequestParam(required = false) String function, 
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException { 
		
		
		//Get username
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		
		int startRecord = 0, endRecord = 0, pageSize = 100;
		String sortfield = "DAY";
		String sortorder = "";
		int pageNum = Integer.parseInt(request.getParameter("pagenum"));
		if(pageNum == -1)
			pageNum = 1;
		if(!request.getParameter("pagesize").equals(""))
			 pageSize = Integer.parseInt(request.getParameter("pagesize"));
		else pageSize = 100;
		sortfield = request.getParameter("sortdatafield");
		sortorder = request.getParameter("sortorder");
		List<CTableConfig> columnList = cTableConfigDAO.getTableConfigGet("R_ALARM_LOG_IPBB", sortfield);
		List<CTableConfig> tableConfigList = cTableConfigDAO.getTableConfigGet("R_ALARM_LOG_IPBB", null);
		
		// Tim kiem tren grid
		String strWhere = HelpTableConfigs.filter(request);
		for(CTableConfig column: tableConfigList)
		{
			strWhere = strWhere.toUpperCase().replaceAll(column.getDataField().toUpperCase(), column.getTableColumn());
		}
		// Sap xep
		for(CTableConfig column: columnList)
		{
			sortfield = column.getTableColumn();
			break;
		}
		startRecord = pageNum*pageSize;
		endRecord = startRecord + pageSize;
		int totalRow =0;
		List<RAlarmLogIpbb> rAlarmIpbbLog =  rAlarmLogIpbbDAO.getAlarmLogIpbb(function,startDate, endDate, host, username, startRecord, endRecord, sortfield, sortorder, strWhere);
			totalRow = rAlarmLogIpbbDAO.countAlarmLogIpbb(function,startDate, endDate, host ,username, strWhere);
	 
		String strjson = "[{\"TotalRows\":\""+totalRow+"\"},";
		strWhere = strWhere.replace("%", "___");
		strjson += "{\"strWhere\":\""+strWhere+"\"},";
		strjson += "{\"sortfield\":\""+sortfield+"\"},";
		strjson += "{\"sortorder\":\""+sortorder+"\"},";
        strjson += "{\"Rows\":" ;
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(rAlarmIpbbLog);
		strjson+= jsonCartList;
	    strjson += "}]";
	    
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(strjson);
		out.close();
		
	}
	  
	@RequestMapping("exportData")
  	public ModelAndView exportData(
  			@RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate, 	   
			   @RequestParam(required = false) String host, 
			   @RequestParam(required = false) String function, 
			   @RequestParam(required = false) String sortfield,
			   @RequestParam(required = false) String sortorder,
			   @RequestParam(required = false) String strWhere,
  			HttpServletRequest request, HttpServletResponse response) throws Exception {
  		strWhere = strWhere.replace("___", "%");
  		
  		// temp file
  		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		String basePath = request.getSession().getServletContext().getRealPath("");
		String dataDir = basePath + "/upload";
		String tempName = UUID.randomUUID().toString();
		String ext = "xls";
		String outfile = tempName + "." + ext;
		File tempFile = new File(dataDir + "/" + outfile);
		List<RAlarmLogIpbb> rAlarmIpbbLog =  rAlarmLogIpbbDAO.getAlarmLogIpbb(function,startDate, endDate, host, username, null, null, sortfield, sortorder, strWhere);		
		exportReport(tempFile, rAlarmIpbbLog);
	 
		
		// return
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String filename = "Ipbb_Report_"+ dateNow;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();
  		return null;
  	}
	 
	private void exportReport(File tempFile, List<RAlarmLogIpbb> rAlarmIpbbLog) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("Report", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			int i=0;
			
			Label label0 = new Label(i, 0, "Log Time", cellFormatHeader);
			sheet.addCell(label0);
			i++;
			Label label1 = new Label(i, 0, "Host", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "Vendor", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "Message", cellFormatHeader);
			sheet.addCell(label3); 
			i = 1;
			
			for (RAlarmLogIpbb record : rAlarmIpbbLog) {
				int j=0;
				Label record0 = new Label(j, i, df_full.format(record.getLogtime()), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,record.getHost(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getVendor(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getMessage(), cellFormat);
				sheet.addCell(record3); 
				
				i++;
			}
			workbook.write();
			workbook.close();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
