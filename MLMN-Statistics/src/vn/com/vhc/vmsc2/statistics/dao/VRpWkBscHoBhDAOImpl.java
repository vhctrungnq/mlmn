package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkBscHoBh;

public class VRpWkBscHoBhDAOImpl extends SqlMapClientDaoSupport implements VRpWkBscHoBhDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_BSC_HO_BH
     *
     * @ibatorgenerated Thu Nov 18 15:01:01 ICT 2010
     */
    public VRpWkBscHoBhDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_BSC_HO_BH
     *
     * @ibatorgenerated Thu Nov 18 15:01:01 ICT 2010
     */
    public void insert(VRpWkBscHoBh record) {
        getSqlMapClientTemplate().insert("V_RP_WK_BSC_HO_BH.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_BSC_HO_BH
     *
     * @ibatorgenerated Thu Nov 18 15:01:01 ICT 2010
     */
    public void insertSelective(VRpWkBscHoBh record) {
        getSqlMapClientTemplate().insert("V_RP_WK_BSC_HO_BH.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpWkBscHoBh> filterDetails(String startWeek, String startYear, String endWeek, String endYear, String bscid, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("startWeek", startWeek);
		map.put("startYear",  startYear);
		map.put("endWeek", endWeek);
		map.put("endYear",  endYear);
		map.put("region", region);
		return getSqlMapClientTemplate().queryForList("V_RP_WK_BSC_HO_BH.filterDetails", map);
	}
}