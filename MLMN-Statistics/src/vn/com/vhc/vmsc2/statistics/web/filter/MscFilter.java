package vn.com.vhc.vmsc2.statistics.web.filter;

public class MscFilter {
	private String mscid;
	private String vendor;
	private String location;
	private String type;
	

	public String getMscid() {
		return mscid;
	}

	public void setMscid(String mscid) {
		this.mscid = mscid.trim();
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor.trim();
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location.trim();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
