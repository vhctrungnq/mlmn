package vn.com.vhc.vmsc2.statistics.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpDyBscGprsQos;

public class VRpDyBscGprsQosDAOImpl extends SqlMapClientDaoSupport implements VRpDyBscGprsQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BSC_GPRS_QOS
     *
     * @ibatorgenerated Thu Nov 18 17:14:07 ICT 2010
     */
    public VRpDyBscGprsQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BSC_GPRS_QOS
     *
     * @ibatorgenerated Thu Nov 18 17:14:07 ICT 2010
     */
    public void insert(VRpDyBscGprsQos record) {
        getSqlMapClientTemplate().insert("V_RP_DY_BSC_GPRS_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_DY_BSC_GPRS_QOS
     *
     * @ibatorgenerated Thu Nov 18 17:14:07 ICT 2010
     */
    public void insertSelective(VRpDyBscGprsQos record) {
        getSqlMapClientTemplate().insert("V_RP_DY_BSC_GPRS_QOS.ibatorgenerated_insertSelective", record);
    }
    @SuppressWarnings("unchecked")
    public List<VRpDyBscGprsQos> filter(Date day, String bscid, String region){
    	VRpDyBscGprsQos key = new VRpDyBscGprsQos();
    	key.setBscid(bscid);
    	key.setDay(day);
    	key.setRegion(region);
    	return getSqlMapClientTemplate().queryForList("V_RP_DY_BSC_GPRS_QOS.filter", key);
    }
    
    @SuppressWarnings("unchecked")
    public List<VRpDyBscGprsQos> filter(String startDate,String endDate, String bscid, String region){
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("region", region);
    	return getSqlMapClientTemplate().queryForList("V_RP_DY_BSC_GPRS_QOS.filter2", map);
    }
}