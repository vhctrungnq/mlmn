package dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.AlAlarmTypes;
import vo.AlAlarmWorkDetails;

public class AlAlarmWorkDetailsDAOImpl extends SqlMapClientDaoSupport implements AlAlarmWorkDetailsDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_ALARM_WORKS_DETAIL
     *
     * @ibatorgenerated Thu Apr 07 14:45:15 ICT 2016
     */
    public AlAlarmWorkDetailsDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_ALARM_WORKS_DETAIL
     *
     * @ibatorgenerated Thu Apr 07 14:45:15 ICT 2016
     */
    public void insert(AlAlarmWorkDetails record) {
        getSqlMapClientTemplate().insert("AL_ALARM_WORKS_DETAIL.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_ALARM_WORKS_DETAIL
     *
     * @ibatorgenerated Thu Apr 07 14:45:15 ICT 2016
     */
    public void insertSelective(AlAlarmWorkDetails record) {
        getSqlMapClientTemplate().insert("p_type.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<AlAlarmWorkDetails> getUCDH(String function, String type, String startTime, String endTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_function", function);
		map.put("p_type", type);
		map.put("p_start_time", startTime);
		map.put("p_end_time", endTime);
		map.put("p_data", null);
		return getSqlMapClientTemplate().queryForList("AL_ALARM_WORKS_DETAIL.getUCDH", map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AlAlarmWorkDetails> getAllProvince(String startTime, String endTime){
		Map<String, Object> parms = new HashMap<String, Object>();
    	parms.put("p_start_time", startTime);
    	parms.put("p_end_time", endTime);
    	parms.put("p_data", null);
		return getSqlMapClientTemplate().queryForList("AL_ALARM_WORKS_DETAIL.getAllProvince", parms);
	} 

	@SuppressWarnings("unchecked")
	@Override
	public List<AlAlarmWorkDetails> getAllTeam(String startTime, String endTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_start_time", startTime);
		map.put("p_end_time", endTime);
		map.put("p_data", null);
		return getSqlMapClientTemplate().queryForList("AL_ALARM_WORKS_DETAIL.getAllTeam", map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AlAlarmWorkDetails> getDay(String startTime, String endTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("p_start_time", startTime);
		map.put("p_end_time", endTime);
		map.put("p_data", null);
		return getSqlMapClientTemplate().queryForList("AL_ALARM_WORKS_DETAIL.getDay", map);
	}
}