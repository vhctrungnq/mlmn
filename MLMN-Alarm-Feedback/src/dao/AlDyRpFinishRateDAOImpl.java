package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vo.AlDyRpFinishRate;
import vo.SYS_PARAMETER;

public class AlDyRpFinishRateDAOImpl extends SqlMapClientDaoSupport implements AlDyRpFinishRateDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_DY_RP_FINISH_RATE
     *
     * @ibatorgenerated Mon Dec 17 14:53:02 ICT 2012
     */
    public AlDyRpFinishRateDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_DY_RP_FINISH_RATE
     *
     * @ibatorgenerated Mon Dec 17 14:53:02 ICT 2012
     */
    public int deleteByPrimaryKey(Integer id) {
        AlDyRpFinishRate key = new AlDyRpFinishRate();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("AL_DY_RP_FINISH_RATE.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_DY_RP_FINISH_RATE
     *
     * @ibatorgenerated Mon Dec 17 14:53:02 ICT 2012
     */
    public void insert(AlDyRpFinishRate record) {
        getSqlMapClientTemplate().insert("AL_DY_RP_FINISH_RATE.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_DY_RP_FINISH_RATE
     *
     * @ibatorgenerated Mon Dec 17 14:53:02 ICT 2012
     */
    public void insertSelective(AlDyRpFinishRate record) {
        getSqlMapClientTemplate().insert("AL_DY_RP_FINISH_RATE.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_DY_RP_FINISH_RATE
     *
     * @ibatorgenerated Mon Dec 17 14:53:02 ICT 2012
     */
    public AlDyRpFinishRate selectByPrimaryKey(Integer id) {
        AlDyRpFinishRate key = new AlDyRpFinishRate();
        key.setId(id);
        AlDyRpFinishRate record = (AlDyRpFinishRate) getSqlMapClientTemplate().queryForObject("AL_DY_RP_FINISH_RATE.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_DY_RP_FINISH_RATE
     *
     * @ibatorgenerated Mon Dec 17 14:53:02 ICT 2012
     */
    public int updateByPrimaryKeySelective(AlDyRpFinishRate record) {
        int rows = getSqlMapClientTemplate().update("AL_DY_RP_FINISH_RATE.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table AL_DY_RP_FINISH_RATE
     *
     * @ibatorgenerated Mon Dec 17 14:53:02 ICT 2012
     */
    public int updateByPrimaryKey(AlDyRpFinishRate record) {
        int rows = getSqlMapClientTemplate().update("AL_DY_RP_FINISH_RATE.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<AlDyRpFinishRate> distinctSystem(String date, String startDate, String endDate, String startMinutes, String endMinutes){
    	Map<String, Object> parms = new HashMap<String, Object>();
    	parms.put("P_DATE", date);
    	parms.put("P_START_DATE", startDate);
    	parms.put("P_END_DATE", endDate);
    	parms.put("P_START_MINUTES", startMinutes);
    	parms.put("P_END_MINUTES", endMinutes);
		parms.put("P_DATA", null);	
		return (List<AlDyRpFinishRate>) getSqlMapClientTemplate().queryForList("AL_DY_RP_FINISH_RATE.distinctSystem", parms);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<AlDyRpFinishRate> tyLeTheoPTMang(String system, String alarmClass, String date, String startDate, String endDate, String startMinutes, String endMinutes){
    	Map<String, Object> parms = new HashMap<String, Object>();
    	parms.put("P_SYSTEM", system);
    	parms.put("P_ALARM_CLASS", alarmClass);
    	parms.put("P_DATE", date);
    	parms.put("P_START_DATE", startDate);
    	parms.put("P_END_DATE", endDate);
    	parms.put("P_START_MINUTES", startMinutes);
    	parms.put("P_END_MINUTES", endMinutes);
		parms.put("P_DATA", null);	
		return (List<AlDyRpFinishRate>) getSqlMapClientTemplate().queryForList("AL_DY_RP_FINISH_RATE.tyLeTheoPTMang", parms);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<AlDyRpFinishRate> distinctAlarmClass(String system){
    	Map<String, Object> parms = new HashMap<String, Object>();
    	parms.put("P_SYSTEM", system);
		parms.put("P_DATA", null);	
		return (List<AlDyRpFinishRate>) getSqlMapClientTemplate().queryForList("AL_DY_RP_FINISH_RATE.distinctAlarmClass", parms);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<SYS_PARAMETER> titleFormFinishRateCurrent(){
    	
    	Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_DATA", null);
    	return getSqlMapClientTemplate().queryForList("AL_DY_RP_FINISH_RATE.titleFormFinishRateCurrent", parms);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<SYS_PARAMETER> titleFormFinishRateOther(){
    	
    	Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_DATA", null);
    	return getSqlMapClientTemplate().queryForList("AL_DY_RP_FINISH_RATE.titleFormFinishRateOther", parms);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<SYS_PARAMETER> autoRefreshForFinishRate(){
    	
    	Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_DATA", null);
    	return getSqlMapClientTemplate().queryForList("AL_DY_RP_FINISH_RATE.autoRefreshForFinishRate", parms);
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<SYS_PARAMETER> khoangTgKetThuc(){
    	
    	Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_DATA", null);
    	return getSqlMapClientTemplate().queryForList("AL_DY_RP_FINISH_RATE.khoangTgKetThuc", parms);
    }
}