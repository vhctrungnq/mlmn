package vn.com.vhc.sts.cni.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;


import sts.pms.service.nns.AlcatelNNSExporter;
import sts.pms.service.nns.AlcatelNNSExporterFactory;
import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.core.STS_Global;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.STS_Util;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;


public class OCBConverter extends STS_BasicConverter {

    private final Pattern CLASS_F_PATTERN = Pattern.compile("CLASS_F");
    private final Pattern CLASS_S_PATTERN = Pattern.compile("SGNL");
    private final Pattern CLASS_T_PATTERN = Pattern.compile("CLASS_T");
    private final Pattern CLASS_C_PATTERN = Pattern.compile("CLASS_C");

    private final String CLASS_C_TABLE = "CLASS_C";
    private final String CLASS_S_TABLE = "CLASS_S";
    private final String CLASS_F_TABLE = "CLASS_F";
    private final String CLASS_T_TABLE = "CLASS_T";

    private final String[] CLASS_C_COLUMN_NAME =
    { "MSCID", "YEAR", "DAY", "HOUR", "TRAFFTYPE", "TRAFFIC" };
    private final String[] CLASS_S_COLUMN_NAME =
    { "UNKNOWN", "UNKNOWN", "UNKNOWN", "MSCID", "UNKNOWN", "DAY", "HOUR",
      "UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN",
      "LINKSETID", "LINKCODE", "NTE_I", "NTR_I" };
    private final String[] CLASS_F_COLUMN_NAME =
    { "UNKNOWN", "UNKNOWN", "UNKNOWN", "MSCID", "UNKNOWN", "DAY", "HOUR",
      "UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN", "ROUTEID", "UNKNOWN", "FS_I",
      "FQ_I", "FOA_I", "FOD_I", "FEA_I", "FED_I", "FPA_I", "FPD_I", "FRA_I",
      "FRD_I" };
    private final String[] CLASS_T_COLUMN_NAME =
    { "MSCID", "UNKNOWN", "DAY", "HOUR", "TRAFFTYPE", "T01", "T02", "T03",
      "T04", "T05", "T06", "T07", "T08", "T09", "T10", "T11" };

    private static Logger logger =
        Logger.getLogger(OCBConverter.class.getName());

    private List<String> listFileName = new ArrayList<String>();
    private int fileId = -1;

    /**
     * @param file
     * @param direcPath
     * @param params
     * @throws STS_ConvertException
     */
    public void convertFile(File file, String direcPath,
                            Hashtable<Byte, String> params) throws STS_ConvertException {
        prepareParams(params);
        makeDirectory(direcPath);

        BufferedReader reader = null;
        File tmpFile = null;
        boolean isRemoveFileTemp = false;
        try {
            List<String[]> classF = new ArrayList<String[]>();
            List<String[]> classS = new ArrayList<String[]>();
            List<String[]> classT = new ArrayList<String[]>();
            List<String[]> classC = new ArrayList<String[]>();

            AlcatelNNSExporter exporter =
                AlcatelNNSExporterFactory.newInstance(file.getPath(),
                                                      file.getPath() + ".tmp",
                                                      nodeName.trim(),
                                                      separator);
            exporter.exportToTextFile();

            tmpFile = new File(file.getPath() + ".tmp");
            reader = new BufferedReader(new FileReader(tmpFile));
            String line = "";
            //Read until reach end of file
            while ((line = reader.readLine()) != null) {
                //Ignore line doesn't contain value
                if (line.trim().length() <= 0) {
                    continue;
                }

                if (CLASS_C_PATTERN.matcher(line.trim()).find()) {
                    String[] strs = line.split(separator);
                    for (int i = 0; i < 4; i++) {
                        String[] classCData = new String[6];
                        classCData[0] = strs[3];
                        classCData[1] = strs[4];
                        classCData[2] = strs[5];
                        classCData[3] = strs[6];

                        switch (i) {
                        case 0:
                            classCData[4] = "I";
                            break;
                        case 1:
                            classCData[4] = "D";
                            break;
                        case 2:
                            classCData[4] = "A";
                            break;
                        default:
                            classCData[4] = "T";
                        }
                        classCData[5] = strs[11 + i];

                        classC.add(classCData);
                    }
                } else if (CLASS_F_PATTERN.matcher(line.trim()).find()) {
                    classF.add(line.split(separator));
                } else if (CLASS_S_PATTERN.matcher(line.trim()).find()) {
                    classS.add(line.split(separator));
                } else if (CLASS_T_PATTERN.matcher(line.trim()).find()) {
                    String[] strs = line.split(separator);
                    //Class T (0)
                    if (strs[10].equalsIgnoreCase("0")) {
                        for (int i = 0; i < 4; i++) {
                            String[] classTData = new String[16];
                            classTData[0] = strs[3];
                            classTData[1] = strs[4];
                            classTData[2] = strs[5];
                            classTData[3] = strs[6];

                            switch (i) {
                            case 0:
                                classTData[4] = "I";
                                break;
                            case 1:
                                classTData[4] = "D";
                                break;
                            case 2:
                                classTData[4] = "A";
                                break;
                            default:
                                classTData[4] = "T";
                            }

                            int cnt = 5;
                            for (int j = 11 + i; j <= 51 + i; j += 4) {
                                classTData[cnt] = strs[j];
                                cnt++;
                            }

                            classT.add(classTData);
                        }
                    }
                }
            }

            String fn = "_" + getFileName(file.getName());
            List<String> fileNames = new ArrayList<String>();
            fileNames.add(CLASS_C_TABLE + fn);
            fileNames.add(CLASS_F_TABLE + fn);
            fileNames.add(CLASS_S_TABLE + fn);
            fileNames.add(CLASS_T_TABLE + fn);

            List<String[]> data = null;
            for (String name : fileNames) {
                if (name.indexOf(CLASS_C_TABLE) >= 0) {
                    data = classC;
                }
                if (name.indexOf(CLASS_F_TABLE) >= 0) {
                    data = classF;
                }
                if (name.indexOf(CLASS_S_TABLE) >= 0) {
                    data = classS;
                }
                if (name.indexOf(CLASS_T_TABLE) >= 0) {
                    data = classT;
                }
                try {
                    this.writeToFile(new File(direcPath, name), data,
                                     separator, commentChar);
                    isRemoveFileTemp = true;
                } catch (Exception e) {
                    logger.warn("Can not write file: " + name +
                                   " - MESSAGE: " + e.getMessage());
                }
            }

            //insert record and update status in C_RAW_FILES_MLMN table
            if (params.containsKey(STS_Setting.FILE_ID_KEY)) {
                fileId = Integer.parseInt(params.get(STS_Setting.FILE_ID_KEY));
            }
            this.genarateFileRecord();
        } catch (IOException ioe) {
            throw new STS_ConvertException(ioe.getMessage(), "VMSC2-0306", ioe);
        } catch (SQLException sql) {
            throw new STS_ConvertException(sql.getMessage(), "VMSC2-0307", sql);
        } catch (Exception e) {
            throw new STS_ConvertException(e.getMessage(), "VMSC2-0308", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception ex) {
                    logger.warn("Close IO stream failure");
                }
            }
        }
        if (isRemoveFileTemp == true) {
            tmpFile.delete();
        }

