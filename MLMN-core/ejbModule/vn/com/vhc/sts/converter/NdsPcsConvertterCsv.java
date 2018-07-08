package vn.com.vhc.sts.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;



public class NdsPcsConvertterCsv extends STS_BasicConverter {

    private static Logger logger =
        Logger.getLogger(NdsPcsConvertterCsv.class.getName());

    private String sep = ",";

    public void convertFile(File file, String direcPath,
                            Hashtable<Byte, String> params) throws STS_ConvertException {
        prepareParams(params);
        makeDirectory(direcPath);

        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = "";
            String fileName = file.getName();
            File outFile = new File(direcPath,fileName);
            writer = new BufferedWriter(new FileWriter(outFile));
            
            
            

            String header = "";
            String[] arrFileName = fileName.split("-");
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue;
                }
                if (header.length() == 0) {
                    header = "Y";
                    continue;
                }
                
               line = arrFileName[1]+sep+arrFileName[2]+sep+line;
               line = getCorrectData(line);
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
                    logger.warn("Close IO stream failure " + e);
                }
            }
        }
        logger.info("Convert file: " + file.getName() + " success");
    }
    
    private String getCorrectData(String data) {
        data = data.replace("NO_VAL", "");

        return data;
    }
   
 
    
}
