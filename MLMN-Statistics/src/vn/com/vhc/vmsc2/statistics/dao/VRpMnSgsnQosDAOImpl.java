package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnSgsnQos;

public class VRpMnSgsnQosDAOImpl extends SqlMapClientDaoSupport implements VRpMnSgsnQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_SGSN_QOS
     *
     * @ibatorgenerated Wed Jun 22 15:29:43 ICT 2011
     */
    public VRpMnSgsnQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_SGSN_QOS
     *
     * @ibatorgenerated Wed Jun 22 15:29:43 ICT 2011
     */
    public void insert(VRpMnSgsnQos record) {
        getSqlMapClientTemplate().insert("V_RP_MN_SGSN_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_SGSN_QOS
     *
     * @ibatorgenerated Wed Jun 22 15:29:43 ICT 2011
     */
    public void insertSelective(VRpMnSgsnQos record) {
        getSqlMapClientTemplate().insert("V_RP_MN_SGSN_QOS.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpMnSgsnQos> filter(Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String sgsnid, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sgsnid", sgsnid);
		map.put("startMonth", Integer.toString(startMonth));
		map.put("endMonth", Integer.toString(endMonth));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
		map.put("region", region);
    	return getSqlMapClientTemplate().queryForList("V_RP_MN_SGSN_QOS.filter", map);
	}
}