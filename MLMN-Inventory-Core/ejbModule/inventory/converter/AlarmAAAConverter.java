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

// Su dung de convert file du lieu Alarm AAA
public class AlarmAAAConverter extends IN_BasicConverter {
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
			
			String day="",csiName="",status="",alarmId="",alarmInfo="",csiType="AAA";
			
			while ((strLine = reader.readLine()) != null){
				day = strLine.substring(0, 15).trim();//yyyyMMdd.HH24miss
				alarmInfo = strLine.substring(22, strLine.length()).trim();
				String temp = "";
				
				if(strLine.contains("CLEAR:")){
					status = "CLEAR";
					temp = alarmInfo.substring(0, alarmInfo.indexOf("CLEAR:")); 
				}else if(strLine.contains("RAISE:")){
					status = "RAISE"; 
					temp = alarmInfo.substring(0, alarmInfo.indexOf("RAISE:")).trim();
				}else if(strLine.contains("ERROR:")){
					status = "ERROR"; 
					temp = alarmInfo.substring(0, alarmInfo.indexOf("ERROR:")).trim();
				}else if(strLine.contains("WARNING:")){
					status = "WARNING"; 
					temp = alarmInfo.substring(0, alarmInfo.indexOf("WARNING:")).trim();
				}
				
				try {
					csiName = temp.substring(0, temp.lastIndexOf(":")).trim();
					alarmId = temp.substring(temp.lastIndexOf(":")+1,temp.length()).trim();
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(strLine);
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
