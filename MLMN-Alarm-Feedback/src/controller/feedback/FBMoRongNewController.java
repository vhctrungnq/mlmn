package controller.feedback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import vn.com.vhc.alarm.core.AL_Global;
import vo.CSystemConfigs;
import vo.CTableConfig;
import vo.FbProcess;
import vo.FbReport;
import vo.SYS_PARAMETER;
import vo.SysMailParameter;
import vo.SysReportBlock;
import vo.SysUsers;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.HelpTableConfigs;
import vo.alarm.utils.Mail;
import vo.alarm.utils.SendMailSetting;
import vo.alarm.utils.URLCoderTools;
import controller.BaseController;
import controller.alarm.AlWorkCalendarShiftController;
import dao.CHighlightConfigsDAO;
import dao.CSystemConfigsDAO;
import dao.CTableConfigDAO;
import dao.FbProcessDAO;
import dao.SYS_PARAMETERDAO;
import dao.SysMailParameterDAO;
import dao.SysReportBlockDAO;
import dao.SysUsersDAO;

@Controller
@RequestMapping("/report-fb/*")
public class FBMoRongNewController extends BaseController {
	@Autowired
	private SYS_PARAMETERDAO sysParameterDao;
	@Autowired
 	private FbProcessDAO fbProcessDAO;
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	
	@Autowired
	private CHighlightConfigsDAO cHighlightConfigsDAO;
	
	@Autowired
	private SysReportBlockDAO sysReportBlockDAO;

	@Autowired
	private SysUsersDAO sysUsersDao;
	
