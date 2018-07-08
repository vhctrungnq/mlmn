package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyBscCssrDetails3G;

public class VRpDyBscCssrDetails3GDAOImpl extends SqlMapClientDaoSupport implements VRpDyBscCssrDetails3GDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BSC_CSSR_DETAILS_3G
     *
     * @ibatorgenerated Mon May 16 09:59:40 ICT 2011
     */
    public VRpDyBscCssrDetails3GDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BSC_CSSR_DETAILS_3G
     *
     * @ibatorgenerated Mon May 16 09:59:40 ICT 2011
     */
    public void insert(VRpDyBscCssrDetails3G record) {
        getSqlMapClientTemplate().insert("V_RP_DY_BSC_CSSR_DETAILS_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BSC_CSSR_DETAILS_3G
     *
     * @ibatorgenerated Mon May 16 09:59:40 ICT 2011
     */
    public void insertSelective(VRpDyBscCssrDetails3G record) {
        getSqlMapClientTemplate().insert("V_RP_DY_BSC_CSSR_DETAILS_3G.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpDyBscCssrDetails3G> filter(String startDate, String endDate, String bscid, String region) {
		Map<String, String> map = new HashMap<String, String>();
    	map.put("bscid", bscid);
    	map.put("startDate", startDate);
    	map.put("endDate", endDate);
		map.put("region", region);
    return getSqlMapClientTemplate().queryForList("V_RP_DY_BSC_CSSR_DETAILS_3G.filter", map);
	}
}