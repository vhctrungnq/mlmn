package vn.com.vhc.vmsc2.statistics.web.filter;

public class KpiMappingFilter {
	private String reportName;
	private String reportNameColumn;
	private String vendor;
	private String tableName;
	private String tableColumn;
	private String status;
	private String network;

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportNameColumn() {
		return reportNameColumn;
	}

	public void setReportNameColumn(String reportNameColumn) {
		this.reportNameColumn = reportNameColumn;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableColumn() {
		return tableColumn;
	}

	public void setTableColumn(String tableColumn) {
		this.tableColumn = tableColumn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

}
