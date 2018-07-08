package vn.com.vhc.sts.cni.converter;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.core.STS_Global;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.STS_Util;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;


public class NokiaSiemen3GConverter extends STS_BasicConverter {

    private static Logger logger =
        Logger.getLogger(NokiaSiemen3GConverter.class.getName());

    private Hashtable<String, DataMap> maps = new Hashtable<String, DataMap>();
    private Hashtable<String, List<Node>> collectData =
        new Hashtable<String, List<Node>>();

    private int fileId = -1;
    private String timestamp = "";

    public NokiaSiemen3GConverter() {
        loadMapping();
    }

    /**
     * The sample mapping: maps.put(String1, new DataMap(String2, String3))
     * Decription parametter detail:
     *  String1: is the block data key, used to identify blocks of data to export.
     *  String2: It may be empty string. It was added to the front of the filename.
     *  String3: It is the name of the raw table. Used to create records in the Database
     */
    private void loadMapping() {
        maps.put("Availability", new DataMap("Avail", "R_N_SAAL"));
        maps.put("ATM_interface", new DataMap("ATM", "R_N_SAAL"));
        //......config in here......
    }

    public void convertFile(File file, String direcPath,
                            Hashtable<Byte, String> params) throws STS_ConvertException {
        prepareParams(params);
        makeDirectory(direcPath);

        try {
            DocumentBuilderFactory docBuilderFactory =
                DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder =
                docBuilderFactory.newDocumentBuilder();
            docBuilder.setEntityResolver(new EntityResolver() {
                    public InputSource resolveEntity(String publicId,
                                                     String systemId) throws SAXException,
                                                                             IOException {
                        return new InputSource(new StringReader(""));
                    }
                });
            //parse file
            Document doc = docBuilder.parse(file);

            Node parentNode = doc.getElementsByTagName("PMSetup").item(0);
            NodeList nodeList = parentNode.getChildNodes();

            String groupByNodeName = "PMMOResult";
            for (int index = 0; index < nodeList.getLength(); index++) {
                Node node = nodeList.item(index);
                if (node.getNodeType() == 3) {
                    continue;
                }
                if (node != null &&
                    node.getNodeName().equalsIgnoreCase(groupByNodeName)) {
                    String measType =
                        getTagValue(node, "PMTarget", "measurementType");
                    if (!isProperMeasType(measType)) {
                        continue;
                    }
                    if (this.collectData.containsKey(measType)) {
                        List<Node> ls = this.collectData.get(measType);
                        ls.add(node);
                    } else {
                        List<Node> ls = new ArrayList<Node>();
                        ls.add(node);
                        this.collectData.put(measType, ls);
                    }
                }
            }

            //If the file does not contain the data blocks to export
            if (collectData.size() == 0) {
                updateRecordStatus();
                return;
            }

            if (params.containsKey(STS_Setting.FILE_ID_KEY)) {
                fileId = Integer.parseInt(params.get(STS_Setting.FILE_ID_KEY));
            }
            timestamp = this.getTimeFromFileName(file.getName(), "[0-9]{14}");
            //String startTime =
            //    this.getTagValue(parentNode, "PMSetup", "startTime");
            //System.out.println("startTime: " + startTime);
            int count = 0;
            for (String key : collectData.keySet()) {
                String fName = getFileName(key, file.getName());
                try {
                    File fileOut = new File(direcPath, fName);
                    extractData(key, collectData.get(key), fileOut);
                    count++;
                } catch (Exception e) {
                    logger.warn("Write data faiule: " + fName);
                }
            }
            if (count > 0) {
                updateRecordStatus();
            }
        } catch (Exception e) {
            throw new STS_ConvertException(e.getMessage(), "VMSC2-0306", e);
        }

        logger.info("Convert file: " + file.getName() + " success");
    }

    private String getFileName(String measType, String fileName) {
        return getKey(measType) + "_" + fileName;
    }

