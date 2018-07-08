package vo.alarm.utils;

public class RawFileFilter {

	private String startDate; 
	private String endDate;
	private String rawTable;
	private String nodeName;
	private String fileName;
	private int convertFlag;
	private int importFlag;
	private int startRecord;
	private int endRecord;
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate.trim();
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate.trim();
	}
	public String getRawTable() {
		return rawTable;
	}
	public void setRawTable(String rawTable) {
		this.rawTable = rawTable.trim();
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName.trim();
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName.trim();
	}
	public int getConvertFlag() {
		return convertFlag;
	}
	public void setConvertFlag(int convertFlag) {
		this.convertFlag = convertFlag;
	}
	public int getImportFlag() {
		return importFlag;
	}
	public void setImportFlag(int importFlag) {
		this.importFlag = importFlag;
	}
	public int getStartRecord() {
		return startRecord;
	}
	public void setStartRecord(int startRecord) {
		this.startRecord = startRecord;
	}
	public int getEndRecord() {
		return endRecord;
	}
	public void setEndRecord(int endRecord) {
		this.endRecord = endRecord;
	}
}