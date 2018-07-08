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
NAME:       InventoryAlcatelMFSConverter
PURPOSE:	Convert file inventory alcatel mfs

REVISIONS:
Ver        Date        		Author          Description
---------  ----------  ---------------  ------------------------------------
1.0        10/10/2013       AnhNT      		1. Raw file format .txt
											Dinh dang file tho : (mfs.*)_(amerie).(.*)
******************************************************************************/
public class InventoryAlcatelMFSTCConverter extends IN_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(InventoryAlcatelMFSTCConverter.class.getName());
	
	private String sep = ";";
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		BufferedReader reader = null;
		BufferedWriter writerFile = null; 
		String line = ""; 
		String ne ="",scan_date = "",neType = ""; 
		int count = 0;
		boolean block = false;
		try { 
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			writerFile = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));
			writerFile.write("#VENDOR;NE_TYPE;NE;SCAN_DATE;RACK;SUBRACK;SLOT;BOARD_NAME;PRODUCT_CODE;SERIAL_NUMBER\n");			
			String strLine = ""; 
			while ((strLine = reader.readLine()) != null){
				count ++;
				strLine = strLine.trim(); 
				
				if(count == 1){
					try{
						ne = strLine.split("\t")[1]; 
					}catch(Exception e){
						ne = strLine.split(";")[1]; 
					}
					continue;
				} 
				
				if(count == 2){
					try {
						scan_date = strLine.split(";")[2];
					} catch (Exception e) {
						scan_date = strLine.split(";")[1];
					} 
					continue;
				}
				
				if(count == 3){
					try {
						neType = strLine.split(";")[2];
					} catch (Exception e) {
						neType = strLine.split(";")[1];
					} 
					continue;
				}
				
				if(strLine.contains("RACK;SUBRACK;SLOT")){
					block = true;
					continue;
				}
				
				if(block == true){
					String[] str = strLine.split(";");
					try{
						if(str[5].trim().equals("") == false){
							line = "";
							line += "ALCATEL;"+neType+sep+ne+sep+scan_date+sep+str[1].trim()+sep+str[2].trim()+sep
									+str[3].trim()+sep+str[4].trim()+sep+str[5].trim()+sep+str[6].trim();
							writerFile.write(line);
							writerFile.newLine();
						}
					}catch(Exception e){
						try { 
							line = "";
							line += "ALCATEL;"+neType+sep+ne+sep+scan_date+sep+str[1].trim()+sep+str[2].trim()+sep
									+str[3].trim()+sep+str[4].trim()+sep+str[5].trim()+sep;
							writerFile.write(line);
							writerFile.newLine();
						} catch (Exception e2) {
							// TODO: handle exception
						}
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