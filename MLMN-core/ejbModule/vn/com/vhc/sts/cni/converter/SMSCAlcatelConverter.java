package vn.com.vhc.sts.cni.converter;

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
import vn.com.vhc.sts.util.STS_Util;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;



/**
 * Convert Alcatel SMSC raw file. Example:
 * --------------------------------------------------
 *
 * Time_Stamp: 2009-10-08 00:01
 * Event   Description                Count     Daily
 * 0    Submit SM-Rcvd                 5845      5845
 * 1    Delete SM-Rcvd                    0         0
 * 2    Query SM-Rcvd                     0         0
 * 3    Status report-Sent              989       989
 * 4    SMS_DELIVER-Sent              39075     39075
 * 5    SMS_SUBMIT-Rcvd                4340      4340
 * 6    SMS_SUBMIT-Failed               471       471
 * 7    SMS_STATUS-Sent                 989       989
 * 8    RP_ERROR-Rcvd                 33844     33844
 * 9    RP_ALERT-Rcvd                   474       474
 * 10   Delivered SM                   5221      5221
 * 11   Expired SM                      116       116
 * 12   Failed SM                         0         0
 * 13   Delete SM                        42        42
 * 14   Delivery Attempts             39076     39076
 */
public class SMSCAlcatelConverter extends STS_BasicConverter {

    private final Pattern timePattern =
        Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}");
    private final Pattern valuePattern = Pattern.compile(" \\d+");

    private final String IN_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    private final String OUT_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

    private final int BLOCK_LIMIT = 18;

    private static Logger logger =
        Logger.getLogger(SMSCAlcatelConverter.class.getName());

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
            String[] columnNames =
            { "SMSCID", "TIME", "Submit SM-Rcvd", "Delete SM-Rcvd",
              "Query SM-Rcvd", "Status report-Sent", "SMS_DELIVER-Sent",
              "SMS_SUBMIT-Rcvd", "SMS_SUBMIT-Failed", "SMS_STATUS-Sent",
              "RP_ERROR-Rcvd", "RP_ALERT-Rcvd", "Delivered SM", "Expired SM",
              "Failed SM", "Delete SM", "Delivery Attempts" };
            List<String> list = getFileHeaderInfo(columnNames, separator);
            for (String line : list) {
                line = commentChar + line;
                writer.write(line);
                writer.newLine();
            }

            String line = null;
            int lineNumber = 0;
            StringBuffer buf = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue;
                }

                lineNumber++;
                line = line.trim();

                if (lineNumber == 2) {
                    buf.append(nodeName);
                    buf.append(separator);
                    String time = getTimeFromLine(line);
                    buf.append(STS_Util.getTime(time, IN_TIME_FORMAT,
                                            OUT_TIME_FORMAT));
                    buf.append(separator);
                } else if (lineNumber >= 4 && lineNumber <= BLOCK_LIMIT) {
                    buf.append(this.getValueFromLine(line, 1));
                    buf.append(separator);
                    /*
                    buf.append(this.getValueFromLine(line, 2));
                    buf.append(separator); */
                } else if (line.indexOf("------") > -1) {
                    lineNumber = 1;
                }

                if (lineNumber == BLOCK_LIMIT) {
                    writer.write(buf.toString().trim());
                    writer.newLine();
                    buf.delete(0, buf.length());
                }
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

    private String getTimeFromLine(String line) {
        Matcher m = timePattern.matcher(line.trim());
        if (m.find()) {
            return m.group().trim();
        } else {
            return null;
        }
    }

    private String getValueFromLine(String line, int group) {
        Matcher m = valuePattern.matcher(line.trim());
        for (int i = 0; i < group - 1; i++) {
            m.find();
        }

        if (m.find()) {
            return m.group().trim();
        } else {
            return "-1";
        }
    }
}
