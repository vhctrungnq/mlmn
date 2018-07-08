package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDySiteQosBh;

public class VRpDySiteQosBhDAOImpl extends SqlMapClientDaoSupport implements VRpDySiteQosBhDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SITE_BH
     *
     * @ibatorgenerated Mon Dec 13 09:58:44 ICT 2010
     */
    public VRpDySiteQosBhDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SITE_BH
     *
     * @ibatorgenerated Mon Dec 13 09:58:44 ICT 2010
     */
    public void insert(VRpDySiteQosBh record) {
        getSqlMapClientTemplate().insert("V_RP_DY_SITE_BH.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SITE_BH
     *
     * @ibatorgenerated Mon Dec 13 09:58:44 ICT 2010
     */
    public void insertSelective(VRpDySiteQosBh record) {
        getSqlMapClientTemplate().insert("V_RP_DY_SITE_BH.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpDySiteQosBh> filterDetails(String startDate, String endDate, String bscid, String siteid, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("siteid", siteid);
		map.put("startDate", startDate);
		map.put("endDate",  endDate);
		map.put("region", region);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_SITE_BH.filterDetails", map);
	}
}