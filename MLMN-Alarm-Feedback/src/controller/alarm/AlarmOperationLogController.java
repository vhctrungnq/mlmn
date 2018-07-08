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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import vo.CTableConfig;

import vo.RAlarmOperationLog;
import vo.SYS_PARAMETER;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;
import vo.dictionary.TableConfigsHelper;

import dao.CTableConfigDAO;
import dao.RAlarmOperationLogDAO;
import dao.SYS_PARAMETERDAO;
/**
 * Function        : QUAN LY OPERATION LOG
 * Created By      : BUIQUANG
 * Create Date     : 11/02/2014
 * Modified By     : 
 * Modify Date     : 
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/alarm/operation-log/*")
public class AlarmOperationLogController {

	@Autowired
	RAlarmOperationLogDAO rAlarmOperationLogDAO;
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping(value = "{function}")
    public String list(@RequestParam(required = false) String startDate,
					   @RequestParam(required = false) String endDate, 	   
					   @RequestParam(required = false) String vendor,
					   @RequestParam(required = false) String neType,
					   @RequestParam(required = false) String neName,
					   @RequestParam(required = false) String askUser,
					   @PathVariable String function,  ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "OperationLog_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		loadDataCombobox(model);
		// Ngay thang
		if (startDate == null || startDate.equals("")|| DateTools.isValid("dd/MM/yyyy", startDate)==false) {			
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DAY_OF_MONTH, -1);
			
			startDate = df.format(cal.getTime());
		
		}
		if (endDate == null || endDate.equals("")|| DateTools.isValid("dd/MM/yyyy", endDate)==false)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			endDate = df.format(cal.getTime());
		}
		String filterUrl = "";
		String temp = "";
		if(vendor != null){
			filterUrl += "vendor=" + vendor; 
			temp = "&";
		}
		if(neType != null){
			filterUrl += temp + "neType=" + neType.trim(); 
			temp = "&";
		}
		if(neName != null){
			filterUrl += temp + "neName=" + neName.trim(); 
			temp = "&";
		}
		if(askUser != null){
			filterUrl += temp + "askUser=" + askUser.trim(); 
			temp = "&";
		}
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
		
		if(function.equals("tong-hop"))
		{
			//do du lieu ra grid
			List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("R_ALARM_OPERATION_LOG_TOTAL");
			//Lay du lieu cac cot an hien cua grid
			List<CTableConfig> listSource = cTableConfigDAO.getColumnList("R_ALARM_OPERATION_LOG_TOTAL");
			String url = "dataTotal.htm" + filterUrl;
			//Grid
			String gridReport = TableConfigsHelper.getGridReportPaging(configList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", "Yes", null, "singlecell", null);
			model.addAttribute("gridReport", gridReport);
		}
		else if(function.equals("chi-tiet"))
		{
			//do du lieu ra grid
			List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("R_ALARM_OPERATION_LOG_DETAIL");
			//Lay du lieu cac cot an hien cua grid
			List<CTableConfig> listSource = cTableConfigDAO.getColumnList("R_ALARM_OPERATION_LOG_DETAIL");
			String url = "data.htm" + filterUrl;
			//Grid
			String gridReport = TableConfigsHelper.getGridReportPaging(configList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", null, null, "singlecell", null);
			model.addAttribute("gridReport", gridReport);
		}
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("function", function);
		model.addAttribute("vendor", vendor);
		model.addAttribute("neType", neType);
		model.addAttribute("neName", neName);
		model.addAttribute("askUser", askUser);
		return "jspalarm/operationLogList";
	}
	
	private void loadDataCombobox(ModelMap model)
	{
		// Danh sach nha cung cap
		List<SYS_PARAMETER> vendorList = sysParameterDao.getVendorList();
		model.addAttribute("vendorList", vendorList);
		
		
	}
	
	// Tab tong hop
	@RequestMapping("/dataTotal")
	public void dataTotal(@RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate, 	   
			   @RequestParam(required = false) String vendor,
			   @RequestParam(required = false) String neType,
			   @RequestParam(required = false) String neName,
			   @RequestParam(required = false) String askUser,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<RAlarmOperationLog> rAlarmOperationLog = null;
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
		List<CTableConfig> columnList = cTableConfigDAO.getTableConfigGet("R_ALARM_OPERATION_LOG_TOTAL", sortfield);
		List<CTableConfig> tableConfigList = cTableConfigDAO.getTableConfigGet("R_ALARM_OPERATION_LOG_TOTAL", null);
		
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
		try
		{
			rAlarmOperationLog = rAlarmOperationLogDAO.getOperationLogTotal(startDate, endDate, vendor, neType, neName, askUser, startRecord, endRecord, sortfield, sortorder, strWhere);
			totalRow = rAlarmOperationLogDAO.countOperationLogTotal(startDate, endDate, vendor, neType, neName, askUser, strWhere);
		}
		catch(Exception exp)
		{
		}
		String strjson = "[{\"TotalRows\":\""+totalRow+"\"},";
		strWhere = strWhere.replace("%", "___");
		strjson += "{\"strWhere\":\""+strWhere+"\"},";
		strjson += "{\"sortfield\":\""+sortfield+"\"},";
		strjson += "{\"sortorder\":\""+sortorder+"\"},";
        strjson += "{\"Rows\":" ;
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(rAlarmOperationLog);
		strjson+= jsonCartList;
	    strjson += "}]";
	    
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(strjson);
		out.close();
		
	}
	
	// Tab chi tiet
	@RequestMapping("/data")
	public void data(@RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate, 	   
			   @RequestParam(required = false) String vendor,
			   @RequestParam(required = false) String neType,
			   @RequestParam(required = false) String neName,
			   @RequestParam(required = false) String askUser,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<RAlarmOperationLog> rAlarmOperationLog = null;
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
		List<CTableConfig> columnList = cTableConfigDAO.getTableConfigGet("R_ALARM_OPERATION_LOG_DETAIL", sortfield);
		List<CTableConfig> tableConfigList = cTableConfigDAO.getTableConfigGet("R_ALARM_OPERATION_LOG_DETAIL", null);
		
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
		try
		{
			rAlarmOperationLog = rAlarmOperationLogDAO.getOperationLogDetail(startDate, endDate, vendor, neType, neName, askUser, startRecord, endRecord, sortfield, sortorder, strWhere);
			totalRow = rAlarmOperationLogDAO.countOperationLogDetail(startDate, endDate, vendor, neType, neName, askUser, strWhere);
		}
		catch(Exception exp)
		{
		}
		String strjson = "[{\"TotalRows\":\""+totalRow+"\"},";
		strWhere = strWhere.replace("%", "___");
		strjson += "{\"strWhere\":\""+strWhere+"\"},";
		strjson += "{\"sortfield\":\""+sortfield+"\"},";
		strjson += "{\"sortorder\":\""+sortorder+"\"},";
        strjson += "{\"Rows\":" ;
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(rAlarmOperationLog);
		strjson+= jsonCartList;
	    strjson += "}]";
	    
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(strjson);
		out.close();
		
	}
	
	// Danh sach user
	@RequestMapping("userList")
	public @ResponseBody
	List<RAlarmOperationLog> userList(@RequestParam(required = false) String team) {
		List<RAlarmOperationLog>  userList = rAlarmOperationLogDAO.getUserForOperationLog();
		
		return 	userList;
	}
	
	@RequestMapping("exportData")
  	public ModelAndView exportData(
  			@RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate, 	   
			   @RequestParam(required = false) String vendor,
			   @RequestParam(required = false) String neType,
			   @RequestParam(required = false) String neName,
			   @RequestParam(required = false) String askUser,
			   @RequestParam(required = false) String function,
			   @RequestParam(required = false) String sortfield,
			   @RequestParam(required = false) String sortorder,
			   @RequestParam(required = false) String strWhere,
  			HttpServletRequest request, HttpServletResponse response) throws Exception {
  		strWhere = strWhere.replace("___", "%");
  		
  		// temp file
		String basePath = request.getSession().getServletContext().getRealPath("");
		String dataDir = basePath + "/upload";
		String tempName = UUID.randomUUID().toString();
		String ext = "xls";
		String outfile = tempName + "." + ext;
		File tempFile = new File(dataDir + "/" + outfile);
		if(function.equals("tong-hop"))
		{
			List<RAlarmOperationLog> rAlarmOperationLog = rAlarmOperationLogDAO.getOperationLogTotal(startDate, endDate, vendor, neType, neName, askUser, null, null, sortfield, sortorder, strWhere);
			exportReportTotal(tempFile, rAlarmOperationLog);
		}
		else if(function.equals("chi-tiet"))
		{
			List<RAlarmOperationLog> rAlarmOperationLog = rAlarmOperationLogDAO.getOperationLogDetail(startDate, endDate, vendor, neType, neName, askUser, null, null, sortfield, sortorder, strWhere);
			exportReport(tempFile, rAlarmOperationLog);
		}
		
		// return
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String filename = "OperationLog_"+ dateNow;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();
  		return null;
  	}
	
	private void exportReportTotal(File tempFile, List<RAlarmOperationLog> rAlarmOperationLog) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("Report", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			int i=0;
			
			Label label0 = new Label(i, 0, "Date", cellFormatHeader);
			sheet.addCell(label0);
			i++;
			Label label1 = new Label(i, 0, "Vendor", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "NE Type", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "NE Name", cellFormatHeader);
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "Qty", cellFormatHeader);
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "Dangerous", cellFormatHeader);
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "User", cellFormatHeader);
			sheet.addCell(label6);
			i = 1;
			
			for (RAlarmOperationLog record : rAlarmOperationLog) {
				int j=0;
				Label record0 = new Label(j, i, record.getStartTimeStr(), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,record.getVendor(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getNeType(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getNeName(), cellFormat);
				sheet.addCell(record3);
				j++;
				Label record4 = new Label(j, i, record.getQtyStr(), cellFormat);
				sheet.addCell(record4);
				j++;
				Label record5 = new Label(j, i, record.getDangerousStr(), cellFormat);
				sheet.addCell(record5);
				j++;
				Label record6 = new Label(j, i, record.getAskUser(), cellFormat);
				sheet.addCell(record6);
				
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
	
	private void exportReport(File tempFile, List<RAlarmOperationLog> rAlarmOperationLog) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("Report", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			int i=0;
			
			Label label0 = new Label(i, 0, "Vendor", cellFormatHeader);
			sheet.addCell(label0);
			i++;
			Label label1 = new Label(i, 0, "NE Type", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "NE Name", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "Command", cellFormatHeader);
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "Start Date", cellFormatHeader);
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "End Date", cellFormatHeader);
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "User IP", cellFormatHeader);
			sheet.addCell(label6);
			i++;
			Label label7 = new Label(i, 0, "User", cellFormatHeader);
			sheet.addCell(label7);
			
			i = 1;
			
			for (RAlarmOperationLog record : rAlarmOperationLog) {
				int j=0;
				Label record0 = new Label(j, i, record.getVendor(), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,record.getNeType(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getNeName(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getCommand(), cellFormat);
				sheet.addCell(record3);
				j++;
				Label record4 = new Label(j, i, record.getStartTimeStr(), cellFormat);
				sheet.addCell(record4);
				j++;
				Label record5 = new Label(j, i, record.getEndTimeStr(), cellFormat);
				sheet.addCell(record5);
				j++;
				Label record6 = new Label(j, i, record.getUserIp(), cellFormat);
				sheet.addCell(record6);
				j++;
				Label record7 = new Label(j, i, record.getAskUser(), cellFormat);
				sheet.addCell(record7);
				
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
