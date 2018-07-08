package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyHlrForGtmsc;

public class VRpDyHlrForGtmscDAOImpl extends SqlMapClientDaoSupport implements VRpDyHlrForGtmscDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_HLR_FOR_GTMSC
     *
     * @ibatorgenerated Fri May 10 13:41:12 ICT 2013
     */
    public VRpDyHlrForGtmscDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_HLR_FOR_GTMSC
     *
     * @ibatorgenerated Fri May 10 13:41:12 ICT 2013
     */
    public void insert(VRpDyHlrForGtmsc record) {
        getSqlMapClientTemplate().insert("V_RP_DY_HLR_FOR_GTMSC.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_HLR_FOR_GTMSC
     *
     * @ibatorgenerated Fri May 10 13:41:12 ICT 2013
     */
    public void insertSelective(VRpDyHlrForGtmsc record) {
        getSqlMapClientTemplate().insert("V_RP_DY_HLR_FOR_GTMSC.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
	public List<VRpDyHlrForGtmsc> filter(String startDate, String endDate, String nodeid){
    	Map<String, String> map = new HashMap<String, String>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("nodeid", nodeid);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_HLR_FOR_GTMSC.filter", map);
    }
    @SuppressWarnings("unchecked")
  	public List<VRpDyHlrForGtmsc> filterDay(String startDate, String endDate){
      	Map<String, String> map = new HashMap<String, String>();
  		map.put("startDate", startDate);
  		map.put("endDate", endDate);
  		return getSqlMapClientTemplate().queryForList("V_RP_DY_HLR_FOR_GTMSC.filterDay", map);
      }
}