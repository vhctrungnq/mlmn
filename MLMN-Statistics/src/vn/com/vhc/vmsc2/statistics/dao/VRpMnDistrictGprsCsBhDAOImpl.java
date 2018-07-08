package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnDistrictGprsCsBh;

public class VRpMnDistrictGprsCsBhDAOImpl extends SqlMapClientDaoSupport implements VRpMnDistrictGprsCsBhDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_DISTRICT_GPRS_CS_BH
     *
     * @ibatorgenerated Tue Dec 21 11:13:58 ICT 2010
     */
    public VRpMnDistrictGprsCsBhDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_DISTRICT_GPRS_CS_BH
     *
     * @ibatorgenerated Tue Dec 21 11:13:58 ICT 2010
     */
    public void insert(VRpMnDistrictGprsCsBh record) {
        getSqlMapClientTemplate().insert("V_RP_MN_DISTRICT_GPRS_CS_BH.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_DISTRICT_GPRS_CS_BH
     *
     * @ibatorgenerated Tue Dec 21 11:13:58 ICT 2010
     */
    public void insertSelective(VRpMnDistrictGprsCsBh record) {
        getSqlMapClientTemplate().insert("V_RP_MN_DISTRICT_GPRS_CS_BH.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpMnDistrictGprsCsBh> filterDetails(String startMonth, String startYear, String endMonth, String endYear, String province, String district, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("province", province);
		map.put("district", district);
		map.put("startMonth", startMonth);
		map.put("startYear",  startYear);
		map.put("endMonth", endMonth);
		map.put("endYear",  endYear);
		map.put("region", region);
		return getSqlMapClientTemplate().queryForList("V_RP_MN_DISTRICT_GPRS_CS_BH.filterDetails", map);
	}
}