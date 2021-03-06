package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpYrDistrictGprsCs;

public class VRpYrDistrictGprsCsDAOImpl extends SqlMapClientDaoSupport implements VRpYrDistrictGprsCsDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_YR_DISTRICT_GPRS_CS
     *
     * @ibatorgenerated Wed Sep 30 15:46:20 ICT 2015
     */
    public VRpYrDistrictGprsCsDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_YR_DISTRICT_GPRS_CS
     *
     * @ibatorgenerated Wed Sep 30 15:46:20 ICT 2015
     */
    public void insert(VRpYrDistrictGprsCs record) {
        getSqlMapClientTemplate().insert("V_RP_YR_DISTRICT_GPRS_CS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_YR_DISTRICT_GPRS_CS
     *
     * @ibatorgenerated Wed Sep 30 15:46:20 ICT 2015
     */
    public void insertSelective(VRpYrDistrictGprsCs record) {
        getSqlMapClientTemplate().insert("V_RP_YR_DISTRICT_GPRS_CS.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpYrDistrictGprsCs> filter(String startYear, String endYear, String province, String district, String region, Integer order, String column) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_START_YEAR", startYear);
		map.put("P_END_YEAR", endYear);
		map.put("P_PROVINCE", province);
		map.put("P_DISTRICT", district);
		map.put("P_REGION", region);
		map.put("P_ORDER", order);
		map.put("P_COLUMN", column);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("V_RP_YR_DISTRICT_GPRS_CS.filter", map);
	}
}