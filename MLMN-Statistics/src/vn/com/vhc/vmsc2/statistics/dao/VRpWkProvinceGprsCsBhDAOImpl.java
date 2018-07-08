package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkProvinceGprsCsBh;

public class VRpWkProvinceGprsCsBhDAOImpl extends SqlMapClientDaoSupport implements VRpWkProvinceGprsCsBhDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_PROVINCE_GPRS_CS_BH
     *
     * @ibatorgenerated Thu Nov 18 17:00:52 ICT 2010
     */
    public VRpWkProvinceGprsCsBhDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_PROVINCE_GPRS_CS_BH
     *
     * @ibatorgenerated Thu Nov 18 17:00:52 ICT 2010
     */
    public void insert(VRpWkProvinceGprsCsBh record) {
        getSqlMapClientTemplate().insert("V_RP_WK_PROVINCE_GPRS_CS_BH.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_PROVINCE_GPRS_CS_BH
     *
     * @ibatorgenerated Thu Nov 18 17:00:52 ICT 2010
     */
    public void insertSelective(VRpWkProvinceGprsCsBh record) {
        getSqlMapClientTemplate().insert("V_RP_WK_PROVINCE_GPRS_CS_BH.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpWkProvinceGprsCsBh> filterDetails(String startWeek, String startYear, String endWeek, String endYear, String province, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("province", province);
		map.put("startWeek", startWeek);
		map.put("startYear",  startYear);
		map.put("endWeek", endWeek);
		map.put("endYear",  endYear);
		map.put("region", region);
		return getSqlMapClientTemplate().queryForList("V_RP_WK_PROVINCE_GPRS_CS_BH.filterDetails", map);
	}
}