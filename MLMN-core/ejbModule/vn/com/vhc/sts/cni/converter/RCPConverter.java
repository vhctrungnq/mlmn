package vn.com.vhc.sts.cni.converter;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.core.STS_Global;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.STS_Util;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;



public class RCPConverter extends STS_BasicConverter {

    private static Logger logger =
        Logger.getLogger(RCPConverter.class.getName());

    private Vector<RCPMap> vctMap = new Vector<RCPMap>();
    private List<Integer> listCounter = new ArrayList<Integer>();

    public RCPConverter() {
        loadRCPMap();
        loadListCounter();
    }

    public void convertFile(File file, String direcPath,
                            Hashtable<Byte, String> params) throws STS_ConvertException {
        prepareParams(params);
        makeDirectory(direcPath);

        int year = 2009;
        byte month = 1;
        byte day = 1;
        byte hour = 1;
        byte minute = 1;
        byte second = 1;

        int dispatchNum = 0;
        int numOfTicket = 0;
        int ticketNum = 0;

        int i_byte1 = 0;
        int i_byte2 = 0;
        int i_data = 0;
        int iTicketLength = 0;
        int iByteCount = 0;

        int counter = 0;
        int counterType = 0;
        int counterValue = 0;
        int counterValid = 0;

        int currentYear = STS_Util.getCurrentYear();

        String counterRank = "";
        String time = ""; //FORMAT: dd/MM/yyyy

        String mscid = "";
        String rcp = "";
        RCPMap map = this.getRCPMap(nodeName);
        if (map == null) {
            mscid = this.getMSCId(file.getName());
            rcp = this.getRCP(file.getName());
        } else {
            mscid = map.getMscId();
            rcp = map.getRCP();
        }

        DataInputStream data_in = null;
        BufferedWriter writer = null;
        try {
            String columnNames[] =
            { "DAY", "HOUR", "MSCID", "RCP", "COUNTER", "COUNTERTYPE",
              "COUNTERRANK", "COUNTERVALUE", "COUNTERVALID", "MINUTE" };

            File outFile = new File(direcPath, file.getName());
            writer = new BufferedWriter(new FileWriter(outFile));

            // Append header to file
            List<String> colNames = getFileHeaderInfo(columnNames, separator);
            for (String s : colNames) {
                writer.write(commentChar + s);
                writer.newLine();
            }

            // Wrap the FileInputStream with a DataInputStream
            FileInputStream fileInput = new FileInputStream(file);
            BufferedInputStream buffer = new BufferedInputStream(fileInput);
            data_in = new DataInputStream(buffer);
            String rowData = "";

            while (true) {
                try {
                    i_byte1 = data_in.readUnsignedByte();
                    i_byte2 = data_in.readUnsignedByte();
                    i_data = i_byte1 * 256 + i_byte2;

                    //System.out.println("Data: " + i_data);
                    if (iByteCount == iTicketLength) {
                        iTicketLength = i_data; //Length of the ticket in bytes

                        //Read ticket header
                        year = data_in.readByte() + 2000;
                        month = data_in.readByte();
                        day = data_in.readByte();
                        hour = data_in.readByte();
                        minute = data_in.readByte();
                        second = data_in.readByte();

                        dispatchNum = data_in.read();
                        numOfTicket = data_in.read();
                        ticketNum = data_in.read();

                        //Set byte counter
                        iByteCount = 9;
                    } else { //Ticket body
                        //Counter ID
                        counter = i_data;
                        //Counter type
                        counterType = data_in.readUnsignedByte();
                        iByteCount = iByteCount + 3;

                        int iChar = 0;
                        int iCharCount = 0;
                        counterRank = "";
                        //get rank of the counter within the family
                        if (counterType < 3) { //Counter type 1,2
                            for (iCharCount = 0; iCharCount < 8;
                                 iCharCount++) {
                                iChar = data_in.readUnsignedByte();
                                if ((iChar < 32) || (iChar > 126)) {
                                    counterRank = counterRank + "-";
                                } else {
                                    counterRank = counterRank + (char)iChar;
                                }
                            }
                            iByteCount = iByteCount + 8;
                        } else if ((counterType > 2) &&
                                   (counterType < 5)) { //Counter type 3,4
                            for (iCharCount = 0; iCharCount < 16;
                                 iCharCount++) {
                                iChar = data_in.readUnsignedByte();
                                if ((iChar < 32) || (iChar > 126)) {
                                    counterRank = counterRank + "-";
                                } else {
                                    counterRank = counterRank + (char)iChar;
                                }
                            }
                            iByteCount = iByteCount + 16;
                        }

                        counterValue = data_in.readInt(); //Counter Value
                        //CounterValid
                        counterValid = data_in.readUnsignedByte();
                        iByteCount = iByteCount + 5;
                        //validate counter
                        if (!this.isValidCounter(counter)) {
                            continue;
                        }

                        //concat time
                        time = String.valueOf(day);
                        time += STS_Setting.DATE_SEPARATOR;
                        time += String.valueOf(month);
                        time += STS_Setting.DATE_SEPARATOR;
                        time += String.valueOf(year);
                        if ((year > currentYear) || (month < 1) ||
                            (month > 12) || (day < 1) || (day > 31) ||
                            (hour < 0) || (hour > 23) || (minute < 0) ||
                            (minute > 59)) {
                            throw new STS_ConvertException("DATE TIME value invalid: " +
                                                       time);
                        }

                        // push line data to file
                        rowData = time + separator;
                        rowData += hour + separator;
                        rowData += mscid + separator;
                        rowData += rcp + separator;
                        rowData += counter + separator;
                        rowData += counterType + separator;
                        rowData += counterRank.trim() + separator;
                        rowData += counterValue + separator;
                        rowData += counterValid + separator;
                        rowData += minute;

                        writer.write(rowData);
                        writer.newLine();
                    }
                } catch (Exception ex) {
                    break;
                }
            }
        } catch (Exception e) {
            throw new STS_ConvertException(e.getMessage(), "VMSC2-0306", e);
        } finally {
            if (data_in != null && writer != null) {
                try {
                    data_in.close();
                    writer.close();
                } catch (IOException e) {
                    logger.warn("Close IO stream failure");
                }
            }
        }

        logger.info("Convert file: " + file.getName() + " success");
    }


