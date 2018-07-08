/*package vn.com.vhc.alarm.util.telnet;
 import java.io.BufferedReader; 
import java.io.File;
import java.io.FileReader; 
import java.io.IOException; 
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
 

*//**
 * @author AnhNT
 *
 *//*
public class Main  {  
	
	File file = new File("/home/thanhlv/oracle/u02/alarm/rawfile/download/");
	
	String destinationPath = "/home/thanhlv/oracle/u02/alarm/rawfile/converter/"; 
	
	private static final Logger logger = Logger
			.getLogger(Main.class.getName());

	public void convertFile(File file, String direcPath,
			Hashtable<Byte, String> params) {  
		
		BufferedReader reader = null; 
		try {
			reader = new BufferedReader(new FileReader(file));
		
			String line = "";
			while ((line = reader.readLine()) != null) {  
				if (isFileHandover(file.getName())) 
				{
					String[] strLine = line.split(";");
					String bscName = strLine[0].toString().trim();
					
				}
			}
		} catch (IOException ioe) { 
		} finally {
			if (reader != null) {
				try {
					reader.close(); 
				} catch (IOException e) {
					logger.warn("Close IO stream failure " + e);
				}
			}
		} 
	} 
	
	public boolean getTypeName(String strSource) {
		
		String regex = "(MO)_(B.*)(.*)";
        Pattern timePattern = Pattern.compile(regex);
        Matcher m = timePattern.matcher(strSource.trim());
        
        if (m.find()) {
        	return true;
        }
		return false;
	}  
}
*/