package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.VRAlarmLog3G;


public class VRlAlarmLog3GDAOImpl extends SqlMapClientDaoSupport implements VRlAlarmLog3GDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_R_ALARM_LOG_3G
     *
     * @ibatorgenerated Wed Dec 12 10:33:01 ICT 2012
     */
    public VRlAlarmLog3GDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_R_ALARM_LOG_3G
     *
     * @ibatorgenerated Wed Dec 12 10:33:01 ICT 2012
     */
    public void insert(VRAlarmLog3G record) {
        getSqlMapClientTemplate().insert("V_R_ALARM_LOG_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_R_ALARM_LOG_3G
     *
     * @ibatorgenerated Wed Dec 12 10:33:01 ICT 2012
     */
    public void insertSelective(VRAlarmLog3G record) {
        getSqlMapClientTemplate().insert("V_R_ALARM_LOG_3G.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<VRAlarmLog3G> getCB3GListAtShift(Integer shiftid,int startRecord7, int endRecord7,
			String column, String order) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_SHIFT_ID", shiftid);
		map.put("P_StartRecord", startRecord7);
		map.put("P_EndRecord", endRecord7);
		map.put("P_column", column);
		map.put("P_order", order);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("V_R_ALARM_LOG_3G.getCB3GListAtShift", map);
	}

	@Override
	public int delete(String fmAlarmId, String rncid, String alarmLevel,
			String sdate) {
		Map<String, Object> parms = new HashMap<String, Object>();
		if (fmAlarmId!=null&& !fmAlarmId.equals(""))
		{
			parms.put("P_FM_ALARM_ID",Integer.parseInt(fmAlarmId));
		}
		else
			parms.put("P_FM_ALARM_ID", null);
		parms.put("P_RNCID", rncid);
		parms.put("P_ALARM_LEVEL", alarmLevel);
		parms.put("P_SDATE", sdate);
		int rows = getSqlMapClientTemplate().update("V_R_ALARM_LOG_3G.delete", parms);
        return rows;
	}

	@Override
	public VRAlarmLog3G selectByMore(String fmAlarmId, String rncid,
			String alarmLevel, String sdate) {
		Map<String, Object> parms = new HashMap<String, Object>();
		if (fmAlarmId!=null&& !fmAlarmId.equals(""))
		{
			parms.put("P_FM_ALARM_ID",Integer.parseInt(fmAlarmId));
		}
		else
			parms.put("P_FM_ALARM_ID", null);
		parms.put("P_RNCID", rncid);
		parms.put("P_ALARM_LEVEL", alarmLevel);
		parms.put("P_SDATE", sdate);
		parms.put("P_DATA", null);
		VRAlarmLog3G record = (VRAlarmLog3G) getSqlMapClientTemplate().queryForObject("V_R_ALARM_LOG_3G.selectByMore", parms);
	    return record;
	}

	/*@SuppressWarnings("unchecked")
	@Override
	public List<VRAlarmLog3G> getAlarmSameSystem(String rncid, String site,String sdate,String edate) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_RNCID", rncid);
		parms.put("P_ALARM_LEVEL", site);
		parms.put("P_SDATE", sdate);
		parms.put("P_EDATE", edate);
		parms.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("V_R_ALARM_LOG_3G.getAlarmSameSystem", parms);
	}*/

	@Override
	public int update(VRAlarmLog3G vAlAlarmLog) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_ID", vAlAlarmLog.getId());
		map.put("P_SDATE", vAlAlarmLog.getSdate());
		map.put("P_EDATE", vAlAlarmLog.getEdate());
		map.put("P_RNCID", vAlAlarmLog.getRncid());
		map.put("P_ALARM_LEVEL", vAlAlarmLog.getAlarmLevel());
		map.put("P_ALARM_NAME", vAlAlarmLog.getAlarmName());
		map.put("P_CAUSEBY", vAlAlarmLog.getCauseby());
		map.put("P_ACTION_PROCESS", vAlAlarmLog.getActionProcess());
		map.put("P_DVT_TEAM_PROCESS", vAlAlarmLog.getDvtTeamProcess());
		map.put("P_DVT_USER_PROCESS", vAlAlarmLog.getDvtUserProcess());
		map.put("P_SHIFT_ID", vAlAlarmLog.getShiftId());
        int rows = getSqlMapClientTemplate().update("V_R_ALARM_LOG_3G.update", map);
        return rows;
	}
	
	@Override
	public int countCB3GList(Integer shiftid) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_SHIFT_ID", shiftid);
		parms.put("P_DATA", null);
    	getSqlMapClientTemplate().queryForObject("V_R_ALARM_LOG_3G.countCB3GList", parms);
    	Integer record = (Integer) parms.get("P_DATA");
    	return record;
	}

	/*@SuppressWarnings("unchecked")
	@Override
	public List<VRAlarmLog3G> getAlarmSameSystem(String rncid,
			String alarmLevel, String alarmType, String sdate, String edate) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_RNCID", rncid);
		parms.put("P_ALARM_LEVEL", alarmLevel);
		parms.put("P_ALARM_TYPE", alarmType);
		parms.put("P_SDATE", sdate);
		parms.put("P_EDATE", edate);
		parms.put("P_DATA", null);	
		return getSqlMapClientTemplate().queryForList("V_R_ALARM_LOG_3G.getAlarmSameSystem", parms);
	}*/
	/*@SuppressWarnings("unchecked")
	@Override
	public List<VRAlarmLog3G> getALL3G(String day) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_DAY", day);
		parms.put("P_DATA", null);	
		return getSqlMapClientTemplate().queryForList("V_R_ALARM_LOG_3G.getALL3G", parms);
	}*/
	@SuppressWarnings("unchecked")
	@Override
	public List<VRAlarmLog3G> getAlarmSameSystem(String operator,
			String system, String alarmType, String sdate, String edate) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_RNCID", operator);
		parms.put("P_ALARM_LEVEL", system);
		parms.put("P_ALARM_TYPE", alarmType);
		parms.put("P_SDATE", sdate);
		parms.put("P_EDATE", edate);
		parms.put("P_DATA", null);	
		return getSqlMapClientTemplate().queryForList("V_R_ALARM_LOG_3G.getAlarmSameSystem", parms);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VRAlarmLog3G> getALL3G(String day, String alarmType,
			String statusKT, String bscidTK, String cellidTK) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_DAY", day);
		parms.put("P_ALARM_TYPE", alarmType);
		parms.put("P_STATUS", statusKT);
		parms.put("P_RNCID", bscidTK);
		parms.put("P_CELLID", cellidTK);
		parms.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("V_R_ALARM_LOG_3G.getALL3G", parms);
		
	}

	@Override
	public int updateShiftID(String id, String shiftId) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_ID", id);
		parms.put("P_SHIFT_ID", shiftId);
        int rows = getSqlMapClientTemplate().update("V_R_ALARM_LOG_3G.updateShiftID", parms);
        return rows;
	}

	
}