    private void loadListCounter() {
        Connection conn = null;
        try {
            conn = STS_Global.DATA_SOURCE.getConnection();
            Statement st = conn.createStatement();
            //query data from H_RCP_COUNTER_CONFIG table
            String counter = "";
            ResultSet rs =
                st.executeQuery("select COUNTER from H_RCP_COUNTER_CONFIG where STATUS = 'Y'");
            while (rs.next()) {
                counter = rs.getString("COUNTER");
                if (counter != null && counter.length() > 0) {
                    try {
                        this.listCounter.add(Integer.parseInt(counter));
                    } catch (NumberFormatException nfe) {
                        logger.warn("COUNTER value invalid: " +
                                    nfe.getMessage());
                    }
                }
            }
            rs.close();
            st.close();
        } catch (SQLException sql) {
            logger.warn("Can not load data from H_RCP_COUNTER_CONFIG table" +
                        sql.getMessage());
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

    private boolean isValidCounter(int counter) {
        if (this.listCounter.size() == 0) {
            return true;
        }
        for (int i : listCounter) {
            if (i == counter) {
                return true;
            }
        }
        return false;
    }

    private RCPMap getRCPMap(String key) {
        if (vctMap.size() > 0) {
            for (RCPMap map : vctMap) {
                if (map.isContainedKey(key)) {
                    return map;
                }
            }
        }
        return null;
    }

    private void loadRCPMap() {
        Connection conn = null;
        try {
            conn = STS_Global.DATA_SOURCE.getConnection();
            Statement st = conn.createStatement();
            //query data from H_RCP table
            String key = "";
            String mscId = "";
            String rcp = "";
            ResultSet rs =
                st.executeQuery("select KEY, MSCID, RCP from H_RCP");
            while (rs.next()) {
                key = rs.getString("KEY");
                mscId = rs.getString("MSCID");
                rcp = rs.getString("RCP");
                if (key != null && key.length() > 0 && mscId != null &&
                    mscId.length() > 0 && rcp != null && rcp.length() > 0) {
                    vctMap.add(new RCPMap(key, mscId, rcp));
                }
            }
            rs.close();
            st.close();
        } catch (SQLException sql) {
            logger.warn("Can not load data from H_RCP table" +
                        sql.getMessage());
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

    private String getMSCId(String fileName) {
        String mscid = "";
        if (fileName.indexOf("RCPHP01") >= 0) {
            mscid = "MHPH01A";
        }
        if (fileName.indexOf("RCPHP02") >= 0) {
            mscid = "MHPH01A";
        }
        if (fileName.indexOf("RCTO01") >= 0) {
            mscid = "MCTO01A";
        }
        if (fileName.indexOf("RCTO02") >= 0) {
            mscid = "MCTO01A";
        }
        if (fileName.indexOf("RCTO03") >= 0) {
            mscid = "MCTO01A";
        }
        if (fileName.indexOf("RCTO04") >= 0) {
            mscid = "MCTO01A";
        }
        if (fileName.indexOf("RHCM051") >= 0) {
            mscid = "MHCM05A";
        }
        if (fileName.indexOf("RHCM05A2") >= 0) {
            mscid = "MHCM05A";
        }
        if (fileName.indexOf("RHCM05A3") >= 0) {
            mscid = "MHCM05A";
        }
        if (fileName.indexOf("RHCM05A4") >= 0) {
            mscid = "MHCM05A";
        }
        if (fileName.indexOf("RCPHLR") >= 0) {
            mscid = "MHAN01A";
        }
        if (fileName.indexOf("RHANRCP2") >= 0) {
            mscid = "MHAN01A";
        }
        if (fileName.indexOf("RHAN03A") >= 0) {
            mscid = "MHAN01A";
        }
        if (fileName.indexOf("RHAN04A") >= 0) {
            mscid = "MHAN01A";
        }

        return mscid;
    }

    private String getRCP(String fileName) {
        String rcp = "";

        if (fileName.indexOf("RCPHP01") >= 0) {
            rcp = "RHPH01A";
        }
        if (fileName.indexOf("RCPHP02") >= 0) {
            rcp = "RHPH02A";
        }
        if (fileName.indexOf("RCTO01") >= 0) {
            rcp = "RCTO01A";
        }
        if (fileName.indexOf("RCTO02") >= 0) {
            rcp = "RCTO02A";
        }
        if (fileName.indexOf("RCTO03") >= 0) {
            rcp = "RCTO03A";
        }
        if (fileName.indexOf("RCTO04") >= 0) {
            rcp = "RCTO04A";
        }
        if (fileName.indexOf("RHCM051") >= 0) {
            rcp = "RHCM051A";
        }
        if (fileName.indexOf("RHCM05A2") >= 0) {
            rcp = "RHCM052A";
        }
        if (fileName.indexOf("RHCM05A3") >= 0) {
            rcp = "RHCM053A";
        }
        if (fileName.indexOf("RHCM05A4") >= 0) {
            rcp = "RHCM054A";
        }
        if (fileName.indexOf("RCPHLR") >= 0) {
            rcp = "RHANRCP1";
        }
        if (fileName.indexOf("RHANRCP2") >= 0) {
            rcp = "RHANRCP2";
        }
        if (fileName.indexOf("RHAN03A") >= 0) {
            rcp = "RHANRCP3";
        }
        if (fileName.indexOf("RHAN04A") >= 0) {
            rcp = "RHANRCP4";
        }

        return rcp;
    }

    class RCPMap {

        private String key = "";
        private String mscId = "";
        private String rcp = "";

        protected RCPMap(String key, String mscId, String rcp) {
            this.key = key;
            this.mscId = mscId;
            this.rcp = rcp;
        }

        protected String getKey() {
            return this.key;
        }

        protected String getMscId() {
            return this.mscId;
        }

        protected String getRCP() {
            return this.rcp;
        }

        protected boolean isContainedKey(String k) {
            return key.equalsIgnoreCase(k);
        }
    }

}
