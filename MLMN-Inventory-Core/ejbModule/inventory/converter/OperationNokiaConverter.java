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
NAME:       OperationNokiaConverter
PURPOSE:	Conventer thong tin Operation logs BSC

REVISIONS:
Ver        Date        		Author          Description
---------  ----------  ---------------  ------------------------------------
1.0        26/05/2014       Thangpx      		1. Raw file format *.txt
												2. Khi telnet de ten file tho giong dinh dang tren.
												3. Cu phap lenh telnet < ZIGO:date:userid=ALL;
******************************************************************************/
public class OperationNokiaConverter extends IN_BasicConverter {
	private static final Logger logger = Logger
			.getLogger(OperationNokiaConverter.class.getName());

	String sep = ">";

	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws IN_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath); 
		
		 BufferedReader reader = null;
	     BufferedWriter writer = null;
		 
		try { 
			reader = new BufferedReader(new FileReader(file));

            File outFile = new File(direcPath, file.getName());
            writer = new BufferedWriter(new FileWriter(outFile));
			 
			String strLine = "", vendor = "NOKIA SIEMENS", line="", neType="";
			String userid="",dateTime="",command="";
			//Header
			writer.write("@Vendor>Ne Type>Ne Name>User>Start Time>Operation>");
			writer.newLine();
			neType = getNeType(file.getName());
			int count = 0;
			while ((strLine = reader.readLine()) != null){
				if(strLine.length() == 0){
					continue;
				}
				
				if(strLine.contains("SESSION"))
				{
					count =1;
					String[] lineArray = strLine.split(" ");
					for(int i=0; i<lineArray.length; i++)
					{
						if(lineArray[i].contains("USERID"))
							userid= getUser(lineArray[i]);
						if(lineArray[i].contains(":"))
						{
							if(lineArray[i].contains("+"))
								dateTime=lineArray[i-1]+" "+ getDateTime(lineArray[i],"+");
							else if(lineArray[i].contains("."))
								dateTime=lineArray[i-1]+" "+ getDateTime(lineArray[i],".");
							else 
								dateTime=lineArray[i-1]+" "+ getDateTime(lineArray[i],"");
						}
							
					}
					
				}
				if(count ==1 && !strLine.contains("SESSION"))
				{
					command = getCommand(strLine);
					count = 2;
				}
				if(!command.equals(""))
				{
					//Content
					line = vendor + sep + neType + sep + userid + sep + dateTime +sep+ command ;
					
					writer.write(line);
					writer.newLine(); 
					  userid=""; dateTime="";command="";
					line="";
				}
				
			 }
			 
			 
		}catch (Exception e) {
			throw new IN_ConvertException(e.getMessage(), "VMSC2-0306", e);
		}
		finally {
			if (reader != null) {
				try {
					reader.close(); 
					writer.close();
				} catch (IOException e) {
					logger.warn("Close IO stream failure " + e);
				}
			}
		} 
		
	}  
	private String getNeType(String strSource)
	{
		String temp = strSource.substring(strSource.indexOf(".")+1, strSource.indexOf(".")+2);
		String neType = "";
		String neName = strSource.substring(strSource.indexOf(".")+1, strSource.indexOf("-"));
		if(temp.equals("B"))
			neType = "BSC";
		if(temp.equals("R"))
			neType = "RNC";
		if(temp.equals("S"))
			neType= "SGSN";
		return neType + sep + neName;
	}
	
	private String getUser(String strSource)
	{
		String temp = strSource.substring(strSource.indexOf("USERID=")+7, strSource.length()); 
		return temp;
	}
	private String getDateTime(String strSource,String sep)
	{
		String temp = "";
		if(!sep.equals(""))
			temp = strSource.substring(0, strSource.indexOf(sep));
		else
			temp = strSource.substring(0, strSource.length());
		return temp;
	}
	private String getCommand(String strSource)
	{
		String temp = strSource.replace("/*", "");
		temp = temp.replace("*/", "");
		temp = temp.replace(" 4 ", "");
		return temp;
	}
}
