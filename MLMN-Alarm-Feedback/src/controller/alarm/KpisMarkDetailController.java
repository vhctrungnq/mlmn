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
import vo.VHSiteLevelTh;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.ExportTools;
import vo.dictionary.TableConfigsHelper;

import com.google.gson.Gson;

import dao.CTableConfigDAO;
import dao.SysUserAreaDAO;
import dao.VHSiteLevelThDAO;

/**
 * Ten file: KpisMarkGroupController.java
 * Muc dich: Cham diem ca nhan theo nhom
 * @author ThangPX
 * Ngay tao: 29/05/2014
 * Lich su thay doi:
 *  
 */

@Controller
@RequestMapping("/alarm/kpi-mark-detail/*")
public class KpisMarkDetailController {

	@Autowired
	private VHSiteLevelThDAO vHSiteLevelThDAO;
	@Autowired  
	private CTableConfigDAO cTableConfigDAO; 
	@Autowired
	private SysUserAreaDAO sysUserAreaDAO;
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	DateFormat df_full = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	@RequestMapping(value = "{function}")
    public String list(@RequestParam(required = false) String startDate,
					   @RequestParam(required = false) String endDate, 
					   @RequestParam(required = false) String dept, 
					   @RequestParam(required = false) String team, 
					   @RequestParam(required = false) String group, 
					   @RequestParam(required = false) String siteLevel, 
					   @RequestParam(required = false) String shiftTime, 
					   @RequestParam(required = false) String causeby,
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
		String exportFileName = "ChamdiemKPIs_Chitiet_" + dateNow;
		model.addAttribute("exportFileName", exportFileName);
		
		 
		String filter = "";
		String temp = "";
		if(dept != null){
			filter += "dept=" + dept; 
			temp = "&";
		}
		if(team != null){
			filter +=  temp + "team=" + team; 
			temp = "&";
		}
		if(group != null){
			filter +=  temp + "group=" + group; 
			temp = "&";
		}
		if(siteLevel != null){
			filter +=  temp + "siteLevel=" + siteLevel; 
			temp = "&";
		}
		if(causeby != null){
			filter +=  temp + "causeby=" + causeby; 
			temp = "&";
		}
		if(shiftTime != null){
			filter +=  temp + "shiftTime=" + shiftTime; 
			temp = "&";
		}
		if(startDate != null){
			filter +=  temp + "startDate=" + startDate; 
			temp = "&";
		}
		if(endDate != null){
			filter += temp + "endDate=" + endDate;  
		} 
		if(filter != "")
			filter = "?" + filter;
		
		//do du lieu ra grid
		List<CTableConfig> configList = cTableConfigDAO.getTableConfigsForGrid("V_RP_DY_SITE_DETAIL");
		//Lay du lieu cac cot an hien cua grid
		List<CTableConfig> listSource = cTableConfigDAO.getColumnList("V_RP_DY_SITE_DETAIL");
		//Lay duong link url de lay du lieu do vao grid
		String url = "data.htm" + filter;
		//lay du lieu column group cua grid
		 
		//Grid
		String gridReport = TableConfigsHelper.getGridReportDontPaging(configList, "jqxgrid", url, "jqxlistbox", listSource, "Menu", "Yes", null, "singlecell", null);
		model.addAttribute("gridReport", gridReport);
		
		String cn="";
		//Lay danh sach team theo user
		List<String> teamList = sysUserAreaDAO.getSubTeamList(null,null);
		String teamArray="var teamList = new Array(";//Danh sach district cho cobobox
		cn="";
		for (String dis : teamList) {
			teamArray = teamArray + cn +"\""+dis+"\"";
			cn=",";
		}
		teamArray = teamArray+");";
		model.addAttribute("teamList", teamArray);
		
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate); 
		model.addAttribute("function", function);
		model.addAttribute("dept", dept);
		model.addAttribute("team", team);
		model.addAttribute("group", group);
		model.addAttribute("siteLevel", siteLevel);
		model.addAttribute("causeby", causeby);
		model.addAttribute("shiftTime", shiftTime);
		return "jspalarm/kpimark/detailList";
	}
	
	@RequestMapping("/data")
	public void doGet(@RequestParam(required = false) String startDate,
			   @RequestParam(required = false) String endDate, 
			   @RequestParam(required = false) String dept, 
			   @RequestParam(required = false) String team, 
			   @RequestParam(required = false) String group, 
			   @RequestParam(required = false) String siteLevel, 
			   @RequestParam(required = false) String shiftTime, 
			   @RequestParam(required = false) String causeby,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		List<VHSiteLevelTh> siteDeptsList = vHSiteLevelThDAO.getInfo(startDate, endDate, dept, team, group, siteLevel, shiftTime, causeby);
		
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
			@RequestParam(required = false) String dept, 
			@RequestParam(required = false) String team, 
			@RequestParam(required = false) String group,
			@RequestParam(required = false) String siteLevel,
			@RequestParam(required = false) String shiftTime,
			@RequestParam(required = false) String causeby,
  			HttpServletRequest request, HttpServletResponse response) throws Exception {
  		
  		// temp file
		String basePath = request.getSession().getServletContext().getRealPath("");
		String dataDir = basePath + "/upload";
		String tempName = UUID.randomUUID().toString();
		String ext = "xls";
		String outfile = tempName + "." + ext;
		File tempFile = new File(dataDir + "/" + outfile);
		List<VHSiteLevelTh> siteList = vHSiteLevelThDAO.getInfo(startDate, endDate,dept, team, group,siteLevel,shiftTime, causeby); 
		exportReport(tempFile, siteList);
		
		// return
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String filename = "ChamdiemKPIs_Chitiet_"+ dateNow;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());

		tempFile.delete();
  		return null;
  	}
	
	private void exportReport(File tempFile, List<VHSiteLevelTh> siteList) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("Report", 0);
			
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
 
			
			String str="Ngày bắt đầu,Ngày kết thúc,Thời gian MLL,ĐàiVT,Tổ,Nhóm,DS site,Loại Alarm,Alarm,Nguyên nhân,Alarm Info,Site cấp 1,Site cấp 2,Site cấp 3,Site cấp 4,Ngày trực,Ca trực";
			Label temp;
			
			String[] s = str.split(",");
			for(int i=0; i < s.length; i++)
			{
				temp = new Label(i, 0, s[i], cellFormatHeader);
				sheet.addCell(temp);
			}
		    int k = 1;
			Label record2;
			for (VHSiteLevelTh record : siteList) {
				int j=0;
			    record2 = new Label(j, k,df_full.format(record.getStime()), cellFormat);
				sheet.addCell(record2);
				j++; 
				record2 = new Label(j, k,df_full.format(record.getEtime()), cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j, k, record.getSiteTime(), cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = new Label(j, k,record.getDept(), cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = new Label(j, k,record.getTeam(), cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = new Label(j, k,record.getGroups(), cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = new Label(j, k,record.getSiteList(), cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = new Label(j, k,record.getAlarmType(), cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = new Label(j, k,record.getAlarm(), cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = new Label(j, k,record.getCauseby(), cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = new Label(j, k,record.getAlarmInfo(), cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j, k, record.getSiteQl1(), cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j, k, record.getSiteQl2(), cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j, k, record.getSiteQl3(), cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = checkNull(j, k, record.getSiteQl4(), cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = new Label(j, k,record.getGiaoCaTruc(), cellFormat);
				sheet.addCell(record2);
				j++;
				record2 = new Label(j, k,record.getCaTruc(), cellFormat);
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
