package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnBscGprsCsBh;

public class VRpMnBscGprsCsBhDAOImpl extends SqlMapClientDaoSupport implements VRpMnBscGprsCsBhDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_BSC_GPRS_CS_BH
     *
     * @ibatorgenerated Thu Nov 18 14:43:32 ICT 2010
     */
    public VRpMnBscGprsCsBhDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_BSC_GPRS_CS_BH
     *
     * @ibatorgenerated Thu Nov 18 14:43:32 ICT 2010
     */
    public void insert(VRpMnBscGprsCsBh record) {
        getSqlMapClientTemplate().insert("V_RP_MN_BSC_GPRS_CS_BH.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_BSC_GPRS_CS_BH
     *
     * @ibatorgenerated Thu Nov 18 14:43:32 ICT 2010
     */
    public void insertSelective(VRpMnBscGprsCsBh record) {
        getSqlMapClientTemplate().insert("V_RP_MN_BSC_GPRS_CS_BH.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
    public List<VRpMnBscGprsCsBh> filter(Integer startMonth,Integer startYear, Integer endMonth,Integer endYear,String bscid, String region){
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("startMonth", Integer.toString(startMonth));
		map.put("endMonth", Integer.toString(endMonth));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
		map.put("region", region);
    	return getSqlMapClientTemplate().queryForList("V_RP_MN_BSC_GPRS_CS_BH.filter", map);
    }
}