package vn.com.vhc.sts.cni.info;

public class STS_MappingInfo {

    private String tableColumn = "";
    private String fileColumn = "-1";
    private String dataType = "";
    private String dataFormat = "";
    private String fileColumnHeader = "";


    public STS_MappingInfo() {

    }

    public STS_MappingInfo(String tableColumn, String fileColumn, String dataType,
                       String dataFormat, String fileColumnHeader) {
        this.tableColumn = tableColumn;
        this.fileColumn = fileColumn;
        this.dataType = dataType;
        this.dataFormat = dataFormat;
        this.fileColumnHeader = fileColumnHeader;
    }

    public String getTableColumn() {
        return this.tableColumn;
    }

    public void setTableColumn(String tableColumn) {
        this.tableColumn = tableColumn;
    }

    public String getFileColumn() {
        return this.fileColumn;
    }

    public void setFileColumn(String fileColumn) {
        this.fileColumn = fileColumn;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataFormat() {
        return this.dataFormat;
    }

    public void setDataFormat(String dataFormat) {
        this.dataFormat = dataFormat;
    }

    public void setFileColumnHeader(String columnHeader) {
        this.fileColumnHeader = columnHeader;
    }

    public String getFileColumnHeader() {
        return this.fileColumnHeader;
    }
}
