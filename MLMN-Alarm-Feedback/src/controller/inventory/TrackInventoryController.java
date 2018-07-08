package controller.inventory;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import dao.CTableConfigDAO;
import dao.IsoInventoryDAO;
import dao.IsoInventoryHistoryDAO;
import dao.IsoInventoryRemoveDAO;

import vo.CTableConfig;
import vo.IsoInventory;
import vo.IsoInventoryHistory;
import vo.IsoInventoryRemove;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;

/**
 * Ten file: IsoEquipmentController.java
 * Muc dich: Theo dõi tài nguyên mạng lưới mới phát sinh, di chuyển và gỡ khỏi hệ thống
 * @author QuangBui
 * Ngay tao: 18/09/2013
 * Lich su thay doi:
 *  
 */
@Controller
@RequestMapping("/iso/theo-doi-tai-nguyen-mang-luoi/*")
public class TrackInventoryController {

	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired
	private IsoInventoryDAO isoInventoryDAO;
	@Autowired
	private IsoInventoryHistoryDAO isoInventoryHistoryDAO;
	@Autowired
	private IsoInventoryRemoveDAO isoInventoryRemoveDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@RequestMapping("list")
	public ModelAndView list(
			   @RequestParam(required = false) String status,
			   @RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate,
			   @RequestParam(required = false) String productCode,
			   @RequestParam(required = false) String oldNe,
			   @RequestParam(required = false) String ne,
			   @RequestParam(required = false) String productName,
			   @RequestParam(required = false) String locationName,
			   @RequestParam(required = false) String seriNo, ModelMap model, HttpServletRequest request, HttpServletResponse response){
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "TaiNguyen" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
				
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
		if(startDate != null){
			filterUrl += "startDate=" + startDate; 
			temp = "&";
		}
		if(endDate != null){
			filterUrl += temp + "endDate=" + endDate; 
			temp = "&";
		}
		if(oldNe != null){
			filterUrl += temp + "oldNe=" + oldNe.trim();
			temp = "&";
		}
		if(ne != null){
			filterUrl += temp + "ne=" + ne.trim();
			temp = "&";
		}
		if(productCode != null){
			filterUrl += temp + "productCode=" + productCode.trim();
			temp = "&";
		}
		if(productName != null){
			filterUrl += temp + "productName=" + productName.trim();
			temp = "&";
		}
		if(locationName != null){
			filterUrl += temp + "locationName=" + locationName.trim();
			temp = "&";
		} 
		if(seriNo != null){
			filterUrl += temp + "seriNo=" + seriNo.trim();
			temp = "&";
		}
		if(status != null){
			filterUrl += temp + "status=" + status;
		}
		if(filterUrl != "")
			filterUrl = "?" + filterUrl;
		
		if(status.equals("N")){
		
			model.addAttribute("aliasName", "NE Parent");
			//do du lieu ra grid
			List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("TRACK_INVENTORY_NEW");
			//lay du lieu column cua grid
			model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
			//Lay du lieu datafield cua grid
			model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
			//Lay du lieu cac cot an hien cua grid
			List<CTableConfig> columnList = cTableConfigDAO.getColumnList("TRACK_INVENTORY_NEW");
			model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
			
			//Lay duong link url de lay du lieu do vao grid
			model.addAttribute("url", "dataCardNew.htm" + filterUrl);
		}
		else if(status.equals("H")){
			model.addAttribute("aliasName", "Old Ne Name");
			//do du lieu ra grid
			List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("TRACK_INVENTORY_HISTORY");
			//lay du lieu column cua grid
			model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
			//Lay du lieu datafield cua grid
			model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
			//Lay du lieu cac cot an hien cua grid
			List<CTableConfig> columnList = cTableConfigDAO.getColumnList("TRACK_INVENTORY_HISTORY");
			model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
			
			//Lay duong link url de lay du lieu do vao grid
			model.addAttribute("url", "dataCardHistory.htm" + filterUrl);
		}
		else if(status.equals("R")){
			model.addAttribute("aliasName", "NE Parent");
			//do du lieu ra grid
			List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("TRACK_INVENTORY_REMOVE");
			//lay du lieu column cua grid
			model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
			//Lay du lieu datafield cua grid
			model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
			//Lay du lieu cac cot an hien cua grid
			List<CTableConfig> columnList = cTableConfigDAO.getColumnList("TRACK_INVENTORY_REMOVE");
			model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
			
			//Lay duong link url de lay du lieu do vao grid
			model.addAttribute("url", "dataCardRemove.htm" + filterUrl);
		}
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		model.addAttribute("productCode", productCode);
		model.addAttribute("productName", productName);
		model.addAttribute("locationName", locationName);
		model.addAttribute("oldNeName", oldNe);
		model.addAttribute("neName", ne);
		model.addAttribute("seriNo", seriNo);		
		return new ModelAndView("jspiso/inventoryList");
	}
	
