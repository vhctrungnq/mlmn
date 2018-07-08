package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyMgwLoad;

public class VRpDyMgwLoadDAOImpl extends SqlMapClientDaoSupport implements VRpDyMgwLoadDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_MGW_LOAD
     *
     * @ibatorgenerated Wed Dec 18 11:26:07 ICT 2013
     */
    public VRpDyMgwLoadDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_MGW_LOAD
     *
     * @ibatorgenerated Wed Dec 18 11:26:07 ICT 2013
     */
    public void insert(VRpDyMgwLoad record) {
        getSqlMapClientTemplate().insert("V_RP_DY_MGW_LOAD.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_MGW_LOAD
     *
     * @ibatorgenerated Wed Dec 18 11:26:07 ICT 2013
     */
    public void insertSelective(VRpDyMgwLoad record) {
        getSqlMapClientTemplate().insert("V_RP_DY_MGW_LOAD.ibatorgenerated_insertSelective", record);
    }
    public List<VRpDyMgwLoad> filterHourly(String tableName, String mscid,String startDate, String startHour, String endHour)
    {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_MSCID", 		mscid);
    	parms.put("P_START_DATE", 		startDate);
    	parms.put("P_START_HOUR", 		startHour);
    	parms.put("P_END_HOUR", 		endHour);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDyMgwLoad> record = getSqlMapClientTemplate().queryForList("V_RP_DY_MGW_LOAD.filterHourly", parms);
        return record;
	}
    public List<VRpDyMgwLoad> filterDaily(String tableName,String mscid,String startDate, String endDate) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_MSCID", 		mscid);
    	parms.put("P_START_DATE", 		startDate);
    	parms.put("P_END_DATE", 		endDate);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDyMgwLoad> record = getSqlMapClientTemplate().queryForList("V_RP_DY_MGW_LOAD.filterDaily", parms);
        return record;
	}
    public List<VRpDyMgwLoad> filterMonthly(String tableName,String mscid,String startMonth,String endMonth,String startYear,String endYear) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_MSCID", 		mscid);
    	parms.put("P_START_MONTH", 		startMonth);
    	parms.put("P_END_MONTH", 		endMonth);
    	parms.put("P_START_YEAR", 		startYear);
    	parms.put("P_END_YEAR", 		endYear);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDyMgwLoad> record = getSqlMapClientTemplate().queryForList("V_RP_DY_MGW_LOAD.filterMonthly", parms);
        return record;
	}
    public List<VRpDyMgwLoad> filterWeekly(String tableName,String mscid,String startWeek,String endWeek,String startYear,String endYear) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_MSCID", 		mscid);
    	parms.put("P_START_WEEK", 		startWeek);
    	parms.put("P_END_WEEK", 		endWeek);
    	parms.put("P_START_YEAR", 		startYear);
    	parms.put("P_END_YEAR", 		endYear);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDyMgwLoad> record = getSqlMapClientTemplate().queryForList("V_RP_DY_MGW_LOAD.filterWeekly", parms);
        return record;
	}
}