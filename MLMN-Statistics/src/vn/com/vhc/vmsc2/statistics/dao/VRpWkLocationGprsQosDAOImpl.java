package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkLocationGprsQos;

public class VRpWkLocationGprsQosDAOImpl extends SqlMapClientDaoSupport implements VRpWkLocationGprsQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_LOCATION_GPRS_QOS
     *
     * @ibatorgenerated Fri Nov 19 10:35:49 ICT 2010
     */
    public VRpWkLocationGprsQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_LOCATION_GPRS_QOS
     *
     * @ibatorgenerated Fri Nov 19 10:35:49 ICT 2010
     */
    public void insert(VRpWkLocationGprsQos record) {
        getSqlMapClientTemplate().insert("V_RP_WK_LOCATION_GPRS_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_LOCATION_GPRS_QOS
     *
     * @ibatorgenerated Fri Nov 19 10:35:49 ICT 2010
     */
    public void insertSelective(VRpWkLocationGprsQos record) {
        getSqlMapClientTemplate().insert("V_RP_WK_LOCATION_GPRS_QOS.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpWkLocationGprsQos> filterDetails(String startWeek, String startYear, String endWeek, String endYear, String location, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("location", location);
		map.put("startWeek", startWeek);
		map.put("startYear",  startYear);
		map.put("endWeek", endWeek);
		map.put("endYear",  endYear);
		map.put("region", region);
		return getSqlMapClientTemplate().queryForList("V_RP_WK_LOCATION_GPRS_QOS.filterDetails", map);
	}

	@SuppressWarnings("unchecked")
	public List<VRpWkLocationGprsQos> filter(String location, Integer week, Integer year, String region) {
		VRpWkLocationGprsQos key = new VRpWkLocationGprsQos();
		key.setLocation(location);
		key.setWeek(week);
		key.setYear(year);
		key.setRegion(region);
		return getSqlMapClientTemplate().queryForList("V_RP_WK_LOCATION_GPRS_QOS.filter", key);
	}
}