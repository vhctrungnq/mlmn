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

//Convert file du lieu Active 3G Nokia
public class AlarmCurrentlyRNCConverter extends AL_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(AlarmCurrentlyRNCConverter.class.getName());

	private String bscName = "";
	private String strDip="";
	private String strCell="";
	private String strSite ="";
	private String alarmInfo = ""; 
	private String vendor = "NOKIA SIEMENS";
	private String categories = "";
	private String line = "";
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
			wAlarmLog.write("#VENDOR,NETWORK,NE_TYPE,ALARM_LOG_ID,BSC,TIME,ALARM_CLASS,ALARM_ID,ALARM_NAME,SITE,DIP,CELL,ALARM_INFO\n");
			
			boolean blockAlarm = false;//Danh dau bat dau khoi Alarm
			boolean block = false;
			boolean blockFirt = false;
			String strLine = "";
			int count = 0; 
			bscName = outFile.getName().substring(outFile.getName().indexOf(".")+1, outFile.getName().indexOf(".")+8);
			
			while ((strLine = reader.readLine()) != null){
				while(strLine.indexOf("  ")>= 0)
				{
					strLine = strLine.replaceAll("  ", " ");
				}
				strLine = strLine.replaceAll(",", ".");

				if(strLine.contains("ACTIVE ALARMS")){
					blockFirt = true;
				} 
				
				if(blockFirt == true){
					if(strLine.contains(bscName)){
						if(!alarmInfo.equalsIgnoreCase("")){
							if(!categories.equalsIgnoreCase("DISTUR") && !categories.equalsIgnoreCase("NOTICE")){
								wAlarmLog.write(line+alarmInfo);
								wAlarmLog.newLine();
							} 
						}
						count = 0;
						blockAlarm = true;
						alarmInfo = "";
						line = "";
					}
					if(strLine.contains("NUMBER OF ACTIVE ALARMS") && !alarmInfo.equalsIgnoreCase("")){
						if(!categories.equalsIgnoreCase("DISTUR") && !categories.equalsIgnoreCase("NOTICE")){
							wAlarmLog.write(line+alarmInfo);
						} 
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
							String str1 = str[1].toString().trim();
							line += vendor+",3G,RNC,";
							//Alarm_Log_Id
							line += str1.substring(str1.indexOf("(")+1,str1.indexOf(")"))+",";
							//Bsc
							line += str[2].toString().trim()+",";
							//Time
							line += str[strLength-2].toString().trim()+" "+str[strLength-1].toString().trim().substring(0,8)+",";
						}catch(Exception e){
							logger.warn("Try line 1 : "+e);
						}						
					}
					else if(count == 2){
						try{
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
							
							if(str.length == 4){
								strDip = str[2].toString().trim();
								strCell = str[3].substring(str[3].indexOf("-")+1,str[3].length());// chi so cell(1,2,3.....)
							}else if(str.length == 3){
								strDip = str[2].toString().trim();
								
								if(!strDip.contains("WBTS"))
									strDip ="";
								
								strCell = "";
							}else{
								strDip = "";
								strCell = "";
							}
						}catch(Exception e){
							
						}
					}
					else if(count == 3){
						try{
							String[] str = strLine.split(" ");
							if(str.length > 2){
								//alarmName
								line += str[1]+",";
								String line1 = "";
								for(int i=2; i <str.length-1; i++){
									line1 += str[i]+" ";
								}
								line += line1 + str[str.length-1]+",";
							}else{
								//site
								strSite = strLine.toString().trim();
								block = true;
							}
						}catch(Exception e){
							
						}						
					}
					if(count == 4){
						if(block == true){
							try{
								String[] str = strLine.split(" ");
								//alarmName
								line += str[1]+",";
								String line1 = "";
								for(int i=2; i <str.length-1; i++){
									line1 += str[i]+" ";
								}
								line += line1+ str[str.length-1]+",";
								if(strCell == ""){
									line += strSite+","+strDip+","+strCell+",";
									strSite="";strDip="";strCell="";
								} 
								else{
									line += strSite+","+strDip+","+strSite+strCell+",";
									strSite="";strDip="";strCell="";
								} 
								block = false;
							}catch(Exception e){
								
							}
						}else{
							line += strSite+","+strDip+","+strCell+",";
							strSite="";strDip="";strCell="";
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
		logger.info("Convert file: " + file.getName() + " success");
	}
}
