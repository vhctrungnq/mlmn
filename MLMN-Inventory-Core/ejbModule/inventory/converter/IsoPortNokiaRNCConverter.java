package inventory.converter;

import inventory.cni.IN_BasicConverter;
import inventory.util.exceptions.IN_ConvertException;
import inventory.utils.IN_DateTools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.log4j.Logger;


/******************************************************************************
NAME:       IsoAbisNokiaRNCConverter
PURPOSE:	Convert file iso abis Nokia RNC (data port & card)

REVISIONS:
Ver        Date        		Author          Description
---------  ----------  ---------------  ------------------------------------
1.0        27/08/2013       AnhNT      		1. Raw file format .txt
											Dinh dang ten file tho (R_N_RNC_PORT).([0-9]{8}).(.*)
Lenh telnet lay thong tin port card Nokia: 
Lenh 1 : ZQRB:NPGEP,4:;
Lenh 2 : ZQRB:NPGEP,6:;
******************************************************************************/
public class IsoPortNokiaRNCConverter extends IN_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(IsoPortNokiaRNCConverter.class.getName());

	private String sep = ";";

	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		BufferedReader reader = null;
		BufferedWriter writerFile = null;
		//Bien "block" danh dau su bat dau cua mot khoi du lieu tra ve
		boolean block = false;  
		String scan_date = "";
		String rnc = "";
		String vendor = "";
		String ne_type = "";
		String interface_card = "";
		String interface_name = "";
		String ip_address = ""; 
		String wbtsid = "";
		String line = "";
		
		try { 
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			writerFile = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));
			writerFile.write("#SCAN_DATE;RNC;VENDOR;NE_TYPE;INTERFACE_CARD;INTERFACE_NAME;IP_ADDRESS;WBTSID\n");			
			String strLine = "";
			int count = 0;
			while ((strLine = reader.readLine()) != null){ 
				if(strLine.contains("---")){
					block = true;
					count = 0;
				}
				
				if(block == true) count++; 
				
				if(strLine.contains("RNC")){
					ne_type = "RNC";
					while(strLine.indexOf("  ") > 0){
						strLine = strLine.replace("  ", " ");
					}  
					String[] str = strLine.split(" ");
					rnc = str[1].trim(); 
					if(rnc.substring(6,7).equalsIgnoreCase("N")){
						vendor = "NOKIA SIEMENS";
					}
					scan_date = str[2].trim();
				}
				
				if(block == true){
					if(count > 1){
						strLine = strLine.trim();
						while(strLine.indexOf("  ") > 0){
							strLine = strLine.replace("  ", " ");
						}
						String[] str =strLine.split(" ");
						
						if(str.length == 7 && IN_DateTools.isValidNumber(str[3])){ 						
							interface_card = str[0]; interface_name = str[1]; ip_address = str[2];
							wbtsid = str[5]; 
							line = scan_date + sep + rnc + sep + vendor + sep + ne_type + sep + interface_card + sep + interface_name + sep 
									+ ip_address + sep + wbtsid;
							writerFile.write(line);
							writerFile.newLine();
						}
						else if(str.length == 4 && IN_DateTools.isValidNumber(str[2])){
							wbtsid = str[2];
							line = scan_date + sep + rnc + sep + vendor + sep + ne_type + sep + interface_card + sep + interface_name + sep 
									+ ip_address + sep + wbtsid;
							writerFile.write(line);
							writerFile.newLine();
						}
						else{ 
							block = false;
							System.out.println(strLine);
						} 
					}
				}
			}  
		}catch (Exception e) {
			throw new IN_ConvertException(e.getMessage(), "VMSC2-0306", e);
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
