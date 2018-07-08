package vn.com.vhc.sts.cni.converter;

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



public class HuaweiConverter extends STS_BasicConverter {

    private static Logger logger =
        Logger.getLogger(HuaweiConverter.class.getName());

    private final Pattern valuePattern = Pattern.compile("\"[^\"]*\"");

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
            int count = 0;
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue;
                }
                if (header.length() == 0) {
                    header = line;
                    header = header.replace("\"", "");
                    writer.write(commentChar + header.toUpperCase());
                    writer.newLine();
                    count = 1;
                    continue;
                }
                //continue when read at the second row
                if (count == 1) {
                    count = 0;
                    continue;
                }
              //  String[] splitLine = line.split(",",2);
               // line = updateTime(splitLine[0])+ "," + splitLine[1];
                writer.write(getCorrectData(line));
                writer.newLine();
            }
        } catch (IOException ioe) {
            throw new STS_ConvertException(ioe.getMessage(), "VMSC2-0306", ioe);
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
    private String updateTime(String pTime)
    {
    	String vTime = "";
    	String strDay = null;
		String strHour = null;
		String[] time ; 
		int hour = 0;
		String[] strSub = pTime.split(" ");
		strDay = strSub[0];
		time = strSub[1].split(":");
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(strDay));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		hour = Integer.parseInt(time[0].toString());
		hour = hour + 7;
		if(hour > 23)
		{
			c.add(Calendar.DATE, 1); 
			hour = hour%24;
		}
		strDay = sdf.format(c.getTime());	
		if (hour <10)
			strHour = "0"+Integer.toString(hour);
		else
			strHour = Integer.toString(hour);
		
		vTime = strDay +" "+ strHour + ":" + time[1]; 
    	return vTime;
    }
}
