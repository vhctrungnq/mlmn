package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnBranchTraff3G;

public class VRpMnBranchTraff3GDAOImpl extends SqlMapClientDaoSupport implements VRpMnBranchTraff3GDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_BRANCH_TRAFF_3G
     *
     * @ibatorgenerated Tue May 10 14:23:21 ICT 2011
     */
    public VRpMnBranchTraff3GDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_BRANCH_TRAFF_3G
     *
     * @ibatorgenerated Tue May 10 14:23:21 ICT 2011
     */
    public void insert(VRpMnBranchTraff3G record) {
        getSqlMapClientTemplate().insert("V_RP_MN_BRANCH_TRAFF_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_BRANCH_TRAFF_3G
     *
     * @ibatorgenerated Tue May 10 14:23:21 ICT 2011
     */
    public void insertSelective(VRpMnBranchTraff3G record) {
        getSqlMapClientTemplate().insert("V_RP_MN_BRANCH_TRAFF_3G.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpMnBranchTraff3G> filter(Integer startMonth, Integer startYear, Integer endMonth, Integer endYear, String branch, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("branch", branch);
		map.put("startMonth", Integer.toString(startMonth));
		map.put("endMonth", Integer.toString(endMonth));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
		map.put("region", region);
    	return getSqlMapClientTemplate().queryForList("V_RP_MN_BRANCH_TRAFF_3G.filter", map);
	}
}