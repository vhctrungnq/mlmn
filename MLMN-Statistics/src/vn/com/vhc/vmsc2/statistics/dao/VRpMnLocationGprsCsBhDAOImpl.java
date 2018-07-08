package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnLocationGprsCsBh;

public class VRpMnLocationGprsCsBhDAOImpl extends SqlMapClientDaoSupport implements VRpMnLocationGprsCsBhDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_LOCATION_GPRS_CS_BH
     *
     * @ibatorgenerated Fri Nov 19 10:36:56 ICT 2010
     */
    public VRpMnLocationGprsCsBhDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_LOCATION_GPRS_CS_BH
     *
     * @ibatorgenerated Fri Nov 19 10:36:56 ICT 2010
     */
    public void insert(VRpMnLocationGprsCsBh record) {
        getSqlMapClientTemplate().insert("V_RP_MN_LOCATION_GPRS_CS_BH.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_LOCATION_GPRS_CS_BH
     *
     * @ibatorgenerated Fri Nov 19 10:36:56 ICT 2010
     */
    public void insertSelective(VRpMnLocationGprsCsBh record) {
        getSqlMapClientTemplate().insert("V_RP_MN_LOCATION_GPRS_CS_BH.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpMnLocationGprsCsBh> filterDetails(String startMonth, String startYear, String endMonth, String endYear, String location, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("location", location);
		map.put("startMonth", startMonth);
		map.put("startYear",  startYear);
		map.put("endMonth", endMonth);
		map.put("endYear",  endYear);
		map.put("region", region);
		return getSqlMapClientTemplate().queryForList("V_RP_MN_LOCATION_GPRS_CS_BH.filterDetails", map);
	}
}