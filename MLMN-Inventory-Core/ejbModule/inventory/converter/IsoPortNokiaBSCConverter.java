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
NAME:       IsoPortNokiaBSCConverter
PURPOSE:	Conventer thong tin port&card Nokia BSC

REVISIONS:
Ver        Date        		Author          Description
---------  ----------  ---------------  ------------------------------------
1.0        19/09/2013       AnhNT      		1. Raw file format (R_N_BSC_PORT).([0-9]{8})
											2. Khi telnet de ten file tho giong dinh dang tren.
											3. Cu phap lenh telnet < ZEEI:;
******************************************************************************/
public class IsoPortNokiaBSCConverter extends IN_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(IsoPortNokiaBSCConverter.class.getName());

	String sep = ";";

	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		BufferedReader reader = null;
		BufferedWriter writerFile = null;
		boolean block = false;
		int count = 0;
		String bsc = "";
		String cell = "";
		String day = "";
		String line = ""; 
		String vendor = "NOKIA SIEMENS";
		String neType = "BSC";
		try { 
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			writerFile = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));
			writerFile.write("#DAY;VENDOR;NE_TYPE;NE;CELL;TRX;ADM_STA;OP_STA;FREQ;FRT;BSC_PORT\n");			
			String strLine = "";
			while ((strLine = reader.readLine()) != null){
				if(strLine.length() == 0){
					continue;
				}
				
				if(strLine.contains("FlexiBSC")){
					while (strLine.indexOf("  ") > 0) {
						strLine = strLine.replace("  ", " ");
					}
					
					String[] str = strLine.split(" ");
					bsc = str[1];
					day = str[2]+" "+str[3];
					continue;
				}
				
				if(strLine.contains("BCF-")){
					block = true;
					continue;
				}
				
				if(block == true){
					strLine = strLine.trim();
					try{
						if(IN_DateTools.isValidNumber(strLine.substring(0, strLine.indexOf(" ")))){
							count = 0;
							continue;
						}
					}catch(Exception e){}
					
					count ++;
					if(count == 1){
						cell = strLine.substring(0, strLine.indexOf(" "));
						continue;
					}
					
					if(strLine.contains("TRX-")){
						while (strLine.indexOf("  ") > 0) {
							strLine = strLine.replace("  ", " ");
						}
						
						String[] str =  strLine.split(" ");
						if(str[5].equals("-")){
							str[5] = "";
						} 
						line += day+sep+vendor+sep+neType+sep+bsc+sep+cell+sep+str[0]+sep+str[1]+sep+str[2]+sep+str[3]+sep+str[4]+sep+str[5];
						writerFile.write(line);
						writerFile.newLine();
						line = "";
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
