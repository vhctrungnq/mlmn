package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyRegionGprsQos;

public class VRpDyRegionGprsQosDAOImpl extends SqlMapClientDaoSupport implements VRpDyRegionGprsQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_REGION_GPRS_QOS
     *
     * @ibatorgenerated Wed May 25 09:42:29 ICT 2011
     */
    public VRpDyRegionGprsQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_REGION_GPRS_QOS
     *
     * @ibatorgenerated Wed May 25 09:42:29 ICT 2011
     */
    public void insert(VRpDyRegionGprsQos record) {
        getSqlMapClientTemplate().insert("V_RP_DY_REGION_GPRS_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_REGION_GPRS_QOS
     *
     * @ibatorgenerated Wed May 25 09:42:29 ICT 2011
     */
    public void insertSelective(VRpDyRegionGprsQos record) {
        getSqlMapClientTemplate().insert("V_RP_DY_REGION_GPRS_QOS.ibatorgenerated_insertSelective", record);
    }

	@SuppressWarnings("unchecked")
	public List<VRpDyRegionGprsQos> filter(String startDate, String endDate) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("startDate", startDate);
		map.put("endDate",  endDate);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_REGION_GPRS_QOS.filter", map);
	}

	@SuppressWarnings("unchecked")
	public List<VRpDyRegionGprsQos> filter(String startDate, String endDate, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("startDate", startDate);
		map.put("endDate",  endDate);
		map.put("region",  region);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_REGION_GPRS_QOS.filter", map);
	}
}