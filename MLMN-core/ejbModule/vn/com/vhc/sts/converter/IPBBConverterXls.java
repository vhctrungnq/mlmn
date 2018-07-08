package vn.com.vhc.sts.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;
/*
 * @author: ThangPX. Create date: 10/08/2013
 * @param  Source file, DirectPath: File XML format Excel
 * @return file is converted
 * */
public class IPBBConverterXls extends STS_BasicConverter {
	private static final Logger logger = Logger.getLogger(IPBBConverterXls.class.getName());
	String header = null;
	// Convert file
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException{
	
		
	prepareParams(params);
	makeDirectory(direcPath);

	BufferedReader reader = null;
	
	
	
	try {
		reader = new BufferedReader(new FileReader(file));

		File outFile = new File(direcPath, file.getName());
		if (outFile.exists()) {
			outFile.delete();
		}
		
		
		// line: line reading
		// time: Time of line
		// value: value of line
		// values: time + value
		String line = "",  value = "";
		boolean flag = false;
		
		String node = "";
		
		while ((line = reader.readLine()) != null) {
			if (line.trim().length() == 0) {
				continue;
			}
			if(line.contains("AM")||line.contains("PM"))
			{
				flag = true;
			}
			
			if(flag == true){
				if(line.contains("</Data"))
				{
					
					String currentLine = line.substring(line.lastIndexOf("\"String\">")+9,line.lastIndexOf("</Data"));
					if(currentLine.equals(""))
					{
						currentLine = "&#48.&#48";
					}
					if(currentLine.length() > 3)
					{
						
						if(isNumberic(currentLine.substring(2, 3))==true)
						{
							currentLine = relapceString(currentLine);
							
							if(value.equals(""))
								value = node;
							currentLine = currentLine.replace("&#", "");
						    String[] time2=null; 
						    String day = "", strTime="",tmps2="";
						    if(currentLine.contains("AM")||currentLine.contains("PM"))
						    {
						    	if(!currentLine.contains("©"))
						    	{
						    		time2 = currentLine.split("-");
									day = time2[0].substring(0, time2[0].indexOf(" "));
									strTime = time2[0].trim();
								    tmps2 =  covertToDate(strTime) + ";" +  covertToDate(day + time2[1]) ;
								    currentLine = tmps2;
						    	}
						    }
						    else
						    {
						    	currentLine = currentLine.replace(",", "");
						    }
						    if(!currentLine.equals(""))
						    	value += ";" + currentLine;
						}
						else
						{
							if(currentLine.contains("Sensor"))
							{
								currentLine = relapceString(currentLine);
								currentLine = currentLine.replace("&#", "");
								node = currentLine;
							}
						}
					}
				}
			}
			if(line.contains("/Row"))
			{
				String[]s = value.split(":");
				if(s.length > 2)
				{
					if(!value.contains("©"))
						writeLine(outFile, value);
						value = "";
				}
				
				flag = false;
			}
				
			
			
		}
		logger.info("Convert file: " + file.getName() + " success");
	} catch (IOException ioe) {
		throw new STS_ConvertException(ioe.getMessage(), "GNOS-0306", ioe);
	} finally {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				logger.warn("Close IO stream failure " + e);
			}
		}
	}
	

	}
	// convert Ascii -> numberic
	private String relapceString(String str)
	{
		str = str.replace("&#10", ";");
		String[] charNew = str.split("&#");
		String strDest= charNew[0];
		for(int i=1;i<charNew.length;i++)
		{
			if(!charNew[i].contains("10"))
			{
				int hc = Integer.parseInt(charNew[i].substring(0,2));
				char str2 =  (char)(hc);
				charNew[i]= charNew[i].replace(charNew[i].substring(0, 2), String.valueOf(str2));
				strDest += charNew[i];
			}
			
		}
		return strDest;
	}
	// Check Number type
	private boolean isNumberic(String str) {
		try {

			Double.parseDouble(str);
			return true;
		} catch (Exception e) {
			return false;
		}

	}
	// Check date Type
	private Date isValidDate(String pattern, String date) {
		DateFormat df = new SimpleDateFormat(pattern);
		Date newDate ;
		try {
			newDate = df.parse(date);
		} catch (Exception ex) {
			newDate = null;
		}
		return newDate;
	}
	// Convert datetime
	private String covertToDate(String soureDate)
	{
		String newDate = "";
		if (this.isValidDate("MM/dd/yyyy hh:mm aa", soureDate)!= null)
			newDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(this.isValidDate("MM/dd/yyyy hh:mm aa", soureDate));
		if (this.isValidDate("MM/dd/yyyy hh:mm:ss aa", soureDate)!= null)
			newDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(this.isValidDate("MM/dd/yyyy hh:mm:ss aa", soureDate));
		return newDate;
	}
	// Insert to database
	private void writeLine(File outFile, String line) throws IOException {
		// write data
		if (line != null && line.length() > 0) {
			BufferedWriter writer = new BufferedWriter(new FileWriter(outFile, true));
			writer.append(line);
			writer.newLine();
			writer.flush();
			writer.close();
		}
	}
	
}