    private void extractData(String measType, List<Node> data,
                             File desFile) throws Exception {
        List<Hashtable<String, String>> values = null;

        if (data != null && data.size() > 0) {
            values = new ArrayList<Hashtable<String, String>>();
            for (Node node : data) {
                //System.out.println(node.getNodeName());
                Node targetNode = this.getChildByNodeName(node, "PMTarget");
                if (targetNode != null) {
                    NodeList lsNode = targetNode.getChildNodes();
                    Hashtable<String, String> hashItem =
                        new Hashtable<String, String>();
                    for (int i = 0; i < lsNode.getLength(); i++) {
                        if (lsNode.item(i).getNodeType() == 3) {
                            continue;
                        }
                        hashItem.put(lsNode.item(i).getNodeName() + "@" + i,
                                     lsNode.item(i).getTextContent());
                    }
                    if (hashItem.size() > 0) {
                        values.add(hashItem);
                    }
                }
            }
        }

        if (values != null && values.size() > 0) {
            List<String> rows = null;

            List<String> keys = this.getMaxList(values);
            if (keys != null && keys.size() > 0) {
                rows = new ArrayList<String>();
                for (Hashtable<String, String> v : values) {
                    String line =
                        this.getLine(v, keys, separator, this.timestamp);
                    if (line != null && line.length() > 0) {
                        rows.add(line);
                    }
                }
            }
            String header = this.getLine(keys, separator, "Time");
            writeParsedData(desFile, rows, header, measType);
        }
        //insert data record into Database
        String rawTable = getTableName(measType);
        this.genarateDBRecord(desFile.getName(), rawTable);
    }

    private List<String> getMaxList(List<Hashtable<String, String>> data) {
        Set<String> maxset = null;
        if (data.size() > 0) {
            for (Hashtable<String, String> hash : data) {
                if (hash == null) {
                    continue;
                }
                if (maxset == null) {
                    maxset = hash.keySet();
                    continue;
                }
                Set<String> temp = hash.keySet();
                if (temp.size() > maxset.size()) {
                    maxset = temp;
                }
            }
        }
        List<String> maxls = null;
        if (maxset != null && maxset.size() > 0) {
            maxls = new ArrayList<String>(maxset.size());
            for (String s : maxset) {
                System.out.println(s);
                maxls.add(s);
            }
        }
        return maxls;
    }

    private String getLine(Hashtable<String, String> line, List<String> keys,
                           String sep, String extend) {
        String result = extend;
        for (String k : keys) {
            result += sep;
            String v = line.get(k);
            if (v == null) {
                result += "NULL";
                continue;
            } else {
                result += v;
            }
        }
        return result;
    }

    private String getLine(List<String> line, String sep, String extend) {
        String result = extend;
        if (line.size() > 0) {
            for (String s : line) {
                result += sep;
                result += s;
            }
        }
        return result;
    }

    private Node getChildByNodeName(Node node, String name) {
        NodeList nodeList = node.getChildNodes();
        if (nodeList != null && nodeList.getLength() > 0) {
            for (int index = 0; index < nodeList.getLength(); index++) {
                Node n = nodeList.item(index);
                if (n != null && n.getNodeName().equalsIgnoreCase(name)) {
                    return n;
                }
            }
        }
        return null;
    }

    private String getTagValue(Node parentNode, String tagName,
                               String propertyName) {
        String v = null;
        if (parentNode != null) {
            if (parentNode.getNodeName().equalsIgnoreCase(tagName)) {
                Node nodeItem =
                    parentNode.getAttributes().getNamedItem(propertyName);
                if (nodeItem != null) {
                    v = nodeItem.getTextContent();
                }
            } else {
                NodeList lsNode = parentNode.getChildNodes();
                for (int i = 0; i < lsNode.getLength(); i++) {
                    Node node = lsNode.item(i);
                    if (node.getNodeType() == 3) {
                        continue;
                    }
                    if (node.getNodeName().equalsIgnoreCase(tagName)) {
                        Node nodeItem =
                            node.getAttributes().getNamedItem(propertyName);
                        if (nodeItem != null) {
                            v = nodeItem.getTextContent();
                            if (v != null) {
                                break;
                            }
                        }
                    } else {
                        String tempValue =
                            getTagValue(node, tagName, propertyName);
                        if (tempValue != null) {
                            v = tempValue;
                        }
                    }
                }
            }
        }
        return v;
    }

