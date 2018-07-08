package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkProvince3G;

public class VRpWkProvince3GDAOImpl extends SqlMapClientDaoSupport implements VRpWkProvince3GDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_PROVINCE_3G
     *
     * @ibatorgenerated Mon Apr 25 10:16:58 ICT 2011
     */
    public VRpWkProvince3GDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_PROVINCE_3G
     *
     * @ibatorgenerated Mon Apr 25 10:16:58 ICT 2011
     */
    public void insert(VRpWkProvince3G record) {
        getSqlMapClientTemplate().insert("V_RP_WK_PROVINCE_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_PROVINCE_3G
     *
     * @ibatorgenerated Mon Apr 25 10:16:58 ICT 2011
     */
    public void insertSelective(VRpWkProvince3G record) {
        getSqlMapClientTemplate().insert("V_RP_WK_PROVINCE_3G.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpWkProvince3G> filter(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String province, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("province", province);
		map.put("startWeek", Integer.toString(startWeek));
		map.put("endWeek", Integer.toString(endWeek));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
		map.put("region", region);
    	return getSqlMapClientTemplate().queryForList("V_RP_WK_PROVINCE_3G.filter", map);
	}
}