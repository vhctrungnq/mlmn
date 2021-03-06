package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpQrDistrictGprsCs;

public class VRpQrDistrictGprsCsDAOImpl extends SqlMapClientDaoSupport implements VRpQrDistrictGprsCsDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_QR_DISTRICT_GPRS_CS
     *
     * @ibatorgenerated Wed Sep 30 15:46:04 ICT 2015
     */
    public VRpQrDistrictGprsCsDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_QR_DISTRICT_GPRS_CS
     *
     * @ibatorgenerated Wed Sep 30 15:46:04 ICT 2015
     */
    public void insert(VRpQrDistrictGprsCs record) {
        getSqlMapClientTemplate().insert("V_RP_QR_DISTRICT_GPRS_CS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_QR_DISTRICT_GPRS_CS
     *
     * @ibatorgenerated Wed Sep 30 15:46:04 ICT 2015
     */
    public void insertSelective(VRpQrDistrictGprsCs record) {
        getSqlMapClientTemplate().insert("V_RP_QR_DISTRICT_GPRS_CS.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpQrDistrictGprsCs> filter(String startQuarter, String startYear, String endQuarter, String endYear, String province, String district,
			String region, Integer order, String column) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("P_START_QUARTER", startQuarter);
		map.put("P_START_YEAR", startYear);
		map.put("P_END_QUARTER", endQuarter);
		map.put("P_END_YEAR", endYear);
		map.put("P_PROVINCE", province);
		map.put("P_DISTRICT", district);
		map.put("P_REGION", region);
		map.put("P_ORDER", order);
		map.put("P_COLUMN", column);
		map.put("P_DATA", null);
		return getSqlMapClientTemplate().queryForList("V_RP_QR_DISTRICT_GPRS_CS.filter", map);
	}
}