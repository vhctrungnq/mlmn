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
 * MCA_SUM_SUB_ACTIVE             =2000457
 * MCA_SUB_ACTIVE_TODAY           =5412
 * MCA_SUB_DEACTIVE_TODAY           =1321
 * MCA_SUM_FC_TODAY               =956660
 * MCA_SUM_SMS_TODAY              =157464
 * MCA_SUCCESSED_SMS_TODAY        =153890
 * MCA_FAILED_SMS_TODAY           =3574
 * MCA_SUM_SMS_ADD_QC_TODAY       =155018
 * MCA_MAX_HOUR_FC_TODAY          =77720
 * MCA_MAX_HOUR_SMS_TODAY         =11684
 * MCA_MAX_FC_PER_SECOND          =49
 * MCA_DATE_KPI                   =22/09/2009 05:30:04
 * MCA_TIME_MAX_HOUR_FC           =21/09/2009 09:00:00
 * MCA_TIME_MAX_HOUR_SMS          =21/09/2009 00:00:00
 * MCA_TIME_FC_PER_SECOND         =21/09/2009 20:14:10
 * MCA_PER_FC_PRO_SUCCESS         =97
 */

public class MCAConverter extends STS_BasicConverter {

    private static Logger logger =
        Logger.getLogger(MCAConverter.class.getName());

    public MCAConverter() {
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
            String[] columnNames = { "DATE", "KEY", "VALUE" };
            List<String> list = getFileHeaderInfo(columnNames, separator);
            for (String s : list) {
                writer.write(commentChar + s);
                writer.newLine();
            }

            String line = "";
            String[] temp = null;
            String date = getTimeFromFileName(file.getName(), "\\d{8}");
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue;
                }
                temp = line.split("=");
                if (temp.length > 1) {
                    line = date + separator + temp[0].trim();
                    line += separator + temp[1].trim();
                    writer.write(line);
                    writer.newLine();
                }
            }
            /*
            String header = "";
            String value = "";
            String[] temp = null;
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue;
                }
                temp = line.split("=");
                if (temp.length > 1) {
                    if (header.length() > 0) {
                        header += separator;
                    }
                    header += temp[0].trim();
                    if (value.length() > 0) {
                        value += separator;
                    }
                    value += temp[1].trim();
                }
            }
            writer.write(commentChar + header);
            writer.newLine();
            writer.write(value);
            */
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
