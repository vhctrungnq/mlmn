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
 * Time,Prbt Node,Direction,Call Attempt,Call Attempt Succ Rate,SS7 Used Rate,E1 Used Rate
 * 2009-09-19 00:00:00,Cantho,1,55182,98.013,0.9104,0.0271
 * 2009-09-19 00:00:00,Cantho,2,13845,99.613,0.0100,0.0148
 * 2009-09-19 00:00:00,Cantho,3,11748,99.401,0.9083,0.0127
 * 2009-09-19 00:00:00,Danang,0,299178,99.533,0.2389,0.0576
 * 2009-09-19 00:00:00,Danang,1,16737,99.083,0.1229,0.0235
 * ................................
 */

public class Funring2Converter extends STS_BasicConverter {

    private static Logger logger =
        Logger.getLogger(Funring2Converter.class.getName());

    private final Pattern valuePattern =
        Pattern.compile("([0-9]{4}[-/][0-9]{2}[-/][0-9]{2})(.*)");

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

            String line = "";
            boolean isHeader = false;
            Matcher m = null;
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue;
                }
                if (isHeader == false) {
                    // Append header to file
                    List<String> list = getFileHeaderInfo();
                    for (String s : list) {
                        writer.write(commentChar + s);
                        writer.newLine();
                    }
                    writer.write(commentChar + line.toUpperCase());
                    isHeader = true;
                    continue;
                }

                m = valuePattern.matcher(line);
                if (m.find()) {
                    writer.newLine();
                    writer.write(line);
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
