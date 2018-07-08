package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkBscGprsCs;

public class VRpWkBscGprsCsDAOImpl extends SqlMapClientDaoSupport implements VRpWkBscGprsCsDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_BSC_GPRS_CS
     *
     * @ibatorgenerated Thu Nov 18 14:43:32 ICT 2010
     */
    public VRpWkBscGprsCsDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_BSC_GPRS_CS
     *
     * @ibatorgenerated Thu Nov 18 14:43:32 ICT 2010
     */
    public void insert(VRpWkBscGprsCs record) {
        getSqlMapClientTemplate().insert("V_RP_WK_BSC_GPRS_CS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_BSC_GPRS_CS
     *
     * @ibatorgenerated Thu Nov 18 14:43:32 ICT 2010
     */
    public void insertSelective(VRpWkBscGprsCs record) {
        getSqlMapClientTemplate().insert("V_RP_WK_BSC_GPRS_CS.ibatorgenerated_insertSelective", record);
    }
    @SuppressWarnings("unchecked")
    public List<VRpWkBscGprsCs> filter(Integer week,Integer year, String bscid, String region){
    	VRpWkBscGprsCs key = new VRpWkBscGprsCs();
    	key.setBscid(bscid);
    	key.setWeek(week);
    	key.setYear(year);
    	key.setRegion(region);
    	return getSqlMapClientTemplate().queryForList("V_RP_WK_BSC_GPRS_CS.filter", key);
    }
    
    @SuppressWarnings("unchecked")
    public List<VRpWkBscGprsCs> filter(Integer startWeek,Integer startYear,Integer endWeek,Integer endYear, String bscid, String region){
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("startWeek", Integer.toString(startWeek));
		map.put("endWeek", Integer.toString(endWeek));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
		map.put("region", region);
    	return getSqlMapClientTemplate().queryForList("V_RP_WK_BSC_GPRS_CS.filter2", map);
    }
}