package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyDistrictGprsQos;

public class VRpDyDistrictGprsQosDAOImpl extends SqlMapClientDaoSupport implements VRpDyDistrictGprsQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_DISTRICT_GPRS_QOS
     *
     * @ibatorgenerated Tue Dec 21 10:39:25 ICT 2010
     */
    public VRpDyDistrictGprsQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_DISTRICT_GPRS_QOS
     *
     * @ibatorgenerated Tue Dec 21 10:39:25 ICT 2010
     */
    public void insert(VRpDyDistrictGprsQos record) {
        getSqlMapClientTemplate().insert("V_RP_DY_DISTRICT_GPRS_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_DISTRICT_GPRS_QOS
     *
     * @ibatorgenerated Tue Dec 21 10:39:25 ICT 2010
     */
    public void insertSelective(VRpDyDistrictGprsQos record) {
        getSqlMapClientTemplate().insert("V_RP_DY_DISTRICT_GPRS_QOS.ibatorgenerated_insertSelective", record);
    }
    
	@SuppressWarnings("unchecked")
	public List<VRpDyDistrictGprsQos> filterDetails(String startDate, String endDate, String province, String district, String region) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("province", province);
		map.put("district", district);
		map.put("startDate", startDate);
		map.put("endDate",  endDate);
		map.put("region", region);
		return getSqlMapClientTemplate().queryForList("V_RP_DY_DISTRICT_GPRS_QOS.filterDetails", map);
	}
}