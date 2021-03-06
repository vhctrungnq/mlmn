package vn.com.vhc.vmsc2.statistics.dao;

import java.util.*;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpHrSiteQos;

public class VRpHrSiteQosDAOImpl extends SqlMapClientDaoSupport implements VRpHrSiteQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_SITE
     *
     * @ibatorgenerated Thu Dec 16 17:04:31 ICT 2010
     */
    public VRpHrSiteQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_SITE
     *
     * @ibatorgenerated Thu Dec 16 17:04:31 ICT 2010
     */
    public void insert(VRpHrSiteQos record) {
        getSqlMapClientTemplate().insert("V_RP_HR_SITE.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_SITE
     *
     * @ibatorgenerated Thu Dec 16 17:04:31 ICT 2010
     */
    public void insertSelective(VRpHrSiteQos record) {
        getSqlMapClientTemplate().insert("V_RP_HR_SITE.ibatorgenerated_insertSelective", record);
    }
    @SuppressWarnings("unchecked")
	public List<VRpHrSiteQos> filter(String startHour, String endHour, String day, String bscid, String siteid, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("siteid", siteid);
		map.put("startHour", startHour);
		map.put("endHour",  endHour);
		map.put("day", day);
		map.put("region", region);
		return getSqlMapClientTemplate().queryForList("V_RP_HR_SITE.filterDetails", map);
	}
}