	@RequestMapping("/dataCardNew")
	public void dataCardNew(@RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate, 
			   @RequestParam(required = false) String status,
			   @RequestParam(required = false) String oldNe,
			   @RequestParam(required = false) String ne,
			   @RequestParam(required = false) String productCode,
			   @RequestParam(required = false) String productName,
			   @RequestParam(required = false) String locationName,
			   @RequestParam(required = false) String seriNo,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<IsoInventory> trackNewList = null;
		int startRecord = 0, endRecord = 0, pageSize = 100;
		String sortfield = "";
		String sortorder = "";
		int pageNum = Integer.parseInt(request.getParameter("pagenum"));
		if(pageNum == -1)
			pageNum = 1;
		if(!request.getParameter("pagesize").equals(""))
			 pageSize = Integer.parseInt(request.getParameter("pagesize"));
		else pageSize = 100;
		sortfield = request.getParameter("sortdatafield");
		sortorder = request.getParameter("sortorder");
		List<CTableConfig> columnList = cTableConfigDAO.getTableConfigGet("TRACK_INVENTORY_NEW", sortfield);
		// Tim kiem tren grid
		String strWhere = HelpTableConfigs.filter(request);
		for(CTableConfig column: columnList)
		{
			strWhere = strWhere.toUpperCase().replaceAll(column.getDataField().toUpperCase(), column.getTableColumn());
		}
		// Sap xep
		for(CTableConfig column: columnList)
		{
			sortfield = column.getTableColumn();
			break;
		}
		if(sortfield==null)
			sortfield = "INITIALIZE_DATE";
		if(sortorder==null)
			sortorder = "ASC";
		startRecord = pageNum*pageSize;
		endRecord = startRecord + pageSize;
		int totalRow =0;
		try
		{
			trackNewList = isoInventoryDAO.getInventoryTrackNewFilter(startDate, endDate, oldNe, ne, productCode, productName, locationName, seriNo, status, startRecord, endRecord, sortfield, sortorder, strWhere);
			totalRow = isoInventoryDAO.countInventoryTrackFilter(startDate, endDate, oldNe, ne, productCode, productName, locationName, seriNo, status, strWhere);
		}
		catch(Exception exp)
		{
		}
		String strjson = "[{\"TotalRows\":\""+totalRow+"\"},";
		strjson += "{\"strWhere\":\""+strWhere+"\"},";
		strjson += "{\"sortfield\":\""+sortfield+"\"},";
		strjson += "{\"sortorder\":\""+sortorder+"\"},";
        strjson += "{\"Rows\":";
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(trackNewList);
		strjson+= jsonCartList;
	    strjson += "}]";
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(strjson);
		out.close();
	}
	
