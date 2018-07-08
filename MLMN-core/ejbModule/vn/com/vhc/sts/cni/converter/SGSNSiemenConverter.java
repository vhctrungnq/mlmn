package vn.com.vhc.sts.cni.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.core.STS_Global;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.STS_Util;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;



public class SGSNSiemenConverter extends STS_BasicConverter {

    private static Logger logger =
        Logger.getLogger(SGSNSiemenConverter.class.getName());

    private List<DataMap> biasFileBock = new ArrayList<DataMap>();
    private List<DataMap> scanrepFileBock = new ArrayList<DataMap>();
    private List<DataMap> gprsFileBlock = new ArrayList<DataMap>();

    private int fileId = 0;
    private String fileType = "";

    public SGSNSiemenConverter() {
        this.prepareList();
    }

    /**
     *
     *
     * */
    private void prepareList() {
        //bias
        biasFileBock.add(new DataMap("biasLoad", "R_N_BIASLOAD"));
        //scanrep
        scanrepFileBock.add(new DataMap("vcpuCurrentData", "R_N_VCPU_CD"));
        scanrepFileBock.add(new DataMap("ra2gGMMAFCurrentData",
                                        "R_N_RA2GGMMAF_CD"));
        scanrepFileBock.add(new DataMap("raPsPagingCurrentData",
                                        "R_N_RAPSPAGING_CD"));
        scanrepFileBock.add(new DataMap("sgsn2gSecurityCurrentData",
                                        "R_N_SGSN2GSECURITY_CD"));
        scanrepFileBock.add(new DataMap("ra2gGMMAFRejCausesCurrentData",
                                        "R_N_RA2GGMMAFREJCAUSES_CD"));
        scanrepFileBock.add(new DataMap("sgsnSNDCPCurrentData",
                                        "R_N_SGSNSNDCP_CD"));
        scanrepFileBock.add(new DataMap("sgsn2gGMMAFCurrentData",
                                        "R_N_SGSN2GGMMAF_CD"));
        scanrepFileBock.add(new DataMap("sgsn2gSMCurrentData",
                                        "R_N_SGSN2GSM_CD"));
        scanrepFileBock.add(new DataMap("sgsn2gSMExtCurrentData",
                                        "R_N_SGSN2GSMEXT_CD"));
        //gprs
        gprsFileBlock.add(new DataMap("GPRS-gtpTrafficClass",
                                      "R_N_GPRSGTPTRAFFICCLASS"));
        gprsFileBlock.add(new DataMap("GPRS-gbNsvc", "R_N_GPRSGBNSVC"));
    }

