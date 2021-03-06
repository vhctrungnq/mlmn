package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpQrIrsrAlarmRegion3g;

public class VRpQrIrsrAlarmRegion3gDAOImpl extends SqlMapClientDaoSupport implements VRpQrIrsrAlarmRegion3gDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_QR_IRSR_ALARM_REGION_3G
     *
     * @ibatorgenerated Thu Aug 04 08:59:53 ICT 2011
     */
    public VRpQrIrsrAlarmRegion3gDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_QR_IRSR_ALARM_REGION_3G
     *
     * @ibatorgenerated Thu Aug 04 08:59:53 ICT 2011
     */
    public void insert(VRpQrIrsrAlarmRegion3g record) {
        getSqlMapClientTemplate().insert("V_RP_QR_IRSR_ALARM_REGION_3G.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_QR_IRSR_ALARM_REGION_3G
     *
     * @ibatorgenerated Thu Aug 04 08:59:53 ICT 2011
     */
    public void insertSelective(VRpQrIrsrAlarmRegion3g record) {
        getSqlMapClientTemplate().insert("V_RP_QR_IRSR_ALARM_REGION_3G.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpQrIrsrAlarmRegion3g> filter(String region, Integer startQuarter, Integer startYear, Integer endQuarter, Integer endYear) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("region", region);
		map.put("startQuarter", Integer.toString(startQuarter));
		map.put("endQuarter", Integer.toString(endQuarter));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
    	return getSqlMapClientTemplate().queryForList("V_RP_QR_IRSR_ALARM_REGION_3G.filter", map);
	}
}