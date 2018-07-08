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

import vo.CTableConfig;
import vo.VRpDySiteDepts;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.ExportTools;
import vo.dictionary.TableConfigsHelper;

import com.google.gson.Gson;

import dao.CTableConfigDAO;
import dao.VRpDySiteDeptsDAO;

/**
 * Ten file: KpisMarkController.java
 * Muc dich: Cham diem ca nhan theo don vi
 * @author ThangPX
 * Ngay tao: 29/05/2014
 * Lich su thay doi:
 *  
 */

@Controller
@RequestMapping("/alarm/kpi-mark-dept/*")
public class KpisMarkDeptController {

	@Autowired
	private VRpDySiteDeptsDAO vRpDySiteDeptsDAO;
	@Autowired  
	private CTableConfigDAO cTableConfigDAO; 
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	@RequestMapping(value = "{function}")
    public String list(@RequestParam(required = false) String startDate,
					   @RequestParam(required = false) String endDate, 
					   @PathVariable String function, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		
		// Ngay thang
		if (startDate == null || startDate.equals("")|| DateTools.isValid("dd/MM/yyyy", startDate)==false) {			

			startDate = df.format(DateTools.minusWeek(new Date(),1));
		
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
		String exportFileName = "ChamdiemKPIs_TheoDV_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		 
		String filter = "";
		String temp = "";
		if(startDate != null){
			filter += "startDate=" + startDate; 
			temp = "&";
		}
		if(endDate != null){
			filter += temp + "endDate=" + endDate;  
		} 
		if(filter != "")
			filter = "?" + filter;
		
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("V_RP_DY_SITE_DEPTS");
		//Lay du lieu cac cot an hien cua grid
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList("V_RP_DY_SITE_DEPTS");
		//Lay duong link url de lay du lieu do vao grid
		String url = "data.htm" + filter;
		//lay du lieu column group cua grid
		String groupColumn="";
		
		List<String> columnGrid = cTableConfigDAO.getGroupList("V_RP_DY_SITE_DEPTS");
		String con2="";
		groupColumn =" var columngroups=[";
		for (String alarmTypeG : columnGrid) {
			groupColumn= groupColumn+		con2+"{ text: '"+alarmTypeG+"', align: 'center', name: '"+alarmTypeG+"' }";
			con2 = ",";
		}
		groupColumn= groupColumn+	"]; ";
		//Grid
		String gridReport = TableConfigsHelper.getGridReportDontPaging(configList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", "Yes", null, "singlecell", groupColumn);
		model.addAttribute("gridReport", gridReport);
		
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate); 
		model.addAttribute("function", function);
		
		return "jspalarm/kpimark/deptList";
	}
	
	@RequestMapping("/data")
	public void doGet(@RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate,  
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		List<VRpDySiteDepts> siteDeptsList = vRpDySiteDeptsDAO.getInfo(startDate, endDate);
		
		Gson gs = new Gson();
		String jsonCartList = gs.toJson(siteDeptsList);
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		out.println(jsonCartList);
		out.close();
	}
	
	@RequestMapping("exportData")
  	public ModelAndView exportData(
  			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate, 	  
  			HttpServletRequest request, HttpServletResponse response) throws Exception {
  		
  		// temp file
		String basePath = request.getSession().getServletContext().getRealPath("");
		String dataDir = basePath + "/upload";
		String tempName = UUID.randomUUID().toString();
		String ext = "xls";
		String outfile = tempName + "." + ext;
		File tempFile = new File(dataDir + "/" + outfile);
		List<VRpDySiteDepts> siteDeptsList = vRpDySiteDeptsDAO.getInfo(startDate, endDate);
		exportReport(tempFile, siteDeptsList);
		
		// return
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String filename = "ChamdiemKPIs_TheoDV_"+ dateNow;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();
  		return null;
  	}
	
	private void exportReport(File tempFile, List<VRpDySiteDepts> siteDeptsList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("Report", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			
			int i=0;
			
			Label label1 = new Label(i, 0, "Ngày tổng hợp", cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label0 = new Label(i, 0, "Chỉ tiêu", cellFormatHeader);
			sheet.addCell(label0);
			i++;
			Label label2 = new Label(i, 0, "VTBD KH", cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "VTBD TH", cellFormatHeader);
			sheet.addCell(label3);
			i++;
			Label label4 = new Label(i, 0, "VTDN KH", cellFormatHeader);
			sheet.addCell(label4);
			i++;
			Label label5 = new Label(i, 0, "VTDN TH", cellFormatHeader);
			sheet.addCell(label5);
			i++;
			Label label6 = new Label(i, 0, "Phòng KTKT KH", cellFormatHeader);
			sheet.addCell(label6);
			i++;
			Label label7 = new Label(i, 0, "Phòng KTKT TH", cellFormatHeader);
			sheet.addCell(label7);
			i++;
			Label label8 = new Label(i, 0, "Đài 1090 KH", cellFormatHeader);
			sheet.addCell(label8);
			i++;
			Label label9 = new Label(i, 0, "Đài 1090 TH", cellFormatHeader);
			sheet.addCell(label9);  
			i = 1;
			Label record2;
			for (VRpDySiteDepts record : siteDeptsList) {
				int j=0;
				Label record0 = new Label(j, i, record.getColumnIndexs(), cellFormat);
				sheet.addCell(record0);
				j++;
				Label record1 = new Label(j, i,df.format(record.getSumDate()), cellFormat);
				sheet.addCell(record1);
				j++; 
				record2 = checkNull(j,i,record.getTt6VtbdKh(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,i,record.getTt6VtbdTh(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,i,record.getTt6VtdnKh(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,i,record.getTt6VtdnTh(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,i,record.getTt6PhongktktKh(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,i,record.getTt6PhongktktTh(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,i,record.getTt6Dai1090Kh(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,i,record.getTt6Dai1090Th(),cellFormat);
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
	private Label checkNull(int j, int i, Integer src,WritableCellFormat cellFormat)
	{
		Label record;
		if(src!= null)
			record = new Label(j, i, src.toString(), cellFormat);
		else
			record = new Label(j, i, "", cellFormat);
		
		return record;
	}
}
