package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpYrIrsrAlarmRegion3g;

public class VRpYrIrsrAlarmRegion3gDAOImpl extends SqlMapClientDaoSupport implements VRpYrIrsrAlarmRegion3gDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_YR_IRSR_ALARM_REGION_3G
     *
     * @ibatorgenerated Thu Aug 04 08:59:53 ICT 2011
     */
    public VRpYrIrsrAlarmRegion3gDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_YR_IRSR_ALARM_REGION_3G
     *
     * @ibatorgenerated Thu Aug 04 08:59:53 ICT 2011
     */
    public void insert(VRpYrIrsrAlarmRegion3g record) {
        getSqlMapClientTemplate().insert("V_RP_YR_IRSR_ALARM_REGION_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_YR_IRSR_ALARM_REGION_3G
     *
     * @ibatorgenerated Thu Aug 04 08:59:53 ICT 2011
     */
    public void insertSelective(VRpYrIrsrAlarmRegion3g record) {
        getSqlMapClientTemplate().insert("V_RP_YR_IRSR_ALARM_REGION_3G.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpYrIrsrAlarmRegion3g> filter(String region, Integer startYear, Integer endYear) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("region", region);
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
    	return getSqlMapClientTemplate().queryForList("V_RP_YR_IRSR_ALARM_REGION_3G.filter", map);
	}
}