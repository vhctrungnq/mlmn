package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpYrBscQos;

public class VRpYrBscQosDAOImpl extends SqlMapClientDaoSupport implements VRpYrBscQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_YR_BSC_QOS
     *
     * @ibatorgenerated Tue Oct 06 10:16:44 ICT 2015
     */
    public VRpYrBscQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_YR_BSC_QOS
     *
     * @ibatorgenerated Tue Oct 06 10:16:44 ICT 2015
     */
    public void insert(VRpYrBscQos record) {
        getSqlMapClientTemplate().insert("V_RP_YR_BSC_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_YR_BSC_QOS
     *
     * @ibatorgenerated Tue Oct 06 10:16:44 ICT 2015
     */
    public void insertSelective(VRpYrBscQos record) {
        getSqlMapClientTemplate().insert("V_RP_YR_BSC_QOS.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpYrBscQos> filter(String startYear, String endYear, String bscid, String region, int order, String column) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_START_YEAR", startYear);
		map.put("P_END_YEAR", endYear);
		map.put("P_BSCID", bscid);
		map.put("P_REGION", region);
		map.put("P_ORDER", order);
		map.put("P_COLUMN", column);
		map.put("P_DATA", null);
    	return getSqlMapClientTemplate().queryForList("V_RP_YR_BSC_QOS.filter", map);
	}
}