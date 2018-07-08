package vn.com.vhc.vmsc2.statistics.web.filter;

public class SystemConfigFilter {
	private String paramName;
	private String configType;
	private String paramValue;
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName.trim();
	}
	public String getConfigType() {
		return configType;
	}
	public void setConfigType(String configType) {
		this.configType = configType.trim();
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue.trim();
	}	
}
