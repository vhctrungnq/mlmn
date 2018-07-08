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
NAME:       InventoryAlcatelFormatNewConverter
PURPOSE:	Convert file inventory alcatel mfs

REVISIONS:
Ver        Date        		Author          Description
---------  ----------  ---------------  ------------------------------------
1.0        10/10/2013       AnhNT      		1. Raw file format .txt
											Dinh dang file tho : (mfs.*)_(amerie).(.*)
******************************************************************************/
public class InventoryAlcatelFormatNewConverter extends IN_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(InventoryAlcatelFormatNewConverter.class.getName());
	
	private String sep = ";";
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		BufferedReader reader = null;
		BufferedWriter writerFile = null; 
		String line = ""; 
		String ne ="",neParent = "",scan_date = "", scan_time = ""; 
		int count = 0; 
		boolean block = false;
		try { 
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			writerFile = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));
			writerFile.write("#VENDOR;NE_TYPE;NEID;NE;SCAN_DATE;SCAN_TIME;RACK;SUBRACK;SLOT;BOARD_NAME;PRODUCT_CODE;SERIAL_NUMBER;NE_PARENT\n");			
			String strLine = ""; 
			while ((strLine = reader.readLine()) != null){
				line = "";
				count++;
				if(count == 2){
					if(strLine.contains(";")){
						block = true;
						continue;
					}  
					if(strLine.contains(",")){
						block = false;
						continue;
					}
				}
				
				if(count > 2){
					int j = 0;
					String[] str = null;
					if(block){
						str = strLine.split(";");
					}else{
						str = strLine.split(",");
					}
					
					//Thong tin inventory MFS
					try {
						if(str[0].equals("MFS")){
							line += "ALCATEL;";
							for(int i=0; i < 11; i++){
								j = i;
								line += str[i].trim().replace(" ", "")+sep; 
							} 
							writerFile.write(line.replace("-", ""));
							writerFile.newLine(); 
							continue;
						}
					}catch (Exception e) {
						// TODO: handle exception
						for(int k=j; k < 11; k++){ 
							line += ";";
						}
						writerFile.write(line); 
						writerFile.newLine();
						continue;
					}
					
					//Thong tin inventory TC 
					try {
						if(str[0].equals("TC")){  
							line +=  "ALCATEL"+sep+str[0]+sep+""+sep; 
							for(int i=1; i< 10; i++){
								j = i;
								line += str[i].trim().replace(" ", "")+sep;
							}  
							writerFile.write(line);
							writerFile.newLine(); 
							continue;
						}
					} catch (Exception e) {
						// TODO: handle exception
						for(int k=j; k < 10; k++){ 
							line += ";";
						}  
						writerFile.write(line); 
						writerFile.newLine();
					}
					
					//Thong tin inventory BTS
					try {
						if(str[0].equals("BTS")){    
							String[] str1 = str[2].split("/");
							neParent = str1[0].trim();
							ne = str1[1].trim();
							neParent = neParent.substring(neParent.indexOf(" ")+1, neParent.length());
							ne = ne.substring(ne.indexOf(" ")+1, ne.length());
							scan_date = str[3].trim();
							scan_time = str[4].trim();
							
							line +=  "ALCATEL"+sep+str[0]+sep+str[1]+sep+ne+sep+scan_date+sep+scan_time+sep;
							
							for(int i=7; i< 11; i++){
								line += str[i].trim()+sep;
							}  
							
							line = line+str[12].trim().replace(" ", "")+sep+str[11].trim().replace("?", "")+sep+neParent;
							writerFile.write(line.replace("-", ""));
							writerFile.newLine(); 
							continue;
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					//Thong tin inventory BSC
					try {
						if(str[0].equals("BSC")){
							line += "ALCATEL;";
							for(int i = 0; i < 11; i++){
								j = i;
								line += str[i].trim().replace(" ", "")+sep;
							}
							writerFile.write(line.replace("-", ""));
							writerFile.newLine(); 
							continue;
						} 
					} catch (Exception e) {
						// TODO: handle exception
						for(int k=j; k < 11; k++){ 
							line += ";";
						}  
						writerFile.write(line.replace("-", "")); 
						writerFile.newLine();
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