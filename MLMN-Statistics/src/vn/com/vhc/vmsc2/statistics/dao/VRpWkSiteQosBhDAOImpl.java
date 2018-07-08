package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkSiteQosBh;

public class VRpWkSiteQosBhDAOImpl extends SqlMapClientDaoSupport implements VRpWkSiteQosBhDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_SITE_BH
     *
     * @ibatorgenerated Mon Dec 13 09:58:44 ICT 2010
     */
    public VRpWkSiteQosBhDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_SITE_BH
     *
     * @ibatorgenerated Mon Dec 13 09:58:44 ICT 2010
     */
    public void insert(VRpWkSiteQosBh record) {
        getSqlMapClientTemplate().insert("V_RP_WK_SITE_BH.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_SITE_BH
     *
     * @ibatorgenerated Mon Dec 13 09:58:44 ICT 2010
     */
    public void insertSelective(VRpWkSiteQosBh record) {
        getSqlMapClientTemplate().insert("V_RP_WK_SITE_BH.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpWkSiteQosBh> filterDetails(String startWeek, String startYear, String endWeek, String endYear, String bscid, String siteid, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("siteid", siteid);
		map.put("startWeek", startWeek);
		map.put("startYear",  startYear);
		map.put("endWeek", endWeek);
		map.put("endYear",  endYear);
		map.put("region", region);
		return getSqlMapClientTemplate().queryForList("V_RP_WK_SITE_BH.filterDetails", map);
	}
}