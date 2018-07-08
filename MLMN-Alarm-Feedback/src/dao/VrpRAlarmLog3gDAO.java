package dao;

import java.util.List;

import vo.VrpRAlarmLog3g;

public interface VrpRAlarmLog3gDAO {

	List<VrpRAlarmLog3g> getRAlarmLog3G(String vendor, String conAlarmName,
			String conbsc, String consite, String condip, String startTime,
			String bscid, String siteCell, String dip, String alarmType,
			String alarmClass, String alarmName, String alarmId, String status,
			int order, String column, String endTime);
}