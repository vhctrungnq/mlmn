package vn.com.vhc.sts.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;
/**
 * 
 * @author ThangPX
 * @return: converted File  LicenseStatistics_cmd1.txt
 *
 */
public class CmdLicenseConverter extends STS_BasicConverter {


	private static Logger logger = Logger.getLogger(CmdLicenseConverter.class.getName());
	String sep=";";
	
	@Override
	public void convertFile(File file, String direcPath, Hashtable<Byte, String> params) throws STS_ConvertException {
		
		prepareParams(params);					//set params: separator, commentChar, nodeName
		makeDirectory(direcPath);	
		BufferedReader reader = null;
		BufferedWriter writer = null;
		String filename = file.getName();
		try {
			reader = new BufferedReader(new FileReader(file));
			File outFile = new File(direcPath,filename );
			writer = new BufferedWriter(new FileWriter(outFile));
			String line = ""; 
			
			String object = filename.substring(filename.lastIndexOf("_")+1,filename.indexOf(".log"));
			List<String> arrayLine = new ArrayList<String>();
			while ((line = reader.readLine()) != null) {
				  
				 arrayLine.add(line);
				
			}
			if(arrayLine.size() > 2)
			{
				for(int i =arrayLine.size()-2; i< arrayLine.size();i++)
				{
					 writer.write(object + sep + getCorrectData(arrayLine.get(i)));
		             writer.newLine();
				}
			}
			
			
		} catch (IOException ioe) {
			throw new STS_ConvertException(ioe.getMessage(), "GNOS-0306", ioe);
		} finally {
			if (reader != null) {
				try {
					reader.close();
					writer.close();
				} catch (IOException e) {
					logger.warn("Close IO stream failure " + e);
				}
			}
		}
		logger.info("Convert file: " + file.getName() + " success");
		
	}
	private String getCorrectData(String line)
	{
		String destLine = "";
		String dateTime = line.substring(0,line.indexOf(" "));
		String type = subStrings(line, "Licence counter: ", " - LIMIT");
		String licence = subStrings(line, "LIMIT: ", " - USED:");
		String used = subStringEnd(line, "USED: ",line.length());
		destLine = dateTime + sep + type+ sep  + licence+ sep  + used ;
		return destLine;
	}
	private String subStrings(String line, String startChar, String endChar)
	{
		String temp = line.substring(line.indexOf(startChar)+startChar.length(),line.indexOf(endChar));
		return temp;
	}
	private String subStringEnd(String line, String startChar, int length)
	{
		String temp = line.substring(line.indexOf(startChar)+startChar.length(),length);
		return temp;
	} 
}
