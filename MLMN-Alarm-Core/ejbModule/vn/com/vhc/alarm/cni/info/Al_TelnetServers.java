package vn.com.vhc.alarm.cni.info;

import java.util.Date;

public class Al_TelnetServers {
	private Integer id;
	private String vendor; 
	private String network; 
	private String neType; 
	private String ne; 
	private String ipAddress; 
	private String taskInfo;
	private Integer telnetPort;
	private String telnetUser;
	private String telnetPassword;
	private String telnetType;
	private Date   loginTime;
	private String telnetCommand;
	private String status;
	private String module; 
	private Integer count;
	private String description;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getNetwork() {
		return network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	public String getNeType() {
		return neType;
	}
	public void setNeType(String neType) {
		this.neType = neType;
	}
	public String getNe() {
		return ne;
	}
	public void setNe(String ne) {
		this.ne = ne;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getTaskInfo() {
		return taskInfo;
	}
	public void setTaskInfo(String taskInfo) {
		this.taskInfo = taskInfo;
	}
	public Integer getTelnetPort() {
		return telnetPort;
	}
	public void setTelnetPort(Integer telnetPort) {
		this.telnetPort = telnetPort;
	}
	public String getTelnetUser() {
		return telnetUser;
	}
	public void setTelnetUser(String telnetUser) {
		this.telnetUser = telnetUser;
	}
	public String getTelnetPassword() {
		return telnetPassword;
	}
	public void setTelnetPassword(String telnetPassword) {
		this.telnetPassword = telnetPassword;
	}
	public String getTelnetType() {
		return telnetType;
	}
	public void setTelnetType(String telnetType) {
		this.telnetType = telnetType;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public String getTelnetCommand() {
		return telnetCommand;
	}
	public void setTelnetCommand(String telnetCommand) {
		this.telnetCommand = telnetCommand;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
