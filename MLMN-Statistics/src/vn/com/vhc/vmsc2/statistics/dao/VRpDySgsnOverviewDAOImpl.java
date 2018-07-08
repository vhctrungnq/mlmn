package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDySgsnOverview;

public class VRpDySgsnOverviewDAOImpl extends SqlMapClientDaoSupport implements VRpDySgsnOverviewDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SGSN_OVERVIEW
     *
     * @ibatorgenerated Tue Oct 15 15:30:51 ICT 2013
     */
    public VRpDySgsnOverviewDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SGSN_OVERVIEW
     *
     * @ibatorgenerated Tue Oct 15 15:30:51 ICT 2013
     */
    public void insert(VRpDySgsnOverview record) {
        getSqlMapClientTemplate().insert("V_RP_DY_SGSN_OVERVIEW.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SGSN_OVERVIEW
     *
     * @ibatorgenerated Tue Oct 15 15:30:51 ICT 2013
     */
    public void insertSelective(VRpDySgsnOverview record) {
        getSqlMapClientTemplate().insert("V_RP_DY_SGSN_OVERVIEW.ibatorgenerated_insertSelective", record);
    }
    public List<VRpDySgsnOverview> filterHourly(String tableName, String sgsnid,String startDate, String startHour, String endHour)
    {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_SGSNID", 		sgsnid);
    	parms.put("P_START_DATE", 		startDate);
    	parms.put("P_START_HOUR", 		startHour);
    	parms.put("P_END_HOUR", 		endHour);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDySgsnOverview> record = getSqlMapClientTemplate().queryForList("V_RP_DY_SGSN_OVERVIEW.filterHourly", parms);
        return record;
	}
    public List<VRpDySgsnOverview> filterDaily(String tableName,String sgsnid,String startDate, String endDate) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_SGSNID", 		sgsnid);
    	parms.put("P_START_DATE", 		startDate);
    	parms.put("P_END_DATE", 		endDate);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDySgsnOverview> record = getSqlMapClientTemplate().queryForList("V_RP_DY_SGSN_OVERVIEW.filterDaily", parms);
        return record;
	}
    public List<VRpDySgsnOverview> filterMonthly(String tableName,String sgsnid,String startMonth,String endMonth,String startYear,String endYear) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_SGSNID", 		sgsnid);
    	parms.put("P_START_MONTH", 		startMonth);
    	parms.put("P_END_MONTH", 		endMonth);
    	parms.put("P_START_YEAR", 		startYear);
    	parms.put("P_END_YEAR", 		endYear);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDySgsnOverview> record = getSqlMapClientTemplate().queryForList("V_RP_DY_SGSN_OVERVIEW.filterMonthly", parms);
        return record;
	}
    public List<VRpDySgsnOverview> filterWeekly(String tableName,String sgsnid,String startWeek,String endWeek,String startYear,String endYear) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_SGSNID", 		sgsnid);
    	parms.put("P_START_WEEK", 		startWeek);
    	parms.put("P_END_WEEK", 		endWeek);
    	parms.put("P_START_YEAR", 		startYear);
    	parms.put("P_END_YEAR", 		endYear);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDySgsnOverview> record = getSqlMapClientTemplate().queryForList("V_RP_DY_SGSN_OVERVIEW.filterWeekly", parms);
        return record;
	}
}