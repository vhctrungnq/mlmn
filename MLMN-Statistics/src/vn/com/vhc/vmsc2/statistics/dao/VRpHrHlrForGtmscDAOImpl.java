package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpHrHlrForGtmsc;

public class VRpHrHlrForGtmscDAOImpl extends SqlMapClientDaoSupport implements VRpHrHlrForGtmscDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_HLR_FOR_GTMSC
     *
     * @ibatorgenerated Fri May 10 13:41:37 ICT 2013
     */
    public VRpHrHlrForGtmscDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_HLR_FOR_GTMSC
     *
     * @ibatorgenerated Fri May 10 13:41:37 ICT 2013
     */
    public void insert(VRpHrHlrForGtmsc record) {
        getSqlMapClientTemplate().insert("V_RP_HR_HLR_FOR_GTMSC.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_HLR_FOR_GTMSC
     *
     * @ibatorgenerated Fri May 10 13:41:37 ICT 2013
     */
    public void insertSelective(VRpHrHlrForGtmsc record) {
        getSqlMapClientTemplate().insert("V_RP_HR_HLR_FOR_GTMSC.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
   	public List<VRpHrHlrForGtmsc> filter(String startHour, Date startDate, String endHour,Date endDate, String nodeid) {
   		Map<String, Object> map = new HashMap<String, Object>();
   		map.put("startHour", startHour);
   		map.put("endHour", endHour);
   		map.put("startDate",startDate);
   		map.put("endDate",endDate);
   		map.put("nodeid", nodeid);
       	return getSqlMapClientTemplate().queryForList("V_RP_HR_HLR_FOR_GTMSC.filter", map);
   	}
       @SuppressWarnings("unchecked")
       public List<VRpHrHlrForGtmsc> filter2(String startHour, Date startDate, String endHour, String nodeid) {
   		Map<String, Object> map = new HashMap<String, Object>();
   		map.put("startHour", startHour);
   		map.put("endHour", endHour);
   		map.put("startDate", startDate);
   		map.put("nodeid", nodeid);
   		return getSqlMapClientTemplate().queryForList("V_RP_HR_HLR_FOR_GTMSC.filter2", map);
      	}
}