        logger.info("Convert file: " + file.getName() + " success");
    }

    private void writeToFile(File outputFile, List<String[]> data,
                             String seperator,
                             String commentChar) throws IOException {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(outputFile));

            // Append header to file
            String[] columnNames = getTableColumns(outputFile.getName());
            List<String> list = getFileHeaderInfo(columnNames, separator);
            for (String line : list) {
                line = commentChar + line;
                out.write(line);
                out.newLine();
            }
            //write body
            if (data != null && data.size() > 0) {
                StringBuffer buf = new StringBuffer();
                for (String[] strs : data) {
                    for (String s : strs) {
                        buf.append(s);
                        buf.append(seperator);
                    }
                    out.write(buf.toString().trim());
                    //out.write(buf.toString();
                    out.newLine();
                    buf.delete(0, buf.length());
                }
            }

            listFileName.add(outputFile.getName());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private void genarateFileRecord() throws SQLException {
        if (listFileName.size() == 0) {
            return;
        }
        Connection conn = null;
        try {
            conn = STS_Global.DATA_SOURCE.getConnection();
            Statement st = conn.createStatement();
            // update state
            String queryStr = "update C_RAW_FILES_MLMN set \n" +
                "CONVERT_FLAG = 0, IMPORT_FLAG = 1 \n" +
                "where FILE_ID = " + fileId;
            st.execute(queryStr);
            //query data
            queryStr = "select PATTERN_ID, \n" +
                    "DAY, HOUR, NODE_NAME from C_RAW_FILES_MLMN \n" +
                    "where FILE_ID = " + fileId;
            String patternId = "";
            String day = "";
            String hour = "";
            String nodeName = "";
            String rawTable = "";
            //
            ResultSet rs = st.executeQuery(queryStr);
            while (rs.next()) {
                patternId = rs.getString("PATTERN_ID");
                  day = STS_Util.getTime(rs.getDate("DAY"));
                hour = rs.getString("HOUR");
                nodeName = rs.getString("NODE_NAME");
            }
            if (patternId.length() > 0) {
                queryStr = "";
                for (String fileName : listFileName) {
                    rawTable = this.getRawTable(fileName);

                    queryStr = "insert into C_RAW_FILES_MLMN(FILE_ID,\n" +
                            "PATTERN_ID, FILE_NAME, DAY, HOUR, \n" +
                            "CONVERT_FLAG, IMPORT_FLAG, \n" +
                            "NODE_NAME, RAW_TABLE) values(SEQ_CRF.NEXTVAL, " +
                            patternId + ", '" + fileName +
                            "', to_date('" + day + "', '" +
                            STS_Setting.DB_TIME_FORMAT + "'), " + hour +
                            ", 1, 0, '" + nodeName + "', '" + rawTable +
                            "')";

                    st.execute(queryStr);
                }
            }

            rs.close();
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

    private String getRawTable(String fileName) {
        String tbl = "R_A_";
        if (fileName.indexOf(CLASS_C_TABLE) >= 0) {
            tbl += CLASS_C_TABLE;
        }
        if (fileName.indexOf(CLASS_S_TABLE) >= 0) {
            tbl += CLASS_S_TABLE;
        }
        if (fileName.indexOf(CLASS_F_TABLE) >= 0) {
            tbl += CLASS_F_TABLE;
        }
        if (fileName.indexOf(CLASS_T_TABLE) >= 0) {
            tbl += CLASS_T_TABLE;
        }
        return tbl;
    }

    private String[] getTableColumns(String fileName) {
        String[] columnNames = null;
        if (fileName.indexOf(CLASS_C_TABLE) >= 0) {
            columnNames = CLASS_C_COLUMN_NAME;
        }
        if (fileName.indexOf(CLASS_S_TABLE) >= 0) {
            columnNames = CLASS_S_COLUMN_NAME;
        }
        if (fileName.indexOf(CLASS_F_TABLE) >= 0) {
            columnNames = CLASS_F_COLUMN_NAME;
        }
        if (fileName.indexOf(CLASS_T_TABLE) >= 0) {
            columnNames = CLASS_T_COLUMN_NAME;
        }
        return columnNames;
    }

}
