package vo.alarm.utils;

public class ProvinceFilter {
	private String region;
	private String code;
	private String district;
	private String location;
	private String province;
	private String branch;
	
	public void setRegion(String region){
		this.region=region.trim();
	}
	public void setCode(String code){
		this.code=code.trim();
	}
	public void setDistrict(String district){
		this.district=district.trim();
	}
	public void setLocation(String location){
		this.location=location.trim();
	}
	public void setProvince(String province){
		this.province=province.trim();
	}
	public void setBranch(String branch){
		this.branch=branch.trim();
	}
	
	public String getRegion(){
		return region;
	}
	public String getCode(){
		return code;
	}
	public String getDistrict(){
		return district;
	}
	public String getLocation(){
		return location;
	}
	public String getProvince(){
		return province;
	}
	public String getBranch(){
		return branch;
	}

}
