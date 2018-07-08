package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vn.com.vhc.vmsc2.statistics.domain.RpHrSite2g3g;

public class RpHrSite2g3gDAOImpl extends SqlMapClientDaoSupport implements RpHrSite2g3gDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table RP_HR_SITE_2G_3G
     *
     * @ibatorgenerated Sat Nov 16 11:03:33 ICT 2013
     */
    public RpHrSite2g3gDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table RP_HR_SITE_2G_3G
     *
     * @ibatorgenerated Sat Nov 16 11:03:33 ICT 2013
     */
    public void insert(RpHrSite2g3g record) {
        getSqlMapClientTemplate().insert("RP_HR_SITE_2G_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table RP_HR_SITE_2G_3G
     *
     * @ibatorgenerated Sat Nov 16 11:03:33 ICT 2013
     */
    public void insertSelective(RpHrSite2g3g record) {
        getSqlMapClientTemplate().insert("RP_HR_SITE_2G_3G.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
    public List<RpHrSite2g3g> getTraffic2g3gInfoFilter(String siteid) {
    	Map<String, Object> parms = new HashMap<String, Object>();
    	parms.put("P_SITEID", siteid);
		parms.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("RP_HR_SITE_2G_3G.getTraffic2g3gInfoFilter", parms);
    }
}