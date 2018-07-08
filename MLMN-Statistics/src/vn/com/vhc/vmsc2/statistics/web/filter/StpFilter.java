package vn.com.vhc.vmsc2.statistics.web.filter;

public class StpFilter {
	private String stpid;
	private String vendor;
	private String location;

	public String getStpid() {
		return stpid;
	}

	public void setStpid(String stpid) {
		this.stpid = stpid.trim();
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
}
