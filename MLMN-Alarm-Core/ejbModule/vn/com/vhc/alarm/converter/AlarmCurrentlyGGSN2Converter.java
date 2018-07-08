package vn.com.vhc.alarm.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import vn.com.vhc.alarm.cni.AL_BasicConverter;
import vn.com.vhc.alarm.util.exceptions.AL_ConvertException;

// Su dung de convert file du lieu Active GGSN dinh dang kieu 2
public class AlarmCurrentlyGGSN2Converter extends AL_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(AlarmCurrentlyGGSN2Converter.class.getName());

	private String ggsnName = "";
	private int count = 0;
	private String strCause = "";
	private String alarmTime = "";
	private int index = 0;
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws AL_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);
		
		BufferedReader reader = null;
		BufferedWriter wAlarmLog = null; 
		boolean block = false; 
		
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			wAlarmLog = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+getFileName(outFile.getName())));
			wAlarmLog.write("#BSC;NOTIFICATION_ID;SPECIFIC_PROBLEM;ALARM_TEXT;PERCEIVED_SEVERITY;ALARM_TIME;PROBABLE_CAUSE;COUNT\n");
			
			String strLine = "";
			
			String tenfile = outFile.getName().substring(0,outFile.getName().lastIndexOf("."));
			index = Integer.parseInt(tenfile.substring(tenfile.lastIndexOf(".")+1, tenfile.length()));
 
			while ((strLine = reader.readLine()) != null){
				while(strLine.indexOf("  ")>= 0)
				{
					strLine = strLine.replaceAll("  ", " ");
				}
				
				if(strLine.contains("show fm alarms")){
					count = 0;
					block = true;
					ggsnName = strLine.substring(strLine.indexOf(".")+1,strLine.lastIndexOf("."));
				}
				
				if(block == true){
					count++;
					if(count > 	3){
						try{
							if(strLine.contains("+7:0")){
								
								String[] str = strLine.split(" ");
								
								//GGSN
								wAlarmLog.write(ggsnName + ";");
								
								//NOTIFICATION_ID
								wAlarmLog.write(str[0].toString()+";");
								
								//SPECIFIC_PROBLEM
								wAlarmLog.write(str[1].toString()+";");
								
								//ALARM_TEXT
								for(int i = 2;i<str.length-3; i++){
									wAlarmLog.write(str[i].toString()+" ");
								}
								wAlarmLog.write(str[str.length-3].toString()+";");
								
								//PERCEIVED_SEVERITY
								wAlarmLog.write(str[str.length-2].toString()+";");
								
								//ALARM_TIME
								alarmTime = str[str.length-1].substring(0,str[str.length-1].lastIndexOf("."));
								alarmTime = getTimestamp(alarmTime);
								wAlarmLog.write(alarmTime+";");
								
							}else{
								//PROBABLE_CAUSE
								strCause = strLine.toString().trim();
								if(!strCause.contains(">")){
									wAlarmLog.write(strCause+";"+index);
									wAlarmLog.newLine();
								}
							}
						}catch(Exception e){}					
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
	
	private String getTimestamp(String timestamp) {
		DateTime dateTime = new DateTime();
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd,HH:mm:ss");
		dateTime = formatter.parseDateTime(timestamp).plusHours(7);
		//dateTime = dateTime.minusMinutes(15);

		return dateTime.toString(formatter);
	}
}
