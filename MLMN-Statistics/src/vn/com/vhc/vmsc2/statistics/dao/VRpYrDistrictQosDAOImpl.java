package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpYrDistrictQos;

public class VRpYrDistrictQosDAOImpl extends SqlMapClientDaoSupport implements VRpYrDistrictQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_YR_DISTRICT_QOS
     *
     * @ibatorgenerated Tue Oct 06 15:56:50 ICT 2015
     */
    public VRpYrDistrictQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_YR_DISTRICT_QOS
     *
     * @ibatorgenerated Tue Oct 06 15:56:50 ICT 2015
     */
    public void insert(VRpYrDistrictQos record) {
        getSqlMapClientTemplate().insert("V_RP_YR_DISTRICT_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_YR_DISTRICT_QOS
     *
     * @ibatorgenerated Tue Oct 06 15:56:50 ICT 2015
     */
    public void insertSelective(VRpYrDistrictQos record) {
        getSqlMapClientTemplate().insert("V_RP_YR_DISTRICT_QOS.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpYrDistrictQos> filter(String startYear, String endYear, String province, String district, String region, Integer order, String column) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_START_YEAR", startYear);
		map.put("P_END_YEAR", endYear);
		map.put("P_PROVINCE", province);
		map.put("P_DISTRICT", district);
		map.put("P_REGION", region);
		map.put("P_ORDER", order);
		map.put("P_COLUMN", column);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("V_RP_YR_DISTRICT_QOS.filter", map);
	}
}