	@RequestMapping("/dataCardHistory")
	public void dataCardHistory(@RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate, 
			   @RequestParam(required = false) String status,
			   @RequestParam(required = false) String oldNe,
			   @RequestParam(required = false) String ne,
			   @RequestParam(required = false) String productCode,
			   @RequestParam(required = false) String productName,
			   @RequestParam(required = false) String locationName,
			   @RequestParam(required = false) String seriNo,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<IsoInventoryHistory> trackHistoryList = null;
		int startRecord = 0, endRecord = 0, pageSize = 100;
		String sortfield = "";
		String sortorder = "";
		int pageNum = Integer.parseInt(request.getParameter("pagenum"));
		if(pageNum == -1)
			pageNum = 1;
		if(!request.getParameter("pagesize").equals(""))
			 pageSize = Integer.parseInt(request.getParameter("pagesize"));
		else pageSize = 100;
		sortfield = request.getParameter("sortdatafield");
		sortorder = request.getParameter("sortorder");
		List<CTableConfig> columnList = cTableConfigDAO.getTableConfigGet("TRACK_INVENTORY_HISTORY", sortfield);
		// Tim kiem tren grid
		String strWhere = HelpTableConfigs.filter(request);
		for(CTableConfig column: columnList)
		{
			strWhere = strWhere.replaceAll(column.getDataField(), column.getTableColumn());
		}
		// Sap xep
		for(CTableConfig column: columnList)
		{
			sortfield = column.getTableColumn();
			break;
		}
		if(sortfield==null)
			sortfield = "DAY";
		if(sortorder==null)
			sortorder = "ASC";
		startRecord = pageNum*pageSize;
		endRecord = startRecord + pageSize;
		int totalRow =0;
		try{
			trackHistoryList= isoInventoryHistoryDAO.getInventoryTrackHistoryFilter(startDate, endDate, oldNe, ne, productCode, productName, locationName, seriNo, status, startRecord, endRecord, sortfield, sortorder, strWhere);
			totalRow = isoInventoryDAO.countInventoryTrackFilter(startDate, endDate, oldNe, ne, productCode, productName, locationName, seriNo, status, strWhere);
		}
		catch(Exception e){}
		String strjson = "[{\"TotalRows\":\""+totalRow+"\"},";
        strjson += "{\"Rows\":";
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(trackHistoryList);
		strjson+= jsonCartList;
	    strjson += "}]";
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(strjson);
		out.close();
	}
	
	@RequestMapping("/dataCardRemove")
	public void dataCardRemove(@RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate, 
			   @RequestParam(required = false) String status,
			   @RequestParam(required = false) String oldNe,
			   @RequestParam(required = false) String ne,
			   @RequestParam(required = false) String productCode,
			   @RequestParam(required = false) String productName,
			   @RequestParam(required = false) String locationName,
			   @RequestParam(required = false) String seriNo,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<IsoInventoryRemove> trackRemoveList = null;
		int startRecord = 0, endRecord = 0, pageSize = 100;
		String sortfield = "";
		String sortorder = "";
		int pageNum = Integer.parseInt(request.getParameter("pagenum"));
		if(pageNum == -1)
			pageNum = 1;
		if(!request.getParameter("pagesize").equals(""))
			 pageSize = Integer.parseInt(request.getParameter("pagesize"));
		else pageSize = 100;
		sortfield = request.getParameter("sortdatafield");
		sortorder = request.getParameter("sortorder");
		List<CTableConfig> columnList = cTableConfigDAO.getTableConfigGet("TRACK_INVENTORY_REMOVE", sortfield);
		// Tim kiem tren grid
		String strWhere = HelpTableConfigs.filter(request);
		for(CTableConfig column: columnList)
		{
			strWhere = strWhere.replaceAll(column.getDataField(), column.getTableColumn());
		}
		// Sap xep
		for(CTableConfig column: columnList)
		{
			sortfield = column.getTableColumn();
			break;
		}
		if(sortfield==null)
			sortfield = "DAY";
		if(sortorder==null)
			sortorder = "ASC";
		startRecord = pageNum*pageSize;
		endRecord = startRecord + pageSize;
		int totalRow =0;
		try{
			trackRemoveList= isoInventoryRemoveDAO.getInventoryTrackRemoveFilter( startDate, endDate, oldNe, ne, productCode, productName, locationName, seriNo, status, startRecord, endRecord, sortfield, sortorder, strWhere);
			totalRow = isoInventoryDAO.countInventoryTrackFilter(startDate, endDate, oldNe, ne, productCode, productName, locationName, seriNo, status, strWhere);
		}
		catch(Exception e){}
		String strjson = "[{\"TotalRows\":\""+totalRow+"\"},";
        strjson += "{\"Rows\":";
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(trackRemoveList);
		strjson+= jsonCartList;
	    strjson += "}]";
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(strjson);
		out.close();
	}
	
