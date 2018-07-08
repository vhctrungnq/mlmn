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
import vo.VRpDySiteGroupsCausby;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.ExportTools;
import vo.dictionary.TableConfigsHelper;

import com.google.gson.Gson;

import dao.CTableConfigDAO;
import dao.VRpDySiteGroupsCausbyDAO;

/**
 * Ten file: KpisMarkGroupCausebyController.java
 * Muc dich: Cham diem ca nhan theo nguyen nhan mat lien lac
 * @author ThangPX
 * Ngay tao: 29/05/2014
 * Lich su thay doi:
 *  
 */

@Controller
@RequestMapping("/alarm/kpi-mark-group-causeby/*")
public class KpisMarkGroupCausebyController {

	@Autowired
	private VRpDySiteGroupsCausbyDAO vRpDySiteGroupsDAO;
	@Autowired  
	private CTableConfigDAO cTableConfigDAO; 
	public static final String MESSAGES_KEY = "successMessagesKey";
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
		String exportFileName = "ChamdiemKPIs_Theonguyennhan_MLL_" + dateNow;
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
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("V_RP_DY_SITE_GROUPS_CAUSEBY");
		//Lay du lieu cac cot an hien cua grid
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList("V_RP_DY_SITE_GROUPS_CAUSEBY");
		//Lay duong link url de lay du lieu do vao grid
		String url = "data.htm" + filter;
		//lay du lieu column group cua grid
		String groupColumn="";
		
		List<String> columnGrid = cTableConfigDAO.getGroupList("V_RP_DY_SITE_GROUPS_CAUSEBY");
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
		
