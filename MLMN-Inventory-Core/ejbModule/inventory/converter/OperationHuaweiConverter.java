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
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.csvreader.CsvReader;


/**
 * 
 * @author ThangPX
 * @Purpose Convert file to get OperationLogs vendor Huawei
 * @date 26/04/2014
 *
 */

public class OperationHuaweiConverter extends IN_BasicConverter {

    private static Logger logger =
        Logger.getLogger(OperationHuaweiConverter.class.getName());
 
    private String separator = ">";
    private String vendor = "HUAWEI";
    public void convertFile(File file, String direcPath,
                            Hashtable<Byte, String> params) throws IN_ConvertException {
        prepareParams(params);
        makeDirectory(direcPath);

        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(file));

            File outFile = new File(direcPath, file.getName());
            writer = new BufferedWriter(new FileWriter(outFile));

            // Add comment for file
            List<String> list = getFileHeaderInfo();
            for (String s : list) {
                writer.write(commentChar + s);
                writer.newLine();
            }  
            String header = "";
            String line = ""; 
            CsvReader records = new CsvReader(new FileReader(file));
    		//Add header for file
			if(records.readHeaders())
				header = records.getRawRecord();
			header = replaceLine(header).trim();
			writer.write(commentChar +"Vendor"+separator+ header);
            writer.newLine();
            // 
             String[] col = header.split(separator);
             String temp = "";
			while (records.readRecord())
			{
				for(int i=0;i<col.length; i++)
				{
					 
					if(col[i].equalsIgnoreCase("MMLStartTime") || col[i].equalsIgnoreCase("MMLEndTime"))
					{
						String s = records.get(col[i]);
						temp = getTimestamp(s);
					} 
					else
						temp = records.get(col[i]);
					line += temp + separator;
				}
				 
				// perform program logic here
				 writer.write(getCorrectData(line));
	             writer.newLine();
	             line = "";
			}
	
			records.close(); 
        } catch (IOException ioe) {
            throw new IN_ConvertException(ioe.getMessage(), "VMSC2-0306", ioe);
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
        data = data.substring(0,data.length()-1);
        data = data.replace("\r\n", "");
        data = data.replace("\n", "");
        data = vendor + separator + data;
        return data;
    }
   
    private String getTimestamp(String timestamp) {
    	timestamp = timestamp.substring(0,timestamp.indexOf("+"));
		DateTime dateTime = new DateTime();
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		dateTime = formatter.parseDateTime(timestamp).plusHours(7);
		return dateTime.toString(formatter);
	}
 // Replace data header 
 	private String replaceLine(String source)
 	{

 		while(source.contains("  "))
 		{
 			source = source.replace("  ", " ");
 		} 
 		source = source.replace(",", separator)	  ;
 		source = source.replace(" "+separator, separator);
 		return source	;
 	}
}
