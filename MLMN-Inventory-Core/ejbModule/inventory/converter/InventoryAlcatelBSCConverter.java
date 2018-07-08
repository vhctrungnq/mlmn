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
NAME:       InventoryAlcatelneConvert
PURPOSE:	Convert file inventory alcatel ne

REVISIONS:
Ver        Date        		Author          Description
---------  ----------  ---------------  ------------------------------------
1.0        23/08/2013       AnhNT      		1. Raw file format .csv
******************************************************************************/
public class InventoryAlcatelBSCConverter extends IN_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(InventoryAlcatelBSCConverter.class.getName());

	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		BufferedReader reader = null;
		BufferedWriter writerFile = null;
		String scanDate = "";
		String ne = "";
		String line = ""; 
		
		try { 
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			writerFile = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));
			writerFile.write("#VENDOR;NE_TYPE;NE;SCAN_DATE;RACK;SUBRACK;SLOT;BOARD_NAME;SERIAL_NUMBER;PRODUCT_CODE;FIRMWARE_VERSION\n");			
			String strLine = "";
			int count = 0;
			while ((strLine = reader.readLine()) != null){
				count++;
				if(count == 1){ 
					ne = strLine.substring(strLine.indexOf(";")+1, strLine.length()-1);
				}
				if(count == 2){
					scanDate = strLine.substring(strLine.indexOf(";")+1, strLine.length()-1);
				}
				if(count > 6){
					try{  
						String[] str = strLine.split(";");
						//str[1] = rack; str[2] = subrack; str[3] = slot; str[4] = broad name
						//str[5] = product code; str[6] = serial number; str[7] = firmware version
						line += "ALCATEL;BSC;"+ne+";"+scanDate+";"+str[1].trim()+";"+str[2].trim()+";"+str[3].trim()+";"
								+str[4].trim()+";"+str[6].trim().replace(" ", "")+";"+str[5].trim().replace(" ", "")+";"+str[7].trim();
						
						writerFile.write(line);
						writerFile.newLine();
						line = "";
					}catch(Exception e){ 
					}
				}
			}  
		}catch (Exception e) {
			throw new IN_ConvertException(e.getMessage(), "VMS-0306", e);
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
}
