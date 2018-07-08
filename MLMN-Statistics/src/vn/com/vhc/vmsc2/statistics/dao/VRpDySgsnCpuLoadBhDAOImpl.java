package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDySgsnCpuLoadBh;

public class VRpDySgsnCpuLoadBhDAOImpl extends SqlMapClientDaoSupport implements VRpDySgsnCpuLoadBhDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SGSN_CPULOAD_BH
     *
     * @ibatorgenerated Thu Oct 17 10:23:43 ICT 2013
     */
    public VRpDySgsnCpuLoadBhDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SGSN_CPULOAD_BH
     *
     * @ibatorgenerated Thu Oct 17 10:23:43 ICT 2013
     */
    public void insert(VRpDySgsnCpuLoadBh record) {
        getSqlMapClientTemplate().insert("V_RP_DY_SGSN_CPULOAD_BH.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SGSN_CPULOAD_BH
     *
     * @ibatorgenerated Thu Oct 17 10:23:43 ICT 2013
     */
    public void insertSelective(VRpDySgsnCpuLoadBh record) {
        getSqlMapClientTemplate().insert("V_RP_DY_SGSN_CPULOAD_BH.ibatorgenerated_insertSelective", record);
    }
    public List<VRpDySgsnCpuLoadBh> filterHourly(String tableName, String sgsnid,String startDate, String startHour, String endHour)
    {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_SGSNID", 		sgsnid);
    	parms.put("P_START_DATE", 		startDate);
    	parms.put("P_START_HOUR", 		startHour);
    	parms.put("P_END_HOUR", 		endHour);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDySgsnCpuLoadBh> record = getSqlMapClientTemplate().queryForList("V_RP_DY_SGSN_CPULOAD_BH.filterHourly", parms);
        return record;
	}
    public List<VRpDySgsnCpuLoadBh> filterDaily(String tableName,String sgsnid,String startDate, String endDate) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_SGSNID", 		sgsnid);
    	parms.put("P_START_DATE", 		startDate);
    	parms.put("P_END_DATE", 		endDate);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDySgsnCpuLoadBh> record = getSqlMapClientTemplate().queryForList("V_RP_DY_SGSN_CPULOAD_BH.filterDaily", parms);
        return record;
	}
    public List<VRpDySgsnCpuLoadBh> filterMonthly(String tableName,String sgsnid,String startMonth,String endMonth,String startYear,String endYear) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_SGSNID", 		sgsnid);
    	parms.put("P_START_MONTH", 		startMonth);
    	parms.put("P_END_MONTH", 		endMonth);
    	parms.put("P_START_YEAR", 		startYear);
    	parms.put("P_END_YEAR", 		endYear);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDySgsnCpuLoadBh> record = getSqlMapClientTemplate().queryForList("V_RP_DY_SGSN_CPULOAD_BH.filterMonthly", parms);
        return record;
	}
    public List<VRpDySgsnCpuLoadBh> filterWeekly(String tableName,String sgsnid,String startWeek,String endWeek,String startYear,String endYear) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_SGSNID", 		sgsnid);
    	parms.put("P_START_WEEK", 		startWeek);
    	parms.put("P_END_WEEK", 		endWeek);
    	parms.put("P_START_YEAR", 		startYear);
    	parms.put("P_END_YEAR", 		endYear);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDySgsnCpuLoadBh> record = getSqlMapClientTemplate().queryForList("V_RP_DY_SGSN_CPULOAD_BH.filterWeekly", parms);
        return record;
	}
}