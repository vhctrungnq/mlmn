package inventory.converter;

import inventory.cni.IN_BasicConverter;
import inventory.util.exceptions.IN_ConvertException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Hashtable;

import org.apache.log4j.Logger;

// Su dung de convert file du lieu tai ericsson 3G
public class AlarmLoadEricsson3GConverter extends IN_BasicConverter {
	private static final Logger logger = Logger.getLogger(AlarmLoadEricsson2GConverter.class.getName()); 
	DateFormat dfTime = new SimpleDateFormat("yyyyMMdd-HHmmss");
	private String sep = ";"; 
	
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		BufferedReader reader = null;
		BufferedWriter _writer = null; 	
		
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			_writer = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+getFileName(outFile.getName())));
			_writer.write("#DATETIME;VENDOR;NETWORK;NE_TYPE;NE;OBJECT;COUNTER;VALUE\n");
			
			String strLine = "";
			String fileName = outFile.getName();
			String ne = fileName.substring(fileName.indexOf(".")+1, fileName.indexOf(".")+8); 
			String vendor = "ERICSSON",neType="RNC",network="3G";  
			String timeFull = fileName.substring(fileName.indexOf("-")+1, fileName.indexOf("-")+16);
			String day = "";
			int hour = -1;
			
			int[] lengthArr = new int[10];
			boolean block = false;
			
			while ((strLine = reader.readLine()) != null){
				if(strLine.length() < 1){
					continue;
				}
				 
				try {
					if(strLine.contains("Date:")){
						day = strLine.split(":")[1].trim();
						continue;
					}
					
					if(strLine.contains("Object") && strLine.contains("Counter")){
						lengthArr = new int[2];
						lengthArr[0] = strLine.substring(0, strLine.indexOf("Counter")).length();
						lengthArr[1] = lengthArr[0] + strLine.substring(strLine.indexOf("Counter"), strLine.indexOf("Counter")+8).length();
						hour = hour7(strLine.substring(strLine.indexOf("Counter")+8).split(":")[0].trim());
						block = true;
						continue;
					} 
					
					if(block){
						if(strLine.contains("Enter the report number")){
							block = false;
							continue; 
						}
						
						String _object = strLine.substring(0,lengthArr[0]).trim();
						String _counter = strLine.substring(lengthArr[0],lengthArr[1]).trim();
						String _value = strLine.substring(lengthArr[1]).trim();
						_value = _value.replace("N/A", "");
						String line = timeFull+sep+vendor+sep+network+sep+neType+sep+ne+sep+_object+sep+_counter+sep+_value;
						_writer.write(line);
						_writer.newLine();
					} 
				} catch (Exception e) {
					// TODO: handle exception
					logger.warn("Try line: "+strLine);
				}
			}
		}catch (Exception e) {
			throw new IN_ConvertException(e.getMessage(), "VMSC2-0306", e);
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
					_writer.close();
				} catch (IOException e) {
					logger.warn("Close IO stream failure " + e);
				}
			}
		}
	}
	
	private int hour7(String hour){
		int h = Integer.parseInt(hour);
		h = (h+7)%24;
		return h;
	}
} 