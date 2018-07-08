package controller.iso;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import vo.CTableConfig;
import vo.HProvincesCode;
import vo.IsoInventory;
import vo.SYS_PARAMETER;
import vo.SysUsers;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;
import vo.dictionary.TableConfigsHelper;

import dao.CTableConfigDAO;
import dao.HProvincesCodeDAO;
import dao.IsoInventoryDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysUsersDAO;
/**
 * Chuc nang bao cao chi tiet - 29/10/2013
 * @author BUIQUANG
 *
 */
@Controller
@RequestMapping("/iso/report-detail/*")
public class ReportDetailController {

	@Autowired
	private IsoInventoryDAO isoInventoryDAO;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDAO;
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
	private SysUsersDAO sysUsersDao;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat df_hh = new SimpleDateFormat("HH");
	
	@RequestMapping(value = "list")
    public String list(
    					@RequestParam(required = false) String deptCode,
    					@RequestParam(required = false) String team,
    					@RequestParam(required = false) String subTeam,
    					@RequestParam(required = false) String province,
    					@RequestParam(required = false) String district,
			   		   	@RequestParam(required = false) String neType,
			   		   	@RequestParam(required = false) String locationName,
			   		   	@RequestParam(required = false) String tenDonViSuDung,
			   		   	@RequestParam(required = false) String nguoiLapBieu, 
			   		   	@RequestParam(required = false) String donViSuDung, 
			   		   	@RequestParam(required = false) String keToanTruong,
			   		   	@RequestParam(required = false) String location,
			   		   ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "ReportDetail_" + dateNow;
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
			filterUrl += temp + "deptCode=" + deptCode.trim(); 
			temp = "&";
		}
		if(team != null){
			filterUrl += temp + "team=" + team.trim(); 
			temp = "&";
		}
		if(subTeam != null){
			filterUrl += temp + "subTeam=" + subTeam.trim(); 
			temp = "&";
		}
		if(province != null){
			filterUrl += temp + "province=" + province.trim(); 
			temp = "&";
		}
		if(district != null){
			filterUrl += temp + "district=" + district.trim(); 
			temp = "&";
		}
		if(neType != null){
			filterUrl += temp + "neType=" + neType.trim();
			temp = "&";
		}
		if(location != null){
			filterUrl += temp + "location=" + location.trim();
			temp = "&";
		}
		if(locationName != null){
			filterUrl += temp + "locationName=" + locationName.trim();
		} 
		if(filterUrl != "")
			filterUrl = "?" + filterUrl;
		loadData( model, locationName);
		
		/*//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("ISO_REPORT_DETAIL");
		//lay du lieu column cua grid
		model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
		//Lay du lieu datafield cua grid
		model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
		//Lay du lieu cac cot an hien cua grid
		List<CTableConfig> columnList = cTableConfigDAO.getColumnList("ISO_REPORT_DETAIL");
		model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
		//Lay duong link url de lay du lieu do vao grid
		model.addAttribute("url", "data.htm" + filterUrl);*/
		
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("ISO_REPORT_DETAIL");
		//Lay du lieu cac cot an hien cua grid
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList("ISO_REPORT_DETAIL");
		//Lay duong link url de lay du lieu do vao grid
		String url = "data.htm" + filterUrl;
		//Grid
		String gridReport = TableConfigsHelper.getGridReportPagingWithHeightLimit(configList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", "Yes", "Yes", "multiplerowsextended", null, "450");
		model.addAttribute("gridReport", gridReport);
				
		model.addAttribute("deptCode", deptCode);
		model.addAttribute("team", team);
		model.addAttribute("subTeam", subTeam);
		model.addAttribute("province", province);
		model.addAttribute("district", district);
		model.addAttribute("neType", neType);
		model.addAttribute("locationName", locationName);
		model.addAttribute("tenDonViSuDung", tenDonViSuDung);
		model.addAttribute("nguoiLapBieu", nguoiLapBieu);
		model.addAttribute("donViSuDung", donViSuDung);
		model.addAttribute("keToanTruong", keToanTruong);
		model.addAttribute("location", location);
		
		return "jspiso/baocao/deviceDetailList";
	}
	
