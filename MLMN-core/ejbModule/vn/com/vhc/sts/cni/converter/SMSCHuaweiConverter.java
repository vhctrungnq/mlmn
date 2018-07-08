package vn.com.vhc.sts.cni.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;



/**
 *  Index,Time,MoTotal,MoSuccess,MtTotal,MtSuccess,TryTotal
 *  1,00:00,25919,22800,22959,21889,31681
 *  2,00:10,22279,19299,19522,18574,26522
 *  3,00:20,19233,16205,16469,15609,23227
 *  4,00:30,16200,13333,13522,12739,19937
 *  5,00:40,13882,11066,11387,10621,17571
 *  6,00:50,12041,9273,9476,8877,14910
 *  7,01:00,10504,7822,8074,7442,13830
 * */

public class SMSCHuaweiConverter extends STS_BasicConverter {

    private final Pattern timePattern = Pattern.compile("\\d{2}:\\d{2}");

    private final String IN_TIME_FORMAT = "yyyyMMdd HH:mm";
    private final String OUT_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";

    private static Logger logger =
        Logger.getLogger(SMSCHuaweiConverter.class.getName());

    public SMSCHuaweiConverter() {
    }

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
            for (String line : list) {
                line = commentChar + line;
                writer.write(line);
                writer.newLine();
            }

            String line = "";
            int index = -1;
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue;
                }
                //create file header
                if (line.indexOf("Index") >= 0) {
                    line = line.replace("Index", "SMSCID");
                    line = commentChar + line;
                    writer.write(line.toUpperCase());
                    continue;
                }
                //remove data in INDEX column
                index = line.indexOf(separator);
                if (index >= 0) {
                    line = line.substring(index + 1, line.length());
                }

                Matcher m = timePattern.matcher(line);
                if (m.find()) {
                    String time = m.group().trim();
                    String date = this.getFullTime(time, file.getName());
                    line = line.replaceAll(time, date);
                }

                line = nodeName + separator + line;
                writer.newLine();
                writer.write(line);
            }
        } catch (IOException ioe) {
            throw new STS_ConvertException(ioe.getMessage(), "VMSC2-0306", ioe);
        } finally {
            if (reader != null && writer != null) {
                try {
                    reader.close();
                    writer.close();
                } catch (Exception e) {
                    logger.warn("Close IO stream failure");
                }
            }
        }

        logger.info("Convert file: " + file.getName() + " success");
    }

    private String getFullTime(String time, String fileName) {
        String d = "";
        int indexOf = fileName.lastIndexOf(".");
        if (indexOf >= 0) {
            d = fileName.substring(indexOf + 1, fileName.length());
        }
        d = d + " " + time;

        SimpleDateFormat format = new SimpleDateFormat(IN_TIME_FORMAT);
        try {
            Date date = format.parse(d);
            format.applyPattern(OUT_TIME_FORMAT);
            d = format.format(date);
        } catch (ParseException e) {
        }

        return d;
    }

}
