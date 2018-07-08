package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;


import vn.com.vhc.vmsc2.statistics.domain.VRpDyStp;

public class VRpDyStpDAOImpl extends SqlMapClientDaoSupport implements VRpDyStpDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_STP
     *
     * @ibatorgenerated Sun Jan 13 11:04:01 ICT 2013
     */
    public VRpDyStpDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_STP
     *
     * @ibatorgenerated Sun Jan 13 11:04:01 ICT 2013
     */
    public void insert(VRpDyStp record) {
        getSqlMapClientTemplate().insert("V_RP_DY_STP.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_STP
     *
     * @ibatorgenerated Sun Jan 13 11:04:01 ICT 2013
     */
    public void insertSelective(VRpDyStp record) {
        getSqlMapClientTemplate().insert("V_RP_DY_STP.ibatorgenerated_insertSelective", record);
    }
    @SuppressWarnings("unchecked")
   	public List<VRpDyStp> filter(String startDate, String endDate, String stpid) {
   		Map<String, Object> map = new HashMap<String, Object>();
   		map.put("startDate", startDate);
   		map.put("endDate", endDate);
   		map.put("stpid", stpid);
   		return getSqlMapClientTemplate().queryForList("V_RP_DY_STP.filter", map);
   	}
    @SuppressWarnings("unchecked")
   	public List<VRpDyStp> filterDay(String startDate, String endDate) {
   		Map<String, Object> map = new HashMap<String, Object>();
   		map.put("startDate", startDate);
   		map.put("endDate", endDate);
   		return getSqlMapClientTemplate().queryForList("V_RP_DY_STP.filterDay", map);
   	}
}