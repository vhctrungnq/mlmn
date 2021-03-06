package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyIpbbCpuMem;

public class VRpDyIpbbCpuMemDAOImpl extends SqlMapClientDaoSupport implements VRpDyIpbbCpuMemDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_IPBB_CPU_MEM
     *
     * @ibatorgenerated Wed Dec 25 15:44:20 ICT 2013
     */
    public VRpDyIpbbCpuMemDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_IPBB_CPU_MEM
     *
     * @ibatorgenerated Wed Dec 25 15:44:20 ICT 2013
     */
    public void insert(VRpDyIpbbCpuMem record) {
        getSqlMapClientTemplate().insert("V_RP_DY_IPBB_CPU_MEM.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_IPBB_CPU_MEM
     *
     * @ibatorgenerated Wed Dec 25 15:44:20 ICT 2013
     */
    public void insertSelective(VRpDyIpbbCpuMem record) {
        getSqlMapClientTemplate().insert("V_RP_DY_IPBB_CPU_MEM.ibatorgenerated_insertSelective", record);
    }
    public List<VRpDyIpbbCpuMem> filterDaily(String tableName,String linkid,String startDate, String endDate) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_LINKID", 		linkid);
    	parms.put("P_START_DATE", 		startDate);
    	parms.put("P_END_DATE", 		endDate);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDyIpbbCpuMem> record = getSqlMapClientTemplate().queryForList("V_RP_DY_IPBB_CPU_MEM.filterDaily", parms);
        return record;
	}
    public List<VRpDyIpbbCpuMem> filterHourly(String tableName,String linkid,String startDate, String endDate) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("P_TABLE_NAME", 		tableName);
    	parms.put("P_LINKID", 		linkid);
    	parms.put("P_START_DATE", 		startDate);
    	parms.put("P_END_DATE", 		endDate);
    	parms.put("P_DATA", 				null);
    	
		@SuppressWarnings("unchecked")
		List<VRpDyIpbbCpuMem> record = getSqlMapClientTemplate().queryForList("V_RP_DY_IPBB_CPU_MEM.filterHourly", parms);
        return record;
	}
    @SuppressWarnings("unchecked")
	public List<VRpDyIpbbCpuMem> getLinkList(String startDate, String endDate)
    {
    	Map<String,String>map = new HashMap<String, String>();
    	map.put("startDate", startDate);
    	map.put("endDate", endDate);
    	return getSqlMapClientTemplate().queryForList("V_RP_DY_IPBB_CPU_MEM.getLinkList", map);
    }
}