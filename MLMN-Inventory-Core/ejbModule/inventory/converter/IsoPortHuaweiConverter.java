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
NAME:       IsoAbisHuaweiConverter
PURPOSE:	Convert file iso abis Huawei (data port & card)
PARAM:      file, directPath, hashtable
REVISIONS:
Ver        Date        		Author          Description
---------  ----------  ---------------  ------------------------------------
1.0        27/08/2013       AnhNT      		1. Raw file format .txt
1.1	       04/04/2014       ThangPX         2. Change header file Portcard format .txt 
******************************************************************************/
public class IsoPortHuaweiConverter extends IN_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(IsoPortHuaweiConverter.class.getName());
/*************************************************
	Fields
**************************************************/
	String sep = ";";
	
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
/*************************************************
		Variables
**************************************************/
		BufferedReader reader = null;
		BufferedWriter writerFile = null;
		boolean block = false;  
		String scan_date = "", vendor="", header = "", line = ""; 
		try { 
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath, file.getName());
			writerFile = new BufferedWriter(new FileWriter(outFile.getParent()+"/"+outFile.getName()));
			String strLine = "";
			int count = 0 ;
				
			
			while ((strLine = reader.readLine()) != null){
				line = "";
				
				if(strLine.contains("+++")){
					block = true;
					strLine = this.curTailSpace(strLine.substring(strLine.indexOf("+++")+3,strLine.length()).trim());
					String[] str = strLine.split(" ",3);
					vendor = str[0].trim(); 
					scan_date = str[1].trim();
				
				}
				// Highlight start get data
				if(block == true){
					
					count ++;
					//Write header
					if(count == 8 & header.equals(""))
					{
						header = "#VENDOR" + sep +"NE_TYPE" + sep +"SCAN_DATE" + sep;
						header += strLine.trim().replace("  ", sep); 
						header = header.replace(".", "");
						header = header.replace(" ", "_");
						header = header.toUpperCase();
						writerFile.write(header+"\n");
					 
					}
					//Writhe content
					if(count >=10 )
					{
						strLine = this.curTailSpace(strLine);
						String[] str = strLine.split(" ");
						if(IN_DateTools.isValidNumber(str[1]))
						{
							for(int i =1; i < str.length; i++)
							{
								line +=   str[i] + sep;
							}
							line = line.replace("<NULL>", "");
							line = line.substring(0,line.lastIndexOf(sep));
							line = vendor + sep + "BTS" + sep + scan_date + sep + line;
							writerFile.write(line);
							writerFile.newLine();
						}
						else 
						{
							block = false; 
							count = 0;
						}
							
					}
				}
			}  
		}catch (Exception e) {
			throw new IN_ConvertException(e.getMessage(), "Convert Error: ", e);
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
	// Remove double space
	private String curTailSpace(String strLineSrc)
	{
		while(strLineSrc.indexOf("  ") > 0){
			strLineSrc = strLineSrc.replace("  ", " ");
		}
		return strLineSrc;
	}
}
