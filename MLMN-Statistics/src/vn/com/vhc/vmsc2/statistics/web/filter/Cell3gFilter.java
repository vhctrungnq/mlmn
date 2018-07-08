package vn.com.vhc.vmsc2.statistics.web.filter;

public class Cell3gFilter {

	private String bscid;

	private String cellid;

	private String province;

	private String vendor;
	
	private String siteid;

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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province.trim();
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor.trim();
	}

	public String getSiteid() {
		return siteid;
	}

	public void setSiteid(String siteid) {
		this.siteid = siteid.trim();
	}
}
