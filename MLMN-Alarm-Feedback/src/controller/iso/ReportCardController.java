package controller.iso;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import vo.IsoInventory;
import vo.SysUsers;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;
import vo.dictionary.TableConfigsHelper;

import com.google.gson.Gson;

import dao.CTableConfigDAO;
import dao.IsoInventoryDAO;
import dao.SysUsersDAO;

/**
 * Function        : Thong ke thiet bi
 * Created By      : BUIQUANG
 * Create Date     : 11/10/2013
 * Modified By     : 
 * Modify Date     : 
 * @author BUIQUANG
 * Description     :
 */
@Controller
@RequestMapping("/iso/bao-cao-card-du-phong/*")
public class ReportCardController {

	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired
	private IsoInventoryDAO isoInventoryDAO;
	@Autowired
	private SysUsersDAO sysUsersDao;
	
	@RequestMapping(value = "{function}")
    public String list(
			    		@RequestParam(required = false) String deptCode,
						@RequestParam(required = false) String team,
						@RequestParam(required = false) String subTeam,
						@RequestParam(required = false) String province,
						@RequestParam(required = false) String district,
    					@RequestParam(required = false) String productName,
    					@RequestParam(required = false) String productCode, 
    					@RequestParam(required = false) String neType, 
    					@PathVariable String function, 
    					@RequestParam(required = false) String fbType, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "Thong_ke_thiet_bi" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		boolean rolesManagerDeptCode = false;
		boolean rolesManagerTeam = false;
		boolean rolesManagerSubTeam = false;
		SysUsers user = sysUsersDao.selectSysUsersByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		if(user.getIsRoleManager() != null && user.getIsRoleManager().equals("Y")){
			
		}
		else{
			if(user.getSubTeam() != null && !user.getSubTeam().equals("")){
				subTeam = user.getSubTeam();
				rolesManagerSubTeam = true;
			}
			if(user.getTeam() != null && !user.getTeam().equals("")){
				team = user.getTeam();
				rolesManagerTeam = true;
			}
			if(user.getMaPhong() != null && !user.getMaPhong().equals("")){
				deptCode = user.getMaPhong();
				rolesManagerDeptCode = true;
			}
		}
		model.addAttribute("rolesManagerDeptCode", rolesManagerDeptCode);
		model.addAttribute("rolesManagerTeam", rolesManagerTeam);
		model.addAttribute("rolesManagerSubTeam", rolesManagerSubTeam);
		
		String filterUrl = "";
		String temp = "";
		if(deptCode != null){
			filterUrl += temp +  "deptCode=" + deptCode.trim(); 
			temp = "&";
		}
		if(team != null){
			filterUrl += temp +  "team=" + team.trim(); 
			temp = "&";
		}
		if(subTeam != null){
			filterUrl += temp +  "subTeam=" + subTeam.trim(); 
			temp = "&";
		}
		if(province != null){
			filterUrl += temp +  "province=" + province.trim(); 
			temp = "&";
		}
		if(district != null){
			filterUrl += temp +  "district=" + district.trim(); 
			temp = "&";
		}
		if(productName != null){
			filterUrl += temp +  "productName=" + productName.trim(); 
			temp = "&";
		}
		if(productCode != null){
			filterUrl += temp + "productCode=" + productCode.trim();
		}
		if(neType != null){
			filterUrl += temp + "neType=" + neType.trim();
		}
		if(filterUrl != "")
			filterUrl = "?" + filterUrl;
		
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("ISO_REPORT_CARD");
		//Lay du lieu cac cot an hien cua grid
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList("ISO_REPORT_CARD");
		//Lay duong link url de lay du lieu do vao grid
		String url = "data.htm" + filterUrl;
		//Grid
		String gridReport = TableConfigsHelper.getGridReportPaging(configList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", "Yes", "Yes", "singlecell", null);
		model.addAttribute("gridReport", gridReport);
		
		model.addAttribute("deptCode", deptCode);
		model.addAttribute("team", team);
		model.addAttribute("subTeam", subTeam);
		model.addAttribute("province", province);
		model.addAttribute("district", district);
		model.addAttribute("productName", productName);
		model.addAttribute("productCode", productCode);
		model.addAttribute("neType", neType);
		model.addAttribute("function", function);
		
		return "jspiso/baocao/cardDuPhongList";
	}
	
	@RequestMapping("/data")
	public void doGet(
				@RequestParam(required = false) String deptCode,
				@RequestParam(required = false) String team,
				@RequestParam(required = false) String subTeam,
				@RequestParam(required = false) String province,
				@RequestParam(required = false) String district,
				@RequestParam(required = false) String productName,
				@RequestParam(required = false) String productCode,
				@RequestParam(required = false) String neType, 
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int startRecord = 0, endRecord = 0, pageSize = 100;
		String sortfield = "NE_TYPE";
		String sortorder = "";
		int pageNum = Integer.parseInt(request.getParameter("pagenum"));
		if(pageNum == -1)
			pageNum = 1;
		if(!request.getParameter("pagesize").equals(""))
			 pageSize = Integer.parseInt(request.getParameter("pagesize"));
		else pageSize = 100;
		sortfield = request.getParameter("sortdatafield");
		sortorder = request.getParameter("sortorder");
		List<CTableConfig> columnList = cTableConfigDAO.getTableConfigGet("ISO_REPORT_CARD", sortfield);
		List<CTableConfig> tableConfigList = cTableConfigDAO.getTableConfigGet("ISO_REPORT_CARD", null);
		
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
		List<IsoInventory> isoInventoryList = isoInventoryDAO.getReportCardDuPhong(deptCode, team, subTeam, province, district, productName, productCode, neType, startRecord, endRecord, sortfield, sortorder, strWhere);
		totalRow = isoInventoryDAO.countReportCardDuPhong(deptCode, team, subTeam, province, district, productName, productCode, neType, strWhere);
 
		String strjson = "[{\"TotalRows\":\""+totalRow+"\"},";
		strWhere = strWhere.replace("%", "___");
		strjson += "{\"strWhere\":\""+strWhere+"\"},";
		strjson += "{\"sortfield\":\""+sortfield+"\"},";
		strjson += "{\"sortorder\":\""+sortorder+"\"},";
        strjson += "{\"Rows\":" ;
        Gson gs = new Gson();
		String jsonCartList = gs.toJson(isoInventoryList);
		strjson+= jsonCartList;
	    strjson += "}]";
	     
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(strjson);
		out.close();
	}
	
	@RequestMapping("exportData")
  	public ModelAndView exportData(
  			@RequestParam(required = false) String deptCode,
			@RequestParam(required = false) String team,
			@RequestParam(required = false) String subTeam,
			@RequestParam(required = false) String province,
			@RequestParam(required = false) String district,
			@RequestParam(required = false) String productName,
			@RequestParam(required = false) String productCode,
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
		List<IsoInventory> isoEquipmentSynList = isoInventoryDAO.getReportCardDuPhong(deptCode, team, subTeam, province, district, productName, productCode, neType,null, null, sortfield, sortorder, strWhere);
		exportReport(tempFile, isoEquipmentSynList);
		
		// return
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String filename = "ThongKeThietBi_"+ dateNow;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();
  		return null;
  	}
	
	private void exportReport(File tempFile, List<IsoInventory> isoEquipmentSynList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("Report", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			int i=0;
			
			Label label0 = new Label(i, 0, "No.", cellFormatHeader);
			sheet.addCell(label0);
			i++;
			Label label1 = new Label(i, 0, "Region", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "Department", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "Team", cellFormatHeader);
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "Sub Team", cellFormatHeader);
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "Province", cellFormatHeader);
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "District", cellFormatHeader);
			sheet.addCell(label6);
			i++;
			Label label7 = new Label(i, 0, "Product Name", cellFormatHeader);
			sheet.addCell(label7);
			i++;
			Label label8 = new Label(i, 0, "Product Code", cellFormatHeader);
			sheet.addCell(label8);
			i++;
			Label label9 = new Label(i, 0, "NE Type", cellFormatHeader);
			sheet.addCell(label9);
			i++;
			Label label10 = new Label(i, 0, "NE Type Support", cellFormatHeader);
			sheet.addCell(label10);
			i++;
			Label label11 = new Label(i, 0, "Vendor", cellFormatHeader);
			sheet.addCell(label11);
			i++;
			Label label12 = new Label(i, 0, "Tổng số đang hoạt động trên mạng (cards)", cellFormatHeader);
			sheet.addCell(label12);
			i++;
			Label label13 = new Label(i, 0, "Tổng số không hoạt động trên mạng (cards)", cellFormatHeader);
			sheet.addCell(label13);
			i++;
			Label label14 = new Label(i, 0, "Đang có trong kho (cards)", cellFormatHeader);
			sheet.addCell(label14);
			i++;
			Label label15 = new Label(i, 0, "Đang gửi BHSC (cards)", cellFormatHeader);
			sheet.addCell(label15);
			i++;
			Label label16 = new Label(i, 0, "Ghi chú", cellFormatHeader);
			sheet.addCell(label16);
			i = 1;
			
