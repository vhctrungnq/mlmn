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
NAME:       IsoPortRNCSGSNConverter
PURPOSE:	Conventer thong tin port&card RNC-SGSN

REVISIONS:
Ver        Date        		Author          Description
---------  ----------  ---------------  ------------------------------------
1.0        12/09/2013       AnhNT      		1. Raw file format (R_RNC_SGSN_PORT).([0-9]{8})
											2. Khi telnet de ten file tho giong dinh dang tren.
											3. Cu phap lenh telnet < ZE6I:;
******************************************************************************/
public class IsoPortSGSNRNCConverter extends IN_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(IsoPortSGSNRNCConverter.class.getName());

	String sep = ";";

	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		BufferedReader reader = null;
		BufferedWriter writerFile = null;
		String sgsn = "";
		String day = "";
		String line = ""; 
		String temp = "";
		try { 
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			writerFile = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));
			writerFile.write("#DAY;SGSN;VENDOR;NE_TYPE;RNC;PAPU\n");			
			String strLine = "";
			while ((strLine = reader.readLine()) != null){
				try{
					temp = strLine.substring(0, 4);
				}catch(Exception e){}
				if(temp.equals("SGSN")){
					while (strLine.indexOf("   ") > 0) {
						strLine = strLine.replace("   ", "  ");
					}
					String[] str = strLine.split("  ");
					sgsn = str[1].trim();
					day = str[2].trim()+ " "+str[3].trim(); 
					continue;
				} 
				if(strLine.contains("RNC NAME")){
					String[] str = strLine.split(":");
					String vendor = str[1].substring(str[1].length()- 1, str[1].length());
					if(vendor.equals("A")){
						vendor = "ALCATEL";
					}else if(vendor.equals("E")){
						vendor = "ERICSSON";
					}else if(vendor.equals("H")){
						vendor = "HUAWEI";
					}else{
						vendor = "NOKIA SIEMENS";
					}
					
					line += day+sep+sgsn+sep+vendor+sep+"SGSN"+sep+str[1].trim()+sep;
				}
				if(strLine.contains("PAPU UNIT INDEX")){
					String[] str = strLine.split(":");
					line += str[1].trim();
					writerFile.write(line);
					writerFile.newLine();
					line = "";
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
