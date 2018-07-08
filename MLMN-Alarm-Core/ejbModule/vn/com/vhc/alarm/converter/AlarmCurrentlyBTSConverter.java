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
 
// Su dung convert du lieu file Active Nokia BTS (ZEOL)
public class AlarmCurrentlyBTSConverter extends AL_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(AlarmCurrentlyBTSConverter.class.getName());

	private String bscName = "";
	private String line = "";
	private String strDip = "";
	private String categories = "";
	private String alarmInfo = "";
	
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
			wAlarmLog.write("#VENDOR,NETWORK,NE_TYPE,BSC,SITE,TIME,ALARM_CLASS,CELL,DIP,ALARM_LOG_ID,ALARM_ID,ALARM_NAME,ALARM_INFO\n");
			
			boolean blockAlarm = false;//Danh dau bat dau khoi Alarm
			boolean block = false;
			boolean blockFrist = false;
			String vendor = "NOKIA SIEMENS",network = "2G",neType = "BTS"; 

			String strLine = "";
			int count = 0; 
			
			bscName = outFile.getName().substring(outFile.getName().indexOf(".")+1, outFile.getName().indexOf(".")+8); 
			
			while ((strLine = reader.readLine()) != null){
				while(strLine.indexOf("  ")>= 0)
				{
					strLine = strLine.replaceAll("  ", " ");
				}
				strLine = strLine.replaceAll(",", ".");
				
				if(strLine.contains("BTS ALARM LISTING")){
					blockFrist = true;
				}
				
				if(blockFrist == true){
					if(strLine.contains(bscName)){
						if(!alarmInfo.equalsIgnoreCase("")){
							if(!categories.equalsIgnoreCase("DISTUR") 
									&& !categories.equalsIgnoreCase("NOTICE")){
								wAlarmLog.write(line+alarmInfo);
								wAlarmLog.newLine();
							} 
						}
						line = "";
						categories = "";
						alarmInfo = "";
						count = 0;
						blockAlarm = true;
					}
					
					if(strLine.contains("END OF BTS")){
						if(!categories.equalsIgnoreCase("DISTUR") 
								&& !categories.equalsIgnoreCase("NOTICE")){
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
							//vendor,network,netype
							line += vendor+","+network+","+neType+",";
							//bsc
							line += str[1].toString().trim()+","; 
							//Site
							String strSite = str[2].trim().split("-")[0]+"-"+Integer.parseInt(str[2].trim().split("-")[1])+",";
							line +=strSite; 
							//Dip
							if(strLength == 7){
								strDip = str[3].toString().trim();
								block = false;
							}else if(strLength == 6){
								block = true;
							}else{
								strDip = "";
							}
							
							//Time
							line += str[strLength-2].toString().trim()+" "+str[strLength-1].toString().trim().substring(0,8)+","; 
						}catch(Exception e){
							//logger.warn("Try line 1 : "+e);
						}
					}else if(count == 2){
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
							//Cell
							if(str.length > 2){
								line += str[str.length-1]+","; 
							}else{
								line += ","; 
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
					}
					
					if(count ==3 && block == true){
						String[] str = strLine.split(" ");
						try{
							if(str.length == 3){							
								strDip = str[1].trim()+str[2].trim();
							}else{
								strDip = "";
							}							
						}catch(Exception e){
						}
						
					}
						
					if(strLine.contains("(")){
						//DIP
						line += strDip+","; 
						String[] str = strLine.split(" ");
						//Alarm_Log_id
						try {
							line += str[1].toString().trim().substring(str[1].toString().trim().indexOf("(")+1
									,str[1].toString().trim().indexOf(")"))+",";
							
							String alarm_name  = str[2].toString().trim();
							 
							//Alarm_Name
							line += alarm_name+","; 
							String line1 ="";
							for(int i = 3; i < str.length-1; i++){
								line1 += str[i]+" "; 
							}
							line += line1 + str[str.length-1]+",";
						} catch (Exception e) {
							System.out.println(strLine);
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
