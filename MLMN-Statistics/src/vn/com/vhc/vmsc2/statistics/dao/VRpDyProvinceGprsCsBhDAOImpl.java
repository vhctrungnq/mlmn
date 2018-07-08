package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyProvinceGprsCsBh;

public class VRpDyProvinceGprsCsBhDAOImpl extends SqlMapClientDaoSupport implements VRpDyProvinceGprsCsBhDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_PROVINCE_GPRS_CS_BH
     *
     * @ibatorgenerated Thu Nov 18 17:00:18 ICT 2010
     */
    public VRpDyProvinceGprsCsBhDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_PROVINCE_GPRS_CS_BH
     *
     * @ibatorgenerated Thu Nov 18 17:00:18 ICT 2010
     */
    public void insert(VRpDyProvinceGprsCsBh record) {
        getSqlMapClientTemplate().insert("V_RP_DY_PROVINCE_GPRS_CS_BH.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_PROVINCE_GPRS_CS_BH
     *
     * @ibatorgenerated Thu Nov 18 17:00:18 ICT 2010
     */
    public void insertSelective(VRpDyProvinceGprsCsBh record) {
        getSqlMapClientTemplate().insert("V_RP_DY_PROVINCE_GPRS_CS_BH.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpDyProvinceGprsCsBh> filterDetails(String startDate, String endDate, String province, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("province", province);
		map.put("startDate", startDate);
		map.put("endDate",  endDate);
		map.put("region", region);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_PROVINCE_GPRS_CS_BH.filterDetails", map);
	}
}