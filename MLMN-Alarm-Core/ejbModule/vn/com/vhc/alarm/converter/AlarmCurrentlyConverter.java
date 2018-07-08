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

public class AlarmCurrentlyConverter extends AL_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(AlarmCurrentlyConverter.class.getName());

	private String bscName = "";
	private String index;
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
			wAlarmLog.write("#BSC,TIME,ALARM_CLASS,TYPE,ALARM_LOG_ID,SOLOGAN,ALARM_NAME,ALARM_INFO,COUNT");
			
			boolean blockAlarm = false;//Danh dau bat dau khoi Alarm
			//boolean block = false;//Danh dau dinh dang khoi Alarm
			String strLine = "";
			int count = 0;
			String alarmInfo = "";
			bscName = outFile.getName().substring(outFile.getName().indexOf(".")+1, outFile.getName().indexOf(".")+8);
			 
			String tenfile = outFile.getName().substring(0,outFile.getName().lastIndexOf("."));
			index = tenfile.substring(tenfile.lastIndexOf(".")+1, tenfile.length()); 
			
			//System.out.println("bscName= "+bscName);
			while ((strLine = reader.readLine()) != null){
				while(strLine.indexOf("  ")>= 0)
				{
					strLine = strLine.replaceAll("  ", " ");
				}
				strLine = strLine.replaceAll(",", ".");
				//System.out.println("strLineLeng= "+strLine.length());
				if(strLine.contains(bscName)){
					count = 0;
					wAlarmLog.newLine();
					blockAlarm = true;
				}
				
				if(blockAlarm == true){
					if(!strLine.contains("END OF ALARMS")){
						count = count + 1;
						if(count > 0){
							if(strLine.length() > 1)
								alarmInfo = alarmInfo+"<->"+strLine.toString().trim();
							//System.out.println("alarmInfo= "+alarmInfo);
						}
						if(count == 1){
							String[] str = strLine.split(" ");
							int strLength = str.length;
							//System.out.println("strLength= "+strLength);
							//Bsc
							wAlarmLog.write(str[1].toString().trim()+",");
							//Time
							wAlarmLog.write(str[strLength-2]+" "+str[strLength-1]+",");
						}
						else if(count == 2){
							String[] str = strLine.split(" ");
							//Alarm Class
							if(str[0].equals("***")){
								wAlarmLog.write("A1,");
							}else if(str[0].equals("**")){
								wAlarmLog.write("A2,");
							}else if(str[0].equals("*")){
								wAlarmLog.write("A3,");
							}else{
								wAlarmLog.write("NONE,");
							}
							//Type (Dung trong qua trinh Import)
							try{
								if(str[1].equals("ALARM"))
									wAlarmLog.write("ALARM FROM,");
							}catch(Exception e){
								//logger.warn("Try: "+e);
							}
						}
						else if(strLine.contains("(")){
							String[] str = strLine.split(" ");
							//Alarm_Log_id
							wAlarmLog.write(str[1].toString().trim().substring(str[1].toString().trim().indexOf("(")+1,str[1].toString().trim().indexOf(")"))+",");
							//sologan
							wAlarmLog.write(str[2]+",");
							//alarm_name
							for(int i = 3; i < str.length-1; i++){
								wAlarmLog.write(str[i]+" ");
							}
							wAlarmLog.write(str[str.length-1]+",");
						}
						else if(strLine.length() == 0){
							alarmInfo = alarmInfo.replace("COMMAND EXECUTED", "");
							wAlarmLog.write(alarmInfo+",");
							wAlarmLog.write(index);
							alarmInfo = "";
						};					
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
		//logger.info("Convert file: " + file.getName() + " success");
	}
}
