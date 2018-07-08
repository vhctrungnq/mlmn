package vn.com.vhc.vmsc2.statistics.web.filter;

public class SummaryLogFilter {
	private String startDate; // dd/MM/yyyy HH:mm
	private String endDate;
	private int startRecord;
	private int endRecord;
	private String result;
	private String sqlStatement;
	private String errorCode;
	private String procedureName;
	
	public void setStartDate(String startDate) {
		if(startDate != null)
			startDate = startDate.trim();
		this.startDate = startDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setEndDate(String endDate) {
		if(endDate != null)
			endDate = endDate.trim();
		this.endDate = endDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setStartRecord(int startRecord) {
		this.startRecord = startRecord;
	}

	public int getStartRecord() {
		return startRecord;
	}

	public void setEndRecord(int endRecord) {
		this.endRecord = endRecord;
	}

	public int getEndRecord() {
		return endRecord;
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode.trim();
	}

	public String getProcedureName() {
		return procedureName;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName.trim();
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result.trim();
	}
	
	public String getSqlStatement() {
		return sqlStatement;
	}

	public void setSqlStatement(String sqlStatement) {
		this.sqlStatement = sqlStatement.trim();
	}
}
