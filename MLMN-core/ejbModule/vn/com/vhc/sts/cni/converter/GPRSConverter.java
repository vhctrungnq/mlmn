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
 * # P-MSC observation file V2.0
 * # Node:               Not Implemented yet
 * # Filename:           ##FILEPATH##
 * .............................................
 * 2006/04/13 00:15:00;Y;2452;2;31;14804;.......
 * 2006/04/13 00:30:00;Y;2369;6;40;14205;.......
 * 2006/04/13 00:45:00;Y;2325;5;31;15022;.......
 * .............................................
 */
public class GPRSConverter extends STS_BasicConverter {

    private static Logger logger =
        Logger.getLogger(GPRSConverter.class.getName());

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
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || line.trim().length() == 0) {
                    continue;
                }

                line = line.replaceAll(";", separator);
                writer.write(nodeName);
                writer.write(separator);
                writer.write(line.trim());
                writer.newLine();
            }
        } catch (IOException ioe) {
            throw new STS_ConvertException(ioe.getMessage(), "VMSC2-0306", ioe);
        } finally {
            if (reader != null && writer != null) {
                try {
                    reader.close();
                    writer.close();
                } catch (Exception e) {
                    logger.warn("Close IO stream failure " + e);
                }
            }
        }
        logger.info("Convert file: " + file.getName() + " success");
    }
}
