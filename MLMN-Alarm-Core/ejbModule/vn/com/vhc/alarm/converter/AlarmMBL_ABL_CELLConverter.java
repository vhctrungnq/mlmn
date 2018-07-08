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

public class AlarmMBL_ABL_CELLConverter extends AL_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(AlarmMBL_ABL_CELLConverter.class.getName());

	//private int fileId = -1;
	private String bscName = "";
	private String moName = "";
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
			wAlarmLog.write("#TYPE,BSC,MO,CELL\n");
			
			String strLine = "";
			
			while ((strLine = reader.readLine()) != null){
				while(strLine.indexOf("  ")>= 0)
				{
					strLine = strLine.replaceAll("  ", " ");
				}
				
				strLine = strLine.replaceAll(",", ".");
				if(strLine.contains("eaw")){
					//bsc
					String[] str = strLine.split(" ");
					bscName = str[str.length-1].toString();
					//bscName = strLine.substring(strLine.indexOf(";")-6,strLine.length());
				}
				
				if(strLine.contains("<RXMOP:MO")){
					//mo
					moName = strLine.substring(strLine.indexOf("=")+1,strLine.indexOf(";"));
					//System.out.println("MO = "+str);
					block = true;
				}		
				
				if(block == true){
					try{
						if(strLine.contains(moName) && !strLine.contains(";")){
							String[] str = strLine.split(" ");
							//cell
							String cell = str[1].toString();
							wAlarmLog.write("ALARM CLEARING,"+bscName+","+moName+","+cell);
							wAlarmLog.newLine();
						}
					}catch(Exception e){
						
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
