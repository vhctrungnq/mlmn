package vn.com.vhc.vmsc2.statistics.web.filter;

public class Bsc3gFilter {
	private String bscid;
	private String vendor;
	private String team;
	private String dept;
	private String mscid;
	private String subTeam;
	private String locationName;
	public String getBscid(){
		return bscid;
	}

	public String getVendor(){
		return vendor;
	}
	
	public void setBscid(String bscid){
		this.bscid=bscid.trim();
	}
	
	public void setVendor(String vendor){
		this.vendor=vendor.trim();
	}
	private String region;
	
	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region.trim();
	}

	public String getMscid() {
		return mscid;
	}

	public void setMscid(String mscid) {
		this.mscid = mscid;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getSubTeam() {
		return subTeam;
	}

	public void setSubTeam(String subTeam) {
		this.subTeam = subTeam;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
}
