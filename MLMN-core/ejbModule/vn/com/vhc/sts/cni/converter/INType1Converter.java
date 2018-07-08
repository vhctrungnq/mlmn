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
 * 30012006
 * Application copies at 00:00:00 system=9% p0=2% p1=8% p2=8% p3=7%
 * -----------------------------------------------------------------------------
 * |pPF| service|  DB  |ApId| PID |%CPU |MEM(M)|    START   |CAPS|  CALLS |OPEN|
 * -----------------------------------------------------------------------------
 * |110|ppsvnm33|S05G05| 12 |24792|  3.3| 27.57|Jul 18 21:30| 2.7| 9070471|  50|
 * .............................................................................
 * |220|ppsvnm33|S05G05|  9 |31523|  3.3| 30.05|Jul 18 21:30| 2.7| 9070983|  39|
 * |--TOTAL------------------------ 24.1-300.41------------  21.4-210249193- 284
 */
public class INType1Converter extends STS_BasicConverter {

    private final Pattern timePattern =
        Pattern.compile("\\d\\d:\\d\\d:\\d\\d"); //hh:mm:ss
    private final Pattern valuePattern = Pattern.compile("\\d+\\.?\\d*");
    private final Pattern datePattern = Pattern.compile("\\d{8}"); //ddMMyyyy

    private final String IN_TYPE1_TIME_FORMAT = "ddMMyyyyHH:mm:ss";
    private final String OUT_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

    private static Logger logger =
        Logger.getLogger(INType1Converter.class.getName());

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
            String time = null;
            int lineNumber = 0;
            StringBuffer buf = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue;
                }

                lineNumber++;
                line = line.trim();

                if (lineNumber == 1) {
                    buf.append(nodeName);
                    time = this.getDateOfLine(line);
                } else if (lineNumber == 2) {
                    time = time + getTimeFromLine(line);
                    buf.append(STS_Util.getTime(time, IN_TYPE1_TIME_FORMAT,
                                            OUT_TIME_FORMAT));
                    buf.append(separator);
                } else if (line.indexOf("TOTAL--") > -1) {
                    buf.append(getValueFromLine(line, 3));
                    lineNumber = 0;
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
        Matcher m = valuePattern.matcher(line);
        for (int i = 0; i < group - 1; i++) {
            m.find();
        }

        if (m.find()) {
            return m.group().trim();
        } else {
            return "-1";
        }
    }

    private String getDateOfLine(String line) {
        Matcher m = datePattern.matcher(line);
        if (m.find()) {
            return m.group();
        } else {
            return null;
        }
    }
}
