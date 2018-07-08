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



public class SGSNAlcatelConverter extends STS_BasicConverter {

    private static Logger logger =
        Logger.getLogger(SGSNAlcatelConverter.class.getName());

    public SGSNAlcatelConverter() {

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
            List<String> colNames = getFileHeaderInfo();
            for (String s : colNames) {
                writer.write(commentChar + s);
                writer.newLine();
            }

            String line = "";
            String header = "";
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue;
                }
                if (line.startsWith(commentChar)) {
                    header = line;
                    continue;
                }
                if (header.length() > 0) {
                    header = header.replaceAll(commentChar, "");
                    header = "SGSNID" + separator + header.trim();
                    writer.write(commentChar + header.toUpperCase());
                    header = "";
                }
                writer.newLine();
                line = nodeName + separator + line;
                writer.write(line);
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

    private String getSGSNId(String fileName) {
        String id = "";

        String regex = "(SGSN.*)[-](.*)(\\d{4})(.csv)";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(fileName);
        if (m.find() && m.groupCount() > 1) {
            id = m.group(1).trim();
        }
        return id;
    }

}
