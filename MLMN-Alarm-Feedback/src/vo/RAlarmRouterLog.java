package vo;

import java.io.Serializable;
import java.util.Date;

public class RAlarmRouterLog implements Serializable {

    private Date createDate;

    private String ip;
    
    private String routerName;

    private Date sdate;

    private String alarmInfo;
    
    private String local;
    
    private String alarmLogId;

    private static final long serialVersionUID = 1L;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getSdate() {
		return sdate;
	}

	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}

	public String getAlarmInfo() {
		return alarmInfo;
	}

	public void setAlarmInfo(String alarmInfo) {
		this.alarmInfo = alarmInfo;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getAlarmLogId() {
		return alarmLogId;
	}

	public void setAlarmLogId(String alarmLogId) {
		this.alarmLogId = alarmLogId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getRouterName() {
		return routerName;
	}

	public void setRouterName(String routerName) {
		this.routerName = routerName;
	}
}