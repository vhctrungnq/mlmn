package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnDistrictGprsQosBh;

public class VRpMnDistrictGprsQosBhDAOImpl extends SqlMapClientDaoSupport implements VRpMnDistrictGprsQosBhDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_DISTRICT_GPRS_QOS_BH
     *
     * @ibatorgenerated Tue Dec 21 10:38:53 ICT 2010
     */
    public VRpMnDistrictGprsQosBhDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_DISTRICT_GPRS_QOS_BH
     *
     * @ibatorgenerated Tue Dec 21 10:38:53 ICT 2010
     */
    public void insert(VRpMnDistrictGprsQosBh record) {
        getSqlMapClientTemplate().insert("V_RP_MN_DISTRICT_GPRS_QOS_BH.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_DISTRICT_GPRS_QOS_BH
     *
     * @ibatorgenerated Tue Dec 21 10:38:53 ICT 2010
     */
    public void insertSelective(VRpMnDistrictGprsQosBh record) {
        getSqlMapClientTemplate().insert("V_RP_MN_DISTRICT_GPRS_QOS_BH.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpMnDistrictGprsQosBh> filterDetails(String province, String district, String startMonth, String startYear, String endMonth, String endYear, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("province", province);
		map.put("district", district);
		map.put("startMonth", startMonth);
		map.put("startYear",  startYear);
		map.put("endMonth", endMonth);
		map.put("endYear",  endYear);
		map.put("region", region);
		return getSqlMapClientTemplate().queryForList("V_RP_MN_DISTRICT_GPRS_QOS_BH.filterDetails", map);
	}
}