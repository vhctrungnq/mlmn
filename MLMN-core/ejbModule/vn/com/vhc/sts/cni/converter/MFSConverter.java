package vn.com.vhc.sts.cni.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.util.STS_Util;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;



public class MFSConverter extends STS_BasicConverter {

    private static Logger logger =
        Logger.getLogger(MFSConverter.class.getName());

    public MFSConverter() {
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

            String header = "";
            String day = "";
            String hour = "";
            boolean isRead = false;

            String line = "";
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() > 0 &&
                    line.indexOf("begin date and time") >= 0) {
                    String time =
                        getTimeFromFileName(line, "\\d{4}-\\d{2}-\\d{2} \\d\\d:\\d\\d");

                    String timeFormat = "yyyy-MM-dd HH:mm";
                    day = STS_Util.getTime(time, timeFormat, "yyyy-MM-dd");
                    hour = STS_Util.getTime(time, timeFormat, "HH");
                }
                if (line.trim().length() > 0 && line.indexOf("BSS") >= 0 &&
                    line.indexOf("CI") >= 0) {
                    isRead = true;
                }
                if (isRead == true) {
                    if (line.trim().length() == 0) {
                        break;
                    }
                    //write header
                    if (header.length() == 0) {
                        header = line;
                        header =
                                "DAY" + separator + "HOUR" + separator + header;
                        List<String> list =
                            getFileHeaderInfo(header.split(separator),
                                              separator);
                        for (String s : list) {
                            writer.write(commentChar + s);
                            writer.newLine();
                        }
                        continue;
                    }
                    writer.write(day + separator + hour + separator + line);
                    writer.newLine();
                }
            }
        } catch (IOException ioe) {
            throw new STS_ConvertException(ioe.getMessage(), "VMSC2-0306", ioe);
        } finally {
            if (reader != null) {
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
