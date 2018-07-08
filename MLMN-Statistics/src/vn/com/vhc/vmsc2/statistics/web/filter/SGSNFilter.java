package vn.com.vhc.vmsc2.statistics.web.filter;

public class SGSNFilter {
	private String region;
	private String sgsnid;
	private String sgsnName;

	public String getSgsnid() {
		return sgsnid;
	}

	public void setSgsnid(String sgsnid) {
		this.sgsnid = sgsnid.trim();
	}
	
	public String getSgsnName() {
		return sgsnName;
	}

	public void setSgsnName(String sgsnName) {
		this.sgsnName = sgsnName.trim();
	}
	
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region.trim();
	}
}
