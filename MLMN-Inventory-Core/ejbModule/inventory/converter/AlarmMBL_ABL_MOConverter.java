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

public class AlarmMBL_ABL_MOConverter extends IN_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(AlarmMBL_ABL_MOConverter.class.getName());

	private String bscName = "";
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);
		
		// insert record and update status in C_RAW_FILES_MLMN table
//		if (params.containsKey(IN_Setting.FILE_ID_KEY)) {
//			fileId = Integer.parseInt(params.get(IN_Setting.FILE_ID_KEY));
//		}
		
		BufferedReader reader = null;
		BufferedWriter wAlarmLog = null; 	
		//BufferedWriter wAlarmLogMo = null; 	// ghi bsc,mo dung trong qua trinh telnet ABL,MBL cell
		
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			wAlarmLog = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+getFileName(outFile.getName())));
			wAlarmLog.write("#BSC,MO,ALARM_TYPE,ALARM_INFO\n");
			
			//String fileName1 = "";
			//fileName1 = outFile.getName().substring(outFile.getName().indexOf(".")+1, outFile.getName().indexOf(".")+8);
			
			//wAlarmLogMo = new BufferedWriter(new FileWriter(outFile.getParent()+"/MO_"+fileName1+".txt")); 
			
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
				//	wAlarmLogMo.write(bscName+";");
					//bscName = strLine.substring(strLine.indexOf(";")-6,strLine.length());
				}
				
				if(strLine.contains("RXOTRX-")){
					if(strLine.contains("BLO")||strLine.contains("MBL")){
						String[] str = strLine.split(" ");
						try{
							//bsc
							wAlarmLog.write(bscName+",");
							//mo
							wAlarmLog.write(str[0].toString()+",");
							
						//	wAlarmLogMo.write(str[0].toString()+",");
							
							//alarm type
							if(str[2].toString().equals("BLO")){
								wAlarmLog.write("ABL"+",");
							}else{
								wAlarmLog.write("MBL"+",");
							}
							//alarm info
							wAlarmLog.write(strLine.toString());
							
						}catch(Exception e){
							//logger.warn("Try line 1: "+e);
						}
						wAlarmLog.newLine();
					}
				}								
			}
		}catch (Exception e) {
			throw new IN_ConvertException(e.getMessage(), "VMSC2-0306", e);
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
					wAlarmLog.close();
					//wAlarmLogMo.close();
				} catch (IOException e) {
					logger.warn("Close IO stream failure " + e);
				}
			}
		}
		//logger.info("Convert file: " + file.getName() + " success");
	}
}
