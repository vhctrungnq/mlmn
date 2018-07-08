package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyS7link;

public class VRpDyS7linkDAOImpl extends SqlMapClientDaoSupport implements VRpDyS7linkDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_S7LINK
     *
     * @ibatorgenerated Wed Nov 13 10:21:01 ICT 2013
     */
    public VRpDyS7linkDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_S7LINK
     *
     * @ibatorgenerated Wed Nov 13 10:21:01 ICT 2013
     */
    public void insert(VRpDyS7link record) {
        getSqlMapClientTemplate().insert("V_RP_DY_S7LINK.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_S7LINK
     *
     * @ibatorgenerated Wed Nov 13 10:21:01 ICT 2013
     */
    public void insertSelective(VRpDyS7link record) {
        getSqlMapClientTemplate().insert("V_RP_DY_S7LINK.ibatorgenerated_insertSelective", record);
    }
    public List<VRpDyS7link> filterHourly(String tableName, String mscid, String linkid, String linksetname, String type, String startDate, String startHour, String endHour)
    {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_MSCID", 		mscid);
    	parms.put("P_LINKID", 		linkid);
    	parms.put("P_LINKSETNAME", 		linksetname);
    	parms.put("P_TYPE", 		type);
    	parms.put("P_START_DATE", 		startDate);
    	parms.put("P_START_HOUR", 		startHour);
    	parms.put("P_END_HOUR", 		endHour);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDyS7link> record = getSqlMapClientTemplate().queryForList("V_RP_DY_S7LINK.filterHourly", parms);
        return record;
	}
    public List<VRpDyS7link> filterDaily(String tableName,String mscid, String linkid, String linksetname, String type,String startDate, String endDate) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_MSCID", 		mscid);
    	parms.put("P_LINKID", 		linkid);
    	parms.put("P_LINKSETNAME", 		linksetname);
    	parms.put("P_TYPE", 		type);
    	parms.put("P_START_DATE", 		startDate);
    	parms.put("P_END_DATE", 		endDate);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDyS7link> record = getSqlMapClientTemplate().queryForList("V_RP_DY_S7LINK.filterDaily", parms);
        return record;
	}
    public List<VRpDyS7link> filterMonthly(String tableName,String mscid, String linkid, String linksetname, String type,String startMonth,String endMonth,String startYear,String endYear) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_MSCID", 		mscid);
    	parms.put("P_LINKID", 		linkid);
    	parms.put("P_LINKSETNAME", 		linksetname);
    	parms.put("P_TYPE", 		type);
    	parms.put("P_START_MONTH", 		startMonth);
    	parms.put("P_END_MONTH", 		endMonth);
    	parms.put("P_START_YEAR", 		startYear);
    	parms.put("P_END_YEAR", 		endYear);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDyS7link> record = getSqlMapClientTemplate().queryForList("V_RP_DY_S7LINK.filterMonthly", parms);
        return record;
	}
    public List<VRpDyS7link> filterWeekly(String tableName,String mscid, String linkid, String linksetname, String type,String startWeek,String endWeek,String startYear,String endYear) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_MSCID", 		mscid);
    	parms.put("P_LINKID", 		linkid);
    	parms.put("P_LINKSETNAME", 		linksetname);
    	parms.put("P_TYPE", 		type);
    	parms.put("P_START_WEEK", 		startWeek);
    	parms.put("P_END_WEEK", 		endWeek);
    	parms.put("P_START_YEAR", 		startYear);
    	parms.put("P_END_YEAR", 		endYear);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDyS7link> record = getSqlMapClientTemplate().queryForList("V_RP_DY_S7LINK.filterWeekly", parms);
        return record;
	}
}