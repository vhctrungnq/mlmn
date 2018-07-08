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
import java.util.List;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;
/*
 * @author: ThangPX. Create date: 01/07/2013
 * @param  Source file, DirectPath
 * @return file is converted
 * */
public class IPBBFWConverter extends STS_BasicConverter {
	private static final Logger logger = Logger.getLogger(IPBBFWConverter.class.getName());
	String header = null;
	// Convert file
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException{
	
		
	prepareParams(params);
	makeDirectory(direcPath);

	BufferedReader reader = null;
	int count = 0;
	
	
	
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
		String line = "", time="", value = "", values = "";
		
		
		String[] node = file.getName().split("_");
		String nodeName = node[4] + ";" + node[2] + node[3] + ";";
		header = "ROUTE;SCP;START_TIME;END_TIME;";
		if(node[1].contains("CPU"))
			header += "CPU_UTIL";
		if(node[1].contains("MEM"))
			header += "MEMORY";
		if(node[1].contains("SESSION"))
			header += "TOTAL_SESSION";
		
		writeFileHeader(outFile, header);
		
		
		while ((line = reader.readLine()) != null) {
			if (line.trim().length() == 0) {
				continue;
			}
			if(line.contains("<Data")&&line.contains("&#")&&!line.contains("Create")&&!line.contains("Page"))
			{
				
				String currentLine = line.substring(line.indexOf(">")+1,line.lastIndexOf("<"));
				
				if(isNumberic(currentLine.substring(2,3)))
				{
					count ++;
					currentLine = relapceString(currentLine);
					currentLine = currentLine.replace("&#", "");
					if(currentLine.contains("AM")||currentLine.contains("PM"))
					{
						 time += currentLine;
					}
					else
					{
						currentLine = currentLine.replace(",", "");
						 value += currentLine;
					}
					if(count%2==0)
					{
					    values = nodeName;
					    String[] time2=null; 
					    String day = "", strTime="",tmps2="";
					    if(time.contains("AM")||time.contains("PM"))
					    {
					    	time2 = time.split("-");
							day = time2[0].substring(0, time2[0].indexOf(" "));
							strTime = time2[0].trim();
						    tmps2 =  covertToDate(strTime) + ";" +  covertToDate(day + time2[1]) ;
					    }
						
						
						
						values += tmps2+";"+ value;
						writeLine(outFile, values);
						time = "";
						value = "";
						values="";
					}
					
				}
					
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
		String strDest= "";
		String[] charNew = str.split("&#");
		for(int i=1;i<charNew.length;i++)
		{
			int hc = Integer.parseInt(charNew[i].substring(0,2));
			char str2 =  (char)(hc);
			charNew[i]= charNew[i].replace(charNew[i].substring(0, 2), String.valueOf(str2));
			strDest += charNew[i];
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
	private void writeFileHeader(File outFile, String header) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
		List<String> list = getFileHeaderInfo();
		for (String s : list) {
			writer.write(commentChar + s);
			writer.newLine();
		}
		if (header != null && header.length() > 0) {
			writer.write(commentChar + " ORIGINAL HEADER:");
			writer.newLine();
			writer.write(commentChar + "---------------------------------------------");
			writer.newLine();
			writer.write(commentChar + header);
			writer.newLine();
			
		}
		writer.close();
	}
}
