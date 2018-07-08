package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpWkBscGprsQosBh;

public class VRpWkBscGprsQosBhDAOImpl extends SqlMapClientDaoSupport implements VRpWkBscGprsQosBhDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_BSC_GPRS_QOS_BH
     *
     * @ibatorgenerated Thu Nov 18 14:42:20 ICT 2010
     */
    public VRpWkBscGprsQosBhDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_BSC_GPRS_QOS_BH
     *
     * @ibatorgenerated Thu Nov 18 14:42:20 ICT 2010
     */
    public void insert(VRpWkBscGprsQosBh record) {
        getSqlMapClientTemplate().insert("V_RP_WK_BSC_GPRS_QOS_BH.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_WK_BSC_GPRS_QOS_BH
     *
     * @ibatorgenerated Thu Nov 18 14:42:20 ICT 2010
     */
    public void insertSelective(VRpWkBscGprsQosBh record) {
        getSqlMapClientTemplate().insert("V_RP_WK_BSC_GPRS_QOS_BH.ibatorgenerated_insertSelective", record);
    }
    @SuppressWarnings("unchecked")
    public List<VRpWkBscGprsQosBh> filter(Integer startWeek,Integer startYear,Integer endWeek,Integer endYear,String bscid, String region){
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("startWeek", Integer.toString(startWeek));
		map.put("endWeek", Integer.toString(endWeek));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
		map.put("region", region);
    	return getSqlMapClientTemplate().queryForList("V_RP_WK_BSC_GPRS_QOS_BH.filter", map);
    }
}