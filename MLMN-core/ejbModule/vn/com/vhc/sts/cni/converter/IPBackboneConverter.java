package vn.com.vhc.sts.cni.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.util.STS_Setting;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;


public class IPBackboneConverter extends STS_BasicConverter {

    private static Logger logger =
        Logger.getLogger(IPBackboneConverter.class.getName());

    private String regex = "";

    public IPBackboneConverter() {
        super();
    }

    public static void main(String[] args) throws FileNotFoundException,
                                                  IOException,
                                                  ParserConfigurationException,
                                                  STS_ConvertException {

        long begin = System.currentTimeMillis();
        new IPBackboneConverter().convertFile(new File("D:/temp/TT3_Backbone_RoDNG1N AnDon_RoHNI2N GiapBat_Saturday_ September 12_ 2009.xls"),
                                              "D:/temp/t1.xls", null);
        System.out.println("Convert finish in (milisec): " +
                           (System.currentTimeMillis() - begin));
    }

    public void convertFile(File file, String direcPath,
                            Hashtable<Byte, String> params) throws STS_ConvertException {
        prepareParams(params);
        makeDirectory(direcPath);

        if (params.containsKey(STS_Setting.FILE_PATTERN_KEY)) {
            regex = params.get(STS_Setting.FILE_PATTERN_KEY);
        }

        //Detect add column from file name;
        String addColumns = parseFileName(file.getName(), separator);

        File tmpFile = null;
        try {
            //Decode excel file to temp xml file
            tmpFile = File.createTempFile("pms2", "ipbackbone.tmp");
            decodeExcelToFile(file, tmpFile);

            //Parse xml file
            DocumentBuilderFactory docBuilderFactory =
                DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder =
                docBuilderFactory.newDocumentBuilder();

            Document doc = docBuilder.parse(tmpFile);
            List<String> data =
                parseTable(doc.getElementsByTagName("Table").item(0),
                           separator, addColumns);

            //Write to file
            File outFile = new File(direcPath, file.getName());
            writeParsedData(outFile, data);
        } catch (Exception e) {
            throw new STS_ConvertException(e);
        } finally {
            if (tmpFile != null) {
                tmpFile.delete();
            }
        }

        logger.info("Convert file: " + file.getName() + " success");
    }

    protected void writeParsedData(File outFile,
                                   List<String> data) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(outFile));

            // Append header to file
            String[] columnNames =
            { "TIME", "CONNECTION", "SOURCE", "DESTINATION", "IN", "OUT",
              "IN_BH", "OUT_BH", "TOTAL TRAFFIC", "MAX TRAFFIC", "COVERAGE" };
            List<String> list = getFileHeaderInfo(columnNames, separator);
            for (String s : list) {
                writer.write(commentChar + s);
                writer.newLine();
            }

            if (data != null && data.size() > 0) {
                for (String s : data) {
                    writer.write(s);
                    writer.newLine();
                }
            }
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    protected String parseFileName(String fileName,
                                   String separator) throws STS_ConvertException {
        Pattern objPattern = Pattern.compile(regex);
        Matcher m = objPattern.matcher(fileName);
        if (m.matches()) {
            StringBuffer buf = new StringBuffer();
            //ID (Backbone or Traffic)
            buf.append(m.group(2));
            buf.append(separator);
            //From Node
            buf.append(m.group(3));
            buf.append(separator);
            //To Node
            buf.append(m.group(4));
            return buf.toString();
        } else {
            throw new STS_ConvertException("Filename " + fileName +
                                       " not match pattern");
        }
    }

    protected void decodeExcelToFile(File source,
                                     File dest) throws FileNotFoundException,
                                                       IOException {
        BufferedWriter writer = null;
        try {
            String s = new ExcelReader(new FileReader(source)).readAll();
            writer = new BufferedWriter(new FileWriter(dest));
            writer.write(s);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    private List<String> parseTable(Node table, String separator,
                                    String addColumns) {
        NodeList rows = table.getChildNodes();
        int length = rows.getLength();

        List<String> listOfRow = new ArrayList<String>();
        for (int i = 0; i < length; i++) {
            //Detect row have valid data
            if (rows.item(i).getChildNodes().getLength() == 25) {
                String s = parseRow(rows.item(i), separator, addColumns);
                if (s != null) {
                    listOfRow.add(s);
                }
            }
        }
        return listOfRow;
    }

    private String parseRow(Node row, String separator, String addColumn) {
        if (row.getNodeType() == Node.ELEMENT_NODE) {
            NodeList cells = row.getChildNodes();

            int length = cells.getLength();
            String s;
            StringBuffer data = new StringBuffer();
            int timeIdx = 0;
            int lastTimeIdx = 0;
            for (int i = 0; i < length; i++) {
                s = getValueOfCell(cells.item(i));
                //Detect and remove cell have don't have data
                if (!s.equalsIgnoreCase("#")) {
                    data.append(s);
                    data.append(separator);
                }
            }

            //Remove total and average row
            if (data.length() > 0 && (data.indexOf("Total") == -1) &&
                (data.indexOf("Average") == -1)) {
                //Remove trailing time; Example 11:40 AM( - 11:50 AM)
                timeIdx = data.indexOf("M");
                lastTimeIdx = data.lastIndexOf("M");

                if (timeIdx > 0) {
                    data.delete(timeIdx - 2, lastTimeIdx - 2);
                }

                //Add column
                if (addColumn != null) {
                    data.insert(data.indexOf(separator),
                                separator + addColumn);
                }

                //Remove trailing separator
                return data.deleteCharAt(data.length() - 1).toString();
            }
        }

        return null;
    }

    private String getValueOfCell(Node cell) {
        if (cell == null || cell.getNodeType() != Node.ELEMENT_NODE) {
            return "#"; //Cell don't have data
        } else {
            //Cell have valid data include null
            if (cell.getChildNodes().getLength() == 3) {
                return cell.getTextContent().replaceAll(",", "").trim();
            } else {
                return "#"; //Other cell, don't have data
            }
        }
    }

    protected static class ExcelReader {
        private BufferedReader bReader = null;

        public ExcelReader(FileReader reader) {
            bReader = new BufferedReader(reader);
        }

        public String readAll() throws IOException {
            try {
                StringBuffer sbuf = new StringBuffer();
                int r = 0;
                do {
                    r = read();
                    if (r > -1) {
                        sbuf.append((char)r);
                    }
                } while (r > -1);
                return sbuf.toString();
            } finally {
                bReader.close();
            }
        }


        public int read() throws IOException {
            bReader.mark(4);
            char[] buf = new char[4];
            int cnt = bReader.read(buf, 0, 4);

            if (cnt == -1) {
                return cnt;
            } else if (cnt == 4 && buf[0] == '&' && buf[1] == '#') {
                bReader.reset();
                bReader.skip(4);
                return Integer.parseInt(new String(buf, 2, 2));
            } else {
                bReader.reset();
                return bReader.read();
            }
        }
    }
}
