package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDySrx;

public class VRpDySrxDAOImpl extends SqlMapClientDaoSupport implements VRpDySrxDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SRX
     *
     * @ibatorgenerated Thu Dec 12 22:17:53 ICT 2013
     */
    public VRpDySrxDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SRX
     *
     * @ibatorgenerated Thu Dec 12 22:17:53 ICT 2013
     */
    public void insert(VRpDySrx record) {
        getSqlMapClientTemplate().insert("V_RP_DY_SRX.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SRX
     *
     * @ibatorgenerated Thu Dec 12 22:17:53 ICT 2013
     */
    public void insertSelective(VRpDySrx record) {
        getSqlMapClientTemplate().insert("V_RP_DY_SRX.ibatorgenerated_insertSelective", record);
    }
    public List<VRpDySrx> filterHourly(String tableName, String nodeid,String startDate, String startHour, String endHour)
    {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_NODEID", 		nodeid);
    	parms.put("P_START_DATE", 		startDate);
    	parms.put("P_START_HOUR", 		startHour);
    	parms.put("P_END_HOUR", 		endHour);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDySrx> record = getSqlMapClientTemplate().queryForList("V_RP_DY_SRX.filterHourly", parms);
        return record;
	}
    public List<VRpDySrx> filterDaily(String tableName,String nodeid,String startDate, String endDate) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_NODEID", 		nodeid);
    	parms.put("P_START_DATE", 		startDate);
    	parms.put("P_END_DATE", 		endDate);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDySrx> record = getSqlMapClientTemplate().queryForList("V_RP_DY_SRX.filterDaily", parms);
        return record;
	}
    public List<VRpDySrx> filterMonthly(String tableName,String nodeid,String startMonth,String endMonth,String startYear,String endYear) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_NODEID", 		nodeid);
    	parms.put("P_START_MONTH", 		startMonth);
    	parms.put("P_END_MONTH", 		endMonth);
    	parms.put("P_START_YEAR", 		startYear);
    	parms.put("P_END_YEAR", 		endYear);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDySrx> record = getSqlMapClientTemplate().queryForList("V_RP_DY_SRX.filterMonthly", parms);
        return record;
	}
    public List<VRpDySrx> filterWeekly(String tableName,String nodeid,String startWeek,String endWeek,String startYear,String endYear) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_NODEID", 		nodeid);
    	parms.put("P_START_WEEK", 		startWeek);
    	parms.put("P_END_WEEK", 		endWeek);
    	parms.put("P_START_YEAR", 		startYear);
    	parms.put("P_END_YEAR", 		endYear);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDySrx> record = getSqlMapClientTemplate().queryForList("V_RP_DY_SRX.filterWeekly", parms);
        return record;
	}
}