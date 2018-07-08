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
import vo.VRpDySiteTeams;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.ExportTools;
import vo.dictionary.TableConfigsHelper;

import com.google.gson.Gson;

import dao.CTableConfigDAO;
import dao.VRpDySiteTeamsDAO;

/**
 * Ten file: KpisMarkTeamController.java
 * Muc dich: Cham diem ca nhan theo tổ VT
 * @author ThangPX
 * Ngay tao: 29/05/2014
 * Lich su thay doi:
 *  
 */

@Controller
@RequestMapping("/alarm/kpi-mark-team/*")
public class KpisMarkTeamController {

	@Autowired
	private VRpDySiteTeamsDAO vRpDySiteTeamsDAO;
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
		String exportFileName = "ChamdiemKPIs_TheoTo_" + dateNow;
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
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("V_RP_DY_SITE_TEAMS");
		//Lay du lieu cac cot an hien cua grid
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList("V_RP_DY_SITE_TEAMS");
		//Lay duong link url de lay du lieu do vao grid
		String url = "data.htm" + filter;
		//lay du lieu column group cua grid
		String groupColumn="";
		
		List<String> columnGrid = cTableConfigDAO.getGroupList("V_RP_DY_SITE_TEAMS");
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
		
		return "jspalarm/kpimark/teamList";
	}
	
	@RequestMapping("/data")
	public void doGet(@RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate,  
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		List<VRpDySiteTeams> siteDeptsList = vRpDySiteTeamsDAO.getInfo(startDate, endDate);
		
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
		List<VRpDySiteTeams> siteDeptsList = vRpDySiteTeamsDAO.getInfo(startDate, endDate);
		exportReport(tempFile, siteDeptsList);
		
		// return
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String filename = "ChamdiemKPIs_TheoTo_"+ dateNow;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();
  		return null;
  	}
	
	private void exportReport(File tempFile, List<VRpDySiteTeams> siteTeamsList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("Report", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
 
			
			String str="Ngày tổng hợp,Tổ VT,Số lần MLL tối đa trạm cấp 1 KH,Số lần MLL tối đa trạm cấp 1 TH,Số lần MLL tối đa trạm cấp 2 KH, Số lần MLL tối đa trạm cấp 2 TH, Số lần MLL tối đa trạm cấp 3 KH, Số lần MLL tối đa trạm cấp 3 TH," +
					"TGXL MLL ban ngày trạm cấp 1 KH, TGXL MLL ban ngày trạm cấp 1 TH, TGXL MLL ban ngày trạm cấp 2 KH, TGXL MLL ban ngày trạm cấp 2 TH, TGXL MLL ban ngày trạm cấp 3 KH, TGXL MLL ban ngày trạm cấp 3 TH," +
					"TGXL MLL ban đêm trạm cấp 1 KH, TGXL MLL ban đêm trạm cấp 1 TH, TGXL MLL ban đêm trạm cấp 2 KH, TGXL MLL ban đêm trạm cấp 2 TH, TGXL MLL ban đêm trạm cấp 3 KH, TGXL MLL ban đêm trạm cấp 3 TH";
			Label temp;
			
			String[] s = str.split(",");
			for(int i=0; i < s.length; i++)
			{
				temp = new Label(i, 0, s[i], cellFormatHeader);
				sheet.addCell(temp);
			}
		    int k = 1;
			Label record2;
			for (VRpDySiteTeams record : siteTeamsList) {
				int j=0;
				Label record1 = new Label(j, k,df.format(record.getSumDate()), cellFormat);
				sheet.addCell(record1);
				j++; 
				Label record0 = new Label(j, k, record.getTeam(), cellFormat);
				sheet.addCell(record0);
				j++;
				
				record2 = checkNull(j,k,record.getLostContactNum1Kh(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getLostContactNum1Th(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getLostContactNum2Kh(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getLostContactNum2Th(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getLostContactNum3Kh(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getLostContactNum3Th(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getPrcessTime1Kh(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getPrcessTime1Th(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getPrcessTime2Kh(),cellFormat);
				sheet.addCell(record2);
				j++;
				
				record2 = checkNull(j,k,record.getPrcessTime2Th(),cellFormat);
				sheet.addCell(record2);
				j++;
				
				record2 = checkNull(j,k,record.getPrcessTime3Kh(),cellFormat);
				sheet.addCell(record2);
				j++;
				
				record2 = checkNull(j,k,record.getPrcessTime3Th(),cellFormat);
				sheet.addCell(record2);
				j++;
				
				record2 = checkNull(j,k,record.getNightPrcessTime1Kh(),cellFormat);
				sheet.addCell(record2);
				j++;
				
				record2 = checkNull(j,k,record.getNightPrcessTime1Th(),cellFormat);
				sheet.addCell(record2);
				j++;
				
				record2 = checkNull(j,k,record.getNightPrcessTime2Kh(),cellFormat);
				sheet.addCell(record2);
				j++;
				
				record2 = checkNull(j,k,record.getNightPrcessTime2Th(),cellFormat);
				sheet.addCell(record2);
				j++;
				
				record2 = checkNull(j,k,record.getNightPrcessTime3Kh(),cellFormat);
				sheet.addCell(record2);
				j++;
				
				record2 = checkNull(j,k,record.getNightPrcessTime3Th(),cellFormat);
				sheet.addCell(record2); 
			 
				k++;
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
