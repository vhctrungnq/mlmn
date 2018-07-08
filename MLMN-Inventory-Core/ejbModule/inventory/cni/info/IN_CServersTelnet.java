package inventory.cni.info;

import java.io.Serializable;

public class IN_CServersTelnet implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String sDate;
	private String eDate;
	private String sTime;
	private String eTime;
	private String sysDate;
	private Integer serverId;
	private String serverName;
	private String ipAddress;
	private Integer port;
	private String telnetUser;
	private String telnetPassword;
	private String loginTime;
	private String description;
	private String alarmName;
	private String type;
	private Integer ordering;
	private String function;
	private String deviceType;
	private String alarmType;
	private String command;
	private Integer count;
	private String node;
	private String promptUser;
	private String promptPass;
	private String promptCommand;
	private String status;
	private String module;
	private String taskInfo;
	
	
	public String getsDate() {
		return sDate;
	}
	public void setsDate(String sDate) {
		this.sDate = sDate;
	}
	public String geteDate() {
		return eDate;
	}
	public void seteDate(String eDate) {
		this.eDate = eDate;
	}
	public String getsTime() {
		return sTime;
	}
	public void setsTime(String sTime) {
		this.sTime = sTime;
	}
	public String geteTime() {
		return eTime;
	}
	public void seteTime(String eTime) {
		this.eTime = eTime;
	}
	public String getSysDate() {
		return sysDate;
	}
	public void setSysDate(String sysDate) {
		this.sysDate = sysDate;
	}
	public String getTaskInfo() {
		return taskInfo;
	}
	public void setTaskInfo(String taskInfo) {
		this.taskInfo = taskInfo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getServerId() {
		return serverId;
	}
	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
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
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAlarmName() {
		return alarmName;
	}
	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getOrdering() {
		return ordering;
	}
	public void setOrdering(Integer ordering) {
		this.ordering = ordering;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getNode() {
		return node;
	}
	public void setNode(String node) {
		this.node = node;
	}
	public String getPromptUser() {
		return promptUser;
	}
	public void setPromptUser(String promptUser) {
		this.promptUser = promptUser;
	}
	public String getPromptPass() {
		return promptPass;
	}
	public void setPromptPass(String promptPass) {
		this.promptPass = promptPass;
	}
	public String getPromptCommand() {
		return promptCommand;
	}
	public void setPromptCommand(String promptCommand) {
		this.promptCommand = promptCommand;
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

}