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



public class WAPConverter extends STS_BasicConverter {

    private static Logger logger =
        Logger.getLogger(WAPConverter.class.getName());

    private final Pattern timePattern = Pattern.compile("\\d{2}:\\d{2}");

    private final String IN_TIME_FORMAT = "yyyyMMdd HH:mm";
    private final String OUT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public WAPConverter() {
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

            String time = "";
            String date = "";
            String hour_minute = "";

            String line = "";
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue;
                }
                if (line.startsWith("TIME")) {
                    writer.write(commentChar + line);
                    writer.newLine();
                    continue;
                }

                Matcher m = timePattern.matcher(line);
                if (m.find()) {
                    hour_minute = m.group().trim();

                    date = getTimeFromFileName(file.getName(), "\\d{8}");
                    date = date + " " + hour_minute;
                    time = STS_Util.getTime(date, IN_TIME_FORMAT, OUT_TIME_FORMAT);

                    line = line.replaceAll(hour_minute, time);
                }
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException ioe) {
            throw new STS_ConvertException(ioe.getMessage(), "VMSC2-0306", ioe);
        } finally {
            if (reader != null && writer != null) {
                try {
                    reader.close();
                    writer.close();
                } catch (IOException e) {
                    logger.warn("Close IO stream failure");
                }
            }
        }

        logger.info("Convert file: " + file.getName() + " success");
    }
}
