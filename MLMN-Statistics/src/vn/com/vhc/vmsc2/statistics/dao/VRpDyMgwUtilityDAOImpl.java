package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyMgwUtility;

public class VRpDyMgwUtilityDAOImpl extends SqlMapClientDaoSupport implements VRpDyMgwUtilityDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_MGW_UTILITY
     *
     * @ibatorgenerated Tue Jan 07 17:45:09 ICT 2014
     */
    public VRpDyMgwUtilityDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_MGW_UTILITY
     *
     * @ibatorgenerated Tue Jan 07 17:45:09 ICT 2014
     */
    public void insert(VRpDyMgwUtility record) {
        getSqlMapClientTemplate().insert("V_RP_DY_MGW_UTILITY.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_MGW_UTILITY
     *
     * @ibatorgenerated Tue Jan 07 17:45:09 ICT 2014
     */
    public void insertSelective(VRpDyMgwUtility record) {
        getSqlMapClientTemplate().insert("V_RP_DY_MGW_UTILITY.ibatorgenerated_insertSelective", record);
    }
    
    public List<VRpDyMgwUtility> filterHourly(String tableName, String mscid,String startDate, String startHour, String endHour)
    {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_MSCID", 		mscid);
    	parms.put("P_START_DATE", 		startDate);
    	parms.put("P_START_HOUR", 		startHour);
    	parms.put("P_END_HOUR", 		endHour);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDyMgwUtility> record = getSqlMapClientTemplate().queryForList("V_RP_DY_MGW_UTILITY.filterHourly", parms);
        return record;
	}
    public List<VRpDyMgwUtility> filterDaily(String tableName,String mscid,String startDate, String endDate) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_MSCID", 		mscid);
    	parms.put("P_START_DATE", 		startDate);
    	parms.put("P_END_DATE", 		endDate);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDyMgwUtility> record = getSqlMapClientTemplate().queryForList("V_RP_DY_MGW_UTILITY.filterDaily", parms);
        return record;
	}
    public List<VRpDyMgwUtility> filterMonthly(String tableName,String mscid,String startMonth,String endMonth,String startYear,String endYear) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_MSCID", 		mscid);
    	parms.put("P_START_MONTH", 		startMonth);
    	parms.put("P_END_MONTH", 		endMonth);
    	parms.put("P_START_YEAR", 		startYear);
    	parms.put("P_END_YEAR", 		endYear);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDyMgwUtility> record = getSqlMapClientTemplate().queryForList("V_RP_DY_MGW_UTILITY.filterMonthly", parms);
        return record;
	}
    public List<VRpDyMgwUtility> filterWeekly(String tableName,String mscid,String startWeek,String endWeek,String startYear,String endYear) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_MSCID", 		mscid);
    	parms.put("P_START_WEEK", 		startWeek);
    	parms.put("P_END_WEEK", 		endWeek);
    	parms.put("P_START_YEAR", 		startYear);
    	parms.put("P_END_YEAR", 		endYear);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDyMgwUtility> record = getSqlMapClientTemplate().queryForList("V_RP_DY_MGW_UTILITY.filterWeekly", parms);
        return record;
	}
}