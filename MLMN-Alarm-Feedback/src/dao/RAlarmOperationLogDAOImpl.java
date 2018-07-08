package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vo.RAlarmOperationLog;

public class RAlarmOperationLogDAOImpl extends SqlMapClientDaoSupport implements RAlarmOperationLogDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_OPERATION_LOG
     *
     * @ibatorgenerated Tue Feb 11 15:37:34 ICT 2014
     */
    public RAlarmOperationLogDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_OPERATION_LOG
     *
     * @ibatorgenerated Tue Feb 11 15:37:34 ICT 2014
     */
    public int deleteByPrimaryKey(Integer id) {
        RAlarmOperationLog key = new RAlarmOperationLog();
        key.setId(id);
        int rows = getSqlMapClientTemplate().delete("R_ALARM_OPERATION_LOG.ibatorgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_OPERATION_LOG
     *
     * @ibatorgenerated Tue Feb 11 15:37:34 ICT 2014
     */
    public void insert(RAlarmOperationLog record) {
        getSqlMapClientTemplate().insert("R_ALARM_OPERATION_LOG.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_OPERATION_LOG
     *
     * @ibatorgenerated Tue Feb 11 15:37:34 ICT 2014
     */
    public void insertSelective(RAlarmOperationLog record) {
        getSqlMapClientTemplate().insert("R_ALARM_OPERATION_LOG.ibatorgenerated_insertSelective", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_OPERATION_LOG
     *
     * @ibatorgenerated Tue Feb 11 15:37:34 ICT 2014
     */
    public RAlarmOperationLog selectByPrimaryKey(Integer id) {
        RAlarmOperationLog key = new RAlarmOperationLog();
        key.setId(id);
        RAlarmOperationLog record = (RAlarmOperationLog) getSqlMapClientTemplate().queryForObject("R_ALARM_OPERATION_LOG.ibatorgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_OPERATION_LOG
     *
     * @ibatorgenerated Tue Feb 11 15:37:34 ICT 2014
     */
    public int updateByPrimaryKeySelective(RAlarmOperationLog record) {
        int rows = getSqlMapClientTemplate().update("R_ALARM_OPERATION_LOG.ibatorgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table R_ALARM_OPERATION_LOG
     *
     * @ibatorgenerated Tue Feb 11 15:37:34 ICT 2014
     */
    public int updateByPrimaryKey(RAlarmOperationLog record) {
        int rows = getSqlMapClientTemplate().update("R_ALARM_OPERATION_LOG.ibatorgenerated_updateByPrimaryKey", record);
        return rows;
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public List<RAlarmOperationLog> getOperationLogDetail(
			String startDate, 
			String endDate, 
			String vendor,
			String neType,
			String neName,
			String user,
			Integer startRecord, 
			Integer endRecord, 
			String sortfield, 
			String sortorder, 
			String strWhere) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_START_DATE", startDate);
		parms.put("P_END_DATE", endDate);
		parms.put("P_VENDOR", vendor);
		parms.put("P_NE_TYPE", neType);
		parms.put("P_NE_NAME", neName);
		parms.put("P_USER", user);
		parms.put("P_START_RECORD", startRecord);
		parms.put("P_END_RECORD", endRecord);
		parms.put("P_SOFT_FIELD", sortfield);
		parms.put("P_SOFT_ORDER", sortorder);
		parms.put("P_STR_WHERE", strWhere);
		parms.put("P_DATA", null);	
		return getSqlMapClientTemplate().queryForList("R_ALARM_OPERATION_LOG.getOperationLogDetail", parms);
	}
    
    @Override
    public int countOperationLogDetail(
    		String startDate, 
			String endDate, 
			String vendor,
			String neType,
			String neName,
			String user,
			String strWhere){
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_START_DATE", startDate);
    	map.put("P_END_DATE", endDate);
    	map.put("P_VENDOR", vendor);
    	map.put("P_NE_TYPE", neType);
    	map.put("P_NE_NAME", neName);
    	map.put("P_USER", user);
    	map.put("P_STR_WHERE", strWhere);
    	map.put("P_DATA", null);
    	getSqlMapClientTemplate().queryForObject("R_ALARM_OPERATION_LOG.countOperationLogDetail", map);
    	Integer record = (Integer) map.get("P_DATA");
    	return record;
    }
    
    // Tab tong hop
    @SuppressWarnings("unchecked")
	@Override
	public List<RAlarmOperationLog> getOperationLogTotal(
			String startDate, 
			String endDate, 
			String vendor,
			String neType,
			String neName,
			String user,
			Integer startRecord, 
			Integer endRecord, 
			String sortfield, 
			String sortorder, 
			String strWhere) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_START_DATE", startDate);
		parms.put("P_END_DATE", endDate);
		parms.put("P_VENDOR", vendor);
		parms.put("P_NE_TYPE", neType);
		parms.put("P_NE_NAME", neName);
		parms.put("P_USER", user);
		parms.put("P_START_RECORD", startRecord);
		parms.put("P_END_RECORD", endRecord);
		parms.put("P_SOFT_FIELD", sortfield);
		parms.put("P_SOFT_ORDER", sortorder);
		parms.put("P_STR_WHERE", strWhere);
		parms.put("P_DATA", null);	
		return getSqlMapClientTemplate().queryForList("R_ALARM_OPERATION_LOG.getOperationLogTotal", parms);
	}
    
    @Override
    public int countOperationLogTotal(
    		String startDate, 
			String endDate, 
			String vendor,
			String neType,
			String neName,
			String user,
			String strWhere){
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("P_START_DATE", startDate);
    	map.put("P_END_DATE", endDate);
    	map.put("P_VENDOR", vendor);
    	map.put("P_NE_TYPE", neType);
    	map.put("P_NE_NAME", neName);
    	map.put("P_USER", user);
    	map.put("P_STR_WHERE", strWhere);
    	map.put("P_DATA", null);
    	getSqlMapClientTemplate().queryForObject("R_ALARM_OPERATION_LOG.countOperationLogTotal", map);
    	Integer record = (Integer) map.get("P_DATA");
    	return record;
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public List<RAlarmOperationLog> getUserForOperationLog() {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_DATA", null);	
		return getSqlMapClientTemplate().queryForList("R_ALARM_OPERATION_LOG.getUserForOperationLog", parms);
	}
    
    
}