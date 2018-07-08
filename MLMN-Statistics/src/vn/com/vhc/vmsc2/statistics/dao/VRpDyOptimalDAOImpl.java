package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import vn.com.vhc.vmsc2.statistics.domain.VRpDyOptimal;

public class VRpDyOptimalDAOImpl extends SqlMapClientDaoSupport implements VRpDyOptimalDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_OPTIMAL
     *
     * @ibatorgenerated Mon Apr 03 14:50:09 ICT 2017
     */
    public VRpDyOptimalDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_OPTIMAL
     *
     * @ibatorgenerated Mon Apr 03 14:50:09 ICT 2017
     */
    public void insert(VRpDyOptimal record) {
        getSqlMapClientTemplate().insert("V_RP_DY_OPTIMAL.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_OPTIMAL
     *
     * @ibatorgenerated Mon Apr 03 14:50:09 ICT 2017
     */
    public void insertSelective(VRpDyOptimal record) {
        getSqlMapClientTemplate().insert("V_RP_DY_OPTIMAL.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<VRpDyOptimal> getDyData(String startDate, String endDate) {
		// TODO Auto-generated method stub
		Map<String, String> parms = new HashMap<String, String>();
		parms.put("p_start_date", startDate);
		parms.put("p_end_date", endDate);
		return  getSqlMapClientTemplate().queryForList("V_RP_DY_OPTIMAL.getDyData", parms);
	}
}