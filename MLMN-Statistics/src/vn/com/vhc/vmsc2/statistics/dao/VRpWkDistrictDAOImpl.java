package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkDistrict;

public class VRpWkDistrictDAOImpl extends SqlMapClientDaoSupport implements VRpWkDistrictDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_DISTRICT
     *
     * @ibatorgenerated Mon Dec 20 16:09:19 ICT 2010
     */
    public VRpWkDistrictDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_DISTRICT
     *
     * @ibatorgenerated Mon Dec 20 16:09:19 ICT 2010
     */
    public void insert(VRpWkDistrict record) {
        getSqlMapClientTemplate().insert("V_RP_WK_DISTRICT.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_DISTRICT
     *
     * @ibatorgenerated Mon Dec 20 16:09:19 ICT 2010
     */
    public void insertSelective(VRpWkDistrict record) {
        getSqlMapClientTemplate().insert("V_RP_WK_DISTRICT.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpWkDistrict> filter(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String province, String district, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("district", district);
		map.put("province", province);
		map.put("startWeek", Integer.toString(startWeek));
		map.put("endWeek", Integer.toString(endWeek));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
		map.put("region", region);
    	return getSqlMapClientTemplate().queryForList("V_RP_WK_DISTRICT.filter", map);
	}
}