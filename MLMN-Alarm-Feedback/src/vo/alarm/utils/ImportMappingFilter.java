package vo.alarm.utils;

public class ImportMappingFilter {

	private String rawTable;
	private String status;
	private String tableColumn;
	private String fileColumnHeader;
	
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
	
	  public String getFilePattern() {
			return filePattern;
		}

		public void setFilePattern(String filePattern) {
			this.filePattern = filePattern;
		}

		private String filePattern;
}
