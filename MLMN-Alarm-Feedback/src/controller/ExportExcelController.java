package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dao.CTableConfigDAO;
import dao.DyAlDetailNonFinishDAOImpl;
import dao.RAlarmLogDAO;


import vo.CTableConfig;
import vo.DyAlDetailNonFinish;
import vo.RAlarmLog;
import vo.SYS_PARAMETER;
import vo.alarm.utils.DateTools;
import vo.alarm.utils.ExportTools;
import vo.alarm.utils.Helper;

@Controller
@RequestMapping("/exportExcel/*")
public class ExportExcelController extends BaseController {

	@Autowired
	private RAlarmLogDAO rAlarmLogDAO;
	
	@Autowired
	private CTableConfigDAO cTableConfigDAO;
	
	@Autowired
	private DyAlDetailNonFinishDAOImpl dyAlDetailNonFinishDAO;
	
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	@RequestMapping("alarmLog")
	public String alarmLog(@RequestParam(required = false) String sdateF,
			@RequestParam(required = false) String sdateT,
			@RequestParam(required = false) String edateF,
			@RequestParam(required = false) String edateT,
			@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String cellid,
			@RequestParam(required = false) String vendor,
			@RequestParam(required = false) String district,
			@RequestParam(required = false) String alarmName,
			@RequestParam(required = false) String province,
			@RequestParam(required = false) String team,
			@RequestParam(required = false) String alarmType,
			@RequestParam(required = false) String alarmMappingName,
			@RequestParam(required = false) String statusFinish,
			@RequestParam(required = false) String severity,
			@RequestParam(required = false) String netWork,
			@RequestParam(required = true) String function,
			@RequestParam(required = true) String statusView,
			@RequestParam(required = false) String duaration,
			@RequestParam(required = true) String typeExport,
			@RequestParam(required = false) String region,
			@RequestParam(required = false) String unAlarmMappingName,
			@RequestParam(required = false) String severityF,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//Kiem tra dieu kien tim kiem 
		Calendar cal = Calendar.getInstance();	
		cal.setTime(new Date());
		
		if (sdateF == null || sdateF.equals("")
				||(sdateF!=null && !sdateF.equals("") && DateTools
							.isValid("dd/MM/yyyy HH:mm", sdateF)==false))
		{
			sdateF = df.format(cal.getTime())+" "+"00:00";
		}
		
		if (sdateT == null || sdateT.equals("")
				||(sdateT!=null && !sdateT.equals("") && DateTools
							.isValid("dd/MM/yyyy HH:mm", sdateT)==false))
		{
			sdateT = df.format(cal.getTime())+" "+"23:59";
		}
		sdateF = sdateF+":00";
		sdateT = sdateT+":00";
		if (bscid!=null)
		{
			bscid=bscid.trim();
		}
		else
		{
			bscid="";
		}
		if (cellid!=null)
		{
			cellid=cellid.trim();
		}
		else
		{
			cellid="";
		}
		if (district!=null)
		{
			district=district.trim();
		}
		else
		{
			district="";
		}
		if (vendor!=null)
		{
			vendor=vendor.trim();
		}
		else
		{
			vendor="";
		}
		if (alarmName!=null)
		{
			alarmName=alarmName.trim();
		}
		else
		{
			alarmName="";
		}
		if (province!=null)
		{
			province=province.trim();
		}
		else
		{
			province="";
		}
		if (team!=null)
		{
			team=team.trim();
		}
		else
		{
			team="";
		}
		if (alarmType!=null)
		{
			alarmType=alarmType.trim();
		}
		else
		{
			alarmType="";
		}
		if (alarmMappingName!=null)
		{
			alarmMappingName=alarmMappingName.trim();
		}
		else
		{
			alarmMappingName="";
		}
		if (severity!=null)
		{
			severity=severity.trim();
			severityF = severity;
		}
		else
		{
			severity="";
		}
		/*SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date d1 = null;
		Date d2 = null;*/
		String nameTable="R_ALARM_LOG";
		if (!netWork.equals("2G")&&!netWork.equals("3G"))		
		{
			nameTable =nameTable+netWork;
		}
		try {
			
			if (typeExport==null||typeExport.equals(""))
			{
				typeExport="xls";
			}
			String ext = typeExport;
			String dataDir =  "/u02";
			String uploadPath=dataDir.concat("/export/"+typeExport);
	        // Tạo ra các thư mục nếu nó không tồn tại
	        File uploadDir = new File(uploadPath);
	        if (!uploadDir.exists()) {
	            uploadDir.mkdirs();
	        }
			
			
			Calendar currentDate = Calendar.getInstance();
			SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
			String dateNow = formatter.format(currentDate.getTime());
			//String fileName = "AlarmLog"+ function + dateNow;
			String fileName = "AlarmLog"+ function+netWork ;
		//	String outfile = tempName + "." + ext;
			 ServletOutputStream sos = response.getOutputStream();
			 response.setContentType("application/zip");
		     response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName+dateNow + ".zip\"");
		       
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        ZipOutputStream zos = new ZipOutputStream(baos);
	        byte bytes[] = new byte[1024];
	        int count = rAlarmLogDAO.countRAlarmLog(sdateF,sdateT,edateF,edateT,bscid,cellid, vendor,district, alarmName, function, severityF,netWork,username,province,team,alarmType,alarmMappingName,statusFinish,null,statusView,duaration,region,unAlarmMappingName);
				if (count>0)
				{
					String fileNameA = fileName+ dateNow;
					int lopCount = count/30000;
					System.out.println("So lan ghi query:"+lopCount);
					for (int i=0;i<=lopCount;i++)
					{
						int start = i*30000;
						int end =0;
						if ((i+1)*30000<count)
							end = (i+1)*30000;
						else
							end =i*30000+count%30000;
						
						String outfile = fileNameA+"("+(i+1)+")"+ "." + ext;
						File tempFile = new File(uploadPath + "/" + outfile);
						if (ext.equals("xls"))
						{
							List<RAlarmLog> alarmList = new ArrayList<RAlarmLog>();
							alarmList = rAlarmLogDAO.getRAlarmLog(sdateF,sdateT,edateF,edateT,bscid,cellid, vendor,district, alarmName, function, severityF, null, start,end ,null,null,netWork,username,province,team,alarmType,alarmMappingName,statusFinish,statusView,duaration,region,unAlarmMappingName);
							exportAlarmLog(tempFile,alarmList);
						}
						else
						{	
							List<RAlarmLog> alarmList = new ArrayList<RAlarmLog>();
							alarmList = rAlarmLogDAO.getRAlarmLog(sdateF,sdateT,edateF,edateT,bscid,cellid, vendor,district, alarmName, function, severityF, null, start,end ,null,null,netWork,username,province,team,alarmType,alarmMappingName,statusFinish,statusView,duaration,region,unAlarmMappingName);
							exportAlarmLogCSV(tempFile, alarmList);
							
						}
						
						FileInputStream fis = new FileInputStream(tempFile);
			            BufferedInputStream bis = new BufferedInputStream(fis);
			
			            zos.putNextEntry(new ZipEntry(tempFile.getName()));
			
			            int bytesRead;
			            while ((bytesRead = bis.read(bytes)) != -1) {
			                zos.write(bytes, 0, bytesRead);
			            }
			            zos.closeEntry();
			            bis.close();
			            fis.close();
			            tempFile.delete();
					}
			}
			zos.flush();
	        baos.flush();
	        zos.close();
	        baos.close();
			
	        byte[] zip = baos.toByteArray();
	        sos.write(zip);
	        sos.flush();
		}
		catch(Exception exp)
		{
			System.out.println(exp);
		}
		