	@RequestMapping("/data")
	public void doGet(
				@RequestParam(required = false) String deptCode,
				@RequestParam(required = false) String team,
				@RequestParam(required = false) String subTeam,
				@RequestParam(required = false) String province,
				@RequestParam(required = false) String district, 
				@RequestParam(required = false) String neType,
				@RequestParam(required = false) String locationName,
				@RequestParam(required = false) String location,
			   ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<IsoInventory> reportDetailList = null;
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
		List<CTableConfig> columnList = cTableConfigDAO.getTableConfigGet("ISO_REPORT_DETAIL", sortfield);
		List<CTableConfig> tableConfigList = cTableConfigDAO.getTableConfigGet("ISO_REPORT_DETAIL", null);
		
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
			reportDetailList = isoInventoryDAO.getReportGeneralList(deptCode, team, subTeam, province, district, locationName, neType, location, startRecord, endRecord, sortfield, sortorder, strWhere);
			totalRow = isoInventoryDAO.countReportGeneralList(deptCode, team, subTeam, province, district, locationName, neType, location, strWhere);
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
		String jsonCartList = gs.toJson(reportDetailList);
		strjson+= jsonCartList;
	    strjson += "}]";
	    
	    model.addAttribute("strWhere", strWhere);
	    
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(strjson);
		out.close();
	}
	
	private void loadData(ModelMap model, String locationName)
	{
		// Danh sach gợi nhớ location name
		List<IsoInventory> locationNameList = isoInventoryDAO.getLocationNameList();
		String locationNameArray="var locationNameList = new Array(";
		String cn="";
		for (int i=0;i<locationNameList.size();i++) {
			locationNameArray = locationNameArray + cn +"\""+locationNameList.get(i).getLocationName()+"\"";
			cn=",";
		}
		locationNameArray = locationNameArray+");";
		model.addAttribute("locationNameList", locationNameArray);
		model.addAttribute("locationNameCBB", locationName);
	}
	
	// Lay danh sach loai thiet bi
 	@RequestMapping("dsLoaiThietBi")
 	public @ResponseBody 
 	List<SYS_PARAMETER> getNeType() {
 		List<SYS_PARAMETER> neTypeList = new ArrayList<SYS_PARAMETER>();
 		neTypeList = sysParameterDao.getEquipmentType();
 		
 		return neTypeList;
 	 }
 	
