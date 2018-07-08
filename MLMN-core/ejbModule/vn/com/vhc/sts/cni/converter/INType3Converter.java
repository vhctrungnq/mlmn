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
 * IN2      , VALID , 27819
 * IN2      , ACTIF , 346574
 * IN2      , INACT , 115112
 * IN2      , DEACT , 24
 * IN3      , VALID , 245163
 * IN3      , ACTIF , 408898
 * IN3      , INACT , 238741
 * IN3      , DEACT , 608
 * IN4-SDP1 , VALID , 130686
 * IN4-SDP1 , ACTIF , 366339
 * IN4-SDP1 , INACT , 220243
 * IN4-SDP1 , DEACT , 699
 * IN4-SDP2 , VALID , 135231
 * IN4-SDP2 , ACTIF , 433419
 * IN4-SDP2 , INACT , 188923
 * IN4-SDP2 , DEACT , 505
 * IN4-SDP3 , VALID , 296220
 * IN4-SDP3 , ACTIF , 292764
 * IN4-SDP3 , INACT , 207013
 * IN4-SDP3 , DEACT , 1186
 * IN6-SDP1 , VALID , 179350
 * IN6-SDP1 , ACTIF , 538
 * IN6-SDP1 , INACT , 8
 * IN6-SDP2 , VALID , 9138
 * IN6-SDP2 , ACTIF , 76277
 * IN6-SDP2 , INACT , 34624
 * IN6-SDP2 , DEACT , 114
 * IN6-SDP3 , VALID , 14524
 * IN6-SDP3 , ACTIF , 165729
 * IN6-SDP3 , INACT , 57177
 * IN6-SDP3 , DEACT , 205
 */
public class INType3Converter extends STS_BasicConverter {

    private final String IN_TYPE3_TIME_PATTERN = "yyyyMMdd";
    private final String DATE_FORMAT = "dd/MM/yyyy";

    private static Logger logger =
        Logger.getLogger(INType3Converter.class.getName());


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

            String date =
                STS_Util.getTime(this.getDateOfFile(file.getName()), IN_TYPE3_TIME_PATTERN,
                             DATE_FORMAT);
            String line = null;
            StringBuffer buf = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue;
                }

                line = line.trim();
                String[] strs = line.split(",");
                buf.append(strs[0].trim());
                buf.append(nodeName);
                buf.append(separator);
                buf.append(date);
                buf.append(separator);

                for (int i = 1; i < strs.length; i++) {
                    buf.append(strs[i].trim());
                    buf.append(separator);
                }

                writer.write(buf.toString().trim());
                writer.newLine();
                buf.delete(0, buf.length());
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

    private String getDateOfFile(String fileName) {
        Pattern p = Pattern.compile("\\d{8}");
        Matcher m = p.matcher(fileName);
        if (m.find()) {
            return m.group();
        } else {
            return null;
        }
    }

}
