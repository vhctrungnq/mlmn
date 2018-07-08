package vn.com.vhc.vmsc2.statistics.web.filter;

public class MessageCodesFilter {
	private String msgCode;
	public void setCode(String msgCode){
		this.msgCode=msgCode.trim();
	}
	public String getMsgCode(){
		return msgCode;
	}
	
}
