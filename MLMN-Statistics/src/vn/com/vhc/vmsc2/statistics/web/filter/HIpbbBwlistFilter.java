package vn.com.vhc.vmsc2.statistics.web.filter;

public class HIpbbBwlistFilter {
	private String direction;
	private String link;
	private Float bw;
	private String vendor;
	private String interfaceName; 
	private String ip;
	private String locationName;
	private Float localId;
	
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction.trim();
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link.trim();
	}
	public Float getBw() {
		return bw;
	}
	public void setBw(Float bw) {
		this.bw = bw;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public Float getLocalId() {
		return localId;
	}
	public void setLocalId(Float localId) {
		this.localId = localId;
	} 
	
	
}
