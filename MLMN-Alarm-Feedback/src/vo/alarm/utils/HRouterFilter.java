package vo.alarm.utils;

public class HRouterFilter {
	private String routerId;
	private String routerName;
	
	public String getRouterName() {
		return routerName;
	}
	public void setRouterName(String routerName) {
		this.routerName = routerName.trim();
	}
	public String getRouterId() {
		return routerId;
	}
	public void setRouterId(String routerId) {
		this.routerId = routerId.trim();
	}
}
