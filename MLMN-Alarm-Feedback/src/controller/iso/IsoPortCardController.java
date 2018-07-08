package controller.iso;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import controller.BaseController;

import vo.CHighlightConfigs;
import vo.CTableConfig;
import vo.IsoPortCard;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;
import vo.alarm.utils.UploadTools;

import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.IsoPortCardDAO;
import dao.SYS_PARAMETERDAO;
/**
 * Ten file: IsoPortCardController.java
 * Muc dich: Quan ly thong tin card & port
 * @author QUANG
 * Ngay tao: 15/09/2013
 * Lich su thay doi:
 */
@Controller
@RequestMapping("/iso/thong-tin-port-card/*")
public class IsoPortCardController extends BaseController{

	@Autowired
	private IsoPortCardDAO isoPortCardDAO;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	@ModelAttribute("highlight")
	public String highlight() {
		String highlight = "";
		List<CHighlightConfigs> highlightConfigList2 = cHighlightConfigsDAO.getByKey("NOT_NULL");
		if (highlightConfigList2.size()>0)
		{ 
			highlight = " $(this).find('.NOT_NULL').css({"+highlightConfigList2.get(0).getStyle()+"});";
		}
		return highlight;
	}
	
	@RequestMapping(value = "{function}")
	public ModelAndView list( @RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate,
			   @RequestParam(required = false) String neType,
			   @PathVariable String function, ModelMap model, HttpServletRequest request, HttpServletResponse response)
	{
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
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "PortCard_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		//do du lieu ra grid
		List<CTableConfig> configList;
		List<CTableConfig> columnList;
		if(neType.equals("BSC_BTS"))
		{
			configList = cTableConfigDAO.getTableConfigsForGrid("ISO_PORT_CARD_2G");
			//lay du lieu column cua grid
			model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
			//Lay du lieu datafield cua grid
			model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
			//Lay du lieu cac cot an hien cua grid
			columnList = cTableConfigDAO.getColumnList("ISO_PORT_CARD_2G");
			model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
			
			//Lay duong link url de lay du lieu do vao grid
			model.addAttribute("url", "data.htm" + "?startDate="+startDate+"&endDate="+endDate+"&neType=" + neType);
		}
		else if(neType.equals("RNC_NODEB"))
		{
			//do du lieu ra grid
			configList = cTableConfigDAO.getTableConfigsForGrid("ISO_PORT_CARD_3G");
			//lay du lieu column cua grid
			model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
			//Lay du lieu datafield cua grid
			model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
			//Lay du lieu cac cot an hien cua grid
			columnList = cTableConfigDAO.getColumnList("ISO_PORT_CARD_3G");
			model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
			
			//Lay duong link url de lay du lieu do vao grid
			model.addAttribute("url", "data.htm" + "?startDate="+startDate+"&endDate="+endDate+"&neType=" + neType);
		}
		else if(neType.equals("MGW"))
		{
			//do du lieu ra grid
			configList = cTableConfigDAO.getTableConfigsForGrid("ISO_PORT_CARD_MGW");
			//lay du lieu column cua grid
			model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
			//Lay du lieu datafield cua grid
			model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
			//Lay du lieu cac cot an hien cua grid
			columnList = cTableConfigDAO.getColumnList("ISO_PORT_CARD_MGW");
			model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
			
			//Lay duong link url de lay du lieu do vao grid
			model.addAttribute("url", "data.htm" + "?startDate="+startDate+"&endDate="+endDate+"&neType=" + neType);
		}
		else if(neType.equals("MFS"))
		{
			//do du lieu ra grid
			configList = cTableConfigDAO.getTableConfigsForGrid("ISO_PORT_CARD_MFS");
			//lay du lieu column cua grid
			model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
			//Lay du lieu datafield cua grid
			model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
			//Lay du lieu cac cot an hien cua grid
			columnList = cTableConfigDAO.getColumnList("ISO_PORT_CARD_MFS");
			model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
			
			//Lay duong link url de lay du lieu do vao grid
			model.addAttribute("url", "data.htm" + "?startDate="+startDate+"&endDate="+endDate+"&neType=" + neType);
		}
		else if(neType.equals("SGSN"))
		{
			//do du lieu ra grid
			configList = cTableConfigDAO.getTableConfigsForGrid("ISO_PORT_CARD_SGSN");
			//lay du lieu column cua grid
			model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
			//Lay du lieu datafield cua grid
			model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
			//Lay du lieu cac cot an hien cua grid
			columnList = cTableConfigDAO.getColumnList("ISO_PORT_CARD_SGSN");
			model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
			
			//Lay duong link url de lay du lieu do vao grid
			model.addAttribute("url", "data.htm" + "?startDate="+startDate+"&endDate="+endDate+"&neType=" + neType);
		}
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("function", function);
		model.addAttribute("neType", neType);	
		return new ModelAndView("jspiso/isoPortCardList");
	}
	
