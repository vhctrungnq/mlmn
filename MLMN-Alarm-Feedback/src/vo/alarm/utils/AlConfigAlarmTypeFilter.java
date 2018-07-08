package vo.alarm.utils;

public class AlConfigAlarmTypeFilter {
	private String alarmType;
	private String rawTable;
	private String alarmBlockValue;
	private String alarmInfo;
	public String getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType.trim();
	}
	public String getRawTable() {
		return rawTable;
	}
	public void setRawTable(String rawTable) {
		this.rawTable = rawTable.trim();
	}
	public String getAlarmBlockValue() {
		return alarmBlockValue;
	}
	public void setAlarmBlockValue(String alarmBlockValue) {
		this.alarmBlockValue = alarmBlockValue.trim();
	}
	public String getAlarmInfo() {
		return alarmInfo;
	}
	public void setAlarmInfo(String alarmInfo) {
		this.alarmInfo = alarmInfo.trim();
	}	
}