	@Autowired
	private CSystemConfigsDAO cSystemConfigsDAO;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	/*tao bao cao cua email gui hang ngay hang gio. AnhCTV:30/10/2015
	 * function:dy/week/month/year/option: Bao cao muc ngay, tuan, thang, nam,tuy chon */
	@RequestMapping(value = "{function}")
	public ModelAndView list(
			@RequestParam(required = false) String sdate,
			@RequestParam(required = false) String edate,
			@RequestParam(required = false) Integer sweek,
			@RequestParam(required = false) Integer smonth,
			@RequestParam(required = false) Integer syear,
			@RequestParam(required = false) String region,
			@RequestParam(required = false) String province,
			@RequestParam(required = false) String fbType,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String sqlWhere,
			@PathVariable String function,
			Model model, HttpServletRequest request) throws ParseException, SQLException {
		
		List<SYS_PARAMETER> sysParemeterTitle = sysReportBlockDAO.titleRpFeedbackMLMN(function);
		String subject="";
		String title="";
		if(sysParemeterTitle.size() > 0)
		{
			subject = sysParemeterTitle.get(0).getValue() ;
			title = subject;
		}
		
		
		//Kiem tra dieu kien tim kiem 
		Calendar cal = Calendar.getInstance();
		
		 if (function.equalsIgnoreCase("week")){
			 Calendar sDateCalendar = Calendar.getInstance();
				sDateCalendar.setFirstDayOfWeek(Calendar.WEDNESDAY);
				sDateCalendar.setMinimalDaysInFirstWeek(4);
				if(sweek == null)
					sweek = sDateCalendar.get(Calendar.WEEK_OF_YEAR);
				if (syear == null)
					syear = sDateCalendar.get(Calendar.YEAR);
			 sdate= df.format(DateTools.convertWeekToFirstDate(sweek,syear));
			 edate= df.format(DateTools.convertWeekToLastDate(sweek,syear));
			 title =title+" "+ sweek+"/"+syear+" ("+sdate+"-"+edate+")";
			subject +=" ("+sweek+"/"+syear+")";
		} else if (function.equalsIgnoreCase("month")){
			
			String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		    model.addAttribute("monthList", monthList);
			
			if (smonth == null) {
				smonth = cal.get(Calendar.MONTH);
				if (smonth==0) smonth=1;
				syear =  cal.get(Calendar.YEAR);
			}
			title =title+" "+ smonth+"/"+syear;
			subject +=" ("+smonth+"/"+syear+")";
		}
		else if (function.equalsIgnoreCase("quarter")){
			
			String[] quarterList = {"1", "2", "3", "4"};
		    model.addAttribute("quarterList", quarterList);
			
			if (smonth == null) {
				smonth = (cal.get(Calendar.MONTH)-1)/3+1;
				syear =cal.get(Calendar.YEAR);
			}
			title =title+" "+ smonth+"/"+syear;
			subject +=" ("+smonth+"/"+syear+")";
		}
		else if (function.equalsIgnoreCase("year")){
			if (syear == null) {
				syear = cal.get(Calendar.YEAR);
			}
			title =title+" "+syear;
			subject +=" ("+syear+")";
		}
		else
		{
			if (sdate == null || sdate.equals("")||(sdate!=null && !sdate.equals("") && DateTools.isValid("dd/MM/yyyy", sdate)==false))
			{
				sdate = df.format(new Date());
			}
			
			if (function.equalsIgnoreCase("option"))
			{
				if (edate == null || edate.equals("")||(edate!=null && !edate.equals("") && DateTools.isValid("dd/MM/yyyy", edate)==false))
				{
					edate = df.format(new Date());
				}
				title =title+" từ "+sdate+" đến "+edate;
				subject +=" ("+sdate+"-"+edate+")";
			}	
			else
			{
				edate = sdate;
				subject +=" ("+sdate+")";
			}
				
		}
		
		List<FbReport> reportFBFBType = fbProcessDAO.getReportFBFBType(sdate, edate, sweek, smonth,syear,region,province,fbType,status,sqlWhere,function,"1",null);
		model.addAttribute("reportFBFBType", reportFBFBType);
		
		List<FbReport> reportFBDVTProcess = fbProcessDAO.getReportFBDVTProcess(sdate, edate, sweek, smonth,syear,region,province,fbType,status,sqlWhere,function,"1",null);
		model.addAttribute("reportFBDVTProcess", reportFBDVTProcess);
		
		List<FbReport> reportFBRequest = fbProcessDAO.getReportFBRequest(sdate, edate, sweek, smonth,syear,region,province,fbType,status,sqlWhere,function,"1",null);
		model.addAttribute("reportFBRequest", reportFBRequest);
		
		List<FbReport> reportFBRise = fbProcessDAO.getReportFBRise(sdate, edate, sweek, smonth,syear,region,province,fbType,status,sqlWhere,function,"1",null);
		model.addAttribute("reportFBRise", reportFBRise);
		
		//Danh sach nguoi nhan mail
		
		List<SysUsers> userList = new ArrayList<SysUsers>();
		userList = sysUsersDao.getUserByMaPhong(null);
 		String groupArray="var emailList = new Array(";
		String cn="";
		for (SysUsers sys : userList) {
			groupArray = groupArray + cn +"\""+sys.getEmail()+"\"";
			cn=",";
		}
		groupArray = groupArray+");";
		model.addAttribute("emailList", groupArray);
		model.addAttribute("subject", subject);
		model.addAttribute("title",title);
		
		
		model.addAttribute("sdate", sdate);
		model.addAttribute("edate", edate);
		model.addAttribute("sweek", sweek);
		model.addAttribute("smonth", smonth);
		model.addAttribute("syear", syear);
		model.addAttribute("region", region);
		model.addAttribute("province", province);
		model.addAttribute("fbType", fbType);
		model.addAttribute("status", status);
		model.addAttribute("sqlWhere", sqlWhere);
		model.addAttribute("function", function);
		model.addAttribute("funct", function);
		String exportFileNameFBType="";
		String exportFileNameDVTProcess="";
		String exportFileNameRequest="";
		String exportFileNameRise="";
		// Lay ten file export
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		exportFileNameFBType = "FB_LoaiPA_"+ function + dateNow;
		exportFileNameDVTProcess = "FB_DaiVT_"+ function + dateNow;
		exportFileNameRequest = "FB_LuyKe_"+ function + dateNow;
		exportFileNameRise = "FB_TangCao_"+ function + dateNow;
		model.addAttribute("exportFileNameFBType", exportFileNameFBType);
		model.addAttribute("exportFileNameDVTProcess", exportFileNameDVTProcess);
		model.addAttribute("exportFileNameRequest", exportFileNameRequest);
		model.addAttribute("exportFileNameRise", exportFileNameRise);	

		return new ModelAndView("jspfeedback/mlmn/reportDay");
		
		
	}
	
