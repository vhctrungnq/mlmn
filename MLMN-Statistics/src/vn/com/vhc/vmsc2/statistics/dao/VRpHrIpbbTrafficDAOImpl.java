package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpHrIpbbTraffic;

public class VRpHrIpbbTrafficDAOImpl extends SqlMapClientDaoSupport implements VRpHrIpbbTrafficDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_IPBB_TRAFFIC
     *
     * @ibatorgenerated Thu Dec 05 22:48:35 ICT 2013
     */
    public VRpHrIpbbTrafficDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_IPBB_TRAFFIC
     *
     * @ibatorgenerated Thu Dec 05 22:48:35 ICT 2013
     */
    public void insert(VRpHrIpbbTraffic record) {
        getSqlMapClientTemplate().insert("V_RP_HR_IPBB_TRAFFIC.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_IPBB_TRAFFIC
     *
     * @ibatorgenerated Thu Dec 05 22:48:35 ICT 2013
     */
    public void insertSelective(VRpHrIpbbTraffic record) {
        getSqlMapClientTemplate().insert("V_RP_HR_IPBB_TRAFFIC.ibatorgenerated_insertSelective", record);
    }
    @SuppressWarnings("unchecked")
    public List<VRpHrIpbbTraffic> getList(String date, String fuction){
    	Map<String, Object> parms = new HashMap<String, Object>();
    	
    	parms.put("P_DATE", date);
    	parms.put("P_FUNCTION", fuction);
		parms.put("P_DATA", null);	
		return (List<VRpHrIpbbTraffic>) getSqlMapClientTemplate().queryForList("V_RP_HR_IPBB_TRAFFIC.getList", parms);
    }
}