package vo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailLostConfig {
	private Integer id;
	private String alarm;
	private String site;
	private String alarmType;
	private String bscid;
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
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}
	public String getBscid() {
		return bscid;
	}
	public void setBscid(String bscid) {
		this.bscid = bscid;
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
}
