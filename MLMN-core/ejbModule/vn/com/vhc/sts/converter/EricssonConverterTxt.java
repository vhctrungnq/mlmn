package vn.com.vhc.sts.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;

/**
 * @author ThangPX
 *
 */
public class EricssonConverterTxt extends STS_BasicConverter {
	 private static final Logger logger = Logger
			.getLogger(EricssonConverterTxt.class.getName());
	 private String sep = ",";
	@Override
	public void convertFile(File file, String direcPath,
			Hashtable<Byte, String> params) throws STS_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);

		BufferedReader reader = null;
		BufferedWriter writer = null;

		try {
			reader = new BufferedReader(new FileReader(file));

			File outFile = new File(direcPath, file.getName());
			writer = new BufferedWriter(new FileWriter(outFile));
			
            String line = "";
            String strLine = "";
            boolean flag = false;
            int block = 0;
            String mscid = this.getMsc(file.getName());
            String time = this.getTime(file.getName());
			while ((strLine = reader.readLine()) != null) {
				
				if (strLine.trim().length() == 0) {
					continue;
				}
				if(strLine.contains("LAI"))
				{
					block ++;
					flag = true;
					continue;
				}
				if(strLine.contains("END"))
					flag = false;	
				if(flag == true)
					line += mscid + sep + time+ sep + block + sep + replaceLine(strLine) + "\n";
				
				
			}
			 writer.write(line);
             writer.newLine();
			
		} catch (IOException ioe) {
			throw new STS_ConvertException(ioe.getMessage(), "gnos-0306", ioe);
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

 
	public String getMsc(String strSource)
	{
		String regex = "(.*)_(.*)_(M.*)_([0-9]{4})_([0-9]{6})(.txt)";
        Pattern timePattern = Pattern.compile(regex);
        Matcher m = timePattern.matcher(strSource.trim());

        String msc = "";
        if (m.find()) {
        	msc = m.group(3).trim();
        }
		return msc;
	}
	// Replace data
		private String replaceLine(String source)
		{

			while(source.contains("  "))
			{
				source = source.replace("  ", " ");
			}
			source = source.replace("452-01-", "");
			source = source.replace(" ", sep); 
			return source	;
		} 
		// Get time
		private String getTime(String strSource) {
			String strDay = null;
			String strHour = null;
			String regex = "(.*)_(.*)_(M.*)_([0-9]{4})_([0-9]{6})(.txt)";
	        Pattern timePattern = Pattern.compile(regex);
	        Matcher m = timePattern.matcher(strSource.trim());
 
	        if (m.find()) {
	        	strHour = m.group(4).trim().substring(0,2);
	        	strDay = m.group(5).trim();
	        }
			return strDay + sep + strHour;
		}
}
