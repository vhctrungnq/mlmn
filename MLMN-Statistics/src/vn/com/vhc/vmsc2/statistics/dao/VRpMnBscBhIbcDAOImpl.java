package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnBscBhIbc;

public class VRpMnBscBhIbcDAOImpl extends SqlMapClientDaoSupport implements VRpMnBscBhIbcDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_BSC_BH_IBC
     *
     * @ibatorgenerated Mon Dec 13 09:58:44 ICT 2010
     */
    public VRpMnBscBhIbcDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_BSC_BH_IBC
     *
     * @ibatorgenerated Mon Dec 13 09:58:44 ICT 2010
     */
    public void insert(VRpMnBscBhIbc record) {
        getSqlMapClientTemplate().insert("V_RP_MN_BSC_BH_IBC.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_BSC_BH_IBC
     *
     * @ibatorgenerated Mon Dec 13 09:58:44 ICT 2010
     */
    public void insertSelective(VRpMnBscBhIbc record) {
        getSqlMapClientTemplate().insert("V_RP_MN_BSC_BH_IBC.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpMnBscBhIbc> filter(Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String bscid, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("startMonth", Integer.toString(startMonth));
		map.put("endMonth", Integer.toString(endMonth));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
		map.put("region", region);
    	return getSqlMapClientTemplate().queryForList("V_RP_MN_BSC_BH_IBC.filter", map);
	}
}