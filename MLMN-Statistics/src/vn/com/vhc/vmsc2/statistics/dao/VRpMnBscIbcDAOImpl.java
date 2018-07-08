package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.VRpMnBscIbc;

public class VRpMnBscIbcDAOImpl extends SqlMapClientDaoSupport implements VRpMnBscIbcDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_BSC_IBC
     *
     * @ibatorgenerated Fri Dec 17 08:55:10 ICT 2010
     */
    public VRpMnBscIbcDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_BSC_IBC
     *
     * @ibatorgenerated Fri Dec 17 08:55:10 ICT 2010
     */
    public void insert(VRpMnBscIbc record) {
        getSqlMapClientTemplate().insert("V_RP_MN_BSC_IBC.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_BSC_IBC
     *
     * @ibatorgenerated Fri Dec 17 08:55:10 ICT 2010
     */
    public void insertSelective(VRpMnBscIbc record) {
        getSqlMapClientTemplate().insert("V_RP_MN_BSC_IBC.ibatorgenerated_insertSelective", record);
    }

    @SuppressWarnings("unchecked")
    public List<VRpMnBscIbc> filter(Integer month,Integer year, String bscid, String region){
    	VRpMnBscIbc key = new VRpMnBscIbc();
    	key.setBscid(bscid);
    	key.setMonth(month);
    	key.setYear(year);
    	key.setRegion(region);
    	return getSqlMapClientTemplate().queryForList("V_RP_MN_BSC_IBC.filter", key);
    }
    
    @SuppressWarnings("unchecked")
    public List<VRpMnBscIbc> filter(Integer startMonth,Integer startYear, Integer endMonth,Integer endYear,String bscid, String region){
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("startMonth", Integer.toString(startMonth));
		map.put("endMonth", Integer.toString(endMonth));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
		map.put("region", region);
    	return getSqlMapClientTemplate().queryForList("V_RP_MN_BSC_IBC.filter2", map);
    }
}