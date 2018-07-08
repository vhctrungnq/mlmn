package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnSiteHoBh;

public class VRpMnSiteHoBhDAOImpl extends SqlMapClientDaoSupport implements VRpMnSiteHoBhDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_SITE_HO_BH
     *
     * @ibatorgenerated Wed Nov 17 11:40:44 ICT 2010
     */
    public VRpMnSiteHoBhDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_SITE_HO_BH
     *
     * @ibatorgenerated Wed Nov 17 11:40:44 ICT 2010
     */
    public void insert(VRpMnSiteHoBh record) {
        getSqlMapClientTemplate().insert("V_RP_MN_SITE_HO_BH.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_SITE_HO_BH
     *
     * @ibatorgenerated Wed Nov 17 11:40:44 ICT 2010
     */
    public void insertSelective(VRpMnSiteHoBh record) {
        getSqlMapClientTemplate().insert("V_RP_MN_SITE_HO_BH.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpMnSiteHoBh> filterDetails(String startMonth, String startYear, String endMonth, String endYear, String bscid, String siteid, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("siteid", siteid);
		map.put("startMonth", startMonth);
		map.put("startYear",  startYear);
		map.put("endMonth", endMonth);
		map.put("endYear",  endYear);
		map.put("region", region);
		return getSqlMapClientTemplate().queryForList("V_RP_MN_SITE_HO_BH.filterDetails", map);
	}
}