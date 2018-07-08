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
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;



/**
 * HN_FDA_Performance_Statistics_Rerport: from 2009-09-19 00 to 2009-09-19 01
 * Site                          : VMS_HN_FDA
 * --------------------------------------------------------------------------------------
 * 001 succSubmitMsToMs : 106137 (the success value of MO from MS to MS)
 * 002 failSubmitMsToMs : 1762 (the fail value of MO from MS to MS)
 * 003 succSubmitMsToEsme : 4392 (the success value of MO from MS to ESME)
 * 004 failSubmitMsToEsme : 3 (the fail value of MO from MS to ESME)
 * 005 moSuccRatio : 98.43% (MO success Ratio:the value = [001+003]/[001+002+003+004]*100%)
 * 006 succSubmitEsmeToMs : 11550 (the success value of AO from ESME to MS)
 * 007 failureSubmitEsmeToMs : 0 (the fail value of AO from ESME to MS)
 * 008 succSubmitEsmeToEsme : 0 (the success value of AO from ESME to ESME)
 * 009 failureSubmitEsmeToEsme : 0 (the fail value of AO from ESME to ESME)
 * .................................................
 * */

public class SMSCZTEConverter extends STS_BasicConverter {

    private static Logger logger =
        Logger.getLogger(SMSCZTEConverter.class.getName());

    private final Pattern indexPattern = Pattern.compile("[0-9]{3} ");

    public SMSCZTEConverter() {
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
            for (String s : list) {
                writer.write(commentChar + s);
                writer.newLine();
            }

            String fullTime = getTimeFromFileName(file.getName(), "[0-9]{10}");
            String time = fullTime.substring(0, fullTime.length() - 2);
            String hour = fullTime.substring(time.length(), fullTime.length());
            String site = "";

            String header =
                "SMSCID" + separator + "TIME" + separator + "HOUR" +
                separator + "SITE";
            String value = nodeName + separator + time + separator + hour;
            String line = "";
            String[] temp = null;
            Matcher m = null;
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue;
                }
                if (line.startsWith("Site")) {
                    site =
line.substring(line.indexOf(":") + 1, line.length());
                    value += separator + site.trim();
                }

                m = indexPattern.matcher(line);
                if (m.find()) {
                    temp = line.split(" ");
                    if (temp.length > 3) {
                        if (header.length() > 0) {
                            header += separator;
                        }
                        header += temp[1].trim();

                        if (value.length() > 0) {
                            value += separator;
                        }
                        value += temp[3].trim();
                    }
                }
            }

            writer.write(commentChar + header.toUpperCase());
            writer.newLine();
            writer.write(value);
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
