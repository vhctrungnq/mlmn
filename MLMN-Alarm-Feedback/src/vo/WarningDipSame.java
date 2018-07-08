package vo;

import java.util.Date;

public class WarningDipSame {
	private String alarmName;
	private String bscid;
	private String device;
	private Date day;
	private Date edate;
	private String causeby;
	private String actionProcess;
	private Integer id;
	public String getAlarmName() {
		return alarmName;
	}
	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}

	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public Date getDay() {
		return day;
	}
	public void setDay(Date day) {
		this.day = day;
	}
	public String getCauseby() {
		return causeby;
	}
	public void setCauseby(String causeby) {
		this.causeby = causeby;
	}
	public String getActionProcess() {
		return actionProcess;
	}
	public void setActionProcess(String actionProcess) {
		this.actionProcess = actionProcess;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBscid() {
		return bscid;
	}
	public void setBscid(String bscid) {
		this.bscid = bscid;
	}
	public Date getEdate() {
		return edate;
	}
	public void setEdate(Date edate) {
		this.edate = edate;
	}

}
