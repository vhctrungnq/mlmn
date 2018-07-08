package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDySiteHoBh;

public class VRpDySiteHoBhDAOImpl extends SqlMapClientDaoSupport implements VRpDySiteHoBhDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SITE_HO_BH
     *
     * @ibatorgenerated Wed Nov 17 11:40:54 ICT 2010
     */
    public VRpDySiteHoBhDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SITE_HO_BH
     *
     * @ibatorgenerated Wed Nov 17 11:40:54 ICT 2010
     */
    public void insert(VRpDySiteHoBh record) {
        getSqlMapClientTemplate().insert("V_RP_DY_SITE_HO_BH.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SITE_HO_BH
     *
     * @ibatorgenerated Wed Nov 17 11:40:54 ICT 2010
     */
    public void insertSelective(VRpDySiteHoBh record) {
        getSqlMapClientTemplate().insert("V_RP_DY_SITE_HO_BH.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpDySiteHoBh> filterDetails(String startDate, String endDate, String bscid, String siteid, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("siteid", siteid);
		map.put("startDate", startDate);
		map.put("endDate",  endDate);
		map.put("region", region);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_SITE_HO_BH.filterDetails", map);
	}
}