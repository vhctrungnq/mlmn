package vo.alarm.utils;

public class FilePatternFilter {
	private String filePattern;
	private String rawTable;
	private String status;
	private String nodeType;
	private String convertClass;

	public String getFilePattern() {
		return filePattern;
	}

	public void setFilePattern(String filePattern) {
		this.filePattern = filePattern.trim();
	}

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
		this.status = status;
	}
	
	public String getNodeType() {
		return nodeType;
	}
	
	public void setNodeType(String nodeType){
		this.nodeType = nodeType.trim();
	}

	public String getConvertClass() {
		return convertClass;
	}

	public void setConvertClass(String convertClass) {
		this.convertClass = convertClass.trim();
	}
	
}
