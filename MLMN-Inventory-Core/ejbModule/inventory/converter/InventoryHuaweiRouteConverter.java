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



/******************************************************************************
NAME:       InventoryTellabsHuaweiConverter
PURPOSE:	

REVISIONS:
Ver        Date        		Author          Description
---------  ----------  ---------------  ------------------------------------
1.0        24/07/2014       ThangPX      		1. Raw file format *.txt
												2. Khi FTP de ten file tho giong dinh dang tren. 
******************************************************************************/
public class InventoryHuaweiRouteConverter extends IN_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(InventoryHuaweiRouteConverter.class.getName());
	private String sep = ";";
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		BufferedReader reader = null;
		BufferedWriter writerFile = null; 
		boolean flag = false, module_flag=false;
		String strLine  ="", line = "";
		String vendor = "",device="", slot="",serial_number="",part_number="", module_name="";
		try { 
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			writerFile = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));
			vendor = "HUAWEI";
			String header = "#VENDOR;DEVICE;SLOT;SERIAL_NUMBER;PART_NUMBER;MODULE_NAME";
			writerFile.write(header);
			writerFile.newLine(); 
		
			while ((strLine = reader.readLine()) != null){
				if(strLine.contains("_Board"))
					module_name = subStringbyKey(strLine, "[", "]");
				 if(strLine.contains("<") && strLine.contains(">"))
					 device = subStringbyKey(strLine, "<", ">");
				 if(strLine.contains("Slot"))
					 flag = true;
				 if(flag == true)
				 {
					 if(strLine.contains("Slot"))
						 slot = subStringbyKey(strLine,"[","]");  
					 if(strLine.contains("BoardType"))
					 {
						 part_number=  subStringtoLength(strLine,"BoardType=");
						 module_flag = true;
					 }
						 
					 if(strLine.contains("BarCode="))
					 { 
						 serial_number=  subStringtoLength(strLine,"BarCode=");
					 } 
					 if(strLine.contains("BOM=")&& module_flag == true)
					 {
						
						 line = vendor+sep+device + sep + slot +sep+ serial_number +sep+ part_number+sep+module_name ;
							
							writerFile.write(line);
							writerFile.newLine(); 
							line ="";
							module_flag= false;
					 } 
					
				 }
				  
					 
				 	 
					
				} 
		}catch (Exception e) {
			throw new IN_ConvertException(e.getMessage(), "ERROR-0306", e);
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
	private String subStringbyKey(String strLine, String startKey, String endKey)
	{
		String strDest = "";
		strDest  = strLine.substring(strLine.indexOf(startKey)+startKey.length(),strLine.indexOf(endKey));
		return strDest;
	}
	private String subStringtoLength(String strLine, String startKey)
	{
		String strDest = "";
		strDest  = strLine.substring(strLine.indexOf(startKey)+startKey.length(),strLine.length());
		return strDest;
	}
}
