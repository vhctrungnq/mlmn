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

//Su dung de convert file canh bao cac ater alcater drop
public class AterAluDropByHourConverter extends AL_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(AterAluDropByHourConverter.class.getName());
	 
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws AL_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);
		
		BufferedReader reader = null;
		BufferedWriter wAlarmLog = null; 	
		String bsc = "";
		String time = ""; 
		boolean block = false;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			wAlarmLog = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));
			wAlarmLog.write("#BSC;TIME;LINK_ID;C750;C751\n");
			
			String strLine = "";

			while ((strLine = reader.readLine()) != null){ 
				if(block == false){
					if(strLine.contains("Name of BSC")){
						// Doc ten bsc
						bsc = strLine.substring(strLine.indexOf(":")+1, strLine.length()).trim();
					}else if(strLine.contains("Measurement end date and time")){
						// Doc thoi gian ghi loi
						time = strLine.substring(strLine.indexOf(":")+1, strLine.length()).trim();
					}else if(strLine.contains("LINK_ID")){
						block = true;
					}
				}else{
					String[] str = strLine.split("\t");
					wAlarmLog.write(bsc+";"+time+";"+str[0].toString().trim()+";"+str[1].toString().trim()+";"+str[2].toString().trim());
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
