package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnSiteHo;

public class VRpMnSiteHoDAOImpl extends SqlMapClientDaoSupport implements VRpMnSiteHoDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_SITE_HO
     *
     * @ibatorgenerated Wed Nov 17 11:40:38 ICT 2010
     */
    public VRpMnSiteHoDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_SITE_HO
     *
     * @ibatorgenerated Wed Nov 17 11:40:38 ICT 2010
     */
    public void insert(VRpMnSiteHo record) {
        getSqlMapClientTemplate().insert("V_RP_MN_SITE_HO.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_SITE_HO
     *
     * @ibatorgenerated Wed Nov 17 11:40:38 ICT 2010
     */
    public void insertSelective(VRpMnSiteHo record) {
        getSqlMapClientTemplate().insert("V_RP_MN_SITE_HO.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpMnSiteHo> filter(String province,String bscid, String siteid, Integer month, Integer year, String region) {
		VRpMnSiteHo key = new VRpMnSiteHo();
		key.setProvince(province);
		key.setBscid(bscid);
		key.setSiteid(siteid);
		key.setMonth(month);
		key.setYear(year);
		key.setRegion(region);
		return getSqlMapClientTemplate().queryForList("V_RP_MN_SITE_HO.filter", key);
	}

	@SuppressWarnings("unchecked")
	public List<VRpMnSiteHo> filterDetails(String startMonth, String startYear, String endMonth, String endYear, String bscid, String siteid, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("siteid", siteid);
		map.put("startMonth", startMonth);
		map.put("startYear",  startYear);
		map.put("endMonth", endMonth);
		map.put("endYear",  endYear);
		map.put("region", region);
		return getSqlMapClientTemplate().queryForList("V_RP_MN_SITE_HO.filterDetails", map);
	}
}