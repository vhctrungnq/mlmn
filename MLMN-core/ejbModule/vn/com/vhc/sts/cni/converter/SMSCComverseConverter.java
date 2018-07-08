package vn.com.vhc.sts.cni.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.core.STS_Global;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.STS_Util;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;



/**
 * SYSTAT sfe1b R
 * STEIMAP.1:3 1.0 C 172.17.105.204:53433 N/A
 * EIDLGS 1
 * SEITOT 160:25302307 1000:0 10000:0 32768:0 63
 * STEIMAP.1:2 1.0 C 172.17.105.203:41643 N/A
 * EIDLGS 2
 * SEITOT 160:12595081 1000:0 10000:0 32768:0 64
 * STEIMAP.1:1 1.0 C 172.17.105.203:41587 N/A
 * EIDLGS 2
 * SEITOT 160:12608464 1000:0 10000:0 32768:0 64
 * STEIMAP.1:6 1.0 C 172.17.105.218:39150 N/A
 * EIDLGS 1
 * SEITOT 160:5247082 1000:0 10000:0 32768:0 63
 * STEIMAP.1:4 1.0 C 172.17.105.204:51327 N/A
 */

public class SMSCComverseConverter extends STS_BasicConverter {

    private static Logger logger =
        Logger.getLogger(SMSCComverseConverter.class.getName());

    private final String BLOCK_DATA_KEY = "TEIINF";
    private final String ATTEMP_KEY = "RTDLVA";
    private final String SUCC_KEY = "RTDLVD";

    private final String OUTPUT_TIME_FORMAT = "yyyyMMdd HH:mm:ss";

    /*
     * Convert for SMSC Comverse
     * */

    public SMSCComverseConverter() {
    }

    public void convertFile(File file, String direcPath,
                            Hashtable<Byte, String> params) throws STS_ConvertException {
        prepareParams(params);
        makeDirectory(direcPath);

        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            //File name = SMSCID + file name
            String fileName = nodeName.toUpperCase() + "_" + file.getName();
            File outFile = new File(direcPath, fileName);
            writer = new BufferedWriter(new FileWriter(outFile));

            // Append header to file
            String[] columnNames =
            { "SMSCID", "TIME", "GROUPID", "ATTEMP", "SUCC" };
            List<String> list = getFileHeaderInfo(columnNames, separator);
            for (String s : list) {
                writer.write(commentChar + s);
                writer.newLine();
            }

            String groupId = this.getGroupId(file.getName());
            String time = this.getTime(file.getName());
            String attemp = "";
            String succ = "";

            int dataIndex = 4;
            String[] temp = null;
            boolean isBlockData = false;
            String line = "";
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue;
                }
                if (line.startsWith(BLOCK_DATA_KEY)) {
                    isBlockData = true;
                }
                if (isBlockData == false) {
                    continue;
                }
                if (line.startsWith(ATTEMP_KEY)) {
                    temp = line.split(" ");
                    if (temp.length > dataIndex) {
                        attemp = temp[dataIndex];
                    }
                }
                if (line.startsWith(SUCC_KEY)) {
                    temp = line.split(" ");
                    if (temp.length > dataIndex) {
                        succ = temp[dataIndex];
                        break;
                    }
                }
            }

            line = nodeName + separator + time + separator;
            line += groupId + separator + attemp + separator + succ;
            writer.write(line);

            //update record in C_RAW_FILES_MLMN table in database
            if (params.containsKey(STS_Setting.FILE_ID_KEY)) {
                int fileId = Integer.parseInt(params.get(STS_Setting.FILE_ID_KEY));
                if (fileId > 0) {
                    this.updateFileName(fileId, fileName);
                }
            }
        } catch (IOException ioe) {
            throw new STS_ConvertException(ioe.getMessage(), "VMSC2-0306", ioe);
        } catch (SQLException sql) {
            throw new STS_ConvertException(sql.getMessage(), "VMSC2-0307", sql);
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

    private String getGroupId(String source) {
        String groupId = "";
        Pattern pattern = Pattern.compile("STAT.[0-9]*.");
        Matcher m = pattern.matcher(source.trim());
        if (m.find()) {
            groupId = m.group().trim();
            groupId = groupId.substring(0, groupId.length() - 1);
        }
        return groupId;
    }

    private String getTime(String fileName) {
        String time = getTimeFromFileName(fileName, "[0-9]{14}");
        return STS_Util.getTime(time, "yyyyMMddHHmmss", OUTPUT_TIME_FORMAT);
    }

    private void updateFileName(int fileId,
                                String fileName) throws SQLException {
        Connection conn = null;
        try {
            conn = STS_Global.DATA_SOURCE.getConnection();
            Statement st = conn.createStatement();

            String execCommand =
                "update C_RAW_FILES_MLMN set FILE_NAME = '" + fileName + "'" +
                " where FILE_ID = " + fileId;
            st.execute(execCommand);
            st.close();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    logger.warn("Cannot close connection to database");
                }
            }
        }
    }

}