		return "jspalarm/kpimark/groupCausebyList";
	}
	
	@RequestMapping("/data")
	public void doGet(@RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate,  
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		List<VRpDySiteGroupsCausby> siteDeptsList = vRpDySiteGroupsDAO.getInfo(startDate, endDate);
		
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
		List<VRpDySiteGroupsCausby> siteDeptsList = vRpDySiteGroupsDAO.getInfo(startDate, endDate);
		exportReport(tempFile, siteDeptsList);
		
		// return
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String filename = "ChamdiemKPIs_Theonguyennhan_MLL_"+ dateNow;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();
  		return null;
  	}
	
	private void exportReport(File tempFile, List<VRpDySiteGroupsCausby> siteTeamsList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("Report", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
 
			
			String str="Ngày tổng hợp,Tổ VT, Nhóm,Số trạm MLL do mất AC<Ngày>,Tổng T/g MLL do mất AC<Ngày>,T/g UCTT MLL do mất AC<Ngày>,Số trạm MLL do TD VTT<Ngày>,Tổng T/g MLL do TD VTT<Ngày>,T/g UCTT MLL do TD VTT<Ngày>,Số trạm MLL do TD Viba<Ngày>,Tổng T/g MLL do TD Viba<Ngày>,"
					+"T/g UCTT MLL do TD Viba<Ngày>,Số trạm MLL do lỗi PC<Ngày>,Tổng T/g MLL do lỗi PC<Ngày>,T/g UCTT MLL do lỗi PC<Ngày>,Số trạm MLL do hạ tầng<Ngày>,Tổng T/g MLL do hạ tầng<Ngày>,T/g UCTT MLL do hạ tầng<Ngày>,"
					+"Số trạm MLL<Ngày>,Tổng T/g MLL<Ngày>,T/g UCTT MLL<Ngày>,Chỉ tiêu MLL<Ngày>,Đánh giá MLL<Ngày>,Số trạm MLL do mất AC<Đêm>,Tổng T/g MLL do mất AC<Đêm>,T/g UCTT MLL do mất AC<Đêm>,Số trạm MLL do TD VTT<Đêm>,Tổng T/g MLL do TD VTT<Đêm>,"
					+"T/g UCTT MLL do TD VTT<Đêm>,Số trạm MLL do TD Viba<Đêm>,Tổng T/g MLL do TD Viba<Đêm>,T/g UCTT MLL do TD Viba<Đêm>,Số trạm MLL do lỗi PC<Đêm>,Tổng T/g MLL do lỗi PC<Đêm>,T/g UCTT MLL do lỗi PC<Đêm>,Số trạm MLL do hạ tầng<Đêm>,"
					+"Tổng T/g MLL do hạ tầng<Đêm>,T/g UCTT MLL do hạ tầng<Đêm>, Số trạm MLL<Đêm>,Tổng T/g MLL<Đêm>,T/g UCTT MLL<Đêm>,Chỉ tiêu MLL<Đêm>,Đánh giá MLL<Đêm>";
			Label temp;
			
			String[] s = str.split(",");
			for(int i=0; i < s.length; i++)
			{
				temp = new Label(i, 0, s[i], cellFormatHeader);
				sheet.addCell(temp);
			}
		    int k = 1;
			Label record2;
			for (VRpDySiteGroupsCausby record : siteTeamsList) {
				int j=0;
				Label record1 = new Label(j, k,df.format(record.getSumDate()), cellFormat);
				sheet.addCell(record1);
				j++; 
				Label record0 = new Label(j, k, record.getTeam(), cellFormat);
				sheet.addCell(record0);
				j++;
			    record2 = new Label(j, k, record.getTeamGroups(), cellFormat);
				sheet.addCell(record2);
				j++;
				
				record2 = checkNull(j,k,record.getLostContactNumAc(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getLostContactAc(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getPrcessTimeAc(),cellFormat);
				sheet.addCell(record2);
				j++;
				
				record2 = checkNull(j,k,record.getLostContactNumTdVtt(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getLostContactTdVtt(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getPrcessTimeTdVtt(),cellFormat);
				sheet.addCell(record2);
				j++;
				 
				record2 = checkNull(j,k,record.getLostContactNumTdViba(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getLostContactTdViba(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getPrcessTimeTdViba(),cellFormat);
				sheet.addCell(record2); 
				
				j++;
				record2 = checkNull(j,k,record.getLostContactNumTdPc(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getLostContactTdPc(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getPrcessTimeTdPc(),cellFormat);
				sheet.addCell(record2); 
			 
				 
				j++;
				record2 = checkNull(j,k,record.getLostContactNumHt(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getLostContactHt(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getPrcessTimeHt(),cellFormat);
				sheet.addCell(record2); 
				
				j++;
				record2 = checkNull(j,k,record.getLostContactNumSum(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getLostContactSum(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getPrcessTimeSum(),cellFormat);
				sheet.addCell(record2); 
				j++;
				record2 = checkNull(j,k,record.getChiTieuMllNgay(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = new Label(j, k, record.getDanhGiaMllNgay(), cellFormat);
				sheet.addCell(record2);
				j++;
				
				record2 = checkNull(j,k,record.getLostContactNumAcD(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getLostContactAcD(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getPrcessTimeAcD(),cellFormat);
				sheet.addCell(record2);
				j++;
				
				record2 = checkNull(j,k,record.getLostContactNumTdVttD(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getLostContactTdVttD(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getPrcessTimeTdVttD(),cellFormat);
				sheet.addCell(record2);
				j++;
				
				 
				record2 = checkNull(j,k,record.getLostContactNumTdVibaD(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getLostContactTdVibaD(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getPrcessTimeTdVibaD(),cellFormat);
				sheet.addCell(record2); 
				 
				j++;
				record2 = checkNull(j,k,record.getLostContactNumTdPcD(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getLostContactTdPcD(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getPrcessTimeTdPcD(),cellFormat);
				sheet.addCell(record2); 
				j++;
				record2 = checkNull(j,k,record.getLostContactNumHtD(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getLostContactHtD(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getPrcessTimeHtD(),cellFormat);
				sheet.addCell(record2); 
				
				j++;
				record2 = checkNull(j,k,record.getLostContactNumSumD(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getLostContactSumD(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j,k,record.getPrcessTimeSumD(),cellFormat);
				sheet.addCell(record2); 
				j++;
				record2 = checkNull(j,k,record.getChiTieuMllDem(),cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = new Label(j, k, record.getDanhGiaMllDem(), cellFormat);
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
