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
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;



/**
 * Date,User Number,Active User Number,Register Number,Cancel number,...
 * 2009-09-19,6216482,5947864,42465,5832,40276,70.98%,893,68.87%,186,20.97%,...
 * Total,-,-,42465,5832,40276,-,893,-,186,-,21763,-,1302,-,4365,-,126562,-,...
 * ......................
 * Return,
 */
public class FunringConverter extends STS_BasicConverter {

    private static Logger logger =
        Logger.getLogger(FunringConverter.class.getName());

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
            String[] columnNames = { "DATE", "KEY", "VALUE" };
            List<String> list = getFileHeaderInfo(columnNames, separator);
            for (String s : list) {
                writer.write(commentChar + s);
                writer.newLine();
            }

            //TIME FORMAT: yyyy-MM-dd HH-mm-ss
            String regex = "\\d{4}-\\d\\d-\\d\\d \\d\\d-\\d\\d-\\d\\d";
            String date = getTimeFromFileName(file.getName(), regex);
            String[] keys = null;
            String[] values = null;

            String line = "";
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue;
                }
                if (keys == null) {
                    line = line.replaceAll("\"", "");
                    keys = line.split(separator);
                    continue;
                }
                if (values == null) {
                    values = line.split(separator);
                    break;
                }
            }
            //write the content to file body
            for (int i = 0; i < keys.length; i++) {
                line = date + separator + keys[i] + separator + values[i];
                writer.write(line);
                writer.newLine();
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
