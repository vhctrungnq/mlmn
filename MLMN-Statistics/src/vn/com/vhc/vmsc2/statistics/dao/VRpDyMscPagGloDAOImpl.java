package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyMscPagGlo;

public class VRpDyMscPagGloDAOImpl extends SqlMapClientDaoSupport implements VRpDyMscPagGloDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_MSC_PAG_GLO
     *
     * @ibatorgenerated Sat Oct 12 14:40:48 ICT 2013
     */
    public VRpDyMscPagGloDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_MSC_PAG_GLO
     *
     * @ibatorgenerated Sat Oct 12 14:40:48 ICT 2013
     */
    public void insert(VRpDyMscPagGlo record) {
        getSqlMapClientTemplate().insert("V_RP_DY_MSC_PAG_GLO.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_MSC_PAG_GLO
     *
     * @ibatorgenerated Sat Oct 12 14:40:48 ICT 2013
     */
    public void insertSelective(VRpDyMscPagGlo record) {
        getSqlMapClientTemplate().insert("V_RP_DY_MSC_PAG_GLO.ibatorgenerated_insertSelective", record);
    }
    public List<VRpDyMscPagGlo> filterHourly(String tableName, String mscid,String startDate, String startHour, String endHour)
    {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_MSCID", 		mscid);
    	parms.put("P_START_DATE", 		startDate);
    	parms.put("P_START_HOUR", 		startHour);
    	parms.put("P_END_HOUR", 		endHour);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDyMscPagGlo> record = getSqlMapClientTemplate().queryForList("V_RP_DY_MSC_PAG_GLO.filterHourly", parms);
        return record;
	}
    public List<VRpDyMscPagGlo> filterDaily(String tableName,String mscid,String startDate, String endDate) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_MSCID", 		mscid);
    	parms.put("P_START_DATE", 		startDate);
    	parms.put("P_END_DATE", 		endDate);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDyMscPagGlo> record = getSqlMapClientTemplate().queryForList("V_RP_DY_MSC_PAG_GLO.filterDaily", parms);
        return record;
	}
    public List<VRpDyMscPagGlo> filterMonthly(String tableName,String mscid,String startMonth,String endMonth,String startYear,String endYear) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_MSCID", 		mscid);
    	parms.put("P_START_MONTH", 		startMonth);
    	parms.put("P_END_MONTH", 		endMonth);
    	parms.put("P_START_YEAR", 		startYear);
    	parms.put("P_END_YEAR", 		endYear);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDyMscPagGlo> record = getSqlMapClientTemplate().queryForList("V_RP_DY_MSC_PAG_GLO.filterMonthly", parms);
        return record;
	}
    public List<VRpDyMscPagGlo> filterWeekly(String tableName,String mscid,String startWeek,String endWeek,String startYear,String endYear) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_MSCID", 		mscid);
    	parms.put("P_START_WEEK", 		startWeek);
    	parms.put("P_END_WEEK", 		endWeek);
    	parms.put("P_START_YEAR", 		startYear);
    	parms.put("P_END_YEAR", 		endYear);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDyMscPagGlo> record = getSqlMapClientTemplate().queryForList("V_RP_DY_MSC_PAG_GLO.filterWeekly", parms);
        return record;
	}
}