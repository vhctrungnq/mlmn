package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyBranch2G3G;

public class VRpDyBranch2G3GDAOImpl extends SqlMapClientDaoSupport implements VRpDyBranch2G3GDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BRANCH_2G_3G
     *
     * @ibatorgenerated Thu May 26 10:30:17 ICT 2011
     */
    public VRpDyBranch2G3GDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BRANCH_2G_3G
     *
     * @ibatorgenerated Thu May 26 10:30:17 ICT 2011
     */
    public void insert(VRpDyBranch2G3G record) {
        getSqlMapClientTemplate().insert("V_RP_DY_BRANCH_2G_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BRANCH_2G_3G
     *
     * @ibatorgenerated Thu May 26 10:30:17 ICT 2011
     */
    public void insertSelective(VRpDyBranch2G3G record) {
        getSqlMapClientTemplate().insert("V_RP_DY_BRANCH_2G_3G.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpDyBranch2G3G> filter(String startDate, String endDate, String region) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("startDate", startDate);
		map.put("endDate",  endDate);
		map.put("region",  region);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_BRANCH_2G_3G.filter", map);
	}
}