	@RequestMapping("export")
 	public String exportAll(@RequestParam(required = false) String sdate,
			@RequestParam(required = false) String edate,
			@RequestParam(required = false) String sweek,
			@RequestParam(required = false) String smonth,
			@RequestParam(required = false) String syear,
			@RequestParam(required = false) String region,
			@RequestParam(required = false) String province,
			@RequestParam(required = false) String fbType,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String sqlWhere,
			@RequestParam(required = false) String function,
			@RequestParam(required = false) String blockID,
			@RequestParam(required = false) String blockSQL,
 			HttpServletRequest request, HttpServletResponse response) throws Exception {
 		
 		if (sdate==null)
 			sdate = "";
 		if (edate==null)
 			edate = "";
 		if (sweek==null)
 			sweek = "";
 		if (smonth==null)
 			smonth="";
 		if (syear==null)
 			syear = "";
 		if (region==null)
 			region = "";
 		if (province==null)
 			province = "";
 		if (fbType==null)
 			fbType="";
 		if (status==null)
 			status = "";
 		if (sqlWhere==null)
 			sqlWhere = "";
 		if (function==null)
 			function = "";
 		if (blockID==null)
 			blockID = "";
 		if (blockSQL==null)
 			blockSQL = "";
 		
 	// Lay ten file export
 		/*Calendar currentDate = Calendar.getInstance();
 		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
 		String dateNow = formatter.format(currentDate.getTime());*/
 		String exportFileName= blockID.toUpperCase()+"_"+ sdate.replace("/", "");
 		
 	/*	SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
 		String dateNow2 = formatter2.format(currentDate.getTime());*/
 		// temp file
 		String basePath = request.getSession().getServletContext().getRealPath("");
 		String dataDir = basePath + "/upload";

 		String tempName = UUID.randomUUID().toString();

 		String ext = "xls";

 		String outfile = tempName + "." + ext;
 		// return
 		// Lay ten file export		
 		File tempFile = new File(dataDir + "/" + outfile);
 		String content="";
		//Tao file
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(exportFileName);
		
		int rownum=0;
		//style caption
		HSSFCellStyle style1 = workbook.createCellStyle();
        style1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont bold1 = workbook.createFont();
        bold1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        bold1.setFontHeightInPoints((short) 14);
        //bold1.setColor(HSSFColor.WHITE.index);
        style1.setFont(bold1);
        
        String title="BÁO CÁO FEEDBACK NGÀY "+sdate;
        HSSFRow xlsRow1 = sheet.createRow(rownum++);
        xlsRow1.setHeightInPoints(30);
        HSSFCell cell1 = xlsRow1.createCell(0);
        cell1.setCellStyle(style1);
        cell1.setCellValue(title);
        sheet.addMergedRegion(new CellRangeAddress(xlsRow1.getRowNum(),cell1.getColumnIndex(), xlsRow1.getRowNum(), 10));
        	
		
		//Tao style header cho moi block
		HSSFCellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        headerStyle.setFillBackgroundColor(new HSSFColor.GREEN().getIndex());
        headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headerStyle.setFillForegroundColor(new HSSFColor.GREEN().getIndex());
        
        HSSFFont bold = workbook.createFont();
        bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        bold.setColor(HSSFColor.WHITE.index);
        headerStyle.setFont(bold);
        
      //lay danh sach cac block trong email: ten
		List<SysReportBlock> blockList = sysReportBlockDAO.getBlockListReport("FEEDBACK",function);
		//Moi block:
		//lay cac cot config cho block
		//lay du lieu cho block
		//Tao grid hien thi len tren web
		List<String> gridList = new ArrayList<String>();
		for (SysReportBlock block : blockList) {
			String feedbackType = "FEEDBACK_"+function.toUpperCase();
			List<CTableConfig> columnList = cTableConfigDAO.getColumnReportBlock(feedbackType, block.getBlockId());
			// tap tieu de cho moi block
			rownum++;
			HSSFRow xlsRow = sheet.createRow(rownum++);
			xlsRow.setHeightInPoints(30);
            HSSFCell cell = xlsRow.createCell(0);
            cell.setCellStyle(style1);
            cell.setCellValue(block.getBlockName());
            sheet.addMergedRegion(new CellRangeAddress(xlsRow.getRowNum(),xlsRow.getRowNum(),cell.getColumnIndex(),  (columnList.size()-1)));
	           	
			// ghi du lieu ra workbook 
			List<String> paramList = new ArrayList<String>();
			paramList.add(sdate==null?"":sdate);
			paramList.add(edate==null?"":edate);
			paramList.add(sweek==null?"":sweek);
			paramList.add(smonth==null?"":smonth);
			paramList.add(syear==null?"":syear);
			paramList.add(region==null?"":region);
			paramList.add(province==null?"":province);
			paramList.add(fbType==null?"":fbType);
			paramList.add(status==null?"":status);
			paramList.add(sqlWhere==null?"":sqlWhere);
			paramList.add(function);
			paramList.add("1");		// report type: 1 - get data, 2 - get sql query
			paramList.add(null);
			
			rownum = ExportTools.exportToSheetContinue(block.getSqlWhere(),paramList, feedbackType,columnList,workbook,sheet,rownum,"Y");
			sheet.createRow(rownum++);
		}
		try {
			 FileOutputStream out =
	                new FileOutputStream(tempFile);
	        workbook.write(out);
	        out.close();
	        System.out.println("Excel written successfully..");   
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	        System.out.println("CREATE_FILE_EXPORT:"+ e); 
	    } 
		catch (IOException e) {
	    	
	    	 System.out.println("CREATE_CONTENT_EXPORT:"+ e); 
	        e.printStackTrace();
	    }	
 		response.setContentType("application/ms-excel");
 		response.setContentLength((int) tempFile.length());
 		response.setHeader("Content-Disposition", "attachment; filename=\"" + title + "." + ext + "\"");
 		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());
 		tempFile.delete();
 		
