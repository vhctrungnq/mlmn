package inventory.converter;

import inventory.cni.IN_BasicConverter;
import inventory.util.exceptions.IN_ConvertException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.log4j.Logger;

// Su dung de convert file du lieu Active Nokia 3G
public class AlarmActiveNokia3GConvert extends IN_BasicConverter {
	private static final Logger logger = Logger.getLogger(AlarmActiveNokia3GConvert.class.getName()); 
	private String sep = ";";
	private String ne = "", bts ="", cell ="",alarmType = "", fmAlarmId = "", severity = "", sdate = "",
			 alarmId = "", alarmName = "", alarmInfo = "", day = "";
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
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
			String network = "3G"; 
			String userName = "SYSTEM_ACTIVE";
			String neType = "";
			String strLine = "";
			//
			int count = 0; 
			//Phan biet khoi NeType
			boolean blockRsc = false; 
			//Dem so luong dong trong 1 khoi du lieu
			int countBlock = 0; 
			while ((strLine = reader.readLine()) != null){
				//Bo qua nhung ban dong khong co gia tri
				if(strLine.length() == 0) continue;
				strLine = strLine.trim();
				count++; 
				if(count == 3){
					strLine = delSpace(strLine);
					ne = strLine.split(" ")[1];
				}
				if(count == 5){
					wAlarmLog.write("#USER_NAME;TYPE;SDATE;VENDOR;NETWORK;NE_TYPE;NE;SITE;CELLID;SEVERITY;FM_ALARM_ID;ALARM_ID;ALARM_NAME;ALARM_INFO\n");
					blockRsc = true;  
				}
				
				//Convert du lieu RNC
				if(blockRsc){
					if(blockAlarm){
						if(strLine.contains(ne) || strLine.contains("NUMBER OF ACTIVE ALARMS DISPLAYED")){
							if(alarmInfo.length() > 0){
								String line = userName+sep+alarmType+sep+sdate+sep+vendor+sep+network+sep+
										neType+sep+ne+sep+bts+sep+cell+sep+severity+sep+fmAlarmId+sep+alarmId+sep+alarmName+sep+alarmInfo;
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
						String[] str = strLine.split(" ");
						
						if(countBlock == 1){ 
							try {
								fmAlarmId = str[0].substring(str[0].indexOf("(")+1, str[0].indexOf(")"));
							} catch (Exception e) {
								// TODO: handle exception
							}
							
							try {
								day = str[str.length-2]+" "+str[str.length-1].substring(0,8); 
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
						
						if(countBlock == 2){
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
								if(str[2].contains("WBTS")){
									neType = "NODEB";
									bts = str[2];
								}else{
									neType = "RNC";
								}
							} catch (Exception e) {
								// TODO: handle exception
								neType = "RNC";
							}
							
							try {
								if(str[3].contains("WCEL"))
									cell = str[3];
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
						
						if(countBlock == 3 || countBlock == 4){
							if(isValidNumber(str[0])){
								alarmId = str[0];
								String temp = "";
								for(int i = 1; i < str.length; i++){
									temp += str[i]+" ";
								}
								alarmName = temp.trim();
							}
						}
						
						if(countBlock > 0){
							alarmInfo += "<->"+strLine; 
						}
					} 
				}
			}
		}catch (Exception e) {
			throw new IN_ConvertException(e.getMessage(), "VMSC2-0306", e);
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
	 //Kiem tra kieu so
	 private static boolean isValidNumber(String number) { 
			try {
				Integer.parseInt(number);
				return true;
			} catch(Exception ex) {
				return false;
			}

		}
}
