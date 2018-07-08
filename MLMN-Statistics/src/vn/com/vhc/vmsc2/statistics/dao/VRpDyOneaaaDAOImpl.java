package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyOneaaa;

public class VRpDyOneaaaDAOImpl extends SqlMapClientDaoSupport implements VRpDyOneaaaDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_ONEAAA
     *
     * @ibatorgenerated Sat Dec 07 21:29:04 ICT 2013
     */
    public VRpDyOneaaaDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_ONEAAA
     *
     * @ibatorgenerated Sat Dec 07 21:29:04 ICT 2013
     */
    public void insert(VRpDyOneaaa record) {
        getSqlMapClientTemplate().insert("V_RP_DY_ONEAAA.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_ONEAAA
     *
     * @ibatorgenerated Sat Dec 07 21:29:04 ICT 2013
     */
    public void insertSelective(VRpDyOneaaa record) {
        getSqlMapClientTemplate().insert("V_RP_DY_ONEAAA.ibatorgenerated_insertSelective", record);
    }
    public List<VRpDyOneaaa> filterHourly(String tableName, String nodeid,String startDate, String startHour, String endHour)
    {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_NODEID", 		nodeid);
    	parms.put("P_START_DATE", 		startDate);
    	parms.put("P_START_HOUR", 		startHour);
    	parms.put("P_END_HOUR", 		endHour);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDyOneaaa> record = getSqlMapClientTemplate().queryForList("V_RP_DY_ONEAAA.filterHourly", parms);
        return record;
	}
    public List<VRpDyOneaaa> filterDaily(String tableName,String nodeid,String startDate, String endDate) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_NODEID", 		nodeid);
    	parms.put("P_START_DATE", 		startDate);
    	parms.put("P_END_DATE", 		endDate);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDyOneaaa> record = getSqlMapClientTemplate().queryForList("V_RP_DY_ONEAAA.filterDaily", parms);
        return record;
	}
    public List<VRpDyOneaaa> filterMonthly(String tableName,String nodeid,String startMonth,String endMonth,String startYear,String endYear) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_NODEID", 		nodeid);
    	parms.put("P_START_MONTH", 		startMonth);
    	parms.put("P_END_MONTH", 		endMonth);
    	parms.put("P_START_YEAR", 		startYear);
    	parms.put("P_END_YEAR", 		endYear);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDyOneaaa> record = getSqlMapClientTemplate().queryForList("V_RP_DY_ONEAAA.filterMonthly", parms);
        return record;
	}
    public List<VRpDyOneaaa> filterWeekly(String tableName,String nodeid,String startWeek,String endWeek,String startYear,String endYear) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_NODEID", 		nodeid);
    	parms.put("P_START_WEEK", 		startWeek);
    	parms.put("P_END_WEEK", 		endWeek);
    	parms.put("P_START_YEAR", 		startYear);
    	parms.put("P_END_YEAR", 		endYear);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDyOneaaa> record = getSqlMapClientTemplate().queryForList("V_RP_DY_ONEAAA.filterWeekly", parms);
        return record;
	}
    
}