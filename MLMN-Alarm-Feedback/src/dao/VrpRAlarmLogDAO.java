package dao;

import java.util.List;

import vo.VrpRAlarmLog;

public interface VrpRAlarmLogDAO {

	List<VrpRAlarmLog> getRAlarmLog(String vendor, String conAlarmName,
			String conbsc, String consite, String condip, String startTime,
			String bscid, String siteCell, String dip, String alarmType,
			String mo, String alarmClass, String alarmName, String alarmId,
			String status, int order, String column, String endTime);
}