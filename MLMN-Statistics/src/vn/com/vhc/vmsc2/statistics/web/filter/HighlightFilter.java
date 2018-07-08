package vn.com.vhc.vmsc2.statistics.web.filter;

public class HighlightFilter {
	private String id;
	public void setId(String id){
		this.id=id.trim();
	}
	public String getId(){
		return id;
	}
	private String kpi;
	public String getKpi() {
		return kpi;
	}
	public void setKpi(String kpi) {
		this.kpi = kpi;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	private String key;
}
