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
NAME:       IsoAlcatelAbisChainRingConverter
PURPOSE:	

REVISIONS:
Ver        Date        		Author          Description
---------  ----------  ---------------  ------------------------------------
1.0        19/09/2013       AnhNT      		1. Raw file format AlcatelAbisChainRing.csv
											2. Khi FTP de ten file tho giong dinh dang tren. 
******************************************************************************/
public class IsoAlcatelAbisChainRingConverter extends IN_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(IsoAlcatelAbisChainRingConverter.class.getName());
	private String sep = ";";
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		BufferedReader reader = null;
		BufferedWriter writerFile = null; 
		String day = "";
		String bsc = "";
		String vendor = "ALCATEL";
		String neType = "BSC";
		int count = 0;
		String line = "";
		try { 
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			writerFile = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));
			writerFile.write("#DAY;VENDOR;NE_TYPE;BSC;BTSLIST;RELATEDE1PORTNUMBER;RELATEDSECONDE1PORTNUMBER\n");			
			String strLine = ""; 
			while ((strLine = reader.readLine()) != null){
				count++;
				if(count == 1){
					String[] str = strLine.split(";");
					day = str[1]+" "+str[2];
					bsc = str[5];
					continue;
				}
				
				if(count > 2){
					String[] str = strLine.split(";");
					str[6] = str[6].substring(str[6].lastIndexOf("{")+1, str[6].indexOf("}")).trim();
					if(Integer.parseInt(str[21]) == 0){
						str[21] = "";
					}
					
					line += day+sep+vendor+sep+neType+sep+bsc+sep+str[6]+sep+str[19]+sep+str[21];
					writerFile.write(line);
					writerFile.newLine();
					line ="";
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
