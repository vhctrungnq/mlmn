package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnProvinceBh;

public class VRpMnProvinceBhDAOImpl extends SqlMapClientDaoSupport implements VRpMnProvinceBhDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_PROVINCE_BH
     *
     * @ibatorgenerated Mon Dec 13 10:10:26 ICT 2010
     */
    public VRpMnProvinceBhDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_PROVINCE_BH
     *
     * @ibatorgenerated Mon Dec 13 10:10:26 ICT 2010
     */
    public void insert(VRpMnProvinceBh record) {
        getSqlMapClientTemplate().insert("V_RP_MN_PROVINCE_BH.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_PROVINCE_BH
     *
     * @ibatorgenerated Mon Dec 13 10:10:26 ICT 2010
     */
    public void insertSelective(VRpMnProvinceBh record) {
        getSqlMapClientTemplate().insert("V_RP_MN_PROVINCE_BH.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpMnProvinceBh> filter(Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String province, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("province", province);
		map.put("startMonth", Integer.toString(startMonth));
		map.put("endMonth", Integer.toString(endMonth));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
		map.put("region", region);
    	return getSqlMapClientTemplate().queryForList("V_RP_MN_PROVINCE_BH.filter", map);
	}
}