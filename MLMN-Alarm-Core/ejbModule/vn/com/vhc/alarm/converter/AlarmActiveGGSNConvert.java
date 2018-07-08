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
import vn.com.vhc.alarm.utils.AL_DateTools;

// Su dung de convert file du lieu Active GGSN
public class AlarmActiveGGSNConvert extends AL_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(AlarmActiveGGSNConvert.class.getName());

	private String bscName = "";
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws AL_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		BufferedReader reader = null;
		BufferedWriter wAlarmLog = null; 
		String count = "";
		
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			wAlarmLog = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+getFileName(outFile.getName())));
			wAlarmLog.write("#BSC;NOTIFICATION_ID;SPECIFIC_PROBLEM;ALARM_TEXT;PROBABLE_CAUSE;EVENT_TYPE_NAME;ALARM_TIME;PERCEIVED_SEVERITY;ACKNOWLEDGED;ACKNOWLEDGED_TIME;ACKNOWLEDGED_USER_ID;CLEARED;MANAGED_OBJECT_ID;APPLICATION_ID;IDENTIFYING_APP_INFO;COUNT\n");
			
			String tenfile = outFile.getName().substring(0,outFile.getName().lastIndexOf("."));
			count = tenfile.substring(tenfile.lastIndexOf(".")+1, tenfile.length()); 
			
			String strLine = "";
		
			while ((strLine = reader.readLine()) != null){
				strLine = strLine.replaceAll(";", ".");
				strLine = strLine.replace("|", ";");
				if(strLine.contains("show alarm active")){
					// Doc bsc
					bscName = strLine.substring(strLine.indexOf("[")+1,strLine.indexOf("]"));
				}
				
				String[] str = strLine.split(";");
				String str1 = str[0].toString().trim();
				if(AL_DateTools.isValidNumber(str1)){
					wAlarmLog.write(bscName);
					for(int i=0;i< str.length-1;i++){
						if(str[8].toString().trim().equalsIgnoreCase("0")){
							str[8]="";
						}
						wAlarmLog.write(";"+str[i].toString().trim());
					}
					wAlarmLog.write(";"+count);
					wAlarmLog.newLine();
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