	@RequestMapping("/data")
	public void data(@RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate, 
			   @RequestParam(required = false) String neType, 
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		List<IsoPortCard> isoPortCardList = null;
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
		List<CTableConfig> columnList = null;
		List<CTableConfig> tableConfigList = null;
		
		if(neType.equals("BSC_BTS")){
			columnList = cTableConfigDAO.getTableConfigGet("ISO_PORT_CARD_2G", sortfield);
			tableConfigList = cTableConfigDAO.getTableConfigGet("ISO_PORT_CARD_2G", null);
		}
		else if(neType.equals("RNC_NODEB")){
			columnList = cTableConfigDAO.getTableConfigGet("ISO_PORT_CARD_3G", sortfield);
			tableConfigList = cTableConfigDAO.getTableConfigGet("ISO_PORT_CARD_3G", null);
		}
		else if(neType.equals("MGW")){
			columnList = cTableConfigDAO.getTableConfigGet("ISO_PORT_CARD_MGW", sortfield);
			tableConfigList = cTableConfigDAO.getTableConfigGet("ISO_PORT_CARD_MGW", null);
		}
		else if(neType.equals("MFS")){
			columnList = cTableConfigDAO.getTableConfigGet("ISO_PORT_CARD_MFS", sortfield);
			tableConfigList = cTableConfigDAO.getTableConfigGet("ISO_PORT_CARD_MFS", null);
		}
		else if (neType.equals("SGSN")){
			columnList = cTableConfigDAO.getTableConfigGet("ISO_PORT_CARD_SGSN", sortfield);
			tableConfigList = cTableConfigDAO.getTableConfigGet("ISO_PORT_CARD_SGSN", null);
		}
		
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
			isoPortCardList = isoPortCardDAO.getIsoPortCardFilter( startDate, endDate, neType, startRecord, endRecord, sortfield, sortorder, strWhere);
			totalRow = isoPortCardDAO.countIsoPortCardFilter(startDate, endDate, neType, strWhere);
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
		String jsonCartList = gs.toJson(isoPortCardList);
		strjson+= jsonCartList;
	    strjson += "}]";
		
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(strjson);
		out.close();
	}
	
	//FB Form
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String activeDay,
			 @RequestParam(required = false) String id, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		IsoPortCard isoPortCard = (id == null ) ? new IsoPortCard() : isoPortCardDAO.selectByPrimaryKey(Integer.parseInt(id));
		model.addAttribute("isoPortCard", isoPortCard);
		
		isoPortCardAddEdit(id, model);
		
		if(id == null)
		{
			// Ngay thang
			if (activeDay == null || activeDay.equals("") || DateTools.isValid("dd/MM/yyyy", activeDay)==false) {
				activeDay = df.format(new Date());
			}
			model.addAttribute("activeDay", activeDay);
		}
		else
		{
			if(isoPortCard.getActiveDay() != null)
				activeDay = df.format(isoPortCard.getActiveDay());
			model.addAttribute("activeDay", activeDay);
		}
		
