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
NAME:       InventoryAlcatelConverter
PURPOSE:	Convert file inventory alcatel bts

REVISIONS:
Ver        Date        		Author          Description
---------  ----------  ---------------  ------------------------------------
1.0        23/08/2013       AnhNT      		1. Raw file format .txt
******************************************************************************/
public class InventoryAlcatelBTSConverter extends IN_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(InventoryAlcatelBTSConverter.class.getName());

	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		BufferedReader reader = null;
		BufferedWriter writerFile = null;
		String bsc = "";
		String bts = "";
		String line = ""; 
		
		try { 
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			writerFile = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));
			writerFile.write("#VENDOR;NE_TYPE;BSC;BTS;SCAN_DATE;STATUS;RACK;SUBRACK;SLOT;BOARD_NAME;SERIAL_NUMBER;PRODUCT_CODE\n");			
			String strLine = "";
			int count = 0;
			while ((strLine = reader.readLine()) != null){
				strLine = strLine.replace("?", " ").trim();
				count++;
				if(count == 1){
					String[] str = strLine.split("	");
					bsc = str[1];
					bts = str[3]; 
				}
				if(count > 13){
					try{ 
						if(strLine.substring(0,1).equals("C") == false){
							while(strLine.indexOf("  ") > 0){
								strLine = strLine.replace("  ", " ");
							} 
							String[] str = strLine.split(" "); 
							if(str[7].equals("ADDIMOD")){
								line += "ALCATEL;BTS;"+bsc+";"+bts+";"+str[1]+" "+str[2]+";"+str[3]+";"+str[4]+";"+str[5]+";"
										+str[6]+";"+str[7]+";;"+str[8]+str[9]+str[10]+str[11]; 
							}else{
								/*if(str[7].equals("--")){
									Neu thong itn broad name co gia tri
								}*/
								//str[1]+" "+str[2] = scan date and time ; str[3] = status
								//str[4] = rack ; str[5] = subrack; str[6] = slot ; str[7] = broad name
								//str[8] = serial number ; str[9]+str[10]+str[11] = product code; str[12] = ICS
								line += "ALCATEL;BTS;"+ bsc+";"+bts+";"+str[1]+" "+str[2]+";"+str[3]+";"+str[4]+";"+str[5]+";"
										+str[6]+";"+str[7]+";"+str[8].replace(" ", "")+";"+str[9]+str[10]+str[11]+str[12];
							}
							writerFile.write(line);
							writerFile.newLine();
							line = "";
						}
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
