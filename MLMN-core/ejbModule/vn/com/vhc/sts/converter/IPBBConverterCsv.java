package vn.com.vhc.sts.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;



public class IPBBConverterCsv extends STS_BasicConverter {

    private static Logger logger =
        Logger.getLogger(IPBBConverterCsv.class.getName());

    private final Pattern valuePattern = Pattern.compile("\"[^\"]*\"");
    private String sep = ",";

    public void convertFile(File file, String direcPath,
                            Hashtable<Byte, String> params) throws STS_ConvertException {
        prepareParams(params);
        makeDirectory(direcPath);

        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = "";
            String fileName = file.getName();
            File outFile = new File(direcPath,fileName);
            writer = new BufferedWriter(new FileWriter(outFile));
            
            
            // Append header to file
            List<String> list = getFileHeaderInfo();
            for (String s : list) {
                writer.write(commentChar + s);
                writer.newLine();
            }

            String header = "";
            String strFileName = fileName.substring(fileName.indexOf("_")+1, fileName.lastIndexOf("_"));
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue;
                }
                if (header.length() == 0) {
                    header = "LINKID"+sep+line;
                    header = header.replace("\"", "");
                    writer.write(commentChar + header.toUpperCase());
                    writer.newLine();
                    continue;
                }
                
               // line = updateTime(line);
               if(line.contains("Averages"))
            	   break;
               line = strFileName+sep+line;
               line = getCorrectData(line);
               line = updateTime(line);
                writer.write(line);
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
            s = s.replace(" MByte", "");
            s = s.replace("#", "");
            s = s.replace(" msec", "");
            s = s.replace(" %", ""); 
            data = data.replace(m.group(), s);
        }

        return data;
    }
    //Split date time: 12/15/2013 11:55:00 PM - 12:00:00 AM-> 12/15/2013 11:55:00
    private String updateTime(String pLine)
    {
    	String[] s = pLine.split(sep,4);
    	String sDateTime = s[2].substring(0,s[2].indexOf(" - "));
    	String date = s[0]+sep+s[1]+sep+sDateTime+sep+s[3];
    	return date;
    }
 
    
}