 		return null;
 	}
	
	@RequestMapping("exportOLd")
 	public String export(@RequestParam(required = false) String sdate,
			@RequestParam(required = false) String edate,
			@RequestParam(required = false) String sweek,
			@RequestParam(required = false) String smonth,
			@RequestParam(required = false) String syear,
			@RequestParam(required = false) String region,
			@RequestParam(required = false) String province,
			@RequestParam(required = false) String fbType,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String sqlWhere,
			@RequestParam(required = false) String function,
			@RequestParam(required = false) String blockID,
			@RequestParam(required = false) String blockSQL,
 			HttpServletRequest request, HttpServletResponse response) throws Exception {
 		
 		if (sdate==null)
 			sdate = "";
 		if (edate==null)
 			edate = "";
 		if (sweek==null)
 			sweek = "";
 		if (smonth==null)
 			smonth="";
 		if (syear==null)
 			syear = "";
 		if (region==null)
 			region = "";
 		if (province==null)
 			province = "";
 		if (fbType==null)
 			fbType="";
 		if (status==null)
 			status = "";
 		if (sqlWhere==null)
 			sqlWhere = "";
 		if (function==null)
 			function = "";
 		if (blockID==null)
 			blockID = "";
 		if (blockSQL==null)
 			blockSQL = "";
 		
 		// temp file
 		String basePath = request.getSession().getServletContext().getRealPath("");
 		String dataDir = basePath + "/upload";

 		String tempName = UUID.randomUUID().toString();

 		String ext = "xls";

 		String outfile = tempName + "." + ext;
 		// return
 		// Lay ten file export		
 		File tempFile = new File(dataDir + "/" + outfile);
 		List<String> paramList = new ArrayList<String>();
 		paramList.add(sdate==null?"":sdate);
		paramList.add(edate==null?"":edate);
		paramList.add(sweek==null?"":sweek);
		paramList.add(smonth==null?"":smonth);
		paramList.add(syear==null?"":syear);
		paramList.add(region==null?"":region);
		paramList.add(province==null?"":province);
		paramList.add(fbType==null?"":fbType);
		paramList.add(status==null?"":status);
		paramList.add(sqlWhere==null?"":sqlWhere);
		paramList.add(function);
		paramList.add("1");		// report type: 1 - get data, 2 - get sql query
		paramList.add(null);
		
		String feedbackType = "FEEDBACK_"+function.toUpperCase()+"_"+blockID.toUpperCase();
		
 		// Lay ten file export
 		Calendar currentDate = Calendar.getInstance();
 		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
 		String dateNow = formatter.format(currentDate.getTime());
 		String exportFileName= blockID.toUpperCase()+"_"+ dateNow;
 		
 		List<CTableConfig> columnList = cTableConfigDAO.getColumnList(feedbackType);
 		
 		ExportTools.exportToExcel(blockSQL,paramList,tempFile,exportFileName, feedbackType,null,columnList,"Y");
 		
 		response.setContentType("application/ms-excel");
 		response.setContentLength((int) tempFile.length());
 		response.setHeader("Content-Disposition", "attachment; filename=\"" + exportFileName + "." + ext + "\"");
 		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());
 		tempFile.delete();
 		
 		return null;
 	}
	
	/*tao bao cao cua email gui hang ngay hang gio. AnhCTV:30/10/2015
	 * function:dy/week/month/year/option: Bao cao muc ngay, tuan, thang, nam,tuy chon */
	@RequestMapping(value = "advance/{function}")
	public ModelAndView advanceReport(
			@RequestParam(required = false) String sdate,
			@RequestParam(required = false) String edate,
			@RequestParam(required = false) String sweek,
			@RequestParam(required = false) String eweek,
			@RequestParam(required = false) String smonth,
			@RequestParam(required = false) String emonth,
			@RequestParam(required = false) String syear,
			@RequestParam(required = false) String eyear,
			@RequestParam(required = false) String region,
			@RequestParam(required = false) String province,
			@RequestParam(required = false) String fbType,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String sqlWhere,
			@PathVariable String function,
			Model model, HttpServletRequest request) throws ParseException, SQLException {
		
		List<SYS_PARAMETER> sysParemeterTitle = sysReportBlockDAO.titleRpFeedbackMLMN(function);
		if(sysParemeterTitle.size() > 0)
		{
			model.addAttribute("title", sysParemeterTitle.get(0).getValue());
		}
		//Kiem tra dieu kien tim kiem 
		Calendar cal = Calendar.getInstance();
		
		 if (function.equalsIgnoreCase("week")){
			DateTime dt = new DateTime();
			if (eweek == null) {
				dt = dt.minusWeeks(1);
				eweek = String.valueOf(dt.getWeekOfWeekyear());
				eyear = String.valueOf(dt.getYear());
			}
			if (sweek == null) {
				sweek = eweek;
				syear = eyear;
			}
			
		} else if (function.equalsIgnoreCase("month")){
			
			String[] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		    model.addAttribute("monthList", monthList);
			
			if (emonth == null) {
				emonth = String.valueOf(cal.get(Calendar.MONTH));
				eyear = String.valueOf(cal.get(Calendar.YEAR));
			}
			if (smonth == null) {
				smonth = emonth;
				syear = eyear;
			}
			
		}
		else if (function.equalsIgnoreCase("quarter")){
			
			String[] quarterList = {"1", "2", "3", "4"};
		    model.addAttribute("quarterList", quarterList);
			
			if (emonth == null) {
				emonth = String.valueOf((cal.get(Calendar.MONTH)-1)/3+1);
				eyear = String.valueOf(cal.get(Calendar.YEAR));
			}
			if (smonth == null) {
				smonth = emonth;
				syear = eyear;
			}
			
		}
		else if (function.equalsIgnoreCase("year")){
			if (eyear == null) {
				eyear = String.valueOf(cal.get(Calendar.YEAR));
			}
			if (syear == null) {
				syear = eyear;
			}
		}
		else
		{
			if (sdate == null || sdate.equals("")||(sdate!=null && !sdate.equals("") && DateTools.isValid("dd/MM/yyyy", sdate)==false))
			{
				sdate = df.format(new Date());
			}
			if (edate == null || edate.equals("")||(edate!=null && !edate.equals("") && DateTools.isValid("dd/MM/yyyy", edate)==false))
			{
				edate = df.format(new Date());
			}
		}
		
		//lay danh sach cac block trong email: ten
		List<SysReportBlock> blockList = sysReportBlockDAO.getBlockListReport("FEEDBACK",function);
		//Moi block:
		//lay cac cot config cho block
		//lay du lieu cho block
		//Tao grid hien thi len tren web
		List<String> gridList = new ArrayList<String>();
		List<SysReportBlock> blockListTmp = new ArrayList<SysReportBlock>();
		for (SysReportBlock block : blockList) {
			List<String> paramList = new ArrayList<String>();
			paramList.add(sdate==null?"":sdate);
			paramList.add(edate==null?"":edate);
			paramList.add(sweek==null?"":sweek);
			paramList.add(eweek==null?"":eweek);
			paramList.add(smonth==null?"":smonth);
			paramList.add(emonth==null?"":emonth);
			paramList.add(syear==null?"":syear);
			paramList.add(eyear==null?"":eyear);
			paramList.add(region==null?"":region);
			paramList.add(province==null?"":province);
			paramList.add(fbType==null?"":fbType);
			paramList.add(status==null?"":status);
			paramList.add(sqlWhere==null?"":sqlWhere);
			paramList.add(function);
			paramList.add("2");		// report type: 1 - get data, 2 - get sql query
			String feedbackType = "FEEDBACK_"+function.toUpperCase();
			List<CTableConfig> columnList = cTableConfigDAO.getColumnReportBlock(feedbackType, block.getBlockId());
			String sqlQuery = ExportTools.getProcedureSqlQuery(block.getSqlWhere(),paramList,columnList);
			
			String dataArray = ExportTools.exportToStringArray(sqlQuery);
			
			String grid = HelpTableConfigs.getGridReportDataString(columnList, "grid"+block.getBlockId(), dataArray, block.getBlockId(), columnList, "menu"+block.getBlockId(), null,"Y",columnList.get(0).getGroupFuntion(),null);
			//String grid = HelpTableConfigs.getTreeGridReportDataString(columnList, "grid"+block.getBlockId(), dataArray, block.getBlockId(), columnList, "menu"+block.getBlockId(), null,"Y",columnList.get(0).getGroupFuntion());
			gridList.add(grid);
			
			block.setSqlQuery(sqlQuery);
			
			blockListTmp.add(block);
		}
		
		model.addAttribute("blockList", blockListTmp);
		model.addAttribute("gridList", 	gridList);
		model.addAttribute("sdate", 	sdate);
		model.addAttribute("edate", 	edate);
		model.addAttribute("sweek", 	sweek);
		model.addAttribute("eweek", 	eweek);
		model.addAttribute("smonth", 	smonth);
		model.addAttribute("emonth", 	emonth);
		model.addAttribute("syear", 	syear);
		model.addAttribute("eyear", 	eyear);
		model.addAttribute("region", 	region);
		model.addAttribute("province", 	province);
		model.addAttribute("fbType", 	fbType);
		model.addAttribute("status", 	status);
		model.addAttribute("sqlWhere", 	sqlWhere);
		model.addAttribute("function", 	function);
		model.addAttribute("funct", 	function);
		
		
		return new ModelAndView("jspfeedback/mlmn/reportOption");
	}
	
	@RequestMapping("sendMail")
	public @ResponseBody
	Map<String, Object> sendMail(String content,String subject,String email,String titlePage,String sdate,String edate,String sweek,String smonth,String syear,String region,String province,String fbType,String status,String sqlWhere,String func, HttpServletRequest request) {
		String kt="1";
		// noi dung mail gui va nguoi nhan
		content = URLCoderTools.decode(content);
		email = URLCoderTools.decode(email);
		titlePage = URLCoderTools.decode(titlePage);
		subject = URLCoderTools.decode(subject);
		email = email.replaceAll(";", ",");
		if (sdate==null)
 			sdate = "";
		else{
			sdate = URLCoderTools.decode(sdate);
		}
 		if (edate==null)
 			edate = "";
 		else{
 			edate = URLCoderTools.decode(edate);
		}
 		if (sweek==null)
 			sweek = "";
 		else{
 			sweek = URLCoderTools.decode(sweek);
		}
 		if (smonth==null)
 			smonth="";
 		else{
 			smonth = URLCoderTools.decode(smonth);
		}
 		if (syear==null)
 			syear = "";
 		else{
 			syear = URLCoderTools.decode(syear);
		}
 		if (region==null)
 			region = "";
 		else{
 			region = URLCoderTools.decode(region);
		}
 		if (province==null)
 			province = "";
 		else{
 			province = URLCoderTools.decode(province);
		}
 		if (fbType==null)
 			fbType="";
 		else{
 			fbType = URLCoderTools.decode(fbType);
		}
 		if (status==null)
 			status = "";
 		else{
 			status = URLCoderTools.decode(status);
		}
 		if (sqlWhere==null)
 			sqlWhere = "";
 		else{
 			sqlWhere = URLCoderTools.decode(sqlWhere);
		}
 		if (func==null)
 			func = "";
 		else{
 			func = URLCoderTools.decode(func);
		}
		// lay noi dung nguoi gui, password nguoi gui
		String userFrom = AL_Global.MAIL_SYSTEM_CONFIG.getProperty(SendMailSetting.SENDMAIL_USER_FROM);
		String password = AL_Global.MAIL_SYSTEM_CONFIG.getProperty(SendMailSetting.SENDMAIL_PASSWORD_FROM);
		if (userFrom==null){
			try {
				AlWorkCalendarShiftController.loadSystemConfig();
				// LAY THONG SO MAY CHU GUI MAIL
				userFrom = AL_Global.SHIFT_CONFIG.getProperty(SendMailSetting.SENDMAIL_USER_FROM);
				password = AL_Global.SHIFT_CONFIG.getProperty(SendMailSetting.SENDMAIL_PASSWORD_FROM);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List<CSystemConfigs> p = cSystemConfigsDAO.getSystemConfigMail();
		Properties props = new Properties();
		for (CSystemConfigs item: p) {
			props.put(item.getParamName(), item.getParamValue());
		}
		//lay noi dung gui tren mail
		String message="<h2 align=\"left\" style=\"font-weight: bold;font-size: 12;\">"+titlePage+"</h2>";
		message +=  "</br>"+"<span>"+content+"</span>";
		message += getDataDashboard(sdate,edate,sweek,smonth,syear,region,province,fbType,status,sqlWhere,func);

		//gui mails
		try
		{
			Mail mail = new Mail(props, null, userFrom, password,
					email,null,null, subject, message);
			
			String result = mail.send();
			
			if(result != null)
			{
				System.out.println("Gui mail that bai");
				kt="0";
			}
		}
		catch(Exception ex)
		{
			System.out.println("Gui mail that bai");
			kt="0";
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
	 	data.put("value", kt);
		return data;
		
	}
	
	private String getDataDashboard(String sdate,
			String edate,
			String sweek,
			String smonth,
			String syear,
			String region,
			String province,
			String fbType,
			String status,
			String sqlWhere,
			String function) {
		String content="";
		String con="<br>";
		
		List<String> paramList = new ArrayList<String>();
 		paramList.add(sdate==null?"":sdate);
		paramList.add(edate==null?"":edate);
		paramList.add(sweek==null?"":sweek);
		paramList.add(smonth==null?"":smonth);
		paramList.add(syear==null?"":syear);
		paramList.add(region==null?"":region);
		paramList.add(province==null?"":province);
		paramList.add(fbType==null?"":fbType);
		paramList.add(status==null?"":status);
		paramList.add(sqlWhere==null?"":sqlWhere);
		paramList.add(function);
		paramList.add("1");		// report type: 1 - get data, 2 - get sql query
		paramList.add(null);
		
		//lay danh sach cac block trong email: ten
		List<SysReportBlock> blockList = sysReportBlockDAO.getBlockListReport("FEEDBACK",function);
		//Moi block:
		//lay cac cot config cho block
		//lay du lieu cho block
		for (SysReportBlock block : blockList) {
			
			String feedbackType = "FEEDBACK_"+function.toUpperCase()+"_"+block.getBlockId().toUpperCase();
			List<CTableConfig> columnList = cTableConfigDAO.getColumnMailList(feedbackType, block.getBlockId());
			content+= con + ExportTools.exportToStringHTML(block.getSqlWhere(),paramList,columnList,block.getBlockName()==null?"":block.getBlockName(),block.getBlockId());
			
		}
		
		return content;
	}
	
}
