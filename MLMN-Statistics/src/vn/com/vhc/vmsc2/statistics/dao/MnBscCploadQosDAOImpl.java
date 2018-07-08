package vn.com.vhc.vmsc2.statistics.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import vn.com.vhc.vmsc2.statistics.domain.MnBscCploadQos;

public class MnBscCploadQosDAOImpl extends SqlMapClientDaoSupport implements MnBscCploadQosDAO {

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_BSC_CPLOAD_QOS
     *
     * @ibatorgenerated Wed Feb 23 17:14:23 ICT 2011
     */
    public MnBscCploadQosDAOImpl() {
        super();
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_BSC_CPLOAD_QOS
     *
     * @ibatorgenerated Wed Feb 23 17:14:23 ICT 2011
     */
    public void insert(MnBscCploadQos record) {
        getSqlMapClientTemplate().insert("V_RP_MN_BSC_CPLOAD_QOS.ibatorgenerated_insert", record);
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method corresponds to the database table V_RP_MN_BSC_CPLOAD_QOS
     *
     * @ibatorgenerated Wed Feb 23 17:14:23 ICT 2011
     */
    public void insertSelective(MnBscCploadQos record) {
        getSqlMapClientTemplate().insert("V_RP_MN_BSC_CPLOAD_QOS.ibatorgenerated_insertSelective", record);
    }
    
    @SuppressWarnings("unchecked")
    public List<MnBscCploadQos> filter(Integer startMonth,Integer startYear, Integer endMonth,Integer endYear,String bscid, String region){
		Map<String, String> map = new HashMap<String, String>();
		map.put("bscid", bscid);
		map.put("startMonth", Integer.toString(startMonth));
		map.put("endMonth", Integer.toString(endMonth));
		map.put("startYear", Integer.toString(startYear));
		map.put("endYear", Integer.toString(endYear));
		map.put("region", region);
    	return getSqlMapClientTemplate().queryForList("V_RP_MN_BSC_CPLOAD_QOS.filter2", map);
    }
}