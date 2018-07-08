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

//Su dung de convert file du lieu Active Ericsson 3G va Active Ericsson Mgw
public class AlarmActiveCoreConverter extends AL_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(AlarmActiveCoreConverter.class.getName());
	
	private String ne = "" ;
	private String network = "";

	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws AL_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);
		
		BufferedReader reader = null;
		BufferedWriter wAlarmLog = null;
		String vendor = "ERICSSON";
		int[] lengthArr = new int[3];
		
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			wAlarmLog = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+getFileName(outFile.getName())));
			wAlarmLog.write("#VENDOR;NETWORK;NE_TYPE;NE;SDATE;ALARM_CLASS;ALARM_NAME;SITE;CELL;ALARM_INFO\n");
			
			boolean blockAlarm = false;//Danh dau bat dau khoi Alarm
			String strLine = "";  
			
			ne = outFile.getName().substring(outFile.getName().indexOf(".")+1, outFile.getName().indexOf(".")+8);
			
			if(ne.substring(0,1).equalsIgnoreCase("R")){
				network = "3G";
			}else{
				network = "CS_CORE";
			}

			while ((strLine = reader.readLine()) != null){
				if(strLine.length() < 1 || strLine.contains("===")){
					continue;
				}
				strLine = strLine.replaceAll(";", ".");
				 
				if(strLine.contains("Date & Time")){
					lengthArr = new int[3]; 
					lengthArr[0] = strLine.substring(0, strLine.indexOf("S")).length();
					lengthArr[1] = lengthArr[0]+ strLine.substring(strLine.indexOf("S"), strLine.indexOf("Specific Problem")).length();
					lengthArr[2] = lengthArr[1]+ strLine.substring(strLine.indexOf("Specific Problem"), strLine.indexOf("MO")).length();
					blockAlarm = true;
					continue;
				}
				
				try{
					if(blockAlarm){
						String sdate = "",severity="",alarmName="",alarmInfo="", site = "", cell="", neType = "";
						if(strLine.contains("Licensing")){
							sdate = strLine.substring(0,lengthArr[0]).trim();
							severity = strLine.substring(lengthArr[0],lengthArr[1]).trim();
							alarmName = strLine.substring(lengthArr[1],strLine.indexOf("Licensing")+9).trim();
							alarmInfo = strLine.substring(strLine.indexOf("Licensing")+9).trim();
						}else{
							sdate = strLine.substring(0,lengthArr[0]).trim();
							severity = strLine.substring(lengthArr[0],lengthArr[1]).trim();
							alarmName = strLine.substring(lengthArr[1],lengthArr[2]).trim();
							alarmInfo = strLine.substring(lengthArr[2]).trim();
						} 
						
						if(alarmInfo.contains("UtranCell=")){
							cell = alarmInfo.substring(alarmInfo.indexOf("UtranCell=")+10, alarmInfo.indexOf("UtranCell=")+17).trim();
							site = cell.substring(0,cell.length()-1);
						}
						
						if(network.equals("3G")){
							if(site.equals("")){
								neType = "RNC";
							}else{
								neType = "NODEB";
							}
						}else{
							if(ne.substring(0,2).equalsIgnoreCase("MS")){
								neType = "MSC";
							}else{
								neType = "MGW";
							} 
						}
						
						if(severity.equalsIgnoreCase("C")){
							severity = "A1";						
						}else if(severity.equalsIgnoreCase("M")){
							severity = "A2";
						}else if(severity.equalsIgnoreCase("Mi")){
							severity = "A3";
						}else if(severity.equalsIgnoreCase("W")){
							severity = "O2";
						}else{
							severity = "Indeterminate";
						} 
						
						wAlarmLog.write(vendor+";"+network+";"+neType+";"+ne+";"+sdate+";"+severity+";"+alarmName+";"+site+";"+cell+";"+alarmInfo);
						wAlarmLog.newLine();
					}	
				}catch(Exception e){}							
			}
		}catch (Exception e) {
			throw new AL_ConvertException(e.getMessage(), "VMSC2-0306", e);
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
		logger.info("Convert file: " + file.getName() + " success");
	}
}
