package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDySgsnPdpActFail;

public class VRpDySgsnPdpActFailDAOImpl extends SqlMapClientDaoSupport implements VRpDySgsnPdpActFailDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SGSN_PDP_ACT_FAIL
     *
     * @ibatorgenerated Wed Oct 16 14:55:58 ICT 2013
     */
    public VRpDySgsnPdpActFailDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SGSN_PDP_ACT_FAIL
     *
     * @ibatorgenerated Wed Oct 16 14:55:58 ICT 2013
     */
    public void insert(VRpDySgsnPdpActFail record) {
        getSqlMapClientTemplate().insert("V_RP_DY_SGSN_PDP_ACT_FAIL.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SGSN_PDP_ACT_FAIL
     *
     * @ibatorgenerated Wed Oct 16 14:55:59 ICT 2013
     */
    public void insertSelective(VRpDySgsnPdpActFail record) {
        getSqlMapClientTemplate().insert("V_RP_DY_SGSN_PDP_ACT_FAIL.ibatorgenerated_insertSelective", record);
    }
    public List<VRpDySgsnPdpActFail> filterHourly(String tableName, String sgsnid,String startDate, String startHour, String endHour)
    {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_SGSNID", 		sgsnid);
    	parms.put("P_START_DATE", 		startDate);
    	parms.put("P_START_HOUR", 		startHour);
    	parms.put("P_END_HOUR", 		endHour);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDySgsnPdpActFail> record = getSqlMapClientTemplate().queryForList("V_RP_DY_SGSN_PDP_ACT_FAIL.filterHourly", parms);
        return record;
	}
    public List<VRpDySgsnPdpActFail> filterDaily(String tableName,String sgsnid,String startDate, String endDate) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_SGSNID", 		sgsnid);
    	parms.put("P_START_DATE", 		startDate);
    	parms.put("P_END_DATE", 		endDate);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDySgsnPdpActFail> record = getSqlMapClientTemplate().queryForList("V_RP_DY_SGSN_PDP_ACT_FAIL.filterDaily", parms);
        return record;
	}
    public List<VRpDySgsnPdpActFail> filterMonthly(String tableName,String sgsnid,String startMonth,String endMonth,String startYear,String endYear) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_SGSNID", 		sgsnid);
    	parms.put("P_START_MONTH", 		startMonth);
    	parms.put("P_END_MONTH", 		endMonth);
    	parms.put("P_START_YEAR", 		startYear);
    	parms.put("P_END_YEAR", 		endYear);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDySgsnPdpActFail> record = getSqlMapClientTemplate().queryForList("V_RP_DY_SGSN_PDP_ACT_FAIL.filterMonthly", parms);
        return record;
	}
    public List<VRpDySgsnPdpActFail> filterWeekly(String tableName,String sgsnid,String startWeek,String endWeek,String startYear,String endYear) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_SGSNID", 		sgsnid);
    	parms.put("P_START_WEEK", 		startWeek);
    	parms.put("P_END_WEEK", 		endWeek);
    	parms.put("P_START_YEAR", 		startYear);
    	parms.put("P_END_YEAR", 		endYear);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDySgsnPdpActFail> record = getSqlMapClientTemplate().queryForList("V_RP_DY_SGSN_PDP_ACT_FAIL.filterWeekly", parms);
        return record;
	}
}