		return "jspiso/isoPortCardForm";
	}
		
	private void isoPortCardAddEdit(String id, ModelMap model)
	{
		if(id != null && id != "")
			model.addAttribute("isoPortCardAddEdit", "Y");
		else
			model.addAttribute("isoPortCardAddEdit", "N");
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id,
			@RequestParam(required = false) String activeDay, 
			@ModelAttribute("isoPortCard") @Valid IsoPortCard isoPortCard, BindingResult result, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		//Ném lỗi
		if (result.hasErrors()) {
			isoPortCardAddEdit(id, model);
			if(result.hasFieldErrors("activeDay"))
				model.addAttribute("activeDayError", "activeDayError");
			model.addAttribute("activeDay", activeDay);
			return "jspiso/isoPortCardForm";
		}
		
		if (activeDay == null || activeDay.equals("") || DateTools.isValid("dd/MM/yyyy", activeDay)==false)
		{
			isoPortCardAddEdit(id, model);
			model.addAttribute("activeDay", activeDay);
			model.addAttribute("errorActiveDay", "*");
			return "jspiso/isoPortCardForm";
		}
		
		if(id == "")
		{
			try{
				isoPortCard.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
				isoPortCard.setNeType("MFS");
				isoPortCardDAO.insert(isoPortCard);
				saveMessageKey(request, "messsage.confirm.addsuccess");
			}
			catch(Exception e){
				saveMessageKey(request, "message.isoLicenseSoft.errorAdd");
				isoPortCardAddEdit(id, model);
				
				return "jspiso/isoPortCardForm";
			}
		}
		else{
			isoPortCard.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
			isoPortCard.setNeType("MFS");
			isoPortCardDAO.updateByPrimaryKey(isoPortCard);
			saveMessageKey(request, "messsage.confirm.updatesuccess");
		}
		return "redirect:list.htm?neType=MFS";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) Integer id, HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
			isoPortCardDAO.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch(Exception e)
		{
			saveMessageKey(request, "message.confirm.deleteOther");
			}
		
		return "redirect:list.htm?neType=MFS";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		return "jspiso/isoPortCardUpload";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile filePath,  ModelMap model, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
		
		if (!filePath.isEmpty()) {

			String[] ten=filePath.getOriginalFilename().split("\\.");
			String fileExtn = ten[ten.length-1];
			
			if (fileExtn.equals("xls")) {
				if (fileExtn.equals("xls")) {
					@SuppressWarnings("rawtypes")
					List sheetData = UploadTools.readXlsFile2(filePath.getInputStream());
					
					importContent(sheetData, model, request);
					
				}
			}
			else {
				 saveMessageKey(request, "cautruc.typeFIle");
			}
		}
		else {
			saveMessageKey(request, "cautruc.emptyFile");
		}
		
		return "jspiso/isoPortCardUpload";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent(List sheetData, ModelMap model,HttpServletRequest request) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<IsoPortCard> successList = new ArrayList<IsoPortCard>();
		List<IsoPortCard> failedList = new ArrayList<IsoPortCard>();
		List<IsoPortCard> success = new ArrayList<IsoPortCard>();
		
		String activeDay;
		String neParent;
		String ne;
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 3) {
        		saveMessageKey(request, "sidebar.admin.isoPortCardUploadErrorStructuresNumber");
        		
        		return "jspiso/isoPortCardUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		IsoPortCard isoPortCard;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			isoPortCard = new IsoPortCard();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=3; j++) {
     					data.add("");
     				}
        			activeDay				= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			neParent				= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			ne						= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			
        			// Kiem tra loi
        			if (activeDay == null || neParent == null || ne == null
        					|| (neParent != null && neParent.length() > 20)
        					|| (ne != null && ne.length() > 20)
						) {
						error = true;
					}
        			
        			
        			try
	    			{
        				if(activeDay != null){
		    				Date simple = new SimpleDateFormat("dd/MM/yyyy").parse(activeDay);
		    				isoPortCard.setActiveDay(new SimpleDateFormat("dd/MM/yyyy").parse(activeDay));
		    				System.out.println(simple);
	    				}	
	    			}
	    			catch(Exception e)
	    			{
	    				error = true;
	    			}
        			
        			//---------------------------------------------------------------------------
        			isoPortCard.setNeParent(neParent);
        			isoPortCard.setNe(ne);
        			isoPortCard.setNeType("MFS");
        			
        			if (activeDay == null && neParent == null && ne == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(isoPortCard);
    					} else  {
    						
    						successList.add(isoPortCard);
    					}
        			}
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (IsoPortCard record: successList) {
			try {
					record.setCreatedBy(username);
					isoPortCardDAO.insertSelective(record);
					success.add(record);
			} catch (Exception ex) {
					failedList.add(record);
			}
		}
		
		if (failedList.size() == 0 && successList.size() > 0) {
			model.addAttribute("status", "Upload thành công.");				// Upload thành công
		} else if (failedList.size() == 0 && successList.size() == 0) {
			model.addAttribute("status", "Không có dữ liệu.");				// Không có dữ liệu
		} else {
			model.addAttribute("status", "Dữ liệu card & port không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác");	// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", success.size());
		model.addAttribute("totalNum", failedList.size()+success.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		return "jspiso/isoPortCardUpload";
	}
	
	@RequestMapping("exportPortCard")
  	public ModelAndView reportAlarmLog(@RequestParam(required = false) String startDate,
  			@RequestParam(required = false) String endDate,
  			@RequestParam(required = false) String neType,
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
		
		List<IsoPortCard> isoPortCardList = isoPortCardDAO.getIsoPortCardFilter( startDate, endDate, neType, null, null, sortfield, sortorder, strWhere);
		if(neType.equals("BSC_BTS"))
			export2GReport(tempFile, isoPortCardList);
		else if(neType.equals("RNC_NODEB"))
			export3GReport(tempFile,isoPortCardList);
		else if(neType.equals("MGW"))
			exportMgwReport(tempFile, isoPortCardList);
		else if(neType.equals("MFS"))
			exportMfsReport(tempFile, isoPortCardList);
		else if(neType.equals("SGSN"))
			exportSgsnReport(tempFile, isoPortCardList);
			
		// return
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String filename = "PortCard_"+ dateNow;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();
  		return null;
  	}
	
	private void export2GReport(File tempFile, List<IsoPortCard> isoPortCardList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("BSC & BTS", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			int i=0;
			
			Label label0 = new Label(i, 0, "Date", cellFormatHeader);
			sheet.addCell(label0);
			i++;
			Label label1 = new Label(i, 0, "BSC", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "Abis Interface Card", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "BSC PORT", cellFormatHeader);
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "BTS", cellFormatHeader);
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "BTS PORT", cellFormatHeader);
			sheet.addCell(label5);
			
			i = 1;
			
			for (IsoPortCard record : isoPortCardList) {
				int j=0;
				Label record0 = new Label(j, i, record.getActiveDayStr(), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,record.getNeParent(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getInterfaceCard(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getNeParPortStr(), cellFormat);
				sheet.addCell(record3);
				j++;
				Label record4 = new Label(j, i, record.getNe(), cellFormat);
				sheet.addCell(record4);
				j++;
				Label record5 = new Label(j, i, record.getNePortStr(), cellFormat);
				sheet.addCell(record5);
				
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
	
	private void export3GReport(File tempFile, List<IsoPortCard> isoPortCardList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("RNC & NodeB", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			int i=0;
			
			Label label0 = new Label(i, 0, "Date", cellFormatHeader);
			sheet.addCell(label0);
			i++;
			Label label1 = new Label(i, 0, "RNCID", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "IuB Interface Card", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "Interface Name", cellFormatHeader);
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "VL IP Address", cellFormatHeader);
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "NodeB", cellFormatHeader);
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "WBTSID", cellFormatHeader);
			sheet.addCell(label6);
			
			i = 1;
			
			for (IsoPortCard record : isoPortCardList) {
				int j=0;
				Label record0 = new Label(j, i, record.getActiveDayStr(), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,record.getNeParent(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getInterfaceCard(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getInterfaceName(), cellFormat);
				sheet.addCell(record3);
				j++;
				Label record4 = new Label(j, i, record.getIpAddress(), cellFormat);
				sheet.addCell(record4);
				j++;
				Label record5 = new Label(j, i, record.getNe(), cellFormat);
				sheet.addCell(record5);
				j++;
				Label record6 = new Label(j, i, record.getWbtsidStr(), cellFormat);
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
	
	private void exportMgwReport(File tempFile, List<IsoPortCard> isoPortCardList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("BSC/RNC & MGW", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			int i=0;
			
			Label label0 = new Label(i, 0, "Date", cellFormatHeader);
			sheet.addCell(label0);
			i++;
			Label label1 = new Label(i, 0, "BSC/RNC", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "MGW", cellFormatHeader);
			sheet.addCell(label2);
			
			i = 1;
			
			for (IsoPortCard record : isoPortCardList) {
				int j=0;
				Label record0 = new Label(j, i, record.getActiveDayStr(), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,record.getNeParent(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getNe(), cellFormat);
				sheet.addCell(record2);
				
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
	
	private void exportMfsReport(File tempFile, List<IsoPortCard> isoPortCardList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("BSC & MFS", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			int i=0;
			
			Label label0 = new Label(i, 0, "Date", cellFormatHeader);
			sheet.addCell(label0);
			i++;
			Label label1 = new Label(i, 0, "BSC/RNC", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "MFS/MGW/MSC", cellFormatHeader);
			sheet.addCell(label2);
			
			i = 1;
			
			for (IsoPortCard record : isoPortCardList) {
				int j=0;
				Label record0 = new Label(j, i, record.getActiveDayStr(), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,record.getNeParent(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getNe(), cellFormat);
				sheet.addCell(record2);
				
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
	
	private void exportSgsnReport(File tempFile, List<IsoPortCard> isoPortCardList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("BSC/RNC & SGSN", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			int i=0;
			
			Label label0 = new Label(i, 0, "Date", cellFormatHeader);
			sheet.addCell(label0);
			i++;
			Label label1 = new Label(i, 0, "SGSN", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "PAPU", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "Local IP Addr", cellFormatHeader);
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "Local UDP Port", cellFormatHeader);
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "BSC/RNC", cellFormatHeader);
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "Remote IP Endpoint", cellFormatHeader);
			sheet.addCell(label6);
			i++;
			Label label7 = new Label(i, 0, "Remote UDP Port", cellFormatHeader);
			sheet.addCell(label7);
			i = 1;
			
			for (IsoPortCard record : isoPortCardList) {
				int j=0;
				Label record0 = new Label(j, i, record.getActiveDayStr(), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,record.getNe(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getPapu(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getLocalIpAddr(), cellFormat);
				sheet.addCell(record3);
				j++;
				Label record4 = new Label(j, i, record.getlUdpPort(), cellFormat);
				sheet.addCell(record4);
				j++;
				Label record5 = new Label(j, i, record.getNeParent(), cellFormat);
				sheet.addCell(record5);
				j++;
				Label record6 = new Label(j, i, record.getIpAddress(), cellFormat);
				sheet.addCell(record6);
				j++;
				Label record7 = new Label(j, i, record.getrUdpPort(), cellFormat);
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
