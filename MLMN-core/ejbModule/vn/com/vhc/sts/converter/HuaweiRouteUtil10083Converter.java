package vn.com.vhc.sts.converter; 

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException; 
import java.util.Hashtable;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;



/******************************************************************************
NAME:       HuaweiRouteUtil10083Converter
PURPOSE:	Convert data route huawei

REVISIONS:
Ver        Date        		Author          Description
---------  ----------  ---------------  ------------------------------------
1.0        11/01/2014       AnhNT      		1. Ten file : MmlTaskResult 
******************************************************************************/
public class HuaweiRouteUtil10083Converter extends STS_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(HuaweiRouteUtil10083Converter.class.getName());
	
	private String sep = ";"; 
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		BufferedReader reader = null;
		BufferedWriter writerFile = null;
		
		String datetime = "", mscid = "",_AIETG = "", _USER_RATE = "";
		/*
		 * Danh dau khoi du lieu gui ve
		 * 
		 * */
		boolean block = false; 
		
		try { 
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			writerFile = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));
			writerFile.write("#DATETIME;MSCID;AIETG;USER_RATE");			
			String strLine = ""; 
			while ((strLine = reader.readLine()) != null){   
				if(strLine.trim().length() == 0){
					continue;
				}
				
				if(strLine.contains("+++")){ 
					int temp = seach(strLine);
					if(temp != -1){
						mscid = strLine.substring(temp+1, temp+8);
						datetime = strLine.substring(strLine.lastIndexOf("  ")+2).trim();
					}  
				} 
				
				if(strLine.trim().equalsIgnoreCase("AIETG Statistical Information")){
					block = true; 
					continue;
				}
				
				if(block == true){
					try {
						if(strLine.trim().contains("END")){
							block = false; 
							continue;
						}else if (strLine.contains("AIETG No.")){
							_AIETG = strLine.split("=")[1].trim();
						}else if (strLine.contains("Terminations's use rate in this AIETG")){
							_USER_RATE = strLine.split("=")[1].replace("%", "").trim();
							
							String line = datetime+sep+mscid+sep+_AIETG+sep+_USER_RATE;
							writerFile.newLine();
							writerFile.write(line);
							_AIETG="";_USER_RATE="";
						} 
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}  
		}catch (Exception e) {
			throw new STS_ConvertException(e.getMessage(), "VMS-0306", e);
		}
		finally {
			if (reader != null) {
				try {
					reader.close(); 
					writerFile.close();
				} catch (IOException e) {
					logger.warn("Close IO stream failure " + e);
				}
			}
		}  
	}  
	
	// Tim kiem ky tu danh dau su bat dau cua ten MSC
	private int seach(String strLine) {
		int count = -1;
		for ( int i = 0; i < strLine.length(); ++i ){
			char c = strLine.charAt(i);
			int j = (int) c;
			if(j == 7){
				count = i;
				break;
			} 
		} 
		return count;
	} 
}