 	// Lay danh sach dia diem
   	@RequestMapping("dsDiaDiem")
   	public @ResponseBody 
   	List<HProvincesCode> dsDiaDiem() {
   		List<HProvincesCode> hProvinceCodeList = hProvincesCodeDAO.getHProvinceCodeByEquiment();
   		
   		return hProvinceCodeList;
   	 }
 	
 	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "reportDetailDocx", method = RequestMethod.GET)
	public String reportDetailDocx(
			@RequestParam(required = false) String deptCode,
			@RequestParam(required = false) String team,
			@RequestParam(required = false) String subTeam,
			@RequestParam(required = false) String province,
			@RequestParam(required = false) String district,
			@RequestParam(required = false) String neType, 
			@RequestParam(required = false) String locationName,
			@RequestParam(required = false) String tenDonViSuDung,
			@RequestParam(required = false) String nguoiLapBieu, 
			@RequestParam(required = false) String donViSuDung, 
			@RequestParam(required = false) String keToanTruong,
			@RequestParam(required = false) String location,
			@RequestParam(required = false) String sortfield,
  			@RequestParam(required = false) String sortorder,
  			@RequestParam(required = false) String strWhere,
			ModelMap model, HttpServletRequest request,HttpServletResponse response) throws JRException, IOException {
 		strWhere = strWhere.replace("___", "%");
 		//List<IsoInventory> reportDetailList = isoInventoryDAO.getReportGeneralList(locationName, neType.equals("ALL") ? null : neType, province.equals("ALL") ? null : province, null, null, sortfield, sortorder, strWhere);
 		List<IsoInventory> reportDetailList = isoInventoryDAO.getReportGeneralList(deptCode, team, subTeam, province, district, locationName, neType, location, null, null, sortfield, sortorder, strWhere);
		String dateNow = df.format(new Date());
		String hour = df_hh.format(new Date());
		InputStream inputStream = new FileInputStream (request.getSession().getServletContext().getRealPath("/report/detailDevice.jrxml")); 
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(reportDetailList);
		String temp = "";
		if(!neType.equals("ALL"))
			temp += "" + neType;
		if(!locationName.equals(""))
			temp += " - " + locationName;
		Map parameters = new HashMap();
		parameters.put("titleHeaderRight", "BẢNG KIỂM KÊ THIẾT BỊ " + temp);
		parameters.put("titleDateTime", "Cập nhật " + hour + "h ngày " + dateNow);
		parameters.put("tenDonViSuDung", tenDonViSuDung);
		parameters.put("nguoiLapBieu", nguoiLapBieu);
		parameters.put("donViSuDung", donViSuDung);
		parameters.put("keToanTruong", keToanTruong);
		
		JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
		
		response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		response.setHeader("Content-Disposition", "inline; filename=\"detailDevice__"+dateNow+".docx\"");
		
		JRDocxExporter exporterDocx = new JRDocxExporter();
		exporterDocx.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		OutputStream ouputStream = response.getOutputStream();
		exporterDocx.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
		exporterDocx.exportReport();	
		response.getOutputStream().flush();
		response.getOutputStream().close();
		
		return "jspiso/baocao/deviceDetailList";
	}
 	
 	@RequestMapping("exportDetail")
  	public ModelAndView exportDetail(
  			@RequestParam(required = false) String deptCode,
			@RequestParam(required = false) String team,
			@RequestParam(required = false) String subTeam,
			@RequestParam(required = false) String province,
			@RequestParam(required = false) String district,
  			@RequestParam(required = false) String locationName,
  			@RequestParam(required = false) String neType,
  			@RequestParam(required = false) String location,
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
		
		//List<IsoInventory> isoReportList = isoInventoryDAO.getReportGeneralList(locationName, neType.equals("ALL") ? null : neType, province.equals("ALL") ? null : province, null, null, sortfield, sortorder, strWhere);
		List<IsoInventory> isoReportList = isoInventoryDAO.getReportGeneralList(deptCode, team, subTeam, province, district, locationName, neType, location, null, null, sortfield, sortorder, strWhere);
		exportReportGeneral(tempFile, isoReportList);
		// return
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String filename = "ChiTietThietBi_"+ dateNow;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();
  		return null;
  	}
  	
  	private void exportReportGeneral(File tempFile, List<IsoInventory> isoReportList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("Report", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			int i=0;
			
			Label label0 = new Label(i, 0, "Department", cellFormatHeader);
			sheet.addCell(label0);
			i++;
			Label label1 = new Label(i, 0, "Team", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "Sub Team", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "Location Name", cellFormatHeader);
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "Province", cellFormatHeader);
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "District", cellFormatHeader);
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "NE Type", cellFormatHeader);
			sheet.addCell(label6);
			i++;
			Label label7 = new Label(i, 0, "NE Name", cellFormatHeader);
			sheet.addCell(label7);
			i++;
			Label label8 = new Label(i, 0, "Product Name", cellFormatHeader);
			sheet.addCell(label8);
			i++;
			Label label9 = new Label(i, 0, "Product Code", cellFormatHeader);
			sheet.addCell(label9);
			i++;
			Label label10 = new Label(i, 0, "Seri No", cellFormatHeader);
			sheet.addCell(label10);
			i++;
			Label label11 = new Label(i, 0, "Qty", cellFormatHeader);
			sheet.addCell(label11);
			i++;
			Label label12 = new Label(i, 0, "Status", cellFormatHeader);
			sheet.addCell(label12);
			i++;
			Label label13 = new Label(i, 0, "Description", cellFormatHeader);
			sheet.addCell(label13);
			i++;
			Label label14 = new Label(i, 0, "Location", cellFormatHeader);
			sheet.addCell(label14);
			
			i = 1;
			
			for (IsoInventory record : isoReportList) {
				int j=0;
				
				Label record0 = new Label(j, i, record.getDeptCode(), cellFormat);
				sheet.addCell(record0);
				j++;
				
				Label record1 = new Label(j, i, record.getTeam(), cellFormat);
				sheet.addCell(record1);
				j++;
				
				Label record2 = new Label(j, i, record.getSubTeam(), cellFormat);
				sheet.addCell(record2);
				j++;
				Label record3 = new Label(j, i, record.getLocationName(), cellFormat);
				sheet.addCell(record3);
				j++;
				Label record4 = new Label(j, i, record.getProvince(), cellFormat);
				sheet.addCell(record4);
				j++;
				Label record5 = new Label(j, i, record.getDistrict(), cellFormat);
				sheet.addCell(record5);
				j++;
				Label record6 = new Label(j, i, record.getNeType(), cellFormat);
				sheet.addCell(record6);
				j++;
				Label record7 = new Label(j, i, record.getNe(), cellFormat);
				sheet.addCell(record7);
				j++;
				Label record8 = new Label(j, i, record.getProductName(), cellFormat);
				sheet.addCell(record8);
				j++;
				Label record9 = new Label(j, i, record.getProductCode(), cellFormat);
				sheet.addCell(record9);
				j++;
				Label record10 = new Label(j, i, record.getSeriNo(), cellFormat);
				sheet.addCell(record10);
				j++;
				Label record11 = new Label(j, i, record.getTotal().toString(), cellFormat);
				sheet.addCell(record11);
				j++;
				Label record12 = new Label(j, i, record.getStatusName(), cellFormat);
				sheet.addCell(record12);
				j++;
				Label record13 = new Label(j, i, record.getDescription(), cellFormat);
				sheet.addCell(record13);
				j++;
				Label record14 = new Label(j, i, record.getLocation(), cellFormat);
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
}