			for (IsoInventory record : isoEquipmentSynList) {
				int j=0;
				Label record0 = new Label(j, i, String.valueOf(i), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,record.getRegion(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getDeptCode(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getTeam(), cellFormat);
				sheet.addCell(record3);
				j++;
				Label record4 = new Label(j, i, record.getSubTeam(), cellFormat);
				sheet.addCell(record4);
				j++;
				Label record5 = new Label(j, i, record.getProvince(), cellFormat);
				sheet.addCell(record5);
				j++;
				Label record6 = new Label(j, i, record.getDistrict(), cellFormat);
				sheet.addCell(record6);
				j++;
				Label record7 = new Label(j, i, record.getProductName(), cellFormat);
				sheet.addCell(record7);
				j++;
				Label record8 = new Label(j, i, record.getProductCode(), cellFormat);
				sheet.addCell(record8);
				j++;
				Label record9 = new Label(j, i, record.getNeType(), cellFormat);
				sheet.addCell(record9);
				j++;
				Label record10 = new Label(j, i, record.getNeTypeSupport(), cellFormat);
				sheet.addCell(record10);
				j++;
				Label record11 = new Label(j, i, record.getVendor(), cellFormat);
				sheet.addCell(record11);
				j++;
				Label record12 = new Label(j, i, record.getDangHoatDongStr(), cellFormat);
				sheet.addCell(record12);
				j++;
				Label record13 = new Label(j, i, record.getKhongHoatDongStr(), cellFormat);
				sheet.addCell(record13);
				j++;
				Label record14 = new Label(j, i, record.getTrongKhoStr(), cellFormat);
				sheet.addCell(record14);
				j++;
				Label record15 = new Label(j, i, record.getBaoHanhStr(), cellFormat);
				sheet.addCell(record15);
				j++;
				Label record16 = new Label(j, i, record.getDescription(), cellFormat);
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
	
	@RequestMapping("exportDataOptima")
  	public ModelAndView exportDataOptima(
  			@RequestParam(required = false) String productName,
			@RequestParam(required = false) String productCode,
			@RequestParam(required = false) String neType,
			@RequestParam(required = false) String vendor,
  			HttpServletRequest request, HttpServletResponse response) throws Exception {
  		
  		// temp file
		String basePath = request.getSession().getServletContext().getRealPath("");
		String dataDir = basePath + "/upload";
		String tempName = UUID.randomUUID().toString();
		String ext = "xls";
		String outfile = tempName + "." + ext;
		File tempFile = new File(dataDir + "/" + outfile);
		List<IsoInventory> isoEquipmentSynList = isoInventoryDAO.getReportToOptima(productName, productCode, neType, vendor);
		exportReportOptima(tempFile, isoEquipmentSynList);
		
		// return
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String filename = "ReportToOptima_"+ dateNow;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();
  		return null;
  	}
	
	private void exportReportOptima(File tempFile, List<IsoInventory> isoEquipmentSynList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("Report", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			int i=0;
			
			Label label0 = new Label(i, 0, "STT", cellFormatHeader);
			sheet.addCell(label0);
			i++;
			Label label1 = new Label(i, 0, "Trung tâm", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "Tên card", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "Mã card", cellFormatHeader);
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "Thiết bị", cellFormatHeader);
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "Nhà cung cấp", cellFormatHeader);
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "Tổng số đang hoạt động trên mạng (cards)", cellFormatHeader);
			sheet.addCell(label6);
			i++;
			Label label7 = new Label(i, 0, "Đang có trong kho (cards)", cellFormatHeader);
			sheet.addCell(label7);
			i++;
			Label label8 = new Label(i, 0, "Đang gửi BHSC (cards)", cellFormatHeader);
			sheet.addCell(label8);
			i++;
			Label label9 = new Label(i, 0, "Ghi chú", cellFormatHeader);
			sheet.addCell(label9);
			i = 1;
			
			for (IsoInventory record : isoEquipmentSynList) {
				int j=0;
				Label record0 = new Label(j, i, String.valueOf(i), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,record.getRegion(), cellFormat);
				sheet.addCell(record1);
				j++;
				Label record2 = new Label(j, i, record.getProductName(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getProductCode(), cellFormat);
				sheet.addCell(record3);
				j++;
				Label record4 = new Label(j, i, record.getNeType(), cellFormat);
				sheet.addCell(record4);
				j++;
				Label record5 = new Label(j, i, record.getVendor(), cellFormat);
				sheet.addCell(record5);
				j++;
				Label record6 = new Label(j, i, record.getDangHoatDongStr(), cellFormat);
				sheet.addCell(record6);
				j++;
				Label record7 = new Label(j, i, record.getTrongKhoStr(), cellFormat);
				sheet.addCell(record7);
				j++;
				Label record8 = new Label(j, i, record.getBaoHanhStr(), cellFormat);
				sheet.addCell(record8);
				j++;
				Label record9 = new Label(j, i, record.getDescription(), cellFormat);
				sheet.addCell(record9);
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
