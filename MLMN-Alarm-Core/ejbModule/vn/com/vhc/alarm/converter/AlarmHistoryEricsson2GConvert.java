package vn.com.vhc.alarm.converter;
/*anhnt@vhc.com.vn*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import vn.com.vhc.alarm.cni.AL_BasicConverter;
import vn.com.vhc.alarm.model.StructAlarmEricsson2G;
import vn.com.vhc.alarm.util.exceptions.AL_ConvertException;
import vn.com.vhc.alarm.utils.DataTools;

// Su dung convert file du lieu Ericsson history 2G
public class AlarmHistoryEricsson2GConvert extends AL_BasicConverter {

	private static final Logger logger = Logger
			.getLogger(AlarmHistoryEricsson2GConvert.class.getName()); 
	
	String line="";
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws AL_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		BufferedReader reader = null;
		BufferedWriter writerAlarmLogs = null; 
		StructAlarmEricsson2G alarm2gModel = null;
		StringBuffer problemText = new StringBuffer();
		Pattern patt = Pattern.compile("([^\\s]+)(\\s+)(.*)");
		Matcher m;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			writerAlarmLogs = new BufferedWriter(new FileWriter(outFile));
			writerAlarmLogs.write("#Type;Vendor;Network;NeType;BscId;BtsId;Cell;Mo;Dip;StartDate;EndDate;EventType;ObjectOfReference;SpecificProblem;ProblemText;Severity;FmAlarmId");
			String line = ""; 
			boolean blockFrom=false;
			String cell = "";
			String mo = "";
			String dip = ""; 
			
			while ((line = reader.readLine()) != null){
				if(line.equals("")) continue;
				
				if(line.contains("ALARM CLEARING")) { 
					alarm2gModel = new StructAlarmEricsson2G();
					blockFrom = false; 
					alarm2gModel.vendor = "ERICSSON";
					alarm2gModel.network = "2G";
					alarm2gModel.type = "ALARM CLEARING";
				}
				
				if(line.contains("ALARM FROM") || line.contains("EVENT FROM")) { 
					alarm2gModel = new StructAlarmEricsson2G();
					blockFrom = true;
					alarm2gModel.vendor = "ERICSSON";
					alarm2gModel.network = "2G";
					alarm2gModel.type = "ALARM FROM";
				}
				
				if(line.contains("ALARM END")) blockFrom = false;
				
				if(line.contains("Log Record ID:")) alarm2gModel.logRecordId = DataTools.getData(line,":");
				else if(line.contains("Event Time:")){
					while(line.indexOf("  ")>= 0) line = line.replaceAll("  ", " ");
					if(blockFrom) alarm2gModel.startDate = line.substring(line.indexOf(":")+1).trim();
					else alarm2gModel.endDate = line.substring(line.indexOf(":")+1).trim();
				}else if(line.contains("Event Type:")) alarm2gModel.eventType = DataTools.getData(line,":");
				else if(line.contains("Object of Reference:")){
					String str = DataTools.getData(line,":");
					String[] strList = str.split(",");
					
					for(String i:strList){
						if(i.contains("ManagedElement")) alarm2gModel.bscId = DataTools.getData(i,"="); 
						if(i.contains("BtsSiteMgr")) alarm2gModel.btsId = DataTools.getData(i,"=");
					}
					 
					alarm2gModel.objectOfReference = str;
				}else if (line.contains("Perceived Severity:")){
					String str1 = DataTools.getData(line,":");  
					alarm2gModel.severity = DataTools.formatSeverity(str1);
				}else if (line.contains("Specific Problem:")) 
					alarm2gModel.specificProblem = DataTools.getData(line,":");
				else if (line.contains("Problem Text")) {
					problemText.setLength(0);
					
					// neu la canh bao cell thi bo di 3 dong con canh bao radio thi 
					line = reader.readLine();
					
					// TRUNGNQ 27/9/2016 bo qua phan alarm name dong thoi lay them cell, mo, dip
					line = reader.readLine();
					while (alarm2gModel.specificProblem.contains(line)) {
						line = reader.readLine();
					} 
					// doc problem
					while (!line.equals("END")) {
						
						// neu END BREAK
						if (line.equals("")) {
							line = reader.readLine();
							if(line.equals("END")){
								break;
							}
						}
						// neu la canh bao clear va khong co END
						if (line != null && line.contains("Correlated ID:") && !blockFrom) {
							break;
						}
						
						//set cell
						if (alarm2gModel.specificProblem.startsWith("CELL") && line.startsWith("CELL")) {
							line = reader.readLine();
							m = patt.matcher(line);
							if (m.find()) {
								cell = m.group(1);
							}
							alarm2gModel.cell = cell;
									
							continue;
						} else if (alarm2gModel.specificProblem.startsWith("RADIO TRANSMISSION") && line.contains("CELL")) {
							line = reader.readLine();
							String[] tmp2 = line.trim().split("\\s+");
							if (tmp2.length > 2) {
								cell = tmp2[2];
							}
							alarm2gModel.cell = cell;
							
							continue;
						} else if (alarm2gModel.specificProblem.startsWith("RADIO") && line.startsWith("MO")) {
							line = reader.readLine();
							m = patt.matcher(line);
							if (m.find()) {
								mo = m.group(1);
								try {
									mo = mo.split("-")[1];
								} catch (Exception e) {
									// TODO: handle exception
								}
							}
							alarm2gModel.mo = mo;
							
							continue;
						} else if (alarm2gModel.specificProblem.startsWith("DIGITAL") && line.startsWith("DIP")) {
							line = reader.readLine();
							m = patt.matcher(line);
							if (m.find()) {
								dip = m.group(1);
							}
							alarm2gModel.dip=dip;
							
							continue;
						} else if (line.startsWith("EXTERNAL ALARM")) {
							line = reader.readLine();
							
							continue;
						} else {
							problemText.append(line + ">");
							line = reader.readLine();
						} 
					}
					
					// xoa dau ">" cuoi cung neu co
					if (problemText.length() > 0) {
						problemText.deleteCharAt(problemText.length()-1);
					}
					
					String pT =problemText.toString().replaceAll(">>", ">").replaceAll("\\s+", " ").trim();
					if (pT.length()>2000)
					{
					 pT= pT.substring(0, 2000);	
					}
					alarm2gModel.problemText = pT;
					
				}  else if (line.contains("Correlated ID:")) {
					alarm2gModel.correlatedId = DataTools.getData(line,":");
					if(blockFrom) alarm2gModel.fmAlarmId = alarm2gModel.logRecordId;
					else alarm2gModel.fmAlarmId = alarm2gModel.correlatedId;
				} else if(line.contains("FmAlarmId:")){
					alarm2gModel.fmAlarmId = DataTools.getData(line, ":");
					
				}else if(line.contains("repeat_count:")){
					alarm2gModel.repeatCount = DataTools.getData(line, ":");
					
				}
				
				if(line.contains("ALARM END")) {
					if(alarm2gModel.dip != null && dip.length() >= 7){
						alarm2gModel.btsId = alarm2gModel.dip.substring(0,6);
					}
					
					if(alarm2gModel.cell != null){
						alarm2gModel.neType = "CELL";
						alarm2gModel.btsId = alarm2gModel.cell.substring(0,6);
					}else{
						if(alarm2gModel.btsId != null){
							alarm2gModel.neType = "BTS";
						}else{
							alarm2gModel.neType = "BSC";
						}
					}
					
					// kiem tra neu thoi gian bat dau hoac ket thuc co > thoi diem hien tai - lastHour ko? Neu thoa man thi cho ghi ra file.
					boolean accept = true;
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Calendar cal = Calendar.getInstance();	
					cal.setTime(new Date());
					cal.add(Calendar.HOUR,lastHour*(-1));
					if (alarm2gModel.startDate!=null)
					{
						Date sdate = df.parse(alarm2gModel.startDate);
						if (sdate.before(cal.getTime()))
							accept= false;
					}
					if (alarm2gModel.endDate!=null)
					{
						Date edate = df.parse(alarm2gModel.endDate);
						if (edate.before(cal.getTime()))
							accept= false;
					}
					
					if(alarm2gModel.repeatCount == null && accept == true){
						writerAlarmLogs.newLine();
						writerAlarmLogs.write(alarm2gModel.toString());
					}
					
					cell = "";
					mo = "";
					dip = "";	
				}
			}
		}catch (Exception e) {
			throw new AL_ConvertException(e.getMessage(), "VMSC2-0306", e);
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
					writerAlarmLogs.close();
				} catch (IOException e) {
					logger.warn("Close IO stream failure " + e);
				}
			}
		}

		logger.info("Convert file: " + file.getName() + " success");
	}
}

