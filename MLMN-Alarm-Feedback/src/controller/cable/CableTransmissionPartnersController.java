package controller.cable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import controller.BaseController;

import vo.CHighlightConfigs;
import vo.CTableConfig;
import vo.CableTransmissionPartners;
import vo.SysUsers;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;
import vo.alarm.utils.Helper;
import vo.alarm.utils.UploadTools;
import vo.dictionary.TableConfigsHelper;

import dao.CHighlightConfigsDAO;
import dao.CTableConfigDAO;
import dao.CableTransmissionPartnersDAO;
import dao.SysUsersDAO;
/**
 * Function        : Truyen dan thue doi tac
 * Created By      : BUIQUANG
 * Create Date     : 27/02/2014
 * Modified By     : 
 * Modify Date     : 
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/cable/transmission-partners/*")
public class CableTransmissionPartnersController extends BaseController{

	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired
	private CableTransmissionPartnersDAO cableTransmissionPartnersDAO;
	@Autowired 
	private SysUsersDAO  sysUserDao;
	
	@Autowired 
	private CHighlightConfigsDAO cHighlightConfigsDAO;
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
	
	@RequestMapping(value="list")
    public ModelAndView list(
    		@RequestParam(required = false) String vendor,
			@RequestParam(required = false) String odfName, 
			@RequestParam(required = false) String transmission, 	 
    		@ModelAttribute("filter") CableTransmissionPartners filter, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		String filterUrl = "";
		String temp = "";
		if(vendor != null){
			filterUrl += temp + "vendor=" + vendor.trim(); 
			temp = "&";
		}
		if(odfName != null){
			filterUrl += temp + "odfName=" + odfName.trim(); 
			temp = "&";
		}
		if(transmission != null){
			filterUrl += temp + "transmission=" + transmission.trim(); 
			temp = "&";
		}
		if(filterUrl != "")
			filterUrl = "?" + filterUrl;
		
		
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("CABLE_TRANSMISSION_PARTNERS");
		//Lay du lieu cac cot an hien cua grid
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList("CABLE_TRANSMISSION_PARTNERS");
		String url = "data.htm" + filterUrl;
		//Grid
		String gridManage = TableConfigsHelper.getGridAddAndPaging(configList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", "Yes", null, "multiplerowsextended", null);
		model.addAttribute("gridManage", gridManage);
		
		model.addAttribute("vendor", vendor);
		model.addAttribute("odfName", odfName);
		model.addAttribute("transmission", transmission);
		
		//Check Role User(Administrator)
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<SysUsers> userList = sysUserDao.checkRole(username);
		Helper.checkRole(userList, model);	
		return new ModelAndView("jspcable/cableTransmissionPartnersList");
	}
	
	@RequestMapping("/data")
	public void data(@RequestParam(required = false) String vendor,
			@RequestParam(required = false) String odfName, 
			@RequestParam(required = false) String transmission, 
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<CableTransmissionPartners> cableTransmissionPartners = null;
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
		if(request.getParameter("sortorder") != null)
			sortorder = request.getParameter("sortorder");
		List<CTableConfig> columnList = null;
		if(sortfield != null && !sortfield.equals(""))
			columnList = cTableConfigDAO.getTableConfigGet("CABLE_TRANSMISSION_PARTNERS", sortfield);
		List<CTableConfig> tableConfigList = cTableConfigDAO.getTableConfigGet("CABLE_TRANSMISSION_PARTNERS", null);
		
		// Tim kiem tren grid
		String strWhere = HelpTableConfigs.filter(request);
		for(CTableConfig column: tableConfigList)
		{
			strWhere = strWhere.toUpperCase().replaceAll(column.getDataField().toUpperCase(), column.getTableColumn());
		}
		
		// Sap xep
		if(columnList != null)
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
			cableTransmissionPartners = cableTransmissionPartnersDAO.getCableTransmissionPartner(vendor, odfName, transmission, startRecord, endRecord, sortfield, sortorder, strWhere);
			totalRow = cableTransmissionPartnersDAO.countCableTransmissionPn(vendor, odfName, transmission, strWhere);
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
		String jsonCartList = gs.toJson(cableTransmissionPartners);
		strjson+= jsonCartList;
	    strjson += "}]";
	    
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(strjson);
		out.close();
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String onDelete(@RequestParam (required = true) Integer id, HttpServletRequest request, HttpServletResponse response) {
		
		try
		{
			cableTransmissionPartnersDAO.deleteByPrimaryKey(id);
			saveMessageKey(request, "messsage.confirm.deletesuccess");
		}
		catch(Exception e)
		{
			saveMessageKey(request, "message.confirm.deleteOther");
			}
		
		return "redirect:list.htm";
	}
	
	//FB Form
	@RequestMapping(value = "form", method = RequestMethod.GET)
	public String showForm(@RequestParam(required = false) String id, 
			ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		CableTransmissionPartners cableTransmissionPartners = (id == null) ? new CableTransmissionPartners() : cableTransmissionPartnersDAO.selectByPrimaryKey(Integer.parseInt(id));
		model.addAttribute("cableTransmissionPartners", cableTransmissionPartners);
		cableTransmissionPartnersAddEdit( id, model);
		
		return "jspcable/cableTransmissionPartnersForm";
	}
	
	private void cableTransmissionPartnersAddEdit(String id, ModelMap model)
	{
		if(id != null && !id.equals(""))
			model.addAttribute("cableTransmissionPartnersAddEdit", "Y");
		else
			model.addAttribute("cableTransmissionPartnersAddEdit", "N");
	}
	
	@RequestMapping(value = "form", method = RequestMethod.POST)
	public String onSubmit(@RequestParam(required = false) String id,
			@ModelAttribute("cableTransmissionPartners") @Valid CableTransmissionPartners cableTransmissionPartners, BindingResult result, 
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws ParseException {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		//Ném lỗi
		if (result.hasErrors()) {
			cableTransmissionPartnersAddEdit( id, model);
				
			return "jspcable/cableTransmissionPartnersForm";
		}
		
		if(id.equals(""))
		{
			if(cableTransmissionPartnersDAO.checkCableTransmissionPnUk(
					cableTransmissionPartners.getVendor(), 
					cableTransmissionPartners.getOdfName(), 
					cableTransmissionPartners.getFiberCable(),
					cableTransmissionPartners.getOdfPos(),
					cableTransmissionPartners.getTransmission(), 
					cableTransmissionPartners.getThreadName(),
					cableTransmissionPartners.getOdfDestination(),
					cableTransmissionPartners.getPortDestination(),
					cableTransmissionPartners.getSite(), null).size() == 0){
				cableTransmissionPartners.setCreatedBy(username);
				cableTransmissionPartnersDAO.insert(cableTransmissionPartners);
				saveMessageKey(request, "messsage.confirm.addsuccess");
			}
			else{
				cableTransmissionPartnersAddEdit( id, model);
				saveMessageKey(request, "messsage.confirm.cableTransmissionPartners.checkUniqueKey");
				return "jspcable/cableTransmissionPartnersForm";
			}
		}
		else{
			if(cableTransmissionPartnersDAO.checkCableTransmissionPnUk(
					cableTransmissionPartners.getVendor(), 
					cableTransmissionPartners.getOdfName(), 
					cableTransmissionPartners.getFiberCable(),
					cableTransmissionPartners.getOdfPos(),
					cableTransmissionPartners.getTransmission(), 
					cableTransmissionPartners.getThreadName(),
					cableTransmissionPartners.getOdfDestination(),
					cableTransmissionPartners.getPortDestination(),
					cableTransmissionPartners.getSite(), id).size() == 0){
				cableTransmissionPartners.setModifiedBy(username);
				
				cableTransmissionPartnersDAO.updateByPrimaryKey(cableTransmissionPartners);
				saveMessageKey(request, "messsage.confirm.updatesuccess");
			}
			else{
				cableTransmissionPartnersAddEdit( id, model);
				saveMessageKey(request, "messsage.confirm.cableTransmissionPartners.checkUniqueKey");
				return "jspcable/cableTransmissionPartnersForm";
			}
		}
		return "redirect:list.htm";
	}
	
	@RequestMapping(value = "upload", method = RequestMethod.GET)
	public String showUploadForm() {
		return "jspcable/cableTransmissionPartnersUpload";
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
		
		return "jspcable/cableTransmissionPartnersUpload";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String importContent(List sheetData, ModelMap model,HttpServletRequest request) {
		String createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
		
		List<CableTransmissionPartners> successList = new ArrayList<CableTransmissionPartners>();
		List<CableTransmissionPartners> failedList = new ArrayList<CableTransmissionPartners>();
		List<CableTransmissionPartners> success = new ArrayList<CableTransmissionPartners>();
		
		String vendor;
		String odfName;
		String fiberCable;
		String odfPos;
		String transmission;
		String threadName;
		String speed;
		String odfDestination;
		String portDestination;
		String purposeUse;
		String site;
		
		if (sheetData.size() > 0) {
			List heard= (List)sheetData.get(0);
			
        	if (heard.size() != 11) {
        		saveMessageKey(request, "sidebar.admin.cableTransmissionPartnersUploadErrorStructuresNumber");
        		
        		return "jspcable/cableTransmissionPartnersUpload";
        	}
        	
        	if (sheetData.size() > 1) {
        		CableTransmissionPartners cableTransmissionPartners;
        		
        		for (int i=1; i<sheetData.size(); i++) {
        			boolean error = false;
        			
        			cableTransmissionPartners = new CableTransmissionPartners();
        			
        			List data= (List) sheetData.get(i);
        			
        			for (int j=data.size(); j<=11; j++) {
     					data.add("");
     				}
        			vendor				= data.get(0).toString().trim().equals("")?null:data.get(0).toString().trim();
        			odfName				= data.get(1).toString().trim().equals("")?null:data.get(1).toString().trim();
        			fiberCable			= data.get(2).toString().trim().equals("")?null:data.get(2).toString().trim();
        			odfPos				= data.get(3).toString().trim().equals("")?null:data.get(3).toString().trim();
        			transmission		= data.get(4).toString().trim().equals("")?null:data.get(4).toString().trim();
        			threadName			= data.get(5).toString().trim().equals("")?null:data.get(5).toString().trim();
        			speed				= data.get(6).toString().trim().equals("")?null:data.get(6).toString().trim();
        			odfDestination		= data.get(7).toString().trim().equals("")?null:data.get(7).toString().trim();
        			portDestination		= data.get(8).toString().trim().equals("")?null:data.get(8).toString().trim();
        			purposeUse			= data.get(9).toString().trim().equals("")?null:data.get(9).toString().trim();
        			site				= data.get(10).toString().trim().equals("")?null:data.get(10).toString().trim();
        			
        			// Kiem tra loi
        			if (vendor == null || odfName == null
						) {
						error = true;
					}
        			
        			
        			//---------------------------------------------------------------------------
        			cableTransmissionPartners.setVendor(vendor);
        			cableTransmissionPartners.setOdfName(odfName);
        			cableTransmissionPartners.setFiberCable(fiberCable);
        			cableTransmissionPartners.setOdfPos(odfPos);
        			cableTransmissionPartners.setTransmission(transmission);
        			cableTransmissionPartners.setThreadName(threadName);
        			cableTransmissionPartners.setSpeed(speed);
        			cableTransmissionPartners.setOdfDestination(odfDestination);
        			cableTransmissionPartners.setPortDestination(portDestination);
        			cableTransmissionPartners.setPurposeUse(purposeUse);
        			cableTransmissionPartners.setSite(site);
        			
        			if (vendor == null && odfName == null) {
        				// nothing
        				System.out.println("Du lieu loi!");
        			} else {
        				if (error) {
    						failedList.add(cableTransmissionPartners);
    					} else  {
    						
    						successList.add(cableTransmissionPartners);
    					}
        			}
        		}
        	}
		}
		
		System.out.println("successList.size(): " + successList.size());
		for (CableTransmissionPartners record: successList) {
			try {	
				if(cableTransmissionPartnersDAO.checkCableTransmissionPnUk(
						record.getVendor(), 
						record.getOdfName(), 
						record.getFiberCable(),
						record.getOdfPos(),
						record.getTransmission(), 
						record.getThreadName(), 
						record.getOdfDestination(),
						record.getPortDestination(),
						record.getSite(), null).size() == 0){
						record.setCreatedBy(createdBy);
						cableTransmissionPartnersDAO.insert(record);
					}else{
						record.setModifiedBy(createdBy);
						cableTransmissionPartnersDAO.updateByUniqueKey(record);
					}
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
			model.addAttribute("status", "Dữ liệu truyền dẫn thuê đối tác không hợp lệ do: Thiếu thông tin nhập liệu bắt buộc, thông tin có độ dài không cho phép hoặc định dạng dữ liệu không chính xác hoặc thông tin đơn vị xử lý không được phép nhập");	// Upload lỗi
		}
		
		model.addAttribute("failNum", failedList.size());
		model.addAttribute("successNum", success.size());
		model.addAttribute("totalNum", failedList.size()+success.size());
		model.addAttribute("successList", success);
		model.addAttribute("failedList", failedList);
		
		return "jspcable/cableTransmissionPartnersUpload";
	}
	
	@RequestMapping("exportData")
  	public ModelAndView exportData(
  			@RequestParam(required = false) String vendor,
			@RequestParam(required = false) String odfName, 
			@RequestParam(required = false) String transmission,
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
		
		List<CableTransmissionPartners> cableTransmissionPartners = cableTransmissionPartnersDAO.getCableTransmissionPartner(vendor, odfName, transmission, null, null, sortfield, sortorder, strWhere);
		exportReport(tempFile, cableTransmissionPartners);
		
		// return
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String filename = "CableTransmissionPartners_"+ dateNow;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();
  		return null;
  	}
	
	private void exportReport(File tempFile, List<CableTransmissionPartners> cableTransmissionPartners) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("CableTransmissionPartners", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			int i=0;
			
			Label label0 = new Label(i, 0, "Đối tác", cellFormatHeader);
			sheet.addCell(label0);
			i++;
			Label label1 = new Label(i, 0, "Tên ODF", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "Sợi cáp", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "ODF POS", cellFormatHeader);
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "Truyền dẫn end-to-end", cellFormatHeader);
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "Tên luồng", cellFormatHeader);
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "Tốc độ", cellFormatHeader);
			sheet.addCell(label6);
			i++;
			Label label7 = new Label(i, 0, "ODF thiết bị đích", cellFormatHeader);
			sheet.addCell(label7);
			i++;
			Label label8 = new Label(i, 0, "Port thiết bị đích", cellFormatHeader);
			sheet.addCell(label8);
			i++;
			Label label9 = new Label(i, 0, "Mục đích sử dụng", cellFormatHeader);
			sheet.addCell(label9);
			i++;
			Label label10 = new Label(i, 0, "Vị trí", cellFormatHeader);
			sheet.addCell(label10);
			
			i = 1;
			
			for (CableTransmissionPartners record : cableTransmissionPartners) {
				int j=0;
				Label record0 = new Label(j, i, record.getVendor(), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,record.getOdfName(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getFiberCable(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getOdfPos(), cellFormat);
				sheet.addCell(record3);
				j++;
				Label record4 = new Label(j, i, record.getTransmission(), cellFormat);
				sheet.addCell(record4);
				j++;
				Label record5 = new Label(j, i, record.getThreadName(), cellFormat);
				sheet.addCell(record5);
				j++;
				Label record6 = new Label(j, i, record.getSpeed(), cellFormat);
				sheet.addCell(record6);
				j++;
				Label record7 = new Label(j, i, record.getOdfDestination(), cellFormat);
				sheet.addCell(record7);
				j++;
				Label record8 = new Label(j, i, record.getPortDestination(), cellFormat);
				sheet.addCell(record8);
				j++;
				Label record9 = new Label(j, i, record.getPurposeUse(), cellFormat);
				sheet.addCell(record9);
				j++;
				Label record10 = new Label(j, i, record.getSite(), cellFormat);
				sheet.addCell(record10);
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
	
	// Lay danh sach vendor
   	@RequestMapping("loadVendor")
   	public @ResponseBody 
   	List<CableTransmissionPartners> loadVendor() {
   		List<CableTransmissionPartners> getVendorListPartners = cableTransmissionPartnersDAO.getVendorListPartners();
   		
   		return getVendorListPartners;
   	 }
}
