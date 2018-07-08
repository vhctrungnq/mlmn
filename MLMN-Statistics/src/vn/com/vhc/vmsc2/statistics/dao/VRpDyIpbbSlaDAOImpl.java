package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyIpbbSla;

public class VRpDyIpbbSlaDAOImpl extends SqlMapClientDaoSupport implements VRpDyIpbbSlaDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_IPBB_SLA
     *
     * @ibatorgenerated Fri Feb 07 14:28:07 ICT 2014
     */
    public VRpDyIpbbSlaDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_IPBB_SLA
     *
     * @ibatorgenerated Fri Feb 07 14:28:07 ICT 2014
     */
    public void insert(VRpDyIpbbSla record) {
        getSqlMapClientTemplate().insert("V_RP_DY_IPBB_SLA.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_IPBB_SLA
     *
     * @ibatorgenerated Fri Feb 07 14:28:07 ICT 2014
     */
    public void insertSelective(VRpDyIpbbSla record) {
        getSqlMapClientTemplate().insert("V_RP_DY_IPBB_SLA.ibatorgenerated_insertSelective", record);
    }
    
    public List<VRpDyIpbbSla> filterDaily(String tableName,String direction, String link,String startDate, String endDate) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_DIRECTION", 		direction);
    	parms.put("P_LINK", 		link);
    	parms.put("P_START_DATE", 		startDate);
    	parms.put("P_END_DATE", 		endDate);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDyIpbbSla> record = getSqlMapClientTemplate().queryForList("V_RP_DY_IPBB_SLA.filterDaily", parms);
        return record;
	}
    public List<VRpDyIpbbSla> filterHourly(String tableName,String direction, String link,String startDate, String endDate) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_DIRECTION", 		direction);
    	parms.put("P_LINK", 		link);
    	parms.put("P_START_DATE", 		startDate);
    	parms.put("P_END_DATE", 		endDate);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDyIpbbSla> record = getSqlMapClientTemplate().queryForList("V_RP_DY_IPBB_SLA.filterHourly", parms);
        return record;
	}
}