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
public class AlarmSMSConverter extends IN_BasicConverter {
	private static final Logger logger = Logger.getLogger(AlarmSMSConverter.class.getName()); 
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
			wAlarmLog.write("#PERIOD;FILE_NAME");
			
			String strLine = "";
			
			String  period = "", fileName ="" ;
			fileName = subString(file.getName(), "_", null); 
			while ((strLine = reader.readLine()) != null){
				if(strLine.contains("PERIOD=")) period = subString(strLine, "PERIOD=", ";"); 
				else if(strLine.contains("EXPORT_PERIOD\t\t\t"))  period = subString(strLine, "EXPORT_PERIOD\t\t\t", null);  
				if(!period.equals("")){
					wAlarmLog.newLine();
					wAlarmLog.write(period.trim()+sep+fileName.trim() ); 
					period="";fileName=""; 
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
				} catch (IOException e) {
					logger.warn("Close IO stream failure " + e);
				}
			}
		}
		
	}
	
	private String subString(String name, String arg0, String arg1){
		if(arg0 == null)
			name = name.substring(0, name.indexOf(arg1));
		else if(arg1 == null)
			name = name.substring(name.indexOf(arg0) + arg0.length(), name.length());
		else
			name = name.substring(name.indexOf(arg0) + arg0.length(), name.indexOf(arg1));
		return name;
	}
}
