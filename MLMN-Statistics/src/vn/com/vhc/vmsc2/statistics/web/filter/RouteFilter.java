package vn.com.vhc.vmsc2.statistics.web.filter;

public class RouteFilter {

	private String routeid;
	private String fromMscid;
	private String toMscid;
	private String routename;

	public String getRoutename() {
		return routename;
	}

	public void setRoutename(String routename) {
		this.routename = routename.trim();
	}

	public void setRouteid(String routeid) {
		this.routeid = routeid.trim();
	}

	public void setFromMscid(String fromMscid) {
		this.fromMscid = fromMscid.trim();
	}

	public void setToMscid(String toMscid) {
		this.toMscid = toMscid.trim();
	}

	public String getRouteid() {
		return routeid;
	}

	public String getFromMscid() {
		return fromMscid;
	}

	public String getToMscid() {
		return toMscid;
	}

}