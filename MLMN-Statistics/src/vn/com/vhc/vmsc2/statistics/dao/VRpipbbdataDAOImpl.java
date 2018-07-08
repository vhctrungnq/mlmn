package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpipbbdata;

public class VRpipbbdataDAOImpl extends SqlMapClientDaoSupport implements VRpipbbdataDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_IPBB_DATA
     *
     * @ibatorgenerated Wed Nov 21 09:24:31 ICT 2012
     */
    public VRpipbbdataDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_IPBB_DATA
     *
     * @ibatorgenerated Wed Nov 21 09:24:31 ICT 2012
     */
    public void insert(VRpipbbdata record) {
        getSqlMapClientTemplate().insert("V_RP_IPBB_DATA.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_IPBB_DATA
     *
     * @ibatorgenerated Wed Nov 21 09:24:31 ICT 2012
     */
    public void insertSelective(VRpipbbdata record) {
        getSqlMapClientTemplate().insert("V_RP_IPBB_DATA.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
    public List<VRpipbbdata> filter(String module, String direction, String startDate, String endDate){
    	Map<String, Object> parms = new HashMap<String, Object>(); 
    	parms.put("P_MODULE", module);
    	parms.put("P_DIRECTION", direction);
    	parms.put("P_START_DATE", startDate);
    	parms.put("P_END_DATE", endDate);
    	
		parms.put("P_DATA", null);	
		return (List<VRpipbbdata>) getSqlMapClientTemplate().queryForList("V_RP_IPBB_DATA.filterIpbbData", parms);
    }
    
    @SuppressWarnings("unchecked")
    public List<VRpipbbdata> filter1(String startDate, String endDate){
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("startDate",startDate);
    	map.put("endDate",endDate);
    	return getSqlMapClientTemplate().queryForList("V_RP_IPBB_DATA.filter1", map);
    }
    @SuppressWarnings("unchecked")
    public List<VRpipbbdata> getBadLink(String date, String function){
    	Map<String, Object> parms = new HashMap<String, Object>();
    	parms.put("P_DATE", date);
    	parms.put("P_FUNCTION", function);
		parms.put("P_DATA", null);	
		return (List<VRpipbbdata>) getSqlMapClientTemplate().queryForList("V_RP_IPBB_DATA.getBadLink", parms);
    }
    @SuppressWarnings("unchecked")
    public List<VRpipbbdata> getList(String module, String direction, String startDate, String endDate){
    	Map<String, Object> parms = new HashMap<String, Object>();
     
    	parms.put("P_MODULE", module);
    	parms.put("P_DIRECTION", direction);
    	parms.put("P_START_DATE", startDate);
    	parms.put("P_END_DATE", endDate);
    	
		parms.put("P_DATA", null);	
		return (List<VRpipbbdata>) getSqlMapClientTemplate().queryForList("V_RP_IPBB_DATA.getList", parms);
    }
   
}