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

// Su dung de convert file du lieu tai Nokia 2G, 3G
public class AlarmLoadNokiaConverter extends IN_BasicConverter {
	private static final Logger logger = Logger.getLogger(AlarmLoadNokiaConverter.class.getName()); 
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
			_writer.write("#DATETIME;VENDOR;NETWORK;NE_TYPE;NE;CARD_TYPE;TIME_USAGE_ALLOWED;LOAD_ALLOWED;LOAD_PERCENT;CALLS_FOR_CRRQ;CLOCK_FREQUENCY_MHZ\n");
			
			String strLine = "";
			String fileName = outFile.getName();
			String ne = fileName.substring(fileName.indexOf(".")+1, fileName.indexOf(".")+8);
			String timeFull = fileName.substring(fileName.indexOf("-")+1, fileName.indexOf("-")+16);
			String vendor = "NOKIA SEIMENS",neType="",network="";  
			
			String day = timeFull.substring(0, timeFull.indexOf("-"));
			String hour = timeFull.substring(timeFull.indexOf("-")+1,timeFull.indexOf("-")+3);
			
			if(ne.substring(0, 1).equals("B")){
				neType = "BSC";
				network = "2G";
			}else if(ne.substring(0, 1).equals("R")){
				neType = "RNC";
				network = "3G";
			}
			
			String cardType="",timeUsageAllowed = "", loadAllowed="", 
					loadPercent="", callForCrrq="",clockPrequency="";
			
			while ((strLine = reader.readLine()) != null){
				if(strLine.length() < 1){
					continue;
				}
				
				try {
					String[] str = strLine.split(":");
					String str0 = str[0].trim();
					
					if((str0.equals("UNIT") && !cardType.equals("")) || strLine.contains("COMMAND EXECUTED")){
						String line = timeFull+sep+vendor+sep+network+sep+neType+sep+ne+sep+cardType+sep+timeUsageAllowed+sep+
								loadAllowed+sep+loadPercent+sep+callForCrrq+sep+clockPrequency;
						
						_writer.write(line);
						_writer.newLine();
						
						cardType="";timeUsageAllowed = ""; loadAllowed=""; 
						loadPercent=""; callForCrrq="";clockPrequency="";
					}
					
					if(str0.equals("UNIT")){
						cardType = str[1].trim();
						continue;
					}else if(str0.equals("TIME USAGE ALLOWED")){
						timeUsageAllowed = str[1].trim();
						continue;
					}else if(str0.equals("LOAD ALLOWED")){
						loadAllowed = str[1].trim();
						continue;
					}else if(str0.equals("LOAD PERCENT")){
						loadPercent = str[1].trim();
						continue;
					}else if(str0.equals("CALLS FOR CRRQ")){
						callForCrrq = str[1].trim();
						continue;
					}else if(str0.equals("CLOCK FREQUENCY (MHZ)")){
						clockPrequency = str[1].trim();
						continue;
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
}

