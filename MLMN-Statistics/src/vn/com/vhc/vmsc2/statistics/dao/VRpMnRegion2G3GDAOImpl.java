package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnRegion2G3G;

public class VRpMnRegion2G3GDAOImpl extends SqlMapClientDaoSupport implements VRpMnRegion2G3GDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_REGION_2G_3G
     *
     * @ibatorgenerated Fri Jun 03 10:11:24 ICT 2011
     */
    public VRpMnRegion2G3GDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_REGION_2G_3G
     *
     * @ibatorgenerated Fri Jun 03 10:11:24 ICT 2011
     */
    public void insert(VRpMnRegion2G3G record) {
        getSqlMapClientTemplate().insert("V_RP_MN_REGION_2G_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_REGION_2G_3G
     *
     * @ibatorgenerated Fri Jun 03 10:11:24 ICT 2011
     */
    public void insertSelective(VRpMnRegion2G3G record) {
        getSqlMapClientTemplate().insert("V_RP_MN_REGION_2G_3G.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpMnRegion2G3G> filter(Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("startMonth", Integer.toString(startMonth));
		map.put("endMonth", Integer.toString(endMonth));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
		map.put("region", region);
    	return getSqlMapClientTemplate().queryForList("V_RP_MN_REGION_2G_3G.filter", map);
	}
}