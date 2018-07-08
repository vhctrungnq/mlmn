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
public class InventoryTellabsConverter extends IN_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(InventoryTellabsConverter.class.getName());
	private String sep = ";";
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		BufferedReader reader = null;
		BufferedWriter writerFile = null; 
		boolean flag = false, module_flag=false;
		String strLine  ="", line = "";
		String vendor = "",device="", slot="",serial_number="",part_number="", module_part_number="",module_name="", module_serial_number="";
		try { 
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			writerFile = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));
			device = subStringbyKey(file.getName(),"_",".");
			vendor="TELLABS";
			String header = "#VENDOR;DEVICE;SLOT;SERIAL_NUMBER;PART_NUMBER;MODULE_NAME;MODULE_PART_NUMBER;MODULE_SERIAL_NUMBER";
			writerFile.write(header);
			writerFile.newLine(); 
			while ((strLine = reader.readLine()) != null){
				 if(strLine.contains("slot"))
					 flag = true;
				 if(flag == true)
				 {
					 if(strLine.contains("slot"))
						 slot = subStringbyKey(strLine,"slot "," is"); 
					 if(strLine.contains("serial number")&& module_flag == false)
						 serial_number= subStringbyKey(strLine,"serial number ",","); 
					 if(strLine.contains("HW type")||strLine.contains("SW type"))
						 part_number=  subStringbyKey(strLine,"HW type "," v");
					 if(strLine.contains(" of type"))
					 {
						 module_flag = true;
						 module_name= subStringbyKey(strLine,"module "," of");
						 module_part_number=  subStringbyKey(strLine,"of type "," v");
					 }
					 if(strLine.contains("serial number") && module_flag == true)
					 {
						 if(strLine.contains(","))
							 module_serial_number= subStringbyKey(strLine,"serial number ",","); 
						 else
							 module_serial_number = subStringbyKey(strLine,"serial number ",null);
					 }
					 if(strLine.contains("manufacturing date")&& module_flag == true)
					 {
						
						 line = vendor+sep+device + sep + slot +sep+ serial_number +sep+ part_number +sep+ module_name +sep+ module_part_number+sep+module_serial_number;
							
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
		try {
			if(endKey==null)
				strDest  = strLine.substring(strLine.indexOf(startKey)+startKey.length(),strLine.length());
			else
				strDest  = strLine.substring(strLine.indexOf(startKey)+startKey.length(),strLine.indexOf(endKey));
		} catch (Exception e) {
			logger.warn("Substring error "+ strLine);
		}
		
		return strDest;
	}
	
}
