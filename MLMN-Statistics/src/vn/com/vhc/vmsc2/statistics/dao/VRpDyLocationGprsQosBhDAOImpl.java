package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyLocationGprsQosBh;

public class VRpDyLocationGprsQosBhDAOImpl extends SqlMapClientDaoSupport implements VRpDyLocationGprsQosBhDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_LOCATION_GPRS_QOS_BH
     *
     * @ibatorgenerated Fri Nov 19 10:35:23 ICT 2010
     */
    public VRpDyLocationGprsQosBhDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_LOCATION_GPRS_QOS_BH
     *
     * @ibatorgenerated Fri Nov 19 10:35:23 ICT 2010
     */
    public void insert(VRpDyLocationGprsQosBh record) {
        getSqlMapClientTemplate().insert("V_RP_DY_LOCATION_GPRS_QOS_BH.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_LOCATION_GPRS_QOS_BH
     *
     * @ibatorgenerated Fri Nov 19 10:35:23 ICT 2010
     */
    public void insertSelective(VRpDyLocationGprsQosBh record) {
        getSqlMapClientTemplate().insert("V_RP_DY_LOCATION_GPRS_QOS_BH.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpDyLocationGprsQosBh> filterDetails(String startDate, String endDate, String location, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("location", location);
		map.put("startDate", startDate);
		map.put("endDate",  endDate);
		map.put("region", region);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_LOCATION_GPRS_QOS_BH.filterDetails", map);
	}
}