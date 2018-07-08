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

public class AlarmCurrentlySGSNConverter extends AL_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(AlarmCurrentlySGSNConverter.class.getName());

	private String bscName = "";
	private int count = 0;
	private String strData = "";
	private int index;

	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws AL_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);
		
		BufferedReader reader = null;
		BufferedWriter wAlarmLog = null; 
		boolean block = false;
		boolean blockAlarm = false;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			wAlarmLog = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+getFileName(outFile.getName())));
			wAlarmLog.write("#BSC;DIP;SDATE;ALARM_CLASS;ALARM_LOG_ID;ALARM_NAME;SOLOGAN;ALARM_INFO;COUNT\n");
			
			String strLine = "";  
			String tenfile = outFile.getName().substring(0,outFile.getName().lastIndexOf("."));
			index = Integer.parseInt(tenfile.substring(tenfile.lastIndexOf(".")+1, tenfile.length())); 
		
			while ((strLine = reader.readLine()) != null){
				while(strLine.indexOf("  ")>= 0)
				{
					strLine = strLine.replaceAll("  ", " ");
				}
				strLine = strLine.replaceAll(";", ".");
				if(strLine.contains("SGSN")){
					String[] str = strLine.split(" ");
					//Lay ten SGSN
					bscName = str[1].toString().trim();
				}else if(strLine.toString().trim().equals("ALARMS CURRENTLY ON")){
					// Bat dau khoi du lieu tra ve
					block = true;
				}
				
				if(block == true){
					if(strLine.contains(bscName)){
						if(!strData.equalsIgnoreCase("")){
							wAlarmLog.write(strData+";"+index);// ghi alarm info
							wAlarmLog.newLine();
						}
						strData = "";
						count = 0;
						blockAlarm = true;
					}
					if(strLine.contains("END OF ALARMS")){
						wAlarmLog.write(strData+";"+index); 
						blockAlarm = false;
					}				
				}
				
				if(blockAlarm == true){
					count++;
					if(count > 0){
						strData = strData+"<->"+strLine.toString().trim();
					}
					if(count == 1){
						try{
							String[] str = strLine.split(" ");
							//bsc
							wAlarmLog.write(bscName+";");
							//dip
							wAlarmLog.write(str[2].toString().trim()+";"); 
							String sdate = str[4].toString().trim()+" "+str[5].toString().trim().substring(0,8);
							
							wAlarmLog.write(sdate+";");
						}catch(Exception e){ 
						}
					}else if(count == 2){
						try{
							String[] str = strLine.split(" ");
							if(str[0].equals("***")){
								wAlarmLog.write("A1;");
							}else if(str[0].equals("**")){
								wAlarmLog.write("A2;");
							}else if(str[0].equals("*")){
								wAlarmLog.write("A3;");
							}else{
								wAlarmLog.write(";");
							}
						}catch(Exception e){
							//logger.warn("Try line 2 : "+e);
						}
					}else if(count == 3){
						try{
							String[] str = strLine.split(" ");
							String alarmLogId = str[1].substring(str[1].indexOf("(")+1,str[1].indexOf(")"));
							wAlarmLog.write(alarmLogId+";");
							String alarmName = str[2].toString().trim();
							wAlarmLog.write(alarmName+";");
							//sologan
							for(int i = 3; i < str.length-1; i++){
								wAlarmLog.write(str[i]+" ");
							}
							wAlarmLog.write(str[str.length-1]+";");
							
						}catch(Exception e){
							//logger.warn("Try line 3 : "+e);
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
