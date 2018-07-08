package vn.com.vhc.alarm.converter;

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

import org.apache.log4j.Logger;

import vn.com.vhc.alarm.cni.AL_BasicConverter;
import vn.com.vhc.alarm.model.StructAlarmEricsson3G;
import vn.com.vhc.alarm.util.exceptions.AL_ConvertException;
import vn.com.vhc.alarm.utils.DataTools;

/**
 * <p>
 * Title: Chuan hoa du lieu canh bao 3g vendor ericsson
 * </p>
 * <p>
 * Description: Du lieu kieu text bao gom 2 khoi bat dau, va ket thuc.
 * </p>
 * <p>
 * Copyright: Copyright (c) by VHCSoft 2016
 * </p>
 * <p>
 * Company: VietNam High Technology Software JSC.
 * </p>
 * <p>
 * Create Date: May 24, 2016
 * </p>
 * 
 * @author VHC - Software
 * @version 1.0
 */
public class AlarmLog3GConverter extends AL_BasicConverter {	
	private static final Logger logger = Logger
			.getLogger(AlarmLog3GConverter.class.getName()); 
	
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws AL_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);
		
		BufferedReader reader = null;
		BufferedWriter writerAlarmLogs3G = null;
		StructAlarmEricsson3G structAlarm3G = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			writerAlarmLogs3G = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));
			writerAlarmLogs3G.write("#TYPE;VENDOR;NETWORK;OBJECT_OF_REPERENCE;RNCID;SITE;CELLID;SECTOR;ANTEN_ERROR;SDATE;EDATE;EVENT_TYPE;ALARM_CLASS;PRO_CAUSE;ALARM_NAME;PROBLEM_TEXT;FM_ALARM_ID;NE_TYPE");
			String strLine = "";
			boolean blockAlarm = false;
			boolean blockProblemText = false;
			int count = 0;
			
			while ((strLine = reader.readLine()) != null){
				if(strLine.length() == 0) continue;
				
				while(strLine.indexOf("  ")>= 0) strLine = strLine.replaceAll("  ", " ");
				
				if(strLine.contains("--ALARM FROM") || strLine.contains("--EVENT FROM")){
					structAlarm3G = new StructAlarmEricsson3G();
					structAlarm3G.type = "ALARM FROM";
					structAlarm3G.vendor = "ERICSSON";
					structAlarm3G.network = "3G";
					blockAlarm = true;
					
				}else if(strLine.contains("--ALARM CLEARING")){
					structAlarm3G = new StructAlarmEricsson3G();
					structAlarm3G.type = "ALARM CLEARING";
					structAlarm3G.vendor = "ERICSSON";
					structAlarm3G.network = "3G";
					blockAlarm = false;
					
				}else if(strLine.contains("Log Record ID:")){
					structAlarm3G.log_record_id = DataTools.getData(strLine, ":");
					
				}else if(strLine.contains("Event Time:")){
					if(blockAlarm) structAlarm3G.sdate = DataTools.getData(strLine, ":");
					else structAlarm3G.edate = DataTools.getData(strLine, ":");
					
				}else if(strLine.contains("Event Type:")){
					structAlarm3G.event_type = DataTools.getData(strLine, ":");
					
				}else if(strLine.contains("Object of Reference:")){
					String data = DataTools.getData(strLine, ":");
					structAlarm3G.object_of_reperence = data;
					String[] arrayData = data.split(",");
					for(String item : arrayData){
						if(item.contains("SubNetwork=")){
							structAlarm3G.rncid = DataTools.getData(item, "=");
							
						}else if(item.contains("MeContext=")){
							if(!DataTools.getData(item, "=").equals(structAlarm3G.rncid)){
								structAlarm3G.site = DataTools.getData(item, "=");
							}
							
						}else if(item.contains("UtranCell=")){
							structAlarm3G.cellid=DataTools.getData(item, "=");
							
						}else if(item.contains("SectorAntenna=")){
							structAlarm3G.sector=DataTools.getData(item, "=");
							
						}else if(item.contains("AntennaBranch=")){
							structAlarm3G.anten_error=DataTools.getData(item, "=");
							
						}
						
						if(structAlarm3G.cellid != null){
							structAlarm3G.site = structAlarm3G.cellid.substring(0,6);
						}
						
						if(structAlarm3G.site == null || structAlarm3G.site.equals("")){
							if (item.contains("ManagedElement=")){
								try {
									structAlarm3G.site = item.substring(item.indexOf("ManagedElement=")+15,item.length());
									if(structAlarm3G.site.length() < 6){
										structAlarm3G.site = "";
									}
								} catch (Exception e) { structAlarm3G.site = ""; }
							} 
						} 
					}
					
				}else if(strLine.contains("Probable Cause:")){
					structAlarm3G.probable_cause=DataTools.getData(strLine, ":");
					
				}else if(strLine.contains("Perceived Severity:")){
					String data = DataTools.getData(strLine, ":");
					structAlarm3G.perceived_severity = data;
					structAlarm3G.alarm_class =  DataTools.formatSeverity(data);
					
				}else if(strLine.contains("Specific Problem:")){
					structAlarm3G.alarm_name = DataTools.getData(strLine, ":");
					
				}/*else if(strLine.contains("Logging Time:")){
					structAlarm3G.logging_time = DataTools.getData(strLine, ":");
					
				}*/else if(strLine.contains("Problem Text:")){
					blockProblemText = true;
					structAlarm3G.problem_text = "";
					count = 0;
					
				}else if(strLine.contains("Problem Data:")){
					blockProblemText = false;
					
				}else if(strLine.contains("Correlated ID:")){
					structAlarm3G.correlated_id =  DataTools.getData(strLine, ":");
					
				}
				else if(strLine.contains("Previous Severity:")){
					String previous_severity = DataTools.getData(strLine, ":");
					if(!blockAlarm){
						structAlarm3G.alarm_class =  DataTools.formatSeverity(previous_severity);
					}
				}else if(strLine.contains("FmAlarmId:")){
					structAlarm3G.fm_alarm_id = DataTools.getData(strLine, ":");
					
				}else if(strLine.contains("repeat_count:")){
					structAlarm3G.repeat_count = DataTools.getData(strLine, ":");
					
				}else if(strLine.contains("--ALARM END--")){
					
					// kiem tra neu thoi gian bat dau hoac ket thuc co > thoi diem hien tai - lastHour ko? Neu thoa man thi cho ghi ra file.
					boolean accept = true;
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Calendar cal = Calendar.getInstance();	
					cal.setTime(new Date());
					cal.add(Calendar.HOUR,lastHour*(-1));
					if (structAlarm3G.sdate!=null)
					{
						Date sdate = df.parse(structAlarm3G.sdate);
						if (sdate.before(cal.getTime()))
							accept= false;
					}
					if (structAlarm3G.edate!=null)
					{
						Date edate = df.parse(structAlarm3G.edate);
						if (edate.before(cal.getTime()))
							accept= false;
					}
					
					if((structAlarm3G.repeat_count == null || structAlarm3G.repeat_count.equals(""))&&accept==true){
						if(structAlarm3G.cellid != null && !structAlarm3G.cellid.equals("")){
							structAlarm3G.ne_type = "CELL";
						}else{
							if(structAlarm3G.site != null && !structAlarm3G.site.equals("")){
								structAlarm3G.ne_type = "NODEB";
							}else{
								structAlarm3G.ne_type = "RNC";
							}
						}
						
						if(blockAlarm) structAlarm3G.fm_alarm_id=structAlarm3G.log_record_id;
						else structAlarm3G.fm_alarm_id=structAlarm3G.correlated_id;
						
						if (structAlarm3G.problem_text.length()>2000)
							structAlarm3G.problem_text = structAlarm3G.problem_text.substring(0, 2000);
						
						writerAlarmLogs3G.newLine();
						writerAlarmLogs3G.write(structAlarm3G.toString()); 
					}
				}
				
				if(blockProblemText){
					count ++;
					if(count > 1)
						structAlarm3G.problem_text += strLine+" ";
				}
			}
		}catch (Exception e) {
			throw new AL_ConvertException(e.getMessage(), "VMSC2-0306", e);
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
					writerAlarmLogs3G.close();
				} catch (IOException e) {
					System.out.println("Close IO stream failure " + e);
				}
			}
		}

		logger.info("Convert file: " + file.getName() + " success");

	} }
