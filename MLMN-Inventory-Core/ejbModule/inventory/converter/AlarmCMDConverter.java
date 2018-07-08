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

// Su dung de convert file du lieu Alarm CMD
public class AlarmCMDConverter extends IN_BasicConverter {
	private static final Logger logger = Logger.getLogger(AlarmCMDConverter.class.getName()); 
	private String sep = ";";
	
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		BufferedReader reader = null;
		BufferedWriter wAlarmLog = null; 	
		
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			wAlarmLog = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));
			wAlarmLog.write("#DAY;CSI_TYPE;CSI_NAME;STATUS;ALARM_ID;ALARM_INFO");
			
			String strLine = "";
			
			String day="",csiName="",status="",alarmId="",alarmInfo="",csiType="CMD";
			csiName = file.getName().substring(file.getName().lastIndexOf("_")+1, file.getName().lastIndexOf("."));
			
			while ((strLine = reader.readLine()) != null){
				if(strLine.contains("Alarm cleared")){
					status = "Alarm cleared";
					day = strLine.substring(0,19).trim();
					alarmInfo = strLine.substring(strLine.indexOf("Alarm cleared"), strLine.length()).trim();
					alarmId = strLine.substring(strLine.lastIndexOf(":")+1, strLine.length()).trim(); 
				}else if(strLine.contains("Alarm sent")){
					status = "Alarm sent";
					day = strLine.substring(0,19).trim();
					alarmInfo = strLine.substring(strLine.indexOf("Alarm sent"), strLine.length()).trim();
					alarmId = alarmInfo.split(":")[1].trim();
				}
				
				String line = day+sep+csiType+sep+csiName+sep+status+sep+alarmId+sep+alarmInfo;
				wAlarmLog.newLine();
				wAlarmLog.write(line); 
				day="";status="";alarmId="";alarmInfo="";
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
}
