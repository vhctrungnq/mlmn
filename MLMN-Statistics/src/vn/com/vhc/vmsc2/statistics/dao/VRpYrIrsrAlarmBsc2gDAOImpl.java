package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpYrIrsrAlarmBsc2g;

public class VRpYrIrsrAlarmBsc2gDAOImpl extends SqlMapClientDaoSupport implements VRpYrIrsrAlarmBsc2gDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_YR_IRSR_ALARM_BSC_2G
     *
     * @ibatorgenerated Thu Aug 04 08:59:53 ICT 2011
     */
    public VRpYrIrsrAlarmBsc2gDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_YR_IRSR_ALARM_BSC_2G
     *
     * @ibatorgenerated Thu Aug 04 08:59:53 ICT 2011
     */
    public void insert(VRpYrIrsrAlarmBsc2g record) {
        getSqlMapClientTemplate().insert("V_RP_YR_IRSR_ALARM_BSC_2G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_YR_IRSR_ALARM_BSC_2G
     *
     * @ibatorgenerated Thu Aug 04 08:59:53 ICT 2011
     */
    public void insertSelective(VRpYrIrsrAlarmBsc2g record) {
        getSqlMapClientTemplate().insert("V_RP_YR_IRSR_ALARM_BSC_2G.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpYrIrsrAlarmBsc2g> filter(String bscid, Integer startYear, Integer endYear) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
    	return getSqlMapClientTemplate().queryForList("V_RP_YR_IRSR_ALARM_BSC_2G.filter", map);
	}
}