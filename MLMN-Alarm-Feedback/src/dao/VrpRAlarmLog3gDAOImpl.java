package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.VrpRAlarmLog3g;

public class VrpRAlarmLog3gDAOImpl extends SqlMapClientDaoSupport implements
		VrpRAlarmLog3gDAO {

	/**
	 * This method was generated by Apache iBATIS ibator. This method
	 * corresponds to the database table V_RP_R_ALARM_LOG_3G
	 * 
	 * @ibatorgenerated Fri Jan 11 12:29:59 ICT 2013
	 */
	public VrpRAlarmLog3gDAOImpl() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VrpRAlarmLog3g> getRAlarmLog3G(String vendor,
			String conAlarmName, String conbsc, String consite, String condip,
			String startTime, String bscid, String siteCell, String dip,
			String alarmType, String alarmClass, String alarmName,
			String alarmId, String status, int order, String column,
			String endTime) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("P_VENDOR", vendor);
		map.put("p_CON_ALARM_NAME", conAlarmName);
		map.put("P_CONBSC", conbsc);
		map.put("P_CONSITE", consite);
		map.put("P_CONDIP", condip);
		map.put("P_SDATE", startTime);
		map.put("P_BSCID", bscid);
		map.put("P_SITE", siteCell);
		map.put("P_DIP", dip);
		map.put("P_ALARM_TYPE", alarmType);
		map.put("P_ALARM_CLASS", alarmClass);
		map.put("P_ALARM_NAME", alarmName);
		map.put("P_ALARM_ID", alarmId);
		map.put("P_STATUS", status);
		map.put("P_COLUMN", column);
		map.put("P_ORDER", order);
		map.put("P_EDATE", endTime);

		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList(
				"V_RP_R_ALARM_LOG_3G.getRAlarmLog3G", map);

	}
}