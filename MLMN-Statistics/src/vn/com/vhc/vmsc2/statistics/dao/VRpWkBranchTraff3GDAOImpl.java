package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkBranchTraff3G;

public class VRpWkBranchTraff3GDAOImpl extends SqlMapClientDaoSupport implements VRpWkBranchTraff3GDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_BRANCH_TRAFF_3G
     *
     * @ibatorgenerated Tue May 10 14:24:37 ICT 2011
     */
    public VRpWkBranchTraff3GDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_BRANCH_TRAFF_3G
     *
     * @ibatorgenerated Tue May 10 14:24:37 ICT 2011
     */
    public void insert(VRpWkBranchTraff3G record) {
        getSqlMapClientTemplate().insert("V_RP_WK_BRANCH_TRAFF_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_BRANCH_TRAFF_3G
     *
     * @ibatorgenerated Tue May 10 14:24:37 ICT 2011
     */
    public void insertSelective(VRpWkBranchTraff3G record) {
        getSqlMapClientTemplate().insert("V_RP_WK_BRANCH_TRAFF_3G.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpWkBranchTraff3G> filter(Integer startWeek, Integer startYear, Integer endWeek, Integer endYear, String branch, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("branch", branch);
		map.put("startWeek", Integer.toString(startWeek));
		map.put("endWeek", Integer.toString(endWeek));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
		map.put("region", region);
    	return getSqlMapClientTemplate().queryForList("V_RP_WK_BRANCH_TRAFF_3G.filter", map);
	}
}