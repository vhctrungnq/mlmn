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
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import dao.CTableConfigDAO;
import dao.DyTrxDAO;
import dao.DyTrxDownDAO;
import dao.DyTrxNewDAO;
import dao.SYS_PARAMETERDAO;
import dao.VRpDyTrx3gDAO;
import dao.VRpDyTrxTotal3gDAO;

import vo.CTableConfig;
import vo.DyTrx;
import vo.DyTrxDown;
import vo.DyTrxNew;
import vo.SYS_PARAMETER;
import vo.VRpDyTrx3g;
import vo.VRpDyTrxTotal3g;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;
import vo.dictionary.TableConfigsHelper;

/**
 * Function        : Theo doi su thay doi mang luoi 3g
 * Created By      : BUIQUANG
 * Create Date     : 15/12/2013
 * Modified By     : 
 * Modify Date     : 
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/alarm/dy-trx-3g-up-down/*")
public class DyTrx3gUpDownController {
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	@Autowired
	private VRpDyTrx3gDAO vRpDyTrx3gDAO;
	@Autowired
	private VRpDyTrxTotal3gDAO vRpDyTrxTotal3gDAO;
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
	private DyTrxNewDAO dyTrxNewDAO;
	@Autowired
	private DyTrxDownDAO dyTrxDownDAO;
	@Autowired
	private DyTrxDAO dyTrxDAO;
	
	@RequestMapping(value = "{function}")
    public String list(@RequestParam(required = false) String startDate,
					   @RequestParam(required = false) String endDate, 	   
					   @RequestParam(required = false) String vendor,
					   @RequestParam(required = false) String rncid,
					   @RequestParam(required = false) String siteCell,
					   @RequestParam(required = false) String type,
					   @RequestParam(required = false) String status,
					   @PathVariable String function,  ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "BienDongSiteCellTrx3G_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		loadDataCombobox(model);
		// Ngay thang
		if (startDate == null || startDate.equals("")|| DateTools.isValid("dd/MM/yyyy", startDate)==false) {			
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			
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
			filterUrl += temp + "vendor=" + vendor; 
			temp = "&";
		}
		if(rncid != null){
			filterUrl += temp + "rncid=" + rncid.trim(); 
			temp = "&";
		}
		if(siteCell != null){
			filterUrl += temp + "siteCell=" + siteCell.trim(); 
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
		if(status != null)
		{
			filterUrl += temp + "status=" + status.trim();
		}
		if(type != null)
		{
			filterUrl += temp + "type=" + type.trim();
		}
		if(filterUrl != "")
			filterUrl = "?" + filterUrl;
		
		if(function.equals("tong-hop"))
		{
			//do du lieu ra grid
			List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("V_RP_DY_TRX_TOTAL_3G");
			//Lay du lieu cac cot an hien cua grid
			List<CTableConfig> listSource = cTableConfigDAO.getColumnList("V_RP_DY_TRX_TOTAL_3G");
			String url = "dataTotal.htm" + filterUrl;
			List<String> columnGrid = cTableConfigDAO.getGroupList("V_RP_DY_TRX_TOTAL_3G");
			String con2="";
			//lay du lieu column group cua grid
			String groupColumn="";
			groupColumn =" var columngroups=[";
			for (String alarmTypeG : columnGrid) {
				groupColumn= groupColumn+		con2+"{ text: '"+alarmTypeG+"', align: 'center', name: '"+alarmTypeG+"' }";
				con2 = ",";
			}
			groupColumn= groupColumn+	"]; ";
			//Grid
			String gridReport = TableConfigsHelper.getGridReportDontPaging(configList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", "Yes", "Yes", "singlecell", groupColumn);
			model.addAttribute("gridReport", gridReport);
		}
		else if(function.equals("chi-tiet"))
		{
			//do du lieu ra grid
			List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("DY_TRX_3G");
			//Lay du lieu cac cot an hien cua grid
			List<CTableConfig> listSource = cTableConfigDAO.getColumnList("DY_TRX_3G");
			String url = "data.htm" + filterUrl;
			//Grid
			String gridReport = TableConfigsHelper.getGridReportPaging(configList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", null, null, "singlecell", null);
			model.addAttribute("gridReport", gridReport);
		}
		else if(function.equals("lich-su-tang"))
		{
			//do du lieu ra grid
			List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("DY_TRX_NEW_3G");
			//Lay du lieu cac cot an hien cua grid
			List<CTableConfig> listSource = cTableConfigDAO.getColumnList("DY_TRX_NEW_3G");
			String url = "dataNew.htm" + filterUrl;
			//Grid
			String gridReport = TableConfigsHelper.getGridReportPaging(configList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", null, null, "singlecell", null);
			model.addAttribute("gridReport", gridReport);
		}
		else if(function.equals("lich-su-giam"))
		{
			//do du lieu ra grid
			List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("DY_TRX_NEW_3G");
			//Lay du lieu cac cot an hien cua grid
			List<CTableConfig> listSource = cTableConfigDAO.getColumnList("DY_TRX_NEW_3G");
			String url = "dataDown.htm" + filterUrl;
			//Grid
			String gridReport = TableConfigsHelper.getGridReportPaging(configList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", null, null, "singlecell", null);
			model.addAttribute("gridReport", gridReport);
		}
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("function", function);
		model.addAttribute("vendorCBB", vendor);
		model.addAttribute("rncid", rncid);
		model.addAttribute("siteCell", siteCell);
		model.addAttribute("type", type);
		model.addAttribute("status", status);
		return "jspalarm/vRpDyTrx3gList";
	}
	
	private void loadDataCombobox(ModelMap model)
	{
		// Danh sach nha cung cap
		List<SYS_PARAMETER> vendorList = sysParameterDao.getVendorList();
		model.addAttribute("vendorList", vendorList);
		
		// Danh sach status
		List<SYS_PARAMETER> statusList = sysParameterDao.getStatusDyTrx();
		model.addAttribute("statusList", statusList);
		
		// Danh sach type
		List<SYS_PARAMETER> typeList = sysParameterDao.getTypeDyTrx3g();
		model.addAttribute("typeList", typeList);
		
		// Danh sach gợi nhớ bscid
		List<VRpDyTrx3g> getRncidList = vRpDyTrx3gDAO.getRncidList();
		String rncidArray="var rncidList = new Array(";
		String cn="";
		for (int i=0;i<getRncidList.size();i++) {
			rncidArray = rncidArray + cn +"\""+getRncidList.get(i).getNe()+"\"";
			cn=",";
		}
		rncidArray = rncidArray+");";
		model.addAttribute("rncidList", rncidArray);
		
		// Danh sach gợi nhớ site/cell
		List<VRpDyTrx3g> siteCellList = vRpDyTrx3gDAO.getSiteCell3gList();
		String siteCellArray="var siteCellList = new Array(";
		String cn1="";
		for (int i=0;i<siteCellList.size();i++) {
			siteCellArray = siteCellArray + cn1 +"\""+siteCellList.get(i).getCellid()+"\"";
			cn1=",";
		}
		siteCellArray = siteCellArray+");";
		model.addAttribute("siteCellList", siteCellArray);
	}
	
	@RequestMapping("/dataTotal")
	public void dataTotal(@RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate, 	   
			   @RequestParam(required = false) String vendor,
			   @RequestParam(required = false) String rncid,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<VRpDyTrxTotal3g> vRpDyTrxTotal3g = null;
		try
		{
			vRpDyTrxTotal3g = vRpDyTrxTotal3gDAO.getVRpDyTrxTotal3g(startDate, endDate, vendor, rncid);
		}
		catch(Exception exp)
		{
		}

		Gson gs = new Gson();
		String jsonCartList = gs.toJson(vRpDyTrxTotal3g);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
		
	}
	
	/*@RequestMapping("/data")
	public void data(@RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate, 	   
			   @RequestParam(required = false) String vendor,
			   @RequestParam(required = false) String rncid,
			   @RequestParam(required = false) String siteCell,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<VRpDyTrx3g> vRpDyTrx3g = null;
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
		List<CTableConfig> columnList = cTableConfigDAO.getTableConfigGet("V_RP_DY_TRX_3G", sortfield);
		List<CTableConfig> tableConfigList = cTableConfigDAO.getTableConfigGet("V_RP_DY_TRX_3G", null);
		
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
			vRpDyTrx3g = vRpDyTrx3gDAO.getVRpDyTrx3gFilter(startDate, endDate, vendor, rncid, siteCell, startRecord, endRecord, sortfield, sortorder, strWhere);
			totalRow = vRpDyTrx3gDAO.countVRpDyTrx3gFilter(startDate, endDate, vendor, rncid, siteCell, strWhere);
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
		String jsonCartList = gs.toJson(vRpDyTrx3g);
		strjson+= jsonCartList;
	    strjson += "}]";
	    
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(strjson);
		out.close();
		
	}*/
	
	@RequestMapping("/data")
	public void data( 	   
			   @RequestParam(required = false) String vendor,
			   @RequestParam(required = false) String rncid,
			   @RequestParam(required = false) String siteCell,
			   @RequestParam(required = false) String status,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<DyTrx> dyTrx = null;
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
		List<CTableConfig> columnList = cTableConfigDAO.getTableConfigGet("DY_TRX_3G", sortfield);
		List<CTableConfig> tableConfigList = cTableConfigDAO.getTableConfigGet("DY_TRX_3G", null);
		
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
			dyTrx = dyTrxDAO.getDyTrxFilter(vendor, rncid, siteCell, null, status, "3G", startRecord, endRecord, sortfield, sortorder, strWhere);
			totalRow = dyTrxDAO.countDyTrxFilter(vendor, rncid, siteCell, null, status, "3G", strWhere);
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
		String jsonCartList = gs.toJson(dyTrx);
		strjson+= jsonCartList;
	    strjson += "}]";
	    
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(strjson);
		out.close();
		
	}
	
	@RequestMapping("/dataNew")
	public void dataNew( 
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate,
			   @RequestParam(required = false) String vendor,
			   @RequestParam(required = false) String rncid,
			   @RequestParam(required = false) String siteCell,
			   @RequestParam(required = false) String type,
			   @RequestParam(required = false) String status,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<DyTrxNew> dyTrxNew = null;
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
		List<CTableConfig> columnList = cTableConfigDAO.getTableConfigGet("DY_TRX_NEW_3G", sortfield);
		List<CTableConfig> tableConfigList = cTableConfigDAO.getTableConfigGet("DY_TRX_NEW_3G", null);
		
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
			dyTrxNew = dyTrxNewDAO.getDyTrxNewFilter(startDate, endDate, vendor, rncid, siteCell, null, type, status, "3G", startRecord, endRecord, sortfield, sortorder, strWhere);
			totalRow = dyTrxNewDAO.countDyTrxNewFilter(startDate, endDate, vendor, rncid, siteCell, null, type, status, "3G", strWhere);
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
		String jsonCartList = gs.toJson(dyTrxNew);
		strjson+= jsonCartList;
	    strjson += "}]";
	    
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(strjson);
		out.close();
		
	}
	
	@RequestMapping("/dataDown")
	public void dataDown( 
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate,
			   @RequestParam(required = false) String vendor,
			   @RequestParam(required = false) String rncid,
			   @RequestParam(required = false) String siteCell,
			   @RequestParam(required = false) String type,
			   @RequestParam(required = false) String status,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<DyTrxDown> dyTrxDown = null;
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
		List<CTableConfig> columnList = cTableConfigDAO.getTableConfigGet("DY_TRX_NEW_3G", sortfield);
		List<CTableConfig> tableConfigList = cTableConfigDAO.getTableConfigGet("DY_TRX_NEW_3G", null);
		
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
			dyTrxDown = dyTrxDownDAO.getDyTrxDownFilter(startDate, endDate, vendor, rncid, siteCell, null, type, status, "3G", startRecord, endRecord, sortfield, sortorder, strWhere);
			totalRow = dyTrxDownDAO.countDyTrxDownFilter(startDate, endDate, vendor, rncid, siteCell, null, type, status, "3G", strWhere);
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
		String jsonCartList = gs.toJson(dyTrxDown);
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
			@RequestParam(required = false) String vendor,
			@RequestParam(required = false) String rncid,
			@RequestParam(required = false) String siteCell,
			@RequestParam(required = false) String type,
			@RequestParam(required = false) String status,
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
			List<VRpDyTrxTotal3g> vRpDyTrxTotal3g = vRpDyTrxTotal3gDAO.getVRpDyTrxTotal3g(startDate, endDate, vendor, rncid);
			exportReportTotal(tempFile, vRpDyTrxTotal3g);
		}
		else if(function.equals("chi-tiet"))
		{
			List<DyTrx> dyTrx = dyTrxDAO.getDyTrxFilter(vendor, rncid, siteCell, null, status, "3G", null, null, sortfield, sortorder, strWhere);
			exportReport(tempFile, dyTrx);
		}
		else if(function.equals("lich-su-tang"))
		{
			List<DyTrxNew> dyTrxNew = dyTrxNewDAO.getDyTrxNewFilter(startDate, endDate, vendor, rncid, siteCell, null, type, status, "3G", null, null, sortfield, sortorder, strWhere);
			exportReportNew(tempFile, dyTrxNew);
		}
		else if(function.equals("lich-su-giam"))
		{
			List<DyTrxDown> dyTrxDown = dyTrxDownDAO.getDyTrxDownFilter(startDate, endDate, vendor, rncid, siteCell, null, type, status, "3G", null, null, sortfield, sortorder, strWhere);
			exportReportDown(tempFile, dyTrxDown);
		}
		
		// return
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String filename = "RpTrx3g_"+ dateNow;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();
  		return null;
  	}
	
	private void exportReportTotal(File tempFile, List<VRpDyTrxTotal3g> vRpDyTrxTotal3g) {
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
			Label label2 = new Label(i, 0, "RNCID", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "Site Tăng", cellFormatHeader);
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "Site Giảm", cellFormatHeader);
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "Tổng Site", cellFormatHeader);
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "Cell Tăng", cellFormatHeader);
			sheet.addCell(label6);
			i++;
			Label label7 = new Label(i, 0, "Cell Giảm", cellFormatHeader);
			sheet.addCell(label7);
			i++;
			Label label8 = new Label(i, 0, "Tổng Cell", cellFormatHeader);
			sheet.addCell(label8);
			i = 1;
			
			for (VRpDyTrxTotal3g record : vRpDyTrxTotal3g) {
				int j=0;
				Label record0 = new Label(j, i, record.getDayStr(), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,record.getVendor(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getNe(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getUpSite(), cellFormat);
				sheet.addCell(record3);
				j++;
				Label record4 = new Label(j, i, record.getDownSite(), cellFormat);
				sheet.addCell(record4);
				j++;
				Label record5 = new Label(j, i, record.getSumSiteStr(), cellFormat);
				sheet.addCell(record5);
				j++;
				Label record6 = new Label(j, i, record.getUpCellStr(), cellFormat);
				sheet.addCell(record6);
				j++;
				Label record7 = new Label(j, i, record.getDownCellStr(), cellFormat);
				sheet.addCell(record7);
				j++;
				Label record8 = new Label(j, i, record.getSumCellStr(), cellFormat);
				sheet.addCell(record8);
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
	
	private void exportReport(File tempFile, List<DyTrx> dyTrx) {
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
			Label label1 = new Label(i, 0, "BSCID", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "Site", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "Cell", cellFormatHeader);
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "Initial Date", cellFormatHeader);
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "Block Date", cellFormatHeader);
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "Remove Date", cellFormatHeader);
			sheet.addCell(label6);
			i++;
			Label label7 = new Label(i, 0, "Status", cellFormatHeader);
			sheet.addCell(label7);
			
			i = 1;
			
			for (DyTrx record : dyTrx) {
				int j=0;
				Label record0 = new Label(j, i, record.getVendor(), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,record.getBscid(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getSite(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getCellid(), cellFormat);
				sheet.addCell(record3);
				j++;
				Label record4 = new Label(j, i, record.getInitializeStr(), cellFormat);
				sheet.addCell(record4);
				j++;
				Label record5 = new Label(j, i, record.getBlockDateStr(), cellFormat);
				sheet.addCell(record5);
				j++;
				Label record6 = new Label(j, i, record.getRemoveDateStr(), cellFormat);
				sheet.addCell(record6);
				j++;
				Label record7 = new Label(j, i, record.getStatus(), cellFormat);
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
	
	private void exportReportNew(File tempFile, List<DyTrxNew> dyTrxNew) {
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
			Label label2 = new Label(i, 0, "BSCID", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "Site", cellFormatHeader);
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "Cell", cellFormatHeader);
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "Qty", cellFormatHeader);
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "Status", cellFormatHeader);
			sheet.addCell(label6);
			i++;
			Label label7 = new Label(i, 0, "Type", cellFormatHeader);
			sheet.addCell(label7);
			i = 1;
			
			for (DyTrxNew record : dyTrxNew) {
				int j=0;
				Label record0 = new Label(j, i, record.getDayStr(), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,record.getVendor(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getBscid(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getSite(), cellFormat);
				sheet.addCell(record3);
				j++;
				Label record4 = new Label(j, i, record.getCellid(), cellFormat);
				sheet.addCell(record4);
				j++;
				Label record5 = new Label(j, i, record.getQtyStr(), cellFormat);
				sheet.addCell(record5);
				j++;
				Label record6 = new Label(j, i, record.getStatus(), cellFormat);
				sheet.addCell(record6);
				j++;
				Label record7 = new Label(j, i, record.getType(), cellFormat);
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
	
	private void exportReportDown(File tempFile, List<DyTrxDown> dyTrxDown) {
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
			Label label2 = new Label(i, 0, "BSCID", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "Site", cellFormatHeader);
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "Cell", cellFormatHeader);
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "Qty", cellFormatHeader);
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "Status", cellFormatHeader);
			sheet.addCell(label6);
			i++;
			Label label7 = new Label(i, 0, "Type", cellFormatHeader);
			sheet.addCell(label7);
			i = 1;
			
			for (DyTrxDown record : dyTrxDown) {
				int j=0;
				Label record0 = new Label(j, i, record.getDayStr(), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,record.getVendor(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getBscid(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getSite(), cellFormat);
				sheet.addCell(record3);
				j++;
				Label record4 = new Label(j, i, record.getCellid(), cellFormat);
				sheet.addCell(record4);
				j++;
				Label record5 = new Label(j, i, record.getQtyStr(), cellFormat);
				sheet.addCell(record5);
				j++;
				Label record6 = new Label(j, i, record.getStatus(), cellFormat);
				sheet.addCell(record6);
				j++;
				Label record7 = new Label(j, i, record.getType(), cellFormat);
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
