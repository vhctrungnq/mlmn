package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyHlrforbe;

public class VRpDyHlrforbeDAOImpl extends SqlMapClientDaoSupport implements VRpDyHlrforbeDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_HLR_FOR_BE
     *
     * @ibatorgenerated Tue Nov 27 14:29:06 ICT 2012
     */
    public VRpDyHlrforbeDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_HLR_FOR_BE
     *
     * @ibatorgenerated Tue Nov 27 14:29:06 ICT 2012
     */
    public void insert(VRpDyHlrforbe record) {
        getSqlMapClientTemplate().insert("V_RP_DY_HLR_FOR_BE.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_HLR_FOR_BE
     *
     * @ibatorgenerated Tue Nov 27 14:29:06 ICT 2012
     */
    public void insertSelective(VRpDyHlrforbe record) {
        getSqlMapClientTemplate().insert("V_RP_DY_HLR_FOR_BE.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
	public List<VRpDyHlrforbe> filter(String startDate, String endDate, String nodeid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("nodeid", nodeid);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_HLR_FOR_BE.filter", map);
	}
}