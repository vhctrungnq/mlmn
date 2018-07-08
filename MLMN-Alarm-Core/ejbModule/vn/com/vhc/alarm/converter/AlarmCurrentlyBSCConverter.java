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

// Su dung de convert file du lieu Active Nokia Bsc (ZAHO)
public class AlarmCurrentlyBSCConverter extends AL_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(AlarmCurrentlyBSCConverter.class.getName());

	private String bscName = "";
	private String categories = "";
	private String alarmInfo = "";
	private String line = "";
	private String vendor = "NOKIA SIEMENS";
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws AL_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		BufferedReader reader = null;
		BufferedWriter wAlarmLog = null; 	
		
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			wAlarmLog = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+getFileName(outFile.getName())));
			wAlarmLog.write("#VENDOR,NETWORK,NE_TYPE,BSC,TIME,ALARM_CLASS,ALARM_LOG_ID,ALARM_ID,ALARM_NAME,ALARM_INFO\n");
			
			boolean blockAlarm = false;//Danh dau bat dau khoi Alarm
			boolean block = false;
			String strLine = "";
			int count = 0;
			
			bscName = outFile.getName().substring(outFile.getName().indexOf(".")+1, outFile.getName().indexOf(".")+8); 
			
			while ((strLine = reader.readLine()) != null){
				while(strLine.indexOf("  ")>= 0)
				{
					strLine = strLine.replaceAll("  ", " ");
				}
				
				strLine = strLine.replaceAll(",", "."); 
				
				if(strLine.contains("ALARMS CURRENTLY ON")){
					block = true;
				}
				
				if(block == true){
					if(strLine.contains(bscName)){
						if(!alarmInfo.equalsIgnoreCase("")){
							if(!categories.equalsIgnoreCase("DISTUR") && !categories.equalsIgnoreCase("NOTICE")){
								wAlarmLog.write(line+alarmInfo);
								wAlarmLog.newLine();
							} 
						} 
						categories = "";
						alarmInfo = "";
						line = "";
						count = 0;					
						blockAlarm = true;
					}
					
					if(strLine.contains("END OF ALARMS")&& !alarmInfo.equalsIgnoreCase("")){
						if(!categories.equalsIgnoreCase("DISTUR") && !categories.equalsIgnoreCase("NOTICE"))
							wAlarmLog.write(line+alarmInfo);
						blockAlarm = false;
					}
				}
				
				if(blockAlarm == true){

					count = count + 1;
					if(count > 0){
						if(strLine.length() > 1)
							alarmInfo = alarmInfo+"<->"+strLine.toString().trim(); 
					}
					if(count == 1){
						try{
							String[] str = strLine.split(" ");
							int strLength = str.length; 
							//vendor && type
							line += vendor+","+"2G,BSC,";
							//bsc
							line += str[1].toString().trim()+",";
							//Time
							line += str[strLength-2]+" "+str[strLength-1].substring(0,8)+",";
						}catch(Exception e){
						}
					}
					else if(count == 2){
						try {
							String[] str = strLine.split(" ");
							categories = str[1].trim();
							//Alarm Class
							if(str[0].equals("***")){
								line += "A1,";
							}else if(str[0].equals("**")){
								line += "A2,";
							}else if(str[0].equals("*")){
								line += "A3,";
							}else{
								line += "Indeterminate,";
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
					else if(strLine.contains("(")){
						try {
							String[] str = strLine.split(" ");
							//Alarm_Log_id
							line += str[1].toString().trim().substring(str[1].toString().trim().indexOf("(")+1,str[1].toString().trim().indexOf(")"))+",";
							//alarm_name
							line += str[2]+",";
							//sologan
							String line1 = "";
							for(int i = 3; i < str.length-1; i++){
								line1 += str[i]+" ";
							}
							line += line1 + str[str.length-1]+",";
						} catch (Exception e) {
							// TODO: handle exception
						}
					}		
				}									
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
	}
}
