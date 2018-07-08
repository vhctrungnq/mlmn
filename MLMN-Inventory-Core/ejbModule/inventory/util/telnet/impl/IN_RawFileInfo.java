package inventory.util.telnet.impl;

import inventory.utils.log.IN_DBLogParam;

import java.sql.Date;
import java.sql.Timestamp;



public class IN_RawFileInfo implements IN_DBLogParam {
    boolean illegalFile;
    private int patternId;
    private String directory;
    private String fileName;
    private Date day;
    private int hour;
    private String nodeName;
    private String rawTable;
    private Timestamp downloadTime;
    private String statusCode;
    private long fileSize;

    public IN_RawFileInfo() {
        super();
    }

    public String[] getDMLs() {
        //Nead to implements log to database
        return new String[0];
    }

    public void setIllegalFile(boolean illegalFile) {
        this.illegalFile = illegalFile;
    }

    public boolean isIllegalFile() {
        return illegalFile;
    }

    public void setPatternId(int patternId) {
        this.patternId = patternId;
    }

    public int getPatternId() {
        return patternId;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public String getDirectory() {
        return directory;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Date getDay() {
        return day;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getHour() {
        return hour;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setRawTable(String rawTable) {
        this.rawTable = rawTable;
    }

    public String getRawTable() {
        return rawTable;
    }

    public void setDownloadTime(Timestamp downloadTime) {
        this.downloadTime = downloadTime;
    }

    public Timestamp getDownloadTime() {
        return downloadTime;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public long getFileSize() {
        return fileSize;
    }
}
