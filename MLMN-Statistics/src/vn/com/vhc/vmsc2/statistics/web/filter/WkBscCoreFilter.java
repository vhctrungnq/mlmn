package vn.com.vhc.vmsc2.statistics.web.filter;
public class WkBscCoreFilter {
	private String bscid;
	private String week;
	private String year;
	
	public String getBscid(){
		return bscid;
	}
	public void setBscid(String bscid){
		this.bscid=bscid.trim();
	}
	
	public String getWeek(){
		return week;
	}
	public String getYear(){
		return year;
	}
	
	public void setWeek(String week){
		this.week=week.trim();
	}
	public void setYear(String year){
		this.year=year.trim();
	}
}
