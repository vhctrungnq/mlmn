package vn.com.vhc.alarm.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.log4j.Logger;


import vn.com.vhc.alarm.cni.AL_BasicConverter;
import vn.com.vhc.alarm.util.exceptions.AL_ConvertException;

public class AlarmActiveNokia2GConvert extends AL_BasicConverter{

	private static final Logger logger = Logger.getLogger(AlarmActiveNokia2GConvert.class.getName()); 
	private String sep = ";";
	private String ne = "",bts = "", cell = "", trx = "";
	private String day = "",sdate = "", severity = "", alarmType = "", fmAlarmId = "",
					alarmId = "", alarmName = "", alarmInfo = "";
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws AL_ConvertException  {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		BufferedReader reader = null;
		BufferedWriter wAlarmLog = null; 	
		
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			wAlarmLog = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+getFileName(outFile.getName())));

			boolean blockAlarm = false;//Danh dau bat dau khoi Alarm
			String vendor = "NOKIA SIEMENS";
			String network = "2G"; 
			String userName = "SYSTEM_ACTIVE";
			String neType = "";
			String strLine = "";
			//
			int count = 0; 
			//Phan biet khoi NeType(thuoc BTS hay BSC)
			boolean blockBsc = false; 
			boolean blockBts = false;
			//Dem so luong dong trong 1 khoi du lieu
			int countBlock = 0; 
			boolean blockExternal = false;
			while ((strLine = reader.readLine()) != null){
				//Bo qua nhung ban dong khong co gia tri
				if(strLine.length() == 0) continue;
				strLine = strLine.trim();
				count++; 
				if(count == 3){
					strLine = delSpace(strLine);
					neType = strLine.split(" ")[0];
					if(neType.equalsIgnoreCase("FlexiBSC")){
						wAlarmLog.write("#USER_NAME;TYPE;SDATE;VENDOR;NETWORK;NE_TYPE;NE;SEVERITY;FM_ALARM_ID;ALARM_ID;ALARM_NAME;ALARM_INFO\n");
						neType = "BSC";
						ne = strLine.split(" ")[1];
						blockBsc = true;
						blockBts = false;
					}else if(neType.equalsIgnoreCase("BTS")){
						wAlarmLog.write("#USER_NAME;TYPE;SDATE;VENDOR;NETWORK;NE_TYPE;NE;SITEID;CELLID;TRX;SEVERITY;FM_ALARM_ID;ALARM_ID;ALARM_NAME;ALARM_INFO\n");
						neType = "BTS";
						ne = outFile.getName().substring(outFile.getName().indexOf(".")+1, outFile.getName().indexOf(".")+8); 
						blockBsc = false;
						blockBts = true;
					} 
				}
				
				//Convert du lieu BSC
				if(blockBsc){
					if(blockAlarm){
						if(strLine.contains(ne) || strLine.contains("END OF ALARMS CURRENTLY ON")){
							if(alarmInfo.length() > 0){
								String line = userName+sep+alarmType+sep+sdate+sep+vendor+sep+network+sep+
										neType+sep+ne+sep+severity+sep+fmAlarmId+sep+alarmId+sep+alarmName+sep+alarmInfo;
								wAlarmLog.write(line);
								wAlarmLog.newLine();
								refresh();
							}
						}
					}
					
					if(strLine.contains(ne)){ 
						blockAlarm = true;
						countBlock = 0;
					}
					if(blockAlarm){
						countBlock++; 
						
						strLine = delSpace(strLine);
						String[] str = null;
						
						if(countBlock > 0){
							alarmInfo += "<->"+strLine; 
						}
						
						if(countBlock == 1){  
							str = strLine.split(" ");
							try {
								day = str[str.length-2]+" "+str[str.length-1].substring(0,8); 
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
						
						if(countBlock == 2){ 
							str = strLine.split(" ");
							severity = get_Severity(str[0].toString());
							try {
								if(str[1].equalsIgnoreCase("ALARM")){
									sdate = day;
									alarmType = "ALARM ACTIVE";
								}else{
									blockAlarm = false;
									refresh();
									continue;
								}
							} catch (Exception e) {
								// TODO: handle exception
								blockAlarm = false;
								refresh();
								continue;
							}
						}
						
						if(strLine.contains("(")){
							str = strLine.split(" ");
							if(str[0].contains("(")){
								try {
									blockExternal = true;
									fmAlarmId = str[0].substring(str[0].indexOf("(")+1, str[0].indexOf(")"));
								} catch (Exception e) {
									blockExternal = false;
								} 
							} 
							alarmId = str[1];
							String temp = "";
							for(int i = 2;i < str.length; i++){
								temp += str[i]+" ";
							}
							alarmName = temp.trim();
							continue;
						}
						
						if(blockExternal){
							if(alarmName.contains("EXTERNAL AL")){
								alarmName = strLine;
							} 
							blockExternal = false;
						}  
					} 
				}
				//Convert du lieu BTS
				if(blockBts){
					if(blockAlarm){
						if(strLine.contains(ne) || strLine.contains("END OF BTS ALARM LISTING")){
							if(alarmInfo.length() > 0){
								String line = userName+sep+alarmType+sep+sdate+sep+vendor+sep+network+sep+
										neType+sep+ne+sep+bts+sep+cell+sep+trx+sep+severity+sep+fmAlarmId+sep+alarmId+sep+alarmName+sep+alarmInfo;
								wAlarmLog.write(line);
								wAlarmLog.newLine();
								refresh();
							}
						}
					}
					
					if(strLine.contains(ne)){ 
						blockAlarm = true;
						countBlock = 0;
					}
					if(blockAlarm){
						countBlock++; 
						strLine = delSpace(strLine);
						String[] str = null;
						
						if(countBlock > 0){
							alarmInfo += "<->"+strLine; 
						} 
						
						if(countBlock == 1){ 
							str = strLine.split(" ");
							try {
								if(str.length == 6){ 
									bts = str[1].split("-")[0]+"-"+Integer.parseInt(str[1].split("-")[1]);
									cell = str[2].split("-")[0]+"-"+Integer.parseInt(str[2].split("-")[1]);
									day = str[4]+" "+str[5].substring(0, 8);
								}else if(str.length == 5){ 
									bts = str[1].split("-")[0]+"-"+Integer.parseInt(str[1].split("-")[1]);
									day = str[3]+" "+str[4].substring(0, 8);
								} 
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
						
						if(countBlock == 2){
							str = strLine.split(" ");
							severity = get_Severity(str[0].toString());
							try {
								if(str[1].equalsIgnoreCase("ALARM")){
									sdate = day;
									alarmType = "ALARM ACTIVE";
								}else{
									blockAlarm = false;
									refresh();
									continue;
								}
							} catch (Exception e) {
								// TODO: handle exception
								blockAlarm = false;
								refresh();
								continue;
							}
							
							try {
								if(str[2].equalsIgnoreCase("TRX")){
									trx = str[2]+"-"+Integer.parseInt(str[3].substring(str[3].indexOf("-")+1));
								}
							} catch (Exception e) {
								// TODO: handle exception
							} 
						}
						
						if(strLine.contains("(")){
							str = strLine.split(" ");
							if(str[0].contains("(")){
								try {
									blockExternal = true;
									fmAlarmId = str[0].substring(str[0].indexOf("(")+1, str[0].indexOf(")"));
								} catch (Exception e) {
									blockExternal = false;
								} 
							} 
							alarmId = str[1];
							String temp = "";
							for(int i = 2;i < str.length; i++){
								temp += str[i]+" ";
							}
							alarmName = temp.trim();
							continue;
						}
						
						if(blockExternal){
							if(alarmName.contains("EXTERNAL AL")){
								alarmName = strLine;
							} 
							blockExternal = false;
						}  
					}
				}
			}
		}catch (Exception e) {
			logger.error(e, e);;
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
					wAlarmLog.close();
				} catch (IOException e) {
					logger.warn("Close IO stream failure " + e);
				}
			}
		}
	}
	//Loai bo dau cach
	private String delSpace(String str){
		while(str.indexOf("  ") > 0){
			str = str.replace("  ", " ");
		}
		return str;
	}
	//Refresh
	private void refresh(){ 
		day = "";
		sdate = "";
		severity = "";
		alarmType = "";
		fmAlarmId = "";
		alarmId = "";
		alarmName = "";
		alarmInfo = "";
		bts = "";
		cell = "";
		trx = "";
	}
	//Convert Severity
	 private static String  get_Severity(  String strAlarm_Class)
	 {
		 String strReturn = "";
	
	 if (strAlarm_Class.equals("***") || strAlarm_Class.equals("..."))
		 strReturn = "A1";
	 else if (strAlarm_Class.equals("**") || strAlarm_Class.equals(".."))
		 strReturn = "A2";
	 else if (strAlarm_Class.equals("*") || strAlarm_Class.equals("."))
		 strReturn = "A3";
	 else
	     strReturn = "A4";
	     return strReturn;
	 }

}