		return null;
	}
	
	
	
	private void exportAlarmLog(File tempFile, List<RAlarmLog> alarmList) {
		try {
			WorkbookSettings wbSetting = new WorkbookSettings();  
           // wbSetting.setUseTemporaryFileDuringWrite(true);  
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile,wbSetting);
			WritableSheet sheet = workbook.createSheet("Alarm report", 0);
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			  
	           
			Label label0 = new Label(0, 0, "NE",cellFormatHeader);
			sheet.addCell(label0);
			Label label1 = new Label(1, 0, "SITE",cellFormatHeader);
			sheet.addCell(label1);
			Label label2 = new Label(2, 0, "CELLID",cellFormatHeader);
			sheet.addCell(label2);
			Label label3 = new Label(3, 0, "SDATE",cellFormatHeader);
			sheet.addCell(label3);
			Label label4 = new Label(4, 0, "EDATE",cellFormatHeader);
			sheet.addCell(label4);
			Label label38 = new Label(5, 0, "DUARATION",cellFormatHeader);
			sheet.addCell(label38);
			Label label5 = new Label(6, 0, "ALARM TYPE",cellFormatHeader);
			sheet.addCell(label5);
			Label label6 = new Label(7, 0, "SEVERITY",cellFormatHeader);
			sheet.addCell(label6);
			Label label7 = new Label(8, 0, "ALARM NAME",cellFormatHeader);
			sheet.addCell(label7);
			Label label8 = new Label(9, 0, "ALARM INFO",cellFormatHeader);
			sheet.addCell(label8);
			Label label9 = new Label(10, 0, "ALARM ID",cellFormatHeader);
			sheet.addCell(label9);
			Label label10 = new Label(11	, 0, "ALARM MAPPING ID",cellFormatHeader);
			sheet.addCell(label10);
			Label label11 = new Label(12, 0, "ALARM MAPPING NAME",cellFormatHeader);
			sheet.addCell(label11);
			Label label12 = new Label(13, 0, "STATUS VIEW",cellFormatHeader);
			sheet.addCell(label12);
			Label label13 = new Label(14, 0, "ACK STATUS",cellFormatHeader);
			sheet.addCell(label13);
			Label label14 = new Label(15, 0, "ACK TIME",cellFormatHeader);
			sheet.addCell(label14);
			Label label15 = new Label(16, 0, "ACK USER",cellFormatHeader);
			sheet.addCell(label15);
			Label label16 = new Label(17, 0, "SHIFTID",cellFormatHeader);
			sheet.addCell(label16);
			Label label17 = new Label(18, 0, "DISTRICT",cellFormatHeader);
			sheet.addCell(label17);
			Label label18 = new Label(19, 0, "DEPT",cellFormatHeader);
			sheet.addCell(label18);
			Label label19 = new Label(20, 0, "CAUSE BY",cellFormatHeader);
			sheet.addCell(label19);
			Label label20 = new Label(21, 0, "ACTION PROCESS",cellFormatHeader);
			sheet.addCell(label20);
			Label label21 = new Label(22, 0, "CAUSEBY SYS",cellFormatHeader);
			sheet.addCell(label21);
			Label label22 = new Label(23, 0, "DESCRIPTION",cellFormatHeader);
			sheet.addCell(label22);
			Label label23 = new Label(24, 0, "NETWORK",cellFormatHeader);
			sheet.addCell(label23);
			Label label24 = new Label(25, 0, "VENDOR",cellFormatHeader);
			sheet.addCell(label24);
			Label label40 = new Label(26, 0, "BSCPORT",cellFormatHeader);
			sheet.addCell(label40);
			Label label41 = new Label(27, 0, "BTSPORT",cellFormatHeader);
			sheet.addCell(label41);
			Label label25 = new Label(28, 0, "TEAM",cellFormatHeader);
			sheet.addCell(label25);
			Label label26 = new Label(29, 0, "SUB TEAM",cellFormatHeader);
			sheet.addCell(label26);
			Label label27 = new Label(30, 0, "NOTE",cellFormatHeader);
			sheet.addCell(label27);
			Label label28 = new Label(31, 0, "CREATED BY",cellFormatHeader);
			sheet.addCell(label28);
			Label label29 = new Label(32, 0, "CREATE DATE",cellFormatHeader);
			sheet.addCell(label29);
			Label label30 = new Label(33, 0, "MODIFIED BY",cellFormatHeader);
			sheet.addCell(label30);
			Label label31 = new Label(34, 0, "MODIFY DATE",cellFormatHeader);
			sheet.addCell(label31);
			Label label32 = new Label(35, 0, "NE TYPE",cellFormatHeader);
			sheet.addCell(label32);
			Label label33 = new Label(36, 0, "REP COUNT",cellFormatHeader);
			sheet.addCell(label33);
			Label label34 = new Label(37, 0, "CH TYPE",cellFormatHeader);
			sheet.addCell(label34);
			Label label35 = new Label(38, 0, "OBJECT REFERENCE",cellFormatHeader);
			sheet.addCell(label35);
			Label label36 = new Label(39, 0, "SLOT",cellFormatHeader);
			sheet.addCell(label36);
			Label label37 = new Label(40, 0, "FM ALARMID",cellFormatHeader);
			sheet.addCell(label37);
			
			Label label42 = new Label(41, 0, "OBJ TYPE",cellFormatHeader);
			sheet.addCell(label42);
			Label label43 = new Label(42, 0, "CLR STATUS",cellFormatHeader);
			sheet.addCell(label43);
			Label label44 = new Label(43, 0, "PROVINCE",cellFormatHeader);
			sheet.addCell(label44);
			Label label45 = new Label(44, 0, "EVTTYPE",cellFormatHeader);
			sheet.addCell(label45);
			

			int i = 1;
			for (RAlarmLog alarmlog : alarmList) {
				Label NE = new Label(0, i,alarmlog.getNe(), cellFormat);
				sheet.addCell(NE);
				Label SITE = new Label(1, i, alarmlog.getSite(), cellFormat);
				sheet.addCell(SITE);
				Label CELLID = new Label(2, i, alarmlog.getCellid(), cellFormat);
				sheet.addCell(CELLID);
				Label SDATE = new Label(3, i, alarmlog.getSdateStr(), cellFormat);
				sheet.addCell(SDATE);
				Label EDATE = new Label(4, i, alarmlog.getEdateStr(), cellFormat);
				sheet.addCell(EDATE);
				/*jxl.write.Number DUARATION = new jxl.write.Number(5, i, Helper.int2Double(alarmlog.getDuaration()));
				sheet.addCell(DUARATION);*/
				/*Label DUARATION = new Label(5, i, alarmlog.getDuaration().toString(), cellFormat);
				sheet.addCell(DUARATION);*/
				jxl.write.Number DUARATION = new jxl.write.Number(5, i, alarmlog.getDuaration()==null?0:alarmlog.getDuaration(), cellFormat);
				sheet.addCell(DUARATION);
				Label ALARM_TYPE = new Label(6, i, alarmlog.getAlarmType(), cellFormat);
				sheet.addCell(ALARM_TYPE);
				Label SEVERITY = new Label(7, i, alarmlog.getSeverity(), cellFormat);
				sheet.addCell(SEVERITY);
				Label ALARMNAME = new Label(8, i, alarmlog.getAlarmName(), cellFormat);
				sheet.addCell(ALARMNAME);
				Label ALARMINFO = new Label(9, i, alarmlog.getAlarmInfo(), cellFormat);
				sheet.addCell(ALARMINFO);
				Label ALARMID = new Label(10, i, alarmlog.getAlarmId(), cellFormat);
				sheet.addCell(ALARMID);
				Label ALARMMAPPINGID = new Label(11, i, alarmlog.getAlarmMappingId(), cellFormat);
				sheet.addCell(ALARMMAPPINGID);
				Label ALARMMAPPINGNAME = new Label(12, i, alarmlog.getAlarmMappingName(), cellFormat);
				sheet.addCell(ALARMMAPPINGNAME);
				Label STATUSVIEW = new Label(13, i, alarmlog.getStatusView(), cellFormat);
				sheet.addCell(STATUSVIEW);
				Label ACKSTATUS = new Label(14, i, alarmlog.getAckStatus(), cellFormat);
				sheet.addCell(ACKSTATUS);
				Label ACKTIME = new Label(15, i, alarmlog.getAckTimeStr(), cellFormat);
				sheet.addCell(ACKTIME);
				Label ACKUSER = new Label(16, i, alarmlog.getAckUser(), cellFormat);
				sheet.addCell(ACKUSER);
				jxl.write.Number SHIFTID = new jxl.write.Number(17, i, alarmlog.getShiftId()==null?0:alarmlog.getShiftId(), cellFormat);
				sheet.addCell(SHIFTID);
				Label DISTRICT = new Label(18, i, alarmlog.getDistrict(), cellFormat);
				sheet.addCell(DISTRICT);
				Label DEPT = new Label(19, i, alarmlog.getDept(), cellFormat);
				sheet.addCell(DEPT);
				Label CAUSEBY = new Label(20, i, alarmlog.getCauseby(), cellFormat);
				sheet.addCell(CAUSEBY);
				Label ACTIONPROCESS = new Label(21, i, alarmlog.getActionProcess(), cellFormat);
				sheet.addCell(ACTIONPROCESS);
				Label CAUSEBYSYS = new Label(22, i, alarmlog.getCausebySys(), cellFormat);
				sheet.addCell(CAUSEBYSYS);
				Label DESCRIPTION = new Label(23, i, alarmlog.getDescription(), cellFormat);
				sheet.addCell(DESCRIPTION);
				Label NETWORK = new Label(24, i, alarmlog.getNetwork(), cellFormat);
				sheet.addCell(NETWORK);
				Label VENDOR = new Label(25, i, alarmlog.getVendor(), cellFormat);
				sheet.addCell(VENDOR);
				Label BSCPORT = new Label(26, i, alarmlog.getBscport(), cellFormat);
				sheet.addCell(BSCPORT);
				Label BTSPORT = new Label(27, i, alarmlog.getBtsport(), cellFormat);
				sheet.addCell(BTSPORT);
				Label TEAM = new Label(28, i, alarmlog.getTeam(), cellFormat);
				sheet.addCell(TEAM);
				Label GROUPS = new Label(29, i, alarmlog.getGroups(), cellFormat);
				sheet.addCell(GROUPS);
				Label NOTE = new Label(30, i, alarmlog.getNote(), cellFormat);
				sheet.addCell(NOTE);
				Label CREATEDBY = new Label(31, i, alarmlog.getCreatedBy(), cellFormat);
				sheet.addCell(CREATEDBY);
				Label CREATEDATE = new Label(32, i, alarmlog.getCreateDateStr(), cellFormat);
				sheet.addCell(CREATEDATE);
				Label MODIFIEDBY = new Label(33, i, alarmlog.getModifiedBy(), cellFormat);
				sheet.addCell(MODIFIEDBY);
				Label MODIFYDATE = new Label(34, i, alarmlog.getModifyDateStr(), cellFormat);
				sheet.addCell(MODIFYDATE);
				Label NETYPE = new Label(35, i, alarmlog.getNeType(), cellFormat);
				sheet.addCell(NETYPE);
				jxl.write.Number REPCOUNT = new jxl.write.Number(36, i, alarmlog.getRepCount()==null?0:alarmlog.getRepCount(), cellFormat);
				sheet.addCell(REPCOUNT);
				Label CHTYPE = new Label(37, i, alarmlog.getChType(), cellFormat);
				sheet.addCell(CHTYPE);
				Label OBJECTREFERENCE = new Label(38, i, alarmlog.getObjectReference(), cellFormat);
				sheet.addCell(OBJECTREFERENCE);
				jxl.write.Number SLOT = new jxl.write.Number(39, i, alarmlog.getSlot()==null?0:alarmlog.getSlot(), cellFormat);
				sheet.addCell(SLOT);
				Label FMALARMID = new Label(40, i, alarmlog.getFmAlarmid(), cellFormat);
				sheet.addCell(FMALARMID);
				
				Label OBJ_TYPE = new Label(41, i, alarmlog.getObjType(), cellFormat);
				sheet.addCell(OBJ_TYPE);
				Label CLR_STATUS = new Label(42, i, alarmlog.getClrStatus(), cellFormat);
				sheet.addCell(CLR_STATUS);
				Label PROVINCE = new Label(43, i, alarmlog.getProvince(), cellFormat);
				sheet.addCell(PROVINCE);
				Label EVTTYPE = new Label(44, i, alarmlog.getEvttype(), cellFormat);
				sheet.addCell(EVTTYPE);
				
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
	
	private void exportAlarmLogCSV(File tempFile, List<RAlarmLog> alarmList) {
		String CSV_SEPARATOR = ";";
		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
		                    new FileOutputStream(tempFile.getPath()), "UTF-8"));
			 String lineHeader="";
			 lineHeader = lineHeader +"NE"+CSV_SEPARATOR
						+"SITE"+CSV_SEPARATOR
						+"CELLID"+CSV_SEPARATOR
						+ "SDATE"+CSV_SEPARATOR
						+"EDATE"+CSV_SEPARATOR
						+ "DUARATION"+CSV_SEPARATOR
						+ "ALARM TYPE"+CSV_SEPARATOR
						+ "SEVERITY"+CSV_SEPARATOR
						+"ALARM NAME"+CSV_SEPARATOR
						+"ALARM INFO"+CSV_SEPARATOR
						+"ALARM ID"+CSV_SEPARATOR
						+"ALARM MAPPING ID"+CSV_SEPARATOR
						+"ALARM MAPPING NAME"+CSV_SEPARATOR
						+"STATUS VIEW"+CSV_SEPARATOR
						+"ACK STATUS"+CSV_SEPARATOR
						+"ACK TIME"+CSV_SEPARATOR
						+"ACK USER"+CSV_SEPARATOR
						+ "SHIFTID"+CSV_SEPARATOR
						+ "DISTRICT"+CSV_SEPARATOR
						+"DEPT"+CSV_SEPARATOR
						+"CAUSE BY"+CSV_SEPARATOR
						+"ACTION PROCESS"+CSV_SEPARATOR
						+ "CAUSEBY SYS"+CSV_SEPARATOR
						+"DESCRIPTION"+CSV_SEPARATOR
						+"NETWORK"+CSV_SEPARATOR
						+ "VENDOR"+CSV_SEPARATOR
						+"BSCPORT"+CSV_SEPARATOR
						+"BTSPORT"+CSV_SEPARATOR
						+"TEAM"+CSV_SEPARATOR
						+ "SUB TEAM"+CSV_SEPARATOR
						+ "NOTE"+CSV_SEPARATOR
						+"CREATED BY"+CSV_SEPARATOR
						+"CREATE DATE"+CSV_SEPARATOR
						+ "MODIFIED BY"+CSV_SEPARATOR
						+ "MODIFY DATE"+CSV_SEPARATOR
						+"NE TYPE"+CSV_SEPARATOR
						+ "REP COUNT"+CSV_SEPARATOR
						+"CH TYPE"+CSV_SEPARATOR
						+ "OBJECT REFERENCE"+CSV_SEPARATOR
						+ "SLOT"+CSV_SEPARATOR
						+"FM ALARMID"+CSV_SEPARATOR
						+"OBJ TYPE"+CSV_SEPARATOR
						+ "CLR STATUS"+CSV_SEPARATOR
						+"PROVINCE"+CSV_SEPARATOR
						+"EVTTYPE"+CSV_SEPARATOR;
			
			 lineHeader+=System.getProperty("line.separator");
			 writer.write(lineHeader);
			for (RAlarmLog alarmlog : alarmList) {
				String line="";
				line = line+ alarmlog.getNe()+CSV_SEPARATOR
				+alarmlog.getSite()+CSV_SEPARATOR
				+alarmlog.getCellid()+CSV_SEPARATOR
				+alarmlog.getSdateStr()+CSV_SEPARATOR
				+alarmlog.getEdateStr()+CSV_SEPARATOR
				+(alarmlog.getDuaration()==null?0:alarmlog.getDuaration())+CSV_SEPARATOR
				+alarmlog.getAlarmType()+CSV_SEPARATOR
				+alarmlog.getSeverity()+CSV_SEPARATOR
				+alarmlog.getAlarmName()+CSV_SEPARATOR
				+alarmlog.getAlarmInfo()+CSV_SEPARATOR
				+(alarmlog.getAlarmId()==null?0:alarmlog.getAlarmId())+CSV_SEPARATOR
				+alarmlog.getAlarmMappingId()+CSV_SEPARATOR
				+alarmlog.getAlarmMappingName()+CSV_SEPARATOR
				+alarmlog.getStatusView()+CSV_SEPARATOR
				+alarmlog.getAckStatus()+CSV_SEPARATOR
				+alarmlog.getAckTimeStr()+CSV_SEPARATOR
				+alarmlog.getAckUser()+CSV_SEPARATOR
				+(alarmlog.getShiftId()==null?0:alarmlog.getShiftId())+CSV_SEPARATOR
				+alarmlog.getDistrict()+CSV_SEPARATOR
				+ alarmlog.getDept()+CSV_SEPARATOR
				+alarmlog.getCauseby()+CSV_SEPARATOR
				+alarmlog.getActionProcess()+CSV_SEPARATOR
				+alarmlog.getCausebySys()+CSV_SEPARATOR
				+alarmlog.getDescription()+CSV_SEPARATOR
				+alarmlog.getNetwork()+CSV_SEPARATOR
				+alarmlog.getVendor()+CSV_SEPARATOR
				+alarmlog.getBscport()+CSV_SEPARATOR
				+alarmlog.getBtsport()+CSV_SEPARATOR
				+alarmlog.getTeam()+CSV_SEPARATOR
				+alarmlog.getGroups()+CSV_SEPARATOR
				+alarmlog.getNote()+CSV_SEPARATOR
				+alarmlog.getCreatedBy()+CSV_SEPARATOR
				+ alarmlog.getCreateDateStr()+CSV_SEPARATOR
				+alarmlog.getModifiedBy()+CSV_SEPARATOR
				+alarmlog.getModifyDateStr()+CSV_SEPARATOR
				+alarmlog.getNeType()+CSV_SEPARATOR
				+( alarmlog.getRepCount()==null?0:alarmlog.getRepCount())+CSV_SEPARATOR
				+alarmlog.getChType()+CSV_SEPARATOR
				+ alarmlog.getObjectReference()+CSV_SEPARATOR
				+(alarmlog.getSlot()==null?0:alarmlog.getSlot())+CSV_SEPARATOR
				+ (alarmlog.getFmAlarmid()==null?0:alarmlog.getFmAlarmid())+CSV_SEPARATOR
				+ alarmlog.getObjType()+CSV_SEPARATOR
				+alarmlog.getClrStatus()+CSV_SEPARATOR
				+alarmlog.getProvince()+CSV_SEPARATOR
				+alarmlog.getEvttype()+CSV_SEPARATOR;
				line+=System.getProperty("line.separator");
				line=line.replaceAll("null;", ";");
				writer.write(line);
			}
		}
			
		catch (IOException e) {
        	System.out.println("export file :"+e);
        } finally {
        	if (writer != null) {
                try {
                    writer.close();
	                } catch (Exception e) {
	                	System.out.println("OUT file :"+e);
	                }
            }
        }
	}
	

	//export report alarm
	@RequestMapping("reportAlarmLog")
	public ModelAndView reportAlarmLog(@RequestParam(required = false) String sdate,
			@RequestParam(required = false) String edate,
			@RequestParam(required = false) String bscid,
			@RequestParam(required = false) String cellid,
			@RequestParam(required = false) String vendor,
			@RequestParam(required = false) String district,
			@RequestParam(required = false) String alarmName,
			@RequestParam(required = false) String province,
			@RequestParam(required = false) String team,
			@RequestParam(required = false) String alarmType,
			@RequestParam(required = false) String alarmMappingName,
			@RequestParam(required = false) String reload,
			@RequestParam(required = false) String reloadStr,
			@RequestParam(required = false) String statusFinish,
			@RequestParam(required = false) String severity,
			@RequestParam(required = true) String netWork,
			@RequestParam(required = false) String duarationTo,
			@RequestParam(required = false) String bscPort,
			@RequestParam(required = false) String btsPort,
			@RequestParam(required = false) String objType,
			@RequestParam(required = false) String region,
			@RequestParam(required = true) String function,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		//Kiem tra dieu kien tim kiem 
		//Kiem tra dieu kien tim kiem 
		if (!function.equalsIgnoreCase("nonfinish"))
		{
			Calendar cal = Calendar.getInstance();	
			cal.setTime(new Date());
			cal.add(Calendar.DATE,-1);
			if (sdate == null || sdate.equals("")
					||(sdate!=null && !sdate.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm", sdate)==false))
			{
				sdate = df.format(cal.getTime())+" "+"00:00";
			}
			
			if (edate == null || edate.equals("")
					||(edate!=null && !edate.equals("") && DateTools.isValid("dd/MM/yyyy HH:mm", edate)==false))
			{
				edate = df.format(cal.getTime())+" "+"23:59";
			}
			sdate = sdate+":00";
			edate = edate+":00";
		}
		else
		{
			Calendar cal = Calendar.getInstance();	
			cal.setTime(new Date());
			if (sdate == null || sdate.equals("")
					||(sdate!=null && !sdate.equals("") && DateTools.isValid("dd/MM/yyyy", sdate)==false))
			{
				sdate = df.format(cal.getTime());
			}
			
			if (edate == null || edate.equals("")
					||(edate!=null && !edate.equals("") && DateTools.isValid("dd/MM/yyyy", edate)==false))
			{
				cal.add(Calendar.DATE,1);
				edate = df.format(cal.getTime());
			}
		}
		//reload
		if (reloadStr==null)
		{
			reload="Y";
			reloadStr="Y";
		}
		if (bscid!=null)
		{
			bscid=bscid.replaceAll(" ", "");
		}
		else
		{
			bscid="";
		}
		if (cellid!=null)
		{
			cellid=cellid.replaceAll(" ", "");
		}
		else
		{
			cellid="";
		}
		if (district!=null)
		{
			district=district.trim();
		}
		else
		{
			district="";
		}
		if (vendor!=null)
		{
			vendor=vendor.trim();
		}
		else
		{
			vendor="";
		}
		if (alarmName!=null)
		{
			alarmName=alarmName.trim();
		}
		else
		{
			alarmName="";
		}
		if (province==null)
		{
			province="";
			
		}
		else
		{
			province=province.trim();
		}
		if (team!=null)
		{
			team=team.trim();
		}
		else
		{
			team="";
		}
		if (alarmType!=null)
		{
			alarmType=alarmType.trim();
		}
		else
		{
			alarmType="";
		}
		if (alarmMappingName!=null)
		{
			alarmMappingName=alarmMappingName.trim();
		}
		else
		{
			alarmMappingName="";
		}
		if (severity!=null)
		{
			severity=severity.trim();
		}
		else
		{
			severity="";
		}
		if (statusFinish!=null)
		{
			statusFinish=statusFinish.trim();
		}
		else
		{
			statusFinish="";
		}
		if (duarationTo==null||duarationTo.equals(""))
		{
			duarationTo="30";
		}
		
		if (bscPort!=null)
		{
			bscPort=bscPort.trim();
		}
		else
		{
			bscPort="";
		}
		
		if (btsPort!=null)
		{
			btsPort=btsPort.trim();
		}
		else
		{
			btsPort="";
		}
		
		if (objType!=null)
		{
			objType=objType.trim();
		}
		else
		{
			objType="";
		}
		if (region!=null)
		{
			region=region.trim();
		}
		else
		{
			region="";
		}
		
		// temp file
		String basePath = request.getSession().getServletContext().getRealPath("");
		String dataDir = basePath + "/upload";

		String tempName = UUID.randomUUID().toString();

		String ext = "xls";

		String outfile = tempName + "." + ext;

		File tempFile = new File(dataDir + "/" + outfile);
		List<DyAlDetailNonFinish> alarmList = new ArrayList<DyAlDetailNonFinish>();
		alarmList = dyAlDetailNonFinishDAO.getAlarmLog(sdate,edate,bscid,cellid, vendor,district, alarmName, function, severity, null, null,null,null,null,netWork,username,province,team,alarmType,alarmMappingName,statusFinish,Integer.parseInt(duarationTo), bscPort, btsPort, objType,null,region);
		
		exportReportAlarmLog(tempFile, alarmList,function);
		// return
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("ddMMyyyy");
		String dateNow = formatter.format(currentDate.getTime());
		String filename = "Report_"+ function + dateNow;
		response.setContentType("application/ms-excel");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "." + ext + "\"");

		FileCopyUtils.copy(new FileInputStream(tempFile), response.getOutputStream());
		tempFile.delete();

		return null;
	}
	private void exportReportAlarmLog(File tempFile, List<DyAlDetailNonFinish> alarmList,String function) {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(tempFile);
			WritableSheet sheet = workbook.createSheet("Alarm report", 0);
			WritableCellFormat cellFormatHeader = new WritableCellFormat();
			cellFormatHeader = ExportTools.getCellFormatHeader(cellFormatHeader);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat = ExportTools.getCellFormat(cellFormat);
			int i=0;
			if (function.equals("nonfinish"))
			{
				Label label14 = new Label(i, 0, "DAY",cellFormatHeader);
				sheet.addCell(label14);
				i++;
			}
			Label label6 = new Label(i, 0, "SEVERITY",cellFormatHeader);
			sheet.addCell(label6);
			i++;
			Label label0 = new Label(i, 0, "NE",cellFormatHeader);
			sheet.addCell(label0);
			i++;
			Label label1 = new Label(i, 0, "SITE",cellFormatHeader);
			sheet.addCell(label1);
			i++;
			Label label2 = new Label(i, 0, "CELLID",cellFormatHeader);
			sheet.addCell(label2);
			i++;
			Label label3 = new Label(i, 0, "SDATE",cellFormatHeader);
			sheet.addCell(label3);
			i++;
			
			Label label4 = new Label(i, 0, "EDATE",cellFormatHeader);
			sheet.addCell(label4);
			i++;
			
			Label label38 = new Label(i, 0, "DUARATION",cellFormatHeader);
			sheet.addCell(label38);
			i++;
			Label label5 = new Label(i, 0, "NETWORK",cellFormatHeader);
			sheet.addCell(label5);
			i++;
			Label label21 = new Label(i, 0, "VENDOR",cellFormatHeader);
			sheet.addCell(label21);
			i++;
			Label label22 = new Label(i, 0, "ALARM TYPE",cellFormatHeader);
			sheet.addCell(label22);
			i++;
			Label label7 = new Label(i, 0, "ALARM MAPPING ID",cellFormatHeader);
			sheet.addCell(label7);
			i++;
			Label label8 = new Label(i, 0, "ALARM MAPPING NAME",cellFormatHeader);
			sheet.addCell(label8);
			i++;
			Label label9 = new Label(i, 0, "PROVINCE",cellFormatHeader);
			sheet.addCell(label9);
			i++;
			Label label10 = new Label(i	, 0, "DISTRICT",cellFormatHeader);
			sheet.addCell(label10);
			i++;
			Label label11 = new Label(i, 0, "TEAM",cellFormatHeader);
			sheet.addCell(label11);
			i++;
			Label label12 = new Label(i, 0, "DEPT",cellFormatHeader);
			sheet.addCell(label12);
			i++;
			Label label13 = new Label(i, 0, "SUB TEAM",cellFormatHeader);
			sheet.addCell(label13);
			i++;
			if (!function.equals("nonfinish"))
			{
				Label label16 = new Label(i, 0, "BSCPORT",cellFormatHeader);
				sheet.addCell(label16);
				i++;
				Label label17 = new Label(i, 0, "BTSPORT",cellFormatHeader);
				sheet.addCell(label17);
				i++;
				Label label18 = new Label(i, 0, "CAUSEBY SYS",cellFormatHeader);
				sheet.addCell(label18);
				i++;
				Label label19 = new Label(i, 0, "CAUSE BY",cellFormatHeader);
				sheet.addCell(label19);
				i++;
				Label label20 = new Label(i, 0, "RESULT CONTENT",cellFormatHeader);
				sheet.addCell(label20);
				i++;
			}
			Label label28 = new Label(i, 0, "CREATED BY",cellFormatHeader);
			sheet.addCell(label28);
			i++;
			Label label29 = new Label(i, 0, "CREATE DATE",cellFormatHeader);
			sheet.addCell(label29);
			i++;
			Label label30 = new Label(i, 0, "MODIFIED BY",cellFormatHeader);
			sheet.addCell(label30);
			i++;
			Label label31 = new Label(i, 0, "MODIFY DATE",cellFormatHeader);
			sheet.addCell(label31);
			
			i = 1;
			
			for (DyAlDetailNonFinish alarmlog : alarmList) {
				int j=0;
				if (function.equals("nonfinish"))
				{
					Label label14 = new Label(j, i,alarmlog.getDayStr(), cellFormat);
					sheet.addCell(label14);
					j++;
				}
				Label SEVERITY = new Label(j, i, alarmlog.getSeverity(), cellFormat);
				sheet.addCell(SEVERITY);
				j++;
				Label NE = new Label(j, i,alarmlog.getNe(), cellFormat);
				sheet.addCell(NE);
				j++;
				Label SITE = new Label(j, i, alarmlog.getSite(), cellFormat);
				sheet.addCell(SITE);
				j++;
				Label CELLID = new Label(j, i, alarmlog.getCellid(), cellFormat);
				sheet.addCell(CELLID);
				j++;
				Label SDATE = new Label(j, i, alarmlog.getSdateStr(), cellFormat);
				sheet.addCell(SDATE);
				j++;
				
				Label EDATE = new Label(j, i, alarmlog.getEdateStr(), cellFormat);
				sheet.addCell(EDATE);
				j++;
				jxl.write.Number DUARATION;
				if (alarmlog.getDuaration()!=null)
				{
					DUARATION = new jxl.write.Number(j, i, alarmlog.getDuaration(), cellFormat);
				}
				else
				{
					DUARATION = new jxl.write.Number(j, i, 0);
				}
				sheet.addCell(DUARATION);
				j++;
				Label NETWORK = new Label(j, i, alarmlog.getNetwork(), cellFormat);
				sheet.addCell(NETWORK);
				j++;
				Label VENDOR = new Label(j, i, alarmlog.getVendor(), cellFormat);
				sheet.addCell(VENDOR);
				j++;
				Label ALARM_TYPE = new Label(j, i, alarmlog.getAlarmType(), cellFormat);
				sheet.addCell(ALARM_TYPE);
				j++;
				Label ALARMMAPPINGID = new Label(j, i, alarmlog.getAlarmMappingId(), cellFormat);
				sheet.addCell(ALARMMAPPINGID);
				j++;
				Label ALARMMAPPINGNAME = new Label(j, i, alarmlog.getAlarmMappingName(), cellFormat);
				sheet.addCell(ALARMMAPPINGNAME);
				j++;
				Label PROVINCE = new Label(j, i, alarmlog.getProvince(), cellFormat);
				sheet.addCell(PROVINCE);
				j++;
				Label DISTRICT = new Label(j, i, alarmlog.getDistrict(), cellFormat);
				sheet.addCell(DISTRICT);
				j++;
				Label TEAM = new Label(j, i, alarmlog.getTeam(), cellFormat);
				sheet.addCell(TEAM);
				j++;
				Label DEPT = new Label(j, i, alarmlog.getDept(), cellFormat);
				sheet.addCell(DEPT);
				j++;
				Label GROUPS = new Label(j, i, alarmlog.getGroups(), cellFormat);
				sheet.addCell(GROUPS);
				j++;
				if (!function.equals("nonfinish"))
				{
					Label BSCPORT = new Label(j, i, alarmlog.getBscport(), cellFormat);
					sheet.addCell(BSCPORT);
					j++;
					Label BTSPORT = new Label(j, i, alarmlog.getBtsport(), cellFormat);
					sheet.addCell(BTSPORT);
					j++;
					Label CAUSEBYSys = new Label(j, i, alarmlog.getCausebySys(), cellFormat);
					sheet.addCell(CAUSEBYSys);
					j++;
					Label CAUSEBY = new Label(j, i, alarmlog.getCauseby(), cellFormat);
					sheet.addCell(CAUSEBY);
					j++;
					Label ResultContent = new Label(j, i, alarmlog.getResultContent(), cellFormat);
					sheet.addCell(ResultContent);
					j++;
					
				}
				
				Label CREATEDBY = new Label(j, i, alarmlog.getCreatedBy(), cellFormat);
				sheet.addCell(CREATEDBY);
				j++;
				Label CREATEDATE = new Label(j, i, alarmlog.getCreateDateStr(), cellFormat);
				sheet.addCell(CREATEDATE);
				j++;
				Label MODIFIEDBY = new Label(j, i, alarmlog.getModifiedBy(), cellFormat);
				sheet.addCell(MODIFIEDBY);
				j++;
				Label MODIFYDATE = new Label(j, i, alarmlog.getModifyDateStr(), cellFormat);
				sheet.addCell(MODIFYDATE);
				
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