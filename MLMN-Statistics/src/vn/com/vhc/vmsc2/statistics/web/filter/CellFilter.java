package vn.com.vhc.vmsc2.statistics.web.filter;

public class CellFilter {

	private String bscid;

	private String cellid;

	private String province;
	
	private String district;

	private String vendor;
	
	private String siteid;
	
	private String type;

	private int startRecord;

	private int endRecord;
	
	private String endDate;
	
	private String startDate;

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate.trim();
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate.trim();
	}

	public String getBscid() {
		return bscid;
	}

	public void setBscid(String bscid) {
		this.bscid = bscid.trim();
	}

	public String getCellid() {
		return cellid;
	}

	public void setCellid(String cellid) {
		this.cellid = cellid.trim();
	}

	public void setProvince(String province) {
		this.province = province.trim();
	}
	public String getProvince() {
		return province;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district.trim();
	}

	

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor.trim();
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

	public String getSiteid() {
		return siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid.trim();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type.trim();
	}
}
