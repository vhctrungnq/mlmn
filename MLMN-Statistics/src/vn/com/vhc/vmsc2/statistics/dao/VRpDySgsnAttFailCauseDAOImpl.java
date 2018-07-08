package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDySgsnAttFailCause;

public class VRpDySgsnAttFailCauseDAOImpl extends SqlMapClientDaoSupport implements VRpDySgsnAttFailCauseDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SGSN_ATT_FAIL_CAUSE
     *
     * @ibatorgenerated Mon Oct 14 15:53:17 ICT 2013
     */
    public VRpDySgsnAttFailCauseDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SGSN_ATT_FAIL_CAUSE
     *
     * @ibatorgenerated Mon Oct 14 15:53:17 ICT 2013
     */
    public void insert(VRpDySgsnAttFailCause record) {
        getSqlMapClientTemplate().insert("V_RP_DY_SGSN_ATT_FAIL_CAUSE.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SGSN_ATT_FAIL_CAUSE
     *
     * @ibatorgenerated Mon Oct 14 15:53:17 ICT 2013
     */
    public void insertSelective(VRpDySgsnAttFailCause record) {
        getSqlMapClientTemplate().insert("V_RP_DY_SGSN_ATT_FAIL_CAUSE.ibatorgenerated_insertSelective", record);
    }
    public List<VRpDySgsnAttFailCause> filterHourly(String tableName, String sgsnid,String startDate, String startHour, String endHour)
    {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_SGSNID", 		sgsnid);
    	parms.put("P_START_DATE", 		startDate);
    	parms.put("P_START_HOUR", 		startHour);
    	parms.put("P_END_HOUR", 		endHour);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDySgsnAttFailCause> record = getSqlMapClientTemplate().queryForList("V_RP_DY_SGSN_ATT_FAIL_CAUSE.filterHourly", parms);
        return record;
	}
    public List<VRpDySgsnAttFailCause> filterDaily(String tableName,String sgsnid,String startDate, String endDate) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_SGSNID", 		sgsnid);
    	parms.put("P_START_DATE", 		startDate);
    	parms.put("P_END_DATE", 		endDate);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDySgsnAttFailCause> record = getSqlMapClientTemplate().queryForList("V_RP_DY_SGSN_ATT_FAIL_CAUSE.filterDaily", parms);
        return record;
	}
    public List<VRpDySgsnAttFailCause> filterMonthly(String tableName,String sgsnid,String startMonth,String endMonth,String startYear,String endYear) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_SGSNID", 		sgsnid);
    	parms.put("P_START_MONTH", 		startMonth);
    	parms.put("P_END_MONTH", 		endMonth);
    	parms.put("P_START_YEAR", 		startYear);
    	parms.put("P_END_YEAR", 		endYear);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDySgsnAttFailCause> record = getSqlMapClientTemplate().queryForList("V_RP_DY_SGSN_ATT_FAIL_CAUSE.filterMonthly", parms);
        return record;
	}
    public List<VRpDySgsnAttFailCause> filterWeekly(String tableName,String sgsnid,String startWeek,String endWeek,String startYear,String endYear) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_SGSNID", 		sgsnid);
    	parms.put("P_START_WEEK", 		startWeek);
    	parms.put("P_END_WEEK", 		endWeek);
    	parms.put("P_START_YEAR", 		startYear);
    	parms.put("P_END_YEAR", 		endYear);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDySgsnAttFailCause> record = getSqlMapClientTemplate().queryForList("V_RP_DY_SGSN_ATT_FAIL_CAUSE.filterWeekly", parms);
        return record;
	}
}