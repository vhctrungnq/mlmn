package vo.alarm.utils;

public class ImportMappingError {

	private String rawTable;
	private String status;
	private String tableColumn;
	private String fileColumnHeader;
	private String filePattern;
	private String fileColumn;
	private String dataType;
	private String dataFormat;
	
	public String getRawTable() {
		return rawTable;
	}
	public void setRawTable(String rawTable) {
		this.rawTable = rawTable.trim();
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status.trim();
	}
	public String getTableColumn() {
		return tableColumn;
	}
	public void setTableColumn(String tableColumn) {
		this.tableColumn = tableColumn.trim();
	}
	public String getFileColumnHeader() {
		return fileColumnHeader;
	}
	public void setFileColumnHeader(String fileColumnHeader) {
		this.fileColumnHeader = fileColumnHeader.trim();
	}
	
	public String getFileColumn() {
		return fileColumn;
	}
	public void setFileColumn(String fileColumn) {
		this.fileColumn = fileColumn;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getDataFormat() {
		return dataFormat;
	}
	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}
	public String getFilePattern() {
		return filePattern;
	}
	public void setFilePattern(String filePattern) {
		this.filePattern = filePattern;
	}

	

}