	@RequestMapping("exportTrackInventory")
  	public ModelAndView reportAlarmLog(@RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate, 
			   @RequestParam(required = false) String status,
			   @RequestParam(required = false) String oldNe,
			   @RequestParam(required = false) String ne,
			   @RequestParam(required = false) String productCode,
			   @RequestParam(required = false) String productName,
			   @RequestParam(required = false) String locationName,
			   @RequestParam(required = false) String seriNo,
			   @RequestParam(required = false) String sortfield,
			   @RequestParam(required = false) String sortorder,
			   @RequestParam(required = false) String strWhere,
  			HttpServletRequest request, HttpServletResponse response) throws Exception {
  		
  		if(productCode != null)
  			productCode = productCode.trim();
  		if(seriNo != null)
  			seriNo = seriNo.trim();
  		
  		// temp file
		String basePath = request.getSession().getServletContext().getRealPath("");
		String dataDir = basePath + "/upload";
		String tempName = UUID.randomUUID().toString();
		String ext = "xls";
		String outfile = tempName + "." + ext;
		File tempFile = new File(dataDir + "/" + outfile);
		if(status.equals("N")){
			List<IsoInventory> trackNewList = isoInventoryDAO.getInventoryTrackNewFilter(startDate, endDate, oldNe, ne, productCode, productName, locationName, seriNo, status, null, null, sortfield, sortorder, strWhere);
			exportReportTrackNew(tempFile, trackNewList);
		}
		else if(status.equals("H")){
			List<IsoInventoryHistory> trackHistoryList= isoInventoryHistoryDAO.getInventoryTrackHistoryFilter(startDate, endDate, oldNe, ne, productCode, productName, locationName, seriNo, status, null, null, sortfield, sortorder, strWhere);
			exportReportTrackHistory(tempFile, trackHistoryList);
		}
		else if(status.equals("R"))
		{
			List<IsoInventoryRemove> trackRemoveList = isoInventoryRemoveDAO.getInventoryTrackRemoveFilter(startDate, endDate, oldNe, ne, productCode, productName, locationName, seriNo, status, null, null, sortfield, sortorder, strWhere);
			exportReportTrackRemove(tempFile, trackRemoveList);
		}
		
		// return
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String filename = "TaiNguyen_"+ dateNow;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();
  		return null;
  	}
  	
