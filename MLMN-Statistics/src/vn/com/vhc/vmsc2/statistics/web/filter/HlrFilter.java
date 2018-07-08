package vn.com.vhc.vmsc2.statistics.web.filter;

public class HlrFilter {
	
	private String hlrid;
	private String vendor;
	private String location;
	
	public String getHlrid(){
		return hlrid;
	}
	
	public String getVendor(){
		return vendor;
	}
	
	public String getLocation(){
		return location;
	}
	
	public void setHlrid(String hlrid){
		this.hlrid=hlrid.trim();
	}
	
	public void setVendor(String vendor){
		this.vendor=vendor.trim();
	}
	
	public void setLocation(String location){
		this.location=location.trim();
	}
}
