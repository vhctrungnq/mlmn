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
NAME:       HuaweiRouteUtil10082Converter
PURPOSE:	Convert data route huawei

REVISIONS:
Ver        Date        		Author          Description
---------  ----------  ---------------  ------------------------------------
1.0        11/01/2014       AnhNT      		1. Ten file : MmlTaskResult 
******************************************************************************/
public class HuaweiRouteUtil10082Converter extends STS_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(HuaweiRouteUtil10082Converter.class.getName());
	
	private String sep = ";"; 
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		BufferedReader reader = null;
		BufferedWriter writerFile = null;
		
		String datetime = "", mscid = "";
		/*
		 * Danh dau khoi du lieu gui ve
		 * 
		 * */
		boolean block = false;
		
		int count = 0;
		int[] lengthArr = new int[5];
		
		try { 
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			writerFile = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));
			writerFile.write("#DATETIME;MSCID;ROUTE_NAME;AIETG;AIETG_NAME;DPC;NI;BSC;ACPOOL");			
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
				
				if(strLine.trim().equalsIgnoreCase("AIETG Configuration")){
					block = true;
					count = 0;
					continue;
				}
				
				if(block == true){
					count ++;
					try {
						if(count == 2){
							lengthArr = new int[5];
							strLine = strLine.toUpperCase(); 
							lengthArr[0] = strLine.substring(0, strLine.indexOf("AIETG NAME")).length();
							lengthArr[1] = lengthArr[0] + strLine.substring(strLine.indexOf("AIETG NAME"), strLine.indexOf("DPC")).length();
							lengthArr[2] = lengthArr[1] + strLine.substring(strLine.indexOf("DPC"), strLine.indexOf("NI")).length();
							lengthArr[3] = lengthArr[2] + strLine.substring(strLine.indexOf("NI"), strLine.indexOf("BSC")).length();
							lengthArr[4] = lengthArr[3] + strLine.substring(strLine.indexOf("BSC"), strLine.indexOf("ACPOOL")).length();
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					try {
						if(count >= 3){
							if(strLine.trim().contains("Number of results")){
								block = false;
								count = 0;
								continue;
							}else{
								String _AIETG = strLine.substring(0, lengthArr[0]).trim();
								String _AIETG_NAME = strLine.substring(lengthArr[0], lengthArr[1]).trim();
								String _DPC = strLine.substring(lengthArr[1], lengthArr[2]).trim();
								String _NI = strLine.substring(lengthArr[2], lengthArr[3]).trim();
								String _BSC = strLine.substring(lengthArr[3], lengthArr[4]).trim();
								String _ACPOOL = strLine.substring(lengthArr[4]).trim();
								String _ROUTE_NAME = _AIETG_NAME+"_"+mscid; 
								
								String line = datetime+sep+mscid+sep+_ROUTE_NAME+sep+_AIETG+sep+_AIETG_NAME+sep+
										_DPC+sep+_NI+sep+_BSC+sep+_ACPOOL;
								
								writerFile.newLine();
								writerFile.write(line);
							}
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