  	private void exportReportTrackNew(File tempFile, List<IsoInventory> trackNewList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("Track Report", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			int i=0;
			
			Label label0 = new Label(i, 0, "Detection Date", cellFormatHeader);
			sheet.addCell(label0);
			i++;
			Label label1 = new Label(i, 0, "NE Name", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "NE Parent", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "NE Type", cellFormatHeader);
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "Product Name", cellFormatHeader);
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "Product Code", cellFormatHeader);
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "Seri No", cellFormatHeader);
			sheet.addCell(label6);
			i++;
			Label label7 = new Label(i, 0, "Rack", cellFormatHeader);
			sheet.addCell(label7);
			i++;
			Label label8 = new Label(i, 0, "Subrack", cellFormatHeader);
			sheet.addCell(label8);
			i++;
			Label label9 = new Label(i, 0, "Slot", cellFormatHeader);
			sheet.addCell(label9);
			i++;
			Label label10 = new Label(i, 0, "Software vs", cellFormatHeader);
			sheet.addCell(label10);
			i++;
			Label label11 = new Label(i, 0, "Product Date", cellFormatHeader);
			sheet.addCell(label11);
			i++;
			Label label12 = new Label(i	, 0, "Vendor", cellFormatHeader);
			sheet.addCell(label12);
			i++;
			Label label13 = new Label(i, 0, "District", cellFormatHeader);
			sheet.addCell(label13);
			i++;
			Label label14 = new Label(i, 0, "Province", cellFormatHeader);
			sheet.addCell(label14);
			i++;
			Label label15 = new Label(i, 0, "Location", cellFormatHeader);
			sheet.addCell(label15);
			i++;
			Label label16 = new Label(i, 0, "Department", cellFormatHeader);
			sheet.addCell(label16);
			
			i = 1;
			
			for (IsoInventory record : trackNewList) {
				int j=0;
				Label record0 = new Label(j, i, record.getInitializeDateStr(), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,record.getNe(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getNeParent(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getNeType(), cellFormat);
				sheet.addCell(record3);
				j++;
				Label record4 = new Label(j, i, record.getProductName(), cellFormat);
				sheet.addCell(record4);
				j++;
				Label record5 = new Label(j, i, record.getProductCode(), cellFormat);
				sheet.addCell(record5);
				j++;
				Label record6 = new Label(j, i, record.getSeriNo(), cellFormat);
				sheet.addCell(record6);
				j++;
				Label record7 = new Label(j, i, record.getRack(), cellFormat);
				sheet.addCell(record7);
				j++;
				Label record8 = new Label(j, i, record.getSubrack(), cellFormat);
				sheet.addCell(record8);
				j++;
				Label record9 = new Label(j, i, record.getSlot(), cellFormat);
				sheet.addCell(record9);
				j++;
				Label record10 = new Label(j, i, record.getSwVersion(), cellFormat);
				sheet.addCell(record10);
				j++;
				Label record11 = new Label(j, i, record.getProductDateStr(), cellFormat);
				sheet.addCell(record11);
				j++;
				Label record12 = new Label(j, i, record.getVendor(), cellFormat);
				sheet.addCell(record12);
				j++;
				Label record13 = new Label(j, i, record.getDistrict(), cellFormat);
				sheet.addCell(record13);
				j++;
				Label record14 = new Label(j, i, record.getProductName(), cellFormat);
				sheet.addCell(record14);
				j++;
				Label record15 = new Label(j, i, record.getLocation(), cellFormat);
				sheet.addCell(record15);
				j++;
				Label record16 = new Label(j, i, record.getAbbreviated(), cellFormat);
				sheet.addCell(record16);
				
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
  	
  	private void exportReportTrackHistory(File tempFile, List<IsoInventoryHistory> trackHistoryList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("Track Report", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			int i=0;
			
			Label label0 = new Label(i, 0, "Date", cellFormatHeader);
			sheet.addCell(label0);
			i++;
			Label label1 = new Label(i, 0, "NE Type", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "NE Name", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "Old NE Name", cellFormatHeader);
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "Product Name", cellFormatHeader);
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "Product Code", cellFormatHeader);
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "Seri No", cellFormatHeader);
			sheet.addCell(label6);
			i++;
			Label label7 = new Label(i, 0, "Rack", cellFormatHeader);
			sheet.addCell(label7);
			i++;
			Label label8 = new Label(i, 0, "Subrack", cellFormatHeader);
			sheet.addCell(label8);
			i++;
			Label label9 = new Label(i, 0, "Slot", cellFormatHeader);
			sheet.addCell(label9);
			i++;
			Label label10 = new Label(i, 0, "Software vs", cellFormatHeader);
			sheet.addCell(label10);
			i++;
			Label label11 = new Label(i, 0, "Product Date", cellFormatHeader);
			sheet.addCell(label11);
			i++;
			Label label12 = new Label(i	, 0, "Detection Date", cellFormatHeader);
			sheet.addCell(label12);
			i++;
			Label label13 = new Label(i, 0, "Location", cellFormatHeader);
			sheet.addCell(label13);
			i++;
			Label label14 = new Label(i, 0, "Vendor", cellFormatHeader);
			sheet.addCell(label14);
			
			i = 1;
			
			for (IsoInventoryHistory record : trackHistoryList) {
				int j=0;
				Label record0 = new Label(j, i, record.getDayStr(), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,record.getNeType(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getNe(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getNeOld(), cellFormat);
				sheet.addCell(record3);
				j++;
				Label record4 = new Label(j, i, record.getProductName(), cellFormat);
				sheet.addCell(record4);
				j++;
				Label record5 = new Label(j, i, record.getProductCode(), cellFormat);
				sheet.addCell(record5);
				j++;
				Label record6 = new Label(j, i, record.getSeriNo(), cellFormat);
				sheet.addCell(record6);
				j++;
				Label record7 = new Label(j, i, record.getRack(), cellFormat);
				sheet.addCell(record7);
				j++;
				Label record8 = new Label(j, i, record.getSubrack(), cellFormat);
				sheet.addCell(record8);
				j++;
				Label record9 = new Label(j, i, record.getSlot(), cellFormat);
				sheet.addCell(record9);
				j++;
				Label record10 = new Label(j, i, record.getSwVersion(), cellFormat);
				sheet.addCell(record10);
				j++;
				Label record11 = new Label(j, i, record.getProductDateStr(), cellFormat);
				sheet.addCell(record11);
				j++;
				Label record12 = new Label(j, i, record.getInitializeDateStr(), cellFormat);
				sheet.addCell(record12);
				j++;
				Label record13 = new Label(j, i, record.getLocation(), cellFormat);
				sheet.addCell(record13);
				j++;
				Label record14 = new Label(j, i, record.getVendor(), cellFormat);
				sheet.addCell(record14);
				
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
		
  	private void exportReportTrackRemove(File tempFile, List<IsoInventoryRemove> trackRemoveList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("Track Report", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			int i=0;
			
			Label label0 = new Label(i, 0, "Date", cellFormatHeader);
			sheet.addCell(label0);
			i++;
			Label label1 = new Label(i, 0, "Last NE Parent", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "Last NE Name", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "NE Type", cellFormatHeader);
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "Product Name", cellFormatHeader);
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "Product Code", cellFormatHeader);
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "Seri No", cellFormatHeader);
			sheet.addCell(label6);
			i++;
			Label label7 = new Label(i, 0, "Rack", cellFormatHeader);
			sheet.addCell(label7);
			i++;
			Label label8 = new Label(i, 0, "Subrack", cellFormatHeader);
			sheet.addCell(label8);
			i++;
			Label label9 = new Label(i, 0, "Slot", cellFormatHeader);
			sheet.addCell(label9);
			i++;
			Label label10 = new Label(i, 0, "Product Date", cellFormatHeader);
			sheet.addCell(label10);
			i++;
			Label label11 = new Label(i, 0, "Software vs", cellFormatHeader);
			sheet.addCell(label11);
			i++;
			Label label12 = new Label(i	, 0, "Location", cellFormatHeader);
			sheet.addCell(label12);
			i++;
			Label label13 = new Label(i, 0, "Vendor", cellFormatHeader);
			sheet.addCell(label13);
			
			i = 1;
			
			for (IsoInventoryRemove record : trackRemoveList) {
				int j=0;
				Label record0 = new Label(j, i, record.getDayStr(), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,record.getNeParent(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getNe(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getNeType(), cellFormat);
				sheet.addCell(record3);
				j++;
				Label record4 = new Label(j, i, record.getProductName(), cellFormat);
				sheet.addCell(record4);
				j++;
				Label record5 = new Label(j, i, record.getProductCode(), cellFormat);
				sheet.addCell(record5);
				j++;
				Label record6 = new Label(j, i, record.getSeriNo(), cellFormat);
				sheet.addCell(record6);
				j++;
				Label record7 = new Label(j, i, record.getRack(), cellFormat);
				sheet.addCell(record7);
				j++;
				Label record8 = new Label(j, i, record.getSubrack(), cellFormat);
				sheet.addCell(record8);
				j++;
				Label record9 = new Label(j, i, record.getSlot(), cellFormat);
				sheet.addCell(record9);
				j++;
				Label record10 = new Label(j, i, record.getProductDateStr(), cellFormat);
				sheet.addCell(record10);
				j++;
				Label record11 = new Label(j, i, record.getSwVersion(), cellFormat);
				sheet.addCell(record11);
				j++;
				Label record12 = new Label(j, i, record.getLocation(), cellFormat);
				sheet.addCell(record12);
				j++;
				Label record13 = new Label(j, i, record.getVendor(), cellFormat);
				sheet.addCell(record13);
				
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
