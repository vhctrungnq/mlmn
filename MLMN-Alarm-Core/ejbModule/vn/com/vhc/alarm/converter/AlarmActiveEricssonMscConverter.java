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

// Su dung de convert file du lieu Active Ericsson MSC
public class AlarmActiveEricssonMscConverter extends AL_BasicConverter {
	private static final Logger logger = Logger.getLogger(AlarmActiveErissonConverter.class.getName());

	private String _phancach = ",";
	private String alarmName = "", alarmClass = "", alarmLogId = "", time = "", alarmInfo = ""; 
	
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
			wAlarmLog.write("#VENDOR,NETWORK,NE_TYPE,ALARM_CLASS,NE,ALARM_LOG_ID,SDATE,ALARM_NAME,ALARM_INFO\n");
			
			boolean blockAlarm = false;//Danh dau bat dau khoi Alarm
			boolean block = false;
			boolean block1 = false;
			
			String strLine = ""; 
			int count = 0; 
			String vendor = "ERICSSON",network = "CS_CORE",neType = "MSC"; 
			String ne = outFile.getName().substring(outFile.getName().indexOf(".")+1, outFile.getName().indexOf(".")+8);
			String neTemp = ne.substring(0, ne.length()-1);
			
			while ((strLine = reader.readLine()) != null){
				if(strLine.length() < 1){
					continue;
				}
				strLine = strLine.toString().trim();
				while(strLine.indexOf("  ")>= 0)
				{
					strLine = strLine.replaceAll("  ", " ");
				}
				strLine = strLine.replaceAll(_phancach, ".");  
				
				if(strLine.contains("ALARM LIST")){
					block = true;
				}

				if(block == true){ 
					if(strLine.contains(neTemp) && strLine.split(" ").length > 4){
						if(!alarmInfo.equalsIgnoreCase("")){ 
							wAlarmLog.write(vendor+","+network+","+neType+","+alarmClass+","+ne+","+
												alarmLogId+","+time+","+alarmName+","+alarmInfo);
							wAlarmLog.newLine();
						} 
						refresh();
						count = 0; 
						blockAlarm = true;
					}		
					if(strLine.equalsIgnoreCase("END")){
						if(!alarmInfo.equalsIgnoreCase("")){ 
							wAlarmLog.write(vendor+","+network+","+neType+","+alarmClass+","+ne+","+
												alarmLogId+","+time+","+alarmName+","+alarmInfo);
							wAlarmLog.newLine();
						}
						refresh();
						blockAlarm = false;
					}
				}
				 
				if(blockAlarm == true){		
					count = count + 1;
					if(count > 0){
						if(strLine.length() > 1)
							alarmInfo = alarmInfo+"<->"+strLine.replace("\t", " ");
					}
					if(count == 1){
						try{
							String[] str = strLine.split(" ");
							alarmClass = str[0].substring(0,str[0].indexOf("/"));
							alarmLogId = str[str.length - 3].toString().trim();
							time = str[str.length-2]+" "+str[str.length-1];// yyMMdd HHmm
						}catch(Exception e){}
					}else if(count == 2){
						if(strLine.equals("BLADE")){
							block1 = true;
						}else{
							block1 = false;
						}
					}
					
					if(block1){
						if(count == 4){
							alarmName = strLine;
						}
					}else{
						if(count == 3){
							alarmName = strLine;
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
	
	private void refresh(){
		alarmName = "";
		alarmClass = "";
		alarmLogId = "";
		time = "";
		alarmInfo = "";
	}
}
