package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkLocationBh;

public class VRpWkLocationBhDAOImpl extends SqlMapClientDaoSupport implements VRpWkLocationBhDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_LOCATION_BH
     *
     * @ibatorgenerated Mon Dec 13 09:58:44 ICT 2010
     */
    public VRpWkLocationBhDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_LOCATION_BH
     *
     * @ibatorgenerated Mon Dec 13 09:58:44 ICT 2010
     */
    public void insert(VRpWkLocationBh record) {
        getSqlMapClientTemplate().insert("V_RP_WK_LOCATION_BH.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_LOCATION_BH
     *
     * @ibatorgenerated Mon Dec 13 09:58:44 ICT 2010
     */
    public void insertSelective(VRpWkLocationBh record) {
        getSqlMapClientTemplate().insert("V_RP_WK_LOCATION_BH.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpWkLocationBh> filter(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String location, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("location", location);
		map.put("startWeek", Integer.toString(startWeek));
		map.put("endWeek", Integer.toString(endWeek));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
		map.put("region", region);
    	return getSqlMapClientTemplate().queryForList("V_RP_WK_LOCATION_BH.filter", map);
	}
}