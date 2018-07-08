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
public class EricssonConverterCSV extends STS_BasicConverter {
	private String[] arrStringOBJ = { "RNCSTAT", "DEFMGW1", "LOCAREAST", "LOSSROUTE", "MAPSIGIWRK", "MTRAFTYPE", "NBRMSCGUH", "NBRMSCLST", "NBRMSCUGHO", "TRUNKROUTE"};
	
	private final Pattern valuePattern = Pattern.compile("\"[^\"]*\"");
	private static final Logger logger = Logger
			.getLogger(EricssonConverterCSV.class.getName());

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
			 // Append header to file
            List<String> list = getFileHeaderInfo();
            for (String s : list) {
                writer.write(commentChar + s);
                writer.newLine();
            }

            String header = "";
            String line = "";
            String[] strHeader;
            String dayAndHour = "";
            String bc = "BC";
            String[] strLine;

			while ((line = reader.readLine()) != null) {
				
				if (line.trim().length() == 0) {
					continue;
				}
				 if (header.length() == 0) {
					 header = line;
					 if(isFileTwoID(file.getName()))
					 {
						 strHeader = header.split(",",5);
						 dayAndHour = strHeader[0].replace("TIME", "DAY"+separator+"HOUR");
						 
						 header = dayAndHour + separator + strHeader[1] + separator + strHeader[2] + separator + strHeader[3] + separator + bc + separator + strHeader[4];
					 }
					 else
					 {
						 strHeader = header.split(",",5);
						 dayAndHour = strHeader[0].replace("TIME", "DAY"+separator+"HOUR");
						 bc= strHeader[3].replace("OBJ", bc);
						 header = dayAndHour + separator + strHeader[1] + separator + strHeader[2]+ separator + bc + separator + strHeader[4]; 
					 }
	                   
	                  	writer.write(commentChar + header.toUpperCase());
	                    writer.newLine();
	                    continue;
	                }
				 String strTime = "";
				 if(isFileTwoID(file.getName()))
				 {	
		
					 strLine = line.split(",",5);
					 strTime = this.ChangerFormat(strLine[0]);
					 
					 line =  strTime + separator +strLine[1] + separator +strLine[2] + separator +strLine[3] + separator + this.getBC(file.getName()) + separator +strLine[4];
				 }
				 else{
					 strLine = line.split(",",5);
					 strTime = this.ChangerFormat(strLine[0]);
					 String strBC = strLine[3].replace("-", this.getBC(file.getName()));
					 line =  strTime + separator +strLine[1] + separator +strLine[2] + separator + strBC +  separator + strLine[4];
				 }
				 writer.write(getCorrectData(line));
	             writer.newLine();
				}
			
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


	public boolean isFileTwoID(String strSource)
	{
		for (String subString : arrStringOBJ)
			if (subString.equals(getTypeName(strSource))) return true;
		return false;
	}
	public String getBC(String strSource)
	{
		String regex = "(.*)_(.*)_(M.*)_(.*)_([0-9]{8}.[0-9]{4})(.*)";
        Pattern timePattern = Pattern.compile(regex);
        Matcher m = timePattern.matcher(strSource.trim());

        String bc = "";
        if (m.find()) {
        	bc = m.group(4).trim();
        }
		return bc;
	}
	public String getTypeName(String strSource) {
		
		String regex = "(.*)_(.*)_(M.*)_(.*)_([0-9]{8}.[0-9]{4})(.*)";
        Pattern timePattern = Pattern.compile(regex);
        Matcher m = timePattern.matcher(strSource.trim());

        String typeNode = "";
        if (m.find()) {
        	typeNode = m.group(2).trim();
        }
		return typeNode;
	}
    private String getCorrectData(String data) {
        //"([^"]+,[^"]*)"
        String s = "";
        Matcher m = valuePattern.matcher(data);
        while (m.find()) {
            s = m.group();
            s = s.replace("\"", "");
            s = s.replace(separator, "");
            data = data.replace(m.group(), s);
        }

        return data;
    }
    private String ChangerFormat(String strSource) {
		String strDay = null;
		String strHour = null;
		int hour = 0;
		String[] strSub = strSource.split(" ");
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
