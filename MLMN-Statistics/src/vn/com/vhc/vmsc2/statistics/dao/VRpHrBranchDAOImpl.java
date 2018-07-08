package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpHrBranch;

public class VRpHrBranchDAOImpl extends SqlMapClientDaoSupport implements VRpHrBranchDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BRANCH
     *
     * @ibatorgenerated Mon Feb 21 14:31:48 ICT 2011
     */
    public VRpHrBranchDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BRANCH
     *
     * @ibatorgenerated Mon Feb 21 14:31:48 ICT 2011
     */
    public void insert(VRpHrBranch record) {
        getSqlMapClientTemplate().insert("V_RP_HR_BRANCH.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_HR_BRANCH
     *
     * @ibatorgenerated Mon Feb 21 14:31:48 ICT 2011
     */
    public void insertSelective(VRpHrBranch record) {
        getSqlMapClientTemplate().insert("V_RP_HR_BRANCH.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpHrBranch> filter(String startHour, String endHour, String day, String branch, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("startHour", startHour);
		map.put("endHour", endHour);
		map.put("day", day);
		map.put("branch", branch);
		map.put("region", region);
    	return getSqlMapClientTemplate().queryForList("V_RP_HR_BRANCH.filter2", map);
	}

	@SuppressWarnings("unchecked")
	public List<VRpHrBranch> filter(Date d, Integer hour, String branch, String region) {
		VRpHrBranch key = new VRpHrBranch();
    	key.setDay(d);
    	key.setHour(hour);
    	key.setBranch(branch);
    	key.setRegion(region);
    	return getSqlMapClientTemplate().queryForList("V_RP_HR_BRANCH.filter", key);
	}
}