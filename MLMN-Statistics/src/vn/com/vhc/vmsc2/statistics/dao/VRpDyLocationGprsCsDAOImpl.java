package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyLocationGprsCs;

public class VRpDyLocationGprsCsDAOImpl extends SqlMapClientDaoSupport implements VRpDyLocationGprsCsDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_LOCATION_GPRS_CS
     *
     * @ibatorgenerated Fri Nov 19 10:36:11 ICT 2010
     */
    public VRpDyLocationGprsCsDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_LOCATION_GPRS_CS
     *
     * @ibatorgenerated Fri Nov 19 10:36:11 ICT 2010
     */
    public void insert(VRpDyLocationGprsCs record) {
        getSqlMapClientTemplate().insert("V_RP_DY_LOCATION_GPRS_CS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_LOCATION_GPRS_CS
     *
     * @ibatorgenerated Fri Nov 19 10:36:11 ICT 2010
     */
    public void insertSelective(VRpDyLocationGprsCs record) {
        getSqlMapClientTemplate().insert("V_RP_DY_LOCATION_GPRS_CS.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpDyLocationGprsCs> filter(String location, Date d, String region) {
		VRpDyLocationGprsCs key = new VRpDyLocationGprsCs();
		key.setLocation(location);
		key.setDay(d);
		key.setRegion(region);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_LOCATION_GPRS_CS.filter", key);
	}

	@SuppressWarnings("unchecked")
	public List<VRpDyLocationGprsCs> filterDetails(String startDate, String endDate, String location, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("location", location);
		map.put("startDate", startDate);
		map.put("endDate",  endDate);
		map.put("region", region);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_LOCATION_GPRS_CS.filterDetails", map);
	}
}