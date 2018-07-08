package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vo.VRpHrSite2g3g;

public class VRpHrSite2g3gDAOImpl extends SqlMapClientDaoSupport implements VRpHrSite2g3gDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_SITE_2G_3G
     *
     * @ibatorgenerated Thu Nov 14 14:35:05 ICT 2013
     */
    public VRpHrSite2g3gDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_SITE_2G_3G
     *
     * @ibatorgenerated Thu Nov 14 14:35:05 ICT 2013
     */
    public void insert(VRpHrSite2g3g record) {
        getSqlMapClientTemplate().insert("V_RP_HR_SITE_2G_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_SITE_2G_3G
     *
     * @ibatorgenerated Thu Nov 14 14:35:05 ICT 2013
     */
    public void insertSelective(VRpHrSite2g3g record) {
        getSqlMapClientTemplate().insert("V_RP_HR_SITE_2G_3G.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<VRpHrSite2g3g> getTraffic2g3gInfoFilter(String siteid) {
    	Map<String, Object> parms = new HashMap<String, Object>();
    	parms.put("P_SITEID", siteid);
		parms.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("V_RP_HR_SITE_2G_3G.getTraffic2g3gInfoFilter", parms);
    }
}