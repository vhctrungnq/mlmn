package vo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailLostConfig3g {
	private Integer id;
	private String alarm;
	private String rncid;
	private String alarmType;
	private String alarmLevel;
	private String alarmClass;
	private String alarmInfo;
	private Integer tgMD;
	private Date sdate;
	private Date edate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAlarm() {
		return alarm;
	}
	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}
	
	public String getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}
	
	public String getAlarmClass() {
		return alarmClass;
	}
	public void setAlarmClass(String alarmClass) {
		this.alarmClass = alarmClass;
	}
	public Integer getTgMD() {
		return tgMD;
	}
	public void setTgMD(Integer tgMD) {
		this.tgMD = tgMD;
	}
	public Date getSdate() {
		return sdate;
	}
	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}
	public Date getEdate() {
		return edate;
	}
	public void setEdate(Date edate) {
		this.edate = edate;
	}
	public String getSDateStr()
    {
    	if (sdate!=null)
    		return (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(sdate));
    	return null;
    }
    public String getEDateStr()
    {
    	if (edate!=null)
    		return (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(edate));
    		
    	return null;
    }
	public String getAlarmInfo() {
		return alarmInfo;
	}
	public void setAlarmInfo(String alarmInfo) {
		this.alarmInfo = alarmInfo;
	}
	public String getRncid() {
		return rncid;
	}
	public void setRncid(String rncid) {
		this.rncid = rncid;
	}
	public String getAlarmLevel() {
		return alarmLevel;
	}
	public void setAlarmLevel(String alarmLevel) {
		this.alarmLevel = alarmLevel;
	}
}
