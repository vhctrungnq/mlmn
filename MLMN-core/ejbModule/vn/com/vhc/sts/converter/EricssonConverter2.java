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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;

/**
 * @author ThanhLV
 *
 */
public class EricssonConverter2 extends STS_BasicConverter {
	/*private String[] arrString = { "BSC", "SHMSGSERV", "PLMNSUB", "M3PERF",
			"SCTPLM", "SCTPAM", "LOAS", "UPDLOCAT", "PAGING", "HNDOVER",
			"SECHAND", "AUTHEN", "HLRSUBS", "TRAPCOM" };*/
	
	private String[] arrStringHandover = {"NICELASS","NCELLREL", "NECELLREL", "NECELASS"};
	private String[] arrStringGprs = {"CELLGPRS"} ;
	
	private static final Logger logger = Logger
			.getLogger(EricssonConverter2.class.getName());

	@Override
	public void convertFile(File file, String direcPath,
			Hashtable<Byte, String> params) throws STS_ConvertException {
		prepareParams(params);
		makeDirectory(direcPath);

		BufferedReader reader = null;
		BufferedWriter writer = null;
		int count = 0;
		try {
			reader = new BufferedReader(new FileReader(file));

			File outFile = new File(direcPath, file.getName());
			writer = new BufferedWriter(new FileWriter(outFile));

		

			String line = "";
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() == 0) {
					continue;
				}
				
					count  ++;
			
				if(count >= 2)
				{
					if(line.contains(",-,"))
					{
						line = line.replace(",-,", ",");
					}
					String[] subString = line.split(separator);
					subString[0] = ChangerFormat(subString[0].toString());
					subString[2] = getBSC(subString[2].toString());
					if (isFileHandover(file.getName())) subString[3] = subString[3].replace('-', ',');
					
				/*	if (isFileGprs(file.getName()) && subString.length == 28) {
						for (int i = 0; i < subString.length - 1; i++) {
							if(i != 1)
							{
								writer.write(subString[i] + separator);
							}
							
						}
						writer.write(subString[subString.length - 1]);
						writer.newLine();
					} else {*/
						for (int i = 0; i < subString.length - 1; i++) {
							if(i != 1)
							{
								writer.write(subString[i] + separator);
							}
						}
						writer.write(subString[subString.length - 1]);
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
	private String getBSC(String str)
	{
		String strDest = "";
		strDest = str.substring(0,str.indexOf(" "));
		return strDest;
	}
	public boolean isFileHandover(String strSource){
		for (String subString : arrStringHandover)
			if (subString.equals(getTypeName(strSource))) return true;
		return false;
	}
	public boolean isFileGprs(String strSource){
		for (String subString : arrStringGprs)
			if (subString.equals(getTypeName(strSource))) return true;
		return false;
	}
	
	public String getTypeName(String strSource) {
		
		String regex = "(.*)_(.*)_(.*)_([0-9]{8}.[0-9]{4})(.*)";
        Pattern timePattern = Pattern.compile(regex);
        Matcher m = timePattern.matcher(strSource.trim());

        String typeNode = "";
        if (m.find()) {
        	typeNode = m.group(2).trim();
        }
		return typeNode;
	}
	
	public String getNodeName(String strSource) {

		String regex = "(.*)_(.*)_(.*)_([0-9]{8}.[0-9]{4})(.*)";
		Pattern timePattern = Pattern.compile(regex);
		Matcher m = timePattern.matcher(strSource.trim());                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              

		String nameNode = "";
		if (m.find()) {
			nameNode = m.group(3).trim();
		}

		/*for (String strSub : arrString) {
			if (strSub.equals(typeNode))
				return "";
		}*/
		return nameNode + separator;
	}

	private String ChangerFormat(String strSource) {
		String strDay = null;
		String strHour = null;
		int hour = 0;
		String[] strSub = strSource.split(" ") ;
		strDay = strSub[0];
		strHour = strSub[1].substring(0, 2) ;
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(strDay));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		hour = Integer.parseInt(strHour);
		hour = hour + 6;
		if(hour > 23)
		{
			c.add(Calendar.DATE, 1); 
			hour = hour%24;
		}
			
		strDay = sdf.format(c.getTime());	
		strHour = Integer.toString(hour);
		return strDay + separator + strHour;
	}

}
