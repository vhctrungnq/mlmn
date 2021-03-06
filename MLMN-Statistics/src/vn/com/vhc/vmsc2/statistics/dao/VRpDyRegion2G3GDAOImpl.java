package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyRegion2G3G;

public class VRpDyRegion2G3GDAOImpl extends SqlMapClientDaoSupport implements VRpDyRegion2G3GDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_REGION_2G_3G
     *
     * @ibatorgenerated Wed May 25 17:47:19 ICT 2011
     */
    public VRpDyRegion2G3GDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_REGION_2G_3G
     *
     * @ibatorgenerated Wed May 25 17:47:19 ICT 2011
     */
    public void insert(VRpDyRegion2G3G record) {
        getSqlMapClientTemplate().insert("V_RP_DY_REGION_2G_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_REGION_2G_3G
     *
     * @ibatorgenerated Wed May 25 17:47:19 ICT 2011
     */
    public void insertSelective(VRpDyRegion2G3G record) {
        getSqlMapClientTemplate().insert("V_RP_DY_REGION_2G_3G.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpDyRegion2G3G> filter(String startDate, String endDate, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("startDate", startDate);
		map.put("endDate",  endDate);
		map.put("region",  region);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_REGION_2G_3G.filter", map);
	}
}