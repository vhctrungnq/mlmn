package dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.DetailLostConfig;
import vo.DetailLostConfig3g;
import vo.SYS_PARAMETER;
import vo.VAlRbLossConfig3G;
import vo.VAlRbLossConfigurationTK;
import vo.V_RB_LOSSCONFIG_3G;

public class VAlRbLossConfig3GDAOImpl extends SqlMapClientDaoSupport implements VAlRbLossConfig3GDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_AL_RB_LOSS_CONFIG3G
     *
     * @ibatorgenerated Wed Jan 16 15:58:45 ICT 2013
     */
    public VAlRbLossConfig3GDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_AL_RB_LOSS_CONFIG3G
     *
     * @ibatorgenerated Wed Jan 16 15:58:45 ICT 2013
     */
    public void insert(VAlRbLossConfig3G record) {
        getSqlMapClientTemplate().insert("V_AL_RB_LOSS_CONFIG3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_AL_RB_LOSS_CONFIG3G
     *
     * @ibatorgenerated Wed Jan 16 15:58:45 ICT 2013
     */
    public void insertSelective(VAlRbLossConfig3G record) {
        getSqlMapClientTemplate().insert("V_AL_RB_LOSS_CONFIG3G.ibatorgenerated_insertSelective", record);
    }
    
   /* @Override
	public V_RB_LOSSCONFIG_3G selectByPrimaryKey(int id) {
		V_RB_LOSSCONFIG_3G key = new V_RB_LOSSCONFIG_3G();
        key.setId(id);
        V_RB_LOSSCONFIG_3G record = (V_RB_LOSSCONFIG_3G) getSqlMapClientTemplate().queryForObject("V_AL_RB_LOSS_CONFIG3G.selectByPrimaryKey", key);
        return record;
	}
*/
	@Override
	public int delete(Date sdate, String rncid, String alarmLevel,
			int fmAlarmId) {
		V_RB_LOSSCONFIG_3G key = new V_RB_LOSSCONFIG_3G();
        key.setSdate(sdate);
        key.setRncid(rncid);
        key.setAlarmLevel(alarmLevel);
        key.setFmAlarmId(fmAlarmId);
        int rows = getSqlMapClientTemplate().delete("V_AL_RB_LOSS_CONFIG3G.delete", key);
        return rows;
	}

    @Override
   	public int deleteByPrimaryKey(Integer id) {
           Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_ID", id);
   		int rows = getSqlMapClientTemplate().delete("V_AL_RB_LOSS_CONFIG3G.deleteByPrimaryKey", parms);
   		return rows;
   	}
   	@SuppressWarnings("unchecked")
   	@Override
   	public V_RB_LOSSCONFIG_3G selectByPrimaryKey(int id) {
   		Map<String, Object> parms = new HashMap<String, Object>();
   		parms.put("P_ID",id);
   		parms.put("P_DATA", null);
   		List<V_RB_LOSSCONFIG_3G> recordList = getSqlMapClientTemplate().queryForList("V_AL_RB_LOSS_CONFIG3G.selectByPrimaryKey", parms);
   		V_RB_LOSSCONFIG_3G record = new V_RB_LOSSCONFIG_3G();
   		if (recordList.size()>0)
   		{
   			record = recordList.get(0);
   		}
           return record;
   	}
	@Override
	public V_RB_LOSSCONFIG_3G selectByMore(Date stime, String rncid,
			String alarmLevel, int alarmID) {
		V_RB_LOSSCONFIG_3G key = new V_RB_LOSSCONFIG_3G();
        key.setSdate(stime);
        key.setRncid(rncid);
        key.setAlarmLevel(alarmLevel);
        key.setFmAlarmId(alarmID);
        V_RB_LOSSCONFIG_3G record = (V_RB_LOSSCONFIG_3G) getSqlMapClientTemplate().queryForObject("V_AL_RB_LOSS_CONFIG3G.selectByPrimaryKey", key);
        return record;
	}

	@Override
	public int update(V_RB_LOSSCONFIG_3G vAlRbLossConfig3G) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_ID", vAlRbLossConfig3G.getId());
		parms.put("P_SDATE", vAlRbLossConfig3G.getSdate());
		parms.put("P_EDATE", vAlRbLossConfig3G.getEdate());
		parms.put("P_DDH_BAO_MCH", vAlRbLossConfig3G.getDdhBaoMch());
		parms.put("P_CAUSE_SDATE", vAlRbLossConfig3G.getCauseSdate());
		parms.put("P_CAUSE_EDATE", vAlRbLossConfig3G.getCauseEdate());
		parms.put("P_RNCID", vAlRbLossConfig3G.getRncid());
		parms.put("P_ALARM_LEVEL", vAlRbLossConfig3G.getAlarmLevel());
		parms.put("P_CELLID", vAlRbLossConfig3G.getCellid());
		parms.put("P_DVT_TEAM_PROCESS", vAlRbLossConfig3G.getDvtTeamProcess());
		parms.put("P_DVT_USER_PROCESS", vAlRbLossConfig3G.getDvtUserProcess());
		parms.put("P_CAUSEBY", vAlRbLossConfig3G.getCauseby());
		parms.put("P_ALARM_TYPE", vAlRbLossConfig3G.getAlarmType());
		parms.put("P_ACTION_PROCESS", vAlRbLossConfig3G.getActionProcess());
		parms.put("P_DESCRIPTION", vAlRbLossConfig3G.getDescription());
		parms.put("P_SHIFT_ID", vAlRbLossConfig3G.getShiftId());
		parms.put("P_FM_ALARM_ID", vAlRbLossConfig3G.getFmAlarmId());
		parms.put("P_CAUSEBY_SY", vAlRbLossConfig3G.getCausebySy());

        int rows = getSqlMapClientTemplate().update("V_AL_RB_LOSS_CONFIG3G.update", parms);
        return rows;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<V_RB_LOSSCONFIG_3G> getLossConfig3GList(String startTime,String endTime,
			String rncid, String alarmLevel, String cellid, int totalTime,
			int totalTimeEN, String dvtTeamProcess, String dvtUserProcess,
			String causeby, String statusKTMCH, String alarmType,
			String column, int order) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_SDATE", startTime);
		map.put("P_SDATE_END", endTime);
		map.put("P_RNCID", rncid);
		map.put("P_ALARM_LEVEL", alarmLevel);
		map.put("P_CELLID", cellid);
		map.put("P_DVT_TEAM_PROCESS", dvtTeamProcess);
		map.put("P_DVT_USER_PROCESS", dvtUserProcess);
		map.put("P_CAUSEBY", causeby);
		map.put("P_ALARM_TYPE", alarmType);
		map.put("P_DURATION", totalTime);
		map.put("P_DURATION_E", totalTimeEN);
		map.put("P_statusKTMCH", statusKTMCH);
		map.put("P_COLUMN", column);
		map.put("P_ORDER", order);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("V_AL_RB_LOSS_CONFIG3G.getLossConfig3GList", map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<V_RB_LOSSCONFIG_3G> getLossConfig3G(String startTime,
			String rncid, String alarmLevel, String cellid, int totalTime,
			int totalTimeEN, String dvtTeamProcess, String dvtUserProcess,
			String causeby, String statusKTMCH, String alarmType,
			String column, int order) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_SDATE", startTime);
		map.put("P_RNCID", rncid);
		map.put("P_ALARM_LEVEL", alarmLevel);
		map.put("P_CELLID", cellid);
		map.put("P_DVT_TEAM_PROCESS", dvtTeamProcess);
		map.put("P_DVT_USER_PROCESS", dvtUserProcess);
		map.put("P_CAUSEBY", causeby);
		map.put("P_ALARM_TYPE", alarmType);
		map.put("P_DURATION", totalTime);
		map.put("P_DURATION_E", totalTimeEN);
		map.put("P_statusKTMCH", statusKTMCH);
		map.put("P_COLUMN", column);
		map.put("P_ORDER", order);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("V_AL_RB_LOSS_CONFIG3G.getLossConfig3G", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SYS_PARAMETER> titleForm(String alarmType,String typeForm) {
		Map<String, Object> parms = new HashMap<String, Object>();
    	parms.put("P_NAME", alarmType);
    	parms.put("P_TYPE", typeForm);
		parms.put("P_DATA", null);
    	return getSqlMapClientTemplate().queryForList("V_AL_RB_LOSS_CONFIG3G.titleForm", parms);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DetailLostConfig> getDetail(String rncid, String alarmLevel,
			String timer, int order, String column) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_SDATE", timer);
		map.put("P_RNCID", rncid);
		map.put("P_ALARM_LEVEL", alarmLevel);
		map.put("P_COLUMN", column);
		map.put("P_ORDER", order);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("V_AL_RB_LOSS_CONFIG3G.getDetail", map);
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SYS_PARAMETER> titleFormDetail() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("V_AL_RB_LOSS_CONFIG3G.titleFormDetail", map);
	}

	@Override
	public int updateStatusViewByPrimaryKey(String netWork, String status,
			String id) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_ID", id);
		parms.put("P_STATUS_VIEW", status);
		parms.put("P_NETWORK", netWork);
	
        int rows = getSqlMapClientTemplate().update("V_AL_RB_LOSS_CONFIG3G.updateStatusViewByPrimaryKey", parms);
        return rows;
	}
	
}