    public void convertFile(File file, String direcPath,
                            Hashtable<Byte, String> params) throws STS_ConvertException {
        prepareParams(params);
        makeDirectory(direcPath);

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));

            this.setFileType(file.getName());
            if (params.containsKey(STS_Setting.FILE_ID_KEY)) {
                fileId = Integer.parseInt(params.get(STS_Setting.FILE_ID_KEY));
            }

            List<String> dataBlock = null;
            String key = "";
            String header = "";
            String line = "";
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue;
                }

                if (line.startsWith("HEADER")) {
                    if (dataBlock != null) {
                        System.out.println(key);
                        try {
                            writeBlockData(dataBlock, header, key, direcPath,
                                           file.getName());
                        } catch (IOException ioe) {
                            logger.warn("Convert block failure: " + key +
                                           " - FILE_ID: " + fileId +
                                           " - FILE_NAME: " + file.getName() +
                                           " - MESSAGE: " + ioe.getMessage());
                        } catch (SQLException sqlEx) {
                            logger.warn("Create record block failure: " +
                                           key + " - FILE_ID: " + fileId +
                                           " - FILE_NAME: " + file.getName() +
                                           " - MESSAGE: " +
                                           sqlEx.getMessage());
                        } catch (Exception e) {
                            logger.warn(e.getMessage());
                        } finally {
                            dataBlock = null;
                        }
                    }

                    key = getBlockKey(line);
                    if (key.length() > 0) {
                        dataBlock = new ArrayList<String>();
                        header = line;
                    }
                    continue;
                }
                if (key.length() == 0) {
                    dataBlock = null;
                    continue;
                }
                dataBlock.add(line);
            }

            updateRecordStatus();
        } catch (IOException ioe) {
            throw new STS_ConvertException(ioe.getMessage(), "VMSC2-0306", ioe);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.warn("Close IO stream failure");
                }
            }
        }

        logger.info("Convert file: " + file.getName() + " success");
    }

    private void setFileType(String fileName) {
        if (fileName.length() == 0) {
            return;
        }
        fileName = fileName.toUpperCase();
        if (fileName.indexOf("BIAS") >= 0) {
            fileType = "BIAS";
        } else if (fileName.indexOf("SCANREP") >= 0) {
            fileType = "SCANREP";
        } else if (fileName.indexOf("GPRS36140") >= 0) {
            fileType = "GPRS36140";
        }
    }

    private boolean isBiasFileType() {
        return this.fileType.equalsIgnoreCase("BIAS");
    }

    private boolean isScanrepFileType() {
        return this.fileType.equalsIgnoreCase("SCANREP");
    }

    private boolean isGprs36140FileType() {
        return this.fileType.equalsIgnoreCase("GPRS36140");
    }

    private void writeBlockData(List<String> data, String header, String key,
                                String direcPath,
                                String fileName) throws IOException,
                                                        SQLException {

        String fName = key.toUpperCase() + "-" + fileName;
        BufferedWriter writer = null;
        try {
            File outFile = new File(direcPath, fName);
            writer = new BufferedWriter(new FileWriter(outFile));

            // Append header to file
            List<String> list = getFileHeaderInfo();
            list.remove(list.size() - 1);
            list.add(" Ten file goc: " + fileName);
            list.add(" Block name: " + key);
            list.add("---------------------------------------------");
            for (String s : list) {
                writer.write(commentChar + s);
                writer.newLine();
            }
            header = replaceByIndex(header, ";", " ", 2);
            writer.write(commentChar + header.toUpperCase());
            writer.newLine();

            if (data != null && data.size() > 0) {
                for (String s : data) {
                    s = this.replaceByIndex(s, ";", " ", 2);
                    writer.write(s);
                    writer.newLine();
                }
            }
            //insert record in C_RAW_FILES_MLMN table
            insertRecord(fName, getRawTable(key));
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    private String replaceByIndex(String source, String regex,
                                  String replacement, int index) {
        String s = "";
        String arr[] = source.split(regex);
        for (int i = 0; i < arr.length; i++) {
            if (i == index) {
                s += arr[i] + replacement;
                continue;
            }
            s += arr[i] + regex;
        }
        s = s.substring(0, s.length() - 1);

        return s;
    }

    private String getBlockKey(String header) {
        List<DataMap> maps = this.getDataMap();
        if (maps != null) {
            header = header.toLowerCase();
            String k = "";
            Matcher matcher = null;
            for (DataMap m : maps) {
                k = m.getKey().toLowerCase();
                matcher =
                        Pattern.compile(separator + k + separator).matcher(header);
                if (matcher.find()) {
                    return m.getKey();
                }
            }
        }
        return "";
    }

    private String getRawTable(String key) {
        List<DataMap> maps = this.getDataMap();
        if (maps != null) {
            for (DataMap m : maps) {
                if (m.isContainedKey(key)) {
                    return m.getRawTable();
                }
            }
        }
        return "";
    }

    private List<DataMap> getDataMap() {
        List<DataMap> map = null;
        if (this.isBiasFileType()) {
            map = biasFileBock;
        } else if (this.isScanrepFileType()) {
            map = scanrepFileBock;
        } else if (this.isGprs36140FileType()) {
            map = gprsFileBlock;
        }
        return map;
    }

    private Connection conn = null;
    private PreparedStatement pst = null;

    private void openConnection() throws SQLException {
        DataSource ds = STS_Global.DATA_SOURCE;
        if (ds != null) {
            this.conn = ds.getConnection();
        }
        //this.conn = CNISetting.getDBConnection(); //add to test
    }

    private void updateRecordStatus() {
        try {
            if (this.conn == null || this.conn.isClosed()) {
                openConnection();
            }
            String queryStr = "update C_RAW_FILES_MLMN set \n" +
                "CONVERT_FLAG = 0, IMPORT_FLAG = 1 \n" +
                "where FILE_ID = " + fileId;

            Statement st = conn.createStatement();
            st.execute(queryStr);
        } catch (Exception e) {
            logger.warn("Can not update UPLOAD and IMPORT status when convert finish. FILE_ID: " +
                           fileId);
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

    private void insertRecord(String fileName,
                              String tableName) throws SQLException {
        if (this.conn == null || this.conn.isClosed()) {
            openConnection();
        }
        try {
            if (pst == null) {
                String preStr = getPreStateCommand();
                //System.out.println(preStr);
                pst = conn.prepareStatement(preStr);
            }
            pst.setString(1, fileName);
            pst.setString(2, tableName);
            pst.executeUpdate();
        } catch (SQLException sql) {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    logger.warn("Cannot close connection to database");
                }
            }
            throw sql;
        }
    }

    private String getPreStateCommand() throws SQLException {
        String preState = "";
        if (conn == null) {
            return preState;
        }

        String patternId = "";
        String day = "";
        String hour = "";
        String nodeName = "";

        String queryStr = "select PATTERN_ID, \n" +
            "DAY, HOUR, NODE_NAME from C_RAW_FILES_MLMN \n" +
            "where FILE_ID = " + fileId;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(queryStr);
        while (rs.next()) {
            patternId = rs.getString("PATTERN_ID");
            day = STS_Util.getTime(rs.getDate("DAY"));
            hour = rs.getString("HOUR");
            nodeName = rs.getString("NODE_NAME");
        }
        if (patternId.length() > 0) {
            preState = "insert into C_RAW_FILES_MLMN(FILE_ID,\n" +
                    "PATTERN_ID, FILE_NAME, DAY, HOUR, \n" +
                    "CONVERT_FLAG, IMPORT_FLAG, \n" +
                    "NODE_NAME, RAW_TABLE) values(SEQ_CRF.NEXTVAL, " +
                    patternId + ", " + "?" +
                    ", to_date('" + day + "', '" + STS_Setting.DB_TIME_FORMAT +
                    "'), " + hour + ", 1, 0, '" + nodeName + "', " + "?)";
        }
        rs.close();
        st.close();

        return preState;
    }

    class DataMap {
        private String key = "";
        private String rawTable = "";

        protected DataMap(String key, String rawTable) {
            this.key = key;
            this.rawTable = rawTable;
        }

        protected String getKey() {
            return this.key;
        }

        protected String getRawTable() {
            return this.rawTable;
        }

        protected boolean isContainedKey(String k) {
            return this.key.equalsIgnoreCase(k);
        }
    }
}
