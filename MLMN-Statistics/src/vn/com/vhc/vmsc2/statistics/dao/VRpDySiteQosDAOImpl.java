package vn.com.vhc.vmsc2.statistics.dao;

import java.util.*;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDySiteQos;

public class VRpDySiteQosDAOImpl extends SqlMapClientDaoSupport implements VRpDySiteQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SITE
     *
     * @ibatorgenerated Thu Dec 16 17:04:12 ICT 2010
     */
    public VRpDySiteQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SITE
     *
     * @ibatorgenerated Thu Dec 16 17:04:12 ICT 2010
     */
    public void insert(VRpDySiteQos record) {
        getSqlMapClientTemplate().insert("V_RP_DY_SITE.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_SITE
     *
     * @ibatorgenerated Thu Dec 16 17:04:12 ICT 2010
     */
    public void insertSelective(VRpDySiteQos record) {
        getSqlMapClientTemplate().insert("V_RP_DY_SITE.ibatorgenerated_insertSelective", record);
    }
    @SuppressWarnings("unchecked")
	public List<VRpDySiteQos> filterDetails(String startDate, String endDate, String bscid, String siteid, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("siteid", siteid);
		map.put("startDate", startDate);
		map.put("endDate",  endDate);
		map.put("region", region);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_SITE.filterDetails", map);
	}

	@SuppressWarnings("unchecked")
	public List<VRpDySiteQos> filter(String province,String bscid, String siteid, Date d, String region) {
		VRpDySiteQos key = new VRpDySiteQos();
		key.setProvince(province);
		key.setBscid(bscid);
		key.setSiteid(siteid);
		key.setDay(d);
		key.setRegion(region);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_SITE.filter", key);
	}
}