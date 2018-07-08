package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkLocationGprsQosBh;

public class VRpWkLocationGprsQosBhDAOImpl extends SqlMapClientDaoSupport implements VRpWkLocationGprsQosBhDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_LOCATION_GPRS_QOS_BH
     *
     * @ibatorgenerated Fri Nov 19 10:35:18 ICT 2010
     */
    public VRpWkLocationGprsQosBhDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_LOCATION_GPRS_QOS_BH
     *
     * @ibatorgenerated Fri Nov 19 10:35:18 ICT 2010
     */
    public void insert(VRpWkLocationGprsQosBh record) {
        getSqlMapClientTemplate().insert("V_RP_WK_LOCATION_GPRS_QOS_BH.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_LOCATION_GPRS_QOS_BH
     *
     * @ibatorgenerated Fri Nov 19 10:35:18 ICT 2010
     */
    public void insertSelective(VRpWkLocationGprsQosBh record) {
        getSqlMapClientTemplate().insert("V_RP_WK_LOCATION_GPRS_QOS_BH.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpWkLocationGprsQosBh> filterDetails(String startWeek, String startYear, String endWeek, String endYear, String location, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("location", location);
		map.put("startWeek", startWeek);
		map.put("startYear",  startYear);
		map.put("endWeek", endWeek);
		map.put("endYear",  endYear);
		map.put("region", region);
		return getSqlMapClientTemplate().queryForList("V_RP_WK_LOCATION_GPRS_QOS_BH.filterDetails", map);
	}
}