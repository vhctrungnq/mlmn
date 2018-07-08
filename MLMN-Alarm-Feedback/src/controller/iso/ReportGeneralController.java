package controller.iso;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;

import vo.CTableConfig;
import vo.IsoInventory;
import vo.alarm.utils.HelpTableConfigs;

import dao.CTableConfigDAO;
import dao.HProvincesCodeDAO;
import dao.IsoInventoryDAO;
/**
 * Chuc nang: bao cao tong hop thiet bi - 29/10/2013
 * @author BUIQUANG
 *
 */
@Controller
@RequestMapping("/iso/report-general/*")
public class ReportGeneralController {

	@Autowired
	private IsoInventoryDAO isoInventoryDAO;
	@Autowired
	private HProvincesCodeDAO hProvincesCodeDAO;
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private DateFormat df_hh = new SimpleDateFormat("HH");
	
	@RequestMapping(value = "{function}")
    public String list(@RequestParam(required = false) String locationName,
			   		   @RequestParam(required = false) String tenDonViSuDung,
			   		   @RequestParam(required = false) String phieuKKSo,
					   @PathVariable String function, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String exportFileName = "DeviceGeneral_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		String filterUrl = "";
		if(locationName != null){
			filterUrl += "locationName=" + locationName.trim(); 
		}
		if(filterUrl != "")
			filterUrl = "?" + filterUrl;
		loadData(model, locationName);
		
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("ISO_REPORT_GENERAL");
		//lay du lieu column cua grid
		model.addAttribute("column", HelpTableConfigs.getColumns(configList));    
		//Lay du lieu datafield cua grid
		model.addAttribute("datafields", HelpTableConfigs.getDataFields(configList));
		//Lay du lieu cac cot an hien cua grid
		List<CTableConfig> columnList = cTableConfigDAO.getColumnList("ISO_REPORT_GENERAL");
		model.addAttribute("listSource", HelpTableConfigs.getListSource(columnList));
		//Lay duong link url de lay du lieu do vao grid
		model.addAttribute("url", "data.htm" + filterUrl);
				
		model.addAttribute("locationName", locationName);
		model.addAttribute("function", function);
		model.addAttribute("tenDonViSuDung", tenDonViSuDung);
		model.addAttribute("phieuKKSo", phieuKKSo);
		return "jspiso/baocao/deviceGeneralList";
	}
	
	@RequestMapping("/data")
	public void doGet(
			   @RequestParam(required = false) String locationName, 
			   @RequestParam(required = false) String neType,
			   ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<IsoInventory> reportGeneralList = null;
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
		List<CTableConfig> columnList = cTableConfigDAO.getTableConfigGet("ISO_REPORT_GENERAL", sortfield);
		List<CTableConfig> tableConfigList = cTableConfigDAO.getTableConfigGet("ISO_REPORT_GENERAL", null);
		
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
			reportGeneralList = isoInventoryDAO.getReportGeneralList(null, null, null, null, null, locationName, neType, null, startRecord, endRecord, sortfield, sortorder, strWhere);
			totalRow = isoInventoryDAO.countReportGeneralList(null, null, null, null, null, locationName, neType, null, strWhere);
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
		String jsonCartList = gs.toJson(reportGeneralList);
		strjson+= jsonCartList;
	    strjson += "}]";
	    
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
   	
   	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "reportGeneralDocx", method = RequestMethod.GET)
	public String reportGeneralDocx(
			@RequestParam(required = false) String locationName, 
			@RequestParam(required = false) String tenDonViSuDung,
			@RequestParam(required = false) String phieuKKSo,
			@RequestParam(required = false) String sortfield,
  			@RequestParam(required = false) String sortorder,
  			@RequestParam(required = false) String strWhere,
			ModelMap model, HttpServletRequest request,HttpServletResponse response) throws JRException, IOException {
   		strWhere = strWhere.replace("___", "%");
 		List<IsoInventory> reportDetailList = isoInventoryDAO.getReportGeneralList(null, null, null, null, null, locationName, null, null, null, null, sortfield, sortorder, strWhere);
		
		String dateNow = df.format(new Date());
		String hour = df_hh.format(new Date());
		InputStream inputStream = new FileInputStream (request.getSession().getServletContext().getRealPath("/report/generalDevice.jrxml")); 
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(reportDetailList);
		Map parameters = new HashMap();
		parameters.put("titleHeaderRight", "BẢNG KIỂM KÊ THIẾT BỊ DỤNG CỤ QUẢN LÝ THUỘC TÀI SẢN CỐ ĐỊNH");
		parameters.put("titleDateTime", "Có đến " + hour + "h ngày " + dateNow);
		parameters.put("tenDonViSuDung", tenDonViSuDung + " - "  + locationName);
		String vPhieu = "";
		if(phieuKKSo != null)
			vPhieu = "Phiếu KK số " +phieuKKSo + "/TBCTK";
		parameters.put("phieuKKSo", vPhieu);
		
		JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
		
		response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		response.setHeader("Content-Disposition", "inline; filename=\"generalDevice__"+dateNow+".docx\"");
		
		JRDocxExporter exporterDocx = new JRDocxExporter();
		exporterDocx.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		OutputStream ouputStream = response.getOutputStream();
		exporterDocx.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
		exporterDocx.exportReport();	
		response.getOutputStream().flush();
		response.getOutputStream().close();
		
		return "jspiso/baocao/deviceGeneralList";
	}
}
