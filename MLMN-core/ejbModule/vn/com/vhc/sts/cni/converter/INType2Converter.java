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
 * == STATS Service: vmssdpa Date: 20060808_00:00:08 ==
 * dap101 caps: 0 cpu: 0
 * dap201 caps: 4 cpu: 3
 * dap102 caps: 0 cpu: 0
 * dap202 caps: 0 cpu: 1
 * TOTAL CAPS: 4
 * == STATS Service: pfmddup Date: 20060808_00:00:08 ==
 * dap101 caps: 64 cpu: 0
 * dap201 caps: 0 cpu: 0
 * dap102 caps: 46 cpu: 0
 * dap202 caps: 0 cpu: 0
 * TOTAL CAPS: 110
 */
public class INType2Converter extends STS_BasicConverter {

    private final Pattern timePattern =
        Pattern.compile("\\d{8}_\\d\\d:\\d\\d:\\d\\d");
    private final Pattern serviceTypePattern = Pattern.compile("\\w+");
    private final Pattern valuePattern = Pattern.compile("\\d+\\.?\\d*");

    private final String IN_TYPE2_TIME_FORMAT = "yyyyMMdd_HH:mm:ss";
    private final String OUT_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

    private static Logger logger =
        Logger.getLogger(INType2Converter.class.getName());


    /**
     * @param file
     * @param direcPath
     * @param params
     * @throws STS_ConvertException
     */
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

            String line = null;
            StringBuffer buf = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue;
                }

                line = line.trim();
                //Line contain the gmtTime value
                if (line.indexOf("STATS Service") > -1) {
                    buf.append(nodeName);

                    //Read caps type
                    buf.append(getServiceTypeFromLine(line, 3));
                    buf.append(separator);

                    //Read time
                    String time = this.getTimeFromLine(line);
                    buf.append(STS_Util.getTime(time, IN_TYPE2_TIME_FORMAT,
                                            OUT_TIME_FORMAT));
                    buf.append(separator);
                }
                //Line contain the bebNum and capTotal values
                if (line.indexOf("TOTAL CAPS") > -1) {
                    //Read capTotal value
                    buf.append(getValueFromLine(line, 1));
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

    private String getServiceTypeFromLine(String line, int group) {
        Matcher m = serviceTypePattern.matcher(line.trim());
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
