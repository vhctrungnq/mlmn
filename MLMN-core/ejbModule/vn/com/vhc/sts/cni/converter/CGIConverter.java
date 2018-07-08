package vn.com.vhc.sts.cni.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import vn.com.vhc.sts.cni.STS_BasicConverter;
import vn.com.vhc.sts.util.STS_Util;
import vn.com.vhc.sts.util.exceptions.STS_ConvertException;



public class CGIConverter extends STS_BasicConverter {

    private static Logger logger =
        Logger.getLogger(CGIConverter.class.getName());

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

            String time = this.getTime(file.getName());
            String header = "";
            String value = "";
            int blockCount = 0;
            int count = 6;
            boolean isRead = true;
            boolean writeHeader = false;

            String line = "";
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue;
                }
                isRead = !(blockCount == 0 && line.indexOf("CGI") == -1);
                if (!isRead) {
                    continue;
                }
                blockCount++;
                if (blockCount % 2 == 0) {
                    if (value.length() > 0) {
                        value += separator;
                    }
                    line = line.replace("'", "");
                    value += getLine(line);
                } else {
                    if (writeHeader == false) {
                        if (header.length() > 0) {
                            header += separator;
                        }
                        header += getLine(line);
                    }
                }

                if (blockCount == count) {
                    if (writeHeader == false) {
                        header =
                                "TIME" + separator + "BSCID" + separator + header;
                        List<String> list =
                            getFileHeaderInfo(header.split(separator),
                                              separator);
                        for (String s : list) {
                            writer.write(commentChar + s);
                            writer.newLine();
                        }
                        writeHeader = true;
                    }

                    int columnMore =
                        header.split(separator).length - value.split(separator).length;
                    if (columnMore > 0) {
                        for (int i = 0; i < columnMore - 1; i++) {
                            if (value.length() > 0) {
                                value += separator;
                            }
                            value += "";
                        }
                    }
                    value = time + separator + nodeName + separator + value;
                    writer.write(value);
                    writer.newLine();

                    blockCount = 0;
                    value = "";
                }
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

    private String getLine(String data) {
        String s = data;
        if (s.length() > 0) {
            String a[] = data.split(" ");
            s = "";
            for (int i = 0; i < a.length; i++) {
                if (a[i].trim().length() == 0) {
                    continue;
                }
                if (s.length() > 0) {
                    s += separator;
                }
                s += a[i].trim();
            }
        }
        return s;
    }

    private String getTime(String fileName) {
        String regex = "(CGI)[-_](.*)([0-9]{4}_[0-9]{6})(.*)";
        Pattern timePattern = Pattern.compile(regex);
        Matcher m = timePattern.matcher(fileName.trim());

        String time = "";
        if (m.find()) {
            time = m.group(3).trim();
        }

        return STS_Util.getTime(time, "HHmm_yyMMdd", "yyyy-MM-dd HH:mm");
    }
}
