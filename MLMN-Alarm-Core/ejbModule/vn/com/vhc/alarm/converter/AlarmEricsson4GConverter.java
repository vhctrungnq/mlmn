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
import vn.com.vhc.alarm.model.StructAlarmEricsson;
import vn.com.vhc.alarm.util.exceptions.AL_ConvertException;
import vn.com.vhc.alarm.utils.DataTools;

/**
 * <p>
 * Title: 
 * </p>
 * <p>
 * Description: 
 * </p>
 * <p>
 * Copyright: Copyright (c) by VHCSoft 2016
 * </p>
 * <p>
 * Company: VietNam High Technology Software JSC.
 * </p>
 * <p>
 * Create Date:Dec 29, 2016
 * </p>
 * 
 * @author VHC - Software
 * @version 1.0
 */

public class AlarmEricsson4GConverter extends AL_BasicConverter {
	
	private static final Logger logger = Logger.getLogger(AlarmEricsson4GConverter.class.getName()); 
	
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws AL_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);
		
		// Tao buffer doc file raw
		BufferedReader reader = null;
		
		// Tao buffer ghi du lieu da convert
		BufferedWriter writer = null;
		StructAlarmEricsson structAlarm = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			
			// Tao file convert
			File outFile = new File(direcPath, file.getName());
			
			// Khoi tao buffer writer
			writer = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));
			
			// Ghi dong head file
			writer.write("#TYPE;VENDOR;NETWORK;OBJECT_OF_REPERENCE;NE;SITE;CELL;SDATE;EDATE;EVENT_TYPE;ALARM_CLASS;PROBABLE_CAUSE;ALARM_NAME;PROBLEM_TEXT;FM_ALARM_ID;NE_TYPE");
			String strLine = "";
			boolean blockAlarm = false;
			boolean blockProblemText = false;
			int count = 0;
			String network="";
			
			if(file.getName().contains("LTE_")){
				network="RAN_4G";
			}else{
				network="CORE_4G";
			}
			
			while ((strLine = reader.readLine()) != null){
				if(strLine.length() == 0) continue;
				
				while(strLine.indexOf("  ")>= 0) strLine = strLine.replaceAll("  ", " ");
				
				if(strLine.contains("--ALARM FROM") || strLine.contains("--EVENT FROM")){
					structAlarm = new StructAlarmEricsson();
					structAlarm.type = "ALARM FROM";
					structAlarm.vendor = "ERICSSON";
					structAlarm.network = network;
					blockAlarm = true;
					
				}else if(strLine.contains("--ALARM CLEARING")){
					structAlarm = new StructAlarmEricsson();
					structAlarm.type = "ALARM CLEARING";
					structAlarm.vendor = "ERICSSON";
					structAlarm.network = network;
					blockAlarm = false;
					
				}else if(strLine.contains("Log Record ID:")){
					structAlarm.log_record_id = DataTools.getData(strLine, ":");
					
				}else if(strLine.contains("Event Time:")){
					if(blockAlarm) structAlarm.sdate = DataTools.getData(strLine, ":");
					else structAlarm.edate = DataTools.getData(strLine, ":");
				}
				
				if(strLine.contains("Event Type:")){
					structAlarm.event_type = DataTools.getData(strLine, ":");
					
				}else if(strLine.contains("Object of Reference:")){
					String data = DataTools.getData(strLine, ":");
					structAlarm.object_of_reperence = data;
					
					String[] arrayData = data.split(",");
					for(int i = 0 ; i < arrayData.length; i++){
						
						//get ne
						if(i==2){
							structAlarm.mscid = DataTools.getData(arrayData[2], "="); 
							if(structAlarm.network.equals("RAN_4G")) structAlarm.site = structAlarm.mscid;
						}
						
						//get cell
						if(arrayData[i].contains("EUtranCellFDD")){
							structAlarm.cell = DataTools.getData(arrayData[i], "=");
							break;
						}
					}
					
				}else if(strLine.contains("Perceived Severity:")){
					String data = DataTools.getData(strLine, ":");
					structAlarm.perceived_severity = data;
					if(blockAlarm){
						structAlarm.alarm_class =  DataTools.formatSeverity(data);
					}
					
				}else if(strLine.contains("Probable Cause:")){
					structAlarm.probable_cause = DataTools.getData(strLine, ":");
					
				}else if(strLine.contains("Specific Problem:")){
					structAlarm.alarm_name = DataTools.getData(strLine, ":");
					
				}else if(strLine.contains("Problem Text:")){
					blockProblemText = true;
					structAlarm.problem_text = "";
					count = 0;
					
				}else if(strLine.contains("Problem Data:")){
					blockProblemText = false;
					
				}else if(strLine.contains("Correlated ID:")){
					blockProblemText = false;
					structAlarm.correlated_id = DataTools.getData(strLine, ":");
					
				}else if(strLine.contains("Previous Severity:")){
					String previous_severity = DataTools.getData(strLine, ":");
					if(!blockAlarm){
						structAlarm.alarm_class =  DataTools.formatSeverity(previous_severity);
					}
				}else if(strLine.contains("FmAlarmId:")){
					structAlarm.fm_alarm_id = DataTools.getData(strLine, ":");
					
				}else if(strLine.contains("repeat_count:")){
					structAlarm.repeat_count = DataTools.getData(strLine, ":");
					
				}else if(strLine.contains("--ALARM END--")){
					if(structAlarm.repeat_count == null || structAlarm.repeat_count.equals("")){
						if(blockAlarm){
							structAlarm.fm_alarm_id = structAlarm.log_record_id;
						}else{
							structAlarm.fm_alarm_id = structAlarm.correlated_id;
						}
						
						if(structAlarm.mscid.substring(0,3).equalsIgnoreCase("EPG")){
							structAlarm.ne_type = "EPG";
						}else if(structAlarm.mscid.contains("LTE")){
							structAlarm.ne_type = "LTE";
						}else if(structAlarm.mscid.substring(0,3).equalsIgnoreCase("MME")){
							structAlarm.ne_type = "SGSN";
						}
						// kiem tra neu thoi gian bat dau hoac ket thuc co > thoi diem hien tai - lastHour ko? Neu thoa man thi cho ghi ra file.
						boolean accept = false;
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						Calendar cal = Calendar.getInstance();	
						cal.setTime(new Date());
						cal.add(Calendar.HOUR,lastHour*(-1));
						if (structAlarm.sdate!=null)
						{
							Date sdate = df.parse(structAlarm.sdate);
							if (sdate.after(cal.getTime()))
								accept= true;
						}
						if (structAlarm.edate!=null)
						{
							Date edate = df.parse(structAlarm.edate);
							if (edate.after(cal.getTime()))
								accept= true;
						}
						
						if( accept == true){
							writer.newLine();
							writer.write(structAlarm.toString()); 
						}
					}
				}
				
				if(blockProblemText){
					count ++;
					if(count > 1)
						structAlarm.problem_text += strLine+">";
					structAlarm.problem_text = structAlarm.problem_text.replace("	", "");
					structAlarm.problem_text = structAlarm.problem_text.replace(";", ".");
				}
			}
		}catch (Exception e) {
			throw new AL_ConvertException(e.getMessage(), "VMS-ERROR-0306", e);
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
					writer.close();
				} catch (IOException e) {
					System.out.println("Close IO stream failure " + e);
				}
			}
		}

		logger.info("Convert file: " + file.getName() + " success");

	} 
}