    private void writeParsedData(File outFile, List<String> data,
                                 String header,
                                 String measType) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
        //write header
        List<String> list = getFileHeaderInfo();
        list.remove(list.size() - 1);
        list.add(" Block key: " + measType);
        list.add("---------------------------------------------");
        for (String s : list) {
            writer.write(commentChar + s);
            writer.newLine();
        }
        if (header != null && header.length() > 0) {
            writer.write(commentChar + header);
            writer.newLine();
        }
        //write data
        if (data != null && data.size() > 0) {
            for (String s : data) {
                writer.write(s);
                writer.newLine();
            }
        }
        writer.close();
    }

    private String getKey(String measType) {
        String key = "";
        DataMap m = this.maps.get(measType);
        if (m != null) {
            key = m.getKey();
        }
        return key;
    }

    private String getTableName(String measType) {
        String tblName = "";
        DataMap m = this.maps.get(measType);
        if (m != null) {
            tblName = m.getTableName();
        }
        return tblName;
    }

    private boolean isProperMeasType(String measType) {
        if (measType == null || measType.trim().equalsIgnoreCase("")) {
            return false;
        }
        return this.maps.containsKey(measType);
    }

    private void updateRecordStatus() throws SQLException {
        Connection conn = null;
        try {
            conn = STS_Global.DATA_SOURCE.getConnection();
            Statement st = conn.createStatement();
            // update state
            String queryStr = "update C_RAW_FILES_MLMN set \n" +
                "CONVERT_FLAG = 0, IMPORT_FLAG = 1 \n" +
                "where FILE_ID = " + fileId;
            st.execute(queryStr);
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

    private void genarateDBRecord(String fileName,
                                  String rawTable) throws SQLException {
        Connection conn = null;
        try {
            conn = STS_Global.DATA_SOURCE.getConnection();
            Statement st = conn.createStatement();
            //query data
            String queryStr = "select PATTERN_ID, \n" +
                "DAY, HOUR, NODE_NAME from C_RAW_FILES_MLMN \n" +
                "where FILE_ID = " + fileId;
            String patternId = "";
            String day = "";
            String hour = "";
            String nodeName = "";
            //
            ResultSet rs = st.executeQuery(queryStr);
            while (rs.next()) {
                patternId = rs.getString("PATTERN_ID");
                day = STS_Util.getTime(rs.getDate("DAY"));
                hour = rs.getString("HOUR");
                nodeName = rs.getString("NODE_NAME");
            }
            if (patternId.length() > 0) {
                queryStr = "insert into C_RAW_FILES_MLMN(FILE_ID,\n" +
                        "PATTERN_ID, FILE_NAME, DAY, HOUR, \n" +
                        "CONVERT_FLAG, IMPORT_FLAG, \n" +
                        "NODE_NAME, RAW_TABLE) values(SEQ_CRF.NEXTVAL, " +
                        patternId + ", '" + fileName +
                        "', to_date('" + day + "', '" +
                        STS_Setting.DB_TIME_FORMAT + "'), " + hour +
                        ", 1, 0, '" + nodeName + "', '" + rawTable + "')";

                st.execute(queryStr);
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

    private class DataMap {
        private String key = "";
        private String tableName = "";

        DataMap(String key, String tableName) {
            this.key = key;
            this.tableName = tableName;
        }

        public String getKey() {
            return key;
        }

        public String getTableName() {
            return tableName;
        }
    }
}
