package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnBscBh;

public class VRpMnBscBhDAOImpl extends SqlMapClientDaoSupport implements VRpMnBscBhDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_BSC_BH
     *
     * @ibatorgenerated Mon Dec 13 09:58:44 ICT 2010
     */
    public VRpMnBscBhDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_BSC_BH
     *
     * @ibatorgenerated Mon Dec 13 09:58:44 ICT 2010
     */
    public void insert(VRpMnBscBh record) {
        getSqlMapClientTemplate().insert("V_RP_MN_BSC_BH.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_BSC_BH
     *
     * @ibatorgenerated Mon Dec 13 09:58:44 ICT 2010
     */
    public void insertSelective(VRpMnBscBh record) {
        getSqlMapClientTemplate().insert("V_RP_MN_BSC_BH.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpMnBscBh> filter(Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String bscid, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("startMonth", Integer.toString(startMonth));
		map.put("endMonth", Integer.toString(endMonth));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
		map.put("region", region);
    	return getSqlMapClientTemplate().queryForList("V_RP_MN_BSC_BH.filter", map);
	}
}